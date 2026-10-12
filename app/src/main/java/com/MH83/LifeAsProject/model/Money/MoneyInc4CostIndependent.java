package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyInc4CostDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

public class MoneyInc4CostIndependent extends MoneyInc4Cost
{
    public MoneyInc4CostIndependent(Long id, Long pnl_id,
                                    EnumTypeCategory typeCategory,
                                    Long cost_id,
                                    Long costSubCategory_id, Long oldCostSubCategoryId,
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
                                    Long sumLong, Long oldSumLong)
    {
        super(id, pnl_id, typeCategory,
                cost_id, costSubCategory_id, oldCostSubCategoryId,
                costCategoryName,
                costCategoryIcon,
                costSubCategoryName,
                costSubCategoryIcon,
                inc_id, incSubCategory_id, oldIncSubCategory_id,
                incCategoryName,
                incCategoryIcon,
                incSubCategoryName,
                incSubCategoryIcon,
                dayInc, monthInc, yearInc,
                sumLong, oldSumLong);
    }

    public MoneyInc4CostIndependent Clone()
    {
        return new MoneyInc4CostIndependent(0L, get_pnl_id(),
                _typeCategory,
                _costCategoryId,
                get_cost_subCategory_id(), get_cost_subCategory_id(),
                _costCategoryName,
                _costCategoryIcon,
                get_costSubCategoryName(),
                get_costSubCategoryIcon(),
                _increaseCategoryId, get_inc_subCategory_id(),
                get_inc_subCategory_id(),
                _increaseCategoryName, _increaseCategoryIcon,
                _subCategoryName, _subCategoryIcon,
                getDay(), getMonth(), getYear(),
                _sumLong, _sumLong);
    }

    //region CanQuestions
    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
//            if (MoneyInc4CostDataSource.GetCount(context) >= moneyInc4CostFreeCount)
//            {
//                Toast.makeText(context,
//                        context.getString(R.string.ErrorTooManyInc4Cost), Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//            else
                return true;
        }
        else
            return false;
    }
    //endregion

    //region WorkWithDatabase
    @Override
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyInc4Cost(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyInc4Cost(this);
//        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyInc4Cost(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyInc4Cost(this);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyInc4Cost(MoneyInc4CostDataSource.GetCount(dbHelper));
    }
    //endregion
}
