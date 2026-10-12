package com.MH83.LifeAsProject.model.Money;

import android.content.Context;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.ISValue;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Hashtable;

public class MoneyIncomingList extends CustomMoneyObject
        implements ISValue, IIsParent
{
    private final MoneyCategory _moneyCategory;
    private Hashtable<Long, MoneyIncoming> _moneyIncoming;
    public MoneyCategory getMoneyCategory() { return _moneyCategory; }
    public Long getMoneyCategoryId() { return _moneyCategory.get_id(); }
    public MoneyIncoming getMoneyIncoming(Long increaseId)
    {
        return _moneyIncoming.get(increaseId);
    }
    public void setMoneyIncoming(Long increaseId, MoneyIncoming moneyIncoming)
    {
        if (moneyIncoming != null)
            _moneyIncoming.put(increaseId, moneyIncoming);
        else
            _moneyIncoming.remove(increaseId);
    }
    public boolean canDelete(Context context)
    {
        return true;
    }

    public MoneyIncomingList(MoneyCategory moneyCategory,
                             Hashtable<Long, MoneyIncoming> moneyIncoming)
    {
        _moneyCategory = moneyCategory;
        _moneyIncoming = moneyIncoming;
        if (_moneyIncoming == null)
            _moneyIncoming = new Hashtable<>();
    }

    @Override
    public String getSValue()
    {
        return _moneyCategory.get_name();
    }

    @Override
    public boolean isParent() { return _moneyCategory.isParent(); }

    @Override
    public void set_id(Long id) { }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception { return false;}
}
