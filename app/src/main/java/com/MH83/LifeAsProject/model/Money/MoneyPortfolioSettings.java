package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;

public class MoneyPortfolioSettings extends CustomMoneyObject
{
    //region Parameters
    private ArrayList<Long> _expandedList;
    public ArrayList<Long> get_expandedList() { return _expandedList; }
    public void set_expandedList(ArrayList<Long> list) { _expandedList = list; }
    //endregion

    //region Constructors
    public MoneyPortfolioSettings(ArrayList<Long> expandedList)
    {
        _expandedList = expandedList;
    }

    public MoneyPortfolioSettings Clone()
    {
        return new MoneyPortfolioSettings(_expandedList);
    }
    //endregion

    //region CustomMoneyObject
    @Override
    public String getSValue() { return ""; }

    @Override
    public void set_id(Long id) {}

    @Override
    public boolean canDelete(Context context) { return false; }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }
    //endregion

    //region SaveToSharedPreferences
    public static SharedPreferences.Editor SaveToSharedPreferences(
            SharedPreferences.Editor editor,
            MoneyPortfolioSettings moneyPortfolioSettings)
    {
//        editor.putString("TaskProjectSettings.TargetStatus4Settings",
//                taskProjectSettings.get_enumTargetStatus4Settings().toString());

        return editor;
    }

    public static MoneyPortfolioSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        return new MoneyPortfolioSettings(null);
//        return new MoneyPortfolioSettings(
//                EnumTargetStatus4Settings.valueOf(
//                        settings.getString("TaskProjectSettings.TargetStatus4Settings",
//                                EnumTargetStatus4Settings.All.toString())), null);
    }
    //endregion
}
