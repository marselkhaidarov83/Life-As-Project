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
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryStatus;

import java.util.List;

public class SpinnerStatusCategoryAdapter extends ArrayAdapter<EnumCategoryStatus>
{
    private static final int _layoutId = R.layout.simple_spinner_creditstatus_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_creditstatus_item;
    public SpinnerStatusCategoryAdapter(@NonNull Context context, List<EnumCategoryStatus> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumCategoryStatus categoryStatus = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(categoryStatus));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumCategoryStatus categoryStatus = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(categoryStatus));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumCategoryStatus categoryStatus)
    {
        if (categoryStatus == EnumCategoryStatus.Active)
            return getContext().getResources().getString(R.string.CreditStatusActive);
        else if (categoryStatus == EnumCategoryStatus.Inactive)
            return getContext().getResources().getString(R.string.CreditStatusInactive);
        else
            return getContext().getResources().getString(R.string.CreditStatusUnidentified);
    }
}
