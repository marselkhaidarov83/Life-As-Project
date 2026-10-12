package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.Custom.TaggedDataSource;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyPNLDataSource
{
    public static ArrayList<MoneyTransaction> LoadDataInd(Context context,
                                                          Calendar dateStart, Calendar dateEnd,
                                                          MoneyTransactionSettings moneyTransactionSettings)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadDataFromCursorInd(context,
                    dbHelper.QueryMoneyPNL(
                            null, dateStart, dateEnd, moneyTransactionSettings));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<MoneyTransaction> LoadDataInd(Context context, EnumTypeCategory typeCategory,
                                               Calendar dateStart, Calendar dateEnd)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadDataFromCursorInd(context,
                    dbHelper.QueryMoneyPNL(typeCategory, dateStart, dateEnd, false));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<MoneyTransaction> LoadDataInd4Accumulation(Context context, EnumTypeCategory typeCategory,
                                                          Calendar dateStart, Calendar dateEnd)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadDataFromCursorInd(context,
                    dbHelper.QueryMoneyPNL(typeCategory, dateStart, dateEnd, true));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<MoneyTransaction> LoadDataInd4Accumulation(Context context,
                                                                       long accumulationId,
                                                                       Calendar dateStart, Calendar dateEnd)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadDataFromCursorInd(context,
                    dbHelper.QueryMoneyPNL(
//                            -1L,
                            accumulationId, dateStart, dateEnd, null));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Long LoadSumInd(Context context, EnumTypeCategory typeCategory,
                                     Calendar dateStart, Calendar dateEnd)
    {
        ArrayList<MoneyTransaction> arrayList = LoadDataInd(context, typeCategory,
                dateStart, dateEnd);

        Long sum = 0L;
        if (arrayList != null)
            for (MoneyTransaction item: arrayList)
                sum += item.get_sumLong();

        return sum;
    }

    public static Long LoadSumInd(Context context,
                                  long accumulationId,
                                  Calendar dateStart, Calendar dateEnd)
    {
        ArrayList<MoneyTransaction> arrayList = LoadDataInd4Accumulation(context,
                accumulationId,
                dateStart, dateEnd);

        Long sum = 0L;
        if (arrayList != null)
            for (MoneyTransaction item: arrayList)
                sum += item.get_sumLong();

        return sum;
    }

    public static Long LoadSumIndSavings(Context context,
                                  Calendar dateStart, Calendar dateEnd)
    {
        ArrayList<MoneyTransaction> arrayList = LoadDataInd4Accumulation(
                context, EnumTypeCategory.Wallet, dateStart, dateEnd);

        Long sum = 0L;
        if (arrayList != null)
            for (MoneyTransaction item: arrayList)
                if (item instanceof MoneyPNLIndependent)
                    if (((MoneyPNLIndependent) item).get_accumulationId() > 0)
                        sum += item.get_sumLong();

        return sum;
    }

    public static ArrayList<MoneyPNL> LoadData4Credit(Context context, EnumTypeCategory typeCategory,
                                                      Long creditId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            ArrayList<MoneyTransaction> list = LoadDataFromCursor(context,
                    dbHelper.QueryMoneyPNL4Credit(creditId, typeCategory));
            ArrayList<MoneyPNL> list2 = new ArrayList<>();

            for (MoneyTransaction item: list)
                list2.add((MoneyPNL) item);

            return list2;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<MoneyPNL> LoadData4Accumulation(Context context, EnumTypeCategory typeCategory,
                                                      int accumulationId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            ArrayList<MoneyTransaction> list = LoadDataFromCursor(context,
                    dbHelper.QueryMoneyPNL4Accumulation(accumulationId, typeCategory));
            ArrayList<MoneyPNL> list2 = new ArrayList<>();

            for (MoneyTransaction item: list)
                list2.add((MoneyPNL) item);

            return list2;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static ArrayList<MoneyTransaction> LoadDataFromCursorInd(Context context, Cursor cursor)
    {
        ArrayList<MoneyTransaction> data = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_ID);
            int increaseCategoryId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_INC_CATEGORY_ID);
            int increaseCategoryNameId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_INC_CATEGORY_NAME);
            int increaseCategoryTypeCategory = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY);
            int costCategoryId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_COST_CATEGORY_ID);
            int costCategoryNameId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_COST_CATEGORY_NAME);
            int costCategoryTypeCategory = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY);
            int increaseCategoryIconId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_INC_CATEGORY_ICON);
            int costCategoryIconId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_COST_CATEGORY_ICON);
            int subCategoryId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID);
            int subCategoryIconId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUB_CATEGORY_ICON);
            int subCategoryNameId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUB_CATEGORY_NAME);
            int dayId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_DATE_INT);
            int monthId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);
            int yearId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);
            int sumLongId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUM_L);
            int taskId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_TASK_ID);
            int commentId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_COMMENT);
            int isPlanId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_ISPLAN);
            int creditId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_CREDIT_ID);
            int accumulationId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_ACCUMULATION_ID);
            int calculatedId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_CALCULATED);
            int percentSumLongId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_PERCENTSUM_L);

            do
            {
                EnumTypeCategory typeCategory = EnumTypeCategory.Increase;
                if (EnumTypeCategory.Increase.toString().equals(cursor.getString(costCategoryTypeCategory)))
                    typeCategory = EnumTypeCategory.Increase;
                else if (EnumTypeCategory.Cost.toString().equals(cursor.getString(increaseCategoryTypeCategory)))
                    typeCategory = EnumTypeCategory.Cost;
                else if (EnumTypeCategory.Wallet.toString().equals(cursor.getString(increaseCategoryTypeCategory)) ||
                        EnumTypeCategory.Wallet.toString().equals(cursor.getString(costCategoryTypeCategory)))
                    typeCategory = EnumTypeCategory.Wallet;

                ArrayList<MoneyTransaction> list1 = MoneyInc4CostDataSource.LoadData(context, cursor.getLong(idIndex));
                ArrayList<MoneyInc4Cost> list2 = new ArrayList<>();
                for (MoneyTransaction moneyTransaction: list1)
                    list2.add((MoneyInc4Cost) moneyTransaction);

                ArrayList<Tagged> taggedArrayList = TaggedDataSource.LoadData4PNL(context, cursor.getLong(idIndex));

                data.add(new MoneyPNLIndependent(cursor.getLong(idIndex), typeCategory,
                        cursor.getLong(increaseCategoryId), cursor.getLong(increaseCategoryId),
                        cursor.getLong(costCategoryId), cursor.getLong(costCategoryId),
                        cursor.getLong(subCategoryId), cursor.getLong(subCategoryId),
                        cursor.getString(increaseCategoryNameId), cursor.getString(costCategoryNameId),
                        cursor.getString(subCategoryNameId),
                        cursor.getInt(dayId), cursor.getInt(monthId),
                        cursor.getInt(yearId),
                        cursor.getLong(sumLongId), cursor.getLong(sumLongId),
                        taskId == -1 ? 0 : cursor.getInt(taskId),
                        commentId == -1 ? "" : cursor.getString(commentId),
                        cursor.getLong(increaseCategoryIconId),
                        cursor.getLong(costCategoryIconId),
                        cursor.getLong(subCategoryIconId),
                        isPlanId != -1 && cursor.getInt(isPlanId) == 1,
                        isPlanId != -1 && cursor.getInt(isPlanId) == 1,
                        creditId == -1 ? 0 : cursor.getLong(creditId),
                        accumulationId == -1 ? 0 : cursor.getLong(accumulationId),
                        cursor.getString(calculatedId) == null ? false : cursor.getString(calculatedId).equals("1"),
                        cursor.getLong(percentSumLongId),
                        cursor.getLong(percentSumLongId),
                        list2,
                        null,
                        taggedArrayList,
                        null));
            }
            while (cursor.moveToNext());
        }

        return data;
    }

    private static ArrayList<MoneyTransaction> LoadDataFromCursor(Context context, Cursor cursor)
    {
        ArrayList<MoneyTransaction> data = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_ID);
            int increaseCategoryId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_INC_CATEGORY_ID);
            int increaseCategoryNameId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_INC_CATEGORY_NAME);
            int increaseCategoryTypeCategory = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY);
            int costCategoryId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_COST_CATEGORY_ID);
            int costCategoryNameId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_COST_CATEGORY_NAME);
            int costCategoryTypeCategory = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY);
            int increaseCategoryIconId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_INC_CATEGORY_ICON);
            int costCategoryIconId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_COST_CATEGORY_ICON);
            int subCategoryId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID);
            int subCategoryIconId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUB_CATEGORY_ICON);
            int subCategoryNameId = cursor.getColumnIndex(DBHelper.key_MONEY_PNL_SUB_CATEGORY_NAME);
            int dayId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_DATE_INT);
            int monthId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_MONTH_INT);
            int yearId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_YEAR_INT);
            int sumLongId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_SUM_L);
            int taskId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_TASK_ID);
            int commentId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_COMMENT);
            int isPlanId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_ISPLAN);
            int creditId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_CREDIT_ID);
            int accumulationId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_ACCUMULATION_ID);
            int calculatedId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_CALCULATED);
            int percentSumLongId = cursor.getColumnIndex(DBHelper.KEY_MONEY_PNL_PERCENTSUM_L);

            do
            {
                EnumTypeCategory typeCategory = EnumTypeCategory.Increase;
                if (EnumTypeCategory.Increase.toString().equals(cursor.getString(costCategoryTypeCategory)))
                    typeCategory = EnumTypeCategory.Increase;
                else if (EnumTypeCategory.Cost.toString().equals(cursor.getString(increaseCategoryTypeCategory)))
                    typeCategory = EnumTypeCategory.Cost;
                else if (EnumTypeCategory.Wallet.toString().equals(cursor.getString(increaseCategoryTypeCategory)) ||
                        EnumTypeCategory.Wallet.toString().equals(cursor.getString(costCategoryTypeCategory)))
                    typeCategory = EnumTypeCategory.Wallet;

                ArrayList<MoneyTransaction> list1 = MoneyInc4CostDataSource.LoadData(context, cursor.getLong(idIndex));
                ArrayList<MoneyInc4Cost> list2 = new ArrayList<>();
                for (MoneyTransaction moneyTransaction: list1)
                    list2.add((MoneyInc4Cost) moneyTransaction);

                ArrayList<Tagged> taggedArrayList = TaggedDataSource.LoadData4PNL(context, cursor.getLong(idIndex));

                data.add(new MoneyPNL(cursor.getLong(idIndex), typeCategory,
                        cursor.getLong(increaseCategoryId), cursor.getLong(increaseCategoryId),
                        cursor.getLong(costCategoryId), cursor.getLong(costCategoryId),
                        cursor.getLong(subCategoryId), cursor.getLong(subCategoryId),
                        cursor.getString(increaseCategoryNameId), cursor.getString(costCategoryNameId),
                        cursor.getString(subCategoryNameId),
                        cursor.getInt(dayId), cursor.getInt(monthId),
                        cursor.getInt(yearId),
                        cursor.getLong(sumLongId), cursor.getLong(sumLongId),
                        taskId == -1 ? 0 : cursor.getInt(taskId),
                        commentId == -1 ? "" : cursor.getString(commentId),
                        cursor.getLong(increaseCategoryIconId),
                        cursor.getLong(costCategoryIconId),
                        subCategoryIconId == -1? 0 : cursor.getLong(subCategoryIconId),
                        isPlanId != -1 && cursor.getInt(isPlanId) == 1,
                        isPlanId != -1 && cursor.getInt(isPlanId) == 1,
                        creditId == -1 ? 0 : cursor.getLong(creditId),
                        accumulationId == -1 ? 0 : cursor.getLong(accumulationId),
                        cursor.getString(calculatedId).equals("1"),
                        cursor.getLong(percentSumLongId),
                        cursor.getLong(percentSumLongId),
                        list2,
                        null,
                        taggedArrayList,
                        null));
            }
            while (cursor.moveToNext());
        }

        return data;
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyPNL();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyPNL();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
