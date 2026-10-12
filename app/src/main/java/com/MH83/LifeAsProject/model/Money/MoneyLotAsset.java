package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAssetDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MoneyLotAsset extends CustomMoneyObject
    implements IgetId, IIsParent
{
    //region EnumTypeMoneyLotAsset
    public enum EnumTypeMoneyLotAsset
    {
        Summary,
        KindAsset,
        TypeAsset,
        Lot,
        LotWallet,
        LotCredit
    }
    //endregion

    //region Parameters
    private Long _id;
    private Long _parentId = 0L;
    private final Long _groupPortfolioId;
    private final String _groupPortfolioName;
    private final Long _portfolioId;
    private final String _portfolioName;
    private final Long _portfolioWalletId;
    private final String _name;
    private final String _comment;
    private final Integer _count;
    private int _position;
    private final EnumStatus _status;
    private final MoneyAsset _moneyAsset;
    private boolean _hasChild;
    private EnumTypeMoneyLotAsset _typeMoneyLotAsset = EnumTypeMoneyLotAsset.Lot;
    private final Calendar _calendarStart;
    private Calendar _calendarEnd;
    private final ArrayList<MoneyLotAssetOperation> _lotAssetOperations;
    private ArrayList<MoneyLotAssetOperation> _deletedLotAssetOperations;
    //endregion

    //region IGetId
    @Override
    public Long get_parentId() { return _parentId; }

    public void set_parentId(Long parentId) { this._parentId = parentId; }
    //endregion

    //region IIsParent
    @Override
    public boolean isParent()
    {
        return _typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.KindAsset) ||
                _typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.TypeAsset);
    }
    //endregion

    //region Get/Set data
    public String get_name() { return _name; }
    public String get_comment() { return _comment; }
    @Override
    public String getSValue() { return _name; }
    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }
    public EnumStatus get_status() { return _status; }
    public MoneyAsset get_moneyAsset() { return _moneyAsset; }
    public void set_hasChild(boolean hasChild) { _hasChild = hasChild; }
    public Long get_groupPortfolioId() { return _groupPortfolioId; }

    public String get_groupPortfolioName() { return _groupPortfolioName; }

    public Long get_portfolioId() { return _portfolioId; }

    public Long get_portfolioWalletId() { return _portfolioWalletId; }

    public String get_portfolioName() { return _portfolioName; }

    public Integer get_count() { return _count; }

    public Calendar get_calendarStart() { return _calendarStart; }

    public Calendar get_calendarEnd() { return _calendarEnd; }

    public int get_dateStart() { return _calendarStart.get(Calendar.DATE); }
    public int get_monthStart() { return _calendarStart.get(Calendar.MONTH); }
    public int get_yearStart() { return _calendarStart.get(Calendar.YEAR); }
    public int get_dateEnd() { return _calendarEnd.get(Calendar.DATE); }
    public int get_monthEnd() { return _calendarEnd.get(Calendar.MONTH); }
    public int get_yearEnd() { return _calendarEnd.get(Calendar.YEAR); }
    public ArrayList<MoneyLotAssetOperation> get_lotAssetOperations() { return _lotAssetOperations; }
    public ArrayList<MoneyLotAssetOperation> get_deletedLotAssetOperations() { return _deletedLotAssetOperations;}

    public EnumTypeMoneyLotAsset get_typeMoneyLotAsset() { return _typeMoneyLotAsset; }

    @Override
    public boolean canDelete(Context context)
    {
        return _typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.Lot);
    }
    public static boolean canAdd(Context context, boolean hasSubscription)
    {
//        if (hasSubscription)
//            return true;
//
//        if (MoneyAssetDataSource.GetCount(context) >= moneyAssetFreeCount)
//        {
//            Toast.makeText(context,
//                    context.getString(R.string.ErrorTooManyAsset), Toast.LENGTH_LONG).show();
//
//            return false;
//        }

        return false;
    }

    private void reNew()
    {
        _deletedLotAssetOperations.clear();
    }
    //endregion

    //region Recalc calc-data
    private Integer _calcCountStart = null;
    private Integer _calcCountEnd = null;
    private Long _calcPriceStart = null;
    private Long _calcSumStart = null;
    private Long _calcSumStartPercent = null;
    private Long _calcSumEndPercent = null;
    private Long _calcNKDStart = null;
    private Integer _calcCountThisPeriodPlus = null;
    private Long _calcPriceThisPeriodPlus = null;
    private Long _calcNKDThisPeriodPlus = null;
    private Long _calcSumThisPeriodPlus = null;
    private Integer _calcCountThisPeriodMinus = null;
    private Long _calcPriceThisPeriodMinus = null;
    private Long _calcNKDThisPeriodMinus = null;
    private Long _calcSumThisPeriodMinus = null;
    private Long _calcPriceEnd = null;
    private Long _calcSumEnd = null;
    private Long _calcNKDEnd = null;
    private Long _calcPayments = null;
    private Long _calcDifference = null;
    private Long _calcDifferenceTotal = null;
    private Long _calcProfitabilityThisPeriod = null;
    private Long _calcProfitabilityTotal = null;
    private Long _calcBaseSumPlus = null;

    public Integer get_calcCountStart() { return _calcCountStart; }

    public void add_calcCountStart(Integer calcCountStart)
    {
        if (calcCountStart == null)
            return;

        if (_calcCountStart == null)
            _calcCountStart = calcCountStart;
        else
            _calcCountStart += calcCountStart;

        if (_calcCountStart == 0L)
            _calcCountStart = null;
    }
    public Long get_calcSumStart() { return _calcSumStart; }

    public Long get_calcBaseSumPlus(Context context, EnumKindAsset kindAsset)
    {
        if (kindAsset.equals(EnumKindAsset.Conservative) &&
                _typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.TypeAsset) &&
            !_name.equals(context.getString(R.string.WalletCategories)))
            return null;
        if (_typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.KindAsset) &&
                !_name.equals(context.getString(R.string.Money_KindAsset_Conservative)))
            return null;
        return _calcBaseSumPlus;
    }

    public Long get_calcNKDStart() { return _calcNKDStart; }

    public Integer get_calcCountThisPeriodPlus() { return _calcCountThisPeriodPlus; }
    public void add_calcCountThisPeriodPlus(Integer calcCountThisPeriodPlus)
    {
        if (calcCountThisPeriodPlus == null)
            return;

        if (_calcCountThisPeriodPlus == null)
            _calcCountThisPeriodPlus = calcCountThisPeriodPlus;
        else
            _calcCountThisPeriodPlus += calcCountThisPeriodPlus;

        if (_calcCountThisPeriodPlus == 0L)
            _calcCountThisPeriodPlus = null;
    }
    public Long get_calcSumThisPeriodPlus() { return _calcSumThisPeriodPlus; }
    public void add_calcSumThisPeriodPlus(Long calcSumThisPeriodPlus)
    {
        if (calcSumThisPeriodPlus == null)
            return;

        if (_calcSumThisPeriodPlus == null)
            _calcSumThisPeriodPlus = calcSumThisPeriodPlus;
        else
            _calcSumThisPeriodPlus += calcSumThisPeriodPlus;

        if (_calcSumThisPeriodPlus == 0L)
            _calcSumThisPeriodPlus = null;
    }
    public Long get_calcNKDThisPeriodPlus() { return _calcNKDThisPeriodPlus; }
    public void add_calcNKDThisPeriodPlus(Long calcNKDThisPeriod)
    {
        if (calcNKDThisPeriod == null)
            return;

        if (_calcNKDThisPeriodPlus == null)
            _calcNKDThisPeriodPlus = calcNKDThisPeriod;
        else
            _calcNKDThisPeriodPlus += calcNKDThisPeriod;

        if (_calcNKDThisPeriodPlus == 0L)
            _calcNKDThisPeriodPlus = null;
    }

    public Integer get_calcCountThisPeriodMinus() { return _calcCountThisPeriodMinus; }
    public void add_calcCountThisPeriodMinus(Integer calcCountThisPeriodMinus)
    {
        if (calcCountThisPeriodMinus == null)
            return;

        if (_calcCountThisPeriodMinus == null)
            _calcCountThisPeriodMinus = calcCountThisPeriodMinus;
        else
            _calcCountThisPeriodMinus += calcCountThisPeriodMinus;

        if (_calcCountThisPeriodMinus == 0L)
            _calcCountThisPeriodMinus = null;
    }
    public Long get_calcSumThisPeriodMinus() { return _calcSumThisPeriodMinus; }
    public void add_calcSumThisPeriodMinus(Long calcSumThisPeriodMinus)
    {
        if (calcSumThisPeriodMinus == null)
            return;

        if (_calcSumThisPeriodMinus == null)
            _calcSumThisPeriodMinus = calcSumThisPeriodMinus;
        else
            _calcSumThisPeriodMinus += calcSumThisPeriodMinus;

        if (_calcSumThisPeriodMinus == 0L)
            _calcSumThisPeriodMinus = null;
    }
    public Long get_calcNKDThisPeriodMinus() { return _calcNKDThisPeriodMinus; }
    public void add_calcNKDThisPeriodMinus(Long calcNKDThisPeriodMinus)
    {
        if (calcNKDThisPeriodMinus == null)
            return;

        if (_calcNKDThisPeriodMinus == null)
            _calcNKDThisPeriodMinus = calcNKDThisPeriodMinus;
        else
            _calcNKDThisPeriodMinus += calcNKDThisPeriodMinus;

        if (_calcNKDThisPeriodMinus == 0L)
            _calcNKDThisPeriodMinus = null;
    }

    public Long get_calcNKDEnd() { return _calcNKDEnd; }

    public void add_calcSumStart(Long calcSumStart)
    {
        if (calcSumStart == null)
            return;

        if (_calcSumStart == null)
            _calcSumStart = calcSumStart;
        else
            _calcSumStart += calcSumStart;

        if (_calcSumStart == 0L)
            _calcSumStart = null;
    }
    public void add_calcBaseSumPlus(Long calcBaseSumPlus)
    {
        if (calcBaseSumPlus == null)
            return;

        if (_calcBaseSumPlus == null)
            _calcBaseSumPlus = calcBaseSumPlus;
        else
            _calcBaseSumPlus += calcBaseSumPlus;

        if (_calcBaseSumPlus == 0L)
            _calcBaseSumPlus = null;
    }
    public void add_calcNKDStart(Long calcNKDStart)
    {
        if (calcNKDStart == null)
            return;

        if (_calcNKDStart == null)
            _calcNKDStart = calcNKDStart;
        else
            _calcNKDStart += calcNKDStart;

        if (_calcNKDStart == 0L)
            _calcNKDStart = null;
    }
    public void add_calcNKDEnd(Long calcNKDEnd)
    {
        if (calcNKDEnd == null)
            return;

        if (_calcNKDEnd == null)
            _calcNKDEnd = calcNKDEnd;
        else
            _calcNKDEnd += calcNKDEnd;

        if (_calcNKDEnd == 0L)
            _calcNKDEnd = null;
    }

    public Long get_calcSumEnd() { return _calcSumEnd; }
    public void add_calcSumEnd(Long calcSumEnd)
    {
        if (calcSumEnd == null)
            return;

        if (_calcSumEnd == null)
            _calcSumEnd = calcSumEnd;
        else
            _calcSumEnd += calcSumEnd;

        if (_calcSumEnd == 0L)
            _calcSumEnd = null;
    }
    public Long get_calcPayments() { return _calcPayments; }
    public void add_calcPayments(Long calcPayments)
    {
        if (calcPayments == null)
            return;

        if (_calcPayments == null)
            _calcPayments = calcPayments;
        else
            _calcPayments += calcPayments;

        if (_calcPayments == 0L)
            _calcPayments = null;
    }
    public Long get_calcDifference() { return _calcDifference; }
    public void add_calcDifference(Long calcDifference)
    {
        if (calcDifference == null)
            return;

        if (_calcDifference == null)
            _calcDifference = calcDifference;
        else
            _calcDifference += calcDifference;

        if (_calcDifference == 0L)
            _calcDifference = null;
    }

    public Long get_calcDifferenceTotal() { return _calcDifferenceTotal; }
    public void add_calcDifferenceTotal(Long calcDifferenceTotal)
    {
        if (calcDifferenceTotal == null)
            return;

        if (_calcDifferenceTotal == null)
            _calcDifferenceTotal = calcDifferenceTotal;
        else
            _calcDifferenceTotal += calcDifferenceTotal;

        if (_calcDifferenceTotal == 0L)
            _calcDifferenceTotal = null;
    }

    public Long get_calcProfitabilityThisPeriod() { return _calcProfitabilityThisPeriod; }
    public Long get_calcProfitabilityTotal() { return _calcProfitabilityTotal; }

    public Long get_calcSumStartPercent() { return _calcSumStartPercent; }
    public Long get_calcSumEndPercent() { return _calcSumEndPercent; }

    public void InitialCalcData()
    {
        _calcCountStart = null;
        _calcPriceStart = null;
        _calcSumStart = null;
        _calcNKDStart = null;
        _calcCountEnd = null;
        _calcPriceEnd = null;
        _calcSumEnd = null;
        _calcNKDEnd = null;
        _calcCountThisPeriodPlus = null;
        _calcPriceThisPeriodPlus = null;
        _calcSumThisPeriodPlus = null;
        _calcNKDThisPeriodPlus = null;
        _calcCountThisPeriodMinus = null;
        _calcPriceThisPeriodMinus = null;
        _calcSumThisPeriodMinus = null;
        _calcNKDThisPeriodMinus = null;
        _calcPayments = null;
        _calcDifference = null;
        _calcDifferenceTotal = null;
        _calcProfitabilityThisPeriod = null;
        _calcProfitabilityTotal = null;
        _calcSumStartPercent = null;
        _calcSumEndPercent = null;
        _calcBaseSumPlus = null;
    }

    public void RecalcCalcData(CustomPeriodClass customPeriodClass,
                               Context context,
                               Long sumStartTotal,
                               Long sumEndtotal)
    {
        long _calcBaseSum = 0L;

        if (_typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.Lot) ||
                _typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.LotWallet) ||
                _typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.LotCredit))
        {
            _calcCountStart = null;
            _calcPriceStart = null;
            _calcSumStart = null;
            _calcNKDStart = null;
            _calcCountThisPeriodPlus = null;
            _calcPriceThisPeriodPlus = null;
            _calcSumThisPeriodPlus = null;
            _calcNKDThisPeriodPlus = null;
            _calcCountThisPeriodMinus = null;
            _calcPriceThisPeriodMinus = null;
            _calcSumThisPeriodMinus = null;
            _calcNKDThisPeriodMinus = null;
            _calcCountEnd = null;
            _calcPriceEnd = null;
            _calcSumEnd = null;
            _calcNKDEnd = null;
            _calcPayments = null;
            _calcDifference = null;
            _calcDifferenceTotal = null;
            _calcProfitabilityThisPeriod = null;
            _calcProfitabilityTotal = null;
            _calcSumStartPercent = null;
            _calcSumEndPercent = null;
            _calcBaseSumPlus = 0L;
            Long _calcComission;
            if (_lotAssetOperations == null)
                return;

            Calendar customPeriodClassCalendarStart = customPeriodClass.get_start_dateC();
            if (customPeriodClassCalendarStart == null)
            {
                customPeriodClassCalendarStart = Calendar.getInstance();
                customPeriodClassCalendarStart.set(1900, 1, 1);
            }

            Calendar customPeriodClassCalendarEnd = customPeriodClass.get_end_dateC();
            if (customPeriodClassCalendarEnd == null)
            {
                customPeriodClassCalendarEnd = Calendar.getInstance();
                customPeriodClassCalendarEnd.set(9999, 11, 25);
            }

            if (_typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.Lot))
            {
                //region _calcCountStart, _calcPriceStart, _calcSumStart
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() < customPeriodClassCalendarStart.getTime().getTime() &&
                            (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                    moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Revaluation))) {
                        _calcCountStart = moneyLotAssetOperation.get_count();
                        _calcPriceStart = moneyLotAssetOperation.get_price();
                        break;
                    }
                }
                if (_calcCountStart != null && _calcPriceStart != null)
                    _calcSumStart = _calcCountStart * _calcPriceStart;
                //endregion

                //region _calcCountThisPeriodPlus, _calcPriceThisPeriodPlus, _calcSumThisPeriodPlus
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations) {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime()) {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy)) {
                            _calcCountThisPeriodPlus = moneyLotAssetOperation.get_count();
                            _calcPriceThisPeriodPlus = moneyLotAssetOperation.get_price();
                            break;
                        }
                    }
                }
                if (_calcCountThisPeriodPlus != null && _calcPriceThisPeriodPlus != null)
                    _calcSumThisPeriodPlus = _calcCountThisPeriodPlus * _calcPriceThisPeriodPlus;
                _calcBaseSumPlus = _calcSumThisPeriodPlus;
                //endregion

                //region _calcCountThisPeriodMinus, _calcPriceThisPeriodMinus, _calcSumThisPeriodMinus
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime()) {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell))
                        {
                            _calcCountThisPeriodMinus = moneyLotAssetOperation.get_count();
                            _calcPriceThisPeriodMinus = -moneyLotAssetOperation.get_price();
                            break;
                        }
                    }
                }
                if (_calcCountThisPeriodMinus != null && _calcPriceThisPeriodMinus != null)
                    _calcSumThisPeriodMinus = _calcCountThisPeriodMinus * _calcPriceThisPeriodMinus;
                //endregion

                //region _calcCountEnd, _calcPriceEnd, _calcSumEnd
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Revaluation)) {
                        _calcCountEnd = moneyLotAssetOperation.get_count();
                        _calcPriceEnd = moneyLotAssetOperation.get_price();
                        break;
                    }
                }
                if (_calcCountEnd != null && _calcPriceEnd != null)
                    _calcSumEnd = _calcCountEnd * _calcPriceEnd;
                //endregion

                //region _calcComission
                _calcComission = 0L;
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                    moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell)))
                        _calcComission += moneyLotAssetOperation.get_commission();
                }
                //endregion

                //region _calcDifference
                if (_calcSumStart != null && _calcSumEnd != null)
                    _calcDifference = _calcSumEnd - _calcSumStart;
                else if (_calcSumThisPeriodPlus != null && _calcSumEnd != null)
                    _calcDifference = _calcSumEnd - _calcSumThisPeriodPlus;
                else if (_calcSumEnd != null && _calcSumThisPeriodPlus != null)
                    _calcDifference = _calcSumEnd - _calcSumThisPeriodPlus;
                else if (_calcSumStart != null && _calcSumThisPeriodMinus != null)
                    _calcDifference = -_calcSumThisPeriodMinus - _calcSumStart;

                if (_calcComission != null) {
                    if (_calcDifference == null)
                        _calcDifference = -_calcComission;
                    else
                        _calcDifference -= _calcComission;
                }
                if (_calcSumThisPeriodMinus == null)
                    _calcSumThisPeriodMinus = -_calcComission;
                else
                    _calcSumThisPeriodMinus -= _calcComission;
                //endregion

                //region _calcNKDStart
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() < customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy)) {
                        _calcNKDStart = moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                        break;
                    }
                }

                if (_calcNKDStart != null && _calcNKDStart > 0L)
                    for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                        if (moneyLotAssetOperation.get_calendar().getTime().getTime() < customPeriodClassCalendarStart.getTime().getTime() &&
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon)) {
                            long payment = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            if (payment < _calcNKDStart)
                                _calcNKDStart -= payment;
                            else {
                                _calcNKDStart = 0L;
                                break;
                            }
                        }
                //endregion

                //region _calcNKDThisPeriodPlus
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy)) {
                        _calcNKDThisPeriodPlus = moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                        break;
                    }
                }
                //endregion

                //region _calcNKDThisPeriodMinus
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell))
                    {
                        _calcNKDThisPeriodMinus = moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                        break;
                    }
                }
                //endregion

                //region _calcNKDEnd
                if (_calcNKDStart != null && _calcNKDStart > 0L)
                    _calcNKDEnd = _calcNKDStart;
                else if (_calcNKDThisPeriodPlus != null && _calcNKDThisPeriodPlus > 0L)
                    _calcNKDEnd = _calcNKDThisPeriodPlus;
                if (_calcNKDEnd != null && _calcNKDEnd > 0L)
                    for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                    {
                        if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                                moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon)) {
                            long payment = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            if (payment < _calcNKDEnd)
                                _calcNKDEnd -= payment;
                            else {
                                _calcNKDEnd = 0L;
                                break;
                            }
                        }
                    }
                //endregion

                //region _calcDifferenceTotal
                if (_calcSumStart != null && _calcSumEnd != null)
                {
                    _calcDifferenceTotal = _calcSumEnd - _calcSumStart;
                    if (_calcNKDStart != null)
                        _calcDifferenceTotal -= _calcNKDStart;
                    if (_calcNKDEnd != null)
                        _calcDifferenceTotal += _calcNKDEnd;
                }
                else if (_calcSumThisPeriodPlus != null && _calcSumEnd != null)
                {
                    _calcDifferenceTotal = _calcSumEnd;
                    if (_calcNKDEnd != null)
                        _calcDifferenceTotal += _calcNKDEnd;
                }
                else if (_calcSumThisPeriodMinus != null)
                {
                    if (_calcSumStart != null)
                        _calcDifferenceTotal = -_calcSumStart;
                }

                //endregion

                //region _calcPayments
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                    moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend)))
                    {
                        if (_calcPayments == null)
                            _calcPayments = 0L;
                        _calcPayments += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                    }
                }
                if (_calcNKDStart != null && _calcPayments != null)
                    _calcPayments -= _calcNKDStart;
                else if (_calcNKDThisPeriodPlus != null && _calcPayments != null)
                    _calcPayments -= _calcNKDThisPeriodPlus;

//                if (_calcPayments != null && _calcDifferenceTotal != null && _calcSumStart != null)
//                    _calcDifferenceTotal += _calcPayments;
                //endregion

                //region _calcBaseSum
                _calcBaseSum += _calcSumStart != null ? _calcSumStart : 0L;
                _calcBaseSum += _calcSumThisPeriodPlus != null ? _calcSumThisPeriodPlus : 0L;
                //endregion
            }
            else if (_typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.LotWallet))
            {
                //region _calcCountStart, _calcPriceStart, _calcSumStart
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() < customPeriodClassCalendarStart.getTime().getTime())
                    {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                        {
                            if (_calcSumStart == null)
                            {
                                _calcSumStart = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumStart += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus))
                        {
                            if (_calcSumStart == null)
                            {
                                _calcSumStart = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumStart -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                        {
                            if (_calcSumStart == null)
                                _calcSumStart = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumStart -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                        {
                            if (_calcSumStart == null)
                                _calcSumStart = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumStart += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                    }

                _calcPriceStart = null;
                _calcCountStart = null;
                //endregion

                //region _calcCountThisPeriodPlus, _calcPriceThisPeriodPlus, _calcSumThisPeriodPlus
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime())
                    {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus))
                        {
                            if (_calcSumThisPeriodPlus == null)
                            {
                                _calcSumThisPeriodPlus = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                            }
                            else
                            {
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                            }

                            if (_calcSumThisPeriodMinus == null)
                                _calcSumThisPeriodMinus = -moneyLotAssetOperation.get_commission();
                            else
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();

                            _calcBaseSumPlus += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend))
                        {
                            if (_calcSumThisPeriodPlus == null)
                            {
                                _calcSumThisPeriodPlus = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                            }
                            else
                            {
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                            }

                            if (_calcSumThisPeriodMinus == null)
                                _calcSumThisPeriodMinus = -moneyLotAssetOperation.get_commission();
                            else
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus))
                        {
                            if (_calcSumThisPeriodMinus == null)
                            {
                                _calcSumThisPeriodMinus = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                        {
                            if (_calcSumThisPeriodPlus == null)
                            {
                                _calcSumThisPeriodPlus = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                            }
                            else
                            {
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodPlus += moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                            }

                            if (_calcSumThisPeriodMinus == null)
                                _calcSumThisPeriodMinus = -moneyLotAssetOperation.get_commission();
                            else
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();

                            if (_calcDifference == null)
                                _calcDifference = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcDifference += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                        {
                            if (_calcSumThisPeriodMinus == null)
                            {
                                _calcSumThisPeriodMinus = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumThisPeriodMinus -= moneyLotAssetOperation.get_commission();
                            }

                            if (_calcDifference == null)
                                _calcDifference = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcDifference -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                    }
                }

                _calcPriceThisPeriodPlus = null;
                _calcCountThisPeriodPlus = null;
                _calcPriceThisPeriodMinus = null;
                _calcCountThisPeriodMinus = null;
                //endregion

                //region _calcCountEnd, _calcPriceEnd, _calcSumEnd
                _calcSumEnd = _calcSumStart;
//                _calcSumStart -= _calcSumThisPeriod;
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime())
                    {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                        {
                            if (_calcSumEnd == null)
                            {
                                _calcSumEnd = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumEnd += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus))
                        {
                            if (_calcSumEnd == null)
                            {
                                _calcSumEnd = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumEnd -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                        {
                            if (_calcSumEnd == null)
                                _calcSumEnd = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumEnd -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                        {
                            if (_calcSumEnd == null)
                                _calcSumEnd = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumEnd += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                    }
                }

                _calcPriceEnd = null;
                _calcCountEnd = null;
                //endregion

                //region _calcComission
                _calcComission = 0L;
                //endregion

                //region _calcDifference
                if (_calcDifference != null && _calcDifference == 0L)
                    _calcDifference = null;
                //endregion

                //region _calcDifferenceTotal
                if (_calcSumStart != null)
                    _calcDifferenceTotal = _calcSumEnd - _calcSumStart;
                else
                    _calcDifferenceTotal = _calcSumEnd;
                //endregion

                //region _calcNKD
                _calcNKDStart = 0L;
                //endregion

                //region _calcPayments
                _calcNKDStart = null;
                _calcPayments = null;
                //endregion

                if (_calcComission != null)
                {
                    if (_calcDifference == null)
                        _calcDifference = -_calcComission;
                    else
                        _calcDifference -= _calcComission;
                    if (_calcDifference != null && _calcDifference == 0L)
                        _calcDifference = null;
                }

                //region _calcBaseSum
                _calcBaseSum += _calcSumStart != null ? _calcSumStart : 0L;
                _calcBaseSum += _calcBaseSumPlus != null ? _calcBaseSumPlus : 0L;
                //endregion
            }
            else if (_typeMoneyLotAsset.equals(EnumTypeMoneyLotAsset.LotCredit))
            {
                //region _calcCountStart, _calcPriceStart, _calcSumStart
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() < customPeriodClassCalendarStart.getTime().getTime())
                    {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                        {
                            if (_calcSumStart == null)
                            {
                                _calcSumStart = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumStart += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus))
                        {
                            if (_calcSumStart == null)
                            {
                                _calcSumStart = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumStart -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumStart -= moneyLotAssetOperation.get_commission();
                            }
                        }
                    }

                _calcPriceStart = null;
                _calcCountStart = null;
                //endregion

                //region _calcCountThisPeriod, _calcPriceThisPeriod, _calcSumThisPeriod
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime())
                    {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                        {
                            if (_calcSumThisPeriodMinus == null)
                                _calcSumThisPeriodMinus = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumThisPeriodMinus += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus))
                        {
                            if (_calcSumThisPeriodPlus == null)
                                _calcSumThisPeriodPlus = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumThisPeriodPlus -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                    }
                }

                _calcPriceThisPeriodPlus = null;
                _calcCountThisPeriodPlus = null;
                _calcPriceThisPeriodMinus = null;
                _calcCountThisPeriodMinus = null;
                //endregion

                //region _calcCountEnd, _calcPriceEnd, _calcSumEnd
                _calcSumEnd = _calcSumStart;
//                _calcSumStart -= _calcSumThisPeriod;
                for (MoneyLotAssetOperation moneyLotAssetOperation : _lotAssetOperations)
                {
                    if (moneyLotAssetOperation.get_calendar().getTime().getTime() <= customPeriodClassCalendarEnd.getTime().getTime() &&
                            moneyLotAssetOperation.get_calendar().getTime().getTime() >= customPeriodClassCalendarStart.getTime().getTime())
                    {
                        if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                        {
                            if (_calcSumEnd == null)
                            {
                                _calcSumEnd = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumEnd += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                                moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus))
                        {
                            if (_calcSumEnd == null)
                            {
                                _calcSumEnd = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                            else
                            {
                                _calcSumEnd -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_accumulatedPrice() * moneyLotAssetOperation.get_count();
                                _calcSumEnd -= moneyLotAssetOperation.get_commission();
                            }
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                        {
                            if (_calcSumEnd == null)
                                _calcSumEnd = -moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumEnd -= moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                        else if (moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                        {
                            if (_calcSumEnd == null)
                                _calcSumEnd = moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                            else
                                _calcSumEnd += moneyLotAssetOperation.get_price() * moneyLotAssetOperation.get_count();
                        }
                    }
                }

                _calcPriceEnd = null;
                _calcCountEnd = null;
                //endregion

                //region _calcComission
                _calcComission = 0L;
                //endregion

                //region _calcDifference
                //endregion

                //region _calcDifferenceTotal
                if (_calcSumStart != null)
                    _calcDifferenceTotal = _calcSumEnd - _calcSumStart;
                else
                    _calcDifferenceTotal = _calcSumEnd;
                //endregion

                //region _calcNKD
                _calcNKDStart = 0L;
                //endregion

                //region _calcPayments
                _calcNKDStart = null;
                _calcPayments = null;
                //endregion

                if (_calcComission != null)
                {
                    if (_calcDifference == null)
                        _calcDifference = -_calcComission;
                    else
                        _calcDifference -= _calcComission;
                    if (_calcDifference != null && _calcDifference == 0L)
                        _calcDifference = null;
                }

                //region _calcBaseSum
                _calcBaseSum += _calcSumStart != null ? _calcSumStart : 0L;
                _calcBaseSum += _calcSumThisPeriodPlus != null ? _calcSumThisPeriodPlus : 0L;
                //endregion
            }
        }
        else
        {
            //region _calcBaseSum
            _calcBaseSum += _calcSumStart != null ? _calcSumStart : 0L;
            _calcBaseSum += _calcBaseSumPlus != null ? _calcBaseSumPlus : 0L;
            //endregion
        }

        //region _calcProfitability
        long diff = 0L;
        diff += _calcDifference != null ? _calcDifference : 0L;
        diff += _calcPayments != null ? _calcPayments : 0L;
        if (_calcBaseSum != 0L)
            _calcProfitabilityThisPeriod = 10000 * diff / _calcBaseSum;
        //endregion

        //region _calcProfitabilityTotal
        if (_calcSumStart == null && _calcDifferenceTotal != null)
            _calcProfitabilityTotal = _calcDifferenceTotal > 0L ? 10000L : -10000L;
        else if (_calcDifferenceTotal != null && _calcSumStart != 0L)
            _calcProfitabilityTotal = 10000L * _calcDifferenceTotal / _calcSumStart;
        //endregion

        //region _calcSumStartPercent, _calcSumEndPercent
        if (_calcSumStart != null && sumStartTotal != null && sumStartTotal != 0)
            _calcSumStartPercent = 10000 * _calcSumStart / sumStartTotal;
        if (_calcSumEnd != null && sumEndtotal != null && sumEndtotal != 0)
            _calcSumEndPercent = 10000 * _calcSumEnd / sumEndtotal;
        //endregion
    }
    //endregion

    //region Constructors
    public static MoneyLotAsset FromEnumKindAsset(Context context, EnumKindAsset kindAsset)
    {
        long id;
        String name;
        int position;
        switch (kindAsset)
        {
            case Conservative:
                id = -9999L;
                name = context.getString(R.string.Money_KindAsset_Conservative);
                position = 1;
                break;
            case Moderate:
                id = -9998L;
                name = context.getString(R.string.Money_KindAsset_Moderate);
                position = 2;
                break;
            default:
                id = -9997L;
                name = context.getString(R.string.Money_KindAsset_Agressive);
                position = 3;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1900, 1, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2900, 12, 31);

        return new MoneyLotAsset(id,
                0L, "",
                0L, "",
                0L,
                null,
                name, kindAsset.name(), position, 1,
                calendar1, calendar2,
                EnumStatus.Started,
                EnumTypeMoneyLotAsset.KindAsset,
                null,
                null);
    }

    public static MoneyLotAsset GetSummaryMoneyLotAsset(Context context)
    {
        long id = -10000;
        String name = "";
        int position = 0;

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1900, 1, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2900, 12, 31);

        return new MoneyLotAsset(id,
                0L, "",
                0L, "",
                0L,
                null,
                name, "", position, 1,
                calendar1, calendar2,
                EnumStatus.Started,
                EnumTypeMoneyLotAsset.Summary,
                null,
                null);
    }

    public MoneyLotAsset(Long id,
                         Long groupPortfolioId,
                         String groupPortfolioName,
                         Long portfolioId,
                         String portfolioName,
                         Long portfolioWalletId,
                         MoneyAsset moneyAsset,
                         String name,
                         String comment,
                         int position,
                         int count,
                         Calendar calendarStart,
                         Calendar calendarEnd,
                         EnumStatus status,
                         ArrayList<MoneyLotAssetOperation> lotAssetOperations,
                         ArrayList<MoneyLotAssetOperation> deletedLotAssetOperations)
    {
        _id = id;
        _groupPortfolioId = groupPortfolioId;
        _groupPortfolioName = groupPortfolioName;
        _portfolioId = portfolioId;
        _portfolioName = portfolioName;
        _portfolioWalletId = portfolioWalletId;
        _moneyAsset = moneyAsset;
        _name = name;
        _comment = comment;
        _position = position;
        _count = count;
        _calendarStart = calendarStart;
        _calendarEnd = calendarEnd;
        _status = status;
        if (moneyAsset.get_wallet_id() != 0L)
            _typeMoneyLotAsset = EnumTypeMoneyLotAsset.LotWallet;
        else if (moneyAsset.get_credit_id() != 0L)
            _typeMoneyLotAsset = EnumTypeMoneyLotAsset.LotCredit;
        _lotAssetOperations = lotAssetOperations;
        _deletedLotAssetOperations = deletedLotAssetOperations;
        if (_deletedLotAssetOperations == null)
            _deletedLotAssetOperations = new ArrayList<>();
    }
    public MoneyLotAsset(Long id,
                         Long groupPortfolioId,
                         String groupPortfolioName,
                         Long portfolioId,
                         String portfolioName,
                         Long portfolioWalletId,
                         MoneyAsset moneyAsset,
                         String name,
                         String comment,
                         int position,
                         int count,
                         Calendar calendarStart,
                         Calendar calendarEnd,
                         EnumStatus status,
                         EnumTypeMoneyLotAsset typeMoneyLotAsset,
                         ArrayList<MoneyLotAssetOperation> lotAssetOperations,
                         ArrayList<MoneyLotAssetOperation> deletedLotAssetOperations)
    {
        _id = id;
        _groupPortfolioId = groupPortfolioId;
        _groupPortfolioName = groupPortfolioName;
        _portfolioId = portfolioId;
        _portfolioName = portfolioName;
        _portfolioWalletId = portfolioWalletId;
        _moneyAsset = moneyAsset;
        _name = name;
        _comment = comment;
        _position = position;
        _count = count;
        _status = status;
        _calendarStart = calendarStart;
        _calendarEnd = calendarEnd;
        _typeMoneyLotAsset = typeMoneyLotAsset;
        _lotAssetOperations = lotAssetOperations;
        _deletedLotAssetOperations = deletedLotAssetOperations;
        if (_deletedLotAssetOperations == null)
            _deletedLotAssetOperations = new ArrayList<>();
    }

    public MoneyLotAsset Clone()
    {
        return new MoneyLotAsset(_id,
                _groupPortfolioId,
                _groupPortfolioName,
                _portfolioId,
                _portfolioName,
                _portfolioWalletId,
                _moneyAsset,
                _name, _comment, _position, _count,
                (Calendar) _calendarStart.clone(),
                (Calendar) _calendarEnd.clone(),
                _status,
                _lotAssetOperations,
                _deletedLotAssetOperations);
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
//        _id = dbHelper.AddMoneyLotAsset(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        _id = dbHelper.AddMoneyLotAsset(this);
//        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _calendarEnd = Calendar.getInstance();
        _calendarEnd.set(2900, 11, 31);
        for (MoneyLotAssetOperation item: _lotAssetOperations)
            if (_moneyAsset.get_wallet_id() == 0 && item.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell))
            {
                _calendarEnd = item.get_calendar();
                break;
            }


        boolean b = dbHelper.UpdateMoneyLotAsset(this, context);
        if (b)
        {
            reNew();
            for (MoneyLotAssetOperation item: get_lotAssetOperations())
                item.reNew();

            return true;
        }

        return false;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyLotAsset(this);
    }
    //endregion

    //region Track
//    private void TrackCount(DBHelper dbHelper, Context context)
//    {
//        CustomTrack.TrackCountMoneyLotAsset(MoneyLotAssetDataSource.GetCount(dbHelper));
//    }
    //endregion
}
