package com.MH83.LifeAsProject.view.Tasks.TasksProject;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;
import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;

public class TasksTaskSimpleCallback extends CustomSimpleCallback
{
    public TasksTaskSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(0,
                ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT, rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.LinearLayout, false, true,
                R.drawable.icons_copy_blue_100, R.drawable.icons_delete_item_50);
    }
}
