package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;

import com.MH83.LifeAsProject.data.DBHelper;

public class CCabinet extends CustomMoneyObject
{
    private Long _id;
    private final String _name;

    public Long get_id() { return _id; }

    public String get_name() { return _name; }

    @Override
    public String getSValue() { return _name;}

    @Override
    public void set_id(Long id) { _id = id; }

    @Override
    public boolean canDelete(Context context)
    {
        return false;
    }

    public CCabinet(Long id, String name)
    {
        _id = id;
        _name = name;
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddCCabinet(this);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateCCabinet(this);
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteCCabinet(_id);
    }
}
