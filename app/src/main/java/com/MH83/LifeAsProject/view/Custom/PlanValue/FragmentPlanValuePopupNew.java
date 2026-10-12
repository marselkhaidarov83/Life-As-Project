package com.MH83.LifeAsProject.view.Custom.PlanValue;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerEnumPlanValueTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerParamAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerPlanValueAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Custom.ParamDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumPlanValueType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.Param;
import com.MH83.LifeAsProject.model.Custom.PlanValue;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentPlanValuePopupNew extends CustomPopupBottomFragment<PlanValue>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_planvalue_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    private Long _target_id;
    private List<PlanValue> _planValuesFromTarget;
    //endregion

    //region Fragment Components
    private Spinner _spinnerParam;
    private TextView _textViewMeasure;
    private Spinner _spinnerType;
    private EditText _editTextKoef;
    private Spinner _spinnerParamSrc1;
    private TextView _textViewMeasureSrc1;
    private Spinner _spinnerParamSrc2;
    private TextView _textViewMeasureSrc2;
    private EditText _editTextValue;
    //endregion

    //region Constructors
    public FragmentPlanValuePopupNew()
    {
        super(_layoutId,
                R.string.PopupPlanValueAdd,
                R.string.PopupPlanValueUpdate,
                R.string.errorUpdatePlanValue);
    }

    public FragmentPlanValuePopupNew(AppCompatActivity activity,
                                     PlanValue data,
                                     EnumTypeAction typeAction,
                                     EnumTypeAction typeAction2,
                                     IFragmentViewCustom iFragmentViewCustom,
                                     IFragmentViewCustom iFragmentViewCustom2,
                                     Long target_id,
                                     List<PlanValue> planValuesFromTarget)
    {
        super(activity, _layoutId, data,
                typeAction,
                typeAction2,
                null,
                R.string.PopupPlanValueAdd,
                R.string.PopupPlanValueUpdate,
                R.string.errorUpdatePlanValue,
                iFragmentViewCustom,
                iFragmentViewCustom2,
                null);

        _target_id = target_id;
        _planValuesFromTarget = planValuesFromTarget;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_target_id(target_id);
        globalVariable.set_planValues(_planValuesFromTarget);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _target_id = globalVariable.get_target_id();
        _planValuesFromTarget = globalVariable.get_planValues();
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerParam = view.findViewById(R.id.spinnerParam);
        _textViewMeasure = view.findViewById(R.id.textViewMeasure);
        _spinnerType = view.findViewById(R.id.spinnerType);
        _editTextKoef = view.findViewById(R.id.editTextKoef);
        _spinnerParamSrc1 = view.findViewById(R.id.spinnerParamSrc1);
        _textViewMeasureSrc1 = view.findViewById(R.id.textViewMeasureSrc1);
        _spinnerParamSrc2 = view.findViewById(R.id.spinnerParamSrc2);
        _textViewMeasureSrc2 = view.findViewById(R.id.textViewMeasureSrc2);
        _editTextValue = view.findViewById(R.id.editTextValue);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _spinnerParam;
        _focusedViewToUpdate = _spinnerParam;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        FillEnabled(_data == null ? EnumPlanValueType.Value : _data.get_type());
    }

    private void FillEnabled(EnumPlanValueType type)
    {
        if (type == EnumPlanValueType.Koef || type == EnumPlanValueType.InverseKoef)
        {
            _editTextKoef.setEnabled(true);
            _spinnerParamSrc1.setEnabled(true);
            _spinnerParamSrc2.setEnabled(false);
            _editTextValue.setEnabled(false);
        }
        else if (type == EnumPlanValueType.Sum || type == EnumPlanValueType.Diff ||
                type == EnumPlanValueType.Dividing || type == EnumPlanValueType.Multiplication)
        {
            _editTextKoef.setEnabled(false);
            _spinnerParamSrc1.setEnabled(true);
            _spinnerParamSrc2.setEnabled(true);
            _editTextValue.setEnabled(false);
            _editTextKoef.setText("");
        }
        else
        {
            _editTextKoef.setEnabled(false);
            _spinnerParamSrc1.setEnabled(false);
            _spinnerParamSrc2.setEnabled(false);
            _editTextValue.setEnabled(true);
            _editTextKoef.setText("");
        }

        _spinnerParam.setEnabled(_typeAction2 == EnumTypeAction.Insert);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetSpinnerParamAdapter();
        SetSpinnerParamType();
    }

    private void SetSpinnerParamAdapter()
    {
        List<Param> params = ParamDataSource.LoadData(_activity.getBaseContext());

        List<Param> list = new ArrayList<>();
        List<PlanValue> list2 = new ArrayList<>();

        list2.add(new PlanValue(0L, _target_id, 0L, EnumPlanValueType.Value,
                0, 0, 0L, 0L, "", ""));

        for (Param param: params)
        {
            boolean paramInPV = false;

            for (PlanValue planValue: _planValuesFromTarget)
            {
                if (param.get_id() == planValue.get_param_id())
                {
                    list2.add(planValue);
                    paramInPV = true;
                    break;
                }
            }

            if (!paramInPV)
                list.add(param);
        }

        if (_typeAction2 == EnumTypeAction.Update)
            list.add(new Param(_data.get_param_id(), _data.get_name(), "", _data.get_measure()));

        SpinnerParamAdapter adapter = new SpinnerParamAdapter(_activity.getBaseContext(), list);
        _spinnerParam.setAdapter(adapter);

        SpinnerPlanValueAdapter adapter2 = new SpinnerPlanValueAdapter(_activity.getBaseContext(), list2);
        _spinnerParamSrc1.setAdapter(adapter2);

        SpinnerPlanValueAdapter adapter3 = new SpinnerPlanValueAdapter(_activity.getBaseContext(), list2);
        _spinnerParamSrc2.setAdapter(adapter3);
    }

    private void SetSpinnerParamType()
    {
        List<EnumPlanValueType> list = new ArrayList<>();
        list.add(EnumPlanValueType.Value);
        list.add(EnumPlanValueType.Koef);
        list.add(EnumPlanValueType.InverseKoef);
        list.add(EnumPlanValueType.Sum);
        list.add(EnumPlanValueType.Diff);
        list.add(EnumPlanValueType.Multiplication);
        list.add(EnumPlanValueType.Dividing);

        SpinnerEnumPlanValueTypeAdapter adapter =
                new SpinnerEnumPlanValueTypeAdapter(_activity.getBaseContext(), list);
        _spinnerType.setAdapter(adapter);
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_data} );
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

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
//            getDialog().getWindow().setLayout(
//                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width) /*our width*/,
//                    ViewGroup.LayoutParams.MATCH_PARENT);
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.PlanValuePopupWidth),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

//            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
//            else
//            {
//                getDialog().getWindow().setLayout(
//                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width) /*our width*/,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//            }
        }
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _spinnerParam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                setMeasure(((Param)_spinnerParam.getSelectedItem()).get_measure());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                setMeasure("");
            }
        });

        _spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                EnumPlanValueType type = (EnumPlanValueType) _spinnerType.getSelectedItem();

                CalcValue();
                FillEnabled(type);
                view.requestLayout();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        _editTextKoef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                CalcValue();
            }
        });

        _spinnerParamSrc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                setMeasureSrc1(((PlanValue)_spinnerParamSrc1.getSelectedItem()).get_measure());
                CalcValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                setMeasureSrc1("");
            }
        });

        _spinnerParamSrc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                setMeasureSrc2(((PlanValue)_spinnerParamSrc2.getSelectedItem()).get_measure());
                CalcValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                setMeasureSrc2("");
            }
        });
    }

    @Override
    protected void ClearFocus()
    {
        super.ClearFocus();

        InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextValue.getWindowToken(), 0);
    }

    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        _data = (PlanValue) objects[0];

        if (_data != null)
        {
            setParam(_data.get_param_id());
            setType(_data.get_type());
            setCoef(String.valueOf(_data.get_coeff()));
            setParamSrc1(_data.get_src_planValue_id1());
            setParamSrc2(_data.get_src_planValue_id2());
            setValue(String.valueOf(_data.get_value()));
        }
        else
        {
            setParam(0L);
            setType(EnumPlanValueType.Value);
            setCoef("");
            setParamSrc1(0L);
            setParamSrc2(0L);
            setValue("");
        }
    }

    private void setParam(Long id)
    {
        setMeasure("");
        for (int i = 0; i < _spinnerParam.getAdapter().getCount(); i++)
            if (Objects.equals(((Param) _spinnerParam.getAdapter().getItem(i)).get_id(), id))
            {
                _spinnerParam.setSelection(i);
                setMeasure(((Param)_spinnerParam.getAdapter().getItem(i)).get_measure());
                return;
            }

        if (_spinnerParam.getAdapter().getCount() > 0)
        {
            _spinnerParam.setSelection(0);
            setMeasure(((Param)_spinnerParam.getAdapter().getItem(0)).get_measure());
        }
        else
            setMeasure("");
    }

    private void setMeasure(String measure)
    {
        _textViewMeasure.setText(measure);
    }

    private void setType(EnumPlanValueType type)
    {
        for (int i = 0; i < _spinnerType.getAdapter().getCount(); i++)
            if (_spinnerType.getAdapter().getItem(i) == type)
            {
                _spinnerType.setSelection(i);
                break;
            }
    }

    private void setCoef(String coef)
    {
        _editTextKoef.setText(coef);
    }

    private void setParamSrc1(Long id)
    {
        setMeasureSrc1("");
        for (int i = 0; i < _spinnerParamSrc1.getAdapter().getCount(); i++)
            if (Objects.equals(((PlanValue) _spinnerParamSrc1.getAdapter().getItem(i)).get_id(), id))
            {
                _spinnerParamSrc1.setSelection(i);
                setMeasure(((PlanValue)_spinnerParamSrc1.getAdapter().getItem(i)).get_measure());
                return;
            }

        if (_spinnerParamSrc1.getAdapter().getCount() > 0)
        {
            _spinnerParamSrc1.setSelection(0);
            setMeasureSrc1(((PlanValue)_spinnerParamSrc1.getAdapter().getItem(0)).get_measure());
        }
        else
            setMeasureSrc1("");
    }

    private void setParamSrc2(Long id)
    {
        setMeasureSrc2("");
        for (int i = 0; i < _spinnerParamSrc2.getAdapter().getCount(); i++)
            if (Objects.equals(((PlanValue) _spinnerParamSrc2.getAdapter().getItem(i)).get_id(), id))
            {
                _spinnerParamSrc2.setSelection(i);
                setMeasure(((PlanValue)_spinnerParamSrc2.getAdapter().getItem(i)).get_measure());
                return;
            }

        if (_spinnerParamSrc2.getAdapter().getCount() > 0)
        {
            _spinnerParamSrc2.setSelection(0);
            setMeasureSrc2(((PlanValue)_spinnerParamSrc2.getAdapter().getItem(0)).get_measure());
        }
        else
            setMeasureSrc2("");
    }

    private void setMeasureSrc1(String measure)
    {
        _textViewMeasureSrc1.setText(measure);
    }

    private void setMeasureSrc2(String measure)
    {
        _textViewMeasureSrc2.setText(measure);
    }

    private void setValue(String value)
    {
        _editTextValue.setText(value);
    }
    //endregion

    //region ChangeParams
    private void CalcValue()
    {
        EnumPlanValueType type = get_type();
        PlanValue planValue1 = get_planValue1();
        PlanValue planValue2 = get_planValue2();

        double coef;
        try { coef = get_coeff(); }
        catch (Exception e) { coef = 0d;}

        switch (type)
        {
            case Koef:
                setValue(String.valueOf(((int) (100 * planValue1.get_value() * coef)) / 100d));
                break;
            case InverseKoef:
                setValue(String.valueOf(((int) (100 * planValue1.get_value() / coef) / 100d)));
                break;
            case Diff:
                setValue(String.valueOf(((int) (100 * (planValue1.get_value() - planValue2.get_value()))) / 100d));
                break;
            case Sum:
                setValue(String.valueOf(((int) (100 * (planValue1.get_value() + planValue2.get_value()))) / 100d));
                break;
            case Multiplication:
                setValue(String.valueOf(((int) (100 * planValue1.get_value() * planValue2.get_value())) / 100d));
                break;
            case Dividing:
                setValue(String.valueOf(((int) (100 * planValue1.get_value() / planValue2.get_value())) / 100d));
                break;
            default:
        }
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        if (!CheckParam())              return false;
        if (!CheckCoef())               return false;
        if (!CheckPlanValueSrc1())      return false;
        if (!CheckPlanValueSrc2())      return false;
        if (!CheckValue())              return false;

        return true;
    }

    private boolean CheckParam()
    {
        try
        {
            Long paramId = get_param_id();
            if (paramId == 0)
            {
                _spinnerParam.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_spinnerParam, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyParam), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _spinnerParam.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_spinnerParam, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectParam), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckCoef()
    {
        EnumPlanValueType type = get_type();

        if (type == EnumPlanValueType.Value ||
            type == EnumPlanValueType.Sum ||
                type == EnumPlanValueType.Diff ||
                type == EnumPlanValueType.Multiplication ||
                type == EnumPlanValueType.Dividing)
            return true;

        try
        {
            double coef = get_coeff();
            if (coef == 0)
            {
                _editTextKoef.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextKoef, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyCoef), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextKoef.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextKoef, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectCoef), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckPlanValueSrc1()
    {
        if (get_type() == EnumPlanValueType.Value)
            return true;

        try
        {
            Long id = get_planValueSrc1();
            if (id == 0L)
            {
                _spinnerParamSrc1.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_spinnerParamSrc1, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyPlanValueSrc1), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _spinnerParamSrc1.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_spinnerParamSrc1, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectPlanValueSrc1), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckPlanValueSrc2()
    {
        if (get_type() == EnumPlanValueType.Value ||
                get_type() == EnumPlanValueType.Koef ||
                get_type() == EnumPlanValueType.InverseKoef)
            return true;

        try
        {
            Long id = get_planValueSrc2();
            if (id == 0)
            {
                _spinnerParamSrc2.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_spinnerParamSrc2, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyPlanValueSrc2), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _spinnerParamSrc2.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_spinnerParamSrc2, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectPlanValueSrc2), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckValue()
    {
        try
        {
            double value = get_value();
            if (value == 0)
            {
                _editTextValue.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextValue, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyValue), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextValue.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextValue, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectValue), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
//        if (_baseParentId != 0)
//            return true;
//        else
//        {
//            if (_hasChild)
//            {
//                Toast.makeText(_iFragmentViewCustom.getActivity(),
//                        getContext().getString(R.string.errorExistChildsMoneyCategory), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            else
//                return true;
//        }
        return true;
    }
    //endregion

    //region Get Params
    private Long get_id() throws Exception
    {
        if (_data != null)
            return _data.get_id();

        Long maxId;
        try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
        {
            maxId = dbHelper.QueryPLanValueMaxId();
        }
        catch (Exception e) { throw new Exception(e.getMessage()); }

        if (_spinnerParamSrc1.getAdapter().getCount() > 1)
            for (int i = 1; i < _spinnerParamSrc1.getAdapter().getCount(); i++)
                if (maxId < ((PlanValue) _spinnerParamSrc1.getAdapter().getItem(i)).get_id())
                    maxId = ((PlanValue) _spinnerParamSrc1.getAdapter().getItem(i)).get_id();

        return maxId + 1;
    }

    private Long get_target_id()
    {
        return _data == null ? _target_id : _data.get_target_id();
    }

    private Long get_param_id()
    {
        return ((Param) _spinnerParam.getSelectedItem()).get_id();
    }

    private EnumPlanValueType get_type()
    {
        return (EnumPlanValueType) _spinnerType.getSelectedItem();
    }

    private Double get_coeff() throws Exception
    {
        EnumPlanValueType type = get_type();

        if (type == EnumPlanValueType.Value ||
                type == EnumPlanValueType.Sum ||
                type == EnumPlanValueType.Diff ||
                type == EnumPlanValueType.Multiplication ||
                type == EnumPlanValueType.Dividing)
            return 0d;

        try
        {
            return Double.parseDouble(_editTextKoef.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Double get_value() throws Exception
    {
        try
        {
            return Double.parseDouble(_editTextValue.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private PlanValue get_planValue1()
    {
        if (_spinnerParamSrc1.getAdapter().getCount() == 0)
            return null;

        return ((PlanValue) _spinnerParamSrc1.getSelectedItem());
    }

    private Long get_planValueSrc1()
    {
        if (get_type() == EnumPlanValueType.Value)
            return 0L;

        return ((PlanValue) _spinnerParamSrc1.getSelectedItem()).get_id();
    }

    private PlanValue get_planValue2()
    {
        if (_spinnerParamSrc2.getAdapter().getCount() == 0)
            return null;

        return ((PlanValue) _spinnerParamSrc2.getSelectedItem());
    }

    private Long get_planValueSrc2()
    {
        if (get_type() == EnumPlanValueType.Value ||
                get_type() == EnumPlanValueType.Koef ||
                get_type() == EnumPlanValueType.InverseKoef)
            return 0L;

    return ((PlanValue) _spinnerParamSrc2.getSelectedItem()).get_id();
    }

    private String get_name()
    {
        return ((Param) _spinnerParam.getSelectedItem()).get_name();
    }

    private String get_measure()
    {
        return (String) _textViewMeasure.getText();
    }
    //endregion

    //region Save Params
    protected PlanValue GetDataFromForm()
    {
        try
        {
            return new PlanValue(get_id(), get_target_id(), get_param_id(), get_type(),
                    get_coeff(), get_value(), get_planValueSrc1(), get_planValueSrc2(),
                    get_name(), get_measure());
        }
        catch (Exception e) { return  null; }
    }
    //endregion
}