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
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCredit;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;

import java.util.List;

public class SpinnerTypeIncomingAdapter extends ArrayAdapter<EnumTypeIncoming>
{
    private static final int _layoutId = R.layout.simple_spinner_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dd_item;
    public SpinnerTypeIncomingAdapter(@NonNull Context context, List<EnumTypeIncoming> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTypeIncoming typeIncoming = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(typeIncoming));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTypeIncoming typeIncoming = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(typeIncoming));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumTypeIncoming typeIncoming)
    {
        if (typeIncoming == EnumTypeIncoming.Sum)
            return getContext().getResources().getString(R.string.TypeIncomingSum);
        else if (typeIncoming == EnumTypeIncoming.Percent)
            return getContext().getResources().getString(R.string.TypeIncomingPercent);
        else if (typeIncoming == EnumTypeIncoming.Accumulation)
            return getContext().getResources().getString(R.string.TypeIncomingAccumulation);
        else if (typeIncoming == EnumTypeIncoming.Credit)
            return getContext().getResources().getString(R.string.TypeIncomingCredit);
        else
            return getContext().getResources().getString(R.string.TypeIncomingUnidentified);
    }
}
