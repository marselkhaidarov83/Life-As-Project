package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyCreditDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCredit;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MoneyCredit extends CustomMoneyObject
{
    private Long _id;
    private final Long _target_id;
    private final String _targetName;
    private final String _name;
    private final Long _startSummLong;
    private Long _oldStartSumLong;
    private final Long _sumLong;
    private final double _percent;
    private final Calendar _calendarStart;
    private final Calendar _calendarEnd;
    private final int _loanTerm;
    private final Long _wallet;
    private Long _oldWallet;
    private final EnumTypeCredit _type;
    private final EnumCreditStatus _status;
    private final EnumCreditStatus _oldStatus;
    private ArrayList<MoneyPNL> _pnls;
    private ArrayList<MoneyPNL> _deletedPnls;
    private final Long _inc4CostCategoryId;
    private final Long _inc4CostSubCategoryId;
    private ArrayList<MoneyCreditPivotPoint> _pivots;
    private ArrayList<MoneyCreditPivotPoint> _deletedPivots;

    public void set_pnls(ArrayList<MoneyPNL> list) { _pnls = list; }
    public ArrayList<MoneyPNL> get_pnls() { return _pnls; }
    public ArrayList<MoneyPNL> get_deletedPnls() { return _deletedPnls; }
    public void add_deletedPNL(MoneyPNL item) { _deletedPnls.add(item);}

    public String get_name() { return _name; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public Long get_target_id() { return _target_id; }
    public String get_targetName() { return _targetName; }
    public Long get_startSummLong() { return _startSummLong; }
    public Long get_oldStartSumLong() { return _oldStartSumLong; }
    public Long get_sumLong() { return _sumLong; }
    public double get_percent() { return _percent; }
    public Calendar get_calendarStart() { return _calendarStart; }

    public Date get_dateCalendarStart() { return _calendarStart.getTime(); }
    public int get_calendarStart_Date() { return _calendarStart.get(Calendar.DATE); }
    public int get_calendarStart_Month() { return _calendarStart.get(Calendar.MONTH); }
    public int get_calendarStart_Year() { return _calendarStart.get(Calendar.YEAR); }
    public int get_loanTerm() { return _loanTerm; }
    public int get_calendarEnd_Date() { return _calendarEnd.get(Calendar.DATE); }
    public int get_calendarEnd_Month() { return _calendarEnd.get(Calendar.MONTH); }
    public int get_calendarEnd_Year() { return _calendarEnd.get(Calendar.YEAR); }
    public EnumTypeCredit get_type() { return _type; }
    public EnumCreditStatus get_status() { return _status; }
    public EnumCreditStatus get_oldStatus() { return _oldStatus; }
    public Long get_wallet() { return _wallet; }
    public Long get_oldWallet() { return _oldWallet; }
    public Long get_inc4CostCategoryId() { return _inc4CostCategoryId; }
    public Long get_inc4CostSubCategoryId() { return _inc4CostSubCategoryId; }

    public ArrayList<MoneyCreditPivotPoint> get_pivots() { return _pivots; }
    public void set_points(ArrayList<MoneyCreditPivotPoint> points) { _pivots = points; }
    public ArrayList<MoneyCreditPivotPoint> get_deletedPivots() { return _deletedPivots; }
    public void add_deletedPoints(MoneyCreditPivotPoint item) { _deletedPivots.add(item);}

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyCreditDataSource.GetCount(context) >= moneyCreditFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyCredit), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    private void reNew()
    {
        _oldWallet = _wallet;
        _oldStartSumLong = _startSummLong;
        _deletedPnls.clear();
        _deletedPivots.clear();
    }

    public MoneyCredit(Long id,
                       Long target_id,
                       String targetName,
                       String name,
                       Long startSummLong, Long oldStartSumLong,
                       Long sumLong,
                       double percent,
                       int dayStart, int monthStart, int yearStart,
                       int loanTerm,
                       EnumTypeCredit type,
                       EnumCreditStatus status,
                       EnumCreditStatus oldStatus,
                       Long wallet, Long oldWallet,
                       Long inc4CostCategoryId,
                       Long inc4CostSubCategoryId,
                       ArrayList<MoneyCreditPivotPoint> points,
                       ArrayList<MoneyCreditPivotPoint> deletedPoints,
                       ArrayList<MoneyPNL> pnls,
                       ArrayList<MoneyPNL> deletedPnls)
    {
        _id = id;
        _target_id = target_id;
        _targetName = targetName;
        _name = name;
        _startSummLong = startSummLong;
        _sumLong = sumLong;
        _oldStartSumLong = oldStartSumLong;

        _calendarStart = Calendar.getInstance();
        _calendarStart.set(yearStart, monthStart, dayStart, 0, 0, 0);
        _calendarStart.set(Calendar.HOUR, 0);
        _calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        _calendarStart.set(Calendar.MILLISECOND, 0);

        _loanTerm = loanTerm;
        _calendarEnd = Calendar.getInstance();
        _calendarEnd.set(yearStart, monthStart, dayStart, 0, 0, 0);
        _calendarEnd.set(Calendar.HOUR, 0);
        _calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        _calendarEnd.set(Calendar.MILLISECOND, 0);
        _calendarEnd.add(Calendar.MONTH, loanTerm);

        _percent = percent;
        _type = type;
        _status = status;
        _oldStatus = oldStatus;
        _wallet = wallet;
        _oldWallet = oldWallet;
        _inc4CostCategoryId = inc4CostCategoryId;
        _inc4CostSubCategoryId = inc4CostSubCategoryId;

        _pivots = points;
        _deletedPivots = deletedPoints;
        if (_deletedPivots == null)
            _deletedPivots = new ArrayList<>();

        _pnls = pnls;
        _deletedPnls = deletedPnls;
        if (_deletedPnls == null)
            _deletedPnls = new ArrayList<>();
    }

    public MoneyCredit(Long id,
                       Long target_id,
                       String targetName,
                       String name,
                       Long startSummLong, Long oldStartSumLong,
                       Long summLong,
                       double percent,
                       Calendar calendarStart,
                       int loanTerm,
                       EnumTypeCredit type,
                       EnumCreditStatus status,
                       EnumCreditStatus oldStatus,
                       Long wallet, Long oldWallet,
                       Long inc4CostCategoryId,
                       Long inc4CostSubCategoryId,
                       ArrayList<MoneyCreditPivotPoint> points,
                       ArrayList<MoneyCreditPivotPoint> deletedPoints,
                       ArrayList<MoneyPNL> pnls,
                       ArrayList<MoneyPNL> deletedPnls)
    {
        _id = id;
        _target_id = target_id;
        _targetName = targetName;
        _name = name;
        _startSummLong = startSummLong;
        _oldStartSumLong = oldStartSumLong;
        _sumLong = summLong;

        _calendarStart = calendarStart;

        _loanTerm = loanTerm;
        _calendarEnd = Calendar.getInstance();
        _calendarEnd.set(calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DATE), 0, 0, 0);
        _calendarEnd.set(Calendar.HOUR, 0);
        _calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        _calendarEnd.set(Calendar.MILLISECOND, 0);
        _calendarEnd.add(Calendar.MONTH, loanTerm);

        _percent = percent;
        _type = type;
        _status = status;
        _oldStatus = oldStatus;
        _wallet = wallet;
        _oldWallet = oldWallet;
        _inc4CostCategoryId = inc4CostCategoryId;
        _inc4CostSubCategoryId = inc4CostSubCategoryId;

        _pivots = points;
        _deletedPivots = deletedPoints;
        if (_deletedPivots == null)
            _deletedPivots = new ArrayList<>();

        _pnls = pnls;
        _deletedPnls = deletedPnls;
        if (_deletedPnls == null)
            _deletedPnls = new ArrayList<>();
    }

    //region Compare
    public boolean CompareDate(MoneyCredit moneyCredit)
    {
        return moneyCredit.get_calendarStart_Date() == get_calendarStart_Date() &&
                moneyCredit.get_calendarStart_Month() == get_calendarStart_Month() &&
                moneyCredit.get_calendarStart_Year() == get_calendarStart_Year();
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyCredit(this, context);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyCredit(this, context);
        TrackCount(dbHelper, context);
        for (int i = 0; i < get_pnls().size(); i++)
            TrackCountPNL(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        boolean b = dbHelper.UpdateMoneyCredit(this, context);
        if (b)
        {
            reNew();
            for (MoneyPNL item: get_pnls())
                item.reNew();
        }

        return b;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyCredit(this, context);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyCredit(MoneyCreditDataSource.GetCount(dbHelper));
    }

    private void TrackCountPNL(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyPNL(MoneyPNLDataSource.GetCount(dbHelper));
    }
    //endregion
}
