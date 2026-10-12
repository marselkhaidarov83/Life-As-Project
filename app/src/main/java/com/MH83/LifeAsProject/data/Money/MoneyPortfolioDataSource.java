package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolio;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolioSettings;

import java.util.ArrayList;
import java.util.List;

public class MoneyPortfolioDataSource
{
    public static ArrayList<MoneyPortfolio> LoadDataParent(Context context, boolean addEmptyItem,
                                                      MoneyPortfolioSettings moneyPortfolioSettings)
    {
        ArrayList<MoneyPortfolio> data = LoadDataChildren(context, 0L, null, moneyPortfolioSettings);

        if (addEmptyItem)
            data.add(0, new MoneyPortfolio(0L,
                    "",
                    "",
                    0L,
                    0,
                    EnumStatus.Blank,
                    0L,
                    0L,
                    0));

        return data;
    }

    public static List<MoneyPortfolio> LoadDataChildren(Context context, boolean withBlank,
                                                      EnumStatus status,
                                                      MoneyPortfolioSettings moneyPortfolioSettings)
    {
        List<MoneyPortfolio> list = LoadDataChildren(context, withBlank, moneyPortfolioSettings);
        List<MoneyPortfolio> list2 = new ArrayList<>();
        for (MoneyPortfolio moneyPortfolio: list)
            if (moneyPortfolio.get_status() == status)
                list2.add(moneyPortfolio);
        return list2;
    }

    public static List<MoneyPortfolio> LoadDataChildren(Context context, boolean withBlank,
                                                      MoneyPortfolioSettings moneyPortfolioSettings)
    {
        ArrayList<MoneyPortfolio> data = new ArrayList<>();

        if (withBlank)
            data.add(0, new MoneyPortfolio(0L,
                    "",
                    "",
                    0L,
                    0,
                    EnumStatus.Blank,
                    0L,
                    0L,
                    0));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyPortfolio(null, null, moneyPortfolioSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_STATUS);
                int walletIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_WALLET);
                int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_SUBCATEGORY);
                int lotCountIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PORTFOLIO_LOTCOUNT);

                do
                {
                    String status = cursor.getString(statusIndex);
                    data.add(new MoneyPortfolio(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getInt(positionIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status),
                            cursor.getLong(walletIndex),
                            cursor.getLong(subCategoryIndex),
                            cursor.getInt(lotCountIndex)));
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

    public static ArrayList<MoneyPortfolio> LoadDataChildren(Context context, Long parentId,
                                                             Long id,
                                                            MoneyPortfolioSettings moneyPortfolioSettings)
    {
        return LoadDataChildren(context, parentId, id, false, moneyPortfolioSettings);
    }

    public static ArrayList<MoneyPortfolio> LoadDataChildren(Context context,
                                                             Long parentId,
                                                      Long id,
                                                      boolean withBlank,
                                                      MoneyPortfolioSettings moneyPortfolioSettings)
    {
        ArrayList<MoneyPortfolio> data = new ArrayList<>();

        if (withBlank)
            data.add(new MoneyPortfolio(0L,
                    "",
                    "",
                    0L,
                    0,
                    EnumStatus.Blank,
                    0L,
                    0L,
                    0));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyPortfolio(parentId, id, moneyPortfolioSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_STATUS);
                int walletIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_WALLET);
                int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_SUBCATEGORY);
                int lotCountIndex = cursor.getColumnIndex(DBHelper.key_MONEY_PORTFOLIO_LOTCOUNT);

                do
                {
                    String status = cursor.getString(statusIndex);
                    data.add(new MoneyPortfolio(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getInt(positionIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status),
                            cursor.getLong(walletIndex),
                            cursor.getLong(subCategoryIndex),
                            cursor.getInt(lotCountIndex)));
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

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyPortfolio();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyPortfolio();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
