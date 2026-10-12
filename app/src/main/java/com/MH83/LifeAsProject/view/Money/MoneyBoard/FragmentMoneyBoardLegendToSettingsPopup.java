package com.MH83.LifeAsProject.view.Money.MoneyBoard;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMoneyBoardLegendToSettingsPopup extends CustomLegendPopup
{
    public FragmentMoneyBoardLegendToSettingsPopup()
    {
        super(R.layout.popup_legend_money_board_settings);
    }

    public FragmentMoneyBoardLegendToSettingsPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_money_board_settings);
    }
}
