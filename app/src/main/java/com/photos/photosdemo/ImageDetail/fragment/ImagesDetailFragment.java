package com.photos.photosdemo.ImageDetail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.photos.photosdemo.ImageDetail.downloadImage.DownLoadImageAsyncTask;
import com.photos.photosdemo.ImageDetail.downloadImage.ImageDownLoadCallBack;
import com.photos.photosdemo.ImageDetail.fragment.adapter.ImagePagerAdapter;
import com.photos.photosdemo.ImageDetail.viewpager.HackyViewPager;
import com.photos.photosdemo.R;

import java.io.File;

/**
 * Created by zhangyao on 16/12/16.
 */

public class ImagesDetailFragment extends Fragment {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private DownLoadImageAsyncTask asyncTask;
    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private TextView download;
    private String[] urls = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_detail_pager,null);
        Bundle bundle = getArguments();

        if (bundle !=null) {
            pagerPosition = bundle.getInt(EXTRA_IMAGE_INDEX, 0);
            urls = bundle.getStringArray(EXTRA_IMAGE_URLS);
        }

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        initView(view);
        initData();
        initListener();

        return view;
    }

    private void initListener() {
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTask = new DownLoadImageAsyncTask(getContext(), new ImageDownLoadCallBack() {
                    @Override
                    public void onDownLoadSuccess(File file) {
                        Toast.makeText(getContext(),"保存成功",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDownLoadFailed() {
                        Toast.makeText(getContext(),"保存失败",Toast.LENGTH_LONG).show();
                    }
                });
                asyncTask.execute(urls[mPager.getCurrentItem()]);
            }
        });
    }

    private void initData() {

    }

    private void initView(View view) {
        mPager = (HackyViewPager) view.findViewById(R.id.image_detail_viewpager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getChildFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) view.findViewById(R.id.image_detail_indicator);
        download = (TextView) view.findViewById(R.id.image_detail_download);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
                .getAdapter().getCount());
        indicator.setText(text);
        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (asyncTask!=null){
            asyncTask.cancel(true);
        }
    }
}
