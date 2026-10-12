package com.MH83.LifeAsProject.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Custom.CCabinet;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;

import java.util.ArrayList;

public class CabinetAdapter
        extends CustomMoneyAdapter<CCabinet, CabinetAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew>
{
    CabinetAdapter.ItemViewHolder prevViewHolder;
    CCabinet _currentCabinet;

    public CabinetAdapter(Context context, ArrayList<CCabinet> dataSet,
                          IFragmentViewCustom fragmentViewCustom,
                          CCabinet currentCabinet)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_cabinet);
        _currentCabinet = currentCabinet;
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

        if (_currentCabinet != null && _dataSet.get(position).get_id() == _currentCabinet.get_id())
        {
            holder.SetSelected(true);
            prevViewHolder = holder;
        }

        holder.itemView.setOnClickListener(view ->
        {
            if (_dataSet.get(position).get_id() != 9999)
            {
                if (prevViewHolder != null)
                    prevViewHolder.SetSelected(false);
                holder.SetSelected(true);

                prevViewHolder = holder;
                _currentCabinet = _dataSet.get(position);

                final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
                globalVariable.set_cCabinet(_currentCabinet);
            }
        });


//        holder.itemView.setOnLongClickListener(view ->
//        {
//            _moneyIncomingSumTrio.SetIncomMoneyAccountingSum(_dataSet.get(pos));
//            ClipData data = ClipData.newPlainText("", "");
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//            view.startDragAndDrop(data, shadowBuilder, view, 0);
//            return true;
//        });
    }

    public boolean LeftButtonClick() { return false; }

    public boolean RightButtonClick() { return false; }

    public int AddItem(CCabinet cCabinet)
    {
        /*Integer month = moneyIncrease.getImonth();
        Integer year = moneyIncrease.getIyear();

        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if ((month < _dataSet.get(i).getImonth() && year.equals(_dataSet.get(i).getIyear()))
                || (year < _dataSet.get(i).getIyear()))
            {
                insertPosition = i;
                break;
            }
        }

        if (insertPosition > 0)
            moneyIncrease.set_upOrDown(_dataSet.get(insertPosition - 1).get_value());
        _dataSet.add(insertPosition, moneyIncrease);
        notifyItemInserted(insertPosition);

        if (insertPosition < _dataSet.size() - 1)
        {
            _dataSet.get(insertPosition + 1).set_upOrDown(_dataSet.get(insertPosition).get_value());
            notifyItemChanged(insertPosition + 1);
        }

        return insertPosition;*/

        return 0;
    }

    public int UpdateItem(CCabinet cCabinet)
    {
        /*long id = moneyIncrease.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (moneyIncrease.get_month().equals(_dataSet.get(i).get_month()) &&
                    moneyIncrease.get_year().equals(_dataSet.get(i).get_year()))
                {
                    _dataSet.set(i, moneyIncrease);
                    notifyItemChanged(i);
                    if (i < _dataSet.size() - 1)
                    {
                        _dataSet.get(i + 1).set_upOrDown(_dataSet.get(i).get_value());
                        notifyItemChanged(i + 1);
                    }
                    return 0;
                }
                else
                {
                    if (i > 0 && i < _dataSet.size() - 1)
                    {
                        _dataSet.get(i + 1).set_upOrDown(_dataSet.get(i - 1).get_value());
                        notifyItemChanged(i + 1);
                    }
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyIncrease);
                }
            }
        }*/

        return 0;
    }

    public boolean DeleteCurrent()
    {
        /*try (DBHelper dbHelper = new DBHelper(_context))
        {
            if (dbHelper.DeleteMoneyIncrease(_currentItem.get_id()))
            {
                int index = _dataSet.indexOf(_currentItem);

                if (index > 0 && index < _dataSet.size() - 1)
                {
                    _dataSet.get(index + 1).set_upOrDown(_dataSet.get(index - 1).get_value());
                    notifyItemChanged(index + 1);
                }

                _dataSet.remove(_currentItem);
                notifyItemRemoved(index);
                return true;
            }
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyIncreaseNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {

            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyIncrease) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/

        return false;
    }

    public boolean DeleteFromDataset(CCabinet cCabinet) throws Exception
    {
        return false;
    }

    public static class ItemViewHolder extends CustomItemViewHolder<CCabinet>
    {
        private final TextView textViewName;
        private final TextView textViewNameChar;
        private final LinearLayout linearLayout;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNameChar = itemView.findViewById(R.id.textViewNameChar);
            linearLayout = itemView.findViewById(R.id.linear_layout_item);
        }

        public void setName(String name)
        {
            textViewName.setText(name);
            textViewNameChar.setText(name.substring(0, 1));
        }

        public void SetSelected(boolean selected)
        {
            if (selected)
                linearLayout.setBackgroundResource(R.color.lightGrey);
            else
                linearLayout.setBackgroundResource(R.color.white);
        }

        @Override
        public void setObject(CCabinet cCabinet)
        {
            super.setObject(cCabinet);
            setName(cCabinet.get_name());
        }
    }
}
