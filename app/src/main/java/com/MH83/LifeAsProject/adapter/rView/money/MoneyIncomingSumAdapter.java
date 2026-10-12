package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumDataOrSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccounting;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.MoneyIncomingSumTrio;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class MoneyIncomingSumAdapter
        extends CustomMoneyAdapter<MoneyAccountingSum, MoneyIncomingSumAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew>
{
    private final MoneyIncomingSumTrio _moneyIncomingSumTrio;
    private final Calendar _dateStart;
    private final Calendar _dateEnd;
    private final Hashtable<Long, Icon> _icons;
    public MoneyIncomingSumAdapter(Context context, ArrayList<MoneyAccountingSum> dataSet,
                                   IFragmentViewCustom iFragmentViewCustom,
                                   MoneyIncomingSumTrio moneyIncomingSumTrio,
                                   Calendar dateStart, Calendar dateEnd)
    {
        super(context, dataSet, iFragmentViewCustom, R.layout.item_money_accounting);
        _moneyIncomingSumTrio = moneyIncomingSumTrio;
        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _icons = IconDataSource.GetHashtable(EnumTypeCategory.Increase);
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
        holder.setIcons(_icons);
        int pos = position;
        super.onBindViewHolder(holder, position);
        //MoneyAccountingSum moneyAccountingSum = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Data))
            {
                globalVar.set_pnl_transactionType(EnumTransactionType.PNL);
                globalVar.get_moneyTransactionSettings().set_typeCategory(EnumTypeCategory.Increase);
                ((FragmentMoneyAccounting) _fragmentViewCustom).GoToFragmentMoneyPNL(
                        _dataSet.get(pos).getCategoryId(), _dataSet.get(pos), null);
            }
            else
            {
                globalVar.set_moneyCategorySettings_fromItem(R.id.navigation_money_accounting);
                globalVar.set_moneyCategorySettingEnumTypeCategory(EnumTypeCategory.Increase);
                ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_category,
                        R.id.navigation_money_pnl);
            }
        });

        /*holder.itemView.setOnTouchListener((view, motionEvent) ->
        {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            {
                _moneyIncomingSumTrio.SetIncomMoneyAccountingSum(_dataSet.get(pos));
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            }

            return false;
        });*/

        holder.itemView.setOnLongClickListener(view ->
        {
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Setting))
                return false;

            GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            if (globalVariable.get_hasUnpaidSubscription())
            {
                Calendar calendarThisMonthBegin = Calendar.getInstance();
                calendarThisMonthBegin.set(Calendar.DATE, 1);
                calendarThisMonthBegin.set(Calendar.HOUR, 0);
                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
                calendarThisMonthBegin.set(Calendar.SECOND, 0);
                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);

                Calendar calendarThisMonthEnd = Calendar.getInstance();
                calendarThisMonthBegin.set(Calendar.DATE, 1);
                calendarThisMonthBegin.set(Calendar.HOUR, 0);
                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
                calendarThisMonthBegin.set(Calendar.SECOND, 0);
                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
                calendarThisMonthEnd.add(Calendar.MONTH, 1);
                calendarThisMonthEnd.add(Calendar.SECOND, -1);

                if (!(_dateStart.getTime().getTime() >= calendarThisMonthBegin.getTime().getTime() &&
                        _dateStart.getTime().getTime() <= calendarThisMonthEnd.getTime().getTime()) ||
                        !(_dateEnd.getTime().getTime() >= calendarThisMonthBegin.getTime().getTime() &&
                                _dateEnd.getTime().getTime() <= calendarThisMonthEnd.getTime().getTime()))
                    return false;
            }

            _moneyIncomingSumTrio.SetIncomMoneyAccountingSum(_dataSet.get(pos));
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(data, shadowBuilder, view, 0);
            return true;
        });
    }

    public boolean LeftButtonClick()
    {
        //((FragmentMoneyIncreaseTable) _fragmentViewCustom).GoToFragmentMoneyIncoming();
        return true;
    }

    public boolean RightButtonClick()
    {
        //return DeleteCurrent();
        return false;
    }

    public void AddSubItem(MoneyPNLIndependent moneyPNL)
    {
        if (moneyPNL.get_typeCategory() != EnumTypeCategory.Increase)
            return;

        if (_dateStart.getTime().getTime() > moneyPNL.getDate().getTime())
            return;

        if (_dateEnd.getTime().getTime() < moneyPNL.getDate().getTime())
            return;

        for (int i = 0; i < _dataSet.size(); i++)
            if (_dataSet.get(i).getCategoryId() == moneyPNL.getCostCategoryId())
            {
//                _dataSet.get(i).setSum(_dataSet.get(i).getSum() + moneyPNL.get_totalSum());
                _dataSet.get(i).set_sumLong(_dataSet.get(i).get_sumLong() + moneyPNL.get_totalSumLong());
                notifyItemChanged(i);
                break;
            }
    }

    public int AddItem(MoneyAccountingSum moneyAccountingSum)
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

    public int UpdateItem(MoneyAccountingSum moneyAccountingSum)
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

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

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

    public boolean DeleteFromDataset(MoneyAccountingSum moneyAccountingSum) throws Exception
    {
        return false;
    }

//    public Double GetSum()
//    {
//        Double sum = 0d;
//        for (MoneyAccountingSum moneyAccountingSum: _dataSet)
//            sum += moneyAccountingSum.getSum();
//        return sum;
//    }

    public Long GetSumLong()
    {
        Long sumLong = 0L;
        for (MoneyAccountingSum moneyAccountingSum: _dataSet)
            sumLong += moneyAccountingSum.get_sumLong();
        return sumLong;
    }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyAccountingSum>
    {
        private final TextView textViewName;
        private final TextView textViewSum;
        private final TextView textViewBudget;
        private final ImageView imageViewIcon;
        final LinearLayout linearLayoutItem;
        private Hashtable<Long, Icon> _icons = new Hashtable<>();
        public void setIcons(Hashtable<Long, Icon> icons) { _icons = icons; }
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            textViewBudget = itemView.findViewById(R.id.textViewBudget);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            linearLayoutItem = itemView.findViewById(R.id.linear_layout_item);
        }

        public void setName(EnumDataOrSetting dataOrSetting, String name)
        {
            textViewName.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? name : "");
        }
//        public void setSum(EnumDataOrSetting dataOrSetting, Double sum)
//        {
//            textViewSum.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? CustomStaticClass.getRubleSum(sum) : "");
//        }
        public void setSumLong(EnumDataOrSetting dataOrSetting, Long sumLong)
        {
            textViewSum.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? CustomStaticClass.getRubleSum(sumLong / 100d) : "");
        }
        public void setBudget()
        {
            textViewBudget.getLayoutParams().height = 0;
        }
        public void setImage(EnumDataOrSetting dataOrSetting, Long icon)
        {
            if (dataOrSetting.equals(EnumDataOrSetting.Setting))
                imageViewIcon.setImageResource(R.drawable.icons_settings_purple_100);
            else if (icon != 0)
            {
                try { imageViewIcon.setImageResource(_icons.get(icon).get_icon_id()); }
                catch (Exception e) { }
            }
        }

        @Override
        public void setObject(MoneyAccountingSum moneyAccounting)
        {
            super.setObject(moneyAccounting);
            setName(moneyAccounting.get_dataOrSetting(), moneyAccounting.getCategoryName());
//            setSum(moneyAccounting.get_dataOrSetting(), moneyAccounting.getSum());
            setSumLong(moneyAccounting.get_dataOrSetting(), moneyAccounting.get_sumLong());
            setBudget();
            setImage(moneyAccounting.get_dataOrSetting(), moneyAccounting.get_icon());
        }
    }
}
