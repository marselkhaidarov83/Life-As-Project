package com.MH83.LifeAsProject.view.Tasks.TasksInbox;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.model.Settings.NotifTasksSettings;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskCustom;

public class FragmentTasksInbox extends FragmentTasksTaskCustom
{
    //region Constructors
    public FragmentTasksInbox() { super(); }
    //endregion

    //region StartFragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_enumTaskSettings(EnumTaskSettings.Inbox);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        StopNotifCountTaskInbox();
    }

    private void StopNotifCountTaskInbox()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifTasksSettings.NotifIdCountTasksInbox);
    }
    //endregion

}
