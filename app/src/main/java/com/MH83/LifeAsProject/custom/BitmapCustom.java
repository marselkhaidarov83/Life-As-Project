package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class BitmapCustom
{
    public static Bitmap createBitmapFromLayout(SubsamplingScaleImageView subsamplingScaleImageView)
    {
        subsamplingScaleImageView.setDrawingCacheEnabled(true);
        subsamplingScaleImageView.buildDrawingCache();
        return subsamplingScaleImageView.getDrawingCache();
    }
}
