package com.photos.photosdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.photos.photosdemo.ImageDetail.fragment.ImagesDetailFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] urls = {"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=758184152,2811195219&fm=21&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3350782413,2517396971&fm=11&gp=0.jpg",
                "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3706555796,1550133346&fm=58",
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=758184152,2811195219&fm=21&gp=0.jpg"};
        Bundle bundle = new Bundle();
        bundle.putInt(ImagesDetailFragment.EXTRA_IMAGE_INDEX,0);
        bundle.putStringArray(ImagesDetailFragment.EXTRA_IMAGE_URLS,urls);
        ImagesDetailFragment fragment = new ImagesDetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.activity_main,fragment).commitAllowingStateLoss();
    }
}
