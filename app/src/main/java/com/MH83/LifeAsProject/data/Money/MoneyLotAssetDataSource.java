package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperationSettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyLotAssetDataSource
{
    public static ArrayList<MoneyLotAsset> LoadData(Context context,
                                                            boolean withBlank,
                                                            MoneyLotAssetSettings moneyLotAssetSettings,
                                                            Long moneyLotAssetId)
    {
        ArrayList<MoneyLotAsset> data = new ArrayList<>();

        if (withBlank)
            data.add(0,
                    new MoneyLotAsset(0L,
                            0L,
                            "",
                            0L,
                            "",
                            0L,
                            null,
                            "", "", 0, 0,
                            Calendar.getInstance(), Calendar.getInstance(),
                            EnumStatus.Started,
                            null,
                            null));

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor;

            //region Wallet lot
            if (moneyLotAssetId == null)
            {
                cursor = dbHelper.QueryMoneyLotAsset4Wallet(moneyLotAssetSettings, null);
                if (cursor.moveToFirst())
                {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_ID);
                    int groupPortfolioIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID);
                    int groupPortfolioNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME);
                    int portfolioIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_PORTFOLIO_ID);
                    int portfolioNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_PORTFOLIO_NAME);
                    int portfolioWalletIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_PORTFOLIO_WALLET);
                    int assetIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_ASSET_ID);
                    int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_POSITION);
                    int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_STATUS);
                    int countIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_COUNT);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_NAME);
                    int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_COMMENT);
                    int dateStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_DATE_START);
                    int monthStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_START);
                    int yearStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_START);
                    int dateEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_DATE_END);
                    int monthEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_END);
                    int yearEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_END);
                    int walletIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_WALLET_ID);

                    do {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(cursor.getInt(yearStartIndex), cursor.getInt(monthStartIndex), cursor.getInt(dateStartIndex));
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.set(cursor.getInt(yearEndIndex), cursor.getInt(monthEndIndex), cursor.getInt(dateEndIndex));
                        MoneyAsset moneyAsset = MoneyAssetDataSource.LoadOneAsset(context, cursor.getLong(assetIndex));

                        ArrayList<MoneyLotAssetOperation> list = new ArrayList<>();
                        MoneyTransactionSettings moneyTransactionSettings = new MoneyTransactionSettings(
                                EnumTransactionType.PNL,
                                EnumTypeCategory.All,
                                null,
                                cursor.getLong(walletIndex),
                                null,
                                null);
                        ArrayList<MoneyTransaction> pnlList = MoneyPNLDataSource.LoadDataInd(
                                context,
//                                cursor.getLong(walletIndex),
                                calendar1, calendar2,
                                moneyTransactionSettings);
                        for (MoneyTransaction item : pnlList)
                            if (!item.get_isPlan())
                            {
                                EnumLotAssetOperation lotAssetOperation = EnumLotAssetOperation.PNLPlus;
                                if (item.get_typeCategory().equals(EnumTypeCategory.Cost))
                                    lotAssetOperation = EnumLotAssetOperation.PNLMinus;
                                else if (item.get_typeCategory().equals(EnumTypeCategory.Wallet) &&
                                        item.getCostCategoryId() == cursor.getLong(walletIndex))
                                    lotAssetOperation = EnumLotAssetOperation.PNLMinus;

                                list.add(new MoneyLotAssetOperation(
                                        -1000000000 + item.get_id(),
                                        null,
                                        false,
                                        lotAssetOperation,
                                        0L,
                                        0L,
                                        1,
                                        1,
                                        item.get_totalSumLong(),
                                        item.get_totalSumLong(),
                                        0L,
                                        0L,
                                        0L,
                                        item.getCalendar()));
                            }

                        MoneyLotAssetOperationSettings moneyLotAssetOperationSettings =
                                new MoneyLotAssetOperationSettings(cursor.getLong(idIndex));
                        ArrayList<MoneyLotAssetOperation> list2 =
                                MoneyLotAssetOperationDataSource.LoadData(context,
                                        moneyLotAssetOperationSettings, false);
                        for (MoneyLotAssetOperation item2: list2)
                        {
                            int pos = 0;
                            for (int i = list.size() - 1; i >= 0; i--)
                                if (item2.get_calendar().getTime().getTime() <
                                        list.get(i).get_calendar().getTime().getTime())
                                {
                                    pos = i + 1;
                                    break;
                                }
                            list.add(pos, item2);
                        }

                        ArrayList<MoneyLotAssetOperation> list3 =
                                MoneyLotAssetOperationDataSource.LoadData4Wallet(context, cursor.getLong(walletIndex));
                        for (MoneyLotAssetOperation item3: list3)
                        {
                            int pos = 0;
                            for (int i = list.size() - 1; i >= 0; i--)
                                if (item3.get_calendar().getTime().getTime() <
                                        list.get(i).get_calendar().getTime().getTime())
                                {
                                    pos = i + 1;
                                    break;
                                }
                            list.add(pos, item3);
                        }

                        if (moneyLotAssetSettings == null ||
                                moneyLotAssetSettings.get_kindAsset() == moneyAsset.get_kindAsset()) {
                            data.add(new MoneyLotAsset(
                                    cursor.getLong(idIndex),
                                    cursor.getLong(groupPortfolioIndex),
                                    cursor.getString(groupPortfolioNameIndex),
                                    cursor.getLong(portfolioIndex),
                                    cursor.getString(portfolioNameIndex),
                                    cursor.getLong(portfolioWalletIndex),
                                    moneyAsset,
                                    cursor.getString(nameIndex),
                                    cursor.getString(commentIndex),
                                    cursor.getInt(positionIndex),
                                    cursor.getInt(countIndex),
                                    calendar1, calendar2,
                                    EnumStatus.valueOf(cursor.getString(statusIndex)),
                                    list,
                                    null));

                            for (MoneyLotAssetOperation item : list)
                                item.set_moneyLotAsset(data.get(data.size() - 1));
                        }
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
            }
            //endregion

            //region Other lot
            cursor = dbHelper.QueryMoneyLotAsset(moneyLotAssetSettings, moneyLotAssetId);
            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_ID);
                int groupPortfolioIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID);
                int groupPortfolioNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME);
                int portfolioIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_PORTFOLIO_ID);
                int portfolioNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_PORTFOLIO_NAME);
                int portfolioWalletIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_PORTFOLIO_WALLET);
                int assetIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_ASSET_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_STATUS);
                int countIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_COUNT);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_COMMENT);
                int dateStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_DATE_START);
                int monthStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_START);
                int yearStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_START);
                int dateEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_DATE_END);
                int monthEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_END);
                int yearEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_END);
                int creditIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_ASSET_CREDIT_ID);

                do
                {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(cursor.getInt(yearStartIndex), cursor.getInt(monthStartIndex), cursor.getInt(dateStartIndex));
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(cursor.getInt(yearEndIndex), cursor.getInt(monthEndIndex), cursor.getInt(dateEndIndex));
                    MoneyAsset moneyAsset = MoneyAssetDataSource.LoadOneAsset(context, cursor.getLong(assetIndex));
                    MoneyLotAssetOperationSettings moneyLotAssetOperationSettings =
                            new MoneyLotAssetOperationSettings(cursor.getLong(idIndex));
                    ArrayList<MoneyLotAssetOperation> list = new ArrayList<>();

                    long creditId = cursor.getLong(creditIndex);
                    if (creditId == 0)
                        list = MoneyLotAssetOperationDataSource.LoadData(context, moneyLotAssetOperationSettings, false);
                    else
                    {
                        ArrayList<MoneyPNL> pnlList = MoneyPNLDataSource.LoadData4Credit(
                                context, EnumTypeCategory.Cost, creditId);
                        for (MoneyPNL item: pnlList)
                            if (!item.get_isPlan())
                                list.add(0, new MoneyLotAssetOperation(
                                        -100000000 + item.get_id(),
                                        null,
                                        false,
                                        EnumLotAssetOperation.CreditMinus,
                                        0L,
                                        0L,
                                        1,
                                        1,
                                        item.get_sumLong(),
                                        item.get_sumLong(),
                                        0L,
                                        0L,
                                        0L,
                                        item.getCalendar()));

                        pnlList = MoneyPNLDataSource.LoadData4Credit(
                                context, EnumTypeCategory.Increase, creditId);
                        for (MoneyPNL item: pnlList)
                            if (!item.get_isPlan())
                                list.add(new MoneyLotAssetOperation(
                                        -100000000 + item.get_id(),
                                        null,
                                        false,
                                        EnumLotAssetOperation.CreditPlus,
                                        0L,
                                        0L,
                                        1,
                                        1,
                                        item.get_sumLong(),
                                        item.get_sumLong(),
                                        0L,
                                        0L,
                                        0L,
                                        item.getCalendar()));
                    }

                    if (moneyLotAssetSettings == null ||
                            moneyLotAssetSettings.get_kindAsset() == moneyAsset.get_kindAsset())
                    {
                        data.add(new MoneyLotAsset(
                                cursor.getLong(idIndex),
                                cursor.getLong(groupPortfolioIndex),
                                cursor.getString(groupPortfolioNameIndex),
                                cursor.getLong(portfolioIndex),
                                cursor.getString(portfolioNameIndex),
                                cursor.getLong(portfolioWalletIndex),
                                moneyAsset,
                                cursor.getString(nameIndex),
                                cursor.getString(commentIndex),
                                cursor.getInt(positionIndex),
                                cursor.getInt(countIndex),
                                calendar1, calendar2,
                                EnumStatus.valueOf(cursor.getString(statusIndex)),
                                list,
                                null));

                        for (MoneyLotAssetOperation item: list)
                            item.set_moneyLotAsset(data.get(data.size() - 1));
                    }
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            //endregion

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static MoneyLotAsset LoadData4WalletAsset(Context context,
                                                    Long walletId)
    {
        MoneyLotAsset data = null;

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor;

            //region Wallet lot
            cursor = dbHelper.QueryMoneyLotAsset4Wallet(null, walletId);
            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_ID);
                int groupPortfolioIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID);
                int groupPortfolioNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME);
                int portfolioIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_PORTFOLIO_ID);
                int portfolioNameIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_PORTFOLIO_NAME);
                int portfolioWalletIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_PORTFOLIO_WALLET);
                int assetIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_ASSET_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_POSITION);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_STATUS);
                int countIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_COUNT);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_NAME);
                int commentIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_COMMENT);
                int dateStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_DATE_START);
                int monthStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_START);
                int yearStartIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_START);
                int dateEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_DATE_END);
                int monthEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_END);
                int yearEndIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_END);
                int walletIndex = cursor.getColumnIndex(DBHelper.key_MONEY_LOT_ASSET_WALLET_ID);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(cursor.getInt(yearStartIndex), cursor.getInt(monthStartIndex), cursor.getInt(dateStartIndex));
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(cursor.getInt(yearEndIndex), cursor.getInt(monthEndIndex), cursor.getInt(dateEndIndex));
                MoneyAsset moneyAsset = MoneyAssetDataSource.LoadOneAsset(context, cursor.getLong(assetIndex));

                ArrayList<MoneyLotAssetOperation> list = new ArrayList<>();
                MoneyTransactionSettings moneyTransactionSettings = new MoneyTransactionSettings(
                        EnumTransactionType.PNL,
                        EnumTypeCategory.All,
                        null,
                        cursor.getLong(walletIndex),
                        null,
                        null);
                ArrayList<MoneyTransaction> pnlList = MoneyPNLDataSource.LoadDataInd(
                        context,
//                        cursor.getLong(walletIndex),
                        calendar1, calendar2, moneyTransactionSettings);
                for (MoneyTransaction item : pnlList)
                    if (!item.get_isPlan())
                    {
                        EnumLotAssetOperation lotAssetOperation = EnumLotAssetOperation.PNLPlus;
                        if (item.get_typeCategory().equals(EnumTypeCategory.Cost))
                            lotAssetOperation = EnumLotAssetOperation.PNLMinus;
                        else if (item.get_typeCategory().equals(EnumTypeCategory.Wallet) &&
                                item.getCostCategoryId() == cursor.getLong(walletIndex))
                            lotAssetOperation = EnumLotAssetOperation.PNLMinus;

                        list.add(new MoneyLotAssetOperation(
                                -1000000000 + item.get_id(),
                                null,
                                false,
                                lotAssetOperation,
                                0L,
                                0L,
                                1,
                                1,
                                item.get_totalSumLong(),
                                item.get_totalSumLong(),
                                0L,
                                0L,
                                0L,
                                item.getCalendar()));
                    }

                MoneyLotAssetOperationSettings moneyLotAssetOperationSettings =
                        new MoneyLotAssetOperationSettings(cursor.getLong(idIndex));
                ArrayList<MoneyLotAssetOperation> list2 =
                        MoneyLotAssetOperationDataSource.LoadData(context,
                                moneyLotAssetOperationSettings, false);
                for (MoneyLotAssetOperation item2: list2)
                {
                    int pos = 0;
                    for (int i = list.size() - 1; i >= 0; i--)
                        if (item2.get_calendar().getTime().getTime() <
                                list.get(i).get_calendar().getTime().getTime())
                        {
                            pos = i + 1;
                            break;
                        }
                    list.add(pos, item2);
                }

                ArrayList<MoneyLotAssetOperation> list3 =
                        MoneyLotAssetOperationDataSource.LoadData4Wallet(context, cursor.getLong(walletIndex));
                for (MoneyLotAssetOperation item3: list3)
                {
                    int pos = 0;
                    for (int i = list.size() - 1; i >= 0; i--)
                        if (item3.get_calendar().getTime().getTime() <
                                list.get(i).get_calendar().getTime().getTime())
                        {
                            pos = i + 1;
                            break;
                        }
                    list.add(pos, item3);
                }

                data = new MoneyLotAsset(
                        cursor.getLong(idIndex),
                        cursor.getLong(groupPortfolioIndex),
                        cursor.getString(groupPortfolioNameIndex),
                        cursor.getLong(portfolioIndex),
                        cursor.getString(portfolioNameIndex),
                        cursor.getLong(portfolioWalletIndex),
                        moneyAsset,
                        cursor.getString(nameIndex),
                        cursor.getString(commentIndex),
                        cursor.getInt(positionIndex),
                        cursor.getInt(countIndex),
                        calendar1, calendar2,
                        EnumStatus.valueOf(cursor.getString(statusIndex)),
                        list,
                        null);

                for (MoneyLotAssetOperation item : list)
                    item.set_moneyLotAsset(data);

                cursor.close();
            }
            //endregion

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
            return dbHelper.GetCountMoneyLotAsset();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyLotAsset();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
