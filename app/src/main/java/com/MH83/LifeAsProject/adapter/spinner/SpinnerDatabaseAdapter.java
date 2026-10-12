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
import com.MH83.LifeAsProject.interfac.enums.EnumDatabase;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;

import java.util.List;

public class SpinnerDatabaseAdapter extends ArrayAdapter<EnumDatabase>
{
    private static final int _layoutId = R.layout.simple_spinner_typedata_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_typedata_item;
    public SpinnerDatabaseAdapter(@NonNull Context context, List<EnumDatabase> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumDatabase database = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(database));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumDatabase database = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(database));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumDatabase database)
    {
        if (database.equals(EnumDatabase.Work))
            return getContext().getResources().getString(R.string.DatabaseWork);
        else if (database.equals(EnumDatabase.Learn))
            return getContext().getResources().getString(R.string.DatabaseLearn);
        else
            return getContext().getResources().getString(R.string.DatabaseUnidentified);
    }
}
