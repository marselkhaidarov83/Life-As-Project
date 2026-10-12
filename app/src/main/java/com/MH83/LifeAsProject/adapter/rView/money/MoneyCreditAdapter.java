package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCredit;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.view.Money.MoneyCredit.FragmentMoneyCreditPopupNew;

import java.util.ArrayList;
import java.util.Objects;

public class MoneyCreditAdapter
        extends CustomMoneyAdapter<MoneyCredit, MoneyCreditAdapter.ItemViewHolder,
        FragmentMoneyCreditPopupNew>
{
    private final static int _layoutId = R.layout.item_money_credit;

    public MoneyCreditAdapter(Context context, ArrayList<MoneyCredit> dataSet,
                              IFragmentViewCustom<MoneyCredit> fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, _layoutId);
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
        MoneyCredit moneyCredit = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = moneyCredit;
            _fragmentPopupNew = new FragmentMoneyCreditPopupNew((AppCompatActivity) _context,
                    _currentItem, EnumTypeAction.Update, _fragmentViewCustom);
            _fragmentPopupNew.PopupShow();
        });
    }

    @Override
    public boolean canActionLeftImage() { return false; }

    @Override
    public boolean canActionRightImage() { return false; }

    public boolean LeftButtonClick() { return true; }

    public boolean RightButtonClick() { return true; }

    public int AddItem(MoneyCredit moneyCredit)
    {
        int insertPosition = 0;

        for (int i = _dataSet.size() - 1; i >= 0; i--)
            if (moneyCredit.get_dateCalendarStart().getTime() < _dataSet.get(i).get_dateCalendarStart().getTime())
            {
                insertPosition = i + 1;
                break;
            }

//        for (MoneyCredit moneyCreditItem: _dataSet)
//            if (moneyCreditItem.CompareDate(moneyCredit))
//            {
//                MoneyTransactionSum moneyTransactionSum = moneyTransactionItem.get_moneyTransactionSum();
//                moneyTransactionSum.change_sum(moneyTransaction, _moneyAccountingSumIncrease,
//                        EnumTypeCategory.Increase);
//                moneyTransaction.set_moneyTransactionSum(moneyTransactionSum);
//
//                break;
//            }
//        if (moneyTransaction.get_moneyTransactionSum() == null)
//            moneyTransaction.set_moneyTransactionSum(new MoneyTransactionSum(moneyTransaction, _moneyAccountingSumIncrease));

        _dataSet.add(insertPosition, moneyCredit);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(MoneyCredit moneyCredit)
    {
        Long id = moneyCredit.get_id();

//        if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost &&
//                _moneyAccountingSumCost != null &&
//                moneyTransaction.getCostCategoryId() != _moneyAccountingSumCost.getCategoryId())
//        {
//            DeleteFromDataset(moneyTransaction);
//            return -1;
//        }
//
//        if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Increase &&
//                _moneyAccountingSumIncrease != null &&
//                moneyTransaction.getIncreaseCategoryId() != _moneyAccountingSumIncrease.getCategoryId())
//        {
//            DeleteFromDataset(moneyTransaction);
//            return -1;
//        }

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                if (moneyCredit.get_dateCalendarStart().getTime() == _dataSet.get(i).get_dateCalendarStart().getTime())
                {
//                    _dataSet.get(i).get_moneyTransactionSum().change_sum(
//                            _dataSet.get(i), _moneyAccountingSumIncrease,
//                            EnumTypeCategory.Cost);
//
//                    moneyTransaction.set_moneyTransactionSum(_dataSet.get(i).get_moneyTransactionSum());
                    _dataSet.set(i, moneyCredit);
//                    _dataSet.get(i).get_moneyTransactionSum().change_sum(
//                            _dataSet.get(i), _moneyAccountingSumIncrease,
//                            EnumTypeCategory.Increase);

                    notifyItemChanged(i);
                    return i;
                }
                else
                {
//                    _dataSet.get(i).get_moneyTransactionSum().change_sum(
//                            _dataSet.get(i), _moneyAccountingSumIncrease,
//                            EnumTypeCategory.Cost);
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyCredit);
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
                    _context.getString(R.string.errorDeletedMoneyCreditNotFound),
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context,
                    _context.getString(R.string.errorDeleteMoneyCredit) + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyCredit moneyCredit)
    {
        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_id(), moneyCredit.get_id()))
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

//        _dataSet.get(index).get_moneyTransactionSum().change_sum(
//                _dataSet.get(index), _moneyAccountingSumIncrease,
//                EnumTypeCategory.Cost);
        _dataSet.remove(index);
        notifyItemRemoved(index);

        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

//    public Double getSum(int categoryId, MoneyAccountingSum moneyAccountingSumCost,
//                        MoneyAccountingSum moneyAccountingSumIncrease)
//    {
//        Double sum = 0d;
//
//        if (moneyAccountingSumIncrease != null &&
//                moneyAccountingSumIncrease.getTypeCategory() == EnumTypeCategory.Cost)
//            for (MoneyTransaction moneyTransaction: _dataSet)
//                sum += moneyTransaction.getSum();
//
//        if (moneyAccountingSumCost != null &&
//                moneyAccountingSumCost.getTypeCategory() == EnumTypeCategory.Increase)
//            for (MoneyTransaction moneyTransaction: _dataSet)
//                sum += moneyTransaction.getSum();
//
//        if (moneyAccountingSumCost != null &&
//                moneyAccountingSumCost.getTypeCategory() == EnumTypeCategory.Wallet)
//            for (MoneyTransaction moneyTransaction: _dataSet)
//                if (moneyTransaction.getCostCategoryId() == categoryId)
//                    sum -= moneyTransaction.getSum();
//                else
//                    sum += moneyTransaction.getSum();
//
//        if (moneyAccountingSumCost == null && moneyAccountingSumIncrease == null)
//            for (MoneyTransaction moneyTransaction: _dataSet)
//                if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Increase)
//                    sum += moneyTransaction.getSum();
//                else if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost)
//                    sum -= moneyTransaction.getSum();
//
//        return sum;
//    }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyCredit>
    {
        private final TextView textViewDateStart;
        private final TextView textViewDateEnd;
        private final TextView textViewName;
        private final TextView textViewTargetName;
        private final TextView textViewType;
        private final TextView textViewPercent;
        private final TextView textViewSum;
        private final TextView textViewStartSum;
        private final LinearLayout linearLayoutItem;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDateStart = itemView.findViewById(R.id.textViewDateStart);
            textViewDateEnd = itemView.findViewById(R.id.textViewDateEnd);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewTargetName = itemView.findViewById(R.id.textViewTargetName);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            textViewStartSum = itemView.findViewById(R.id.textViewStartSum);
            linearLayoutItem = itemView.findViewById(R.id.linear_layout_item);
        }

        private void setDateStart(Integer day, Integer month, Integer year)
        {
            if (textViewDateStart != null)
                CustomStaticClass.setDateView(_context, day, month, year, textViewDateStart);
        }
        private void setDateEnd(Integer day, Integer month, Integer year)
        {
            if (textViewDateEnd != null)
                CustomStaticClass.setDateView(_context, day, month, year, textViewDateEnd);
        }
        private void setName(String name) { textViewName.setText(name); }
        private void setTargetName(String targetName) { textViewTargetName.setText(targetName); }
        private void setType(EnumTypeCredit type)
        {
            switch (type)
            {
                case Annuitant:
                    textViewType.setText(R.string.TypeCreditAnnuitent);
                    break;
                case Different:
                    textViewType.setText(R.string.TypeCreditDifferent);
                    break;
                default:
            }
        }
        private void setPercent(double percent) { textViewPercent.setText(String.valueOf(percent)); }
        private void setStartSumLong(Long sumLong) { textViewStartSum.setText(String.valueOf(sumLong / 100d)); }
        private void setSumLong(Long sumLong) { textViewSum.setText(String.valueOf(sumLong / 100d)); }
        private void set_status(EnumCreditStatus creditStatus)
        {
            switch (creditStatus)
            {
                case Active:
                    linearLayoutItem.setBackgroundResource(R.color.white);
                    break;
                case Inactive:
                    linearLayoutItem.setBackgroundResource(R.color.lightGrey);
                    break;
                default:
            }
        }

        @Override
        public void setObject(MoneyCredit moneyCredit)
        {
            super.setObject(moneyCredit);

            setDateStart(moneyCredit.get_calendarStart_Date(),
                    moneyCredit.get_calendarStart_Month(),
                    moneyCredit.get_calendarStart_Year());
            setDateEnd(moneyCredit.get_calendarEnd_Date(),
                    moneyCredit.get_calendarEnd_Month(),
                    moneyCredit.get_calendarEnd_Year());
            setName(moneyCredit.get_name());
            setTargetName(moneyCredit.get_targetName());
            setType(moneyCredit.get_type());
            setPercent(moneyCredit.get_percent());
            setStartSumLong(moneyCredit.get_startSummLong());
            setSumLong(moneyCredit.get_sumLong());
            set_status(moneyCredit.get_status());
        }
    }
}
