package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyCategoryAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyTypeCategoryAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.settings.SpinnerSettingsMoneyOperationIsPlanAdapter;
import com.MH83.LifeAsProject.adapter.spinner.settings.SpinnerSeetingsTagAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Custom.TagDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyTransactionSettings extends CustomPopupBottomFragment<MoneyTransactionSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_transaction;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private Spinner _spinnerOperationIsPlan;
    private Spinner _spinnerTypeCategory;
    private Spinner _spinnerCategory;
    private Spinner _spinnerSubCategory;
    private Spinner _spinnerTag;
    private ImageView _imageViewMascotToSettings;
    //endregion

    //region Start Fragment
    public FragmentMoneyTransactionSettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyAccounting,
                R.string.SettingsMoneyAccounting,
                0);
    }

    public FragmentMoneyTransactionSettings(AppCompatActivity activity,
                                            MoneyTransactionSettings moneyTransactionSettings,
                                            IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyTransactionSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyAccounting,
                R.string.SettingsMoneyAccounting,
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

        _spinnerOperationIsPlan = view.findViewById(R.id.spinnerOperationIsPlan);
        _spinnerTypeCategory = view.findViewById(R.id.spinnerTypeCategory);
        _spinnerCategory = view.findViewById(R.id.spinnerCategory);
        _spinnerSubCategory = view.findViewById(R.id.spinnerSubCategory);
        _spinnerTag = view.findViewById(R.id.spinnerTag);
        _imageViewMascotToSettings = view.findViewById(R.id.imageViewMascotToSettings);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            _spinnerOperationIsPlan.setEnabled(false);
            _spinnerTypeCategory.setEnabled(false);
            _spinnerCategory.setEnabled(false);
            _spinnerSubCategory.setEnabled(false);
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
        SetTypeCategoryAdapter();
        SetCategoryAdapter(_data.get_typeCategory());
        SetSubCategoryAdapter(_data.get_categoryId(), _data.get_typeCategory());
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

    private void SetTypeCategoryAdapter()
    {
        List<EnumTypeCategory> list = new ArrayList<>();
        list.add(EnumTypeCategory.All);
        list.add(EnumTypeCategory.Increase);
        list.add(EnumTypeCategory.Cost);
        list.add(EnumTypeCategory.Wallet);

        SpinnerSettingsMoneyTypeCategoryAdapter adapter2 = new SpinnerSettingsMoneyTypeCategoryAdapter(getContext(), list);
        _spinnerTypeCategory.setAdapter(adapter2);
    }

    private void SetCategoryAdapter(EnumTypeCategory typeCategory)
    {
        List<MoneyCategory> list = new ArrayList<>();
        if (typeCategory != null)
        {
            MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                    typeCategory, true, false,
                    true, EnumCategoryType.All,null);
            list = MoneyCategoryDataSource.LoadDataParent(getContext(), true, moneyCategorySettings);
        }
        SpinnerSettingsMoneyCategoryAdapter moneyCategoryAdapter = new SpinnerSettingsMoneyCategoryAdapter(
                getContext(), list);
        _spinnerCategory.setAdapter(moneyCategoryAdapter);
    }

    private void SetSubCategoryAdapter(Long categoryId, EnumTypeCategory typeCategory)
    {
        List<MoneyCategory> list = new ArrayList<>();
        if (categoryId != null && categoryId != 0L)
        {
            MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                    typeCategory, true, false,
                    true, EnumCategoryType.All,null);
            list = MoneyCategoryDataSource.LoadDataChildrenWithEmpty(getContext(), categoryId,
                    moneyCategorySettings, true);
        }
        SpinnerSettingsMoneyCategoryAdapter moneyCategoryAdapter = new SpinnerSettingsMoneyCategoryAdapter(
                getContext(), list);
        _spinnerSubCategory.setAdapter(moneyCategoryAdapter);
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

        _spinnerTypeCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Long categoryId = getCategoryId();
                SetCategoryAdapter(getCategoryType());
                SetCategoryId(categoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        _spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Long subCategory = getSubCategoryId();
                Long id = ((MoneyCategory) _spinnerCategory.getSelectedItem()).get_id();
                SetSubCategoryAdapter(id, _data.get_typeCategory());
                SetSubCategoryId(subCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        _imageViewMascotToSettings.setOnClickListener(view ->
                new FragmentMoneyTransactionLegendToSettingsPopup((AppCompatActivity) getContext()).PopupShow());
    }

    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetTypeCategory(_data.get_typeCategory());
        SetOperationIsPlan(_data.get_isPlan());
        SetCategoryId(_data.get_categoryId());
        SetSubCategoryId(_data.get_subCategoryId());
        SetTagId(_data.get_tagId());
    }

    private void SetTypeCategory(EnumTypeCategory typeCategory)
    {
        for (int i = 0; i < _spinnerTypeCategory.getAdapter().getCount(); i++)
            if (_spinnerTypeCategory.getAdapter().getItem(i).equals(typeCategory))
            {
                _spinnerTypeCategory.setSelection(i);
                break;
            }
    }

    private void SetOperationIsPlan(Boolean b)
    {
        for (int i = 0; i < _spinnerOperationIsPlan.getAdapter().getCount(); i++)
            if (Objects.equals(_spinnerOperationIsPlan.getAdapter().getItem(i), b))
            {
                _spinnerOperationIsPlan.setSelection(i);
                break;
            }
    }

    private void SetCategoryId(Long categoryId)
    {
        if (categoryId != null)
            for (int i = 0; i < _spinnerCategory.getAdapter().getCount(); i++)
                if (((MoneyCategory) _spinnerCategory.getAdapter().getItem(i)).get_id().equals(categoryId))
                {
                    _spinnerCategory.setSelection(i);
                    break;
                }
    }

    private void SetSubCategoryId(Long subCategoryId)
    {
        if (subCategoryId != null)
            for (int i = 0; i < _spinnerSubCategory.getAdapter().getCount(); i++)
                if (((MoneyCategory) _spinnerSubCategory.getAdapter().getItem(i)).get_id().equals(subCategoryId))
                {
                    _spinnerSubCategory.setSelection(i);
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
    private EnumTransactionType getTransactionType()
    {
        return _data.get_enumTransactionType();
    }

    private EnumTypeCategory getCategoryType()
    {
        if (_spinnerTypeCategory.getAdapter().getCount() > 0)
            return (EnumTypeCategory) _spinnerTypeCategory.getSelectedItem();

        return null;
    }

    private Boolean getOperationIsPlan()
    {
        if (_spinnerOperationIsPlan.getAdapter().getCount() > 0)
            return (Boolean) _spinnerOperationIsPlan.getSelectedItem();

        return null;
    }

    private Long getCategoryId()
    {
        if (_spinnerCategory.getAdapter().getCount() > 0)
            return ((MoneyCategory) _spinnerCategory.getSelectedItem()).get_id();

        return null;
    }

    private Long getSubCategoryId()
    {
        if (_spinnerSubCategory.getAdapter().getCount() > 0)
            return ((MoneyCategory) _spinnerSubCategory.getSelectedItem()).get_id();

        return null;
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
    protected MoneyTransactionSettings GetDataFromForm()
    {
        return new MoneyTransactionSettings(
                getTransactionType(),
                getCategoryType(),
                getOperationIsPlan(),
                getCategoryId(),
                getSubCategoryId(),
                getTagId());
    }
    //endregion
}
