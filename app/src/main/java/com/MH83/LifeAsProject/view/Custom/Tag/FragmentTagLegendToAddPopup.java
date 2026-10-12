package com.MH83.LifeAsProject.view.Custom.Tag;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTagLegendToAddPopup extends CustomLegendPopup
{
    public FragmentTagLegendToAddPopup()
    {
        super(R.layout.popup_legend_tag_toadd);
    }

    public FragmentTagLegendToAddPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_tag_toadd);
    }
}
