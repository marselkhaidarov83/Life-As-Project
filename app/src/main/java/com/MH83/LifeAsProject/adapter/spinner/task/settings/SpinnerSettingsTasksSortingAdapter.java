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
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;

import java.util.List;

public class SpinnerSettingsTasksSortingAdapter extends ArrayAdapter<EnumTaskSorting>
{
    private static final int _layoutId = R.layout.simple_spinner_sett_1v_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_sett_dd_item;

    public SpinnerSettingsTasksSortingAdapter(@NonNull Context context, List<EnumTaskSorting> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTaskSorting taskSorting = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(get_name(taskSorting));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumTaskSorting taskSorting = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(get_name(taskSorting));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(
                        R.dimen.Tasks_Sorting_Spinner_Item_Height));

        return convertView;
    }

    private String get_name(EnumTaskSorting taskSorting)
    {
        switch (taskSorting)
        {
            case Date_Priority_Name:
                return getContext().getResources().getString(R.string.Tasks_Task_Sorting_Date_Priority_Name);
            case Priority_Date_Name:
                return getContext().getResources().getString(R.string.Tasks_Task_Sorting_Priority_Date_Name);
            default:
                return getContext().getResources().getString(R.string.Tasks_Task_Sorting_Name);
        }
    }
}
