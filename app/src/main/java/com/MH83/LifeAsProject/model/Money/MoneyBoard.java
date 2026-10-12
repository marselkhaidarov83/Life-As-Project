package com.MH83.LifeAsProject.model.Money;

import android.content.Context;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumBoard;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;

public class MoneyBoard extends CustomMoneyObject
{
    public static class MoneyBoardData
    {
        public Long LongValue;
        public Boolean ShowAsRuble;

        public MoneyBoardData(Long longValue, Boolean showAsRuble)
        {
            LongValue = longValue;
            ShowAsRuble = showAsRuble;
        }
    }

    private Long _id;
    private Long _position;
    private boolean _visible;

    private final EnumBoard _enumBoard;
    private ArrayList<MoneyBoardData> _values;

    public Long get_id() { return _id; }
    public EnumBoard get_enumBoard() { return _enumBoard; }
    public Long get_position() { return _position; }

    public void set_position(Long position) { _position = position; }

    public int get_visibleI()
    {
        return _visible ? 1 : 0;
    }
    public boolean is_visible() { return _visible; }
    public void inverse_visible() { _visible = !_visible; }

    public ArrayList<MoneyBoardData> get_Values() { return _values; }
    public void set_values(ArrayList<MoneyBoardData> values) { _values = values; }
    public String get_name(Context context)
    {
        switch (_enumBoard)
        {
            case TransactionBoard:
                return context.getResources().getString(R.string.EnumBoard_TransactionBoard);
            case WalletBoard:
                return context.getResources().getString(R.string.EnumBoard_WalletBoard);
            case GoalsAllBoard:
                return context.getResources().getString(R.string.EnumBoard_GoalsAllBoard);
            case GoalsForYearBoard:
                return context.getResources().getString(R.string.EnumBoard_GoalsForYearBoard);
            case WalletOperBoard:
                return context.getResources().getString(R.string.EnumBoard_WalletOperBoard);
            default:
                return "";
        }
    }

    public MoneyBoard(Long id,
                      EnumBoard enumBoard,
                      Long position,
                      boolean visible,
                      ArrayList<MoneyBoardData> values)
    {
        _id = id;
        _enumBoard = enumBoard;
        _position = position;
        _visible = visible;
        _values = values;
    }

    //region CustomMoneyObject
    @Override
    public String getSValue() { return null; }

    @Override
    public void set_id(Long id)
    {
        _id = id;
    }

    @Override
    public boolean canDelete(Context context) { return false; }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        dbHelper.AddMoneyBoard(this);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyBoard(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyBoard(_id);
    }
    //endregion
}
