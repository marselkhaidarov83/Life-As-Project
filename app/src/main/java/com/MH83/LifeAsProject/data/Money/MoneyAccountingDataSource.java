package com.MH83.LifeAsProject.data.Money;

import android.content.Context;

import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumDataOrSetting;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MoneyAccountingDataSource
{
    public static ArrayList<MoneyAccountingSum> LoadDataIncoming(Context context,
                                                                 Calendar _dateStart, Calendar _dateEnd)
    {
        return LoadData(context, _dateStart, _dateEnd, EnumTypeCategory.Increase, true, true);
    }

    public static ArrayList<MoneyAccountingSum> LoadDataWallet(Context context,
                                                               Calendar _dateStart, Calendar _dateEnd)
    {
        return LoadData(context, _dateStart, _dateEnd, EnumTypeCategory.Wallet, true, true);
    }

    public static ArrayList<MoneyAccountingSum> LoadDataCost(Context context,
                                                             Calendar _dateStart, Calendar _dateEnd)
    {
        return LoadData(context, _dateStart, _dateEnd, EnumTypeCategory.Cost, true, true);
    }

    public static MoneyAccountingSum LoadDataWithoutPNL(Context context, Long categoryId)
    {
        MoneyCategory moneyCategory =
                MoneyCategoryDataSource.LoadOne(context, categoryId);

        if (moneyCategory == null)
            return null;

        return new MoneyAccountingSum(moneyCategory.get_id(),
                    moneyCategory.get_name(), moneyCategory.getTypeCategory(),
                    moneyCategory.get_sumLong(),
                    moneyCategory.get_sumLong(),
                    moneyCategory.get_icon(),
                    EnumDataOrSetting.Data);
    }

    public static ArrayList<MoneyAccountingSum> LoadData(Context context,
                                                         Calendar _dateStart, Calendar _dateEnd,
                                                         EnumTypeCategory typeCategory,
                                                         boolean needPNL,
                                                         boolean needSettingElem)
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                typeCategory, true, false,
                true, EnumCategoryType.All,null);
        ArrayList<MoneyCategory> moneyCategories =
                (ArrayList<MoneyCategory>) MoneyCategoryDataSource.LoadDataChildren(context,
                        0L, moneyCategorySettings);

        ArrayList<MoneyAccountingSum> data = new ArrayList<>();
        if (moneyCategories != null)
            for (MoneyCategory moneyCategory: moneyCategories)
            {
                Long budgetLong = 0L;
                MoneyCategorySettings moneyCategorySettings1 = new MoneyCategorySettings(
                        EnumTypeCategory.Cost, true, false,
                        true, EnumCategoryType.All,null);
                if (typeCategory.equals(EnumTypeCategory.Cost) && needPNL)
                    for (MoneyCategory moneyCategory1:
                            MoneyCategoryDataSource.LoadDataChildren(context,
                                    moneyCategory.get_id(), moneyCategorySettings1))
                        budgetLong += moneyCategory1.get_sumLong();

                MoneyAccountingSum moneyAccounting = new MoneyAccountingSum(moneyCategory.get_id(),
                        moneyCategory.get_name(),
                        moneyCategory.getTypeCategory(),
                        moneyCategory.get_sumLong(),
                        budgetLong,
                        moneyCategory.get_icon(),
                        EnumDataOrSetting.Data);
                data.add(moneyAccounting);
            }

        if (needSettingElem)
            data.add(new MoneyAccountingSum(-9999L,
                    "", typeCategory,
                    0L, 0L,
                    0L, EnumDataOrSetting.Setting));

        if ((typeCategory.equals(EnumTypeCategory.Increase) ||
                typeCategory.equals(EnumTypeCategory.Cost)) && needPNL)
        {
            for (MoneyAccountingSum mas: data)
                mas.set_sumLong(0L);

            ArrayList<MoneyTransaction> moneyPNLS =
                    MoneyPNLDataSource.LoadDataInd(context, typeCategory, _dateStart, _dateEnd);
            if (moneyPNLS != null)
                for (MoneyTransaction moneyTransaction: moneyPNLS)
                    for (MoneyAccountingSum mas: data)
                        if (Objects.equals(mas.getCategoryId(), moneyTransaction.getIncreaseCategoryId()) ||
                                Objects.equals(mas.getCategoryId(), moneyTransaction.getCostCategoryId()))
                        {
                            if (!moneyTransaction.get_isPlan())
                                mas.set_sumLong(mas.get_sumLong() + moneyTransaction.get_totalSumLong());
                            break;
                        }
        }

        return data;
    }
}
