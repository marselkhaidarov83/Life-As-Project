package com.MH83.LifeAsProject.view.Money.MoneyBoard;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;
import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;

public class MoneyBoardSettingsSimpleCallback extends CustomSimpleCallback
{
    public MoneyBoardSettingsSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(ItemTouchHelper.UP + ItemTouchHelper.DOWN,
                0,
                rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.LinearLayout, true, true,
                R.drawable.icons_copy_blue_100, R.drawable.icons_delete_item_50);
    }
}
