package com.MH83.LifeAsProject.model.Custom;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.ISValue;

public abstract class CustomMoneyObject2 implements ISValue
{
    public abstract void set_id(int id);
    public abstract void AddToDatabase(DBHelper dbHelper) throws Exception ;
    public abstract boolean UpdateInDatabase(DBHelper dbHelper) throws Exception ;
    public abstract boolean DeleteFromDatabase(DBHelper dbHelper) throws Exception ;
}
