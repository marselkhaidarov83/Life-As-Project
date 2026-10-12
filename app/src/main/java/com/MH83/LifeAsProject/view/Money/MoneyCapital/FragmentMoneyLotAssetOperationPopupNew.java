package com.MH83.LifeAsProject.view.Money.MoneyCapital;

import android.app.Activity;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerAssetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerTypeAssetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerTypeLotAssetOperationAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyCategoryAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyPortfolioAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAssetDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPortfolioDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperationIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolio;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolioSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAssetSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class FragmentMoneyLotAssetOperationPopupNew extends CustomPopupBottomFragment<MoneyLotAssetOperation>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_lot_asset_operation_new;
    //endregion

    //region Fragment parameters
    private Calendar _date;
    private boolean _independent;
    private CustomPeriodClass _customPeriodClass;
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    private Spinner _spinnerGroupPortfolio;
    private Spinner _spinnerPortfolio;
    private Spinner _spinnerTypeAsset;
    private Spinner _spinnerAsset;
    private Spinner _spinnerOperation;
    private Spinner _spinnerCategory;
    private Spinner _spinnerSubCategory;
    private TextView _textViewDate;
    private TextView _textViewDateTitle;
    private ImageView _imageViewDateMinus;
    private ImageView _imageViewDatePlus;
    private EditText _editTextCount;
    private EditText _editTextPrice;
    private EditText _editTextComission;
    private TextView _textViewSum;
    private EditText _editTextAccumulatedPrice;
    private TextView _textViewAccumulatedSum;
    private TextView _textViewAssetOperationTV;
    private TextView _textViewAssetOperation2;
    private TableRow _tableRowOperation;
    private TableRow _tableRowOperationTV;
    private TextView _textViewAssetOperation;
    private TableRow _tableRowCategory;
    private TableRow _tableRowSubCategory;
    private TextView _textViewCategory;
    private TextView _textViewSubCategory;
    private TableLayout _tableLayout;
    private TableRow _tableRowGroupPortfolio;
    private TableRow _tableRowPortfolio;
    private TextView _textViewGroupPortfolio;
    private TextView _textViewPortfolio;
    //endregion

    //region Constructors
    public FragmentMoneyLotAssetOperationPopupNew()
    {
        super(_layoutId,
                R.string.PopupLotAssetOperationAdd,
                R.string.PopupLotAssetOperationUpdate,
                R.string.errorUpdateMoneyLotAssetOperation);
    }

    public FragmentMoneyLotAssetOperationPopupNew(AppCompatActivity activity,
                                                  MoneyLotAssetOperation data,
                                                  EnumTypeAction typeAction,
                                                  EnumTypeAction typeAction2,
                                                  IFragmentViewCustom iFragmentViewCustom,
                                                  IFragmentViewCustom iFragmentViewCustom2,
                                                  boolean independent,
                                                  CustomPeriodClass customPeriodClass)
    {
        super(activity, _layoutId, data, typeAction,
                typeAction2,
                null,
                R.string.PopupLotAssetOperationAdd,
                R.string.PopupLotAssetOperationUpdate,
                R.string.errorUpdateMoneyLotAssetOperation,
                iFragmentViewCustom,
                iFragmentViewCustom2,
                null);

        _independent = independent;
        _customPeriodClass = customPeriodClass;
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerGroupPortfolio = view.findViewById(R.id.spinnerGroupPortfolio);
        _spinnerPortfolio = view.findViewById(R.id.spinnerPortfolio);
        _spinnerTypeAsset = view.findViewById(R.id.spinnerTypeAsset);
        _spinnerAsset = view.findViewById(R.id.spinnerAsset);
        _spinnerOperation = view.findViewById(R.id.spinnerAssetOperation);
        _spinnerCategory = view.findViewById(R.id.spinnerCategory);
        _spinnerSubCategory = view.findViewById(R.id.spinnerSubCategory);
        _textViewDate = view.findViewById(R.id.textViewDate);
        _textViewDateTitle = view.findViewById(R.id.textViewDateTitle);
        _imageViewDateMinus = view.findViewById(R.id.imageViewDateMinus);
        _imageViewDatePlus = view.findViewById(R.id.imageViewDatePlus);
        _editTextCount = view.findViewById(R.id.editTextCount);
        _editTextPrice = view.findViewById(R.id.editTextPrice);
        _editTextComission = view.findViewById(R.id.editTextComission);
        _editTextAccumulatedPrice = view.findViewById(R.id.editTextAccumulatedPrice);
        _textViewSum = view.findViewById(R.id.textViewSum);
        _textViewAccumulatedSum = view.findViewById(R.id.textViewAccumulatedSum);
        _textViewAssetOperationTV = view.findViewById(R.id.textViewAssetOperationTV);
        _textViewAssetOperation2 = view.findViewById(R.id.textViewAssetOperation2);
        _tableRowOperation = view.findViewById(R.id.tableRowOperation);
        _tableRowOperationTV = view.findViewById(R.id.tableRowOperationTV);
        _textViewAssetOperation = view.findViewById(R.id.textViewAssetOperation);
        _tableRowCategory = view.findViewById(R.id.tableRowCategory);
        _tableRowSubCategory = view.findViewById(R.id.tableRowSubCategory);
        _textViewCategory = view.findViewById(R.id.textViewCategory);
        _textViewSubCategory = view.findViewById(R.id.textViewSubCategory);
        _tableLayout = view.findViewById(R.id.tableLayout);
        _tableRowGroupPortfolio = view.findViewById(R.id.tableRowGroupPortfolio);
        _tableRowPortfolio = view.findViewById(R.id.tableRowPortfolio);
        _textViewGroupPortfolio = view.findViewById(R.id.textViewGroupPortfolio);
        _textViewPortfolio = view.findViewById(R.id.textViewPortfolio);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextPrice;
        _focusedViewToUpdate = _editTextPrice;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Lot_Asset_Width),
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
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Lot_Asset_Width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }
    //endregion

    //region Set Components Setting
    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
        MoneyLotAssetSettings moneyLotAssetSettings = globalVar.get_moneyLotAssetSettings();

        _spinnerGroupPortfolio.setEnabled(_data == null &&
                (moneyLotAssetSettings.get_groupPortfolioId() == null ||
                moneyLotAssetSettings.get_groupPortfolioId() == 0L));
        _spinnerPortfolio.setEnabled(_data == null &&
                (moneyLotAssetSettings.get_portfolioId() == null ||
                        moneyLotAssetSettings.get_portfolioId() == 0L));
        _spinnerTypeAsset.setEnabled(_data == null &&
                (moneyLotAssetSettings.get_typeAsset() == null ||
                        moneyLotAssetSettings.get_typeAsset() == 0L));
        _spinnerAsset.setEnabled(_data == null);

        if (_typeAction2 != null)
        {
            if (_typeAction2.equals(EnumTypeAction.Insert))
            {
                _tableRowOperationTV.getLayoutParams().height = 0;
                _textViewAssetOperationTV.getLayoutParams().height = 0;
                _textViewAssetOperation2.getLayoutParams().height = 0;
            }
            else if (_typeAction2.equals(EnumTypeAction.Update))
            {
                _tableRowOperation.getLayoutParams().height = 0;
                _textViewAssetOperation.getLayoutParams().height = 0;
                _spinnerOperation.getLayoutParams().height = 0;
            }
        }
        else
        {
            _tableRowOperation.getLayoutParams().height = 0;
            _textViewAssetOperation.getLayoutParams().height = 0;
            _spinnerOperation.getLayoutParams().height = 0;
        }

        SetVisible();
    }

    private void SetVisible()
    {
        MoneyAsset moneyAsset = get_MoneyAsset();
        if (_data != null)
            moneyAsset = _data.get_moneyLotAsset().get_moneyAsset();
        if (moneyAsset == null || moneyAsset.get_wallet_id() == 0)
        {
            _tableRowCategory.getLayoutParams().height = 0;
            _textViewCategory.getLayoutParams().height = 0;
            _spinnerCategory.getLayoutParams().height = 0;

            _tableRowSubCategory.getLayoutParams().height = 0;
            _textViewSubCategory.getLayoutParams().height = 0;
            _spinnerSubCategory.getLayoutParams().height = 0;

            _tableLayout.getLayoutParams().height =
                (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_LotAssetOperation_Height);
        }
        else
        {
            _tableRowGroupPortfolio.getLayoutParams().height = 0;
            _textViewGroupPortfolio.getLayoutParams().height = 0;
            _spinnerGroupPortfolio.getLayoutParams().height = 0;

            _tableRowPortfolio.getLayoutParams().height = 0;
            _textViewPortfolio.getLayoutParams().height = 0;
            _spinnerPortfolio.getLayoutParams().height = 0;

            _tableLayout.getLayoutParams().height =
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_LotAssetOperation_Height);
        }
    }
    //endregion

    //region Set Adapters
    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillSpinnerGroupPortfolio();
//        FillSpinnerPortfolio();
        FillSpinnerTypeAsset();
        FillSpinnerAsset();
        FillSpinnerLotOperation();
        FillSpinnerCategory();
    }

    private void FillSpinnerGroupPortfolio()
    {
        ArrayList<MoneyPortfolio> list = MoneyPortfolioDataSource.LoadDataParent(
                getContext(), false, new MoneyPortfolioSettings(null));

        SpinnerMoneyPortfolioAdapter adapter = new SpinnerMoneyPortfolioAdapter(getContext(), list);
        _spinnerGroupPortfolio.setAdapter(adapter);
    }

    private void FillSpinnerPortfolio()
    {
        ArrayList<MoneyPortfolio> list = MoneyPortfolioDataSource.LoadDataChildren(
                getContext(), get_groupPortfolioId(), null, new MoneyPortfolioSettings(null));

        SpinnerMoneyPortfolioAdapter adapter = new SpinnerMoneyPortfolioAdapter(getContext(), list);
        _spinnerPortfolio.setAdapter(adapter);
    }

    private void FillSpinnerTypeAsset()
    {
        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
        ArrayList<MoneyTypeAsset> list = MoneyTypeAssetDataSource.LoadDataParent(
                getContext(), false, new MoneyTypeAssetSettings(null));

        ArrayList<MoneyTypeAsset> listNew = new ArrayList<>();
        for (MoneyTypeAsset item: list)
            if (!Objects.equals(item.get_id(), globalVar.get_typeAsset4Creditd()) &&
                    !Objects.equals(item.get_id(), globalVar.get_typeAsset4WalletId()))
                listNew.add(item);

        SpinnerTypeAssetAdapter adapter = new SpinnerTypeAssetAdapter(getContext(), listNew);
        _spinnerTypeAsset.setAdapter(adapter);
    }

    private void FillSpinnerAsset()
    {
        MoneyAssetSettings moneyAssetSettings = new MoneyAssetSettings(get_typeAssetId(),null);
        ArrayList<MoneyAsset> list = MoneyAssetDataSource.LoadDataParent(
                getContext(), false, moneyAssetSettings);

        SpinnerAssetAdapter adapter = new SpinnerAssetAdapter(getContext(), list);
        _spinnerAsset.setAdapter(adapter);
    }

    private void FillSpinnerLotOperation()
    {
        ArrayList<EnumLotAssetOperation> list = new ArrayList<>();
        if (_data == null)
        {
            list.add(EnumLotAssetOperation.Buy);
            set_OperationTV(EnumLotAssetOperation.Buy);
        }
        else if (!_data.get_moneyLotAsset().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
        {
            list.add(EnumLotAssetOperation.Buy);
            list.add(EnumLotAssetOperation.Revaluation);
            list.add(EnumLotAssetOperation.Dividend);
            list.add(EnumLotAssetOperation.Coupon);
            list.add(EnumLotAssetOperation.Sell);
        }
        else
        {
            list.add(EnumLotAssetOperation.WalletIncome);
            list.add(EnumLotAssetOperation.WalletComission);
        }
        SpinnerTypeLotAssetOperationAdapter adapter = new SpinnerTypeLotAssetOperationAdapter(getContext(), list);
        _spinnerOperation.setAdapter(adapter);
    }

    private void FillSpinnerCategory()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Cost, true, false,
                true, EnumCategoryType.All, null);
        ArrayList<MoneyCategory> list = MoneyCategoryDataSource.LoadDataParent(getContext(),
                false, moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerCategory.setAdapter(adapter);
    }

    private void FillSpinnerSubCategory()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Cost, true, false,
                true, EnumCategoryType.All, null);
        ArrayList<MoneyCategory> list = MoneyCategoryDataSource.LoadDataChildren(getContext(),
                get_CategoryId(), moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerSubCategory.setAdapter(adapter);
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _spinnerGroupPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                FillSpinnerPortfolio();
                GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
                MoneyLotAssetSettings moneyLotAssetSettings = globalVar.get_moneyLotAssetSettings();
                if (_data != null)
                    set_portfolio(_data.get_moneyLotAsset().get_portfolioId());
                else if (moneyLotAssetSettings.get_portfolioId() != null &&
                        moneyLotAssetSettings.get_portfolioId() != 0L)
                    set_portfolio(moneyLotAssetSettings.get_portfolioId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        _spinnerTypeAsset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                FillSpinnerAsset();
                if (_data != null)
                    set_Asset(_data.get_moneyLotAsset().get_moneyAsset().get_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        _spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                FillSpinnerSubCategory();
                if (_data != null && _data.get_subCategoryId() != 0L)
                    set_subCategory(_data.get_subCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
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

        _editTextCount.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s)
            {
                ReCalcSum();
                ReCalcAccumulatedSum();
            }
        });

        _editTextPrice.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { ReCalcSum(); }
        });

        _editTextAccumulatedPrice.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { ReCalcAccumulatedSum(); }
        });
    }

    private void ReCalcSum()
    {
        try
        {
            long price = get_price();
            int count = get_count();
            if (price == 0L)
                _textViewSum.setText("");
            else if (Math.floorMod(price, 100L) == 0)
                _textViewSum.setText(String.valueOf(Math.floorDiv(count * price, 100L)));
            else
                _textViewSum.setText(String.valueOf(count * price / 100d));
        }
        catch (Exception e) { }
    }

    private void ReCalcAccumulatedSum()
    {
        try
        {
            long accumulatedPrice = get_accumulatedPrice();
            int count = get_count();
            if (accumulatedPrice == 0L)
                _textViewAccumulatedSum.setText("");
            else if (Math.floorMod(accumulatedPrice, 100L) == 0)
                _textViewAccumulatedSum.setText(String.valueOf(Math.floorDiv(count * accumulatedPrice, 100L)));
            else
                _textViewAccumulatedSum.setText(String.valueOf(count * accumulatedPrice / 100d));
        }
        catch (Exception e) { }
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
            set_groupPortfolio(_data.get_moneyLotAsset().get_groupPortfolioId());
            FillSpinnerPortfolio();
            set_portfolio(_data.get_moneyLotAsset().get_portfolioId());
            set_TypeAsset(_data.get_moneyLotAsset().get_moneyAsset().get_moneyTypeAssetId());
            set_Asset(_data.get_moneyLotAsset().get_moneyAsset().get_id());
            set_Operation(_data.get_lotAssetOperation());
            set_OperationTV(_data.get_lotAssetOperation());
            MoneyCategory moneyCategory = MoneyCategoryDataSource.LoadOne(getContext(), _data.get_subCategoryId());
            if (moneyCategory != null)
            {
                set_Category(moneyCategory.get_parentId());
                FillSpinnerSubCategory();
                set_subCategory(_data.get_subCategoryId());
            }
            set_date(_data.get_calendar());
            set_Count(_data.get_count());
            set_Price(_data.get_count(), _data.get_price());
            set_comission(_data.get_commission());
            set_accumulatedPrice(_data.get_count(), _data.get_accumulatedPrice());
        }
        else
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            MoneyLotAssetSettings moneyLotAssetSettings = globalVar.get_moneyLotAssetSettings();

            if (moneyLotAssetSettings.get_groupPortfolioId() != null &&
                    moneyLotAssetSettings.get_groupPortfolioId() != 0L)
                set_groupPortfolio(moneyLotAssetSettings.get_groupPortfolioId());
            FillSpinnerPortfolio();
            if (moneyLotAssetSettings.get_portfolioId() != null &&
                    moneyLotAssetSettings.get_portfolioId() != 0L)
                set_portfolio(moneyLotAssetSettings.get_portfolioId());
            if (moneyLotAssetSettings.get_typeAsset() != null &&
                    moneyLotAssetSettings.get_typeAsset() != 0L)
                set_TypeAsset(moneyLotAssetSettings.get_typeAsset());
            else
                set_TypeAsset(0L);
            set_Asset(0L);
            set_Operation(EnumLotAssetOperation.Buy);
            if (_customPeriodClass == null)
                set_date(Calendar.getInstance());
            else
                set_date(_customPeriodClass.get_start_dateC());
            set_Count(1);
        }
    }

    private void set_groupPortfolio(Long groupPortfolioId)
    {
        for (int i = 0; i < _spinnerGroupPortfolio.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyPortfolio) _spinnerGroupPortfolio.getAdapter().getItem(i)).get_id(), groupPortfolioId))
            {
                _spinnerGroupPortfolio.setSelection(i);
                break;
            }
    }

    private void set_portfolio(Long portfolioId)
    {
        for (int i = 0; i < _spinnerPortfolio.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyPortfolio) _spinnerPortfolio.getAdapter().getItem(i)).get_id(), portfolioId))
            {
                _spinnerPortfolio.setSelection(i);
                break;
            }
    }

    private void set_TypeAsset(Long typeAssetId)
    {
        for (int i = 0; i < _spinnerTypeAsset.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyTypeAsset) _spinnerTypeAsset.getAdapter().getItem(i)).get_id(), typeAssetId))
            {
                _spinnerTypeAsset.setSelection(i);
                break;
            }
    }

    private void set_Asset(Long assetId)
    {
        for (int i = 0; i < _spinnerAsset.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyAsset) _spinnerAsset.getAdapter().getItem(i)).get_id(), assetId))
            {
                _spinnerAsset.setSelection(i);
                break;
            }
    }

    private void set_Operation(EnumLotAssetOperation lotAssetOperation)
    {
        for (int i = 0; i < _spinnerOperation.getAdapter().getCount(); i++)
            if (Objects.equals(_spinnerOperation.getAdapter().getItem(i), lotAssetOperation))
            {
                _spinnerOperation.setSelection(i);
                break;
            }
    }

    private void set_OperationTV(EnumLotAssetOperation lotAssetOperation)
    {
        if (lotAssetOperation == EnumLotAssetOperation.Buy)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Buy);
        else if (lotAssetOperation == EnumLotAssetOperation.Sell)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Sell);
        else if (lotAssetOperation == EnumLotAssetOperation.Dividend)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Dividend);
        else if (lotAssetOperation == EnumLotAssetOperation.Coupon)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Coupon);
        else if (lotAssetOperation == EnumLotAssetOperation.Revaluation)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Revaluation);
        else if (lotAssetOperation == EnumLotAssetOperation.WalletComission)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Comission);
        else if (lotAssetOperation == EnumLotAssetOperation.WalletIncome)
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Income);
        else
            _textViewAssetOperationTV.setText(R.string.Money_LotAssetOperation_Undentified);
    }

    private void set_Category(Long categoryId)
    {
        for (int i = 0; i < _spinnerCategory.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerCategory.getAdapter().getItem(i)).get_id(), categoryId))
            {
                _spinnerCategory.setSelection(i);
                break;
            }
    }
    private void set_subCategory(Long subCategoryId)
    {
        for (int i = 0; i < _spinnerSubCategory.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerSubCategory.getAdapter().getItem(i)).get_id(), subCategoryId))
            {
                _spinnerSubCategory.setSelection(i);
                break;
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

    private void set_Count(int count)
    {
        _editTextCount.setText(String.valueOf(count));
    }
    private void set_Price(int count, long price)
    {
        if (price == 0L)
        {
            _editTextPrice.setText("");
            _textViewSum.setText("");
        }
        else if (Math.floorMod(price, 100L) == 0)
        {
            _editTextPrice.setText(String.valueOf(Math.floorDiv(price, 100L)));
            _textViewSum.setText(String.valueOf(Math.floorDiv(count * price, 100L)));
        }
        else
        {
            _editTextPrice.setText(String.valueOf(price / 100d));
            _textViewSum.setText(String.valueOf(count * price / 100d));
        }
    }
    private void set_comission(long comission)
    {
        if (comission == 0L)
            _editTextComission.setText("");
        else if (Math.floorMod(comission, 100L) == 0)
            _editTextComission.setText(String.valueOf(Math.floorDiv(comission, 100L)));
        else
            _editTextComission.setText(String.valueOf(comission / 100d));
    }
    private void set_accumulatedPrice(int count, long accumulatedPrice)
    {
        if (accumulatedPrice == 0L)
        {
            _editTextAccumulatedPrice.setText("");
            _textViewAccumulatedSum.setText("");
        }
        else if (Math.floorMod(accumulatedPrice, 100L) == 0)
        {
            _editTextAccumulatedPrice.setText(String.valueOf(Math.floorDiv(accumulatedPrice, 100L)));
            _textViewAccumulatedSum.setText(String.valueOf(Math.floorDiv(count * accumulatedPrice, 100L)));
        }
        else
        {
            _editTextAccumulatedPrice.setText(String.valueOf(accumulatedPrice / 100d));
            _textViewAccumulatedSum.setText(String.valueOf(count * accumulatedPrice / 100d));
        }
    }
    //endregion

    //region Check Params
    @Override
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        return true;
    }

    protected boolean CheckFormValuesForDelete() { return true; }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        return _data != null ? _data.get_id() : 0L;
    }
    private MoneyLotAsset get_MoneyLotAsset()
    {
        if (_data == null)
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2900, 11, 31);
                return new MoneyLotAsset(0L,
                        get_groupPortfolioId(),
                        get_groupPortfolioName(),
                        get_portfolioId(),
                        get_portfolioName(),
                        get_portfolioWallet(),
                        get_MoneyAsset(),
                        get_MoneyAsset().get_name(), "",
                        dbHelper.QueryMoneyLotAssetMaxPosition(get_typeAssetId()) + 1,
                        get_count(),
                        _date,
                        calendar,
                        EnumStatus.Started,
                        null,
                        null);
            }
            catch (Exception e)
            {
                return null;
            }
        else
            return _data.get_moneyLotAsset();
    }
    private Long get_groupPortfolioId()
    {
        if (_spinnerGroupPortfolio.getAdapter().getCount() == 0)
            return 0L;
        return ((MoneyPortfolio) _spinnerGroupPortfolio.getSelectedItem()).get_id();
    }
    private String get_groupPortfolioName()
    {
        return ((MoneyPortfolio) _spinnerGroupPortfolio.getSelectedItem()).get_name();
    }
    private Long get_portfolioId()
    {
        if (_spinnerPortfolio.getAdapter().getCount() == 0)
            return 0L;
        return ((MoneyPortfolio) _spinnerPortfolio.getSelectedItem()).get_id();
    }
    private String get_portfolioName()
    {
        return ((MoneyPortfolio) _spinnerPortfolio.getSelectedItem()).get_name();
    }
    private Long get_portfolioWallet()
    {
        return ((MoneyPortfolio) _spinnerPortfolio.getSelectedItem()).get_walletId();
    }
    private MoneyAsset get_MoneyAsset()
    {
        return ((MoneyAsset) _spinnerAsset.getSelectedItem());
    }
    private Long get_typeAssetId()
    {
        if (_spinnerTypeAsset.getSelectedItem() != null)
            return ((MoneyTypeAsset) _spinnerTypeAsset.getSelectedItem()).get_id();

        return 0L;
    }
    private EnumLotAssetOperation get_lotAssetOperation()
    {
        return (EnumLotAssetOperation) _spinnerOperation.getSelectedItem();
    }
    private Integer get_count() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextCount.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }
    private Integer get_countOld()
    {
        return _data == null ? 0 : _data.get_countOld();
    }
    private Long get_price() throws Exception
    {
        try
        {
            String s = _editTextPrice.getText().toString();
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
    private Long get_priceOld()
    {
        return _data == null ? 0L : _data.get_priceOld();
    }
    private Long get_comission() throws Exception
    {
        try
        {
            String s = _editTextComission.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            if (s.equals(""))
                return 0L;
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
    private Long get_comissionOld()
    {
        return _data == null ? 0L : _data.get_commissionOld();
    }
    private Long get_accumulatedPrice() throws Exception
    {
        try
        {
            String s = _editTextAccumulatedPrice.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            if (s.equals(""))
                return 0L;
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

    private Long get_CategoryId()
    {
        if (_spinnerCategory.getAdapter() != null)
            return ((MoneyCategory) _spinnerCategory.getSelectedItem()).get_id();
        return 0L;
    }
    private Long get_subCategoryId()
    {
        if (_spinnerSubCategory.getAdapter() != null)
            return ((MoneyCategory) _spinnerSubCategory.getSelectedItem()).get_id();
        return 0L;
    }
    //endregion

    //region Save Params
    protected MoneyLotAssetOperation GetDataFromForm()
    {
        try
        {
            if (_independent)
                return new MoneyLotAssetOperationIndependent(
                        get_Id(),
                        get_MoneyLotAsset(),
                        false,
                        get_lotAssetOperation(),
                        get_CategoryId(),
                        get_subCategoryId(),
                        get_count(),
                        get_countOld(),
                        get_price(),
                        get_priceOld(),
                        get_comission(),
                        get_comissionOld(),
                        get_accumulatedPrice(),
                        _date);
            else
                return new MoneyLotAssetOperation(
                        get_Id(),
                        get_MoneyLotAsset(),
                        false,
                        get_lotAssetOperation(),
                        get_CategoryId(),
                        get_subCategoryId(),
                        get_count(),
                        get_countOld(),
                        get_price(),
                        get_priceOld(),
                        get_comission(),
                        get_comissionOld(),
                        get_accumulatedPrice(),
                        _date);

        }
        catch (Exception e)
        {
            return  null;
        }
    }
    //endregion
}