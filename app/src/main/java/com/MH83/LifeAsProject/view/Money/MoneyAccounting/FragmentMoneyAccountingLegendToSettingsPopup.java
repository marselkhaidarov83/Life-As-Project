package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyAccountingLegendToSettingsPopup extends CustomLegendPopup
{
    public FragmentMoneyAccountingLegendToSettingsPopup()
    {
        super(R.layout.popup_legend_money_accounting_settings);
    }

    public FragmentMoneyAccountingLegendToSettingsPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_accounting_settings);
    }
}
