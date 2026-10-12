package com.MH83.LifeAsProject.data.Person;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Person.PersonAssistant;

import java.util.ArrayList;
import java.util.List;

public class PersonAssistantDataSource
{
    public static List<PersonAssistant> LoadData(Context context)
    {
        ArrayList<PersonAssistant> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryPersonAssistant();

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_ASSISTANT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_ASSISTANT_NAME);
                int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_ASSISTANT_SURNAME);
                int middleNameIndex = cursor.getColumnIndex(DBHelper.KEY_PERSON_ASSISTANT_MIDDLENAME);

                do
                {
                    data.add(new PersonAssistant(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(surnameIndex),
                            cursor.getString(middleNameIndex)));
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
            return dbHelper.GetCountPersonAssistant();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountPersonAssistant();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
