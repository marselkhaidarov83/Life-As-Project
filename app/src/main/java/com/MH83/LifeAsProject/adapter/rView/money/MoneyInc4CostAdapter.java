package com.MH83.LifeAsProject.adapter.rView.money;

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
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.Money.MoneyAccountingDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumIndependent;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.view.Money.MoneyInc4Cost.FragmentMoneyInc4CostPopupNew;

import java.util.ArrayList;
import java.util.Objects;

public class MoneyInc4CostAdapter
        extends CustomMoneyAdapter<MoneyInc4Cost, MoneyInc4CostAdapter.ItemViewHolder,
        FragmentMoneyInc4CostPopupNew>
{
    IFragmentViewCustom<MoneyPNL> fragmentViewCustomPNL;
    EnumTypeAction _typeActionPNL;

    public MoneyInc4CostAdapter(Context context, ArrayList<MoneyInc4Cost> dataSet,
                                EnumTypeAction typeActionPNL,
                                IFragmentViewCustom<MoneyPNL> fragmentViewCustom,
                                IFragmentViewCustom<MoneyInc4Cost> fragmentViewCustom2)
    {
        super(context, dataSet, fragmentViewCustom2, R.layout.item_money_inc4cost);

        fragmentViewCustomPNL = fragmentViewCustom;
        _typeActionPNL = typeActionPNL;
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
        MoneyInc4Cost moneyInc4Cost = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = moneyInc4Cost;

            MoneyAccountingSum moneyAccountingSumCost =
                    MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                            moneyInc4Cost.getIncreaseCategoryId());

            _fragmentPopupNew =
                    new FragmentMoneyInc4CostPopupNew((AppCompatActivity) _context,
                            moneyInc4Cost,
                            _typeActionPNL,
                            EnumTypeAction.Update,
                            fragmentViewCustomPNL,
                            _fragmentViewCustom,
                            null,
                            moneyAccountingSumCost,
                            moneyInc4Cost.getCalendar(),
                            moneyInc4Cost.getCalendar(),
                            EnumIndependent.Dependent);

            _fragmentPopupNew.PopupShow();
        });
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.canDelete(_context);
    }
    @Override
    public boolean canActionLeftImage() { return true; }

    public boolean LeftButtonClick() { return false; }

    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public int AddItem(MoneyInc4Cost moneyInc4Cost)
    {
        int insertPosition = _dataSet.size();
        _dataSet.add(insertPosition, moneyInc4Cost);
        notifyItemInserted(insertPosition);
        return insertPosition;
    }

    public int UpdateItem(MoneyInc4Cost moneyInc4Cost)
    {
        Long id = moneyInc4Cost.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                _dataSet.set(i, moneyInc4Cost);
                notifyItemChanged(i);
                return i;
            }
        }

        return 0;
    }

    private boolean DeleteCurrent()
    {
//        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
//        {
//            if (dbHelper.DeleteMoneyInc4Cost(_currentItem.get_id()))
//            {
//                return DeleteFromDataset(_currentItem);
//            }
//            else
//                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyInc4CostNotFound), Toast.LENGTH_SHORT).show();
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyInc4Cost) + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyInc4Cost moneyInc4Cost)
    {
        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(moneyInc4Cost.get_id(), _dataSet.get(i).get_id()))
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

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyInc4Cost>
    {
        private final TextView textViewDate;
        private final TextView textViewCategory;
        private final TextView textViewSum;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewSum = itemView.findViewById(R.id.textViewSum);
        }

        public Long getId() { return _object.get_id(); }
        public void setDate(Integer day, Integer month, Integer year)
        {
            if (textViewDate != null)
                CustomStaticClass.setDateView(_context, day,
                        month, year, textViewDate);
        }

        private void setCategory(String category)
        {
            textViewCategory.setText(category);
        }
        private void setSum(String sum)
        {
            textViewSum.setText(sum);
        }

        @Override
        public void setObject(MoneyInc4Cost moneyInc4Cost)
        {
            super.setObject(moneyInc4Cost);

            setDate(_object.getDay(), _object.getMonth(), _object.getYear());
            setCategory(_object.getCategoryName());
            setSum(_object.getSValue());
        }
    }
}
