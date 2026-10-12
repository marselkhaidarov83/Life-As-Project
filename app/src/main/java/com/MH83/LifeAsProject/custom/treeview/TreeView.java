package com.MH83.LifeAsProject.custom.treeview;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.adapter.custom.TreeViewAdapter;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.view.Custom.CustomFragmentPopup;
import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;


public class TreeView extends RecyclerView
{
    public TreeView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public TreeView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public TreeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
      setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public <VH extends ViewHolder, T extends CustomMoneyObject,
            TPopupNew extends CustomPopupBottomFragment>
    void setAdapter(@Nullable TreeViewAdapter<VH, T, TPopupNew> adapter)
    {
        super.setAdapter(adapter);
        if (adapter != null)
            adapter.setIdentationWidth(getResources().getDimensionPixelSize(R.dimen.treeview_default_indentation_width));
    }
}
