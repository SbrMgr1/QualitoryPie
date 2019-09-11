package com.qualitorypie.qualitorypie.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qualitorypie.qualitorypie.R;
import com.squareup.picasso.Picasso;

public class HomeBanner extends PagerAdapter {
    private Context current_activity;
//    private int[] imageIds = new int[]{R.mipmap.banners1_fg, R.mipmap.banners2_fg};
    private int[] imageIds = new int[]{R.mipmap.banners1_fg, R.mipmap.banners2_fg};

    public HomeBanner(Context context) {
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
        if(position == 0){
            Picasso.get()
                    .load("http://laz-img-cdn.alicdn.com/images/ims-web/TB129nvfrj1gK0jSZFuXXcrHpXa.jpg_1200x1200Q100.jpg_.webp")
                    .into(imageView);

        }else{
            imageView.setImageResource(imageIds[position]);
        }
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


}
