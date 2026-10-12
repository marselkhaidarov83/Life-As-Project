package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperationIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperationSettings;

import java.util.ArrayList;

public class MoneyLotAssetOperationDataSource
{
    public static ArrayList<MoneyLotAssetOperation> LoadData(Context context,
                                                            MoneyLotAssetOperationSettings moneyLotAssetOperationSettings,
                                                             boolean independent)
    {
        ArrayList<MoneyLotAssetOperation> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyLotAssetOperation(moneyLotAssetOperationSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ID);
                int operationIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_OPERATION);
                int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_CATEGORY);
                int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_SUBCATEGORY);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_YEAR);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_MONTH);
                int dateIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_DATE);
                int countIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_COUNT);
                int priceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_PRICE);
                int comissionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_COMISSION);
                int accumulatedPriceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ACCUMULATED_PRICE);

                do
                {
                    if (independent)
                        data.add(new MoneyLotAssetOperationIndependent(
                                cursor.getLong(idIndex),
                                null,
                                false,
                                EnumLotAssetOperation.valueOf(cursor.getString(operationIndex)),
                                cursor.getLong(categoryIndex),
                                cursor.getLong(subCategoryIndex),
                                cursor.getInt(countIndex),
                                cursor.getInt(countIndex),
                                cursor.getLong(priceIndex),
                                cursor.getLong(priceIndex),
                                cursor.getLong(comissionIndex),
                                cursor.getLong(comissionIndex),
                                cursor.getLong(accumulatedPriceIndex),
                                cursor.getInt(dateIndex),
                                cursor.getInt(monthIndex),
                                cursor.getInt(yearIndex)
                                ));
                    else
                        data.add(new MoneyLotAssetOperation(
                                cursor.getLong(idIndex),
                                null,
                                false,
                                EnumLotAssetOperation.valueOf(cursor.getString(operationIndex)),
                                cursor.getLong(categoryIndex),
                                cursor.getLong(subCategoryIndex),
                                cursor.getInt(countIndex),
                                cursor.getInt(countIndex),
                                cursor.getLong(priceIndex),
                                cursor.getLong(priceIndex),
                                cursor.getLong(comissionIndex),
                                cursor.getLong(comissionIndex),
                                cursor.getLong(accumulatedPriceIndex),
                                cursor.getInt(dateIndex),
                                cursor.getInt(monthIndex),
                                cursor.getInt(yearIndex)
                        ));
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

    public static ArrayList<MoneyLotAssetOperation> LoadData4Wallet(Context context, Long walletId)
    {
        ArrayList<MoneyLotAssetOperation> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyLotAssetOperation4Wallet(walletId);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ID);
                int operationIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_OPERATION);
                int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_CATEGORY);
                int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_SUBCATEGORY);
                int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_YEAR);
                int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_MONTH);
                int dateIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_DATE);
                int countIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_COUNT);
                int priceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_PRICE);
                int comissionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_COMISSION);
                int accumulatedPriceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ACCUMULATED_PRICE);

                do
                {
                    data.add(new MoneyLotAssetOperation(
                            cursor.getLong(idIndex),
                            null,
                            false,
                            EnumLotAssetOperation.valueOf(cursor.getString(operationIndex)),
                            cursor.getLong(categoryIndex),
                            cursor.getLong(subCategoryIndex),
                            cursor.getInt(countIndex),
                            cursor.getInt(countIndex),
                            cursor.getLong(priceIndex),
                            cursor.getLong(priceIndex),
                            cursor.getLong(comissionIndex),
                            cursor.getLong(comissionIndex),
                            cursor.getLong(accumulatedPriceIndex),
                            cursor.getInt(dateIndex),
                            cursor.getInt(monthIndex),
                            cursor.getInt(yearIndex)
                    ));
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
            return dbHelper.GetCountMoneyLotAssetOperation();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyLotAssetOperation();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
