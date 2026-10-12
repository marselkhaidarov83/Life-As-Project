package com.MH83.LifeAsProject.view.Money.MoneyIncoming;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;

public class FragmentMoneyIncomingPopupNew extends FragmentMoneyIncomingAndCostPopupNew
{
    //region Constructors
    public FragmentMoneyIncomingPopupNew()
    {
        super(R.string.fragmentMoneyIncomingPopupAddTitle,
                R.string.fragmentMoneyIncomingPopupUpdateTitle,
                R.string.errorUpdateMoneyIncoming);
    }
    public FragmentMoneyIncomingPopupNew(AppCompatActivity activity,
                                         MoneyIncoming data,
                                         EnumTypeAction typeAction,
                                         IFragmentViewCustom iFragmentViewCustom,
                                         MoneyCategory moneyCategory,
                                         MoneyIncrease moneyIncrease)
    {
        super(activity, data, typeAction, iFragmentViewCustom,
                R.string.fragmentMoneyIncomingPopupAddTitle,
                R.string.fragmentMoneyIncomingPopupUpdateTitle,
                R.string.errorUpdateMoneyIncoming,
                moneyCategory, moneyIncrease);
    }
}