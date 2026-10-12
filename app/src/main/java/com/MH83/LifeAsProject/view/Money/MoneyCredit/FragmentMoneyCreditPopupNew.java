package com.MH83.LifeAsProject.view.Money.MoneyCredit;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyPNLCreditAdapter;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyCreditPivotAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyCategoryAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyTargetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerStatusCreditAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerTypeCreditAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAccountingDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCreditPivotDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTargetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCredit;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyCreditPivotPoint;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyCostPopupNew;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyCreditPopupNew extends CustomPopupBottomFragment<MoneyCredit>
    implements IFragmentViewCustom
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_credit_new;
    //endregion

    //region Fragment parameters
    private int _rViewHeight = 1000;
    //endregion

    //region Fragment settings
    private Calendar _date;
    private final FragmentMoneyCreditPopupNew _this;
    private boolean _refreshPNLs;
    //endregion

    //region Fragment Components
    private EditText _editTextName;
    private Spinner _spinnerTarget;
    private EditText _editTextStartSum;
    private EditText _editTextSum;
    private TextView _textViewDate;
    private EditText _editTextLoanTerm;
    private EditText _editTextCreditPercent;
    private TextView _textViewDateTitle;
    private ImageView _imageViewDateMinus;
    private ImageView _imageViewDatePlus;
    private Spinner _spinnerTypeCredit;
    private Spinner _spinnerWallet;
    private Button _buttonNext;
    private Button _buttonNext2;
    private Button _buttonPrev;
    private Button _buttonPrev3;
    private NestedScrollView _nestedScrollView1;
    private NestedScrollView _nestedScrollView2;
    private NestedScrollView _nestedScrollView3;
    private ConstraintLayout _constraintLayoutNSV2;
    private ConstraintLayout _constraintLayoutNSV3;
    private LinearLayout _linearLayoutNSV2;
    private LinearLayout _linearLayoutNSV3;
    private RecyclerView recyclerViewPNL;
    private RecyclerView recyclerViewPivots;
    private Spinner _spinnerStatus;
    private ImageView _imageViewAddToRView;
    private ImageView _imageViewAddToRView2;
    private Spinner _spinnerInc4CostCategoryId;
    private Spinner _spinnerInc4CostSubCategoryId;
    //endregion

    //region Constructors
    public FragmentMoneyCreditPopupNew()
    {
        super(_layoutId,
                R.string.fragmentMoneyCreditPopupAddTitle,
                R.string.fragmentMoneyCreditPopupUpdateTitle,
                R.string.errorUpdateMoneyCredit);
        _this = this;
    }

    public FragmentMoneyCreditPopupNew(AppCompatActivity activity,
                                       MoneyCredit data,
                                       EnumTypeAction typeAction,
                                       IFragmentViewCustom<MoneyCredit> iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                R.string.fragmentMoneyCreditPopupAddTitle,
                R.string.fragmentMoneyCreditPopupUpdateTitle,
                R.string.errorUpdateMoneyCredit,
                iFragmentViewCustom,
                null,
                null);

        _this = this;
        _refreshPNLs = true;

//        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
//        globalVariable.set_moneyAccountingSumCost(moneyAccountingSumCost);
//        globalVariable.set_moneyAccountingSumIncrease(moneyAccountingSumIncrease);
//        globalVariable.set_MAccPstartDate(startDate);
//        globalVariable.set_MAccPendDate(endDate);
//        globalVariable.set_MAccmoneyPNL(data);
//
//        globalVariable.set_MAccOldCostCategoryId(null);
//        globalVariable.set_MAccOldIncreaseCategoryId(null);
//        globalVariable.set_MAccOldSum(null);
//        globalVariable.set_selSubCategoryId(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        _moneyAccountingSumCost = globalVariable.get_moneyAccountingSumCost();
//        _moneyAccountingSumIncrease = globalVariable.get_moneyAccountingSumIncrease();
//        _startDate = globalVariable.get_MAccPstartDate();
//        _endDate = globalVariable.get_MAccPendDate();
//        _data = globalVariable.get_MAccPmoneyPNL();
//        _increaseCategoryId = globalVariable.get_MAccIncreaseCategoryId();
//        _costCategoryId = globalVariable.get_MAccCostCategoryId();
//        _oldIncreaseCategoryId = globalVariable.get_MAccOldIncreaseCategoryId();
//        _oldCostCategoryId = globalVariable.get_MAccOldCostCategoryId();
//        _oldSubCategoryId = globalVariable.get_MAccOldSubCategoryId();
//        _oldSum = globalVariable.get_MAccOldSum();
//        _date = globalVariable.get_MAccDate();
//        _typeCategory = globalVariable.get_MAccTypeCategory();
//        _typeCategoryChanged = globalVariable.get_MAccTypeCategoryChanged();
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _editTextName = view.findViewById(R.id.editTextName);
        _spinnerTarget = view.findViewById(R.id.spinnerTarget);
        _editTextCreditPercent = view.findViewById(R.id.editTextPercent);
        _textViewDate = view.findViewById(R.id.textViewDate);
        _editTextSum = view.findViewById(R.id.editTextSum);
        _editTextStartSum = view.findViewById(R.id.editTextStartSum);
        _editTextLoanTerm = view.findViewById(R.id.editTextLoanTerm);
        _textViewDateTitle = view.findViewById(R.id.textViewDateTitle);
        _imageViewDateMinus = view.findViewById(R.id.imageViewDateMinus);
        _imageViewDatePlus = view.findViewById(R.id.imageViewDatePlus);
        _spinnerTypeCredit = view.findViewById(R.id.spinnerTypeCredit);
        _spinnerWallet = view.findViewById(R.id.spinnerWallet);
        _spinnerInc4CostCategoryId = view.findViewById(R.id.spinnerInc4CostCategoryId);
        _spinnerInc4CostSubCategoryId = view.findViewById(R.id.spinnerInc4CostSubCategoryId);
        _buttonNext = view.findViewById(R.id.buttonNext);
        _buttonNext2 = view.findViewById(R.id.buttonNext2);
        _buttonPrev = view.findViewById(R.id.buttonPrev);
        _buttonPrev3 = view.findViewById(R.id.buttonPrev3);
        _nestedScrollView1 = view.findViewById(R.id.nestedScrollView1);
        _nestedScrollView2 = view.findViewById(R.id.nestedScrollView2);
        _nestedScrollView3 = view.findViewById(R.id.nestedScrollView3);
        _constraintLayoutNSV2 = view.findViewById(R.id.constraintLayoutNSV2);
        _constraintLayoutNSV3 = view.findViewById(R.id.constraintLayoutNSV3);
        _linearLayoutNSV2 = view.findViewById(R.id.linearLayoutNSV2);
        _linearLayoutNSV3 = view.findViewById(R.id.linearLayoutNSV3);
        recyclerViewPNL = view.findViewById(R.id.recycleViewPNLCredit);
        recyclerViewPivots = view.findViewById(R.id.recycleViewPivots);
        _spinnerStatus = view.findViewById(R.id.spinnerStatus);
        _imageViewAddToRView = view.findViewById(R.id.imageViewAdd);
        _imageViewAddToRView2 = view.findViewById(R.id.imageViewAdd2);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

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

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Credit_Width),
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
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Credit_Width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        _rViewHeight = displaymetrics.heightPixels;
        _rViewHeight -= _layoutCaption.getLayoutParams().height;
        _rViewHeight -= 9 * getContext().getResources().getDimension(R.dimen.BasePadding);
        _rViewHeight -= _buttonSave.getLayoutParams().height;
        _rViewHeight -= _spinnerStatus.getLayoutParams().height;

//        recyclerViewPNL.getLayoutParams().height = height;
//        recyclerViewPivots.getLayoutParams().height = height;

        _nestedScrollView2.setVisibility(View.INVISIBLE);
        _nestedScrollView2.getLayoutParams().height = 0;
        _nestedScrollView2.requestLayout();

        _constraintLayoutNSV2.getLayoutParams().height = 0;
        _constraintLayoutNSV2.requestLayout();

        recyclerViewPNL.getLayoutParams().height = 0;
        recyclerViewPNL.requestLayout();

        _linearLayoutNSV2.getLayoutParams().height = 0;
        _linearLayoutNSV2.requestLayout();

        _nestedScrollView3.setVisibility(View.INVISIBLE);
        _nestedScrollView3.getLayoutParams().height = 0;
        _nestedScrollView3.requestLayout();

        _constraintLayoutNSV3.getLayoutParams().height = 0;
        _constraintLayoutNSV3.requestLayout();

        recyclerViewPivots.getLayoutParams().height = 0;
        recyclerViewPivots.requestLayout();

        _linearLayoutNSV3.getLayoutParams().height = 0;
        _linearLayoutNSV3.requestLayout();
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

//        MoneyInc4CostAdapter adapter =
//                new MoneyInc4CostAdapter(getContext(),
//                        _data == null ? null : (ArrayList<MoneyInc4Cost>) _data.get_inc4Cost(),
//                        _typeAction,
//                        _iFragmentViewCustom,
//                        this);
//        recycleViewInc4Cost.setAdapter(adapter);
        SetSpinnerTargetAdapter();
        SetSpinnerTypeCreditAdapter();
        SetWalletAdapter();
        SetInc4CostCategoryAdapter();
        SetSpinnerStatusAdapter();
    }

    private void SetSpinnerTargetAdapter()
    {
        List<MoneyTarget> list = MoneyTargetDataSource.LoadDataWithBlank(getContext());

        SpinnerMoneyTargetAdapter adapter = new SpinnerMoneyTargetAdapter(getContext(), list);
        _spinnerTarget.setAdapter(adapter);
    }

    private void SetSpinnerTypeCreditAdapter()
    {
        List<EnumTypeCredit> list = new ArrayList<>();
        list.add(EnumTypeCredit.Annuitant);
        list.add(EnumTypeCredit.Different);

        SpinnerTypeCreditAdapter adapter = new SpinnerTypeCreditAdapter(getContext(), list);
        _spinnerTypeCredit.setAdapter(adapter);
    }

    private void SetWalletAdapter()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Wallet, true, false,
                true, EnumCategoryType.All,null);
        List<MoneyCategory> list = MoneyCategoryDataSource.LoadDataParent(getContext(),
                false, moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerWallet.setAdapter(adapter);
    }

    private void SetInc4CostCategoryAdapter()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Cost, true, false,
                true, EnumCategoryType.All, null);
        List<MoneyCategory> list = MoneyCategoryDataSource.LoadDataParent(getContext(),
                true, moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerInc4CostCategoryId.setAdapter(adapter);
    }

    private void SetInc4CostSubCategoryAdapter(Long parentCategory)
    {
        List<MoneyCategory> list = new ArrayList<>();

        if (parentCategory != null && parentCategory != 0L)
        {
            MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                    EnumTypeCategory.Cost, true, false,
                    true, EnumCategoryType.All,null);
            list = MoneyCategoryDataSource.LoadDataChildren(getContext(), parentCategory,
                    moneyCategorySettings);
        }

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerInc4CostSubCategoryId.setAdapter(adapter);
    }

    private void SetSpinnerStatusAdapter()
    {
        List<EnumCreditStatus> list = new ArrayList<>();
        list.add(EnumCreditStatus.Active);
        list.add(EnumCreditStatus.Inactive);

        SpinnerStatusCreditAdapter adapter = new SpinnerStatusCreditAdapter(getContext(), list);
        _spinnerStatus.setAdapter(adapter);
    }
    //endregion

    //region Set Listeners
    public void ClearFocus()
    {
        _editTextSum.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextSum.getWindowToken(), 0);
    }

    public void clearRViewPNL()
    {
        _refreshPNLs = true;
        if (((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet() != null)
            for (MoneyPNL item : ((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet())
                if (!item.get_isPlan())
                {
                    _refreshPNLs = false;
                    break;
                }
    }

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _editTextStartSum.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                _editTextSum.setText(_editTextStartSum.getText());
                clearRViewPNL();
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

        _editTextLoanTerm.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                clearRViewPNL();
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        _editTextCreditPercent.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                clearRViewPNL();
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

        _spinnerTypeCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                clearRViewPNL();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        _textViewDate.setOnClickListener(view1 ->
        {
                ClearFocus();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (datePicker, year, month, day) ->
                        {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day);
                            set_date(calendar);
                            clearRViewPNL();
                        },
                        _date.get(Calendar.YEAR),
                        _date.get(Calendar.MONTH),
                        _date.get(Calendar.DATE));
                datePickerDialog.show();
            });

        _imageViewDateMinus.setOnClickListener(view ->
        {
                ClearFocus();
                _date.add(Calendar.DATE, -1);
                set_date(_date);
                clearRViewPNL();
            });

        _imageViewDatePlus.setOnClickListener(view ->
        {
                ClearFocus();
                _date.add(Calendar.DATE, 1);
                set_date(_date);
                clearRViewPNL();
            });

        _spinnerInc4CostCategoryId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                SetInc4CostSubCategoryAdapter(get_inc4CostCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        _buttonNext.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            _nestedScrollView1.setVisibility(View.INVISIBLE);
            _nestedScrollView1.getLayoutParams().height = 0;

            _nestedScrollView2.setVisibility(View.VISIBLE);
            _nestedScrollView2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV2.requestLayout();

            recyclerViewPNL.getLayoutParams().height = _rViewHeight;
            recyclerViewPNL.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _linearLayoutNSV2.requestLayout();

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
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//                else
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//            }
//            else
//            {
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//            }

            recalcPayments();
        });

        _buttonNext2.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            _nestedScrollView2.setVisibility(View.INVISIBLE);
            _nestedScrollView2.getLayoutParams().height = 0;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = 0;
            _constraintLayoutNSV2.requestLayout();

            recyclerViewPNL.getLayoutParams().height = 0;
            recyclerViewPNL.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = 0;
            _linearLayoutNSV2.requestLayout();

            _nestedScrollView3.setVisibility(View.VISIBLE);
            _nestedScrollView3.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView3.requestLayout();

            _constraintLayoutNSV3.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV3.requestLayout();

            recyclerViewPivots.getLayoutParams().height = _rViewHeight;
            recyclerViewPivots.requestLayout();

            _linearLayoutNSV3.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _linearLayoutNSV3.requestLayout();

//            _nestedScrollView2.setVisibility(View.INVISIBLE);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//            lp.weight = 1;
//            _nestedScrollView2.setLayoutParams(lp);
//
//            _nestedScrollView3.setVisibility(View.VISIBLE);
//            lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
//            lp.weight = 0;
//            _nestedScrollView3.setLayoutParams(lp);
//
//            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            {
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
//                {
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
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//            }
//            else
//            {
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//            }
        });

        _buttonPrev.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            _nestedScrollView1.setVisibility(View.VISIBLE);
            _nestedScrollView1.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView1.requestLayout();

            _nestedScrollView2.setVisibility(View.INVISIBLE);
            _nestedScrollView2.getLayoutParams().height = 0;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = 0;
            _constraintLayoutNSV2.requestLayout();

            recyclerViewPNL.getLayoutParams().height = 0;
            recyclerViewPNL.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = 0;
            _linearLayoutNSV2.requestLayout();

//            _nestedScrollView1.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
//            lp.weight = 0;
//            _nestedScrollView1.setLayoutParams(lp);
//
//            _nestedScrollView2.setVisibility(View.INVISIBLE);
//            lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//            lp.weight = 1;
//            _nestedScrollView2.setLayoutParams(lp);
//
//            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            {
////                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
////                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//                }
//            }
//            else
//            {
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV2.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV2.setLayoutParams(lp);
//                }
//            }
        });

        _buttonPrev3.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            _nestedScrollView2.setVisibility(View.VISIBLE);
            _nestedScrollView2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV2.requestLayout();

            recyclerViewPNL.getLayoutParams().height = _rViewHeight;
            recyclerViewPNL.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _linearLayoutNSV2.requestLayout();

            _nestedScrollView3.setVisibility(View.INVISIBLE);
            _nestedScrollView3.getLayoutParams().height = 0;
            _nestedScrollView3.requestLayout();

            _constraintLayoutNSV3.getLayoutParams().height = 0;
            _constraintLayoutNSV3.requestLayout();

            recyclerViewPivots.getLayoutParams().height = 0;
            recyclerViewPivots.requestLayout();

            _linearLayoutNSV3.getLayoutParams().height = 0;
            _linearLayoutNSV3.requestLayout();

            recalcPayments();
//            _nestedScrollView2.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
//            lp.weight = 0;
//            _nestedScrollView2.setLayoutParams(lp);
//
//            _nestedScrollView3.setVisibility(View.INVISIBLE);
//            lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
//            lp.weight = 1;
//            _nestedScrollView3.setLayoutParams(lp);
//
//            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            {
////                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
////                if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//            }
//            else
//            {
//                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//                if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                {
//                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _constraintLayoutNSV3.setLayoutParams(lp);
//
//                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
//                    lp.height = 0;
//                    _linearLayoutNSV3.setLayoutParams(lp);
//                }
//            }
        });

        _imageViewAddToRView.setOnClickListener(view ->
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(_activity.getApplicationContext()))
            {
                final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
                MoneyAccountingSum moneyAccountingSumCost =
                        MoneyAccountingDataSource.LoadDataWithoutPNL(_activity.getApplicationContext(),
                                get_wallet());
                Long increaseCategoryId = globalVariable.get_costCreditsCategoryId();
                MoneyAccountingSum moneyAccountingSumIncrease =
                        MoneyAccountingDataSource.LoadDataWithoutPNL(_activity.getApplicationContext(),
                                increaseCategoryId);
                Long subCategoryId = dbHelper.OneMoneyCategoryId(get_id(), EnumTypeCategory.Cost);

                Calendar clnd = (Calendar) get_dateStart().clone();
                clnd.add(Calendar.MONTH, 1);

                MoneyPNL item = new MoneyPNL(0L,
                        EnumTypeCategory.Cost,
                        increaseCategoryId,
                        increaseCategoryId,
                        get_wallet(),
                        get_wallet(),
                        subCategoryId,
                        subCategoryId,
                        "", get_walletName(), "",
                        clnd.get(Calendar.DATE),
                        clnd.get(Calendar.MONTH),
                        clnd.get(Calendar.YEAR),
                        0L, 0L,
                        0, "",
                        0L, 0L, 0L,
                        true,
                        true, get_id(), 0L, false,
                        0L, 0L,
                        null, null,
                        null, null);

                FragmentMoneyCostPopupNew _fragmentPopupNew = new FragmentMoneyCostPopupNew(
                    _activity, item, EnumTypeAction.Insert,
                    _this, moneyAccountingSumCost, moneyAccountingSumIncrease,
                    Calendar.getInstance(), Calendar.getInstance(),
                        true, true, true);

                _fragmentPopupNew.PopupShow();
            }
            catch (Exception e)
            {

            }
        });

        _imageViewAddToRView2.setOnClickListener(view ->
        {
            FragmentMoneyCreditPivotPopupNew _fragmentPopupNew = new FragmentMoneyCreditPivotPopupNew(
                    _activity,
                    null,
                    EnumTypeAction.Insert,
                    _this);

            _fragmentPopupNew.PopupShow();
        });

        _textViewCaption.setOnClickListener(view ->
        {
            recyclerViewPNL.scrollToPosition(0);
            recyclerViewPivots.scrollToPosition(0);
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
            set_name(_data.get_name());
            set_target(_data.get_target_id());
            set_StartSumLong(_data.get_startSummLong());
            set_sumLong(_data.get_sumLong());
            set_date(_data.get_calendarStart());
            set_loanTerm(_data.get_loanTerm());
            set_percent(_data.get_percent());
            set_typeCredit(_data.get_type());
            set_wallet(_data.get_wallet());
            set_category(_data.get_inc4CostCategoryId());
            SetInc4CostSubCategoryAdapter(_data.get_inc4CostCategoryId());
            set_subCategory(_data.get_inc4CostSubCategoryId());
            set_status(_data.get_status());
            set_pnls(MoneyPNLDataSource.LoadData4Credit(getContext(), EnumTypeCategory.Cost, _data.get_id()));
            set_pivots(MoneyCreditPivotDataSource.LoadData4Credit(getContext(), _data.get_id()));
            _refreshPNLs = false;
        }
        else
        {
            set_name("");
            set_target(0L);
            set_StartSumLong(0L);
            set_sumLong(0L);
            set_date(Calendar.getInstance());
            set_loanTerm(0);
            set_percent(0d);
            set_typeCredit(EnumTypeCredit.Annuitant);
            set_wallet(0L);
            set_category(0L);
            set_status(EnumCreditStatus.Active);
            set_pnls(null);
            set_pivots(null);
            _refreshPNLs = true;
        }
    }

    private void set_name(String name)
    {
        _editTextName.setText(name);
    }

    private void set_target(Long targetId)
    {
        for (int i = 0; i < _spinnerTarget.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyTarget) _spinnerTarget.getAdapter().getItem(i)).get_id(), targetId))
            {
                _spinnerTarget.setSelection(i);
                break;
            }
    }

    private void set_StartSumLong(Long sumLong)
    {
        if (sumLong == 0L)
            _editTextStartSum.setText("");
        else if (Math.floorMod(sumLong, 100L) == 0)
        {
            _editTextStartSum.setText(String.valueOf(Math.floorDiv(sumLong, 100L)));
        }
        else
        {
            _editTextStartSum.setText(String.valueOf(sumLong / 100d));
        }
    }

    private void set_sumLong(Long sumLong)
    {
        if (sumLong == 0L)
            _editTextSum.setText("");
        else if (Math.floorMod(sumLong, 100L) == 0)
        {
            _editTextSum.setText(String.valueOf(Math.floorDiv(sumLong, 100L)));
        }
        else
        {
            _editTextSum.setText(String.valueOf(sumLong / 100d));
        }
    }

    private void set_date(Calendar calendar)
    {
        _date = (Calendar) calendar.clone();
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_MAccDate(_date);

        CustomStaticClass.setDateView(getContext(),
                _date.get(Calendar.DATE),
                _date.get(Calendar.MONTH),
                _date.get(Calendar.YEAR),
                _textViewDate);

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

    private void set_percent(Double percent)
    {
        if (percent == 0d)
            _editTextCreditPercent.setText("");
        else if ((100 * percent.intValue()) == (100 * percent))
        {
            _editTextCreditPercent.setText(String.valueOf(percent.intValue()));
        }
        else
        {
            _editTextCreditPercent.setText(String.valueOf(percent));
        }
    }

    private void set_loanTerm(int loanTerm)
    {
        if (loanTerm == 0)
            _editTextLoanTerm.setText("");
        else
            _editTextLoanTerm.setText(String.valueOf(loanTerm));
    }

    private void set_typeCredit(EnumTypeCredit typeCredit)
    {
        for (int i = 0; i < _spinnerTypeCredit.getAdapter().getCount(); i++)
            if (_spinnerTypeCredit.getAdapter().getItem(i).equals(typeCredit))
            {
                _spinnerTypeCredit.setSelection(i);
                break;
            }
    }

    private void set_wallet(Long walletId)
    {
        for (int i = 0; i < _spinnerWallet.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerWallet.getAdapter().getItem(i)).get_id(), walletId))
            {
                _spinnerWallet.setSelection(i);
                break;
            }
    }

    private void set_category(Long categoryId)
    {
        for (int i = 0; i < _spinnerInc4CostCategoryId.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerInc4CostCategoryId.getAdapter().getItem(i)).get_id(), categoryId))
            {
                _spinnerInc4CostCategoryId.setSelection(i);
                break;
            }
    }

    private void set_subCategory(Long subCategoryId)
    {
        for (int i = 0; i < _spinnerInc4CostSubCategoryId.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerInc4CostSubCategoryId.getAdapter().getItem(i)).get_id(), subCategoryId))
            {
                _spinnerInc4CostSubCategoryId.setSelection(i);
                break;
            }
    }

    private void set_status(EnumCreditStatus status)
    {
        for (int i = 0; i < _spinnerStatus.getAdapter().getCount(); i++)
            if (_spinnerStatus.getAdapter().getItem(i).equals(status))
            {
                _spinnerStatus.setSelection(i);
                break;
            }
    }

    private void set_pnls(ArrayList<MoneyPNL> pnls)
    {
        MoneyPNLCreditAdapter adapter = new MoneyPNLCreditAdapter(_activity,
                pnls , _this, _typeAction != EnumTypeAction.Insert);
        recyclerViewPNL.setAdapter(adapter);

        setEnabled(pnls);
    }

    private void set_pivots(ArrayList<MoneyCreditPivotPoint> points)
    {
        MoneyCreditPivotAdapter adapter = new MoneyCreditPivotAdapter(_activity,
                points , _this, _typeAction != EnumTypeAction.Insert);
        recyclerViewPivots.setAdapter(adapter);
    }

    private void setEnabled(ArrayList<MoneyPNL> pnls)
    {
        _editTextCreditPercent.setEnabled(true);
        _editTextStartSum.setEnabled(true);
        _editTextLoanTerm.setEnabled(true);
        _spinnerTypeCredit.setEnabled(true);
        _spinnerWallet.setEnabled(true);
        _spinnerInc4CostCategoryId.setEnabled(true);
        _spinnerInc4CostSubCategoryId.setEnabled(true);
        _textViewDate.setEnabled(true);
        _imageViewDatePlus.setEnabled(true);
        _imageViewDateMinus.setEnabled(true);

        if (_data != null && pnls != null)
            for (MoneyPNL item : pnls)
                if (!item.get_isPlan())
                {
                    _editTextCreditPercent.setEnabled(false);
                    _editTextStartSum.setEnabled(false);
                    _editTextLoanTerm.setEnabled(false);
                    _spinnerTypeCredit.setEnabled(false);
                    _spinnerWallet.setEnabled(false);
                    _spinnerInc4CostCategoryId.setEnabled(false);
                    _spinnerInc4CostSubCategoryId.setEnabled(false);
                    _textViewDate.setEnabled(false);
                    _imageViewDatePlus.setEnabled(false);
                    _imageViewDateMinus.setEnabled(false);
                    break;
                }
    }

    private void recalcPayments()
    {
        if (!_refreshPNLs)
            return;

//        if (_data == null || ((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet() == null ||
//                (recyclerViewPNL.getAdapter()).getItemCount() == 0)
        {
            if (_spinnerTypeCredit.getSelectedItem() == EnumTypeCredit.Annuitant)
                set_annuitantPayments2();
            else if (_spinnerTypeCredit.getSelectedItem() == EnumTypeCredit.Different)
                set_differencialPayments();
        }
    }

    private double calcPercent_annuitantPayments(Long startSum, Calendar calendar, boolean endMonth,
                                                 double getPercent)
    {
        Calendar prevCalendar = (Calendar) calendar.clone();
        Calendar calendar1day = (Calendar) calendar.clone();

        if (endMonth)
        {
            prevCalendar.add(Calendar.DATE, 1);
            prevCalendar.add(Calendar.MONTH, -1);
            prevCalendar.add(Calendar.DATE, -1);
        }
        else
            prevCalendar.add(Calendar.MONTH, -1);

        calendar1day.set(Calendar.DATE, 1);

        double percent = 0d;
        try
        {
            int lengthOfYear = 365;
            if (Math.floorMod(prevCalendar.get(Calendar.YEAR), 4) == 0)
                lengthOfYear = 366;
            long diff = (calendar1day.getTimeInMillis() - prevCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000);
            percent += (double) Math.round(startSum * (double) diff * getPercent / (double) lengthOfYear / 100);
        }
        catch (Exception e) { return 0d; }

        try
        {
            int lengthOfYear = 365;
            if (Math.floorMod(calendar.get(Calendar.YEAR), 4) == 0)
                lengthOfYear = 366;
            long diff = (calendar.getTimeInMillis() - calendar1day.getTimeInMillis()) / (24 * 60 * 60 * 1000);
            percent += (double) Math.round(startSum * (double) diff * getPercent / (double) lengthOfYear / 100);
        }
        catch (Exception e) { return 0d; }

        return percent;
    }

    private void set_annuitantPayments()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_activity.getApplicationContext()))
        {
            final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
            double x, k, m;
            m = getPercent() / 1200;
            k = (m * Math.pow (1 + m, get_loanTerm())) / (Math.pow (1 + m, get_loanTerm()) - 1);
            x = (double) getStartSumLong() * k * 100;

            long startSum = 100 * getStartSumLong();
            Calendar calendar = (Calendar) get_dateStart().clone();

            int month1 = calendar.get(Calendar.MONTH);
            calendar.add(Calendar.DATE, 1);
            int month2 = calendar.get(Calendar.MONTH);
            calendar.add(Calendar.DATE, -1);
            boolean endMonth = month1 != month2;

            ArrayList<MoneyPNL> pnls = new ArrayList<>();
            Long increaseCategoryId = globalVariable.get_costCreditsCategoryId();
            Long subCategoryId = dbHelper.OneMoneyCategoryId(get_id(), EnumTypeCategory.Cost);
            ArrayList<MoneyPNL> oldPNLs = null;
            if (recyclerViewPNL.getAdapter() != null)
                oldPNLs = ((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet();

            int shift = 0;
            for (int i = 0; i < get_loanTerm(); i++)
            {
                if (endMonth)
                {
                    calendar.add(Calendar.DATE, 1);
                    calendar.add(Calendar.MONTH, 1);
                    calendar.add(Calendar.DATE, -1);
                }
                else
                    calendar.add(Calendar.MONTH, 1);

                double percentSum = calcPercent_annuitantPayments(startSum, calendar, endMonth, getPercent());
                double bodySum = x - percentSum;
                pnls.add(new MoneyPNL(
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_id() : 0,
                        EnumTypeCategory.Cost,
                        increaseCategoryId,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldIncreaseCategoryId() : increaseCategoryId,
                        get_wallet(),
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldCostCategoryId() : get_wallet(),
                        subCategoryId,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldSubCategoryId() : subCategoryId,
                        "", get_walletName(), "",
                        calendar.get(Calendar.DATE),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        (long) bodySum/100,
                        (long) (oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldSumLong() : bodySum/100),
                        0, "",
                        0L, 0L, 0L,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_isPlan() : true,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldIsPlan() : true,
                        get_id(),
                        0L,
                        true,
                        (long) percentSum/100,
                        (long) (oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldPercentSumLong() : percentSum/100),
                        null, null,
                        null, null));

                startSum -= bodySum;

                while (oldPNLs != null && oldPNLs.size() != 0 && oldPNLs.size() > i + 1 + shift &&
                        i + shift + 1 != oldPNLs.size() &&
                        oldPNLs.get(i + shift + 1).get_percentSumLong() == 0L)
                    {
                        startSum -= 100 * oldPNLs.get(i + shift + 1).get_sumLong();
                        pnls.add(oldPNLs.get(i + shift + 1));

                        k = (m * Math.pow (1 + m, get_loanTerm() - i - 1)) / (Math.pow (1 + m, get_loanTerm() - i - 1) - 1);
                        x = (double) Math.round(startSum * k);

                        shift++;
                    }
            }
            if (startSum != 0d)
                pnls.get(pnls.size() - 1).set_sumLong(
                    (100 * pnls.get(pnls.size() - 1).get_sumLong() + startSum) / 100);

            MoneyPNLCreditAdapter adapter = new MoneyPNLCreditAdapter(_activity,
                    pnls , _this, _typeAction != EnumTypeAction.Insert);
            recyclerViewPNL.setAdapter(adapter);
            _refreshPNLs = false;
        }
        catch (Exception e)
        {
            recyclerViewPNL.setAdapter(null);
        }
    }
    private void set_annuitantPayments2()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_activity.getApplicationContext()))
        {
            final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
            ArrayList<MoneyCreditPivotPoint> listPoints = new ArrayList<>();
            listPoints.add(new MoneyCreditPivotPoint(0L, 0L, getStartSumLong(), getPercent(), get_loanTerm()));
            listPoints.addAll(get_points());

            ArrayList<MoneyPNL> pnls = new ArrayList<>();
            Long increaseCategoryId = globalVariable.get_costCreditsCategoryId();
            Long subCategoryId = dbHelper.OneMoneyCategoryId(get_id(), EnumTypeCategory.Cost);
            ArrayList<MoneyPNL> oldPNLs = null;
            if (recyclerViewPNL.getAdapter() != null)
                oldPNLs = ((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet();

            Calendar calendar = (Calendar) get_dateStart().clone();
            int month1 = calendar.get(Calendar.MONTH);
            calendar.add(Calendar.DATE, 1);
            int month2 = calendar.get(Calendar.MONTH);
            calendar.add(Calendar.DATE, -1);
            boolean endMonth = month1 != month2;

            int shift = 0;
            long startSum = 0L;

            for (int iPoints = 0; iPoints < listPoints.size(); iPoints++)
            {
                double x, k, m;
                m = listPoints.get(iPoints).get_percent() / 1200;
                int loanTerm = listPoints.get(iPoints).get_loanTerm();
                long startSumLong = listPoints.get(iPoints).get_startSumLong();
                if (iPoints + 1 < listPoints.size())
                {
                    loanTerm -= listPoints.get(iPoints + 1).get_loanTerm();
                    startSumLong -= listPoints.get(iPoints + 1).get_startSumLong();
                }
                k = (m * Math.pow(1 + m, loanTerm)) / (Math.pow(1 + m, loanTerm) - 1);
                startSum += 100 * startSumLong;
                x = (double) startSum * k;


                for (int i = 0; i < loanTerm; i++)
                {
                    if (endMonth)
                    {
                        calendar.add(Calendar.DATE, 1);
                        calendar.add(Calendar.MONTH, 1);
                        calendar.add(Calendar.DATE, -1);
                    }
                    else
                        calendar.add(Calendar.MONTH, 1);

                    double percentSum = calcPercent_annuitantPayments(startSum, calendar, endMonth,
                            listPoints.get(iPoints).get_percent());
                    double bodySum = x - percentSum;
                    pnls.add(new MoneyPNL(
                            oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_id() : 0,
                            EnumTypeCategory.Cost,
                            increaseCategoryId,
                            oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldIncreaseCategoryId() : increaseCategoryId,
                            get_wallet(),
                            oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldCostCategoryId() : get_wallet(),
                            subCategoryId,
                            oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldSubCategoryId() : subCategoryId,
                            "", get_walletName(), "",
                            calendar.get(Calendar.DATE),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.YEAR),
                            (long) bodySum / 100,
                            (long) (oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldSumLong() : bodySum / 100),
                            0, "",
                            0L, 0L, 0L,
                            oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_isPlan() : true,
                            oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldIsPlan() : true,
                            get_id(),
                            0L,
                            true,
                            (long) percentSum / 100,
                            (long) (oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldPercentSumLong() : percentSum / 100),
                            null, null,
                            null, null));

                    startSum -= bodySum;

                    while (oldPNLs != null && oldPNLs.size() != 0 && oldPNLs.size() > i + 1 + shift &&
                            i + shift + 1 != oldPNLs.size() &&
                            oldPNLs.get(i + shift + 1).get_percentSumLong() == 0L)
                    {
                        startSum -= 100 * oldPNLs.get(i + shift + 1).get_sumLong();
                        pnls.add(oldPNLs.get(i + shift + 1));

                        k = (m * Math.pow(1 + m, loanTerm - i - 1)) / (Math.pow(1 + m, loanTerm - i - 1) - 1);
                        x = (double) Math.round(startSum * k);

                        shift++;
                    }
                }
            }

            if (startSum != 0d)
                pnls.get(pnls.size() - 1).set_sumLong(
                        (100 * pnls.get(pnls.size() - 1).get_sumLong() + startSum) / 100);

            MoneyPNLCreditAdapter adapter = new MoneyPNLCreditAdapter(_activity,
                    pnls , _this, _typeAction != EnumTypeAction.Insert);
            recyclerViewPNL.setAdapter(adapter);
            _refreshPNLs = false;
        }
        catch (Exception e)
        {
            recyclerViewPNL.setAdapter(null);
        }
    }
    private void set_differencialPayments()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_activity.getApplicationContext()))
        {
            final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
//            int startSum = (int) (100 * getStartSum());
            long startSum = 100L * getStartSumLong();
            double bodySum = Math.round(startSum / (double) get_loanTerm());

            Calendar calendar = (Calendar) get_dateStart().clone();
            ArrayList<MoneyPNL> pnls = new ArrayList<>();
            Long increaseCategoryId = globalVariable.get_costCreditsCategoryId();
            Long subCategoryId = dbHelper.OneMoneyCategoryId(get_id(), EnumTypeCategory.Cost);
            ArrayList<MoneyPNL> oldPNLs = null;
            if (recyclerViewPNL.getAdapter() != null)
                oldPNLs = ((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet();

            int shift = 0;
            for (int i = 0; i < get_loanTerm(); i++)
            {
                calendar.add(Calendar.MONTH, 1);
                double percentSum = (double) Math.round(startSum * getPercent() / 1200);
                pnls.add(new MoneyPNL(
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_id() : 0,
                        EnumTypeCategory.Cost,
                        increaseCategoryId,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldIncreaseCategoryId() : increaseCategoryId,
                        get_wallet(),
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldCostCategoryId() : get_wallet(),
                        subCategoryId,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).getOldSubCategoryId() : subCategoryId,
                        "", get_walletName(), "",
                        calendar.get(Calendar.DATE),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        (long) bodySum /100,
                        (long) (oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldSumLong() : bodySum/100),
                        0, "",
                        0L, 0L, 0L,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_isPlan() : true,
                        oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldIsPlan() : true,
                        get_id(), 0L, true,
                        (long) percentSum/100,
                        (long) (oldPNLs != null && oldPNLs.size() != 0 ? oldPNLs.get(i + shift).get_oldPercentSumLong() : percentSum/100),
                        null, null,
                        null, null));

                startSum -= bodySum;

                while (oldPNLs != null && oldPNLs.size() != 0 && oldPNLs.size() > i + 1 + shift &&
                        i + shift + 1 != oldPNLs.size() &&
//                        oldPNLs.get(i + shift + 1).get_percentSum() == 0d)
                        oldPNLs.get(i + shift + 1).get_percentSumLong() == 0d)
                {
//                    startSum -= 100 * oldPNLs.get(i + shift + 1).getSum();
                    startSum -= 100 * oldPNLs.get(i + shift + 1).get_sumLong();
                    if (get_loanTerm() - i - 1 == 0)
                        bodySum = startSum;
                    else
                        bodySum = Math.round(startSum / (double) (get_loanTerm() - i - 1));
                    pnls.add(oldPNLs.get(i + shift + 1));

                    shift++;
                }
            }
            if (startSum != 0d)
//                pnls.get(pnls.size() - 1).set_sum((100 * pnls.get(pnls.size() - 1).getSum() +
//                        startSum) / 100);
                pnls.get(pnls.size() - 1).set_sumLong((100 * pnls.get(pnls.size() - 1).get_sumLong() +
                        startSum) / 100);

            MoneyPNLCreditAdapter adapter = new MoneyPNLCreditAdapter(_activity,
                    pnls , _this, _typeAction != EnumTypeAction.Insert);
            recyclerViewPNL.setAdapter(adapter);
            _refreshPNLs = false;
        }
        catch (Exception e)
        {
            recyclerViewPNL.setAdapter(null);
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
        if (!CheckName())       return false;
        if (!CheckStartSum())   return false;
        if (!CheckLoanTerm())   return false;
        if (!CheckPercent())    return false;
        if (!CheckWallet())     return false;

        return true;
    }

    private boolean CheckName()
    {
        try
        {
            if (get_name().equals(""))
            {
                _editTextSum.requestFocus();
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

    private boolean CheckStartSum()
    {
        try
        {
            if (getStartSumLong() == 0L)
            {
                _editTextStartSum.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextStartSum, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifySumm), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextStartSum.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextStartSum, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectSumm), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckLoanTerm()
    {
        try
        {
            if (_editTextLoanTerm.getText().toString().equals(""))
            {
                _editTextLoanTerm.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextLoanTerm, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyLoanTerm), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextLoanTerm.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextLoanTerm, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectLoanTerm), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckPercent()
    {
        try
        {
            if (getPercent() == 0d)
            {
                _editTextCreditPercent.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextCreditPercent, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyCreditPercent), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextCreditPercent.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextCreditPercent, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectCreditPercent), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckWallet()
    {
        if (_spinnerWallet.getAdapter().getCount() == 0)
        {
            _spinnerWallet.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_spinnerWallet, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyWalletToUp), Toast.LENGTH_SHORT).show();

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
        if (_data != null)
            return _data.get_id();

        return 0L;
    }

    private Long get_targetId()
    {
        return ((MoneyTarget) _spinnerTarget.getSelectedItem()).get_id();
    }

    private String get_targetName()
    {
        return ((MoneyTarget) _spinnerTarget.getSelectedItem()).get_name();
    }

    private String get_name()
    {
        return _editTextName.getText().toString();
    }

    private Long getStartSumLong() throws Exception
    {
        try
        {
            String s = _editTextStartSum.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            long res = 0L;
            if (!s.equals(""))
                res = Long.parseLong(s);            if (pos < 0)
                return 100 * res;
            else if (pos + 2 == length)
                return 10 * res;
            else
                return res;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Long get_oldStartSumLong()
    {
        return (_data != null ? _data.get_oldStartSumLong() : 0L);
    }

    private Long getSumLong() throws Exception
    {
        try
        {
            String s = _editTextSum.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            long res = 0L;
            if (!s.equals(""))
                res = Long.parseLong(s);
            if (pos < 0)
                return 100 * res;
            else if (pos + 2 == length)
                return 10 * res;
            else
                return res;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Double getPercent() throws Exception
    {
        try
        {
            return Double.parseDouble(_editTextCreditPercent.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Calendar get_dateStart()
    {
        return _date;
    }

    private int get_loanTerm() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextLoanTerm.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private EnumTypeCredit get_typeCredit()
    {
        return (EnumTypeCredit) _spinnerTypeCredit.getSelectedItem();
    }

    private EnumCreditStatus get_status()
    {
        return (EnumCreditStatus) _spinnerStatus.getSelectedItem();
    }

    private EnumCreditStatus get_oldStatus()
    {
        if (_data == null)
            return EnumCreditStatus.Active;

        return _data.get_status();
    }

    private Long get_wallet()
    {
        return ((MoneyCategory) _spinnerWallet.getSelectedItem()).get_id();
    }

    private String get_walletName()
    {
        return ((MoneyCategory) _spinnerWallet.getSelectedItem()).get_name();
    }
    private Long get_oldWallet()
    {
        return _data != null ? _data.get_oldWallet() : 0;
    }

    private Long get_inc4CostCategoryId()
    {
        return ((MoneyCategory) _spinnerInc4CostCategoryId.getSelectedItem()).get_id();
    }

    private Long get_inc4CostSubCategoryId()
    {
        if (_spinnerInc4CostSubCategoryId.getAdapter() == null ||
                _spinnerInc4CostSubCategoryId.getAdapter().getCount() == 0)
            return 0L;

        return ((MoneyCategory) _spinnerInc4CostSubCategoryId.getSelectedItem()).get_id();
    }

    ArrayList<MoneyCreditPivotPoint> get_points()
    {
        return ((MoneyCreditPivotAdapter) recyclerViewPivots.getAdapter()).get_dataSet();
    }

    private ArrayList<MoneyCreditPivotPoint> get_deletedPoints()
    {
        return _data != null ? _data.get_deletedPivots() : null;
    }

    private ArrayList<MoneyPNL> get_pnls()
    {
        ArrayList<MoneyPNL> list = ((MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter()).get_dataSet();
        if (get_status().equals(EnumCreditStatus.Inactive))
            for (MoneyPNL item: list)
                item.set_isPlan(true);
        return list;
    }

    private ArrayList<MoneyPNL> get_deletedPNLs()
    {
        return _data != null ? _data.get_deletedPnls() : null;
    }
    //endregion

    //region Save Params
    protected MoneyCredit GetDataFromForm()
    {
        try
        {
            recalcPayments();

            return new MoneyCredit(
                    get_id(),
                    get_targetId(),
                    get_targetName(),
                    get_name(),
                    getStartSumLong(), get_oldStartSumLong(),
                    getSumLong(),
                    getPercent(),
                    get_dateStart(),
                    get_loanTerm(),
                    get_typeCredit(),
                    get_status(), get_oldStatus(),
                    get_wallet(), get_oldWallet(),
                    get_inc4CostCategoryId(),
                    get_inc4CostSubCategoryId(),
                    get_points(),
                    get_deletedPoints(),
                    get_pnls(),
                    get_deletedPNLs());
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
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyPNL)
        {
            ((MoneyPNL) object).set_creditId(_data != null ? _data.get_id() : 0);
            MoneyPNLCreditAdapter adapter = (MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter();
            if (adapter != null)
                adapter.AddItem((MoneyPNL) object);

            setEnabled(adapter.get_dataSet());
            _refreshPNLs = true;
            recalcPayments();

            return true;
        }
        else if (object instanceof MoneyCreditPivotPoint)
        {
            ((MoneyCreditPivotPoint) object).set_creditId(_data != null ? _data.get_id() : 0);
            MoneyCreditPivotAdapter adapter = (MoneyCreditPivotAdapter) recyclerViewPivots.getAdapter();
            if (adapter != null)
                adapter.AddItem((MoneyCreditPivotPoint) object);

            _refreshPNLs = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyPNL)
        {
            MoneyPNLCreditAdapter adapter = (MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter();
            if (adapter != null)
                adapter.UpdateItem((MoneyPNL) object);

            setEnabled(adapter.get_dataSet());

            return true;
        }
        else if (object instanceof MoneyCreditPivotPoint)
        {
            MoneyCreditPivotAdapter adapter = (MoneyCreditPivotAdapter) recyclerViewPivots.getAdapter();
            if (adapter != null)
                adapter.UpdateItem((MoneyCreditPivotPoint) object);

            _refreshPNLs = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyPNL)
        {
            if (_typeAction != EnumTypeAction.Insert)
                _data.add_deletedPNL((MoneyPNL) object);

            MoneyPNLCreditAdapter adapter = (MoneyPNLCreditAdapter) recyclerViewPNL.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset((MoneyPNL) object);

            setEnabled(adapter.get_dataSet());

            _refreshPNLs = true;
            recalcPayments();

            return true;
        }
        else if (object instanceof MoneyCreditPivotPoint)
        {
            if (_typeAction != EnumTypeAction.Insert && _data.get_id() != 0L)
                _data.add_deletedPoints((MoneyCreditPivotPoint) object);

            MoneyCreditPivotAdapter adapter = (MoneyCreditPivotAdapter) recyclerViewPivots.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset((MoneyCreditPivotPoint) object);

            _refreshPNLs = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion
}