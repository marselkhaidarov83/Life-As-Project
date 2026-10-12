package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoneyTargetSettings extends CustomMoneyObject
{
    //region Parameters
    private EnumTargetStatus4Settings _targetStatus4Settings;
    public EnumTargetStatus4Settings get_targetStatus4Settings() { return _targetStatus4Settings; }
    public void set_targetStatus4Settings(EnumTargetStatus4Settings targetStatus4Settings)
    {
        _targetStatus4Settings = targetStatus4Settings;
    }

    public ArrayList<EnumStatus> get_statusList()
    {
        return get_statusList(_targetStatus4Settings);
    }

    public static ArrayList<EnumStatus> get_statusList(EnumTargetStatus4Settings enumTargetStatus4Settings)
    {
        ArrayList<EnumStatus> list = new ArrayList<>();

        switch (enumTargetStatus4Settings)
        {
            case InJob:
                list.add(EnumStatus.Started);
                break;
            case Finished:
                list.add(EnumStatus.Finished);
                break;
            case All:
                break;
            default:
        }

        return list;
    }
    //endregion

    //region Constructors
    public MoneyTargetSettings(EnumTargetStatus4Settings targetStatus4Settings)
    {
        _targetStatus4Settings = targetStatus4Settings;
    }

    public MoneyTargetSettings Clone()
    {
        return new MoneyTargetSettings(_targetStatus4Settings);
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
            Hashtable<Long, MoneyTargetSettings> moneyTargetSettingsHT)
    {
        int i = 0;
        if (moneyTargetSettingsHT != null)
            for (Long dreamId : moneyTargetSettingsHT.keySet())
            {
                MoneyTargetSettings moneyTargetSettings = moneyTargetSettingsHT.get(dreamId);
                editor.putLong("MoneyTargetSettings." + i + ".DreamId", dreamId);
                editor.putString("MoneyTargetSettings." + i + ".TargetStatus4Settings",
                        moneyTargetSettings.get_targetStatus4Settings().toString());

                i++;
            }
        editor.putInt("MoneyTargetSettings.Count", i);

        return editor;
    }

    public static Hashtable<Long, MoneyTargetSettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<Long, MoneyTargetSettings> ht = new Hashtable<>();

        int count = settings.getInt("MoneyTargetSettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            MoneyTargetSettings moneyTargetSettings = new MoneyTargetSettings(
                    EnumTargetStatus4Settings.valueOf(
                    settings.getString("MoneyTargetSettings." + i + ".TargetStatus4Settings",
                            EnumTargetStatus4Settings.All.toString())));

            try
            {
                ht.put(settings.getLong("MoneyTargetSettings." + i + ".DreamId", 0),
                        moneyTargetSettings);
            }
            catch (Exception e)
            {
                ht.put(0L, moneyTargetSettings);
            }
        }

        return ht;
    }
    //endregion
}
