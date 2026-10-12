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
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.view.Money.MoneyCapital.FragmentMoneyLotAssetOperationPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyCredit.FragmentMoneyCreditPopupNew;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MoneyLotAssetOperationAdapter
        extends CustomMoneyAdapter<MoneyLotAssetOperation, MoneyLotAssetOperationAdapter.ItemViewHolder,
        FragmentMoneyCreditPopupNew>
{
    private final static int _layoutId = R.layout.item_money_lot_asset_operation;

    private EnumTypeAction _typeAction;
    private  IFragmentViewCustom _iFragmentViewCustom0;

    public MoneyLotAssetOperationAdapter(Context context, ArrayList<MoneyLotAssetOperation> dataSet,
                                         EnumTypeAction typeAction, IFragmentViewCustom fragmentViewCustom0,
                                         IFragmentViewCustom fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, _layoutId);

        _iFragmentViewCustom0 = fragmentViewCustom0;
        _typeAction = typeAction;
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
        MoneyLotAssetOperation moneyLotAssetOperation = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus) ||
                    moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                    moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus) ||
                    moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                return;

            if (moneyLotAssetOperation.get_moneyLotAsset().get_moneyAsset().get_wallet_id() != 0L &&
                !moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission) &&
                !moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                return;

            _currentItem = moneyLotAssetOperation;
            new FragmentMoneyLotAssetOperationPopupNew((AppCompatActivity) _context,
                    _currentItem,
                    _typeAction,
                    EnumTypeAction.Update,
                    _iFragmentViewCustom0,
                    _fragmentViewCustom,
                    false, null).PopupShow();
        });
    }

    @Override
    public boolean canActionLeftImage() { return false; }

    @Override
    public boolean canActionRightImage() { return false; }

    public boolean LeftButtonClick() { return true; }

    public boolean RightButtonClick() { return true; }

    public int AddItem(MoneyLotAssetOperation moneyLotAssetOperation)
    {
        int insertPosition = 0;

        for (int i = _dataSet.size() - 1; i >= 0; i--)
            if (moneyLotAssetOperation.get_calendar().getTime().getTime() < _dataSet.get(i).get_calendar().getTime().getTime())
            {
                insertPosition = i + 1;
                break;
            }

        _dataSet.add(insertPosition, moneyLotAssetOperation);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(MoneyLotAssetOperation moneyLotAssetOperation)
    {
        Long id = moneyLotAssetOperation.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                if (moneyLotAssetOperation.get_calendar().getTime() == _dataSet.get(i).get_calendar().getTime())
                {
                    _dataSet.set(i, moneyLotAssetOperation);

                    notifyItemChanged(i);
                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyLotAssetOperation);
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
                    _context.getString(R.string.errorDeletedMoneyLotAssetOperationNotFound),
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context,
                    _context.getString(R.string.errorDeleteMoneyLotAssetOperation) + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyLotAssetOperation moneyLotAssetOperation)
    {
        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_id(), moneyLotAssetOperation.get_id()))
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

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyLotAssetOperation>
    {
        private final TextView textViewDate;
        private final TextView textViewOperation;
        private final TextView textViewCount;
        private final TextView textViewCost;
        private final TextView textViewSum;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewOperation = itemView.findViewById(R.id.textViewOperation);
            textViewCount = itemView.findViewById(R.id.textViewCount);
            textViewCost = itemView.findViewById(R.id.textViewCost);
            textViewSum = itemView.findViewById(R.id.textViewSum);
        }

        private void set_date(Calendar calendar)
        {
            CustomStaticClass.setDateView(_context,
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    textViewDate);
        }
        private void set_operation(EnumLotAssetOperation lotAssetOperation)
        {
            if (lotAssetOperation == EnumLotAssetOperation.Create)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Create);
            else if (lotAssetOperation == EnumLotAssetOperation.Buy)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Buy);
            else if (lotAssetOperation == EnumLotAssetOperation.Sell)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Sell);
            else if (lotAssetOperation == EnumLotAssetOperation.Dividend)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Dividend);
            else if (lotAssetOperation == EnumLotAssetOperation.Coupon)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Coupon);
            else if (lotAssetOperation == EnumLotAssetOperation.Revaluation)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Revaluation);
            else if (lotAssetOperation == EnumLotAssetOperation.Finish)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Finish);
            else if (lotAssetOperation == EnumLotAssetOperation.PNLPlus)
                textViewOperation.setText(R.string.Money_LotAssetOperation_PNLPlus);
            else if (lotAssetOperation == EnumLotAssetOperation.PNLMinus)
                textViewOperation.setText(R.string.Money_LotAssetOperation_PNLMinus);
            else if (lotAssetOperation == EnumLotAssetOperation.CreditPlus)
                textViewOperation.setText(R.string.Money_LotAssetOperation_CreditPlus);
            else if (lotAssetOperation == EnumLotAssetOperation.CreditMinus)
                textViewOperation.setText(R.string.Money_LotAssetOperation_CreditMinus);
            else if (lotAssetOperation == EnumLotAssetOperation.WalletComission)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Comission);
            else if (lotAssetOperation == EnumLotAssetOperation.WalletIncome)
                textViewOperation.setText(R.string.Money_LotAssetOperation_Income);
            else
                textViewOperation.setText(R.string.Money_LotAssetOperation_Undentified);
        }
        private void set_countPrice(long count, long price)
        {
            textViewCount.setText(String.valueOf(count));
            if (price == 0L)
            {
                textViewCost.setText("");
                textViewSum.setText("");
            }
            else if (Math.floorMod(price, 100L) == 0)
            {
                textViewCost.setText(String.valueOf(Math.floorDiv(price, 100L)));
                textViewSum.setText(String.valueOf(Math.floorDiv(count * price, 100L)));
            }
            else
            {
                textViewCost.setText(String.valueOf(price / 100d));
                textViewSum.setText(String.valueOf(count * price / 100d));
            }
        }
        @Override
        public void setObject(MoneyLotAssetOperation moneyLotAssetOperation)
        {
            super.setObject(moneyLotAssetOperation);

            set_date(moneyLotAssetOperation.get_date());
            set_operation(moneyLotAssetOperation.get_lotAssetOperation());
            set_countPrice(moneyLotAssetOperation.get_count(),
                    moneyLotAssetOperation.get_price());
        }
    }
}
