package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Tagged;

import java.util.Calendar;
import java.util.List;

public class MoneyPNLIndependent extends MoneyPNL
{
    //region Constructors
    public MoneyPNLIndependent(Long id, EnumTypeCategory typeCategory,
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
                               List<MoneyInc4Cost> inc4Cost, List<MoneyInc4Cost> deletedInc4Cost,
                               List<Tagged> tagged, List<Tagged> deletedTagged)
    {
        super(id, typeCategory, increaseCategoryId, oldIncreaseCategoryId, costCategoryId,
                oldCostCategoryId, subCategoryId, oldSubCategoryId, increaseCategoryName,
                costCategoryName, subCategoryName, day, month, year,
                sumLong, oldSumLong, taskId,
                comment, increaseCategoryIcon, costCategoryIcon, subCategoryIcon,
                isPlan, oldIsPlan, creditId, accumulationId, calculated,
                percentSumLong, oldPercentSumLong,
                inc4Cost, deletedInc4Cost,
                tagged, deletedTagged);
    }

    public MoneyPNLIndependent(Long id, EnumTypeCategory typeCategory, Long increaseCategoryId,
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
                               List<MoneyInc4Cost> inc4Cost, List<MoneyInc4Cost> deletedInc4Cost,
                               List<Tagged> tagged, List<Tagged> deletedTagged)
    {
        super(id, typeCategory, increaseCategoryId, oldIncreaseCategoryId, costCategoryId,
                oldCostCategoryId, subCategoryId, oldSubCategoryId, increaseCategoryName,
                costCategoryName, subCategoryName, calendar,
                sumLong, oldSumLong, taskId, comment,
                increaseCategoryIcon, costCategoryIcon, subCategoryIcon,
                isPlan, oldIsPlan, creditId, accumulationId, calculated,
                percentSumLong, oldPercentSumLong,
                inc4Cost, deletedInc4Cost,
                tagged, deletedTagged);
    }

    public MoneyPNLIndependent Clone()
    {
        return new MoneyPNLIndependent(-1L, _typeCategory, _increaseCategoryId, _increaseCategoryId,
                _costCategoryId, _costCategoryId, getSubCategoryId(), getSubCategoryId(),
                _increaseCategoryName, _costCategoryName, _subCategoryName, _calendar,
                _sumLong, _sumLong,
                _taskId, _comment, _increaseCategoryIcon, _costCategoryIcon, _subCategoryIcon,
                get_isPlan(), get_isPlan(),
                _creditId, _accumulationId, _calculated,
                _percentSumLong, _percentSumLong,
                _inc4Cost, null,
                _tagged, null);
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyPNL(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyPNL(this);
        TrackCount(dbHelper);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        boolean b = dbHelper.UpdateMoneyPNL(this);
        if (b)
        {
            clear_deletedInc4Cost();
            clear_deletedTags();
            reNew();
        }
        return b;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyPNL(this);
    }

    private void TrackCount(DBHelper dbHelper)
    {
        CustomTrack.TrackCountMoneyPNL(MoneyPNLDataSource.GetCount(dbHelper));
    }
    //endregion
}
