package com.qualitorypie.qualitorypie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qualitorypie.qualitorypie.R;

public class HomeBannerAdapter extends PagerAdapter {
    private Context current_activity;
    private int[] imageIds = new int[]{R.mipmap.banners1_fg, R.mipmap.banners2_fg};

    public HomeBannerAdapter(Context context) {
        current_activity = context;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(current_activity);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(imageIds[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


}
