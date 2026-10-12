package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyBoard;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyBoardAdapter
        extends CustomMoneyAdapter<MoneyBoard, MoneyBoardAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew> implements IAUDAdapter<MoneyBoard>
{
    private final int _heightBoardItem;
    GlobalClass globalVariable;

    public MoneyBoardAdapter(Context context, ArrayList<MoneyBoard> dataSet,
                             IFragmentViewCustom iFragmentViewCustom,
                             int heightBoardItem)
    {
        super(context, dataSet, iFragmentViewCustom, R.layout.item_money_board_transaction);
        _heightBoardItem = heightBoardItem;
        globalVariable = (GlobalClass) context.getApplicationContext();
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (_dataSet.get(position).get_enumBoard())
        {
            case TransactionBoard:
                return 1;
            case TransactionBoardQuarter:
                if (globalVariable.get_hasPaymentOrTestSubscription())
                    return 2;
                else
                    return 1002;
            case TransactionBoardYear:
                if (globalVariable.get_hasPaymentOrTestSubscription())
                    return 3;
                else
                    return 1003;
            case WalletBoard:
                if (globalVariable.get_hasPaymentOrTestSubscription())
                    return 10;
                else
                    return 1010;
            case GoalsAllBoard:
                if (globalVariable.get_hasPaymentOrTestSubscription())
                    return 20;
                else
                    return 1020;
            case GoalsForYearBoard:
                if (globalVariable.get_hasPaymentOrTestSubscription())
                    return 30;
                else
                    return 1030;
            case WalletOperBoard:
                if (globalVariable.get_hasPaymentOrTestSubscription())
                    return 40;
                else
                    return 1040;
        }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case 1:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_transaction, parent, false), _context);
            case 2:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_transaction_quarter, parent, false), _context);
            case 3:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_transaction_year, parent, false), _context);
            case 10:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_wallet, parent, false), _context);
            case 20:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_target, parent, false), _context);
            case 30:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_target_year, parent, false), _context);
            case 40:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_wallet_oper, parent, false), _context);
            case 1002:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_transaction_quarter_none, parent, false), _context);
            case 1003:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_transaction_year_none, parent, false), _context);
            case 1010:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_wallet_none, parent, false), _context);
            case 1020:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_target_none, parent, false), _context);
            case 1030:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_target_year_none, parent, false), _context);
            case 1040:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_money_board_wallet_oper_none, parent, false), _context);
        }

        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(_layoutId, parent, false), _context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);
        MoneyBoard moneyBoard = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            switch (moneyBoard.get_enumBoard())
            {
                case GoalsAllBoard:
                    if (globalVariable.get_hasPaymentOrTestSubscription())
                    {
                        globalVariable.set_moneyTargetDreamId(null);
                        globalVariable.set_moneyTargetTargetId(0L);
                        globalVariable.set_moneyTargetYear(null);
                        globalVariable.set_moneyTargetEnumTargetStatus4Settings(EnumTargetStatus4Settings.All);
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_target);
                    }
                    else
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_cabinet);
                    break;
                case GoalsForYearBoard:
                    if (globalVariable.get_hasPaymentOrTestSubscription())
                    {
                        Calendar calendar = Calendar.getInstance();

                        globalVariable.set_moneyTargetDreamId(null);
                        globalVariable.set_moneyTargetTargetId(0L);
                        globalVariable.set_moneyTargetYear(calendar.get(Calendar.YEAR));
                        globalVariable.set_moneyTargetEnumTargetStatus4Settings(EnumTargetStatus4Settings.All);
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_target);
                    }
                    else
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_cabinet);
                    break;
                case TransactionBoard:
                    if (globalVariable.get_hasPaymentOrTestSubscription())
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_accounting);
                    break;
                case WalletBoard:
                    if (globalVariable.get_hasPaymentOrTestSubscription())
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_accounting);
                    else
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_cabinet);
                    break;
                case WalletOperBoard:
                    if (globalVariable.get_hasPaymentOrTestSubscription())
                    {
                        globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_board);
                        globalVariable.set_moneyCategorySettingEnumTypeCategory(EnumTypeCategory.Cost);
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_category,
                                R.id.navigation_money_board);
                    }
                    else
                        ((MainActivity) _context).NavigationItemSelected(R.id.navigation_cabinet);
                    break;
            }
        });
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.canDelete(_context);
    }
    @Override
    public boolean canActionLeftImage() { return true; }

    public Long AddItem(MoneyBoard moneyBoard) { return 0L; }

    public Long UpdateItem(MoneyBoard moneyBoard) { return 0L; }
    @Override
    public boolean DeleteFromDataset(MoneyBoard moneyBoard) { return false;}

    public class ItemViewHolder extends CustomItemViewHolder<MoneyBoard>
    {
        private final ArrayList<TextView> textViewIntegers;
        private final MaterialCardView materialCardViewBoard;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);

            materialCardViewBoard = itemView.findViewById(R.id.materialCardViewBoard);
            textViewIntegers = new ArrayList<>();
            textViewIntegers.add(itemView.findViewById(R.id.textViewIntegers1));
            textViewIntegers.add(itemView.findViewById(R.id.textViewIntegers2));
            textViewIntegers.add(itemView.findViewById(R.id.textViewIntegers3));
        }

        private void setHeightItem()
        {
            materialCardViewBoard.getLayoutParams().height = _heightBoardItem;
        }

        @SuppressLint("SetTextI18n")
        private void setData(ArrayList<MoneyBoard.MoneyBoardData> data)
        {
            for (int i = 0; i < 3; i++)
                if (data != null && data.size() > i && textViewIntegers.get(i) != null)
                {
                    if (data.get(i).ShowAsRuble)
                        textViewIntegers.get(i).setText(
                                CustomStaticClass.getRubleSum(
                                        data.get(i).LongValue / 100d, true));
                    else
                        textViewIntegers.get(i).setText(data.get(i).toString());
                }
        }

        @Override
        public void setObject(MoneyBoard object)
        {
            super.setObject(object);

            setHeightItem();
            setData(object.get_Values());
        }
    }
}
