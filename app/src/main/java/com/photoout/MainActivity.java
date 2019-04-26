package com.photoout;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.compress.CompressImage;
import com.jph.takephoto.compress.CompressImageImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends TakePhotoActivity {
    private TextView cropAvatar;
    private TakePhoto takePhoto;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cropAvatar = findViewById(R.id.cropAvatar);
        image = findViewById(R.id.image);
        takePhoto = getTakePhoto();
        configCompress(takePhoto);
        cropAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto.onPickFromCaptureWithCrop(getUri(), getCropOptions());
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result != null) {
            for (TImage tImage : result.getImages()) {
                Log.e("takeSuccess", tImage.getOriginalPath());//压缩前路径
            }
            CompressImageImpl.of(MainActivity.this, getCompressConfig(), result.getImages(), new CompressImage.CompressListener() {
                @Override
                public void onCompressSuccess(ArrayList<TImage> images) {
                    //图片压缩成功
                    for (TImage tImage : images) {
                        Log.e("onCompressSuccess", tImage.getCompressPath());//压缩后路径
                        Glide.with(MainActivity.this).load(tImage.getCompressPath()).into(image);
                    }
                }

                @Override
                public void onCompressFailed(ArrayList<TImage> images, String msg) {
                    //图片压缩失败
                    for (TImage tImage : images) {
                        Log.e("onCompressFailed", tImage.getOriginalPath());
                    }
                    Log.e("onCompressFailed", msg);
                }
            }).compress();
        }

//        }
    }


    private String getRootDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "photoout");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    private Uri getUri() {
        File file = new File(getRootDir(), "/photoDemo/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    /**
     * 剪切配置
     *
     * @return
     */
    private CropOptions getCropOptions() {
        int height = Integer.parseInt("800");
        int width = Integer.parseInt("800");

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    /**
     * 压缩配置
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = Integer.parseInt("500");
        int height = Integer.parseInt("800");
        int width = Integer.parseInt("800");
        boolean showProgressBar = true;
        boolean enableRawFile = true;//ture保留原图，false删除原图，当且仅当类型为CAMERA此配置才有效
        CompressConfig config;
//        if (rgCompressTool.getCheckedRadioButtonId() == R.id.rbCompressWithOwn) {
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
//        } else {
//            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
//            config = CompressConfig.ofLuban(option);
//            config.enableReserveRaw(enableRawFile);
//        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    /**
     * 压缩成功后返回原图路径(originalPath), 以便用户可以自行处理原图。
     * 压缩成功后压缩路径path改为compressPath。
     * 压缩成功后返回图片来源类型，现在分CAMERA, OTHER两种。
     * 用户可以配置CompressConfig.enableReserveRaw(boolean)方法，ture保留原图，false删除原图，当且仅当类型为CAMERA此配置才有效
     *
     * @return
     */
    private CompressConfig getCompressConfig() {
        int maxSize = 102400;
        int height = 800;
        int width = 800;
        boolean enableRawFile = true;//ture保留原图，false删除原图，当且仅当类型为CAMERA此配置才有效
        CompressConfig config;
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
        return config;
    }
}
