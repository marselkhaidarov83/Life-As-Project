package com.MH83.LifeAsProject.view.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.MainActivity;

public class TasksProjectActivity extends AppCompatActivity
{
    //region Extras
    public static String ProjectIdExtra = "ProjectId";
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        CustomTrack.TrackPNotifFromNeedTaskInProject();

        String s = getIntent().getStringExtra(ProjectIdExtra);
        if (s.contains("TasksProject"))
        {
            globalVar.set_enumTaskSettings(EnumTaskSettings.FromProject);
            globalVar.set_tasksTaskSettingsHT(new TasksTaskSettings(
                    new CustomPeriodClass(2, ""),
                    false,
                    null,
                    Long.parseLong(s.substring(12)),
                    null,
                    EnumTaskSorting.Date_Priority_Name,
                    true));
            globalVar.set_mainActivity_navigationId(R.id.navigation_tasks_tasks_fromproject);
        }

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(resultIntent);
        finish();

        super.onCreate(savedInstanceState);
    }
}
