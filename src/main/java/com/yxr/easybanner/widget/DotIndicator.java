package com.yxr.easybanner.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.yxr.easybanner.R;

/**
 * Created by 63062 on 2017/8/14.
 */

public class DotIndicator extends BaseIndicator {
    private static final int DEFAULT_DOT = 0;
    private static final int CUSTOM_DOT = 1;
    private int dotSpace = 25;
    private int dotSize = 50;
    private int dotType = DEFAULT_DOT;
    private int dotColor = 0xFF000000;
    private int dotSelectColor = 0xFFFFFFFF;
    private int dotImage = R.drawable.dot;
    private int dotSelectImage = R.drawable.dot_select;
    private Paint paint;
    private Bitmap mDotBitmap;
    private Bitmap mDotSelectBitmap;

    public DotIndicator(Context context) {
        this(context,null);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DotIndicator);
            dotType = array.getInteger(R.styleable.DotIndicator_dot_type, DEFAULT_DOT);
            dotColor = array.getColor(R.styleable.DotIndicator_dot_color, 0xFF000000);
            dotSelectColor = array.getColor(R.styleable.DotIndicator_dot_select_color, 0xFFFFFFFF);
            dotImage = array.getResourceId(R.styleable.DotIndicator_dot_image, R.drawable.dot);
            dotSelectImage = array.getResourceId(R.styleable.DotIndicator_dot_select_image, R.drawable.dot_select);
            dotSpace = (int) array.getDimension(R.styleable.DotIndicator_dot_space, 25);
            dotSize = (int) array.getDimension(R.styleable.DotIndicator_dot_size, 50);
        }
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    void update() {
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDotBitmap != null){
            mDotBitmap.recycle();
            mDotBitmap = null;
        }
        if (mDotSelectBitmap != null){
            mDotSelectBitmap.recycle();
            mDotSelectBitmap = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dotType == DEFAULT_DOT){
            drawDefaultDot(canvas);
            return;
        }
        drawImageDot(canvas);
    }

    private void drawImageDot(Canvas canvas) {
        if (total <= 0 || index >= total)
            return;
        if (mDotBitmap == null){
            mDotBitmap = getSizeBitmap(dotImage);
        }
        if (mDotSelectBitmap == null)
            mDotSelectBitmap = getSizeBitmap(dotSelectImage);

        if (mDotBitmap == null || mDotSelectBitmap == null)
            return;

        for (int i = 0 ; i < total ; i++)
            canvas.drawBitmap(i == index ? mDotSelectBitmap : mDotBitmap
                    , dotSize * i + dotSpace * i
                    , i == index ? (dotSize - mDotSelectBitmap.getHeight()) / 2 : (dotSize - mDotBitmap.getHeight()) / 2
                    , paint);
    }

    private Bitmap getSizeBitmap(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        if (bitmap == null)
            return null;
        int bmWidth = bitmap.getWidth();
        int bmHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(dotSize / (float)bmWidth , dotSize / (float)bmHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bmWidth, bmHeight, matrix, true);
        return bitmap;
    }

    private void drawDefaultDot(Canvas canvas) {
        if (total <= 0 || index >= total)
            return;
        for (int i = 0 ; i < total ; i++){
            paint.setColor(i == index ? dotSelectColor : dotColor);
            canvas.drawCircle(dotSize * i + dotSpace * i + dotSize / 2 , dotSize / 2 , dotSize / 2,paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null){
            int width = total * (dotSpace + dotSize) - dotSize / 2;
            layoutParams.width = width;
            layoutParams.height = dotSize;
        }
    }
}
