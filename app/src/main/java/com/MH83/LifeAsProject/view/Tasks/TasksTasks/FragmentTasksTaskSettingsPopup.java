package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.task.settings.SpinnerSettingsConfirmedTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.settings.SpinnerSettingsTasksProjectAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.settings.SpinnerSettingsTasksPriorityAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.settings.SpinnerSettingsTasksSortingAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Tasks.TasksPriorityDataSource;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksPriority;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentTasksTaskSettingsPopup extends CustomPopupBottomFragment<TasksTaskSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_tasks_task;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private Spinner _spinnerConfirmedType;
    private Spinner _spinnerGroupOfProject;
    private Spinner _spinnerProject;
    private Spinner _spinnerPriority;
    private Spinner _spinnerSorting;
    //endregion

    //region Start Fragment
    public FragmentTasksTaskSettingsPopup()
    {
        super(_layoutId,
                R.string.SettingsTasksTask,
                R.string.SettingsTasksTask,
                0);
    }

    public FragmentTasksTaskSettingsPopup(AppCompatActivity activity,
                                          TasksTaskSettings tasksTaskSettings,
                                          IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                tasksTaskSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsTasksTask,
                R.string.SettingsTasksTask,
                0,
                iFragmentViewCustom,
                null,
                null);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.Tasks_Task_Settings_Width) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    }

    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerConfirmedType = view.findViewById(R.id.spinnerConfirmedType);
        _spinnerGroupOfProject = view.findViewById(R.id.spinnerGroupOfProjectProject);
        _spinnerProject = view.findViewById(R.id.spinnerProject);
        _spinnerPriority = view.findViewById(R.id.spinnerPriority);
        _spinnerSorting = view.findViewById(R.id.spinnerSorting);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) view.getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            _spinnerGroupOfProject.setEnabled(false);
            _spinnerProject.setEnabled(false);
            _spinnerConfirmedType.setEnabled(false);
            _spinnerSorting.setEnabled(false);
            _spinnerPriority.setEnabled(false);
        }
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        List<Boolean> list = new ArrayList<>();
        list.add(false);
        list.add(null);
        list.add(true);
        SpinnerSettingsConfirmedTypeAdapter adapter = new SpinnerSettingsConfirmedTypeAdapter(getContext(), list);
        _spinnerConfirmedType.setAdapter(adapter);

        List<TasksProject> list0 = TasksProjectDataSource.LoadDataParent(
                _activity.getBaseContext(), true,
                new TaskProjectSettings(EnumTargetStatus4Settings.All, null));
        SpinnerSettingsTasksProjectAdapter adapter0 = new SpinnerSettingsTasksProjectAdapter(getContext(), list0);
        _spinnerGroupOfProject.setAdapter(adapter0);

        SetProjectAdapter(get_groupOfProject());

        List<TasksPriority> list2 = TasksPriorityDataSource.LoadData(_activity.getBaseContext());
        SpinnerSettingsTasksPriorityAdapter adapter2 = new SpinnerSettingsTasksPriorityAdapter(getContext(), list2);
        _spinnerPriority.setAdapter(adapter2);

        List<EnumTaskSorting> list3 = new ArrayList<>();
        list3.add(EnumTaskSorting.Date_Priority_Name);
        list3.add(EnumTaskSorting.Priority_Date_Name);
        list3.add(EnumTaskSorting.Name);
        SpinnerSettingsTasksSortingAdapter adapter3 = new SpinnerSettingsTasksSortingAdapter(getContext(), list3);
        _spinnerSorting.setAdapter(adapter3);
    }

    private void SetProjectAdapter(Long groupProjectId)
    {
        List<TasksProject> list1 = TasksProjectDataSource.LoadDataChildren(
                _activity.getBaseContext(),
                groupProjectId,
                true,
                new TaskProjectSettings(EnumTargetStatus4Settings.All, null));
        SpinnerSettingsTasksProjectAdapter adapter1 = new SpinnerSettingsTasksProjectAdapter(getContext(), list1);
        _spinnerProject.setAdapter(adapter1);
    }

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _spinnerGroupOfProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Long projectId = get_project();
                SetProjectAdapter(get_groupOfProject());
                set_project(projectId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        set_confirmedType(_data.get_confirmedType());
        set_groupOfProject(_data.get_groupOfProjectId());
        set_project(_data.get_projectId());
        set_priority(_data.get_priority());
        set_taskSorting(_data.get_taskSorting());
    }

    private void set_confirmedType(Boolean confirmedType)
    {
        for (int i = 0; i < _spinnerConfirmedType.getAdapter().getCount(); i++)
            if (_spinnerConfirmedType.getAdapter().getItem(i) == confirmedType)
            {
                _spinnerConfirmedType.setSelection(i);
                break;
            }
    }

    private void set_groupOfProject(Long projectId)
    {
        if (projectId == null)
            return;

        for (int i = 0; i < _spinnerGroupOfProject.getAdapter().getCount(); i++)
            if (Objects.equals(((TasksProject) _spinnerGroupOfProject.getAdapter().getItem(i)).get_id(), projectId))
            {
                _spinnerGroupOfProject.setSelection(i);
                break;
            }
    }

    private void set_project(Long projectId)
    {
        if (projectId == null)
            return;

        for (int i = 0; i < _spinnerProject.getAdapter().getCount(); i++)
            if (Objects.equals(((TasksProject) _spinnerProject.getAdapter().getItem(i)).get_id(), projectId))
            {
                _spinnerProject.setSelection(i);
                break;
            }
    }

    private void set_priority(Long priority)
    {
        if (priority == null)
            return;

        for (int i = 0; i < _spinnerPriority.getAdapter().getCount(); i++)
            if (Objects.equals(((TasksPriority) _spinnerPriority.getAdapter().getItem(i)).get_id(), priority))
            {
                _spinnerPriority.setSelection(i);
                break;
            }
    }

    private void set_taskSorting(EnumTaskSorting taskSorting)
    {
        for (int i = 0; i < _spinnerSorting.getAdapter().getCount(); i++)
            if (_spinnerSorting.getAdapter().getItem(i).equals(taskSorting))
            {
                _spinnerSorting.setSelection(i);
                break;
            }
    }
    //endregion

    //region Check Params
    @Override
    protected boolean CheckFormValues()
    {
        return true;
    }
    //endregion

    //region Get Params
    private CustomPeriodClass get_customPeriodClass()
    {
        return _data.get_customPeriodClass();
    }

    private Boolean get_confirmedType()
    {
        return (Boolean) _spinnerConfirmedType.getSelectedItem();
    }

    private Long get_groupOfProject()
    {
        Long projectID = ((TasksProject) _spinnerGroupOfProject.getSelectedItem()).get_id();
        return projectID == 0 ? null : projectID;
    }

    private Long get_project()
    {
        Long projectID = ((TasksProject) _spinnerProject.getSelectedItem()).get_id();
        return projectID == 0 ? null : projectID;
    }

    private Long get_priority()
    {
        return ((TasksPriority) _spinnerPriority.getSelectedItem()).get_id();
    }

    private EnumTaskSorting get_taskSorting()
    {
        return (EnumTaskSorting) _spinnerSorting.getSelectedItem();
    }

    private boolean get_showSpinnerDate()
    {
        return _data.get_showSpinnerDate();
    }
    //endregion

    //region Save Params
    @Override
    protected TasksTaskSettings GetDataFromForm()
    {
        return new TasksTaskSettings(
                get_customPeriodClass(),
                get_confirmedType(),
                get_groupOfProject(),
                get_project(),
                get_priority(),
                get_taskSorting(),
                get_showSpinnerDate());
    }
    //endregion
}
