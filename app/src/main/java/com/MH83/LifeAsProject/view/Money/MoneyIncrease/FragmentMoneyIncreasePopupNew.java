package com.MH83.LifeAsProject.view.Money.MoneyIncrease;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FragmentMoneyIncreasePopupNew extends CustomPopupBottomFragment<MoneyIncrease>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_increase_new;
    private static final String dateFormat = "dd.MM.yyyy";
    //endregion

    //region Fragment parameters
    private Long _id;
    private String _month;
    private String _year;
    private Integer _prevValue;
//    private boolean _existIncoming;
    private boolean _editOnlySum;
    Integer _value;
    private boolean _archive = false;
    //endregion

    //region Fragment Components
    private TextView _textViewDate;
    private TextView _textViewDatePrev;
    private TextView _textViewDateNext;
    private TextView _textViewPrevValue;
    private TextView _textViewNextValue;
    private ImageView _imageViewDateBack;
    private ImageView _imageViewDateForward;
    private EditText editTextValue;
    private ImageView _imageViewArchive;
    //endregion

    //region Constructors
    public FragmentMoneyIncreasePopupNew()
    {
        super(_layoutId,
                R.string.PopupIncreaseAdd,
                R.string.PopupIncreaseUpdate,
                R.string.errorUpdateMoneyIncrease);
    }

    public FragmentMoneyIncreasePopupNew(AppCompatActivity activity,
                                         MoneyIncrease data,
                                         EnumTypeAction typeAction,
                                         IFragmentViewCustom<MoneyIncrease> iFragmentViewCustom,
                                         boolean editOnlySum,
                                         Long id,
                                         String month, String year, Integer value
//            , boolean existIncoming
    )
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                R.string.PopupIncreaseAdd,
                R.string.PopupIncreaseUpdate,
                R.string.errorUpdateMoneyIncrease,
                iFragmentViewCustom,
                null,
                null);

        _editOnlySum = editOnlySum;
        _id = id;
        _month = month;
        _year = year;
        _value = value;
//        _existIncoming = existIncoming;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_dateMonth(_month);
        globalVariable.set_dateYear(_year);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _month = globalVariable.get_dateMonth();
        _year = globalVariable.get_dateYear();
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _textViewDate = view.findViewById(R.id.textViewDate);
        _textViewDatePrev = view.findViewById(R.id.textViewDatePrev);
        _textViewDateNext = view.findViewById(R.id.textViewDateNext);
        _textViewPrevValue = view.findViewById(R.id.textViewPrevValue);
        _textViewNextValue = view.findViewById(R.id.textViewNextValue);
        _imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        _imageViewDateForward = view.findViewById(R.id.imageViewDateForward);
        editTextValue = view.findViewById(R.id.editTextValue);
        _imageViewArchive = view.findViewById(R.id.imageViewArchive);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = editTextValue;
        _focusedViewToUpdate = editTextValue;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        if (_editOnlySum)
        {
            _imageViewDateBack.setVisibility(View.INVISIBLE);
            _imageViewDateForward.setVisibility(View.INVISIBLE);
            _textViewDate.setBackgroundResource(R.color.white);
            _textViewDate.setBackgroundResource(R.color.white);
        }
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_id, _month, _year, _value
//                , _existIncoming
        });
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

        getDialog().getWindow().setLayout(
                (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Increase_Width) /*our width*/,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewArchive.setOnClickListener(view ->
        {
            _archive = !_archive;
            setArchiveImage(_archive);
        });

        if (!_editOnlySum)
            _textViewDate.setOnClickListener(view1 ->
            {
                try
                {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            (datePicker, year1, month1, day) ->
                            {
                                String monthS;
                                if (month1 + 1 < 10)
                                    monthS = "0" + (month1 + 1);
                                else
                                    monthS = String.valueOf(month1 + 1);
                                setDate(monthS, String.valueOf(year1));
                                setPrevValue(monthS, String.valueOf(year1));
                                setNextValue(monthS, String.valueOf(year1));
                            },
                            Integer.parseInt(getYear()), Integer.parseInt(getMonth()) - 1, Integer.parseInt(getDay()));
                    datePickerDialog.show();
                }
                catch (Exception e)
                {
                    if(e.getMessage() != null)
                        Log.d("Error datePickerDialog", e.getMessage());
                }
            });

        _imageViewDateBack.setOnClickListener(view1 ->
        {
            setPrevMonthDate();
            try
            {
                setPrevValue(getMonth(), getYear());
                setNextValue(getMonth(), getYear());
            }
            catch (Exception e) { }
        });

        _imageViewDateForward.setOnClickListener(view2 ->
        {
            setNextMonthDate();
            try
            {
                setPrevValue(getMonth(), getYear());
                setNextValue(getMonth(), getYear());
            }
            catch (Exception e) { }
        });
    }
    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        Long id = (Long) objects[0];
        String month = (String) objects[1];
        String year = (String) objects[2];

        setPrevValue(month, year);
        setNextValue(month, year);
        set_Id(id);
        setDate(month, year);
        setCurValue((Integer) objects[3]);
        if (_data != null)
        {
            set_archive(_data.get_archive());
        }
        else
        {
            set_archive(false);
        }
//        setExistIncoming((boolean)objects[4]);
    }

    private void set_Id(Long id)
    {
        if (id != 0)
            _id = id;
    }

    private void setDate(String month, String year)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (month != null && year != null)
        {
            _month = String.valueOf(Integer.parseInt(month));
            _year = String.valueOf(Integer.parseInt(year));
            setDateView(month, year, _textViewDate);
            try { CheckDateExist(); }
            catch (Exception e) { }

            globalVariable.set_dateMonth(_month);
            globalVariable.set_dateYear(_year);
        }
        else
        {
            _month = "";
            _year = "";
            _textViewDate.setText("");

            globalVariable.set_dateMonth(null);
            globalVariable.set_dateYear(null);
        }
    }

    private void setCurValue(Number value)
    {
        if (value == null)
            editTextValue.setText("");
        else
            editTextValue.setText(value.toString());
    }

    private void set_archive(boolean archive)
    {
        _archive = archive;
        setArchiveImage(archive);
    }

    private void setArchiveImage(boolean archive)
    {
        if (archive)
            _imageViewArchive.setImageResource(R.drawable.icon_archive);
        else
            _imageViewArchive.setImageResource(R.drawable.icons_nonarchive);
    }

    //endregion

    //region Check Params
    private void setPrevValue(String month, String year)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
        {
            Cursor cursor = dbHelper.QueryMoneyIncreasePrevValue(month, year);

            if (cursor != null && cursor.moveToFirst())
            {
                int valueIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_VALUE);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_MONTH);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_YEAR);

                _prevValue = cursor.getInt(valueIndex);
                _textViewPrevValue.setText(cursor.getString(valueIndex));
                String monthPrev = cursor.getString(monthIndex);
                String yearPrev = cursor.getString(yearIndex);
                setDateView(monthPrev, yearPrev, _textViewDatePrev);
            }
            else
            {
                _prevValue = null;
                _textViewPrevValue.setText("");
                setDateView("", "", _textViewDatePrev);
            }
        }
        catch (Exception e) { }
    }

    private void setNextValue(String month, String year)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
        {
            Cursor cursor = dbHelper.QueryMoneyIncreaseNextValue(month, year);

            if (cursor != null && cursor.moveToFirst())
            {
                int valueIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_VALUE);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_MONTH);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_YEAR);

                _textViewNextValue.setText(cursor.getString(valueIndex));
                String monthNext = cursor.getString(monthIndex);
                String yearNext = cursor.getString(yearIndex);
                setDateView(monthNext, yearNext, _textViewDateNext);
            }
            else
            {
                _textViewNextValue.setText("");
                setDateView("", "", _textViewDateNext);
            }
        }
        catch (Exception e) {}
    }

    private void setExistIncoming(boolean existIncoming)
    {
//        _existIncoming = existIncoming;
    }
    //endregion

    //region Change Params
    private void setPrevMonthDate()
    {
        int month, year;
        try
        {
            month = Integer.parseInt(getMonth());
            year = Integer.parseInt(getYear());
            if (month == 1)
            {
                month = 12;
                year--;
            }
            else
                month--;
            setDate(String.valueOf(month), String.valueOf(year));
        }
        catch (Exception e) { }
    }

    private void setNextMonthDate()
    {
        int month, year;
        try
        {
            month = Integer.parseInt(getMonth());
            year = Integer.parseInt(getYear());
            if (month == 12)
            {
                month = 1;
                year++;
            }
            else
                month++;
            setDate(String.valueOf(month), String.valueOf(year));
        }
        catch (Exception e) { }
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
//        return _data != null && !_existIncoming;
        return _data != null && _data.canDelete(getContext());
    }

//    @Override
    protected boolean CheckFormValues()
    {
        try
        {
            getDateCurValue();
        }
        catch (Exception e)
        {
            _textViewDate.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_textViewDate, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_activity,
                    getContext().getString(R.string.specifyCorrectDate), Toast.LENGTH_SHORT).show();
            return false;
        }

        try
        {
            getCurValue();
        }
        catch (Exception e)
        {
            editTextValue.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_activity,
                    getContext().getString(R.string.specifyCorrectSumm), Toast.LENGTH_SHORT).show();
            return false;
        }

        try
        {
            if (CheckDateExist())
                return false;
        }
        catch (Exception e) { }

        return true;
    }

    private boolean CheckDateExist() throws Exception
    {
        try(DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
        {
            boolean exist = dbHelper.CheckDateExist(get_Id(), getMonth(), getYear());
            if (exist)
            {
                Toast.makeText(_activity,
                        getContext().getString(R.string.errorExistValueOnDate), Toast.LENGTH_SHORT).show();
                _textViewDate.setTextColor(getContext().getColor(R.color.red));
            }
            else
            {
                _textViewDate.setTextColor(getContext().getColor(R.color.black));
            }
            return exist;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    protected boolean CheckFormValuesForDelete() { return true; }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        if (_data != null)
            return _data.get_id();

        return _id;
    }

    private void getDateCurValue() throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        String value = getDateSCurValue();
        try
        {
            formatter.parse(value);
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private String getDay()
    {
        return "01";
    }

    private String getMonth() throws Exception
    {
        try
        {
            int month = Integer.parseInt(_month);
            if (month < 10)
                return "0" + month;
            else
                return String.valueOf(month);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private String getYear() throws Exception
    {
        try
        {
            return _year;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private String getDateSCurValue()
    {
        String month = _month;
        if (month.length() == 1)
            month = "0" + month;
        return "01." + month + "." + _year;
    }

    private Integer getCurValue() throws Exception
    {
        try
        {
            String value = editTextValue.getText().toString();
            return Integer.parseInt(value);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private Float getPercent() throws Exception
    {
        if (_prevValue == null)
            return null;

        return 100 * (getCurValue() - _prevValue) / (float) _prevValue;
    }

    private boolean getArchive()
    {
        return _archive;
    }

    private boolean getExistIncoming()
    {
        return _data != null && _data.getExistIncoming();
//        return _existIncoming;
    }
    //endregion

    //region Save Params
    protected MoneyIncrease GetDataFromForm()
    {
        try
        {
            return new MoneyIncrease(get_Id(), getDay(), getMonth(), getYear(), getCurValue(),
                    0, getPercent(), getArchive(), getExistIncoming());
        }
        catch (Exception e) { return null; }
    }
    //endregion
}