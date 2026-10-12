package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAssetDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyLotAssetDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyLotAssetOperationDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Calendar;
import java.util.Objects;

public class MoneyLotAssetOperation extends CustomMoneyObject implements IIsParent
{
    //region Parameters
    protected Long _id;
    private final boolean _isPlan;
    private MoneyLotAsset _moneyLotAsset;
    private final Integer _count;
    private Integer _countOld;
    private final Long _price;
    private final Long _accumulatedPrice;
    private final Long _commission;
    private Long _priceOld;
    private Long _commissionOld;
    private EnumLotAssetOperation _lotAssetOperation;
    private final Calendar _calendar;
    private final Long _categoryId;
    private final Long _subCategoryId;
    //endregion

    //region Get/Set data
    @Override
    public String getSValue() { return ""; }
    public MoneyLotAsset get_moneyLotAsset() { return _moneyLotAsset; }

    public void set_moneyLotAsset(MoneyLotAsset moneyLotAsset) { _moneyLotAsset = moneyLotAsset; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public boolean get_isPlan() { return _isPlan; }
    public EnumLotAssetOperation get_lotAssetOperation() { return _lotAssetOperation; }

    public void set_lotAssetOperation(EnumLotAssetOperation lotAssetOperation)
    {
        _lotAssetOperation = lotAssetOperation;
    }

    public Calendar get_calendar() { return _calendar; }
    public Integer get_count() { return _count; }
    public Integer get_countOld() { return _countOld; }
    public Long get_price() { return _price; }
    public Long get_commission() { return _commission; }
    public Long get_priceOld() { return _priceOld; }
    public Long get_commissionOld() { return _commissionOld; }
    public Long get_accumulatedPrice() { return _accumulatedPrice; }

    public Long get_sum()
    {
        if (Objects.requireNonNull(_lotAssetOperation) == EnumLotAssetOperation.Sell)
            return _count * _price - _commission + _count * _accumulatedPrice;

        return _count * _price + _commission + _count * _accumulatedPrice;
    }
    public Long get_sumOld()
    {
        if (Objects.requireNonNull(_lotAssetOperation) == EnumLotAssetOperation.Sell)
            return _countOld * _priceOld - _commissionOld + _countOld * _accumulatedPrice;

        return _countOld * _priceOld + _commissionOld + _countOld * _accumulatedPrice;
    }
    public Integer get_year() { return _calendar.get(Calendar.YEAR); }
    public Integer get_month() { return _calendar.get(Calendar.MONTH); }
    public Integer get_day() { return _calendar.get(Calendar.DATE); }
    public Calendar get_date() { return _calendar; }

    public Long get_categoryId() { return _categoryId; }

    public Long get_subCategoryId() { return _subCategoryId;}

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyLotAssetOperationDataSource.GetCount(context) >= moneyLotAssetOperationFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyLotAssetOperation), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public void reNew()
    {
        _countOld = _count;
        _priceOld = _price;
        _commissionOld = _commission;
    }
    //endregion

    //region IIsParent
    @Override
    public boolean isParent()
    {
        return false;
    }
    //endregion

    //region Constructors
    public MoneyLotAssetOperation(Long id,
                                  MoneyLotAsset moneyLotAsset,
                                  boolean isPlan,
                                  EnumLotAssetOperation lotAssetOperation,
                                  long categoryId,
                                  long subCategoryId,
                                  Integer count,
                                  Integer countOld,
                                  Long price,
                                  Long priceOld,
                                  Long commission,
                                  Long commissionOld,
                                  Long accumulatedPrice,
                                  Integer day,
                                  Integer month,
                                  Integer year)
    {
        _id = id;
        _moneyLotAsset = moneyLotAsset;
        _isPlan = isPlan;
        _lotAssetOperation = lotAssetOperation;
        _categoryId = categoryId;
        _subCategoryId = subCategoryId;
        _count = count;
        _countOld = countOld;
        _price = price;
        _priceOld = priceOld;
        _commission = commission;
        _commissionOld = commissionOld;
        _accumulatedPrice = accumulatedPrice;
        _calendar = Calendar.getInstance();
        _calendar.set(year, month, day);
    }

    public MoneyLotAssetOperation(Long id,
                                  MoneyLotAsset moneyLotAsset,
                                  boolean isPlan,
                                  EnumLotAssetOperation lotAssetOperation,
                                  long categoryId,
                                  long subCategoryId,
                                  Integer count,
                                  Integer countOld,
                                  Long price,
                                  Long priceOld,
                                  Long commission,
                                  Long commissionOld,
                                  Long accumulatedPrice,
                                  Calendar calendar)
    {
        _id = id;
        _moneyLotAsset = moneyLotAsset;
        _isPlan = isPlan;
        _lotAssetOperation = lotAssetOperation;
        _categoryId = categoryId;
        _subCategoryId = subCategoryId;
        _count = count;
        _countOld = countOld;
        _price = price;
        _priceOld = priceOld;
        _commission = commission;
        _commissionOld = commissionOld;
        _accumulatedPrice = accumulatedPrice;
        _calendar = (Calendar) calendar.clone();
    }

    public MoneyLotAssetOperation Clone()
    {
        return new MoneyLotAssetOperation(_id, _moneyLotAsset, _isPlan,
                _lotAssetOperation, _categoryId, _subCategoryId,
                _count, _countOld, _price, _priceOld,
                _commission, _commissionOld,
                _accumulatedPrice, (Calendar) _calendar.clone());
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception { }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return true;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return true;
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyLotAsset(MoneyLotAssetDataSource.GetCount(dbHelper));
    }
    //endregion
}
