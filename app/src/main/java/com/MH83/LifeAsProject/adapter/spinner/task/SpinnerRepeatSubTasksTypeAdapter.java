package com.MH83.LifeAsProject.adapter.spinner.task;

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
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatSubTasks;

import java.util.List;

public class SpinnerRepeatSubTasksTypeAdapter extends ArrayAdapter<EnumRepeatSubTasks>
{
    private static final int _layoutId = R.layout.simple_spinner_repeatsubtasks_item;
    private static final int _textViewId = android.R.id.text1;
    private static final int _layoutDropDownId = R.layout.simple_spinner_dropdown_repeatsubtasks_item;
    public SpinnerRepeatSubTasksTypeAdapter(@NonNull Context context, List<EnumRepeatSubTasks> list)
    {
        super(context, _layoutId, _textViewId, list);
        setDropDownViewResource(_layoutDropDownId);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumRepeatSubTasks nextTasksType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutId, null);

        ((TextView) convertView).setText(getName(nextTasksType));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        EnumRepeatSubTasks nextTasksType = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(_layoutDropDownId, null);

        ((TextView) convertView.findViewById(_textViewId)).setText(getName(nextTasksType));

        convertView.setMinimumHeight(
                (int) convertView.getContext().getResources().getDimension(R.dimen.MoneyTypeDataSpinnerItemHeight));

        return convertView;
    }

    private String getName(EnumRepeatSubTasks nextTasksType)
    {
        switch (nextTasksType)
        {
            case All:
                return getContext().getResources().getString(R.string.RepeatSubTasksAll);
            case OnlyNotConfirmed:
                return getContext().getResources().getString(R.string.RepeatSubTasksOnlyNotConfirmed);
            default:
                return getContext().getResources().getString(R.string.RepeatSubTasksUnidentified);
        }
    }
}
