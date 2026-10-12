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

import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.R;

import java.util.ArrayList;

public class SpinnerPeriodAdapter extends ArrayAdapter<CustomPeriodClass>
{
    private static final int _layoutId = R.layout.simple_spinner_period_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_period_item;
    public SpinnerPeriodAdapter(@NonNull Context context, ArrayList<CustomPeriodClass> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        CustomPeriodClass customPeriodClass = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        customPeriodClass.get_name4View(getContext(), ((TextView) convertView));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        CustomPeriodClass customPeriodClass = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(customPeriodClass.get_name());

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyAccountingSpinnerItemHeight));

        return convertView;
    }
}
