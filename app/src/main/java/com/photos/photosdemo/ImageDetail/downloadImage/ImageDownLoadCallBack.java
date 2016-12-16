package com.photos.photosdemo.ImageDetail.downloadImage;

import java.io.File;

/**
 * Created by zhangyao on 16/12/16.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadFailed();
}
