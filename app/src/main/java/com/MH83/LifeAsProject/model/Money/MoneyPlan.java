package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyIncomingDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPlanDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyPlan extends CustomMoneyObject
{
    private Long _id;
    public Long get_id() { return _id; }

    private MoneyCategory _moneyCategory;
    public MoneyCategory getMoneyCategory() { return _moneyCategory; }
    public void set_moneyCategory(MoneyCategory moneyCategory)
    {
        _moneyCategory = moneyCategory;
    }
    public Long getMoneyCategoryId() { return _moneyCategory.get_id(); }

    private int _month;
    public int get_month() { return _month; }
    public void set_month(int month) { _month = month; }

    private int _year;
    public int get_year() { return _year; }
    public void set_year(int year) { _year = year; }

    private Long _sumLong;
    public Long get_sumLong() { return _sumLong; }
    public void set_sumLong(Long sumLong) { _sumLong = sumLong; }
    public void inc_sumLong(Long sum)
    {
        if (sum == null)
            return;
        if (_sumLong == null)
            _sumLong = sum;
        else
            _sumLong += sum;
    }

    private int _percent;
    public void set_percent(int percent) { _percent = percent; }

    private EnumTypeIncoming _typeIncoming;
    public EnumTypeIncoming get_typeIncoming() { return _typeIncoming; }
    public void set_typeIncoming(EnumTypeIncoming typeIncoming) { _typeIncoming = typeIncoming; }

    private Long _externalId;
    public Long get_externalId() { return _externalId; }
    public void set_externalId(Long externalId) { _externalId = externalId; }

    public boolean canDelete(Context context)
    {
        return false;
    }

    public String get_StringSum(boolean withIncreasePercent, boolean withPercent,
                                boolean shortS)
    {
        String sSum= "";
        if (!shortS || (withIncreasePercent == false && withPercent == false))
            sSum = CustomStaticClass.getIntFormatSum((int) (_sumLong / 100), false);

        if (withIncreasePercent)
        {
            if (withPercent)
                return sSum + " (" + _percent + "%)";
            else
                return sSum;
        }
        else
            return sSum +
                    (withPercent ? " " +
                            (!shortS ? "(" : "") +
                            _percent + "%" +
                            (!shortS ? ")" : "")
                            : "");
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyPlanDataSource.GetCount(context) >= moneyPlanFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyMoneyPlan), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public MoneyPlan(Long id,
                     MoneyCategory moneyCategory,
                     int month,
                     int year,
                     Long sumLong,
                     EnumTypeIncoming typeIncoming,
                     Long externalId)
    {
        _id = id;
        _moneyCategory = moneyCategory;
        _month = month;
        _year = year;
        _sumLong = sumLong;
        _typeIncoming = typeIncoming;
        _externalId = externalId;
    }

    @Override
    public String getSValue() { return _sumLong.toString(); }

    @Override
    public void set_id(Long id) { _id = id; }

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyPlan(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyPlan(this);
        TrackCount(dbHelper);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyPlan(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyPlan(_id);
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper)
    {
        CustomTrack.TrackCountMoneyPlan(MoneyPlanDataSource.GetCount(dbHelper));
    }
    //endregion
}
