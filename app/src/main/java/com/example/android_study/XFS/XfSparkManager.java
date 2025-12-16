package com.example.android_study.XFS;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//星火大模型对接（科大讯飞）
public class XfSparkManager {
    private static final String TAG = "XfSparkManager";
    // ========== 替换为你Java端的APIPassword（成品Token） ==========
    private static final String APIPassword = "ZqAtHBKuxxNmDzIqLcNb:ZbUzJVpKSaAgbBCOivii"; // 填入你Java端的APIPassword值
    // 接口地址（和Java端完全一致）
    private static final String API_URL = "https://spark-api-open.xf-yun.com/v2/chat/completions";
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    // 回调接口（保持不变）
    public interface ChatCallback {
        void onStreamData(String partialContent);
        void onSuccess();
        void onError(String errorMsg);
    }

    // 核心方法：完全对齐Java端请求逻辑
    public static void chatWithSpark(String userInput, String userId, ChatCallback callback) {
        new Thread(() -> {
            try {
                // ========== 1. 构造请求体（完全复刻Java端） ==========
                JSONObject jsonObject = new JSONObject();
                // 外层参数
                jsonObject.put("user", userId); // 你的用户ID：10284711用户
                jsonObject.put("model", "x1"); // 和Java端一致，用x1模型
                jsonObject.put("stream", true); // 流式返回
                jsonObject.put("max_tokens", 4096); // 最大令牌数

                // 创建messages数组
                JSONArray messagesArray = new JSONArray();
                // 创建单个消息对象（Temperature放在这里！）
                JSONObject messageObject = new JSONObject();
                messageObject.put("role", "user");
                messageObject.put("content", userInput);
                messageObject.put("temperature", "0.5"); // 关键：移到message对象内
                // 添加消息到数组
                messagesArray.put(messageObject);
                // 将messages数组添加到外层
                jsonObject.put("messages", messagesArray);

                String requestBody = jsonObject.toString();

                // ========== 2. 构造请求头（和Java端完全一致） ==========
                Request request = new Request.Builder()
                        .url(API_URL)
                        // 关键：Authorization拼接方式和Java端一致（Bearer后无空格）
                        .addHeader("Authorization", "Bearer" + APIPassword)
                        // 仅添加Content-Type，不添加Date/Host/X-Appid（和Java端一致）
                        .addHeader("Content-Type", "application/json")
                        .post(RequestBody.create(JSON_MEDIA_TYPE, requestBody))
                        .build();

                // ========== 3. 执行请求（和Java端一致） ==========
                Response response = OK_HTTP_CLIENT.newCall(request).execute();
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "无响应体";
                    throw new IOException("请求失败：" + response.code() + " " + response.message() +
                            " | 响应信息：" + errorBody);
                }

                // ========== 4. 解析流式响应（和Java端一致） ==========
                InputStream inputStream = response.body().byteStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    // 打印原始响应（和Java端的System.out.println(inputLine)一致）
                    Log.d(TAG,"line = "+line);
                    // 处理流式数据
                    if (line.startsWith("data: ")) {
                        String dataStr = line.substring(6).trim();
                        // 流式结束标记
                        if ("[DONE]".equals(dataStr)) {
                            MAIN_HANDLER.post(callback::onSuccess);
                            break;
                        }
                        // 解析回复内容
                        try {
                            JSONObject dataJson = new JSONObject(dataStr);
                            String partialContent = dataJson.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("delta")
                                    .optString("content", "");
                            if (!partialContent.isEmpty()) {
                                MAIN_HANDLER.post(() -> callback.onStreamData(partialContent));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                reader.close();
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                MAIN_HANDLER.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    // 简化调用：默认用户ID（和Java端一致）
    public static void chatWithSpark(String userInput, ChatCallback callback) {
        chatWithSpark(userInput, "10284711用户", callback);
    }
}