package com.MH83.LifeAsProject.model.Settings;

import android.content.SharedPreferences;

public class NotifProjectSettings
{
    public static int NotifIdNeedDream = 1;
    public static int NotifIdNeedTargetShift = 10000;
    public static int NotifIdNeedTaskInProjectShift = 20000;

    private boolean _needDream;
    public boolean get_needDream() { return _needDream; }
    public void set_needDream(boolean needDream) { _needDream = needDream; }

    private boolean _needTarget;
    public boolean get_needTarget() { return _needTarget; }
    public void set_needTarget(boolean needTarget) { _needTarget = needTarget; }

    private boolean _needTaskInProject;
    public boolean get_needTaskInProject() { return _needTaskInProject; }
    public void set_needTaskInProject(boolean needTaskInProject) { _needTaskInProject = needTaskInProject; }

    public NotifProjectSettings()
    {
        _needDream = true;
        _needTarget = true;
        _needTaskInProject = true;
    }

    public SharedPreferences.Editor SaveToSharedPreferences(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifProjectSettings.NeedDream", _needDream);
        editor.putBoolean("MainSettings.NotifProjectSettings.NeedTarget", _needTarget);
        editor.putBoolean("MainSettings.NotifProjectSettings.NeedTaskInProject", _needTaskInProject);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNeedDream(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifProjectSettings.NeedDream", _needDream);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNeedTarget(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifProjectSettings.NeedTarget", _needTarget);

        return editor;
    }

    public SharedPreferences.Editor SaveToSharedPreferencesNeedTaskInProject(SharedPreferences.Editor editor)
    {
        editor.putBoolean("MainSettings.NotifProjectSettings.NeedTaskInProject", _needTaskInProject);

        return editor;
    }

    public static NotifProjectSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        NotifProjectSettings notifProjectSettings = new NotifProjectSettings();

        notifProjectSettings.set_needDream(settings.getBoolean("MainSettings.NotifProjectSettings.NeedDream", true));
        notifProjectSettings.set_needTarget(settings.getBoolean("MainSettings.NotifProjectSettings.NeedTarget", true));
        notifProjectSettings.set_needTaskInProject(settings.getBoolean("MainSettings.NotifProjectSettings.NeedTaskInProject", true));

        return notifProjectSettings;
    }
}
