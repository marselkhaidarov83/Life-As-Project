package com.MH83.LifeAsProject.view.Money.MoneyInc4Cost;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneySubCategoryPopupAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
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
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyInc4CostIndependent;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccountingLegendToChangePopup;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyCategoryPopup4PNLNew;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Objects;

public class FragmentMoneyInc4CostPopupNew extends CustomPopupBottomFragment<MoneyInc4Cost>
        implements IFragmentViewCustom, ISetSelSubCategoryId
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_inc4cost_new;
    //endregion

    //region Fragment parameters
    private EnumIndependent _independent;
    private boolean _enabledIncrease = true;
    private Long _sumLong = 0L;
    //endregion

    //region Fragment settings
    private Long _increaseCategoryId;
    private Long _increaseCategoryIcon;
    private Long _costCategoryId;
    private Long _costCategoryIcon;
    Long _oldCostCategoryId;
    private Long _oldIncreaseCategoryId;
    private Long _oldCostSubCategoryId;
    private Long _oldIncSubCategoryId;
    private Long _oldSumLong;
    private Calendar _date;
    private final FragmentMoneyInc4CostPopupNew _this;
    private EnumTypeCategory _typeCategory;
    private EnumTypeCategory _typeCategoryChanged;
    private IMASCategory _moneyAccountingSumCost;
    private IMASCategory _moneyAccountingSumIncrease;
    private Calendar _startDate;
    private Calendar _endDate;
    private Hashtable<Long, Icon> _icons;
    //endregion

    //region Fragment Components
    private TextView _textViewDate;
    private TextView _textViewDateTitle;
    private TextView _textViewNameIncreaseCategory;
    private TextView _textViewTypeIncreaseCategory;
    private TextView _textViewSumIncreaseCategory;
    private TextView _textViewNameCostCategory;
    private TextView _textViewTypeCostCategory;
    private TextView _textViewSumCostCategory;
    private EditText _editTextSum;
    private ImageView _imageViewDateMinus;
    private ImageView _imageViewDatePlus;
    private ImageView _imageViewCostCategory;
    private ImageView _imageViewIncreaseCategory;
    private RecyclerView rViewCost;
    private RecyclerView rViewIncrease;
    private LinearLayout linearLayoutCost;
    private ImageView imageViewMascotToChange2;
    //endregion

    //region Constructors
    public FragmentMoneyInc4CostPopupNew()
    {
        super(_layoutId,
                R.string.fragmentMoneyInc4CostPopupAddTitle,
                R.string.fragmentMoneyInc4CostPopupUpdateTitle,
                R.string.errorUpdateMoneyInc4Cost);
        _this = this;
    }

    public FragmentMoneyInc4CostPopupNew(AppCompatActivity activity,
                                         MoneyInc4Cost data,
                                         EnumTypeAction typeAction,
                                         EnumTypeAction typeAction2,
                                         IFragmentViewCustom iFragmentViewCustom,
                                         IFragmentViewCustom iFragmentViewCustom2,
                                         IMASCategory moneyAccountingSumCost,
                                         IMASCategory moneyAccountingSumIncrease,
                                         Calendar startDate, Calendar endDate,
                                         EnumIndependent independent)
    {
        super(activity, _layoutId, data,
                typeAction,
                typeAction2,
                null,
                R.string.fragmentMoneyInc4CostPopupAddTitle,
                R.string.fragmentMoneyInc4CostPopupUpdateTitle,
                R.string.errorUpdateMoneyInc4Cost,
                iFragmentViewCustom,
                iFragmentViewCustom2,
                null);

        _this = this;
        _moneyAccountingSumCost = moneyAccountingSumCost;
        _moneyAccountingSumIncrease = moneyAccountingSumIncrease;
        _startDate = startDate;
        _endDate = endDate;
        _independent = independent;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_moneyAccountingSumIncrease(moneyAccountingSumIncrease);
        globalVariable.set_MAccPstartDate(startDate);
        globalVariable.set_MAccPendDate(endDate);
        globalVariable.set_MAccMoneyInc4Cost(data);

        globalVariable.set_MAccOldIncreaseCategoryId(null);
        globalVariable.set_MAccOldSumLong(null);
        globalVariable.set_selSubCategoryId(null);
        globalVariable.set_selSubCategoryIcon(null);
    }

    public FragmentMoneyInc4CostPopupNew(AppCompatActivity activity,
                                         MoneyInc4Cost data,
                                         EnumTypeAction typeAction,
                                         EnumTypeAction typeAction2,
                                         IFragmentViewCustom iFragmentViewCustom,
                                         IFragmentViewCustom iFragmentViewCustom2,
                                         IMASCategory moneyAccountingSumCost,
                                         IMASCategory moneyAccountingSumIncrease,
                                         Long selSubCategoryId,
                                         Long selSubCategoryIcon,
                                         Calendar startDate, Calendar endDate,
                                         EnumIndependent independent,
                                         Long sumLong)
    {
        super(activity, _layoutId, data,
                typeAction,
                typeAction2,
                null,
                R.string.fragmentMoneyInc4CostPopupAddTitle,
                R.string.fragmentMoneyInc4CostPopupUpdateTitle,
                R.string.errorUpdateMoneyInc4Cost,
                iFragmentViewCustom,
                iFragmentViewCustom2,
                null);

        _this = this;
        _moneyAccountingSumCost = moneyAccountingSumCost;
        _moneyAccountingSumIncrease = moneyAccountingSumIncrease;
        _startDate = startDate;
        _endDate = endDate;
        _independent = independent;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_moneyAccountingSumIncrease(moneyAccountingSumIncrease);
        globalVariable.set_MAccPstartDate(startDate);
        globalVariable.set_MAccPendDate(endDate);
        globalVariable.set_MAccMoneyInc4Cost(data);

        globalVariable.set_MAccOldIncreaseCategoryId(null);
        globalVariable.set_MAccOldSumLong(null);
        globalVariable.set_selSubCategoryId(selSubCategoryId);
        globalVariable.set_selSubCategoryIcon(selSubCategoryIcon);

        _enabledIncrease = false;
        _sumLong = sumLong;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _moneyAccountingSumIncrease = globalVariable.get_moneyAccountingSumIncrease();
        _startDate = globalVariable.get_MAccPstartDate();
        _endDate = globalVariable.get_MAccPendDate();
        _data = globalVariable.get_MAccMoneyInc4Cost();
        _increaseCategoryId = globalVariable.get_MAccIncreaseCategoryId();
        _increaseCategoryIcon = globalVariable.get_MAccIncreaseCategoryIcon();
        _costCategoryId = globalVariable.get_MAccCostCategoryId();
        _costCategoryIcon = globalVariable.get_MAccCostCategoryIcon();
        _oldIncreaseCategoryId = globalVariable.get_MAccOldIncreaseCategoryId();
        _oldIncSubCategoryId = globalVariable.get_MAccOldIncSubCategoryId();
        _oldSumLong = globalVariable.get_MAccOldSumLong();
        _date = globalVariable.get_MAccDate();
        _typeCategory = globalVariable.get_MAccTypeCategory();
        _typeCategoryChanged = globalVariable.get_MAccTypeCategoryChanged();

        _icons = IconDataSource.GetHashtable(EnumTypeCategory.Cost);
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
        _textViewTypeIncreaseCategory = view.findViewById(R.id.textViewTypeIncreaseCategory);
        _textViewNameIncreaseCategory = view.findViewById(R.id.textViewNameIncreaseCategory);
        _textViewSumIncreaseCategory = view.findViewById(R.id.textViewSumIncreaseCategory);
        _textViewTypeCostCategory = view.findViewById(R.id.textViewTypeCostCategory);
        _textViewNameCostCategory = view.findViewById(R.id.textViewNameCostCategory);
        _textViewSumCostCategory = view.findViewById(R.id.textViewSumCostCategory);
        _imageViewDateMinus = view.findViewById(R.id.imageViewDateMinus);
        _imageViewDatePlus = view.findViewById(R.id.imageViewDatePlus);
        _imageViewCostCategory = view.findViewById(R.id.imageViewCostCategory);
        _imageViewIncreaseCategory = view.findViewById(R.id.imageViewIncreaseCategory);
        rViewCost = view.findViewById(R.id.recycleViewCategoryCost);
        rViewIncrease = view.findViewById(R.id.recycleViewCategoryIncrease);
        linearLayoutCost = view.findViewById(R.id.linearLayoutCost);
        imageViewMascotToChange2 = view.findViewById(R.id.imageViewMascotToChange2);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextSum;
        _focusedViewToUpdate = _editTextSum;
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
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_PNL_Width) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_PNL_Width) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

        if (_typeCategory == EnumTypeCategory.Increase)
        {
            linearLayoutCost.getLayoutParams().height = 0;
            rViewCost.getLayoutParams().height = 0;
        }
    }

    //endregion

    //region Set Listeners
    @Override
    public void SetSelSubCategoryId(Long selCategoryId, String selCategoryName, Long selCategoryIcon)
    {
        if (_data != null)
        {
            _data.set_inc_subCategoryId(selCategoryId);
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
        super.SetListeners();

        _editTextSum.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

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
        _textViewDate.setOnClickListener(view1 ->
        {
            ClearFocus();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (datePicker, year, month, day) ->
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        set_date(calendar);
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
        });
        _imageViewDatePlus.setOnClickListener(view ->
        {
            ClearFocus();
            _date.add(Calendar.DATE, 1);
            set_date(_date);
        });

        _imageViewCostCategory.setOnClickListener(view ->
        {
            ClearFocus();
            _typeCategoryChanged = EnumTypeCategory.Cost;
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_MAccTypeCategoryChanged(_typeCategoryChanged);

            FragmentMoneyCategoryPopup4PNLNew fragmentPopupNew =
                    new FragmentMoneyCategoryPopup4PNLNew((AppCompatActivity) getContext(),
                            null,
                            _typeAction,
                            _typeAction2,
                            EnumTypeAction.Select,
                            _iFragmentViewCustom,
                            _iFragmentViewCustom2,
                            _this,
                            EnumTypeCategory.Cost,
                            getCostCategoryId());
            fragmentPopupNew.PopupShow();
        });

        if (_enabledIncrease)
            _imageViewIncreaseCategory.setOnClickListener(view ->
            {
                ClearFocus();
                _typeCategoryChanged = EnumTypeCategory.Increase;
                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
                globalVariable.set_MAccTypeCategoryChanged(_typeCategoryChanged);

                FragmentMoneyCategoryPopup4PNLNew fragmentPopupNew =
                        new FragmentMoneyCategoryPopup4PNLNew((AppCompatActivity) getContext(),
                                null,
                                _typeAction,
                                _typeAction2,
                                EnumTypeAction.Select,
                                _iFragmentViewCustom,
                                _iFragmentViewCustom2,
                                _this,
                                EnumTypeCategory.Cost,
                                getIncreaseCategoryId());
                fragmentPopupNew.PopupShow();
            });

        imageViewMascotToChange2.setOnClickListener(view ->
                new FragmentMoneyInc4CostLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
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
        MoneyInc4Cost moneyInc4Cost = (MoneyInc4Cost) objects[2];
        Calendar dateStart = (Calendar) objects[3];
        Calendar dateEnd = (Calendar) objects[4];

        if (moneyInc4Cost != null)
        {
            set_typeCategory(moneyInc4Cost.get_typeCategory());
            set_costAccSum(moneyAccountingSumCost, moneyInc4Cost.get_cost_subCategory_id(),
                    moneyInc4Cost.get_costSubCategoryName(), moneyInc4Cost.get_costCategoryIcon());
            set_oldCostAccSum(moneyAccountingSumCost);
            set_increaseAccSum(moneyAccountingSumIncrease, moneyInc4Cost.get_inc_subCategory_id(),
                    moneyInc4Cost.get_subCategoryName(), moneyInc4Cost.get_increaseCategoryIcon());
            set_oldIncreaseAccSum(moneyAccountingSumIncrease);
            set_date(moneyInc4Cost.getCalendar());
            set_sumLong(moneyInc4Cost.get_oldSumLong(), moneyInc4Cost.get_sumLong());
        }
        else
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (moneyAccountingSumCost != null)
            {
                set_costAccSum(moneyAccountingSumCost,
                        globalVariable.get_selSubCategoryId(),
                        globalVariable.get_selSubCategoryName(),
                        globalVariable.get_selSubCategoryIcon());
                set_oldCostAccSum(moneyAccountingSumCost);
            }
            else
                set_costAccSum(null, null, null, null);

            if (moneyAccountingSumIncrease != null)
            {
                set_increaseAccSum(moneyAccountingSumIncrease,
                        globalVariable.get_selSubCategoryId(),
                        globalVariable.get_selSubCategoryName(),
                        globalVariable.get_selSubCategoryIcon());
                set_oldIncreaseAccSum(moneyAccountingSumIncrease);
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

            set_sumLong(_sumLong, _sumLong);
        }
    }
    private void set_typeCategory(EnumTypeCategory typeCategory)
    {
        _typeCategory = typeCategory;
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

    private void set_sumLong(Long oldSumLong, Long sumLong)
    {
        _oldSumLong = oldSumLong;
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

    private void set_oldCostAccSum(IMASCategory masCost)
    {
        _oldCostCategoryId = 0L;

        if (masCost != null)
            _oldCostCategoryId = masCost.getCategoryId();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_MAccOldCostCategoryId() == null)
            globalVariable.set_MAccOldCostCategoryId(_oldCostCategoryId);
        else
            _oldCostCategoryId = globalVariable.get_MAccOldCostCategoryId();

        if (_typeCategory == EnumTypeCategory.Increase)
        {
            if (globalVariable.get_MAccOldSubCategoryId() == null)
                globalVariable.set_MAccOldSubCategoryId(_oldCostSubCategoryId);
            else
                _oldCostSubCategoryId = globalVariable.get_MAccOldSubCategoryId();
        }
    }

    private void set_oldIncreaseAccSum(IMASCategory masIncrease)
    {
        _oldIncreaseCategoryId = masIncrease.getCategoryId();
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_MAccOldIncreaseCategoryId() == null)
            globalVariable.set_MAccOldIncreaseCategoryId(_oldIncreaseCategoryId);
        else
            _oldIncreaseCategoryId = globalVariable.get_MAccOldIncreaseCategoryId();

        _oldIncSubCategoryId = masIncrease.getCategoryId();

        if (globalVariable.get_MAccOldIncSubCategoryId() == null)
            globalVariable.set_MAccOldIncSubCategoryId(_oldIncSubCategoryId);
        else
            _oldIncSubCategoryId = globalVariable.get_MAccOldIncSubCategoryId();
    }

    private void set_costAccSum(IMASCategory masCost, Long selMoneyCategory,
                                String selMoneyCategoryName, Long selMoneyCategoryIcon)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_moneyAccountingSumCost(masCost);

        if (masCost == null)
        {
            _costCategoryId = 0L;
            _costCategoryIcon = 0L;
            _textViewNameCostCategory.setText("");
            _textViewTypeCostCategory.setText("");
            _textViewSumCostCategory.setText("");
        }
        else
        {
            _costCategoryId = masCost.getCategoryId();
            _costCategoryIcon = masCost.get_icon();
            _textViewNameCostCategory.setText(masCost.getCategoryName());
            if (!masCost.getTypeCategory().equals(EnumTypeCategory.Wallet))
                _imageViewCostCategory.setImageResource(_icons.get(masCost.get_icon()).get_icon_id());
            _textViewTypeCostCategory.setText(CustomStaticClass.getTypeCategory4View(
                    getContext(), masCost.getTypeCategory()));
            _textViewSumCostCategory.setText(masCost.getTypeCategory().equals(EnumTypeCategory.Wallet) ?
                    CustomStaticClass.getRubleSum(masCost.get_sumLong() / 100d) : "");

            {
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Cost, true, false,
                        true, EnumCategoryType.All,null);
                ArrayList<MoneyCategory> dataSet =
                        MoneyCategoryDataSource.LoadDataChildren(
                                getContext(), masCost.getCategoryId(), moneyCategorySettings);
                MoneySubCategoryPopupAdapter adapter = new MoneySubCategoryPopupAdapter(
                        getContext(),
                        EnumTypeCategory.Cost,
                        dataSet, this, selMoneyCategory,
                        selMoneyCategoryName,
                        selMoneyCategoryIcon,
                        true);

                if (selMoneyCategory != null)
                    for (int i = 0; i < dataSet.size(); i++)
                        if (Objects.equals(dataSet.get(i).get_id(), selMoneyCategory))
                        {
                            rViewCost.smoothScrollToPosition(i);
                            break;
                        }

                rViewCost.setAdapter(adapter);
            }
        }

        globalVariable.set_MAccCostCategoryId(_costCategoryId);
        globalVariable.set_selSubCategoryIcon(_costCategoryIcon);
    }

    private void set_increaseAccSum(IMASCategory masIncrease, Long selMoneyCategory,
                                    String selMoneyCategoryName, Long selMoneyCategoryIcon)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_moneyAccountingSumIncrease(masIncrease);

        if (masIncrease == null)
        {
            _increaseCategoryId = 0L;
            _increaseCategoryIcon = 0L;
            _textViewNameIncreaseCategory.setText("");
            _textViewTypeIncreaseCategory.setText("");
            _textViewSumIncreaseCategory.setText("");
        }
        else
        {
            _increaseCategoryId = masIncrease.getCategoryId();
            _increaseCategoryIcon = masIncrease.get_icon();
            _textViewNameIncreaseCategory.setText(masIncrease.getCategoryName());
            if (!masIncrease.getTypeCategory().equals(EnumTypeCategory.Wallet) &&
                    _icons.get(masIncrease.get_icon()) != null)
                _imageViewIncreaseCategory.setImageResource(_icons.get(masIncrease.get_icon()).get_icon_id());
            _textViewTypeIncreaseCategory.setText(CustomStaticClass.getTypeCategory4View(
                    getContext(), masIncrease.getTypeCategory()));
            _textViewSumIncreaseCategory.setText(masIncrease.getTypeCategory().equals(EnumTypeCategory.Wallet) ?
                    CustomStaticClass.getRubleSum(masIncrease.get_sumLong() / 100d) : "");

            {
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Cost, true, false,
                        true, EnumCategoryType.All,null);
                ArrayList<MoneyCategory> dataSet =
                        MoneyCategoryDataSource.LoadDataChildren(
                                getContext(), masIncrease.getCategoryId(),
                                moneyCategorySettings);
                MoneySubCategoryPopupAdapter adapter = new MoneySubCategoryPopupAdapter(
                        getContext(),
                        EnumTypeCategory.Cost,
                        dataSet, this, selMoneyCategory,
                        selMoneyCategoryName,
                        selMoneyCategoryIcon,
                        _enabledIncrease);

                if (selMoneyCategory != null)
                    for (int i = 0; i < dataSet.size(); i++)
                        if (Objects.equals(dataSet.get(i).get_id(), selMoneyCategory))
                        {
                            rViewIncrease.smoothScrollToPosition(i);
                            break;
                        }

                rViewIncrease.setAdapter(adapter);
            }
        }

        globalVariable.set_MAccIncreaseCategoryId(_increaseCategoryId);
        globalVariable.set_MAccIncreaseCategoryIcon(_increaseCategoryIcon);
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _typeAction2 == EnumTypeAction.Update;
    }

    protected boolean CheckFormValues()
    {
        if (_costCategoryId == 0 && _typeCategory.equals(EnumTypeCategory.Wallet))
        {
            Toast.makeText(_iFragmentViewCustom2.getActivity(),
                            getContext().getString(R.string.specifyCostCategory),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_increaseCategoryId == 0)
        {
            Toast.makeText(_iFragmentViewCustom2.getActivity(),
                    _typeCategory.equals(EnumTypeCategory.Increase)?
                            getContext().getString(R.string.specifyIncWallet):
                            getContext().getString(R.string.specifyIncCategory),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_typeCategory == EnumTypeCategory.Wallet)
        {
            if (getCostSubCategoryId() == null)
            {
                Toast.makeText(_iFragmentViewCustom2.getActivity(),
                        getContext().getString(R.string.specifyCostSubCategory),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        {
            if (getIncreaseSubCategoryId() == null)
            {
                Toast.makeText(_iFragmentViewCustom2.getActivity(),
                        getContext().getString(R.string.specifyIncreaseSubCategory),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        try
        {
            Double sum = getSum();
            if (sum == 0d)
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
        if (_data != null)
            return _data.get_id();

        return 0L;
    }

    private Long get_pnlId()
    {
        return _data != null ? _data.get_pnl_id() : 0;
    }
    private EnumTypeCategory get_typeCategory() { return _typeCategory; }
    private Long getCostCategoryId() { return _costCategoryId; }
    private Long get_costCategoryIcon() { return _costCategoryIcon; }
    private String getCostCategoryName() { return _textViewNameCostCategory.getText().toString(); }
    private Long getOldCostSubCategoryId()
    {
        return _data == null ? 0 : _data.get_old_cost_subCategory_id();
    }

    private Long getIncreaseCategoryId() { return _increaseCategoryId; }
    private Long get_increaseCategoryIcon() { return _increaseCategoryIcon; }
    private String getIncreaseCategoryName() { return _textViewNameIncreaseCategory.getText().toString(); }

    private Long getOldSumLong() { return _oldSumLong; }
    private Long getOldIncSubCategoryId()
    {
        return _data == null ? 0 : _data.get_old_inc_subCategory_id();
    }
    private Long getCostSubCategoryId()
    {
        if (rViewCost.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) rViewCost.getAdapter()).get_selMoneyCategoryId();

        return 0L;
    }
    private String getCostSubCategoryName()
    {
        if (rViewCost.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) rViewCost.getAdapter()).get_selMoneyCategoryName();

        return "";
    }
    private Long getCostSubCategoryIcon()
    {
        if (rViewCost.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) rViewCost.getAdapter()).get_selMoneyCategoryIcon();

        return 0L;
    }

    private Long getIncreaseSubCategoryId()
    {
        if (rViewIncrease.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) rViewIncrease.getAdapter()).get_selMoneyCategoryId();

        return 0L;
    }
    private String getIncraseSubCategoryName()
    {
        if (rViewIncrease.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) rViewIncrease.getAdapter()).get_selMoneyCategoryName();

        return "";
    }
    private Long getIncraseSubCategoryIcon()
    {
        if (rViewIncrease.getAdapter() != null)
            return ((MoneySubCategoryPopupAdapter) rViewIncrease.getAdapter()).get_selMoneyCategoryIcon();

        return 0L;
    }

    private Double getSum() throws Exception
    {
        try
        {
            return Double.parseDouble(_editTextSum.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
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
    //endregion

    //region Save Params
    protected MoneyInc4Cost GetDataFromForm()
    {
        try
        {
            if (_independent == EnumIndependent.Dependent)
                return new MoneyInc4Cost(get_Id(), get_pnlId(),
                        get_typeCategory(),
                        getCostCategoryId(),
                        getCostSubCategoryId(),
                        getOldCostSubCategoryId(),
                        getCostCategoryName(),
                        get_costCategoryIcon(),
                        getCostSubCategoryName(),
                        getCostSubCategoryIcon(),
                        getIncreaseCategoryId(),
                        getIncreaseSubCategoryId(),
                        getOldIncSubCategoryId(),
                        getIncreaseCategoryName(),
                        get_increaseCategoryIcon(),
                        getIncraseSubCategoryName(),
                        getIncraseSubCategoryIcon(),
                        getDate().get(Calendar.DATE), getDate().get(Calendar.MONTH), getDate().get(Calendar.YEAR),
                        getSumLong(),
                        getOldSumLong());
            else
                return new MoneyInc4CostIndependent(get_Id(), get_pnlId(),
                        get_typeCategory(),
                        getCostCategoryId(),
                        getCostSubCategoryId(),
                        getOldCostSubCategoryId(),
                        getCostCategoryName(),
                        get_costCategoryIcon(),
                        getCostSubCategoryName(),
                        getCostSubCategoryIcon(),
                        getIncreaseCategoryId(),
                        getIncreaseSubCategoryId(),
                        getOldIncSubCategoryId(),
                        getIncreaseCategoryName(),
                        get_increaseCategoryIcon(),
                        getIncraseSubCategoryName(),
                        getIncraseSubCategoryIcon(),
                        getDate().get(Calendar.DATE), getDate().get(Calendar.MONTH), getDate().get(Calendar.YEAR),
                        getSumLong(),
                        getOldSumLong());
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
    public boolean refreshViewOnAdd(Object object) { return false; }

    @Override
    public boolean refreshViewOnUpdate(Object object) { return false; }

    @Override
    public boolean refreshViewOnDelete(Object object) { return false; }

    @Override
    public boolean refreshViewOnSelect(Object object)
    {
        if (object instanceof MoneyCategory)
        {
            MoneyCategory moneyCategory = (MoneyCategory) object;

            if (_typeCategoryChanged.equals(EnumTypeCategory.Increase))
                set_increaseAccSum(moneyCategory, null, null, null);
            else if (_typeCategoryChanged.equals(EnumTypeCategory.Cost))
                set_costAccSum(moneyCategory, null, null, null);

            return true;
        }

        return false;
    }
    //endregion
}