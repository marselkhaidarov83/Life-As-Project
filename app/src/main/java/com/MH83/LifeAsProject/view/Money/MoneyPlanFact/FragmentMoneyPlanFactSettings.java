package com.MH83.LifeAsProject.view.Money.MoneyPlanFact;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyCategoryTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyOperationIsPlanAdapter;
import com.MH83.LifeAsProject.adapter.spinner.settings.SpinnerSeetingsTagAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Custom.TagDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyPlanFactSettings extends CustomPopupBottomFragment<MoneyPlanFactSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_planfact;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private CheckBox _checkBoxShowPercent;
    private CheckBox _checkBoxShowBlankData;
    private Spinner _spinnerOperationIsPlan;
    private Spinner _spinnerCategoryType;
    private Spinner _spinnerTag;
    private ImageView _imageViewMascotToSettings;
    //endregion

    //region Start Fragment
    public FragmentMoneyPlanFactSettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyPlanFact,
                R.string.SettingsMoneyPlanFact,
                0);
    }

    public FragmentMoneyPlanFactSettings(AppCompatActivity activity,
                                         MoneyPlanFactSettings moneyPlanFactSettings,
                                         IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyPlanFactSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyPlanFact,
                R.string.SettingsMoneyPlanFact,
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

        _checkBoxShowPercent = view.findViewById(R.id.checkBoxShowPercent);
        _checkBoxShowBlankData = view.findViewById(R.id.checkBoxShowBlankData);
        _imageViewMascotToSettings = view.findViewById(R.id.imageViewMascotToSettings);

        _spinnerOperationIsPlan = view.findViewById(R.id.spinnerOperationIsPlan);
        _spinnerCategoryType = view.findViewById(R.id.spinnerCategoryType);
        _spinnerTag = view.findViewById(R.id.spinnerTag);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            _checkBoxShowPercent.setEnabled(false);
            _checkBoxShowBlankData.setEnabled(false);
            _spinnerOperationIsPlan.setEnabled(false);
            _spinnerCategoryType.setEnabled(false);
            _spinnerTag.setEnabled(false);
        }

        if (!global.get_showMascot())
            _imageViewMascotToSettings.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetOperationIsPlanAdapter();
        SetCategoryTypeAdapter();
        SetTagAdapter(EnumTagTypeData.PNL);
    }

    private void SetOperationIsPlanAdapter()
    {
        ArrayList<Boolean> list = new ArrayList<>();
        list.add(null);
        list.add(true);
        list.add(false);

        SpinnerSettingsMoneyOperationIsPlanAdapter spinnerOperationIsPlanAdapter =
                new SpinnerSettingsMoneyOperationIsPlanAdapter(getContext(), list);
        _spinnerOperationIsPlan.setAdapter(spinnerOperationIsPlanAdapter);
    }

    private void SetCategoryTypeAdapter()
    {
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

    private void SetTagAdapter(EnumTagTypeData tagTypeData)
    {
        List<Tag> list = new ArrayList<>();
        TagSettings tagSettings = new TagSettings(tagTypeData, false);
        if (tagTypeData != null)
            list = TagDataSource.LoadData(getContext(), tagSettings, true);
        SpinnerSeetingsTagAdapter tagAdapter = new SpinnerSeetingsTagAdapter(
                getContext(), list);
        _spinnerTag.setAdapter(tagAdapter);
    }

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewMascotToSettings.setOnClickListener(view ->
                new FragmentMoneyPlanFactLegendToSettingsPopup((AppCompatActivity) getContext()).PopupShow());
    }

    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetShowPercent(_data.get_showPercent());
        SetShowBlankData(_data.get_showBlankData());
        SetCategoryType(_data.get_categoryType());
        SetOperationIsPlan(_data.get_isPlan());
        SetTagId(_data.get_tagId());
    }

    private void SetShowPercent(boolean b)
    {
        _checkBoxShowPercent.setChecked(b);
    }

    private void SetShowBlankData(boolean b)
    {
        _checkBoxShowBlankData.setChecked(b);
    }

    private void SetCategoryType(EnumCategoryType categoryType)
    {
        for (int i = 0; i < _spinnerCategoryType.getAdapter().getCount(); i++)
            if ((_spinnerCategoryType.getAdapter().getItem(i))
                    .equals(categoryType))
            {
                _spinnerCategoryType.setSelection(i);
                break;
            }
    }

    private void SetOperationIsPlan(Boolean b)
    {
        for (int i = 0; i < _spinnerOperationIsPlan.getAdapter().getCount(); i++)
            if (Objects.equals((_spinnerOperationIsPlan.getAdapter().getItem(i)), b))
            {
                _spinnerOperationIsPlan.setSelection(i);
                break;
            }
    }

    private void SetTagId(Long tagId)
    {
        if (tagId != null)
            for (int i = 0; i < _spinnerTag.getAdapter().getCount(); i++)
                if (((Tag) _spinnerTag.getAdapter().getItem(i)).get_id().equals(tagId))
                {
                    _spinnerTag.setSelection(i);
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
    private EnumCategoryType getCategoryType()
    {
        if (_spinnerCategoryType.getAdapter().getCount() > 0)
            return (EnumCategoryType) _spinnerCategoryType.getSelectedItem();

        return null;
    }

    private EnumTypeCategory getTypeCategory()
    {
        return _data.get_typeCategory();
    }

    private CustomPeriodClass getCustomPeriodClass()
    {
        return _data.get_customPeriodClass();
    }

    private Boolean getOperationIsPlan()
    {
        if (_spinnerOperationIsPlan.getAdapter().getCount() > 0)
            return (Boolean) _spinnerOperationIsPlan.getSelectedItem();

        return null;
    }

    private boolean getShowPercent()
    {
        return _checkBoxShowPercent.isChecked();
    }

    private boolean getShowBlankData()
    {
        return _checkBoxShowBlankData.isChecked();
    }

    private ArrayList<Long> getExpandedList()
    {
        return _data.get_expandedList();
    }

    private boolean getShowExpandButton()
    {
        return _data.get_showExpandButton();
    }

    private Long getTagId()
    {
        if (_spinnerTag.getAdapter().getCount() > 0)
            return ((Tag) _spinnerTag.getSelectedItem()).get_id();

        return null;
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyPlanFactSettings GetDataFromForm()
    {
        return new MoneyPlanFactSettings(
                getTypeCategory(),
                getCustomPeriodClass(),
                getShowPercent(),
                getShowBlankData(),
                getOperationIsPlan(),
                getExpandedList(),
                getShowExpandButton(),
                getCategoryType(),
                getTagId());
    }
    //endregion
}
