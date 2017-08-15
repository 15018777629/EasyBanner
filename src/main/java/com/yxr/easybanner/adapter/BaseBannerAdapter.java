package com.yxr.easybanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 63062 on 2017/8/14.
 */

public abstract class BaseBannerAdapter<T> extends PagerAdapter {
    public List<T> datas;
    public boolean isLoop;

    public BaseBannerAdapter(List<T> datas,boolean isLoop){
        this.datas = datas;
        this.isLoop = isLoop;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : isLoop ? Integer.MAX_VALUE : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null && object != null && object instanceof View){
            Log.e("TAG", "destroyItem: " );
            container.removeView((View) object);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return instantiateBanner(container, position);
    }

    private Object instantiateBanner(ViewGroup container, int position) {
        Log.e("TAG", "instantiateBanner: " + getCount() + "----------" + position );
        T t = datas.get(position % datas.size());
        return instantiateItem(container,t);
    }

    public abstract View instantiateItem(ViewGroup container, T t);

    public T getItem(int position) {
        return datas == null ? null : datas.size() <= 0 ? null : datas.get(position % datas.size());
    }
}
