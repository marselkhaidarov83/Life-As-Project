package com.MH83.LifeAsProject.view.Tasks.TasksProject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerProjectStatusAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerTasksProjectAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentTasksProjectPopupNew extends CustomPopupBottomFragment<TasksProject>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_tasks_project_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    private Spinner _spinnerParent;
    private Spinner _spinnerStatus;
    private EditText _editTextName;
    private EditText _editTextComment;
    //endregion

    //region Constructors
    public FragmentTasksProjectPopupNew()
    {
        super(_layoutId,
                R.string.PopupTasksProjectAdd,
                R.string.PopupTasksProjectUpdate,
                R.string.errorUpdateTasksProject);
    }

    public FragmentTasksProjectPopupNew(AppCompatActivity activity,
                                        TasksProject data,
                                        EnumTypeAction typeAction,
                                        IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.PopupTasksProjectAdd,
                R.string.PopupTasksProjectUpdate,
                R.string.errorUpdateTasksProject,
                iFragmentViewCustom,
                null,
                null);

//        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        _typeCategory = globalVariable.get_typeCategory();
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerParent = view.findViewById(R.id.spinnerParent);
        _spinnerStatus = view.findViewById(R.id.spinnerStatus);
        _editTextName = view.findViewById(R.id.editTextName);
        _editTextComment = view.findViewById(R.id.editTextComment);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextName;
        _focusedViewToUpdate = _editTextName;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        SetEnabled();
    }

    private void SetEnabled()
    {
        if (_data != null && _data.get_target_id() != 0)
            {
                _spinnerParent.setEnabled(false);
                _spinnerStatus.setEnabled(false);
                _editTextName.setEnabled(false);
            }
        else if (_data != null && _data.get_hasChild())
            _spinnerParent.setEnabled(false);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillSpinnerParent();
        FillSpinnerStatus();
    }

    private void FillSpinnerParent()
    {
        GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        List<TasksProject> list =
                TasksProjectDataSource.LoadDataParent(_activity.getApplicationContext(),
                        true, globalVariable.get_taskProjectSettings());
        if (_data != null && _data.get_id() != 0)
            for (TasksProject item : list)
                if (item.get_id() == _data.get_id())
                {
                    list.remove(item);
                    break;
                }

        SpinnerTasksProjectAdapter adapter = new SpinnerTasksProjectAdapter(getContext(), list);
        _spinnerParent.setAdapter(adapter);
    }

    private void FillSpinnerStatus()
    {
        ArrayList<EnumStatus> list = new ArrayList<>();
        list.add(EnumStatus.Blank);
        list.add(EnumStatus.Started);
        list.add(EnumStatus.Finished);
        list.add(EnumStatus.Irrelevant);

        SpinnerProjectStatusAdapter adapter = new SpinnerProjectStatusAdapter(getContext(), list);
        _spinnerStatus.setAdapter(adapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width),
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }
    //endregion

    //region Set Listeners
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {} );
    }

    protected void SetValues(Object[] objects)
    {
        if (_data != null)
        {
            set_Parent(_data.get_parentId());
            set_Status(_data.get_status());
            set_Name(_data.get_name());
            set_Comment(_data.get_comment());
//            set_hasChild(_data.get_hasChild());
        }
        else
        {
            set_Parent(0L);
            set_Status(EnumStatus.Blank);
            set_Name("");
            set_Comment("");
//            set_hasChild(false);
        }
    }

    private void set_Parent(Long parentId)
    {
        for (int i = 0; i < _spinnerParent.getAdapter().getCount(); i++)
            if (((TasksProject) _spinnerParent.getAdapter().getItem(i)).get_id().equals(parentId))
            {
                _spinnerParent.setSelection(i);
                break;
            }
    }
    private void set_Status(EnumStatus status)
    {
        for (int i = 0; i < _spinnerStatus.getAdapter().getCount(); i++)
            if (_spinnerStatus.getAdapter().getItem(i).equals(status))
            {
                _spinnerStatus.setSelection(i);
                break;
            }
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }
    private void set_Comment(String comment)
    {
        _editTextComment.setText(comment);
    }
    //endregion

    //region Check Params
    @Override
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        return CheckName();
    }

    private boolean CheckName()
    {
        try
        {
            String name = getName();
            if (name.equals(""))
            {
                _editTextName.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyName), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextName.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectName), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
        if (_data != null && _data.get_parentId() != 0)
            return true;
        else
        {
            if (_data != null && _data.get_hasChild())
            {
                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.errorExistChildsTasksProject), Toast.LENGTH_SHORT).show();
                return false;
            }
            else
                return true;
        }
    }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        return _data != null ? _data.get_id() : 0;
    }

    private Integer get_Position()
    {
        if (_data == null || _data.get_parentId() != get_ParentId())
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                return dbHelper.QueryTasksProjectMaxPosition(get_ParentId()) + 1;
            }
            catch (Exception e) { return 0; }
        }
        else
            return _data.get_position();
    }

    private Long get_ParentId()
    {
        return  ((TasksProject)_spinnerParent.getSelectedItem()).get_id();
    }

    private Long get_targetId()
    {
        return _data != null ? _data.get_target_id() : 0;
    }

    private String getName() throws Exception
    {
        try
        {
            return _editTextName.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private String getComment() throws Exception
    {
        try
        {
            return _editTextComment.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private int get_ProjectCount()
    {
        return _data == null ? 0 : _data.get_projectCount();
    }

    private int get_TaskHighCount()
    {
        return _data == null ? 0 : _data.get_taskCountHigh();
    }

    private int get_TaskMiddleCount()
    {
        return _data == null ? 0 : _data.get_taskCountMiddle();
    }

    private int get_TaskLowCount()
    {
        return _data == null ? 0 : _data.get_taskCountLow();
    }

    private int get_TaskNoneCount()
    {
        return _data == null ? 0 : _data.get_taskCountNone();
    }

    private EnumStatus get_status()
    {
        return (EnumStatus) _spinnerStatus.getSelectedItem();
    }
    //endregion

    //region Save Params
    protected TasksProject GetDataFromForm()
    {
        try
        {
            return new TasksProject(get_Id(),
                    getName(),
                    getComment(),
                    get_ParentId(),
                    get_targetId(),
                    get_Position(),
                    get_ProjectCount(),
                    get_TaskHighCount(),
                    get_TaskMiddleCount(),
                    get_TaskLowCount(),
                    get_TaskNoneCount(),
                    get_status());
        }
        catch (Exception e)
        {
            return  null;
        }
    }
    //endregion
}