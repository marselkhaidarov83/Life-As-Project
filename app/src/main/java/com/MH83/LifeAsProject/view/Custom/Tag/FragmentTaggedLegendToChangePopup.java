package com.MH83.LifeAsProject.view.Custom.Tag;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTaggedLegendToChangePopup extends CustomLegendPopup
{
    public FragmentTaggedLegendToChangePopup()
    {
        super(R.layout.popup_legend_money_tagged_tochange);
    }

    public FragmentTaggedLegendToChangePopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_tagged_tochange);
    }
}
