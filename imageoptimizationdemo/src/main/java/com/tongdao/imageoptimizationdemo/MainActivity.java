package com.tongdao.imageoptimizationdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;

/**
 * Android中的图片优化的demo
 * 三种方式对图片进行优化
 * A:等比例压缩
 * B:选择图片的大小方式
 * C:加载部分的图片
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mIv;
    private WindowManager mWm;
    private int mWidth;
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = (ImageView) findViewById(R.id.iv);
        mWm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWidth = mWm.getDefaultDisplay().getWidth();
        mHeight = mWm.getDefaultDisplay().getHeight();
        loadPartPic();
    }

    /**
     * 加载部分的图片
     */
    private void loadPartPic() {
        File file = new File("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1492137648180.jpg");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            //获取图片的宽和高
            BitmapFactory.Options options = new BitmapFactory.Options();
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;
            //设置图片的显示区域 (中心区域)
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(outWidth / 2, outHeight / 2, outWidth / 2, outHeight / 2), options1);
            System.out.println("bitmap的图片的大小事 : " + bitmap.getByteCount());
            mIv.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过等比缩放加上图片的解码方式进行图片的优化
     */
    private void changeARGB() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        BitmapFactory.decodeFile("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1492137648180.jpg", options);
        //获取图片的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //计算缩放比,,按照大的去缩放
        int scale = 1;
        int scaleX = outWidth / mWidth;
        int scaleY = outHeight / mHeight;
        if (scaleX >= scaleY && scaleX > scale) {
            scale = scaleX;
        }

        if (scaleY >= scaleX && scaleY > scale) {
            scale = scaleY;
        }
        System.out.println("图片的缩放比例大小是:" + scale);
        //按照计算出来的缩放比及进行缩放
        options.inSampleSize = scale;
        //开始真正的解析位图对象
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1492137648180.jpg", options);
        System.out.println("bitmap图片的大小:" + bitmap.getByteCount());
        mIv.setImageBitmap(bitmap);
    }

    /**
     * 改变图片的大小方式对图片进行缩放
     */
    private void changePicSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1492137648180.jpg", options);
        //获取图片的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //计算缩放比,,按照大的去缩放
        int scale = 1;
        int scaleX = outWidth / mWidth;
        int scaleY = outHeight / mHeight;
        if (scaleX >= scaleY && scaleX > scale) {
            scale = scaleX;
        }

        if (scaleY >= scaleX && scaleY > scale) {
            scale = scaleY;
        }

        System.out.println("图片的缩放比例是:" + scale);
        //按照计算出来的缩放比及进行缩放
        options.inSampleSize = scale;
        //开始真正的解析位图对象
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1492137648180.jpg", options);
        System.out.println("bitmap图片的大小:" + bitmap.getByteCount());
        mIv.setImageBitmap(bitmap);
    }

    /**
     * 普通方式获取图片
     */
    private void getPicture() {
        File file = new File("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1492137648180.jpg");
        System.out.println("图片的大小 :" + file.length());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        System.out.println("bitmap的大小:" + bitmap.getByteCount() + "=='" + file.getAbsolutePath());
        mIv.setImageBitmap(bitmap);
        //图片的大小 :983436
        //bitmap的大小:40310784
    }
}
