package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class MoneyIncomingDataSource
{
    public static Hashtable<Long, Hashtable<Long, MoneyIncoming>> LoadData(Context context,
                  ArrayList<Long> increaseIds, ArrayList<Long> categoryIds,
                  MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        if (moneyIncomingAndCostSettings.get_typeData() == EnumTypeData.Plan)
            return LoadDataPlan(context, increaseIds, categoryIds);
        else
            return LoadDataFact(context, increaseIds, categoryIds, moneyIncomingAndCostSettings);
    }

    public static Hashtable<Long, Hashtable<Long, MoneyIncoming>> LoadDataPlan(Context context,
                    ArrayList<Long> increaseIds, ArrayList<Long> categoryIds)
    {
        Hashtable<Long, Hashtable<Long, MoneyIncoming>> data = new Hashtable<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            {
                Cursor cursor = dbHelper.QueryMoneyIncoming(increaseIds, categoryIds);
                if (cursor == null) return data;

                if (cursor.moveToFirst())
                {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_ID);
                    int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_CATEGORY_ID);
                    int increaseIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_INCREASE_ID);
                    int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_SUM_L);
                    int percentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_PERCENT);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_TYPE);
                    int externalIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCOMING_EXTERNAL_ID);

                    do
                    {
                        Hashtable<Long, MoneyIncoming> dataMI = data.get(cursor.getLong(increaseIndex));
                        if (dataMI == null)
                        {
                            dataMI = new Hashtable<>();
                            dataMI.put(cursor.getLong(categoryIndex),
                                    new MoneyIncoming(
                                            cursor.getLong(idIndex),
                                            cursor.getLong(categoryIndex),
                                            cursor.getLong(increaseIndex),
                                            cursor.getLong(sumLongIndex),
                                            cursor.getFloat(percentIndex),
                                            EnumTypeIncoming.valueOf(cursor.getString(typeIndex)),
                                            cursor.getLong(externalIdIndex)));
                            data.put(cursor.getLong(increaseIndex), dataMI);
                        }
                        else
                        {
                            dataMI.put(cursor.getLong(categoryIndex),
                                    new MoneyIncoming(
                                            cursor.getLong(idIndex),
                                            cursor.getLong(categoryIndex),
                                            cursor.getLong(increaseIndex),
                                            cursor.getInt(sumLongIndex),
                                            cursor.getFloat(percentIndex),
                                            EnumTypeIncoming.valueOf(cursor.getString(typeIndex)),
                                            cursor.getLong(externalIdIndex)));
                        }
                    }
                    while (cursor.moveToNext());
                }
            }

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Hashtable<Long, Hashtable<Long, MoneyIncoming>> LoadDataFact(Context context,
                    ArrayList<Long> increaseIds, ArrayList<Long> categoryIds,
                    MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        Hashtable<Long, Hashtable<Long, MoneyIncoming>> data = new Hashtable<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();

            Calendar calendarThisMonth = Calendar.getInstance();
            calendarThisMonth.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisMonth.set(Calendar.MINUTE, 0);
            calendarThisMonth.set(Calendar.SECOND, 0);
            calendarThisMonth.set(Calendar.MILLISECOND, 0);
            calendarThisMonth.set(Calendar.DATE, 1);

            Calendar calendarPrevMonth = Calendar.getInstance();
            calendarPrevMonth.set(Calendar.HOUR_OF_DAY, 0);
            calendarPrevMonth.set(Calendar.MINUTE, 0);
            calendarPrevMonth.set(Calendar.SECOND, 0);
            calendarPrevMonth.set(Calendar.MILLISECOND, 0);
            calendarPrevMonth.add(Calendar.DATE, -3);
            calendarPrevMonth.set(Calendar.DATE, 1);

            Calendar calendarThisQuarter = Calendar.getInstance();
            calendarThisQuarter.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisQuarter.set(Calendar.MINUTE, 0);
            calendarThisQuarter.set(Calendar.SECOND, 0);
            calendarThisQuarter.set(Calendar.MILLISECOND, 0);
            calendarThisQuarter.set(Calendar.DATE, 1);
            while (calendarThisQuarter.get(Calendar.MONTH) != Calendar.JANUARY &&
                    calendarThisQuarter.get(Calendar.MONTH) != Calendar.APRIL &&
                    calendarThisQuarter.get(Calendar.MONTH) != Calendar.JULY &&
                    calendarThisQuarter.get(Calendar.MONTH) != Calendar.OCTOBER)
            {
                calendarThisQuarter.add(Calendar.MONTH, -1);
                calendarThisQuarter.set(Calendar.DATE, 1);
            }

            Calendar calendarThisYear = Calendar.getInstance();
            calendarThisYear.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisYear.set(Calendar.MINUTE, 0);
            calendarThisYear.set(Calendar.SECOND, 0);
            calendarThisYear.set(Calendar.MILLISECOND, 0);
            calendarThisYear.set(Calendar.DATE, 1);
            calendarThisYear.set(Calendar.MONTH, 0);

            Calendar calendarThisWeek = Calendar.getInstance();
            calendarThisWeek.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisWeek.set(Calendar.MINUTE, 0);
            calendarThisWeek.set(Calendar.SECOND, 0);
            calendarThisWeek.set(Calendar.MILLISECOND, 0);
            while (calendarThisWeek.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
                calendarThisWeek.add(Calendar.DATE, -1);

            switch (moneyIncomingAndCostSettings.get_periodData())
            {
                case Week:
                    Cursor cursor = dbHelper.QueryMoneyIncomingFactDWMY(increaseIds, categoryIds,
                            moneyIncomingAndCostSettings.get_typeCategory());
                    if (cursor == null) return data;

                    if (cursor.moveToFirst())
                    {
                        int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID);
                        int sumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUMSUM_L);
                        int percentSumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_PERCENTSUMSUM_L);
                        int dateIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_DATE_INT);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);
                        int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);

                        int month = 0;
                        int quarter = 0;
                        int year = 0;
                        int weekNumber = 0;

                        do
                        {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(cursor.getInt(yearIndex), cursor.getInt(monthIndex),
                                    cursor.getInt(dateIndex));
                            int newWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
                            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                                newWeekNumber--;
                            if (cursor.getInt(monthIndex) == 1 && newWeekNumber == 1)
                                newWeekNumber = 99;
                            if (cursor.getInt(monthIndex) == 11 && newWeekNumber == 1)
                            {
                                year++;
                                quarter = 1;
                                month = 0;
                            }

                            if (cursor.getInt(yearIndex) != year ||
                                    cursor.getInt(monthIndex) != month ||
                                    newWeekNumber != weekNumber)
                            {
                                year = cursor.getInt(yearIndex);
                                month = cursor.getInt(monthIndex);
                                quarter = Math.floorDiv(month, 3) + 1;
                                weekNumber = newWeekNumber;
                            }

                            long increaseId = 10000000L * year + 1000000L * quarter + 10000L * (month + 1) + 100L * weekNumber;
                            MoneyIncoming moneyIncoming = new MoneyIncoming(
                                    0L,
                                    cursor.getLong(categoryIndex),
                                    increaseId,
                                    cursor.getLong(sumLongIndex) + cursor.getLong(percentSumLongIndex),
                                    0f,
                                    EnumTypeIncoming.Sum,
                                    0L);

                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
                                calendar.add(Calendar.DATE, -1);

                            if (globalVariable.get_hasPaymentOrTestSubscription() ||
                                    calendarThisWeek.getTime().getTime() == calendar.getTime().getTime())
                            {
                                Hashtable<Long, MoneyIncoming> dataMI = data.get(increaseId);
                                if (dataMI == null)
                                {
                                    dataMI = new Hashtable<>();
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                                    data.put(increaseId, dataMI);
                                }
                                else
                                {
                                    MoneyIncoming moneyIncomingOld = dataMI.get(cursor.getInt(categoryIndex));
                                    if (moneyIncomingOld != null)
                                        moneyIncoming.set_sumLong(moneyIncoming.get_sumLong() + moneyIncomingOld.get_sumLong());
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                                }
                            }
                        }
                        while (cursor.moveToNext());
                    }

                    break;
                case Year:
                    cursor = dbHelper.QueryMoneyIncomingFactY(increaseIds, categoryIds,
                            moneyIncomingAndCostSettings.get_typeCategory());
                    if (cursor == null) return data;

                    if (cursor.moveToFirst())
                    {
                        int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID);
                        int sumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUMSUM_L);
                        int percentSumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_PERCENTSUMSUM_L);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);

                        int year = 0;

                        do
                        {
                            if (cursor.getInt(yearIndex) != year)
                                year = cursor.getInt(yearIndex);
                            long increaseId = 10000000L * year;
                            MoneyIncoming moneyIncoming = new MoneyIncoming(
                                    0L,
                                    cursor.getLong(categoryIndex),
                                    increaseId,
                                    cursor.getLong(sumLongIndex) + cursor.getLong(percentSumLongIndex),
                                    0f,
                                    EnumTypeIncoming.Sum,
                                    0L);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, 0, 1);
                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);

                            if (globalVariable.get_hasPaymentOrTestSubscription() ||
                                    calendarThisYear.getTime().getTime() == calendar.getTime().getTime())
                            {
                                Hashtable<Long, MoneyIncoming> dataMI = data.get(increaseId);
                                if (dataMI == null)
                                {
                                    dataMI = new Hashtable<>();
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                                    data.put(increaseId, dataMI);
                                }
                                else
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                            }
                        }
                        while (cursor.moveToNext());
                    }

                    break;
                case Quarter:
                    cursor = dbHelper.QueryMoneyIncomingFactMY(increaseIds, categoryIds,
                            moneyIncomingAndCostSettings.get_typeCategory());
                    if (cursor == null) return data;

                    if (cursor.moveToFirst())
                    {
                        int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID);
                        int sumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUMSUM_L);
                        int percentSumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_PERCENTSUMSUM_L);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);
                        int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);

                        int month = 0;
                        int quarter = 0;
                        int year = 0;

                        do
                        {
                            if (cursor.getInt(yearIndex) != year ||
                                    cursor.getInt(monthIndex) != month)
                            {
                                year = cursor.getInt(yearIndex);
                                month = cursor.getInt(monthIndex);
                                quarter = Math.floorDiv(month, 3) + 1;
                            }
                            long increaseId = 10000000L * year + 1000000L * quarter;
                            if (year == 2025)
                                year = 2025;
                            MoneyIncoming moneyIncoming = new MoneyIncoming(
                                    0L,
                                    cursor.getLong(categoryIndex),
                                    increaseId,
                                    cursor.getLong(sumLongIndex) + cursor.getLong(percentSumLongIndex),
                                    0f,
                                    EnumTypeIncoming.Sum,
                                    0L);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, 1);
                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            while (calendar.get(Calendar.MONTH) != Calendar.JANUARY &&
                                    calendar.get(Calendar.MONTH) != Calendar.APRIL &&
                                    calendar.get(Calendar.MONTH) != Calendar.JULY &&
                                    calendar.get(Calendar.MONTH) != Calendar.OCTOBER)
                            {
                                calendar.add(Calendar.MONTH, -1);
                                calendar.set(Calendar.DATE, 1);
                            }

                            if (globalVariable.get_hasPaymentOrTestSubscription() ||
                                    calendarThisQuarter.getTime().getTime() == calendar.getTime().getTime())
                            {
                                Hashtable<Long, MoneyIncoming> dataMI = data.get(increaseId);
                                if (dataMI == null)
                                {
                                    dataMI = new Hashtable<>();
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                                    data.put(increaseId, dataMI);
                                }
                                else
                                {
                                    MoneyIncoming moneyIncomingOld = dataMI.get(cursor.getLong(categoryIndex));
                                    if (moneyIncomingOld != null)
                                        moneyIncoming.set_sumLong(moneyIncoming.get_sumLong() + moneyIncomingOld.get_sumLong());
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                                }
                            }
                        }
                        while (cursor.moveToNext());
                    }
                    break;
                case Month:
                default:
                    cursor = dbHelper.QueryMoneyIncomingFactMY(increaseIds, categoryIds,
                            moneyIncomingAndCostSettings.get_typeCategory());
                    if (cursor == null) return data;

                    if (cursor.moveToFirst())
                    {
                        int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID);
                        int sumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUMSUM_L);
                        int percentSumLongIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_PERCENTSUMSUM_L);
                        int dateIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_DATE_INT);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);
                        int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);

                        int month = 0;
                        int quarter = 0;
                        int year = 0;

                        do
                        {
                            if (cursor.getInt(yearIndex) != year ||
                                cursor.getInt(monthIndex) != month)
                            {
                                year = cursor.getInt(yearIndex);
                                month = cursor.getInt(monthIndex);
                                quarter = Math.floorDiv(month, 3) + 1;
                            }
                            long increaseId = 10000000L * year + 1000000L * quarter + 10000L * (month + 1);
                            MoneyIncoming moneyIncoming = new MoneyIncoming(
                                    0L,
                                    cursor.getLong(categoryIndex),
                                    increaseId,
                                    cursor.getLong(sumLongIndex) + cursor.getLong(percentSumLongIndex),
                                    0f,
                                    EnumTypeIncoming.Sum,
                                    0L);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, 1);
                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);

                            if (globalVariable.get_hasPaymentOrTestSubscription() ||
                                    calendarThisMonth.getTime().getTime() == calendar.getTime().getTime() ||
                                    calendarPrevMonth.getTime().getTime() == calendar.getTime().getTime())
                            {
                                Hashtable<Long, MoneyIncoming> dataMI = data.get(increaseId);
                                if (dataMI == null)
                                {
                                    dataMI = new Hashtable<>();
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                                    data.put(increaseId, dataMI);
                                }
                                else
                                    dataMI.put(cursor.getLong(categoryIndex), moneyIncoming);
                            }
                        }
                        while (cursor.moveToNext());
                    }
            }

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyIncoming();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyIncoming();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
