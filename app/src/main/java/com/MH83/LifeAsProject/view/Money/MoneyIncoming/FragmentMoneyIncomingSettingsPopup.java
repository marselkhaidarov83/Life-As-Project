package com.MH83.LifeAsProject.view.Money.MoneyIncoming;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyCategoryTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyTypeDataAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsPeriodDataAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentMoneyIncomingSettingsPopup extends CustomPopupBottomFragment<MoneyIncomingAndCostSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_incoming;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private CheckBox _checkBoxShowIncreasePercent;
    private CheckBox _checkBoxShowPercent;
    private CheckBox _checkBoxShowBlankData;
    private Spinner _spinnerTypeData;
    private Spinner _spinnerPeriodData;
    private Spinner _spinnerCategoryType;
    //endregion

    //region Start Fragment
    public FragmentMoneyIncomingSettingsPopup()
    {
        super(_layoutId,
                R.string.SettingsMoneyIncoming,
                R.string.SettingsMoneyIncoming,
                0);
    }

    public FragmentMoneyIncomingSettingsPopup(AppCompatActivity activity,
                                              MoneyIncomingAndCostSettings moneyIncomingSettings,
                                              IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyIncomingSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyIncoming,
                R.string.SettingsMoneyIncoming,
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
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopupSettings_Incoming_Width) /*our width*/,
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

        _checkBoxShowIncreasePercent = view.findViewById(R.id.checkBoxShowIncreasePercent);
        _checkBoxShowPercent = view.findViewById(R.id.checkBoxShowPercent);
        _checkBoxShowBlankData = view.findViewById(R.id.checkBoxShowBlankData);
        _spinnerTypeData = view.findViewById(R.id.spinnerPlanFact);
        _spinnerPeriodData = view.findViewById(R.id.spinnerPeriodData);
        _spinnerCategoryType = view.findViewById(R.id.spinnerCategoryType);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        List<EnumTypeData> list = new ArrayList<>();
        list.add(EnumTypeData.Plan);
        list.add(EnumTypeData.Fact);
        SpinnerSettingsMoneyTypeDataAdapter adapter = new SpinnerSettingsMoneyTypeDataAdapter(getContext(), list);
        _spinnerTypeData.setAdapter(adapter);

        List<EnumPeriodData> list1 = new ArrayList<>();
//        list1.add(EnumPeriodData.Day);
        list1.add(EnumPeriodData.Week);
        list1.add(EnumPeriodData.Month);
        list1.add(EnumPeriodData.Quarter);
        list1.add(EnumPeriodData.Year);
        SpinnerSettingsPeriodDataAdapter adapter1 = new SpinnerSettingsPeriodDataAdapter(getContext(), list1);
        _spinnerPeriodData.setAdapter(adapter1);

        List<EnumCategoryType> list2 = new ArrayList<>();
        list2.add(EnumCategoryType.Base);
        list2.add(EnumCategoryType.Advanced);
        list2.add(EnumCategoryType.AdvancedPlus);
        list2.add(EnumCategoryType.Project);
        list2.add(EnumCategoryType.ProjectPlus);
        list2.add(EnumCategoryType.All);
        SpinnerSettingsMoneyCategoryTypeAdapter adapter2 = new SpinnerSettingsMoneyCategoryTypeAdapter(getContext(), list2);
        _spinnerCategoryType.setAdapter(adapter2);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            _checkBoxShowBlankData.setEnabled(false);
            _checkBoxShowPercent.setEnabled(false);
            _checkBoxShowIncreasePercent.setEnabled(false);
            _spinnerCategoryType.setEnabled(false);
            _spinnerPeriodData.setEnabled(false);
            _spinnerTypeData.setEnabled(false);
            _buttonSave.setEnabled(false);
        }
    }

    //endregion

    //region SetListeners

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _checkBoxShowIncreasePercent.setOnClickListener(view ->
        {
            if (_checkBoxShowIncreasePercent.isChecked())
                _checkBoxShowPercent.setChecked(false);
        });

        _checkBoxShowPercent.setOnClickListener(view ->
        {
            if (_checkBoxShowPercent.isChecked())
                _checkBoxShowIncreasePercent.setChecked(false);
        });

        _spinnerTypeData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (get_typeData() == EnumTypeData.Plan)
                {
                    SetPeriodData(EnumPeriodData.Month);
                    _spinnerPeriodData.setEnabled(false);
                }
                else
                    _spinnerPeriodData.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        _checkBoxShowIncreasePercent.setChecked(_data.get_showIncreasePercent());
        _checkBoxShowPercent.setChecked(_data.get_showPercent());
        _checkBoxShowBlankData.setChecked(_data.get_showBlankData());

        for (int i = 0; i < _spinnerTypeData.getAdapter().getCount(); i++)
            if (_spinnerTypeData.getAdapter().getItem(i) == _data.get_typeData())
            {
                _spinnerTypeData.setSelection(i);
                break;
            }

        SetPeriodData(_data.get_periodData());
        SetCategoryType(_data.get_categoryType());
    }

    private void SetPeriodData(EnumPeriodData periodData)
    {
        for (int i = 0; i < _spinnerPeriodData.getAdapter().getCount(); i++)
            if (_spinnerPeriodData.getAdapter().getItem(i) == periodData)
            {
                _spinnerPeriodData.setSelection(i);
                break;
            }
    }

    private void SetCategoryType(EnumCategoryType categoryType)
    {
        for (int i = 0; i < _spinnerCategoryType.getAdapter().getCount(); i++)
            if (_spinnerCategoryType.getAdapter().getItem(i) == categoryType)
            {
                _spinnerCategoryType.setSelection(i);
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
    private boolean get_showIncreasePercent()
    {
        return _checkBoxShowIncreasePercent.isChecked();
    }

    private boolean get_showPercent()
    {
        return _checkBoxShowPercent.isChecked();
    }

    private boolean get_showBlankData()
    {
        return _checkBoxShowBlankData.isChecked();
    }

    private EnumTypeData get_typeData()
    {
        return (EnumTypeData) _spinnerTypeData.getSelectedItem();
    }

    private EnumPeriodData get_periodData()
    {
        return (EnumPeriodData) _spinnerPeriodData.getSelectedItem();
    }

    private EnumCategoryType get_categoryType()
    {
        return (EnumCategoryType) _spinnerCategoryType.getSelectedItem();
    }

    private int get_countMoneyIncrease()
    {
        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        return globalVar.get_moneyIncomingAndCostSettingsCountMoneyIncrease(_data.get_typeCategory(),
                get_typeData());
    }

    private ArrayList<Long> get_expandedList()
    {
        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        return globalVar.get_expandedList(_data.get_typeCategory(), get_typeData());
    }

    private boolean get_showExpandButton()
    {
        return _data.get_showExpandButton();
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyIncomingAndCostSettings GetDataFromForm()
    {
        return new MoneyIncomingAndCostSettings(
                _data.get_typeCategory(),
                get_showIncreasePercent(),
                get_showPercent(),
                get_showBlankData(),
                get_typeData(),
                get_periodData(),
                _data.get_fragmentId(),
                _data.get_selMoneyIncreaseId(),
                get_countMoneyIncrease(),
                get_expandedList(),
                get_showExpandButton(),
                get_categoryType());
    }
    //endregion
}
