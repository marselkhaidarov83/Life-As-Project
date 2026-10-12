package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;

public class FragmentTasksTask
        extends FragmentTasksTaskCustom
        implements IFragmentSettings
{
    public FragmentTasksTask() { super(); }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_enumTaskSettings(EnumTaskSettings.Custom);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //region IFragmentSettings
    @Override
    public boolean ShowFormSettingButton() { return true; }

    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            TasksTaskSettings setting = globalVariable.get_tasksTaskSettingsHT();

            TasksTaskSettings newSettings = setting.Clone();

            new FragmentTasksTaskSettingsPopup(
                    (AppCompatActivity) getContext(),
                    newSettings,
                    this).PopupShow();
        };
    }
    //endregion
}
