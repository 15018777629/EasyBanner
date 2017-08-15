package com.yxr.easybanner.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.yxr.easybanner.R;
import com.yxr.easybanner.adapter.BaseBannerAdapter;

/**
 * Created by 63062 on 2017/8/14.
 */

public class EasyBanner extends ViewPager implements Runnable {
    private boolean isAuto = false;
    private int interval = 5000;
    private BaseIndicator indicator;
    private BaseBannerAdapter adapter;
    private Handler handler = new Handler();
    private PagerChangedListener listener;

    public EasyBanner(Context context) {
        this(context,null);
    }

    public EasyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.EasyBanner);
            isAuto = array.getBoolean(R.styleable.EasyBanner_auto_play,false);
            interval = array.getInteger(R.styleable.EasyBanner_interval,5000);
        }

        if (interval <= 0)
            interval = 1000;

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacksAndMessages(null);
                if (listener != null)
                    listener.onPageSelected(position,adapter == null ? null : adapter.getItem(position));

                if (indicator != null && adapter != null && adapter.datas != null)
                    indicator.setIndex(position % Math.max(1,adapter.datas.size()));
                if (isAuto)
                    handler.postDelayed(EasyBanner.this,interval);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setPagerChangedListener(PagerChangedListener listener){
        this.listener = listener;
    }

    /**
     * 设置轮播图
     * @param indicator ： 轮播图指示器
     * @param adapter ： 轮播Adapter
     */
    public void setBanner(BaseIndicator indicator, BaseBannerAdapter adapter){
        this.indicator = indicator;
        this.adapter = adapter;

        if (indicator != null)
            indicator.total = adapter == null ? 0 : adapter.datas == null ? 0 : adapter.datas.size();

        setAdapter(adapter);

        if (adapter != null && adapter.isLoop){
            int mid = adapter.getCount() / 2;
            int size = adapter.datas == null ? 1 : adapter.datas.size();
            int midPosition = mid % size;
            setCurrentItem(mid - midPosition);
        }
        if (isAuto)
            handler.postDelayed(EasyBanner.this,interval);
    }

    public interface PagerChangedListener<T>{
        void onPageSelected(int position,T t);
    }


    @Override
    public void run() {
        if (adapter == null)
            return;
        if (getCurrentItem() >= adapter.getCount() - 1)
            setCurrentItem(0);
        else
            setCurrentItem(getCurrentItem() + 1);
    }

    /**暂停自动轮播*/
    public void onPause(){
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    /**恢复自动轮播*/
    public void onResume(){
        if (handler != null && isAuto){
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(EasyBanner.this,interval);
        }
    }
}
