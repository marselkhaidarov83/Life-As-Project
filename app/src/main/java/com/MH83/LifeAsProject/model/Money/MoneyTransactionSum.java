package com.MH83.LifeAsProject.model.Money;

import static com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory.Cost;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

public class MoneyTransactionSum
{
//    private Double _sum;
    private Long _sumLong;
//    public Double get_sum() { return _sum; }
    public Long get_sumLong() { return _sumLong; }

    public void change_sum(MoneyTransaction moneyTransaction, MoneyAccountingSum mas,
                           EnumTypeCategory typeCategory)
    {
        int k = 1;
        if (typeCategory == Cost)
            k = -1;

        switch (moneyTransaction.get_typeCategory()) {
            case Increase:
                if (!moneyTransaction.get_isPlan())
//                    _sum += k * moneyTransaction.get_totalSum();
                    _sumLong += k * moneyTransaction.get_totalSumLong();
                break;
            case Cost:
                if (!moneyTransaction.get_isPlan())
//                    _sum -= k * moneyTransaction.get_totalSum();
                    _sumLong -= k * moneyTransaction.get_totalSumLong();
                break;
            default:
                if (mas != null)
                {
                    if (moneyTransaction.getIncreaseCategoryId() == mas.getCategoryId() &&
                            !moneyTransaction.get_isPlan())
//                        _sum += k * moneyTransaction.get_totalSum();
                        _sumLong += k * moneyTransaction.get_totalSumLong();
                    else if (!moneyTransaction.get_isPlan())
//                        _sum -= k * moneyTransaction.get_totalSum();
                        _sumLong -= k * moneyTransaction.get_totalSumLong();
                }
        }
    }

    public MoneyTransactionSum(MoneyTransaction moneyTransaction, MoneyAccountingSum mas)
    {
        if (moneyTransaction != null)
            switch (moneyTransaction.get_typeCategory())
            {
                case Increase:
//                    _sum = moneyTransaction.get_totalSum();
                    _sumLong = moneyTransaction.get_totalSumLong();
                    break;
                case Cost:
//                    _sum = -moneyTransaction.get_totalSum();
                    _sumLong = -moneyTransaction.get_totalSumLong();
                    break;
                default:
                    if (mas != null)
                    {
                        if (moneyTransaction.getIncreaseCategoryId() == mas.getCategoryId())
//                            _sum = moneyTransaction.get_totalSum();
                            _sumLong = moneyTransaction.get_totalSumLong();
                        else
//                            _sum = -moneyTransaction.get_totalSum();
                            _sumLong = -moneyTransaction.get_totalSumLong();
                    }
                    else
//                        _sum = 0d;
                        _sumLong = 0L;
            }
        else
//            _sum = 0d;
            _sumLong = 0L;
    }
}
