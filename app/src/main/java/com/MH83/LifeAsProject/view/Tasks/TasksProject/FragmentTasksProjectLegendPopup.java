package com.MH83.LifeAsProject.view.Tasks.TasksProject;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentTasksProjectLegendPopup extends CustomLegendPopup
{
    public FragmentTasksProjectLegendPopup()
    {
        super(R.layout.popup_legend_tasks_project);
    }

    public FragmentTasksProjectLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_tasks_project);
    }
}
