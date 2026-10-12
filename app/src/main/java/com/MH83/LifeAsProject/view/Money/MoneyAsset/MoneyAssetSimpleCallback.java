package com.MH83.LifeAsProject.view.Money.MoneyAsset;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;
import com.MH83.LifeAsProject.view.Custom.CustomSimpleCallback;

public class MoneyAssetSimpleCallback extends CustomSimpleCallback
{
    public MoneyAssetSimpleCallback(RecyclerView rView, int basedBorderLineLeftDp)
    {
        super(ItemTouchHelper.DOWN + ItemTouchHelper.UP, 0, rView, basedBorderLineLeftDp,
                EnumRecycleViewParentLayout.FrameLayout, true, true,
                R.drawable.icons_list_100_blue, R.drawable.icons_delete_item_50);
    }
}
