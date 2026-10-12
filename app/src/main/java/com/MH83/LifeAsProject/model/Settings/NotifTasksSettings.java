package com.MH83.LifeAsProject.model.Settings;

import android.content.SharedPreferences;

public class NotifTasksSettings
{
    public static int NotifIdOverdueTasks = 201;
    public static int NotifIdCountTasksForDay = 202;
    public static int NotifIdNoTasksForDay = 203;
    public static int NotifIdCountUnfinishedTasksInEvening = 204;
    public static int NotifIdCountTasksInbox = 205;

    private boolean _overdueTasksInMorning;
    public boolean get_overdueTasksInMorning() { return _overdueTasksInMorning; }
    public void set_overdueTasksInMorning(boolean overdueTasksInMorning) { _overdueTasksInMorning = overdueTasksInMorning; }

    private boolean _countTasksForDay;
    public boolean get_countTasksForDay() { return _countTasksForDay; }
    public void set_countTasksForDay(boolean countTasksForDay) { _countTasksForDay = countTasksForDay; }

    private boolean _noTasksForDay;
    public boolean get_noTasksForDay() { return _noTasksForDay; }
    public void set_noTasksForDay(boolean noTasksForDay) { _noTasksForDay = noTasksForDay; }

    private boolean _countUnfinishedTasksInEvening;
    public boolean get_countUnfinishedTasksInEvening() { return _countUnfinishedTasksInEvening; }
    public void set_countUnfinishedTasksInEvening(boolean countUnfinishedTasksInEvening) { _countUnfinishedTasksInEvening = countUnfinishedTasksInEvening; }

    private boolean _countTasksInbox;
    public boolean get_countTasksInbox() { return _countTasksInbox; }
    public void set_countTasksInbox(boolean countTasksInbox) { _countTasksInbox = countTasksInbox; }

    public NotifTasksSettings()
    {
        _overdueTasksInMorning = true;
        _countTasksForDay = true;
        _noTasksForDay = true;
        _countUnfinishedTasksInEvening = true;
        _countTasksInbox = true;
    }

    public SharedPreferences.Editor SaveToSharedPreferences(SharedPreferences.Editor editor)
    {
        editor = SaveToSharedPreferencesOverdueTasksInMorning(editor);
        editor = SaveToSharedPreferencesCountTasksForDay(editor);
        editor = SaveToSharedPreferencesNoTasksForDay(editor);
        editor = SaveToSharedPreferencesCountUnfinishedTasksInEvening(editor);
        editor = SaveToSharedPreferencesCountTasksInbox(editor);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesOverdueTasksInMorning(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifTasksSettings.OverdueTasksInMorning", _overdueTasksInMorning);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesCountTasksForDay(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifTasksSettings.CountTasksForDay", _countTasksForDay);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNoTasksForDay(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifTasksSettings.NoTasksForDay", _noTasksForDay);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesCountUnfinishedTasksInEvening(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifTasksSettings.CountUnfinishedTasksInEvening", _countUnfinishedTasksInEvening);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesCountTasksInbox(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifTasksSettings.CountTasksInbox", _countTasksInbox);

        return editor;
    }

    public static NotifTasksSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        NotifTasksSettings notifTasksSettings = new NotifTasksSettings();

        notifTasksSettings.set_overdueTasksInMorning(settings.getBoolean("MainSettings.NotifTasksSettings.OverdueTasksInMorning", true));
        notifTasksSettings.set_countTasksForDay(settings.getBoolean("MainSettings.NotifTasksSettings.CountTasksForDay", true));
        notifTasksSettings.set_noTasksForDay(settings.getBoolean("MainSettings.NotifTasksSettings.NoTasksForDay", true));
        notifTasksSettings.set_countUnfinishedTasksInEvening(settings.getBoolean("MainSettings.NotifTasksSettings.CountUnfinishedTasksInEvening", true));
        notifTasksSettings.set_countTasksInbox(settings.getBoolean("MainSettings.NotifTasksSettings.CountTasksInbox", true));

        return notifTasksSettings;
    }
}
