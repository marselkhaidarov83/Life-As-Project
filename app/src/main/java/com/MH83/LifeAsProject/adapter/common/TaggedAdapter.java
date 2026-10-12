package com.MH83.LifeAsProject.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.view.Custom.Tag.FragmentTaggedPopupNew;

import java.util.ArrayList;
import java.util.Objects;

public class TaggedAdapter
        extends CustomMoneyAdapter<Tagged, TaggedAdapter.ItemViewHolder,
        FragmentTaggedPopupNew>
{
    public TaggedAdapter(Context context, ArrayList<Tagged> dataSet,
                         IFragmentViewCustom<Tagged> fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_tagged);
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
        Tagged tagged = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = tagged;

            _fragmentPopupNew = new FragmentTaggedPopupNew((AppCompatActivity) _context,
                    tagged, EnumTypeAction.Update, _fragmentViewCustom);
            _fragmentPopupNew.PopupShow();
        });
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
        return DeleteCurrent();
    }

    public int AddItem(Tagged tagged)
    {
        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (tagged.getSValue().compareTo(_dataSet.get(i).getSValue()) < 0)
            {
                insertPosition = i;
                break;
            }
        }

        _dataSet.add(insertPosition, tagged);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(Tagged tagged)
    {
        long id = tagged.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (tagged.getSValue().equals(_dataSet.get(i).getSValue()))
                {
                    _dataSet.set(i, tagged);
                    notifyItemChanged(i);
                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(tagged);
                }
            }
        }
        return 0;
    }

    private boolean DeleteCurrent()
    {
        return false;
    }

    public boolean DeleteFromDataset(Tagged tagged)
    {
        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(tagged.get_id(), _dataSet.get(i).get_id()))
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        _dataSet.remove(index);
        notifyItemRemoved(index);

        return true;
    }

    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<Tagged>
    {
        private final TextView textViewName;
        private final TextView textViewComment;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public Long getId() { return _object.get_id(); }
        private void setName(String name)
        {
            textViewName.setText(name);
        }
        private void setComment(String comment)
        {
            textViewComment.setText(comment);
        }

        @Override
        public void setObject(Tagged tagged)
        {
            super.setObject(tagged);

            setName(_object.getSValue());
            setComment(_object.get_comment());
        }
    }
}
