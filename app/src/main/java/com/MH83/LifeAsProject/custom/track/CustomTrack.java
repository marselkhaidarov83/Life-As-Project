package com.MH83.LifeAsProject.custom.track;

import android.app.Activity;
import android.app.Application;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.my.tracker.MyTracker;
import com.my.tracker.MyTrackerConfig;
import com.my.tracker.MyTrackerParams;

import java.util.HashMap;
import java.util.Map;

public class CustomTrack
{
    private static boolean _disabledTrack()
    {
        return GlobalClass.TestProgram() && !GlobalClass.SendTestTrack();
    }

    //region BaseMethods
    public static void InitiateMyTracker(GlobalClass globalVariable,
                                         Application application,
                                         Activity activity)
    {
        if (_disabledTrack())
            return;

        MyTrackerParams trackerParams = MyTracker.getTrackerParams();
        MyTrackerConfig trackerConfig = MyTracker.getTrackerConfig();

        trackerParams.setCustomUserId(globalVariable.get_deviceId());

        trackerConfig.setTrackingLaunchEnabled(true);
        trackerConfig.setAutotrackingPurchaseEnabled(true);
        trackerConfig.setTrackingLocationEnabled(true);

        MyTracker.initTracker(globalVariable.get_sdk_key_tracker(), application);
        MyTracker.trackLaunchManually(activity);

        MyTracker.trackLoginEvent(globalVariable.get_deviceId(), null);
    }

    public static void Track(String eventName, String paramName, String value)
    {
        if (_disabledTrack())
            return;

        String evtName = eventName;
        if (GlobalClass.SendTestTrack())
            evtName = "test_" + evtName;

        Map<String, String> eventParams = new HashMap<>();
        eventParams.put(paramName, value);
        MyTracker.trackEvent(evtName, eventParams);
        MyTracker.flush();
    }

    public static void TrackProgramOpen(GlobalClass globalVariable)
    {
        if (_disabledTrack())
            return;

        String paramProgramInstall = "programInstall";
        String paramProgramOpen = "programOpen";
        String eventProgramOpen = "eventProgramOpen";
        if (GlobalClass.SendTestTrack())
        {
            paramProgramInstall = "test_" + paramProgramInstall;
            paramProgramOpen = "test_" + paramProgramOpen;
            eventProgramOpen = "test_" + eventProgramOpen;
        }
        Map<String, String> eventParams = new HashMap<>();

        if (globalVariable.getFirstOpen())
        {
            globalVariable.setFirstOpen(false);
            globalVariable.SaveSharedPreferences();
            eventParams.put(paramProgramInstall, globalVariable.get_typeDevice().toString());
        }

        eventParams.put(paramProgramOpen, globalVariable.get_typeDevice().toString());
        MyTracker.trackEvent(eventProgramOpen, eventParams);
        MyTracker.flush();
    }
    //endregion

    //region PaidFormOpen
    public static void PaidFormOpenFromCabinet()
    {
        Track("eventPaid", "FormOpenFromCabinet", "1");
    }

    public static void PaidFormOpenFromParam()
    {
        Track("eventPaid", "FormOpenFromParam", "1");
    }

    public static void PaidFormOpenFromTag()
    {
        Track("eventPaid", "FormOpenFromTag", "1");
    }

    public static void PaidFormOpenFromParamValue()
    {
        Track("eventPaid", "FormOpenFromParamValue", "1");
    }

    public static void PaidFormOpenFromDream()
    {
        Track("eventPaid", "FormOpenFromDream", "1");
    }

    public static void PaidFormOpenFromCredit()
    {
        Track("eventPaid", "FormOpenFromCredit", "1");
    }

    public static void PaidFormOpenFromTarget()
    {
        Track("eventPaid", "FormOpenFromTarget", "1");
    }

    public static void PaidFormOpenFromIncrease()
    {
        Track("eventPaid", "FormOpenFromIncrease", "1");
    }

    public static void PaidFormOpenFromIncAndCost()
    {
        Track("eventPaid", "FormOpenFromIncAndCost", "1");
    }

    public static void PaidFormOpenFromPlan()
    {
        Track("eventPaid", "FormOpenFromPlan", "1");
    }

    public static void PaidFormOpenFromCategory()
    {
        Track("eventPaid", "FormOpenFromCategory", "1");
    }

    public static void PaidFormOpenFromAccounting()
    {
        Track("eventPaid", "FormOpenFromAccounting", "1");
    }

    public static void PaidFormOpenFromPNL()
    {
        Track("eventPaid", "FormOpenFromPNL", "1");
    }

    public static void PaidFormOpenFromMoneyPortfolio()
    {
        Track("eventPaid", "FormOpenFromMoneyPortfolio", "1");
    }

    public static void PaidFormOpenFromMoneyTypeAsset()
    {
        Track("eventPaid", "FormOpenFromMoneyTypeAsset", "1");
    }

    public static void PaidFormOpenFromMoneyLotAssetOperation()
    {
        Track("eventPaid", "FormOpenFromMoneyLotAssetOperation", "1");
    }

    public static void PaidFormOpenFromMoneyAsset()
    {
        Track("eventPaid", "FormOpenFromMoneyTypeAsset", "1");
    }

    public static void PaidFormOpened()
    {
        Track("eventPaid", "FormOpened", "1");
    }

    public static void PaidFormOpenFromTasksProject()
    {
        Track("eventPaid", "FormOpenFromTasksProject", "1");
    }

    public static void PaidFormOpenFromTasksTask()
    {
        Track("eventPaid", "FormOpenFromTasksTask", "1");
    }
    //endregion

    //region PaidStatus
    public static void PaidRustoreStarted(String price)
    {
        Track("eventPaid", "Rustore Started", price);
    }

    public static void PaidGoogleStarted(String price)
    {
        Track("eventPaid", "Google Started", price);
    }

    public static void PaidHuaweiStarted(String price)
    {
        Track("eventPaid", "Huawei Started", price);
    }

    public static void PaidRustoreSuccess(String price)
    {
        Track("eventPaid", "Rustore Success", price);
    }

    public static void PaidGoogleSuccess(String price)
    {
        Track("eventPaid", "Google Success", price);
    }

    public static void PaidHuaweiSuccess(String price)
    {
        Track("eventPaid", "Huawei Success", price);
    }

    public static void PaidRustoreCanceled(String price)
    {
        Track("eventPaid", "Rustore Canceled", price);
    }

    public static void PaidGoogleCanceled(String price)
    {
        Track("eventPaid", "Google Canceled", price);
    }

    public static void PaidHuaweiCanceled(String price)
    {
        Track("eventPaid", "Huawei Canceled", price);
    }

    public static void PaidRustoreFailure(String price)
    {
        Track("eventPaid", "Rustore Failure", price);
    }

    public static void PaidGoogleFailure(String price)
    {
        Track("eventPaid", "Google Failure", price);
    }

    public static void PaidHuaweiFailure(String price)
    {
        Track("eventPaid", "Huawei Failure", price);
    }

    public static void PaidRustoreInvalidPaymentState(String price)
    {
        Track("eventPaid", "Rustore InvalidPaymentState", price);
    }

    public static void PaidGoogleInvalidPaymentState(String price)
    {
        Track("eventPaid", "Google InvalidPaymentState", price);
    }

    public static void PaidHuaweiInvalidPaymentState(String price)
    {
        Track("eventPaid", "Huawei InvalidPaymentState", price);
    }

    public static void PaidRustoreFailureFail(String price)
    {
        Track("eventPaid", "Rustore FailureFail", price);
    }

    public static void PaidGoogleFailureFail(String price)
    {
        Track("eventPaid", "Google FailureFail", price);
    }

    public static void PaidHuaweiFailureFail(String price)
    {
        Track("eventPaid", "Huawei FailureFail", price);
    }
    //endregion

    //region TrackCount
    public static void TrackCountMeasure(int count)
    {
        Track("eventInputData", "measureCount", String.valueOf(count));
    }

    public static void TrackCountParam(int count)
    {
        Track("eventInputData", "paramCount", String.valueOf(count));
    }

    public static void TrackCountPlanValue(int count)
    {
        Track("eventInputData", "planValueCount", String.valueOf(count));
    }

    public static void TrackCountTag(int count)
    {
        Track("eventInputData", "tagCount", String.valueOf(count));
    }

    public static void TrackCountValue(int count)
    {
        Track("eventInputData", "valueCount", String.valueOf(count));
    }

    public static void TrackCountMoneyDream(int count)
    {
        Track("eventInputData", "moneyDreamCount", String.valueOf(count));
    }

    public static void TrackCountMoneyTarget(int count)
    {
        Track("eventInputData", "moneyTargetCount", String.valueOf(count));
    }

    public static void TrackCountMoneyCategory(int count)
    {
        Track("eventInputData", "moneyCategoryCount", String.valueOf(count));
    }

    public static void TrackCountMoneyIncAndCost(int count)
    {
        Track("eventInputData", "moneyIncAndCostCount", String.valueOf(count));
    }

    public static void TrackCountMoneyPlan(int count)
    {
        Track("eventInputData", "moneyPlan", String.valueOf(count));
    }

    public static void TrackCountMoneyPNL(int count)
    {
        Track("eventInputData", "moneyPNLCount", String.valueOf(count));
    }

    public static void TrackCountMoneyIncrease(int count)
    {
        Track("eventInputData", "moneyIncreaseCount", String.valueOf(count));
    }

    public static void TrackCountMoneyInc4Cost(int count)
    {
        Track("eventInputData", "moneyInc4CostCount", String.valueOf(count));
    }

    public static void TrackCountMoneyInc4CostSum(int count)
    {
        Track("eventInputData", "moneyInc4CostSumCount", String.valueOf(count));
    }

    public static void TrackCountMoneyCredit(int count)
    {
        Track("eventInputData", "moneyCreditCount", String.valueOf(count));
    }

    public static void TrackCountMoneyPortfolio(int count)
    {
        Track("eventInputData", "moneyPortfolioCount", String.valueOf(count));
    }

    public static void TrackCountMoneyTypeAsset(int count)
    {
        Track("eventInputData", "moneyTypeAssetCount", String.valueOf(count));
    }

    public static void TrackCountMoneyAsset(int count)
    {
        Track("eventInputData", "moneyAssetCount", String.valueOf(count));
    }

    public static void TrackCountMoneyLotAsset(int count)
    {
        Track("eventInputData", "moneyLotAssetCount", String.valueOf(count));
    }

    public static void TrackCountMoneyLotAssetOperation(int count)
    {
        Track("eventInputData", "moneyLotAssetCountOperation", String.valueOf(count));
    }

    public static void TrackCountTimeProject(int count)
    {
        Track("eventInputData", "timeProjectCount", String.valueOf(count));
    }

    public static void TrackCountTimePriority(int count)
    {
        Track("eventInputData", "timePriorityCount", String.valueOf(count));
    }

    public static void TrackCountTimeTask(int count)
    {
        Track("eventInputData", "timeTaskCount", String.valueOf(count));
    }

    public static void TrackCountPersonAssistant(int count)
    {
        Track("eventInputData", "personAssistantCount", String.valueOf(count));
    }

    public static void TrackCountPersonComm(int count)
    {
        Track("eventInputData", "personCommCount", String.valueOf(count));
    }
    //endregion

    //region TrackProjectNotification
    public static void TrackPNotifNeedDream()
    {
        Track("eventNotif", "projectNotif", "NeedDream");
    }

    public static void TrackPNotifFromNeedDream()
    {
        Track("eventNotif", "projectNotif", "FromNeedDream");
    }

    public static void TrackPNotifNeedTargetInDream()
    {
        Track("eventNotif", "projectNotif", "NeedTargetInDream");
    }

    public static void TrackPNotifFromNeedTargetInDream()
    {
        Track("eventNotif", "projectNotif", "FromNeedTargetInDream");
    }

    public static void TrackPNotifNeedTaskInProject()
    {
        Track("eventNotif", "projectNotif", "NeedTaskInProject");
    }

    public static void TrackPNotifFromNeedTaskInProject()
    {
        Track("eventNotif", "projectNotif", "FromNeedTaskInProject");
    }
    //endregion

    //region TrackMoneyNotification
    public static void TrackMNotifActualMonthPlanIncoming()
    {
        Track("eventNotif", "moneyNotif", "ActualMonthPlanIncoming");
    }

    public static void TrackMNotifFromActualMonthPlanIncoming()
    {
        Track("eventNotif", "moneyNotif", "FromActualMonthPlanIncoming");
    }

    public static void TrackMNotifActualMonthPlanCost()
    {
        Track("eventNotif", "moneyNotif", "ActualMonthPlanCost");
    }

    public static void TrackMNotifFromActualMonthPlanCost()
    {
        Track("eventNotif", "moneyNotif", "FromActualMonthPlanCost");
    }

    public static void TrackMNotifNoEnteredCostPerDay()
    {
        Track("eventNotif", "moneyNotif", "NoEnteredCostPerDay");
    }

    public static void TrackMNotifFromNoEnteredCostPerDay()
    {
        Track("eventNotif", "moneyNotif", "FromNoEnteredCostPerDay");
    }

    public static void TrackMNotifNoAccumulationThisMonth()
    {
        Track("eventNotif", "moneyNotif", "NoAccumulationThisMonth");
    }


    public static void TrackMNotifFromNoAccumulationThisMonth()
    {
        Track("eventNotif", "moneyNotif", "FromNoAccumulationThisMonth");
    }

    public static void TrackMNotifAccumulationDone()
    {
        Track("eventNotif", "moneyNotif", "AccumulationDone");
    }


    public static void TrackMNotifFromAccumulationDone()
    {
        Track("eventNotif", "moneyNotif", "FromAccumulationDone");
    }
    //endregion

    //region TrackTasksNotification
    public static void TrackTNotifCountOverdueTasks()
    {
        Track("eventNotif", "tasksNotif", "CountOverdueTasks");
    }

    public static void TrackTNotifFromCountOverdueTasks()
    {
        Track("eventNotif", "tasksNotif", "FromCountOverdueTasks");
    }

    public static void TrackTNotifCountTasksForDay()
    {
        Track("eventNotif", "tasksNotif", "CountTasksForDay");
    }

    public static void TrackTNotifFromCountTasksForDay()
    {
        Track("eventNotif", "tasksNotif", "FromCountTasksForDay");
    }

    public static void TrackTNotifCountUnfinishedTasksToday()
    {
        Track("eventNotif", "tasksNotif", "CountUnfinishedTasksToday");
    }

    public static void TrackTNotifFromCountUnfinishedTasksToday()
    {
        Track("eventNotif", "tasksNotif", "FromCountUnfinishedTasksToday");
    }

    public static void TrackTNotifNoTasksForDay()
    {
        Track("eventNotif", "tasksNotif", "NoTasksForDay");
    }

    public static void TrackTNotifFromNoTasksForDay()
    {
        Track("eventNotif", "tasksNotif", "FromNoTasksForDay");
    }

    public static void TrackTNotifCountTasksInbox()
    {
        Track("eventNotif", "tasksNotif", "CountTasksInbox");
    }

    public static void TrackTNotifFromCountTasksInbox()
    {
        Track("eventNotif", "tasksNotif", "FromCountTasksInbox");
    }
    //endregion
}
