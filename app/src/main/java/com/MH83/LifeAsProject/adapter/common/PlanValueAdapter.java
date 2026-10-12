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
import com.MH83.LifeAsProject.interfac.enums.EnumPlanValueType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;
import com.MH83.LifeAsProject.model.Custom.PlanValue;
import com.MH83.LifeAsProject.view.Custom.PlanValue.FragmentPlanValuePopupNew;

import java.util.ArrayList;
import java.util.Objects;

public class PlanValueAdapter
        extends CustomMoneyAdapter<PlanValue, PlanValueAdapter.ItemViewHolder,
        FragmentPlanValuePopupNew>
{
    Long _target_id;
    IFragmentViewCustom<MoneyTarget> _fragmentViewCustomTarget;
    EnumTypeAction _typeActionTarget;

    public PlanValueAdapter(Context context, ArrayList<PlanValue> dataSet,
                            EnumTypeAction typeActionTarget,
                            IFragmentViewCustom<MoneyTarget> fragmentViewCustomTarget,
                            IFragmentViewCustom<PlanValue> fragmentViewCustom,
                            Long target_id)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_planvalue);

        _fragmentViewCustomTarget = fragmentViewCustomTarget;
        _typeActionTarget = typeActionTarget;
        _target_id = target_id;
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
        PlanValue planValue = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = planValue;

            _fragmentPopupNew = new FragmentPlanValuePopupNew((AppCompatActivity) _context,
                    planValue,
                    _typeActionTarget,
                    EnumTypeAction.Update,
                    _fragmentViewCustomTarget,
                    _fragmentViewCustom,
                    _target_id,
                    _dataSet);
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

    public int AddItem(PlanValue planValue)
    {
        int insertPosition = _dataSet.size();
        _dataSet.add(insertPosition, planValue);
        notifyItemInserted(insertPosition);
        return insertPosition;
    }

    public int UpdateItem(PlanValue planValue)
    {
        Long id = planValue.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                _dataSet.set(i, planValue);
                notifyItemChanged(i);
                return i;
            }
        }

        return 0;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeletePlanValue(_currentItem.get_id()))
            {
                return DeleteFromDataset(_currentItem);
            }
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedPlanValueNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteMPlanValue) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(PlanValue planValue)
    {
        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(planValue.get_id(), _dataSet.get(i).get_id()))
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

    public static class ItemViewHolder extends CustomItemViewHolder<PlanValue>
    {
        private final TextView textViewName;
        private final TextView textViewKoef;
        private final TextView textViewValue;
        private final TextView textViewType;
        private final TextView textViewMeasure;
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewKoef = itemView.findViewById(R.id.textViewKoef);
            textViewValue = itemView.findViewById(R.id.textViewValue);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);
        }

        public Long getId() { return _object.get_id(); }
        private void setName(String name)
        {
            textViewName.setText(name);
        }
        private void setType(EnumPlanValueType type)
        {
            switch (type)
            {
                case Value:
                    textViewType.setText(_context.getString(R.string.Value));
                    break;
                case Sum:
                    textViewType.setText(_context.getString(R.string.EPVSum));
                    break;
                case Diff:
                    textViewType.setText(_context.getString(R.string.Diff));
                    break;
                case Koef:
                    textViewType.setText(_context.getString(R.string.Koef));
                    break;
                case InverseKoef:
                    textViewType.setText(_context.getString(R.string.InverseKoef));
                    break;
                case Multiplication:
                    textViewType.setText(_context.getString(R.string.Multiplication));
                    break;
                case Dividing:
                    textViewType.setText(_context.getString(R.string.Dividing));
                    break;
                default:
                    textViewType.setText("");
            }

        }
        private void setKoef(double koef)
        {
            if (koef == 0d)
                textViewKoef.setText("");
            else
                textViewKoef.setText(String.valueOf(koef));
        }
        private void setValue(double value)
        {
            textViewValue.setText(String.valueOf(value));
        }
        private void setMeasure(String measure)
        {
            textViewMeasure.setText(measure);
        }

        @Override
        public void setObject(PlanValue planValue)
        {
            super.setObject(planValue);

            setName(_object.get_name());
            setType(_object.get_type());
            setKoef(_object.get_coeff());
            setValue(_object.get_value());
            setMeasure(_object.get_measure());
        }
    }
}
