package com.MH83.LifeAsProject.model.Settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;

import java.util.Hashtable;

public class NotifSettings
{
    boolean _notifEnabled;
    public boolean get_notifEnabled() { return _notifEnabled; }
    public void set_notifEnabled(boolean notifEnabled) { _notifEnabled = notifEnabled; }

    int _notifStartHour = 9;
    public void set_notifStartHour(int notifStartHour) {
        _notifStartHour = notifStartHour;
    }
    public int get_notifStartHour() {
        return _notifStartHour;
    }

    int _notifStartMinute = 0;
    public void set_notifStartMinute(int notifStartMinute) {
        _notifStartMinute = notifStartMinute;
    }
    public int get_notifStartMinute() {
        return _notifStartMinute;
    }

    int _notifEndHour = 22;
    public void set_notifEndHour(int notifEndHour) {
        _notifEndHour = notifEndHour;
    }
    public int get_notifEndHour() { return _notifEndHour; }

    int _notifEndMinute = 0;
    public void set_notifEndMinute(int notifEndMinute) {
        _notifEndMinute = notifEndMinute;
    }
    public int get_notifEndMinute() {
        return _notifEndMinute;
    }

    public NotifSettings()
    {
        _notifEnabled = true;
        _notifStartHour = 9;
        _notifStartMinute = 0;
        _notifEndHour = 22;
        _notifEndMinute = 0;
    }

    public NotifSettings(int notifStartHour, int notifStartMinute,
                         int notifEndHour, int notifEndMinute) {
        _notifStartHour = notifStartHour;
        _notifStartMinute = notifStartMinute;
        _notifEndHour = notifEndHour;
        _notifEndMinute = notifEndMinute;
    }

    public SharedPreferences.Editor SaveToSharedPreferences(SharedPreferences.Editor editor)
    {
        editor = SaveToSharedPreferencesNotifEnabled(editor);
        editor = SaveToSharedPreferencesNotifStart(editor);
        editor = SaveToSharedPreferencesNotifEnd(editor);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNotifEnabled(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifSettings.NotifEnabled", _notifEnabled);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNotifStart(SharedPreferences.Editor editor)
    {
        editor.putInt("MainSettings.NotifSettings.NotifStartHour", _notifStartHour);
        editor.putInt("MainSettings.NotifSettings.NotifStartMinute", _notifStartMinute);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNotifEnd(SharedPreferences.Editor editor)
    {
        editor.putInt("MainSettings.NotifSettings.NotifEndHour", _notifEndHour);
        editor.putInt("MainSettings.NotifSettings.NotifEndMinute", _notifEndMinute);

        return editor;
    }

    public static NotifSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        NotifSettings notifSettings = new NotifSettings();

        notifSettings.set_notifEnabled(settings.getBoolean("MainSettings.NotifSettings.NotifEnabled", true));
        notifSettings.set_notifStartHour(settings.getInt("MainSettings.NotifSettings.NotifStartHour", 9));
        notifSettings.set_notifStartMinute(settings.getInt("MainSettings.NotifSettings.NotifStartMinute", 0));
        notifSettings.set_notifEndHour(settings.getInt("MainSettings.NotifSettings.NotifEndHour", 22));
        notifSettings.set_notifEndMinute(settings.getInt("MainSettings.NotifSettings.NotifEndMinute", 0));

        return notifSettings;
    }
}
