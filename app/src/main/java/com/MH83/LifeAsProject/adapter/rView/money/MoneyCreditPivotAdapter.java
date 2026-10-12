package com.MH83.LifeAsProject.adapter.rView.money;

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
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyCreditPivotPoint;
import com.MH83.LifeAsProject.view.Money.MoneyCredit.FragmentMoneyCreditPivotPopupNew;

import java.util.ArrayList;
import java.util.Objects;

public class MoneyCreditPivotAdapter
        extends CustomMoneyAdapter<MoneyCreditPivotPoint, MoneyCreditPivotAdapter.ItemViewHolder,
        FragmentMoneyCreditPivotPopupNew>
{
    private final boolean _editable;
    public MoneyCreditPivotAdapter(Context context, ArrayList<MoneyCreditPivotPoint> dataSet,
                                   IFragmentViewCustom fragmentViewCustom,
                                   boolean editable)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_money_pivot_credit);
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
        MoneyCreditPivotPoint moneyCreditPivotPoint = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            if (!_editable)
                return;

            _currentItem = moneyCreditPivotPoint;
            _fragmentPopupNew = new FragmentMoneyCreditPivotPopupNew(
                    (AppCompatActivity) _context, moneyCreditPivotPoint, EnumTypeAction.Update,
                    _fragmentViewCustom);

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
        return _currentItem != null && _currentItem.canDelete(_context);
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

    public int AddItem(MoneyCreditPivotPoint moneyCreditPivotPoint)
    {
        int insertPosition = 0;

        for (int i = _dataSet.size() - 1; i >= 0 ; i--)
            if (moneyCreditPivotPoint.get_loanTerm() < _dataSet.get(i).get_loanTerm())
            {
                insertPosition = i + 1;
                break;
            }

        _dataSet.add(insertPosition, moneyCreditPivotPoint);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(MoneyCreditPivotPoint moneyCreditPivotPoint)
    {
        Long id = moneyCreditPivotPoint.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                if (moneyCreditPivotPoint.get_loanTerm() == _dataSet.get(i).get_loanTerm())
                {
                    _dataSet.set(i, moneyCreditPivotPoint);
                    notifyItemChanged(i);
                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyCreditPivotPoint);
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
                    _context.getString(R.string.errorDeletedMoneyPivotCreditNotFound),
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context,
                    _context.getString(R.string.errorDeleteMoneyPivotCredit) + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyCreditPivotPoint moneyCreditPivotPoint)
    {
        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (_dataSet.get(i).get_id().equals(moneyCreditPivotPoint.get_id()))
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

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyCreditPivotPoint>
    {
        private final TextView textViewStartSum;
        private final TextView textViewPercent;
        private final TextView textViewLoanTerm;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewStartSum = itemView.findViewById(R.id.textViewStartSum);
            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            textViewLoanTerm = itemView.findViewById(R.id.textViewLoanTerm);
        }

        @SuppressLint("SetTextI18n")
        private void setStartSumLong(Long sumLong)
        {
            if (textViewStartSum != null)
                textViewStartSum.setText(CustomStaticClass.getRubleSum(sumLong / 100d));
        }
        private void setPercent(double percent) { textViewPercent.setText(String.valueOf(percent)); }
        private void setLoanTerm(int loanTerm) { textViewLoanTerm.setText(String.valueOf(loanTerm)); }

        @Override
        public void setObject(MoneyCreditPivotPoint moneyCreditPivotPoint)
        {
            super.setObject(moneyCreditPivotPoint);

            setStartSumLong(moneyCreditPivotPoint.get_startSumLong());
            setPercent(moneyCreditPivotPoint.get_percent());
            setLoanTerm(moneyCreditPivotPoint.get_loanTerm());
        }
    }
}
