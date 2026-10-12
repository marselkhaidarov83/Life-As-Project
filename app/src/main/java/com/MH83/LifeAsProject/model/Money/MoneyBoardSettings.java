package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;

public class MoneyBoardSettings extends CustomMoneyObject
{
    //region Parameters
    private ArrayList<MoneyBoard> _moneyBoards;
    public ArrayList<MoneyBoard> get_moneyBoards() { return _moneyBoards; }
    public void set_moneyBoards(ArrayList<MoneyBoard> moneyBoards) { _moneyBoards = moneyBoards; }
    //endregion

    //region Constructors
    public MoneyBoardSettings(ArrayList<MoneyBoard> moneyBoards)
    {
        _moneyBoards = moneyBoards;
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
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        for (MoneyBoard item: _moneyBoards)
            if (item.get_id() <= 0L)
                item.AddToDatabase(dbHelper, context);
            else
                item.UpdateInDatabase(dbHelper, context);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }
    //endregion
}
