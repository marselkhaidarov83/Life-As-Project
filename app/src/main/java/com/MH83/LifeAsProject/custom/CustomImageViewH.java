package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.util.AttributeSet;

public class CustomImageViewH extends androidx.appcompat.widget.AppCompatImageView
{
    public CustomImageViewH(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CustomImageViewH(Context context) {
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
