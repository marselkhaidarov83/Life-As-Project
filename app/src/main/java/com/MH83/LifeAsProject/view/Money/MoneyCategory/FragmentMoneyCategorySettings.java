package com.MH83.LifeAsProject.view.Money.MoneyCategory;

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
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanFactLegendToSettingsPopup;

import java.util.ArrayList;
import java.util.List;

public class FragmentMoneyCategorySettings extends CustomPopupBottomFragment<MoneyCategorySettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_category;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private CheckBox _checkBoxShowBlankData;
    private CheckBox _checkBoxShowArchive;
    private Spinner _spinnerCategoryType;
    private ImageView _imageViewMascotToSettings;
    //endregion

    //region Start Fragment
    public FragmentMoneyCategorySettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyCategory,
                R.string.SettingsMoneyCategory,
                0);
    }

    public FragmentMoneyCategorySettings(AppCompatActivity activity,
                                         MoneyCategorySettings moneyCategorySettings,
                                         IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyCategorySettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyCategory,
                R.string.SettingsMoneyCategory,
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

        _checkBoxShowArchive = view.findViewById(R.id.checkBoxShowArchive);
        _checkBoxShowBlankData = view.findViewById(R.id.checkBoxShowBlankData);
        _imageViewMascotToSettings = view.findViewById(R.id.imageViewMascotToSettings);
        _spinnerCategoryType = view.findViewById(R.id.spinnerCategoryType);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            _checkBoxShowArchive.setEnabled(false);
            _checkBoxShowBlankData.setEnabled(false);
            _spinnerCategoryType.setEnabled(false);
        }

        if (!global.get_showMascot())
            _imageViewMascotToSettings.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetCategoryTypeAdapter();
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

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewMascotToSettings.setOnClickListener(view ->
                new FragmentMoneyCategoryLegendToSettingsPopup((AppCompatActivity) getContext()).PopupShow());
    }
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        set_showArchive(_data.get_showArchive());
        SetShowBlankData(_data.get_showBlankData());
        SetCategoryType(_data.get_categoryType());
    }
    private void set_showArchive(boolean b)
    {
        _checkBoxShowArchive.setChecked(b);
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
    //endregion

    //region Check Params
    @Override
    protected boolean CheckFormValues()
    {
        return true;
    }
    //endregion

    //region Get Params
    private EnumTypeCategory get_typeCategory()
    {
        return _data.get_typeCategory();
    }
    private boolean get_showBlankData()
    {
        return _checkBoxShowBlankData.isChecked();
    }
    private List<Long> get_expandedList()
    {
        return _data.get_expandedList();
    }
    private boolean getShowArchive()
    {
        return _checkBoxShowArchive.isChecked();
    }
    private boolean get_showExpandButton()
    {
        return _data.get_showExpandButton();
    }
    private EnumCategoryType getCategoryType()
    {
        if (_spinnerCategoryType.getAdapter().getCount() > 0)
            return (EnumCategoryType) _spinnerCategoryType.getSelectedItem();

        return null;
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyCategorySettings GetDataFromForm()
    {
        return new MoneyCategorySettings(
                get_typeCategory(),
                get_showBlankData(),
                getShowArchive(),
                get_showExpandButton(),
                getCategoryType(),
                get_expandedList());
    }
    //endregion
}
