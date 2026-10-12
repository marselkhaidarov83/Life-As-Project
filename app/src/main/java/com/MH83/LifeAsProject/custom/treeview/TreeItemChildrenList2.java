package com.MH83.LifeAsProject.custom.treeview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class TreeItemChildrenList2<TDataSet extends CustomMoneyObject2> implements List<TreeItem2<TDataSet>>
{
    private final List<TreeItem2<TDataSet>> list = new ArrayList<>();
    private final TreeItem2<TDataSet> parent;

    public TreeItemChildrenList2(TreeItem2<TDataSet> parent) {
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
    public Iterator<TreeItem2<TDataSet>> iterator() {
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
    public boolean add(TreeItem2<TDataSet> ele)
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
    public boolean addAll(@NonNull Collection<? extends TreeItem2<TDataSet>> c)
    {
        ensureParentIsExpandable();

        for (TreeItem2<TDataSet> ele : c)
          ele.parent = parent;

        return list.addAll(c);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public boolean addAll(int index, @NonNull Collection<? extends TreeItem2<TDataSet>> c)
    {
        ensureParentIsExpandable();

        for (TreeItem2<TDataSet> ele : c)
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
    public TreeItem2<TDataSet> get(int index) {
      return list.get(index);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public TreeItem2<TDataSet> set(int index, TreeItem2<TDataSet> ele)
    {
        ensureParentIsExpandable();

        ele.parent = parent;
        return list.set(index, ele);
    }

    /**
     * @throws IllegalStateException if item is not expandable
     */
    @Override
    public void add(int index, TreeItem2<TDataSet> ele)
    {
        ensureParentIsExpandable();

        ele.parent = parent;
        list.add(index, ele);
    }

    @Override
    public TreeItem2<TDataSet> remove(int index) {
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
    public ListIterator<TreeItem2<TDataSet>> listIterator() {
      return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<TreeItem2<TDataSet>> listIterator(int index) {
      return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<TreeItem2<TDataSet>> subList(int fromIndex, int toIndex)
    {
        return list.subList(fromIndex, toIndex);
    }

    private void ensureParentIsExpandable()
    {
        if (!parent.isExpandable())
            throw new IllegalStateException(parent + " is not expandable.");
    }
}
