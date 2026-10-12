package com.MH83.LifeAsProject.view.Money.MoneyPlanFact;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.MH83.LifeAsProject.model.Money.MoneyPlan;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFact;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccountingLegendToChangePopup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyPlanPopupNew extends CustomPopupBottomFragment<MoneyPlanFact>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_plan_new;
    //endregion

    //region Fragment settings
    private Long _id;
    private MoneyCategory _moneyCategory;
    private int _month;
    private int _year;
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
    private TableRow _tableIncomingRow2;
    private TableRow _tableIncomingRow4;
    private TableRow _tableIncomingRow5;
    //endregion

    //region Constructors
    public FragmentMoneyPlanPopupNew(int addTitle, int updateTitle,
                                     int error)
    {
        super(_layoutId,
                addTitle,
                updateTitle,
                error);
    }
    public FragmentMoneyPlanPopupNew(AppCompatActivity activity,
                                     MoneyPlanFact data,
                                     EnumTypeAction typeAction,
                                     IFragmentViewCustom iFragmentViewCustom,
                                     MoneyCategory moneyCategory,
                                     int addTitle,
                                     int updateTitle,
                                     int error,
                                     int month,
                                     int year)
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
        _month = month;
        _year = year;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_moneyPlanPopupNew_moneyCategory(_moneyCategory);
        globalVariable.set_moneyPlanPopupNew_month(_month);
        globalVariable.set_moneyPlanPopupNew_year(_year);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _moneyCategory = globalVariable.get_moneyPlanPopupNew_moneyCategory();
        _month = globalVariable.get_moneyPlanPopupNew_month();
        _year = globalVariable.get_moneyPlanPopupNew_year();
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
        _tableIncomingRow2 = view.findViewById(R.id.tableIncomingRow2);
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
        list.add(EnumTypeIncoming.Accumulation);
        list.add(EnumTypeIncoming.Credit);

        SpinnerTypeIncomingAdapter adapter = new SpinnerTypeIncomingAdapter(getContext(), list);
        _spinnerType.setAdapter(adapter);

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

        SetValues();
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

        _imageViewMascotToChange.setOnClickListener(view ->
                new FragmentMoneyPlanFactLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
    }

    private void typeChanged(EnumTypeIncoming typeIncoming)
    {
        LinearLayout.LayoutParams lp;
        switch (typeIncoming)
        {
            case Sum:
                lp = (LinearLayout.LayoutParams) _tableIncomingRow4.getLayoutParams();
                lp.height = 0;
                lp.setMargins(0, 0, 0, 0);
                _tableIncomingRow4.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _textViewAccumulation.getLayoutParams();
                lp.height = 0;
                _textViewAccumulation.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _spinnerAccumulation.getLayoutParams();
                lp.height = 0;
                _spinnerAccumulation.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _tableIncomingRow5.getLayoutParams();
                lp.height = 0;
                lp.setMargins(0, 0, 0, 0);
                _tableIncomingRow5.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _textViewCredit.getLayoutParams();
                lp.height = 0;
                _textViewCredit.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _spinnerCredit.getLayoutParams();
                lp.height = 0;
                _spinnerCredit.setLayoutParams(lp);

                _editTextSum.setEnabled(true);
                _editTextSum.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextSum, InputMethodManager.SHOW_IMPLICIT);

                break;
            case Accumulation:
                lp = (LinearLayout.LayoutParams) _tableIncomingRow4.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.setMargins(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.BasePadding));
                _tableIncomingRow4.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _textViewAccumulation.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                _textViewAccumulation.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _spinnerAccumulation.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                _spinnerAccumulation.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _tableIncomingRow5.getLayoutParams();
                lp.height = 0;
                lp.setMargins(0, 0, 0, 0);
                _tableIncomingRow5.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _textViewCredit.getLayoutParams();
                lp.height = 0;
                _textViewCredit.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _spinnerCredit.getLayoutParams();
                lp.height = 0;
                _spinnerCredit.setLayoutParams(lp);

                _editTextSum.setEnabled(false);
                _textViewAccumulation.setText(R.string.Accumulation);

                calcSumFromAccumulation();

                break;
            case Credit:
                lp = (LinearLayout.LayoutParams) _tableIncomingRow4.getLayoutParams();
                lp.height = 0;
                lp.setMargins(0, 0, 0, 0);
                _tableIncomingRow4.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _textViewAccumulation.getLayoutParams();
                lp.height = 0;
                _textViewAccumulation.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _spinnerAccumulation.getLayoutParams();
                lp.height = 0;
                _spinnerAccumulation.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _tableIncomingRow5.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.setMargins(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.BasePadding));
                _tableIncomingRow5.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _textViewCredit.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                _textViewCredit.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _spinnerCredit.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                _spinnerCredit.setLayoutParams(lp);

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

        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
        if (gVar.get_hasUnpaidSubscription())
        {
            _spinnerType.setEnabled(false);
        }

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
    protected void SetValues()
    {
        set_categoryName(_moneyCategory.get_name());

        if (_data != null && _data.get_moneyPlan() != null)
        {
            set_Id(_data.get_moneyPlan().get_id());
            set_sum(_data.get_moneyPlan().get_sumLong());
            set_type(_data.get_moneyPlan().get_typeIncoming());
            set_accumulation(_data.get_moneyPlan().get_externalId());
            typeChanged(_data.get_moneyPlan().get_typeIncoming());
        }
        else
        {
            set_Id(0L);
            set_sum(0L);
            set_type(EnumTypeIncoming.Sum);
        }
    }
    private void set_Id(Long id) { _id = id; }
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

    private void set_accumulation(Long accumulationId)
    {
        if (_spinnerAccumulation.getAdapter() == null)
            return;

        for (int i = 0; i < _spinnerAccumulation.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyAccumulation) _spinnerAccumulation.getAdapter().getItem(i)).get_id(), accumulationId))
            {
                _spinnerAccumulation.setSelection(i);
                break;
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

    private MoneyCategory get_moneyCategory()
    {
        return _moneyCategory;
    }

    private int get_month()
    {
        return _month;
    }

    private int get_year()
    {
        return _year;
    }

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

    //endregion

    //region Save Params
    @Override
    protected MoneyPlanFact GetDataFromForm()
    {
        try
        {
            MoneyPlanFact moneyPlanFact = new MoneyPlanFact(
                        get_moneyCategory(),
                        null, null);

            moneyPlanFact.set_moneyPlan(new MoneyPlan(
                    get_Id(),
                    get_moneyCategory(),
                    get_month(),
                    get_year(),
                    100L * getSum(),
                    get_typeIncoming(),
                    get_externalId()));
            return moneyPlanFact;
        }
        catch (Exception e) { return null; }
    }
    //endregion
}