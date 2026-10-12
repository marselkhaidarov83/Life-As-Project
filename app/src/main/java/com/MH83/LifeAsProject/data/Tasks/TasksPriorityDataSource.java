package com.MH83.LifeAsProject.data.Tasks;

import android.content.Context;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Tasks.TasksPriority;

import java.util.ArrayList;
import java.util.List;

public class TasksPriorityDataSource
{
    public static List<TasksPriority> LoadData(Context context)
    {
        ArrayList<TasksPriority> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
//            Cursor cursor = dbHelper.QueryTimePriority();
//
//            if (cursor.moveToFirst())
//            {
//                int idIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PRIORITY_ID);
//                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PRIORITY_NAME);
//                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PRIORITY_COMMENT);
//
//                do
//                {
//                    data.add(new TasksPriority(cursor.getInt(idIndex),
//                            cursor.getString(nameIndex),
//                            cursor.getString(commentIndex),
//                            null));
//                }
//                while (cursor.moveToNext());
//            }

//            if (data.size() == 0)
            {
                data.add(new TasksPriority(0L, TasksPriority.getName(context, 0L),
                        "", R.color.white));
                data.add(new TasksPriority(1L, TasksPriority.getName(context, 1L),
                        "", R.color.lightRed));
                data.add(new TasksPriority(2L, TasksPriority.getName(context, 2L),
                        "", R.color.lightOrange));
                data.add(new TasksPriority(3L, TasksPriority.getName(context, 3L),
                        "", R.color.lightBlue));
                data.add(new TasksPriority(4L, TasksPriority.getName(context, 4L),
                        "", R.color.white));
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
            return dbHelper.GetCountTimePriority();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountTimePriority();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
