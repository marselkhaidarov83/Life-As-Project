package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class CustomSubsamplingScaleImageViewH extends SubsamplingScaleImageView
{
    public CustomSubsamplingScaleImageViewH(Context context, AttributeSet attr)
    {
        super(context, attr);
    }

    public CustomSubsamplingScaleImageViewH(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = 4 * height / 3;
        this.setMeasuredDimension(width, height);
    }
}
