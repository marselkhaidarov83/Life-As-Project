package com.MH83.LifeAsProject.view.Money.MoneyCategory;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyCategoryLegendToSettingsPopup extends CustomLegendPopup
{
    public FragmentMoneyCategoryLegendToSettingsPopup()
    {
        super(R.layout.popup_legend_money_category_settings);
    }

    public FragmentMoneyCategoryLegendToSettingsPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_category_settings);
    }
}
