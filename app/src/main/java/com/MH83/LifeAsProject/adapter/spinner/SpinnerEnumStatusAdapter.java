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
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;

import java.util.List;

public class SpinnerEnumStatusAdapter extends ArrayAdapter<EnumStatus>
{
    private static final int _layoutId = R.layout.simple_spinner_tasks_project_status_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_tasks_project_status_item;
    public SpinnerEnumStatusAdapter(@NonNull Context context, List<EnumStatus> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumStatus status = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(status));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumStatus status = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(status));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumStatus status)
    {
        switch (status)
        {
            case Blank:
                return getContext().getResources().getString(R.string.Project_Status_Created);
            case Started:
                return getContext().getResources().getString(R.string.Project_Status_AtWork);
            case Finished:
                return getContext().getResources().getString(R.string.Project_Status_Achieved);
            case Irrelevant:
                return getContext().getResources().getString(R.string.Project_Status_Irrelevant);
            default:
                return getContext().getResources().getString(R.string.Project_Status_Unidentified);
        }
    }
}
