package com.MH83.LifeAsProject.view.Money.MoneyPlanFact;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFact;

public class FragmentMoneyIncomingPlanPopupNew extends FragmentMoneyPlanPopupNew
{
    //region Constructors
    public FragmentMoneyIncomingPlanPopupNew()
    {
        super(R.string.fragmentMoneyIncomingPopupAddTitle,
                R.string.fragmentMoneyIncomingPopupUpdateTitle,
                R.string.errorUpdateMoneyIncoming);
    }
    public FragmentMoneyIncomingPlanPopupNew(AppCompatActivity activity,
                                             MoneyPlanFact data,
                                             EnumTypeAction typeAction,
                                             IFragmentViewCustom iFragmentViewCustom,
                                             MoneyCategory moneyCategory,
                                             int month,
                                             int year)
    {
        super(activity,
                data,
                typeAction,
                iFragmentViewCustom,
                moneyCategory,
                R.string.fragmentMoneyIncomingPopupAddTitle,
                R.string.fragmentMoneyIncomingPopupUpdateTitle,
                R.string.errorUpdateMoneyIncoming,
                month,
                year);
    }
}