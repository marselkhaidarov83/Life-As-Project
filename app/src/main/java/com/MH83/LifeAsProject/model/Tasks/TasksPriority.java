package com.MH83.LifeAsProject.model.Tasks;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksPriorityDataSource;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class TasksPriority extends CustomMoneyObject
{
    private Long _id;
    private final String _name;
    private final String _comment;
    private final Integer _color;

    public String get_name() { return _name; }
    public String get_comment() { return _comment; }
    public Integer get_color() { return _color; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, boolean hasSubscription)
    {
        if (hasSubscription)
            return true;

        if (TasksPriorityDataSource.GetCount(context) >= timePriorityFreeCount)
        {
            Toast.makeText(context,
                    context.getString(R.string.ErrorTooManyTimeProject), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public TasksPriority(Long id, String name, String comment, Integer color)
    {
        _id = id;
        _name = name;
        _comment = comment;
        _color = color;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddTimePriority(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddTimePriority(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateTimePriority(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteTimePriority(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountTimePriority(TasksPriorityDataSource.GetCount(dbHelper));
    }

    public static String getName(Context context, Long prior)
    {
        switch (prior.intValue())
        {
            case 1: return context.getResources().getString(R.string.Priority_High);
            case 2: return context.getResources().getString(R.string.Priority_Middle);
            case 3: return context.getResources().getString(R.string.Priority_Low);
            case 4: return context.getResources().getString(R.string.Priority_None);
            default: return context.getResources().getString(R.string.Priority_All);
        }
    }
}
