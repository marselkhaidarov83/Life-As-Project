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
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;

import java.util.List;

public class SpinnerSettingsMoneyTypeDataAdapter extends ArrayAdapter<EnumTypeData>
{
    private static final int _layoutId = R.layout.simple_spinner_sett_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_sett_dd_item;
    public SpinnerSettingsMoneyTypeDataAdapter(@NonNull Context context, List<EnumTypeData> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTypeData typeData = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(typeData));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTypeData typeData = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(typeData));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumTypeData typeData)
    {
        if (typeData == EnumTypeData.Plan)
            return getContext().getResources().getString(R.string.TypeDataPlanned);
        else if (typeData == EnumTypeData.Fact)
            return getContext().getResources().getString(R.string.TypeDataActual);
        else
            return getContext().getResources().getString(R.string.TypeDataUnidentified);
    }
}
