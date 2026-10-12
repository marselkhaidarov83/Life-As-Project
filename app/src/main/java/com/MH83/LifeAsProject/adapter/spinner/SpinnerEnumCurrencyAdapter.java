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
import com.MH83.LifeAsProject.interfac.enums.EnumCurrency;

import java.util.List;

public class SpinnerEnumCurrencyAdapter extends ArrayAdapter<EnumCurrency>
{
    private static final int _layoutId = R.layout.simple_spinner_tasks_project_status_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_tasks_project_status_item;
    public SpinnerEnumCurrencyAdapter(@NonNull Context context, List<EnumCurrency> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumCurrency currency = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(currency));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumCurrency currency = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(currency));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumCurrency currency)
    {
        switch (currency)
        {
            case RUB:
                return getContext().getResources().getString(R.string.Money_Currency_RUB);
            case USD:
                return getContext().getResources().getString(R.string.Money_Currency_USD);
            case CNY:
                return getContext().getResources().getString(R.string.Money_Currency_CNY);
            default:
                return getContext().getResources().getString(R.string.Project_Status_Unidentified);
        }
    }
}
