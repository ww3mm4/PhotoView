package com.photos.photosdemo.ImageDetail.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.photos.photosdemo.ImageDetail.fragment.ImageFragment;

/**
 * Created by zhangyao on 16/12/16.
 */


public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    public String[] fileList;

    public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
        super(fm);
        this.fileList = fileList;
    }

    @Override
    public int getCount() {
        return fileList == null ? 0 : fileList.length;
    }

    @Override
    public Fragment getItem(int position) {
        String url = fileList[position];
        return ImageFragment.newInstance(url);
    }

}
