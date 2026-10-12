package com.MH83.LifeAsProject.model.Tasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.model.Money.MoneyTargetSettings;

import java.util.ArrayList;

public class TaskProjectSettings extends CustomMoneyObject
{
    //region Parameters
    private ArrayList<Long> _expandedList;
    public ArrayList<Long> get_expandedList() { return _expandedList; }
    public void set_expandedList(ArrayList<Long> list) { _expandedList = list; }

    private EnumTargetStatus4Settings _enumTargetStatus4Settings;
    public EnumTargetStatus4Settings get_enumTargetStatus4Settings()
    {
        return _enumTargetStatus4Settings;
    }
    public void set_enumTargetStatus4Settings(EnumTargetStatus4Settings enumTargetStatus4Settings)
    {
        _enumTargetStatus4Settings = enumTargetStatus4Settings;
    }
    //endregion

    public ArrayList<EnumStatus> get_statusList()
    {
        return MoneyTargetSettings.get_statusList(_enumTargetStatus4Settings);
    }

    //region Constructors
    public TaskProjectSettings(EnumTargetStatus4Settings enumTargetStatus4Settings,
                               ArrayList<Long> expandedList)
    {
        _enumTargetStatus4Settings = enumTargetStatus4Settings;
        _expandedList = expandedList;
    }

    public TaskProjectSettings Clone()
    {
        return new TaskProjectSettings(_enumTargetStatus4Settings, _expandedList);
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
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

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
            TaskProjectSettings taskProjectSettings)
    {
        editor.putString("TaskProjectSettings.TargetStatus4Settings",
                taskProjectSettings.get_enumTargetStatus4Settings().toString());

        return editor;
    }

    public static TaskProjectSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        return new TaskProjectSettings(
                EnumTargetStatus4Settings.valueOf(
                        settings.getString("TaskProjectSettings.TargetStatus4Settings",
                                EnumTargetStatus4Settings.All.toString())), null);
    }
    //endregion
}
