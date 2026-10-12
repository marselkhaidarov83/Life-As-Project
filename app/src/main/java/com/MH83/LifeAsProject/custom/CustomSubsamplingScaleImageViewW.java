package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class CustomSubsamplingScaleImageViewW extends SubsamplingScaleImageView
{
    private int _height = 1000;
    public int get_Height() { return _height; }

    public CustomSubsamplingScaleImageViewW(Context context, AttributeSet attr)
    {
        super(context, attr);
    }

    public CustomSubsamplingScaleImageViewW(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        _height = 3 * width / 4;
        this.setMeasuredDimension(width, _height);
    }
}
