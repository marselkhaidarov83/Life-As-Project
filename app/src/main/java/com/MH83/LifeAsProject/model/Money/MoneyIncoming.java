package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyIncomingDataSource;
import com.MH83.LifeAsProject.interfac.ISValue;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyIncoming extends CustomMoneyObject implements ISValue
{
    private Long _id;
    private final Long _categoryId;
    private final long _increaseId;
    private Long _sumLong;
    private int _percent;
    private int _increasePercent;
    private boolean _increasePercentExist;

    private final float _percentOfSum;
    private final EnumTypeIncoming _typeIncoming;
    private final Long _externalId;

    public Long get_id() { return _id; }
    public void set_id(Long id) { _id = id; }
    public Long get_categoryId() { return _categoryId; }
    public long get_increaseId() { return _increaseId; }
    public Long get_sumLong() { return _sumLong; }
    public String get_StringSum(boolean withIncreasePercent, boolean withPercent,
                                EnumTypeData typeData, boolean shortS)
    {
        String sSum= "";
        if (!shortS || (withIncreasePercent == false && withPercent == false))
        {
            if (typeData == EnumTypeData.Plan)
                sSum = CustomStaticClass.getIntFormatSum((int) (_sumLong / 100), false);
            else
                sSum = CustomStaticClass.getFormatSum(_sumLong / 100d, false);
        }

        if (withIncreasePercent)
        {
            if (withPercent)
                return sSum +
                        (_increasePercentExist ? " (" + _increasePercent + "%/" + _percent + "%)": " (" + _percent + "%)");
            else
                return sSum +
                        (_increasePercentExist ? " " +
                                (!shortS ? "(" : "") +
                                _increasePercent + "%" +
                                (!shortS ? ")" : "")
                                : "");
        }
        else
            return sSum +
                (withPercent ? " " +
                        (!shortS ? "(" : "") +
                        _percent + "%" +
                        (!shortS ? ")" : "")
                        : "");
    }
    public void set_sumLong(Long sumLong) { _sumLong = sumLong; }
    public void set_increasePercentExist(boolean percentExist) { _increasePercentExist = percentExist; }
    public void set_increasePercent(int percent) { _increasePercent = percent; }
    public void set_percent(int percent) { _percent = percent; }
    public boolean canDelete(Context context)
    {
        return true;
    }

    public float get_percentOfSum() { return _percentOfSum; }
    public EnumTypeIncoming get_typeIncoming() { return _typeIncoming; }
    public Long get_externalId() { return _externalId; }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyIncomingDataSource.GetCount(context) >= moneyIncomingAndCostFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyIncomingAndCost), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public MoneyIncoming(Long id,
                         Long categoryId,
                         Long increaseId,
                         long sumLong,
                         Float percentOfSum,
                         EnumTypeIncoming typeIncoming,
                         Long externalId)
    {
        _id = id;
        _categoryId = categoryId;
        _increaseId = increaseId;
        _sumLong = sumLong;

        _percentOfSum = percentOfSum;
        _typeIncoming = typeIncoming;
        _externalId = externalId;
    }

//    public MoneyIncoming(int id, int categoryId, int increaseId, long sumLong,
//                         float percentOfSum, EnumTypeIncoming typeIncoming, Integer externalId)
//    {
//        _id = id;
//        _categoryId = categoryId;
//        _increaseId = increaseId;
////        _sum = sum;
//        _sumLong = sumLong;
//
//        _percentOfSum = percentOfSum;
//        _typeIncoming = typeIncoming;
//        _externalId = externalId;
//    }

    @Override
    public String getSValue()
    {
        return String.valueOf(_sumLong / 100d);
    }

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyIncoming(this);
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyIncoming(this);
        TrackCount(dbHelper, context);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyIncoming(this);
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyIncoming(_id);
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyIncAndCost(MoneyIncomingDataSource.GetCount(dbHelper));
    }
    //endregion
}
