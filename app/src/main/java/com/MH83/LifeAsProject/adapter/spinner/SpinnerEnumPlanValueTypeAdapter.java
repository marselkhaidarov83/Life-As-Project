package com.MH83.LifeAsProject.adapter.spinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.enums.EnumPlanValueType;
import com.MH83.LifeAsProject.model.Custom.PlanValue;

import java.util.List;

public class SpinnerEnumPlanValueTypeAdapter extends ArrayAdapter<EnumPlanValueType>
{
    private static final int _layoutId = R.layout.simple_spinner_epvtype_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_epvtype_item;
    public SpinnerEnumPlanValueTypeAdapter(@NonNull Context context, List<EnumPlanValueType> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumPlanValueType type = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(PlanValue.get_typeNameC(getContext(), type));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumPlanValueType type = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(PlanValue.get_typeNameC(getContext(), type));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.PlanValueSpinnerItemHeight));

        return convertView;
    }
}
