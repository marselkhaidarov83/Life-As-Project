package com.MH83.LifeAsProject.view.Money.MoneyTarget;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;
import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;

public class MoneyTargetSimpleCallback extends CustomSimpleCallback
{
    public MoneyTargetSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(0,
                ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT, rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.LinearLayout, false, false,
                0, R.drawable.icons_delete_item_50);
    }
}
