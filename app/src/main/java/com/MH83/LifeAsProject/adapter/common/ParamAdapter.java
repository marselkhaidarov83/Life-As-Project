package com.MH83.LifeAsProject.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.Param;
import com.MH83.LifeAsProject.view.Custom.Param.FragmentParamPopupNew;

import java.util.ArrayList;

public class ParamAdapter
        extends CustomMoneyAdapter<Param, ParamAdapter.ItemViewHolder,
        FragmentParamPopupNew>
{
    public ParamAdapter(Context context, ArrayList<Param> dataSet,
                        IFragmentViewCustom<Param> fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_param);
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
        Param param = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = param;

            _fragmentPopupNew = new FragmentParamPopupNew((AppCompatActivity) _context,
                    param, EnumTypeAction.Update, _fragmentViewCustom);
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

    public int AddItem(Param param)
    {
        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (param.get_name().compareTo(_dataSet.get(i).get_name()) < 0)
            {
                insertPosition = i;
                break;
            }
        }

        _dataSet.add(insertPosition, param);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(Param param)
    {
        long id = param.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (param.get_name().equals(_dataSet.get(i).get_name()))
                {
                    _dataSet.set(i, param);
                    notifyItemChanged(i);
                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(param);
                }
            }
        }
        return 0;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeleteParam(_currentItem.get_id()))
            {
                return DeleteFromDataset(_currentItem);
            }
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedParamNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {

            Toast.makeText(_context, _context.getString(R.string.errorDeleteParam) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(Param param)
    {
        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (param.get_id() == _dataSet.get(i).get_id())
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

    public static class ItemViewHolder extends CustomItemViewHolder<Param>
    {
        private final TextView textViewName;
        private final TextView textViewMeasure;
        private final TextView textViewComment;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public Long getId() { return _object.get_id(); }
        private void setName(String name)
        {
            textViewName.setText(name);
        }
        private void setMeasure(String measure)
        {
            textViewMeasure.setText(measure);
        }
        private void setComment(String comment)
        {
            textViewComment.setText(comment);
        }

        @Override
        public void setObject(Param param)
        {
            super.setObject(param);

            setName(_object.get_name());
            setMeasure(_object.get_measure());
            setComment(_object.get_comment());
        }
    }
}
