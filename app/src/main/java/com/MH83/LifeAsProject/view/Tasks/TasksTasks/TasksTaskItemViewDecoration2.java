package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.task.TasksTaskAdapterTreeView;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.model.Tasks.TasksPriority;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;

import java.util.Calendar;

public class TasksTaskItemViewDecoration2 extends RecyclerView.ItemDecoration
{
    private final int offset;
    private int ddY = 0;

    public TasksTaskItemViewDecoration2(Context context)
    {
        super();

        offset = (int) context.getResources().getDimension(R.dimen.MoneyCategorySpinnerItemHeight);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
                       @NonNull RecyclerView.State state)
    {
        super.onDraw(c, parent, state);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
                           @NonNull RecyclerView.State state)
    {
        TasksTaskAdapterTreeView adapter = (TasksTaskAdapterTreeView) parent.getAdapter();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)parent.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int dyOld = 0;
        for (int i = 0; i < parent.getChildCount(); i++)
        {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);

            if (pos == -1)
                continue;

            int prev_pos = 0;
            if (pos > 0)
                for (int k = pos - 1; k >= 0; k--)
                    if (adapter.getItem(k).get_object().isParent())
                    {
                        prev_pos = k;
                        break;
                    }

            TasksTask tasksTask = adapter.getItem(pos).get_object();
            if (i == 0 ||
                    (pos > 0 &&
                            adapter.getItem(pos) != null &&
                            tasksTask.isParent() &&
                            !tasksTask.get_priority().equals(adapter.getItem(prev_pos).get_object().get_priority())))
            {
                LinearLayout divider;

                if (tasksTask.get_priority() == 1)
                    divider = (LinearLayout) LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.divider_drawable_tasks_task_high, parent, false);
                else if (tasksTask.get_priority() == 2)
                    divider = (LinearLayout) LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.divider_drawable_tasks_task_middle, parent, false);
                else if (tasksTask.get_priority() == 3)
                    divider = (LinearLayout) LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.divider_drawable_tasks_task_low, parent, false);
                else
                    divider = (LinearLayout) LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.divider_drawable_tasks_task_none, parent, false);

                TextView textViewDate = divider.findViewById(R.id.textView1Text);
                textViewDate.setWidth(parent.getRight() / 2);

                textViewDate.setText(TasksPriority.getName(parent.getContext(), tasksTask.get_priority()));

                int left = parent.getLeft();
                int right = parent.getRight();
                int top = 0, bottom;
                int dy = 0;

                if (i == 0)
                {
                    bottom = offset;

                    if (adapter.getItemCount() > pos + 1)
                    {
                        TasksTask tasksTask2 = adapter.getItem(pos + 1).get_object();
                        if (!tasksTask.CompareDate(tasksTask2))
                            if (view.getBottom() < offset)
                                dy = view.getBottom() - offset;
                        ddY = dy;
                    }
                }
                else
                {
                    top = view.getTop() - offset;
                    bottom = view.getTop();
                    dy = view.getTop() - offset -
                        (int) parent.getContext().getResources().getDimension(R.dimen.BasePadding)
                        -ddY;
                }

                divider.measure(c.getWidth(), c.getHeight());
                divider.layout(left, top, right, bottom);

                if (ddY < 0 && i == 0)
                {
                    c.translate(0, dy - dyOld);
                    dyOld = dy;
                }
                else if (ddY < 0 && i > 0)
                {
                    c.translate(0, dy - dyOld + ddY);
                    dyOld = dy + ddY;
                    ddY = 0;
                }
                else
                {
                    c.translate(0, dy - dyOld);
                    dyOld = dy;
                }

                divider.draw(c);
            }
        }

        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
    {
        TasksTaskAdapterTreeView adapter = (TasksTaskAdapterTreeView) parent.getAdapter();
        int pos = parent.getChildAdapterPosition(view);

        if (pos == -1)
            return;

        int prev_pos = 0;
        if (pos > 0)
            for (int k = pos - 1; k >= 0; k--)
                if (adapter.getItem(k).get_object().isParent())
                {
                    prev_pos = k;
                    break;
                }

        if (pos == 0 ||
                (pos > 0 &&
                        adapter.getItem(pos) != null &&
                        adapter.getItem(pos).get_object().isParent() &&
                        !adapter.getItem(pos).get_object().get_priority().equals(adapter.getItem(prev_pos).get_object().get_priority())))
            outRect.top = offset +
                    (int) parent.getContext().getResources().getDimension(R.dimen.BasePadding);
    }
}
