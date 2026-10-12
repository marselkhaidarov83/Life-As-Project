package com.MH83.LifeAsProject.service;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Money.MoneyDreamDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.data.Tasks.TasksTaskDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;
import com.MH83.LifeAsProject.model.Settings.NotifProjectSettings;
import com.MH83.LifeAsProject.model.Settings.NotifSettings;
import com.MH83.LifeAsProject.model.Settings.NotifTasksSettings;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Activities.MoneyAccountingActivity;
import com.MH83.LifeAsProject.view.Activities.MoneyCostActivity;
import com.MH83.LifeAsProject.view.Activities.MoneyDreamActivity;
import com.MH83.LifeAsProject.view.Activities.MoneyIncomingActivity;
import com.MH83.LifeAsProject.view.Activities.MoneyTargetActivity;
import com.MH83.LifeAsProject.view.Activities.TasksInboxActivity;
import com.MH83.LifeAsProject.view.Activities.TasksProjectActivity;
import com.MH83.LifeAsProject.view.Activities.TasksTodayActivity1;
import com.MH83.LifeAsProject.view.Activities.TasksTodayActivity2;
import com.MH83.LifeAsProject.view.Activities.TasksTodayActivity3;
import com.MH83.LifeAsProject.view.Activities.TasksTodayActivity4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeBroadcastReceiver extends BroadcastReceiver
{
    //region Class parameters
    private static final String channel_project = "LifeIsProject.Channel.Project";
    private static final String channel_money = "LifeIsProject.Channel.Money";
    private static final String channel_task = "LifeIsProject.Channel.Tasks";
    private static final CharSequence name_channel_project = "Канал уведомлений по проекту";
    private static final String description_channel_project = "Канал уведомлений по проекту";
    private static final CharSequence name_channel_money = "Канал уведомлений по финансам";
    private static final String description_channel_money = "Канал уведомлений по финансам";
    private static final CharSequence name_channel_task = "Канал уведомлений по задачам";
    private static final String description_channel_task = "Канал уведомлений по задачам";
    private static final int intentFlag = Intent.FLAG_ACTIVITY_CLEAR_TOP;
    private static final int pendingIntentFlag = PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE;
    private static final int importance = NotificationManager.IMPORTANCE_LOW;
    private static final String TAG = "Timers";

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    //endregion

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        NotifSettings notifSettings = globalVar.get_notifSettings();
        NotifProjectSettings notifProjectSettings = globalVar.get_notifProjectSettings();
        NotifMoneySettings notifMoneySettings = globalVar.getNotifMoneySettings();
        NotifTasksSettings notifTasksSettings = globalVar.get_notifTasksSettings();

        //region Проверка включения уведомлений в настройках устройства
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (!mNotificationManager.areNotificationsEnabled())
        {
            Log.d(TAG, "Уведомления выключены в настройках устройства");
            return;
        }
        //endregion

        //region Проверка включения уведомлений
        if (!notifSettings.get_notifEnabled())
        {
            Log.d(TAG, "Уведомления выключены");
            return;
        }
        //endregion

        //region Проверка периода тишины
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        boolean prevIsNight = globalVar.get_isNight();
        boolean morningNotif = false;
        if (hour < notifSettings.get_notifStartHour() ||
                (hour == notifSettings.get_notifStartHour() &&
                        minute < notifSettings.get_notifStartMinute()))
        {
            globalVar.set_isNight(true);
            Log.d(TAG, "Действует период тишины для уведомлений");
            return;
        }
        else
        {
            globalVar.set_isNight(false);
            if (prevIsNight)
                morningNotif = true;
        }

        boolean eveningNotif = false;
        if (hour > notifSettings.get_notifEndHour() ||
                (hour == notifSettings.get_notifEndHour() &&
                        minute > notifSettings.get_notifEndMinute()))
        {
            globalVar.set_isNight(true);
            morningNotif = false;

            if (!prevIsNight)
                eveningNotif = true;
            else
            {
                Log.d(TAG, "Действует период тишины для уведомлений");
                return;
            }
        }
        else
            globalVar.set_isNight(false);
        //endregion

        Log.d(TAG, "Уведомления включены");
        Log.d(TAG, String.valueOf(morningNotif));
        Log.d(TAG, String.valueOf(eveningNotif));

        //use a handler to run a toast that shows the current timestamp
        boolean finalMorningNotif = morningNotif;
        boolean finalEveningNotif = eveningNotif;
        handler.post(() ->
        {
            //region Создание каналов уведомлений
            CreateChannelProject(context.getApplicationContext());
            CreateChannelMoney(context.getApplicationContext());
            CreateChannelTask(context.getApplicationContext());
            //endregion

            if (finalMorningNotif || globalVar.TestNotification())
            {
                //region Проверка наличия 3 мечт
                if (notifProjectSettings.get_needDream())
                {
                    int countDream = MoneyDreamDataSource.GetCount(context.getApplicationContext());
                    if (countDream < 3)
                        CreateNotifNeedDream(context.getApplicationContext());
                }
                //endregion

                //region Проверка наличия цели в мечте
                if (notifProjectSettings.get_needTarget())
                {
                    for (MoneyDream dreamItem : MoneyDreamDataSource.LoadData(
                            context.getApplicationContext(), null))
                        if (dreamItem.get_countTarget() == 0)
                            CreateNotifTarget(context.getApplicationContext(), dreamItem);
                }
                //endregion

                //region Проверка наличия задач в проекте на неделю
                if (notifProjectSettings.get_needTaskInProject())
                {
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                    {
                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
                        calendarStart.set(Calendar.MINUTE, 0);
                        calendarStart.set(Calendar.SECOND, 0);
                        calendarStart.set(Calendar.MILLISECOND, 0);

                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
                        calendarEnd.set(Calendar.MINUTE, 0);
                        calendarEnd.set(Calendar.SECOND, 0);
                        calendarEnd.set(Calendar.MILLISECOND, 0);
                        calendarEnd.add(Calendar.DATE, 7);

                        for (TasksProject tasksProject :
                                TasksProjectDataSource.LoadDataChildren(
                                        context.getApplicationContext(),
                                        false,
                                        new TaskProjectSettings(EnumTargetStatus4Settings.All, null))) {
                            TasksTaskSettings tasksTaskSettings = new TasksTaskSettings(
                                    new CustomPeriodClass(6, ""),
                                    false,
                                    null,
                                    tasksProject.get_id(),
                                    null,
                                    EnumTaskSorting.Date_Priority_Name,
                                    false);
                            ArrayList<TasksTaskIndependent> list =
                                    TasksTaskDataSource.LoadDataParent(context.getApplicationContext(),
                                            calendarStart, calendarEnd,
                                            tasksTaskSettings, false);
                            if (list.size() == 0)
                                CreateNotifProject(context.getApplicationContext(), tasksProject);
                        }
                    }
                }
                //endregion

                //region Проверка наличия просроченных задач и наличия задач на день
                if (notifTasksSettings.get_overdueTasksInMorning() ||
                        notifTasksSettings.get_countTasksForDay() ||
                        notifTasksSettings.get_noTasksForDay())
                {
                    ArrayList<TasksTaskIndependent> list0 = null;
                    ArrayList<TasksTaskIndependent> list1;

                    if (notifTasksSettings.get_overdueTasksInMorning())
                    {
                        TasksTaskSettings tasksTaskSettings0 = new TasksTaskSettings(
                                new CustomPeriodClass(0, ""),
                                false,
                                null,
                                null,
                                null,
                                EnumTaskSorting.Date_Priority_Name,
                                false);
                        list0 = TasksTaskDataSource.LoadDataParent(context.getApplicationContext(),
                                tasksTaskSettings0, false);
                    }

                    TasksTaskSettings tasksTaskSettings1 = new TasksTaskSettings(
                            new CustomPeriodClass(1, ""),
                            false,
                            null,
                            null,
                            null,
                            EnumTaskSorting.Date_Priority_Name,
                            false);
                    list1 = TasksTaskDataSource.LoadDataParent(context.getApplicationContext(),
                            tasksTaskSettings1, false);

                    if (notifTasksSettings.get_overdueTasksInMorning() &&
                            (list0.size() - list1.size()) > 0)
                        CreateNotifCountOverdueTasks(context.getApplicationContext(), list0.size() - list1.size());

                    if (notifTasksSettings.get_countTasksForDay() && list1.size() > 0)
                        CreateNotifCountTasksToday(context.getApplicationContext(), list1.size());

                    if (notifTasksSettings.get_noTasksForDay() && list1.size() == 0)
                        CreateNotifNoTasksToday(context.getApplicationContext());
                }
                //endregion

                //region Уведомление о необходимости актуализации плана на месяц в начале месяца
                if (notifMoneySettings.get_actualMonthPlanIncoming())
                {
                    if (calendar.get(Calendar.DATE) == 1)
                        CreateNotifActualMonthPlanIncoming(context.getApplicationContext());
                }

                if (notifMoneySettings.get_actualMonthPlanCost())
                {
                    if (calendar.get(Calendar.DATE) == 1)
                        CreateNotifActualMonthPlanCost(context.getApplicationContext());
                }
                //endregion
            }

            if (finalEveningNotif || globalVar.TestNotification())
            {
                //region Проверка наличия невыполненных задач
                if (notifTasksSettings.get_countUnfinishedTasksInEvening())
                {
                    ArrayList<TasksTaskIndependent> list1;

                    TasksTaskSettings tasksTaskSettings = new TasksTaskSettings(
                            new CustomPeriodClass(0, ""),
                            false,
                            null,
                            null,
                            null,
                            EnumTaskSorting.Date_Priority_Name,
                            false);
                    list1 = TasksTaskDataSource.LoadDataParent(context.getApplicationContext(),
                            tasksTaskSettings, false);

                    if (notifTasksSettings.get_countUnfinishedTasksInEvening() && list1.size() > 0)
                        CreateNotifCountUnfinishedTasksToday(context.getApplicationContext(), list1.size());
                }
                //endregion

                //region Проверка наличия задач в Inbox
                if (notifTasksSettings.get_countTasksInbox() &&
                        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                {
                    ArrayList<TasksTaskIndependent> list1;

                    TasksTaskSettings tasksTaskSettings1 = new TasksTaskSettings(
                            new CustomPeriodClass(6, ""),
                            false,
                            0L,
                            0L,
                            0L,
                            EnumTaskSorting.Date_Priority_Name,
                            false);
                    list1 = TasksTaskDataSource.LoadDataParent(context.getApplicationContext(),
                            tasksTaskSettings1, false);

                    if (notifTasksSettings.get_countTasksInbox() && list1.size() > 0)
                        CreateNotifCountTasksInbox(context.getApplicationContext(), list1.size());
                }
                //endregion

                //region Проверка ввода трат за день
                if (notifMoneySettings.get_noEnteredCostPerDay())
                {
                    List<MoneyTransaction> list =
                            MoneyPNLDataSource.LoadDataInd4Accumulation(context.getApplicationContext(), -1L,
                                    calendar, calendar);
                    if (list.size() == 0)
                        CreateNotifNoPNLToday(context.getApplicationContext());
                }
                //endregion
            }
        });
    }

    //region Create Channels
    private static void CreateChannelProject(Context context)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel mChannel_project;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            mChannel_project = new NotificationChannel(channel_project, name_channel_project, importance);
            mChannel_project.setDescription(description_channel_project);
            mChannel_project.enableLights(true);
            mChannel_project.setLightColor(context.getColor(R.color.colorPrimary));

            mChannel_project.enableVibration(true);
            mChannel_project.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel_project);
        }
    }

    private static void CreateChannelMoney(Context context)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel mChannel_money;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            mChannel_money = new NotificationChannel(channel_money, name_channel_money, importance);
            mChannel_money.setDescription(description_channel_money);
            mChannel_money.enableLights(true);
            mChannel_money.setLightColor(context.getColor(R.color.colorPrimary));

            mChannel_money.enableVibration(true);
            mChannel_money.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel_money);
        }
    }

    private static void CreateChannelTask(Context context)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel mChannel_task;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            mChannel_task = new NotificationChannel(channel_task, name_channel_task, importance);
            mChannel_task.setDescription(description_channel_task);
            mChannel_task.enableLights(true);
            mChannel_task.setLightColor(context.getColor(R.color.colorPrimary));

            mChannel_task.enableVibration(true);
            mChannel_task.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel_task);
        }
    }
    //endregion

    //region Create Notif Project
    private static void CreateNotifNeedDream(Context context)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, MoneyDreamActivity.class);
        resultIntent.putExtra(MoneyDreamActivity.NavigationIdExtra, "MoneyDream");
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifProjectSettings.NotifIdNeedDream, resultIntent, pendingIntentFlag);

        Notif(context, NotifProjectSettings.NotifIdNeedDream, channel_project,
                "У вас очень мало мечт. Добавьте ещё",
                "У вас очень мало мечт. Жизнь без мечты скучна и неэффективна. Зафиксируйте, как минимум, 3 мечты, к которым вы стремитесь",
                resultPendingIntent);
        CustomTrack.TrackPNotifNeedDream();
    }

    private static void CreateNotifTarget(Context context, MoneyDream dreamItem)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, MoneyTargetActivity.class);
        resultIntent.putExtra(MoneyTargetActivity.DreamIdExtra, "MoneyTarget" + dreamItem.get_id());
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, (int) (NotifProjectSettings.NotifIdNeedTargetShift + dreamItem.get_id()),
                resultIntent, pendingIntentFlag);

        Notif(context, (int) (NotifProjectSettings.NotifIdNeedTargetShift + dreamItem.get_id()),
                channel_project,
                "У вашей мечты '" + dreamItem.get_name() + "' нет цели. Добавьте хотя бы одну",
                "У вас нет целей для реализации мечты '" + dreamItem.get_name() +
                        "'. Мечта остается мечтой, если ее не превратить в цель. Зафиксируйте, как минимум, 3 цели для мечты, чтобы мечта в свое время стала реальностью",
                resultPendingIntent);
        CustomTrack.TrackPNotifNeedTargetInDream();
    }

    private static void CreateNotifProject(Context context, TasksProject tasksProject)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, TasksProjectActivity.class);
        resultIntent.putExtra(TasksProjectActivity.ProjectIdExtra, "TasksProject" + tasksProject.get_id());
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, (int) (NotifProjectSettings.NotifIdNeedTaskInProjectShift + tasksProject.get_id()),
                resultIntent, pendingIntentFlag);

        Notif(context, (int) (NotifProjectSettings.NotifIdNeedTaskInProjectShift + tasksProject.get_id()),
                channel_project,
                "У вашего проекта '" + tasksProject.get_name() + "' нет задач. Добавьте хотя бы одну",
                "У вас нет задач на текущеую неделю для проекта '" + tasksProject.get_name() +
                        "'. Проект не будет реализован, если не выполнять задачи по нему. Зафиксируйте, как минимум, 3 задачи для проекта на неделю, чтобы проект в свое время реализовался",
                resultPendingIntent);
        CustomTrack.TrackPNotifNeedTaskInProject();
    }
    //endregion

    //region Create Notif Money
    private static void CreateNotifActualMonthPlanIncoming(Context context)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, MoneyIncomingActivity.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifMoneySettings.NotifIdActualMonthPlanIncoming, resultIntent, pendingIntentFlag);

        Notif(context, NotifMoneySettings.NotifIdActualMonthPlanIncoming, channel_money,
                "Проверьте актуальность плана доходов на месяц",
                "Начался новый месяц. Возможно вы ожидаете рост доходов. Проверьте актуальность плана доходов на месяц",
                resultPendingIntent);
        CustomTrack.TrackMNotifActualMonthPlanIncoming();
    }

    private static void CreateNotifActualMonthPlanCost(Context context)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, MoneyCostActivity.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifMoneySettings.NotifIdActualMonthPlanCost, resultIntent, pendingIntentFlag);

        Notif(context, NotifMoneySettings.NotifIdActualMonthPlanCost, channel_money,
                "Проверьте актуальность плана расходов на месяц",
                "Начался новый месяц. Возможно вы ожидаете изменение расходов. Проверьте актуальность плана расходов на месяц",
                resultPendingIntent);
        CustomTrack.TrackMNotifActualMonthPlanCost();
    }

    private static void CreateNotifNoPNLToday(Context context)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, MoneyAccountingActivity.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifMoneySettings.NotifIdNoEnteredCostPerDay, resultIntent, pendingIntentFlag);

        Notif(context, NotifMoneySettings.NotifIdNoEnteredCostPerDay, channel_money,
                "Не введены доходы и расходы за день",
                "За текущий день вы не ввели ни 1 операции дохода или расхода денег. Введите траты, чтобы всегда иметь актуальную картину ваших финансов",
                resultPendingIntent);
        CustomTrack.TrackMNotifNoEnteredCostPerDay();
    }
    //endregion

    //region Create Notif Tasks
    private static void CreateNotifCountOverdueTasks(Context context, int count)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, TasksTodayActivity1.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifTasksSettings.NotifIdOverdueTasks,
                resultIntent, pendingIntentFlag);

        Notif(context, NotifTasksSettings.NotifIdOverdueTasks,
                channel_task,
                "На сегодняшнее утро у вас " + count + " " + getSOverdueTaskS(count),
                "На сегодняшнее утро у вас " + count + " " +
                        getSOverdueTaskS(count) + ". Отметьте выполненные задачи и перенесите сроки невыполненных задач",
                resultPendingIntent);
        CustomTrack.TrackTNotifCountOverdueTasks();
    }

    private static void CreateNotifCountTasksToday(Context context, int count)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, TasksTodayActivity2.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifTasksSettings.NotifIdCountTasksForDay,
                resultIntent, pendingIntentFlag);

        Notif(context, NotifTasksSettings.NotifIdCountTasksForDay,
                channel_task,
                "На сегодня у вас " + count + " " + getSTaskS(count) + " для выполнения",
                "На сегодня у вас " + count + " " + getSTaskS(count) +
                        " для выполнения. Скорее приступайте к их выполнению",
                resultPendingIntent);
        CustomTrack.TrackTNotifCountTasksForDay();
    }

    private static void CreateNotifCountUnfinishedTasksToday(Context context, int count)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, TasksTodayActivity3.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifTasksSettings.NotifIdCountUnfinishedTasksInEvening,
                resultIntent, pendingIntentFlag);

        Notif(context, NotifTasksSettings.NotifIdCountUnfinishedTasksInEvening,
                channel_task,
                "Сегодня у вас " + count + " " + getSUnfinishedTaskS(count),
                "Сегодня у вас " + count + " " + getSUnfinishedTaskS(count) +
                        ". Отметьте выполненные задачи и перенесите сроки невыполненных задач",
                resultPendingIntent);
        CustomTrack.TrackTNotifCountUnfinishedTasksToday();
    }

    private static void CreateNotifNoTasksToday(Context context)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, TasksTodayActivity4.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifTasksSettings.NotifIdNoTasksForDay,
                resultIntent, pendingIntentFlag);

        Notif(context, NotifTasksSettings.NotifIdNoTasksForDay,
                channel_task,
                "На сегодня у вас нет задач для выполнения",
                "На сегодня у вас нет задач для выполнения. Сформируйте список задач и приступайте к их выполнению",
                resultPendingIntent);
        CustomTrack.TrackTNotifNoTasksForDay();
    }

    private static void CreateNotifCountTasksInbox(Context context, int count)
    {
        // Create PendingIntent
        Intent resultIntent = new Intent(context, TasksInboxActivity.class);
        resultIntent.setFlags(intentFlag);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, NotifTasksSettings.NotifIdCountTasksInbox,
                resultIntent, pendingIntentFlag);

        Notif(context, NotifTasksSettings.NotifIdCountTasksInbox,
                channel_task,
                "У вас всего " + count + " " + getSTaskS(count) + " во входящих",
                "У вас всего " + count + " " + getSTaskS(count) +
                        " во входящих. Распределите задачи по проектам",
                resultPendingIntent);
        CustomTrack.TrackTNotifCountTasksInbox();
    }

    private static String getSTaskS(int count)
    {
        int i = Math.floorMod(count, 10);
        switch (i)
        {
            case 1:
                return "задача";
            case 2:
            case 3:
            case 4:
                return "задачи";
            default:
                return "задач";
        }
    }

    private static String getSUnfinishedTaskS(int count)
    {
        int i = Math.floorMod(count, 10);
        switch (i)
        {
            case 1:
                return "невыполненная задача";
            case 2:
            case 3:
            case 4:
                return "невыполненные задачи";
            default:
                return "невыполненных задач";
        }
    }

    private static String getSOverdueTaskS(int count)
    {
        int i = Math.floorMod(count, 10);
        switch (i)
        {
            case 1:
                return "просроченная задача";
            case 2:
            case 3:
            case 4:
                return "просроченные задачи";
            default:
                return "просроченных задач";
        }
    }
    //endregion

    //region Create Notif
    private static void Notif(Context context,
                              int id, String channel, String text, String longText, PendingIntent pendingIntent)
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_apk)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(text)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(longText))
                        .setChannelId(channel)
                        .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }
    //endregion
}