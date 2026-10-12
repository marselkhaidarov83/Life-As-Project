package com.MH83.LifeAsProject.data.Tasks;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatSubTasks;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskStatus;
import com.MH83.LifeAsProject.model.Tasks.TasksPriority;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class TasksTaskDataSource
{
    public static ArrayList<TasksTaskIndependent> LoadDataParent(Context context,
                                                                 TasksTaskSettings tasksTaskSettings,
                                                                 boolean withBlank)
    {
        return LoadDataCustom(context,
                tasksTaskSettings.get_customPeriodClass().get_start_dateC(),
                tasksTaskSettings.get_customPeriodClass().get_end_dateC(),
                0L, tasksTaskSettings, withBlank, true);
    }

    public static ArrayList<TasksTaskIndependent> LoadDataParent(Context context,
                                                                 Calendar dateStart, Calendar dateEnd,
                                                                 TasksTaskSettings tasksTaskSettings,
                                                                 boolean withBlank)
    {
        return LoadDataCustom(context, dateStart, dateEnd, 0L, tasksTaskSettings, withBlank, true);
    }

    public static ArrayList<TasksTaskIndependent> LoadDataParentWOChildren(Context context,
                                                                 Calendar dateStart, Calendar dateEnd,
                                                                 TasksTaskSettings tasksTaskSettings,
                                                                 boolean withBlank)
    {
        return LoadDataCustom(context, dateStart, dateEnd, 0L, tasksTaskSettings, withBlank, false);
    }

    public static ArrayList<TasksTaskIndependent> LoadDataChildren(Context context,
                                                      Calendar dateStart, Calendar dateEnd,
                                                      Long parentId,
                                                      TasksTaskSettings tasksTaskSettings)
    {
        return LoadDataCustom(context, dateStart, dateEnd, parentId, tasksTaskSettings, false, true);
    }

    public static ArrayList<TasksTaskIndependent> LoadDataCustom(Context context,
                                                      Calendar dateStart, Calendar dateEnd,
                                                                 Long parentId,
                                                      TasksTaskSettings tasksTaskSettings,
                                                      boolean withBlank,
                                                                 boolean withChildren)
    {
        ArrayList<TasksTaskIndependent> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryTasksTask(dateStart, dateEnd, parentId, tasksTaskSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_ID);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_TYPE);
                int projectIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_PROJECT_ID);
                int projectNameIndex = cursor.getColumnIndex(DBHelper.key_TIME_TASK_PROJECT_NAME);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_PARENT_ID);
                int occuredIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_OCCURED_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_COMMENT);
                int priorityIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_PRIORITY);
                int dayPlanIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_DATE_INT_PLAN);
                int monthPlanIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_MONTH_INT_PLAN);
                int yearPlanIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_YEAR_INT_PLAN);
                int hourPlanIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_HOUR_INT_PLAN);
                int minutePlanIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_MINUTE_INT_PLAN);
                int dayFactIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_DATE_INT_FACT);
                int monthFactIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_MONTH_INT_FACT);
                int yearFactIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_YEAR_INT_FACT);
                int hourFactIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_HOUR_INT_FACT);
                int minuteFactIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_MINUTE_INT_FACT);
                int repeatIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_REPEAT);
                int repeatTypeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_REPEAT_TYPE);
                int repeatPerIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_REPEAT_PER);
                int repeatSubTasksIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_REPEAT_SUBTASKS);
                int confirmedIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_CONFIRMED);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_TASK_STATUS);

                do
                {
                    data.add(new TasksTaskIndependent(cursor.getLong(idIndex),
                            EnumTypeTask.valueOf(cursor.getString(typeIndex)),
                            cursor.getLong(projectIndex),
                            cursor.getString(projectNameIndex),
                            cursor.getLong(parentIndex),
                            cursor.getLong(occuredIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(priorityIndex),
                            cursor.getInt(dayPlanIndex),
                            cursor.getInt(monthPlanIndex),
                            cursor.getInt(yearPlanIndex),
                            cursor.getInt(hourPlanIndex),
                            cursor.getInt(minutePlanIndex),
                            cursor.getInt(dayFactIndex),
                            cursor.getInt(monthFactIndex),
                            cursor.getInt(yearFactIndex),
                            cursor.getInt(hourFactIndex),
                            cursor.getInt(minuteFactIndex),
                            cursor.getInt(repeatIndex) == 1,
                            EnumRepeatType.valueOf(cursor.getString(repeatTypeIndex)),
                            cursor.getInt(repeatPerIndex),
                            cursor.getString(repeatSubTasksIndex) == null ? EnumRepeatSubTasks.All :
                                    EnumRepeatSubTasks.valueOf(cursor.getString(repeatSubTasksIndex)),
                            cursor.getInt(confirmedIndex) == 1,
                            cursor.getInt(confirmedIndex) == 1,
                            cursor.getString(statusIndex).equals("") ? null :
                                    EnumTaskStatus.valueOf(cursor.getString(statusIndex)),
                            false, new ArrayList<>(), null));
                }
                while (cursor.moveToNext());
            }

            if (parentId == null || parentId == 0)
                data = ResortParent(context, data, tasksTaskSettings);
            else
                data = ResortChilds(context, data, tasksTaskSettings);

            if (withBlank)
                data.add(0, new TasksTaskIndependent(0L, EnumTypeTask.Task, 0L, "",
                        0L, 0L, "", "", 0L,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        false, null, 0, EnumRepeatSubTasks.All,
                        false, false, null, false, new ArrayList<>(), null));

            TasksTaskSettings tasksTaskSettings1 = tasksTaskSettings.Clone();
            tasksTaskSettings1.set_confirmedType(null);
            if (withChildren)
                for (TasksTaskIndependent item : data)
                    item.set_childs(LoadDataChildren(context, null, null, item.get_id(),
                            tasksTaskSettings1));

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static ArrayList<TasksTaskIndependent> ResortParent(Context context,
                                                          ArrayList<TasksTaskIndependent> list,
                                                          TasksTaskSettings tasksTaskSettings)
    {
        ArrayList<TasksTaskIndependent> listNew = new ArrayList<>();

        if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Date_Priority_Name))
        {
            for (TasksTaskIndependent tasksTask : list)
                if (tasksTask.get_calendarPlan() != null)
                    listNew.add(tasksTask);

            for (TasksTaskIndependent tasksTask : list)
                if (tasksTask.get_calendarPlan() == null)
                    listNew.add(tasksTask);
        }
        else if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Priority_Date_Name))
        {
            ArrayList<TasksPriority> priors = new ArrayList<>();
            priors.add(new TasksPriority(1L, TasksPriority.getName(context, 1L), "", 0));
            priors.add(new TasksPriority(2L, TasksPriority.getName(context, 2L), "", 0));
            priors.add(new TasksPriority(3L, TasksPriority.getName(context, 3L), "", 0));
            priors.add(new TasksPriority(4L, TasksPriority.getName(context, 4L), "", 0));

            for (TasksPriority tasksPriority : priors)
            {
                for (TasksTaskIndependent tasksTask : list)
                    if (tasksTask.get_calendarPlan() != null &&
                            Objects.equals(tasksTask.get_priority(), tasksPriority.get_id()))
                        listNew.add(tasksTask);

                for (TasksTaskIndependent tasksTask : list)
                    if (tasksTask.get_calendarPlan() == null &&
                            Objects.equals(tasksTask.get_priority(), tasksPriority.get_id()))
                        listNew.add(tasksTask);
            }
        }
        else
            return list;

        return listNew;
    }

    private static ArrayList<TasksTaskIndependent> ResortChilds(Context context,
                                                                ArrayList<TasksTaskIndependent> list,
                                                                TasksTaskSettings tasksTaskSettings)
    {
        ArrayList<TasksTaskIndependent> listNew = new ArrayList<>();

        if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Date_Priority_Name))
        {
            for (TasksTaskIndependent tasksTask : list)
                if (tasksTask.get_calendarPlan() != null && !tasksTask.get_confirmed())
                    listNew.add(tasksTask);

            for (TasksTaskIndependent tasksTask : list)
                if (tasksTask.get_calendarPlan() == null && !tasksTask.get_confirmed())
                    listNew.add(tasksTask);

            for (TasksTaskIndependent tasksTask : list)
                if (tasksTask.get_calendarPlan() != null && tasksTask.get_confirmed())
                    listNew.add(tasksTask);

            for (TasksTaskIndependent tasksTask : list)
                if (tasksTask.get_calendarPlan() == null && tasksTask.get_confirmed())
                    listNew.add(tasksTask);
        }
        else if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Priority_Date_Name))
        {
            ArrayList<TasksPriority> priors = new ArrayList<>();
            priors.add(new TasksPriority(1L, TasksPriority.getName(context, 1L), "", 0));
            priors.add(new TasksPriority(2L, TasksPriority.getName(context, 2L), "", 0));
            priors.add(new TasksPriority(3L, TasksPriority.getName(context, 3L), "", 0));
            priors.add(new TasksPriority(4L, TasksPriority.getName(context, 4L), "", 0));

            for (TasksPriority tasksPriority : priors)
            {
                for (TasksTaskIndependent tasksTask : list)
                    if (tasksTask.get_calendarPlan() != null &&
                            Objects.equals(tasksTask.get_priority(), tasksPriority.get_id()) &&
                            !tasksTask.get_confirmed())
                        listNew.add(tasksTask);

                for (TasksTaskIndependent tasksTask : list)
                    if (tasksTask.get_calendarPlan() == null &&
                            Objects.equals(tasksTask.get_priority(), tasksPriority.get_id()) &&
                            !tasksTask.get_confirmed())
                        listNew.add(tasksTask);

                for (TasksTaskIndependent tasksTask : list)
                    if (tasksTask.get_calendarPlan() != null &&
                            Objects.equals(tasksTask.get_priority(), tasksPriority.get_id()) &&
                            tasksTask.get_confirmed())
                        listNew.add(tasksTask);

                for (TasksTaskIndependent tasksTask : list)
                    if (tasksTask.get_calendarPlan() == null &&
                            Objects.equals(tasksTask.get_priority(), tasksPriority.get_id()) &&
                            tasksTask.get_confirmed())
                        listNew.add(tasksTask);
            }
        }
        else
            return list;

        return listNew;
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountTasksTask();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountTasksTask();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
