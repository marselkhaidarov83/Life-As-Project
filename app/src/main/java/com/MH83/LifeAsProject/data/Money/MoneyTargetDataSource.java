package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Custom.PlanValueDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyAccumulation;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoneyTargetDataSource
{
    public static ArrayList<MoneyTarget> LoadData(Context context)
    {
        ArrayList<MoneyTarget> data = new ArrayList<>();
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyTarget(globalVar.get_moneyTargetDreamId(),
                    globalVar.get_moneyTargetYear(),
                    globalVar.get_moneyTargetEnumTargetStatus4Settings(),
                    globalVar.get_moneyTargetSettings());

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_ID);
                int dreamIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_DREAM_ID);
                int headIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_HEAD_ID);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_MONTH);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_YEAR);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_NAME);
                int priceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_PRICE);
                int accumulationIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_ACCUMULATION);
                int credit1Index = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_CREDIT);
                int imagePathIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_IMAGE_PATH);
                int imageUrlIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_IMAGE_URL);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_STATUS);
                int categoryIdIndex = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_CATEGORY_ID);
                int taskCount1Index = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_TASK_COUNT1);
                int taskCount2Index = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_TASK_COUNT2);

                do
                {
                    String status = cursor.getString(statusIndex);
                    if (status == null)
                        status = EnumStatus.Blank.toString();

                    MoneyAccumulation moneyAccumulation = MoneyAccumulationDataSource.LoadData(
                            context, cursor.getInt(idIndex));

                    MoneyCredit moneyCredit = MoneyCreditDataSource.GetOneMoneyCredit4Target(
                            context, cursor.getLong(idIndex));
                    if (moneyCredit != null)
                        moneyCredit.set_pnls(MoneyPNLDataSource.LoadData4Credit(context,
                                EnumTypeCategory.Cost, moneyCredit.get_id()));

                    MoneyTarget moneyTarget = new MoneyTarget(
                            cursor.getLong(idIndex),
                            cursor.getString(monthIndex),
                            cursor.getString(yearIndex), cursor.getString(nameIndex),
                            cursor.getInt(priceIndex), cursor.getInt(accumulationIndex),
                            cursor.getInt(credit1Index), cursor.getString(imagePathIndex),
                            cursor.getString(imageUrlIndex), cursor.getLong(dreamIdIndex),
                            EnumStatus.valueOf(status),
                            "",
                            headIdIndex == -1 ? 0 : cursor.getLong(headIdIndex),
                            cursor.getInt(categoryIdIndex),
                            moneyAccumulation,
                            null,
                            PlanValueDataSource.LoadData(context, cursor.getInt(idIndex)),
                            null,
                            cursor.getInt(taskCount1Index),
                            cursor.getInt(taskCount2Index),
                            moneyCredit);

                    Long walletId = dbHelper.OneMoneyCategoryId4Target(moneyTarget.get_id(), EnumTypeCategory.Wallet);
                    if (walletId != 0L)
                        moneyTarget.set_moneyCategory(MoneyCategoryDataSource.LoadOne(context, walletId));

                    data.add(moneyTarget);
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

    public static ArrayList<MoneyTarget> LoadDataWithOutAddons(Context context)
    {
        ArrayList<MoneyTarget> data = new ArrayList<>();
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyTarget(globalVar.get_moneyTargetDreamId(),
                    globalVar.get_moneyTargetYear(),
                    globalVar.get_moneyTargetEnumTargetStatus4Settings(),
                    globalVar.get_moneyTargetSettings());

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_ID);
                int dreamIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_DREAM_ID);
                int headIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_HEAD_ID);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_MONTH);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_YEAR);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_NAME);
                int priceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_PRICE);
                int accumulationIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_ACCUMULATION);
                int credit1Index = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_CREDIT);
                int imagePathIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_IMAGE_PATH);
                int imageUrlIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_IMAGE_URL);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_STATUS);
                int categoryIdIndex = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_CATEGORY_ID);
                int taskCount1Index = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_TASK_COUNT1);
                int taskCount2Index = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_TASK_COUNT2);

                do
                {
                    String status = cursor.getString(statusIndex);
                    if (status == null)
                        status = EnumStatus.Blank.toString();

                    MoneyTarget moneyTarget = new MoneyTarget(
                            cursor.getLong(idIndex),
                            cursor.getString(monthIndex),
                            cursor.getString(yearIndex), cursor.getString(nameIndex),
                            cursor.getInt(priceIndex), cursor.getInt(accumulationIndex),
                            cursor.getInt(credit1Index), cursor.getString(imagePathIndex),
                            cursor.getString(imageUrlIndex), cursor.getLong(dreamIdIndex),
                            EnumStatus.valueOf(status),
                            "",
                            headIdIndex == -1 ? 0 : cursor.getLong(headIdIndex),
                            cursor.getInt(categoryIdIndex),
                            null,
                            null,
                            PlanValueDataSource.LoadData(context, cursor.getInt(idIndex)),
                            null,
                            cursor.getInt(taskCount1Index),
                            cursor.getInt(taskCount2Index),
                            null);

                    Long walletId = dbHelper.OneMoneyCategoryId4Target(moneyTarget.get_id(), EnumTypeCategory.Wallet);
                    if (walletId != 0L)
                        moneyTarget.set_moneyCategory(MoneyCategoryDataSource.LoadOne(context, walletId));

                    data.add(moneyTarget);
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

    public static ArrayList<MoneyTarget> LoadActiveTargets(Context context,
                                                           Calendar calendarStart, Calendar calendarEnd)
    {
        ArrayList<MoneyTarget> list = LoadDataWithOutAddons(context);
        ArrayList<MoneyTarget> activeList = new ArrayList<>();
        for (MoneyTarget item: list)
            if (item.get_status().equals(EnumStatus.Started))
                if (calendarStart == null ||
                        item.get_calendar().getTime().getTime() >= calendarStart.getTime().getTime())
                    if (calendarEnd == null ||
                            item.get_calendar().getTime().getTime() <= calendarEnd.getTime().getTime())
                activeList.add(item);

        return activeList;
    }

    public static Long LoadActiveTargetsPrice(Context context, Boolean active,
                                      Calendar calendarStart, Calendar calendarEnd)
    {
        ArrayList<MoneyTarget> list;
        if (active)
            list = LoadActiveTargets(context, calendarStart, calendarEnd);
        else
            list = LoadDataWithOutAddons(context);

        long sum = 0L;
        if (list != null)
            for (MoneyTarget item : list)
                if (calendarStart == null ||
                        item.get_calendar().getTime().getTime() >= calendarStart.getTime().getTime())
                    if (calendarEnd == null ||
                            item.get_calendar().getTime().getTime() <= calendarEnd.getTime().getTime())
                        sum += 100L * item.get_price();
        return sum;
    }

    public static Long LoadActiveTargetsNeedSavings(Context context, Boolean active,
                                        Calendar calendarStart, Calendar calendarEnd)
    {
        ArrayList<MoneyTarget> list;
        if (active)
            list = LoadActiveTargets(context, calendarStart, calendarEnd);
        else
            list = LoadDataWithOutAddons(context);

        long sum = 0L;
        if (list != null)
            for (MoneyTarget item : list)
                if (calendarStart == null ||
                        item.get_calendar().getTime().getTime() >= calendarStart.getTime().getTime())
                    if (calendarEnd == null ||
                            item.get_calendar().getTime().getTime() <= calendarEnd.getTime().getTime())
                        sum += 100L * item.get_accumulation();
        return sum;
    }

    public static List<MoneyTarget> LoadDataParents(Context context, Long dreamId,
                                                    List<Long> excludedId)
    {
        ArrayList<MoneyTarget> data = new ArrayList<>();
        data.add(new MoneyTarget(0L, "01", "1999", "", 0, 0, 0,
                "", "", dreamId, EnumStatus.Started, "", 0L, 0,
                null, null,null, null,0, 0, null));

        ArrayList<MoneyTarget> data2 = LoadData(context);
        for (MoneyTarget moneyTarget : data2)
            if (!excludedId.contains(moneyTarget.get_id()) && moneyTarget.get_parentId() == 0)
                data.add(moneyTarget);

        return data;
    }

    public static Integer LoadOneIdByWalletId(Context context, Long walletId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.LoadTargetIdByWalletId(walletId);
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static List<MoneyTarget> LoadDataWithBlank(Context context)
    {
        ArrayList<MoneyTarget> data = new ArrayList<>();
        data.add(new MoneyTarget(0L, "01", "1999", "", 0, 0, 0,
                "", "", 0L, EnumStatus.Started, "", 0L, 0,
                null, null,null, null, 0, 0, null));

        ArrayList<MoneyTarget> data2 = LoadData(context);
        if (data2 != null)
            data.addAll(data2);

        return data;
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyTarget();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyTarget();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
