package com.MH83.LifeAsProject.view.Main.MainSettings;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerDatabaseAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyPortfolioAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyPortfolioDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumDatabase;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolio;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolioSettings;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;
import com.MH83.LifeAsProject.model.Settings.NotifProjectSettings;
import com.MH83.LifeAsProject.model.Settings.NotifSettings;
import com.MH83.LifeAsProject.model.Settings.NotifTasksSettings;

import java.util.ArrayList;

public class FragmentMainSettings extends Fragment
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_main_settings;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private LinearLayout linearLayoutMain,
            linearLayoutNotifications ,
            linearLayoutNotificationsProject,
            linearLayoutNotificationsMoney,
            linearLayoutNotificationsTask,
            linearLayoutCreditSettings;
    private TableRow tableRowNotifications,
            tableRowNotificationsProject,
            tableRowNotificationsMoney,
            tableRowNotificationsTask,
            tableRowCreditsSettings;
    private CheckBox checkboxNotifEnabled,
            checkboxNotifNeedDream,
            checkboxNotifNeedTarget,
            checkboxNotifNeedTaskInProject,
            checkboxNotifActualMonthPlanIncoming,
            checkboxNotifActualMonthPlanCost,
            checkboxNotifNoEnteredCostPerDay,
            checkboxNotifMoneyReportPrevMonth,
            checkboxNotifNoAccumulationThisMonth,
            checkboxNotifAccumulationDone,
            checkboxOverdueTasksInMorning,
            checkboxCountTasksForDay,
            checkboxNoTasksForDay,
            checkboxCountUnfinishedTasksInEvening,
            checkboxCountTasksInbox,
            checkBoxMascot;
    private ConstraintLayout constraintLayoutToMainForm,
            constraintLayoutToNotifFromNotifProject,
            constraintLayoutToNotifFromNotifMoney,
            constraintLayoutToNotifFromNotifTask,
            constraintLayoutToMainFormFromCreditSettings;
    private TextView textViewTimeNotifStart,
            textViewTimeNotifEnd;
    private ImageView imageViewTimeMinusNotifStart,
            imageViewTimePlusNotifStart,
            imageViewTimeMinusNotifEnd,
            imageViewTimePLusNotifEnd;

    private Spinner spinnerDatabase,
            spinnerGroupPortfolio,
            spinnerPortfolio;
    private Button buttonFreeLearnDatabase;
    //endregion

    //region Constructors
    public FragmentMainSettings()
    {
//        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        FindComponents(view);
        SetVisibility();
        SetAdapters();
        SetData(view.getContext());
        SetListeners();

        return view;
    }

    private void FindComponents(View view)
    {
        linearLayoutMain = view.findViewById(R.id.linearLayoutMain);
        linearLayoutNotifications = view.findViewById(R.id.linearLayoutNotifications);
        linearLayoutNotificationsProject = view.findViewById(R.id.linearLayoutNotificationsProject);
        linearLayoutNotificationsMoney = view.findViewById(R.id.linearLayoutNotificationsMoney);
        linearLayoutNotificationsTask = view.findViewById(R.id.linearLayoutNotificationsTask);
        linearLayoutCreditSettings = view.findViewById(R.id.linearLayoutCreditSettings);

        tableRowNotifications = view.findViewById(R.id.tableRowNotifications);
        tableRowNotificationsProject = view.findViewById(R.id.tableRowNotificationsProject);
        tableRowNotificationsMoney = view.findViewById(R.id.tableRowNotificationsMoney);
        tableRowNotificationsTask = view.findViewById(R.id.tableRowNotificationsTask);
        tableRowCreditsSettings = view.findViewById(R.id.tableRowCreditsSettings);

        checkboxNotifEnabled = view.findViewById(R.id.checkboxNotifEnabled);
        checkboxNotifNeedDream = view.findViewById(R.id.checkboxNotifNeedDream);
        checkboxNotifNeedTarget = view.findViewById(R.id.checkboxNotifNeedTarget);
        checkboxNotifNeedTaskInProject = view.findViewById(R.id.checkboxNotifNeedTaskInProject);
        checkboxNotifActualMonthPlanIncoming = view.findViewById(R.id.checkboxNotifActualMonthPlanIncoming);
        checkboxNotifActualMonthPlanCost = view.findViewById(R.id.checkboxNotifActualMonthPlanCost);
        checkboxNotifNoEnteredCostPerDay = view.findViewById(R.id.checkboxNotifNoEnteredCostPerDay);
        checkboxNotifMoneyReportPrevMonth = view.findViewById(R.id.checkboxNotifMoneyReportPrevMonth);
        checkboxNotifNoAccumulationThisMonth = view.findViewById(R.id.checkboxNotifNoAccumulationThisMonth);
        checkboxNotifAccumulationDone = view.findViewById(R.id.checkboxNotifAccumulationDone);
        checkboxOverdueTasksInMorning = view.findViewById(R.id.checkboxOverdueTasksInMorning);
        checkboxCountTasksForDay = view.findViewById(R.id.checkboxCountTasksForDay);
        checkboxNoTasksForDay = view.findViewById(R.id.checkboxNoTasksForDay);
        checkboxCountUnfinishedTasksInEvening = view.findViewById(R.id.checkboxCountUnfinishedTasksInEvening);
        checkboxCountTasksInbox = view.findViewById(R.id.checkboxCountTasksInbox);
        checkBoxMascot = view.findViewById(R.id.checkboxMascotEnabled);

        constraintLayoutToMainForm = view.findViewById(R.id.constraintLayoutToMainForm);
        constraintLayoutToNotifFromNotifProject = view.findViewById(R.id.constraintLayoutToNotifFromNotifProject);
        constraintLayoutToNotifFromNotifMoney = view.findViewById(R.id.constraintLayoutToNotifFromNotifMoney);
        constraintLayoutToNotifFromNotifTask = view.findViewById(R.id.constraintLayoutToNotifFromNotifTask);
        constraintLayoutToMainFormFromCreditSettings = view.findViewById(R.id.constraintLayoutToMainFormFromCreditSettings);

        textViewTimeNotifStart = view.findViewById(R.id.textViewTimeNotifStart);
        textViewTimeNotifEnd = view.findViewById(R.id.textViewTimeNotifEnd);

        imageViewTimeMinusNotifStart = view.findViewById(R.id.imageViewTimeMinusNotifStart);
        imageViewTimePlusNotifStart = view.findViewById(R.id.imageViewTimePlusNotifStart);
        imageViewTimeMinusNotifEnd = view.findViewById(R.id.imageViewTimeMinusNotifEnd);
        imageViewTimePLusNotifEnd = view.findViewById(R.id.imageViewTimePlusNotifEnd);

        spinnerDatabase = view.findViewById(R.id.spinnerDatabase);
        spinnerGroupPortfolio = view.findViewById(R.id.spinnerGroupPortfolio);
        spinnerPortfolio = view.findViewById(R.id.spinnerPortfolio);

        buttonFreeLearnDatabase = view.findViewById(R.id.buttonFreeLearnDatabase);
    }

    private void SetAdapters()
    {
        ArrayList<EnumDatabase> list = new ArrayList<>();
        list.add(EnumDatabase.Work);
        list.add(EnumDatabase.Learn);
        spinnerDatabase.setAdapter(new SpinnerDatabaseAdapter(getContext(), list));

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        long portfolioId = globalVariable.get_creditPortfolioId();

        MoneyPortfolioSettings moneyPortfolioSettings = new MoneyPortfolioSettings(null);
        ArrayList<MoneyPortfolio> portfolios = MoneyPortfolioDataSource.LoadDataParent(
                getContext(), false, moneyPortfolioSettings);
        spinnerGroupPortfolio.setAdapter(new SpinnerMoneyPortfolioAdapter(getContext(), portfolios));
        setGroupPortfolioId(globalVariable.get_creditGroupPortfolioId());

        SetPortfolioAdapter(globalVariable.get_creditGroupPortfolioId());
        setPortfolioId(portfolioId);
    }

    private void SetPortfolioAdapter(Long groupPortfolioId)
    {
        MoneyPortfolioSettings moneyPortfolioSettings = new MoneyPortfolioSettings(null);
        ArrayList<MoneyPortfolio> portfolios = MoneyPortfolioDataSource.LoadDataChildren(
                getContext(), groupPortfolioId, null, moneyPortfolioSettings);
        spinnerPortfolio.setAdapter(new SpinnerMoneyPortfolioAdapter(getContext(), portfolios));
    }

    private void SetVisibility()
    {
        linearLayoutMain.setVisibility(View.VISIBLE);
        linearLayoutNotifications.setVisibility(View.INVISIBLE);
        linearLayoutNotificationsProject.setVisibility(View.INVISIBLE);
        linearLayoutNotificationsMoney.setVisibility(View.INVISIBLE);
        linearLayoutNotificationsTask.setVisibility(View.INVISIBLE);
        linearLayoutCreditSettings.setVisibility(View.INVISIBLE);
    }

    private void SetData(Context context)
    {
        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        NotifSettings notifSettings = globalVariable.get_notifSettings();
        NotifProjectSettings notifProjectSettings = globalVariable.get_notifProjectSettings();
        NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
        NotifTasksSettings notifTasksSettings = globalVariable.get_notifTasksSettings();

        setNotifEnabled(notifSettings);
        setNotifTimeStart(notifSettings);
        setNotifTimeEnd(notifSettings);
        setCheckboxNotifNeedDream(notifProjectSettings);
        setCheckboxNotifNeedTarget(notifProjectSettings);
        setCheckboxNotifNeedTaskInProject(notifProjectSettings);
        setCheckboxNotifActualMonthPlanIncoming(notifMoneySettings);
        setCheckboxNotifActualMonthPlanCost(notifMoneySettings);
        setCheckboxNotifNoEnteredCostPerDay(notifMoneySettings);
        setCheckboxNotifMoneyReportPrevMonth(notifMoneySettings);
        setCheckboxNotifNoAccumulationThisMonth(notifMoneySettings);
        setCheckboxNotifAccumulationDone(notifMoneySettings);
        setCheckboxOverdueTasksInMorning(notifTasksSettings);
        setCheckboxCountTasksForDay(notifTasksSettings);
        setCheckboxNoTasksForDay(notifTasksSettings);
        setCheckboxCountUnfinishedTasksInEvening(notifTasksSettings);
        setCheckboxCountTasksInbox(notifTasksSettings);
        setCheckboxMascotEnabled(globalVariable);

        setSpinnerDatabase(globalVariable.get_enumDatabase());
//        setGroupPortfolioId(globalVariable.get_creditGroupPortfolioId());
//        setPortfolioId(globalVariable.get_creditPortfolioId());
    }

    private void setSpinnerDatabase(EnumDatabase enumDatabase)
    {
        for (int i = 0; i < spinnerDatabase.getAdapter().getCount(); i++)
            if (spinnerDatabase.getAdapter().getItem(i).equals(enumDatabase))
            {
                spinnerDatabase.setSelection(i);
                break;
            }
    }

    private void setGroupPortfolioId(Long groupPortfolioId)
    {
        for (int i = 0; i < spinnerGroupPortfolio.getAdapter().getCount(); i++)
            if (((MoneyPortfolio) spinnerGroupPortfolio.getAdapter().getItem(i)).get_id().equals(groupPortfolioId))
            {
                spinnerGroupPortfolio.setSelection(i);
                break;
            }
    }

    private void setPortfolioId(Long portfolioId)
    {
        for (int i = 0; i < spinnerPortfolio.getAdapter().getCount(); i++)
            if (((MoneyPortfolio) spinnerPortfolio.getAdapter().getItem(i)).get_id().equals(portfolioId))
            {
                spinnerPortfolio.setSelection(i);
                break;
            }
    }

    private void setNotifEnabled(NotifSettings notifSettings)
    {
        checkboxNotifEnabled.setChecked(notifSettings.get_notifEnabled());
    }

    @SuppressLint("SetTextI18n")
    private void setNotifTimeStart(NotifSettings notifSettings)
    {
        setNotifTimeStart(notifSettings.get_notifStartHour(),
                notifSettings.get_notifStartMinute());
    }

    @SuppressLint("SetTextI18n")
    private void setNotifTimeStart(int hour, int minute)
    {
        String sHour = "";
        if (hour < 10)
            sHour = "0";
        sHour += hour;

        String sMinute = "";
        if (minute < 10)
            sMinute = "0";
        sMinute += minute;

        textViewTimeNotifStart.setText(sHour + ":" + sMinute);
    }

    @SuppressLint("SetTextI18n")
    private void setNotifTimeEnd(NotifSettings notifSettings)
    {
        String sHour = "";
        if (notifSettings.get_notifEndHour() < 10)
            sHour = "0";
        sHour += notifSettings.get_notifEndHour();

        String sMinute = "";
        if (notifSettings.get_notifEndMinute() < 10)
            sMinute = "0";
        sMinute += notifSettings.get_notifEndMinute();

        textViewTimeNotifEnd.setText(sHour + ":" + sMinute);
    }

    private void setCheckboxNotifNeedDream(NotifProjectSettings notifProjectSettings)
    {
        checkboxNotifNeedDream.setChecked(notifProjectSettings.get_needDream());
    }

    private void setCheckboxNotifNeedTarget(NotifProjectSettings notifProjectSettings)
    {
        checkboxNotifNeedTarget.setChecked(notifProjectSettings.get_needTarget());
    }

    private void setCheckboxNotifNeedTaskInProject(NotifProjectSettings notifProjectSettings)
    {
        checkboxNotifNeedTaskInProject.setChecked(notifProjectSettings.get_needTaskInProject());
    }

    private void setCheckboxNotifActualMonthPlanIncoming(NotifMoneySettings notifMoneySettings)
    {
        checkboxNotifActualMonthPlanIncoming.setChecked(notifMoneySettings.get_actualMonthPlanIncoming());
    }

    private void setCheckboxNotifActualMonthPlanCost(NotifMoneySettings notifMoneySettings)
    {
        checkboxNotifActualMonthPlanCost.setChecked(notifMoneySettings.get_actualMonthPlanCost());
    }

    private void setCheckboxNotifNoEnteredCostPerDay(NotifMoneySettings notifMoneySettings)
    {
        checkboxNotifNoEnteredCostPerDay.setChecked(notifMoneySettings.get_noEnteredCostPerDay());
    }

    private void setCheckboxNotifMoneyReportPrevMonth(NotifMoneySettings notifMoneySettings)
    {
        checkboxNotifMoneyReportPrevMonth.setChecked(notifMoneySettings.get_moneyReportPrevMonth());
    }

    private void setCheckboxNotifNoAccumulationThisMonth(NotifMoneySettings notifMoneySettings)
    {
        checkboxNotifNoAccumulationThisMonth.setChecked(notifMoneySettings.get_noAccumulationThisMonth());
    }

    private void setCheckboxNotifAccumulationDone(NotifMoneySettings notifMoneySettings)
    {
        checkboxNotifAccumulationDone.setChecked(notifMoneySettings.get_accumulationDone());
    }

    private void setCheckboxOverdueTasksInMorning(NotifTasksSettings notifTasksSettings)
    {
        checkboxOverdueTasksInMorning.setChecked(notifTasksSettings.get_overdueTasksInMorning());
    }

    private void setCheckboxCountTasksForDay(NotifTasksSettings notifTasksSettings)
    {
        checkboxCountTasksForDay.setChecked(notifTasksSettings.get_countTasksForDay());
    }

    private void setCheckboxNoTasksForDay(NotifTasksSettings notifTasksSettings)
    {
        checkboxNoTasksForDay.setChecked(notifTasksSettings.get_noTasksForDay());
    }

    private void setCheckboxCountUnfinishedTasksInEvening(NotifTasksSettings notifTasksSettings)
    {
        checkboxCountUnfinishedTasksInEvening.setChecked(notifTasksSettings.get_countUnfinishedTasksInEvening());
    }

    private void setCheckboxCountTasksInbox(NotifTasksSettings notifTasksSettings)
    {
        checkboxCountTasksInbox.setChecked(notifTasksSettings.get_countTasksInbox());
    }

    private void setCheckboxMascotEnabled(GlobalClass gV)
    {
        checkBoxMascot.setChecked(gV.get_showMascot());
    }
    //endregion

    //region SetListeners
    private void SetListeners()
    {
        tableRowNotifications.setOnClickListener(view ->
        {
            linearLayoutMain.setVisibility(View.INVISIBLE);
            linearLayoutNotifications.setVisibility(View.VISIBLE);
        });

        tableRowCreditsSettings.setOnClickListener(view ->
        {
            linearLayoutMain.setVisibility(View.INVISIBLE);
            linearLayoutCreditSettings.setVisibility(View.VISIBLE);
        });

        tableRowNotificationsProject.setOnClickListener(view ->
        {
            linearLayoutNotifications.setVisibility(View.INVISIBLE);
            linearLayoutNotificationsProject.setVisibility(View.VISIBLE);
        });

        tableRowNotificationsMoney.setOnClickListener(view ->
        {
            linearLayoutNotifications.setVisibility(View.INVISIBLE);
            linearLayoutNotificationsMoney.setVisibility(View.VISIBLE);
        });

        tableRowNotificationsTask.setOnClickListener(view ->
        {
            linearLayoutNotifications.setVisibility(View.INVISIBLE);
            linearLayoutNotificationsTask.setVisibility(View.VISIBLE);
        });

        constraintLayoutToMainForm.setOnClickListener(view ->
        {
            linearLayoutMain.setVisibility(View.VISIBLE);
            linearLayoutNotifications.setVisibility(View.INVISIBLE);
        });

        constraintLayoutToMainFormFromCreditSettings.setOnClickListener(view ->
        {
            linearLayoutMain.setVisibility(View.VISIBLE);
            linearLayoutCreditSettings.setVisibility(View.INVISIBLE);
        });

        constraintLayoutToNotifFromNotifProject.setOnClickListener(view ->
        {
            linearLayoutNotifications.setVisibility(View.VISIBLE);
            linearLayoutNotificationsProject.setVisibility(View.INVISIBLE);
        });

        constraintLayoutToNotifFromNotifMoney.setOnClickListener(view ->
        {
            linearLayoutNotifications.setVisibility(View.VISIBLE);
            linearLayoutNotificationsMoney.setVisibility(View.INVISIBLE);
        });

        constraintLayoutToNotifFromNotifTask.setOnClickListener(view ->
        {
            linearLayoutNotifications.setVisibility(View.VISIBLE);
            linearLayoutNotificationsTask.setVisibility(View.INVISIBLE);
        });

        checkboxNotifEnabled.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();
            notifSettings.set_notifEnabled(checkboxNotifEnabled.isChecked());
            globalVariable.SaveSharedPreferencesNotifSettingsNotifEnabled();
        });

        imageViewTimeMinusNotifStart.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();

            int hour = notifSettings.get_notifStartHour();
            int minute = notifSettings.get_notifStartMinute() - 1;
            if (minute < 0)
            {
                minute = 59;
                hour -= 1;
                if (hour < 0)
                    hour = 23;
            }
            notifSettings.set_notifStartHour(hour);
            notifSettings.set_notifStartMinute(minute);
            globalVariable.SaveSharedPreferencesNotifSettingsNotifStart();

            setNotifTimeStart(notifSettings);
        });

        imageViewTimePlusNotifStart.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();

            int hour = notifSettings.get_notifStartHour();
            int minute = notifSettings.get_notifStartMinute() + 1;
            if (minute > 59)
            {
                minute = 0;
                hour += 1;
                if (hour > 23)
                    hour = 0;
            }
            notifSettings.set_notifStartHour(hour);
            notifSettings.set_notifStartMinute(minute);
            globalVariable.SaveSharedPreferencesNotifSettingsNotifStart();

            setNotifTimeStart(notifSettings);
        });

        textViewTimeNotifStart.setOnClickListener(view1 ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (timePicker, i, i1) ->
                    {
                        notifSettings.set_notifStartHour(i);
                        notifSettings.set_notifStartMinute(i1);
                        globalVariable.SaveSharedPreferencesNotifSettingsNotifStart();

                        setNotifTimeStart(notifSettings);
                    },
                    notifSettings.get_notifStartHour(),
                    notifSettings.get_notifStartMinute(),
                    true);
            timePickerDialog.show();
        });

        imageViewTimeMinusNotifEnd.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();

            int hour = notifSettings.get_notifEndHour();
            int minute = notifSettings.get_notifEndMinute() - 1;
            if (minute < 0)
            {
                minute = 59;
                hour -= 1;
                if (hour < 0)
                    hour = 23;
            }
            notifSettings.set_notifEndHour(hour);
            notifSettings.set_notifEndMinute(minute);
            globalVariable.SaveSharedPreferencesNotifSettingsNotifEnd();

            setNotifTimeEnd(notifSettings);
        });

        imageViewTimePLusNotifEnd.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();

            int hour = notifSettings.get_notifEndHour();
            int minute = notifSettings.get_notifEndMinute() + 1;
            if (minute > 59)
            {
                minute = 0;
                hour += 1;
                if (hour > 23)
                    hour = 0;
            }
            notifSettings.set_notifEndHour(hour);
            notifSettings.set_notifEndMinute(minute);
            globalVariable.SaveSharedPreferencesNotifSettingsNotifEnd();

            setNotifTimeEnd(notifSettings);
        });

        textViewTimeNotifEnd.setOnClickListener(view1 ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifSettings notifSettings = globalVariable.get_notifSettings();

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (timePicker, i, i1) ->
                    {
                        notifSettings.set_notifEndHour(i);
                        notifSettings.set_notifEndMinute(i1);
                        globalVariable.SaveSharedPreferencesNotifSettingsNotifEnd();

                        setNotifTimeEnd(notifSettings);
                    },
                    notifSettings.get_notifEndHour(),
                    notifSettings.get_notifEndMinute(),
                    true);
            timePickerDialog.show();
        });

        checkboxNotifNeedDream.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifProjectSettings notifProjectSettings = globalVariable.get_notifProjectSettings();
            notifProjectSettings.set_needDream(checkboxNotifNeedDream.isChecked());
            globalVariable.SaveSharedPreferencesNotifProjectSettingsNeedDream();
        });

        checkboxNotifNeedTarget.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifProjectSettings notifProjectSettings = globalVariable.get_notifProjectSettings();
            notifProjectSettings.set_needTarget(checkboxNotifNeedTarget.isChecked());
            globalVariable.SaveSharedPreferencesNotifProjectSettingsNeedTarget();
        });

        checkboxNotifNeedTaskInProject.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifProjectSettings notifProjectSettings = globalVariable.get_notifProjectSettings();
            notifProjectSettings.set_needTaskInProject(checkboxNotifNeedTaskInProject.isChecked());
            globalVariable.SaveSharedPreferencesNotifProjectSettingsNeedTaskInProject();
        });

        checkboxNotifActualMonthPlanIncoming.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
            notifMoneySettings.set_actualMonthPlanIncoming(checkboxNotifActualMonthPlanIncoming.isChecked());
            globalVariable.SaveSharedPreferencesNotifMoneySettingsActualMonthPlanIncoming();
        });

        checkboxNotifActualMonthPlanCost.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
            notifMoneySettings.set_actualMonthPlanCost(checkboxNotifActualMonthPlanCost.isChecked());
            globalVariable.SaveSharedPreferencesNotifMoneySettingsActualMonthPlanCost();
        });

        checkboxNotifNoEnteredCostPerDay.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
            notifMoneySettings.set_noEnteredCostPerDay(checkboxNotifNoEnteredCostPerDay.isChecked());
            globalVariable.SaveSharedPreferencesNotifMoneySettingsNoEnteredCostPerDay();
        });

        checkboxNotifMoneyReportPrevMonth.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
            notifMoneySettings.set_moneyReportPrevMonth(checkboxNotifMoneyReportPrevMonth.isChecked());
            globalVariable.SaveSharedPreferencesNotifMoneySettingsMoneyReportPrevMonth();
        });

        checkboxNotifNoAccumulationThisMonth.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
            notifMoneySettings.set_noAccumulationThisMonth(checkboxNotifNoAccumulationThisMonth.isChecked());
            globalVariable.SaveSharedPreferencesNotifMoneySettingsMoneyReportPrevMonth();
        });

        checkboxNotifAccumulationDone.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifMoneySettings notifMoneySettings = globalVariable.getNotifMoneySettings();
            notifMoneySettings.set_accumulationDone(checkboxNotifAccumulationDone.isChecked());
            globalVariable.SaveSharedPreferencesNotifMoneySettingsMoneyReportPrevMonth();
        });

        checkboxOverdueTasksInMorning.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifTasksSettings notifTasksSettings = globalVariable.get_notifTasksSettings();
            notifTasksSettings.set_overdueTasksInMorning(checkboxOverdueTasksInMorning.isChecked());
            globalVariable.SaveSharedPreferencesNotifTasksSettingsOverdueTasksInMorning();
        });

        checkboxCountTasksForDay.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifTasksSettings notifTasksSettings = globalVariable.get_notifTasksSettings();
            notifTasksSettings.set_countTasksForDay(checkboxCountTasksForDay.isChecked());
            globalVariable.SaveSharedPreferencesNotifTasksSettingsCountTasksForDay();
        });

        checkboxNoTasksForDay.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifTasksSettings notifTasksSettings = globalVariable.get_notifTasksSettings();
            notifTasksSettings.set_noTasksForDay(checkboxNoTasksForDay.isChecked());
            globalVariable.SaveSharedPreferencesNotifTasksSettingsNoTasksForDay();
        });

        checkboxCountUnfinishedTasksInEvening.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifTasksSettings notifTasksSettings = globalVariable.get_notifTasksSettings();
            notifTasksSettings.set_countUnfinishedTasksInEvening(checkboxCountUnfinishedTasksInEvening.isChecked());
            globalVariable.SaveSharedPreferencesNotifTasksSettingsCountUnfinishedTasksInEvening();
        });

        checkboxCountTasksInbox.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            NotifTasksSettings notifTasksSettings = globalVariable.get_notifTasksSettings();
            notifTasksSettings.set_countTasksInbox(checkboxCountTasksInbox.isChecked());
            globalVariable.SaveSharedPreferencesNotifTasksSettingsCountTasksInbox();
        });

        checkBoxMascot.setOnClickListener(view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_showMascot(checkBoxMascot.isChecked());
            globalVariable.SaveSharedPreferences();
        });

        spinnerDatabase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
                globalVar.set_enumDatabase((EnumDatabase) spinnerDatabase.getSelectedItem());

                globalVar.set_cCabinet(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spinnerGroupPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
                globalVar.set_creditGroupPortfolioId(get_GroupPortfolioId());
                globalVar.SaveSharedPreferences4CreditParams();
                long profolioId = globalVar.get_creditPortfolioId();
                SetPortfolioAdapter(get_GroupPortfolioId());
                setPortfolioId(profolioId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spinnerPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
                globalVar.set_creditPortfolioId(get_PortfolioId());
                globalVar.SaveSharedPreferences4CreditParams();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        buttonFreeLearnDatabase.setOnClickListener(view ->
        {
            try
            {
                DBHelper.DeleteLearnDatabase(getContext());
                Toast.makeText(getContext(), "Демонстрационная база пересоздана", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region GetData
    private Long get_GroupPortfolioId()
    {
        return ((MoneyPortfolio) spinnerGroupPortfolio.getSelectedItem()).get_id();
    }

    private Long get_PortfolioId()
    {
        return ((MoneyPortfolio) spinnerPortfolio.getSelectedItem()).get_id();
    }
    //endregion
}