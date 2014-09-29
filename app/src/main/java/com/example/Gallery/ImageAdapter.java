package com.example.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;



public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> imgUrls;
    private ImageLoader universalImageLoader;

    public ImageAdapter(Context c, List<String> imageArr) {
            mContext = c;
            imgUrls = imageArr;
            universalImageLoader = MainActivity.getUniversalImageLoader();
        }


    public int getCount() {
            return imgUrls.size();
        }


    @Override
    public Object getItem(int position) {
        Bitmap result = universalImageLoader.getMemoryCache().get(imgUrls.get(position));
        if (result == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            result = BitmapFactory.decodeFile(universalImageLoader.getDiskCache()
                    .get(imgUrls.get(position)).getPath(), options);
        }
        return result;
    }


    public long getItemId(int position) {
            return position;
        }


    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            int image_size = (int) mContext.getResources().getDimension(R.dimen.image_size);
            imageView.setLayoutParams(new GridView.LayoutParams(image_size, image_size));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_launcher)
        .showImageOnFail(R.drawable.error)
        .cacheOnDisk(true)
        .cacheInMemory(true)
        .build();

        universalImageLoader.displayImage(imgUrls.get(position), imageView, options);

        return imageView;
    }


    public String getImageURL(int position) {
        return imgUrls.get(position);
    }
}

