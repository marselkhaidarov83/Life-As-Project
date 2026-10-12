package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAssetSettings;

import java.util.ArrayList;
import java.util.List;

public class MoneyTypeAssetDataSource
{
    public static ArrayList<MoneyTypeAsset> LoadDataParent(Context context, boolean addEmptyItem,
                                                      MoneyTypeAssetSettings moneyTypeAssetSettings)
    {
        ArrayList<MoneyTypeAsset> data =
                (ArrayList<MoneyTypeAsset>) LoadDataChildren(context, 0L, moneyTypeAssetSettings);

        if (addEmptyItem)
            data.add(0, new MoneyTypeAsset(0L, "", "", 0L, 0, EnumStatus.Blank, 0));

        return data;
    }

    public static List<MoneyTypeAsset> LoadDataChildren(Context context, boolean withBlank,
                                                      EnumStatus status,
                                                        MoneyTypeAssetSettings moneyTypeAssetSettings)
    {
        List<MoneyTypeAsset> list = LoadDataChildren(context, withBlank, moneyTypeAssetSettings);
        List<MoneyTypeAsset> list2 = new ArrayList<>();
        for (MoneyTypeAsset moneyTypeAsset: list)
            if (moneyTypeAsset.get_status() == status)
                list2.add(moneyTypeAsset);
        return list2;
    }

    public static List<MoneyTypeAsset> LoadDataChildren(Context context, boolean withBlank,
                                                      MoneyTypeAssetSettings moneyTypeAssetSettings)
    {
        ArrayList<MoneyTypeAsset> data = new ArrayList<>();

        if (withBlank)
            data.add(0, new MoneyTypeAsset(0L, "", "", 0L, 0, EnumStatus.Blank, 0));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyTypeAsset(null, moneyTypeAssetSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_STATUS);
                int countIndex = cursor.getColumnIndex(DBHelper.key_MONEY_TYPEASSET_COUNT_ASSET);

                do
                {
                    String status = cursor.getString(statusIndex);
                    data.add(new MoneyTypeAsset(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getInt(positionIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status),
                            cursor.getInt(countIndex)));
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

    public static List<MoneyTypeAsset> LoadDataChildren(Context context, Long parentId,
                                                      MoneyTypeAssetSettings moneyTypeAssetSettings)
    {
        return LoadDataChildren(context, parentId, false, moneyTypeAssetSettings);
    }

    public static List<MoneyTypeAsset> LoadDataChildren(Context context, Long parentId,
                                                      boolean withBlank,
                                                        MoneyTypeAssetSettings moneyTypeAssetSettings)
    {
        ArrayList<MoneyTypeAsset> data = new ArrayList<>();

        if (withBlank)
            data.add(new MoneyTypeAsset(0L, "", "", 0L, 0, EnumStatus.Blank, 0));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyTypeAsset(parentId, moneyTypeAssetSettings);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_STATUS);
                int countIndex = cursor.getColumnIndex(DBHelper.key_MONEY_TYPEASSET_COUNT_ASSET);

                do
                {
                    String status = cursor.getString(statusIndex);
                    data.add(new MoneyTypeAsset(cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(commentIndex),
                            cursor.getLong(parentIndex),
                            cursor.getInt(positionIndex),
                            status == null ? EnumStatus.Blank : EnumStatus.valueOf(status),
                            cursor.getInt(countIndex)));
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

    public static MoneyLotAsset GetMoneyLotAssetFromTypeAssetId(Context context, Long id)
    {
        ArrayList<MoneyTypeAsset> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryOneMoneyTypeAsset(id);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_COMMENT);
                int parentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TYPEASSET_STATUS);
                int countIndex = cursor.getColumnIndex(DBHelper.key_MONEY_TYPEASSET_COUNT_ASSET);

                String status = cursor.getString(statusIndex);
                MoneyTypeAsset moneyTypeAsset = new MoneyTypeAsset(
                        cursor.getLong(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(commentIndex),
                        cursor.getLong(parentIndex),
                        cursor.getInt(positionIndex),
                        status == null ? EnumStatus.Blank : EnumStatus.valueOf(status),
                        cursor.getInt(countIndex));
                return moneyTypeAsset.GetMoneyLotAsset();
            }

            return null;
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
            return dbHelper.GetCountMoneyTypeAsset();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyTypeAsset();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
