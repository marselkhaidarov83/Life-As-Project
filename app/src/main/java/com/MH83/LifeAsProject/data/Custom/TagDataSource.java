package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;

import java.util.ArrayList;
import java.util.List;

public class TagDataSource
{
    public static List<Tag> LoadData(Context context,
                                     TagSettings tagSettings,
                                     boolean addEmpty)
    {
        ArrayList<Tag> data = new ArrayList<>();
        if (addEmpty)
            data.add(new Tag(0L, "", "",
                    tagSettings.get_tagTypeData(), 0L, false));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            GlobalClass gVar = (GlobalClass) context.getApplicationContext();
            Cursor cursor = dbHelper.QueryTag(tagSettings, gVar.get_hasUnpaidSubscription());

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_COMMENT);
                int tagIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_TYPEDATE);
                int archiveIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_ARCHIVE);
                int countExternalIndex = cursor.getColumnIndex(DBHelper.key_TAG_COUNTEXTERNAL);

                do
                {
                    Tag tag = new Tag(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            EnumTagTypeData.valueOf(cursor.getString(tagIndex)),
                            cursor.getLong(countExternalIndex),
                            cursor.getInt(archiveIndex) == 1);
                    data.add(tag);
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
            return dbHelper.GetCountTag();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountTag();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
