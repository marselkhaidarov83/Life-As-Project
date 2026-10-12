package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyInc4CostIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyInc4CostDataSource
{
    public static ArrayList<MoneyTransaction> LoadData(Context context, Long pnlId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadDataFromCursor(dbHelper.QueryMoneyInc4Cost(pnlId));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<MoneyTransaction> LoadData(Context context, Calendar dateStart,
                                               Calendar dateEnd)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            ArrayList<MoneyTransaction> list1 = LoadDataFromCursorIndependent(
                    dbHelper.QueryMoneyInc4Cost(dateStart, dateEnd));

            ArrayList<MoneyTransaction> list2 = MoneyPNLDataSource.LoadDataInd(context,
                    EnumTypeCategory.Cost, dateStart, dateEnd);

            for (MoneyTransaction moneyTransaction: list2)
            {
                moneyTransaction.set_costCategoryName("");
                list1.add(moneyTransaction);
            }

            return list1;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static ArrayList<MoneyTransaction> LoadDataFromCursor(Cursor cursor)
    {
        ArrayList<MoneyTransaction> data = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_ID);
            int pnlIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_PNL_ID);
            int costIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_COST_ID);
            int costSubCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID);
            int costCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_CATEGORY_NAME);
            int costCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_CATEGORY_ICON);
            int costSubCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_SUB_CATEGORY_NAME);
            int costSubCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_SUB_CATEGORY_ICON);
            int incIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_ID);
            int incSubCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID);
            int incCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_CATEGORY_NAME);
            int incCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_CATEGORY_ICON);
            int incSubCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_SUB_CATEGORY_NAME);
            int incSubCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_SUB_CATEGORY_ICON);
            int dayIncIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_DATE_INT);
            int monthIncIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_MONTH_INT);
            int yearIncIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_YEAR_INT);
            int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_SUM_L);

            do
            {
                data.add(new MoneyInc4Cost(cursor.getLong(idIndex),
                        cursor.getLong(pnlIndex),
                        cursor.getInt(pnlIndex) == 0 ?
                                EnumTypeCategory.Wallet : EnumTypeCategory.Increase,
                        cursor.getLong(costIndex),
                        cursor.getLong(costSubCategoryIndex),
                        cursor.getLong(costSubCategoryIndex),
                        cursor.getString(costCategoryNameIndex),
                        cursor.getLong(costCategoryIconIndex),
                        cursor.getString(costSubCategoryNameIndex),
                        cursor.getLong(costSubCategoryIconIndex),
                        cursor.getLong(incIndex),
                        cursor.getLong(incSubCategoryIndex),
                        cursor.getLong(incSubCategoryIndex),
                        cursor.getString(incCategoryNameIndex),
                        cursor.getLong(incCategoryIconIndex),
                        cursor.getString(incSubCategoryNameIndex),
                        cursor.getLong(incSubCategoryIconIndex),
                        cursor.getInt(dayIncIndex),
                        cursor.getInt(monthIncIndex),
                        cursor.getInt(yearIncIndex),
                        cursor.getLong(sumLongIndex),
                        cursor.getLong(sumLongIndex)));
            }
            while (cursor.moveToNext());
        }

        return data;
    }

    private static ArrayList<MoneyTransaction> LoadDataFromCursorIndependent(Cursor cursor)
    {
        ArrayList<MoneyTransaction> data = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_ID);
            int pnlIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_PNL_ID);
            int costIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_COST_ID);
            int costSubCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID);
            int costCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_CATEGORY_NAME);
            int costCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_CATEGORY_ICON);
            int costSubCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_SUB_CATEGORY_NAME);
            int costSubCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_COST_SUB_CATEGORY_ICON);
            int incIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_ID);
            int incSubCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID);
            int incCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_CATEGORY_NAME);
            int incCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_CATEGORY_ICON);
            int incSubCategoryNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_SUB_CATEGORY_NAME);
            int incSubCategoryIconIndex = cursor.getColumnIndex(DBHelper.key_MONEY_INC4COST_INC_SUB_CATEGORY_ICON);
            int dayIncIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_DATE_INT);
            int monthIncIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_MONTH_INT);
            int yearIncIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_INC_YEAR_INT);
            int summIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_SUM);
            int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_INC4COST_SUM_L);

            do
            {
                data.add(new MoneyInc4CostIndependent(cursor.getLong(idIndex),
                        cursor.getLong(pnlIndex),
                        cursor.getInt(pnlIndex) == 0 ?
                                EnumTypeCategory.Wallet : EnumTypeCategory.Increase,
                        cursor.getLong(costIndex),
                        cursor.getLong(costSubCategoryIndex),
                        cursor.getLong(costSubCategoryIndex),
                        cursor.getString(costCategoryNameIndex),
                        cursor.getLong(costCategoryIconIndex),
                        cursor.getString(costSubCategoryNameIndex),
                        cursor.getLong(costSubCategoryIconIndex),
                        cursor.getLong(incIndex),
                        cursor.getLong(incSubCategoryIndex),
                        cursor.getLong(incSubCategoryIndex),
                        cursor.getString(incCategoryNameIndex),
                        cursor.getLong(incCategoryIconIndex),
                        cursor.getString(incSubCategoryNameIndex),
                        cursor.getLong(incSubCategoryIconIndex),
                        cursor.getInt(dayIncIndex),
                        cursor.getInt(monthIncIndex),
                        cursor.getInt(yearIncIndex),
                        cursor.getLong(sumLongIndex),
                        cursor.getLong(sumLongIndex)));
            }
            while (cursor.moveToNext());
        }

        return data;
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyInc4Cost();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyInc4Cost();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
