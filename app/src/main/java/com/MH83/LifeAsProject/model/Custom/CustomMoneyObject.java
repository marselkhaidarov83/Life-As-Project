package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import com.MH83.LifeAsProject.data.DBHelper;

public abstract class CustomMoneyObject
{
    public static int tagFreeCount = 3;
    protected static int taggedFreeCount = 100;
    protected static int paramFreeCount = 10;
    protected static int planValueFreeCount = 10;
    protected static int valueFreeCount = 10;

    public static int moneyDreamFreeCount = 3;
    protected static int moneyTargetFreeCount = 3;
    protected static int moneyIncreaseFreeCount = 3;
    public static int moneyCategoryFreeCount = 10;
    protected static int moneyIncomingAndCostFreeCount = 10;
//    protected static int moneyPNLFreeCount = 300;
//    protected static int moneyInc4CostFreeCount = 0;
//    protected static int moneyInc4CostSumFreeCount = 25;
    protected static int moneyPlanFreeCount = 10;
    protected static int moneyCreditFreeCount = 1;
    protected static int moneyPortfolioFreeCount = 2;
    protected static int moneyTypeAssetFreeCount = 3;
    protected static int moneyAssetFreeCount = 3;
    protected static int moneyLotAssetFreeCount = 5;
    protected static int moneyLotAssetOperationFreeCount = 3;

    protected static int timeProjectFreeCount = 5;
    protected static int timePriorityFreeCount = 5;
    protected static int timeTaskFreeCount = 100;

    protected static int personAssistantFreeCount = 3;
    protected static int personCommFreeCount = 5;

    public abstract String getSValue();
    public abstract void set_id(Long id);
    public abstract boolean canDelete(Context context);
    public abstract void AddToDatabase(DBHelper dbHelper, Context context) throws Exception ;
    public abstract boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception ;
    public abstract boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception ;
}
