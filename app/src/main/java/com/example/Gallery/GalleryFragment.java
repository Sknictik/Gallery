package com.example.Gallery;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;

import java.io.IOException;
import java.util.List;

public class GalleryFragment extends Fragment {

    private ImageAdapter mAdapter;
    public LruMemoryCache mRetainedCache;
    private static final String TAG = "GalleryFragment";

    private View v;
    private DoubleTapGridView gridView;
    public GalleryFragment() {}


   public static GalleryFragment findOrCreateGalleryFragment(FragmentManager fm) {
        GalleryFragment fragment = (GalleryFragment) fm.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new GalleryFragment();
            fm.beginTransaction().add(R.id.mLayout, fragment, TAG).commit();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.gallery_fragment, container, false);

        gridView = (DoubleTapGridView) v.findViewById(R.id.gridview);
        List<String> urls = null;
        try {
            urls = ImageUrlJSONParser.parse(getResources().openRawResource(R.raw.jsondump));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (urls != null) {
            mAdapter = new ImageAdapter(getActivity(), urls);
            gridView.setAdapter(mAdapter);
        }
        else {
            Log.e("JSON", "Invalid input data");
        }

        gridView.setOnItemDoubleClickListener(new DoubleTapGridView.OnItemDoubleTapLister() {
            @Override
            public void OnDoubleTap(AdapterView parent, View view, int position, long id) {
                ZoomUtils.zoomImageFromThumbnail(getActivity(), view,
                        mAdapter.getImageURL(position));
            }

            @Override
            public void OnSingleTap(AdapterView parent, View view, int position, long id) {
                //Do nothing
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Clean up
        mAdapter = null;
        gridView = null;
        v = null;
    }

}

