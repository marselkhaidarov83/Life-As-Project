package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTasksTaskLegendPopup extends CustomLegendPopup
{
    public FragmentTasksTaskLegendPopup()
    {
        super(R.layout.popup_legend_tasks_task);
    }

    public FragmentTasksTaskLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_tasks_task);
    }
}
