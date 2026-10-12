package com.MH83.LifeAsProject.data.Tasks;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;

import java.util.ArrayList;
import java.util.List;

public class TasksProjectDataSource
{
    public static List<TasksProject> LoadDataParent(Context context, boolean addEmptyItem,
                                                    TaskProjectSettings taskProjectSettings)
    {
        ArrayList<TasksProject> data =
                (ArrayList<TasksProject>) LoadDataChildren(context, 0L, taskProjectSettings);

        if (addEmptyItem)
            data.add(0, new TasksProject(0L, "", "",
                    0L, 0L, 0,
                    0,0,0,0, 0,
                    EnumStatus.Blank));

        return data;
    }

    public static List<TasksProject> LoadDataChildren(Context context, boolean withBlank,
                                                      EnumStatus status,
                                                      TaskProjectSettings taskProjectSettings)
    {
        List<TasksProject> list = LoadDataChildren(context, withBlank, taskProjectSettings);
        List<TasksProject> list2 = new ArrayList<>();
        for (TasksProject tasksProject: list)
            if (tasksProject.get_status() == status)
                list2.add(tasksProject);
        return list2;
    }

    public static List<TasksProject> LoadDataChildren(Context context, boolean withBlank,
                                                      TaskProjectSettings taskProjectSettings)
    {
        ArrayList<TasksProject> data = new ArrayList<>();

        if (withBlank)
            data.add(new TasksProject(0L, "", "", 0L, 0L, 0,
                    0,0,0,0, 0,
                    EnumStatus.Blank));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryTasksProject(null, taskProjectSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_PARENT_ID);
                int targetIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_TARGET_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_STATUS);
                int projectCountIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_PROJECT_COUNT);
                int taskCountHighIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_HIGH);
                int taskCountMiddleIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_MIDDLE);
                int taskCountLowIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_LOW);
                int taskCountNoneIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_NONE);

                do
                {
                    String status = cursor.getString(statusIndex);
                    data.add(new TasksProject(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getLong(targetIndex),
                            cursor.getInt(positionIndex),
                            cursor.getInt(projectCountIndex),
                            cursor.getInt(taskCountHighIndex),
                            cursor.getInt(taskCountMiddleIndex),
                            cursor.getInt(taskCountLowIndex),
                            cursor.getInt(taskCountNoneIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status)));
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

    public static List<TasksProject> LoadDataChildren(Context context, Long parentId,
                                                      TaskProjectSettings taskProjectSettings)
    {
        return LoadDataChildren(context, parentId, false, taskProjectSettings);
    }

    public static List<TasksProject> LoadDataChildren(Context context, Long parentId,
                                                      boolean withBlank,
                                                      TaskProjectSettings taskProjectSettings)
    {
        ArrayList<TasksProject> data = new ArrayList<>();

        if (withBlank)
            data.add(new TasksProject(0L, "", "", 0L, 0L, 0,
                    0, 0, 0, 0, 0,
                    EnumStatus.Blank));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryTasksProject(parentId, taskProjectSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_PARENT_ID);
                int targetIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_TARGET_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_STATUS);
                int projectCountIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_PROJECT_COUNT);
                int taskCountHighIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_HIGH);
                int taskCountMiddleIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_MIDDLE);
                int taskCountLowIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_LOW);
                int taskCountNoneIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_NONE);

                do
                {
                    String status = cursor.getString(statusIndex);
                    data.add(new TasksProject(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getLong(targetIndex),
                            cursor.getInt(positionIndex),
                            cursor.getInt(projectCountIndex),
                            cursor.getInt(taskCountHighIndex),
                            cursor.getInt(taskCountMiddleIndex),
                            cursor.getInt(taskCountLowIndex),
                            cursor.getInt(taskCountNoneIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status)));
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

    public static TasksProject LoadOneByTargetId(Context context, Long targetId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryTaskProjectByTargetId(targetId);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_PARENT_ID);
                int targetIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_TARGET_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_STATUS);
                int projectCountIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_PROJECT_COUNT);
                int taskCountHighIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_HIGH);
                int taskCountMiddleIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_MIDDLE);
                int taskCountLowIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_LOW);
                int taskCountNoneIndex = cursor.getColumnIndex(DBHelper.key_TIME_PROJECT_TASK_COUNT_NONE);

                do
                {
                    String status = cursor.getString(statusIndex);
                    return new TasksProject(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getLong(targetIndex),
                            cursor.getInt(positionIndex),
                            cursor.getInt(projectCountIndex),
                            cursor.getInt(taskCountHighIndex),
                            cursor.getInt(taskCountMiddleIndex),
                            cursor.getInt(taskCountLowIndex),
                            cursor.getInt(taskCountNoneIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status));
                }
                while (cursor.moveToNext());
            }

            return null;
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
            return dbHelper.GetCountTasksProject();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountTasksProject();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
