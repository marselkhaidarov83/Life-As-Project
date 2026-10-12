package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyTransactionLegendPopup extends CustomLegendPopup
{
    public FragmentMoneyTransactionLegendPopup()
    {
        super(R.layout.popup_legend_money_transaction);
    }

    public FragmentMoneyTransactionLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_transaction);
    }
}
