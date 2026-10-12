package com.MH83.LifeAsProject.view.Custom.Param;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentParamLegendPopup extends CustomLegendPopup
{
    public FragmentParamLegendPopup()
    {
        super(R.layout.popup_legend_param);
    }

    public FragmentParamLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_param);
    }
}
