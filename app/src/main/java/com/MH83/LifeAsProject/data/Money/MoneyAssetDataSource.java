package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCurrency;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyAssetSettings;

import java.util.ArrayList;
import java.util.List;

public class MoneyAssetDataSource
{
    public static ArrayList<MoneyAsset> LoadDataParent(Context context, boolean addEmptyItem,
                                                               MoneyAssetSettings moneyAssetSettings)
    {
        ArrayList<MoneyAsset> data =
                (ArrayList<MoneyAsset>) LoadDataChildren(context, moneyAssetSettings);

        if (addEmptyItem)
            data.add(0, new MoneyAsset(0L, 0L, 0L,
                    "", "", 0,
                    EnumStatus.Blank, 0L, "",
                    EnumKindAsset.Conservative, "", EnumCurrency.RUB, false, 1));

        return data;
    }

    public static List<MoneyAsset> LoadDataChildren(Context context, boolean withBlank,
                                                      EnumStatus status,
                                                    MoneyAssetSettings moneyAssetSettings)
    {
        List<MoneyAsset> list = LoadDataChildren(context, withBlank, moneyAssetSettings);
        List<MoneyAsset> list2 = new ArrayList<>();
        for (MoneyAsset moneyTypeAsset: list)
            if (moneyTypeAsset.get_status() == status)
                list2.add(moneyTypeAsset);
        return list2;
    }

    public static List<MoneyAsset> LoadDataChildren(Context context, boolean withBlank,
                                                    MoneyAssetSettings moneyAssetSettings)
    {
        ArrayList<MoneyAsset> data = new ArrayList<>();

        if (withBlank)
            data.add(0, new MoneyAsset(0L, 0L, 0L,
                    "", "", 0,
                    EnumStatus.Blank, 0L, "",
                    EnumKindAsset.Conservative, "", EnumCurrency.RUB, false, 1));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyAsset(moneyAssetSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_ID);
                int walletIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_WALLET_ID);
                int creditIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_CREDIT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_COMMENT);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_STATUS);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_TYPE);
                int typeNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_ASSET_TYPE_NAME);
                int kindIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_KIND);
                int tickerIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_TICKER);
                int currencyIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_CURRENCY);
                int lotCountIndex = cursor.getColumnIndex(DBHelper.key_MONEY_ASSET_LOTCOUNT);

                do
                {
                    data.add(new MoneyAsset(
                            cursor.getLong(idIndex),
                            cursor.getLong(walletIdIndex),
                            cursor.getLong(creditIdIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getInt(positionIndex),
                            EnumStatus.valueOf(cursor.getString(statusIndex)),
                            cursor.getLong(typeIndex),
                            cursor.getString(typeNameIndex),
                            EnumKindAsset.valueOf(cursor.getString(kindIndex)),
                            cursor.getString(tickerIndex),
                            EnumCurrency.valueOf(cursor.getString(currencyIndex)),
                            false,
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

    public static MoneyAsset LoadOneAsset(Context context, Long id)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryOneMoneyAsset(id);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_ID);
                int walletIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_WALLET_ID);
                int creditIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_CREDIT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_COMMENT);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_STATUS);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_TYPE);
                int typeNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_ASSET_TYPE_NAME);
                int kindIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_KIND);
                int tickerIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_TICKER);
                int currencyIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_CURRENCY);
                int lotCountIndex = cursor.getColumnIndex(DBHelper.key_MONEY_ASSET_LOTCOUNT);

                return new MoneyAsset(
                        cursor.getLong(idIndex),
                        cursor.getLong(walletIdIndex),
                        cursor.getLong(creditIdIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(commentIndex),
                        cursor.getInt(positionIndex),
                        EnumStatus.valueOf(cursor.getString(statusIndex)),
                        cursor.getLong(typeIndex),
                        cursor.getString(typeNameIndex),
                        EnumKindAsset.valueOf(cursor.getString(kindIndex)),
                        cursor.getString(tickerIndex),
                        EnumCurrency.valueOf(cursor.getString(currencyIndex)),
                        false,
                        cursor.getInt(lotCountIndex));
            }

            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<MoneyAsset> LoadDataChildren(Context context,
                                                    MoneyAssetSettings moneyAssetSettings)
    {
        return LoadDataChildren(context, false, moneyAssetSettings);
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyAsset();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyAsset();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
