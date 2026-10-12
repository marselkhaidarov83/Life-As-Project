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

import com.MH83.LifeAsProject.model.Money.MoneyCategory;

import com.MH83.LifeAsProject.R;

import java.util.List;

public class SpinnerMoneyCategoryAdapter extends ArrayAdapter<MoneyCategory>
{
    private static final int _layoutId = R.layout.simple_spinner_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dd_item;
    public SpinnerMoneyCategoryAdapter(@NonNull Context context, List<MoneyCategory> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        MoneyCategory moneyCategory = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(moneyCategory.get_name());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        MoneyCategory moneyCategory = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(moneyCategory.get_name());

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyCategorySpinnerItemHeight));

        return convertView;
    }
}
