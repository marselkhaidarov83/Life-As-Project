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
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;

import java.util.List;

public class SpinnerPeriodDataAdapter extends ArrayAdapter<EnumPeriodData>
{
    private static final int _layoutId = R.layout.simple_spinner_perioddata_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_typedata_item;
    public SpinnerPeriodDataAdapter(@NonNull Context context, List<EnumPeriodData> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumPeriodData periodData = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(periodData));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumPeriodData periodData = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(periodData));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyPeriodDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumPeriodData periodData)
    {
//        if (periodData == EnumPeriodData.Day)
//            return getContext().getResources().getString(R.string.PeriodDataDay);
        if (periodData == EnumPeriodData.Week)
            return getContext().getResources().getString(R.string.PeriodDataWeek);
        else if (periodData == EnumPeriodData.Month)
            return getContext().getResources().getString(R.string.PeriodDataMonth);
        else if (periodData == EnumPeriodData.Quarter)
            return getContext().getResources().getString(R.string.PeriodDataQuarter);
        else
            return getContext().getResources().getString(R.string.PeriodDataYear);
    }
}
