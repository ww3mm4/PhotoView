package com.photos.photosdemo.ImageDetail.downloadImage;

/**
 * Created by zhangyao on 16/12/16.
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * 图片下载
 *
 */
public class DownLoadImageAsyncTask extends AsyncTask<String, Void, File> {
    private Context context;
    private ImageDownLoadCallBack callBack;
    public DownLoadImageAsyncTask(Context context, ImageDownLoadCallBack callBack) {
        this.callBack = callBack;
        this.context = context;
    }


    private void saveImageToGallery(Context context, File file,Bitmap bmp) {
        // 首先保存图片


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
    }

    //获取App名称
    private String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }



    @Override
    protected File doInBackground(String... params) {
        File file = getImageFile(params[0]);
        if (!file.exists()) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .load(params[0])
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if (bitmap != null) {
                    // 在这里执行图片保存方法
                    saveImageToGallery(context,file,bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file;
        }
        else {
            return file;
        }
    }

    //获取图片file对象
    private File getImageFile(String url){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = getApplicationName(context);
        File appDir = new File(file ,fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = getMD5(url) + ".jpg";
        File currentFile = new File(appDir, fileName);
        return currentFile;
    }

    //url转MD5
    private String getMD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(File file) {
        if (file.exists()){
            callBack.onDownLoadSuccess(file);
        }else {
            callBack.onDownLoadFailed();
        }
    }
}