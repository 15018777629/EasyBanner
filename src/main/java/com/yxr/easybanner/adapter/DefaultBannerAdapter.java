package com.yxr.easybanner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 63062 on 2017/8/15.
 */

public abstract class DefaultBannerAdapter extends BaseBannerAdapter {
    private final Context context;

    public DefaultBannerAdapter(List<String> datas, boolean isLoop, Context context) {
        super(datas, isLoop);
        this.context = context;
    }

    @Override
    public View instantiateItem(ViewGroup container, Object o) {
        String url = (String) o;
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        loadImage(imageView,url);

        container.addView(imageView);
        return imageView;
    }

    public abstract void loadImage(ImageView imageView, String url);
}
