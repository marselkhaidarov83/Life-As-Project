package com.MH83.LifeAsProject.view.Money.MoneyPlanFact;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyIncreaseAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyIncreaseDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyIncreaseSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import java.util.List;

public class FragmentIncreaseSelectPopupNew extends CustomPopupBottomFragment<MoneyIncrease>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_increase_select;
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    private Spinner _spinnerIncrease;
    private Button buttonSelect;
    //endregion

    //region Constructors
    public FragmentIncreaseSelectPopupNew()
    {
        super(_layoutId,
                R.string.fragmentIncreaseSelectPopupAddTitle,
                R.string.fragmentIncreaseSelectUpdateTitle,
                R.string.errorUpdateMoneyPlan);
    }
    public FragmentIncreaseSelectPopupNew(AppCompatActivity activity,
                                          MoneyIncrease data,
                                          EnumTypeAction typeAction,
                                          IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                R.string.fragmentIncreaseSelectPopupAddTitle,
                R.string.fragmentIncreaseSelectUpdateTitle,
                R.string.errorUpdateMoneyPlan,
                iFragmentViewCustom,
                null,
                null);
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerIncrease = view.findViewById(R.id.spinnerIncrease);
        buttonSelect = view.findViewById(R.id.buttonSelect);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _spinnerIncrease;
        _focusedViewToUpdate = _spinnerIncrease;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        MoneyIncreaseSettings moneyIncreaseSettings = new MoneyIncreaseSettings(false);
        List<MoneyIncrease> list = MoneyIncreaseDataSource.LoadPlanDataWithPercent(
                getContext(), moneyIncreaseSettings);

        SpinnerMoneyIncreaseAdapter moneyIncreaseAdapter = new SpinnerMoneyIncreaseAdapter(getContext(), list);
        _spinnerIncrease.setAdapter(moneyIncreaseAdapter);
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues();
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

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if ((getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ||
                globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_IncomingAndCost_Width) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
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

        buttonSelect.setOnClickListener(view -> SelectItem());
    }

    //endregion

    //region Set Params
    protected void SetValues()
    {
        if (_data != null)
            set_increase(_data);
    }
    private void set_increase(MoneyIncrease moneyIncrease)
    {
        for (int i = 0; i < _spinnerIncrease.getAdapter().getCount(); i++)
            if (_spinnerIncrease.getAdapter().getItem(i).equals(moneyIncrease))
            {
                _spinnerIncrease.setSelection(i);
                break;
            }
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    @Override
    protected boolean CheckFormValues()
    {
        return true;
    }

    protected boolean CheckFormValuesForDelete() { return true; }
    //endregion

    //region Get Params
    private MoneyIncrease get_moneyIncrease()
    {
        return (MoneyIncrease) _spinnerIncrease.getSelectedItem();
    }
    //endregion

    //region Save Params
    @Override
    protected MoneyIncrease GetDataFromForm()
    {
        try
        {
            return get_moneyIncrease();
        }
        catch (Exception e) { return null; }
    }
    //endregion
}