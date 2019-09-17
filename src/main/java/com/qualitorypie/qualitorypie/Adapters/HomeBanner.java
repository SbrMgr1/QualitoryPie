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
    private int[] imageIds = new int[3];

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
                    .load("https://cdn.pixabay.com/photo/2017/03/04/13/52/robot-2116087_960_720.jpg")
                    .placeholder(R.mipmap.loader)
                    .into(imageView);


        }else if(position == 1){
            Picasso.get()
                    .load("https://cdn.pixabay.com/photo/2015/06/12/18/31/cute-807306_960_720.png")
                    .placeholder(R.mipmap.loader)
                    .into(imageView);
//            imageView.setImageResource(imageIds[position]);
        }else{
            Picasso.get()
                    .load("https://cdn.pixabay.com/photo/2016/12/13/21/20/alien-1905155_960_720.png")
                    .placeholder(R.mipmap.loader)
                    .into(imageView);
        }
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


}
