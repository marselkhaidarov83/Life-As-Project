package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;

import com.MH83.LifeAsProject.data.DBHelper;

public class CDelObject extends CustomMoneyObject
{
    private Long _id;
    private final String _name;
    private final Long _num;

    public Long get_id() { return _id; }
    public String get_name() { return _name; }
    public Long get_num() { return _num; }

    @Override
    public String getSValue() { return _name;}

    @Override
    public void set_id(Long id) { _id = id; }

    @Override
    public boolean canDelete(Context context)
    {
        return true;
    }

    public CDelObject(Long id, String name, Long num)
    {
        _id = id;
        _name = name;
        _num = num;
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddCDelObject(this);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteCDelObject(_id);
    }
}
