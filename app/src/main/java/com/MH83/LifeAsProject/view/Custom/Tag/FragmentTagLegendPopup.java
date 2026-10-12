package com.MH83.LifeAsProject.view.Custom.Tag;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTagLegendPopup extends CustomLegendPopup
{
    public FragmentTagLegendPopup()
    {
        super(R.layout.popup_legend_tag);
    }

    public FragmentTagLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_tag);
    }
}
