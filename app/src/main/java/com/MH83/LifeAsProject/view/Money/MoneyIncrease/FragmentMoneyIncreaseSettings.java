package com.MH83.LifeAsProject.view.Money.MoneyIncrease;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyIncreaseSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

public class FragmentMoneyIncreaseSettings extends CustomPopupBottomFragment<MoneyIncreaseSettings>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_increase;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private CheckBox _checkBoxShowArchive;
    //endregion

    //region Start Fragment
    public FragmentMoneyIncreaseSettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyIncrease,
                R.string.SettingsMoneyIncrease,
                0);
    }

    public FragmentMoneyIncreaseSettings(AppCompatActivity activity,
                                         MoneyIncreaseSettings moneyIncreaseSettings,
                                         IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyIncreaseSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyIncrease,
                R.string.SettingsMoneyIncrease,
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
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
            _checkBoxShowArchive.setEnabled(false);
    }
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        set_showArchive(_data.get_showArchive());
    }

    private void set_showArchive(boolean b)
    {
        _checkBoxShowArchive.setChecked(b);
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
    private boolean getShowArchive()
    {
        return _checkBoxShowArchive.isChecked();
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyIncreaseSettings GetDataFromForm()
    {
        return new MoneyIncreaseSettings(getShowArchive());
    }
    //endregion
}
