package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumBoard;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyBoard;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyBoardDataSource
{
    private static ArrayList<EnumBoard> getAllValues()
    {
        ArrayList<EnumBoard> list = new ArrayList<>();

        list.add(EnumBoard.TransactionBoard);
//        list.add(EnumBoard.WalletBoard);
        list.add(EnumBoard.GoalsAllBoard);
        list.add(EnumBoard.GoalsForYearBoard);
        list.add(EnumBoard.WalletOperBoard);

        return list;
    }

    public static ArrayList<MoneyBoard> LoadData(Context context,
                                                            boolean withValues)
    {
        GlobalClass gVar = (GlobalClass) context.getApplicationContext();
        ArrayList<MoneyBoard> data = new ArrayList<>();

        //region Выборка из БД
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryMoneyBoard(gVar);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_BOARD_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_BOARD_NAME);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_BOARD_POSITION);
                int visibleIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_BOARD_VISIBLE);

                do
                {
                    data.add(new MoneyBoard(
                            cursor.getLong(idIndex),
                            EnumBoard.valueOf(cursor.getString(nameIndex)),
                            cursor.getLong(positionIndex),
                            cursor.getLong(visibleIndex) == 1,
                            new ArrayList<>()));
                }
                while (cursor.moveToNext());
            }

            //Удаление старых элементов
            ArrayList<EnumBoard> enumBoardArrayList = getAllValues();
            for (MoneyBoard moneyBoard: data)
                if (!enumBoardArrayList.contains(moneyBoard.get_enumBoard()))
                {
                    moneyBoard.DeleteFromDatabase(dbHelper, context);
                    data.remove(moneyBoard);
                }

            long id = -1L;
            for (EnumBoard enumBoard: enumBoardArrayList)
            {
                boolean contains = false;
                for (MoneyBoard moneyBoard: data)
                    if (moneyBoard.get_enumBoard().equals(enumBoard))
                    {
                        contains = true;
                        break;
                    }

                if (!contains)
                    data.add(new MoneyBoard(id--, enumBoard, 1000L, true, new ArrayList<>()));

            }

            int i = 1;
            for (MoneyBoard moneyBoard: data)
                moneyBoard.set_position((long) i++);
        }
        catch (Exception e)
        {
            return null;
        }

        if (!withValues)
            return data;
        //endregion

        Calendar calendar0 = Calendar.getInstance();
        calendar0.set(Calendar.YEAR, 2000);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DATE, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MONTH, 1);
        calendar2.set(Calendar.DATE, 1);
        calendar2.add(Calendar.DATE, -1);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.YEAR, 3000);
        Calendar calendarYearStart = Calendar.getInstance();
        calendarYearStart.set(Calendar.DATE, 1);
        calendarYearStart.set(Calendar.MONTH, 1);
        Calendar calendarYearEnd = Calendar.getInstance();
        calendarYearEnd.add(Calendar.YEAR, 1);
        calendarYearEnd.set(Calendar.DATE, 1);
        calendarYearEnd.set(Calendar.MONTH, 1);
        calendarYearEnd.add(Calendar.DATE, -1);

        ArrayList<MoneyBoard.MoneyBoardData> values;
        //region 1=BoardTransaction
        for (MoneyBoard moneyBoard: data)
            if ((moneyBoard.get_enumBoard().equals(EnumBoard.TransactionBoard) &&
                moneyBoard.is_visible()) || gVar.get_hasUnpaidSubscription())
            {
                values = new ArrayList<>();
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyPNLDataSource.LoadSumInd(context, EnumTypeCategory.Increase,
                                calendar1, calendar2), true));
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyPNLDataSource.LoadSumInd(context, EnumTypeCategory.Cost,
                                calendar1, calendar2), true));
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Wallet, true, false,
                        true, EnumCategoryType.All, null);
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyCategoryDataSource.LoadSumDataParent(context, moneyCategorySettings),
                        true));
                moneyBoard.set_values(values);

                break;
            }
        //endregion

        //region 2=BoardWallet
        for (MoneyBoard moneyBoard: data)
            if (moneyBoard.get_enumBoard().equals(EnumBoard.WalletBoard) &&
                    moneyBoard.is_visible() &&
                    !gVar.get_hasUnpaidSubscription())
            {
                values = new ArrayList<>();

                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Wallet, true, false,
                        true, EnumCategoryType.All, null);
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyCategoryDataSource.LoadSumDataParent(context, moneyCategorySettings),
                        true));
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyCategoryDataSource.LoadSumDataParentSavings(context, moneyCategorySettings, null),
                        true));
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyPNLDataSource.LoadSumIndSavings(context, calendar1, calendar2), true));
                moneyBoard.set_values(values);

                break;
            }
        //endregion

        //region 3=BoardGoalsAll
        for (MoneyBoard moneyBoard: data)
            if (moneyBoard.get_enumBoard().equals(EnumBoard.GoalsAllBoard) &&
                    moneyBoard.is_visible() &&
                    !gVar.get_hasUnpaidSubscription())
            {
                values = new ArrayList<>();

                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyTargetDataSource.LoadActiveTargetsPrice(context, false, null, null),
                        true));
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyTargetDataSource.LoadActiveTargetsNeedSavings(context, false,null, null),
                        true));
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Wallet, true, false,
                        true, EnumCategoryType.All,null);
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyCategoryDataSource.LoadSumDataParentSavings(context, moneyCategorySettings, null),
                        true));
                moneyBoard.set_values(values);

                break;
            }
        //endregion

        //region 4=BoardGoalsForYear
        for (MoneyBoard moneyBoard: data)
            if (moneyBoard.get_enumBoard().equals(EnumBoard.GoalsForYearBoard) &&
                    moneyBoard.is_visible() &&
                    !gVar.get_hasUnpaidSubscription())
            {
                values = new ArrayList<>();

                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyTargetDataSource.LoadActiveTargetsPrice(context, true, calendarYearStart, calendarYearEnd),
                        true));
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyTargetDataSource.LoadActiveTargetsNeedSavings(context, true, calendarYearStart, calendarYearEnd),
                        true));
                ArrayList<MoneyTarget> activeTargets = MoneyTargetDataSource.LoadActiveTargets(context, calendarYearStart, calendarYearEnd);
                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Wallet, true, false,
                        true, EnumCategoryType.All,null);
                values.add(new MoneyBoard.MoneyBoardData(
                        MoneyCategoryDataSource.LoadSumDataParentSavings(context, moneyCategorySettings, activeTargets),
                        true));
                moneyBoard.set_values(values);

                break;
            }
        //endregion

        //region 5=WalletOperBoard
        for (MoneyBoard moneyBoard: data)
            if (moneyBoard.get_enumBoard().equals(EnumBoard.WalletOperBoard) &&
                    moneyBoard.is_visible() &&
                    !gVar.get_hasUnpaidSubscription())
            {
                values = new ArrayList<>();

                MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Wallet, true, false,
                        true, EnumCategoryType.All,null);
                long sum1 = MoneyCategoryDataSource.LoadSumDataParent(context, moneyCategorySettings);
                moneyCategorySettings = new MoneyCategorySettings(
                        EnumTypeCategory.Cost, true, false,
                        true, EnumCategoryType.All,null);
                long sum2 = MoneyCategoryDataSource.LoadSumDataChildren(context, moneyCategorySettings);
                values.add(new MoneyBoard.MoneyBoardData(sum1, true));
                values.add(new MoneyBoard.MoneyBoardData(sum2, true));
                values.add(new MoneyBoard.MoneyBoardData(sum1 - sum2, true));
                moneyBoard.set_values(values);

                break;
            }
        //endregion

        return data;
    }
}
