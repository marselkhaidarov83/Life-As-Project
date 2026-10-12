package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;

import java.util.Calendar;

public class FragmentMoneyIncomingPopupNew extends FragmentMoneyAccountingPopupNew
{
    //region Constructors
    public FragmentMoneyIncomingPopupNew()
    {
        super(R.string.fragmentMoneyFactIncomingPopupAddTitle,
                R.string.fragmentMoneyFactIncomingPopupUpdateTitle,
                R.string.errorUpdateMoneyFactIncoming);
    }
    public FragmentMoneyIncomingPopupNew(AppCompatActivity activity,
                                         MoneyPNL data,
                                         EnumTypeAction typeAction,
                                         IFragmentViewCustom iFragmentViewCustom,
                                         IMASCategory moneyAccountingSumCost,
                                         IMASCategory moneyAccountingSumIncrease,
                                         Calendar startDate, Calendar endDate,
                                         boolean editable,
                                         boolean editIsPlan)
    {
        super(activity, data, typeAction, iFragmentViewCustom,
                R.string.fragmentMoneyFactIncomingPopupAddTitle,
                R.string.fragmentMoneyFactIncomingPopupUpdateTitle,
                R.string.errorUpdateMoneyFactIncoming,
                moneyAccountingSumCost, moneyAccountingSumIncrease, startDate, endDate,
                editable, editable, editable, editable, editIsPlan);
    }
}