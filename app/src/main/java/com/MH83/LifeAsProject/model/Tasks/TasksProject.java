package com.MH83.LifeAsProject.model.Tasks;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class TasksProject extends CustomMoneyObject implements IgetId, IIsParent
{
    private Long _id;
    private Long _parent_id;
    private final Long _target_id;
    private final String _name;
    private final String _comment;
    private int _position;
    private boolean _hasChild = false;
    private int _projectCount;
    private int _taskCountHigh;
    private int _taskCountMiddle;
    private int _taskCountLow;
    private int _taskCountNone;
    private final EnumStatus _status;

    public String get_name() { return _name; }
    public String get_comment() { return _comment; }
    public boolean get_hasChild() { return _hasChild; }
    public void set_hasChild(boolean hasChild) { _hasChild = hasChild; }

    public boolean isTarget() { return _target_id > 0; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public Long get_parentId() { return _parent_id; }
    public void set_parentId(Long parentId) { _parent_id = parentId; }
    public boolean hasParent() { return _parent_id != 0; }
    public boolean isChild() { return _parent_id != 0; }
    public boolean isParent() { return _parent_id == 0; }
    public Long get_target_id() { return _target_id; }
    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }

    public int get_projectCount() { return _projectCount; }
    public void set_projectCount(int projectCount) { _projectCount = projectCount; }
    public int get_taskCountAll() { return _taskCountHigh + _taskCountMiddle +
            _taskCountLow + _taskCountNone; }
    public int get_taskCountHigh() { return _taskCountHigh; }
    public int get_taskCountMiddle() { return _taskCountMiddle; }
    public int get_taskCountLow() { return _taskCountLow; }
    public int get_taskCountNone() { return _taskCountNone; }
    public void set_taskCountHigh(int taskCount) { _taskCountHigh = taskCount; }
    public void set_taskCountMiddle(int taskCount) { _taskCountMiddle = taskCount; }
    public void set_taskCountLow(int taskCount) { _taskCountLow = taskCount; }
    public void set_taskCountNone(int taskCount) { _taskCountNone = taskCount; }
    public void inc_taskCountHigh(int taskCount) { _taskCountHigh += taskCount; }
    public void inc_taskCountMiddle(int taskCount) { _taskCountMiddle += taskCount; }
    public void inc_taskCountLow(int taskCount) { _taskCountLow += taskCount; }
    public void inc_taskCountNone(int taskCount) { _taskCountNone += taskCount; }
    public EnumStatus get_status() { return _status; }

    @Override
    public boolean canDelete(Context context)
    {
        return !_hasChild && _target_id == 0 && get_taskCountAll() == 0;
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (TasksProjectDataSource.GetCount(context) >= timeProjectFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyTimeProject), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public TasksProject(Long id,
                        String name, String comment,
                        Long parent_id,
                        Long target_id, int position,
                        int projectCount, int taskCountHigh, int taskCountMiddle, int taskCountLow,
                        int taskCountNone, EnumStatus status)
    {
        _id = id;
        _name = name;
        _comment = comment;
        _parent_id = parent_id;
        _target_id = target_id;
        _position = position;

        _projectCount = projectCount;
        _taskCountHigh = taskCountHigh;
        _taskCountMiddle = taskCountMiddle;
        _taskCountLow = taskCountLow;
        _taskCountNone = taskCountNone;

        _status = status;
    }

    public TasksProject Clone()
    {
        return new TasksProject(_id, _name, _comment, _parent_id, _target_id,
                _position, _projectCount, _taskCountHigh, _taskCountMiddle, _taskCountLow,
                _taskCountNone, _status);
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddTasksProject(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddTasksProject(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateTasksProject(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteTasksProject(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountTimeProject(TasksProjectDataSource.GetCount(dbHelper));
    }
}
