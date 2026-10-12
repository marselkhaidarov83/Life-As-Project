package com.MH83.LifeAsProject.adapter.spinner.money.settings;

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
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

import java.util.List;

public class SpinnerSettingsMoneyTypeCategoryAdapter extends ArrayAdapter<EnumTypeCategory>
{
    private static final int _layoutId = R.layout.simple_spinner_sett_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_sett_dd_item;
    public SpinnerSettingsMoneyTypeCategoryAdapter(@NonNull Context context, List<EnumTypeCategory> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTypeCategory typeCategory = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(typeCategory));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTypeCategory typeCategory = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(typeCategory));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumTypeCategory typeCategory)
    {
        if (typeCategory.equals(EnumTypeCategory.All))
            return getContext().getResources().getString(R.string.TypeCategoryAll);
        else if (typeCategory.equals(EnumTypeCategory.Increase))
            return getContext().getResources().getString(R.string.TypeCategoryIncrease);
        else if (typeCategory.equals(EnumTypeCategory.Cost))
            return getContext().getResources().getString(R.string.TypeCategoryCost);
        else if (typeCategory.equals(EnumTypeCategory.Wallet))
            return getContext().getResources().getString(R.string.TypeCategoryWallet);
        else
            return getContext().getResources().getString(R.string.TypeCategoryUnidentified);
    }
}
