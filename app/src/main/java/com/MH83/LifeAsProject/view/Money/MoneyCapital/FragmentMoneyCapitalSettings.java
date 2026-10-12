package com.MH83.LifeAsProject.view.Money.MoneyCapital;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsEnumKindAssetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsEnumTypeCapitalDataAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyPortfolioAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyTypeAssetAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyPortfolioDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.ETypeCapitalData;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolio;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;

public class FragmentMoneyCapitalSettings extends CustomPopupBottomFragment<MoneyLotAssetSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_capital;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private Spinner _spinnerGroupPortfolio;
    private Spinner _spinnerPortfolio;
    private Spinner _spinnerKindAsset;
    private Spinner _spinnerTypeAsset;
    private Spinner _spinnerTypeCapitalData;
    //endregion

    //region Start Fragment
    public FragmentMoneyCapitalSettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyCapital,
                R.string.SettingsMoneyCapital,
                0);
    }

    public FragmentMoneyCapitalSettings(AppCompatActivity activity,
                                        MoneyLotAssetSettings moneyLotAssetSettings,
                                        IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyLotAssetSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyCapital,
                R.string.SettingsMoneyCapital,
                0,
                iFragmentViewCustom,
                null,
                null);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopupSettings_Capital_Width) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    }

    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerGroupPortfolio = view.findViewById(R.id.spinnerGroupPortfolio);
        _spinnerPortfolio = view.findViewById(R.id.spinnerPortfolio);
        _spinnerKindAsset = view.findViewById(R.id.spinnerKindAsset);
        _spinnerTypeAsset = view.findViewById(R.id.spinnerTypeAsset);
        _spinnerTypeCapitalData = view.findViewById(R.id.spinnerTypeCapitalData);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            _spinnerTypeCapitalData.setEnabled(false);
            _spinnerPortfolio.setEnabled(false);
            _spinnerGroupPortfolio.setEnabled(false);
            _spinnerKindAsset.setEnabled(false);
            _spinnerTypeAsset.setEnabled(false);
        }
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetGroupPortfolioAdapter();
        SetPortfolioAdapter(_data.get_groupPortfolioId());
        SetKindAssetAdapter();
        SetTypeAssetAdapter();
        SetTypeCapitalDataAdapter();
    }

    private void SetGroupPortfolioAdapter()
    {
        ArrayList<MoneyPortfolio> list = MoneyPortfolioDataSource.LoadDataParent(
                getContext(), true, null);
        SpinnerSettingsMoneyPortfolioAdapter adapter = new SpinnerSettingsMoneyPortfolioAdapter(getContext(), list);
        _spinnerGroupPortfolio.setAdapter(adapter);
    }

    private void SetPortfolioAdapter(long groupPotfolioId)
    {
        long id = getPortfolioId();

        ArrayList<MoneyPortfolio> list = new ArrayList<>();
        if (groupPotfolioId > 0)
            list = MoneyPortfolioDataSource.LoadDataChildren(
                    getContext(),
                    getGroupPortfolioId(),
                    null,
                    true, null);
        SpinnerSettingsMoneyPortfolioAdapter adapter = new SpinnerSettingsMoneyPortfolioAdapter(getContext(), list);
        _spinnerPortfolio.setAdapter(adapter);

        SetPortfolio(id);
    }

    private void SetKindAssetAdapter()
    {
        ArrayList<EnumKindAsset> list = new ArrayList<>();
        list.add(EnumKindAsset.Blank);
        list.add(EnumKindAsset.Conservative);
        list.add(EnumKindAsset.Moderate);
        list.add(EnumKindAsset.Aggressive);
        SpinnerSettingsEnumKindAssetAdapter adapter = new SpinnerSettingsEnumKindAssetAdapter(getContext(), list);
        _spinnerKindAsset.setAdapter(adapter);
    }
    private void SetTypeAssetAdapter()
    {
        ArrayList<MoneyTypeAsset> list = MoneyTypeAssetDataSource.LoadDataParent(
                getContext(), true, null);
        SpinnerSettingsMoneyTypeAssetAdapter adapter = new SpinnerSettingsMoneyTypeAssetAdapter(getContext(), list);
        _spinnerTypeAsset.setAdapter(adapter);
    }
    private void SetTypeCapitalDataAdapter()
    {
        ArrayList<ETypeCapitalData> list = new ArrayList<>();
        list.add(ETypeCapitalData.Sum);
        list.add(ETypeCapitalData.Percent);
        SpinnerSettingsEnumTypeCapitalDataAdapter adapter = new SpinnerSettingsEnumTypeCapitalDataAdapter(getContext(), list);
        _spinnerTypeCapitalData.setAdapter(adapter);
    }
    //endregion

    //region SetListeners

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _spinnerGroupPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                SetPortfolioAdapter(getGroupPortfolioId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetGroupPortfolio(_data.get_groupPortfolioId());
        SetPortfolioAdapter(_data.get_groupPortfolioId());
        SetPortfolio(_data.get_portfolioId());
        SetKindAsset(_data.get_kindAsset());
        SetTypeAsset(_data.get_typeAsset());
        SetTypeCapitalData(_data.get_typeCapitalData());
    }

    private void SetGroupPortfolio(long id)
    {
        for (int i = 0; i < _spinnerGroupPortfolio.getAdapter().getCount(); i++)
            if (((MoneyPortfolio) _spinnerGroupPortfolio.getAdapter().getItem(i)).get_id() == id)
            {
                _spinnerGroupPortfolio.setSelection(i);
                break;
            }
    }

    private void SetPortfolio(long id)
    {
        for (int i = 0; i < _spinnerPortfolio.getAdapter().getCount(); i++)
            if (((MoneyPortfolio) _spinnerPortfolio.getAdapter().getItem(i)).get_id() == id)
            {
                _spinnerPortfolio.setSelection(i);
                break;
            }
    }

    private void SetKindAsset(EnumKindAsset kindAsset)
    {
        for (int i = 0; i < _spinnerKindAsset.getAdapter().getCount(); i++)
            if (_spinnerKindAsset.getAdapter().getItem(i) == kindAsset)
            {
                _spinnerKindAsset.setSelection(i);
                break;
            }
    }

    private void SetTypeAsset(long id)
    {
        for (int i = 0; i < _spinnerTypeAsset.getAdapter().getCount(); i++)
            if (((MoneyTypeAsset) _spinnerTypeAsset.getAdapter().getItem(i)).get_id() == id)
            {
                _spinnerTypeAsset.setSelection(i);
                break;
            }
    }
    private void SetTypeCapitalData(ETypeCapitalData typeCapitalData)
    {
        for (int i = 0; i < _spinnerTypeCapitalData.getAdapter().getCount(); i++)
            if (_spinnerTypeCapitalData.getAdapter().getItem(i) == typeCapitalData)
            {
                _spinnerTypeCapitalData.setSelection(i);
                break;
            }
    }
    //endregion

    //region Check Params
    @Override
    protected boolean CheckFormValues()
    {
        return true;
    }
    //endregion

    //region Get Params
    private Long getGroupPortfolioId()
    {
        if (_spinnerGroupPortfolio.getAdapter().getCount() > 0)
            return ((MoneyPortfolio) _spinnerGroupPortfolio.getSelectedItem()).get_id();

        return 0L;
    }

    private Long getPortfolioId()
    {
        if (_spinnerPortfolio.getAdapter() != null)
            if (_spinnerPortfolio.getAdapter().getCount() > 0)
                return ((MoneyPortfolio) _spinnerPortfolio.getSelectedItem()).get_id();

        return 0L;
    }

    private EnumKindAsset getKindAsset()
    {
        return (EnumKindAsset) _spinnerKindAsset.getSelectedItem();
    }

    private Long getTypeAssetId()
    {
        if (_spinnerTypeAsset.getAdapter().getCount() > 0)
            return ((MoneyTypeAsset) _spinnerTypeAsset.getSelectedItem()).get_id();

        return 0L;
    }

    private ETypeCapitalData getTypeCapitalData()
    {
        return (ETypeCapitalData) _spinnerTypeCapitalData.getSelectedItem();
    }

    private CustomPeriodClass getCustomPeriodClass()
    {
        return _data.get_customPeriodClass();
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyLotAssetSettings GetDataFromForm()
    {
        return new MoneyLotAssetSettings(
                getGroupPortfolioId(),
                getPortfolioId(),
                getKindAsset(),
                getTypeAssetId(),
                getCustomPeriodClass(),
                getTypeCapitalData());
    }
    //endregion
}
