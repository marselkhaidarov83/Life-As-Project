package com.MH83.LifeAsProject.view.Money.MoneyBoard;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyBoardLegendPopup extends CustomLegendPopup
{
    public FragmentMoneyBoardLegendPopup()
    {
        super(R.layout.popup_legend_money_board);
    }

    public FragmentMoneyBoardLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_board);
    }
}
