package com.MH83.LifeAsProject.adapter.spinner.money;

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
import com.MH83.LifeAsProject.interfac.enums.EnumAccumulationType;

import java.util.List;

public class SpinnerMoneyAccumulationTypeAdapter extends ArrayAdapter<EnumAccumulationType>
{
    private static final int _layoutId = R.layout.simple_spinner_accumulationtype_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_accumulationtype_item;
    public SpinnerMoneyAccumulationTypeAdapter(@NonNull Context context, List<EnumAccumulationType> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumAccumulationType enumAccumulationType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(enumAccumulationType));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumAccumulationType enumAccumulationType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(enumAccumulationType));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTargetSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumAccumulationType enumAccumulationType)
    {
        switch (enumAccumulationType)
        {
            case Linear:
                return getContext().getResources().getString(R.string.AccumulationTypeLinear);
            case AlgorithmicProgression:
                return getContext().getResources().getString(R.string.AccumulationTypeAlgorithmicProgression);
            case GeometricProgression:
                return getContext().getResources().getString(R.string.AccumulationTypeGeometricProgression);
            default:
                return getContext().getResources().getString(R.string.AccumulationTypeIrrelevant);
        }
    }
}
