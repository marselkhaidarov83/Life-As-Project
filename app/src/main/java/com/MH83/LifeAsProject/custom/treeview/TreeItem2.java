package com.MH83.LifeAsProject.custom.treeview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject2;

import java.util.List;

public class TreeItem2<TDataSet extends CustomMoneyObject2> extends CustomMoneyObject2
{
    private final List<TreeItem2<TDataSet>> children;
    private boolean expanded;
    private final boolean expandable;
    TreeItem2<TDataSet> parent;
    private TDataSet _object;

    public TreeItem2(@NonNull TDataSet object, boolean expandable)
    {
        _object = object;
        this.expandable = expandable;
        this.children = new TreeItemChildrenList2<>(this);
    }

    public TDataSet get_object() { return _object; }

    public void set_object(TDataSet object) { _object = object; }

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

    @Nullable
    public TreeItem2<TDataSet> getParent() {
        return parent;
      }

    @NonNull
    public List<TreeItem2<TDataSet>> getChildren() {
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
    public void set_id(int id) { _object.set_id(id); }

    @Override
    public void AddToDatabase(DBHelper dbHelper) throws Exception { _object.AddToDatabase(dbHelper); }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper) throws Exception
    {
        return _object.UpdateInDatabase(dbHelper);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper) throws Exception
    {
        return _object.DeleteFromDatabase(dbHelper);
    }
}
