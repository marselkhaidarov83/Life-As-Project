package com.MH83.LifeAsProject.custom.treeview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.List;

public class TreeItem<TDataSet extends CustomMoneyObject> extends CustomMoneyObject
{
    private final List<TreeItem<TDataSet>> children;
    private boolean expanded;
    private boolean expandable;
    TreeItem<TDataSet> parent;
    private TDataSet _object;
    public boolean canDelete(Context context)
    {
        return true;
    }

    public TreeItem(@NonNull TDataSet object, boolean expandable)
    {
        _object = object;
        this.expandable = expandable;
        this.children = new TreeItemChildrenList<>(this);
    }

    public TDataSet get_object() { return _object; }

    public void set_object(TDataSet object) { _object = object; }

    public void set_parent(TreeItem<TDataSet> par) { parent = par; }

    public boolean isExpanded() { return expanded; }

    /**
     * * @throws IllegalStateException if item is not expandable
     * */
    public void setExpanded(boolean expanded)
    {
        ensureSelfIsExpandable();
        this.expanded = expanded;
    }

    public boolean isExpandable() {
        return expandable;
      }
      public void setExpandable(boolean b) { expandable = b; }

    @Nullable
    public TreeItem<TDataSet> getParent() {
        return parent;
      }

    @NonNull
    public List<TreeItem<TDataSet>> getChildren() {
        return children;
      }

    public int getDepth()
    {
        if (parent == null) return 0;
        return parent.getDepth() + 1;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "TreeItem(" +
                "value=" + _object.getSValue() +
                ", expanded=" + expanded +
                ')';
    }

    private void ensureSelfIsExpandable()
    {
        if (!expandable)
            throw new IllegalStateException(this + " is not expandable.");
    }

    @Override
    public String getSValue() { return _object.getSValue(); }

    @Override
    public void set_id(Long id) { _object.set_id(id); }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _object.AddToDatabase(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return _object.UpdateInDatabase(dbHelper, context);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return _object.DeleteFromDatabase(dbHelper, context);
    }
}
