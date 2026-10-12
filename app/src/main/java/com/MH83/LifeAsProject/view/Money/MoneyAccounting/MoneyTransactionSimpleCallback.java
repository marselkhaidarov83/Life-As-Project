package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;

public class MoneyTransactionSimpleCallback extends CustomSimpleCallback
{
    public MoneyTransactionSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(0,
                ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT, rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.LinearLayout, true, true,
                R.drawable.icons_copy_blue_100, R.drawable.icons_delete_item_50);
    }
}
