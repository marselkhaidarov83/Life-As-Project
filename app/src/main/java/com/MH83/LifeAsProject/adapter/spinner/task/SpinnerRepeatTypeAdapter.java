package com.MH83.LifeAsProject.adapter.spinner.task;

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
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;

import java.util.List;

public class SpinnerRepeatTypeAdapter extends ArrayAdapter<EnumRepeatType>
{
    private static final int _layoutId = R.layout.simple_spinner_repeattype_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_repeattype_item;
    public SpinnerRepeatTypeAdapter(@NonNull Context context, List<EnumRepeatType> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumRepeatType repeatType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(repeatType));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumRepeatType repeatType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(repeatType));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumRepeatType repeatType)
    {
        switch (repeatType)
        {
            case Day:
                return getContext().getResources().getString(R.string.RepeatTypeDay);
            case Week:
                return getContext().getResources().getString(R.string.RepeatTypeWeek);
            case Month:
                return getContext().getResources().getString(R.string.RepeatTypeMonth);
            case MonthEnd:
                return getContext().getResources().getString(R.string.RepeatTypeMonthEnd);
            case Year:
                return getContext().getResources().getString(R.string.RepeatTypeYear);
            case YearEnd:
                return getContext().getResources().getString(R.string.RepeatTypeYearEnd);
            default:
                return getContext().getResources().getString(R.string.TypeCreditUnidentified);
        }
    }
}
