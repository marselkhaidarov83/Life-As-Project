package com.MH83.LifeAsProject.view.Money.MoneyCosts;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.view.Money.MoneyIncoming.FragmentMoneyIncomingAndCostPopupNew;

public class FragmentMoneyCostPopupNew extends FragmentMoneyIncomingAndCostPopupNew
{
    //region Constructors
    public FragmentMoneyCostPopupNew()
    {
        super(R.string.fragmentMoneyCostPopupAddTitle,
                R.string.fragmentMoneyCostPopupUpdateTitle,
                R.string.errorUpdateMoneyCost);
    }
    public FragmentMoneyCostPopupNew(AppCompatActivity activity,
                                     MoneyIncoming data,
                                     EnumTypeAction typeAction,
                                     IFragmentViewCustom iFragmentViewCustom,
                                     MoneyCategory moneyCategory, MoneyIncrease moneyIncrease)
    {
        super(activity, data, typeAction, iFragmentViewCustom,
                R.string.fragmentMoneyCostPopupAddTitle,
                R.string.fragmentMoneyCostPopupUpdateTitle,
                R.string.errorUpdateMoneyCost,
                moneyCategory, moneyIncrease);
    }
}