package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTargetDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumAccumulationType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import java.util.ArrayList;
import java.util.Calendar;

public class MoneyAccumulation extends CustomMoneyObject
{
    //region ClassParams
    private Long _id;
    private Long _targetId;
    private final String _name;
    private final EnumAccumulationType _accumulationType;
    private final Integer _progressionCoefficent;
    private final Integer _monthStart;
    private final Integer _yearStart;
    private final Integer _monthEnd;
    private final Integer _yearEnd;
    private final Integer _startSum;
    private ArrayList<MoneyPNL> _pnls;
    private ArrayList<MoneyPNL> _deletedPnls;
    //endregion

    //region CustomMoneyObject
    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }

    public static boolean canAdd(Context context, boolean hasSubscription)
    {
        if (hasSubscription)
            return true;

        if (MoneyTargetDataSource.GetCount(context) >= moneyTargetFreeCount)
        {
            Toast.makeText(context,
                    context.getString(R.string.ErrorTooManyTarget), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public boolean canDelete(Context context)
    {
        return true;
    }

    public boolean canUpdate()
    {
        if (_pnls == null)
            return true;

        for (MoneyPNL item: _pnls)
            if (!item.get_isPlan())
                return false;

        return true;
    }
    //endregion

    //region GetParams
    public Long get_id() { return _id;}
    public Long get_targetId() { return _targetId; }
    public EnumAccumulationType get_accumulationType() { return _accumulationType; }
    public Integer get_progressionCoefficent() { return _progressionCoefficent; }
    public Integer get_startSum() { return _startSum; }
    public Integer get_monthStart() { return _monthStart; }
    public Integer get_yearStart() { return _yearStart; }
    public Integer get_monthEnd() { return _monthEnd; }
    public Integer get_yearEnd() { return _yearEnd; }
    public ArrayList<MoneyPNL> get_pnls() { return _pnls; }
    public ArrayList<MoneyPNL> get_deletedPnls() { return _deletedPnls; }
    private void clear_deletedPnls() { _deletedPnls.clear(); }
    public int GetSumForExpense(Context context)
    {
        if (_pnls == null)
            _pnls = MoneyPNLDataSource.LoadData4Accumulation(context, EnumTypeCategory.Wallet, _id.intValue());

        int sum = 0;

        if (_accumulationType.equals(EnumAccumulationType.Linear))
            sum = Math.toIntExact(_pnls.get(_pnls.size() - 1).get_sumLong());
        else if (_accumulationType.equals(EnumAccumulationType.AlgorithmicProgression))
        {
            int cnt = 0;
            for (MoneyPNL item: _pnls)
                if (item.get_isPlan())
                {
                    sum += item.get_sumLong();
                    cnt++;
                }

            sum /= cnt;
        }

        return sum;
    }
    public boolean get_noAccumulationThisMonth(int month, int year)
    {
        for (MoneyPNL item: _pnls)
            if (item.getCalendar().get(Calendar.MONTH) == month &&
               item.getCalendar().get(Calendar.YEAR) == year)
                return item.get_isPlan();

        return false;
    }
    public boolean get_accumulationDone()
    {
        for (MoneyPNL item: _pnls)
            if (item.get_isPlan())
                return false;

        return true;
    }
    //endregion

    //region SetParams
    public void set_targetId(Long targetId) { _targetId = targetId; }
    //endregion

    //region ReNew
    public void ReNew()
    {
        clear_deletedPnls();
    }
    //endregion

    public MoneyAccumulation(Long id,
                             Long targetId,
                             String name,
                             EnumAccumulationType accumulationType,
                             Integer progressionCoefficent,
                             Integer monthStart, Integer yearStart,
                             Integer monthEnd, Integer yearEnd,
                             Integer startSum,
                             ArrayList<MoneyPNL> pnls,
                             ArrayList<MoneyPNL> deletedPnls)
    {
        _id = id;
        _targetId = targetId;
        _name = name;
        _accumulationType = accumulationType;
        _progressionCoefficent = progressionCoefficent;
        _monthStart = monthStart;
        _yearStart = yearStart;
        _monthEnd = monthEnd;
        _yearEnd = yearEnd;
        _startSum = startSum;
        _pnls = pnls;
        _deletedPnls = deletedPnls;
        if (_deletedPnls == null)
            _deletedPnls = new ArrayList<>();
    }

    //region CustomMoneyObject
    public void AddToDatabaseWOTrack(DBHelper dbHelper, Context context) throws Exception
    {
//        _id = dbHelper.AddMoneyTarget(this, context);
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        _id = dbHelper.AddMoneyTarget(this, context);
//        TrackCount(dbHelper);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        boolean b = dbHelper.UpdateMoneyTarget(this);
//        if (b)
//            clear_deletedPnls();
        return true;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.DeleteMoneyTarget(_id);
        return true;
    }

    private void TrackCount(DBHelper dbHelper)
    {
//        CustomTrack.TrackCountMoneyTarget(MoneyTargetDataSource.GetCount(dbHelper));
    }
    //endregion
}
