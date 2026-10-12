package com.MH83.LifeAsProject.view;

import static com.MH83.LifeAsProject.interfac.Constants.REQUEST_GALLERY_FOR_MONEY_DREAM_POPUP;
import static com.MH83.LifeAsProject.interfac.Constants.REQUEST_GALLERY_FOR_MONEY_TARGET_POPUP;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MH83.LifeAsProject.custom.CustomRingTextView;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IGetTitle;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeResource;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAsset;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCredit;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentDeleteAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentDohodRashod;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentFinishedTargets;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentIncrease;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMinus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentParam;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPlanFact;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPlanFactMoney;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPlus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPortfolio;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowBlankData;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowFullData;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostAdvanced;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostAdvancedPlus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostBase;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostProject;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostProjectPlus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncreasePercent;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetList;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetListForYear;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTransactionList;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowPercent;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetFinished;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetInJob;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentTag;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentTypeAsset;
import com.MH83.LifeAsProject.service.NotificationWorker;
import com.MH83.LifeAsProject.view.Custom.Tag.FragmentTag;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentCabinet;
import com.MH83.LifeAsProject.view.Main.MainSettings.FragmentMainSettings;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyTransaction;
import com.MH83.LifeAsProject.view.Money.MoneyAsset.FragmentMoneyAsset;
import com.MH83.LifeAsProject.view.Money.MoneyBoard.FragmentMoneyBoard;
import com.MH83.LifeAsProject.view.Money.MoneyCapital.FragmentMoneyCapital;
import com.MH83.LifeAsProject.view.Money.MoneyCosts.FragmentMoneyCostsFact;
import com.MH83.LifeAsProject.view.Money.MoneyCredit.FragmentMoneyCredit;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDream;
import com.MH83.LifeAsProject.view.Money.MoneyIncoming.FragmentMoneyIncomingFact;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanFactCost;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanFactIncome;
import com.MH83.LifeAsProject.view.Money.MoneyPortfolio.FragmentMoneyPortfolio;
import com.MH83.LifeAsProject.view.Money.MoneyTarget.FragmentMoneyTargetTable;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeView;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentManageMoneyCategory;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSetPopupImage;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentTableGraph;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccounting;
import com.MH83.LifeAsProject.view.Money.MoneyCategory.FragmentMoneyCategory;
import com.MH83.LifeAsProject.view.Money.MoneyCosts.FragmentMoneyCosts;
import com.MH83.LifeAsProject.view.Money.MoneyIncoming.FragmentMoneyIncoming;
import com.MH83.LifeAsProject.view.Money.MoneyIncrease.FragmentMoneyIncreaseList;
import com.MH83.LifeAsProject.view.Custom.Param.FragmentParam;
import com.MH83.LifeAsProject.view.Money.MoneyTypeAsset.FragmentMoneyTypeAsset;
import com.MH83.LifeAsProject.view.Tasks.TasksFromProject.FragmentTasksFromProject;
import com.MH83.LifeAsProject.view.Tasks.TasksInbox.FragmentTasksInbox;
import com.MH83.LifeAsProject.view.Tasks.TasksProject.FragmentTasksProject;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTask;
import com.MH83.LifeAsProject.view.Tasks.TasksToday.FragmentTasksToday;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.huawei.hms.iap.Iap;
//import com.huawei.hms.iap.IapApiException;
//import com.huawei.hms.iap.entity.OrderStatusCode;
//import com.huawei.hms.iap.entity.OwnedPurchasesReq;
//import com.huawei.hms.iap.entity.OwnedPurchasesResult;
//import com.huawei.hms.iap.entity.PurchaseResultInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.model.purchase.Purchase;
import ru.rustore.sdk.billingclient.model.purchase.PurchaseState;
import ru.rustore.sdk.billingclient.usecase.PurchasesUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class MainActivity extends AppCompatActivity
{
    //region Activity Components
    private Toolbar toolbarTopLeft;
    private Toolbar toolbarTopRight;
    private Toolbar toolbarTopRight2;
    private BottomNavigationView bottomNavigationMoney;
    private BottomNavigationView bottomNavigationTasks;
    private BottomNavigationView bottomNavigationAssistants;
    private TextView textViewTitle;
    private CustomRingTextView textViewResource;
    private ImageView imageViewGotoSocial;
    private ImageView imageViewGotoCabinet;
    private ImageView imageViewAddItem;
    private ImageView imageViewMascot;
    //endregion

    //region Activity Parameters
    private int _navigationId = 0;
    private Fragment _fragmentContainerMain;
    //endregion

    //region Параметры передержки
    int _dreamScrollPosition = 0;
    int _increaseScrollPosition = 0;
    EnumTypeView _increaseTypeView = EnumTypeView.Table;
    int _increaseCountProgress = 60;
    int _increaseShiftProgress = 0;
    int _increaseCountMax = 60;
    int _increaseShiftMax = 0;
    int _categoryScrollPosition = 0;
    ArrayList<Integer> _categoryExpandedId;
    int _pnlScrollPosition = 0;
    Integer _pnlCategoryId = 0;
    MoneyAccountingSum _pnlMoneyAccountingSumCost = null;
    MoneyAccountingSum _pnlMoneyAccountingSumIncrease = null;
    //endregion

    //region Save and Restore Avctivity
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);

        if (_fragmentContainerMain instanceof FragmentMoneyDream)
        {
            outState.putInt("FragmentMDreamScrollPosition",
                    ((FragmentMoneyDream) _fragmentContainerMain).GetScrollPosition());
        }
        else if (_fragmentContainerMain instanceof FragmentMoneyIncreaseList)
        {
            outState.putInt("FragmentMIncreaseScrollPosition",
                    ((FragmentMoneyIncreaseList) _fragmentContainerMain).GetScrollPosition());
            outState.putString("FragmentMIncreaseTypeView",
                    ((FragmentMoneyIncreaseList) _fragmentContainerMain).GetTypeView().toString());
            outState.putInt("FragmentMIncreaseCountProgress",
                    ((FragmentMoneyIncreaseList) _fragmentContainerMain).GetCountProgress());
            outState.putInt("FragmentMIncreaseShiftProgress",
                    ((FragmentMoneyIncreaseList) _fragmentContainerMain).GetShiftProgress());
            outState.putInt("FragmentMIncreaseCountMax",
                    ((FragmentMoneyIncreaseList) _fragmentContainerMain).GetCountMax());
            outState.putInt("FragmentMIncreaseShiftMax",
                    ((FragmentMoneyIncreaseList) _fragmentContainerMain).GetShiftMax());
        }
        else if (_fragmentContainerMain instanceof FragmentMoneyTransaction)
        {
            outState.putParcelable("FragmentMAccountingPeriod",
                    ((FragmentMoneyTransaction) _fragmentContainerMain).getCustomPeriodClass());
            outState.putLong("FragmentMAccountingListCategoryId",
                    ((FragmentMoneyTransaction) _fragmentContainerMain).GetCategoryId());
            if (((FragmentMoneyTransaction) _fragmentContainerMain).getMASCost() != null)
                outState.putParcelable("FragmentMAccountingListMASCost",
                    ((FragmentMoneyTransaction) _fragmentContainerMain).getMASCost());
            if (((FragmentMoneyTransaction) _fragmentContainerMain).getMASInc() != null)
                outState.putParcelable("FragmentMAccountingListMASInc",
                    ((FragmentMoneyTransaction) _fragmentContainerMain).getMASInc());
        }
    }

    private void ReadInstanceState(@NonNull Bundle savedInstanceState)
    {
        _dreamScrollPosition = savedInstanceState.getInt("FragmentMDreamScrollPosition", 0);

        _increaseScrollPosition = savedInstanceState.getInt("FragmentMIncreaseScrollPosition", 0);
        _increaseTypeView = EnumTypeView.valueOf(
                savedInstanceState.getString("FragmentMIncreaseTypeView", "Table"));
        _increaseCountProgress = savedInstanceState.getInt("FragmentMIncreaseCountProgress", 60);
        _increaseShiftProgress = savedInstanceState.getInt("FragmentMIncreaseShiftProgress", 0);
        _increaseCountMax = savedInstanceState.getInt("FragmentMIncreaseCountMax", 60);
        _increaseShiftMax = savedInstanceState.getInt("FragmentMIncreaseShiftMax", 0);

        _categoryScrollPosition = savedInstanceState.getInt("FragmentMCategoryScrollPosition", 0);
        _categoryExpandedId = savedInstanceState.getIntegerArrayList("FragmentMCategoryExpandedId");

        _pnlScrollPosition = savedInstanceState.getInt("FragmentMAccountingListScrollPosition", 0);
        _pnlCategoryId = savedInstanceState.getInt("FragmentMAccountingListCategoryId", 0);
        _pnlMoneyAccountingSumCost = savedInstanceState.getParcelable("FragmentMAccountingListMASCost");
        _pnlMoneyAccountingSumIncrease = savedInstanceState.getParcelable("FragmentMAccountingListMASInc");
    }
    //endregion

    //region Start Activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.LoadSharedPreferences();

        CustomTrack.InitiateMyTracker(globalVariable, getApplication(), this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int minSize = (int) (Math.min(displaymetrics.heightPixels, displaymetrics.widthPixels) /
                displaymetrics.scaledDensity);
        globalVariable.set_typeDevice(minSize < 540 ? EnumTypeDevice.Phone: EnumTypeDevice.Tablet);

        boolean firstOpen = globalVariable.getFirstOpen();
        CustomTrack.TrackProgramOpen(globalVariable);

//      Включаем только, когда тестируем программу
        if (GlobalClass.TestPaid())
            globalVariable.ClearSubscription4Test();
//      Выключаем только, когда тестируем программу
        if (!GlobalClass.TestPaid())
            UpdateSubscriptionInfo(globalVariable);
//      Включаем только, когда тестируем программу
        if (GlobalClass.FreeProgram())
            UpdateSubscriptionInfo4Test(globalVariable);

        if (savedInstanceState != null)
            ReadInstanceState(savedInstanceState);

        setContentView(R.layout.activity_main);

        try
        {
            DBHelper dbHelper = DBHelper.GetDBHelper(this);
            dbHelper.CorrectIncorrectData(getApplicationContext());
        }
        catch (Exception e) { }

        FindComponents(globalVariable.get_typeDevice());
        SetVisibleToolbar();
        SetListeners();

//        if (isMyServiceRunning(NotificationService.class))
//            stopService(new Intent(getApplicationContext(), NotificationService.class));
//        startService(new Intent(getApplicationContext(), NotificationService.class));

//        BroadcastReceiver broadcastReceiver = new TimeBroadcastReceiver();
//        registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.TIME_TICK"));

//        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
//        JobInfo jobInfo = new JobInfo.Builder(11111, componentName)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                .setPersisted(true)
//                .setPeriodic(10*1000)
//                .build();
//        jobScheduler.schedule(jobInfo);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
//            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(
//                    NotificationWorker.class).build();
//            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);

            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                    NotificationWorker.class, 15, TimeUnit.MINUTES)
                    .build();
            WorkManager.getInstance(this).enqueue(periodicWorkRequest);
        }

//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_DATE_CHANGED);
//        registerReceiver(new Restarter(), intentFilter);

//        CWebData.LoadInflation();
//        CWebData.LoadExchangeRate();

        if (firstOpen)
            ClickToLegend();
    }

    //region ClickToLegend
    private void ClickToLegend()
    {
        new FragmentMainActivityLegendPopup(this).PopupShow();
    }
    //endregion

    @Override
    protected void onResume()
    {
        super.onResume();

        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        if (globalVar.get_mainActivity_navigationId() == R.id.navigation_money_dream ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_target ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_incoming_fact ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_costs_fact ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_incoming ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_costs ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_capital ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_accounting)
        {
            textViewResource.setText("$");
            globalVar.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (globalVar.get_mainActivity_navigationId() == R.id.navigation_tasks_tasks_fromproject ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_tasks_today ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_tasks_inbox)
        {
            textViewResource.setText("T");
            globalVar.set_mainActivity_typeResource(EnumTypeResource.Time);
        }

        if (globalVar.get_mainActivity_navigationId() == R.id.navigation_money_dream ||
                globalVar.get_mainActivity_navigationId() == R.id.navigation_money_target)
        {
            if (_navigationId != globalVar.get_mainActivity_navigationId())
                NavigationItemSelected(globalVar.get_mainActivity_navigationId(), false);
        }
        else
            NavigationItemSelected(globalVar.get_mainActivity_navigationId(), false);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    private void SetResourceTextView(int navigationId)
    {
        if (navigationId != R.id.navigation_money_dream &&
//                navigationId != R.id.navigation_money_increase &&
                navigationId != R.id.navigation_money_board &&
                navigationId != R.id.navigation_money_incoming &&
                navigationId != R.id.navigation_money_costs &&
                navigationId != R.id.navigation_money_incoming_fact &&
                navigationId != R.id.navigation_money_costs_fact &&
                navigationId != R.id.navigation_money_capital &&
                navigationId != R.id.navigation_money_accounting &&
                navigationId != R.id.navigation_tasks_today &&
                navigationId != R.id.navigation_tasks_project &&
                navigationId != R.id.navigation_tasks_tasks &&
                navigationId != R.id.navigation_tasks_tasks_fromproject &&
                navigationId != R.id.navigation_tasks_canban &&
                navigationId != R.id.navigation_tasks_inbox)
            textViewResource.setVisibility(View.INVISIBLE);
        else
            textViewResource.setVisibility(View.VISIBLE);
    }

    private void SetToolbarLeft()
    {
        if (toolbarTopLeft != null)
        {
            setSupportActionBar(toolbarTopLeft);
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_left_50_white);
                getSupportActionBar().setTitle("");
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    private void SetVisibleToolbar()
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        switch (globalVar.get_mainActivity_typeResource())
        {
            case Money:
//                    _typeResource = EnumTypeResource.Person;
//                    textViewResource.setText("P");
//                    bottomNavigationMoney.setVisibility(View.INVISIBLE);
//                    bottomNavigationTasks.setVisibility(View.INVISIBLE);
//                    bottomNavigationAssistants.setVisibility(View.VISIBLE);
//                    break;
//                case Person:
                textViewResource.setText("$");
                bottomNavigationMoney.setVisibility(View.VISIBLE);
                bottomNavigationTasks.setVisibility(View.INVISIBLE);
                bottomNavigationAssistants.setVisibility(View.INVISIBLE);
//                NavigationItemSelected(R.id.navigation_money_dream);
                break;
            case Time:
                textViewResource.setText("T");
                bottomNavigationMoney.setVisibility(View.INVISIBLE);
                bottomNavigationTasks.setVisibility(View.VISIBLE);
                bottomNavigationAssistants.setVisibility(View.INVISIBLE);
//                NavigationItemSelected(R.id.navigation_tasks_project);
                break;
            default:
        }
    }

    private void SetListeners()
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();

        bottomNavigationMoney.setOnNavigationItemSelectedListener(item -> NavigationItemSelected(item.getItemId()));
        bottomNavigationTasks.setOnNavigationItemSelectedListener(item -> NavigationItemSelected(item.getItemId()));
        bottomNavigationAssistants.setOnNavigationItemSelectedListener(item -> NavigationItemSelected(item.getItemId()));

//        textViewCabinet.setOnClickListener(view -> NavigationItemSelected(R.id.navigation_cabinet));

        textViewResource.setOnClickListener(view ->
        {
            switch (globalVar.get_mainActivity_typeResource())
            {
                case Money:
                    globalVar.set_mainActivity_typeResource(EnumTypeResource.Time);
                    NavigationItemSelected(R.id.navigation_tasks_project);
                    break;
                case Time:
//                    _typeResource = EnumTypeResource.Person;
//                    textViewResource.setText("P");
//                    bottomNavigationMoney.setVisibility(View.INVISIBLE);
//                    bottomNavigationTasks.setVisibility(View.INVISIBLE);
//                    bottomNavigationAssistants.setVisibility(View.VISIBLE);
//                    break;
//                case Person:
                    globalVar.set_mainActivity_typeResource(EnumTypeResource.Money);
                    NavigationItemSelected(R.id.navigation_money_dream);
                    break;
                default:
            }
            SetVisibleToolbar();
        });

        textViewTitle.setOnClickListener(view ->
        {
            if (_fragmentContainerMain instanceof IFragmentSettings)
                ChangeToolbarRight2Visible(true);
        });

        toolbarTopRight.setOnClickListener(view ->
        {
            if (_fragmentContainerMain instanceof IFragmentSettings)
                ChangeToolbarRight2Visible(true);
        });

        toolbarTopRight2.setOnClickListener(view ->
        {
            if (_fragmentContainerMain instanceof IFragmentSettings)
                ChangeToolbarRight2Visible(false);
        });
    }

    private void FindComponents(EnumTypeDevice typeDevice)
    {
        toolbarTopLeft = findViewById(R.id.toolbar_topleft);
        if (typeDevice.equals(EnumTypeDevice.Phone))
        {
            ViewGroup.LayoutParams lp = toolbarTopLeft.getLayoutParams();
            lp.width = 0;
            toolbarTopLeft.setLayoutParams(lp);
            toolbarTopLeft.setVisibility(View.INVISIBLE);
        }

        toolbarTopRight = findViewById(R.id.toolbar_topright);
        toolbarTopRight2 = findViewById(R.id.toolbar_topright2);

        textViewResource = findViewById(R.id.textViewResource);
        textViewTitle = findViewById(R.id.toolbar_title);
        bottomNavigationMoney = findViewById(R.id.navigationBottomViewMoney);
        bottomNavigationTasks = findViewById(R.id.navigationBottomViewTasks);
        bottomNavigationAssistants = findViewById(R.id.navigationBottomViewAssistants);
        imageViewGotoSocial = findViewById(R.id.imageViewGotoSocial);
        imageViewGotoCabinet = findViewById(R.id.imageViewGotoCabinet);
        imageViewAddItem = findViewById(R.id.imageViewAddItem);
        imageViewMascot = findViewById(R.id.imageViewMascot);
    }

    @Override
    protected void onDestroy()
    {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.SaveSharedPreferences();

//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservice");
//        broadcastIntent.setClass(this, Restarter.class);
//        this.sendBroadcast(broadcastIntent);

        super.onDestroy();
    }
    //endregion

    //region Navigation
    public boolean NavigationItemSelected(int itemSelected)
    {
        boolean b = NavigationItemSelected(itemSelected, 0);
        SetVisibleToolbar();
        return b;
    }

    public boolean NavigationItemSelected(int itemSelected, boolean withPushToStack)
    {
        boolean b = NavigationItemSelected(itemSelected, 0, withPushToStack);
        SetVisibleToolbar();
        return b;
    }

    @Override
    public void onBackPressed()
    {
        BackToNavigationFromStack();
//        super.onBackPressed();
    }

    public void BackToNavigationFromStack()
    {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        int id = globalVariable.GetFromNavigationIds();
        if (id != 0)
            NavigationItemSelected(id, 0, false);
        else
            finish();
    }

    public boolean NavigationItemSelected(int itemSelected, int fromItem)
    {
        return NavigationItemSelected(itemSelected, fromItem, true);
    }

    @SuppressLint("RestrictedApi")
    public boolean NavigationItemSelected(int itemSelected, int fromItem, boolean withPushToStack)
    {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        if (withPushToStack && globalVariable.get_mainActivity_navigationId() != 0)
            globalVariable.AddToNavigationIds(globalVariable.get_mainActivity_navigationId());

        ChangeToolbarRight2Visible(false);

        _navigationId = itemSelected;
        globalVariable.set_mainActivity_navigationId(_navigationId);
        SetResourceTextView(_navigationId);

        _fragmentContainerMain = null;

        if (globalVariable.noneSubscription())
            _fragmentContainerMain = new FragmentMainActivityPayment();
        else if (itemSelected == R.id.navigation_cabinet)
            _fragmentContainerMain = new FragmentCabinet();
        else if (itemSelected == R.id.navigation_main_settings)
            _fragmentContainerMain = new FragmentMainSettings();
        else if (itemSelected == R.id.navigation_money_dream)
        {
            _fragmentContainerMain = new FragmentMoneyDream(_dreamScrollPosition);
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_target)
        {
            _fragmentContainerMain = new FragmentMoneyTargetTable();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_board)
        {
            _fragmentContainerMain = new FragmentMoneyBoard();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_portfolio)
        {
            _fragmentContainerMain = new FragmentMoneyPortfolio();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_typeasset)
        {
            _fragmentContainerMain = new FragmentMoneyTypeAsset();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_asset)
        {
            _fragmentContainerMain = new FragmentMoneyAsset();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_increase)
        {
            _fragmentContainerMain = new FragmentMoneyIncreaseList(
                    _increaseScrollPosition, _increaseTypeView,
                    _increaseCountProgress, _increaseShiftProgress,
                    _increaseCountMax, _increaseShiftMax);
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_capital)
        {
            _fragmentContainerMain = new FragmentMoneyCapital();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_incoming)
        {
            _fragmentContainerMain = new FragmentMoneyIncoming();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
            globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingPlan);
        }
        else if (itemSelected == R.id.navigation_money_incoming_fact)
        {
            _fragmentContainerMain = new FragmentMoneyIncomingFact();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
            globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingFact);
        }
        else if (itemSelected == R.id.navigation_money_planfact_income)
        {
            _fragmentContainerMain = new FragmentMoneyPlanFactIncome();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_planfact_cost)
        {
            _fragmentContainerMain = new FragmentMoneyPlanFactCost();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_category)
        {
            _fragmentContainerMain = new FragmentMoneyCategory();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_param)
            _fragmentContainerMain = new FragmentParam(0);
        else if (itemSelected == R.id.navigation_tag)
            _fragmentContainerMain = new FragmentTag();
        else if (itemSelected == R.id.navigation_money_costs)
        {
            _fragmentContainerMain = new FragmentMoneyCosts();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
            globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpensePlan);
        }
        else if (itemSelected == R.id.navigation_money_costs_fact)
        {
            _fragmentContainerMain = new FragmentMoneyCostsFact();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
            globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpenseFact);
        }
        else if (itemSelected == R.id.navigation_money_accounting)
        {
            _fragmentContainerMain = new FragmentMoneyAccounting();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_money_pnl)
        {
            _fragmentContainerMain = new FragmentMoneyTransaction();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_credit)
        {
            _fragmentContainerMain = new FragmentMoneyCredit();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Money);
        }
        else if (itemSelected == R.id.navigation_tasks_project)
        {
            _fragmentContainerMain = new FragmentTasksProject();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Time);
        }
        else if (itemSelected == R.id.navigation_tasks_tasks)
        {
            _fragmentContainerMain = new FragmentTasksTask();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Time);
        }
        else if (itemSelected == R.id.navigation_tasks_tasks_fromproject)
        {
            _fragmentContainerMain = new FragmentTasksFromProject();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Time);
        }
        else if (itemSelected == R.id.navigation_tasks_inbox)
        {
            _fragmentContainerMain = new FragmentTasksInbox();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Time);
        }
        else if (itemSelected == R.id.navigation_tasks_today)
        {
            _fragmentContainerMain = new FragmentTasksToday();
            globalVariable.set_mainActivity_typeResource(EnumTypeResource.Time);
        }

        if (_fragmentContainerMain != null)
            openFragmentContainerMain(_fragmentContainerMain);

        SetTitle();

        RefreshToolbarTopLeft();
        RefreshToolbarTopRightBase();

        return true;
    }

    public void SetTitle()
    {
        SetTitle(_navigationId);
    }

    public void SetTitle(int navigationId)
    {
        if (navigationId == R.id.navigation_cabinet)
            textViewTitle.setText(R.string.cabinet);
        if (navigationId == R.id.navigation_main_settings)
            textViewTitle.setText(R.string.main_settings);
        else if (navigationId == R.id.navigation_money_dream)
            textViewTitle.setText(R.string.money_your_dream);
        else if (navigationId == R.id.navigation_money_target)
            textViewTitle.setText(((IGetTitle) _fragmentContainerMain).GetTitle(getApplicationContext()));
        else if (navigationId == R.id.navigation_money_increase)
            textViewTitle.setText(R.string.money_increase);
        else if (navigationId == R.id.navigation_money_portfolio)
            textViewTitle.setText(R.string.money_portfolio);
        else if (navigationId == R.id.navigation_money_typeasset)
            textViewTitle.setText(R.string.money_typeasset);
        else if (navigationId == R.id.navigation_money_asset)
            textViewTitle.setText(R.string.money_asset);
        else if (navigationId == R.id.navigation_money_board)
            textViewTitle.setText(R.string.money_board);
        else if (navigationId == R.id.navigation_money_incoming)
            textViewTitle.setText(R.string.money_your_incoming);
        else if (navigationId == R.id.navigation_money_incoming_fact)
            textViewTitle.setText(R.string.money_your_incoming);
        else if (navigationId == R.id.navigation_money_planfact_income)
            textViewTitle.setText(R.string.money_planfact_income);
        else if (navigationId == R.id.navigation_money_planfact_cost)
            textViewTitle.setText(R.string.money_planfact_cost);
        else if (navigationId == R.id.navigation_money_capital)
            textViewTitle.setText(R.string.money_capital);
        else if (navigationId == R.id.navigation_money_category)
        {
            int title = R.string.money_category_wallet;
            GlobalClass globalVar = (GlobalClass) getApplicationContext();
            if (globalVar.get_moneyCategorySettingEnumTypeCategory().equals(EnumTypeCategory.Increase))
                title = R.string.money_category_increase;
            else if (globalVar.get_moneyCategorySettingEnumTypeCategory().equals(EnumTypeCategory.Cost))
                title = R.string.money_category_cost;
            textViewTitle.setText(title);
        }
        else if (navigationId == R.id.navigation_param)
            textViewTitle.setText(R.string.params);
        else if (navigationId == R.id.navigation_tag)
            textViewTitle.setText(R.string.tags);
        else if (navigationId == R.id.navigation_money_costs)
            textViewTitle.setText(R.string.money_your_cost);
        else if (navigationId == R.id.navigation_money_costs_fact)
            textViewTitle.setText(R.string.money_your_cost);
        else if (navigationId == R.id.navigation_money_accounting)
            textViewTitle.setText(R.string.money_your_accounting);
        else if (navigationId == R.id.navigation_money_pnl)
            textViewTitle.setText(R.string.money_your_acc_oper);
        else if (navigationId == R.id.navigation_credit)
            textViewTitle.setText(R.string.money_credit);
        else if (navigationId == R.id.navigation_tasks_project)
            textViewTitle.setText(R.string.tasks_your_project);
        else if (navigationId == R.id.navigation_tasks_tasks)
            textViewTitle.setText(R.string.tasks_your_tasks);
        else if (navigationId == R.id.navigation_tasks_tasks_fromproject)
            textViewTitle.setText(R.string.tasks_your_tasks);
        else if (navigationId == R.id.navigation_tasks_inbox)
            textViewTitle.setText(R.string.tasks_your_tasks_inbox);
        else if (navigationId == R.id.navigation_tasks_today)
            textViewTitle.setText(R.string.tasks_your_tasks_today);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            if (_fragmentContainerMain != null)
                ((IFragmentBack) _fragmentContainerMain).ClickToBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RefreshToolbarTopLeft()
    {
        if (getSupportActionBar() == null)
            SetToolbarLeft();

        if (_fragmentContainerMain instanceof IFragmentBack)
        {
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        else
        {
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setTitle("");
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            }
        }
    }

    //region Внутренний класс
    private static class ToolBarOnce
    {
        Integer ButtonId;
        Integer MenuId;
        Boolean Visible;
        View.OnClickListener onClick;

        public ToolBarOnce(Integer ButtonId, Integer MenuId, Boolean Visible, View.OnClickListener onClick)
        {
            this.ButtonId = ButtonId;
            this.MenuId = MenuId;
            this.Visible = Visible;
            this.onClick = onClick;
        }
    }
    //endregion

    @SuppressLint("RestrictedApi")
    public void RefreshToolbarTopRightBase()
    {
        boolean visible;
        ArrayList<ToolBarOnce> toolBarOnces = new ArrayList<>();
        ArrayList<ToolBarOnce> toolBarOnces2 = new ArrayList<>();

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        visible = _fragmentContainerMain instanceof IFragmentMinus;
        if (visible)
            visible = ((IFragmentMinus) _fragmentContainerMain).ShowMinus(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_minus, R.menu.navigation050_minus,
                visible, visible ? ((IFragmentMinus) _fragmentContainerMain).onClickToMinus() : null));

        visible = _fragmentContainerMain instanceof IFragmentPlus;
        if (visible)
            visible = ((IFragmentPlus) _fragmentContainerMain).ShowPlus(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_plus, R.menu.navigation051_plus,
                visible, visible ? ((IFragmentPlus) _fragmentContainerMain).onClickToPlus() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowTargetInJob;
        if (visible)
            visible = ((IFragmentShowTargetInJob) _fragmentContainerMain).ShowTargetInJob(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showtargetinjob, R.menu.navigation060_showtargetinjob,
                visible, visible ? ((IFragmentShowTargetInJob) _fragmentContainerMain).onClickToShowTargetInJob() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowTargetFinished;
        if (visible)
            visible = ((IFragmentShowTargetFinished) _fragmentContainerMain).ShowTargetFinished(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showtargetfinished, R.menu.navigation061_showtargetfinished,
                visible, visible ? ((IFragmentShowTargetFinished) _fragmentContainerMain).onClickToShowTargetFinished() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowTargetAll;
        if (visible)
            visible = ((IFragmentShowTargetAll) _fragmentContainerMain).ShowTargetAll(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showtargetall, R.menu.navigation062_showtargetall,
                visible, visible ? ((IFragmentShowTargetAll) _fragmentContainerMain).onClickToShowTargetAll() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncreasePercent;
        if (visible)
            visible = ((IFragmentShowIncreasePercent) _fragmentContainerMain).ShowIncreasePercent(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showincreaseprecent, R.menu.navigation070_showincreasepercent,
                visible, visible ? ((IFragmentShowIncreasePercent) _fragmentContainerMain).onClickToShowIncreasePercent() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncreasePercent;
        if (visible)
            visible = ((IFragmentShowIncreasePercent) _fragmentContainerMain).HideIncreasePercent(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_hideincreaseprecent, R.menu.navigation071_hideincreasepercent,
                visible, visible ? ((IFragmentShowIncreasePercent) _fragmentContainerMain).onClickToHideIncreasePercent() : null));

        visible = _fragmentContainerMain instanceof IFragmentExpandAll;
        if (visible)
            visible = ((IFragmentExpandAll) _fragmentContainerMain).needExpandButton(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_expandall, R.menu.navigation201_expandall,
                visible, visible ? ((IFragmentExpandAll) _fragmentContainerMain).onClickExpandAll() : null));

        visible = _fragmentContainerMain instanceof IFragmentCollapseAll;
        if (visible)
            visible = ((IFragmentCollapseAll) _fragmentContainerMain).needCollapseButton(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_collapseall, R.menu.navigation200_collapseall,
                visible, visible ? ((IFragmentCollapseAll) _fragmentContainerMain).onClickCollapseAll() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowFullData;
        if (visible)
            visible = ((IFragmentShowFullData) _fragmentContainerMain).ShowFullData(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showfulldata, R.menu.navigation210_showfulldata,
                visible, visible ? ((IFragmentShowFullData) _fragmentContainerMain).onClickToShowFullData() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowFullData;
        if (visible)
            visible = ((IFragmentShowFullData) _fragmentContainerMain).HideFullData(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showhalfdata, R.menu.navigation211_showhalfdata,
                visible, visible ? ((IFragmentShowFullData) _fragmentContainerMain).onClickToHideFullData() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowBlankData;
        if (visible)
            visible = ((IFragmentShowBlankData) _fragmentContainerMain).ShowBlankData(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showalldata, R.menu.navigation220_showalldata,
                visible, visible ? ((IFragmentShowBlankData) _fragmentContainerMain).onClickToShowBlankData() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowBlankData;
        if (visible)
            visible = ((IFragmentShowBlankData) _fragmentContainerMain).HideBlankData(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showfilleddata, R.menu.navigation221_showfilleddata,
                visible, visible ? ((IFragmentShowBlankData) _fragmentContainerMain).onClickToHideBlankData() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowPercent;
        if (visible)
            visible = ((IFragmentShowPercent) _fragmentContainerMain).HidePercent(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_hideprecent, R.menu.navigation230_hidepercent,
                visible, visible ? ((IFragmentShowPercent) _fragmentContainerMain).onClickToHidePercent() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowPercent;
        if (visible)
            visible = ((IFragmentShowPercent) _fragmentContainerMain).ShowPercent(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_showprecent, R.menu.navigation231_showpercent,
                visible, visible ? ((IFragmentShowPercent) _fragmentContainerMain).onClickToShowPercent() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncomingAndCostBase;
        if (visible)
            visible = ((IFragmentShowIncomingAndCostBase) _fragmentContainerMain).ShowIncomingAndCostBase(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_show4basecategory, R.menu.navigation400_show4basecategory,
                visible, visible ? ((IFragmentShowIncomingAndCostBase) _fragmentContainerMain).onClickToShowIncomingAndCostBase() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncomingAndCostAdvanced;
        if (visible)
            visible = ((IFragmentShowIncomingAndCostAdvanced) _fragmentContainerMain).ShowIncomingAndCostAdvanced(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_show4advancedcategory, R.menu.navigation401_show4advancedcategory,
                visible, visible ? ((IFragmentShowIncomingAndCostAdvanced) _fragmentContainerMain).onClickToShowIncomingAndCostAdvanced() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncomingAndCostAdvancedPlus;
        if (visible)
            visible = ((IFragmentShowIncomingAndCostAdvancedPlus) _fragmentContainerMain).ShowIncomingAndCostAdvancedPlus(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_show4advancedpluscategory, R.menu.navigation402_show4advancedpluscategory,
                visible, visible ? ((IFragmentShowIncomingAndCostAdvancedPlus) _fragmentContainerMain).onClickToShowIncomingAndCostAdvancedPlus() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncomingAndCostProject;
        if (visible)
            visible = ((IFragmentShowIncomingAndCostProject) _fragmentContainerMain).ShowIncomingAndCostProject(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_show4projectcategory, R.menu.navigation403_show4projectcategory,
                visible, visible ? ((IFragmentShowIncomingAndCostProject) _fragmentContainerMain).onClickToShowIncomingAndCostProject() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncomingAndCostAll;
        if (visible)
            visible = ((IFragmentShowIncomingAndCostAll) _fragmentContainerMain).ShowIncomingAndCostAll(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_show4allcategory, R.menu.navigation404_show4allcategory,
                visible, visible ? ((IFragmentShowIncomingAndCostAll) _fragmentContainerMain).onClickToShowIncomingAndCostAll() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowIncomingAndCostProjectPlus;
        if (visible)
            visible = ((IFragmentShowIncomingAndCostProjectPlus) _fragmentContainerMain).ShowIncomingAndCostProjectPlus(getApplicationContext());
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_show4projectpluscategory, R.menu.navigation405_show4projectpluscategory,
                visible, visible ? ((IFragmentShowIncomingAndCostProjectPlus) _fragmentContainerMain).onClickToShowIncomingAndCostProjectPlus() : null));

        visible = _fragmentContainerMain instanceof IFragmentGotoSocial;
        visible = visible && ((IFragmentGotoSocial) _fragmentContainerMain).ShowGotoSocialButton(
                getApplicationContext());
        imageViewGotoSocial.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        imageViewGotoSocial.setOnClickListener(
                visible ?
                        view ->
                        {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(getString(R.string.TelegramChannelUrl)));
                            startActivity(browserIntent);
                        } :
                        null);

        visible = _fragmentContainerMain instanceof IFragmentGotoCabinet;
        visible = visible && ((IFragmentGotoCabinet) _fragmentContainerMain).ShowGotoCabinetButton(
                getApplicationContext());
        imageViewGotoCabinet.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        imageViewGotoCabinet.setOnClickListener(
                visible ?
                        view -> NavigationItemSelected(R.id.navigation_cabinet) :
                        null);

        visible = _fragmentContainerMain instanceof IFragmentAddItem;
        visible = visible && ((IFragmentAddItem) _fragmentContainerMain).ShowAddItemButton();
        imageViewAddItem.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        imageViewAddItem.setOnClickListener(
                visible ? ((IFragmentAddItem) _fragmentContainerMain).onClickToAdd() : null);


        {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) imageViewMascot.getLayoutParams();
            lp.setMarginEnd(visible ?
                    (int) getApplicationContext().getResources().getDimension(
                            R.dimen.ImageViewMascotAddMarginEnd) : 0);
            imageViewMascot.setLayoutParams(lp);
        }
        visible = _fragmentContainerMain instanceof IFragmentMascotToAdd;
        visible = visible && ((IFragmentMascotToAdd) _fragmentContainerMain).ShowMascotButtonToAdd(getApplicationContext());
        imageViewMascot.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        imageViewMascot.setOnClickListener(
                visible ? ((IFragmentMascotToAdd) _fragmentContainerMain).onClickToMascotToAdd() : null);

        visible = _fragmentContainerMain instanceof IFragmentDohodRashod &&
                ((IFragmentDohodRashod)_fragmentContainerMain).GetTypeCategory(getApplicationContext()) == EnumTypeCategory.Cost;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_dohod, R.menu.navigation780_dohod,
                visible, visible ? ((IFragmentDohodRashod)_fragmentContainerMain).onClickToSetDohod() : null));

        visible = _fragmentContainerMain instanceof IFragmentDohodRashod &&
                ((IFragmentDohodRashod)_fragmentContainerMain).GetTypeCategory(getApplicationContext()) == EnumTypeCategory.Increase;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_rashod, R.menu.navigation781_rashod,
                visible, visible ? ((IFragmentDohodRashod)_fragmentContainerMain).onClickToSetRashod() : null));

        visible = _fragmentContainerMain instanceof IFragmentPlanFact &&
                ((IFragmentPlanFact)_fragmentContainerMain).needPlanButton(getApplicationContext());
//                ((IFragmentPlanFact)_fragmentContainerMain).GetTypeData() == EnumTypeData.Fact;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_plan, R.menu.navigation790_plan,
                visible, visible ? ((IFragmentPlanFact)_fragmentContainerMain).onClickToSetPlan() : null));

        visible = _fragmentContainerMain instanceof IFragmentPlanFact &&
                ((IFragmentPlanFact)_fragmentContainerMain).needFactButton(getApplicationContext());
//                ((IFragmentPlanFact)_fragmentContainerMain).GetTypeData() == EnumTypeData.Plan;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_fact, R.menu.navigation791_fact,
                visible, visible ? ((IFragmentPlanFact)_fragmentContainerMain).onClickToSetFact() : null));

        visible = _fragmentContainerMain instanceof IFragmentTableGraph &&
                ((IFragmentTableGraph)_fragmentContainerMain).GetTypeView() == EnumTypeView.Table;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_graph, R.menu.navigation800_graph,
                visible, visible ? ((IFragmentTableGraph)_fragmentContainerMain).onClickToSetGraph() : null));

        visible = _fragmentContainerMain instanceof IFragmentTableGraph &&
                ((IFragmentTableGraph)_fragmentContainerMain).GetTypeView() == EnumTypeView.Graph;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_table, R.menu.navigation801_table,
                visible, visible ? ((IFragmentTableGraph)_fragmentContainerMain).onClickToSetTable() : null));

        visible = _fragmentContainerMain instanceof IFragmentDeleteAll;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_deleteall, R.menu.navigation810_deleteall,
                visible, visible ? ((IFragmentDeleteAll)_fragmentContainerMain).onClickDeleteAll() : null));

        visible = _fragmentContainerMain instanceof IFragmentFinishedTargets;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_finishedtargets, R.menu.navigation877_finishedtargets,
                visible, visible ? ((IFragmentFinishedTargets)_fragmentContainerMain).onClickToFinishedTargets() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowTargetListForYear;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_listtargetforyear, R.menu.navigation878_targetperyear,
                visible, visible ? ((IFragmentShowTargetListForYear)_fragmentContainerMain).onClickToShowTargetListForYear() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowTargetList;
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_listtarget, R.menu.navigation879_targetlist,
                visible, visible ? ((IFragmentShowTargetList)_fragmentContainerMain).onClickToShowTargetList() : null));

        visible = _fragmentContainerMain instanceof IFragmentShowTransactionList;
        if (visible)
            visible = ((IFragmentShowTransactionList)_fragmentContainerMain).ShowButtonShowList(
                    globalVariable.get_moneyCategorySettingEnumTypeCategory(), getApplicationContext());
        toolBarOnces.add(new ToolBarOnce(R.id.navig_list, R.menu.navigation880_list,
                visible, visible ? ((IFragmentShowTransactionList)_fragmentContainerMain).onClickToShowList() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_list, R.menu.navigation880_list,
                visible, visible ? ((IFragmentShowTransactionList)_fragmentContainerMain).onClickToShowList() : null));

        visible = _fragmentContainerMain instanceof IFragmentCredit;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_credit, R.menu.navigation881_credit,
                visible, visible ? ((IFragmentCredit)_fragmentContainerMain).onClickCredit() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_credit, R.menu.navigation881_credit,
                visible, visible ? ((IFragmentCredit)_fragmentContainerMain).onClickCredit() : null));

        visible = _fragmentContainerMain instanceof IFragmentManageMoneyCategory;
        int menuId = R.menu.navigation890_wallet_category;
        if (visible)
        {
            if (((IFragmentManageMoneyCategory) _fragmentContainerMain).getTypeCategory().equals(EnumTypeCategory.Increase))
                menuId = R.menu.navigation890_income_category;
            else if (((IFragmentManageMoneyCategory) _fragmentContainerMain).getTypeCategory().equals(EnumTypeCategory.Cost))
                menuId = R.menu.navigation890_expense_category;
        }
        toolBarOnces.add(new ToolBarOnce(R.id.navig_category, menuId,
                visible, visible ? ((IFragmentManageMoneyCategory)_fragmentContainerMain).onClickToManageMoneyCategory() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_category, menuId,
                visible, visible ? ((IFragmentManageMoneyCategory)_fragmentContainerMain).onClickToManageMoneyCategory() : null));

        visible = _fragmentContainerMain instanceof IFragmentPortfolio;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_portfolio, R.menu.navigation870_portfolio,
                visible, visible ? ((IFragmentPortfolio)_fragmentContainerMain).onClickToPortfolio() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_portfolio, R.menu.navigation870_portfolio,
                visible, visible ? ((IFragmentPortfolio)_fragmentContainerMain).onClickToPortfolio() : null));

        visible = _fragmentContainerMain instanceof IFragmentTypeAsset;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_typeasset, R.menu.navigation871_typeasset,
                visible, visible ? ((IFragmentTypeAsset)_fragmentContainerMain).onClickToTypeAsset() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_typeasset, R.menu.navigation871_typeasset,
                visible, visible ? ((IFragmentTypeAsset)_fragmentContainerMain).onClickToTypeAsset() : null));

        visible = _fragmentContainerMain instanceof IFragmentAsset;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_asset, R.menu.navigation872_asset,
                visible, visible ? ((IFragmentAsset)_fragmentContainerMain).onClickToAsset() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_asset, R.menu.navigation872_asset,
                visible, visible ? ((IFragmentAsset)_fragmentContainerMain).onClickToAsset() : null));

        visible = _fragmentContainerMain instanceof IFragmentIncrease;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_increase, R.menu.navigation889_increase,
                visible, visible ? ((IFragmentIncrease)_fragmentContainerMain).onClickToIncrease() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_increase, R.menu.navigation889_increase,
                visible, visible ? ((IFragmentIncrease)_fragmentContainerMain).onClickToIncrease() : null));

        visible = _fragmentContainerMain instanceof IFragmentParam;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_param, R.menu.navigation891_param,
                visible, visible ? ((IFragmentParam)_fragmentContainerMain).onClickToParam() : null));

        visible = _fragmentContainerMain instanceof IFragmentPlanFactMoney;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_planfactmoney, R.menu.navigation892_planfactmoney,
                visible, visible ? ((IFragmentPlanFactMoney)_fragmentContainerMain).onClickToPlanFactMoney() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_planfactmoney, R.menu.navigation892_planfactmoney,
                visible, visible ? ((IFragmentPlanFactMoney)_fragmentContainerMain).onClickToPlanFactMoney() : null));

        visible = _fragmentContainerMain instanceof IFragmentTag;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_tag, R.menu.navigation893_tag,
                visible, visible ? ((IFragmentTag)_fragmentContainerMain).onClickToTag() : null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_tag, R.menu.navigation893_tag,
                visible, visible ? ((IFragmentTag)_fragmentContainerMain).onClickToTag() : null));

        visible = _fragmentContainerMain instanceof IFragmentSettings;
        if (visible)
            visible = ((IFragmentSettings) _fragmentContainerMain).ShowFormSettingButton();
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_settings, R.menu.navigation900_settings, visible,
                visible ? ((IFragmentSettings) _fragmentContainerMain).onClickSettings() : null));

        //region Скрытое меню
        visible = _fragmentContainerMain instanceof IFragmentSettings;
        toolBarOnces.add(new ToolBarOnce(R.id.navig_toolbar, R.menu.navigation910_toolbar, visible, null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_hide_toolbar, R.menu.navigation901_hide_toolbar, visible, null));

        visible = !(_fragmentContainerMain instanceof FragmentMainSettings);
        toolBarOnces.add(new ToolBarOnce(R.id.navig_main_settings, R.menu.navigation930_main_settings, visible, null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_main_settings, R.menu.navigation930_main_settings, visible, null));

        visible = !(_fragmentContainerMain instanceof FragmentCabinet);
        toolBarOnces.add(new ToolBarOnce(R.id.navig_cabinet, R.menu.navigation940_cabinet, visible, null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_cabinet, R.menu.navigation940_cabinet, visible, null));

        visible = _fragmentContainerMain instanceof IFragmentLegend && globalVariable.get_showMascot();
        toolBarOnces.add(new ToolBarOnce(R.id.navig_legend, R.menu.navigation950_legend, visible, null));
        toolBarOnces2.add(new ToolBarOnce(R.id.navig_legend, R.menu.navigation950_legend, visible, null));
        //endregion

        toolbarTopRight.getMenu().clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            toolbarTopRight.getMenu().setGroupDividerEnabled(true);
        for (ToolBarOnce toolBarOnce: toolBarOnces)
        {
            ActionMenuItemView actionMenuItemView = toolbarTopRight.findViewById(toolBarOnce.ButtonId);

            if (toolBarOnce.Visible)
            {
                if (actionMenuItemView == null)
                {
                    toolbarTopRight.inflateMenu(toolBarOnce.MenuId);
                    actionMenuItemView = toolbarTopRight.findViewById(toolBarOnce.ButtonId);
                }

                if (actionMenuItemView != null)
                {
                    actionMenuItemView.setOnClickListener(toolBarOnce.onClick);
                    actionMenuItemView.getItemData().setVisible(true);
                }
            }
            else if (actionMenuItemView != null)
                actionMenuItemView.getItemData().setVisible(false);
        }

        toolbarTopRight2.getMenu().clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            toolbarTopRight2.getMenu().setGroupDividerEnabled(true);
        for (ToolBarOnce toolBarOnce: toolBarOnces2)
        {
            ActionMenuItemView actionMenuItemView = toolbarTopRight2.findViewById(toolBarOnce.ButtonId);

            if (toolBarOnce.Visible)
            {
                if (actionMenuItemView == null)
                {
                    toolbarTopRight2.inflateMenu(toolBarOnce.MenuId);
                    actionMenuItemView = toolbarTopRight2.findViewById(toolBarOnce.ButtonId);
                }

                if (actionMenuItemView != null)
                {
                    actionMenuItemView.setOnClickListener(toolBarOnce.onClick);
                    actionMenuItemView.getItemData().setVisible(true);
                }
            }
            else if (actionMenuItemView != null)
                actionMenuItemView.getItemData().setVisible(false);
        }
    }

//    public void SetVisibilityImageViewAddItem(boolean visible)
//    {
//        imageViewAddItem.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//    }

    private void openFragmentContainerMain(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //endregion

    //region Toolbar Click
    public void onCategoryClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentManageMoneyCategory)
        {
            IFragmentManageMoneyCategory ifr = (IFragmentManageMoneyCategory) _fragmentContainerMain;
            GlobalClass globalVar = (GlobalClass) getApplicationContext();
            globalVar.set_moneyCategorySettingEnumTypeCategory(ifr.getTypeCategory());
            globalVar.set_moneyCategorySettings_fromItem(ifr.getFromItem());
            ifr.onClickToManageMoneyCategory().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onTransactionListClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentShowTransactionList)
        {
            IFragmentShowTransactionList ifr = (IFragmentShowTransactionList) _fragmentContainerMain;
            ifr.onClickToShowList().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onTargetListClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentShowTargetList)
        {
            IFragmentShowTargetList ifr = (IFragmentShowTargetList) _fragmentContainerMain;
            ifr.onClickToShowTargetList().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onTargetListForYearClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentShowTargetListForYear)
        {
            IFragmentShowTargetListForYear ifr = (IFragmentShowTargetListForYear) _fragmentContainerMain;
            ifr.onClickToShowTargetListForYear().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onFinishedTargetsClick(MenuItem itme)
    {
        if (_fragmentContainerMain instanceof IFragmentFinishedTargets)
        {
            IFragmentFinishedTargets ifr = (IFragmentFinishedTargets) _fragmentContainerMain;
            ifr.onClickToFinishedTargets().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onCreditsClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentCredit)
        {
            IFragmentCredit ifr = (IFragmentCredit) _fragmentContainerMain;
            ifr.onClickCredit().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onParamsClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentParam)
        {
            IFragmentParam ifr = (IFragmentParam) _fragmentContainerMain;
            ifr.onClickToParam().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onTagsClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentTag)
        {
            IFragmentTag ifr = (IFragmentTag) _fragmentContainerMain;
            ifr.onClickToTag().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onPortfolioClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentPortfolio)
        {
            IFragmentPortfolio ifr = (IFragmentPortfolio) _fragmentContainerMain;
            ifr.onClickToPortfolio().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onTypeAssetClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentTypeAsset)
        {
            IFragmentTypeAsset ifr = (IFragmentTypeAsset) _fragmentContainerMain;
            ifr.onClickToTypeAsset().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onAssetClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentAsset)
        {
            IFragmentAsset ifr = (IFragmentAsset) _fragmentContainerMain;
            ifr.onClickToAsset().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onIncreaseClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentIncrease)
        {
            IFragmentIncrease ifr = (IFragmentIncrease) _fragmentContainerMain;
            ifr.onClickToIncrease().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onPlanFactMoneyClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentPlanFactMoney)
        {
            IFragmentPlanFactMoney ifr = (IFragmentPlanFactMoney) _fragmentContainerMain;
            ifr.onClickToPlanFactMoney().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onGraphClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentTableGraph)
        {
            IFragmentTableGraph ifr = (IFragmentTableGraph) _fragmentContainerMain;
            ifr.onClickToSetGraph().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onTableClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentTableGraph)
        {
            IFragmentTableGraph ifr = (IFragmentTableGraph) _fragmentContainerMain;
            ifr.onClickToSetTable().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onFormSettingsClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentSettings)
        {
            IFragmentSettings ifr = (IFragmentSettings) _fragmentContainerMain;
            ifr.onClickSettings().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onMainSettingsClick(MenuItem item)
    {
        NavigationItemSelected(R.id.navigation_main_settings);
    }

    public void onFormLegendClick(MenuItem item)
    {
        if (_fragmentContainerMain instanceof IFragmentLegend)
        {
            IFragmentLegend ifr = (IFragmentLegend) _fragmentContainerMain;
            ifr.onClickToLegend().onClick(_fragmentContainerMain.getView());
        }
    }

    public void onCabinetClick(MenuItem item)
    {
        NavigationItemSelected(R.id.navigation_cabinet);
    }

    public void onFormToolbarShowClick(MenuItem item)
    {
        ChangeToolbarRight2Visible(true);
    }

    public void onFormToolbarHideClick(MenuItem item)
    {
        ChangeToolbarRight2Visible(false);
    }

    private void ChangeToolbarRight2Visible(boolean visible)
    {
        toolbarTopRight.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
        toolbarTopRight2.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
    //endregion

    //region ActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode)
        {
            case REQUEST_GALLERY_FOR_MONEY_DREAM_POPUP:
            case REQUEST_GALLERY_FOR_MONEY_TARGET_POPUP:
                if (resultCode == RESULT_OK)
                {
                    if (_fragmentContainerMain != null)
                        ((IFragmentSetPopupImage) _fragmentContainerMain).setPopupImage(imageReturnedIntent.getData());
                }
                break;
//            case REQUEST_HUAWEI_BILLING_PURCHASE:
//                if (imageReturnedIntent == null)
//                {
//                    Log.e("onActivityResult", "purchase data is null");
//                    return;
//                }
//                // Call the parsePurchaseResultInfoFromIntent method to parse the payment result.
//                PurchaseResultInfo purchaseResultInfo = Iap.getIapClient(this).
//                        parsePurchaseResultInfoFromIntent(imageReturnedIntent);
//
//                switch(purchaseResultInfo.getReturnCode())
//                {
//                    case OrderStatusCode.ORDER_STATE_CANCEL:
//                        Log.d("Billing: purchase product",
//                                getResources().getString(R.string.PurchaseCancelled));
//                        Toast.makeText(getApplicationContext(), R.string.PurchaseCancelled,
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case OrderStatusCode.ORDER_STATE_FAILED:
//                        Log.d("Billing: purchase product",
//                                getResources().getString(R.string.OrderStateFailed));
//                        Toast.makeText(getApplicationContext(), R.string.PurchaseCancelled,
//                                Toast.LENGTH_SHORT).show();
//                    case OrderStatusCode.ORDER_STATE_DEFAULT_CODE:
//                        Log.d("Billing: purchase product",
//                                getResources().getString(R.string.OrderStateDefaultCode));
//                        Toast.makeText(getApplicationContext(), R.string.PurchaseCancelled,
//                                Toast.LENGTH_SHORT).show();
//                    case OrderStatusCode.ORDER_PRODUCT_OWNED:
//                        // Check whether the delivery is successful.
//                        Log.d("Billing: purchase product",
//                                getResources().getString(R.string.OrderProductOwned));
//                        Toast.makeText(getApplicationContext(), R.string.PurchaseCancelled,
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case OrderStatusCode.ORDER_STATE_SUCCESS:
//                        // The payment is successful.
//                        //TODO: обновиться статус подписки
//                        String inAppPurchaseData = purchaseResultInfo.getInAppPurchaseData();
//                        String inAppPurchaseDataSignature = purchaseResultInfo.getInAppDataSignature();
//                        // Verify the signature using your app's IAP public key.
//                        // If the verification is successful, check the consistency of the information including productId, price, and currency in InAppPurchaseData between HUAWEI IAP and your app. This is a mandatory step. If relevant information is consistent, deliver the product.
//                        // Call the consumeOwnedPurchase API to consume the product after delivery if the product is a consumable.
//                        break;
//                    default:
//                        break;
//                }
        }
    }
    //endregion

    //region Subscription
    private void UpdateSubscriptionInfo4Test(GlobalClass globalVariable)
    {
        globalVariable.set_purchaseId("1");
        globalVariable.set_productId("1");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        globalVariable.set_startDate(calendar);
        globalVariable.set_endDate(calendar);
    }

    public void UpdateSubscriptionInfo(GlobalClass globalVariable)
    {
        if (GlobalClass.SubscrGoogle())
            try
            {
                UpdateSubscrInfoFromGoogle(globalVariable);
            }
            catch (Exception e)
            {
                Log.d("Error", e.getMessage());
            }

        else if (GlobalClass.SubscrRustore())
            UpdateSubscrInfoFromRustore(globalVariable);
//        else if (GlobalClass.SubscrHuawei())
//            UpdateSubscrInfoFromHuawei(globalVariable);
    }

    private void UpdateSubscrInfoFromGoogle(GlobalClass globalVariable)
    {
        BillingClient.Builder builder = BillingClient.newBuilder(this);
        builder.setListener((billingResult, list) -> {});
        builder.enablePendingPurchases();
        BillingClient billingClient = builder.build();

        billingClient.startConnection(new BillingClientStateListener()
        {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult)
            {
                if (billingResult.getResponseCode() != OK)
                    return;

                QueryPurchasesParams.Builder builder = QueryPurchasesParams.newBuilder();
                builder.setProductType(BillingClient.SkuType.SUBS);
                QueryPurchasesParams params1 = builder.build();

                billingClient.queryPurchasesAsync(params1, (billingResult1, list) ->
                {
                    if (billingResult1.getResponseCode() != OK)
                        return;

                    for (com.android.billingclient.api.Purchase purchase: list)
                    {
                        //TODO: найти параметр
                        if (purchase.getPurchaseState() == 1)
                        {
                            globalVariable.set_purchaseId(purchase.getOrderId());
                            globalVariable.set_productId(purchase.getProducts().get(0));

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date(purchase.getPurchaseTime()));
                            globalVariable.set_startDate(calendar);
                            globalVariable.set_endDate(calendar);

                            List<String> ids = Collections.singletonList(purchase.getProducts().get(0));
                            List<QueryProductDetailsParams.Product> products = ids.stream().map(productId ->
                                    QueryProductDetailsParams.Product.newBuilder()
                                            .setProductId(productId)
                                            .setProductType(BillingClient.ProductType.SUBS)
                                            .build()
                            ).collect(Collectors.toList());

                            QueryProductDetailsParams.Builder builder2 = QueryProductDetailsParams.newBuilder();
                            builder2.setProductList(products);
                            QueryProductDetailsParams params2 = builder2.build();

                            billingClient.queryProductDetailsAsync(params2, (billingResult2, list2) ->
                            {
                                if (billingResult2.getResponseCode() != OK)
                                    return;

                                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                                if (list2.size() == 1)
                                {
                                    ProductDetails productDetails2 = list2.get(0);

                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(Calendar.DATE, calendar.get(Calendar.DATE));
                                    calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                                    calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

                                    String s = productDetails2.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getBillingPeriod();
                                    String s1 = s.substring(1, 2);
                                    String s2 = s.substring(2, 3);

                                    switch (s2)
                                    {
                                        case "Y":
                                            calendar1.add(Calendar.YEAR, Integer.parseInt(s1));
                                            break;

                                        case "M":
                                            calendar1.add(Calendar.MONTH, Integer.parseInt(s1));
                                            break;

                                        default:
                                            calendar1.add(Calendar.DATE, Integer.parseInt(s1));
                                    }

                                    globalVariable.set_endDate(calendar1);
                                    globalVariable.SaveSharedPreferences();
                                }
                            });

                            SetResourceTextView(_navigationId);
                            globalVariable.SaveSharedPreferences();

                            break;
                        }
                    }
                });
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: getting purchase info Google", "Serivce Disconnected");
            }
        });
    }

    private void UpdateSubscrInfoFromRustore(GlobalClass globalVariable)
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getApplicationContext());

        PurchasesUseCase purchasesUseCase = billingClient.getPurchases();
        Task<List<Purchase>> tList = purchasesUseCase.getPurchases();

        tList.addOnSuccessListener(purchases ->
        {
            for (Purchase purchase: purchases)
            {
                if (CustomSubscription.GetProductListRustore().contains(purchase.getProductId()) &&
                        purchase.getPurchaseState() == PurchaseState.CONFIRMED &&
                        purchase.getProductType() == ProductType.SUBSCRIPTION)
                {
                    globalVariable.set_purchaseId(purchase.getPurchaseId());
                    globalVariable.set_productId(purchase.getProductId());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(purchase.getPurchaseTime());
                    globalVariable.set_startDate(calendar);
                    globalVariable.set_endDate(calendar);

                    Task<List<Product>> ttask =
                            billingClient.getProducts().getProducts(Collections.singletonList(purchase.getProductId()));

                    ttask.addOnSuccessListener(products ->
                    {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.DATE, calendar.get(Calendar.DATE));
                        calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                        calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

                        if (products.get(0).getSubscription() != null)
                        {
                            calendar1.add(Calendar.DATE, products.get(0).getSubscription().getSubscriptionPeriod().getDays());
                            calendar1.add(Calendar.MONTH, products.get(0).getSubscription().getSubscriptionPeriod().getMonths());
                            calendar1.add(Calendar.YEAR, products.get(0).getSubscription().getSubscriptionPeriod().getYears());
                        }

                        if (products.get(0).getSubscription().getFreeTrialPeriod() != null)
                        {
                            calendar1.add(Calendar.DATE, products.get(0).getSubscription().getFreeTrialPeriod().getDays());
                            calendar1.add(Calendar.MONTH, products.get(0).getSubscription().getFreeTrialPeriod().getMonths());
                            calendar1.add(Calendar.YEAR, products.get(0).getSubscription().getFreeTrialPeriod().getYears());
                        }

                        globalVariable.set_endDate(calendar1);
                        globalVariable.SaveSharedPreferences();
                        NavigationItemSelected(_navigationId);
                    });

//                    SetResourceTextView(_navigationId);
//                    globalVariable.SaveSharedPreferences();
//                    NavigationItemSelected(_navigationId);

                    return;
                }
            }

            globalVariable.ClearSubscription4Test();
            NavigationItemSelected(_navigationId);
        });

        tList.addOnFailureListener(throwable ->
        {
            Log.e("Billing: getting purchase info Rustore", throwable.getMessage() + " - " +
                    purchasesUseCase + " - " + billingClient + " - " + tList);
        });
    }

//    private void UpdateSubscrInfoFromHuawei(GlobalClass globalVariable)
//    {
//        //TODO: получить информацию о подписках Huawei
//        // Construct an OwnedPurchasesReq object.
//        OwnedPurchasesReq req = new OwnedPurchasesReq();
//        req.setPriceType(CustomSubscription.GetPriceTypeHuawei());
//        // Call the obtainOwnedPurchaseRecord API.
//        com.huawei.hmf.tasks.Task<OwnedPurchasesResult> task =
//                Iap.getIapClient(this).obtainOwnedPurchaseRecord(req);
//        task
//            .addOnSuccessListener(result ->
//            {
//                // Obtain the execution result.
//            })
//            .addOnFailureListener(e ->
//            {
//                if (e instanceof IapApiException)
//                {
//                    IapApiException apiException = (IapApiException) e;
//                    int returnCode = apiException.getStatusCode();
//                    Log.e("Billing: getting purchase info Huawei", e.getMessage() + ": " +
//                            returnCode);
//                }
//                else
//                {
//                    Log.e("Billing: getting purchase info Huawei", e.getMessage());
//                }
//            });
//    }
    //endregion
}