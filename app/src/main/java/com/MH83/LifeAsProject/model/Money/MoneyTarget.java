package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTargetDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.model.Custom.PlanValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoneyTarget extends CustomMoneyObject
{
    //region Class params
    private Long _id;
    private final Long _dreamId;
    private String _month;
    private String _year;
    private final Long _parentId;
    private final String _name;
    private final Integer _price;
    private final Integer _accumulation;
    private final Integer _credit;
    private String _imagePath;
    private final String _imageUrl;
    private final EnumStatus _status;
    private final String _textStatus;
    private final List<PlanValue> _planValues;
    private List<Long> _deletedPlanValues;
    private final int _categoryId;
    private MoneyCategory _moneyCategory;
    private final MoneyAccumulation _moneyAccumulation;
    private MoneyAccumulation _oldMoneyAccumulation;
    private final Integer _allTaskCount;
    private final Integer _weekTaskCount;
    private final MoneyCredit _moneyCredit;
    //endregion

    //region GetParams
    public Long get_id() { return _id; }
    @Override
    public String getSValue() { return _name; }
    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_parentId() { return _parentId; }
    public Long get_dreamId() { return _dreamId; }
    public String get_name() { return _name; }
    public Integer get_price() { return _price; }
    public Integer get_accumulation() { return _accumulation; }
    public Integer get_credit() { return _credit; }
    public String get_month() { return _month; }
    public void set_month(String month) { _month = month; }
    public Integer getImonth() { return Integer.parseInt(_month); }
    public String get_year() { return _year; }
    public void set_year(String year) { _year = year; }
    public Integer getIyear() { return Integer.parseInt(_year); }
    public String get_imagePath() { return _imagePath; }
    public void set_imagePath(String imagePath) { _imagePath = imagePath; }
    public String get_imageUrl() { return _imageUrl; }
    public EnumStatus get_status() { return _status; }
    public int get_categoryId() { return _categoryId; }
    Calendar _calendar;
    public Calendar get_calendar() { return _calendar;}

    public int get_statusColor()
    {
        switch (_status)
        {
            case Blank:
                return R.color.white;
            case Started:
                return R.color.orange;
            case Finished:
                return R.color.green;
            default:
                return R.color.lightGrey;
        }
    }
//    public String get_textStatus() { return _textStatus; }

    public List<PlanValue> get_planValues() { return _planValues; }

    public List<Long> get_deletedPlanValues() { return _deletedPlanValues; }
    public void add_deletedPlanValues(Long id) { _deletedPlanValues.add(id); }
    public void clear_deletedPlanValues() { _deletedPlanValues.clear(); }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyTargetDataSource.GetCount(context) >= moneyTargetFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyTarget), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public boolean canDelete(Context context)
    {
        return (_planValues != null && _planValues.size() == 0);
    }

    public MoneyAccumulation get_moneyAccumulation() { return _moneyAccumulation; }
    public MoneyAccumulation get_oldMoneyAccumulation() { return _oldMoneyAccumulation; }

    public MoneyCategory get_moneyCategory() { return _moneyCategory; }

    public Integer get_allTaskCount() { return _allTaskCount; }

    public Integer get_weekTaskCount() { return _weekTaskCount; }

    public MoneyCredit get_moneyCredit() { return _moneyCredit; }

    public Long get_accumFactSum()
    {
        long sum = 0L;

        if (_moneyAccumulation != null)
        {
            sum = _moneyAccumulation.get_startSum();

            for (MoneyPNL item : _moneyAccumulation.get_pnls())
                if (!item.get_isPlan())
                    sum += item.get_sumLong();
        }

        return sum;
    }

    public Long get_creditPlanSum()
    {
        if (_moneyCredit != null)
            if (_moneyCredit.get_status().equals(EnumCreditStatus.Active))
                return _moneyCredit.get_startSummLong();

        return 0L;
    }

    public Long get_creditFactSum()
    {
        long sum = 0L;

        if (_moneyCredit != null)
        {
            for (MoneyPNL item : _moneyCredit.get_pnls())
                if (!item.get_isPlan())
                    sum += item.get_sumLong();
        }

        return sum;
    }
    //endregion

    //region SetParams
    public void set_moneyCategory(MoneyCategory item) { _moneyCategory = item; }
    //endregion

    //region ReNew
    private void ReNew(Context context, DBHelper dbHelper) throws Exception
    {
        clear_deletedPlanValues();

        if (_moneyAccumulation != null)
        {
            _moneyAccumulation.ReNew();
            for (MoneyPNL item : _moneyAccumulation.get_pnls())
                item.reNew();
        }

        Long walletId = dbHelper.OneMoneyCategoryId4Target(_id, EnumTypeCategory.Wallet);
        if (walletId != 0)
            set_moneyCategory(MoneyCategoryDataSource.LoadOne(context, walletId));

        _oldMoneyAccumulation = _moneyAccumulation;
    }
    //endregion

    //region Constructors
    public MoneyTarget(Long id, String month, String year, String name, Integer price,
                       Integer accumulation, Integer credit, String imagePath, String imageUrl,
                       Long dreamId, EnumStatus status, String textStatus, Long parentId,
                       int categoryId,
                       MoneyAccumulation moneyAccumulation,
                       MoneyAccumulation oldMoneyAccumulation,
                       List<PlanValue> planValues, List<Long> deletedPlanValues,
                       Integer allTaskCount, Integer weekTaskCount,
                       MoneyCredit moneyCredit)
    {
        _id = id;
        _month = month;
        _year = year;
        _calendar = Calendar.getInstance();
        _calendar.set(Calendar.DATE, 1);
        _calendar.set(Calendar.MONTH, Integer.parseInt(month));
        _calendar.set(Calendar.YEAR, Integer.parseInt(year));

        _name = name;
        _price = price;
        _accumulation = accumulation;
        _credit = credit;
        _imagePath = imagePath;
        _imageUrl = imageUrl;
        _dreamId = dreamId;
        _status = status;
        _textStatus = textStatus;
        _parentId = parentId;
        _categoryId = categoryId;
        _moneyAccumulation = moneyAccumulation;
        _oldMoneyAccumulation = oldMoneyAccumulation;
        _planValues = planValues;
        _deletedPlanValues = deletedPlanValues;
        if (deletedPlanValues == null)
            _deletedPlanValues = new ArrayList<>();

        _allTaskCount = allTaskCount;
        _weekTaskCount = weekTaskCount;
        _moneyCredit = moneyCredit;
    }

    public MoneyTarget CloneWithId()
    {
        return new MoneyTarget(_id, _month, _year, _name, _price,
                _accumulation, _credit, _imagePath, _imageUrl,
                _dreamId, _status, _textStatus, _parentId, _categoryId,
                _moneyAccumulation, _oldMoneyAccumulation,
                _planValues, _deletedPlanValues, _allTaskCount, _weekTaskCount,
                _moneyCredit);
    }
    //endregion

    //region CustomMoneyObject
    public void AddToDatabaseWOTrack(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyTarget(this, context);
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyTarget(this, context);

        ReNew(context, dbHelper);

        TrackCount(dbHelper);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        boolean b = dbHelper.UpdateMoneyTarget(this);

        if (b)
            ReNew(context, dbHelper);

        return b;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyTarget(this);
    }

    private void TrackCount(DBHelper dbHelper)
    {
        CustomTrack.TrackCountMoneyTarget(MoneyTargetDataSource.GetCount(dbHelper));
    }
    //endregion
}
