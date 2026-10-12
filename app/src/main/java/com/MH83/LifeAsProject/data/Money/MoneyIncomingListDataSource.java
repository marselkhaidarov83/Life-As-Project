package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingList;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MoneyIncomingListDataSource
{
    public static List<MoneyIncomingList> LoadDataParent(Context context, boolean addEmptyItem,
                                                         MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        ArrayList<Long> parents = new ArrayList<>();
        parents.add(0L);

        //TODO: можно при вызове обновлять позиции, начиная с 1
        ArrayList<MoneyIncomingList> data =
                (ArrayList<MoneyIncomingList>) LoadDataChildren(context, parents,
                        null, moneyIncomingAndCostSettings);

        if (addEmptyItem)
            data.add(new MoneyIncomingList(null, null));

        return data;
    }

    public static List<MoneyIncomingList> LoadDataChildren(Context context,
                                                           ArrayList<Long> parentIds,
                                                           ArrayList<Long> increaseIds,
                                                           MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        //TODO: можно при вызове обновлять позиции, начиная с 1
        ArrayList<MoneyIncomingList> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            GlobalClass gVar = (GlobalClass) context.getApplicationContext();
            Cursor cursor = dbHelper.QueryMoneyCategory(parentIds, moneyIncomingAndCostSettings, gVar);
            if (cursor == null) return data;
            ArrayList<Long> categoryIds = new ArrayList<>();

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
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_STATUS);
                int iconIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ICON);

                do
                {
                    MoneyCategory moneyCategory = new MoneyCategory(cursor.getLong(idIndex),
                            cursor.getLong(parentIdIndex),
                            cursor.getInt(positionIndex),
                            cursor.getString(nameIndex),
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
                            cursor.getLong(iconIndex));
                    data.add(new MoneyIncomingList(moneyCategory, null));
                    categoryIds.add(moneyCategory.get_id());
                }
                while (cursor.moveToNext());
            }

            if (increaseIds != null && categoryIds.size() > 0)
            {
                Hashtable<Long, Hashtable<Long, MoneyIncoming>> miList =
                        MoneyIncomingDataSource.LoadData(context, increaseIds, categoryIds,
                                moneyIncomingAndCostSettings);
                for (MoneyIncomingList itemList: data)
                    for (Long increaseId: increaseIds)
                        if (miList.get(increaseId) != null)
                        {
                            MoneyIncoming moneyIncoming = miList.get(increaseId).get(itemList.getMoneyCategoryId());
                            itemList.setMoneyIncoming(increaseId, moneyIncoming);
                        }
            }

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
