package com.MH83.LifeAsProject.view.Money.MoneyCategory;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;
import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;

public class MoneyCategorySimpleCallback extends CustomSimpleCallback
{
    public MoneyCategorySimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(ItemTouchHelper.DOWN + ItemTouchHelper.UP,
                ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT, rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.FrameLayout, true, true,
                R.drawable.icons_copy_blue_100, R.drawable.icons_delete_item_50);
    }
}
