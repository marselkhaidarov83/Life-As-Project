package com.MH83.LifeAsProject.view.Tasks.TasksToday;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.model.Settings.NotifTasksSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskCustom;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskSettingsPopup;

public class FragmentTasksToday extends FragmentTasksTaskCustom
    implements IFragmentSettings
{
    public FragmentTasksToday() { super(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_enumTaskSettings(EnumTaskSettings.Today);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        StopNotifCountOverdueTasks();
        StopNotifCountTaskToday();
        StopNotifNoTaskToday();
        StopNotifUnfinishedTasksInEvening();
    }

    private void StopNotifCountOverdueTasks()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifTasksSettings.NotifIdOverdueTasks);
    }

    private void StopNotifCountTaskToday()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifTasksSettings.NotifIdCountTasksForDay);
    }

    private void StopNotifNoTaskToday()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifTasksSettings.NotifIdNoTasksForDay);
    }

    private void StopNotifUnfinishedTasksInEvening()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifTasksSettings.NotifIdCountUnfinishedTasksInEvening);
    }

    //region IFragmentSettings
    @Override
    public boolean ShowFormSettingButton() { return true; }

    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            TasksTaskSettings setting = globalVariable.get_tasksTaskSettingsHT();

            TasksTaskSettings newSettings = setting.Clone();

            new FragmentTasksTaskSettingsPopup(
                    (AppCompatActivity) getContext(),
                    newSettings,
                    this).PopupShow();
        };
    }
    //endregion
}
