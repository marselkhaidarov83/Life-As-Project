package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyLotAssetDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;

import java.util.Calendar;

public class MoneyLotAssetOperationIndependent extends MoneyLotAssetOperation
{
    //region Constructors
    public MoneyLotAssetOperationIndependent(Long id,
                                             MoneyLotAsset moneyLotAsset,
                                             boolean isPlan,
                                             EnumLotAssetOperation lotAssetOperation,
                                             long categoryId,
                                             long subCategoryId,
                                             Integer count,
                                             Integer countOld,
                                             Long price,
                                             Long priceOld,
                                             Long comission,
                                             Long comissionOld,
                                             Long accumulatedPrice,
                                             Integer day,
                                             Integer month,
                                             Integer year)
    {
        super(id,
                moneyLotAsset,
                isPlan,
                lotAssetOperation,
                categoryId,
                subCategoryId,
                count,
                countOld,
                price,
                priceOld,
                comission,
                comissionOld,
                accumulatedPrice,
                day,
                month,
                year);
    }

    public MoneyLotAssetOperationIndependent(Long id,
                                             MoneyLotAsset moneyLotAsset,
                                             boolean isPlan,
                                             EnumLotAssetOperation lotAssetOperation,
                                             long categoryId,
                                             long _subCategoryId,
                                             Integer count,
                                             Integer countOld,
                                             Long price,
                                             Long priceOld,
                                             Long comission,
                                             Long comissionOld,
                                             Long accumulatedPrice,
                                             Calendar calendar)
    {
        super(id,
                moneyLotAsset,
                isPlan,
                lotAssetOperation,
                categoryId,
                _subCategoryId,
                count,
                countOld,
                price,
                priceOld,
                comission,
                comissionOld,
                accumulatedPrice,
                calendar);
    }

    public MoneyLotAssetOperationIndependent Clone()
    {
        return new MoneyLotAssetOperationIndependent(
                get_id(),
                get_moneyLotAsset(),
                get_isPlan(),
                get_lotAssetOperation(),
                get_categoryId(),
                get_subCategoryId(),
                get_count(),
                get_countOld(),
                get_price(),
                get_priceOld(),
                get_commission(),
                get_commissionOld(),
                get_accumulatedPrice(),
                (Calendar) get_calendar().clone());
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyLotAssetOperation(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyLotAssetOperation(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyLotAsset(MoneyLotAssetDataSource.GetCount(dbHelper));
    }
    //endregion
}
