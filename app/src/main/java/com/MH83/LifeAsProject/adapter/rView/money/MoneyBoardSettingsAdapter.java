package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.model.Money.MoneyBoard;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MoneyBoardSettingsAdapter
        extends CustomMoneyAdapter<MoneyBoard, MoneyBoardSettingsAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew> implements IAUDAdapter<MoneyBoard>
{
    GlobalClass globalVariable;
    public MoneyBoardSettingsAdapter(Context context, ArrayList<MoneyBoard> dataSet,
                                     IFragmentViewCustom<MoneyBoard> iFragmentViewCustom)
    {
        super(context, dataSet, iFragmentViewCustom, R.layout.item_money_board_settings);
        globalVariable = (GlobalClass) context.getApplicationContext();
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
        MoneyBoard moneyBoard = _dataSet.get(position);

        if (!globalVariable.get_hasUnpaidSubscription())
        {
            holder.imageViewVisible.setOnClickListener(view ->
            {
                _currentItem = moneyBoard;
                _currentItem.inverse_visible();
                UpdateItem(_currentItem);
            });

            holder.imageViewUp.setOnClickListener(view ->
            {
                if (position == 0)
                    return;

                long pos = _dataSet.get(position).get_position();
                _dataSet.get(position).set_position(_dataSet.get(position - 1).get_position());
                _dataSet.get(position - 1).set_position(pos);
                Collections.swap(_dataSet, position, position - 1);

                notifyItemMoved(position - 1, position);
                for (int i = position - 1; i <= position; i++)
                    notifyItemChanged(i);
            });

            holder.imageViewDown.setOnClickListener(view ->
            {
                if (position == _dataSet.size() - 1)
                    return;

                long pos = _dataSet.get(position).get_position();
                _dataSet.get(position).set_position(_dataSet.get(position + 1).get_position());
                _dataSet.get(position + 1).set_position(pos);
                Collections.swap(_dataSet, position, position + 1);

                notifyItemMoved(position, position + 1);
                for (int i = position; i <= position + 1; i++)
                    notifyItemChanged(i);
            });
        }
        else
        {
            holder.imageViewVisible.setEnabled(false);
            holder.imageViewUp.setEnabled(false);
            holder.imageViewDown.setEnabled(false);
        }
    }

    @Override
    public boolean canActionRightImage()
    {
        return false;
    }
    @Override
    public boolean canActionLeftImage() { return false; }

    public boolean LeftButtonClick()
    {
        return false;
    }

    public boolean RightButtonClick()
    {
        return false;
    }

    public Long AddItem(MoneyBoard moneyBoard)
    {
        return 0L;
    }

    public Long UpdateItem(MoneyBoard moneyBoard)
    {
        Long id = moneyBoard.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                _dataSet.set(i, moneyBoard);
                notifyItemChanged(i);
                break;
            }

        return 0L;
    }

    @Override
    public boolean DeleteFromDataset(MoneyBoard moneyBoard) { return false; }

    public void onItemMove(int fromPosition, int toPosition)
    {
        int start = toPosition;
        int end = fromPosition;
        if (fromPosition < toPosition)
        {
            start = fromPosition;
            end = toPosition;
        }

        if (fromPosition < toPosition)
            for (int i = start; i < end; i++)
            {
                long pos = _dataSet.get(i).get_position();
                _dataSet.get(i).set_position(_dataSet.get(i + 1).get_position());
//                _dataSet.get(i).set_descPosition(i + 2);
                _dataSet.get(i + 1).set_position(pos);
//                _dataSet.get(i + 1).set_descPosition(i + 1);
                Collections.swap(_dataSet, i, i + 1);
            }
        else
            for (int i = end; i > start; i--)
            {
                long pos = _dataSet.get(i).get_position();
                _dataSet.get(i).set_position(_dataSet.get(i - 1).get_position());
//                _dataSet.get(i).set_descPosition(i);
                _dataSet.get(i - 1).set_position(pos);
//                _dataSet.get(i - 1).set_descPosition(i + 1);
                Collections.swap(_dataSet, i, i - 1);
            }

        notifyItemMoved(fromPosition, toPosition);
        for (int i = start; i <= end; i++)
            notifyItemChanged(i);
    }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyBoard>
    {
        private final TextView textViewName;
        public final ImageView imageViewVisible;
        public final ImageView imageViewUp;
        public final ImageView imageViewDown;
        private final LinearLayout linearLayoutItem;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            linearLayoutItem = itemView.findViewById(R.id.linearLayoutItem);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewVisible = itemView.findViewById(R.id.imageViewVisible);
            imageViewUp = itemView.findViewById(R.id.imageViewUp);
            imageViewDown = itemView.findViewById(R.id.imageViewDown);
        }

        private void setName(String name) { textViewName.setText(name); }
        private void setVisible(boolean visible)
        {
            if (visible)
                imageViewVisible.setImageResource(R.drawable.icons_nonarchive);
            else
                imageViewVisible.setImageResource(R.drawable.icon_archive);

            linearLayoutItem.setBackgroundResource(visible ? 0 : R.color.lightLightGrey);
        }

        @Override
        public void setObject(MoneyBoard object)
        {
            super.setObject(object);
            setName(_object.get_name(_context));
            setVisible(object.is_visible());
        }
    }
}
