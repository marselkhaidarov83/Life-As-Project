package com.MH83.LifeAsProject.model.Tasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumSortFields;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class TasksTaskSettings extends CustomMoneyObject
{
    //region Parameters
    private CustomPeriodClass _customPeriodClass;
    public CustomPeriodClass get_customPeriodClass() { return _customPeriodClass; }
    public void set_customPeriodClass(CustomPeriodClass customPeriodClass)
    {
        _customPeriodClass = customPeriodClass;
    }

    private Boolean _confirmedType;
    public Boolean get_confirmedType() { return _confirmedType; }
    public void set_confirmedType(Boolean confirmedType) { _confirmedType = confirmedType; }

    private final Long _projectId;
    public Long get_projectId() { return _projectId; }

    private final Long _groupOfProjectId;
    public Long get_groupOfProjectId() { return _groupOfProjectId; }

    private final Long _priority;
    public Long get_priority() { return _priority; }

    private final EnumTaskSorting _taskSorting;
    public EnumTaskSorting get_taskSorting() { return _taskSorting; }

    private ArrayList<EnumSortFields> _sortFields;

    public ArrayList<EnumSortFields> get_sortFields() { return _sortFields; }

    private final boolean _showSpinnerDate;
    public boolean get_showSpinnerDate() { return _showSpinnerDate; }
    //endregion

    //region Constructors
    public TasksTaskSettings(CustomPeriodClass customPeriodClass,
                             Boolean confirmedType,
                             Long groupOfProjectId,
                             Long projectId,
                             Long priority,
                             EnumTaskSorting taskSorting,
                             boolean showSpinnerDate)
    {
        _customPeriodClass = customPeriodClass;
        _confirmedType = confirmedType;
        _groupOfProjectId = groupOfProjectId;
        _projectId = projectId;
        _priority = priority;
        _taskSorting = taskSorting;
        _showSpinnerDate = showSpinnerDate;

        switch (_taskSorting)
        {
            case Date_Priority_Name:
                _sortFields = new ArrayList<>();
                _sortFields.add(EnumSortFields.Date);
                _sortFields.add(EnumSortFields.Priority);
                _sortFields.add(EnumSortFields.Name);
                break;
            case Priority_Date_Name:
                _sortFields = new ArrayList<>();
                _sortFields.add(EnumSortFields.Priority);
                _sortFields.add(EnumSortFields.Date);
                _sortFields.add(EnumSortFields.Name);
                break;
            case Name:
                _sortFields = new ArrayList<>();
                _sortFields.add(EnumSortFields.Name);
                break;
            default:
        }
    }

    public TasksTaskSettings Clone()
    {
        return new TasksTaskSettings(_customPeriodClass, _confirmedType, _groupOfProjectId,
                _projectId, _priority, _taskSorting, _showSpinnerDate);
    }
    //endregion

    //region CustomMoneyObject
    @Override
    public String getSValue() { return ""; }

    @Override
    public void set_id(Long id) {}

    @Override
    public boolean canDelete(Context context) { return false; }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        globalVariable.set_tasksTaskSettingsHT(this.Clone());
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }
    //endregion

    //region SaveToSharedPreferences
    public static SharedPreferences.Editor SaveToSharedPreferences(
            SharedPreferences.Editor editor,
            Hashtable<EnumTaskSettings, TasksTaskSettings> tasksTaskSettingsHT)
    {
        SharedPreferences.Editor editor1 = editor;

        int i = 0;
        if (tasksTaskSettingsHT != null)
            for (EnumTaskSettings enumTaskSettings : tasksTaskSettingsHT.keySet())
            {
                if (!enumTaskSettings.equals(EnumTaskSettings.Custom) &&
                        !enumTaskSettings.equals(EnumTaskSettings.FromProject))
                    continue;

                TasksTaskSettings tasksTaskSettings = tasksTaskSettingsHT.get(enumTaskSettings);
                editor1.putString("TasksTaskSettings." + i + ".EnumTaskSettings", enumTaskSettings.toString());
                editor1 = tasksTaskSettings.get_customPeriodClass().SaveToSharedPreferences(
                        editor1, "TasksTaskSettings." + i + ".");
                editor1.putString("TasksTaskSettings." + i + ".ConfirmedType",
                        tasksTaskSettings.get_confirmedType() == null ? "null" :
                        tasksTaskSettings.get_confirmedType().toString());
                editor1.putString("TasksTaskSettings." + i + ".GroupOfProjectId",
                        tasksTaskSettings.get_groupOfProjectId() == null ? "null" :
                        String.valueOf(tasksTaskSettings.get_groupOfProjectId()));
                editor1.putString("TasksTaskSettings." + i + ".ProjectId",
                        tasksTaskSettings.get_projectId() == null ? "null" :
                        String.valueOf(tasksTaskSettings.get_projectId()));
                editor1.putString("TasksTaskSettings." + i + ".Priority",
                        tasksTaskSettings.get_priority() == null ? "null" :
                        String.valueOf(tasksTaskSettings.get_priority()));
                editor1.putString("TasksTaskSettings." + i + ".TaskSorting",
                        tasksTaskSettings.get_taskSorting().toString());
                editor1.putBoolean("TasksTaskSettings." + i + ".ShowSpinnerDate",
                        tasksTaskSettings.get_showSpinnerDate());

                i++;
            }
        editor1.putInt("TasksTaskSettings.Count", i);

        return editor1;
    }

    public static Hashtable<EnumTaskSettings, TasksTaskSettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<EnumTaskSettings, TasksTaskSettings> ht = new Hashtable<>();

        int count = settings.getInt("TasksTaskSettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            String s1 = settings.getString("TasksTaskSettings." + i + ".ConfirmedType", "null");
            String s2 = settings.getString("TasksTaskSettings." + i + ".GroupOfProjectId", "null");
            String s3= settings.getString("TasksTaskSettings." + i + ".ProjectId", "null");
            String s4 = settings.getString("TasksTaskSettings." + i + ".Priority", "null");

            TasksTaskSettings tasksTaskSettings = new TasksTaskSettings(
                    CustomPeriodClass.GetFromSharedPreferences(settings, "TasksTaskSettings." + i + "."),
                    s1.equals("null") ? null : Boolean.valueOf(s1),
                    s2.equals("null") ? null : Long.valueOf(s2),
                    s3.equals("null") ? null : Long.valueOf(s3),
                    s4.equals("null") ? null : Long.valueOf(s4),
                    EnumTaskSorting.valueOf(
                            settings.getString("TasksTaskSettings." + i + ".TaskSorting",
                            EnumTaskSorting.Date_Priority_Name.toString())),
                    settings.getBoolean("TasksTaskSettings." + i + ".ShowSpinnerDate", true));
            ht.put(EnumTaskSettings.valueOf(
                    settings.getString("TasksTaskSettings." + i + ".EnumTaskSettings",
                            EnumTaskSettings.Custom.toString())),
                    tasksTaskSettings);
        }

        return ht;
    }
    //endregion
}
