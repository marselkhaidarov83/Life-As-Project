package com.MH83.LifeAsProject.model.Money;

import android.content.Context;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Calendar;
import java.util.Date;

public class MoneyTransaction extends CustomMoneyObject
{
    //region Parameters
    protected Long _id;
    protected Long _sumLong;
    protected Long _percentSumLong;
    protected Long _oldSumLong;
    protected Long _oldPercentSumLong;
    protected final Long _costCategoryId;
    protected Long _increaseCategoryId;
    protected String _increaseCategoryName;
    protected String _costCategoryName;
    protected final Long _costCategoryIcon;
    protected final Long _increaseCategoryIcon;
    protected String _subCategoryName;
    protected final EnumTypeCategory _typeCategory;
    protected Calendar _calendar;
    protected MoneyTransactionSum _moneyTransactionSum;
    private boolean _isPlan;
    protected final Long _subCategoryIcon;
    //endregion

    //region Constructors
    public MoneyTransaction(Long id, int day, int month, int year,
                            EnumTypeCategory typeCategory,
                            Long increaseCategoryId, String increaseCategoryName,
                            Long costCategoryId, String costCategoryName,
                            String subCategoryName,
                            Long sumLong, Long oldSumLong,
                            Long percentSumLong, Long oldPercentSumLong,
                            Long increaseCategoryIcon, Long costCategoryIcon, Long subCategoryIcon,
                            boolean isPlan)
    {
        _id = id;

        _calendar = Calendar.getInstance();
        _calendar.set(year, month, day, 0, 0, 0);
        _calendar.set(Calendar.HOUR, 0);
        _calendar.set(Calendar.HOUR_OF_DAY, 0);
        _calendar.set(Calendar.MILLISECOND, 0);

        _typeCategory = typeCategory;
        _increaseCategoryId = increaseCategoryId;
        _increaseCategoryName = increaseCategoryName;
        _costCategoryId = costCategoryId;
        _costCategoryName = costCategoryName;
        _subCategoryName = subCategoryName;
        _sumLong = sumLong;
        _oldSumLong = oldSumLong;
        _percentSumLong = percentSumLong;
        _oldPercentSumLong = oldPercentSumLong;
        _increaseCategoryIcon = increaseCategoryIcon;
        _costCategoryIcon = costCategoryIcon;
        _subCategoryIcon = subCategoryIcon;
        _isPlan = isPlan;
    }

    public MoneyTransaction(Long id, Calendar calendar,
                            EnumTypeCategory typeCategory,
                            Long increaseCategoryId, String increaseCategoryName,
                            Long costCategoryId, String costCategoryName,
                            String subCategoryName,
                            Long sumLong, Long oldSumLong,
                            Long percentSumLong, Long oldPercentSumLong,
                            Long increaseCategoryIcon, Long costCategoryIcon, Long subCategoryIcon,
                            boolean isPlan)
    {
        _id = id;

        _calendar = calendar;

        _typeCategory = typeCategory;
        _increaseCategoryId = increaseCategoryId;
        _increaseCategoryName = increaseCategoryName;
        _costCategoryId = costCategoryId;
        _costCategoryName = costCategoryName;
        _subCategoryName = subCategoryName;
        _sumLong = sumLong;
        _oldSumLong = oldSumLong;
        _percentSumLong = percentSumLong;
        _oldPercentSumLong = oldPercentSumLong;
        _increaseCategoryIcon = increaseCategoryIcon;
        _costCategoryIcon = costCategoryIcon;
        _subCategoryIcon = subCategoryIcon;
        _isPlan = isPlan;
    }

    public MoneyTransaction Clone()
    {
        return new MoneyTransaction(-1L, _calendar,
                _typeCategory,
                _increaseCategoryId, _increaseCategoryName,
                _costCategoryId, _costCategoryName,
                _subCategoryName,
                _sumLong, _oldSumLong,
                _percentSumLong, _oldPercentSumLong,
                _increaseCategoryIcon, _costCategoryIcon, _subCategoryIcon, _isPlan);
    }
    //endregion

    //region Get Params
    public Long get_id() { return _id; }
    @Override
    public String getSValue() { return String.valueOf(_sumLong / 100d); }
    public Calendar getCalendar() { return _calendar; }
    public void setCalendar(Calendar calendar) { _calendar = calendar; }
    public Date getDate() { return _calendar.getTime(); }
    public int getDay() { return _calendar.get(Calendar.DATE); }
    public int getMonth() { return _calendar.get(Calendar.MONTH); }
    public int getYear() { return _calendar.get(Calendar.YEAR); }
    public Long get_sumLong() { return _sumLong; }
    public Long get_oldSumLong() { return _oldSumLong; }
    public Long get_oldPercentSumLong() { return _oldPercentSumLong; }
    public Long get_percentSumLong() { return _percentSumLong; }
    public Long get_totalSumLong() { return _percentSumLong + _sumLong; }
    public Long get_oldTotalSumLong() { return _oldSumLong + _oldPercentSumLong; }
    public Long getIncreaseCategoryId() { return _increaseCategoryId; }
    public Long getCostCategoryId() { return _costCategoryId; }
    public EnumTypeCategory get_typeCategory() { return _typeCategory; }
    public boolean get_isPlan() { return _isPlan; }
    public void set_isPlan(boolean isPlan) { _isPlan = isPlan; }
    public String getCostCategoryName()
    {
        if (_typeCategory == EnumTypeCategory.Increase)
            return _costCategoryName + " / " + _subCategoryName;
        else
            return _costCategoryName;
    }
    public String getIncreaseCategoryName()
    {
        if (_typeCategory == EnumTypeCategory.Cost)
            return _increaseCategoryName + " / " + _subCategoryName;
        else
            return _increaseCategoryName;
    }
    public Long get_increaseCategoryIcon() { return _increaseCategoryIcon; }
    public Long get_costCategoryIcon() { return _costCategoryIcon; }
    public Long get_subCategoryIcon() { return _subCategoryIcon; }
    public MoneyTransactionSum get_moneyTransactionSum() { return _moneyTransactionSum; }
    //endregion

    //region Set Params
    @Override
    public void set_id(Long id)
    {
        _id = id;
    }
    public void set_incCategoryName(String name) { _increaseCategoryName = name; }
    public void set_costCategoryName(String name) { _costCategoryName = name; }
    public void set_moneyTransactionSum(MoneyTransactionSum moneyTransactionSum)
    {
        _moneyTransactionSum = moneyTransactionSum;
    }
    public void set_sumLong(Long sumLong) { _sumLong = sumLong; }
    //endregion

    //region Compare Params
    public boolean CompareDate(MoneyTransaction moneyTransaction)
    {
        return moneyTransaction.getDay() == getDay() &&
                moneyTransaction.getMonth() == getMonth() &&
                moneyTransaction.getYear() == getYear();
    }
    //endregion

    //region Can Questions
    @Override
    public boolean canDelete(Context context) { return true; }
    //endregion

    //region WorkWithDatabase
    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }
    //endregion
}
