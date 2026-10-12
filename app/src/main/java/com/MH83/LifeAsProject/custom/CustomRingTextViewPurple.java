package com.MH83.LifeAsProject.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.R;

public class CustomRingTextViewPurple extends androidx.appcompat.widget.AppCompatTextView
{
    private int defaultColor1 = R.color.colorPrimary;
//    private int defaultColor2 = R.color.colorPrimary;
    private int mCenterX;
    private int mCenterY;
    private int mRadius;
    private Paint mPaint;
//    private Paint mPaint2;

    public CustomRingTextViewPurple(Context context)
    {
        super(context);
        init();
    }

    public CustomRingTextViewPurple(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomRingTextViewPurple(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
//        ImageView imageView = new ImageView(getContext());
//        imageView.setImageDrawable(AppCompatResources.getDrawable(getContext(),
//                R.drawable.icons_ring_white_30));
//        imageView.draw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
//        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint2);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int size = Math.max(height, width);
        mCenterX = mCenterY = mRadius = size/2;
        mRadius -= getResources().getDimension(R.dimen.Padding1dp);
        setMeasuredDimension(size, size);
    }

    @SuppressLint("ResourceAsColor")
    public void setColorBackground(int color1, int color2)
    {
        defaultColor1 = color1;
        mPaint.setColor(defaultColor1);

//        defaultColor2 = color2;
//        mPaint.setColor(defaultColor2);

        this.invalidate();
    }

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.Padding2dp));

//        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint2.setStyle(Paint.Style.FILL);

        setGravity(Gravity.CENTER);
        setColorBackground(defaultColor1, defaultColor1);
    }
}
