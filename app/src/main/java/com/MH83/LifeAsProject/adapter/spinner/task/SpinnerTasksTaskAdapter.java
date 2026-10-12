package com.MH83.LifeAsProject.adapter.spinner.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;

import java.util.List;

public class SpinnerTasksTaskAdapter extends ArrayAdapter<TasksTaskIndependent>
{
    private static final int _layoutId = R.layout.simple_spinner_tasks_task_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_tasks_task_item;
    public SpinnerTasksTaskAdapter(@NonNull Context context, List<TasksTaskIndependent> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        TasksTaskIndependent tasksTask = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(tasksTask.get_name());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        TasksTaskIndependent tasksTask = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(tasksTask.get_name());

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.TasksTaskSpinnerItemHeight));

        return convertView;
    }
}
