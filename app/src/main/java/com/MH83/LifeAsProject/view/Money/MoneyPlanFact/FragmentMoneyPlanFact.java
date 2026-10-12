package com.MH83.LifeAsProject.view.Money.MoneyPlanFact;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyPlanFactAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerPeriodAdapter;
import com.MH83.LifeAsProject.custom.CustomDateCheck;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.data.Money.MoneyPlanDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentDohodRashod;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowBlankData;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowPercent;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFact;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccountingLegendToAddPopup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentMoneyPlanFact extends CustomFragment
        implements IFragmentViewCustom,
        IFragmentExpandAll, IFragmentCollapseAll,
        IFragmentLegend, IFragmentDohodRashod,
        IFragmentSettings, IFragmentShowPercent,
        IFragmentShowBlankData,
        IFragmentMascotToAdd
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_planfact;
    //endregion

    //region Fragment parameters
    private CustomPeriodClass _customPeriodClass;
    private CustomPeriodClass _prevCustomPeriodClass;
    //endregion

    //region Fragment Components
    TreeView treeViewData;
    Spinner spinnerDate;
    ImageView imageViewDateBack;
    ImageView imageViewDateForward;
    ImageView imageViewSelectIncrease;
    private List<TextView> textViewResult;
    private SeekBar seekBarData;
    private ConstraintLayout constraintResultLayout1, constraintResultLayout2, constraintResultLayout3;
    //endregion

    //region Constructors
    public FragmentMoneyPlanFact()
    {
//        _customPeriodClass = new CustomPeriodClass(CustomPeriodClass.MONTH_ID, "");
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _customPeriodClass = globalVariable.get_moneyPlanFactSettings().get_customPeriodClass();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        GlobalClass global = (GlobalClass) container.getContext().getApplicationContext();

        View view = inflater.inflate(_layoutId, container, false);

        FindComponents(view);
        if (global.get_hasUnpaidSubscription())
        {
            spinnerDate.setEnabled(false);
        }

        SetComponentSettings(view);
        FillSpinner();
        SetListeners();
        ShowLegend();

        return view;
    }

    private void FillSpinner()
    {
        if (_customPeriodClass == null)
            return;

        ArrayList<CustomPeriodClass> list = CustomPeriodClass.GetList345(getContext());
        SpinnerPeriodAdapter adapter = new SpinnerPeriodAdapter(getContext(), list);
        spinnerDate.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).get_id() == _customPeriodClass.get_id())
            {
                spinnerDate.setSelection(i);
                ((CustomPeriodClass) spinnerDate.getSelectedItem())
                        .SetShift(_customPeriodClass.GetShift());
                break;
            }

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (_prevCustomPeriodClass != null)
                    _prevCustomPeriodClass.ResetShift();
                _prevCustomPeriodClass = ((CustomPeriodClass) spinnerDate.getSelectedItem());
                _customPeriodClass = ((CustomPeriodClass) spinnerDate.getSelectedItem());
                FillData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }
    private void FillData()
    {
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
        gVar.get_moneyPlanFactSettings().set_customPeriodClass(_customPeriodClass);

        Integer typeData = null;
        if (gVar.get_typeDevice() == EnumTypeDevice.Phone &&
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            typeData = seekBarData.getProgress();
        MoneyPlanFactAdapter adapter = new MoneyPlanFactAdapter(
                getContext(),
                this,
                gVar.get_moneyPlanFactSettings(),
                typeData);
        SetHeadComponentsSize(typeData);

        treeViewData.setAdapter(adapter);
        FillResult(typeData);
    }

    private void SetHeadComponentsSize(Integer typeData)
    {
        if (typeData != null)
        {
            imageViewSelectIncrease.setVisibility(typeData == 1 ? View.VISIBLE : View.INVISIBLE);

            LinearLayout.LayoutParams params1, params2, params3;
            params1 = (LinearLayout.LayoutParams) constraintResultLayout1.getLayoutParams();
            params2 = (LinearLayout.LayoutParams) constraintResultLayout2.getLayoutParams();
            params3 = (LinearLayout.LayoutParams) constraintResultLayout3.getLayoutParams();

            params1.weight = 0;
            params2.weight = 1;
            params3.weight = 1;

            params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params2.width = 0;
            params3.width = 0;

            constraintResultLayout1.setLayoutParams(params1);
            constraintResultLayout2.setLayoutParams(params2);
            constraintResultLayout3.setLayoutParams(params3);
        }
    }

    private void FindComponents(View view)
    {
        spinnerDate = view.findViewById(R.id.spinnerDate);
        imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        imageViewDateForward = view.findViewById(R.id.imageViewDateForward);
        imageViewSelectIncrease = view.findViewById(R.id.imageViewSelectIncrease);

        textViewResult = new ArrayList<>();
        textViewResult.add(view.findViewById(R.id.textView0Result));
        textViewResult.add(view.findViewById(R.id.textView1Result));
        textViewResult.add(view.findViewById(R.id.textView2Result));
        textViewResult.add(view.findViewById(R.id.textView3Result));

        treeViewData = view.findViewById(R.id.treeViewData);
        seekBarData = view.findViewById(R.id.seekBarData);

        constraintResultLayout1 = view.findViewById(R.id.constraintResultLayout1);
        constraintResultLayout2 = view.findViewById(R.id.constraintResultLayout2);
        constraintResultLayout3 = view.findViewById(R.id.constraintResultLayout3);
    }

    private void SetComponentSettings(View view)
    {
        Context context = view.getContext().getApplicationContext();
        GlobalClass globalVar = (GlobalClass) context;

        if (globalVar.get_typeDevice() == EnumTypeDevice.Tablet ||
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            seekBarData.getLayoutParams().height = 0;
            seekBarData.setVisibility(View.INVISIBLE);
        }
    }

    private void FillResult(Integer typeData)
    {
        for (int i = 1; i <= 3; i++)
            FillResultOnce(i, typeData, true);
        for (int i = 1; i <= 3; i++)
            if (typeData == null || typeData == i)
                FillResultOnce(i, typeData, false);
    }

    public void FillResultOnce(int i, Integer typeData, boolean clear)
    {
        if (!clear)
        {
            if (treeViewData.getAdapter() != null)
            {
                String sSum;

                sSum = CustomStaticClass.getIntRubleSum(
                            ((MoneyPlanFactAdapter) treeViewData.getAdapter()).getSum(i).intValue(), true);

                if (typeData == null)
                    textViewResult.get(i).setText(sSum);
                else
                    textViewResult.get(1).setText(sSum);
            }
            else
            {
                if (typeData == null)
                    textViewResult.get(i).setText("");
                else
                    textViewResult.get(1).setText("");
            }
        }
        else
        {
            if (typeData == null)
                textViewResult.get(i).setText("");
            else
                textViewResult.get(1).setText("");
        }
    }

    private void SetListeners()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        imageViewDateBack.setOnClickListener(view ->
        {
            if (!CustomDateCheck.CheckPrevDate(globalVariable,
                    ((CustomPeriodClass) spinnerDate.getSelectedItem())))
                return;

            ((CustomPeriodClass) spinnerDate.getSelectedItem()).DecShift();
            _customPeriodClass = (CustomPeriodClass) spinnerDate.getSelectedItem();
            ((SpinnerPeriodAdapter) spinnerDate.getAdapter()).notifyDataSetChanged();
            FillData();
        });

        imageViewDateForward.setOnClickListener(view ->
        {
            if (!CustomDateCheck.CheckNextDate(globalVariable,
                    ((CustomPeriodClass) spinnerDate.getSelectedItem())))
                return;

            ((CustomPeriodClass) spinnerDate.getSelectedItem()).IncShift();
            _customPeriodClass = (CustomPeriodClass) spinnerDate.getSelectedItem();
            ((SpinnerPeriodAdapter) spinnerDate.getAdapter()).notifyDataSetChanged();
            FillData();
        });

        if (imageViewSelectIncrease != null)
            imageViewSelectIncrease.setOnClickListener(view ->
            {
                if (globalVariable.get_hasUnpaidSubscription())
                    return;

                if (_customPeriodClass.get_id() != CustomPeriodClass.MONTH_ID)
                    return;

                new FragmentIncreaseSelectPopupNew((AppCompatActivity) getActivity(),
                        null,
                        EnumTypeAction.Select,
                        this).PopupShow();
            });

        seekBarData.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (treeViewData != null)
                {
                    MoneyPlanFactAdapter adapter = (MoneyPlanFactAdapter) treeViewData.getAdapter();
                    if (adapter != null)
                        adapter.set_dataType(i);
                    SetHeadComponentsSize(i);
                    FillResult(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        textViewResult.get(2).setOnClickListener(view ->
        {
            globalVariable.set_pnl_customPeriodClass(_customPeriodClass);
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_accounting);
        });
    }

    public void NavigateToPNL(Long categoryId, Long subCategoryId)
    {
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
        gVar.set_pnl_customPeriodClass(_customPeriodClass);


        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_moneyAccountingSettings_fromItem(R.id.navigation_money_accounting);
        globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.PNL).set_categoryId(categoryId);
        globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.PNL).set_subCategoryId(subCategoryId);
        globalVariable.set_pnl_customPeriodClass(_customPeriodClass);
        globalVariable.set_pnl_scrollPosition(0);
        globalVariable.set_pnl_transactionType(EnumTransactionType.PNL);

        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_pnl);
    }

    private void ShowLegend()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyPlanDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyPlanFact"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyPlanFact");
        }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyPlanFact)
        {
            MoneyPlanFactAdapter adapter = (MoneyPlanFactAdapter) treeViewData.getAdapter();
            if (adapter != null)
                adapter.AddItem((MoneyPlanFact) object);

            return true;
        }

        if (object instanceof MoneyPlanFactSettings)
        {
            MoneyPlanFactSettings moneyPlanFactSettings = (MoneyPlanFactSettings) object;
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_moneyPlanFactSettings(moneyPlanFactSettings);

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyPlanFact)
        {
            MoneyPlanFactAdapter adapter = (MoneyPlanFactAdapter) treeViewData.getAdapter();
            if (adapter != null)
                adapter.UpdateItem((MoneyPlanFact) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyPlanFact)
        {
            MoneyPlanFactAdapter adapter = (MoneyPlanFactAdapter) treeViewData.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset((MoneyPlanFact) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object)
    {
        if (object instanceof MoneyIncrease)
        {
            MoneyIncrease moneyIncrease = (MoneyIncrease) object;
            GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
            MoneyPlanFactSettings moneyPlanFactSettings = gVar.get_moneyPlanFactSettings();
            boolean b = MoneyPlanDataSource.CopyMoneyPlansFromIncoming(getContext(),
                    moneyPlanFactSettings.get_typeCategory(),
                    _customPeriodClass.get_start_dateC().get(Calendar.MONTH),
                    _customPeriodClass.get_start_dateC().get(Calendar.YEAR),
                    moneyIncrease);

            if (b)
                FillData();

            return b;
        }

        return false;
    }
    //endregion

    //region IFragmentLegend
    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentMoneyPlanFactLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region IFragmentExpandAll
    public boolean needExpandButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyPlanFactSettings().get_showExpandButton();
    }

    @Override
    public View.OnClickListener onClickExpandAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyPlanFactSettings().set_showExpandButton(false);
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            IExpandAllAdapter adapter = ((IExpandAllAdapter) treeViewData.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(false);
        };
    }
    //endregion

    //region IFragmentCollapseAll
    public boolean needCollapseButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return !globalVar.get_moneyPlanFactSettings().get_showExpandButton();
    }

    @Override
    public View.OnClickListener onClickCollapseAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyPlanFactSettings().set_showExpandButton(true);
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) treeViewData.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
        };
    }
    //endregion

    //region IFragmentDohodRashod
    @Override
    public View.OnClickListener onClickToSetDohod()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_enumMoneyPlanFactTypeCategory(EnumTypeCategory.Increase);

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
            ((MainActivity) getActivity()).SetTitle(R.id.navigation_money_planfact_income);
        };
    }

    @Override
    public View.OnClickListener onClickToSetRashod()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_enumMoneyPlanFactTypeCategory(EnumTypeCategory.Cost);

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
            ((MainActivity) getActivity()).SetTitle(R.id.navigation_money_planfact_cost);
        };
    }

    @Override
    public EnumTypeCategory GetTypeCategory(Context context)
    {
        GlobalClass gVar = (GlobalClass) context.getApplicationContext();
        return gVar.get_enumMoneyPlanFactTypeCategory();
    }
    //endregion

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
            new FragmentMoneyPlanFactSettings((AppCompatActivity) getActivity(),
                    gVar.get_moneyPlanFactSettings(),
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton()
    {
        return true;
    }
    //endregion

    //region IFragmentShowPercent
    @Override
    public boolean ShowPercent(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (global.get_hasUnpaidSubscription())
            return false;

        return !global.get_moneyPlanFactSettings().get_showPercent();
    }

    @Override
    public boolean HidePercent(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (global.get_hasUnpaidSubscription())
            return false;

        return global.get_moneyPlanFactSettings().get_showPercent();
    }

    @Override
    public View.OnClickListener onClickToShowPercent()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyPlanFactSettings().set_showPercent(true);
            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    @Override
    public View.OnClickListener onClickToHidePercent()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyPlanFactSettings().set_showPercent(false);
            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowBlankData
    @Override
    public boolean ShowBlankData(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_hasUnpaidSubscription())
            return false;

        return !global.get_moneyPlanFactSettings().get_showBlankData();
    }

    @Override
    public boolean HideBlankData(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_hasUnpaidSubscription())
            return false;

        return global.get_moneyPlanFactSettings().get_showBlankData();
    }

    @Override
    public View.OnClickListener onClickToShowBlankData()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyPlanFactSettings().set_showBlankData(true);
            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    @Override
    public View.OnClickListener onClickToHideBlankData()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyPlanFactSettings().set_showBlankData(false);
            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentMascotToAdd
    public boolean ShowMascotButtonToAdd(Context context)
    {
        GlobalClass gV = (GlobalClass) context.getApplicationContext();
        return gV.get_showMascot();
    }

    public View.OnClickListener onClickToMascotToAdd()
    {
        return view ->
                new FragmentMoneyPlanFactLegendToAddPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion
}