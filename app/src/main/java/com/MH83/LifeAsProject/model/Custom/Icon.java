package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import com.MH83.LifeAsProject.data.DBHelper;

public class Icon extends CustomMoneyObject
{
    private Long _id;
    private final int _icon_id;

    @Override
    public String getSValue() { return String.valueOf(_id); }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    public int get_icon_id() { return _icon_id; }

    @Override
    public boolean canDelete(Context context) { return false; }

    public Icon(Long id, int icon_id)
    {
        _id = id;
        _icon_id = icon_id;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception { }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    private void TrackCount(DBHelper dbHelper, Context context) { }
}
