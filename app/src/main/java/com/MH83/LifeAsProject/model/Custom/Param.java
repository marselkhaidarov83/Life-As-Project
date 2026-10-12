package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Custom.ParamDataSource;

public class Param extends CustomMoneyObject
{
    private Long _id;
    private final String _name;
    private final String _comment;
    private final String _measure;

    public String get_name() { return _name; }
    public String get_comment() { return _comment; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public String get_measure() { return _measure; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (ParamDataSource.GetCount(context) >= paramFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyParam), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public Param(Long id, String name, String comment, String measure)
    {
        _id = id;
        _name = name;
        _comment = comment;
        _measure = measure;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddParam(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddParam(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateParam(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteParam(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountParam(ParamDataSource.GetCount(dbHelper));
    }
}
