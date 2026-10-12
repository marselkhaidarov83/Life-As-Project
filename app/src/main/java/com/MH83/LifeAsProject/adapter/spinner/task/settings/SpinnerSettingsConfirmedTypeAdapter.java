package com.MH83.LifeAsProject.adapter.spinner.task.settings;

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

import java.util.List;

public class SpinnerSettingsConfirmedTypeAdapter extends ArrayAdapter<Boolean>
{

    private static final int _layoutId = R.layout.simple_spinner_sett_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_sett_dd_item;
    public SpinnerSettingsConfirmedTypeAdapter(@NonNull Context context, List<Boolean> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Boolean b = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(b));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Boolean b = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(b));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.EnumConfirmedSpinnerItemHeight));

        return convertView;
    }

    private String getName(Boolean b)
    {
        if (b == null)
            return getContext().getResources().getString(R.string.ConfirmedType_Any);
        else if (b)
            return getContext().getResources().getString(R.string.ConfirmedType_True);
        else
            return getContext().getResources().getString(R.string.ConfirmedType_False);
    }
}
