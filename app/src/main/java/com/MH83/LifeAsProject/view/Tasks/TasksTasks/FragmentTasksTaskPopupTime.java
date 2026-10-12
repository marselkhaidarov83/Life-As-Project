package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerRepeatSubTasksTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.task.SpinnerRepeatTypeAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatSubTasks;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentTasksTaskPopupTime extends CustomPopupBottomFragment<TasksTask>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_tasks_task_time;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    private boolean _repeat;
    private Calendar _todayDate;
    private Calendar _tomorrowDate;
    private Calendar _thisWeekDate;
    private Calendar _onTheWeekendDate;
    private Calendar _nextWeekDate;
    private Calendar _postponeUntilDate;
    private Calendar _date;
    //endregion

    //region Fragment Components
    private TextView _textViewDate;
    private ImageView _imageViewDateMinus;
    private ImageView _imageViewDatePlus;
    private TextView _textViewTime;
    private ImageView _imageViewTimeMinus;
    private ImageView _imageViewTimePlus;
    private TextView _textViewDateTitle;
    private TextView _textViewTodayLabel;
    private TextView _textViewTomorrowLabel;
    private TextView _textViewThisWeekLabel;
    private TextView _textViewOnTheWeekEndLabel;
    private TextView _textViewNextWeekLabel;
    private TextView _textViewPostponeUntilLabel;
    private TextView _textViewWithoutTimeLabel;
    private TextView _textViewToday;
    private TextView _textViewTomorrow;
    private TextView _textViewThisWeek;
    private TextView _textViewOnTheWeekEnd;
    private TextView _textViewNextWeek;
    private TextView _textViewPostponeUntil;
    private TextView _textViewWithoutTime;
    private LinearLayout _linearLayoutToday;
    private LinearLayout _linearLayoutThisWeek;
    private LinearLayout _linearLayoutOnTheWeekEnd;
    private LinearLayout _linearLayoutNextWeek;
    private LinearLayout _linearLayoutPostponeUntil;
    private ConstraintLayout _constraintLayoutRepeat;
    private ImageView _imageViewRepeat;
    private Spinner _spinnerRepeatType;
    private EditText _editTextRepeatPeriod;
    private ImageView _imageViewClearDate;
    private LinearLayout _linearLayoutWithoutTime;
    //endregion

    //region Constructors
    public FragmentTasksTaskPopupTime()
    {
        super(_layoutId,
                R.string.PopupTasksTaskUpdateTime,
                R.string.PopupTasksTaskUpdateTime,
                0);
    }

    public FragmentTasksTaskPopupTime(AppCompatActivity activity,
                                      TasksTask data,
                                      IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                EnumTypeAction.Update,
                null,
                null,
                R.string.PopupTasksTaskUpdateTime,
                R.string.PopupTasksTaskUpdateTime,
                0,
                iFragmentViewCustom,
                null,
                null);

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

        _textViewDate = view.findViewById(R.id.textViewDate);
        _imageViewDateMinus = view.findViewById(R.id.imageViewDateMinus);
        _imageViewDatePlus = view.findViewById(R.id.imageViewDatePlus);
        _textViewDateTitle = view.findViewById(R.id.textViewDateTitle);
        _textViewTime = view.findViewById(R.id.textViewTime);
        _imageViewTimeMinus = view.findViewById(R.id.imageViewTimeMinus);
        _imageViewTimePlus = view.findViewById(R.id.imageViewTimePlus);
        _textViewTodayLabel = view.findViewById(R.id.textViewTodayLabel);
        _textViewTomorrowLabel = view.findViewById(R.id.textViewTomorrowLabel);
        _textViewThisWeekLabel = view.findViewById(R.id.textViewThisWeekLabel);
        _textViewOnTheWeekEndLabel = view.findViewById(R.id.textViewOnTheWeekendLabel);
        _textViewNextWeekLabel = view.findViewById(R.id.textViewNextWeekLabel);
        _textViewPostponeUntilLabel = view.findViewById(R.id.textViewPostponeUntilLabel);
        _textViewWithoutTimeLabel = view.findViewById(R.id.textViewWithoutTimeLabel);
        _textViewToday = view.findViewById(R.id.textViewToday);
        _textViewTomorrow = view.findViewById(R.id.textViewTomorrow);
        _textViewThisWeek = view.findViewById(R.id.textViewThisWeek);
        _textViewOnTheWeekEnd = view.findViewById(R.id.textViewOnTheWeekend);
        _textViewNextWeek = view.findViewById(R.id.textViewNextWeek);
        _textViewPostponeUntil = view.findViewById(R.id.textViewPostponeUntil);
        _textViewWithoutTime = view.findViewById(R.id.textViewWithoutTime);
        _linearLayoutToday = view.findViewById(R.id.linearLayoutToday);
        _linearLayoutThisWeek = view.findViewById(R.id.linearLayoutThisWeek);
        _linearLayoutOnTheWeekEnd = view.findViewById(R.id.linearLayoutOnTheWeekend);
        _linearLayoutNextWeek = view.findViewById(R.id.linearLayoutNextWeek);
        _linearLayoutPostponeUntil = view.findViewById(R.id.linearLayoutPostponeUntil);
        _linearLayoutWithoutTime = view.findViewById(R.id.linearLayoutWithoutTime);
        _constraintLayoutRepeat = view.findViewById(R.id.constraintLayoutRepeat);
        _imageViewRepeat = view.findViewById(R.id.imageViewRepeat);
        _spinnerRepeatType = view.findViewById(R.id.spinnerRepeatType);
        _editTextRepeatPeriod = view.findViewById(R.id.editTextRepeatPeriod);
        _imageViewClearDate = view.findViewById(R.id.imageViewClearDate);

        _focusedViewToAdd = null;
        _focusedViewToUpdate = null;
        _showInputToAdd = false;
        _showInputToUpdate = false;
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
                (int) getContext().getResources().getDimension(R.dimen.Tasks_Task_Popup_Time_Width));

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
    protected void SetComponentsSettingPost(View view)
    {
        super.SetComponentsSettingPost(view);

        SetVisibility();
    }

    private void SetVisibility()
    {
        SetVisibilityLLToday();
        SetVisibilityLLThisWeek();
        SetVisibilityLLOnTheWeekend();
        SetVisibilityLLNextWeek();
        SetVisibilityLLPostponeUntil();
        SetVisibilityLLWithoutTime();
    }

    private void SetVisibilityLLToday()
    {
        if (_date != null &&
                _date.getTime().getTime() >= _todayDate.getTime().getTime())
            _linearLayoutToday.getLayoutParams().height = 0;
    }

    private void SetVisibilityLLThisWeek()
    {
        if (_tomorrowDate.get(Calendar.DATE) == _thisWeekDate.get(Calendar.DATE) &&
                _tomorrowDate.get(Calendar.MONTH) == _thisWeekDate.get(Calendar.MONTH) &&
                _tomorrowDate.get(Calendar.YEAR) == _thisWeekDate.get(Calendar.YEAR))
            _linearLayoutThisWeek.getLayoutParams().height = 0;
    }

    private void SetVisibilityLLOnTheWeekend()
    {
        if (_tomorrowDate.get(Calendar.DATE) == _onTheWeekendDate.get(Calendar.DATE) &&
                _tomorrowDate.get(Calendar.MONTH) == _onTheWeekendDate.get(Calendar.MONTH) &&
                _tomorrowDate.get(Calendar.YEAR) == _onTheWeekendDate.get(Calendar.YEAR))
            _linearLayoutOnTheWeekEnd.getLayoutParams().height = 0;


    }

    private void SetVisibilityLLNextWeek()
    {
        if (_tomorrowDate.get(Calendar.DATE) == _nextWeekDate.get(Calendar.DATE) &&
                _tomorrowDate.get(Calendar.MONTH) == _nextWeekDate.get(Calendar.MONTH) &&
                _tomorrowDate.get(Calendar.YEAR) == _nextWeekDate.get(Calendar.YEAR))
            _linearLayoutNextWeek.getLayoutParams().height = 0;
    }

    private void SetVisibilityLLPostponeUntil()
    {
        _linearLayoutPostponeUntil.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        if (_postponeUntilDate == null)
        {
            _linearLayoutPostponeUntil.getLayoutParams().height = 0;
            return;
        }

        if (_tomorrowDate.get(Calendar.DATE) == _postponeUntilDate.get(Calendar.DATE) &&
                _tomorrowDate.get(Calendar.MONTH) == _postponeUntilDate.get(Calendar.MONTH) &&
                _tomorrowDate.get(Calendar.YEAR) == _postponeUntilDate.get(Calendar.YEAR))
            _linearLayoutPostponeUntil.getLayoutParams().height = 0;
    }

    private void SetVisibilityLLWithoutTime()
    {
        if (_date == null)
            _linearLayoutWithoutTime.getLayoutParams().height = 0;
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetSpinnerRepeatTypeAdapter();
        SetRepeatSubTasksTypeAdapter();
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
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
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

        _imageViewClearDate.setOnClickListener(view ->
        {
            set_date(null);
        });

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

        _textViewTodayLabel.setOnClickListener(view ->
        {
            _date = _todayDate;
            SaveButtonListener();
        });

        _textViewToday.setOnClickListener(view ->
        {
            _date = _todayDate;
            SaveButtonListener();
        });

        _textViewTomorrowLabel.setOnClickListener(view ->
        {
            _date = _tomorrowDate;
            SaveButtonListener();
        });

        _textViewTomorrow.setOnClickListener(view ->
        {
            _date = _tomorrowDate;
            SaveButtonListener();
        });

        _textViewThisWeekLabel.setOnClickListener( view ->
        {
            _date = _thisWeekDate;
            SaveButtonListener();
        });

        _textViewThisWeek.setOnClickListener( view ->
        {
            _date = _thisWeekDate;
            SaveButtonListener();
        });

        _textViewOnTheWeekEndLabel.setOnClickListener( view ->
        {
            _date = _onTheWeekendDate;
            SaveButtonListener();
        });

        _textViewOnTheWeekEnd.setOnClickListener( view ->
        {
            _date = _onTheWeekendDate;
            SaveButtonListener();
        });

        _textViewNextWeekLabel.setOnClickListener( view ->
        {
            _date = _nextWeekDate;
            SaveButtonListener();
        });

        _textViewNextWeek.setOnClickListener( view ->
        {
            _date = _nextWeekDate;
            SaveButtonListener();
        });

        _textViewPostponeUntilLabel.setOnClickListener( view ->
        {
            _date = _postponeUntilDate;
            SaveButtonListener();
        });

        _textViewPostponeUntil.setOnClickListener( view ->
        {
            _date = _postponeUntilDate;
            SaveButtonListener();
        });

        _textViewWithoutTimeLabel.setOnClickListener(view ->
        {
            _date = null;
            SaveButtonListener();
        });

        _textViewWithoutTime.setOnClickListener(view ->
        {
            _date = null;
            SaveButtonListener();
        });
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
        if (_data != null)
        {
            set_date(_data.get_calendarPlan());
            set_repeat(_data.get_repeat());
            set_repeatType(_data.get_repeatType());
            set_repeatPeriod(_data.get_repeatPer());

            CalculateTodayDate();
            CalculateTomorrowDate();
            CalculateThisWeekDate();
            CalculateOnTheWeekendDate();
            CalculateNextWeekDate();
            CalculatePostponeUntilDate();

            setTodayDate(_todayDate);
            setTomorrowDate(_tomorrowDate);
            setThisWeekDate(_thisWeekDate);
            setOnTheWeekendDate(_onTheWeekendDate);
            setNextWeekDate(_nextWeekDate);
            setPostponeUntilDate(_postponeUntilDate);
        }
        else
        {
            set_date(Calendar.getInstance());
            set_repeat(false);
            set_repeatType(EnumRepeatType.Day);
            set_repeatPeriod(1);
        }
    }

    private void CalculateTodayDate()
    {
        _todayDate = Calendar.getInstance();
        _todayDate.set(Calendar.HOUR_OF_DAY, get_hourPlan());
        _todayDate.set(Calendar.MINUTE, get_minutePlan());
        _todayDate.set(Calendar.SECOND, 0);
        _todayDate.set(Calendar.MILLISECOND, 0);
    }

    private void CalculateTomorrowDate()
    {
        _tomorrowDate = Calendar.getInstance();
        _tomorrowDate.set(Calendar.HOUR_OF_DAY, get_hourPlan());
        _tomorrowDate.set(Calendar.MINUTE, get_minutePlan());
        _tomorrowDate.add(Calendar.DATE, 1);
    }

    private void CalculateThisWeekDate()
    {
        _thisWeekDate = Calendar.getInstance();
        _thisWeekDate.set(Calendar.HOUR_OF_DAY, get_hourPlan());
        _thisWeekDate.set(Calendar.MINUTE, get_minutePlan());
        _thisWeekDate.add(Calendar.DATE, 1);
        while (_thisWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY &&
                _thisWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                _thisWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            _thisWeekDate.add(Calendar.DATE, 1);
    }

    private void CalculateOnTheWeekendDate()
    {
        _onTheWeekendDate = Calendar.getInstance();
        _onTheWeekendDate.set(Calendar.HOUR_OF_DAY, get_hourPlan());
        _onTheWeekendDate.set(Calendar.MINUTE, get_minutePlan());
        _onTheWeekendDate.add(Calendar.DATE, 1);
        while (_onTheWeekendDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                _thisWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            _onTheWeekendDate.add(Calendar.DATE, 1);
    }

    private void CalculateNextWeekDate()
    {
        _nextWeekDate = Calendar.getInstance();
        _nextWeekDate.set(Calendar.HOUR_OF_DAY, get_hourPlan());
        _nextWeekDate.set(Calendar.MINUTE, get_minutePlan());
        _nextWeekDate.add(Calendar.DATE, 1);
        while (_nextWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
            _nextWeekDate.add(Calendar.DATE, 1);
    }

    private void CalculatePostponeUntilDate()
    {
        if (!_data.get_repeat())
            return;

        try
        {
            _postponeUntilDate = _data.CalculateNextTaskDate(get_repeatType(), get_repeatPer());
        }
        catch (Exception e)
        {
            _postponeUntilDate = null;
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
            _date = calendar;
            _textViewDate.setText("");
            _textViewTime.setText("");
            SetEnabled4Repeat(false);
            return;
        }

        SetEnabled4Repeat(true);
        _date = (Calendar) calendar.clone();
//        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        globalVariable.set_MAccDate(_date);

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

        _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeData));
    }

    private void set_repeat(boolean repeat)
    {
        _repeat = repeat;
        _imageViewRepeat.setImageResource(_repeat ? R.drawable.icons_is_fact_100 :
                R.drawable.icons_is_plan_100);
        _spinnerRepeatType.setEnabled(_repeat);
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

    private void set_repeatPeriod(int repeatPeriod)
    {
        _editTextRepeatPeriod.setText(String.valueOf(repeatPeriod));
    }

    private void setTodayDate(Calendar calendar)
    {
        CustomStaticClass.setDayOfWeek(_activity.getBaseContext(), calendar, _textViewToday);
    }

    private void setTomorrowDate(Calendar calendar)
    {
        CustomStaticClass.setDayOfWeek(_activity.getBaseContext(), calendar, _textViewTomorrow);
    }

    private void setThisWeekDate(Calendar calendar)
    {
        CustomStaticClass.setDayOfWeek(_activity.getBaseContext(), calendar, _textViewThisWeek);
    }

    private void setOnTheWeekendDate(Calendar calendar)
    {
        CustomStaticClass.setDayOfWeek(_activity.getBaseContext(), calendar, _textViewOnTheWeekEnd);
    }

    private void setNextWeekDate(Calendar calendar)
    {
        CustomStaticClass.setDayOfWeek(_activity.getBaseContext(), calendar, _textViewNextWeek);
    }

    private void setPostponeUntilDate(Calendar calendar)
    {
        if (calendar != null)
            CustomStaticClass.setDayOfWeek(_activity.getBaseContext(), calendar, _textViewPostponeUntil);
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return false;
    }

    @Override
    protected boolean CheckFormValues()
    {
        if (!CheckRepeatPeriod())       return false;

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

    @Override
    protected boolean CheckFormValuesForDelete()
    {
        return false;
    }
    //endregion

    //region Get Params
    private Long get_id()
    {
        return _data.get_id();
    }

    private EnumTypeTask get_typeTask() { return _data.get_type(); }

    private Long get_projectId()
    {
        return _data.get_project_id();
    }

    private String get_projectName()
    {
        return _data.get_projectName();
    }

    private Long get_priority()
    {
        return _data.get_priority();
    }

    private Long get_parentId()
    {
        return _data.get_parentId();
    }

    private Long get_occuredId()
    {
        return _data.get_occured_id();
    }

    private String get_name()
    {
        return _data.get_name();
    }

    private String get_comment()
    {
        return _data.get_comment();
    }

    private int get_dayPlan() { return _date != null ? _date.get(Calendar.DATE) : 0; }

    private int get_monthPlan()
    {
        return _date != null ? _date.get(Calendar.MONTH) : 0;
    }

    private int get_yearPlan()
    {
        return _date != null ? _date.get(Calendar.YEAR) : 0;
    }

    private int get_hourPlan()
    {
        return _date != null ? _date.get(Calendar.HOUR_OF_DAY) : 0;
    }

    private int get_minutePlan()
    {
        return _date != null ? _date.get(Calendar.MINUTE) : 0;
    }

    private int get_dayFact() { return _data.get_day_fact(); }

    private int get_monthFact() { return _data.get_month_fact(); }

    private int get_yearFact() { return _data.get_year_fact(); }

    private int get_hourFact() { return _data.get_hour_fact(); }

    private int get_minuteFact() { return _data.get_minute_fact(); }

    private boolean get_repeat() { return _repeat; }

    private EnumRepeatType get_repeatType()
    {
        return ((EnumRepeatType) _spinnerRepeatType.getSelectedItem());
    }

    private int get_repeatPer() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextRepeatPeriod.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private EnumRepeatSubTasks get_repeatSubTasks()
    {
        return _data.get_repeatSubTasks();
    }

    private boolean get_confirmed() { return _data.get_confirmed(); }

    private boolean get_oldConfirmed() { return _data.get_oldConfirmed(); }

    private EnumTaskStatus get_status() { return _data.get_status(); }

    private boolean get_hasChild() { return _data.get_hasChild(); }

    private ArrayList<TasksTaskIndependent> get_childs() { return _data.get_childs(); }

    private ArrayList<TasksTaskIndependent> get_deletedChilds() { return _data.get_deletedChilds(); }
    //endregion

    //region Save Params
    protected TasksTask GetDataFromForm()
    {
        try
        {
            return new TasksTaskIndependent(get_id(), get_typeTask(),
                    get_projectId(), get_projectName(),
                    get_parentId(), get_occuredId(),
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
}