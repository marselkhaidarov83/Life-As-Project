package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoneyCategoryDataSource
{
    public static ArrayList<MoneyCategory> LoadDataParent(Context context,
                                                          boolean addEmptyItem,
                                                          MoneyCategorySettings moneyCategorySettings)
    {
        ArrayList<MoneyCategory> data =
                LoadDataChildren(context, 0L, moneyCategorySettings);

        if (addEmptyItem)
            data.add(0, new MoneyCategory(0L, 0L, 0, "", 0L,
                    moneyCategorySettings.get_typeCategory(),
                    false, false, false, false,
                    0L, EnumCategoryType.Base,
                    0L, false, 0L));

        return data;
    }

    public static Long LoadSumDataParent(Context context,
                                         MoneyCategorySettings moneyCategorySettings)
    {
        List<MoneyCategory> list = LoadDataParent(context, false, moneyCategorySettings);

        Long sum = 0L;
        if (list != null)
            for (MoneyCategory category : list)
                sum += category.get_sumLong();
        return sum;
    }

    public static Long LoadSumDataChildren(Context context,
                                           MoneyCategorySettings moneyCategorySettings)
    {
        List<MoneyCategory> list = LoadDataChildren(context, null, moneyCategorySettings);

        Long sum = 0L;
        if (list != null)
            for (MoneyCategory category : list)
                sum += category.get_sumLong();
        return sum;
    }

    public static Long LoadSumDataParentSavings(Context context,
                                                MoneyCategorySettings moneyCategorySettings,
                                                ArrayList<MoneyTarget> activeTargets)
    {
        List<MoneyCategory> list = LoadDataParent(context, false, moneyCategorySettings);

        Long sum = 0L;
        for (MoneyCategory category: list)
            if (category.get_targetId() > 0)
            {
                boolean existTarget = false;
                if (activeTargets != null)
                    for (MoneyTarget item: activeTargets)
                        if (Objects.equals(item.get_id(), category.get_targetId()))
                        {
                            existTarget = true;
                            break;
                        }
                if (activeTargets == null || existTarget)
                    sum += category.get_sumLong();
            }
        return sum;
    }

    public static ArrayList<MoneyCategory> LoadDataChildrenWithEmpty(Context context, Long parentId,
                                                            MoneyCategorySettings moneyCategorySettings,
                                                                     boolean addEmptyItem)
    {
        ArrayList<MoneyCategory> data = LoadDataChildren(context, parentId, moneyCategorySettings);

        if (addEmptyItem)
            data.add(0, new MoneyCategory(0L, 0L, 0, "", 0L,
                    moneyCategorySettings.get_typeCategory(), false, false, false, false,
                    0L, EnumCategoryType.Base,
//                    EnumCategoryStatus.Active,
                    0L, false, 0L));

        return data;
    }


    public static ArrayList<MoneyCategory> LoadDataChildren(Context context, Long parentId,
                                                       MoneyCategorySettings moneyCategorySettings)
    {
        ArrayList<MoneyCategory> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            GlobalClass gVar = (GlobalClass) context.getApplicationContext();
            Cursor cursor = dbHelper.QueryMoneyCategory(parentId, moneyCategorySettings, gVar);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ID);
                int parentIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_POSITION);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_NAME);
                int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_SUM_L);
                int typeCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_TYPE_CATEGORY);
                int sumtobalanceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_SUMTOBALANCE);
                int creditIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_CREDIT_ID);
                int archiveIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ARCHIVE);
                int targetIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_TARGET_ID);
                int countChildIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_CHILD);
                int countIncomingIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_INCOMING);
                int countPNLINCIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_PNL_INC);
                int countPNLCOSTIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_PNL_COST);
                int countPNLSUBIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_PNL_SUB);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_TYPE);
                int iconIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ICON);

                do
                {
                    data.add(new MoneyCategory(cursor.getLong(idIndex),
                            cursor.getLong(parentIdIndex),
                            cursor.getInt(positionIndex), cursor.getString(nameIndex),
                            cursor.getLong(sumLongIndex),
                            EnumTypeCategory.valueOf(cursor.getString(typeCategoryIndex)),
                            cursor.getInt(countChildIndex) > 0,
                            cursor.getInt(countIncomingIndex) > 0,
                            cursor.getInt(countPNLINCIndex) > 0 ||
                                    cursor.getInt(countPNLCOSTIndex) > 0 ||
                                    cursor.getInt(countPNLSUBIndex) > 0,
                            sumtobalanceIndex == -1 ? false : cursor.getInt(sumtobalanceIndex) == 1,
                            targetIndex == -1 ? 0 : cursor.getLong(targetIndex),
                            cursor.getString(typeIndex) == null ?
                                    ( EnumTypeCategory.valueOf(cursor.getString(typeCategoryIndex)) == EnumTypeCategory.Wallet ?
                                            EnumCategoryType.WalletWallet : EnumCategoryType.Base ) :
                                    EnumCategoryType.valueOf(cursor.getString(typeIndex)),
//                            cursor.getString(statusIndex) == null ?
//                                    EnumCategoryStatus.Active :
//                                    EnumCategoryStatus.valueOf(cursor.getString(statusIndex)),
                            cursor.getLong(creditIndex),
                            cursor.getInt(archiveIndex) == 1,
                            cursor.getLong(iconIndex)));
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

    public static MoneyCategory LoadOne(Context context, Long categoryId)
    {
        ArrayList<MoneyCategory> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.OneMoneyCategory(categoryId);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ID);
                int parentIdIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_PARENT_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_POSITION);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_NAME);
//                int sumIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_SUM);
                int sumLongIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_SUM_L);
                int typeCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_TYPE_CATEGORY);
                int sumtobalanceIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_SUMTOBALANCE);
                int creditIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_CREDIT_ID);
                int archiveIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ARCHIVE);
                int targetIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_TARGET_ID);
                int countChildIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_CHILD);
                int countIncomingIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_INCOMING);
                int countPNLINCIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_PNL_INC);
                int countPNLCOSTIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_PNL_COST);
                int countPNLSUBIndex = cursor.getColumnIndex(DBHelper.key_MONEY_CATEGORY_COUNT_PNL_SUB);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_TYPE);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_STATUS);
                int iconIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ICON);

                do
                {
                    data.add(new MoneyCategory(cursor.getLong(idIndex),
                            cursor.getLong(parentIdIndex),
                            cursor.getInt(positionIndex), cursor.getString(nameIndex),
                            cursor.getLong(sumLongIndex),
                            EnumTypeCategory.valueOf(cursor.getString(typeCategoryIndex)),
                            cursor.getInt(countChildIndex) > 0,
                            cursor.getInt(countIncomingIndex) > 0,
                            cursor.getInt(countPNLINCIndex) > 0 ||
                                    cursor.getInt(countPNLCOSTIndex) > 0 ||
                                    cursor.getInt(countPNLSUBIndex) > 0,
                            sumtobalanceIndex == -1 ? false : cursor.getInt(sumtobalanceIndex) == 1,
                            targetIndex == -1 ? 0 : cursor.getLong(targetIndex),
                            cursor.getString(typeIndex) == null ?
                                    ( EnumTypeCategory.valueOf(cursor.getString(typeCategoryIndex)) == EnumTypeCategory.Wallet ?
                                            EnumCategoryType.WalletWallet : EnumCategoryType.Base ) :
                                    EnumCategoryType.valueOf(cursor.getString(typeIndex)),
//                            cursor.getString(statusIndex) == null ?
//                                    EnumCategoryStatus.Active :
//                                    EnumCategoryStatus.valueOf(cursor.getString(statusIndex)),
                            cursor.getLong(creditIndex),
                            cursor.getInt(archiveIndex) == 1,
                            cursor.getLong(iconIndex)));
                }
                while (cursor.moveToNext());
            }

            return data.get(0);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Long LoadOneId4Target(Context context, Long targetId)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.OneMoneyCategoryId4Target(targetId, EnumTypeCategory.Wallet);
        }
        catch (Exception e)
        {
            return 0L;
        }
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyCategory();
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyCategory();
        }
        catch (Exception e)
        {
            return 0;
        }
    }}
