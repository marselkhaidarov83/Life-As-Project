package com.MH83.LifeAsProject.model.Money;

import android.content.Context;

import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.ISValue;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Hashtable;

public class MoneyPlanFact extends CustomMoneyObject
        implements ISValue, IIsParent
{
    private long _id;
    public long get_id() { return _id; }

    private final MoneyCategory _moneyCategory;
    public MoneyCategory getMoneyCategory() { return _moneyCategory; }
    public Long getMoneyCategoryId() { return _moneyCategory.get_id(); }

    private MoneyPlan _moneyPlan;
    public MoneyPlan get_moneyPlan() { return _moneyPlan; }
    public void set_moneyPlan(MoneyPlan moneyPlan)
    {
        _moneyPlan = moneyPlan;
        if (_moneyPlan != null)
            _moneyPlan.set_moneyCategory(_moneyCategory);
    }

    private int _percentFact;
    public void set_percentFact(int percent) { _percentFact = percent; }

    private int _percentDeviation;
    public void set_percentDeviation(int percent) { _percentDeviation = percent; }

    private Long _factValue;
    public Long get_factValue() { return _factValue; }
    public void set_factValue(Long factValue) { _factValue = factValue; }
    public void inc_factValue(Long incValue)
    {
        if (_factValue == null)
            _factValue = incValue;
        else
            _factValue += incValue;
    }

    public Long get_deviation()
    {
        Long deviation = null;
        if (_factValue != null)
            deviation = -_factValue;
        if (_moneyPlan != null && _moneyPlan.get_sumLong() != null)
        {
            if (deviation == null)
                deviation = +_moneyPlan.get_sumLong();
            else
                deviation += _moneyPlan.get_sumLong();
        }

        if (_moneyCategory.getTypeCategory().equals(EnumTypeCategory.Increase) && deviation != null)
            return -deviation;
        return deviation;
    }

    public boolean canDelete(Context context)
    {
        return true;
    }

    public MoneyPlanFact(MoneyCategory moneyCategory,
                         MoneyPlan moneyPlan,
                         Long factValue)
    {
        _id = moneyCategory.get_id();
        _moneyCategory = moneyCategory;
        _moneyPlan = moneyPlan;
        _factValue = factValue;
    }

    @Override
    public String getSValue()
    {
        return _moneyCategory.get_name();
    }

    @Override
    public boolean isParent() { return _moneyCategory.isParent(); }

    @Override
    public void set_id(Long id) { _id = id; }

    public String get_StringFact(boolean withIncreasePercent, boolean withPercent,
                                      boolean shortS)
    {
        return get_StringValue(withIncreasePercent, withPercent, shortS,
                _factValue, _percentFact);
    }

    public String get_StringDeviation(boolean withIncreasePercent, boolean withPercent,
                                boolean shortS)
    {
        return get_StringValue(withIncreasePercent, withPercent, shortS,
                get_deviation(), _percentDeviation);
    }

    public String get_StringValue(boolean withIncreasePercent, boolean withPercent,
                                      boolean shortS, Long value, int percent)
    {
        String sSum= "";
        if (!shortS || (withIncreasePercent == false && withPercent == false))
            sSum = CustomStaticClass.getIntFormatSum((int) (value / 100), false);

        if (withIncreasePercent)
        {
            if (withPercent)
                return sSum + " (" + percent + "%)";
            else
                return sSum;
        }
        else
            return sSum +
                    (withPercent ? " " +
                            (!shortS ? "(" : "") +
                            percent + "%" +
                            (!shortS ? ")" : "")
                            : "");
    }

    //region WorkWithDatabse
    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _moneyPlan.AddToDatabase(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return _moneyPlan.UpdateInDatabase(dbHelper, context);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return _moneyPlan.DeleteFromDatabase(dbHelper, context);
    }
    //endregion
}
