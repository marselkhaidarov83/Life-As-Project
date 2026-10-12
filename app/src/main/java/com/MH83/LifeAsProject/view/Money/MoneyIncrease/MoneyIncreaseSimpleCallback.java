package com.MH83.LifeAsProject.view.Money.MoneyIncrease;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;

public class MoneyIncreaseSimpleCallback extends CustomSimpleCallback
{
    public MoneyIncreaseSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(0, ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT, rView,
                basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.FrameLayout, true, true,
                R.drawable.icons_money_incom_blue_100, R.drawable.icons_delete_item_50);
    }
}
