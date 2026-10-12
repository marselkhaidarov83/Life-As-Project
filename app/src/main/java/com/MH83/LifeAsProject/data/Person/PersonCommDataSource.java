package com.MH83.LifeAsProject.data.Person;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Person.PersonComm;

import java.util.ArrayList;
import java.util.List;

public class PersonCommDataSource
{
    public static List<PersonComm> LoadData(Context context)
    {
        ArrayList<PersonComm> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryPersonComm();

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_COMM_ID);
                int assistIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_COMM_ASSIST_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_COMM_NAME);
                int commIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_COMM_COMM);

                do
                {
                    data.add(new PersonComm(cursor.getLong(idIndex),
                            cursor.getInt(assistIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commIndex)));
                }
                while (cursor.moveToNext());
            }

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountPersonComm();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountPersonComm();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
