package com.MH83.LifeAsProject.view.Money.MoneyIncoming;

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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyAccumulationAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerTypeIncomingAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyAccumulationDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyAccumulation;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyIncomingAndCostPopupNew extends CustomPopupBottomFragment<MoneyIncoming>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_incoming_new;
    //endregion

    //region Fragment settings
    private Long _id;
    private Long _categoryId;
    private MoneyIncrease _moneyIncrease;
    private MoneyCategory _moneyCategory;
    //endregion

    //region Fragment Components
    private TextView _textViewCategory;
    private TextView _textViewType;
    private Spinner _spinnerType;
    private TextView _textViewAccumulation;
    private Spinner _spinnerAccumulation;
    private TextView _textViewCredit;
    private Spinner _spinnerCredit;
    private EditText _editTextSum;
    private TextView _textViewPercent;
    private EditText _editTextPercent;
    private TableRow _tableIncomingRow2;
    private TableRow _tableIncomingRow3;
    private TableRow _tableIncomingRow4;
    private TableRow _tableIncomingRow5;
    //endregion

    //region Constructors
    public FragmentMoneyIncomingAndCostPopupNew(int addTitle, int updateTitle,
                                                int error)
    {
        super(_layoutId,
                addTitle,
                updateTitle,
                error);
    }
    public FragmentMoneyIncomingAndCostPopupNew(AppCompatActivity activity,
                                                MoneyIncoming data,
                                                EnumTypeAction typeAction,
                                                IFragmentViewCustom iFragmentViewCustom,
                                                int addTitle, int updateTitle,
                                                int error,
                                                MoneyCategory moneyCategory,
                                                MoneyIncrease moneyIncrease)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                addTitle,
                updateTitle,
                error,
                iFragmentViewCustom,
                null,
                null);

        _moneyCategory = moneyCategory;
        _moneyIncrease = moneyIncrease;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_incomingAndCostPopupNew_moneyCategory(_moneyCategory);
        globalVariable.set_incomingAndCostPopupNew_increase(_moneyIncrease);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _moneyCategory = globalVariable.get_incomingAndCostPopupNew_moneyCategory();
        _moneyIncrease = globalVariable.get_incomingAndCostPopupNew_increase();
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _textViewCategory = view.findViewById(R.id.textViewCategory);
        _textViewType = view.findViewById(R.id.textViewType);
        _spinnerType = view.findViewById(R.id.spinnerType);
        _textViewAccumulation = view.findViewById(R.id.textViewAccumulation);
        _spinnerAccumulation = view.findViewById(R.id.spinnerAccumulation);
        _textViewCredit = view.findViewById(R.id.textViewCredit);
        _spinnerCredit = view.findViewById(R.id.spinnerCredit);
        _editTextSum = view.findViewById(R.id.editTextSum);
        _textViewPercent = view.findViewById(R.id.textViewPercent);
        _editTextPercent = view.findViewById(R.id.editTextPercent);
        _tableIncomingRow2 = view.findViewById(R.id.tableIncomingRow2);
        _tableIncomingRow3 = view.findViewById(R.id.tableIncomingRow3);
        _tableIncomingRow4 = view.findViewById(R.id.tableIncomingRow4);
        _tableIncomingRow5 = view.findViewById(R.id.tableIncomingRow5);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextSum;
        _focusedViewToUpdate = _editTextSum;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        List<EnumTypeIncoming> list = new ArrayList<>();
        list.add(EnumTypeIncoming.Sum);
        if (_moneyCategory.getTypeCategory().equals(EnumTypeCategory.Cost))
        {
            list.add(EnumTypeIncoming.Percent);
            list.add(EnumTypeIncoming.Accumulation);
//            list.add(EnumTypeIncoming.Credit);
        }

        SpinnerTypeIncomingAdapter adapter = new SpinnerTypeIncomingAdapter(getContext(), list);
        _spinnerType.setAdapter(adapter);

        //TODO: ускорить выборку
        ArrayList<MoneyAccumulation> moneyAccumulationArrayList =
                MoneyAccumulationDataSource.LoadData(getContext(), false,true);
        SpinnerMoneyAccumulationAdapter accumulationAdapter =
                new SpinnerMoneyAccumulationAdapter(getContext(), moneyAccumulationArrayList);
        _spinnerAccumulation.setAdapter(accumulationAdapter);
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_data, _moneyCategory} );
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

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if ((getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ||
                globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_IncomingAndCost_Width) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                typeChanged(get_typeIncoming());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        _editTextPercent.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                calcSumFromPercent();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String str = editable.toString();

                int p = str.indexOf(".");
                if (p != -1)
                {
                    String tmpStr = str.substring(p);
                    if (tmpStr.length() == 4)
                        editable.delete(editable.length()-1, editable.length());
                }
            }
        });

        _spinnerAccumulation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (get_moneyAccumulation().get_id() != 0)
                    calcSumFromAccumulation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void typeChanged(EnumTypeIncoming typeIncoming)
    {
        switch (typeIncoming)
        {
            case Sum:
                _tableIncomingRow3.getLayoutParams().height = 0;
                _textViewPercent.getLayoutParams().height = 0;
                _editTextPercent.getLayoutParams().height = 0;
                _tableIncomingRow3.setPadding(0, 0, 0, 0);

                _tableIncomingRow4.getLayoutParams().height = 0;
                _textViewAccumulation.getLayoutParams().height = 0;
                _spinnerAccumulation.getLayoutParams().height = 0;
                _tableIncomingRow4.setPadding(0, 0, 0, 0);

                _tableIncomingRow5.getLayoutParams().height = 0;
                _textViewCredit.getLayoutParams().height = 0;
                _spinnerCredit.getLayoutParams().height = 0;
                _tableIncomingRow5.setPadding(0, 0, 0, 0);

                _editTextSum.setEnabled(true);
                _editTextSum.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextSum, InputMethodManager.SHOW_IMPLICIT);

                break;
            case Percent:
                _tableIncomingRow3.getLayoutParams().height =
                        (int) getContext().getResources().getDimension(R.dimen.MoneyIncomingRowHeight);
                _textViewPercent.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                _editTextPercent.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                _tableIncomingRow3.setPadding(0,
                        (int) getContext().getResources().getDimension(R.dimen.BasePadding),
                        0,
                        (int) getContext().getResources().getDimension(R.dimen.BasePadding));

                _tableIncomingRow4.getLayoutParams().height = 0;
                _textViewAccumulation.getLayoutParams().height = 0;
                _spinnerAccumulation.getLayoutParams().height = 0;
                _tableIncomingRow4.setPadding(0, 0, 0, 0);

                _tableIncomingRow5.getLayoutParams().height = 0;
                _textViewCredit.getLayoutParams().height = 0;
                _spinnerCredit.getLayoutParams().height = 0;
                _tableIncomingRow5.setPadding(0, 0, 0, 0);

                _editTextSum.setEnabled(false);
                _editTextPercent.requestFocus();
                InputMethodManager imm2 = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.showSoftInput(_editTextPercent, InputMethodManager.SHOW_IMPLICIT);

                calcSumFromPercent();

                break;
            case Accumulation:
                _tableIncomingRow3.getLayoutParams().height = 0;
                _textViewPercent.getLayoutParams().height = 0;
                _editTextPercent.getLayoutParams().height = 0;
                _tableIncomingRow3.setPadding(0, 0, 0, 0);

                _tableIncomingRow4.getLayoutParams().height =
                        (int) getContext().getResources().getDimension(R.dimen.MoneyIncomingRowHeight);
                _textViewAccumulation.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                _spinnerAccumulation.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                _tableIncomingRow4.setPadding(0,
                        (int) getContext().getResources().getDimension(R.dimen.BasePadding),
                        0,
                        (int) getContext().getResources().getDimension(R.dimen.BasePadding));

                _tableIncomingRow5.getLayoutParams().height = 0;
                _textViewCredit.getLayoutParams().height = 0;
                _spinnerCredit.getLayoutParams().height = 0;
                _tableIncomingRow5.setPadding(0, 0, 0, 0);

                _editTextSum.setEnabled(false);
                _textViewAccumulation.setText(R.string.Accumulation);

                calcSumFromAccumulation();

                break;
            case Credit:
                _tableIncomingRow3.getLayoutParams().height = 0;
                _textViewPercent.getLayoutParams().height = 0;
                _editTextPercent.getLayoutParams().height = 0;
                _tableIncomingRow3.setPadding(0, 0, 0, 0);

                _tableIncomingRow4.getLayoutParams().height = 0;
                _textViewAccumulation.getLayoutParams().height = 0;
                _spinnerAccumulation.getLayoutParams().height = 0;
                _tableIncomingRow4.setPadding(0, 0, 0, 0);

                _tableIncomingRow5.getLayoutParams().height =
                        (int) getContext().getResources().getDimension(R.dimen.MoneyIncomingRowHeight);
                _textViewCredit.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                _spinnerCredit.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                _tableIncomingRow5.setPadding(0,
                        (int) getContext().getResources().getDimension(R.dimen.BasePadding),
                        0,
                        (int) getContext().getResources().getDimension(R.dimen.BasePadding));

                _editTextSum.setEnabled(false);

                calcSumFromCredit();

                break;
            default:
        }
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        if (_moneyCategory.getTypeCategory().equals(EnumTypeCategory.Increase))
        {
            _tableIncomingRow2.getLayoutParams().height = 0;
            _textViewType.getLayoutParams().height = 0;
            _spinnerType.getLayoutParams().height = 0;
            _tableIncomingRow2.setPadding(0, 0, 0, 0);
        }
    }

    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        _data = (MoneyIncoming) objects[0];
        MoneyCategory moneyCategory = (MoneyCategory) objects[1];

        set_categoryId(moneyCategory.get_id());
        set_categoryName(moneyCategory.get_name());

        if (_data != null)
        {
            set_Id(_data.get_id());
            set_sum(_data.get_sumLong());
            set_type(_data.get_typeIncoming());
            set_percent(_data.get_percentOfSum());
            set_accumulation(_data.get_externalId());
            typeChanged(_data.get_typeIncoming());
        }
        else
        {
            set_Id(0L);
            set_sum(0L);
            set_type(EnumTypeIncoming.Sum);
            set_percent(0f);
        }
    }
    private void set_Id(Long id) { _id = id; }
    private void set_categoryId(Long categoryId) { _categoryId = categoryId; }
    private void set_categoryName(String categoryName) { _textViewCategory.setText(categoryName); }
    private void set_sum(Long sum)
    {
        if (sum != 0L)
            _editTextSum.setText(String.valueOf(sum / 100));
        else
            _editTextSum.setText("");
    }
    private void set_type(EnumTypeIncoming typeIncoming)
    {
        for (int i = 0; i < _spinnerType.getAdapter().getCount(); i++)
            if (_spinnerType.getAdapter().getItem(i).equals(typeIncoming))
            {
                _spinnerType.setSelection(i);
                break;
            }
    }
    private void set_percent(Float percent)
    {
        if (percent == 0f)
            _editTextPercent.setText("");
        else if ((100 * percent.intValue()) == (100 * percent))
            _editTextPercent.setText(String.valueOf(percent.intValue()));
        else
            _editTextPercent.setText(String.valueOf(percent));
    }
    private void set_accumulation(Long accumulationId)
    {
        for (int i = 0; i < _spinnerAccumulation.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyAccumulation) _spinnerAccumulation.getAdapter().getItem(i)).get_id(), accumulationId))
            {
                _spinnerAccumulation.setSelection(i);
                break;
            }
    }
    private void calcSumFromPercent()
    {
        try
        {
            set_sum((long) ((_moneyIncrease.get_value() * (100 * getPercent())) / 100L));
        }
        catch (Exception e)
        {
            set_sum(0L);
        }
    }
    private void calcSumFromAccumulation()
    {
        if (get_moneyAccumulation().get_id() == 0)
            set_sum(0L);
        else
            set_sum((long) get_moneyAccumulation().GetSumForExpense(getContext()));
    }
    private void calcSumFromCredit()
    {

    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    @Override
    protected boolean CheckFormValues()
    {
        try
        {
            int sum = getSum();
            if (sum == 0)
            {
                _editTextSum.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextSum, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(getActivity(),
                        getContext().getString(R.string.specifySumm), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextSum.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextSum, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(getActivity(),
                    getContext().getString(R.string.specifyCorrectSumm), Toast.LENGTH_SHORT).show();
            return false;
        }

        try
        {
            if (get_typeIncoming().equals(EnumTypeIncoming.Percent))
                if (getPercent() == 0f)
                {
                    _editTextPercent.requestFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(_editTextPercent, InputMethodManager.SHOW_IMPLICIT);

                    Toast.makeText(getActivity(),
                            getContext().getString(R.string.specifyExpensePercent), Toast.LENGTH_SHORT).show();
                    return false;
                }
        }
        catch (Exception e2)
        {
            _editTextPercent.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextPercent, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(getActivity(),
                    getContext().getString(R.string.specifyCorrectExpensePercent), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (get_externalId() == 0)
        {
            if (get_typeIncoming().equals(EnumTypeIncoming.Accumulation))
            {
                _spinnerAccumulation.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_spinnerAccumulation, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(getActivity(),
                        getContext().getString(R.string.specifyAccumulation), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (get_typeIncoming().equals(EnumTypeIncoming.Credit))
            {
                _spinnerCredit.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_spinnerCredit, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(getActivity(),
                        getContext().getString(R.string.specifyCredit), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete() { return true; }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        if (_id != 0)
            return _id;

        return -1L;
    }

    private Long getCategoryId() { return _categoryId; }
    private Long getIncreaseId() { return _moneyIncrease.get_id(); }
    private int getSum() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextSum.getText().toString());
        }
        catch (Exception e) { throw new Exception(e.getMessage()); }
    }
    private EnumTypeIncoming get_typeIncoming()
    {
        return (EnumTypeIncoming) _spinnerType.getSelectedItem();
    }
    private MoneyAccumulation get_moneyAccumulation()
    {
        return (MoneyAccumulation) _spinnerAccumulation.getSelectedItem();
    }
    private MoneyCredit get_moneyCredit()
    {
        return (MoneyCredit) _spinnerCredit.getSelectedItem();
    }
    private Long get_externalId()
    {
        if (_moneyCategory.getTypeCategory().equals(EnumTypeCategory.Increase))
            return 0L;

        switch (get_typeIncoming())
        {
            case Accumulation:
                return get_moneyAccumulation().get_id();
            case Credit:
                return get_moneyCredit().get_id();
            default:
                return 0L;
        }
    }
    private Float getPercent() throws Exception
    {
        try
        {
            if (get_typeIncoming().equals(EnumTypeIncoming.Percent))
                return Float.parseFloat(_editTextPercent.getText().toString());
            else
                return 0f;
        }
        catch (Exception e) { throw new Exception(e); }
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyIncoming GetDataFromForm()
    {
        try
        {
            return new MoneyIncoming(get_Id(), getCategoryId(), getIncreaseId(),
                    100L * getSum(),
                    getPercent(),
                    get_typeIncoming(),
                    get_externalId());
        }
        catch (Exception e) { return null; }
    }
    //endregion
}