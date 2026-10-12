package com.MH83.LifeAsProject.model.Tasks;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomInsertPosition;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksTaskDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatSubTasks;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumSortFields;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class TasksTask extends CustomMoneyObject implements IgetId, IIsParent
{
    protected Long _id;
    protected final EnumTypeTask _type;
    protected Long _project_id;
    protected String _project_name;
    protected Long _parent_id;
    protected Long _occured_id;
    protected final String _name;
    protected final String _comment;
    protected final Long _priority;
    protected final Calendar _planCalendar;
    protected final Calendar _factCalendar;
    protected final boolean _repeat;
    protected final EnumRepeatType _repeatType;
    protected final EnumRepeatSubTasks _repeatSubTasks;
    protected final int _repeatPer;
    protected boolean _confirmed;
    protected boolean _oldConfirmed;
    protected final EnumTaskStatus _status;
    protected TasksTask _nextTask;
    protected boolean _hasChild;
    protected ArrayList<TasksTaskIndependent> _childs;
    private ArrayList<TasksTaskIndependent> _deletedChilds;
    private ArrayList<TasksTaskIndependent> _deletedChilds4View = new ArrayList<>();

    public ArrayList<TasksTaskIndependent> get_childs() { return _childs; }
    public void set_childs(ArrayList<TasksTaskIndependent> childs) { _childs = childs; }
    public void update_child(TasksTaskIndependent tasksTaskIndependentNew)
    {
        for (int i = 0; i < _childs.size(); i++)
            if (Objects.equals(_childs.get(i).get_id(), tasksTaskIndependentNew.get_id()))
            {
                _childs.set(i, tasksTaskIndependentNew);
                break;
            }
    }
    public void delete_child(Long id)
    {
        for (TasksTaskIndependent tasksTaskIndependent : _childs)
            if (Objects.equals(tasksTaskIndependent.get_id(), id))
            {
                _childs.remove(tasksTaskIndependent);
                break;
            }
    }

    public ArrayList<TasksTaskIndependent> get_deletedChilds() { return _deletedChilds; }
    public void add_deletedChilds(TasksTaskIndependent tasksTaskIndependent)
    {
        if (tasksTaskIndependent.get_id() != 0)
            _deletedChilds.add(tasksTaskIndependent);
    }
    public void clear_deletedChilds() { _deletedChilds.clear(); }

    public ArrayList<TasksTaskIndependent> get_deletedChilds4View() { return _deletedChilds4View; }
    public void add_deletedChilds4View(TasksTaskIndependent tasksTaskIndependent)
    {
        if (tasksTaskIndependent.get_id() != 0)
            _deletedChilds4View.add(tasksTaskIndependent);
    }
    public void clear_deletedChilds4View() { _deletedChilds4View.clear(); }

    public String get_name() { return _name; }
    public String get_comment() { return _comment; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public boolean isParent() { return _parent_id == 0L; }
    public EnumTypeTask get_type() { return _type; }
    public Long get_project_id() { return _project_id; }
    public String get_projectName() { return _project_name; }
    public Long get_priority() { return _priority; }
    public Long get_parentId() { return _parent_id; }
    public void set_parent_id(Long parentId) { _parent_id = parentId; }
    public Long get_occured_id() { return _occured_id; }
    public void set_occured_id(Long occuredId) { _occured_id = occuredId; }
    public long getPlanTime()
    {
        return _planCalendar == null ? 0 : _planCalendar.getTime().getTime();
    }
    public Calendar get_calendarPlan() { return _planCalendar; }
    public int get_day_plan()
    {
        return _planCalendar == null ? 0 : _planCalendar.get(Calendar.DATE);
    }
    public int get_month_plan()
    {
        return _planCalendar == null ? 0 : _planCalendar.get(Calendar.MONTH);
    }
    public int get_year_plan()
    {
        return _planCalendar == null ? 0 : _planCalendar.get(Calendar.YEAR);
    }
    public int get_hour_plan()
    {
        return _planCalendar == null ? 0 : _planCalendar.get(Calendar.HOUR_OF_DAY);
    }
    public int get_minute_plan()
    {
        return _planCalendar == null ? 0 : _planCalendar.get(Calendar.MINUTE);
    }
    public int get_day_fact() { return _factCalendar.get(Calendar.DATE); }
    public int get_month_fact() { return _factCalendar.get(Calendar.MONTH); }
    public int get_year_fact() { return _factCalendar.get(Calendar.YEAR); }
    public int get_hour_fact() { return _factCalendar.get(Calendar.HOUR_OF_DAY); }
    public int get_minute_fact() { return _factCalendar.get(Calendar.MINUTE); }
    public boolean get_repeat() { return _repeat; }
    public int get_repeatI() { return _repeat? 1 : 0; }
    public EnumRepeatType get_repeatType() { return _repeatType; }
    public int get_repeatPer() { return _repeatPer; }
    public EnumRepeatSubTasks get_repeatSubTasks() { return _repeatSubTasks;}

    public boolean get_confirmed() { return _confirmed; }
    public boolean get_oldConfirmed() { return _oldConfirmed;}
    public int get_confirmedI() { return _confirmed? 1 : 0; }
    public EnumTaskStatus get_status() { return _status; }
    public TasksTask get_nextTask() { return _nextTask; }
    public void clear_nextTask() { _nextTask = null; }
    public void set_hasChild(boolean hasChild) { _hasChild = hasChild; }
    public boolean get_hasChild() { return _hasChild; }
    public boolean hasParent() { return _parent_id != 0; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public void set_project(Long projectId, String projectName)
    {
        _project_id = projectId;
        _project_name = projectName;
    }

    //region Compare Params
    public boolean CompareDate(TasksTask tasksTask)
    {
        return tasksTask.get_day_plan() == get_day_plan() &&
                tasksTask.get_month_plan() == get_month_plan() &&
                tasksTask.get_year_plan() == get_year_plan();
    }
    //endregion

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (TasksTaskDataSource.GetCount(context) >= timeTaskFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyTimeTask), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public TasksTask(Long id, EnumTypeTask type,
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
        _id = id;
        _type = type;
        _project_id = project_id;
        _project_name = project_name;
        _parent_id = parent_id;
        _occured_id = occured_id;
        _name = name;
        _comment = comment;
        _priority = priority;

        if (yearPlan == 0 && monthPlan == 0 && dayPlan == 0 && hourPlan == 0 && minutePlan == 0)
            _planCalendar = null;
        else
        {
            _planCalendar = Calendar.getInstance();
            _planCalendar.set(yearPlan, monthPlan, dayPlan, hourPlan, minutePlan, 0);
            _planCalendar.set(Calendar.MILLISECOND, 0);
        }

        _factCalendar = Calendar.getInstance();
        _factCalendar.set(yearFact, monthFact, dayFact, hourFact, minuteFact, 0);
        _factCalendar.set(Calendar.MILLISECOND, 0);

        _repeat = repeat;
        _repeatType = repeatType;
        _repeatPer = repeatPer;
        _repeatSubTasks = nextTasksType;

        _confirmed = confirmed;
        _oldConfirmed = oldConfirmed;
        _status = status;
        _hasChild = hasChild;
        _childs = childs;

        _deletedChilds = deletedChilds;
        if (_deletedChilds == null)
            _deletedChilds = new ArrayList<>();
    }

    public TasksTask Clone()
    {
        return new TasksTask(_id, _type, _project_id, _project_name,
                _parent_id, _occured_id, _name, _comment, _priority,
                get_day_plan(), get_month_plan(), get_year_plan(), get_hour_plan(), get_minute_plan(),
                get_day_fact(), get_month_fact(), get_year_fact(), get_hour_fact(), get_minute_fact(),
                _repeat, _repeatType, _repeatPer, _repeatSubTasks,
                _confirmed, _oldConfirmed, _status,
                _hasChild, new ArrayList<>(), new ArrayList<>());
    }

    public TasksTask CloneWithInverseConfirmed()
    {
        return new TasksTask(_id, _type, _project_id, _project_name,
                _parent_id, _occured_id, _name, _comment, _priority,
                get_day_plan(), get_month_plan(), get_year_plan(), get_hour_plan(), get_minute_plan(),
                get_day_fact(), get_month_fact(), get_year_fact(), get_hour_fact(), get_minute_fact(),
                _repeat, _repeatType, _repeatPer, _repeatSubTasks,
                !_confirmed, _oldConfirmed, _status,
                _hasChild, new ArrayList<>(), new ArrayList<>());
    }

    public TasksTaskIndependent getIndependentClone()
    {
        return new TasksTaskIndependent(_id, _type, _project_id, _project_name,
                _parent_id, _occured_id, _name, _comment, _priority,
                get_day_plan(), get_month_plan(), get_year_plan(), get_hour_plan(), get_minute_plan(),
                get_day_fact(), get_month_fact(), get_year_fact(), get_hour_fact(), get_minute_fact(),
                _repeat, _repeatType, _repeatPer, _repeatSubTasks,
                _confirmed, _oldConfirmed, _status, false, new ArrayList<>(), new ArrayList<>());
    }

    public Calendar CalculateNextTaskDate(EnumRepeatType repeatType, int repeatPer)
    {
        if (get_calendarPlan() == null)
            return null;

        Calendar calendar = (Calendar) get_calendarPlan().clone();
        int rrepeatType;
        int rrepeatPer = repeatPer;
        switch (repeatType)
        {
            case Year:
                rrepeatType = Calendar.YEAR;
                calendar.add(rrepeatType, rrepeatPer);
                break;
            case YearEnd:
                rrepeatType = Calendar.YEAR;
                calendar.add(Calendar.DATE, 1);
                calendar.add(rrepeatType, rrepeatPer);
                calendar.add(Calendar.DATE, -1);
                break;
            case Month:
                rrepeatType = Calendar.MONTH;
                calendar.add(rrepeatType, rrepeatPer);
                break;
            case MonthEnd:
                rrepeatType = Calendar.MONTH;
                calendar.add(Calendar.DATE, 1);
                calendar.add(rrepeatType, rrepeatPer);
                calendar.add(Calendar.DATE, -1);
                break;
            case Week:
                rrepeatType = Calendar.DATE;
                rrepeatPer = 7 * repeatPer;
                calendar.add(rrepeatType, rrepeatPer);
                break;
            case Day:
            default:
                rrepeatType = Calendar.DATE;
                calendar.add(rrepeatType, rrepeatPer);
        }

        return calendar;
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return true;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.DeleteTimeTask(_id);
        return true;
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
//        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
//        CustomTrack.TrackCountTimeTask(globalVariable, TasksTaskDataSource.GetCount(dbHelper));
    }

    //region GetPositionInDataset
    public CustomInsertPosition GetPositionInDataset(ArrayList<TasksTask> dataSet,
                                                     TasksTaskSettings tasksTaskSettings)
    {
        CustomInsertPosition customInsertPosition = new CustomInsertPosition(
                dataSet.size(), 0, dataSet.size());

        return GetPositionInDataset(dataSet, tasksTaskSettings, customInsertPosition);
    }

    public CustomInsertPosition GetPositionInDataset(ArrayList<TasksTask> dataSet,
                                                     TasksTaskSettings tasksTaskSettings,
                                                     CustomInsertPosition customInsertPosition)
    {
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

    public CustomInsertPosition GetPositionByConfirmed(ArrayList<TasksTask> dataSet,
                                                      CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        for (int i = customInsertPosition.get_end() - 1; i >= customInsertPosition.get_start(); i--)
            if (get_confirmedI() > dataSet.get(i).get_confirmedI() &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < customInsertPosition.get_end(); i++)
            if (get_confirmedI() < dataSet.get(i).get_confirmedI() &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByDate(
            ArrayList<TasksTask> dataSet,
            CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        int start = oldInsertPosition.get_start();
        int end = oldInsertPosition.get_end();
        if (getPlanTime() == 0)
        {
            start = end;
            for (int i = oldInsertPosition.get_start(); i < end; i++)
                if (dataSet.get(i).getPlanTime() == 0 &&
                        isParent() == dataSet.get(i).isParent())
                {
                    start = i;
                    break;
                }
        }
        else
        {
            end = start;
            for (int i = oldInsertPosition.get_end() - 1; i >= start; i--)
                if (dataSet.get(i).getPlanTime() != 0 &&
                        isParent() == dataSet.get(i).isParent())
                {
                    end = i + 1;
                    break;
                }
        }
        customInsertPosition.set_start(start);
        customInsertPosition.set_end(end);

        for (int i = customInsertPosition.get_end() - 1; i >= start; i--)
            if (getPlanTime() > dataSet.get(i).getPlanTime() &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < end; i++)
            if (getPlanTime() < dataSet.get(i).getPlanTime() &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByPriority(ArrayList<TasksTask> dataSet,
                                                      CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        for (int i = customInsertPosition.get_end() - 1; i >= customInsertPosition.get_start(); i--)
            if (get_priority() > dataSet.get(i).get_priority() &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < customInsertPosition.get_end(); i++)
            if (get_priority() < dataSet.get(i).get_priority() &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }

    public CustomInsertPosition GetPositionByName(ArrayList<TasksTask> dataSet,
                                                  CustomInsertPosition oldInsertPosition)
    {
        CustomInsertPosition customInsertPosition = oldInsertPosition.Clone();

        for (int i = customInsertPosition.get_end() - 1; i >= customInsertPosition.get_start(); i--)
            if (get_name().compareTo(dataSet.get(i).get_name()) > 0 &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_start(i + 1);
                break;
            }

        for (int i = customInsertPosition.get_start(); i < customInsertPosition.get_end(); i++)
            if (get_name().compareTo(dataSet.get(i).get_name()) < 0 &&
                    isParent() == dataSet.get(i).isParent())
            {
                customInsertPosition.set_end(i);
                break;
            }

        customInsertPosition.set_insertPosition(customInsertPosition.get_end());

        return customInsertPosition;
    }
    //endregion
}
