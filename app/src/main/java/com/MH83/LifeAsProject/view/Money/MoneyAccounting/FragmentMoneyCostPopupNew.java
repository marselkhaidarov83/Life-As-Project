package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;

import java.util.Calendar;

public class FragmentMoneyCostPopupNew extends FragmentMoneyAccountingPopupNew
{
    //region Constructors
    public FragmentMoneyCostPopupNew()
    {
        super(R.string.fragmentMoneyFactCostPopupAddTitle,
                R.string.fragmentMoneyFactCostPopupUpdateTitle,
                R.string.errorUpdateMoneyFactCost);
    }
    public FragmentMoneyCostPopupNew(AppCompatActivity activity,
                                     MoneyPNL data,
                                     EnumTypeAction typeAction,
                                     IFragmentViewCustom iFragmentViewCustom,
                                     IMASCategory moneyAccountingSumCost,
                                     IMASCategory moneyAccountingSumIncrease,
                                     Calendar startDate, Calendar endDate,
                                     boolean editable,
                                     boolean editableCostCategory,
                                     boolean editIsPlan)
    {
        super(activity, data, typeAction, iFragmentViewCustom,
                R.string.fragmentMoneyFactCostPopupAddTitle,
                R.string.fragmentMoneyFactCostPopupUpdateTitle,
                R.string.errorUpdateMoneyFactCost,
                moneyAccountingSumCost, moneyAccountingSumIncrease, startDate, endDate,
                editable,
//                data == null || !data.get_calculated(),
                true,
                editableCostCategory,
                editable,
                editIsPlan);
    }
}