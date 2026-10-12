package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyInc4CostDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

public class MoneyInc4Cost extends MoneyTransaction
{
    //region Parameters
    private Long _pnl_id;
    private final Long _cost_subCategory_id;
    private final String _costSubCategoryName;
    private final Long _costSubCategoryIcon;
    private Long _inc_subCategory_id;
    private final Long _old_inc_subCategory_id;
    private final Long _old_cost_subCategory_id;
    //endregion

    public Long get_pnl_id() { return _pnl_id; }
    public void set_pnl_id(Long pnl_id) { _pnl_id = pnl_id; }
    public Long get_cost_subCategory_id() { return _cost_subCategory_id; }
    public Long get_inc_subCategory_id() { return _inc_subCategory_id; }
    public Long get_old_inc_subCategory_id() { return _old_inc_subCategory_id; }
    public Long get_old_cost_subCategory_id() { return _old_cost_subCategory_id; }
    public void set_inc_subCategoryId(Long subCategory_id) { _inc_subCategory_id = subCategory_id; }

    public String getCategoryName()
    {
        return _increaseCategoryName + " / " + _subCategoryName;
    }
    public String get_subCategoryName() { return _subCategoryName; }
    public void set_subCategoryName(String subCategoryName)
    {
        _subCategoryName = subCategoryName;
    }

    //region Constructors
    public MoneyInc4Cost(Long id,
                         Long pnl_id,
                         EnumTypeCategory typeCategory,
                         Long cost_id,
                         Long costSubCategory_id,
                         Long oldCostSubCategory_id,
                         String costCategoryName,
                         Long costCategoryIcon,
                         String costSubCategoryName,
                         Long costSubCategoryIcon,
                         Long inc_id,
                         Long incSubCategory_id,
                         Long oldIncSubCategory_id,
                         String incCategoryName,
                         Long incCategoryIcon,
                         String incSubCategoryName,
                         Long incSubCategoryIcon,
                         int dayInc, int monthInc, int yearInc,
                         Long sumLong,
                         Long oldSumLong)
    {
        super(id, dayInc, monthInc, yearInc,
                typeCategory,
                inc_id, incCategoryName,
                cost_id,
                costCategoryName,
                incSubCategoryName,
                sumLong, oldSumLong,
                0L, 0L,
                incCategoryIcon, costCategoryIcon, incSubCategoryIcon, false);

        _pnl_id = pnl_id;

        _cost_subCategory_id = costSubCategory_id;
        _old_cost_subCategory_id = oldCostSubCategory_id;
        _costSubCategoryName = costSubCategoryName;
        _costSubCategoryIcon = costSubCategoryIcon;

        _inc_subCategory_id = incSubCategory_id;
        _old_inc_subCategory_id = oldIncSubCategory_id;
    }
    //endregion

    //region GetParams
    @Override
    public String getCostCategoryName()
    {
        if (_typeCategory == EnumTypeCategory.Increase)
            return _increaseCategoryName + " / " + _subCategoryName;
        else if (_typeCategory == EnumTypeCategory.Cost)
            return "";
        else
            return _increaseCategoryName + " / " + _costSubCategoryName;
    }
    @Override
    public String getIncreaseCategoryName()
    {
        if (_typeCategory == EnumTypeCategory.Cost)
            return _costCategoryName + " / " + _subCategoryName;
        else if (_typeCategory == EnumTypeCategory.Increase)
            return "";
        else
            return _costCategoryName + " / " + _subCategoryName;
    }
    public String get_costSubCategoryName() { return _costSubCategoryName; }
    public Long get_costSubCategoryIcon() { return _costSubCategoryIcon; }
    //endregion

    //region CanQuestions
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyInc4Cost(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        _id = dbHelper.AddMoneyInc4Cost(this);
//        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.UpdateMoneyInc4Cost(this);
        return true;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.DeleteMoneyInc4Cost(_id);
        return true;
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyInc4Cost(MoneyInc4CostDataSource.GetCount(dbHelper));
    }
    //endregion
}
