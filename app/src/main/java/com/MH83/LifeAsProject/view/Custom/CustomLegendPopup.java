package com.MH83.LifeAsProject.view.Custom;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class CustomLegendPopup extends CustomLegendBottomFragment
{
    public CustomLegendPopup(int layoutId)
    {
        super(layoutId);
    }

    public CustomLegendPopup(AppCompatActivity activity, int layoutId)
    {
        super(activity, layoutId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public void onStart()
    {
        super.onStart();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
