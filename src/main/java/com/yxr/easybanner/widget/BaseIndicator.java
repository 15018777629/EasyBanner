package com.yxr.easybanner.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 63062 on 2017/8/14.
 */

public abstract class BaseIndicator extends View {
    public int total;
    public int index;

    public BaseIndicator(Context context) {
        super(context);
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIndicator(int total){
        this.total = total;
        update();
    }

    public void setIndex(int index){
        this.index = index;
        update();
    }

    abstract void update();

}
