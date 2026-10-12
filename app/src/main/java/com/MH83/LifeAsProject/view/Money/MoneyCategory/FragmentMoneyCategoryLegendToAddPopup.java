package com.MH83.LifeAsProject.view.Money.MoneyCategory;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyCategoryLegendToAddPopup extends CustomLegendPopup
{
    public FragmentMoneyCategoryLegendToAddPopup()
    {
        super(R.layout.popup_legend_money_category_toadd);
    }

    public FragmentMoneyCategoryLegendToAddPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_category_toadd);
    }
}
