package com.MH83.LifeAsProject.custom.treeview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class TreeItemChildrenList<TDataSet extends CustomMoneyObject> implements List<TreeItem<TDataSet>>
{
    private final List<TreeItem<TDataSet>> list = new ArrayList<>();
    private final TreeItem<TDataSet> parent;

    public TreeItemChildrenList(TreeItem<TDataSet> parent) {
      this.parent = parent;
    }

    @Override
    public int size() {
      return list.size();
    }

    @Override
    public boolean isEmpty() {
      return list.isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object o) {
      return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<TreeItem<TDataSet>> iterator() {
      return list.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
      return list.toArray();
    }

    @NonNull
    @Override
    public <E> E[] toArray(@NonNull E[] a) {
      throw new UnsupportedOperationException();
    }

    /**
     * @throws IllegalStateException if parent is not a expandable item
     */
    @Override
    public boolean add(TreeItem<TDataSet> ele)
    {
        ensureParentIsExpandable();

        ele.parent = parent;
        return list.add(ele);
    }

    @Override
    public boolean remove(@Nullable Object o) {
      return list.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c)
    {
        return new HashSet<>(list).containsAll(c);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public boolean addAll(@NonNull Collection<? extends TreeItem<TDataSet>> c)
    {
        ensureParentIsExpandable();

        for (TreeItem<TDataSet> ele : c)
          ele.parent = parent;

        return list.addAll(c);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public boolean addAll(int index, @NonNull Collection<? extends TreeItem<TDataSet>> c)
    {
        ensureParentIsExpandable();

        for (TreeItem<TDataSet> ele : c)
          ele.parent = parent;

        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
      return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
      return list.retainAll(c);
    }

    @Override
    public void clear() {
      list.clear();
    }

    @Override
    public TreeItem<TDataSet> get(int index) {
      return list.get(index);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public TreeItem<TDataSet> set(int index, TreeItem<TDataSet> ele)
    {
        ensureParentIsExpandable();

        ele.parent = parent;
        return list.set(index, ele);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public void add(int index, TreeItem<TDataSet> ele)
    {
        ensureParentIsExpandable();

        ele.parent = parent;
        list.add(index, ele);
    }

    @Override
    public TreeItem<TDataSet> remove(int index) {
      return list.remove(index);
    }

    @Override
    public int indexOf(@Nullable Object o) {
      return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
      return list.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<TreeItem<TDataSet>> listIterator() {
      return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<TreeItem<TDataSet>> listIterator(int index) {
      return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<TreeItem<TDataSet>> subList(int fromIndex, int toIndex)
    {
        return list.subList(fromIndex, toIndex);
    }

    private void ensureParentIsExpandable()
    {
        if (!parent.isExpandable())
            throw new IllegalStateException(parent + " is not expandable.");
    }
}
