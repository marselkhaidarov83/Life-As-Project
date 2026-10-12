package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyAccountingSettings extends CustomMoneyObject
{
    //region Parameters
    private boolean _showBudget;
    public boolean get_showBudget() { return _showBudget; }
    public void set_showBudget(boolean b) { _showBudget = b; }
    //endregion

    //region Constructors
    public MoneyAccountingSettings(boolean showBudget)
    {
        _showBudget = showBudget;
    }

    public MoneyAccountingSettings Clone()
    {
        return new MoneyAccountingSettings(_showBudget);
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
            MoneyAccountingSettings moneyAccountingSettings)
    {
        if (moneyAccountingSettings != null)
            {
                editor.putBoolean("MoneyAccountingSettings.ShowBudget",
                        moneyAccountingSettings.get_showBudget());
            }

        return editor;
    }

    public static MoneyAccountingSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        return new MoneyAccountingSettings(
                settings.getBoolean("MoneyAccountingSettings.ShowBudget", false));
    }
    //endregion
}
