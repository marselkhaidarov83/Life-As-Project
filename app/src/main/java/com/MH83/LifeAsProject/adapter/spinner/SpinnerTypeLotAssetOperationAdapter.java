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
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;

import java.util.List;

public class SpinnerTypeLotAssetOperationAdapter extends ArrayAdapter<EnumLotAssetOperation>
{
    private static final int _layoutId = R.layout.simple_spinner_param_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_param_item;
    public SpinnerTypeLotAssetOperationAdapter(@NonNull Context context, List<EnumLotAssetOperation> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumLotAssetOperation lotAssetOperation = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(lotAssetOperation));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumLotAssetOperation lotAssetOperation = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(lotAssetOperation));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.ParamSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumLotAssetOperation lotAssetOperation)
    {
        if (lotAssetOperation == EnumLotAssetOperation.Buy)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Buy);
        else if (lotAssetOperation == EnumLotAssetOperation.Sell)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Sell);
        else if (lotAssetOperation == EnumLotAssetOperation.Dividend)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Dividend);
        else if (lotAssetOperation == EnumLotAssetOperation.Coupon)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Coupon);
        else if (lotAssetOperation == EnumLotAssetOperation.Revaluation)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Revaluation);
        else if (lotAssetOperation == EnumLotAssetOperation.WalletComission)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Comission);
        else if (lotAssetOperation == EnumLotAssetOperation.WalletIncome)
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Income);
        else
            return getContext().getResources().getString(R.string.Money_LotAssetOperation_Undentified);
    }
}
