package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumAccumulationType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyAccumulation;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import java.util.ArrayList;

public class MoneyAccumulationDataSource
{
    public static MoneyAccumulation LoadData(Context context, Integer targetId)
    {
        MoneyAccumulation moneyAccumulation = null;

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyAccumulation(targetId);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_ID);
                int targetIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_TARGET_ID);
                int accumulationTypeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_TYPE);
                int progressionCoefficentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_PROGRESSION_COEF);
                int monthStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_MONTH_START);
                int yearStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_YEAR_START);
                int monthEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_MONTH_END);
                int yearEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_YEAR_END);
                int startSumIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_START_SUM);
                int nameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_ACCUMULATION_NAME);

                ArrayList<MoneyPNL> pnls = MoneyPNLDataSource.LoadData4Accumulation(context,
                        EnumTypeCategory.Wallet, cursor.getInt(idIndex));

                moneyAccumulation = new MoneyAccumulation(cursor.getLong(idIndex),
                        cursor.getLong(targetIdIndex),
                        cursor.getString(nameIndex),
                        EnumAccumulationType.valueOf(cursor.getString(accumulationTypeIndex)),
                        cursor.getInt(progressionCoefficentIndex),
                        cursor.getInt(monthStartIndex),
                        cursor.getInt(yearStartIndex),
                        cursor.getInt(monthEndIndex),
                        cursor.getInt(yearEndIndex),
                        cursor.getInt(startSumIndex),
                        pnls,
                        null);

            }

            return moneyAccumulation;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<MoneyAccumulation> LoadData(Context context,
                                                        boolean withPnl,
                                                        boolean withBlank)
    {
        ArrayList<MoneyAccumulation> moneyAccumulationArrayList = new ArrayList<>();

        if (withBlank)
            moneyAccumulationArrayList.add(new MoneyAccumulation(0L, 0L, "",
                    EnumAccumulationType.Linear, 0, 0, 0,
                    0, 0, 0, null, null));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyAccumulation(null);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_ID);
                int targetIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_TARGET_ID);
                int accumulationTypeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_TYPE);
                int progressionCoefficentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_PROGRESSION_COEF);
                int monthStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_MONTH_START);
                int yearStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_YEAR_START);
                int monthEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_MONTH_END);
                int yearEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_YEAR_END);
                int startSumIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ACCUMULATION_START_SUM);
                int nameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_ACCUMULATION_NAME);

                do
                {
                    ArrayList<MoneyPNL> pnls = null;
                    if (withPnl)
                        pnls = MoneyPNLDataSource.LoadData4Accumulation(context,
                                EnumTypeCategory.Wallet, cursor.getInt(idIndex));

                    MoneyAccumulation moneyAccumulation = new MoneyAccumulation(cursor.getLong(idIndex),
                            cursor.getLong(targetIdIndex),
                            cursor.getString(nameIndex),
                            EnumAccumulationType.valueOf(cursor.getString(accumulationTypeIndex)),
                            cursor.getInt(progressionCoefficentIndex),
                            cursor.getInt(monthStartIndex),
                            cursor.getInt(yearStartIndex),
                            cursor.getInt(monthEndIndex),
                            cursor.getInt(yearEndIndex),
                            cursor.getInt(startSumIndex),
                            pnls,
                            null);

                    moneyAccumulationArrayList.add(moneyAccumulation);
                }
                while (cursor.moveToNext());
            }

            return moneyAccumulationArrayList;
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
            return dbHelper.GetCountMoneyAccumulation();
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
