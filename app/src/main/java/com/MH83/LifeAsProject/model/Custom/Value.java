package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Custom.ValueDataSource;

public class Value extends CustomMoneyObject
{
    private Long _id;
    private final double _value;
    private final int _param_id;
    private final int _task_id;

    @Override
    public String getSValue() { return String.valueOf(_value); }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    public int get_param_id() { return _param_id; }
    public int get_task_id() { return _task_id; }
    public double get_value() { return _value; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, boolean hasSubscription)
    {
        if (hasSubscription)
            return true;

        if (ValueDataSource.GetCount(context) >= valueFreeCount)
        {
            Toast.makeText(context,
                    context.getString(R.string.ErrorTooManyValue), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public Value(Long id, int param_id, int task_id, double value)
    {
        _id = id;
        _param_id = param_id;
        _task_id = task_id;
        _value = value;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddValue(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddValue(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateValue(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteValue(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountValue(ValueDataSource.GetCount(dbHelper));
    }
}
