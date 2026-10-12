package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAccountingDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccountingPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyCostPopupNew;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MoneyPNLCreditAdapter
        extends CustomMoneyAdapter<MoneyPNL, MoneyPNLCreditAdapter.ItemViewHolder,
        FragmentMoneyAccountingPopupNew>
{
    private final boolean _editable;
    public MoneyPNLCreditAdapter(Context context, ArrayList<MoneyPNL> dataSet,
                                 IFragmentViewCustom fragmentViewCustom,
                                 boolean editable)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_money_pnl_credit);
        _editable = editable;
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
        MoneyPNL moneyPNL = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            if (!_editable)
                return;

            _currentItem = moneyPNL;
            int index = _dataSet.indexOf(_currentItem);
            if (index > 0 && _dataSet.get(index - 1).get_isPlan())
                return;
            if (index < _dataSet.size() - 1 && !_dataSet.get(index + 1).get_isPlan())
                return;
            _fragmentPopupNew = null;

            MoneyAccountingSum moneyAccountingSumCost =
                    MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                            moneyPNL.getCostCategoryId());
            MoneyAccountingSum moneyAccountingSumIncrease =
                    MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                            moneyPNL.getIncreaseCategoryId());

            _fragmentPopupNew = new FragmentMoneyCostPopupNew(
                    (AppCompatActivity) _context, moneyPNL, EnumTypeAction.Update,
                    _fragmentViewCustom, moneyAccountingSumCost, moneyAccountingSumIncrease,
                    Calendar.getInstance(), Calendar.getInstance(),
                    _editable, _editable, true);

            _fragmentPopupNew.PopupShow();
        });
    }

    @Override
    public boolean canActionLeftImage()
    {
        return false;
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem != null && _currentItem.get_percentSumLong() == 0L;
    }

    public boolean LeftButtonClick()
    {
        return false;
    }

    public boolean RightButtonClick()
    {
        if (_currentItem == null)
            return false;

        return DeleteCurrent();
    }

    public int AddItem(MoneyPNL moneyPNL)
    {
        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
            if (moneyPNL.getDate().getTime() < _dataSet.get(i).getDate().getTime())
            {
                insertPosition = i;
                break;
            }

        _dataSet.add(insertPosition, moneyPNL);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(MoneyPNL moneyPNL)
    {
        Long id = moneyPNL.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                if (moneyPNL.getDate().getTime() == _dataSet.get(i).getDate().getTime())
                {
                    _dataSet.set(i, moneyPNL);
                    notifyItemChanged(i);
                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyPNL);
                }
            }

        _fragmentPopupNew = null;
        return 0;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (_currentItem.DeleteFromDatabase(dbHelper, _context))
                return DeleteFromDataset(_currentItem);
            else
                Toast.makeText(_context,
                    _context.getString(R.string.errorDeletedMoneyPNLNotFound),
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context,
                    _context.getString(R.string.errorDeleteMoneyPNL) + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyPNL moneyPNL)
    {
        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (_dataSet.get(i).get_id().equals(moneyPNL.get_id()))
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

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyPNL>
    {
        private final TextView textViewDate;
        private final TextView textViewCostName;
        private final TextView textViewBodySum;
        private final TextView textViewSum;
        private final TextView textViewPercentSum;
        private final LinearLayout linearLayoutItem;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCostName = itemView.findViewById(R.id.textViewCostName);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            textViewBodySum = itemView.findViewById(R.id.textViewBodySum);
            textViewPercentSum = itemView.findViewById(R.id.textViewPercentSum);
            linearLayoutItem = itemView.findViewById(R.id.linear_layout_item);
        }

        private void setDate(Integer day, Integer month, Integer year)
        {
            if (textViewDate == null)
                return;

            final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet ||
                    _context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                CustomStaticClass.setDateView(_context, day, month, year, textViewDate);
            else
                textViewDate.setText(CustomStaticClass.getDateShort4View(day, month, year));
        }
        private void setCostName(String name) { textViewCostName.setText(name); }
        @SuppressLint("SetTextI18n")
        private void setBodySumLong(Long sumLong)
        {
            textViewBodySum.setText(CustomStaticClass.getRubleSum(sumLong / 100d));
        }
        @SuppressLint("SetTextI18n")
        private void setTotalSumLong(Long sumLong)
        {
            if (textViewSum != null)
                textViewSum.setText(CustomStaticClass.getRubleSum(sumLong / 100d));
        }
        @SuppressLint("SetTextI18n")
        private void setPercentSumLong(Long sumLong)
        {
            textViewPercentSum.setText(CustomStaticClass.getRubleSum(sumLong / 100d));
        }
        private void set_isPlan(boolean isPlan)
        {
            linearLayoutItem.setBackgroundResource(isPlan ? R.color.lightGrey : R.color.white);
        }

        @Override
        public void setObject(MoneyPNL moneyPNL)
        {
            super.setObject(moneyPNL);

            setDate(moneyPNL.getDay(), moneyPNL.getMonth(), moneyPNL.getYear());
            setCostName(moneyPNL.getCostCategoryName());
            setBodySumLong(moneyPNL.get_sumLong());
            setTotalSumLong(moneyPNL.get_totalSumLong());
            setPercentSumLong(moneyPNL.get_percentSumLong());
            set_isPlan(moneyPNL.get_isPlan());
        }
    }
}
