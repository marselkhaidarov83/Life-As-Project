package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import static com.MH83.LifeAsProject.interfac.Constants.REQUEST_GALLERY_FOR_MONEY_DREAM_POPUP;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.common.TaggedAdapter;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyInc4CostAdapter;
import com.MH83.LifeAsProject.adapter.rView.money.MoneySubCategoryPopupAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.ISetSelSubCategoryId;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumIndependent;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyInc4CostIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Custom.Tag.FragmentTaggedPopupNew;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamLegendToChangePopup;
import com.MH83.LifeAsProject.view.Money.MoneyInc4Cost.FragmentMoneyInc4CostPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentMoneyAccountingPopupNew extends CustomPopupBottomFragment<MoneyPNL>
        implements IFragmentViewCustom, ISetSelSubCategoryId
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_accounting_new;
    //endregion

    //region Fragment parameters
    private boolean _isPlan;
    private boolean _deletable = true;
    private boolean _editable;
    private boolean _editableSum;
    private boolean _editableCostCategory;
    private boolean _editableIncreaseCategory;
    private boolean _editIsPlan;
    private Long newId = -1L;
    private Long newIdTagged = -1L;
    //endregion

    //region Fragment settings
    private Long _increaseCategoryId;
    private Long _costCategoryId;
    private Long _oldIncreaseCategoryId;
    private Long _oldCostCategoryId;
    private Long _oldSubCategoryId;
    private Long _oldSumLong;
    private Calendar _date;
    private final FragmentMoneyAccountingPopupNew _this;
    private EnumTypeCategory _typeCategory;
    private EnumTypeCategory _typeCategoryChanged;
    private IMASCategory _moneyAccountingSumCost;
    private IMASCategory _moneyAccountingSumIncrease;
    private Calendar _startDate;
    private Calendar _endDate;
    private Hashtable<Long, Icon> _iconsIncrease;
    private Hashtable<Long, Icon> _iconsWallet;
    private Hashtable<Long, Icon> _iconsCost;
    //endregion

    //region Fragment Components
    private TextView _textViewDate;
    private TextView _textViewDateTitle;
    private TextView _textViewNameIncreaseCategory;
    private TextView _textViewNameCostCategory;
    private TextView _textViewTypeIncreaseCategory;
    private TextView _textViewTypeCostCategory;
    private TextView _textViewSumIncreaseCategory;
    private TextView _textViewSumCostCategory;
    private EditText _editTextSum;
    private EditText _editTextPercentSum;
    private EditText _editTextTotalSum;
    private ImageView _imageViewDateMinus;
    private ImageView _imageViewDatePlus;
    private ImageView _imageViewCostCategory;
    private ImageView _imageViewIncreaseCategory;
    private RecyclerView _recycleViewCategory;
    private RelativeLayout _relativeLayoutDate;
    private Button _buttonPrev;
    private Button _buttonPrev2;
    private Button _buttonNext;
    private NestedScrollView _nestedScrollView1;
    private NestedScrollView _nestedScrollView2;
    private NestedScrollView _nestedScrollView3;
    private TextView _textViewFreeAmount;
    private RecyclerView recycleViewInc4Cost;
    private RecyclerView recycleViewTags;
    private ImageView _imageViewAddToRView;
    private ImageView _imageViewAddToRView3;
    private LinearLayout _linearLayoutNSV1;
    private TableLayout _tableLayout1;
    private ConstraintLayout _constraintLayoutNSV2;
    private ConstraintLayout _constraintLayoutNSV3;
    private LinearLayout _linearLayoutNSV2;
    private LinearLayout _linearLayoutNSV3;
    private LinearLayout _linearLayoutPercent;
    private ImageView _imageViewIsPlan;
    //endregion

    //region Constructors
    public FragmentMoneyAccountingPopupNew(int addTitle, int updateTitle,
                                           int error)
    {
        super(_layoutId, addTitle, updateTitle, error);
        _this = this;
    }

    public FragmentMoneyAccountingPopupNew(AppCompatActivity activity,
                                           MoneyPNL data,
                                           EnumTypeAction typeAction,
                                           IFragmentViewCustom<MoneyPNL> iFragmentViewCustom,
                                           int addTitle, int updateTitle,
                                           int error,
                                           IMASCategory moneyAccountingSumCost,
                                           IMASCategory moneyAccountingSumIncrease,
                                           Calendar startDate, Calendar endDate,
                                           boolean editable,
                                           boolean editableSum,
                                           boolean editableCostCategory,
                                           boolean editableIncreaseCategory,
                                           boolean editIsPlan)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                addTitle, updateTitle, error,
                iFragmentViewCustom,
                null,
                null);

        _this = this;
        _moneyAccountingSumCost = moneyAccountingSumCost;
        _moneyAccountingSumIncrease = moneyAccountingSumIncrease;
        _startDate = startDate;
        _endDate = endDate;
        _editable = editable;
        _editableSum = editableSum;
        _editableCostCategory = editableCostCategory;
        _editableIncreaseCategory = editableIncreaseCategory;
        _editIsPlan = editIsPlan;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        if (data != null && globalVariable.get_hasUnpaidSubscription())
        {
            Calendar calendarThisMonthBegin = Calendar.getInstance();
            calendarThisMonthBegin.set(Calendar.DATE, 1);
            calendarThisMonthBegin.set(Calendar.HOUR, 0);
            calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisMonthBegin.set(Calendar.MINUTE, 0);
            calendarThisMonthBegin.set(Calendar.SECOND, 0);
            calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);

            Calendar calendarThisMonthEnd = Calendar.getInstance();
            calendarThisMonthBegin.set(Calendar.DATE, 1);
            calendarThisMonthBegin.set(Calendar.HOUR, 0);
            calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisMonthBegin.set(Calendar.MINUTE, 0);
            calendarThisMonthBegin.set(Calendar.SECOND, 0);
            calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
            calendarThisMonthEnd.add(Calendar.MONTH, 1);
            calendarThisMonthEnd.add(Calendar.SECOND, -1);

            if (!(data.getCalendar().getTime().getTime() >= calendarThisMonthBegin.getTime().getTime() &&
                    data.getCalendar().getTime().getTime() <= calendarThisMonthEnd.getTime().getTime()))
            {
                _editable = false;
                _editableSum = false;
                _editableCostCategory = false;
                _editableIncreaseCategory = false;
                _editIsPlan = false;
                _deletable = false;
            }
        }

        globalVariable.set_moneyAccountingSumCost(moneyAccountingSumCost);
        globalVariable.set_moneyAccountingSumIncrease(moneyAccountingSumIncrease);
        globalVariable.set_MAccPstartDate(startDate);
        globalVariable.set_MAccPendDate(endDate);
        globalVariable.set_MAccmoneyPNL(data);
        globalVariable.set_fragmentPopupEditable(_editable);
        globalVariable.set_fragmentPopupEditIsPlan(_editIsPlan);

        globalVariable.set_MAccOldCostCategoryId(null);
        globalVariable.set_MAccOldIncreaseCategoryId(null);
//        globalVariable.set_MAccOldSum(null);
        globalVariable.set_MAccOldSumLong(null);
        globalVariable.set_selSubCategoryId(null);
        globalVariable.set_selSubCategoryIcon(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _moneyAccountingSumCost = globalVariable.get_moneyAccountingSumCost();
        _moneyAccountingSumIncrease = globalVariable.get_moneyAccountingSumIncrease();
        _startDate = globalVariable.get_MAccPstartDate();
        _endDate = globalVariable.get_MAccPendDate();
        _data = globalVariable.get_MAccPmoneyPNL();
        _increaseCategoryId = globalVariable.get_MAccIncreaseCategoryId();
        _costCategoryId = globalVariable.get_MAccCostCategoryId();
        _oldIncreaseCategoryId = globalVariable.get_MAccOldIncreaseCategoryId();
        _oldCostCategoryId = globalVariable.get_MAccOldCostCategoryId();
        _oldSubCategoryId = globalVariable.get_MAccOldSubCategoryId();
        _oldSumLong = globalVariable.get_MAccOldSumLong();
        _date = globalVariable.get_MAccDate();
        _typeCategory = globalVariable.get_MAccTypeCategory();
        _typeCategoryChanged = globalVariable.get_MAccTypeCategoryChanged();
        _editable = globalVariable.get_fragmentPopupEditable();
        _editIsPlan = globalVariable.get_fragmentPopupEditIsPlan();

        _iconsWallet = IconDataSource.GetHashtable(EnumTypeCategory.Wallet);
        if (_typeCategory != null)
        {
            if (_typeCategory.equals(EnumTypeCategory.Increase))
                _iconsIncrease = IconDataSource.GetHashtable(_typeCategory);
            else
                _iconsCost = IconDataSource.GetHashtable(_typeCategory);
        }
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _textViewDate = view.findViewById(R.id.textViewDate);
        _textViewDateTitle = view.findViewById(R.id.textViewDateTitle);
        _editTextSum = view.findViewById(R.id.editTextSum);
        _editTextPercentSum = view.findViewById(R.id.editTextPercentSum);
        _editTextTotalSum = view.findViewById(R.id.editTextTotalSum);
        _textViewTypeCostCategory = view.findViewById(R.id.textViewTypeCostCategory);
        _textViewTypeIncreaseCategory = view.findViewById(R.id.textViewTypeIncreaseCategory);
        _textViewNameCostCategory = view.findViewById(R.id.textViewNameCostCategory);
        _textViewNameIncreaseCategory = view.findViewById(R.id.textViewNameIncreaseCategory);
        _textViewSumCostCategory = view.findViewById(R.id.textViewSumCostCategory);
        _textViewSumIncreaseCategory = view.findViewById(R.id.textViewSumIncreaseCategory);
        _imageViewDateMinus = view.findViewById(R.id.imageViewDateMinus);
        _imageViewDatePlus = view.findViewById(R.id.imageViewDatePlus);
        _imageViewCostCategory = view.findViewById(R.id.imageViewCostCategory);
        _imageViewIncreaseCategory = view.findViewById(R.id.imageViewIncreaseCategory);
        _recycleViewCategory = view.findViewById(R.id.recycleViewCategory);
        _relativeLayoutDate = view.findViewById(R.id.relativeLayoutDate);
        _buttonNext = view.findViewById(R.id.buttonNext);
        _buttonPrev = view.findViewById(R.id.buttonPrev);
        _buttonPrev2 = view.findViewById(R.id.buttonPrev2);
        _nestedScrollView1 = view.findViewById(R.id.nestedScrollView1);
        _nestedScrollView2 = view.findViewById(R.id.nestedScrollView2);
        _nestedScrollView3 = view.findViewById(R.id.nestedScrollView3);
        _textViewFreeAmount = view.findViewById(R.id.textViewFreeAmount);
        recycleViewInc4Cost = view.findViewById(R.id.recycleViewInc4Cost);
        recycleViewTags = view.findViewById(R.id.recycleViewTags);
        _imageViewAddToRView = view.findViewById(R.id.imageViewAdd);
        _imageViewAddToRView3 = view.findViewById(R.id.imageViewAdd3);
        _linearLayoutNSV1 = view.findViewById(R.id.linearLayoutNSV1);
        _tableLayout1 = view.findViewById(R.id.tableLayout1);
        _constraintLayoutNSV2 = view.findViewById(R.id.constraintLayoutNSV2);
        _constraintLayoutNSV3 = view.findViewById(R.id.constraintLayoutNSV3);
        _linearLayoutNSV2 = view.findViewById(R.id.linearLayoutNSV2);
        _linearLayoutNSV3 = view.findViewById(R.id.linearLayoutNSV3);
        _linearLayoutPercent = view.findViewById(R.id.linearLayoutPercent);
        _imageViewIsPlan = view.findViewById(R.id.imageViewIsPlan);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextSum;
        _focusedViewToUpdate = _editTextSum;
        _showInputToAdd = showInput && _editable;
        _showInputToUpdate = showInput && _editable;

        setEnabled(_editable, _editableSum, _editableIncreaseCategory, _editableCostCategory, _editIsPlan);
    }

    private void setEnabled(boolean editable, boolean editableSum,
                            boolean editableIncreaseCategory,
                            boolean editableCostCategory, boolean editIsPlan)
    {
        if (!editIsPlan)
            _imageViewIsPlan.setEnabled(false);

        if (!editableIncreaseCategory)
            _imageViewIncreaseCategory.setEnabled(false);

        if (!editableCostCategory)
            _imageViewCostCategory.setEnabled(false);

        if (!editableSum)
            _editTextSum.setEnabled(false);
        else
            _editTextPercentSum.setEnabled(true);

        if (!editable)
        {
            _imageViewDateMinus.setEnabled(false);
            _imageViewDatePlus.setEnabled(false);
            recycleViewInc4Cost.setEnabled(false);
            recycleViewTags.setEnabled(false);
            _recycleViewCategory.setEnabled(false);
            _textViewDate.setEnabled(false);
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

        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_PNL_Width) /*our width*/,
                    height);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/,
                        height);
            }
            else
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_PNL_Width) /*our width*/,
                        height);
            }
        }
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
        if (gVar.get_hasUnpaidSubscription())
        {
            _buttonNext.getLayoutParams().width = 0;
//            _textViewButton.getLayoutParams().width = 0;
        }
    }

    @Override
    protected void SetWidthComponents()
    {
        super.SetWidthComponents();

        if (_typeCategory != EnumTypeCategory.Increase &&
                _typeCategory != EnumTypeCategory.Cost)
        {
            _buttonNext.getLayoutParams().width = 0;
//            _textViewButton.getLayoutParams().width = 0;
        }
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();

        int height2 = _editTextSum.getLayoutParams().height;
        height2 += _editTextPercentSum.getLayoutParams().height;
        height2 += _imageViewCostCategory.getLayoutParams().height;
        height2 += _recycleViewCategory.getLayoutParams().height;
        height2 += _relativeLayoutDate.getLayoutParams().height;
        height2 += _buttonSave.getLayoutParams().height;
        if (gVar.get_typeDevice() == EnumTypeDevice.Tablet)
            height2 -= 7 * getContext().getResources().getDimension(R.dimen.BasePadding);
        else
        {
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                height2 -= 10 * getContext().getResources().getDimension(R.dimen.BasePadding);
            else
                height2 -= 8 * getContext().getResources().getDimension(R.dimen.BasePadding);
        }

        if (recycleViewInc4Cost != null)
        {
            ViewGroup.LayoutParams lp = recycleViewInc4Cost.getLayoutParams();
            lp.height = height2;
            recycleViewInc4Cost.setLayoutParams(lp);
        }

        if (recycleViewTags != null)
        {
            ViewGroup.LayoutParams lp = recycleViewTags.getLayoutParams();
            lp.height = height2;
            recycleViewTags.setLayoutParams(lp);
        }

        if (_data == null || _data.get_creditId() == 0)
            _linearLayoutPercent.getLayoutParams().height = 0;
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        ArrayList<MoneyInc4Cost> list = new ArrayList<>();
        if (_data != null && _data.get_inc4Cost() != null)
            list = new ArrayList<>(_data.get_inc4Cost());
        MoneyInc4CostAdapter adapter =
                new MoneyInc4CostAdapter(getContext(),
                        _data == null ? null : list,
                        _typeAction,
                        _iFragmentViewCustom,
                        this);
        if (recycleViewInc4Cost != null)
            recycleViewInc4Cost.setAdapter(adapter);

        ArrayList<Tagged> list2 = new ArrayList<>();
        if (_data != null && _data.get_Tagged() != null)
            list2 = new ArrayList<>(_data.get_Tagged());
        TaggedAdapter adapter2 =
                new TaggedAdapter(getContext(),
                        _data == null ? null : list2,
                        this);
        recycleViewTags.setAdapter(adapter2);
    }
    //endregion

    //region Set Listeners
    @Override
    public void SetSelSubCategoryId(Long selCategoryId, String selCategoryName, Long selCategoryIcon)
    {
        if (_data != null && _data.get_creditId() == 0)
        {
            _data.set_subCategoryId(selCategoryId);
            _data.set_subCategoryName(selCategoryName);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_selSubCategoryId(selCategoryId);
            globalVariable.set_selSubCategoryIcon(selCategoryIcon);
            globalVariable.set_selSubCategoryName(selCategoryName);
        }
    }

    @Override
    public void ClearFocus()
    {
        _editTextSum.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextSum.getWindowToken(), 0);
    }

    @Override
    protected void SetListeners()
    {
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        super.SetListeners();

        _textViewCaption.setOnClickListener(view ->
        {
            if (recycleViewInc4Cost != null)
                recycleViewInc4Cost.smoothScrollToPosition(0);
            if (recycleViewTags != null)
                recycleViewTags.smoothScrollToPosition(0);
            ClearFocus();
        });

        if (_editTextSum != null)
            _editTextSum.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                    try
                    {
                        set_freeAmountLong(getSumLong(),
                                ((MoneyInc4CostAdapter) recycleViewInc4Cost.getAdapter()).get_dataSet());

                        set_TotalsumLong(getSumLong() + getPercentSumLong());
                    }
                    catch (Exception e) { }
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

        if (_editTextPercentSum != null)
            _editTextPercentSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    Log.d("qwe1", getSumLong().toString());
                    Log.d("qwe2", getPercentSumLong().toString());
                    set_TotalsumLong(getSumLong() + getPercentSumLong());
                }
                catch (Exception e)
                {
                    Log.d("qweE", e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        if (_textViewDate != null)
            _textViewDate.setOnClickListener(view1 ->
            {
                ClearFocus();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (datePicker, year, month, day) ->
                        {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day);

                            if (globalVariable.get_hasUnpaidSubscription())
                            {
                                Calendar calendarThisMonth = Calendar.getInstance();
                                calendarThisMonth.set(Calendar.DATE, 1);
                                calendarThisMonth.set(Calendar.HOUR, 0);
                                calendarThisMonth.set(Calendar.HOUR_OF_DAY, 0);
                                calendarThisMonth.set(Calendar.MINUTE, 0);
                                calendarThisMonth.set(Calendar.SECOND, 0);
                                calendarThisMonth.set(Calendar.MILLISECOND, 0);
                                if (calendar.getTime().getTime() < calendarThisMonth.getTime().getTime())
                                    calendar = calendarThisMonth;
                                else
                                {
                                    calendarThisMonth.add(Calendar.MONTH, 1);
                                    calendarThisMonth.add(Calendar.SECOND, -1);
                                    if (calendar.getTime().getTime() > calendarThisMonth.getTime().getTime())
                                        calendar = calendarThisMonth;
                                }
                            }

                            set_date(calendar);
                        },
                        _date.get(Calendar.YEAR),
                        _date.get(Calendar.MONTH),
                        _date.get(Calendar.DATE));
                datePickerDialog.show();
            });

        if (!(_data != null && _data.get_creditId() != 0 && _data.get_percentSumLong() > 0L))
            if (_imageViewDateMinus != null)
                _imageViewDateMinus.setOnClickListener(view ->
                {
                    if (globalVariable.get_hasUnpaidSubscription() &&
                        _date.get(Calendar.DATE) == 1)
                            return;

                    ClearFocus();
                    if (_data != null && _data.get_creditId() != 0 && _data.get_percentSumLong() == 0L)
                        _date.add(Calendar.MONTH, -1);
                    else
                        _date.add(Calendar.DATE, -1);
                    set_date(_date);
                });

        if (!(_data != null && _data.get_creditId() != 0 && _data.get_percentSumLong() > 0L))
            if (_imageViewDatePlus != null)
                _imageViewDatePlus.setOnClickListener(view ->
                {
                    if (globalVariable.get_hasUnpaidSubscription())
                    {
                        _date.add(Calendar.DATE, 1);
                        int day = _date.get(Calendar.DATE);
                        _date.add(Calendar.DATE, -1);
                        if (day == 1)
                            return;
                    }

                    ClearFocus();
                    if (_data != null && _data.get_creditId() != 0 && _data.get_percentSumLong() == 0L)
                        _date.add(Calendar.MONTH, 1);
                    else
                        _date.add(Calendar.DATE, 1);
                    set_date(_date);
                });

        if (_imageViewCostCategory != null)
            _imageViewCostCategory.setOnClickListener(view ->
            {
                ClearFocus();
                _typeCategoryChanged = EnumTypeCategory.Cost;
                globalVariable.set_MAccTypeCategoryChanged(_typeCategoryChanged);

                FragmentMoneyCategoryPopup4PNLNew fragmentPopupNew =
                        new FragmentMoneyCategoryPopup4PNLNew((AppCompatActivity) getContext(),
                                null,
                                _typeAction,
                                null,
                                EnumTypeAction.Select,
                                _iFragmentViewCustom,
                                null,
                                _this,
                                _typeCategory == EnumTypeCategory.Increase?
                                        EnumTypeCategory.Increase: EnumTypeCategory.Wallet,
                                        getCostCategoryId());
                fragmentPopupNew.PopupShow();
            });

        if (_imageViewIncreaseCategory != null)
            _imageViewIncreaseCategory.setOnClickListener(view ->
            {
                ClearFocus();
                _typeCategoryChanged = EnumTypeCategory.Increase;
                globalVariable.set_MAccTypeCategoryChanged(_typeCategoryChanged);

                FragmentMoneyCategoryPopup4PNLNew fragmentPopupNew =
                        new FragmentMoneyCategoryPopup4PNLNew((AppCompatActivity) getContext(),
                                null,
                                _typeAction,
                                null,
                                EnumTypeAction.Select,
                                _iFragmentViewCustom,
                                null,
                                _this,
                                _typeCategory == null || _typeCategory == EnumTypeCategory.Cost?
                                        EnumTypeCategory.Cost: EnumTypeCategory.Wallet,
                                getIncreaseCategoryId());
                fragmentPopupNew.PopupShow();
            });

        if (_buttonNext != null)
            _buttonNext.setOnClickListener(view ->
            {
                InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_editTextSum.getWindowToken(), 0);

                _nestedScrollView1.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
                lp.weight = 0;
                lp.height = 0;
                _nestedScrollView1.setLayoutParams(lp);

                FrameLayout.LayoutParams fp = (FrameLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                fp.height = 0;
                _linearLayoutNSV1.setLayoutParams(fp);

                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = 0;
                _tableLayout1.setLayoutParams(lp);

                if (_typeCategory.equals(EnumTypeCategory.Increase))
                {
                    if (recycleViewTags != null)
                        recycleViewTags.getLayoutParams().height = 0;

                    _nestedScrollView2.setVisibility(View.VISIBLE);
                    lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
                    lp.weight = 1;
                    lp.height = height;
                    _nestedScrollView2.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                    lp.height = height;
                    _constraintLayoutNSV2.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                    lp.height = height;
                    _linearLayoutNSV2.setLayoutParams(lp);

                    _nestedScrollView3.setVisibility(View.INVISIBLE);
                    lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
                    lp.weight = 0;
                    lp.height = 0;
                    _nestedScrollView3.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                    lp.height = 0;
                    _constraintLayoutNSV3.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                    lp.height = 0;
                    _linearLayoutNSV3.setLayoutParams(lp);
                }
                else if (_typeCategory.equals(EnumTypeCategory.Cost))
                {
                    if (recycleViewInc4Cost != null)
                        recycleViewInc4Cost.getLayoutParams().height = 0;

                    _nestedScrollView3.setVisibility(View.VISIBLE);
                    lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
                    lp.weight = 1;
                    lp.height = height;
                    _nestedScrollView3.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                    lp.height = height;
                    _constraintLayoutNSV3.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                    lp.height = height;
                    _linearLayoutNSV3.setLayoutParams(lp);

                    _nestedScrollView2.setVisibility(View.INVISIBLE);
                    lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
                    lp.weight = 0;
                    lp.height = 0;
                    _nestedScrollView2.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                    lp.height = 0;
                    _constraintLayoutNSV2.setLayoutParams(lp);

                    lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                    lp.height = 0;
                    _linearLayoutNSV2.setLayoutParams(lp);
                }
            });

        if (_buttonPrev != null)
            _buttonPrev.setOnClickListener(view ->
            {
                InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_editTextSum.getWindowToken(), 0);

                _nestedScrollView1.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
                lp.weight = 1;
                lp.height = height;
                _nestedScrollView1.setLayoutParams(lp);

                FrameLayout.LayoutParams fp = (FrameLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                fp.height = height;
                _linearLayoutNSV1.setLayoutParams(fp);

                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = height;
                _tableLayout1.setLayoutParams(lp);

                _nestedScrollView2.setVisibility(View.INVISIBLE);
                lp = (LinearLayout.LayoutParams) _nestedScrollView2.getLayoutParams();
                lp.weight = 0;
                lp.height = 0;
                _nestedScrollView2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV2.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV2.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV2.setLayoutParams(lp);
            });

        if (_buttonPrev2 != null)
            _buttonPrev2.setOnClickListener(view ->
            {
                InputMethodManager imm = (InputMethodManager) _activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_editTextSum.getWindowToken(), 0);

                _nestedScrollView1.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _nestedScrollView1.getLayoutParams();
                lp.weight = 1;
                lp.height = height;
                _nestedScrollView1.setLayoutParams(lp);

                FrameLayout.LayoutParams fp = (FrameLayout.LayoutParams) _linearLayoutNSV1.getLayoutParams();
                fp.height = height;
                _linearLayoutNSV1.setLayoutParams(fp);

                lp = (LinearLayout.LayoutParams) _tableLayout1.getLayoutParams();
                lp.height = height;
                _tableLayout1.setLayoutParams(lp);

                _nestedScrollView3.setVisibility(View.INVISIBLE);
                lp = (LinearLayout.LayoutParams) _nestedScrollView3.getLayoutParams();
                lp.weight = 0;
                lp.height = 0;
                _nestedScrollView3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _constraintLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _constraintLayoutNSV3.setLayoutParams(lp);

                lp = (LinearLayout.LayoutParams) _linearLayoutNSV3.getLayoutParams();
                lp.height = 0;
                _linearLayoutNSV3.setLayoutParams(lp);
            });

        if (_imageViewAddToRView != null)
            _imageViewAddToRView.setOnClickListener(view ->
            {
                if (!MoneyInc4CostIndependent.canAdd(getContext(), globalVariable))
                {
                    SubscriptionClick();
                    return;
                }

                FragmentMoneyInc4CostPopupNew _fragmentPopup =
                        new FragmentMoneyInc4CostPopupNew(_activity,
                                null,
                                _typeAction,
                                EnumTypeAction.Insert,
                                _iFragmentViewCustom,
                                _this,
                                null,
                                null,
                                _data != null ? _data.getCalendar() : getDate(),
                                _data != null ? _data.getCalendar() : getDate(),
                                EnumIndependent.Dependent);

                _fragmentPopup.PopupShow();
            });

        if (_imageViewAddToRView3 != null)
            _imageViewAddToRView3.setOnClickListener(view ->
            {
            if (!Tagged.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            FragmentTaggedPopupNew _fragmentPopup =
                    new FragmentTaggedPopupNew(_activity,
                            null,
                            EnumTypeAction.Insert,
                            _this);

            _fragmentPopup.PopupShow();
        });

        if (_imageViewIsPlan != null)
            _imageViewIsPlan.setOnClickListener(view -> set_isPlan(!_isPlan));

        if (_textViewSumCostCategory != null)
            _textViewSumCostCategory.setOnClickListener(view ->
            {
                if (_typeCategory == EnumTypeCategory.Wallet || _typeCategory == EnumTypeCategory.Cost)
                    if (_moneyAccountingSumCost.get_sumLong() > 0L)
                        set_sumLong(_moneyAccountingSumCost.get_sumLong());
            });

        if (_textViewCaption != null)
            _textViewCaption.setOnClickListener(view ->
            {
                if (recycleViewInc4Cost != null)
                    recycleViewInc4Cost.smoothScrollToPosition(0);

                if (recycleViewTags != null)
                    recycleViewTags.scrollToPosition(0);
            });

        _imageViewMascotToChange.setOnClickListener(view ->
                new FragmentMoneyAccountingLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
    }
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[]{_moneyAccountingSumCost, _moneyAccountingSumIncrease, _data,
                _startDate, _endDate} );
    }

    protected void SetValues(Object[] objects)
    {
        IMASCategory moneyAccountingSumCost = (IMASCategory) objects[0];
        IMASCategory moneyAccountingSumIncrease = (IMASCategory) objects[1];
        MoneyPNL moneyPNL = (MoneyPNL) objects[2];
        Calendar dateStart = (Calendar) objects[3];
        Calendar dateEnd = (Calendar) objects[4];

        if (moneyPNL != null)
        {
            set_typeCategory(moneyPNL.get_typeCategory());
            set_costAccSum(moneyAccountingSumCost, moneyPNL.getSubCategoryId(),
                    moneyPNL.getSubCategoryName(), moneyPNL.get_subCategoryIcon());
            set_oldCostAccSum(moneyAccountingSumCost, moneyPNL.getOldSubCategoryId());
            set_increaseAccSum(moneyAccountingSumIncrease, moneyPNL.getSubCategoryId(),
                    moneyPNL.getSubCategoryName(), moneyPNL.get_subCategoryIcon());
            set_oldIncreaseAccSum(moneyAccountingSumIncrease, moneyPNL.getOldSubCategoryId());
            set_date(moneyPNL.getCalendar());
            set_sumLong(moneyPNL.get_sumLong());
            set_PercentsumLong(moneyPNL.get_percentSumLong());
            set_TotalsumLong(moneyPNL.get_totalSumLong());
            set_freeAmountLong(moneyPNL.get_sumLong(), moneyPNL.get_inc4Cost());
            set_isPlan(moneyPNL.get_isPlan());
        }
        else
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (moneyAccountingSumCost != null)
            {
                if (moneyAccountingSumCost.getTypeCategory() == EnumTypeCategory.Increase)
                    set_typeCategory(EnumTypeCategory.Increase);
            }

            if (moneyAccountingSumIncrease != null)
            {
                if (moneyAccountingSumIncrease.getTypeCategory() == EnumTypeCategory.Cost)
                    set_typeCategory(EnumTypeCategory.Cost);
            }

            if (moneyAccountingSumIncrease != null && moneyAccountingSumCost != null)
            {
                if (moneyAccountingSumCost.getTypeCategory() == EnumTypeCategory.Wallet
                        && moneyAccountingSumIncrease.getTypeCategory() == EnumTypeCategory.Wallet)
                    set_typeCategory(EnumTypeCategory.Wallet);
            }

            if (moneyAccountingSumCost != null)
            {
                set_costAccSum(moneyAccountingSumCost,
                        globalVariable.get_selSubCategoryId(),
                        globalVariable.get_selSubCategoryName(),
                        globalVariable.get_selSubCategoryIcon());
                set_oldCostAccSum(moneyAccountingSumCost, 0L);
            }
            else
                set_costAccSum(null, null, null, null);


            if (moneyAccountingSumIncrease != null)
            {
                set_increaseAccSum(moneyAccountingSumIncrease,
                        globalVariable.get_selSubCategoryId(),
                        globalVariable.get_selSubCategoryName(),
                        globalVariable.get_selSubCategoryIcon());
                set_oldIncreaseAccSum(moneyAccountingSumIncrease, 0L);
            }
            else
                set_increaseAccSum(null, null, null, null);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            if (c.getTime().getTime() >= dateStart.getTime().getTime() &&
                    c.getTime().getTime() <= dateEnd.getTime().getTime())
                set_date(c);
            else if (c.getTime().getTime() > dateEnd.getTime().getTime())
                set_date(dateEnd);
            else
                set_date(dateStart);

            set_sumLong(0L);
            set_PercentsumLong(0L);
            set_freeAmountLong(0L, null);
            set_isPlan(false);
        }
    }

    private void set_freeAmountLong(Long sumLong, List<MoneyInc4Cost> list)
    {
        Long sum = sumLong;

        if (list != null)
            for (MoneyInc4Cost moneyInc4Cost: list)
                sum -= moneyInc4Cost.get_sumLong();

        if (_textViewFreeAmount != null)
            _textViewFreeAmount.setText(String.valueOf(sum / 100d));
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

//        _textViewDateTitle.setText(getContext().getResources().getString(R.string.PNLDateTypeData));
        switch (_date.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Monday));
                break;
            case Calendar.TUESDAY:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Tuesday));
                break;
            case Calendar.WEDNESDAY:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Wednesday));
                break;
            case Calendar.THURSDAY:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Thursday));
                break;
            case Calendar.FRIDAY:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Friday));
                break;
            case Calendar.SATURDAY:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Saturday));
                break;
            default:
                _textViewDateTitle.setText(getContext().getResources().getString(R.string.Sunday));
        }

    }

    private void set_sumLong(Long sumLong)
    {
        _oldSumLong = sumLong;
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_MAccOldSumLong() == null)
            globalVariable.set_MAccOldSumLong(_oldSumLong);
        else
            _oldSumLong = globalVariable.get_MAccOldSumLong();

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

//    private void set_Percentsum(Double sum)
//    {
//        if (sum == 0d)
//            _editTextPercentSum.setText("");
//        else if ((100 * sum.intValue()) == (100 * sum))
//        {
//            _editTextPercentSum.setText(String.valueOf(sum.intValue()));
//        }
//        else
//        {
//            _editTextPercentSum.setText(String.valueOf(sum));
//        }
//    }

    private void set_PercentsumLong(Long sumLong)
    {
        if (sumLong == 0L)
            _editTextPercentSum.setText("");
        else if (Math.floorMod(sumLong, 100L) == 0)
        {
            _editTextPercentSum.setText(String.valueOf(Math.floorDiv(sumLong, 100L)));
        }
        else
        {
            _editTextPercentSum.setText(String.valueOf(sumLong / 100d));
        }
    }

//    private void set_Totalsum(Double sum)
//    {
//        if (sum == 0d)
//            _editTextTotalSum.setText("");
//        else if ((100 * sum.intValue()) == (100 * sum))
//        {
//            _editTextTotalSum.setText(String.valueOf(sum.intValue()));
//        }
//        else
//        {
//            _editTextTotalSum.setText(String.valueOf(sum));
//        }
//    }

    private void set_TotalsumLong(Long sumLong)
    {
        if (sumLong == 0L)
            _editTextTotalSum.setText("");
        else if (Math.floorMod(sumLong, 100L) == 0)
        {
            _editTextTotalSum.setText(String.valueOf(Math.floorDiv(sumLong, 100L)));
        }
        else
        {
            _editTextTotalSum.setText(String.valueOf(sumLong / 100d));
        }
    }

    private void set_typeCategory(EnumTypeCategory typeCategory)
    {
        _typeCategory = typeCategory;
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_MAccTypeCategory(_typeCategory);

        if (_typeCategory.equals(EnumTypeCategory.Increase))
            _iconsIncrease = IconDataSource.GetHashtable(_typeCategory);
        else
            _iconsCost = IconDataSource.GetHashtable(_typeCategory);
    }
    private void set_oldCostAccSum(IMASCategory masCost,
                                   Long oldSubCategoryId)
    {
        if (masCost != null)
            _oldCostCategoryId = masCost.getCategoryId();
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_MAccOldCostCategoryId() == null)
            globalVariable.set_MAccOldCostCategoryId(_oldCostCategoryId);
        else
            _oldCostCategoryId = globalVariable.get_MAccOldCostCategoryId();

        if (_typeCategory == EnumTypeCategory.Increase)
        {
            _oldSubCategoryId = oldSubCategoryId;
            if (globalVariable.get_MAccOldSubCategoryId() == null)
                globalVariable.set_MAccOldSubCategoryId(_oldSubCategoryId);
            else
                _oldSubCategoryId = globalVariable.get_MAccOldSubCategoryId();
        }
    }
    private void set_oldIncreaseAccSum(IMASCategory masIncrease, Long oldSubCategoryId)
    {
        _oldIncreaseCategoryId = masIncrease.getCategoryId();
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_MAccOldIncreaseCategoryId() == null)
            globalVariable.set_MAccOldIncreaseCategoryId(_oldIncreaseCategoryId);
        else
            _oldIncreaseCategoryId = globalVariable.get_MAccOldIncreaseCategoryId();

        if (_typeCategory == EnumTypeCategory.Cost)
        {
            _oldSubCategoryId = oldSubCategoryId;
            if (globalVariable.get_MAccOldSubCategoryId() == null)
                globalVariable.set_MAccOldSubCategoryId(_oldSubCategoryId);
            else
                _oldSubCategoryId = globalVariable.get_MAccOldSubCategoryId();
        }
    }
    private void set_costAccSum(IMASCategory masCost, Long selMoneyCategory,
                                String selMoneyCategoryName, Long selMoneyCategoryIcon)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_moneyAccountingSumCost(masCost);
        _moneyAccountingSumCost = masCost;

        if (masCost == null)
        {
            _costCategoryId = 0L;
            _textViewNameCostCategory.setText("");
            _textViewTypeCostCategory.setText("");
            _textViewSumCostCategory.setText("");
        }
        else
        {
            _costCategoryId = masCost.getCategoryId();
            _textViewNameCostCategory.setText(masCost.getCategoryName());
            _textViewTypeCostCategory.setText(CustomStaticClass.getTypeCategory4View(
                    getContext(), masCost.getTypeCategory()));
            _textViewSumCostCategory.setText(masCost.getTypeCategory().equals(EnumTypeCategory.Wallet) ?
//                    CustomStaticClass.getRubleSum(masCost.getSum()) : "");
                    CustomStaticClass.getRubleSum(masCost.get_sumLong() / 100d) : "");
            if (masCost.get_icon() != 0)
            {
                if (_typeCategory.equals(EnumTypeCategory.Increase))
                    _imageViewCostCategory.setImageResource(_iconsIncrease.get(masCost.get_icon()).get_icon_id());
                else
                    _imageViewCostCategory.setImageResource(_iconsWallet.get(masCost.get_icon()).get_icon_id());
            }
            else
            {
                if (_typeCategory.equals(EnumTypeCategory.Increase))
                    _imageViewCostCategory.setImageResource(R.drawable.icons_circle_green_50);
                else
                    _imageViewCostCategory.setImageResource(R.drawable.icons_circle_grey_50);
            }

            if (_typeCategory == EnumTypeCategory.Increase)
            {
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        _typeCategory, true, false,
                        true, EnumCategoryType.All,null);
                ArrayList<MoneyCategory> dataSet =
                        MoneyCategoryDataSource.LoadDataChildren(
                                getContext(), masCost.getCategoryId(), moneyCategorySettings);
                MoneySubCategoryPopupAdapter adapter = new MoneySubCategoryPopupAdapter(
                        getContext(),
                        _typeCategory,
                        dataSet, this, selMoneyCategory,
                        selMoneyCategoryName,
                        selMoneyCategoryIcon,
                        _editable && (_data == null || _data.get_creditId() == 0));

                if (selMoneyCategory != null)
                    for (int i = 0; i < dataSet.size(); i++)
                        if (Objects.equals(dataSet.get(i).get_id(), selMoneyCategory))
                        {
                            _recycleViewCategory.smoothScrollToPosition(i);
                            break;
                        }

                _recycleViewCategory.setAdapter(adapter);
            }
        }

        globalVariable.set_MAccCostCategoryId(_costCategoryId);
    }
    private void set_increaseAccSum(IMASCategory masIncrease, Long selMoneyCategory,
                                    String selMoneyCategoryName, Long selMoneyCategoryIcon)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_moneyAccountingSumIncrease(masIncrease);

        if (masIncrease == null)
        {
            _increaseCategoryId = 0L;
            _textViewNameIncreaseCategory.setText("");
            _textViewTypeIncreaseCategory.setText("");
            _textViewSumIncreaseCategory.setText("");
        }
        else
        {
            _increaseCategoryId = masIncrease.getCategoryId();
            _textViewNameIncreaseCategory.setText(masIncrease.getCategoryName());
            _textViewTypeIncreaseCategory.setText(CustomStaticClass.getTypeCategory4View(
                    getContext(), masIncrease.getTypeCategory()));
            _textViewSumIncreaseCategory.setText(masIncrease.getTypeCategory().equals(EnumTypeCategory.Wallet) ?
//                    CustomStaticClass.getRubleSum(masIncrease.getSum()) : "");
                    CustomStaticClass.getRubleSum(masIncrease.get_sumLong() / 100d) : "");
            if (masIncrease.get_icon() != 0)
            {
                if (_typeCategory.equals(EnumTypeCategory.Cost))
                    _imageViewIncreaseCategory.setImageResource(_iconsCost.get(masIncrease.get_icon()).get_icon_id());
                else
                    _imageViewIncreaseCategory.setImageResource(_iconsWallet.get(masIncrease.get_icon()).get_icon_id());
            }
            else
            {
                if (_typeCategory.equals(EnumTypeCategory.Cost))
                    _imageViewIncreaseCategory.setImageResource(R.drawable.icons_circle_yellow_50);
                else
                    _imageViewIncreaseCategory.setImageResource(R.drawable.icons_circle_grey_50);
            }

            if (_typeCategory == EnumTypeCategory.Cost)
            {
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        _typeCategory, true, false,
                        true, EnumCategoryType.All,null);
                ArrayList<MoneyCategory> dataSet =
                        MoneyCategoryDataSource.LoadDataChildren(
                                getContext(), masIncrease.getCategoryId(), moneyCategorySettings);
                MoneySubCategoryPopupAdapter adapter = new MoneySubCategoryPopupAdapter(
                        getContext(),
                        _typeCategory,
                        dataSet, this, selMoneyCategory,
                        selMoneyCategoryName,
                        selMoneyCategoryIcon,
                        _editable && (_data == null || _data.get_creditId() == 0));

                if (selMoneyCategory != null)
                    for (int i = 0; i < dataSet.size(); i++)
                        if (Objects.equals(dataSet.get(i).get_id(), selMoneyCategory))
                        {
                            _recycleViewCategory.smoothScrollToPosition(i);
                            break;
                        }

                _recycleViewCategory.setAdapter(adapter);
            }
        }

        globalVariable.set_MAccIncreaseCategoryId(_increaseCategoryId);
    }

    private void set_isPlan(boolean isPlan)
    {
        _isPlan = isPlan;
        _imageViewIsPlan.setImageResource(_isPlan ? R.drawable.icons_is_plan_100 :
                R.drawable.icons_is_fact_100);
    }
    //endregion

    //region Check Params
    @Override
    protected boolean ShowDeleteButton()
    {
        if (!_deletable)
            return false;
        return _data != null && _data.canDelete(getContext()) && _typeAction == EnumTypeAction.Update;
    }

    protected boolean CheckFormValues()
    {
        if (_increaseCategoryId == 0)
        {
            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    _typeCategory.equals(EnumTypeCategory.Increase)?
                            getContext().getString(R.string.specifyIncWallet):
                            getContext().getString(R.string.specifyCostCategory),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_costCategoryId == 0)
        {
            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    _typeCategory.equals(EnumTypeCategory.Increase)?
                            getContext().getString(R.string.specifyIncCategory):
                            getContext().getString(R.string.specifyCostWallet),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_typeCategory == EnumTypeCategory.Increase ||
                _typeCategory == EnumTypeCategory.Cost)
        {
            if (getSubCategoryId() == null)
            {
                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifySubCategory),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        try
        {
//            Double sum = getSum();
            Long sumLong = getSumLong();
            if (sumLong == 0L)
            {
                _editTextSum.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextSum, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
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

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectSumm), Toast.LENGTH_SHORT).show();
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
        return _data != null ? _data.get_id() : 0;
    }

    private EnumTypeCategory getTypeCategory() { return _typeCategory; }
    private Long getIncreaseCategoryId() { return _increaseCategoryId; }
    private Long getCostCategoryId() { return _costCategoryId; }
    private String getIncreaseCategoryName() { return _textViewNameIncreaseCategory.getText().toString(); }
    private String getCostCategoryName() { return _textViewNameCostCategory.getText().toString(); }
    private Long getOldSumLong() { return _data != null ? _data.get_oldSumLong() : _oldSumLong; }
    private Long getOldIncreaseCategoryId() { return _oldIncreaseCategoryId; }
    private Long getOldCostCategoryId() { return _oldCostCategoryId; }
    private Long getSubCategoryId()
    {
        if (_recycleViewCategory.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) _recycleViewCategory.getAdapter()).get_selMoneyCategoryId();

        return 0L;
    }
    private Long getOldSubCategoryId()
    {
        if (_data == null)
            return _oldSubCategoryId;
        else
            return _data.getOldSubCategoryId();
    }
    private String getSubCategoryName()
    {
        if (_recycleViewCategory.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) _recycleViewCategory.getAdapter()).get_selMoneyCategoryName();

        return "";
    }

    private Long getSumLong() throws Exception
    {
        try
        {
            String s = _editTextSum.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            long res = Long.parseLong(s);
            if (pos < 0)
                return 100 * res;
            else if (pos + 2 == length)
                return 10 * res;
            else
                return res;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Long getPercentSumLong() throws Exception
    {
        try
        {
            String s = _editTextPercentSum.getText().toString();
            if (s.equals(""))
                s = "0";
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            long res = Long.parseLong(s);
            if (pos < 0)
                return 100 * res;
            else if (pos + 2 == length)
                return 10 * res;
            else
                return res;
        }
        catch (Exception e) { throw new Exception(e); }
    }

//    private Double getOldPercentSum()
//    {
//        return _data != null ? _data.get_oldPercentSum() :  0d;
//    }

    private Long getOldPercentSumLong()
    {
        return _data != null ? _data.get_oldPercentSumLong() :  0L;
    }

    @NonNull
    private Calendar getDate()
    {
        _date.set(Calendar.HOUR, 0);
        _date.set(Calendar.HOUR_OF_DAY, 0);
        _date.set(Calendar.MINUTE, 0);
        _date.set(Calendar.SECOND, 0);
        _date.set(Calendar.MILLISECOND, 0);

        return _date;
    }

    private List<MoneyInc4Cost> get_inc4Cost()
    {
        Calendar calendar = getDate();
        ArrayList<MoneyInc4Cost> list = ((MoneyInc4CostAdapter) recycleViewInc4Cost.getAdapter()).get_dataSet();
        for (MoneyTransaction item: list)
            item.setCalendar(calendar);
        return list;
    }

    private List<MoneyInc4Cost> get_deletedInc4Cost()
    {
        return _data == null ? null : _data.get_deletedInc4Cost();
    }

    private List<Tagged> get_Tagged()
    {
        return ((TaggedAdapter) recycleViewTags.getAdapter()).get_dataSet();
    }

    private List<Tagged> get_deletedTagged()
    {
        return _data == null ? null : _data.get_deletedTagged();
    }

    private Long get_increaseCategoryIcon()
    {
        return _moneyAccountingSumIncrease.get_icon();
    }

    private Long get_costCategoryIcon()
    {
        return _moneyAccountingSumCost.get_icon();
    }

    private Long get_subCategoryIcon()
    {
        if (_recycleViewCategory.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) _recycleViewCategory.getAdapter()).get_selMoneyCategoryIcon();

        return 0L;
    }

    private boolean get_isPlan()
    {
        return _isPlan;
    }

    private boolean get_oldIsPlan()
    {
        return _data == null || _data.get_oldIsPlan();
    }

    private Long get_creditId()
    {
        return _data != null ? _data.get_creditId() : 0;
    }

    private Long get_accumulationId()
    {
        return _data != null ? _data.get_accumulationId() : 0;
    }

    private Boolean getCalculated()
    {
        return _data != null && _data.get_calculated();
    }

    private int get_taskId()
    {
        return _data != null ? _data.get_taskId() : 0;
    }
    //endregion

    //region Save Params
    protected MoneyPNL GetDataFromForm()
    {
        try
        {
            if (_data == null || _data instanceof MoneyPNLIndependent)
                return new MoneyPNLIndependent(get_Id(), getTypeCategory(), getIncreaseCategoryId(),
                        getOldIncreaseCategoryId(),
                        getCostCategoryId(),
                        getOldCostCategoryId(),
                        getSubCategoryId(),
                        getOldSubCategoryId(),
                        getIncreaseCategoryName(), getCostCategoryName(),
                        getSubCategoryName(), getDate(),
                        getSumLong(), getOldSumLong(),
                        get_taskId(), "",
                        get_increaseCategoryIcon(), get_costCategoryIcon(), get_subCategoryIcon(),
                        get_isPlan(), get_oldIsPlan(),
                        get_creditId(), get_accumulationId(),
                        getCalculated(),
                        getPercentSumLong(), getOldPercentSumLong(),
                        get_inc4Cost(), get_deletedInc4Cost(),
                        get_Tagged(), get_deletedTagged());
            else
                return new MoneyPNL(get_Id(), getTypeCategory(), getIncreaseCategoryId(),
                        getOldIncreaseCategoryId(), getCostCategoryId(), getOldCostCategoryId(),
                        getSubCategoryId(), getOldSubCategoryId(),
                        getIncreaseCategoryName(), getCostCategoryName(),
                        getSubCategoryName(), getDate(),
                        getSumLong(), getOldSumLong(),
                        get_taskId(), "",
                        get_increaseCategoryIcon(), get_costCategoryIcon(), get_subCategoryIcon(),
                        get_isPlan(), get_oldIsPlan(),
                        get_creditId(), get_accumulationId(),
                        getCalculated(),
                        getPercentSumLong(), getOldPercentSumLong(),
                        get_inc4Cost(), get_deletedInc4Cost(),
                        get_Tagged(), get_deletedTagged());
        }
        catch (Exception e)
        {
            Log.d("FragmentMoneyAccountingPopupNew", e.getMessage());
            return null;
        }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyInc4Cost)
        {
            MoneyInc4Cost moneyInc4Cost = (MoneyInc4Cost) object;
            moneyInc4Cost.set_id(newId--);
            ((MoneyInc4CostAdapter) recycleViewInc4Cost.getAdapter()).AddItem(moneyInc4Cost);

            try
            {
                set_freeAmountLong(getSumLong(), get_inc4Cost());
            }
            catch (Exception e) { }

            return true;
        }

        if (object instanceof Tagged)
        {
            Tagged tagged = (Tagged) object;
            tagged.set_id(newIdTagged--);
            ((TaggedAdapter) recycleViewTags.getAdapter()).AddItem(tagged);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyInc4Cost)
        {
            ((MoneyInc4CostAdapter) recycleViewInc4Cost.getAdapter()).UpdateItem((MoneyInc4Cost) object);

            try
            {
                set_freeAmountLong(getSumLong(), get_inc4Cost());
            }
            catch (Exception e) {}

            return true;
        }

        if (object instanceof Tagged)
        {
            ((TaggedAdapter) recycleViewTags.getAdapter()).UpdateItem((Tagged) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyInc4Cost)
        {
            MoneyInc4Cost moneyInc4Cost = (MoneyInc4Cost) object;

            if (_typeAction != EnumTypeAction.Insert && moneyInc4Cost.get_id() > 0)
                _data.add_deletedInc4Cost(moneyInc4Cost);
            ((MoneyInc4CostAdapter) recycleViewInc4Cost.getAdapter()).DeleteFromDataset((MoneyInc4Cost) object);

            try
            {
                set_freeAmountLong(getSumLong(), get_inc4Cost());
            }
            catch (Exception e) {}

            return true;
        }

        if (object instanceof Tagged)
        {
            Tagged tagged = (Tagged) object;

            if (_typeAction != EnumTypeAction.Insert && tagged.get_id() > 0)
                _data.add_deletedTagged(tagged);
            ((TaggedAdapter) recycleViewTags.getAdapter()).DeleteFromDataset((Tagged) object);

            try
            {
                set_freeAmountLong(getSumLong(), get_inc4Cost());
            }
            catch (Exception e) {}

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object)
    {
        if (object instanceof MoneyCategory)
        {
            MoneyCategory moneyCategory = (MoneyCategory) object;

            if (_typeCategoryChanged.equals(EnumTypeCategory.Increase))
                set_increaseAccSum(moneyCategory, null, null, null);
            else
                set_costAccSum(moneyCategory, null, null, null);

            return true;
        }

        return false;
    }
    //endregion

    //region Subscription
    private void SubscriptionClick()
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle();

        if (GlobalClass.SubscrRustore())
            SubscriptRustore();
    }

    private void SubscriptGoogle()
    {
        BillingClient.Builder builder = BillingClient.newBuilder(getContext());
        builder.setListener((billingResult, list) -> {});
        builder.enablePendingPurchases();
        BillingClient billingClient = builder.build();

        billingClient.startConnection(new BillingClientStateListener()
        {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult)
            {
                if (billingResult.getResponseCode() != OK)
                    return;

                List<String> ids = CustomSubscription.GetProductListGoogle();
                List<QueryProductDetailsParams.Product> products = ids.stream().map(productId ->
                        QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(productId)
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                ).collect(Collectors.toList());

                QueryProductDetailsParams.Builder builder = QueryProductDetailsParams.newBuilder();
                builder.setProductList(products);
                QueryProductDetailsParams params1 = builder.build();

                billingClient.queryProductDetailsAsync(params1, (billingResult1, list) ->
                {
                    if (billingResult1.getResponseCode() != OK)
                        return;

                    ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();

                    for (ProductDetails productDetail: list)
                    {
                        if (GlobalClass.TestPaid() ||
                                productDetail.getProductType().equals(BillingClient.ProductType.SUBS))
                            cBillingProducts.add(new CBillingProduct(productDetail.getProductId(),
                                    productDetail.getName(), "",
                                    productDetail.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice(),
                                    productDetail));
                    }

                    CustomTrack.PaidFormOpenFromPNL();

                    FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                            (AppCompatActivity) getContext(), null, EnumTypeAction.Insert, _this,
                            cBillingProducts);
                    popupNew.PopupShow();
                });
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: getting product details Google", "Serivce Disconnected");
            }
        });
    }

    private void SubscriptRustore()
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        ProductsUseCase productsUseCase = billingClient.getProducts();
        Task<List<Product>> tList = productsUseCase.getProducts(CustomSubscription.GetProductListRustore());

        tList.addOnSuccessListener(products ->
        {
            ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();
            for (Product product: products)
                if (product.getProductStatus() == ProductStatus.ACTIVE)
                    if (GlobalClass.TestPaid() || product.getProductType() == ProductType.SUBSCRIPTION)
                        cBillingProducts.add(new CBillingProduct(product.getProductId(),
                                product.getTitle(), product.getDescription(), product.getPriceLabel()));

            CustomTrack.PaidFormOpenFromPNL();

            FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                    (AppCompatActivity) getContext(), null, EnumTypeAction.Insert, this,
                    cBillingProducts);
            popupNew.PopupShow();
        });

        tList.addOnFailureListener(throwable ->
        {
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: getting products", throwable.getMessage());
        });
    }
    //endregion
}