package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCredit;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyCreditPivotPoint;

import java.util.ArrayList;

public class MoneyCreditDataSource
{
//    public static ArrayList<MoneyLotAsset> LoadData4LotAsset(Context context,
//                                                             MoneyLotAssetSettings moneyLotAssetSettings) {
//        ArrayList<MoneyLotAsset> list = new ArrayList<>();
//        for (MoneyCredit moneyCredit : LoadData(context))
//        {
//            MoneyAsset moneyAsset = new MoneyAsset();
//
//            list.add(new MoneyLotAsset(0L,
//                    groupPortfolioId,
//                    groupPortfolioName,
//                    portfolioId,
//                    portfolioName,
//                    moneyAsset,
//                    moneyCredit.get_name(),
//                    "",
//                    0,
//                    1,
//                    moneyCredit.get_calendarStart(),
//                    moneyCredit.get_calendarEnd(),
//                    EnumStatus.Started,
//                    null,
//                    null));
//            }
//
//        return list;
//    }

    public static ArrayList<MoneyCredit> LoadData(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadFromCursor(dbHelper.QueryMoneyCredit());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static MoneyCredit GetOneMoneyCredit(Context context, Long creditId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            MoneyCredit moneyCredit = LoadFromCursor(dbHelper.GetOneMoneyCredit(creditId)).get(0);

            Cursor cursor = dbHelper.GetMoneyPivotPoints4Credit(moneyCredit.get_id());
            ArrayList<MoneyCreditPivotPoint> data = new ArrayList<>();
            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_ID);
                int creditIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID);
                int startSumIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_STARTSUM_L);
                int percentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_PERCENT);
                int loanTermIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_LOANTERM);

                do
                {
                    data.add(new MoneyCreditPivotPoint(
                            cursor.getLong(idIndex),
                            cursor.getLong(creditIdIndex),
                            cursor.getLong(startSumIndex),
                            cursor.getDouble(percentIndex),
                            cursor.getInt(loanTermIndex)));
                }
                while (cursor.moveToNext());
            }
            moneyCredit.set_points(data);
            return moneyCredit;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static MoneyCredit GetOneMoneyCredit4Target(Context context, Long targetId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return LoadFromCursor(dbHelper.GetOneMoneyCredit4Target(targetId)).get(0);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static ArrayList<MoneyCredit> LoadFromCursor(Cursor cursor)
    {
        ArrayList<MoneyCredit> data = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_ID);
            int targetIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_TARGET_ID);
            int targetNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CREDIT_TARGET_NAME);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_NAME);
            int startSumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_STARTSUM_L);
            int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_SUM_L);
            int percentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_PERCENT);
            int dateStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_DATESTART_INT);
            int monthStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_MONTHSTART_INT);
            int yearStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_YEARSTART_INT);
            int loanTermIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_LOANTERM);
            int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_TYPE);
            int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_STATUS);
            int walletIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_WALLET);
            int inc4CostCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_INC4COST_CATEGORY);
            int inc4CostSubCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CREDIT_INC4COST_SUBCATEGORY);

            do
            {
                data.add(new MoneyCredit(cursor.getLong(idIndex),
                        cursor.getLong(targetIndex),
                        cursor.getString(targetNameIndex),
                        cursor.getString(nameIndex),
                        cursor.getLong(startSumLongIndex),
                        cursor.getLong(startSumLongIndex),
                        cursor.getLong(sumLongIndex),
                        cursor.getDouble(percentIndex),
                        cursor.getInt(dateStartIndex),
                        cursor.getInt(monthStartIndex),
                        cursor.getInt(yearStartIndex),
                        cursor.getInt(loanTermIndex),
                        EnumTypeCredit.valueOf(cursor.getString(typeIndex)),
                        EnumCreditStatus.valueOf(cursor.getString(statusIndex)),
                        EnumCreditStatus.valueOf(cursor.getString(statusIndex)),
                        cursor.getLong(walletIndex),
                        cursor.getLong(walletIndex),
                        cursor.getLong(inc4CostCategoryIndex),
                        cursor.getLong(inc4CostSubCategoryIndex),
                        null, null,
                        null, null));
            }
            while (cursor.moveToNext());
        }

        return data;
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyCredit();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyCredit();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
