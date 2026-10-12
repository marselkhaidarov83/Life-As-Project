package com.MH83.LifeAsProject.view.Custom.Tag;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTagLegendToChangePopup extends CustomLegendPopup
{
    public FragmentTagLegendToChangePopup()
    {
        super(R.layout.popup_legend_tag_tochange);
    }

    public FragmentTagLegendToChangePopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_tag_tochange);
    }
}
