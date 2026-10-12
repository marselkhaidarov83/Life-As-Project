package com.MH83.LifeAsProject.model.Person;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Person.PersonAssistantDataSource;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class PersonAssistant extends CustomMoneyObject
{
    private Long _id;
    private final String _name;
    private final String _surname;
    private final String _middleName;

    public String get_name() { return _name; }
    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public String get_surname() { return _surname; }
    public String get_middleName() { return _middleName; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, boolean hasSubscription)
    {
        if (hasSubscription)
            return true;

        if (PersonAssistantDataSource.GetCount(context) >= personAssistantFreeCount)
        {
            Toast.makeText(context,
                    context.getString(R.string.ErrorTooManyPersonAssistant), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public PersonAssistant(Long id, String name, String surname, String middleName)
    {
        _id = id;
        _name = name;
        _surname = surname;
        _middleName = middleName;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddPersonAssistant(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddPersonAssistant(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdatePersonAssistant(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeletePersonAssistant(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountPersonAssistant(PersonAssistantDataSource.GetCount(dbHelper));
    }
}
