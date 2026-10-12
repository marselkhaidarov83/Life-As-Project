package com.MH83.LifeAsProject.view.Money.MoneyDream;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;
import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;

public class MoneyDreamSimpleCallback extends CustomSimpleCallback
{
    public MoneyDreamSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(ItemTouchHelper.DOWN + ItemTouchHelper.UP +
                        ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT,
//                ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT,
                0,
                rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.RelativeLayout, true, true,
                R.drawable.icons_target_100, R.drawable.icons_delete_item_50);
    }
}
