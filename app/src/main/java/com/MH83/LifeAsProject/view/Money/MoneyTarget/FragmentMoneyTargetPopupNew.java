package com.MH83.LifeAsProject.view.Money.MoneyTarget;

import static com.MH83.LifeAsProject.interfac.Constants.REQUEST_GALLERY_FOR_MONEY_TARGET_POPUP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.common.PlanValueAdapter;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyPNLAccumulationAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyAccumulationTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyTargetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyTargetStatusAdapter;
import com.MH83.LifeAsProject.custom.BitmapCustom;
import com.MH83.LifeAsProject.custom.CustomSubsamplingScaleImageViewW;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyTargetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumAccumulationType;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyAccumulation;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;
import com.MH83.LifeAsProject.model.Custom.PlanValue;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Custom.PlanValue.FragmentPlanValuePopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyMovingPopupNew;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyTargetPopupNew extends CustomPopupBottomFragment<MoneyTarget>
    implements IFragmentViewCustom
{
    //region Constants
    private static final int _layoutId1 = R.layout.popup_money_target_new;
    //endregion

    //region Fragment parameters
    private final FragmentMoneyTargetPopupNew _this;
    private Long _moneyDreamId;
    private String _dateMonth;
    private String _dateYear;
    private int _dialogWidth = 1000;
    private int _rViewPlanValueHeight = 1000;
    private int _rViewPlanAccumulationHeight = 1000;
    private Integer _monthStart;
    private Integer _yearStart;
    private Integer _monthEnd;
    private Integer _yearEnd;
    private final Integer _newIdBase = 100000000;
    private Long _newId = 100000000L;
    //endregion

    //region Fragment Components
    private TextView _textViewDate;
    private EditText _editTextName;
    private ImageView _imageViewDateBack;
    private ImageView _imageViewForward;
    private ImageView _imageViewDateBackBack;
    private ImageView _imageViewForwardForward;
    private EditText _editTextPrice;
    private EditText _editTextAccumulation;
    private EditText _editTextCredit;
    private SubsamplingScaleImageView _subsamplingScaleImageView;
    private ImageView _imageViewPhoto;
    private Spinner _spinnerStatus;
    private LinearLayout linearLayoutHead;
    private TableLayout tableLayoutHead;
    private Button _buttonPrev;
    private Button _buttonPrev2;
    private Button _buttonNext2;
    private Button _buttonNext3;
    private NestedScrollView _nestedScrollView1;
    private NestedScrollView _nestedScrollView2;
    private NestedScrollView _nestedScrollView3;
    private Spinner _spinnerParentTarget;
    private RecyclerView recycleViewPlanValue;
    private RecyclerView recycleViewPlanAccumulation;
    private ImageView _imageViewAddToRView;
    private ConstraintLayout _constraintLayoutNSV2;
    private ConstraintLayout _constraintLayoutNSV3;
    private LinearLayout _linearLayoutNSV2;
    private LinearLayout _linearLayoutNSV3;
    private LinearLayout _linearLayoutHeadNSV2;
    private LinearLayout _linearLayoutHeadNSV3;
    private CheckBox _checkBoxExistAccumulation;
    private Spinner _spinnerAccumulationType;
    private EditText _editTextProgressionCoefficient;
    private EditText _editTextAccumulationStartSum;
    private TextView _textViewAccumulationStartDate;
    private TextView _textViewAccumulationEndDate;
    private ImageView _imageViewDateAccumulationBackBack;
    private ImageView _imageViewDateAccumulationBack;
    private ImageView _imageViewDateAccumulationForward;
    private ImageView _imageViewDateAccumulationForwardForward;
    private TableLayout _tableLayoutAccumulation;
    private ImageView _imageViewAddPlanAccumulation;
    private TextView _textViewStartSum;
    private TextView _textViewAccumulationCoef;
    private TextView _textViewEndDate;
    //endregion

    //region Constructors
    public FragmentMoneyTargetPopupNew()
    {
        super(_layoutId1,
                R.string.PopupTargetAdd,
                R.string.PopupTargetUpdate,
                R.string.errorUpdateMoneyTarget);

        _this = this;
    }
    public FragmentMoneyTargetPopupNew(AppCompatActivity activity,
                                       MoneyTarget data,
                                       EnumTypeAction typeAction,
                                       IFragmentViewCustom<MoneyTarget> iFragmentViewCustom,
                                       Long moneyDreamId)
    {
        super(activity, _layoutId1, data,
                typeAction,
                null,
                null,
                R.string.PopupTargetAdd,
                R.string.PopupTargetUpdate,
                R.string.errorUpdateMoneyTarget,
                iFragmentViewCustom ,
                null,
                null);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10)
            _dateMonth = "0" + month;
        else
            _dateMonth = String.valueOf(month);
        _dateYear = String.valueOf(calendar.get(Calendar.YEAR));

        _moneyDreamId = moneyDreamId;
        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_moneyDreamId(_moneyDreamId);
        globalVariable.set_dateMonth(_dateMonth);
        globalVariable.set_dateYear(_dateYear);

        _this = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_popup(this);
        _moneyDreamId = globalVariable.get_moneyDreamId();
        _dateMonth = globalVariable.get_dateMonth();
        _dateYear = globalVariable.get_dateYear();
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _editTextName = view.findViewById(R.id.editTextName);
        _imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        _imageViewDateBackBack = view.findViewById(R.id.imageViewDateBackBack);
        _textViewDate = view.findViewById(R.id.textViewDate);
        _imageViewForward = view.findViewById(R.id.imageViewDateForward);
        _imageViewForwardForward = view.findViewById(R.id.imageViewDateForwardForward);
        _editTextPrice = view.findViewById(R.id.editTextPrice);
        _editTextAccumulation = view.findViewById(R.id.editTextAccumulation);
        _editTextCredit = view.findViewById(R.id.editTextCredit);
        _subsamplingScaleImageView = view.findViewById(R.id.subsamplingScaleImageView);
        _imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
        _spinnerStatus = view.findViewById(R.id.spinnerStatus);
        linearLayoutHead = view.findViewById(R.id.linearLayoutHead);
        tableLayoutHead = view.findViewById(R.id.tableLayoutHead);
        _buttonNext2 = view.findViewById(R.id.buttonNext2);
        _buttonNext3 = view.findViewById(R.id.buttonNext3);
        _buttonPrev = view.findViewById(R.id.buttonPrev);
        _buttonPrev2 = view.findViewById(R.id.buttonPrev2);
        _nestedScrollView1 = view.findViewById(R.id.nestedScrollView1);
        _nestedScrollView2 = view.findViewById(R.id.nestedScrollView2);
        _nestedScrollView3 = view.findViewById(R.id.nestedScrollView3);
        _spinnerParentTarget = view.findViewById(R.id.spinnerParentTarget);
        recycleViewPlanValue = view.findViewById(R.id.recycleViewPlanValue);
        recycleViewPlanAccumulation = view.findViewById(R.id.recycleViewPlanAccumulation);
        _imageViewAddToRView = view.findViewById(R.id.imageViewAdd);
        _constraintLayoutNSV2 = view.findViewById(R.id.constraintLayoutNSV2);
        _constraintLayoutNSV3 = view.findViewById(R.id.constraintLayoutNSV3);
        _linearLayoutNSV2 = view.findViewById(R.id.linearLayoutNSV2);
        _linearLayoutNSV3 = view.findViewById(R.id.linearLayoutNSV3);
        _linearLayoutHeadNSV2 = view.findViewById(R.id.linearLayoutHeadNSV2);
        _linearLayoutHeadNSV3 = view.findViewById(R.id.linearLayoutHeadNSV3);
        _checkBoxExistAccumulation = view.findViewById(R.id.checkBoxExistAccumulation);
        _spinnerAccumulationType = view.findViewById(R.id.spinnerAccumulationType);
        _editTextProgressionCoefficient = view.findViewById(R.id.editTextProgressionCoefficient);
        _editTextAccumulationStartSum = view.findViewById(R.id.editTextAccumulationStartSum);
        _textViewAccumulationStartDate = view.findViewById(R.id.textViewAccumulationStartDate);
        _textViewAccumulationEndDate = view.findViewById(R.id.textViewAccumulationEndDate);
        _imageViewDateAccumulationBackBack = view.findViewById(R.id.imageViewDateAccumulationBackBack);
        _imageViewDateAccumulationBack = view.findViewById(R.id.imageViewDateAccumulationBack);
        _imageViewDateAccumulationForward = view.findViewById(R.id.imageViewDateAccumulationForward);
        _imageViewDateAccumulationForwardForward = view.findViewById(R.id.imageViewDateAccumulationForwardForward);
        _tableLayoutAccumulation = view.findViewById(R.id.tableLayoutAccumulation);
        _imageViewAddPlanAccumulation = view.findViewById(R.id.imageViewAddPlanAccumulation);
        _textViewStartSum = view.findViewById(R.id.textViewStartSum);
        _textViewAccumulationCoef = view.findViewById(R.id.textViewAccumulationCoef);
        _textViewEndDate = view.findViewById(R.id.textViewEndDate);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextName;
        _focusedViewToUpdate = null;
        _showInputToAdd = showInput;
        _showInputToUpdate = false;
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_data, _moneyDreamId} );
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        List<EnumStatus> list = new ArrayList<>();
        list.add(EnumStatus.Blank);
        list.add(EnumStatus.Started);
        list.add(EnumStatus.Finished);
        list.add(EnumStatus.Irrelevant);

        SpinnerMoneyTargetStatusAdapter adapter = new SpinnerMoneyTargetStatusAdapter(getContext(), list);
        _spinnerStatus.setAdapter(adapter);

        List<Long> list2 = new ArrayList<>();
        if (_data != null)
            list2.add(_data.get_id());
        SpinnerMoneyTargetAdapter adapter2 = new SpinnerMoneyTargetAdapter(getContext(),
                MoneyTargetDataSource.LoadDataParents(getContext(), _moneyDreamId, list2));
        _spinnerParentTarget.setAdapter(adapter2);

        ArrayList<PlanValue> list_PlanValue = new ArrayList<>();
        if (_data != null)
            list_PlanValue.addAll(_data.get_planValues());

        PlanValueAdapter adapter3 =
                new PlanValueAdapter(getContext(),
                        _data == null ? null : list_PlanValue,
                        _typeAction,
                        _iFragmentViewCustom,
                        this,
                        _data == null ? 0L : _data.get_id());
        recycleViewPlanValue.setAdapter(adapter3);

        List<EnumAccumulationType> accumulationTypeList = new ArrayList<>();
        accumulationTypeList.add(EnumAccumulationType.Linear);
        accumulationTypeList.add(EnumAccumulationType.AlgorithmicProgression);
//        accumulationTypeList.add(EnumAccumulationType.GeometricProgression);

        SpinnerMoneyAccumulationTypeAdapter spinnerMoneyAccumulationTypeAdapter =
                new SpinnerMoneyAccumulationTypeAdapter(getContext(), accumulationTypeList);
        _spinnerAccumulationType.setAdapter(spinnerMoneyAccumulationTypeAdapter);

        if (_data != null && _data.get_moneyAccumulation() != null)
        {
            MoneyPNLAccumulationAdapter moneyPNLAccumulationAdapter =
                    new MoneyPNLAccumulationAdapter(
                            getContext(), _data.get_moneyAccumulation().get_pnls(),
                            getName(), this);
            recycleViewPlanAccumulation.setAdapter(moneyPNLAccumulationAdapter);
        }
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
        {
            int height = displaymetrics.heightPixels;
            height -= _layoutCaption.getLayoutParams().height;
            height -= 3 * getContext().getResources().getDimension(R.dimen.BasePadding);
            height -= _buttonSave.getLayoutParams().height;
            height -= _editTextPrice.getLayoutParams().height;
            height -= linearLayoutHead.getLayoutParams().height;
            height -= 3 * getContext().getResources().getDimension(R.dimen.BasePadding);
            _subsamplingScaleImageView.getLayoutParams().height = height;

            _dialogWidth = Math.max((int) (4 * height / 3f), 1200);

            int height2 = displaymetrics.heightPixels;
            height2 -= _layoutCaption.getLayoutParams().height;
            height2 -= 7 * getContext().getResources().getDimension(R.dimen.BasePadding);
            height2 -= _buttonSave.getLayoutParams().height;
            height2 -= _linearLayoutHeadNSV2.getLayoutParams().height;
            height2 -= _tableLayoutAccumulation.getLayoutParams().height;
            _rViewPlanAccumulationHeight = height2;

            int height3 = displaymetrics.heightPixels;
            height3 -= _layoutCaption.getLayoutParams().height;
            height3 -= 7 * getContext().getResources().getDimension(R.dimen.BasePadding);
            height3 -= _buttonSave.getLayoutParams().height;
            height3 -= _linearLayoutHeadNSV3.getLayoutParams().height;
            _rViewPlanValueHeight = height3;
        }

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                int height = displaymetrics.heightPixels;
                height -= _layoutCaption.getLayoutParams().height;
                height -= 8 * getContext().getResources().getDimension(R.dimen.BasePadding);
                height -= _buttonSave.getLayoutParams().height;

                _rViewPlanAccumulationHeight = height - _linearLayoutHeadNSV2.getLayoutParams().height;

                height -= _linearLayoutHeadNSV3.getLayoutParams().height;
                _rViewPlanValueHeight = height;
            }
        }

        _nestedScrollView2.setVisibility(View.INVISIBLE);
        _nestedScrollView2.getLayoutParams().height = 0;
        _nestedScrollView2.requestLayout();

        _constraintLayoutNSV2.getLayoutParams().height = 0;
        _constraintLayoutNSV2.requestLayout();

        recycleViewPlanAccumulation.getLayoutParams().height = 0;
        recycleViewPlanAccumulation.requestLayout();

        _linearLayoutNSV2.getLayoutParams().height = 0;
        _linearLayoutNSV2.requestLayout();

        _nestedScrollView3.setVisibility(View.INVISIBLE);
        _nestedScrollView3.getLayoutParams().height = 0;
        _nestedScrollView3.requestLayout();

        _constraintLayoutNSV3.getLayoutParams().height = 0;
        _constraintLayoutNSV3.requestLayout();

        recycleViewPlanValue.getLayoutParams().height = 0;
        recycleViewPlanValue.requestLayout();

        _linearLayoutNSV3.getLayoutParams().height = 0;
        _linearLayoutNSV3.requestLayout();
    }

    private void SetRViewHeight()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                int height = linearLayoutHead.getLayoutParams().height;
                height += ((CustomSubsamplingScaleImageViewW) _subsamplingScaleImageView).get_Height();
                height -= getContext().getResources().getDimension(R.dimen.BasePadding);
                _rViewPlanAccumulationHeight = height - _linearLayoutHeadNSV2.getLayoutParams().height;

                height -= _linearLayoutHeadNSV3.getLayoutParams().height;
                _rViewPlanValueHeight = height;
            }
            else
            {
                int height = tableLayoutHead.getLayoutParams().height;
                height += ((CustomSubsamplingScaleImageViewW) _subsamplingScaleImageView).get_Height();
                height -= getContext().getResources().getDimension(R.dimen.BasePadding);
                _rViewPlanAccumulationHeight = height - _linearLayoutHeadNSV2.getLayoutParams().height;

                height -= _linearLayoutHeadNSV3.getLayoutParams().height;
                _rViewPlanValueHeight = height;
            }
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

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                getDialog().getWindow().setLayout(_dialogWidth /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void SetComponentsSettingPost(View view)
    {
        super.SetComponentsSetting(view);

        _imageViewDelete.setVisibility(
                !_typeAction.equals(EnumTypeAction.Insert) &&
                        !((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).ExistFactPNL() ?
                        View.VISIBLE : View.INVISIBLE);
    }

    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _subsamplingScaleImageView.setMaxScale(10);

        _imageViewDateBack.setOnClickListener(view -> setPrevMonthDate());

        _imageViewDateBackBack.setOnClickListener(view -> setPrevYearDate());

        _imageViewForward.setOnClickListener(view -> setNextMonthDate());

        _imageViewForwardForward.setOnClickListener(view -> setNextYearDate());

        _editTextPrice.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                try
                {
                    int acc = getPrice() - getCredit();
                    if (acc >= 0)
                        set_Accumulation(String.valueOf(acc));
                    else
                    {
                        set_Accumulation(getPrice().toString());
                        set_Credit("");
                    }
                }
                catch (Exception e)
                {
                    try
                    {
                        set_Accumulation(getPrice().toString());
                    }
                    catch (Exception ex)
                    {
                        set_Accumulation("");
                    }
                }
            }
        });

        _editTextAccumulation.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                try
                {
                    int credit = getPrice() - getAccumulation();
                    if (credit >= 0)
                        set_Credit(String.valueOf(credit));
                    else
                    {
                        set_Credit("");
                        set_Price(getAccumulation().toString());
                        Toast.makeText(_activity,
                                getContext().getString(R.string.specifyCorrectAccumulation), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    try
                    {
                        set_Credit(getPrice().toString());
                    }
                    catch (Exception ex)
                    {
                        set_Credit("");
                        if (_activity != null)
                            Toast.makeText(_activity,
                                    getContext().getString(R.string.specifyPrice), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        _imageViewPhoto.setOnClickListener(ShowPhotoSelect());
        _subsamplingScaleImageView.setOnClickListener(ShowPhotoSelect());

        _buttonNext2.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            SetRViewHeight();

            _nestedScrollView1.setVisibility(View.INVISIBLE);
            _nestedScrollView1.getLayoutParams().height = 0;

            _nestedScrollView2.setVisibility(View.VISIBLE);
            _nestedScrollView2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV2.requestLayout();

            recycleViewPlanAccumulation.getLayoutParams().height = _rViewPlanAccumulationHeight;
            recycleViewPlanAccumulation.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _linearLayoutNSV2.requestLayout();

            recalcPnls();
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

            recycleViewPlanAccumulation.getLayoutParams().height = 0;
            recycleViewPlanAccumulation.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = 0;
            _linearLayoutNSV2.requestLayout();
        });

        _buttonNext3.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            _nestedScrollView2.setVisibility(View.INVISIBLE);
            _nestedScrollView2.getLayoutParams().height = 0;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = 0;
            _constraintLayoutNSV2.requestLayout();

            recycleViewPlanAccumulation.getLayoutParams().height = 0;
            recycleViewPlanAccumulation.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = 0;
            _linearLayoutNSV2.requestLayout();

            _nestedScrollView3.setVisibility(View.VISIBLE);
            _nestedScrollView3.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView3.requestLayout();

            _constraintLayoutNSV3.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV3.requestLayout();

            recycleViewPlanValue.getLayoutParams().height = _rViewPlanValueHeight;
            recycleViewPlanValue.requestLayout();

            _linearLayoutNSV3.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _linearLayoutNSV3.requestLayout();
        });

        _buttonPrev2.setOnClickListener(view ->
        {
            InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_editTextName.getWindowToken(), 0);

            _nestedScrollView2.setVisibility(View.VISIBLE);
            _nestedScrollView2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _nestedScrollView2.requestLayout();

            _constraintLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            _constraintLayoutNSV2.requestLayout();

            recycleViewPlanAccumulation.getLayoutParams().height = _rViewPlanAccumulationHeight;
            recycleViewPlanAccumulation.requestLayout();

            _linearLayoutNSV2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _linearLayoutNSV2.requestLayout();

            _nestedScrollView3.setVisibility(View.INVISIBLE);
            _nestedScrollView3.getLayoutParams().height = 0;
            _nestedScrollView3.requestLayout();

            _constraintLayoutNSV3.getLayoutParams().height = 0;
            _constraintLayoutNSV3.requestLayout();

            recycleViewPlanValue.getLayoutParams().height = 0;
            recycleViewPlanValue.requestLayout();

            _linearLayoutNSV3.getLayoutParams().height = 0;
            _linearLayoutNSV3.requestLayout();
        });

        _imageViewAddToRView.setOnClickListener(view ->
        {
            FragmentPlanValuePopupNew _fragmentPopup =
                    new FragmentPlanValuePopupNew(_activity,
                            null,
                            _typeAction,
                            EnumTypeAction.Insert,
                            _iFragmentViewCustom ,_this,
                            _data == null ? 0L : _data.get_id(),
                            ((PlanValueAdapter)recycleViewPlanValue.getAdapter()).get_dataSet());
            _fragmentPopup.PopupShow();
        });

        _editTextAccumulationStartSum.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s)
            {
                recalcPnls();
            }
        });

        _editTextProgressionCoefficient.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            { }

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

                recalcPnls();
            }
        });

        _checkBoxExistAccumulation.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            set_AccumulationEnabled(isChecked);
            recalcPnls();
        });

        _imageViewDateAccumulationBack.setOnClickListener(view ->
        {
            if (_data != null && _data.get_moneyAccumulation() != null && !_data.get_moneyAccumulation().canUpdate())
                return;
            setPrevAccumulationMonthDate();
            recalcPnls();
        });

        _imageViewDateAccumulationBackBack.setOnClickListener(view ->
        {
            if (_data != null && _data.get_moneyAccumulation() != null && !_data.get_moneyAccumulation().canUpdate())
                return;
            setPrevAccumulationYearDate();
            recalcPnls();
        });

        _imageViewDateAccumulationForward.setOnClickListener(view ->
        {
            if (_data != null && _data.get_moneyAccumulation() != null && !_data.get_moneyAccumulation().canUpdate())
                return;
            setNextAccumulationMonthDate();
            recalcPnls();
        });

        _imageViewDateAccumulationForwardForward.setOnClickListener(view ->
        {
            if (_data != null && _data.get_moneyAccumulation() != null && !_data.get_moneyAccumulation().canUpdate())
                return;
            setNextAccumulationYearDate();
            recalcPnls();
        });

        _spinnerAccumulationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                _editTextProgressionCoefficient.setEnabled(
                        get_existAccumulation() && !getAccumulationType().equals(EnumAccumulationType.Linear));
                recalcPnls();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        _imageViewAddPlanAccumulation.setOnClickListener(view ->
        {
            HideFocus();

            IMASCategory moneyAccountingSumIncrease = new MoneyCategory(-1L, 0L, 0, getName(),
                    0L, EnumTypeCategory.Wallet, false, false,
                    false, true, 0L,
                    EnumCategoryType.Base,
//                    EnumCategoryStatus.Active,
                    0L,
                    false,
                    0L);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, getMonthStart() - 1);
            calendar.set(Calendar.YEAR, getYearStart());
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DATE, 1);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            for (MoneyPNL moneyPNL : ((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).get_dataSet())
                if (moneyPNL.get_isPlan())
                {
                    calendar = moneyPNL.getCalendar();
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    break;
                }

            MoneyPNL item = new MoneyPNL(0L,
                    EnumTypeCategory.Wallet,
                    0L,
                    0L,
                    0L,
                    0L,
                    0L,
                    0L,
                    getName(),
                    "",
                    "",
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    0L, 0L,
                    0, "",
                    0L, 0L, 0L,
                    true,
                    true,
                    0L,
                    get_accumulationId(),
                    false,
                    0L, 0L,
                    null, null,
                    null,null);

            FragmentMoneyMovingPopupNew _fragmentPopupNew = new FragmentMoneyMovingPopupNew(
                    _activity, item, EnumTypeAction.Insert,
                    this, null, moneyAccountingSumIncrease,
                    Calendar.getInstance(), Calendar.getInstance(), false, false);

            _fragmentPopupNew.PopupShow();
        });

        _textViewStartSum.setOnClickListener(view -> HideFocus() );

        _textViewAccumulationCoef.setOnClickListener(view -> HideFocus() );

        _textViewAccumulationEndDate.setOnClickListener(view -> HideFocus() );

        _textViewEndDate.setOnClickListener(view -> HideFocus() );

        _textViewAccumulationStartDate.setOnClickListener(view -> HideFocus() );
    }

    private void set_AccumulationEnabled(boolean enabled)
    {
        _spinnerAccumulationType.setEnabled(enabled);
        _textViewAccumulationStartDate.setEnabled(enabled);
        _textViewAccumulationEndDate.setEnabled(enabled);
        _editTextAccumulationStartSum.setEnabled(enabled);
        _imageViewDateAccumulationBack.setEnabled(enabled);
        _imageViewDateAccumulationBackBack.setEnabled(enabled);
        _imageViewDateAccumulationForward.setEnabled(enabled);
        _imageViewDateAccumulationForwardForward.setEnabled(enabled);
        _editTextProgressionCoefficient.setEnabled(
                enabled && !getAccumulationType().equals(EnumAccumulationType.Linear));
    }

    private View.OnClickListener ShowPhotoSelect()
    {
        return view ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            getActivity().startActivityForResult(intent, REQUEST_GALLERY_FOR_MONEY_TARGET_POPUP);
        };
    }

    private void HideFocus()
    {
        InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextCredit.getWindowToken(), 0);
    }
    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        _data = (MoneyTarget) objects[0];
        Long moneyDreamId = (Long) objects[1];

        if (_data != null)
        {
            set_moneyDreamId(_data.get_dreamId());
            set_Date(_data.get_month(), _data.get_year());
            set_Name(_data.get_name());
            set_Price(_data.get_price().toString());
            set_Accumulation(_data.get_accumulation().toString());
            set_Credit(_data.get_credit().toString());

            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_imagePath(false) != null)
                set_Image(globalVariable.get_imagePath(false));
            else
                set_Image(_data.get_imagePath());

            set_Status(_data.get_status());
            set_headTarget(_data.get_parentId());
            set_moneyAccumulation(_data.get_moneyAccumulation());
        }
        else
        {
            set_moneyDreamId(moneyDreamId);

            set_Date(_dateMonth, _dateYear);
            set_Name("");
            set_Price("");
            set_Accumulation("");
            set_Credit("");

            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_imagePath(false) != null)
                set_Image(globalVariable.get_imagePath(false));

            set_Status(EnumStatus.Blank);
            set_headTarget(-11111L);
            set_moneyAccumulation(null);
        }

        recalcPnls();
    }

    private void set_moneyDreamId(Long moneyDreamId)
    {
        _moneyDreamId = moneyDreamId;
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_moneyDreamId(_moneyDreamId);
    }

    private void set_Date(String month, String year)
    {
        _dateMonth = month;
        _dateYear = year;
        if (_data != null)
        {
            _data.set_month(month);
            _data.set_year(year);
        }
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_dateMonth(_dateMonth);
        globalVariable.set_dateYear(_dateYear);

        setDateView(month, year, _textViewDate);

        _monthEnd = Integer.parseInt(month);
        _yearEnd = Integer.parseInt(year);
        setDateView(month, year, _textViewAccumulationEndDate);
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }

    private void set_Price(String price)
    {
        _editTextPrice.setText(price);
    }

    private void set_Accumulation(String accumulation)
    {
        _editTextAccumulation.setText(accumulation);
    }

    private void set_Credit(String credit)
    {
        _editTextCredit.setText(credit);
    }

    public void set_Image(String imagePath)
    {
        File filePath = new File(imagePath);
        try
        {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
            _subsamplingScaleImageView.setImage(ImageSource.bitmap(bitmap));
        }
        catch (Exception ex) { }
    }

    public void set_Image(Uri uri)
    {
        _subsamplingScaleImageView.setImage(ImageSource.uri(uri));

        File internalStorage = getContext().getDir(DBHelper.getDbName(getContext()) + "TargetPictures", Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, "tmp.jpeg");
        String picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            Bitmap viewBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_imagePath(picturePath);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_imagePath(null);
        }
    }

    private void set_Status(EnumStatus enumStatus)
    {
        for (int i = 0; i < _spinnerStatus.getAdapter().getCount(); i++)
            if (_spinnerStatus.getAdapter().getItem(i) == enumStatus)
            {
                _spinnerStatus.setSelection(i);
                break;
            }
    }

    private void set_headTarget(Long id)
    {
        for (int i = 0; i < _spinnerParentTarget.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyTarget) _spinnerParentTarget.getAdapter().getItem(i)).get_id(), id))
            {
                _spinnerParentTarget.setSelection(i);
                break;
            }
    }

    private void set_moneyAccumulation(MoneyAccumulation moneyAccumulation)
    {
        if (moneyAccumulation == null)
        {
            Calendar calendar = Calendar.getInstance();

            set_existAccumulation(false);
            set_accumulationType(EnumAccumulationType.Linear);
            set_progressionCoefficient(0);
            set_monthStart(calendar.get(Calendar.MONTH) + 1);
            set_yearStart(calendar.get(Calendar.YEAR));
            set_monthEnd(Integer.parseInt(_dateMonth));
            set_yearEnd(Integer.parseInt(_dateYear));
            set_startSum(0);
        }
        else
        {
            set_existAccumulation(true);
            set_accumulationType(moneyAccumulation.get_accumulationType());
            set_progressionCoefficient(moneyAccumulation.get_progressionCoefficent());
            set_monthStart(moneyAccumulation.get_monthStart());
            set_yearStart(moneyAccumulation.get_yearStart());
            set_monthEnd(moneyAccumulation.get_monthEnd());
            set_yearEnd(moneyAccumulation.get_yearEnd());
            set_startSum(moneyAccumulation.get_startSum());
        }

        setDateView(_monthStart.toString(), _yearStart.toString(), _textViewAccumulationStartDate);
        setDateView(_monthEnd.toString(), _yearEnd.toString(), _textViewAccumulationEndDate);
    }

    private void set_existAccumulation(boolean existAccumulation)
    {
        _checkBoxExistAccumulation.setChecked(existAccumulation);
        set_AccumulationEnabled(existAccumulation);
    }
    private void set_accumulationType(EnumAccumulationType accumulationType)
    {
        for (int i = 0; i < _spinnerAccumulationType.getAdapter().getCount(); i++)
            if (_spinnerAccumulationType.getAdapter().getItem(i).equals(accumulationType))
            {
                _spinnerAccumulationType.setSelection(i);
                break;
            }
    }

    private void set_progressionCoefficient(Integer progressionCoefficient)
    {
        if (progressionCoefficient == 0)
            _editTextProgressionCoefficient.setText("");
        else if (Math.floorMod(progressionCoefficient, 100L) == 0)
        {
            _editTextProgressionCoefficient.setText(String.valueOf(Math.floorDiv(progressionCoefficient, 100L)));
        }
        else
        {
            _editTextProgressionCoefficient.setText(String.valueOf(progressionCoefficient / 100d));
        }
    }

    private void set_monthStart(Integer monthStart) { _monthStart = monthStart; }
    private void set_yearStart(Integer yearStart) { _yearStart = yearStart; }
    private void set_monthEnd(Integer monthEnd)
    {
        _monthEnd = monthEnd;
        recalcPnls();
    }
    private void set_yearEnd(Integer yearEnd)
    {
        _yearEnd = yearEnd;
        recalcPnls();
    }

    private void set_startSum(Integer startSum)
    {
        if (startSum == 0)
            _editTextAccumulationStartSum.setText("");
        else if (Math.floorMod(startSum, 100L) == 0)
        {
            _editTextAccumulationStartSum.setText(String.valueOf(Math.floorDiv(startSum, 100L)));
        }
        else
        {
            _editTextAccumulationStartSum.setText(String.valueOf(startSum / 100d));
        }
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
            set_Date(String.valueOf(month), String.valueOf(year));
        }
        catch (Exception e) { }
    }

    private void setPrevYearDate()
    {
        int month, year;
        try
        {
            month = Integer.parseInt(getMonth());
            year = Integer.parseInt(getYear());
            year--;
            set_Date(String.valueOf(month), String.valueOf(year));
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
            set_Date(String.valueOf(month), String.valueOf(year));
        }
        catch (Exception e) { }
    }

    private void setNextYearDate()
    {
        int month, year;
        try
        {
            month = Integer.parseInt(getMonth());
            year = Integer.parseInt(getYear());
            year++;
            set_Date(String.valueOf(month), String.valueOf(year));
        }
        catch (Exception e) { }
    }

    private void setPrevAccumulationMonthDate()
    {
        try
        {
            if (_monthStart == 1)
            {
                _monthStart = 12;
                _yearStart--;
            }
            else
                _monthStart--;

            setDateView(_monthStart.toString(), _yearStart.toString(), _textViewAccumulationStartDate);
        }
        catch (Exception e) { }
    }

    private void setPrevAccumulationYearDate()
    {
        try
        {
            _yearStart--;

            setDateView(_monthStart.toString(), _yearStart.toString(), _textViewAccumulationStartDate);
        }
        catch (Exception e) { }
    }

    private void setNextAccumulationMonthDate()
    {
        try
        {
            if (_monthStart == 12)
            {
                _monthStart = 1;
                _yearStart++;
            }
            else
                _monthStart++;

            setDateView(_monthStart.toString(), _yearStart.toString(), _textViewAccumulationStartDate);
        }
        catch (Exception e) { }
    }

    private void setNextAccumulationYearDate()
    {
        try
        {
            _yearStart++;

            setDateView(_monthStart.toString(), _yearStart.toString(), _textViewAccumulationStartDate);
        }
        catch (Exception e) { }
    }

    private void recalcPnls()
    {
        Long increaseCategoryId = 0L;
        if (_data != null)
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                increaseCategoryId = dbHelper.OneMoneyCategoryId4Target(_data.get_id(), EnumTypeCategory.Wallet);
            }
            catch (Exception e) { }
        }

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.MONTH, _monthStart);
        calendarStart.set(Calendar.YEAR, _yearStart);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.MONTH, _monthEnd);
        calendarEnd.set(Calendar.YEAR, _yearEnd);

        int cnt = 0;
        try
        {
            if (!getAccumulation().equals(getStartSum() / 100))
                while (calendarEnd.getTime().getTime() >= calendarStart.getTime().getTime())
                {
                    cnt++;
                    calendarStart.add(Calendar.MONTH, 1);
                }
        }
        catch (Exception e) { }

        ArrayList<MoneyPNL> list = new ArrayList<>();
        if (cnt > 0 && get_existAccumulation())
        {
            calendarStart.set(Calendar.MONTH, _monthStart);
            calendarStart.set(Calendar.YEAR, _yearStart);
            calendarStart.set(Calendar.DATE, 1);

            Long sum = 0L;
            long delta;
            try
            {
                sum = 100L * getAccumulation() - getStartSum();
            }
            catch (Exception e) {}
            int progressionCoefficient = 0;
            try
            {
                progressionCoefficient = get_progressionCoefficient();
            }
            catch (Exception e) {}

            ArrayList<MoneyPNL> oldPNLs = new ArrayList<>();
            if (recycleViewPlanAccumulation.getAdapter() != null)
                oldPNLs = ((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).get_dataSet();

            switch (getAccumulationType())
            {
                case Linear:
                    delta = sum / cnt;

                    for (int i = 0; i < cnt; i++)
                    {
                        calendarStart.add(Calendar.DATE, -1);

                        if (i < oldPNLs.size() &&
                                (!oldPNLs.get(i).get_isPlan() || !oldPNLs.get(i).get_calculated()))
                        {
                            MoneyPNL moneyPNL = new MoneyPNL(
                                    oldPNLs.get(i).get_id(),
                                    EnumTypeCategory.Wallet,
                                    oldPNLs.get(i).getIncreaseCategoryId(),
                                    oldPNLs.get(i).getOldIncreaseCategoryId(),
                                    oldPNLs.get(i).getCostCategoryId(),
                                    oldPNLs.get(i).getOldCostCategoryId(),
                                    oldPNLs.get(i).getSubCategoryId(),
                                    oldPNLs.get(i).getOldSubCategoryId(),
                                    getName(),
                                    oldPNLs.get(i).getCostCategoryName(),
                                    oldPNLs.get(i).getSubCategoryName(),
                                    calendarStart.get(Calendar.DATE), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                                    oldPNLs.get(i).get_sumLong(), oldPNLs.get(i).get_oldSumLong(),
                                    0, "",
                                    0L, 0L, 0L,
                                    oldPNLs.get(i).get_isPlan(), oldPNLs.get(i).get_oldIsPlan(),
                                    0L, oldPNLs.get(i).get_accumulationId(), oldPNLs.get(i).get_calculated(),
                                    0L, 0L, null, null, null, null);

                            list.add(moneyPNL);
                            sum -= moneyPNL.get_sumLong();
                            if (cnt - i - 1 > 0)
                                delta = sum / (cnt - i - 1);
                            calendarStart.add(Calendar.DATE, 1);
                            calendarStart.add(Calendar.MONTH, 1);
                            continue;
                        }

                        if (i < oldPNLs.size() && oldPNLs.get(i).get_id() > 0 && oldPNLs.get(i).get_id() < _newIdBase)
                        {
                            MoneyPNL moneyPNL = new MoneyPNL(
                                    oldPNLs.get(i).get_id(),
                                    EnumTypeCategory.Wallet,
                                    oldPNLs.get(i).getIncreaseCategoryId(),
                                    oldPNLs.get(i).getOldIncreaseCategoryId(),
                                    oldPNLs.get(i).getCostCategoryId(),
                                    oldPNLs.get(i).getOldCostCategoryId(),
                                    oldPNLs.get(i).getSubCategoryId(),
                                    oldPNLs.get(i).getOldSubCategoryId(),
                                    getName(),
                                    oldPNLs.get(i).getCostCategoryName(),
                                    oldPNLs.get(i).getSubCategoryName(),
                                    calendarStart.get(Calendar.DATE), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                                    delta, oldPNLs.get(i).get_oldSumLong(),
                                    0, "",
                                    0L, 0L, 0L,
                                    oldPNLs.get(i).get_isPlan(), oldPNLs.get(i).get_oldIsPlan(),
                                    0L, oldPNLs.get(i).get_accumulationId(), oldPNLs.get(i).get_calculated(),
                                    0L, 0L, null, null, null, null);

                            list.add(moneyPNL);
                            sum -= delta;
                            calendarStart.add(Calendar.DATE, 1);
                            calendarStart.add(Calendar.MONTH, 1);
                            continue;
                        }

                        list.add(new MoneyPNL(
                                _newId++,
                                EnumTypeCategory.Wallet,
                                increaseCategoryId,
                                increaseCategoryId,
                                0L,
                                0L,
                                0L,
                                0L,
                                getName(), "", "",
                                calendarStart.get(Calendar.DATE), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                                delta, delta,
                                0, "",
                                0L, 0L, 0L,
                                true, true,
                                0L, 0L, true,
                                0L, 0L, null, null, null, null));
                        sum -= delta;

                        calendarStart.add(Calendar.DATE, 1);
                        calendarStart.add(Calendar.MONTH, 1);
                    }
                    list.get(list.size() - 1).set_sumLong(list.get(list.size() - 1).get_sumLong() + sum);
                    if (_data != null)
                        for (int i = cnt; i < oldPNLs.size(); i++)
                            if (oldPNLs.get(i).get_id() != 0 && oldPNLs.get(i).get_id() < _newIdBase)
                                _data.get_moneyAccumulation().get_deletedPnls().add(oldPNLs.get(i));
                    break;
                case AlgorithmicProgression:
                    long average = sum / cnt;
                    if (Math.floorMod(cnt, 2) == 0)
                        delta = average - (long) (cnt / 2) * progressionCoefficient + progressionCoefficient / 2;
                    else
                        delta = average - (long) (cnt / 2) * progressionCoefficient;

                    for (int i = 0; i < cnt; i++)
                    {
                        calendarStart.add(Calendar.DATE, -1);

                        if (i < oldPNLs.size() && (!oldPNLs.get(i).get_isPlan() || !oldPNLs.get(i).get_calculated()))
                        {
                            MoneyPNL moneyPNL = new MoneyPNL(
                                    oldPNLs.get(i).get_id(),
                                    EnumTypeCategory.Wallet,
                                    oldPNLs.get(i).getIncreaseCategoryId(),
                                    oldPNLs.get(i).getOldIncreaseCategoryId(),
                                    oldPNLs.get(i).getCostCategoryId(),
                                    oldPNLs.get(i).getOldCostCategoryId(),
                                    oldPNLs.get(i).getSubCategoryId(),
                                    oldPNLs.get(i).getOldSubCategoryId(),
                                    getName(),
                                    oldPNLs.get(i).getCostCategoryName(),
                                    oldPNLs.get(i).getSubCategoryName(),
                                    calendarStart.get(Calendar.DATE), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                                    oldPNLs.get(i).get_sumLong(), oldPNLs.get(i).get_oldSumLong(),
                                    0, "",
                                    0L, 0L, 0L,
                                    oldPNLs.get(i).get_isPlan(), oldPNLs.get(i).get_oldIsPlan(),
                                    0L, oldPNLs.get(i).get_accumulationId(), oldPNLs.get(i).get_calculated(),
                                    0L, 0L, null, null, null, null);

                            list.add(moneyPNL);
                            sum -= moneyPNL.get_sumLong();
                            average = sum / (cnt - i - 1);
                            if (Math.floorMod(cnt - i - 1, 2) == 0)
                                delta = average - (long) ((cnt - i - 1) / 2) * progressionCoefficient + progressionCoefficient / 2;
                            else
                                delta = average - (long) ((cnt - i - 1) / 2) * progressionCoefficient;
                            calendarStart.add(Calendar.DATE, 1);
                            calendarStart.add(Calendar.MONTH, 1);
                            continue;
                        }

                        if (i < oldPNLs.size() && oldPNLs.get(i).get_id() > 0 && oldPNLs.get(i).get_id() < _newIdBase)
                        {
                            MoneyPNL moneyPNL = new MoneyPNL(
                                    oldPNLs.get(i).get_id(),
                                    EnumTypeCategory.Wallet,
                                    oldPNLs.get(i).getIncreaseCategoryId(),
                                    oldPNLs.get(i).getOldIncreaseCategoryId(),
                                    oldPNLs.get(i).getCostCategoryId(),
                                    oldPNLs.get(i).getOldCostCategoryId(),
                                    oldPNLs.get(i).getSubCategoryId(),
                                    oldPNLs.get(i).getOldSubCategoryId(),
                                    getName(),
                                    oldPNLs.get(i).getCostCategoryName(),
                                    oldPNLs.get(i).getSubCategoryName(),
                                    calendarStart.get(Calendar.DATE), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                                    delta, oldPNLs.get(i).get_oldSumLong(),
                                    0, "",
                                    0L, 0L, 0L,
                                    oldPNLs.get(i).get_isPlan(), oldPNLs.get(i).get_oldIsPlan(),
                                    0L, oldPNLs.get(i).get_accumulationId(), oldPNLs.get(i).get_calculated(),
                                    0L, 0L, null, null, null, null);

                            list.add(moneyPNL);
                            sum -= delta;
                            delta += progressionCoefficient;
                            calendarStart.add(Calendar.DATE, 1);
                            calendarStart.add(Calendar.MONTH, 1);
                            continue;
                        }

                        list.add(new MoneyPNL(
                                _newId++,
                                EnumTypeCategory.Wallet,
                                increaseCategoryId, increaseCategoryId,
                                0L, 0L, 0L, 0L,
                                getName(), "", "",
                                calendarStart.get(Calendar.DATE), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                                delta, delta,
                                0, "",
                                0L, 0L, 0L,
                                true, true,
                                0L, 0L, true,
                                0L, 0L, null, null, null, null));
                        sum -= delta;
                        delta += progressionCoefficient;

                        calendarStart.add(Calendar.DATE, 1);
                        calendarStart.add(Calendar.MONTH, 1);
                    }
                    list.get(list.size() - 1).set_sumLong(list.get(list.size() - 1).get_sumLong() + sum);
                    break;
                default:
            }
        }

        calendarStart.set(Calendar.MONTH, _monthStart);
        calendarStart.set(Calendar.YEAR, _yearStart);
        MoneyPNLAccumulationAdapter moneyPNLAccumulationAdapter = new MoneyPNLAccumulationAdapter(
                getContext(), list, getName(), this);
        recycleViewPlanAccumulation.setAdapter(moneyPNLAccumulationAdapter);
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
        if (!CheckName())   return false;
        if (!CheckPrice())  return false;
        if (!CheckAccumulation())  return false;
        if (!CheckCredit())  return false;

        return true;
    }

    private boolean CheckName()
    {
        if (getName().equals(""))
        {
            _editTextName.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_activity,
                    getContext().getString(R.string.specifyName), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean CheckPrice()
    {
        try
        {
            getPrice();
        }
        catch (Exception e)
        {
            _editTextPrice.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextPrice, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_activity,
                    getContext().getString(R.string.specifyPrice), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean CheckAccumulation()
    {
        try
        {
            getAccumulation();
        }
        catch (Exception e)
        {
            _editTextAccumulation.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextAccumulation, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_activity,
                    getContext().getString(R.string.specifyAccumulation), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean CheckCredit()
    {
        try
        {
            getCredit();
        }
        catch (Exception e)
        {
            _editTextCredit.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextCredit, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_activity,
                    getContext().getString(R.string.specifyCredit), Toast.LENGTH_SHORT).show();
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
    private Long get_Id()
    {
        if (_data != null)
            return _data.get_id();
        else
            return -1L;
    }

    private Long getMoneyDreamId()
    {
        if (_moneyDreamId == null)
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            return globalVariable.get_moneyDreamId();
        }

        return _moneyDreamId;
    }

    private String getMonth()
    {
        int month = Integer.parseInt(_dateMonth);

        if (month < 10)
            return "0" + month;
        else
            return String.valueOf(month);
    }

    private String getYear()
    {
        return _dateYear;
    }

    private String getName()
    {
        return _editTextName.getText().toString();
    }

    private Integer getPrice() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextPrice.getText().toString());
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private Integer getAccumulation() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextAccumulation.getText().toString());
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private Integer getCredit() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextCredit.getText().toString());
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private String getImagePath()
    {
        Long id = get_Id();
        if (id == -1)
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                id = dbHelper.QueryMoneyTargetNextId();
            }
            catch (Exception e) { }

        Bitmap viewBitmap = BitmapCustom.createBitmapFromLayout(_subsamplingScaleImageView);

        File internalStorage = getContext().getDir(DBHelper.getDbName(getContext()) + "TargetPictures", Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, id + ".jpeg");
        String picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();
            return picturePath;
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
            return "";
        }
    }

    private String getImageUrl()
    {
        return "";
    }

    private EnumStatus getStatus()
    {
        return (EnumStatus) _spinnerStatus.getSelectedItem();
    }

    private Long get_headId()
    {
        return ((MoneyTarget) _spinnerParentTarget.getSelectedItem()).get_id();
    }

    private int get_categoryId()
    {
        return _data != null ? _data.get_categoryId() : 0;
    }

    private boolean get_existAccumulation()
    {
        return _checkBoxExistAccumulation.isChecked();
    }

    private Long get_accumulationId()
    {
        if (_data != null && _data.get_moneyAccumulation() != null)
            return _data.get_moneyAccumulation().get_id();
        return 0L;
    }

    private EnumAccumulationType getAccumulationType()
    {
        return (EnumAccumulationType) _spinnerAccumulationType.getSelectedItem();
    }

    private Integer get_progressionCoefficient() throws Exception
    {
        if (getAccumulationType().equals(EnumAccumulationType.Linear))
            return 0;
        else
        {
            try
            {
                String s = _editTextProgressionCoefficient.getText().toString();
                int pos = s.indexOf(".");
                int length = s.length();
                s = s.replace(".", "");
                int res = Integer.parseInt(s);
                if (pos < 0)
                    return 100 * res;
                else if (pos + 2 == length)
                    return 10 * res;
                else
                    return res;
            }
            catch (Exception e) { throw new Exception(e); }
        }
    }

    private Integer getMonthStart() { return _monthStart; }
    private Integer getYearStart() { return _yearStart; }
    private Integer getMonthEnd() { return _monthEnd; }
    private Integer getYeaEnd() { return _yearEnd; }
    private Integer getStartSum() throws Exception
    {
        try
        {
            String s = _editTextAccumulationStartSum.getText().toString();
            if (s.equals(""))
                s = "0";
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            int res = Integer.parseInt(s);
            if (pos < 0)
                return 100 * res;
            else if (pos + 2 == length)
                return 10 * res;
            else
                return res;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private ArrayList<MoneyPNL> getPnls()
    {
        ArrayList<MoneyPNL> arrayList = ((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).get_dataSet();
        ArrayList<MoneyPNL> list = new ArrayList<>();

        for (MoneyPNL item: arrayList)
        {
            if (item.get_id() >= _newIdBase)
                item.set_id(0L);
            list.add(item);
        }

        return list;
    }

    private ArrayList<MoneyPNL> get_deletedPnls()
    {
        ArrayList<MoneyPNL> arrayList = new ArrayList<>();
        if (_data != null && _data.get_moneyAccumulation() != null)
            arrayList = _data.get_moneyAccumulation().get_deletedPnls();
        ArrayList<MoneyPNL> list = new ArrayList<>();

        for (MoneyPNL item: arrayList)
        {
            if (item.get_id() >= _newIdBase)
                item.set_id(0L);
            list.add(item);
        }

        return list;

    }

    private MoneyAccumulation getOldMoneyAccumulation()
    {
        return _data == null ? null : _data.get_moneyAccumulation();
    }

    private MoneyAccumulation getMoneyAccumulation() throws Exception
    {
        if (get_existAccumulation())
            return new MoneyAccumulation(get_accumulationId(),
                    get_Id(),
                    getName(),
                    getAccumulationType(),
                    get_progressionCoefficient(),
                    getMonthStart(), getYearStart(),
                    getMonthEnd(), getYeaEnd(),
                    getStartSum(),
                    getPnls(), get_deletedPnls());
        else
            return null;
    }

    private List<PlanValue> get_planValues()
    {
        return ((PlanValueAdapter) recycleViewPlanValue.getAdapter()).get_dataSet();
    }

    private List<Long> get_deletedPlanValues()
    {
        return _data == null? null : _data.get_deletedPlanValues();
    }

    private Integer get_allTaskCount()
    {
        return _data == null ? 0 : _data.get_allTaskCount();
    }

    private Integer get_weekTaskCount()
    {
        return _data == null ? 0 : _data.get_weekTaskCount();
    }

    private MoneyCredit get_moneyCredit()
    {
        return _data == null ? null : _data.get_moneyCredit();
    }
    //endregion

    //region Save Params
    protected MoneyTarget GetDataFromForm()
    {
        recalcPnls();

        try
        {
            return new MoneyTarget(get_Id(), getMonth(), getYear(),
                    getName(), getPrice(), getAccumulation(), getCredit(),
                    getImagePath(), getImageUrl(), getMoneyDreamId(),
                    getStatus(), "", get_headId(),
                    get_categoryId(),
                    getMoneyAccumulation(),
                    getOldMoneyAccumulation(),
                    get_planValues(),
                    get_deletedPlanValues(),
                    get_allTaskCount(),
                    get_weekTaskCount(),
                    get_moneyCredit());
        }
        catch (Exception e)
        {
            return null;
        }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof PlanValue)
        {
            ((PlanValueAdapter) recycleViewPlanValue.getAdapter()).AddItem((PlanValue) object);
            return true;
        }
        else if (object instanceof MoneyPNL)
        {
            MoneyPNL moneyPNL = (MoneyPNL) object;
            ((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).AddItem(moneyPNL);
            recalcPnls();
            if (!((MoneyPNL) object).get_isPlan())
                _imageViewDelete.setVisibility(View.INVISIBLE);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof PlanValue)
        {
            ((PlanValueAdapter) recycleViewPlanValue.getAdapter()).UpdateItem((PlanValue) object);
            return true;
        }
        else if (object instanceof MoneyPNL)
        {
            MoneyPNL moneyPNL = (MoneyPNL) object;
            ((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).UpdateItem(moneyPNL);
            recalcPnls();
            _imageViewDelete.setVisibility(
                    !_typeAction.equals(EnumTypeAction.Insert) &&
                    !((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).ExistFactPNL() ?
                    View.VISIBLE : View.INVISIBLE);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof PlanValue)
        {
            PlanValue planValue = (PlanValue) object;
            if (_data != null && planValue.get_id() != 0)
                _data.add_deletedPlanValues(planValue.get_id());
            ((PlanValueAdapter) recycleViewPlanValue.getAdapter()).DeleteFromDataset(planValue);
            return true;
        }
        else if (object instanceof MoneyPNL)
        {
            MoneyPNL moneyPNL = (MoneyPNL) object;
            if (_data != null && moneyPNL.get_id() != 0 && moneyPNL.get_id() < _newIdBase)
                _data.get_moneyAccumulation().get_deletedPnls().add(moneyPNL);
            ((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).DeleteFromDataset(moneyPNL);
            recalcPnls();
            _imageViewDelete.setVisibility(
                    !_typeAction.equals(EnumTypeAction.Insert) &&
                    !((MoneyPNLAccumulationAdapter) recycleViewPlanAccumulation.getAdapter()).ExistFactPNL() ?
                            View.VISIBLE : View.INVISIBLE);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion
}