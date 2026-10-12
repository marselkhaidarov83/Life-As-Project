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
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;

import java.util.List;

public class SpinnerMoneyTargetStatusAdapter extends ArrayAdapter<EnumStatus>
{
    private static final int _layoutId = R.layout.simple_spinner_status_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_status_item;
    public SpinnerMoneyTargetStatusAdapter(@NonNull Context context, List<EnumStatus> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumStatus enumStatus = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(enumStatus));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumStatus enumStatus = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(enumStatus));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTargetSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumStatus enumStatus)
    {
        switch (enumStatus)
        {
            case Blank:
                return getContext().getResources().getString(R.string.TargetCreated);
            case Started:
                return getContext().getResources().getString(R.string.TargetAtWork);
            case Finished:
                return getContext().getResources().getString(R.string.TargetAchieved);
            default:
                return getContext().getResources().getString(R.string.TargetIrrelevant);
        }
    }
}
