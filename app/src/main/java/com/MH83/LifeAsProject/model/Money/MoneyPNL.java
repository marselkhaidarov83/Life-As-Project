package com.MH83.LifeAsProject.model.Money;

import android.content.Context;

import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Tagged;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoneyPNL extends MoneyTransaction
{
    private Long _oldIncreaseCategoryId;
    private Long _subCategoryId;
    private Long _oldSubCategoryId;
    private Long _oldCostCategoryId;
    protected final int _taskId;
    protected final String _comment;
    protected Long _creditId;
    protected Long _accumulationId;
    protected Boolean _calculated;
    protected List<MoneyInc4Cost> _inc4Cost;
    private List<MoneyInc4Cost> _deletedInc4Cost;
    protected List<Tagged> _tagged;
    private List<Tagged> _deletedTagged;
    private boolean _oldIsPlan;

    public Long get_creditId() { return _creditId; }
    public void set_creditId(Long id) { _creditId = id; }
    public Long get_accumulationId() { return _accumulationId; }
    public void set_accumulationId(Long id) { _accumulationId = id; }
    public Boolean get_calculated() { return _calculated; }
    public int get_taskId() { return _taskId; }
    public String get_comment() { return _comment; }

    public Long getOldIncreaseCategoryId() { return _oldIncreaseCategoryId; }
    public Long getOldCostCategoryId() { return _oldCostCategoryId; }
    public Long getSubCategoryId() { return _subCategoryId; }
    public Long getOldSubCategoryId() { return _oldSubCategoryId; }
    public void set_subCategoryId(Long subCategoryId) { _subCategoryId = subCategoryId; }
    public void set_IncreaseCategoryId(Long increaseCategoryId) { _increaseCategoryId = increaseCategoryId; }
    public String getSubCategoryName() { return _subCategoryName; }
    public void set_subCategoryName(String subCategoryName) { _subCategoryName = subCategoryName; }
    public boolean get_oldIsPlan() { return _oldIsPlan; }

    protected void reNew()
    {
        _oldSumLong = _sumLong;
        _oldIncreaseCategoryId = _increaseCategoryId;
        _oldCostCategoryId = _costCategoryId;
        _oldSubCategoryId = _subCategoryId;
        _oldIsPlan = get_isPlan();
        _oldPercentSumLong = _percentSumLong;
    }

    public List<MoneyInc4Cost> get_inc4Cost() { return _inc4Cost; }
    public List<MoneyInc4Cost> get_deletedInc4Cost() { return _deletedInc4Cost; }
    public void add_deletedInc4Cost(MoneyInc4Cost moneyInc4Cost)
    {
        if (moneyInc4Cost.get_id() != 0)
            _deletedInc4Cost.add(moneyInc4Cost);
    }
    public void clear_deletedInc4Cost() { _deletedInc4Cost.clear(); }

    public List<Tagged> get_Tagged() { return _tagged; }
    public List<Tagged> get_deletedTagged() { return _deletedTagged; }
    public void add_deletedTagged(Tagged tagged)
    {
        if (tagged.get_id() != 0)
            _deletedTagged.add(tagged);
    }
    public void clear_deletedTags() { _deletedTagged.clear(); }


    //region Constructors
    public MoneyPNL(Long id, EnumTypeCategory typeCategory,
                    Long increaseCategoryId, Long oldIncreaseCategoryId,
                    Long costCategoryId, Long oldCostCategoryId,
                    Long subCategoryId, Long oldSubCategoryId,
                    String increaseCategoryName, String costCategoryName,
                    String subCategoryName,
                    int day, int month, int year,
                    Long sumLong, Long oldSumLong,
                    int taskId, String comment,
                    Long increaseCategoryIcon, Long costCategoryIcon, Long subCategoryIcon,
                    boolean isPlan, boolean oldIsPlan,
                    Long creditId, Long accumulationId,
                    Boolean calculated,
                    Long percentSumLong, Long oldPercentSumLong,
                    List<MoneyInc4Cost> inc4Cost,
                    List<MoneyInc4Cost> deletedInc4Cost,
                    List<Tagged> tags,
                    List<Tagged> deletedtags)
    {
        super(id, day, month, year, typeCategory,
                increaseCategoryId, increaseCategoryName,
                costCategoryId, costCategoryName,
                subCategoryName,
                sumLong, oldSumLong,
                percentSumLong, oldPercentSumLong,
                increaseCategoryIcon, costCategoryIcon, subCategoryIcon,
                isPlan);

        _oldIncreaseCategoryId = oldIncreaseCategoryId;
        _oldCostCategoryId = oldCostCategoryId;
        _subCategoryId = subCategoryId;
        _oldSubCategoryId = oldSubCategoryId;

        _taskId = taskId;
        _comment = comment;
        _oldIsPlan = oldIsPlan;
        _creditId = creditId;
        _accumulationId = accumulationId;
        _calculated = calculated;

        _inc4Cost = inc4Cost;
        if (_inc4Cost == null)
            _inc4Cost = new ArrayList<>();

        _deletedInc4Cost = deletedInc4Cost;
        if (_deletedInc4Cost == null)
            _deletedInc4Cost = new ArrayList<>();

        _tagged = tags;
        if (_tagged == null)
            _tagged = new ArrayList<>();

        _deletedTagged = deletedtags;
        if (_deletedTagged == null)
            _deletedTagged = new ArrayList<>();

    }

    public MoneyPNL(Long id, EnumTypeCategory typeCategory, Long increaseCategoryId,
                    Long oldIncreaseCategoryId, Long costCategoryId, Long oldCostCategoryId,
                    Long subCategoryId, Long oldSubCategoryId,
                    String increaseCategoryName, String costCategoryName,
                    String subCategoryName,
                    Calendar calendar,
                    Long sumLong, Long oldSumLong,
                    int taskId, String comment,
                    Long increaseCategoryIcon, Long costCategoryIcon, Long subCategoryIcon,
                    boolean isPlan, boolean oldIsPlan,
                    Long creditId, Long accumulationId,
                    Boolean calculated,
                    Long percentSumLong, Long oldPercentSumLong,
                    List<MoneyInc4Cost> inc4Cost,
                    List<MoneyInc4Cost> deletedInc4Cost,
                    List<Tagged> tags,
                    List<Tagged> deletedtags)
    {
        super(id, calendar, typeCategory,
                increaseCategoryId, increaseCategoryName,
                costCategoryId, costCategoryName,
                subCategoryName,
                sumLong, oldSumLong,
                percentSumLong, oldPercentSumLong,
                increaseCategoryIcon, costCategoryIcon, subCategoryIcon,
                isPlan);

        _oldIncreaseCategoryId = oldIncreaseCategoryId;
        _oldCostCategoryId = oldCostCategoryId;

        if (subCategoryId == null)
            _subCategoryId = 0L;
        else
            _subCategoryId = subCategoryId;
        if (oldSubCategoryId == null)
            _oldSubCategoryId = 0L;
        else
            _oldSubCategoryId = oldSubCategoryId;

        _taskId = taskId;
        _comment = comment;
        _oldIsPlan = oldIsPlan;
        _creditId = creditId;
        _accumulationId = accumulationId;
        _calculated = calculated;

        _inc4Cost = inc4Cost;
        if (_inc4Cost == null)
            _inc4Cost = new ArrayList<>();

        _deletedInc4Cost = deletedInc4Cost;
        if (_deletedInc4Cost == null)
            _deletedInc4Cost = new ArrayList<>();

        _tagged = tags;
        if (_tagged == null)
            _tagged = new ArrayList<>();

        _deletedTagged = deletedtags;
        if (_deletedTagged == null)
            _deletedTagged = new ArrayList<>();
    }

    public MoneyPNL Clone()
    {
        return new MoneyPNL(-1L, _typeCategory, _increaseCategoryId,
                _oldIncreaseCategoryId, _costCategoryId, _oldCostCategoryId,
                _subCategoryId, _oldSubCategoryId,
                _increaseCategoryName, _costCategoryName,
                _subCategoryName, _calendar,
                _sumLong, _oldSumLong,
                _taskId, _comment,
                _increaseCategoryIcon, _costCategoryIcon, _subCategoryIcon,
                get_isPlan(), _oldIsPlan,
                _creditId, _accumulationId, _calculated,
                _percentSumLong, _oldPercentSumLong,
                _inc4Cost, _deletedInc4Cost,
                _tagged, _deletedTagged);
    }
    //endregion

    @Override
    public boolean canDelete(Context context)
    {
        return !_calculated;
    }

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

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyPNL(MoneyPNLDataSource.GetCount(dbHelper));
    }
    //endregion
}
