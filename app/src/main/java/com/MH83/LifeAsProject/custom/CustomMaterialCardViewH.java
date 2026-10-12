package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.card.MaterialCardView;

public class CustomMaterialCardViewH extends MaterialCardView
{
    public CustomMaterialCardViewH(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CustomMaterialCardViewH(Context context) {
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
