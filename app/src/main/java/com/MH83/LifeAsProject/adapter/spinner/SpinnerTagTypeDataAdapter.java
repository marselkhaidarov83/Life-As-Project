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
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;

import java.util.List;

public class SpinnerTagTypeDataAdapter extends ArrayAdapter<EnumTagTypeData>
{
    private static final int _layoutId = R.layout.simple_spinner_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dd_item;
    public SpinnerTagTypeDataAdapter(@NonNull Context context, List<EnumTagTypeData> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTagTypeData tagTypeData = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(tagTypeData));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTagTypeData tagTypeData = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(tagTypeData));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyPeriodDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumTagTypeData tagTypeData)
    {
        if (tagTypeData == EnumTagTypeData.PNL)
            return getContext().getResources().getString(R.string.TagTypeDataPNL);
        else
            return getContext().getResources().getString(R.string.TagTypeDataUnidentified);
    }
}
