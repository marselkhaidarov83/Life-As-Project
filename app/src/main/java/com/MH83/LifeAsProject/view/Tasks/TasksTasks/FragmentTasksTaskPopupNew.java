package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.task.TasksTaskAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerRepeatSubTasksTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerRepeatTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerTasksProjectAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerTasksTaskAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.data.Tasks.TasksTaskDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatSubTasks;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class FragmentTasksTaskPopupNew extends CustomPopupBottomFragment<TasksTask>
    implements IFragmentViewCustom<TasksTask>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_tasks_task_new;
    //endregion

    //region Fragment parameters
    private boolean _dependent;
    private Long _projectIdPre;
    //endregion

    //region Fragment settings
    private Calendar _date;
    private boolean _confirmed;
    private boolean _repeat;
    private boolean _oldConfirmed = false;
    private EnumTypeTask _typeTask;
    private boolean _smallForm;
    private Long _priority;
    //endregion

    //region Fragment Components
    private ImageView _imageViewConfirmed;
    private EditText _editTextName;
    private EditText _editTextComment;
    private EditText _editTextComment2;
    private TextView _textViewProject;
    private Spinner _spinnerProject;
    private TextView _textViewPriority;
    private ConstraintLayout _constraintLayoutPriority;
    private RelativeLayout _relativeLayoutDate;
    private RelativeLayout _relativeLayoutDate2;
    private TextView _textViewDate;
    private ImageView _imageViewDateMinus;
    private ImageView _imageViewDatePlus;
    private TextView _textViewTime;
    private ImageView _imageViewTimeMinus;
    private ImageView _imageViewTimePlus;
    private TextView _textViewDateTitle;
    private ImageView _imageViewRepeat;
    private ConstraintLayout _constraintLayoutRepeat;
    private ConstraintLayout _constraintLayoutRepeat2;
    private ConstraintLayout _constraintLayoutRepeat3;
    private Spinner _spinnerRepeatType;
    private Spinner _spinnerRepeatSubTasks;
    private EditText _editTextRepeatPeriod;
    private ImageView _imageViewClearDate;

    private TextView textViewButton1;
    private Button _buttonNext1;
    private Button _buttonNext2;
    private Button _buttonPrev2;
    private Button _buttonPrev3;
    private NestedScrollView _nestedScrollView1;
    private NestedScrollView _nestedScrollView2;
    private NestedScrollView _nestedScrollView3;
    private TableLayout _tableLayout1;
    private ConstraintLayout _constraintLayoutNSV2;
    private ConstraintLayout _constraintLayoutNSV3;
    private LinearLayout _linearLayoutNSV1;
    private LinearLayout _linearLayoutNSV2;
    private LinearLayout _linearLayoutNSV3;

    private ImageView _imageViewPriorityHigh;
    private ImageView _imageViewPriorityMiddle;
    private ImageView _imageViewPriorityLow;
    private ImageView _imageViewPriorityNone;

    private Spinner _spinnerParentTask;
    private RecyclerView _recyclerViewTask;
    private ImageView _imageViewAddToRViewTask;
    //endregion

    //region Constructors
    public FragmentTasksTaskPopupNew()
    {
        super(_layoutId,
                R.string.PopupTasksTaskAdd,
                R.string.PopupTasksTaskUpdate,
                R.string.errorUpdateTasksTask);
    }

    public FragmentTasksTaskPopupNew(AppCompatActivity activity,
                                     EnumTypeTask typeTask,
                                     TasksTask data,
                                     EnumTypeAction typeAction,
                                     EnumTypeAction typeAction2,
                                     IFragmentViewCustom iFragmentViewCustom,
                                     IFragmentViewCustom iFragmentViewCustom2,
                                     boolean smallForm,
                                     boolean dependent,
                                     Long projectIdPre)
    {
        super(activity, _layoutId, data,
                typeAction,
                typeAction2,
                null,
                R.string.PopupTasksTaskAdd,
                R.string.PopupTasksTaskUpdate,
                R.string.errorUpdateTasksTask,
                iFragmentViewCustom,
                iFragmentViewCustom2,
                null);

        _typeTask = typeTask;
        _smallForm = smallForm;
        _dependent = dependent;
        _projectIdPre = projectIdPre;

//        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _imageViewConfirmed = view.findViewById(R.id.imageViewConfirmed);
        _editTextName = view.findViewById(R.id.editTextName);
        _editTextComment = view.findViewById(R.id.editTextComment);
        _editTextComment2 = view.findViewById(R.id.editTextComment2);
        _textViewProject = view.findViewById(R.id.textViewProject);
        _spinnerProject = view.findViewById(R.id.spinnerProject);
        _textViewPriority = view.findViewById(R.id.textViewPriority);
        _constraintLayoutPriority = view.findViewById(R.id.constraintLayoutPriority);
        _relativeLayoutDate = view.findViewById(R.id.relativeLayoutDate);
        _relativeLayoutDate2 = view.findViewById(R.id.relativeLayoutDate2);
        _textViewDate = view.findViewById(R.id.textViewDate);
        _imageViewDateMinus = view.findViewById(R.id.imageViewDateMinus);
        _imageViewDatePlus = view.findViewById(R.id.imageViewDatePlus);
        _textViewDateTitle = view.findViewById(R.id.textViewDateTitle);
        _textViewTime = view.findViewById(R.id.textViewTime);
        _imageViewTimeMinus = view.findViewById(R.id.imageViewTimeMinus);
        _imageViewTimePlus = view.findViewById(R.id.imageViewTimePlus);
        _imageViewRepeat = view.findViewById(R.id.imageViewRepeat);
        _constraintLayoutRepeat = view.findViewById(R.id.constraintLayoutRepeat);
        _constraintLayoutRepeat2 = view.findViewById(R.id.constraintLayoutRepeat2);
        _constraintLayoutRepeat3 = view.findViewById(R.id.constraintLayoutRepeat3);
        _spinnerRepeatType = view.findViewById(R.id.spinnerRepeatType);
        _spinnerRepeatSubTasks = view.findViewById(R.id.spinnerRepeatSubTasks);
        _editTextRepeatPeriod = view.findViewById(R.id.editTextRepeatPeriod);
        _imageViewClearDate = view.findViewById(R.id.imageViewClearDate);

        textViewButton1 = view.findViewById(R.id.textViewButton1);
        _buttonNext1 = view.findViewById(R.id.buttonNext);
        _buttonNext2 = view.findViewById(R.id.buttonNext2);
        _buttonPrev2 = view.findViewById(R.id.buttonPrev2);
        _buttonPrev3 = view.findViewById(R.id.buttonPrev3);
        _nestedScrollView1 = view.findViewById(R.id.nestedScrollView1);
        _nestedScrollView2 = view.findViewById(R.id.nestedScrollView2);
        _nestedScrollView3 = view.findViewById(R.id.nestedScrollView3);
        _tableLayout1 = view.findViewById(R.id.tableLayoutNSV1);
        _constraintLayoutNSV2 = view.findViewById(R.id.constraintLayoutNSV2);
        _constraintLayoutNSV3 = view.findViewById(R.id.constraintLayoutNSV3);
        _linearLayoutNSV1 = view.findViewById(R.id.linearLayoutNSV1);
        _linearLayoutNSV2 = view.findViewById(R.id.linearLayoutNSV2);
        _linearLayoutNSV3 = view.findViewById(R.id.linearLayoutNSV3);

        _imageViewPriorityHigh = view.findViewById(R.id.imageViewPriorityHigh);
        _imageViewPriorityMiddle = view.findViewById(R.id.imageViewPriorityMiddle);
        _imageViewPriorityLow = view.findViewById(R.id.imageViewPriorityLow);
        _imageViewPriorityNone = view.findViewById(R.id.imageViewPriorityNone);

        _spinnerParentTask = view.findViewById(R.id.spinnerParentTask);
        _recyclerViewTask = view.findViewById(R.id.recycleViewPNLCredit);
        _imageViewAddToRViewTask = view.findViewById(R.id.imageViewAdd);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = globalVariable.get_typeDevice() == EnumTypeDevice.Tablet ||
                getContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextName;
        _focusedViewToUpdate = _editTextName;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    public void PopupShow()
    {
        super.PopupShow();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int width = Math.min((int) (0.95 * displaymetrics.widthPixels),
                (int) getContext().getResources().getDimension(R.dimen.Tasks_Task_Popup_Width));

        if (getContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE)
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT);
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            else
                getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        ClearFocus();
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        height -= _layoutCaption.getLayoutParams().height;
        height -= 9 * getContext().getResources().getDimension(R.dimen.BasePadding);
        height -= _buttonSave.getLayoutParams().height;
        _editTextComment2.getLayoutParams().height = height;

        height -= _spinnerParentTask.getLayoutParams().height;
//        height = Math.min(height, (int) (2 * displaymetrics.heightPixels / 3f));
        _recyclerViewTask.getLayoutParams().height = height;
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        if (_smallForm)
        {
            _textViewProject.getLayoutParams().height = 0;
            _spinnerProject.getLayoutParams().height = 0;
            _imageViewConfirmed.setVisibility(View.INVISIBLE);
            _relativeLayoutDate.getLayoutParams().height = 0;
            _relativeLayoutDate2.getLayoutParams().height = 0;
            _textViewDateTitle.getLayoutParams().height = 0;
            _textViewPriority.getLayoutParams().height = 0;
            _constraintLayoutPriority.getLayoutParams().height = 0;
            _constraintLayoutRepeat.getLayoutParams().height = 0;
            _constraintLayoutRepeat2.getLayoutParams().height = 0;
            _constraintLayoutRepeat3.getLayoutParams().height = 0;
            textViewButton1.getLayoutParams().width = 0;
            _buttonNext1.getLayoutParams().width = 0;
        }

        if (_dependent)
        {
            _spinnerProject.setEnabled(false);

            _buttonNext1.getLayoutParams().width = 0;
            textViewButton1.getLayoutParams().width = 0;
        }

        GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
        {
            _textViewPriority.getLayoutParams().height = 0;
            _constraintLayoutPriority.getLayoutParams().height = 0;
            _constraintLayoutRepeat.getLayoutParams().height = 0;
            _constraintLayoutRepeat2.getLayoutParams().height = 0;
            _constraintLayoutRepeat3.getLayoutParams().height = 0;
        }
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetSpinnerProjectAdapter();
        SetSpinnerRepeatTypeAdapter();
        SetRepeatSubTasksTypeAdapter();
//        SetSpinnerParentTask();
        if (!_dependent)
            SetRecyclerViewTaskAdapter();
    }

    private void SetSpinnerProjectAdapter()
    {
        if (_spinnerProject.getAdapter() != null)
            return;

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        TasksTaskSettings tasksTaskSettings = globalVariable.get_tasksTaskSettingsHT();

        List<TasksProject> list;
        if (tasksTaskSettings.get_groupOfProjectId() == null ||
                tasksTaskSettings.get_groupOfProjectId() == 0)
            list = TasksProjectDataSource.LoadDataChildren(getContext(), true,
                    new TaskProjectSettings(EnumTargetStatus4Settings.All, null));
        else
            list = TasksProjectDataSource.LoadDataChildren(getContext(),
                     tasksTaskSettings.get_groupOfProjectId(), true,
                    new TaskProjectSettings(EnumTargetStatus4Settings.All, null));

        SpinnerTasksProjectAdapter adapter = new SpinnerTasksProjectAdapter(getContext(), list);
        _spinnerProject.setAdapter(adapter);
    }

    private void SetSpinnerParentTask()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        TasksTaskSettings tasksTaskSettings = globalVariable.get_tasksTaskSettingsHT().Clone();
        tasksTaskSettings.set_confirmedType(false);

        List<TasksTaskIndependent> list = TasksTaskDataSource.LoadDataParentWOChildren(getContext(),
                null, null, tasksTaskSettings, true);
        for (TasksTaskIndependent item : list)
            if (_data != null && Objects.equals(_data.get_id(), item.get_id()) && item.get_id() != 0)
            {
                list.remove(item);
                break;
            }
        SpinnerTasksTaskAdapter adapter = new SpinnerTasksTaskAdapter(getContext(), list);
        _spinnerParentTask.setAdapter(adapter);
    }

    private void SetSpinnerRepeatTypeAdapter()
    {
        List<EnumRepeatType> list = new ArrayList<>();
        list.add(EnumRepeatType.Day);
        list.add(EnumRepeatType.Week);
        list.add(EnumRepeatType.Month);
        list.add(EnumRepeatType.MonthEnd);
        list.add(EnumRepeatType.Year);
        list.add(EnumRepeatType.YearEnd);

        SpinnerRepeatTypeAdapter adapter = new SpinnerRepeatTypeAdapter(getContext(), list);
        _spinnerRepeatType.setAdapter(adapter);
    }

    private void SetRepeatSubTasksTypeAdapter()
    {
        List<EnumRepeatSubTasks> list = new ArrayList<>();
        list.add(EnumRepeatSubTasks.All);
        list.add(EnumRepeatSubTasks.OnlyNotConfirmed);

        SpinnerRepeatSubTasksTypeAdapter adapter = new SpinnerRepeatSubTasksTypeAdapter(getContext(), list);
        _spinnerRepeatSubTasks.setAdapter(adapter);
    }

    private void SetRecyclerViewTaskAdapter()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        TasksTaskSettings tasksTaskSettings = globalVariable.get_tasksTaskSettingsHT().Clone();
        tasksTaskSettings.set_confirmedType(false);

        ArrayList<TasksTask> list = new ArrayList<>();
        if (_data != null)
            list.addAll(_data.get_childs());

        TasksTaskAdapter adapter = new TasksTaskAdapter(getContext(),
                list, _typeAction,
                _iFragmentViewCustom,
                this,
                null,
                null, tasksTaskSettings);
        _recyclerViewTask.setAdapter(adapter);
    }
    //endregion

    //region Set Listeners
    public void ClearFocus()
    {
        _editTextName.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);
    }

    @Override
    protected void SetListeners()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        super.SetListeners();

        _textViewDate.setOnClickListener(view1 ->
        {
                ClearFocus();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (datePicker, year, month, day) ->
                        {
                            _date = _date == null ? Calendar.getInstance() : _date;
                            _date.set(year, month, day);
                            set_date(_date);
                        },
                        _date == null ? Calendar.getInstance().get(Calendar.YEAR) : _date.get(Calendar.YEAR),
                        _date == null ? Calendar.getInstance().get(Calendar.MONTH) : _date.get(Calendar.MONTH),
                        _date == null ? Calendar.getInstance().get(Calendar.DATE) : _date.get(Calendar.DATE));
                datePickerDialog.show();
            });

        _imageViewDateMinus.setOnClickListener(view ->
        {
            ClearFocus();
            _date = _date == null ? Calendar.getInstance() : _date;
            _date.add(Calendar.DATE, -1);
            set_date(_date);
        });

        _imageViewDatePlus.setOnClickListener(view ->
        {
            ClearFocus();
            _date = _date == null ? Calendar.getInstance() : _date;
            _date.add(Calendar.DATE, 1);
            set_date(_date);
        });

        _imageViewClearDate.setOnClickListener(view -> set_date(null));

        _textViewTime.setOnClickListener(view1 ->
        {
            ClearFocus();
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (timePicker, i, i1) ->
                    {
                        _date = _date == null ? Calendar.getInstance() : _date;
                        _date.set(Calendar.HOUR_OF_DAY, i);
                        _date.set(Calendar.MINUTE, i1);
                        set_date(_date);
                    },
                    _date == null ? Calendar.getInstance().get(Calendar.HOUR_OF_DAY) : _date.get(Calendar.HOUR_OF_DAY),
                    _date == null ? Calendar.getInstance().get(Calendar.MINUTE) : _date.get(Calendar.MINUTE),
                    true);
            timePickerDialog.show();
        });

        _imageViewTimeMinus.setOnClickListener(view ->
        {
            ClearFocus();
            _date = _date == null ? Calendar.getInstance() : _date;
            _date.add(Calendar.MINUTE, -1);
            set_date(_date);
        });

        _imageViewTimePlus.setOnClickListener(view ->
        {
            ClearFocus();
            _date = _date == null ? Calendar.getInstance() : _date;
            _date.add(Calendar.MINUTE, 1);
            set_date(_date);
        });

        _imageViewConfirmed.setOnClickListener(view -> set_confirmed(!_confirmed));

        _constraintLayoutRepeat.setOnClickListener(view -> set_repeat(!_repeat));

        _buttonNext1.setOnClickListener(view ->
        {
            if (globalVariable.get_hasUnpaidSubscription())
                NextTo3();
            else
                NextTo2();
//            ClearFocus();
//
//            SetSpinnerParentTask();
//            if (_data != null)
//                set_parentTask(_data.get_parentId());
//            else
//                set_parentTask(0L);
//
//            _nestedScrollView1.setVisibility(View.INVISIBLE);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
//            lp.weight = 1;
//            _nestedScrollView1.setLayoutParams(lp);
//
//            _nestedScrollView2.setVisibility(View.VISIBLE);
//            lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//            lp.weight = 0;
//            _nestedScrollView2.setLayoutParams(lp);
//
//            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            {
//                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
//                lp.height = 0;
//                _tableLayout1.setLayoutParams(lp);
//
//                lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
//                lp.height = 0;
//                _linearLayoutNSV1.setLayoutParams(lp);
//
//                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                _constraintLayoutNSV2.setLayoutParams(lp);
//
//                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                _linearLayoutNSV2.setLayoutParams(lp);
//            }
//            else
//            {
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                {
//                    lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
//                    lp.height = 0;
//                    _tableLayout1.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV1.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//                }
//            }
        });

        if (_buttonNext2 != null)
            _buttonNext2.setOnClickListener(view ->
            {
                NextTo3();
//                ClearFocus();
//
//                _nestedScrollView2.setVisibility(View.INVISIBLE);
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//                lp.weight = 1;
//                _nestedScrollView2.setLayoutParams(lp);
//
//                _nestedScrollView3.setVisibility(View.VISIBLE);
//                lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
//                lp.weight = 0;
//                _nestedScrollView3.setLayoutParams(lp);
//
//                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//                else
//                {
//                    final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                    if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                    {
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                        lp.height = 0;
//                        _constraintLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                        lp.height = 0;
//                        _linearLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _constraintLayoutNSV3.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _linearLayoutNSV3.setLayoutParams(lp);
//                    }
//                }
            });

        if (_buttonPrev2 != null)
            _buttonPrev2.setOnClickListener(view ->
            {
                PrevTo1();
//                ClearFocus();
//
//                _nestedScrollView1.setVisibility(View.VISIBLE);
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
//                lp.weight = 0;
//                _nestedScrollView1.setLayoutParams(lp);
//
//                _nestedScrollView2.setVisibility(View.INVISIBLE);
//                lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//                lp.weight = 1;
//                _nestedScrollView2.setLayoutParams(lp);
//
//                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//                {
//    //                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//    //                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
//                    {
//                        lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _tableLayout1.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _linearLayoutNSV1.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                        lp.height = 0;
//                        _constraintLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                        lp.height = 0;
//                        _linearLayoutNSV2.setLayoutParams(lp);
//                    }
//                }
//                else
//                {
//                    final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                    if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                    {
//                        lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _tableLayout1.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _linearLayoutNSV1.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                        lp.height = 0;
//                        _constraintLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                        lp.height = 0;
//                        _linearLayoutNSV2.setLayoutParams(lp);
//                    }
//                }
            });

        if (_buttonPrev3 != null)
            _buttonPrev3.setOnClickListener(view ->
            {
                if (globalVariable.get_hasUnpaidSubscription())
                    PrevTo1();
                else
                    PrevTo2();
//                ClearFocus();
//
//                _nestedScrollView2.setVisibility(View.VISIBLE);
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//                lp.weight = 0;
//                _nestedScrollView2.setLayoutParams(lp);
//
//                _nestedScrollView3.setVisibility(View.INVISIBLE);
//                lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
//                lp.weight = 1;
//                _nestedScrollView3.setLayoutParams(lp);
//
//                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//                {
//                    //                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                    //                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
//                    {
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _constraintLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _linearLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                        lp.height = 0;
//                        _constraintLayoutNSV3.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                        lp.height = 0;
//                        _linearLayoutNSV3.setLayoutParams(lp);
//                    }
//                }
//                else
//                {
//                    final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                    if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                    {
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _constraintLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        _linearLayoutNSV2.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                        lp.height = 0;
//                        _constraintLayoutNSV3.setLayoutParams(lp);
//
//                        lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                        lp.height = 0;
//                        _linearLayoutNSV3.setLayoutParams(lp);
//                    }
//                }
            });

        _imageViewPriorityHigh.setOnClickListener(view ->
        {
            _priority = 1L;
            set_priority_image();
        });

        _imageViewPriorityMiddle.setOnClickListener(view ->
        {
            _priority = 2L;
            set_priority_image();
        });

        _imageViewPriorityLow.setOnClickListener(view ->
        {
            _priority = 3L;
            set_priority_image();
        });

        _imageViewPriorityNone.setOnClickListener(view ->
        {
            _priority = 4L;
            set_priority_image();
        });

        _imageViewAddToRViewTask.setOnClickListener(view -> {
            new FragmentTasksTaskPopupNew(
                    _activity,
                    EnumTypeTask.Task,
                    null,
                    _typeAction,
                    EnumTypeAction.Insert,
                    _iFragmentViewCustom,
                    this,
                    false,
                    true,
                    get_projectId()).PopupShow();
        });

        _spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (_recyclerViewTask.getAdapter() == null)
                    return;

                int ii = 0;
                for (TasksTask tasksTask : ((TasksTaskAdapter) _recyclerViewTask.getAdapter()).get_dataSet())
                {
                    tasksTask.set_project(get_projectId(), get_projectName());
                    _recyclerViewTask.getAdapter().notifyItemChanged(ii++);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        _spinnerParentTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                TasksTaskIndependent tasksTaskIndependentParent =
                        (TasksTaskIndependent) _spinnerParentTask.getSelectedItem();
                if (tasksTaskIndependentParent.get_id() != 0)
                    set_project(tasksTaskIndependentParent.get_project_id());
                _spinnerProject.setEnabled(tasksTaskIndependentParent.get_id() == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        _editTextComment.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String s = editable.toString();
                if (!_editTextComment2.getText().toString().equals(s))
                    _editTextComment2.setText(s);
            }
        });

        _editTextComment2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String s = editable.toString();
                if (!_editTextComment.getText().toString().equals(s))
                    _editTextComment.setText(s);
            }
        });
    }

    private void NextTo2()
    {
        ClearFocus();

        SetSpinnerParentTask();
        if (_data != null)
            set_parentTask(_data.get_parentId());
        else
            set_parentTask(0L);

        _nestedScrollView1.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
        lp.weight = 1;
        _nestedScrollView1.setLayoutParams(lp);

        _nestedScrollView2.setVisibility(View.VISIBLE);
        lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
        lp.weight = 0;
        _nestedScrollView2.setLayoutParams(lp);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
            lp.height = 0;
            _tableLayout1.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
            lp.height = 0;
            _linearLayoutNSV1.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV2.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            _linearLayoutNSV2.setLayoutParams(lp);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = 0;
                _tableLayout1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _linearLayoutNSV2.setLayoutParams(lp);
            }
        }
    }

    private void NextTo3()
    {
        ClearFocus();

        _nestedScrollView2.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
        lp.weight = 1;
        _nestedScrollView2.setLayoutParams(lp);

        _nestedScrollView3.setVisibility(View.VISIBLE);
        lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
        lp.weight = 0;
        _nestedScrollView3.setLayoutParams(lp);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
            lp.height = 0;
            _tableLayout1.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
            lp.height = 0;
            _linearLayoutNSV1.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
            lp.height = 0;
            _constraintLayoutNSV2.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
            lp.height = 0;
            _linearLayoutNSV2.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV3.setLayoutParams(lp);

            lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            _linearLayoutNSV3.setLayoutParams(lp);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = 0;
                _tableLayout1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _constraintLayoutNSV3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _linearLayoutNSV3.setLayoutParams(lp);
            }
        }
    }

    private void PrevTo1()
    {
        ClearFocus();

        _nestedScrollView1.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
        lp.weight = 0;
        _nestedScrollView1.setLayoutParams(lp);

        _nestedScrollView2.setVisibility(View.INVISIBLE);
        lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
        lp.weight = 1;
        _nestedScrollView2.setLayoutParams(lp);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            //                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            //                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _tableLayout1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _linearLayoutNSV1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV3.setLayoutParams(lp);
            }
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _tableLayout1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _linearLayoutNSV1.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV3.setLayoutParams(lp);
            }
        }
    }

    private void PrevTo2()
    {
        ClearFocus();

        _nestedScrollView2.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
        lp.weight = 0;
        _nestedScrollView2.setLayoutParams(lp);

        _nestedScrollView3.setVisibility(View.INVISIBLE);
        lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
        lp.weight = 1;
        _nestedScrollView3.setLayoutParams(lp);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            //                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            //                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _linearLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV3.setLayoutParams(lp);
            }
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                _linearLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV3.setLayoutParams(lp);
            }
        }
    }
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(null);
    }

    protected void SetValues(Object[] objects)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        TasksTaskSettings tasksTaskSettings = globalVariable.get_tasksTaskSettingsHT();

        if (_data != null)
        {
            set_name(_data.get_name());
            set_comment(_data.get_comment());
            set_project(_data.get_project_id());
//            set_parentTask(_data.get_parentId());
            set_priority(_data.get_priority());
            set_date(_data.get_calendarPlan());
            set_confirmed(_data.get_confirmed());
            set_oldConfirmed(_data.get_confirmed());
            set_repeat(_data.get_repeat());
            set_repeatType(_data.get_repeatType());
            set_repeatPeriod(_data.get_repeatPer());
            set_repeatSubTasks(_data.get_repeatSubTasks());
        }
        else
        {
            set_name("");
            set_comment("");
            if (tasksTaskSettings != null && tasksTaskSettings.get_projectId() != null)
                set_project(tasksTaskSettings.get_projectId());
            else if (_projectIdPre != null)
                set_project(_projectIdPre);
            else
                set_project(0L);
            set_priority(4L);
            set_date(_smallForm ? null : Calendar.getInstance());
            set_confirmed(false);
            set_oldConfirmed(false);
            set_repeat(false);
            set_repeatType(EnumRepeatType.Day);
            set_repeatPeriod(1);
            set_repeatSubTasks(EnumRepeatSubTasks.All);
        }
    }

    private void set_name(String name)
    {
        _editTextName.setText(name);
    }

    private void set_comment(String comment)
    {
        _editTextComment.setText(comment);
        _editTextComment2.setText(comment);
    }

    private void set_project(Long projectId)
    {
        for (int i = 0; i < _spinnerProject.getAdapter().getCount(); i++)
            if (Objects.equals(((TasksProject) _spinnerProject.getAdapter().getItem(i)).get_id(), projectId))
            {
                _spinnerProject.setSelection(i);
                break;
            }
    }

    private void set_parentTask(Long parentTask)
    {
        for (int i = 0; i < _spinnerParentTask.getAdapter().getCount(); i++)
            if (Objects.equals(((TasksTaskIndependent) _spinnerParentTask.getAdapter().getItem(i)).get_id(), parentTask))
            {
                _spinnerParentTask.setSelection(i);
                break;
            }
    }

    private void set_priority(Long priority)
    {
        _priority = priority;
        set_priority_image();
    }

    private void set_priority_image()
    {
        switch (_priority.intValue())
        {
            case 1:
                _imageViewPriorityHigh.setImageResource(R.drawable.icons_priority_high_ok);
                _imageViewPriorityMiddle.setImageResource(R.drawable.icons_priority_middle);
                _imageViewPriorityLow.setImageResource(R.drawable.icons_priority_low);
                _imageViewPriorityNone.setImageResource(R.drawable.icons_priority_none);
                break;
            case 2:
                _imageViewPriorityHigh.setImageResource(R.drawable.icons_priority_high);
                _imageViewPriorityMiddle.setImageResource(R.drawable.icons_priority_middle_ok);
                _imageViewPriorityLow.setImageResource(R.drawable.icons_priority_low);
                _imageViewPriorityNone.setImageResource(R.drawable.icons_priority_none);
                break;
            case 3:
                _imageViewPriorityHigh.setImageResource(R.drawable.icons_priority_high);
                _imageViewPriorityMiddle.setImageResource(R.drawable.icons_priority_middle);
                _imageViewPriorityLow.setImageResource(R.drawable.icons_priority_low_ok);
                _imageViewPriorityNone.setImageResource(R.drawable.icons_priority_none);
                break;
            default:
                _imageViewPriorityHigh.setImageResource(R.drawable.icons_priority_high);
                _imageViewPriorityMiddle.setImageResource(R.drawable.icons_priority_middle);
                _imageViewPriorityLow.setImageResource(R.drawable.icons_priority_low);
                _imageViewPriorityNone.setImageResource(R.drawable.icons_priority_none_ok);
        }
    }

    private void SetEnabled4Repeat(boolean enabled)
    {
        _constraintLayoutRepeat.setEnabled(enabled);

        if (!enabled)
            set_repeat(enabled);
    }

    @SuppressLint("SetTextI18n")
    private void set_date(Calendar calendar)
    {
        if (calendar == null)
        {
            SetEnabled4Repeat(false);
            _date = null;
            _textViewDate.setText("");
            _textViewTime.setText("");
            return;
        }

        SetEnabled4Repeat(true);
        _date = (Calendar) calendar.clone();

        CustomStaticClass.setDateView(getContext(),
                _date.get(Calendar.DATE),
                _date.get(Calendar.MONTH),
                _date.get(Calendar.YEAR),
                _textViewDate);

        String sHour = "";
        if (_date.get(Calendar.HOUR_OF_DAY) < 10)
            sHour = "0";
        sHour += _date.get(Calendar.HOUR_OF_DAY);

        String sMinute = "";
        if (_date.get(Calendar.MINUTE) < 10)
            sMinute = "0";
        sMinute += _date.get(Calendar.MINUTE);

        _textViewTime.setText(sHour + ":" + sMinute);

        Calendar c = Calendar.getInstance();
        if (_date.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
                _date.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                _date.get(Calendar.DATE) == c.get(Calendar.DATE))
        {
            _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeToday));
            return;
        }

        c.add(Calendar.DATE, 1);
        if (_date.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
                _date.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                _date.get(Calendar.DATE) == c.get(Calendar.DATE))
        {
            _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeTomorrow));
            return;
        }

        c.add(Calendar.DATE, 1);
        if (_date.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
                _date.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                _date.get(Calendar.DATE) == c.get(Calendar.DATE))
        {
            _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeAfterTomorrow));
            return;
        }

        c.add(Calendar.DATE, -3);
        if (_date.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
                _date.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                _date.get(Calendar.DATE) == c.get(Calendar.DATE))
        {
            _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeYesterday));
            return;
        }


        c.add(Calendar.DATE, -1);
        if (_date.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
                _date.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                _date.get(Calendar.DATE) == c.get(Calendar.DATE))
        {
            _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeTwoDaysAgo));
            return;
        }

        _textViewDateTitle.setText(CustomStaticClass.getDayOfWeek(getContext(), _date));
//        _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeData));
    }

    private void set_oldConfirmed(boolean confirmed) { _oldConfirmed = confirmed; }

    private void set_confirmed(boolean confirmed)
    {
            _confirmed = confirmed;
        _imageViewConfirmed.setImageResource(_confirmed ? R.drawable.icons_is_fact_100 :
                R.drawable.icons_is_plan_100);
    }

    private void set_repeat(boolean repeat)
    {
        _repeat = repeat;
        _imageViewRepeat.setImageResource(_repeat ? R.drawable.icons_is_fact_100 :
                R.drawable.icons_is_plan_100);
        _spinnerRepeatType.setEnabled(_repeat);
        _spinnerRepeatSubTasks.setEnabled(_repeat);
        _editTextRepeatPeriod.setEnabled(_repeat);
    }

    private void set_repeatType(EnumRepeatType repeatType)
    {
        for (int i = 0; i < _spinnerRepeatType.getAdapter().getCount(); i++)
            if (_spinnerRepeatType.getAdapter().getItem(i).equals(repeatType))
            {
                _spinnerRepeatType.setSelection(i);
                break;
            }
    }

    private void set_repeatSubTasks(EnumRepeatSubTasks repeatSubTasks)
    {
        for (int i = 0; i < _spinnerRepeatSubTasks.getAdapter().getCount(); i++)
            if (_spinnerRepeatSubTasks.getAdapter().getItem(i).equals(repeatSubTasks))
            {
                _spinnerRepeatSubTasks.setSelection(i);
                break;
            }
    }

    private void set_repeatPeriod(int repeatPeriod)
    {
        _editTextRepeatPeriod.setText(String.valueOf(repeatPeriod));
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        if (!CheckName())               return false;
        if (!CheckRepeatPeriod())       return false;

        return true;
    }

    private boolean CheckName()
    {
        try
        {
            if (get_name().equals(""))
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

    private boolean CheckRepeatPeriod()
    {
        if (!get_repeat())
            return true;

        try
        {
            if (_editTextRepeatPeriod.getText().toString().equals(""))
            {
                _editTextRepeatPeriod.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextRepeatPeriod, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyRepeatPeriod), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextRepeatPeriod.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextRepeatPeriod, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectRepeatPeriod), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
        return true;
    }
    //endregion

    //region Get Params
    private Long get_id()
    {
        return _data != null ? _data.get_id() : 0;
    }

    private EnumTypeTask get_typeTask() { return _data != null ? _data.get_type() : _typeTask; }

    private Long get_projectId()
    {
        return ((TasksProject) _spinnerProject.getSelectedItem()).get_id();
    }

    private Long get_parentTask()
    {
        if (_spinnerParentTask.getAdapter() == null)
        {
            if (_data == null)
                return 0L;
            else
                return _data.get_parentId();
        }
        return ((TasksTaskIndependent) _spinnerParentTask.getSelectedItem()).get_id();
    }

    private String get_projectName()
    {
        return ((TasksProject) _spinnerProject.getSelectedItem()).get_name();
    }

    private Long get_priority()
    {
        return _priority;
    }

    private Long get_occuredId()
    {
        return _data != null ? _data.get_occured_id() : 0;
    }

    private String get_name()
    {
        return _editTextName.getText().toString();
    }

    private String get_comment()
    {
        return _editTextComment.getText().toString();
    }

    private int get_dayPlan()
    {
        return _date == null ? 0 : _date.get(Calendar.DATE);
    }

    private int get_monthPlan()
    {
        return _date == null ? 0 : _date.get(Calendar.MONTH);
    }

    private int get_yearPlan()
    {
        return _date == null ? 0 : _date.get(Calendar.YEAR);
    }

    private int get_hourPlan()
    {
        return _date == null ? 0 : _date.get(Calendar.HOUR_OF_DAY);
    }

    private int get_minutePlan()
    {
        return _date == null ? 0 : _date.get(Calendar.MINUTE);
    }

    private int get_dayFact()
    {
        if (_confirmed)
        {
            if (!_oldConfirmed)
                return Calendar.getInstance().get(Calendar.DATE);
            else
                return _data.get_day_fact();
        }
        else
            return 0;
    }

    private int get_monthFact()
    {
        if (_confirmed)
        {
            if (!_oldConfirmed)
                return Calendar.getInstance().get(Calendar.MONTH);
            else
                return _data.get_month_fact();
        }
        else
            return 0;
    }

    private int get_yearFact()
    {
        if (_confirmed)
        {
            if (!_oldConfirmed)
                return Calendar.getInstance().get(Calendar.YEAR);
            else
                return _data.get_year_fact();
        }
        else
            return 0;
    }

    private int get_hourFact()
    {
        if (_confirmed)
        {
            if (!_oldConfirmed)
                return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            else
                return _data.get_hour_fact();
        }
        else
            return 0;
    }

    private int get_minuteFact()
    {
        if (_confirmed)
        {
            if (!_oldConfirmed)
                return Calendar.getInstance().get(Calendar.MINUTE);
            else
                return _data.get_minute_fact();
        }
        else
            return 0;
    }

    private boolean get_repeat() { return _repeat; }

    private int get_repeatPer() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextRepeatPeriod.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private EnumRepeatType get_repeatType()
    {
        return ((EnumRepeatType) _spinnerRepeatType.getSelectedItem());
    }

    private EnumRepeatSubTasks get_repeatSubTasks()
    {
        return ((EnumRepeatSubTasks) _spinnerRepeatSubTasks.getSelectedItem());
    }

    private boolean get_confirmed() { return _confirmed; }

    private boolean get_oldConfirmed() { return _oldConfirmed; }

    private EnumTaskStatus get_status() { return null; }

    private boolean get_hasChild()
    {
        //TODO: определять из адаптера подзадач
        return false;
    }

    private ArrayList<TasksTaskIndependent> get_childs()
    {
        ArrayList<TasksTaskIndependent> list = new ArrayList<>();

        TasksTaskAdapter tasksTaskAdapter = (TasksTaskAdapter) _recyclerViewTask.getAdapter();
        if (tasksTaskAdapter != null)
            for (TasksTask tasksTask : tasksTaskAdapter.get_dataSet())
            {
                TasksTaskIndependent tasksTaskIndependent = tasksTask.getIndependentClone();
                if (tasksTaskIndependent.get_id() < 0)
                    tasksTaskIndependent.set_id(0L);
                list.add(tasksTaskIndependent);
            }

        return list;
    }

    private ArrayList<TasksTaskIndependent> get_deletedChilds()
    {
        return _data != null ? _data.get_deletedChilds() : new ArrayList<>();
    }
    //endregion

    //region Save Params
    protected TasksTask GetDataFromForm()
    {
        try
        {
            if (_dependent)
                return new TasksTask(get_id(), get_typeTask(),
                        get_projectId(), get_projectName(),
                        get_parentTask(), get_occuredId(),
                        get_name(), get_comment(), get_priority(),
                        get_dayPlan(), get_monthPlan(), get_yearPlan(), get_hourPlan(), get_minutePlan(),
                        get_dayFact(), get_monthFact(), get_yearFact(), get_hourFact(), get_minuteFact(),
                        get_repeat(), get_repeatType(), get_repeatPer(), get_repeatSubTasks(),
                        get_confirmed(), get_oldConfirmed(),
                        get_status(), get_hasChild(), get_childs(), get_deletedChilds());
            else
                return new TasksTaskIndependent(get_id(), get_typeTask(),
                        get_projectId(), get_projectName(),
                        get_parentTask(), get_occuredId(),
                        get_name(), get_comment(), get_priority(),
                        get_dayPlan(), get_monthPlan(), get_yearPlan(), get_hourPlan(), get_minutePlan(),
                        get_dayFact(), get_monthFact(), get_yearFact(), get_hourFact(), get_minuteFact(),
                        get_repeat(), get_repeatType(), get_repeatPer(), get_repeatSubTasks(),
                        get_confirmed(), get_oldConfirmed(),
                        get_status(), get_hasChild(), get_childs(), get_deletedChilds());
        }
        catch (Exception e)
        {
            Log.d("error", e.getMessage());
            return null;
        }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(TasksTask tasksTask)
    {
        TasksTaskAdapter adapter = (TasksTaskAdapter) _recyclerViewTask.getAdapter();
        if (adapter != null)
        {
            Long id = 0L;
            for (TasksTask item : adapter.get_dataSet())
                id = Math.min(id, item.get_id());
            id--;

            tasksTask.set_id(id);
            _recyclerViewTask.scrollToPosition(adapter.AddItem(tasksTask));
        }

        return true;
    }

    @Override
    public boolean refreshViewOnUpdate(TasksTask tasksTask)
    {
        TasksTaskAdapter adapter = (TasksTaskAdapter) _recyclerViewTask.getAdapter();
        if (adapter != null)
        {
            int scrollPosition = adapter.UpdateItem(tasksTask);

//            if (scrollPosition == -1 &&
//                    _typeAction != EnumTypeAction.Insert && tasksTask.get_id() > 0)
//                _data.add_deletedChilds(tasksTask.getIndependentClone());

            _recyclerViewTask.scrollToPosition(scrollPosition);
        }

        return true;
    }

    @Override
    public boolean refreshViewOnDelete(TasksTask tasksTask)
    {
        ClearFocus();

        if (_typeAction != EnumTypeAction.Insert && tasksTask.get_id() > 0)
            _data.add_deletedChilds(tasksTask.getIndependentClone());

        TasksTaskAdapter adapter = (TasksTaskAdapter) _recyclerViewTask.getAdapter();
        if (adapter != null)
            adapter.DeleteFromDataset(tasksTask);

        return true;
    }

    @Override
    public boolean refreshViewOnSelect(TasksTask tasksTask) { return false; }
    //endregion
}