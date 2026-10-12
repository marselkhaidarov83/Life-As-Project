package com.MH83.LifeAsProject.model.Money;

import android.content.Context;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyCreditPivotPoint extends CustomMoneyObject
{
    private Long _id;
    private Long _creditId;
    private final Long _startSumLong;
    private final double _percent;
    private final int _loanTerm;

    public Long get_id() { return _id; }
    public Long get_creditId() { return _creditId; }
    public Long get_startSumLong() { return _startSumLong; }
    public double get_percent() { return _percent; }
    public int get_loanTerm() { return _loanTerm; }

    public void set_creditId(Long creditId) { _creditId = creditId; }

    public MoneyCreditPivotPoint(Long id,
                                 Long creditId,
                                 Long startSumLong,
                                 double percent,
                                 int loanTerm)
    {
        _id = id;
        _creditId = creditId;
        _startSumLong = startSumLong;
        _percent = percent;
        _loanTerm = loanTerm;
    }

    //region CustomMoneyObject
    @Override
    public String getSValue() { return ""; }

    @Override
    public void set_id(Long id) { _id = id; }

    @Override
    public boolean canDelete(Context context) { return true; }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception { return true; }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception { return true; }
    //endregion
}
