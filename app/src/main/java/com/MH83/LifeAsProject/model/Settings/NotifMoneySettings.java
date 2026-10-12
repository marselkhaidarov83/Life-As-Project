package com.MH83.LifeAsProject.model.Settings;

import android.content.SharedPreferences;

public class NotifMoneySettings
{
    public static int NotifIdActualMonthPlanIncoming = 101;
    public static int NotifIdActualMonthPlanCost = 102;
    public static int NotifIdNoEnteredCostPerDay = 103;
    public static int NotifNoAccumulationThisMonth = 30000;
    public static int NotifAccumulationDone = 40000;

    private boolean _actualMonthPlanIncoming;
    public boolean get_actualMonthPlanIncoming() { return _actualMonthPlanIncoming; }
    public void set_actualMonthPlanIncoming(boolean actualMonthPlan) { _actualMonthPlanIncoming = actualMonthPlan; }

    private boolean _actualMonthPlanCost;
    public boolean get_actualMonthPlanCost() { return _actualMonthPlanCost; }
    public void set_actualMonthPlanCost(boolean actualMonthPlan) { _actualMonthPlanCost = actualMonthPlan; }

    private boolean _noEnteredCostPerDay;
    public boolean get_noEnteredCostPerDay() { return _noEnteredCostPerDay; }
    public void set_noEnteredCostPerDay(boolean noEnteredCostPerDay) { _noEnteredCostPerDay = noEnteredCostPerDay; }

    private boolean _noAccumulationThisMonth;
    public boolean get_noAccumulationThisMonth() { return _noAccumulationThisMonth; }
    public void set_noAccumulationThisMonth(boolean noAccumulationThisMonth) { _noAccumulationThisMonth = noAccumulationThisMonth; }

    public boolean _accumulationDone;
    public boolean get_accumulationDone() { return _accumulationDone; }
    public void set_accumulationDone(boolean accumulationDone) { _accumulationDone = accumulationDone; }

    private boolean _moneyReportPrevMonth;
    public boolean get_moneyReportPrevMonth() { return _moneyReportPrevMonth; }
    public void set_moneyReportPrevMonth(boolean moneyReportPrevMonth) { _moneyReportPrevMonth = moneyReportPrevMonth; }

    public NotifMoneySettings()
    {
        _actualMonthPlanIncoming = true;
        _actualMonthPlanCost = true;
        _noEnteredCostPerDay = true;
        _moneyReportPrevMonth = true;
        _noAccumulationThisMonth = true;
        _accumulationDone = true;
    }

    public SharedPreferences.Editor SaveToSharedPreferences(SharedPreferences.Editor editor)
    {
        editor = SaveToSharedPreferencesActualMonthPlanIncoming(editor);
        editor = SaveToSharedPreferencesActualMonthPlanCost(editor);
        editor = SaveToSharedPreferencesNoEnteredCostPerDay(editor);
        editor = SaveToSharedPreferencesMoneyReportPrevMonth(editor);
        editor = SaveToSharedPreferencesNoAccumulationThisMonth(editor);
        editor = SaveToSharedPreferencesAccumulationDone(editor);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesActualMonthPlanIncoming(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifMoneySettings.ActualMonthPlanIncoming", _actualMonthPlanIncoming);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesActualMonthPlanCost(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifMoneySettings.ActualMonthPlanCost", _actualMonthPlanCost);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNoEnteredCostPerDay(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifMoneySettings.NoEnteredCostPerDay", _noEnteredCostPerDay);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNoAccumulationThisMonth(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifMoneySettings.NoAccumulationThisMonth", _noAccumulationThisMonth);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesAccumulationDone(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifMoneySettings.AccumulationDone", _accumulationDone);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesMoneyReportPrevMonth(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifMoneySettings.MoneyReportPrevMonth", _moneyReportPrevMonth);

        return editor;
    }

    public static NotifMoneySettings GetFromSharedPreferences(SharedPreferences settings)
    {
        NotifMoneySettings notifMoneySettings = new NotifMoneySettings();

        notifMoneySettings.set_actualMonthPlanIncoming(settings.getBoolean("MainSettings.NotifMoneySettings.ActualMonthPlanIncoming", true));
        notifMoneySettings.set_actualMonthPlanCost(settings.getBoolean("MainSettings.NotifMoneySettings.ActualMonthPlanCost", true));
        notifMoneySettings.set_noEnteredCostPerDay(settings.getBoolean("MainSettings.NotifMoneySettings.NoEnteredCostPerDay", true));
        notifMoneySettings.set_moneyReportPrevMonth(settings.getBoolean("MainSettings.NotifMoneySettings.MoneyReportPrevMonth", true));
        notifMoneySettings.set_noAccumulationThisMonth(settings.getBoolean("MainSettings.NotifMoneySettings.NoAccumulationThisMonth", true));
        notifMoneySettings.set_accumulationDone(settings.getBoolean("MainSettings.NotifMoneySettings.AccumulationDone", true));

        return notifMoneySettings;
    }
}
