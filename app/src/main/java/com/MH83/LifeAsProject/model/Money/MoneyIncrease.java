package com.MH83.LifeAsProject.model.Money;

import static com.MH83.LifeAsProject.interfac.enums.EnumUpOrDown.Up;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyIncreaseDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumUpOrDown;
import com.MH83.LifeAsProject.interfac.ISValue;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Calendar;

public class MoneyIncrease  extends CustomMoneyObject implements ISValue
{
    private Long _id;
    private final String _day;
    private Integer _week;
    private final String _month;
    private final String _year;
    private Integer _value;
    private Float _percent;
    private EnumUpOrDown _upOrDown;
    private final boolean _existIncoming;
    private boolean _archive;

    public Long get_id() { return _id; }
    public void set_id(Long id) { _id = id; }
    public String get_day() { return _day; }
    public Integer get_dayI() { return Integer.parseInt(_day); }
    public String get_month() { return _month; }
    public Integer getI_month() { return Integer.parseInt(_month); }
    public String get_year() { return _year; }
    public Integer getI_year() { return Integer.parseInt(_year); }
    public String get_month_year() { return get_month() + "." + get_year(); }
    public Integer get_week() { return _week; }
    public Integer get_value() { return _value; }
    public void setPercent(Float percent) { _percent = percent; }
    public Float getPercent() { return _percent; }
    public EnumUpOrDown get_upOrDown() { return _upOrDown; }
    public void set_value(Double value) { _value = value.intValue(); }
    @Override
    public String getSValue()
    {
        return get_month_year() + " (" + CustomStaticClass.getIntRubleSum(get_value()) + ")";
    }
    public String getSValue(Character delimiter, EnumTypeData typeData,
                            EnumPeriodData periodData)
    {
        switch (periodData)
        {
            case Week:
                Calendar calendar = Calendar.getInstance();
                calendar.set(getI_year(), getI_month() - 1, get_dayI());
                String s = "";
                if (calendar.get(Calendar.DATE) < 10)
                    s += "0" + calendar.get(Calendar.DATE) + ".";
                else
                    s += calendar.get(Calendar.DATE) + ".";
                if (calendar.get(Calendar.MONTH) + 1 < 10)
                    s += "0" + (calendar.get(Calendar.MONTH) + 1) +
                            "." + calendar.get(Calendar.YEAR) + " -" + delimiter;
                else
                    s += (calendar.get(Calendar.MONTH) + 1)
                            + "." + calendar.get(Calendar.YEAR) + " -" + delimiter;
                calendar.add(Calendar.DATE, 6);
                if (calendar.get(Calendar.DATE) < 10)
                    s += "0" + calendar.get(Calendar.DATE) + ".";
                else
                    s += calendar.get(Calendar.DATE) + ".";
                if (calendar.get(Calendar.MONTH) + 1 < 10)
                    s += "0" + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
                else
                    s += (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
                return s;
            case Year:
                return get_year();
            case Quarter:
                int quarter = Math.floorDiv(getI_month() - 1, 3) + 1;
                return quarter + " кв " + get_year();
            case Month:
            default:
            return get_value() == null || typeData == EnumTypeData.Fact ? get_month_year() :
                    get_month_year() + delimiter +
                            "(" + CustomStaticClass.getIntRubleSum(get_value()) + ")";
        }
    }
    public void set_upOrDown(Integer prevValue)
    {
        if (_value <= prevValue)
            _upOrDown = EnumUpOrDown.Down;
        else
            _upOrDown = Up;
    }
    public boolean getExistIncoming() { return _existIncoming; }
    public boolean canDelete(Context context)
    {
//        return !_existIncoming;
        return true;
    }

    public boolean get_archive() { return _archive; }
    public int get_archiveI() { return _archive ? 1 : 0; }
    public void inverse_archive() { _archive = !_archive; }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyIncreaseDataSource.GetCount(context) >= moneyIncreaseFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyIncrease), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public MoneyIncrease(long id, String day, String month, String year,
                         Integer value, Integer prevValue, Float percent,
                         boolean archive,
                         boolean existIncoming)
    {
        _id = id;
        _day = day;
        _month = month;
        _year = year;
        _value = value;
        if (_value != null && _value <= prevValue)
            _upOrDown = EnumUpOrDown.Down;
        else
            _upOrDown = Up;
        _percent = percent;
        _archive = archive;
        _existIncoming = existIncoming;
    }

    public MoneyIncrease(long id, String day, String month, String year, Integer week,
                         Integer value, Integer prevValue, Float percent,
                         boolean archive,
                         boolean existIncoming)
    {
        _id = id;
        _day = day;
        _month = month;
        _year = year;
        _week = week;
        _value = value;
        if (_value != null && _value <= prevValue)
            _upOrDown = EnumUpOrDown.Down;
        else
            _upOrDown = Up;
        _percent = percent;
        _archive = archive;
        _existIncoming = existIncoming;
    }

    public MoneyIncrease Clone()
    {
        return new MoneyIncrease(-1, _day, _month, _year, _value, _value, 0f, _archive, false);
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyIncrease(this);
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyIncrease(this);
        TrackCount(dbHelper, context);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyIncrease(this);
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyIncrease(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyIncrease(MoneyIncreaseDataSource.GetCount(dbHelper));
    }
}
