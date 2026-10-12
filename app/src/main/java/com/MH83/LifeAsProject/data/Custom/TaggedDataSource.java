package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;

import java.util.ArrayList;

public class TaggedDataSource
{
    public static ArrayList<Tagged> LoadData4PNL(Context context, Long pnlId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadDataFromCursor(dbHelper.QueryTagged(EnumTagTypeData.PNL, pnlId));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static ArrayList<Tagged> LoadDataFromCursor(Cursor cursor)
    {
        ArrayList<Tagged> data = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_TAGGED_ID);
            int externalIndex = cursor.getColumnIndex(DBHelper.KEY_TAGGED_EXTERNALID);
            int tagIndex = cursor.getColumnIndex(DBHelper.KEY_TAGGED_TAGID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_NAME);
            int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_COMMENT);
            int tagTypeDataIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_TYPEDATE);

            do
            {
                data.add(new Tagged(
                        cursor.getLong(idIndex),
                        cursor.getLong(externalIndex),
                        new Tag(cursor.getLong(tagIndex),
                                cursor.getString(nameIndex),
                                cursor.getString(commentIndex),
                                EnumTagTypeData.valueOf(cursor.getString(tagTypeDataIndex)),
                                null,
                                false
                                )));
            }
            while (cursor.moveToNext());
        }

        return data;
    }


    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountTagged();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountTagged();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
