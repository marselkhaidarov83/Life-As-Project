package com.MH83.LifeAsProject.model.Person;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Person.PersonCommDataSource;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class PersonComm extends CustomMoneyObject
{
    private Long _id;
    private final int _assistId;
    private final String _name;
    private final String _comm;

    public String get_name() { return _name; }
    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public int get_assistId() { return _assistId; }
    public String get_comm() { return _comm; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, boolean hasSubscription)
    {
        if (hasSubscription)
            return true;

        if (PersonCommDataSource.GetCount(context) >= personCommFreeCount)
        {
            Toast.makeText(context,
                    context.getString(R.string.ErrorTooManyPersonComm), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public PersonComm(Long id, int assistId, String name, String comm)
    {
        _id = id;
        _assistId = assistId;
        _name = name;
        _comm = comm;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddPersonComm(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddPersonComm(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdatePersonComm(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeletePersonComm(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountPersonComm(PersonCommDataSource.GetCount(dbHelper));
    }
}
