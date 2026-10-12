package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

public class FragmentMoneyAccountingSettings extends CustomPopupBottomFragment<MoneyAccountingSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_accounting;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private CheckBox _checkBoxShowBudget;
    private ImageView _imageViewMascotToSettings;
    //endregion

    //region Start Fragment
    public FragmentMoneyAccountingSettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyAccounting,
                R.string.SettingsMoneyAccounting,
                0);
    }

    public FragmentMoneyAccountingSettings(AppCompatActivity activity,
                                           MoneyAccountingSettings moneyAccountingSettings,
                                           IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyAccountingSettings,
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
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewMascotToSettings.setOnClickListener(view ->
                new FragmentMoneyAccountingLegendToSettingsPopup((AppCompatActivity) getContext()).PopupShow());

    }

    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _checkBoxShowBudget = view.findViewById(R.id.checkBoxShowBudget);
        _imageViewMascotToSettings = view.findViewById(R.id.imageViewMascotToSettings);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
            _checkBoxShowBudget.setEnabled(false);

        if (!global.get_showMascot())
            _imageViewMascotToSettings.setVisibility(View.INVISIBLE);
    }
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        set_showBudget(_data.get_showBudget());
    }

    private void set_showBudget(boolean b)
    {
        _checkBoxShowBudget.setChecked(b);
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
    private boolean getShowBudget()
    {
        return _checkBoxShowBudget.isChecked();
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyAccountingSettings GetDataFromForm()
    {
        return new MoneyAccountingSettings(getShowBudget());
    }
    //endregion
}
