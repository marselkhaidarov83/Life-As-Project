package com.MH83.LifeAsProject.view.Custom.Tag;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTagLegendToSettingsPopup extends CustomLegendPopup
{
    public FragmentTagLegendToSettingsPopup()
    {
        super(R.layout.popup_legend_tag_settings);
    }

    public FragmentTagLegendToSettingsPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_tag_settings);
    }
}
