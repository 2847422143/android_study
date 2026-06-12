package com.example.android_study.AcitivityStudy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import com.example.android_study.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class nineActivity extends AppCompatActivity {

    private  static  final int TAKE_PHOTO = 1;

    private ImageView picture;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_nine);

        Button button =findViewById(R.id.take_photo);
        picture = (ImageView) findViewById(R.id.image_view);
        button.setOnClickListener(v -> {
            File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(Build.VERSION.SDK_INT >= 24){
                imageUri = FileProvider.getUriForFile(nineActivity.this, "com.example.android.fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
        });

        Button capturePhoto = findViewById(R.id.capture_photo);
        capturePhoto.setOnClickListener(v -> {
            Log.d("nineActivity", "capturePhoto");
            // 通过系统选择器(ACTION_GET_CONTENT)挑图不需要任何存储权限,
            // 系统会临时授予返回 Uri 的读权限。
            // Android 11+ 申请 WRITE_EXTERNAL_STORAGE 会被直接拒绝,Android 13+ 读图要用 READ_MEDIA_IMAGES。
            openAlbum();
        });
    }
    private void openAlbum() {
        Log.d("nineActivity", "openAlbum");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            // 处理图片
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                picture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // 处理图片
            if (data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    picture.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}