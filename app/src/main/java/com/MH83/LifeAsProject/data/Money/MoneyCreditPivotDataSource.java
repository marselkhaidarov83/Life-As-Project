package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCreditPivotPoint;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyCreditPivotDataSource
{
    public static ArrayList<MoneyCreditPivotPoint> LoadData4Credit(Context context,
                                                      Long creditId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.GetMoneyPivotPoints4Credit(creditId);
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

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
