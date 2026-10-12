package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyTransactionLegendToChangePopup extends CustomLegendPopup
{
    public FragmentMoneyTransactionLegendToChangePopup()
    {
        super(R.layout.popup_legend_money_transaction_tochange);
    }

    public FragmentMoneyTransactionLegendToChangePopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_transaction_tochange);
    }
}
