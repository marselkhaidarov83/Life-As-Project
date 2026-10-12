package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CCabinet;

import java.util.ArrayList;
import java.util.List;

public class CCabinetDataSource
{
    public static List<CCabinet> LoadDataCustom(DBHelper dbHelperIn, Context context, Integer cCabinetId)
    {
        ArrayList<CCabinet> data = new ArrayList<>();

        try
        {
            DBHelper dbHelper = dbHelperIn;
            if (dbHelper == null)
                dbHelper = DBHelper.GetDBHelper(context);

            Cursor cursor = dbHelper.QueryCCabinet(cCabinetId);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_CABINET_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_CABINET_NAME);

                do
                {
                    data.add(new CCabinet(cursor.getLong(idIndex), cursor.getString(nameIndex)));
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

    public static List<CCabinet> LoadData(Context context)
    {
        return LoadData(context, false);
    }

    public static List<CCabinet> LoadData(Context context, boolean addEmptyCabinet)
    {
        List<CCabinet> list = LoadDataCustom(null, context, null);

        if (addEmptyCabinet)
            list.add(new CCabinet(9999L, "+"));

        return list;
    }

    public static CCabinet GetFirstCCabinet(DBHelper dbHelper, Context context)
    {
        List<CCabinet> list = LoadDataCustom(dbHelper, context, null);
        if (list.size() >= 1)
            return list.get(0);
        else
            return null;
    }
}
