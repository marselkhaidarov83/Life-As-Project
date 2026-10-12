package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyDreamSettings extends CustomMoneyObject
{
    //region Parameters
    private boolean _showArchive;
    public boolean get_showArchive() { return _showArchive; }
    public void set_showArchive(boolean b) { _showArchive = b; }
    //endregion

    //region Constructors
    public MoneyDreamSettings(boolean showArchive)
    {
        _showArchive = showArchive;
    }

    public MoneyDreamSettings Clone()
    {
        return new MoneyDreamSettings(_showArchive);
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
            MoneyDreamSettings moneyDreamSettings)
    {
        editor.putBoolean("MoneyDreamSettings.ShowArchive", moneyDreamSettings.get_showArchive());

        return editor;
    }

    public static MoneyDreamSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        MoneyDreamSettings moneyDreamSettings = new MoneyDreamSettings(true);
        moneyDreamSettings.set_showArchive(settings.getBoolean("MoneyDreamSettings.ShowArchive", true));

        return moneyDreamSettings;
    }
    //endregion
}
