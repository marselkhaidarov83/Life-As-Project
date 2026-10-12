package com.MH83.LifeAsProject.model.Tasks;

import android.content.Context;

import com.MH83.LifeAsProject.custom.CustomInsertPosition;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksTaskDataSource;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatSubTasks;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumSortFields;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TasksTaskIndependent extends TasksTask implements IgetId
{
    protected TasksTaskIndependent _nextTask;

    @Override
    public TasksTaskIndependent get_nextTask() { return _nextTask; }
    public void set_nextTask(TasksTaskIndependent task) { _nextTask = task; }
    public void clear_nextTask() { _nextTask = null; }
    public void set_confirmed(boolean confirmed) { _confirmed = confirmed; }
    public int getCountChild(TasksTaskSettings tasksTaskSettings)
    {
        if (tasksTaskSettings.get_confirmedType() == null)
            return get_childs().size();

        int cnt = 0;
        for (TasksTaskIndependent item: get_childs())
            cnt += item.get_confirmed() == tasksTaskSettings.get_confirmedType() ? 1 : 0;
        return cnt;
    }
    protected void reNew()
    {
        _oldConfirmed = _confirmed;

        for (TasksTask tasksTask : get_deletedChilds())
            add_deletedChilds4View(tasksTask.getIndependentClone());

        clear_deletedChilds();
    }

    //region Constructor
    public TasksTaskIndependent(Long id, EnumTypeTask type,
                                Long project_id, String project_name,
                                Long parent_id, Long occured_id,
                                String name, String comment,
                                Long priority,
                                int dayPlan, int monthPlan, int yearPlan, int hourPlan, int minutePlan,
                                int dayFact, int monthFact, int yearFact, int hourFact, int minuteFact,
                                boolean repeat, EnumRepeatType repeatType, int repeatPer,
                                EnumRepeatSubTasks nextTasksType,
                                boolean confirmed, boolean oldConfirmed,
                                EnumTaskStatus status, boolean hasChild,
                                ArrayList<TasksTaskIndependent> childs,
                                ArrayList<TasksTaskIndependent> deletedChilds)
    {
        super(id, type, project_id, project_name,
                parent_id, occured_id, name, comment, priority,
                dayPlan, monthPlan, yearPlan, hourPlan, minutePlan,
                dayFact, monthFact, yearFact, hourFact, minuteFact,
                repeat, repeatType, repeatPer, nextTasksType,
                confirmed, oldConfirmed, status, hasChild, childs, deletedChilds);

        if (!get_oldConfirmed() && get_confirmed() && _repeat)
            set_nextTask(compileNextTaskTask());
    }
    //endregion

    //region Clone
    public TasksTaskIndependent Clone()
    {
        ArrayList<TasksTaskIndependent> childsClone = new ArrayList<>();
        for (TasksTaskIndependent item : _childs)
            childsClone.add(item.Clone());

        ArrayList<TasksTaskIndependent> deletedChildsClone = new ArrayList<>();
        for (TasksTaskIndependent item : get_deletedChilds())
            deletedChildsClone.add(item.Clone());

        return new TasksTaskIndependent(_id, _type, _project_id, _project_name,
                _parent_id, _occured_id, _name, _comment, _priority,
                get_day_plan(), get_month_plan(), get_year_plan(), get_hour_plan(), get_minute_plan(),
                get_day_fact(), get_month_fact(), get_year_fact(), get_hour_fact(), get_minute_fact(),
                _repeat, _repeatType, _repeatPer, _repeatSubTasks,
                _confirmed, _oldConfirmed, _status,
                _hasChild, childsClone, deletedChildsClone);
    }

    public TasksTaskIndependent CloneWithInverseConfirmed()
    {
        ArrayList<TasksTaskIndependent> childsClone = new ArrayList<>();
        for (TasksTaskIndependent item : _childs)
            childsClone.add(item.Clone());

        TasksTaskIndependent tasksTaskIndependentClone =
                new TasksTaskIndependent(_id, _type, _project_id, _project_name,
                _parent_id, _occured_id, _name, _comment, _priority,
                get_day_plan(), get_month_plan(), get_year_plan(), get_hour_plan(), get_minute_plan(),
                get_day_fact(), get_month_fact(), get_year_fact(), get_hour_fact(), get_minute_fact(),
                _repeat, _repeatType, _repeatPer, _repeatSubTasks,
                !_confirmed, _oldConfirmed, _status,
                _hasChild, childsClone, null);

        return tasksTaskIndependentClone;
    }

    private TasksTaskIndependent compileNextTaskTask()
    {
        if (get_calendarPlan() == null)
            return null;

        Calendar calendar = (Calendar) get_calendarPlan().clone();
        int repeatType;
        int repeatPer = _repeatPer;
        switch (_repeatType)
        {
            case Year:
                repeatType = Calendar.YEAR;
                calendar.add(repeatType, repeatPer);
                break;
            case YearEnd:
                repeatType = Calendar.YEAR;
                calendar.add(Calendar.DATE, 1);
                calendar.add(repeatType, repeatPer);
                calendar.add(Calendar.DATE, -1);
                break;
            case Month:
                repeatType = Calendar.MONTH;
                calendar.add(repeatType, repeatPer);
                break;
            case MonthEnd:
                repeatType = Calendar.MONTH;
                calendar.add(Calendar.DATE, 1);
                calendar.add(repeatType, repeatPer);
                calendar.add(Calendar.DATE, -1);
                break;
            case Week:
                repeatType = Calendar.DATE;
                repeatPer = 7 * _repeatPer;
                calendar.add(repeatType, repeatPer);
                break;
            case Day:
            default:
                repeatType = Calendar.DATE;
                calendar.add(repeatType, repeatPer);
        }

        ArrayList<TasksTaskIndependent> childsClone = new ArrayList<>();
        for (TasksTaskIndependent item : _childs)
            if (_repeatSubTasks.equals(EnumRepeatSubTasks.All) ||
                    (_repeatSubTasks.equals(EnumRepeatSubTasks.OnlyNotConfirmed) && !item.get_confirmed()))
            {
                TasksTaskIndependent tasksTaskClone = item.Clone();
                tasksTaskClone.set_id(0L);
                childsClone.add(tasksTaskClone);
            }

        return new TasksTaskIndependent(0L, _type, _project_id, _project_name, _parent_id, _id,
                _name, _comment, _priority,
                calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                0, 0, 0, 0, 0,
                _repeat, _repeatType, _repeatPer, _repeatSubTasks,
                false, false, _status, _hasChild, childsClone, new ArrayList<>());
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddTasksTask(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddTasksTask(this);

        ArrayList<TasksTaskIndependent> list = new ArrayList<>();
        for (TasksTask tasksTask : get_childs())
            list.add(tasksTask.getIndependentClone());
        set_childs(list);

        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        boolean b = dbHelper.UpdateTasksTask(this);

        if (b)
        {
            reNew();

            ArrayList<TasksTaskIndependent> list = new ArrayList<>();
            for (TasksTask tasksTask : get_childs())
                list.add(tasksTask.getIndependentClone());
            set_childs(list);
        }

        return b;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteTasksTask(this);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountTimeTask(TasksTaskDataSource.GetCount(dbHelper));
    }
    //endregion

    //region GetPositionInDataset
    public CustomInsertPosition GetPositionInDataset(List<TreeItem<TasksTaskIndependent>> dataSet,
                                                     TasksTaskSettings tasksTaskSettings)
    {
        CustomInsertPosition customInsertPosition = new CustomInsertPosition(
                dataSet.size(), 0, dataSet.size());

        return GetPositionInDataset(dataSet, tasksTaskSettings, customInsertPosition);
    }

    public CustomInsertPosition GetPositionInDataset(List<TreeItem<TasksTaskIndependent>> dataSet,
                                                     TasksTaskSettings tasksTaskSettings,
                                                     CustomInsertPosition customInsertPosition)
    {
        return GetPositionInDataset(dataSet, tasksTaskSettings, customInsertPosition, false);
    }

    public CustomInsertPosition GetPositionInDataset(List<TreeItem<TasksTaskIndependent>> dataSet,
                                                     TasksTaskSettings tasksTaskSettings,
                                                     CustomInsertPosition customInsertPosition,
                                                     boolean withConfirmed)
    {
        if (withConfirmed)
            customInsertPosition = GetPositionByConfirmed(dataSet, customInsertPosition);

        for (EnumSortFields item: tasksTaskSettings.get_sortFields())
        {
            switch (item)
            {
                case Date:
                    customInsertPosition = GetPositionByDate(dataSet, customInsertPosition);
                    break;
                case Priority:
                    customInsertPosition = GetPositionByPriority(dataSet, customInsertPosition);
                    break;
                case Name:
                    customInsertPosition = GetPositionByName(dataSet, customInsertPosition);
                    break;
                default:
            }
        }

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByConfirmed(List<TreeItem<TasksTaskIndependent>> dataSet,
                                                       CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        for (int i = customInsertPosition.get_end() - 1; i >= customInsertPosition.get_start(); i--)
            if (get_confirmedI() > dataSet.get(i).get_object().get_confirmedI() &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < customInsertPosition.get_end(); i++)
            if (get_confirmedI() < dataSet.get(i).get_object().get_confirmedI() &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByDate(
            List<TreeItem<TasksTaskIndependent>> dataSet,
                                                   CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        int start = oldInsertPosition.get_start();
        int end = oldInsertPosition.get_end();
        if (getPlanTime() == 0)
        {
            start = end;
            for (int i = oldInsertPosition.get_start(); i < end; i++)
                if (dataSet.get(i).get_object().getPlanTime() == 0 &&
                        isParent() == dataSet.get(i).get_object().isParent())
                {
                    start = i;
                    break;
                }
        }
        else
        {
            end = start;
            for (int i = oldInsertPosition.get_end() - 1; i >= start; i--)
                if (dataSet.get(i).get_object().getPlanTime() != 0 &&
                        isParent() == dataSet.get(i).get_object().isParent())
                {
                    end = i + 1;
                    break;
                }
        }
        customInsertPosition.set_start(start);
        customInsertPosition.set_end(end);

        for (int i = customInsertPosition.get_end() - 1; i >= start; i--)
            if (getPlanTime() > dataSet.get(i).get_object().getPlanTime() &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < end; i++)
            if (getPlanTime() < dataSet.get(i).get_object().getPlanTime() &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByPriority(List<TreeItem<TasksTaskIndependent>> dataSet,
                                                         CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        for (int i = customInsertPosition.get_end() - 1; i >= customInsertPosition.get_start(); i--)
            if (get_priority() > dataSet.get(i).get_object().get_priority() &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < customInsertPosition.get_end(); i++)
            if (get_priority() < dataSet.get(i).get_object().get_priority() &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByName(List<TreeItem<TasksTaskIndependent>> dataSet,
                                                             CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        for (int i = customInsertPosition.get_end() - 1; i >= customInsertPosition.get_start(); i--)
            if (get_name().compareTo(dataSet.get(i).get_object().get_name()) > 0 &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < customInsertPosition.get_end(); i++)
            if (get_name().compareTo(dataSet.get(i).get_object().get_name()) < 0 &&
                    isParent() == dataSet.get(i).get_object().isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }
    //endregion
}
