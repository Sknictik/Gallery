package com.example.Gallery;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    private static ImageLoader universalImageLoader;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Get memoryCache from previous fragment and reattach it
        GalleryFragment retainFragment =
                GalleryFragment.findOrCreateGalleryFragment(getFragmentManager());
        LruMemoryCache mMemoryCache = retainFragment.mRetainedCache;
        if (mMemoryCache == null) {
            mMemoryCache = new LruMemoryCache((int) (Runtime.getRuntime().maxMemory() / 1024) / 4);
            retainFragment.mRetainedCache = mMemoryCache;
        }

        // Initialize universalimageloader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(mMemoryCache)
                .build();

        universalImageLoader = ImageLoader.getInstance();
        universalImageLoader.init(config);

    }

    public static ImageLoader getUniversalImageLoader() {
        return universalImageLoader;
    }

}
