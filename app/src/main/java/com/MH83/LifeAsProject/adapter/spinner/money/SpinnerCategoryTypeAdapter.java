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
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;

import java.util.List;

public class SpinnerCategoryTypeAdapter extends ArrayAdapter<EnumCategoryType>
{
    private static final int _layoutId = R.layout.simple_spinner_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dd_item;
    public SpinnerCategoryTypeAdapter(@NonNull Context context, List<EnumCategoryType> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumCategoryType categoryType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(categoryType));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumCategoryType categoryType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(categoryType));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumCategoryType categoryType)
    {
        if (categoryType == EnumCategoryType.Base)
            return getContext().getResources().getString(R.string.CategoryTypeBase);
        else if (categoryType == EnumCategoryType.Advanced)
            return getContext().getResources().getString(R.string.CategoryTypeAdvanced);
        else if (categoryType == EnumCategoryType.AdvancedPlus)
            return getContext().getResources().getString(R.string.CategoryTypeAdvancedPlus);
        else if (categoryType == EnumCategoryType.Project)
            return getContext().getResources().getString(R.string.CategoryTypeProject);
        else if (categoryType == EnumCategoryType.ProjectPlus)
            return getContext().getResources().getString(R.string.CategoryTypeProjectPlus);
        else if (categoryType == EnumCategoryType.WalletWallet)
            return getContext().getResources().getString(R.string.CategoryTypeWallet);
        else if (categoryType == EnumCategoryType.WalletAccumulation)
            return getContext().getResources().getString(R.string.CategoryTypeAccumulation);
        else if (categoryType == EnumCategoryType.WalletAccumulationPlus)
            return getContext().getResources().getString(R.string.CategoryTypeAccumulationPlus);
        else if (categoryType == EnumCategoryType.All)
            return getContext().getResources().getString(R.string.CategoryTypeAll);
        else
            return getContext().getResources().getString(R.string.CategoryTypeUnidentified);
    }
}
