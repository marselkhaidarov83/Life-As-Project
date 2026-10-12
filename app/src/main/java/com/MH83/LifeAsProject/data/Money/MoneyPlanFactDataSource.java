package com.MH83.LifeAsProject.data.Money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyPlan;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFact;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;

import java.util.ArrayList;
import java.util.List;

public class MoneyPlanFactDataSource
{
    public static List<MoneyPlanFact> LoadDataParent(Context context, boolean addEmptyItem,
                                                     MoneyPlanFactSettings moneyPlanFactSettings)
    {
        ArrayList<Long> parents = new ArrayList<>();
        parents.add(0L);

        ArrayList<MoneyPlanFact> data =
                (ArrayList<MoneyPlanFact>) LoadDataChildren(context, parents, moneyPlanFactSettings);

        if (addEmptyItem)
            data.add(new MoneyPlanFact(null, null, null));

        return data;
    }

    @SuppressLint("SuspiciousIndentation")
    public static List<MoneyPlanFact> LoadDataChildren(Context context,
                                                       ArrayList<Long> parentIds,
                                                       MoneyPlanFactSettings moneyPlanFactSettings)
    {
        ArrayList<MoneyPlanFact> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            GlobalClass gVar = (GlobalClass) context.getApplicationContext();
            Cursor cursor = dbHelper.QueryMoneyCategory(parentIds, moneyPlanFactSettings, gVar);
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
                            cursor.getLong(creditIndex),
                            cursor.getInt(archiveIndex) == 1,
                            cursor.getLong(iconIndex));
                    data.add(new MoneyPlanFact(moneyCategory, null, null));
                    categoryIds.add(moneyCategory.get_id());
                }
                while (cursor.moveToNext());
            }

            ArrayList<MoneyPlan> listPlan = MoneyPlanDataSource.LoadData(
                    context, moneyPlanFactSettings);

            for (MoneyPlanFact item: data)
                for (MoneyPlan itemPlan: listPlan)
                    if (item.getMoneyCategoryId().equals(itemPlan.getMoneyCategoryId()))
                    {
                        if (item.get_moneyPlan() == null)
                            item.set_moneyPlan(itemPlan);
                        else
                            item.get_moneyPlan().inc_sumLong(itemPlan.get_sumLong());
                    }

            MoneyTransactionSettings moneyTransactionSettings = new MoneyTransactionSettings(
                    EnumTransactionType.PNL,
                    moneyPlanFactSettings.get_typeCategory(),
                    gVar.get_hasUnpaidSubscription() ? null : moneyPlanFactSettings.get_isPlan(),
                    null,
                    null,
                    gVar.get_hasUnpaidSubscription() ? null : moneyPlanFactSettings.get_tagId());

            ArrayList<MoneyTransaction> moneyTransactionArrayList =
                    MoneyPNLDataSource.LoadDataInd(context,
                            moneyPlanFactSettings.get_customPeriodClass().get_start_dateC(),
                            moneyPlanFactSettings.get_customPeriodClass().get_end_dateC(),
                            moneyTransactionSettings);

            for (MoneyPlanFact item: data)
                for (MoneyTransaction itemPnl: moneyTransactionArrayList)
                {
                    MoneyPNLIndependent moneyPNLIndependent = (MoneyPNLIndependent)  itemPnl;
                    if (item.getMoneyCategoryId().equals(moneyPNLIndependent.getSubCategoryId()))
                    {
                        if (gVar.get_hasUnpaidSubscription() ||
                                moneyPlanFactSettings.get_isPlan() == null ||
                                (moneyPlanFactSettings.get_isPlan() && moneyPNLIndependent.get_isPlan()) ||
                                (!moneyPlanFactSettings.get_isPlan() && !moneyPNLIndependent.get_isPlan()))
                            item.inc_factValue(moneyPNLIndependent.get_totalSumLong());
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
