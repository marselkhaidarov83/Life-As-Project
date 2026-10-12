package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyPlan;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoneyPlanDataSource
{
    public static ArrayList<MoneyPlan> LoadData(Context context,
                                                MoneyPlanFactSettings moneyPlanFactSettings)
    {
        ArrayList<MoneyPlan> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyPlan(moneyPlanFactSettings);
            if (cursor == null) return data;

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_ID);
                int categoryIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_CATEGORY_ID);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_MONTH);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_YEAR);
                int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_SUM_L);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_TYPE);
                int externalIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_EXTERNAL_ID);

                do
                {
                    data.add(new MoneyPlan(
                            cursor.getLong(idIndex),
                            new MoneyCategory(cursor.getLong(categoryIdIndex)),
                            cursor.getInt(monthIndex),
                            cursor.getInt(yearIndex),
                            cursor.getLong(sumLongIndex),
                            EnumTypeIncoming.valueOf(cursor.getString(typeIndex)),
                            cursor.getLong(externalIdIndex)));
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

    public static boolean CopyMoneyPlansFromIncoming(Context context,
                                                     EnumTypeCategory typeCategory,
                                                     int month, int year,
                                                     MoneyIncrease moneyIncrease)
    {
        ArrayList<Long> increaseList = new ArrayList<>();
        increaseList.add(moneyIncrease.get_id());

        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                typeCategory, true, false,
                true, EnumCategoryType.All,null);
        ArrayList<MoneyCategory> categoryArrayList =
                MoneyCategoryDataSource.LoadDataChildren(context, null, moneyCategorySettings);
        ArrayList<Long> categoryIds = new ArrayList<>();
        for (MoneyCategory moneyCategory: categoryArrayList)
            categoryIds.add(moneyCategory.get_id());

        Hashtable<Long, Hashtable<Long, MoneyIncoming>> moneyIncomingHT =
                MoneyIncomingDataSource.LoadDataPlan(context, increaseList, categoryIds);
        Hashtable<Long, MoneyIncoming> incomingHT = new Hashtable<>();
        if (moneyIncomingHT.size() == 1)
            incomingHT = moneyIncomingHT.get(moneyIncrease.get_id());
        else
            return false;
        ArrayList<MoneyIncoming> moneyIncomingArrayList = new ArrayList<>();
        for (Long key: incomingHT.keySet())
            moneyIncomingArrayList.add(incomingHT.get(key));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.CopyMoneyPlansFromIncoming(categoryIds, moneyIncomingArrayList, month, year);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyPlan();
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyPlan();
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
