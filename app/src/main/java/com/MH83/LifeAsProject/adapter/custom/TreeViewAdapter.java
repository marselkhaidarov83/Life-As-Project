package com.MH83.LifeAsProject.adapter.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Objects;

public abstract class TreeViewAdapter<VH extends TreeView.ViewHolder,
        TDataSet extends CustomMoneyObject,
        TPopupNew extends CustomPopupBottomFragment>
        extends CustomMoneyAdapter<TreeItem<TDataSet>, VH, TPopupNew>
        implements ICollapseAllAdapter, IExpandAllAdapter
{
    private static final int ANIM_TIME = 200;
    private int indentationWidth;
    public OnTreeItemClickListener<TDataSet> listener;
    private boolean showRoot = false;
    public abstract VH onCreateViewHolder(View view);
    protected boolean _setPadding = false;
    protected TreeItem<TDataSet> _root;

    public TreeViewAdapter(Context context, ArrayList<TreeItem<TDataSet>> dataSet,
                           IFragmentViewCustom fragmentViewCustom, int layoutRes)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_treeview, layoutRes);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(_layoutId, parent, false);
        inflater.inflate(_layoutRes, view.findViewById(R.id.content), true);
        return onCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position)
    {
        super.onBindViewHolder(holder, position);

        TreeItem<TDataSet> item = _dataSet.get(position);
        View itemView = holder.itemView;
        ImageView leadingIcon = ((ItemViewHolder<TreeItem<TDataSet>>) holder).leadingIcon;
        ((ItemViewHolder<TreeItem<TDataSet>>) holder).setObject(_dataSet.get(position));

        if (_setPadding)
            itemView.setPadding(computeIndentPadding(item), 0, 0, 0);

        if (item.isExpandable())
        {
            SetLedingIconImageResource(item, leadingIcon, true);

            leadingIcon.setVisibility(View.VISIBLE);
        }
        else if (item.getDepth() <= 1)
            leadingIcon.setImageResource(R.drawable.icons_punkt_purple_24);
        else if (_setPadding)
            leadingIcon.setImageResource(getLeadingIcon4Child(holder));
        else
            leadingIcon.setVisibility(View.INVISIBLE);

        leadingIcon.setOnClickListener((v) ->
        {
            ClickToLeadingIcon(item, holder);
//            if (listener != null)
//                listener.onClick(item);
//
//            ChangeExpandedToItem(item, holder);
        });
    }

    protected int getLeadingIcon4Child(@NonNull VH holder)
    {
        return R.drawable.icons_circle_24;
    }

    protected void ClickToLeadingIcon(TreeItem<TDataSet> item, @NonNull VH holder)
    {
        if (listener != null)
            listener.onClick(item);

        ChangeExpandedToItem(item, holder);
    }

    public ArrayList<Long> GetExpandedId()
    {
        ArrayList<Long> list = new ArrayList<>();

        for (TreeItem<TDataSet> dataItem: _dataSet)
            if (dataItem.isExpanded() && dataItem.get_object() instanceof IgetId)
                list.add(((IgetId)dataItem.get_object()).get_id());
        return list;
    }

    private void SetLedingIconImageResource(TreeItem<TDataSet> item, ImageView leadingIcon,
                                            boolean postProcessing)
    {
        if ((item.isExpanded() && postProcessing) ||
                (!item.isExpanded() && !postProcessing))
            leadingIcon.setImageResource(R.drawable.icons_minus_24);
//            leadingIcon.setImageResource(R.drawable.icons_circle_24);
        else
            leadingIcon.setImageResource(R.drawable.icons_plus_24);
    }

    protected void ChangeExpandedToItem(TreeItem<TDataSet> item, VH holder)
    {
        // click with debounce
        long lastClickTime = (long) holder.itemView.getTag();
        if (System.currentTimeMillis() - lastClickTime < ANIM_TIME)
            return;

        holder.itemView.setTag(System.currentTimeMillis());

        if (item.isExpandable())
        {
            if (item.isExpanded())
            {
                int removedCount = removeAllExpandedItemRecursive(item, holder.getLayoutPosition() + 1);
                notifyItemRangeRemoved(holder.getLayoutPosition() + 1, removedCount);
            }
            else
            {
                item.setExpanded(true);
                int insertedCount = insertAllExpandedItemRecursive(item, holder.getLayoutPosition() + 1);
                notifyItemRangeInserted(holder.getLayoutPosition() + 1, insertedCount);
                item.setExpanded(false);
            }

            SetLedingIconImageResource(item,
                    ((ItemViewHolder<TreeItem<TDataSet>>)holder).leadingIcon, false);

            item.setExpanded(!item.isExpanded());
        }
    }

    @Override
    public boolean canActionRightImage()
    {
        return true;
    }

    private int computeIndentPadding(TreeItem<TDataSet> item)
    {
        int depth = item.getDepth();
        if (!showRoot) depth--;
        return indentationWidth * depth;
    }

    protected int removeAllExpandedItemRecursive(TreeItem<TDataSet> root, int index)
    {
        if (root == null || !root.isExpanded()) return 0;

        int cnt = 0;
        for (TreeItem<TDataSet> child : root.getChildren())
        {
            child.set_object(_dataSet.get(index).get_object());
            _dataSet.remove(index);

            cnt += removeAllExpandedItemRecursive(child, index) + 1;
        }

        return cnt;
    }

    protected int insertAllExpandedItemRecursive(TreeItem<TDataSet> root, int index)
    {
        if (root == null || !root.isExpanded()) return 0;

        int cnt = 0;
        for (TreeItem<TDataSet> child : root.getChildren())
        {
            _dataSet.add(index + cnt, child);

            cnt += insertAllExpandedItemRecursive(child, index + cnt + 1) + 1;
        }

        return cnt;
    }

    @Override
    public int getItemCount() { return _dataSet.size();}

    public void setRoot(TreeItem<TDataSet> root) { setRoot(root, false); }

    @SuppressLint("NotifyDataSetChanged")
    public void setRoot(TreeItem<TDataSet> root, boolean showRoot)
    {
        _root = root;
        _dataSet.clear();

        if (showRoot)
        {
            _dataSet.add(root);
            if (root.isExpanded())
                for (TreeItem<TDataSet> item: root.getChildren())
                    setItem(item);
        }
        else
            for (TreeItem<TDataSet> item: root.getChildren())
                setItem(item);

        notifyDataSetChanged();
        this.showRoot = showRoot;
    }

    private void setItem(TreeItem<TDataSet> rootItem)
    {
        _dataSet.add(rootItem);
        if (rootItem.isExpanded())
            for (TreeItem<TDataSet> item: rootItem.getChildren())
                setItem(item);
    }

    public int addLevel2(TreeItem<TDataSet> item)
    {
        item.set_parent(_root);
        _dataSet.add(item);
        notifyItemInserted(_dataSet.size() - 1);
        return _dataSet.size() - 1;
    }

    public int addLevel2(TreeItem<TDataSet> item, int index)
    {
        item.set_parent(_root);
        _dataSet.add(index, item);
        notifyItemInserted(index);
        return index;
    }

    public int addLevel3(TreeItem<TDataSet> item, Long parent_id)
    {
        int kk = -1;
        for (int i = 0; i < _dataSet.size(); i++)
        {
            IgetId data = (IgetId) _dataSet.get(i).get_object();
            if (Objects.equals(data.get_id(), parent_id))
            {
                _dataSet.get(i).getChildren().add(item);

                if (_dataSet.get(i).isExpanded())
                {
                    kk = _dataSet.size();
                    for (int k = i + 1; k < _dataSet.size(); k++)
                        if (((IgetId) _dataSet.get(k).get_object()).get_parentId() == 0)
                        {
                            kk = k;
                            break;
                        }

                    if (!_dataSet.contains(item))
                    {
                        _dataSet.add(kk, item);
                        notifyItemInserted(kk);
                    }
                    else
                    {
                        kk = _dataSet.indexOf(item);
                        notifyItemInserted(kk);
                    }
                }

                break;
            }
        }

        return kk;
    }

    public int addLevel3ToParent(TreeItem<TDataSet> item, Long parent_id)
    {
        int kk = -1;
        for (int i = 0; i < _dataSet.size(); i++)
        {
            IgetId data = (IgetId) _dataSet.get(i).get_object();
            IIsParent dataParent = (IIsParent) _dataSet.get(i).get_object();
            if (Objects.equals(data.get_id(), parent_id) && dataParent.isParent())
            {
                _dataSet.get(i).getChildren().add(item);

                if (_dataSet.get(i).isExpanded())
                {
                    kk = _dataSet.size();
                    for (int k = i + 1; k < _dataSet.size(); k++)
                        if (((IgetId) _dataSet.get(k).get_object()).get_parentId() == 0)
                        {
                            kk = k;
                            break;
                        }

                    if (!_dataSet.contains(item))
                    {
                        _dataSet.add(kk, item);
                        notifyItemInserted(kk);
                    }
                    else
                    {
                        kk = _dataSet.indexOf(item);
                        notifyItemInserted(kk);
                    }
                }

                break;
            }
        }

        return kk;
    }

    public int addLevel3(TreeItem<TDataSet> item, Long parent_id, int index)
    {
        return addLevel3(item, parent_id, index, true);
    }

    public int addLevel3(TreeItem<TDataSet> item, Long parent_id, int index, boolean visible)
    {
        int kk = -1;
        for (int i = 0; i < _dataSet.size(); i++)
        {
            IgetId data = (IgetId) _dataSet.get(i).get_object();
            if (data.get_id() == parent_id)
            {
                _dataSet.get(i).getChildren().add(index, item);
                item.set_parent(_dataSet.get(i));

                if (_dataSet.get(i).isExpanded() && visible)
                {
                    kk = i + index + 1;
                    _dataSet.add(kk, item);
                }

                break;
            }
        }

        if (kk != -1 && visible)
            notifyItemInserted(kk);
        return kk;
    }

    public void Expand(Long parentId)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (((IIsParent)_dataSet.get(i).get_object()).isParent() &&
                    ((IgetId) _dataSet.get(i).get_object()).get_id().equals(parentId))
                if (!_dataSet.get(i).isExpanded())
                {
                    _dataSet.get(i).setExpanded(true);
                    notifyItemChanged(i);
                    insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                }
        }
    }

    public void ExpandAll(boolean showExpandedButton)
    {
        for (int i = 0; i < _dataSet.size(); i++)
            if (((IIsParent) _dataSet.get(i).get_object()).isParent())
                if (!_dataSet.get(i).isExpanded())
                {
                    _dataSet.get(i).setExpanded(true);
                    notifyItemChanged(i);
                    insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                }
    }

    public int Collapse(Long parentId)
    {
        int removeCntAll = 0;

        for (int i = 0; i < _dataSet.size(); i++)
            if (((IIsParent)_dataSet.get(i).get_object()).isParent() && ((IgetId)_dataSet.get(i).get_object()).get_id() == parentId)
                if (_dataSet.get(i).isExpanded())
                {
                    int removedCount = removeAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    removeCntAll += removedCount;
                    notifyItemRangeRemoved(i + 1, removedCount);
                    _dataSet.get(i).setExpanded(false);
                    notifyItemChanged(i);
                }

        return removeCntAll;
    }

    public void CollapseAll(boolean showExpandedButton)
    {
        for (int i = 0; i < _dataSet.size(); i++)
            if (((IIsParent)_dataSet.get(i).get_object()).isParent())
                if (_dataSet.get(i).isExpanded())
                {
                    int removedCount = removeAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeRemoved(i + 1, removedCount);
                    _dataSet.get(i).setExpanded(false);
                    notifyItemChanged(i);
                }
    }

    public void CollapseAllFromEnd(boolean showExpandedButton)
    {
        for (int i = _dataSet.size() - 1; i >= 0; i--)
            if (((IIsParent)_dataSet.get(i).get_object()).isParent())
                if (_dataSet.get(i).isExpanded())
                {
                    int removedCount = removeAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeRemoved(i + 1, removedCount);
                    _dataSet.get(i).setExpanded(false);
                    notifyItemChanged(i);
                }
    }

    public void setTreeItemClickListener(OnTreeItemClickListener<TDataSet> listener)
    {
        this.listener = listener;
    }

    public void setIdentationWidth(int width) { this.indentationWidth = width;}

    public interface OnTreeItemClickListener<TDataSet extends CustomMoneyObject> { void onClick(TreeItem<TDataSet> item);}

    public static class ItemViewHolder<TData> extends CustomItemViewHolder<TData>
    {
        private final ImageView leadingIcon;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            leadingIcon = itemView.findViewById(R.id.leading_icon);
            itemView.setTag(System.currentTimeMillis());
        }
    }
}