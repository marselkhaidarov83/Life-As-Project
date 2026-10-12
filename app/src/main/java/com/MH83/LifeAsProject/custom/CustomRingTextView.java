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

public class CustomRingTextView extends androidx.appcompat.widget.AppCompatTextView
{
    private int defaultColor = Color.WHITE;

    private int mCenterX;
    private int mCenterY;
    private int mRadius;
    private Paint mPaint;

    public CustomRingTextView(Context context)
    {
        super(context);
        init();
    }

    public CustomRingTextView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomRingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

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
    public void setColorBackground(int color)
    {
        defaultColor = color;
        mPaint.setColor(defaultColor);

        this.invalidate();
    }

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.Padding2dp));

        setGravity(Gravity.CENTER);
        setColorBackground(defaultColor);
    }
}
