package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.MH83.LifeAsProject.custom.CustomDateCheck;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCredit;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyCostSumAdapter;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyIncomingSumAdapter;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyWalletSumAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerPeriodAdapter;
import com.MH83.LifeAsProject.custom.CustomRecycleView;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.MoneyIncomingSumTrio;
import com.MH83.LifeAsProject.data.Money.MoneyAccountingDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentMoneyAccounting extends CustomFragment implements IFragmentViewCustom,
        IFragmentLegend, IFragmentCredit, IFragmentSettings, IFragmentMascotToAdd
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_accounting;
    private static final int _item_layoutId = R.layout.item_money_accounting;
    //endregion

    //region Fragment Components
    RecyclerView rViewIncoming, rViewWallet;
    CustomRecycleView rViewCost;
    TextView textViewIncomingSumCaption, textViewIncomingSum, textViewWalletSum, textViewCostSum;
    private ImageView _imageViewDateBack, _imageViewDateForward;
    private Spinner _spinnerDate;
    //endregion

    //region Fragment settings
    private CustomPeriodClass _customPeriodClass;
    private CustomPeriodClass _prevCustomPeriodClass;
    MoneyIncomingSumTrio _moneyIncomingSumTrio;
    private Long _categoryId;
    private Calendar _dateStart;
    private Calendar _dateEnd;
    private Long _sumIncomingLong;
    private Long _sumWalletLong;
    private Long _sumCostLong;
    public Long getCategoryId() { return _categoryId; }
    public EnumTypeCategory getTypeCategory()
    {
        return EnumTypeCategory.Wallet;
    }
    //endregion

    //region Constructors
    public FragmentMoneyAccounting()
    {
        super();

        _moneyIncomingSumTrio = new MoneyIncomingSumTrio();
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        _customPeriodClass = globalVariable.get_pnl_customPeriodClass();
        if (_customPeriodClass == null)
            _customPeriodClass = new CustomPeriodClass(3, "");

        StopNotification();
    }

    private void StopNotification()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifMoneySettings.NotifIdNoEnteredCostPerDay);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        FindComponents(view);

        SetComponentSettings();
        SetListeners();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyPNLDataSource.GetCount(getContext()) == 0 &&
            !globalVariable.get_legendShowed().equals("FragmentMoneyAccounting"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyAccounting");
        }

        return view;
    }

    private void FindComponents(View view)
    {
        rViewIncoming = view.findViewById(R.id.recycleViewIncoming);
        rViewWallet = view.findViewById(R.id.recycleViewWallet);
        rViewCost = view.findViewById(R.id.recycleViewCost);

        textViewIncomingSumCaption = view.findViewById(R.id.textViewIncomingSumCaption);
        textViewIncomingSum = view.findViewById(R.id.textViewIncomingSum);
        textViewWalletSum = view.findViewById(R.id.textViewWalletSum);
        textViewCostSum = view.findViewById(R.id.textViewCostSum);
        _spinnerDate = view.findViewById(R.id.spinnerDate);
        _imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        _imageViewDateForward = view.findViewById(R.id.imageViewDateForward);
    }

    private void SetComponentSettings()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
        {
            _spinnerDate.setEnabled(false);
        }
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void SetListeners()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        _imageViewDateBack.setOnClickListener(view ->
        {
            if (!CustomDateCheck.CheckPrevDate(globalVariable,
                    ((CustomPeriodClass) _spinnerDate.getSelectedItem())))
                return;

//            if (globalVariable.get_hasUnpaidSubscription())
//            {
//                Calendar calendarPrevMonthBegin = Calendar.getInstance();
//                calendarPrevMonthBegin.add(Calendar.DATE, -3);
//                calendarPrevMonthBegin.set(Calendar.DATE, 1);
//                calendarPrevMonthBegin.set(Calendar.HOUR, 0);
//                calendarPrevMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarPrevMonthBegin.set(Calendar.MINUTE, 0);
//                calendarPrevMonthBegin.set(Calendar.SECOND, 0);
//                calendarPrevMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                Calendar calendarThisMonthBegin = Calendar.getInstance();
//                calendarThisMonthBegin.set(Calendar.DATE, 1);
//                calendarThisMonthBegin.set(Calendar.HOUR, 0);
//                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
//                calendarThisMonthBegin.set(Calendar.SECOND, 0);
//                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
//                Calendar calendar = ((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_start_dateC();
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
//
//                if (calendar.getTime().getTime() != calendarPrevMonthBegin.getTime().getTime() &&
//                    calendar.getTime().getTime() != calendarThisMonthBegin.getTime().getTime())
//                    return;
//            }

            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            globalVariable.set_pnl_customPeriodClass((CustomPeriodClass) _spinnerDate.getSelectedItem());
            FillFormAll();
        });

        _imageViewDateForward.setOnClickListener(view ->
        {
            if (!CustomDateCheck.CheckNextDate(globalVariable,
                    ((CustomPeriodClass) _spinnerDate.getSelectedItem())))
                return;

//            if (globalVariable.get_hasUnpaidSubscription())
//            {
//                Calendar calendarPrevMonthBegin = Calendar.getInstance();
//                calendarPrevMonthBegin.add(Calendar.DATE, -3);
//                calendarPrevMonthBegin.set(Calendar.DATE, 1);
//                calendarPrevMonthBegin.set(Calendar.HOUR, 0);
//                calendarPrevMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarPrevMonthBegin.set(Calendar.MINUTE, 0);
//                calendarPrevMonthBegin.set(Calendar.SECOND, 0);
//                calendarPrevMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                Calendar calendarThisMonthBegin = Calendar.getInstance();
//                calendarThisMonthBegin.set(Calendar.DATE, 1);
//                calendarThisMonthBegin.set(Calendar.HOUR, 0);
//                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
//                calendarThisMonthBegin.set(Calendar.SECOND, 0);
//                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
//                Calendar calendar = ((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_start_dateC();
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
//
//                if (calendar.getTime().getTime() != calendarPrevMonthBegin.getTime().getTime() &&
//                        calendar.getTime().getTime() != calendarThisMonthBegin.getTime().getTime())
//                    return;
//            }

            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            globalVariable.set_pnl_customPeriodClass((CustomPeriodClass) _spinnerDate.getSelectedItem());
            FillFormAll();
        });

        textViewIncomingSumCaption.setOnClickListener(view ->
        {
            if (globalVariable.get_hasUnpaidSubscription())
                return;

            if (rViewIncoming.getVisibility() == View.VISIBLE)
            {
                rViewIncoming.setVisibility(View.INVISIBLE);
                rViewIncoming.getLayoutParams().height = 0;
                rViewIncoming.requestLayout();

                textViewIncomingSumCaption.setText(getResources().getString(R.string.money_incoming) + " +");
            }
            else
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                rViewIncoming.setVisibility(View.VISIBLE);
                rViewIncoming.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rViewIncoming.getAdapter().notifyDataSetChanged();

                textViewIncomingSumCaption.setText(R.string.money_incoming);
            }
        });

        textViewIncomingSum.setOnClickListener(view ->
        {
            Calendar calendar = ((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_start_dateC();
            if (calendar == null)
                calendar = Calendar.getInstance();

            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingFact);
            int quarter = Math.floorDiv(calendar.get(Calendar.MONTH), 3) + 1;
            switch (((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_id())
            {
                case 2:
                    int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Week);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR)
                                    + 1000000L * quarter
                                    + 10000L * (calendar.get(Calendar.MONTH) + 1)
                                    + 100L * weekNumber);
                    break;
                case 4:
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Quarter);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR)
                                    + 1000000L * quarter);
                    break;
                case 6:
                    calendar = Calendar.getInstance();
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Year);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR));
                case 5:
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Year);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR));
                    break;
                case 1:
                case 3:
                default:
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Month);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR)
                                    + 1000000L * quarter
                                    + 10000L * (calendar.get(Calendar.MONTH) + 1));
            }

            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_incoming_fact);
        });

        textViewIncomingSum.setOnLongClickListener(view ->
        {
            _customPeriodClass = ((CustomPeriodClass) _spinnerDate.getSelectedItem());
            if (_customPeriodClass.get_id() != CustomPeriodClass.MONTH_ID &&
                    _customPeriodClass.get_id() != CustomPeriodClass.QUARTER_ID &&
                    _customPeriodClass.get_id() != CustomPeriodClass.YEAR_ID)
                return false;

            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.set_enumMoneyPlanFactTypeCategory(EnumTypeCategory.Increase);
            globalVar.get_moneyPlanFactSettings().set_customPeriodClass(_customPeriodClass);

            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_planfact_income);

            return true;
        });

        textViewCostSum.setOnClickListener(view ->
        {
            Calendar calendar = ((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_start_dateC();
            if (calendar == null)
                calendar = Calendar.getInstance();

            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpenseFact);
            int quarter = Math.floorDiv(calendar.get(Calendar.MONTH), 3) + 1;
            switch (((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_id())
            {
                case 2:
                    int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Week);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR)
                                    + 1000000L * quarter
                                    + 10000L * (calendar.get(Calendar.MONTH) + 1)
                                    + 100L * weekNumber);
                    break;
                case 4:
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Quarter);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR)
                                    + 1000000L * quarter);
                    break;
                case 6:
                    calendar = Calendar.getInstance();
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Year);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR));
                    break;
                case 5:
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Year);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR));
                    break;
                case 1:
                case 3:
                default:
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_periodData(EnumPeriodData.Month);
                    globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(
                            10000000L * calendar.get(Calendar.YEAR)
                                    + 1000000L * quarter
                                    + 10000L * (calendar.get(Calendar.MONTH) + 1));
            }

            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_costs_fact);
        });

        textViewCostSum.setOnLongClickListener(view ->
        {
            _customPeriodClass = ((CustomPeriodClass) _spinnerDate.getSelectedItem());
            if (_customPeriodClass.get_id() != CustomPeriodClass.MONTH_ID &&
                    _customPeriodClass.get_id() != CustomPeriodClass.QUARTER_ID &&
                    _customPeriodClass.get_id() != CustomPeriodClass.YEAR_ID)
                return false;

            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.set_enumMoneyPlanFactTypeCategory(EnumTypeCategory.Cost);
            globalVar.get_moneyPlanFactSettings().set_customPeriodClass(_customPeriodClass);

            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_planfact_cost);

            return true;
        });

        FillSpinner();
    }

    private void FillFormAll()
    {
        CustomPeriodClass customPeriodClass = (CustomPeriodClass) _spinnerDate.getSelectedItem();
        _dateStart = customPeriodClass.get_start_dateC();
        _dateEnd = customPeriodClass.get_end_dateC();

        FillRViewIncoming();
        FillRViewWallet();
        FillRViewCost();

        SetSumIncoming();
        SetSumWallet();
        SetSumCost();
    }

    private void FillSpinner()
    {
        if (_customPeriodClass == null)
            return;

        ArrayList<CustomPeriodClass> list = CustomPeriodClass.GetList(getContext());
        SpinnerPeriodAdapter adapter = new SpinnerPeriodAdapter(getContext(), list);
        _spinnerDate.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).get_id() == _customPeriodClass.get_id())
            {
                _spinnerDate.setSelection(i);
                ((CustomPeriodClass) _spinnerDate.getSelectedItem())
                            .SetShift(_customPeriodClass.GetShift());
                break;
            }

        _spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (_prevCustomPeriodClass != null)
                    _prevCustomPeriodClass.ResetShift();
                _prevCustomPeriodClass = ((CustomPeriodClass) _spinnerDate.getSelectedItem());
                GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
                globalVariable.set_pnl_customPeriodClass((CustomPeriodClass) _spinnerDate.getSelectedItem());
                FillFormAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void SetRViewSettings(RecyclerView rView)
    {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false);
        rView.setLayoutManager(manager);
    }

    private void FillRViewIncoming()
    {
        ArrayList<MoneyAccountingSum> dataSet = MoneyAccountingDataSource.LoadDataIncoming(
                getContext(), _dateStart, _dateEnd);

        MoneyIncomingSumAdapter adapter = new MoneyIncomingSumAdapter(getContext(),
                            dataSet, this, _moneyIncomingSumTrio,
                            _dateStart, _dateEnd);
        rViewIncoming.setAdapter(adapter);

        SetRViewSettings(rViewIncoming);
        _sumIncomingLong = ((MoneyIncomingSumAdapter)rViewIncoming.getAdapter()).GetSumLong();
    }

    private void FillRViewWallet()
    {
        ArrayList<MoneyAccountingSum> dataSet = MoneyAccountingDataSource.LoadDataWallet(
                getContext(), _dateStart, _dateEnd);

        MoneyWalletSumAdapter adapter = new MoneyWalletSumAdapter(getContext(),
                dataSet, this, _moneyIncomingSumTrio,
                _dateStart, _dateEnd);
        rViewWallet.setAdapter(adapter);

        SetRViewSettings(rViewWallet);
        _sumWalletLong = ((MoneyWalletSumAdapter) rViewWallet.getAdapter()).GetSumLong();
    }

    private void FillRViewCost()
    {
        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        ArrayList<MoneyAccountingSum> dataSet = MoneyAccountingDataSource.LoadDataCost(
                getContext(), _dateStart, _dateEnd);

        MoneyCostSumAdapter adapter = new MoneyCostSumAdapter(getContext(),
                dataSet, this, _moneyIncomingSumTrio,
                _dateStart, _dateEnd,
                globalVar.get_moneyAccountingSettings());
        rViewCost.setAdapter(adapter);

        rViewCost.setGridLayoutManager(GridLayoutManager.VERTICAL, _item_layoutId, 3);
        _sumCostLong = ((MoneyCostSumAdapter)rViewCost.getAdapter()).GetSumLong();
    }

    @SuppressLint("SetTextI18n")
    private void SetSumIncoming()
    {
        textViewIncomingSum.setText(CustomStaticClass.getRubleSum(_sumIncomingLong / 100d));
    }

    @SuppressLint("SetTextI18n")
    private void SetSumWallet()
    {
        textViewWalletSum.setText(CustomStaticClass.getRubleSum(_sumWalletLong / 100d));
    }

    @SuppressLint("SetTextI18n")
    private void SetSumCost()
    {
        textViewCostSum.setText(CustomStaticClass.getRubleSum(_sumCostLong / 100d));
    }
    //endregion

    //region Click to button
    public void GoToFragmentMoneyPNL(Long categoryId,
                                     MoneyAccountingSum moneyAccountingSumCost,
                                     MoneyAccountingSum moneyAccountingSumIncrease)
    {
        _categoryId = categoryId;

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_moneyAccountingSettings_fromItem(R.id.navigation_money_accounting);

        globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.PNL).set_isPlan(null);
        globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.PNL).set_categoryId(categoryId);
        globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.PNL).set_subCategoryId(null);
        globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.PNL).set_tagId(null);
        globalVariable.set_pnl_moneyAccountingSumCost(moneyAccountingSumCost);
        globalVariable.set_pnl_moneyAccountingSumIncrease(moneyAccountingSumIncrease);
        globalVariable.set_pnl_customPeriodClass((CustomPeriodClass) _spinnerDate.getSelectedItem());
        globalVariable.set_pnl_scrollPosition(0);
        globalVariable.set_pnl_transactionType(EnumTransactionType.PNL);

        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_pnl);
    }

    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyPNLIndependent)
        {
            MoneyPNLIndependent moneyPNL = (MoneyPNLIndependent) object;
            if (!moneyPNL.get_isPlan())
            {
                CustomPeriodClass customPeriodClass =
                        (CustomPeriodClass) _spinnerDate.getSelectedItem();

                ((MoneyWalletSumAdapter) rViewWallet.getAdapter()).AddSubItem(moneyPNL);
                if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                {
                    _sumWalletLong += moneyPNL.get_sumLong();
                    SetSumWallet();
                    ((MoneyCostSumAdapter) rViewCost.getAdapter()).ChangeBudget(moneyPNL);

                    if (moneyPNL.getDate().getTime() >= customPeriodClass.get_start_dateC().getTime().getTime() &&
                            moneyPNL.getDate().getTime() <= customPeriodClass.get_end_dateC().getTime().getTime())
                    {
                        ((MoneyIncomingSumAdapter) rViewIncoming.getAdapter()).AddSubItem(moneyPNL);
                        _sumIncomingLong += moneyPNL.get_sumLong();
                        SetSumIncoming();
                    }
                }
                else if (moneyPNL.get_typeCategory() == EnumTypeCategory.Cost)
                {
                    _sumWalletLong -= moneyPNL.get_sumLong();
                    SetSumWallet();

                    if (moneyPNL.getDate().getTime() >= customPeriodClass.get_start_dateC().getTime().getTime() &&
                            moneyPNL.getDate().getTime() <= customPeriodClass.get_end_dateC().getTime().getTime())
                    {
                        ((MoneyCostSumAdapter) rViewCost.getAdapter()).AddSubItem(moneyPNL);
                        _sumCostLong += moneyPNL.get_sumLong();
                        SetSumCost();
                    }
                    else
                        ((MoneyCostSumAdapter) rViewCost.getAdapter()).ChangeBudget(moneyPNL);
                }
            }

            return true;
        }
        else if (object instanceof MoneyAccountingSettings)
        {
            MoneyAccountingSettings moneyAccountingSettings = (MoneyAccountingSettings) object;
            GlobalClass gV = (GlobalClass) getContext().getApplicationContext();
            if (!gV.get_hasUnpaidSubscription())
                gV.set_moneyAccountingSettings(moneyAccountingSettings);

            ((MoneyCostSumAdapter) rViewCost.getAdapter()).RefreshData(gV.get_moneyAccountingSettings());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object) { return false; }

    @Override
    public boolean refreshViewOnDelete(Object object) { return false; }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion

    //region IFragmentCredit
    @Override
    public View.OnClickListener onClickCredit()
    {
        return view ->
        {
            if (getActivity() != null)
                ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_credit);
        };
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
        new FragmentMoneyAccountingLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region IFragmentSetting
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyAccountingSettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyAccountingSettings().Clone(),
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton() { return true; }
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
                new FragmentMoneyAccountingLegendToAddPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion
}