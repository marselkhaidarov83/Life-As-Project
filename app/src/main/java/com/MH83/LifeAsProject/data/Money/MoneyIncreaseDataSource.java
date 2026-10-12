package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyIncreaseSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class MoneyIncreaseDataSource
{
    public static List<MoneyIncrease> LoadPlanDataWithPercent(Context context,
                                                              MoneyIncreaseSettings moneyIncreaseSettings)
    {
        ArrayList<MoneyIncrease> data = (ArrayList<MoneyIncrease>) LoadDataPlan(context,
                                moneyIncreaseSettings);

        for (int i = 1; i < data.size(); i++)
            data.get(i).setPercent(
                    (100 * (data.get(i).get_value() - data.get(i - 1).get_value()) /
                            (float) data.get(i-1).get_value()));

        return data;
    }

    public static List<MoneyIncrease> LoadData(Context context,
                                               MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        if (moneyIncomingAndCostSettings.get_typeData() == EnumTypeData.Plan)
            return LoadDataPlan(context, null);
        else
            return LoadDataFact(context, moneyIncomingAndCostSettings);
    }


    private static List<MoneyIncrease> LoadDataPlan(Context context,
                                                    MoneyIncreaseSettings moneyIncreaseSettings)
    {
        ArrayList<MoneyIncrease> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyIncrease(moneyIncreaseSettings);

            int prevValue = 0;

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_ID);
                int dayIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_DAY);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_MONTH);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_YEAR);
                int valueIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_VALUE);
                int archiveIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INCREASE_ARCHIVE);
                int countIncomingIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INCREASE_INCOMING_COUNT);

                do {
                    data.add(new MoneyIncrease(cursor.getInt(idIndex),
                            cursor.getString(dayIndex), cursor.getString(monthIndex),
                            cursor.getString(yearIndex), cursor.getInt(valueIndex), prevValue,
                            null,
                            cursor.getInt(archiveIndex) == 1,
                            cursor.getInt(countIncomingIndex) > 0));
                    prevValue = cursor.getInt(valueIndex);
                }
                while (cursor.moveToNext());
            }

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Hashtable<Long, MoneyIncrease> LoadDataHashTable(Context context,
                                        MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        Hashtable<Long, MoneyIncrease> data = new Hashtable<>();
        List<MoneyIncrease> dataArray = LoadData(context, moneyIncomingAndCostSettings);

        for (MoneyIncrease item: dataArray)
            data.put(item.get_id(), item);

        return data;
    }

    private static List<MoneyIncrease> LoadDataFact(Context context,
                                                    MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        ArrayList<MoneyIncrease> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            switch (moneyIncomingAndCostSettings.get_periodData())
            {
//                case Day:
//                    break;
                case Week:
                    Cursor cursor = dbHelper.QueryMoneyIncrease4FactDWMY();

                    if (cursor.moveToFirst())
                    {
                        int dateIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_DATE_INT);
                        int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);

                        int date1 = cursor.getInt(dateIndex);
                        int month1 = cursor.getInt(monthIndex);
                        int year1 = cursor.getInt(yearIndex);
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year1, month1, date1, 0, 0, 0);
                        Calendar calendarL = Calendar.getInstance();

                        do
                        {
                            int dateL = cursor.getInt(dateIndex);
                            int monthL = cursor.getInt(monthIndex);
                            int yearL = cursor.getInt(yearIndex);
                            calendarL.set(yearL, monthL, dateL);
                        }
                        while (cursor.moveToNext());
                        calendarL.add(Calendar.DATE, 7);
                        while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
                            calendar1.add(Calendar.DATE, -1);

                        do
                        {
                            int year = calendar1.get(Calendar.YEAR);
                            int month = calendar1.get(Calendar.MONTH);
                            int quarter = Math.floorDiv(month, 3) + 1;
                            int date = calendar1.get(Calendar.DATE);
                            int weekNumber = calendar1.get(Calendar.WEEK_OF_YEAR);
                            if (calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                                weekNumber--;
                            if (month == 1 && weekNumber == 1)
                                weekNumber = 99;
                            if (month == 11 && weekNumber == 1)
                            {
                                year++;
                                quarter = 1;
                                month = 0;
                            }

                            long increaseId = year * 10000000L + quarter * 1000000L + (month + 1) * 10000L + weekNumber * 100L;
                            String sMonth = String.valueOf(month + 1);
                            if (month + 1 < 10)
                                sMonth = "0" + sMonth;

                            data.add(new MoneyIncrease(increaseId,
                                        String.valueOf(date),
                                        sMonth,
                                        String.valueOf(year),
                                        weekNumber,
                                        null, 0,
                                        null,
                                        false,
                                    false));

                            calendar1.add(Calendar.DATE, 7);
                        }
                        while (calendar1.getTime().getTime() < calendarL.getTime().getTime());
                    }
                    break;
                case Year:
                    cursor = dbHelper.QueryMoneyIncrease4FactY();

                    if (cursor.moveToFirst())
                    {
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);

                        do
                        {
                            long increaseId = cursor.getInt(yearIndex) * 10000000L;
                            data.add(new MoneyIncrease(increaseId, "01", "01",
                                    cursor.getString(yearIndex), null, 0,
                                    null, false, false));
                        }
                        while (cursor.moveToNext());
                    }

                    break;
                case Quarter:
                    int prevYear = 0;
                    int prevQuarter = 0;
                    cursor = dbHelper.QueryMoneyIncrease4FactMY();

                    if (cursor.moveToFirst())
                    {
                        int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);

                        do
                        {
                            int year = cursor.getInt(yearIndex);
                            int month = cursor.getInt(monthIndex);
                            int quarter = Math.floorDiv(month, 3) + 1;
                            if (year == prevYear && quarter == prevQuarter)
                                continue;

                            String sMonth = String.valueOf(month + 1);
                            if (month + 1 < 10)
                                sMonth = "0" + sMonth;

                            long increaseId = year * 10000000L + quarter * 1000000L;
                            data.add(new MoneyIncrease(increaseId, "01", sMonth,
                                    cursor.getString(yearIndex), null, 0,
                                    null, false, false));

                            prevYear = year;
                            prevQuarter = quarter;
                        }
                        while (cursor.moveToNext());
                    }
                    break;
                case Month:
                default:
                    cursor = dbHelper.QueryMoneyIncrease4FactMY();

                    if (cursor.moveToFirst())
                    {
                        int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);
                        int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);

                        do
                        {
                            int month = cursor.getInt(monthIndex);
                            int quarter = Math.floorDiv(month, 3) + 1;
                            String sMonth = String.valueOf(month + 1);
                            if (month + 1 < 10)
                                sMonth = "0" + sMonth;

                            long increaseId = cursor.getInt(yearIndex) * 10000000L  + quarter * 1000000L +  (month + 1) * 10000L;
                            data.add(new MoneyIncrease(increaseId, "01", sMonth,
                                    cursor.getString(yearIndex), null, 0,
                                    null, false, false));
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
            return dbHelper.GetCountMoneyIncrease();
        }
        catch (Exception e) { return 0; }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyIncrease();
        }
        catch (Exception e) { return 0; }
    }
}
