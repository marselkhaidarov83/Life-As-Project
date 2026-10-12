package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.Param;

import java.util.ArrayList;
import java.util.List;

public class ParamDataSource
{
    public static List<Param> LoadData(Context context)
    {
        ArrayList<Param> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryParam();

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_PARAM_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_PARAM_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_PARAM_COMMENT);
                int measureIndex = cursor.getColumnIndex(DBHelper.KEY_PARAM_MEASURE);

                do
                {
                    data.add(new Param(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getString(measureIndex)));
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
            return dbHelper.GetCountParam();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountParam();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
