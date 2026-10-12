package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;

import java.util.Calendar;

public class FragmentMoneyMovingPopupNew extends FragmentMoneyAccountingPopupNew
{
    //region Constructors
    public FragmentMoneyMovingPopupNew()
    {
        super(R.string.fragmentMoneyMovingPopupAddTitle,
                R.string.fragmentMoneyMovingPopupUpdateTitle,
                R.string.errorUpdateMoneyMoving);
    }
    public FragmentMoneyMovingPopupNew(AppCompatActivity activity,
                                       MoneyPNL data,
                                       EnumTypeAction typeAction,
                                       IFragmentViewCustom iFragmentViewCustom,
                                       IMASCategory moneyAccountingSumCost,
                                       IMASCategory moneyAccountingSumIncrease,
                                       Calendar startDate, Calendar endDate,
                                       boolean editableIncreaseCategory,
                                       boolean editable)
    {
        super(activity, data, typeAction, iFragmentViewCustom,
                R.string.fragmentMoneyMovingPopupAddTitle,
                R.string.fragmentMoneyMovingPopupUpdateTitle,
                R.string.errorUpdateMoneyMoving,
                moneyAccountingSumCost, moneyAccountingSumIncrease, startDate, endDate,
                editable,
                data == null || !data.get_calculated(),
                true,
                editableIncreaseCategory,
                true);
    }
}