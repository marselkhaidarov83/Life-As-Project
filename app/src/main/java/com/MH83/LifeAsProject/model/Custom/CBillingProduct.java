package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;

import com.MH83.LifeAsProject.data.DBHelper;

public class CBillingProduct extends CustomMoneyObject
{
    private final String _name;
    public String get_name() { return _name; }
    private String _id;
    public String get_id() { return _id; }
    private final String _description;
    public String get_description() { return _description; }
    private final String _price;
    public String get_price() { return _price; }

    private Object _detail;
    public Object get_detail() { return _detail; }

    public CBillingProduct(String id, String name, String description, String price)
    {
        _id = id;
        _name = name;
        _description = description;
        _price = price;
    }

    public CBillingProduct(String id, String name, String description, String price, Object detail)
    {
        _id = id;
        _name = name;
        _description = description;
        _price = price;
        _detail = detail;
    }

    //region CustomMoneyObject
    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = String.valueOf(id); }

    @Override
    public boolean canDelete(Context context) { return false; }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }
    //endregion
}
