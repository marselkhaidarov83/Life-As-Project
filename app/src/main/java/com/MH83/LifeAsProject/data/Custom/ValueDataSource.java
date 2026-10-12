package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.Value;

import java.util.ArrayList;
import java.util.List;

public class ValueDataSource
{
    public static List<Value> LoadData(Context context)
    {
        ArrayList<Value> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryValue();

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_VALUE_ID);
                int paramIndex = cursor.getColumnIndex(DBHelper.KEY_VALUE_PARAM);
                int taskIndex = cursor.getColumnIndex(DBHelper.KEY_VALUE_TASK);
                int valueIndex = cursor.getColumnIndex(DBHelper.KEY_VALUE_VALUE);

                do
                {
                    data.add(new Value(cursor.getLong(idIndex),
                            cursor.getInt(paramIndex),
                            cursor.getInt(taskIndex),
                            cursor.getDouble(valueIndex)));
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
            return dbHelper.GetCountValue();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountValue();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
