package com.MH83.LifeAsProject.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.ISetSelIcon;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class IconAdapter
        extends CustomMoneyAdapter<Icon, IconAdapter.ItemViewHolder, CustomPopupBottomFragment<Icon>>
{
    private ItemViewHolder prevViewHolder;
    private Long _selMoneyCategoryIcon;

    public IconAdapter(Context context, ArrayList<Icon> dataSet, IFragmentViewCustom<Icon> fragmentViewCustom,
                       Long selMoneyCategoryIcon)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_money_category_icon);

        _selMoneyCategoryIcon = selMoneyCategoryIcon;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(_layoutId, parent, false), _context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);

        if (_selMoneyCategoryIcon != null && _dataSet.get(position).get_id() == _selMoneyCategoryIcon)
        {
            holder.SetSelected(true);
            prevViewHolder = holder;
        }

        holder.itemView.setOnClickListener(view ->
        {
            ((ISetSelIcon) _fragmentViewCustom).SetSelIcon(_dataSet.get(position).get_id());
            ((ISetSelIcon) _fragmentViewCustom).ClearFocus();

            if (prevViewHolder != null)
                prevViewHolder.SetSelected(false);
            holder.SetSelected(true);

            prevViewHolder = holder;
            _selMoneyCategoryIcon = _dataSet.get(position).get_id();
        });
    }

    public Long getSelectedIcon()
    {
        return _selMoneyCategoryIcon;
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.canDelete(_context);
    }
    @Override
    public boolean canActionLeftImage() { return false; }

    public boolean LeftButtonClick() { return false; }

    public boolean RightButtonClick()
    {
        return false;
    }

    public static class ItemViewHolder extends CustomItemViewHolder<Icon>
    {
        private final MaterialCardView materialCardViewItem;
        private final ImageView imageViewIcon;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            materialCardViewItem = itemView.findViewById(R.id.materialCardViewItem);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }

        private void setIcon(Integer id)
        {
            imageViewIcon.setImageResource(id);
        }
        @SuppressLint("ResourceAsColor")
        public void SetSelected(boolean selected)
        {
            if (selected)
                materialCardViewItem.setBackgroundResource(R.color.lightGrey);
            else
                materialCardViewItem.setBackgroundResource(R.color.white);
        }

        @Override
        public void setObject(Icon icon)
        {
            super.setObject(icon);

            setIcon(_object.get_icon_id());
        }
    }
}
