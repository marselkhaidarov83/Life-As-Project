package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.util.AttributeSet;

public class CustomImageViewW extends androidx.appcompat.widget.AppCompatImageView
{
    public CustomImageViewW(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CustomImageViewW(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 3 * width / 4;
        this.setMeasuredDimension(width, height);
    }
}
