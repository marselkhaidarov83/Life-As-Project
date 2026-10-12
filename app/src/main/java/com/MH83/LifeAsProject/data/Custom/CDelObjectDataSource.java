package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CDelObject;

import java.util.ArrayList;
import java.util.List;

public class CDelObjectDataSource
{
    public static List<CDelObject> LoadDataCustom(Context context)
    {
        ArrayList<CDelObject> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryCDelObject();

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_DEL_OBJECT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_DEL_OBJECT_NAME);
                int numIndex = cursor.getColumnIndex(DBHelper.KEY_DEL_OBJECT_NUM);

                do
                {
                    data.add(new CDelObject(cursor.getLong(idIndex), cursor.getString(nameIndex),
                            cursor.getLong(numIndex)));
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

    public static List<CDelObject> LoadData(Context context)
    {
        return LoadDataCustom(context);
    }
}
