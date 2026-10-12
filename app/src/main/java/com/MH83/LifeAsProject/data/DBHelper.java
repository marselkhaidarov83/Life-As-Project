package com.MH83.LifeAsProject.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Custom.CCabinetDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumCurrency;
import com.MH83.LifeAsProject.interfac.enums.EnumDatabase;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.interfac.enums.EnumPlanValueType;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Custom.CCabinet;
import com.MH83.LifeAsProject.model.Custom.CDelObject;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.model.Interface.IGetCategoryList;
import com.MH83.LifeAsProject.model.Money.MoneyAccumulation;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyBoard;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyCreditPivotPoint;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.model.Money.MoneyDreamSettings;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyInc4CostIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyIncreaseSettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperationSettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyPlan;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolio;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolioSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Param;
import com.MH83.LifeAsProject.model.Money.MoneyTargetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAssetSettings;
import com.MH83.LifeAsProject.model.Person.PersonAssistant;
import com.MH83.LifeAsProject.model.Person.PersonComm;
import com.MH83.LifeAsProject.model.Custom.PlanValue;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksPriority;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;
import com.MH83.LifeAsProject.model.Custom.Value;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class DBHelper extends SQLiteOpenHelper
{
    //region Settings
    private static final int DATABASE_VERSION = 37;
    private static final int DATABASE_VERSION_LEARN = 1;
    private static final String DATABASE_NAME = "lifesProject";
    private static final String DATABASE_NAME_LEARN = "lifesProjectLearn";
    private CCabinet _cCabinet;
    public void set_cCabinet(CCabinet cCabinet) { _cCabinet = cCabinet; }
    //endregion

    //region Custom tables
    public static final String KEY_ALL_DATETIME_CREATE = "dateTimeCreate";
    public static final String KEY_ALL_DATETIME_UPDATE = "dateTimeUpdate";
    public static final String KEY_ALL_CABINET_ID = "cabinet_id";

    private static final String TABLE_CABINET = "t_cabinet";
    public static final String KEY_CABINET_ID = "_id";
    public static final String KEY_CABINET_NAME = "name";

    private static final String TABLE_DEL_OBJECT = "t_del_object";
    public static final String KEY_DEL_OBJECT_ID = "_id";
    public static final String KEY_DEL_OBJECT_CABINET_ID = "cabinet_id";
    public static final String KEY_DEL_OBJECT_NAME = "name";
    public static final String KEY_DEL_OBJECT_NUM = "num";
    //endregion

    //region Params for table
    private static final String TABLE_PARAM = "t_param";
    public static final String KEY_PARAM_ID = "_id";
    public static final String KEY_PARAM_CABINET_ID = "cabinet_id";
    public static final String KEY_PARAM_NAME = "name";
    public static final String KEY_PARAM_COMMENT = "comment";
    public static final String KEY_PARAM_MEASURE = "measure";

    private static final String TABLE_PLANVALUE = "t_planvauel";
    public static final String KEY_PLANVALUE_ID = "_id";
    public static final String KEY_PLANVALUE_CABINET_ID = "cabinet_id";
    public static final String KEY_PLANVALUE_TARGET_ID = "target_id";
    public static final String KEY_PLANVALUE_PARAM_ID = "param_id";
    public static final String KEY_PLANVALUE_TYPE = "type";
    public static final String KEY_PLANVALUE_COEFFICIENT = "coefficient";
    public static final String KEY_PLANVALUE_VALUE = "value";
    public static final String KEY_PLANVALUE_PV_SRC1 = "pv1src";
    public static final String KEY_PLANVALUE_PV_SRC2 = "pv2src";

    private static final String TABLE_VALUE = "t_value";
    public static final String KEY_VALUE_ID = "_id";
    public static final String KEY_VALUE_CABINET_ID = "cabinet_id";
    public static final String KEY_VALUE_TASK = "task_id";
    public static final String KEY_VALUE_PARAM = "param_id";
    public static final String KEY_VALUE_VALUE = "value";

    private static final String TABLE_TAG = "t_tag";
    public static final String KEY_TAG_ID = "_id";
    public static final String KEY_TAG_NAME = "name";
    public static final String KEY_TAG_COMMENT = "comment";
    public static final String KEY_TAG_TYPEDATE = "typeData";
    public static final String KEY_TAG_ARCHIVE = "archive";
    public static final String key_TAG_COUNTEXTERNAL = "countExternal";

    private static final String TABLE_TAGGED = "t_tagged";
    public static final String KEY_TAGGED_ID = "_id";
    public static final String KEY_TAGGED_TAGID = "tagId";
    public static final String KEY_TAGGED_EXTERNALID = "externalId";
    //endregion

    //region Params for money table
    private static final String TABLE_MONEY_DREAM = "t_money_dream";
    public static final String KEY_MONEY_DREAM_ID = "_id";
    public static final String key_MONEY_DREAM_TARGET_COUNT = "targetCount";
    public static final String KEY_MONEY_DREAM_POSITION = "position";
    public static final String KEY_MONEY_DREAM_NAME = "name";
    public static final String KEY_MONEY_DREAM_IMAGE_PATH = "imagePath";
    public static final String KEY_MONEY_DREAM_IMAGE_URL = "imageUrl";
    public static final String KEY_MONEY_DREAM_STATUS = "status";
    public static final String KEY_MONEY_DREAM_ARCHIVE = "archive";

    private static final String TABLE_MONEY_TARGET = "t_money_target";
    public static final String KEY_MONEY_TARGET_ID = "_id";
    public static final String KEY_MONEY_TARGET_DREAM_ID = "dreamId";
    public static final String KEY_MONEY_TARGET_HEAD_ID = "headId";
    public static final String KEY_MONEY_TARGET_MONTH = "month";
    public static final String KEY_MONEY_TARGET_YEAR = "year";
    public static final String KEY_MONEY_TARGET_NAME = "name";
    public static final String KEY_MONEY_TARGET_PRICE = "price";
    public static final String KEY_MONEY_TARGET_ACCUMULATION = "accumulation";
    public static final String KEY_MONEY_TARGET_CREDIT = "credit";
    public static final String KEY_MONEY_TARGET_IMAGE_PATH = "imagePath";
    public static final String KEY_MONEY_TARGET_IMAGE_URL = "imageUrl";
    public static final String KEY_MONEY_TARGET_STATUS = "status";
    public static final String KEY_MONEY_TARGET_TYPE = "type";
    public static final String key_MONEY_TARGET_COUNT = "targetCount";
    public static final String key_MONEY_TARGET_CATEGORY_ID = "categoryId";
    public static final String key_MONEY_TARGET_TASK_COUNT1 = "taskCount1";
    public static final String key_MONEY_TARGET_TASK_COUNT2 = "taskCount2";

    private static final String TABLE_MONEY_INCREASE = "t_money_increase";
    public static final String KEY_MONEY_INCREASE_ID = "_id";
    public static final String KEY_MONEY_INCREASE_DAY = "day";
    public static final String KEY_MONEY_INCREASE_MONTH = "month";
    public static final String KEY_MONEY_INCREASE_YEAR = "year";
    public static final String KEY_MONEY_INCREASE_VALUE = "increase";
    public static final String KEY_MONEY_INCREASE_ARCHIVE = "archive";
    public static final String key_MONEY_INCREASE_INCOMING_COUNT = "incomingsCount";

    private static final String TABLE_MONEY_CATEGORY = "t_money_category";
    public static final String KEY_MONEY_CATEGORY_ID = "_id";
    public static final String KEY_MONEY_CATEGORY_PARENT_ID = "parentId";
    public static final String KEY_MONEY_CATEGORY_TARGET_ID = "targetId";
    public static final String KEY_MONEY_CATEGORY_TYPE_CATEGORY = "typeCategory";
    public static final String KEY_MONEY_CATEGORY_POSITION = "position";
    public static final String KEY_MONEY_CATEGORY_NAME = "name";
    @Deprecated
    public static final String KEY_MONEY_CATEGORY_SUM = "sum";
    public static final String KEY_MONEY_CATEGORY_SUM_L = "sumL";
    public static final String KEY_MONEY_CATEGORY_TYPE = "type";
    @Deprecated
    public static final String KEY_MONEY_CATEGORY_STATUS = "status";
    public static final String KEY_MONEY_CATEGORY_SUMTOBALANCE = "sumtobalance";
    public static final String KEY_MONEY_CATEGORY_CREDIT_ID = "creditID";
    public static final String KEY_MONEY_CATEGORY_ICON = "icon";
    public static final String KEY_MONEY_CATEGORY_ARCHIVE = "archive";
    public static final String key_MONEY_CATEGORY_COUNT_CHILD = "countChild";
    public static final String key_MONEY_CATEGORY_COUNT_INCOMING = "countIncoming";
    public static final String key_MONEY_CATEGORY_COUNT_PNL_INC = "countPNLinc";
    public static final String key_MONEY_CATEGORY_COUNT_PNL_COST = "countPNLcost";
    public static final String key_MONEY_CATEGORY_COUNT_PNL_SUB = "countPNLsub";

    private static final String TABLE_MONEY_CATEGORY_HISTORY = "t_money_category_history";
    public static final String KEY_MONEY_CATEGORY_HISTORY_ID = "_id";
    public static final String KEY_MONEY_CATEGORY_HISTORY_SUM_L = "sumL";
    public static final String KEY_MONEY_CATEGORY_HISTORY_DAY = "day";
    public static final String KEY_MONEY_CATEGORY_HISTORY_MONTH = "month";
    public static final String KEY_MONEY_CATEGORY_HISTORY_YEAR = "year";
    
    private static final String TABLE_MONEY_INCOMING = "t_money_plan";
    public static final String KEY_MONEY_INCOMING_ID = "_id";
    public static final String KEY_MONEY_INCOMING_CATEGORY_ID = "categoryId";
    public static final String KEY_MONEY_INCOMING_INCREASE_ID = "increaseId";
    public static final String KEY_MONEY_INCOMING_SUM = "summ";
    public static final String KEY_MONEY_INCOMING_SUM_L = "sumL";
    public static final String KEY_MONEY_INCOMING_PERCENT = "percent";
    public static final String KEY_MONEY_INCOMING_TYPE = "type";
    public static final String KEY_MONEY_INCOMING_EXTERNAL_ID = "externalId";

    private static final String TABLE_MONEY_PERIODPLAN = "t_money_periodPlan";
    public static final String KEY_MONEY_PERIODPLAN_ID = "_id";
    public static final String KEY_MONEY_PERIODPLAN_CATEGORY_ID = "categoryId";
    public static final String KEY_MONEY_PERIODPLAN_MONTH = "monthI";
    public static final String KEY_MONEY_PERIODPLAN_YEAR = "yearI";
    public static final String KEY_MONEY_PERIODPLAN_SUM_L = "sumL";
    public static final String KEY_MONEY_PERIODPLAN_TYPE = "type";
    public static final String KEY_MONEY_PERIODPLAN_EXTERNAL_ID = "externalId";

    private static final String TABLE_MONEY_PNL = "t_money_pnl";
    public static final String KEY_MONEY_PNL_ID = "_id";
    public static final String KEY_MONEY_PNL_INC_CATEGORY_ID = "incCategoryId";
    public static final String key_MONEY_PNL_INC_CATEGORY_NAME = "incCategoryName";
    public static final String key_MONEY_PNL_INC_CATEGORY_ICON = "incCategoryIcon";
    public static final String key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY = "incCategoryTypeCategory";
    public static final String KEY_MONEY_PNL_COST_CATEGORY_ID = "costCategoryId";
    public static final String key_MONEY_PNL_COST_CATEGORY_NAME = "costCategoryName";
    public static final String key_MONEY_PNL_COST_CATEGORY_ICON = "costCategoryIcon";
    public static final String key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY = "costCategoryTypeCategory";
    public static final String KEY_MONEY_PNL_SUB_CATEGORY_ID = "subCategoryId";
    public static final String key_MONEY_PNL_SUB_CATEGORY_NAME = "subCategoryName";
    public static final String key_MONEY_PNL_SUB_CATEGORY_ICON = "subCategoryIcon";
    public static final String KEY_MONEY_PNL_DATE_INT = "dateI";
    public static final String KEY_MONEY_PNL_MONTH_INT = "monthI";
    public static final String KEY_MONEY_PNL_YEAR_INT = "yearI";
    @Deprecated
    public static final String KEY_MONEY_PNL_SUM = "summ";
    public static final String KEY_MONEY_PNL_SUM_L = "sumL";
    public static final String key_MONEY_PNL_SUMSUM_L = "sumsumL";
    public static final String KEY_MONEY_PNL_TASK_ID = "task_id";
    public static final String KEY_MONEY_PNL_COMMENT = "comment";
    public static final String KEY_MONEY_PNL_ISPLAN = "isPlan";
    public static final String KEY_MONEY_PNL_CREDIT_ID = "creditID";
    public static final String KEY_MONEY_PNL_ACCUMULATION_ID = "accumulationID";
    public static final String KEY_MONEY_PNL_CALCULATED = "calculated";
    public static final String KEY_MONEY_PNL_PERCENTSUM = "percentSum";
    public static final String KEY_MONEY_PNL_PERCENTSUM_L = "percentSumL";
    public static final String key_MONEY_PNL_PERCENTSUMSUM_L = "percentSumsumL";

    private static final String TABLE_MONEY_INC4COST = "t_money_inc4cost";
    public static final String KEY_MONEY_INC4COST_ID = "_id";
    public static final String KEY_MONEY_INC4COST_PNL_ID = "pnl_id";
    public static final String KEY_MONEY_INC4COST_COST_ID = "cost_id";
    public static final String key_MONEY_INC4COST_COST_CATEGORY_NAME = "costCategoryName";
    public static final String key_MONEY_INC4COST_COST_CATEGORY_ICON = "costCategoryIcon";
    public static final String KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID = "costSubCategoryId";
    public static final String key_MONEY_INC4COST_COST_SUB_CATEGORY_NAME = "costSubCategoryName";
    public static final String key_MONEY_INC4COST_COST_SUB_CATEGORY_ICON = "costSubCategoryIcon";
    public static final String KEY_MONEY_INC4COST_INC_ID = "inc_id";
    public static final String key_MONEY_INC4COST_INC_CATEGORY_NAME = "incCategoryName";
    public static final String key_MONEY_INC4COST_INC_CATEGORY_ICON = "incCategoryIcon";
    public static final String KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID = "subCategoryId";
    public static final String key_MONEY_INC4COST_INC_SUB_CATEGORY_NAME = "incSubCategoryName";
    public static final String key_MONEY_INC4COST_INC_SUB_CATEGORY_ICON = "incSubCategoryIcon";
    public static final String KEY_MONEY_INC4COST_INC_DATE_INT = "dateIncI";
    public static final String KEY_MONEY_INC4COST_INC_MONTH_INT = "monthIncI";
    public static final String KEY_MONEY_INC4COST_INC_YEAR_INT = "yearIncI";
    public static final String KEY_MONEY_INC4COST_SUM = "summ";
    public static final String KEY_MONEY_INC4COST_SUM_L = "sumL";

    private static final String TABLE_MONEY_CREDIT = "t_money_credit";
    public static final String KEY_MONEY_CREDIT_ID = "_id";
    public static final String KEY_MONEY_CREDIT_TARGET_ID = "target_id";
    public static final String key_MONEY_CREDIT_TARGET_NAME = "targetName";
    public static final String KEY_MONEY_CREDIT_NAME = "name";
    public static final String KEY_MONEY_CREDIT_STARTSUMM = "startSumm";
    public static final String KEY_MONEY_CREDIT_STARTSUM_L = "startSumL";
    public static final String KEY_MONEY_CREDIT_SUM = "summ";
    public static final String KEY_MONEY_CREDIT_SUM_L = "sumL";
    public static final String KEY_MONEY_CREDIT_PERCENT = "percent";
    public static final String KEY_MONEY_CREDIT_DATESTART_INT = "dateStartI";
    public static final String KEY_MONEY_CREDIT_MONTHSTART_INT = "monthStartI";
    public static final String KEY_MONEY_CREDIT_YEARSTART_INT = "yearStartI";
    public static final String KEY_MONEY_CREDIT_LOANTERM = "loanTerm";
    public static final String KEY_MONEY_CREDIT_TYPE = "type";
    public static final String KEY_MONEY_CREDIT_STATUS = "status";
    public static final String KEY_MONEY_CREDIT_WALLET = "wallet";
    public static final String KEY_MONEY_CREDIT_INC4COST_CATEGORY = "categoryId";
    public static final String KEY_MONEY_CREDIT_INC4COST_SUBCATEGORY = "subCategoryId";

    private static final String TABLE_MONEY_CREDIT_PIVOT_POINT = "t_money_credit_pivot_point";
    public static final String KEY_MONEY_CREDIT_PIVOT_POINT_ID = "_id";
    public static final String KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID = "credit_id";
    public static final String KEY_MONEY_CREDIT_PIVOT_POINT_STARTSUM_L = "startSumL";
    public static final String KEY_MONEY_CREDIT_PIVOT_POINT_PERCENT = "percent";
    public static final String KEY_MONEY_CREDIT_PIVOT_POINT_LOANTERM = "loanTerm";

    private static final String TABLE_MONEY_ACCUMULATION = "t_money_accumulation";
    public static final String KEY_MONEY_ACCUMULATION_ID = "_id";
    public static final String key_MONEY_ACCUMULATION_NAME = "accumulationName";
    public static final String KEY_MONEY_ACCUMULATION_TARGET_ID = "target_id";
    public static final String KEY_MONEY_ACCUMULATION_TYPE = "type";
    public static final String KEY_MONEY_ACCUMULATION_PROGRESSION_COEF = "progressionCoefficent";
    public static final String KEY_MONEY_ACCUMULATION_MONTH_START = "monthStart";
    public static final String KEY_MONEY_ACCUMULATION_YEAR_START = "yearStart";
    public static final String KEY_MONEY_ACCUMULATION_MONTH_END = "monthEnd";
    public static final String KEY_MONEY_ACCUMULATION_YEAR_END = "yearEnd";
    public static final String KEY_MONEY_ACCUMULATION_START_SUM = "startSum";

    private static final String TABLE_MONEY_PORTFOLIO = "t_money_portfolio";
    public static final String KEY_MONEY_PORTFOLIO_ID = "_id";
    public static final String KEY_MONEY_PORTFOLIO_PARENT_ID = "parent_id";
    public static final String KEY_MONEY_PORTFOLIO_NAME = "name";
    public static final String KEY_MONEY_PORTFOLIO_COMMENT = "comment";
    public static final String KEY_MONEY_PORTFOLIO_POSITION = "position";
    public static final String KEY_MONEY_PORTFOLIO_STATUS = "status";
    public static final String KEY_MONEY_PORTFOLIO_WALLET = "wallet";
    public static final String KEY_MONEY_PORTFOLIO_SUBCATEGORY = "sub_category";
    public static final String key_MONEY_PORTFOLIO_LOTCOUNT = "_lot_count";

    private static final String TABLE_MONEY_TYPEASSET = "t_money_typeasset";
    public static final String KEY_MONEY_TYPEASSET_ID = "_id";
    public static final String KEY_MONEY_TYPEASSET_PARENT_ID = "parent_id";
    public static final String KEY_MONEY_TYPEASSET_NAME = "name";
    public static final String KEY_MONEY_TYPEASSET_COMMENT = "comment";
    public static final String KEY_MONEY_TYPEASSET_POSITION = "position";
    public static final String KEY_MONEY_TYPEASSET_STATUS = "status";
    public static final String key_MONEY_TYPEASSET_COUNT_ASSET = "_countAsset";

    private static final String TABLE_MONEY_ASSET = "t_money_asset";
    public static final String KEY_MONEY_ASSET_ID = "_id";
    public static final String KEY_MONEY_ASSET_WALLET_ID = "wallet_id";
    public static final String KEY_MONEY_ASSET_CREDIT_ID = "credit_id";
    public static final String KEY_MONEY_ASSET_NAME = "name";
    public static final String KEY_MONEY_ASSET_TICKER = "ticker";
    public static final String KEY_MONEY_ASSET_COMMENT = "comment";
    public static final String KEY_MONEY_ASSET_POSITION = "position";
    public static final String KEY_MONEY_ASSET_STATUS = "status";
    public static final String KEY_MONEY_ASSET_TYPE = "type";
    public static final String key_MONEY_ASSET_TYPE_NAME = "_typeName";
    public static final String KEY_MONEY_ASSET_KIND = "kind";
    public static final String KEY_MONEY_ASSET_CURRENCY = "currency";
    public static final String key_MONEY_ASSET_LOTCOUNT = "_lotCount";

    private static final String TABLE_MONEY_LOT_ASSET = "t_money_lot_asset";
    public static final String KEY_MONEY_LOT_ASSET_ID = "_id";
    public static final String KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID = "group_portfolio_id";
    public static final String key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME = "_group_portfolio_name";
    public static final String KEY_MONEY_LOT_ASSET_PORTFOLIO_ID = "portfolio_id";
    public static final String key_MONEY_LOT_ASSET_PORTFOLIO_NAME = "_portfolio_name";
    public static final String key_MONEY_LOT_ASSET_PORTFOLIO_WALLET = "_portfolio_wallet";
    public static final String key_MONEY_LOT_ASSET_WALLET_ID = "_walletId";
    public static final String KEY_MONEY_LOT_ASSET_ASSET_ID = "asset_id";
    public static final String KEY_MONEY_LOT_ASSET_POSITION = "position";
    public static final String KEY_MONEY_LOT_ASSET_STATUS = "status";
    public static final String KEY_MONEY_LOT_ASSET_COUNT = "count";
    public static final String KEY_MONEY_LOT_ASSET_NAME = "name";
    public static final String KEY_MONEY_LOT_ASSET_COMMENT = "comment";
    public static final String KEY_MONEY_LOT_ASSET_DATE_START = "date_start";
    public static final String KEY_MONEY_LOT_ASSET_MONTH_START = "month_start";
    public static final String KEY_MONEY_LOT_ASSET_YEAR_START = "year_start";
    public static final String KEY_MONEY_LOT_ASSET_DATE_END = "date_end";
    public static final String KEY_MONEY_LOT_ASSET_MONTH_END = "month_end";
    public static final String KEY_MONEY_LOT_ASSET_YEAR_END = "year_end";

    private static final String TABLE_MONEY_LOT_ASSET_OPERATION = "t_money_lot_asset_operation";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_ID = "_id";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_LOT_ID = "lot_id";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_OPERATION = "operation";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_CATEGORY = "category";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_SUBCATEGORY = "subCategory";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_YEAR = "year";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_MONTH = "month";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_DATE = "date";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_COUNT = "count";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_PRICE = "price";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_COMISSION = "comission";
    public static final String KEY_MONEY_LOT_ASSET_OPERATION_ACCUMULATED_PRICE = "accumulated_price";

    private static final String TABLE_MONEY_BOARD = "t_money_board";
    public static final String KEY_MONEY_BOARD_ID = "_id";
    public static final String KEY_MONEY_BOARD_NAME = "name";
    public static final String KEY_MONEY_BOARD_POSITION = "position";
    public static final String KEY_MONEY_BOARD_VISIBLE = "visible";
    //endregion

    //region Params for task table
    private static final String TABLE_TIME_PROJECT = "t_time_project";
    public static final String KEY_TIME_PROJECT_ID = "_id";
    public static final String KEY_TIME_PROJECT_PARENT_ID = "parent_id";
    public static final String KEY_TIME_PROJECT_TARGET_ID = "target_id";
    public static final String KEY_TIME_PROJECT_NAME = "name";
    public static final String KEY_TIME_PROJECT_COMMENT = "comment";
    public static final String KEY_TIME_PROJECT_POSITION = "position";
    public static final String KEY_TIME_PROJECT_STATUS = "status";
    public static final String key_TIME_PROJECT_PROJECT_COUNT = "_countProject";
    public static final String key_TIME_PROJECT_TASK_COUNT_HIGH = "_countTaskHigh";
    public static final String key_TIME_PROJECT_TASK_COUNT_MIDDLE = "_countTaskMiddle";
    public static final String key_TIME_PROJECT_TASK_COUNT_LOW = "_countTaskLow";
    public static final String key_TIME_PROJECT_TASK_COUNT_NONE = "_countTaskNone";

    private static final String TABLE_TIME_PRIORITY = "t_time_priority";
    public static final String KEY_TIME_PRIORITY_ID = "_id";
    public static final String KEY_TIME_PRIORITY_NAME = "name";
    public static final String KEY_TIME_PRIORITY_COMMENT = "comment";

    private static final String TABLE_TIME_TASK = "t_time_task";
    public static final String KEY_TIME_TASK_ID = "_id";
    public static final String KEY_TIME_TASK_TYPE = "type";
    public static final String KEY_TIME_TASK_PROJECT_ID = "project_id";
    public static final String key_TIME_TASK_PROJECT_NAME = "projectName";
    public static final String KEY_TIME_TASK_PARENT_ID = "parent_id";
    public static final String KEY_TIME_TASK_OCCURED_ID = "occured_id";
    public static final String KEY_TIME_TASK_NAME = "name";
    public static final String KEY_TIME_TASK_COMMENT = "comment";
    public static final String KEY_TIME_TASK_PRIORITY = "priority";
    public static final String KEY_TIME_TASK_DATE_INT_PLAN = "dateIPlan";
    public static final String KEY_TIME_TASK_MONTH_INT_PLAN = "monthIPlan";
    public static final String KEY_TIME_TASK_YEAR_INT_PLAN = "yearIPlan";
    public static final String KEY_TIME_TASK_HOUR_INT_PLAN = "hourIPlan";
    public static final String KEY_TIME_TASK_MINUTE_INT_PLAN = "minuteIPlan";
    public static final String KEY_TIME_TASK_DATE_INT_FACT = "dateIFact";
    public static final String KEY_TIME_TASK_MONTH_INT_FACT = "monthIFact";
    public static final String KEY_TIME_TASK_YEAR_INT_FACT = "yearIFact";
    public static final String KEY_TIME_TASK_HOUR_INT_FACT = "hourIFact";
    public static final String KEY_TIME_TASK_MINUTE_INT_FACT = "minuteIFact";
    public static final String KEY_TIME_TASK_REPEAT = "repeat";
    public static final String KEY_TIME_TASK_REPEAT_TYPE = "repeat_type";
    public static final String KEY_TIME_TASK_REPEAT_PER = "repeat_per";
    public static final String KEY_TIME_TASK_REPEAT_SUBTASKS = "repeat_subTasks";
    public static final String KEY_TIME_TASK_CONFIRMED = "confirmed";
    public static final String KEY_TIME_TASK_STATUS = "status";
    //endregion

    //region Params for assistant table
    private static final String TABLE_PERSON_ASSISTANT = "t_person_assistant";
    public static final String KEY_PERSON_ASSISTANT_ID = "_id";
    public static final String KEY_PERSON_ASSISTANT_NAME = "name";
    public static final String KEY_PERSON_ASSISTANT_SURNAME = "surname";
    public static final String KEY_PERSON_ASSISTANT_MIDDLENAME = "middlename";

    private static final String TABLE_PERSON_COMM = "t_person_communication";
    public static final String KEY_PERSON_COMM_ID = "_id";
    public static final String KEY_PERSON_COMM_ASSIST_ID = "assist_id";
    public static final String KEY_PERSON_COMM_NAME = "name";
    public static final String KEY_PERSON_COMM_COMM = "comm";
    //endregion

    //region Base Methods
    public static String getDbName(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_enumDatabase().equals(EnumDatabase.Work))
            return DATABASE_NAME;
        else
            return DATABASE_NAME_LEARN;
    }

    public static DBHelper GetDBHelper(Context context) throws Exception
    {
        DBHelper dbHelper;
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_enumDatabase().equals(EnumDatabase.Work))
            dbHelper = new DBHelper(context, DATABASE_NAME, DATABASE_VERSION);
        else
            dbHelper = new DBHelper(context, DATABASE_NAME_LEARN, DATABASE_VERSION_LEARN);

        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        if (globalVariable.get_cCabinet() == null)
            globalVariable.set_cCabinet(CCabinetDataSource.GetFirstCCabinet(dbHelper, context));

        if (globalVariable.get_cCabinet() == null)
        {
            CreateStartData(dbHelper, context, globalVariable);
            globalVariable.SaveSharedPreferences();
        }

        dbHelper.set_cCabinet(globalVariable.get_cCabinet());

        return dbHelper;
    }

    private static void CreateStartData(DBHelper dbHelper, Context context, GlobalClass globalVariable) throws Exception
    {
        //region Cabinet
        CCabinet cCabinet = new CCabinet(1L, "Личный");
        cCabinet.AddToDatabase(dbHelper, context);
        globalVariable.set_cCabinet(cCabinet);
        dbHelper.set_cCabinet(globalVariable.get_cCabinet());
        //endregion

        //region Calendar
        Calendar calendarPrev = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        Calendar calendar5Year = Calendar.getInstance();

        calendarPrev.add(Calendar.MONTH, -1);
        calendarPrev.set(Calendar.DATE, 1);
        calendar2.add(Calendar.MONTH, 1);
        calendar3.add(Calendar.MONTH, 2);
        calendar5Year.add(Calendar.MONTH, 59);
        calendar5Year.set(Calendar.DATE, 1);

        String year0 = String.valueOf(calendar1.get(Calendar.YEAR));
        String year1 = String.valueOf(calendar1.get(Calendar.YEAR) + 1);
        String year2 = String.valueOf(calendar1.get(Calendar.YEAR) + 2);
        String year3 = String.valueOf(calendar1.get(Calendar.YEAR) + 3);
        String year4 = String.valueOf(calendar1.get(Calendar.YEAR) + 4);
        String year7 = String.valueOf(calendar1.get(Calendar.YEAR) + 7);
        //endregion

        //region Param
        Param param = new Param(0L, "Количество подписок",
                "Количество подписок в приложение", "шт");
        param.AddToDatabaseWOTrack(dbHelper);

        Param param2 = new Param(0L, "Сумма продаж",
                "Сумма пришедших оплат за подписки", "руб");
        param2.AddToDatabaseWOTrack(dbHelper);

        Param param3 = new Param(0L, "Комиссия магазина",
                "Комиссия за предоставление площадки", "руб");
        param3.AddToDatabaseWOTrack(dbHelper);

        Param param4 = new Param(0L, "Количество переходов",
                "Количество переходов по рекламе", "шт");
        param4.AddToDatabaseWOTrack(dbHelper);

        Param param5 = new Param(0L, "Стоимость рекламы",
                "Сумма денег по рекламе за переходы", "руб");
        param5.AddToDatabaseWOTrack(dbHelper);

        Param param6 = new Param(0L, "Стоимость маркетолога",
                "Сумма денег за работу маркетолога", "руб");
        param6.AddToDatabaseWOTrack(dbHelper);

        Param param7 = new Param(0L, "НЗ",
                "Сумма денег НЗ", "руб");
        param7.AddToDatabaseWOTrack(dbHelper);

        Param param8 = new Param(0L, "Чистая прибыль",
                "Чистая прибыль проекта", "руб");
        param8.AddToDatabaseWOTrack(dbHelper);

        Param param9 = new Param(0L, "Остаток после магазина",
                "Остаток после магазина", "руб");
        param9.AddToDatabaseWOTrack(dbHelper);

        Param param10 = new Param(0L, "Промежуточный итог 1",
                "Промежуточный итог 1", "руб");
        param10.AddToDatabaseWOTrack(dbHelper);

        Param param11 = new Param(0L, "Промежуточный итог 2",
                "Промежуточный итог 1", "руб");
        param11.AddToDatabaseWOTrack(dbHelper);

        Param param12 = new Param(0L, "Рентабельность",
                "Рентабельность", "");
        param12.AddToDatabaseWOTrack(dbHelper);

        Param param13 = new Param(0L, "Рентабельность %",
                "Рентабельность %", "%");
        param13.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region PlanValue
        PlanValue planValue = new PlanValue(1L, 1L, 1L,
                EnumPlanValueType.Value, 0d ,102d, 0L, 0L,
                "Количество подписок", "шт");
        planValue.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue2 = new PlanValue(2L, 1L, 2L,
                EnumPlanValueType.Koef, 1900d ,193800d, 1L, 0L,
                "Сумма продаж", "руб");
        planValue2.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue3 = new PlanValue(3L, 1L, 3L,
                EnumPlanValueType.Koef, 0.15d ,29070d, 2L, 0L,
                "Комиссия магазина", "руб");
        planValue3.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue4 = new PlanValue(4L, 1L, 9L,
                EnumPlanValueType.Koef, 0.85d ,164730d, 2L, 0L,
                "Остаток после магазина", "руб");
        planValue4.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue5 = new PlanValue(5L, 1L, 4L,
                EnumPlanValueType.InverseKoef, 0.2d ,510d, 1L, 0L,
                "Количество переходов", "шт");
        planValue5.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue6 = new PlanValue(6L, 1L, 5L,
                EnumPlanValueType.Koef, 50d ,25500d, 5L, 0L,
                "Стоимость рекламы", "руб");
        planValue6.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue7 = new PlanValue(7L, 1L, 6L,
                EnumPlanValueType.Koef, 0.1d ,19380d, 2L, 0L,
                "Стоимость маркетолога", "руб");
        planValue7.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue8 = new PlanValue(8L, 1L, 7L,
                EnumPlanValueType.Koef, 0.1d ,19380d, 2L, 0L,
                "НЗ", "руб");
        planValue8.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue9 = new PlanValue(9L, 1L, 10L,
                EnumPlanValueType.Diff, 0d ,139230d, 4L, 6L,
                "Промежуточный итог 1", "руб");
        planValue9.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue10 = new PlanValue(10L, 1L, 11L,
                EnumPlanValueType.Diff, 0d ,119850d, 9L, 7L,
                "Промежуточный итог 2", "руб");
        planValue10.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue11 = new PlanValue(11L, 1L, 8L,
                EnumPlanValueType.Diff, 0d ,100470d, 10L, 8L,
                "Чистая прибыль", "руб");
        planValue11.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue12 = new PlanValue(12L, 1L, 12L,
                EnumPlanValueType.Dividing, 0d ,0.5184d, 11L, 2L,
                "Рентабельность", "");
        planValue12.AddToDatabaseWOTrack(dbHelper);

        PlanValue planValue13 = new PlanValue(13L, 1L, 13L,
                EnumPlanValueType.Koef, 100d ,51.84d, 12L, 0L,
                "Рентабельность %", "%");
        planValue13.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyDream
        MoneyDream moneyDream = new MoneyDream(0L, 1, "Большая квартира", "",
                "", false, 0, EnumStatus.Blank, false);
        moneyDream.AddToDatabaseWOTrack(dbHelper);

        Bitmap viewBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_dream001);

        File internalStorage = context.getDir(dbHelper.getDatabaseName() + "DreamPictures", Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, moneyDream.get_id() + ".jpeg");
        String picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyDream.set_imagePath(picturePath);
            moneyDream.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyDream moneyDream2 = new MoneyDream(0L, 2, "Роскошный автомобиль", "",
                "", false, 0, EnumStatus.Blank, false);
        moneyDream2.AddToDatabaseWOTrack(dbHelper);

        Bitmap viewBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_dream002);

        File filePath2 = new File(internalStorage, moneyDream2.get_id() + ".jpeg");
        String picturePath2 = filePath2.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath2))
        {
            viewBitmap2.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyDream2.set_imagePath(picturePath2);
            moneyDream2.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyDream moneyDream3 = new MoneyDream(0L, 3, "Путешествия", "",
                "", false, 0, EnumStatus.Blank, false);
        moneyDream3.AddToDatabaseWOTrack(dbHelper);

        Bitmap viewBitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_dream003);

        File filePath3 = new File(internalStorage, moneyDream3.get_id() + ".jpeg");
        String picturePath3 = filePath3.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath3))
        {
            viewBitmap3.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyDream3.set_imagePath(picturePath3);
            moneyDream3.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyDream moneyDream4 = new MoneyDream(0L, 4, "Бизнес", "",
                "", false, 0, EnumStatus.Blank, false);
        moneyDream4.AddToDatabaseWOTrack(dbHelper);

        Bitmap viewBitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_dream004);

        File filePath4 = new File(internalStorage, moneyDream4.get_id() + ".jpeg");
        String picturePath4 = filePath4.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath4))
        {
            viewBitmap4.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyDream4.set_imagePath(picturePath4);
            moneyDream4.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyDream moneyDream5 = new MoneyDream(0L, 5, "Загородный дом", "",
                "", false, 0, EnumStatus.Blank, false);
        moneyDream5.AddToDatabaseWOTrack(dbHelper);

        Bitmap viewBitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_dream005);

        File filePath5 = new File(internalStorage, moneyDream5.get_id() + ".jpeg");
        String picturePath5 = filePath5.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath5))
        {
            viewBitmap5.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyDream5.set_imagePath(picturePath5);
            moneyDream5.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }
        //endregion

        //region MoneyTarget
        MoneyTarget moneyTarget11 = new MoneyTarget(0L, "01", year1,
                "1-комнатная квартира", 3000000, 500000, 2500000,
                "", "",
                moneyDream.get_id(), EnumStatus.Started,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget11.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target011);

        internalStorage = context.getDir(dbHelper.getDatabaseName() + "TargetPictures", Context.MODE_PRIVATE);
        filePath = new File(internalStorage, moneyTarget11.get_id() + ".jpeg");
        picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget11.set_imagePath(picturePath);
            moneyTarget11.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget12 = new MoneyTarget(0L, "01", year4,
                "3-комнатная квартира", 7000000, 1500000, 5500000,
                "", "", moneyDream.get_id(), EnumStatus.Blank,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget12.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target012);

        filePath2 = new File(internalStorage, moneyTarget12.get_id() + ".jpeg");
        picturePath2 = filePath2.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath2))
        {
            viewBitmap2.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget12.set_imagePath(picturePath2);
            moneyTarget12.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget13 = new MoneyTarget(0L, "01", year7,
                "5-комнатная квартира", 15000000, 3000000, 12000000,
                "", "", moneyDream.get_id(), EnumStatus.Blank,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget13.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap3 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target013);

        filePath3 = new File(internalStorage, moneyTarget13.get_id() + ".jpeg");
        picturePath3 = filePath3.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath3))
        {
            viewBitmap3.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget13.set_imagePath(picturePath3);
            moneyTarget13.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget21 = new MoneyTarget(0L, "01", year1,
                "Toyota RAV4", 4000000, 800000, 3200000,
                "", "", moneyDream2.get_id(), EnumStatus.Started,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget21.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target021);

        filePath = new File(internalStorage, moneyTarget21.get_id() + ".jpeg");
        picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget21.set_imagePath(picturePath);
            moneyTarget21.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget22 = new MoneyTarget(0L, "01", year4,
                "Mersedes", 7000000, 3000000, 4000000,
                "", "", moneyDream2.get_id(), EnumStatus.Blank,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget22.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target022);

        filePath2 = new File(internalStorage, moneyTarget22.get_id() + ".jpeg");
        picturePath2 = filePath2.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath2))
        {
            viewBitmap2.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget22.set_imagePath(picturePath2);
            moneyTarget22.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget23 = new MoneyTarget(0L, "01", year7,
                "Luxury", 15000000, 5000000, 4000000,
                "", "", moneyDream2.get_id(), EnumStatus.Blank,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget23.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap3 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target023);

        filePath3 = new File(internalStorage, moneyTarget23.get_id() + ".jpeg");
        picturePath3 = filePath3.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath3))
        {
            viewBitmap3.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget23.set_imagePath(picturePath3);
            moneyTarget23.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget31 = new MoneyTarget(0L, "06", year1,
                "Турция", 300000, 300000, 0,
                "", "", moneyDream3.get_id(), EnumStatus.Started,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget31.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target031);

        filePath = new File(internalStorage, moneyTarget31.get_id() + ".jpeg");
        picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget31.set_imagePath(picturePath);
            moneyTarget31.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget32 = new MoneyTarget(0L, "06", year2,
                "Египет", 500000, 500000, 0,
                "", "", moneyDream3.get_id(), EnumStatus.Blank,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget32.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target032);

        filePath2 = new File(internalStorage, moneyTarget32.get_id() + ".jpeg");
        picturePath2 = filePath2.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath2))
        {
            viewBitmap2.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget32.set_imagePath(picturePath2);
            moneyTarget32.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget33 = new MoneyTarget(0L, "06", year3,
                "Дубай", 1000000, 1000000, 0,
                "", "", moneyDream3.get_id(), EnumStatus.Blank,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget33.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap3 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target033);

        filePath3 = new File(internalStorage, moneyTarget33.get_id() + ".jpeg");
        picturePath3 = filePath3.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath3))
        {
            viewBitmap3.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget33.set_imagePath(picturePath3);
            moneyTarget33.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget41 = new MoneyTarget(0L, "01", year1,
                "Группа в Телеграм", 0, 0, 0,
                "", "", moneyDream4.get_id(), EnumStatus.Started,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget41.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target041);

        filePath = new File(internalStorage, moneyTarget41.get_id() + ".jpeg");
        picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget41.set_imagePath(picturePath);
            moneyTarget41.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }

        MoneyTarget moneyTarget51 = new MoneyTarget(0L, "01", year1,
                "Дом за городом", 10000000, 1000000, 9000000,
                "", "", moneyDream5.get_id(), EnumStatus.Started,
                "", 0L, 0,
                null, null, null, null,
                0, 0, null);
        moneyTarget51.AddToDatabaseWOTrack(dbHelper, context);

        viewBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test_target051);

        filePath = new File(internalStorage, moneyTarget51.get_id() + ".jpeg");
        picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            moneyTarget51.set_imagePath(picturePath);
            moneyTarget51.UpdateInDatabase(dbHelper, context);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
        }
        //endregion

        //region MoneyIncrease
        MoneyIncrease moneyIncrease0 = new MoneyIncrease(0, "1", "01", year0,
                65000, 0, 0f, false, false);
        moneyIncrease0.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncrease moneyIncrease1 = new MoneyIncrease(0, "1", "01", year1,
                100000, 0, 0f, false, false);
        moneyIncrease1.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncrease moneyIncrease2 = new MoneyIncrease(0, "1", "01", year2,
                150000, 0, 0f, false, false);
        moneyIncrease2.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncrease moneyIncrease3 = new MoneyIncrease(0, "1", "01", year3,
                225000, 0, 0f, false, false);
        moneyIncrease3.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncrease moneyIncrease4 = new MoneyIncrease(0, "1", "01", year4,
                350000, 0, 0f, false, false);
        moneyIncrease4.AddToDatabaseWOTrack(dbHelper, context);
        //endregion

        //region MoneyCategoryInc
        MoneyCategory moneyCategoryParentInc1 = new MoneyCategory(
                1L, 0L, 1, "Зарплата",
                0L, EnumTypeCategory.Increase, false,
                false, false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                10L);
        moneyCategoryParentInc1.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryInc11 = new MoneyCategory(2L,
                moneyCategoryParentInc1.get_id(), 1, "Аванс",
                0L, EnumTypeCategory.Increase, false,
                false, false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                10L);
        moneyCategoryInc11.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryInc12 = new MoneyCategory(3L,
                moneyCategoryParentInc1.get_id(), 1, "Основная часть",
                0L, EnumTypeCategory.Increase, false,
                false, false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                10L);
        moneyCategoryInc12.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryInc13 = new MoneyCategory(4L,
                moneyCategoryParentInc1.get_id(), 1, "Премия",
                0L, EnumTypeCategory.Increase, false,
                false, false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                10L);
        moneyCategoryInc13.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryParentInc2 = new MoneyCategory(5L,
                0L, 1, "Бизнес",
                0L, EnumTypeCategory.Increase, false,
                false, false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                1L);
        moneyCategoryParentInc2.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryInc21 = new MoneyCategory(6L,
                moneyCategoryParentInc2.get_id(), 1, "Проект1",
                0L, EnumTypeCategory.Increase, false,
                false, false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                12L);
        moneyCategoryInc21.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyCategoryCost
        MoneyCategory moneyCategoryParentCost1 = new MoneyCategory(7L, 0L, 1, "Быт",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                214L);
        moneyCategoryParentCost1.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss11 = new MoneyCategory(8L,
                moneyCategoryParentCost1.get_id(), 1, "Продукты",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                201L);
        moneyCategoryCoss11.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss12 = new MoneyCategory(9L,
                moneyCategoryParentCost1.get_id(), 1, "Транспорт",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                208L);
        moneyCategoryCoss12.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss13 = new MoneyCategory(10L,
                moneyCategoryParentCost1.get_id(), 1, "Одежда",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                244L);
        moneyCategoryCoss13.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss14 = new MoneyCategory(11L,
                moneyCategoryParentCost1.get_id(), 1, "Прочее",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                250L);
        moneyCategoryCoss14.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryParentCost2 = new MoneyCategory(12L,
                0L, 2, "Жилье",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                220L);
        moneyCategoryParentCost2.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss21 = new MoneyCategory(13L,
                moneyCategoryParentCost2.get_id(), 1, "Ипотека",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                220L);
        moneyCategoryCoss21.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss22 = new MoneyCategory(14L,
                moneyCategoryParentCost2.get_id(), 1, "Квартплата",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                222L);
        moneyCategoryCoss22.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryParentCost3 = new MoneyCategory(15L,
                0L, 4, "Автомобиль",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                207L);
        moneyCategoryParentCost3.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss31 = new MoneyCategory(16L,
                moneyCategoryParentCost3.get_id(), 1, "Автокредит",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                224L);
        moneyCategoryCoss31.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss32 = new MoneyCategory(17L,
                moneyCategoryParentCost3.get_id(), 1, "Топливо",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                222L);
        moneyCategoryCoss32.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryParentCost4 = new MoneyCategory(18L,
                0L, 4, "Отдых",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                206L);
        moneyCategoryParentCost4.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryCoss41 = new MoneyCategory(19L,
                moneyCategoryParentCost4.get_id(), 1, "Отдых",
                0L, EnumTypeCategory.Cost, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                206L);
        moneyCategoryCoss41.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyCategoryWallet
        MoneyCategory moneyCategoryWallet1 = new MoneyCategory(17L, 0L, 1, "Карта",
                0L, EnumTypeCategory.Wallet, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                108L);
        moneyCategoryWallet1.AddToDatabaseWOTrack(dbHelper);

        MoneyCategory moneyCategoryWallet2 = new MoneyCategory(18L, 0L, 1, "Наличные",
                0L, EnumTypeCategory.Wallet, false, false,
                false, true, 0L,
                EnumCategoryType.Base,
//                EnumCategoryStatus.Active,
                0L,
                false,
                101L);
        moneyCategoryWallet2.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyIncomingInc
        MoneyIncoming moneyIncomingInc01 = new MoneyIncoming(0L, moneyCategoryInc11.get_id(),
                moneyIncrease0.get_id(),  1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc01.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc02 = new MoneyIncoming(0L, moneyCategoryInc12.get_id(),
                moneyIncrease0.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc02.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc11 = new MoneyIncoming(0L, moneyCategoryInc11.get_id(),
                moneyIncrease1.get_id(), 2000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc11.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc12 = new MoneyIncoming(0L, moneyCategoryInc12.get_id(),
                moneyIncrease1.get_id(),  6000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc12.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc13 = new MoneyIncoming(0L, moneyCategoryInc13.get_id(),
                moneyIncrease1.get_id(), 2000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc13.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc21 = new MoneyIncoming(0L, moneyCategoryInc11.get_id(),
                moneyIncrease2.get_id(),  3000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc21.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc22 = new MoneyIncoming(0L, moneyCategoryInc12.get_id(),
                moneyIncrease2.get_id(), 7000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc22.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc23 = new MoneyIncoming(0L, moneyCategoryInc13.get_id(),
                moneyIncrease2.get_id(), 4000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc23.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc24 = new MoneyIncoming(0L, moneyCategoryInc21.get_id(),
                moneyIncrease2.get_id(), 1000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc24.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc31 = new MoneyIncoming(0L, moneyCategoryInc11.get_id(),
                moneyIncrease3.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc31.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc32 = new MoneyIncoming(0L, moneyCategoryInc12.get_id(),
                moneyIncrease3.get_id(), 10000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc32.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc33 = new MoneyIncoming(0L, moneyCategoryInc13.get_id(),
                moneyIncrease3.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc33.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc34 = new MoneyIncoming(0L, moneyCategoryInc21.get_id(),
                moneyIncrease3.get_id(), 2500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc34.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc41 = new MoneyIncoming(0L, moneyCategoryInc11.get_id(),
                moneyIncrease4.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc41.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc42 = new MoneyIncoming(0L, moneyCategoryInc12.get_id(),
                moneyIncrease4.get_id(), 10000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc42.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc43 = new MoneyIncoming(0L, moneyCategoryInc13.get_id(),
                moneyIncrease4.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc43.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingInc44 = new MoneyIncoming(0L, moneyCategoryInc21.get_id(),
                moneyIncrease4.get_id(), 15000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingInc44.AddToDatabaseWOTrack(dbHelper, context);
        //endregion

        //region MoneyIncomingCost
        MoneyIncoming moneyIncomingCost01 = new MoneyIncoming(0L, moneyCategoryCoss11.get_id(),
                moneyIncrease0.get_id(),2500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost01.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost02 = new MoneyIncoming(0L, moneyCategoryCoss12.get_id(),
                moneyIncrease0.get_id(), 1000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost02.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost03 = new MoneyIncoming(0L, moneyCategoryCoss13.get_id(),
                moneyIncrease0.get_id(),1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost03.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost04 = new MoneyIncoming(0L, moneyCategoryCoss14.get_id(),
                moneyIncrease0.get_id(), 1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost04.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost11 = new MoneyIncoming(0L, moneyCategoryCoss11.get_id(),
                moneyIncrease1.get_id(),3000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost11.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost12 = new MoneyIncoming(0L, moneyCategoryCoss12.get_id(),
                moneyIncrease1.get_id(),1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost12.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost13 = new MoneyIncoming(0L, moneyCategoryCoss13.get_id(),
                moneyIncrease1.get_id(), 2500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost13.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost14 = new MoneyIncoming(0L, moneyCategoryCoss14.get_id(),
                moneyIncrease1.get_id(), 3000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost14.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost21 = new MoneyIncoming(0L, moneyCategoryCoss11.get_id(),
                moneyIncrease2.get_id(), 3000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost21.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost22 = new MoneyIncoming(0L, moneyCategoryCoss12.get_id(),
                moneyIncrease2.get_id(), 1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost22.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost23 = new MoneyIncoming(0L, moneyCategoryCoss13.get_id(),
                moneyIncrease2.get_id(), 2500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost23.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost24 = new MoneyIncoming(0L, moneyCategoryCoss14.get_id(),
                moneyIncrease2.get_id(), 2000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost24.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost25 = new MoneyIncoming(0L, moneyCategoryCoss21.get_id(),
                moneyIncrease2.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost25.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost26 = new MoneyIncoming(0L, moneyCategoryCoss22.get_id(),
                moneyIncrease2.get_id(), 1000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost26.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost31 = new MoneyIncoming(0L, moneyCategoryCoss11.get_id(),
                moneyIncrease3.get_id(), 3000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost31.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost32 = new MoneyIncoming(0L, moneyCategoryCoss12.get_id(),
                moneyIncrease3.get_id(), 1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost32.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost33 = new MoneyIncoming(0L, moneyCategoryCoss13.get_id(),
                moneyIncrease3.get_id(), 2500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost33.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost34 = new MoneyIncoming(0L, moneyCategoryCoss14.get_id(),
                moneyIncrease3.get_id(), 2000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost34.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost35 = new MoneyIncoming(0L, moneyCategoryCoss21.get_id(),
                moneyIncrease3.get_id(), 7500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost35.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost36 = new MoneyIncoming(0L, moneyCategoryCoss22.get_id(),
                moneyIncrease3.get_id(), 1000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost36.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost37 = new MoneyIncoming(0L, moneyCategoryCoss31.get_id(),
                moneyIncrease3.get_id(), 4000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost37.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost38 = new MoneyIncoming(0L, moneyCategoryCoss32.get_id(),
                moneyIncrease3.get_id(), 1000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost38.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost41 = new MoneyIncoming(0L, moneyCategoryCoss11.get_id(),
                moneyIncrease4.get_id(), 4500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost41.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost42 = new MoneyIncoming(0L, moneyCategoryCoss12.get_id(),
                moneyIncrease4.get_id(), 1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost42.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost43 = new MoneyIncoming(0L, moneyCategoryCoss13.get_id(),
                moneyIncrease4.get_id(), 2500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost43.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost44 = new MoneyIncoming(0L, moneyCategoryCoss14.get_id(),
                moneyIncrease4.get_id(), 2000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost44.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost45 = new MoneyIncoming(0L, moneyCategoryCoss21.get_id(),
                moneyIncrease4.get_id(), 10000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost45.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost46 = new MoneyIncoming(0L, moneyCategoryCoss22.get_id(),
                moneyIncrease4.get_id(),1500000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost46.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost47 = new MoneyIncoming(0L, moneyCategoryCoss31.get_id(),
                moneyIncrease4.get_id(), 6000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost47.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost48 = new MoneyIncoming(0L, moneyCategoryCoss32.get_id(),
                moneyIncrease4.get_id(), 2000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost48.AddToDatabaseWOTrack(dbHelper, context);

        MoneyIncoming moneyIncomingCost49 = new MoneyIncoming(0L, moneyCategoryCoss41.get_id(),
                moneyIncrease4.get_id(), 5000000, 0f ,EnumTypeIncoming.Sum, 0L);
        moneyIncomingCost49.AddToDatabaseWOTrack(dbHelper, context);
        //endregion

        //region MoneyPNLInc
        MoneyPNLIndependent moneyPNL01 = new MoneyPNLIndependent(1L, EnumTypeCategory.Increase,
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryParentInc1.get_id(),
                moneyCategoryParentInc1.get_id(),
                moneyCategoryInc11.get_id(),
                moneyCategoryInc11.get_id(),
                "", "", "",
                22,
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                1500000L, 1500000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL01.AddToDatabaseWOTrack(dbHelper);

        MoneyPNLIndependent moneyPNL02 = new MoneyPNLIndependent(2L, EnumTypeCategory.Increase,
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryParentInc1.get_id(),
                moneyCategoryParentInc1.get_id(),
                moneyCategoryInc12.get_id(),
                moneyCategoryInc12.get_id(),
                "", "", "",
                10,
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                5000000L, 5000000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL02.AddToDatabaseWOTrack(dbHelper);

        MoneyPNLIndependent moneyPNL03 = new MoneyPNLIndependent(3L, EnumTypeCategory.Increase,
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryParentInc1.get_id(),
                moneyCategoryParentInc1.get_id(),
                moneyCategoryInc13.get_id(),
                moneyCategoryInc13.get_id(),
                "", "", "",
                28,
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                500000L, 500000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL03.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyInc4Cost
        MoneyInc4Cost moneyInc4Cost1 = new MoneyInc4Cost(1L,
                moneyPNL02.get_id(),
                EnumTypeCategory.Increase,
                moneyPNL02.getIncreaseCategoryId(),
                0L,
                0L,
                "",
                0L,
                "",
                0L,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryCoss11.get_id(),
                moneyCategoryCoss11.get_id(),
                moneyCategoryParentCost1.getCategoryName(),
                0L,
                moneyCategoryCoss11.getCategoryName(),
                0L,
                moneyPNL02.getDay(), moneyPNL02.getMonth(), moneyPNL02.getYear(),
                2500000L, 2500000L);
        moneyInc4Cost1.AddToDatabaseWOTrack(dbHelper);

        MoneyInc4Cost moneyInc4Cost2 = new MoneyInc4Cost(1L,
                moneyPNL02.get_id(),
                EnumTypeCategory.Increase,
                moneyPNL02.getIncreaseCategoryId(),
                0L,
                0L,
                "",
                0L,
                "",
                0L,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryCoss12.get_id(),
                moneyCategoryCoss12.get_id(),
                moneyCategoryParentCost1.getCategoryName(),
                0L,
                moneyCategoryCoss12.getCategoryName(),
                0L,
                moneyPNL01.getDay(), moneyPNL01.getMonth(), moneyPNL01.getYear(),
                1000000L, 1000000L);
        moneyInc4Cost2.AddToDatabaseWOTrack(dbHelper);

        MoneyInc4Cost moneyInc4Cost3 = new MoneyInc4Cost(1L,
                moneyPNL02.get_id(),
                EnumTypeCategory.Increase,
                moneyPNL02.getIncreaseCategoryId(),
                0L,
                0L,
                "",
                0L,
                "",
                0L,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryCoss13.get_id(),
                moneyCategoryCoss13.get_id(),
                moneyCategoryParentCost1.getCategoryName(),
                0L,
                moneyCategoryCoss13.getCategoryName(),
                0L,
                moneyPNL01.getDay(), moneyPNL01.getMonth(), moneyPNL01.getYear(),
                1500000L, 1500000L);
        moneyInc4Cost3.AddToDatabaseWOTrack(dbHelper);

        MoneyInc4Cost moneyInc4Cost4 = new MoneyInc4Cost(1L,
                moneyPNL01.get_id(),
                EnumTypeCategory.Increase,
                moneyPNL01.getIncreaseCategoryId(),
                0L,
                0L,
                "",
                0L,
                "",
                0L,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryCoss14.get_id(),
                moneyCategoryCoss14.get_id(),
                moneyCategoryParentCost1.getCategoryName(),
                0L,
                moneyCategoryCoss14.getCategoryName(),
                0L,
                moneyPNL01.getDay(), moneyPNL01.getMonth(), moneyPNL01.getYear(),
                1500000L, 1500000L);
        moneyInc4Cost4.AddToDatabaseWOTrack(dbHelper);

        MoneyInc4Cost moneyInc4Cost5 = new MoneyInc4Cost(1L,
                moneyPNL03.get_id(),
                EnumTypeCategory.Increase,
                moneyPNL03.getIncreaseCategoryId(),
                0L,
                0L,
                "",
                0L,
                "",
                0L,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryCoss14.get_id(),
                moneyCategoryCoss14.get_id(),
                moneyCategoryParentCost1.getCategoryName(),
                0L,
                moneyCategoryCoss14.getCategoryName(),
                0L,
                moneyPNL03.getDay(), moneyPNL03.getMonth(), moneyPNL03.getYear(),
                500000L, 500000L);
        moneyInc4Cost5.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyPNLCost
        MoneyPNLIndependent moneyPNL11 = new MoneyPNLIndependent(0L,
                EnumTypeCategory.Cost,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryParentCost1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryCoss11.get_id(),
                moneyCategoryCoss11.get_id(),
                "", "", "",
                calendar1.get(Calendar.DATE),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                2700000L, 2700000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL11.AddToDatabaseWOTrack(dbHelper);

        MoneyPNLIndependent moneyPNL12 = new MoneyPNLIndependent(0L,
                EnumTypeCategory.Cost,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryParentCost1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryCoss12.get_id(),
                moneyCategoryCoss12.get_id(),
                "", "", "",
                calendar1.get(Calendar.DATE),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                1100000L, 1100000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL12.AddToDatabaseWOTrack(dbHelper);

        MoneyPNLIndependent moneyPNL13 = new MoneyPNLIndependent(0L,
                EnumTypeCategory.Cost,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryParentCost1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryCoss13.get_id(),
                moneyCategoryCoss13.get_id(),
                "", "", "",
                calendar1.get(Calendar.DATE),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                1800000L, 1800000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL13.AddToDatabaseWOTrack(dbHelper);

        MoneyPNLIndependent moneyPNL14 = new MoneyPNLIndependent(0L,
                EnumTypeCategory.Cost,
                moneyCategoryParentCost1.get_id(),
                moneyCategoryParentCost1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryCoss14.get_id(),
                moneyCategoryCoss14.get_id(),
                "", "", "",
                calendar1.get(Calendar.DATE),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                1200000L, 1200000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL14.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyPNLWallet
        MoneyPNLIndependent moneyPNL21 = new MoneyPNLIndependent(0L,
                EnumTypeCategory.Wallet,
                moneyCategoryWallet2.get_id(),
                moneyCategoryWallet2.get_id(),
                moneyCategoryWallet1.get_id(),
                moneyCategoryWallet1.get_id(),
                0L,
                0L,
                "", "", "",
                calendar1.get(Calendar.DATE),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                200000L, 200000L,
                0, "",
                0L, 0L, 0L,
                false, false,
                0L, 0L, false,
                0L, 0L,
                null, null,
                null, null);
        moneyPNL21.AddToDatabaseWOTrack(dbHelper);
        //endregion

        //region MoneyCredit
//            MoneyCredit moneyCredit1 = new MoneyCredit(1, moneyTarget.get_id(),
//                    moneyTarget.get_name(), "Кредит 1",
//                    60000d, 60000d,
//                    55000d,
//                    15d,
//                    calendarPrev.get(Calendar.DATE), calendarPrev.get(Calendar.MONTH),
//                    calendarPrev.get(Calendar.YEAR), 12,
//                    EnumTypeCredit.Different, EnumCreditStatus.Active,
//                    moneyCategoryWallet1.get_id(),
//                    moneyCategoryWallet1.get_id(),
//                    null, null);
//
//            //region MoneyPNLCredit
//            double startSum = moneyCredit1.get_startSumm();
//            double bodySum = startSum / moneyCredit1.get_loanTerm();
//
//            Calendar calendar = (Calendar) moneyCredit1.get_calendarStart().clone();
//            ArrayList<MoneyPNL> pnls = new ArrayList<>();
//            int increaseCategoryId = 20;
//            int subCategoryId = 22;
//            for (int i = 0; i < moneyCredit1.get_loanTerm(); i++)
//            {
//                calendar.add(Calendar.MONTH, 1);
//                double percentSum = startSum * moneyCredit1.get_percent() / 1200;
//                pnls.add(new MoneyPNL(0, EnumTypeCategory.Cost,
//                        increaseCategoryId, increaseCategoryId,
//                        moneyCategoryWallet1.get_id(), moneyCategoryWallet1.get_id(),
//                        subCategoryId, subCategoryId,
//                        "", moneyCategoryWallet1.get_name(), "",
//                        calendar.get(Calendar.DATE),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.YEAR),
//                        bodySum, bodySum,
//                        0, "",
//                        i > 0, false,
//                        moneyCredit1.get_id(),
//                        percentSum, percentSum, null, null));
//
//                startSum -= bodySum;
//            }
//            if (startSum != 0d)
//                pnls.get(pnls.size() - 1).set_percentSum(pnls.get(pnls.size() - 1).get_percentSum() + startSum);
//
//            moneyCredit1.set_pnls(pnls);
//            //endregion
//
//            moneyCredit1.AddToDatabaseWOTrack(dbHelper, context);
        //endregion
    }

    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private DBHelper(Context context, String databaseName, int databaseVersion)
    {
        super(context, databaseName, null, databaseVersion);
    }

    public static void DeleteLearnDatabase(Context context) throws Exception {
        try
        {
            DBHelper dbHelper = new DBHelper(context, DATABASE_NAME_LEARN, DATABASE_VERSION_LEARN);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.delete(TABLE_CABINET, null, null);
            sqLiteDatabase.delete(TABLE_DEL_OBJECT, null, null);
            sqLiteDatabase.delete(TABLE_PARAM, null, null);
            sqLiteDatabase.delete(TABLE_PLANVALUE, null, null);
            sqLiteDatabase.delete(TABLE_VALUE, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_DREAM, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_TARGET, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_INCREASE, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_CATEGORY, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_INCOMING, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_PERIODPLAN, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_PNL, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_INC4COST, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_CREDIT, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_CREDIT_PIVOT_POINT, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_ACCUMULATION, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_PORTFOLIO, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_TYPEASSET, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_ASSET, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_LOT_ASSET, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_LOT_ASSET_OPERATION, null, null);
            sqLiteDatabase.delete(TABLE_MONEY_BOARD, null, null);
            sqLiteDatabase.delete(TABLE_TIME_PROJECT, null, null);
            sqLiteDatabase.delete(TABLE_TIME_PRIORITY, null, null);
            sqLiteDatabase.delete(TABLE_TIME_TASK, null, null);
            sqLiteDatabase.delete(TABLE_PERSON_ASSISTANT, null, null);
            sqLiteDatabase.delete(TABLE_PERSON_COMM, null, null);

            GlobalClass globalClass = (GlobalClass) context.getApplicationContext();
            CreateStartData(dbHelper, context, globalClass);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase)
    {
        //region Custom tables create
        sqLiteDatabase.execSQL("create table " + TABLE_CABINET + "("
                + KEY_CABINET_ID + " integer primary key,"
                + KEY_CABINET_NAME + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_DEL_OBJECT + "("
                + KEY_DEL_OBJECT_ID + " integer primary key,"
                + KEY_DEL_OBJECT_CABINET_ID + " integer,"
                + KEY_DEL_OBJECT_NAME + " text,"
                + KEY_DEL_OBJECT_NUM + " integer, "
                + KEY_ALL_DATETIME_CREATE + " text" + ")");
        //endregion

        //region Tables create
        sqLiteDatabase.execSQL("create table " + TABLE_PARAM + "("
                + KEY_PARAM_ID + " integer primary key,"
                + KEY_PARAM_CABINET_ID + " integer,"
                + KEY_PARAM_NAME + " text,"
                + KEY_PARAM_COMMENT + " text,"
                + KEY_PARAM_MEASURE + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_PLANVALUE + "("
                + KEY_PLANVALUE_ID + " integer primary key,"
                + KEY_PLANVALUE_CABINET_ID + " integer,"
                + KEY_PLANVALUE_TARGET_ID + " integer,"
                + KEY_PLANVALUE_PARAM_ID + " integer,"
                + KEY_PLANVALUE_TYPE + " text,"
                + KEY_PLANVALUE_COEFFICIENT + " real,"
                + KEY_PLANVALUE_VALUE + " real,"
                + KEY_PLANVALUE_PV_SRC1 + " integer,"
                + KEY_PLANVALUE_PV_SRC2 + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_VALUE + "("
                + KEY_VALUE_ID + " integer primary key,"
                + KEY_VALUE_CABINET_ID + " integer,"
                + KEY_VALUE_PARAM + " integer,"
                + KEY_VALUE_TASK + " integer,"
                + KEY_VALUE_VALUE + " real,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_TAG + "("
                + KEY_TAG_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_TAG_NAME + " text,"
                + KEY_TAG_COMMENT + " text,"
                + KEY_TAG_TYPEDATE + " integer,"
                + KEY_TAG_ARCHIVE + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_TAGGED + "("
                + KEY_TAGGED_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_TAGGED_TAGID + " integer,"
                + KEY_TAGGED_EXTERNALID + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");
        //endregion

        //region Money tables create
        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_DREAM + "("
                + KEY_MONEY_DREAM_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_DREAM_POSITION + " integer,"
                + KEY_MONEY_DREAM_NAME + " text,"
                + KEY_MONEY_DREAM_IMAGE_PATH+ " text,"
                + KEY_MONEY_DREAM_IMAGE_URL + " text,"
                + KEY_MONEY_DREAM_STATUS + " text,"
                + KEY_MONEY_DREAM_ARCHIVE + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_TARGET + "("
                + KEY_MONEY_TARGET_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_TARGET_HEAD_ID + " integer,"
                + KEY_MONEY_TARGET_DREAM_ID + " integer,"
                + KEY_MONEY_TARGET_MONTH + " text,"
                + KEY_MONEY_TARGET_YEAR + " text,"
                + KEY_MONEY_TARGET_NAME + " text,"
                + KEY_MONEY_TARGET_PRICE + " integer,"
                + KEY_MONEY_TARGET_ACCUMULATION + " integer,"
                + KEY_MONEY_TARGET_CREDIT + " integer,"
                + KEY_MONEY_TARGET_IMAGE_PATH+ " text,"
                + KEY_MONEY_TARGET_IMAGE_URL + " text,"
                + KEY_MONEY_TARGET_STATUS + " text,"
                + KEY_MONEY_TARGET_TYPE + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_INCREASE + "("
                + KEY_MONEY_INCREASE_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_INCREASE_DAY + " text,"
                + KEY_MONEY_INCREASE_MONTH + " text,"
                + KEY_MONEY_INCREASE_YEAR + " text,"
                + KEY_MONEY_INCREASE_VALUE + " integer,"
                + KEY_MONEY_INCREASE_ARCHIVE + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_CATEGORY + "("
                + KEY_MONEY_CATEGORY_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_CATEGORY_TARGET_ID + " integer,"
                + KEY_MONEY_CATEGORY_PARENT_ID + " integer,"
                + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " text,"
                + KEY_MONEY_CATEGORY_POSITION + " integer,"
                + KEY_MONEY_CATEGORY_NAME + " text, "
//                + KEY_MONEY_CATEGORY_SUM + " real,"
                + KEY_MONEY_CATEGORY_SUM_L + " integer,"
                + KEY_MONEY_CATEGORY_TYPE + " text,"
                + KEY_MONEY_CATEGORY_STATUS + " text,"
                + KEY_MONEY_CATEGORY_SUMTOBALANCE + " integer,"
                + KEY_MONEY_CATEGORY_CREDIT_ID + " integer,"
                + KEY_MONEY_CATEGORY_ARCHIVE + " integer,"
                + KEY_MONEY_CATEGORY_ICON + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_INCOMING + "("
                + KEY_MONEY_INCOMING_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_INCOMING_CATEGORY_ID + " integer,"
                + KEY_MONEY_INCOMING_INCREASE_ID + " integer,"
                + KEY_MONEY_INCOMING_SUM + " integer,"
                + KEY_MONEY_INCOMING_SUM_L + " integer,"
                + KEY_MONEY_INCOMING_PERCENT + " real,"
                + KEY_MONEY_INCOMING_TYPE + " text,"
                + KEY_MONEY_INCOMING_EXTERNAL_ID + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_PERIODPLAN + "("
                + KEY_MONEY_PERIODPLAN_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_PERIODPLAN_CATEGORY_ID + " integer,"
                + KEY_MONEY_PERIODPLAN_MONTH + " integer,"
                + KEY_MONEY_PERIODPLAN_YEAR + " integer,"
                + KEY_MONEY_PERIODPLAN_SUM_L + " integer,"
                + KEY_MONEY_PERIODPLAN_TYPE + " text,"
                + KEY_MONEY_PERIODPLAN_EXTERNAL_ID + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_PNL + "("
                + KEY_MONEY_PNL_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_PNL_INC_CATEGORY_ID + " integer,"
                + KEY_MONEY_PNL_COST_CATEGORY_ID + " integer,"
                + KEY_MONEY_PNL_SUB_CATEGORY_ID + " integer, "
                + KEY_MONEY_PNL_DATE_INT + " integer,"
                + KEY_MONEY_PNL_MONTH_INT + " integer,"
                + KEY_MONEY_PNL_YEAR_INT + " integer,"
                + KEY_MONEY_PNL_SUM + " real,"
                + KEY_MONEY_PNL_SUM_L + " integer,"
                + KEY_MONEY_PNL_TASK_ID + " integer,"
                + KEY_MONEY_PNL_COMMENT + " text,"
                + KEY_MONEY_PNL_ISPLAN + " integer,"
                + KEY_MONEY_PNL_CREDIT_ID + " integer,"
                + KEY_MONEY_PNL_ACCUMULATION_ID + " integer,"
                + KEY_MONEY_PNL_CALCULATED + " integer,"
                + KEY_MONEY_PNL_PERCENTSUM + " real,"
                + KEY_MONEY_PNL_PERCENTSUM_L + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_INC4COST + "("
                + KEY_MONEY_INC4COST_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_INC4COST_PNL_ID + " integer,"
                + KEY_MONEY_INC4COST_COST_ID + " integer,"
                + KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID + " integer, "
                + KEY_MONEY_INC4COST_INC_ID + " integer, "
                + KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID + " integer, "
//                + KEY_MONEY_INC4COST_COST_DATE_INT + " integer,"
//                + KEY_MONEY_INC4COST_COST_MONTH_INT + " integer,"
//                + KEY_MONEY_INC4COST_COST_YEAR_INT + " integer,"
                + KEY_MONEY_INC4COST_INC_DATE_INT + " integer,"
                + KEY_MONEY_INC4COST_INC_MONTH_INT + " integer,"
                + KEY_MONEY_INC4COST_INC_YEAR_INT + " integer,"
                + KEY_MONEY_INC4COST_SUM + " real,"
                + KEY_MONEY_INC4COST_SUM_L + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_CREDIT + "("
                + KEY_MONEY_CREDIT_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_CREDIT_TARGET_ID + " integer,"
                + KEY_MONEY_CREDIT_NAME + " text,"
                + KEY_MONEY_CREDIT_STARTSUMM + " real,"
                + KEY_MONEY_CREDIT_STARTSUM_L + " integer,"
                + KEY_MONEY_CREDIT_SUM + " real,"
                + KEY_MONEY_CREDIT_SUM_L + " interger,"
                + KEY_MONEY_CREDIT_PERCENT + " real,"
                + KEY_MONEY_CREDIT_DATESTART_INT + " integer,"
                + KEY_MONEY_CREDIT_MONTHSTART_INT + " integer,"
                + KEY_MONEY_CREDIT_YEARSTART_INT + " integer,"
                + KEY_MONEY_CREDIT_LOANTERM + " integer,"
                + KEY_MONEY_CREDIT_TYPE + " text,"
                + KEY_MONEY_CREDIT_STATUS + " text,"
                + KEY_MONEY_CREDIT_WALLET + " integer,"
                + KEY_MONEY_CREDIT_INC4COST_CATEGORY + " integer,"
                + KEY_MONEY_CREDIT_INC4COST_SUBCATEGORY + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_CREDIT_PIVOT_POINT + "("
                + KEY_MONEY_CREDIT_PIVOT_POINT_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID + " integer,"
                + KEY_MONEY_CREDIT_PIVOT_POINT_STARTSUM_L + " integer,"
                + KEY_MONEY_CREDIT_PIVOT_POINT_PERCENT + " real,"
                + KEY_MONEY_CREDIT_PIVOT_POINT_LOANTERM + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_ACCUMULATION + "("
                + KEY_MONEY_ACCUMULATION_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_ACCUMULATION_TARGET_ID + " integer,"
                + KEY_MONEY_ACCUMULATION_TYPE + " text,"
                + KEY_MONEY_ACCUMULATION_PROGRESSION_COEF + " integer,"
                + KEY_MONEY_ACCUMULATION_MONTH_START + " integer,"
                + KEY_MONEY_ACCUMULATION_YEAR_START + " integer,"
                + KEY_MONEY_ACCUMULATION_MONTH_END + " integer,"
                + KEY_MONEY_ACCUMULATION_YEAR_END + " interger,"
                + KEY_MONEY_ACCUMULATION_START_SUM + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_PORTFOLIO + "("
                + KEY_MONEY_PORTFOLIO_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_PORTFOLIO_PARENT_ID + " integer,"
                + KEY_MONEY_PORTFOLIO_NAME + " text,"
                + KEY_MONEY_PORTFOLIO_COMMENT + " text,"
                + KEY_MONEY_PORTFOLIO_POSITION + " integer,"
                + KEY_MONEY_PORTFOLIO_STATUS + " text,"
                + KEY_MONEY_PORTFOLIO_WALLET + " integer,"
                + KEY_MONEY_PORTFOLIO_SUBCATEGORY + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_TYPEASSET + "("
                + KEY_MONEY_TYPEASSET_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_TYPEASSET_PARENT_ID + " integer,"
                + KEY_MONEY_TYPEASSET_NAME + " text,"
                + KEY_MONEY_TYPEASSET_COMMENT + " text,"
                + KEY_MONEY_TYPEASSET_POSITION + " integer,"
                + KEY_MONEY_TYPEASSET_STATUS + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_ASSET + "("
                + KEY_MONEY_ASSET_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_ASSET_WALLET_ID + " integer,"
                + KEY_MONEY_ASSET_CREDIT_ID + " integer,"
                + KEY_MONEY_ASSET_NAME + " text,"
                + KEY_MONEY_ASSET_TICKER + " text,"
                + KEY_MONEY_ASSET_COMMENT + " text,"
                + KEY_MONEY_ASSET_POSITION + " integer,"
                + KEY_MONEY_ASSET_STATUS + " text,"
                + KEY_MONEY_ASSET_KIND + " text,"
                + KEY_MONEY_ASSET_TYPE + " integer,"
                + KEY_MONEY_ASSET_CURRENCY + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_LOT_ASSET + "("
                + KEY_MONEY_LOT_ASSET_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID + " integer,"
                + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + " integer,"
                + KEY_MONEY_LOT_ASSET_ASSET_ID + " integer,"
                + KEY_MONEY_LOT_ASSET_POSITION + " integer,"
                + KEY_MONEY_LOT_ASSET_STATUS + " text,"
                + KEY_MONEY_LOT_ASSET_COUNT + " integer,"
                + KEY_MONEY_LOT_ASSET_NAME + " text,"
                + KEY_MONEY_LOT_ASSET_COMMENT + " text,"
                + KEY_MONEY_LOT_ASSET_DATE_START + " integer,"
                + KEY_MONEY_LOT_ASSET_MONTH_START + " integer,"
                + KEY_MONEY_LOT_ASSET_YEAR_START + " integer,"
                + KEY_MONEY_LOT_ASSET_DATE_END + " integer,"
                + KEY_MONEY_LOT_ASSET_MONTH_END + " integer,"
                + KEY_MONEY_LOT_ASSET_YEAR_END + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_LOT_ASSET_OPERATION + "("
                + KEY_MONEY_LOT_ASSET_OPERATION_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_LOT_ID + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + " text,"
                + KEY_MONEY_LOT_ASSET_OPERATION_CATEGORY + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_SUBCATEGORY + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_YEAR + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_MONTH + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_DATE + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_COUNT + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_PRICE + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_COMISSION + " integer,"
                + KEY_MONEY_LOT_ASSET_OPERATION_ACCUMULATED_PRICE + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_MONEY_BOARD + "("
                + KEY_MONEY_BOARD_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_MONEY_BOARD_NAME + " text,"
                + KEY_MONEY_BOARD_POSITION + " integer,"
                + KEY_MONEY_BOARD_VISIBLE + " integer,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");
        //endregion

        //region Task tables create
        sqLiteDatabase.execSQL("create table " + TABLE_TIME_PROJECT + "("
                + KEY_TIME_PROJECT_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_TIME_PROJECT_PARENT_ID + " integer,"
                + KEY_TIME_PROJECT_TARGET_ID + " integer,"
                + KEY_TIME_PROJECT_NAME + " text,"
                + KEY_TIME_PROJECT_COMMENT + " text,"
                + KEY_TIME_PROJECT_POSITION + " integer,"
                + KEY_TIME_PROJECT_STATUS + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_TIME_PRIORITY + "("
                + KEY_TIME_PRIORITY_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_TIME_PRIORITY_NAME + " text,"
                + KEY_TIME_PRIORITY_COMMENT + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_TIME_TASK + "("
                + KEY_TIME_TASK_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_TIME_TASK_TYPE + " text,"
                + KEY_TIME_TASK_PROJECT_ID + " integer,"
                + KEY_TIME_TASK_PARENT_ID + " integer,"
                + KEY_TIME_TASK_OCCURED_ID + " integer,"
                + KEY_TIME_TASK_NAME + " text,"
                + KEY_TIME_TASK_COMMENT + " text,"
                + KEY_TIME_TASK_PRIORITY + " integer,"
                + KEY_TIME_TASK_DATE_INT_PLAN + " integer,"
                + KEY_TIME_TASK_MONTH_INT_PLAN + " integer,"
                + KEY_TIME_TASK_YEAR_INT_PLAN + " integer,"
                + KEY_TIME_TASK_HOUR_INT_PLAN + " integer,"
                + KEY_TIME_TASK_MINUTE_INT_PLAN + " integer,"
                + KEY_TIME_TASK_DATE_INT_FACT + " integer,"
                + KEY_TIME_TASK_MONTH_INT_FACT + " integer,"
                + KEY_TIME_TASK_YEAR_INT_FACT + " integer,"
                + KEY_TIME_TASK_HOUR_INT_FACT + " integer,"
                + KEY_TIME_TASK_MINUTE_INT_FACT + " integer,"
                + KEY_TIME_TASK_REPEAT + " integer,"
                + KEY_TIME_TASK_REPEAT_TYPE + " text,"
                + KEY_TIME_TASK_REPEAT_PER + " integer,"
                + KEY_TIME_TASK_REPEAT_SUBTASKS + " text,"
                + KEY_TIME_TASK_CONFIRMED + " integer,"
                + KEY_TIME_TASK_STATUS + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");
        //endregion

        //region Person table create
        sqLiteDatabase.execSQL("create table " + TABLE_PERSON_ASSISTANT + "("
                + KEY_PERSON_ASSISTANT_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_PERSON_ASSISTANT_NAME + " text,"
                + KEY_PERSON_ASSISTANT_SURNAME + " text,"
                + KEY_PERSON_ASSISTANT_MIDDLENAME + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_PERSON_COMM + "("
                + KEY_PERSON_COMM_ID + " integer primary key,"
                + KEY_ALL_CABINET_ID + " integer,"
                + KEY_PERSON_COMM_ASSIST_ID + " integer,"
                + KEY_PERSON_COMM_NAME + " text,"
                + KEY_PERSON_COMM_COMM + " text,"
                + KEY_ALL_DATETIME_CREATE + " text,"
                + KEY_ALL_DATETIME_UPDATE + " text" + ")");
        //endregion
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        for (int i = oldVersion + 1; i <= newVersion; i++)
        {
            if (i == 2)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_DREAM + " add column "
                        + KEY_MONEY_DREAM_STATUS + " text");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_TARGET + " add column "
                        + KEY_MONEY_TARGET_STATUS + " text");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_TARGET + " add column "
                        + KEY_MONEY_TARGET_TYPE + " text");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_STATUS + " text");
            }

            if (i == 3)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_TARGET + " add column "
                        + KEY_MONEY_TARGET_HEAD_ID + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_TASK_ID + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_COMMENT + " text");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_ISPLAN + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_CREDIT_ID + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_PERCENTSUM + " real");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_SUMTOBALANCE + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_CREDIT_ID + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_TARGET_ID + " integer");

                sqLiteDatabase.execSQL("create table " + TABLE_PARAM + "("
                        + KEY_PARAM_ID + " integer primary key,"
                        + KEY_PARAM_CABINET_ID + " integer,"
                        + KEY_PARAM_NAME + " text,"
                        + KEY_PARAM_COMMENT + " text,"
                        + KEY_PARAM_MEASURE + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_PLANVALUE + "("
                        + KEY_PLANVALUE_ID + " integer primary key,"
                        + KEY_PLANVALUE_CABINET_ID + " integer,"
                        + KEY_PLANVALUE_TARGET_ID + " integer,"
                        + KEY_PLANVALUE_PARAM_ID + " integer,"
                        + KEY_PLANVALUE_TYPE + " text,"
                        + KEY_PLANVALUE_COEFFICIENT + " real,"
                        + KEY_PLANVALUE_VALUE + " real,"
                        + KEY_PLANVALUE_PV_SRC1 + " integer,"
                        + KEY_PLANVALUE_PV_SRC2 + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_INC4COST + "("
                        + KEY_MONEY_INC4COST_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_INC4COST_PNL_ID + " integer,"
                        + KEY_MONEY_INC4COST_COST_ID + " integer,"
                        + KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID + " integer, "
                        + KEY_MONEY_INC4COST_INC_ID + " integer, "
                        + KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID + " integer, "
//                        + KEY_MONEY_INC4COST_COST_DATE_INT + " integer,"
//                        + KEY_MONEY_INC4COST_COST_MONTH_INT + " integer,"
//                        + KEY_MONEY_INC4COST_COST_YEAR_INT + " integer,"
                        + KEY_MONEY_INC4COST_INC_DATE_INT + " integer,"
                        + KEY_MONEY_INC4COST_INC_MONTH_INT + " integer,"
                        + KEY_MONEY_INC4COST_INC_YEAR_INT + " integer,"
                        + KEY_MONEY_INC4COST_SUM + " real,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_CREDIT + "("
                        + KEY_MONEY_CREDIT_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_CREDIT_TARGET_ID + " integer,"
                        + KEY_MONEY_CREDIT_NAME + " text,"
                        + KEY_MONEY_CREDIT_STARTSUMM + " real,"
                        + KEY_MONEY_CREDIT_SUM + " real,"
                        + KEY_MONEY_CREDIT_PERCENT + " real,"
                        + KEY_MONEY_CREDIT_DATESTART_INT + " integer,"
                        + KEY_MONEY_CREDIT_MONTHSTART_INT + " integer,"
                        + KEY_MONEY_CREDIT_YEARSTART_INT + " integer,"
                        + KEY_MONEY_CREDIT_LOANTERM + " integer,"
                        + KEY_MONEY_CREDIT_TYPE + " text,"
                        + KEY_MONEY_CREDIT_STATUS + " text,"
                        + KEY_MONEY_CREDIT_WALLET + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 4)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_VALUE + "("
                        + KEY_VALUE_ID + " integer primary key,"
                        + KEY_VALUE_CABINET_ID + " integer,"
                        + KEY_VALUE_PARAM + " integer,"
                        + KEY_VALUE_TASK + " integer,"
                        + KEY_VALUE_VALUE + " real,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_TIME_PROJECT + "("
                        + KEY_TIME_PROJECT_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_TIME_PROJECT_PARENT_ID + " integer,"
                        + KEY_TIME_PROJECT_TARGET_ID + " integer,"
                        + KEY_TIME_PROJECT_NAME + " text,"
                        + KEY_TIME_PROJECT_COMMENT + " text,"
                        + KEY_TIME_PROJECT_POSITION + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_TIME_PRIORITY + "("
                        + KEY_TIME_PRIORITY_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_TIME_PRIORITY_NAME + " text,"
                        + KEY_TIME_PRIORITY_COMMENT + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_TIME_TASK + "("
                        + KEY_TIME_TASK_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_TIME_TASK_PARENT_ID + " integer,"
                        + KEY_TIME_TASK_OCCURED_ID + " integer,"
                        + KEY_TIME_TASK_TYPE + " text,"
                        + KEY_TIME_TASK_PROJECT_ID + " integer,"
                        + KEY_TIME_TASK_NAME + " text,"
                        + KEY_TIME_TASK_COMMENT + " text,"
                        + KEY_TIME_TASK_DATE_INT_PLAN + " integer,"
                        + KEY_TIME_TASK_MONTH_INT_PLAN + " integer,"
                        + KEY_TIME_TASK_YEAR_INT_PLAN + " integer,"
                        + KEY_TIME_TASK_HOUR_INT_PLAN + " integer,"
                        + KEY_TIME_TASK_MINUTE_INT_PLAN + " integer,"
                        + KEY_TIME_TASK_DATE_INT_FACT + " integer,"
                        + KEY_TIME_TASK_MONTH_INT_FACT + " integer,"
                        + KEY_TIME_TASK_YEAR_INT_FACT + " integer,"
                        + KEY_TIME_TASK_HOUR_INT_FACT + " integer,"
                        + KEY_TIME_TASK_MINUTE_INT_FACT + " integer,"
                        + KEY_TIME_TASK_REPEAT + " integer,"
                        + KEY_TIME_TASK_REPEAT_TYPE + " text,"
                        + KEY_TIME_TASK_REPEAT_PER + " integer,"
                        + KEY_TIME_TASK_CONFIRMED + " integer,"
                        + KEY_TIME_TASK_STATUS + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_PERSON_ASSISTANT + "("
                        + KEY_PERSON_ASSISTANT_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_PERSON_ASSISTANT_NAME + " text,"
                        + KEY_PERSON_ASSISTANT_SURNAME + " text,"
                        + KEY_PERSON_ASSISTANT_MIDDLENAME + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_PERSON_COMM + "("
                        + KEY_PERSON_COMM_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_PERSON_COMM_ASSIST_ID + " integer,"
                        + KEY_PERSON_COMM_NAME + " text,"
                        + KEY_PERSON_COMM_COMM + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 5)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_TIME_TASK + " add column "
                        + KEY_TIME_TASK_PRIORITY + " integer");
            }

            if (i == 6)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_TIME_TASK + " add column "
                        + KEY_TIME_TASK_REPEAT_SUBTASKS + " text");
            }

            if (i == 7)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_TIME_PROJECT + " add column "
                        + KEY_TIME_PROJECT_STATUS + " text");
            }

            if (i == 8)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_ICON + " integer");
            }

            if (i == 9)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_SUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_CATEGORY + " set "
                        + KEY_MONEY_CATEGORY_SUM_L + "= 100 *" + KEY_MONEY_CATEGORY_SUM);

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_SUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_PNL + " set "
                        + KEY_MONEY_PNL_SUM_L + "= 100 *" + KEY_MONEY_PNL_SUM);

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_PERCENTSUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_PNL + " set "
                        + KEY_MONEY_PNL_PERCENTSUM_L + "= 100 *" + KEY_MONEY_PNL_PERCENTSUM);

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_INC4COST + " add column "
                        + KEY_MONEY_INC4COST_SUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_INC4COST + " set "
                        + KEY_MONEY_INC4COST_SUM_L + "= 100 *" + KEY_MONEY_INC4COST_SUM);

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CREDIT + " add column "
                        + KEY_MONEY_CREDIT_STARTSUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_CREDIT + " set "
                        + KEY_MONEY_CREDIT_STARTSUM_L + "= 100 *" + KEY_MONEY_CREDIT_STARTSUMM);

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CREDIT + " add column "
                        + KEY_MONEY_CREDIT_SUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_CREDIT + " set "
                        + KEY_MONEY_CREDIT_SUM_L + "= 100 *" + KEY_MONEY_CREDIT_SUM);
            }

            if (i == 10)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_INCOMING + " add column "
                        + KEY_MONEY_INCOMING_SUM_L + " integer");

                sqLiteDatabase.execSQL("update " + TABLE_MONEY_INCOMING + " set "
                        + KEY_MONEY_INCOMING_SUM_L + "= 100 *" + KEY_MONEY_INCOMING_SUM);
            }

            if (i == 11)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_ACCUMULATION + "("
                        + KEY_MONEY_ACCUMULATION_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_ACCUMULATION_TARGET_ID + " integer,"
                        + KEY_MONEY_ACCUMULATION_TYPE + " text,"
                        + KEY_MONEY_ACCUMULATION_PROGRESSION_COEF + " integer,"
                        + KEY_MONEY_ACCUMULATION_MONTH_START + " integer,"
                        + KEY_MONEY_ACCUMULATION_YEAR_START + " integer,"
                        + KEY_MONEY_ACCUMULATION_MONTH_END + " integer,"
                        + KEY_MONEY_ACCUMULATION_YEAR_END + " interger,"
                        + KEY_MONEY_ACCUMULATION_START_SUM + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_ACCUMULATION_ID + " integer");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PNL + " add column "
                        + KEY_MONEY_PNL_CALCULATED + " integer");
            }

            if (i == 12)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_INCOMING + " add column "
                        + KEY_MONEY_INCOMING_PERCENT + " real");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_INCOMING + " add column "
                        + KEY_MONEY_INCOMING_TYPE + " text");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_INCOMING + " add column "
                        + KEY_MONEY_INCOMING_EXTERNAL_ID + " integer");

                EnumTypeIncoming typeIncoming = EnumTypeIncoming.Sum;
                sqLiteDatabase.execSQL("update " + TABLE_MONEY_INCOMING + " set "
                        + KEY_MONEY_INCOMING_TYPE + " = '" + typeIncoming + "'");
            }

            if (i == 13)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_TYPE + " text");
            }

            if (i == 14)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_PORTFOLIO + "("
                        + KEY_MONEY_PORTFOLIO_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_PORTFOLIO_PARENT_ID + " integer,"
                        + KEY_MONEY_PORTFOLIO_NAME + " text,"
                        + KEY_MONEY_PORTFOLIO_COMMENT + " text,"
                        + KEY_MONEY_PORTFOLIO_POSITION + " integer,"
                        + KEY_MONEY_PORTFOLIO_STATUS + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 15)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_TYPEASSET + "("
                        + KEY_MONEY_TYPEASSET_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_TYPEASSET_PARENT_ID + " integer,"
                        + KEY_MONEY_TYPEASSET_NAME + " text,"
                        + KEY_MONEY_TYPEASSET_COMMENT + " text,"
                        + KEY_MONEY_TYPEASSET_POSITION + " integer,"
                        + KEY_MONEY_TYPEASSET_STATUS + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 16)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_ASSET + "("
                        + KEY_MONEY_ASSET_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_ASSET_NAME + " text,"
                        + KEY_MONEY_ASSET_TICKER + " text,"
                        + KEY_MONEY_ASSET_COMMENT + " text,"
                        + KEY_MONEY_ASSET_POSITION + " integer,"
                        + KEY_MONEY_ASSET_STATUS + " text,"
                        + KEY_MONEY_ASSET_KIND + " text,"
                        + KEY_MONEY_ASSET_TYPE + " integer,"
                        + KEY_MONEY_ASSET_CURRENCY + " text,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PORTFOLIO + " add column "
                        + KEY_MONEY_PORTFOLIO_WALLET + " integer");
            }

            if (i == 17)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_LOT_ASSET + "("
                        + KEY_MONEY_LOT_ASSET_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID + " integer,"
                        + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + " integer,"
                        + KEY_MONEY_LOT_ASSET_ASSET_ID + " integer,"
                        + KEY_MONEY_LOT_ASSET_POSITION + " integer,"
                        + KEY_MONEY_LOT_ASSET_STATUS + " text,"
                        + KEY_MONEY_LOT_ASSET_COUNT + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_LOT_ASSET_OPERATION + "("
                        + KEY_MONEY_LOT_ASSET_OPERATION_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_LOT_ID + " integer,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + " text,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_YEAR + " integer,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_MONTH + " integer,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_DATE + " integer,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_COUNT + " integer,"
                        + KEY_MONEY_LOT_ASSET_OPERATION_PRICE + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 18)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_NAME + " text");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_COMMENT + " text");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_DATE_START + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_MONTH_START + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_YEAR_START + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_DATE_END + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_MONTH_END + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET + " add column "
                        + KEY_MONEY_LOT_ASSET_YEAR_END + " integer");
            }

            if (i == 19)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_PORTFOLIO + " add column "
                        + KEY_MONEY_PORTFOLIO_SUBCATEGORY + " integer");
            }

            if (i == 20)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET_OPERATION + " add column "
                        + KEY_MONEY_LOT_ASSET_OPERATION_ACCUMULATED_PRICE + " integer");
            }

            if (i == 21)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET_OPERATION + " add column "
                        + KEY_MONEY_LOT_ASSET_OPERATION_COMISSION + " integer");
            }

            if (i == 22)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_ASSET + " add column "
                        + KEY_MONEY_ASSET_WALLET_ID + " integer");
            }

            if (i == 23)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_ASSET + " add column "
                        + KEY_MONEY_ASSET_CREDIT_ID + " integer");
            }

            if (i == 24)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CREDIT + " add column "
                        + KEY_MONEY_CREDIT_INC4COST_CATEGORY + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CREDIT + " add column "
                        + KEY_MONEY_CREDIT_INC4COST_SUBCATEGORY + " integer");
            }

            if (i == 26)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET_OPERATION + " add column "
                        + KEY_MONEY_LOT_ASSET_OPERATION_CATEGORY + " integer");
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_LOT_ASSET_OPERATION + " add column "
                        + KEY_MONEY_LOT_ASSET_OPERATION_SUBCATEGORY + " integer");
            }

            if (i == 27)
            {
                sqLiteDatabase.execSQL("update " + TABLE_MONEY_PNL + " set "
                        + KEY_MONEY_PNL_ISPLAN + " = 0 where " +
                        KEY_MONEY_PNL_ISPLAN + " <> 1");

            }

            if (i == 28)
            {
                sqLiteDatabase.execSQL("update " + TABLE_MONEY_PNL + " set "
                        + KEY_MONEY_PNL_ISPLAN + " = 0 where " +
                        KEY_MONEY_PNL_ISPLAN + " = null");

            }

            if (i == 29)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_CREDIT_PIVOT_POINT + "("
                        + KEY_MONEY_CREDIT_PIVOT_POINT_ID + " integer primary key,"
                        + KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID + " integer,"
                        + KEY_MONEY_CREDIT_PIVOT_POINT_STARTSUM_L + " integer,"
                        + KEY_MONEY_CREDIT_PIVOT_POINT_PERCENT + " real,"
                        + KEY_MONEY_CREDIT_PIVOT_POINT_LOANTERM + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 30)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CREDIT_PIVOT_POINT + " add column "
                        + KEY_ALL_CABINET_ID + " integer");
            }

            if (i == 31)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_DREAM + " add column "
                        + KEY_MONEY_DREAM_ARCHIVE + " text");
            }

            if (i == 32)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_PERIODPLAN + "("
                        + KEY_MONEY_PERIODPLAN_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_PERIODPLAN_CATEGORY_ID + " integer,"
                        + KEY_MONEY_PERIODPLAN_MONTH + " integer,"
                        + KEY_MONEY_PERIODPLAN_YEAR + " integer,"
                        + KEY_MONEY_PERIODPLAN_SUM_L + " integer,"
                        + KEY_MONEY_PERIODPLAN_TYPE + " text,"
                        + KEY_MONEY_PERIODPLAN_EXTERNAL_ID + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 33)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_TAG + "("
                        + KEY_TAG_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_TAG_NAME + " text,"
                        + KEY_TAG_COMMENT + " text,"
                        + KEY_TAG_TYPEDATE + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");

                sqLiteDatabase.execSQL("create table " + TABLE_TAGGED + "("
                        + KEY_TAGGED_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_TAGGED_TAGID + " integer,"
                        + KEY_TAGGED_EXTERNALID + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }

            if (i == 34)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_TAG + " add column "
                        + KEY_TAG_ARCHIVE + " text");
            }

            if (i == 35)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_CATEGORY + " add column "
                        + KEY_MONEY_CATEGORY_ARCHIVE + " integer");
            }

            if (i == 36)
            {
                sqLiteDatabase.execSQL("alter table " + TABLE_MONEY_INCREASE + " add column "
                        + KEY_MONEY_INCREASE_ARCHIVE + " integer");
            }

            if (i == 37)
            {
                sqLiteDatabase.execSQL("create table " + TABLE_MONEY_BOARD + "("
                        + KEY_MONEY_BOARD_ID + " integer primary key,"
                        + KEY_ALL_CABINET_ID + " integer,"
                        + KEY_MONEY_BOARD_NAME + " text,"
                        + KEY_MONEY_BOARD_POSITION + " integer,"
                        + KEY_MONEY_BOARD_VISIBLE + " integer,"
                        + KEY_ALL_DATETIME_CREATE + " text,"
                        + KEY_ALL_DATETIME_UPDATE + " text" + ")");
            }
        }
    }
    //endregion

    //region Cabinet
    private ContentValues PutCCabinet(CCabinet cCabinet, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_CABINET_NAME, cCabinet.get_name());

        Calendar calendar = Calendar.getInstance();
        if (typeAction == EnumTypeAction.Insert)
            contentValues.put(DBHelper.KEY_ALL_DATETIME_CREATE, calendar.getTime().toString());
        else if (typeAction == EnumTypeAction.Update)
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

        return contentValues;
    }

    public Cursor QueryCCabinet(Integer cCabinetId) throws Exception
    {
        try
        {
            String[] params = null;
            if (cCabinetId != null)
                params = new String[] { cCabinetId.toString() };

            return getReadableDatabase().query(DBHelper.TABLE_CABINET,
                    null,
                    cCabinetId == null ? null : KEY_CABINET_ID + " = ?",
                    params, null, null, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Long AddCCabinet(CCabinet cCabinet) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_CABINET, null,
                    PutCCabinet(cCabinet, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateCCabinet(CCabinet cCabinet) throws Exception
    {
        Long id = cCabinet.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_CABINET,
                    PutCCabinet(cCabinet, EnumTypeAction.Update),
                    DBHelper.KEY_CABINET_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteCCabinet(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_CABINET,
                        DBHelper.KEY_CABINET_ID + "=" + id, null);

                if (delCount == 1) {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_CABINET, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1) {
                        sqLiteDatabase.setTransactionSuccessful();
                        return b;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region DelObjects
    private ContentValues PutCDelObject(CDelObject cDelObject)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_DEL_OBJECT_NAME, cDelObject.get_name());
        contentValues.put(DBHelper.KEY_DEL_OBJECT_NUM, cDelObject.get_num());

        return PutCustom(contentValues, EnumTypeAction.Insert);
    }

    public Cursor QueryCDelObject() throws Exception
    {
        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_DEL_OBJECT,
                    null, null,
                    null, null, null, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Long AddCDelObject(CDelObject cDelObject) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_DEL_OBJECT, null,
                    PutCDelObject(cDelObject));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public int AddCDelObject(SQLiteDatabase sqLiteDatabase, CDelObject cDelObject) throws Exception
    {
        try
        {
            return (int) sqLiteDatabase.insert(DBHelper.TABLE_DEL_OBJECT, null,
                    PutCDelObject(cDelObject));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteCDelObject(Long id) throws Exception
    {
        if (id == 0L)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            int delCount = sqLiteDatabase.delete(DBHelper.TABLE_DEL_OBJECT,
                    DBHelper.KEY_DEL_OBJECT_ID + "=" + id, null);

            return delCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region Param
    public int GetCountParam() throws Exception { return GetCount(TABLE_PARAM); }

    public Cursor QueryParam() throws Exception
    {
        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_PARAM,
                    null, null,
                    null, null, null,
                    KEY_PARAM_NAME);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutParam(Param param, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_PARAM_NAME, param.get_name());
        contentValues.put(DBHelper.KEY_PARAM_COMMENT, param.get_comment());
        contentValues.put(DBHelper.KEY_PARAM_MEASURE, param.get_measure());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddParam(Param param) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_PARAM, null,
                    PutParam(param, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateParam(Param param) throws Exception
    {
        Long id = param.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_PARAM,
                    PutParam(param, EnumTypeAction.Update),
                    DBHelper.KEY_PARAM_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteParam(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_PARAM,
                        DBHelper.KEY_PARAM_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_PARAM, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region PlanValue
    public int GetCountPlanValue() throws Exception { return GetCount(TABLE_PLANVALUE); }

    public Cursor QueryPlanValue(int targetId) throws Exception
    {
        try
        {
            String sql =
                    "select " + TABLE_PLANVALUE + ".*, param. " + KEY_PARAM_NAME +
                            ", param." + KEY_PARAM_MEASURE +
                            " from " + TABLE_PLANVALUE +
                            " join " + TABLE_PARAM + " param on param." + KEY_PARAM_ID +
                            "=" + TABLE_PLANVALUE + "." + KEY_PLANVALUE_PARAM_ID +
                            " WHERE " + TABLE_PLANVALUE + "." + KEY_PLANVALUE_TARGET_ID + "=?";


            return getReadableDatabase().rawQuery(sql, new String[] {String.valueOf(targetId)});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public Long QueryPLanValueMaxId() throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_PLANVALUE_ID + ") as _max from " + TABLE_PLANVALUE;
            cursor = database.rawQuery(sqlQuery, new String[]{ });
            if (cursor.moveToFirst())
            {
                return cursor.getLong(cursor.getColumnIndex("_max"));
            }
            else
                return 0L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    private ContentValues PutPlanValue(PlanValue planValue, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_PLANVALUE_TARGET_ID, planValue.get_target_id());
        contentValues.put(DBHelper.KEY_PLANVALUE_PARAM_ID, planValue.get_param_id());
        contentValues.put(DBHelper.KEY_PLANVALUE_PV_SRC1, planValue.get_src_planValue_id1());
        contentValues.put(DBHelper.KEY_PLANVALUE_PV_SRC2, planValue.get_src_planValue_id2());
        contentValues.put(DBHelper.KEY_PLANVALUE_TYPE, planValue.get_type().toString());
        contentValues.put(DBHelper.KEY_PLANVALUE_COEFFICIENT, planValue.get_coeff());
        contentValues.put(DBHelper.KEY_PLANVALUE_VALUE, planValue.get_value());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddPlanValue(PlanValue planValue) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_PLANVALUE, null,
                    PutPlanValue(planValue, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdatePlanValue(PlanValue planValue) throws Exception
    {
        Long id = Long.valueOf(planValue.get_id());

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_PLANVALUE,
                    PutPlanValue(planValue, EnumTypeAction.Update),
                    DBHelper.KEY_PLANVALUE_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeletePlanValue(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_PLANVALUE,
                        DBHelper.KEY_PLANVALUE_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_PLANVALUE, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region Value
    public int GetCountValue() throws Exception { return GetCount(TABLE_VALUE); }

    public Cursor QueryValue() throws Exception
    {
        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_VALUE,
                    null, null,
                    null, null, null,
                    KEY_VALUE_PARAM);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutValue(Value value, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_VALUE_PARAM, value.get_param_id());
        contentValues.put(DBHelper.KEY_VALUE_TASK, value.get_task_id());
        contentValues.put(DBHelper.KEY_VALUE_VALUE, value.get_value());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddValue(Value value) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_VALUE, null,
                    PutValue(value, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateValue(Value value) throws Exception
    {
        Long id = value.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_VALUE,
                    PutValue(value, EnumTypeAction.Update),
                    DBHelper.KEY_VALUE_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteValue(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_VALUE,
                        DBHelper.KEY_VALUE_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_VALUE, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region Tag
    public int GetCountTag() throws Exception { return GetCount(TABLE_TAG); }

    @SuppressLint("Recycle")
    public Cursor QueryTag(TagSettings tagSettings,
                           boolean hasUnpaidSubscription) throws Exception
    {
        try
        {
            StringBuilder sql = new StringBuilder();
            sql.append("select ").append(TABLE_TAG).append(".*,").
                    append("tagged.externalCount as ").append(key_TAG_COUNTEXTERNAL).
                    append(" from ").append(TABLE_TAG);

            sql.append(" left join (select " + KEY_TAGGED_ID + ", " + KEY_TAGGED_TAGID +
                    ", count (" + KEY_TAGGED_EXTERNALID + ") as externalCount" +
                    " from " + TABLE_TAGGED +
                    " group by " + KEY_TAGGED_TAGID +
                    ") tagged on tagged." + KEY_TAGGED_TAGID +
                    " = " + TABLE_TAG + "." + KEY_TAG_ID);

            sql.append(" where ").append(TABLE_TAG + "." + KEY_TAG_TYPEDATE + " = '" +
                    tagSettings.get_tagTypeData().toString() + "'");

            if (tagSettings.get_showArchive() != null &&
                    !tagSettings.get_showArchive() && !hasUnpaidSubscription)
            {
                sql.append(" AND " + TABLE_TAG + "." + KEY_TAG_ARCHIVE + "=0");
            }

            sql.append(" order by " + KEY_TAG_NAME);

            return getReadableDatabase().rawQuery(sql.toString(),
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutTag(Tag tag, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TAG_NAME, tag.get_name());
        contentValues.put(DBHelper.KEY_TAG_COMMENT, tag.get_comment());
        contentValues.put(DBHelper.KEY_TAG_TYPEDATE, tag.get_tagTypeData().toString());
        contentValues.put(DBHelper.KEY_TAG_ARCHIVE, tag.get_archiveI());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddTag(Tag tag) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_TAG, null,
                    PutTag(tag, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateTag(Tag tag) throws Exception
    {
        Long id = tag.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_TAG,
                    PutTag(tag, EnumTypeAction.Update),
                    DBHelper.KEY_TAG_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteTag(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_TAG,
                        DBHelper.KEY_TAG_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_TAG, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region Tagged
    public int GetCountTagged() throws Exception { return GetCount(TABLE_TAGGED); }

    public Cursor QueryTagged(EnumTagTypeData tagTypeData, Long externalId) throws Exception
    {
        try
        {
            StringBuilder sql = new StringBuilder();
            sql.append("select ").append(TABLE_TAGGED).append(".*,").
                    append(TABLE_TAG).append(".").append(KEY_TAG_NAME).append(",").
                    append(TABLE_TAG).append(".").append(KEY_TAG_COMMENT).append(",").
                    append(TABLE_TAG).append(".").append(KEY_TAG_TYPEDATE).
                    append(" from ").append(TABLE_TAGGED)

            .append("  join " + TABLE_TAG).append(" on " + TABLE_TAG +
                    "." + KEY_TAG_ID + " = " + TABLE_TAGGED + "." + KEY_TAGGED_TAGID);

            sql.append(" where ").append(TABLE_TAG + "." + KEY_TAG_TYPEDATE + " = '").
                    append(tagTypeData.toString()).append("'");
            if (externalId != null)
                sql.append(" and " + TABLE_TAGGED + "." + KEY_TAGGED_EXTERNALID + "=" + externalId);

            return getReadableDatabase().rawQuery(sql.toString(),
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }


    private ContentValues PutTagged(Tagged tagged, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TAGGED_TAGID, tagged.get_tag().get_id());
        contentValues.put(DBHelper.KEY_TAGGED_EXTERNALID, tagged.get_externalId());

        return PutCustom(contentValues, typeAction);
    }
    //endregion

    //region Custom
    private ContentValues PutCustom(ContentValues contentValues, EnumTypeAction typeAction)
    {
        Calendar calendar = Calendar.getInstance();
        if (typeAction == EnumTypeAction.Insert)
        {
            contentValues.put(KEY_ALL_CABINET_ID, _cCabinet.get_id());
            contentValues.put(KEY_ALL_DATETIME_CREATE, calendar.getTime().toString());
        }
        else if (typeAction == EnumTypeAction.Update)
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

        return contentValues;
    }

    @SuppressLint({"Range", "Recycle"})
    public int GetCount(String tableName, String where) throws Exception
    {
        Cursor cursor;
        try
        {
            String sql = "select count (*) as _count from " + tableName;
            if (where != null && where != "")
                sql += " where " + where;

            cursor = getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToFirst())
                return cursor.getInt(cursor.getColumnIndex("_count"));

            return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public int GetCount(String tableName) throws Exception
    {
        return GetCount(tableName, "");
    }

    @SuppressLint({"Range", "Recycle"})
    public int GetCount(String tableName, ArrayList<Integer> excludedId) throws Exception
    {
        Cursor cursor;
        try
        {
            String sql = "select count (*) as _count from " + tableName;

            cursor = getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToFirst())
                return cursor.getInt(cursor.getColumnIndex("_count"));

            return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Recycle")
    public void CorrectIncorrectData(Context context) throws Exception
    {
//        getWritableDatabase().execSQL("update " + TABLE_MONEY_LOT_ASSET + " set " +
//                KEY_MONEY_LOT_ASSET_DATE_END + "=31, " +
//                KEY_MONEY_LOT_ASSET_MONTH_END + "=11, " +
//                KEY_MONEY_LOT_ASSET_YEAR_END + "=2900");
//        getWritableDatabase().execSQL("delete from " + TABLE_MONEY_PORTFOLIO);
//        getWritableDatabase().execSQL("delete from " + TABLE_MONEY_TYPEASSET);
//        getWritableDatabase().execSQL("delete from " + TABLE_MONEY_ASSET);
//        getWritableDatabase().execSQL("delete from " + TABLE_MONEY_LOT_ASSET);
//        getWritableDatabase().execSQL("delete from " + TABLE_MONEY_LOT_ASSET_OPERATION);
    }
    //endregion

    //region MoneyDream
    public int GetCountMoneyDream() throws Exception { return GetCount(TABLE_MONEY_DREAM); }

    public Cursor QueryMoneyDream(Long dreamId,
                                  MoneyDreamSettings moneyDreamSettings,
                                  boolean hasUnpaidSubscription) throws Exception
    {
        try
        {
            boolean where = false;
            String sql =
                    "select " + TABLE_MONEY_DREAM + ".*, target1." + key_MONEY_DREAM_TARGET_COUNT +
                            " from " + TABLE_MONEY_DREAM +

                            " left join (select " + KEY_MONEY_TARGET_DREAM_ID + ", count (" +
                            KEY_MONEY_DREAM_ID + ") as " + key_MONEY_DREAM_TARGET_COUNT +
                            " from " + TABLE_MONEY_TARGET;

            if (dreamId != null)
                sql += " where " + KEY_MONEY_TARGET_DREAM_ID + " = ?";

            sql += " group by " + KEY_MONEY_TARGET_DREAM_ID + ") target1 " +

                    "on target1." + KEY_MONEY_TARGET_DREAM_ID + "=" + TABLE_MONEY_DREAM + "."+
                    KEY_MONEY_DREAM_ID;

            if (dreamId != null)
            {
                sql += " where " + TABLE_MONEY_DREAM + "." + KEY_MONEY_DREAM_ID + "=? ";
                where = true;
            }

            if (moneyDreamSettings != null)
            {
                if (!moneyDreamSettings.get_showArchive() && !hasUnpaidSubscription)
                {
                    if (!where)
                        sql += " where " + TABLE_MONEY_DREAM + "." + KEY_MONEY_DREAM_ARCHIVE + "=0";
                    else
                        sql += " AND " + TABLE_MONEY_DREAM + "." + KEY_MONEY_DREAM_ARCHIVE + "=0";
                }
            }

            sql += " order by " +  TABLE_MONEY_DREAM + "."+ KEY_MONEY_DREAM_POSITION;

            String[] params = new String[]{};
            if (dreamId != null)
                params = new String[] {dreamId.toString(), dreamId.toString()};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyDream(MoneyDream moneyDream, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_DREAM_POSITION, moneyDream.get_position());
        contentValues.put(DBHelper.KEY_MONEY_DREAM_NAME, moneyDream.get_name());
        contentValues.put(DBHelper.KEY_MONEY_DREAM_IMAGE_PATH, moneyDream.get_imagePath());
        contentValues.put(DBHelper.KEY_MONEY_DREAM_IMAGE_URL, moneyDream.get_imageUrl());
        contentValues.put(DBHelper.KEY_MONEY_DREAM_ARCHIVE, moneyDream.is_archiveI());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyDream(MoneyDream moneyDream) throws Exception
    {
        try
        {
            int position = QueryTasksProjectMaxPosition(0L);
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                Long dreamId = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_DREAM, null,
                        PutMoneyDream(moneyDream, EnumTypeAction.Insert));
                b = dreamId > 0;

                if (b)
                {
                    TasksProject tasksProject = new TasksProject(0L, moneyDream.get_name(), "",
                            0L, dreamId, position,
                            0, 0, 0, 0, 0,
                            moneyDream.getStatus());
                    b = sqLiteDatabase.insert(TABLE_TIME_PROJECT, null,
                            PutTasksProject(tasksProject, EnumTypeAction.Insert)) > 0;
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return dreamId;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyDream(MoneyDream moneyDream) throws Exception
    {
        Long id = moneyDream.get_id();

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_DREAM,
                        PutMoneyDream(moneyDream, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_DREAM_ID + "= ?", new String[]{String.valueOf(id)}) == 1;

                if (b)
                    b = sqLiteDatabase.update(TABLE_TIME_PROJECT,
                            PutTasksProjectName(moneyDream.get_name(), moneyDream.getStatus().toString()),
                            DBHelper.KEY_TIME_PROJECT_TARGET_ID + "= ? AND " +
                            KEY_TIME_PROJECT_PARENT_ID + "=0",
                            new String[] { String.valueOf(id) }) == 1;

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return b;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyDreamPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MONEY_DREAM_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_DREAM,
                    contentValues, DBHelper.KEY_MONEY_DREAM_ID + "= ?", new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyDream(Long id) throws Exception
    {
        if (id == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_DREAM,
                        DBHelper.KEY_MONEY_DREAM_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_DREAM, id);
                    b = AddCDelObject(sqLiteDatabase, cDelObject) > 0;
                }

                if (b)
                {
                    sqLiteDatabase.delete(TABLE_TIME_PROJECT,
                            DBHelper.KEY_TIME_PROJECT_TARGET_ID + "= ? AND " +
                                    KEY_TIME_PROJECT_PARENT_ID + "=0",
                            new String[] { String.valueOf(id) });
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public int QueryMoneyDreamMaxPosition() throws Exception {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_DREAM_POSITION + ") as _max from " + TABLE_MONEY_DREAM;
            cursor = database.rawQuery(sqlQuery, new String[]{ });
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    @SuppressLint("Range")
    public Long QueryMoneyDreamNextId() throws Exception {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_DREAM_ID + ") as _max from " + TABLE_MONEY_DREAM;
            cursor = database.rawQuery(sqlQuery, new String[]{ });
            if (cursor.moveToFirst())
            {
                return cursor.getLong(cursor.getColumnIndex("_max")) + 1;
            }
            else
                return 1L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }
    //endregion

    //region MoneyTarget
    public int GetCountMoneyTarget() throws Exception { return GetCount(TABLE_MONEY_TARGET); }

    public Cursor QueryMoneyTarget(Long dreamId,
                                   Integer year,
                                   EnumTargetStatus4Settings enumTargetStatus4Settings,
                                   MoneyTargetSettings moneyTargetSettings) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String dateS = String.valueOf(calendar.get(Calendar.DATE));
        String monthS = String.valueOf(calendar.get(Calendar.MONTH));
        String yearS = String.valueOf(calendar.get(Calendar.YEAR));

        ArrayList<EnumStatus> statusList0 = new ArrayList<>();
        if (enumTargetStatus4Settings != null)
            statusList0 = MoneyTargetSettings.get_statusList(enumTargetStatus4Settings);

        ArrayList<EnumStatus> statusList = new ArrayList<>();
        if (moneyTargetSettings != null)
                statusList = moneyTargetSettings.get_statusList();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(TABLE_MONEY_TARGET).append(".*,").
                append(TABLE_MONEY_CATEGORY).append(".").append(KEY_MONEY_CATEGORY_TARGET_ID).
                append(" as ").append(key_MONEY_TARGET_CATEGORY_ID).
                append(", tasks1.taskCount1 as ").append(key_MONEY_TARGET_TASK_COUNT1).
                append(", tasks2.taskCount2 as ").append(key_MONEY_TARGET_TASK_COUNT2).
                append(" from ").append(TABLE_MONEY_TARGET).
                append(" left join ").append(TABLE_MONEY_CATEGORY).append(" on (").
                append(TABLE_MONEY_TARGET).append(".").append(KEY_MONEY_TARGET_ID).
                append("=").append(TABLE_MONEY_CATEGORY).append(".").
                append(KEY_MONEY_CATEGORY_TARGET_ID).append(" AND ").
                append(TABLE_MONEY_CATEGORY).append(".").append(KEY_MONEY_CATEGORY_TYPE).
                append("='").append(EnumTypeCategory.Wallet).append("')");

        sql.append(" left join (select * from " + TABLE_TIME_PROJECT +
                " where " + KEY_TIME_PROJECT_PARENT_ID + ">0" +
                ") projects1 on projects1." + KEY_TIME_PROJECT_TARGET_ID +
                "=" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_ID);

        sql.append(" left join (select " + KEY_TIME_TASK_PROJECT_ID +
                ", count (" + KEY_TIME_TASK_ID + ") as taskCount1" +
                " from " + TABLE_TIME_TASK +
                " where " + KEY_TIME_TASK_CONFIRMED + "=0" +
                " AND " + KEY_TIME_TASK_PARENT_ID + "=0" +
                " group by " + KEY_TIME_TASK_PROJECT_ID +
                ") tasks1 on tasks1." + KEY_TIME_TASK_PROJECT_ID +
                "=projects1." + KEY_TIME_PROJECT_ID);

        sql.append(" left join (select " + KEY_TIME_TASK_PROJECT_ID +
                ", count (" + KEY_TIME_TASK_ID + ") as taskCount2" +
                " from " + TABLE_TIME_TASK +
                " where " + KEY_TIME_TASK_CONFIRMED + "=0" +
                " AND " + KEY_TIME_TASK_PARENT_ID + "=0" +
                " AND (" + KEY_TIME_TASK_YEAR_INT_PLAN + "<" + yearS + " OR (" +
                KEY_TIME_TASK_YEAR_INT_PLAN + "=" + yearS + " AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "<" + monthS + ") OR (" +
                KEY_TIME_TASK_YEAR_INT_PLAN + "=" + yearS + " AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "=" + monthS + " AND " +
                KEY_TIME_TASK_DATE_INT_PLAN + "<=" + dateS + "))" +
                " group by " + KEY_TIME_TASK_PROJECT_ID +
                ") tasks2 on tasks2." + KEY_TIME_TASK_PROJECT_ID +
                "=projects1." + KEY_TIME_PROJECT_ID);

        if (dreamId == null)
        {
            if (year == null)
            {
                if (statusList0.size() > 0)
                    sql.append(" where (");
            }
            else
                sql.append(" where (" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_YEAR + " = ").append(year);

            if (statusList0.size() > 0)
            {
                if (year != null)
                    sql.append(" AND ");
                sql.append(" (" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_STATUS + " = '").append(statusList0.get(0).toString()).append("'");
                for (int i = 1; i < statusList0.size(); i++)
                    sql.append(" OR " + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_STATUS + " = '").append(statusList0.get(i).toString()).append("'");
                sql.append(")");
            }

            if (year != null || (statusList0.size() > 0))
                sql.append(")");
        }
        else
        {
            sql.append(" where (").append(KEY_MONEY_TARGET_DREAM_ID).append(" = ").append(dreamId);

            if (statusList.size() > 0)
            {
                sql.append(" AND (" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_STATUS + " = '").append(statusList.get(0).toString()).append("'");
                for (int i = 1; i < statusList.size(); i++)
                    sql.append(" OR " + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_STATUS + " = '").append(statusList.get(i).toString()).append("'");
                sql.append(")");
            }

            sql.append(")");
        }

        sql.append(" order by " + KEY_MONEY_TARGET_YEAR + ", " + KEY_MONEY_TARGET_MONTH);

        try
        {
            return getReadableDatabase().rawQuery(sql.toString(), null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public Integer LoadTargetIdByWalletId(Long walletId)
    {
        try
        {
            Cursor cursor = getReadableDatabase().query(TABLE_MONEY_CATEGORY,
                    null,
                    KEY_MONEY_CATEGORY_ID + "=" + walletId,
                    null, null, null,
                    null);

            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex(KEY_MONEY_CATEGORY_TARGET_ID));
            }

            return 0;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public Cursor QueryMoneyTarget4Status(Integer dreamId) throws Exception
    {
        try
        {
            String sql =
                    "select " + KEY_MONEY_TARGET_DREAM_ID + ", count(" +
                            TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_ID + ") as " +
                            key_MONEY_TARGET_COUNT + ", " + KEY_MONEY_TARGET_STATUS +
                            " from " + TABLE_MONEY_TARGET;

            sql += " where " + KEY_MONEY_TARGET_STATUS + "<> ?";

            sql += " group by " + KEY_MONEY_TARGET_DREAM_ID + ", " + KEY_MONEY_TARGET_STATUS;

            if (dreamId != null)
                sql += " where " + KEY_MONEY_TARGET_DREAM_ID + "=?";

            sql += " order by " +  KEY_MONEY_TARGET_DREAM_ID;

            String[] params = new String[]{EnumStatus.Irrelevant.toString()};
            if (dreamId != null)
                params = new String[] {EnumStatus.Irrelevant.toString(), dreamId.toString()};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyTarget(MoneyTarget moneyTarget, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_TARGET_DREAM_ID, moneyTarget.get_dreamId());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_MONTH, moneyTarget.get_month());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_YEAR, moneyTarget.get_year());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_NAME, moneyTarget.get_name());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_PRICE, moneyTarget.get_price());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_ACCUMULATION, moneyTarget.get_accumulation());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_CREDIT, moneyTarget.get_credit());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_IMAGE_PATH, moneyTarget.get_imagePath());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_IMAGE_URL, moneyTarget.get_imageUrl());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_STATUS, moneyTarget.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_TARGET_HEAD_ID, moneyTarget.get_parentId());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyTarget(MoneyTarget moneyTarget, Context context) throws Exception
    {
        try
        {
            Long parentProjectId = 0L;
//            if (moneyTarget.get_parentId() != 0)
//                QueryTasksProject4Target(moneyTarget.get_parentId());
            if (parentProjectId == 0)
                parentProjectId = QueryTasksProject4Dream(moneyTarget.get_dreamId());
            int position = QueryTasksProjectMaxPosition(parentProjectId) + 1;
            int walletMaxPosition = QueryMoneyCategoryMaxPosition(0L, EnumTypeCategory.Wallet) + 1;
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            Long id = -1L;
            boolean b;

            try
            {
                id = sqLiteDatabase.insert(
                        DBHelper.TABLE_MONEY_TARGET, null,
                        PutMoneyTarget(moneyTarget, EnumTypeAction.Insert));
                moneyTarget.set_id(id);
                b = id > 0;

                MoneyAccumulation moneyAccumulation = moneyTarget.get_moneyAccumulation();
                if (b && moneyAccumulation != null)
                {
                    moneyAccumulation.set_targetId(id);
                    long ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_ACCUMULATION, null,
                            PutMoneyAccumulation(moneyAccumulation, EnumTypeAction.Insert));
                    moneyAccumulation.set_id(ii);
                    b = ii > 0;

                    MoneyCategory wallet = null;
                    if (b)
                    {
                        wallet = new MoneyCategory(0L, 0L,
                                walletMaxPosition,
                                moneyTarget.get_name(),
                                0L, EnumTypeCategory.Wallet, false, false,
                                false, true, moneyTarget.get_id(),
                                EnumCategoryType.Base,
//                                EnumCategoryStatus.Active,
                                0L,
                                false,
                                0L);
                        ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CATEGORY, null,
                                PutMoneyCategory(wallet, EnumTypeAction.Insert));
                        wallet.set_id(ii);
                        b = ii > 0;
                    }

                    if (b)
                        for (MoneyPNL item: moneyAccumulation.get_pnls())
                        {
                            item.set_accumulationId(moneyAccumulation.get_id());
                            item.set_IncreaseCategoryId(wallet.get_id());
                            item.set_incCategoryName(wallet.get_name());
                            ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_PNL, null,
                                    PutMoneyPNL(item, EnumTypeAction.Insert));
                            item.set_id(ii);
                            b = b && ii > 0;

                            if (b && !item.get_isPlan())
                            {
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        item.getIncreaseCategoryId(), item.get_totalSumLong());

                                b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        item.getCostCategoryId(), -item.get_totalSumLong());
                            }
                        }
                }

                long ii;
                if (b && moneyTarget.get_planValues() != null)
                    for (PlanValue planValue: moneyTarget.get_planValues())
                    {
                        planValue.set_target_id(id);
                        ii = sqLiteDatabase.insert(DBHelper.TABLE_PLANVALUE, null,
                                PutPlanValue(planValue, EnumTypeAction.Insert));
                        planValue.set_id(ii);
                        b = b && ii > 0;
                    }

                if (b)
                {
                    TasksProject tasksProject = new TasksProject(0L, moneyTarget.get_name(),
                            "", parentProjectId,
                            id,
                            position, 0, 0, 0, 0,
                            0, moneyTarget.get_status());
                    b = sqLiteDatabase.insert(TABLE_TIME_PROJECT, null,
                            PutTasksProject(tasksProject, EnumTypeAction.Insert)) > 0;
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            catch (Exception e)
            {
                Log.d("MoneyTarget Insert", e.getMessage());
                b = false;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            if (b)
                return id;
            else
                return -1L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyTarget(MoneyTarget moneyTarget) throws Exception
    {
        Long id = Long.valueOf(moneyTarget.get_id());

        try
        {
            int walletMaxPosition = QueryMoneyCategoryMaxPosition(0L, EnumTypeCategory.Wallet) + 1;
            Long walletId = OneMoneyCategoryId4Target(id, EnumTypeCategory.Wallet);

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            int updateCount;
            boolean b;

            try
            {
                updateCount = sqLiteDatabase.update(DBHelper.TABLE_MONEY_TARGET,
                        PutMoneyTarget(moneyTarget, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_TARGET_ID + "= ?", new String[] {id.toString()});
                b = updateCount == 1;

                if (b)
                {
                    MoneyAccumulation moneyAccumulation = moneyTarget.get_moneyAccumulation();
                    MoneyAccumulation oldMoneyAccumulation = moneyTarget.get_oldMoneyAccumulation();

                    if (moneyAccumulation == null && oldMoneyAccumulation != null)
                    {
                        int cnt = sqLiteDatabase.delete(TABLE_MONEY_ACCUMULATION,
                                DBHelper.KEY_MONEY_ACCUMULATION_TARGET_ID + "=?",
                                new String[]{String.valueOf(id)});
                        b = cnt == 1;

                        boolean existPnl = false;
                        if (b)
                            for (MoneyPNL item: oldMoneyAccumulation.get_pnls())
                            {
                                if (item.get_isPlan())
                                {
                                    b = b && sqLiteDatabase.delete(TABLE_MONEY_PNL,
                                            DBHelper.KEY_MONEY_PNL_ID + "=?",
                                            new String[]{String.valueOf(item.get_id())}) == 1;

                                    if (!item.get_oldIsPlan())
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldCostCategoryId(), item.get_oldTotalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldIncreaseCategoryId(), -item.get_oldTotalSumLong());
                                    }
                                }
                                else
                                {
                                    b = b && sqLiteDatabase.update(DBHelper.TABLE_MONEY_PNL,
                                            PutMoneyPNLAccumulationIdAndCalculated(0, false,EnumTypeAction.Update),
                                            DBHelper.KEY_MONEY_PNL_ID + "= ?",
                                            new String[]{String.valueOf(item.get_id())}) == 1;

                                    if (!item.get_oldIsPlan())
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldCostCategoryId(), item.get_oldTotalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldIncreaseCategoryId(), -item.get_oldTotalSumLong());
                                    }

                                    if (!item.get_isPlan())
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getCostCategoryId(), -item.get_totalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getIncreaseCategoryId(), item.get_totalSumLong());
                                    }

                                    existPnl = true;
                                }
                            }

                        if (b)
                        {
                            if (existPnl)
                            {
                                cnt = sqLiteDatabase.update(DBHelper.TABLE_MONEY_CATEGORY,
                                        PutMoneyCategoryNameAndClearTargetId(moneyTarget.get_name()),
                                        DBHelper.KEY_MONEY_CATEGORY_ID + "= ?",
                                        new String[]{String.valueOf(walletId)});
                                b = cnt == 1;
                            }
                            else
                            {
                                b = sqLiteDatabase.delete(TABLE_MONEY_CATEGORY,
                                        DBHelper.KEY_MONEY_CATEGORY_ID + "=?",
                                        new String[]{ String.valueOf(walletId) }) == 1;
                            }
                        }
                    }
                    else if (moneyAccumulation != null && oldMoneyAccumulation != null)
                    {
                        b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_ACCUMULATION,
                                PutMoneyAccumulation(moneyAccumulation, EnumTypeAction.Update),
                                DBHelper.KEY_MONEY_ACCUMULATION_ID + "= ?",
                                new String[] {moneyAccumulation.get_id().toString()}) == 1;

                        if (b)
                        {
                            b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_CATEGORY,
                                    PutMoneyCategoryName(moneyTarget.get_name()),
                                    DBHelper.KEY_MONEY_CATEGORY_ID + "= ?",
                                    new String[] { String.valueOf(walletId) }) == 1;
                        }

                        if (b)
                            for (MoneyPNL item : moneyAccumulation.get_pnls())
                            {
                                if (item.get_id() > 0)
                                {
                                    item.set_accumulationId(moneyAccumulation.get_id());
                                    item.set_IncreaseCategoryId(walletId);
                                    item.set_incCategoryName(moneyTarget.get_name());
                                    b = b && sqLiteDatabase.update(DBHelper.TABLE_MONEY_PNL,
                                            PutMoneyPNL(item, EnumTypeAction.Update),
                                            DBHelper.KEY_MONEY_PNL_ID + "= ?",
                                            new String[]{item.get_id().toString()}) == 1;

                                    if (!item.get_oldIsPlan())
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldCostCategoryId(), item.get_oldTotalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldIncreaseCategoryId(), -item.get_oldTotalSumLong());
                                    }

                                    if (!item.get_isPlan())
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getCostCategoryId(), -item.get_totalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getIncreaseCategoryId(), item.get_totalSumLong());
                                    }
                                }
                                else
                                {
                                    item.set_accumulationId(moneyAccumulation.get_id());
                                    item.set_IncreaseCategoryId(walletId);
                                    item.set_incCategoryName(moneyTarget.get_name());
                                    long ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_PNL, null,
                                            PutMoneyPNL(item, EnumTypeAction.Insert));
                                    item.set_id(ii);
                                    b = b && ii > 0;

                                    if (b && !item.get_isPlan())
                                    {
                                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getIncreaseCategoryId(), item.get_totalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getCostCategoryId(), -item.get_totalSumLong());
                                    }
                                }
                            }

                        if (b)
                            for (MoneyPNL item: moneyAccumulation.get_deletedPnls())
                                if (item.get_id() > 0)
                                {
                                    b = b && sqLiteDatabase.delete(TABLE_MONEY_PNL,
                                            DBHelper.KEY_MONEY_PNL_ID + "=?",
                                            new String[]{String.valueOf(item.get_id())}) == 1;

                                    if (!item.get_oldIsPlan())
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldCostCategoryId(), item.get_oldTotalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                item.getOldIncreaseCategoryId(), -item.get_oldTotalSumLong());
                                    }
                                }
                    }
                    else if (moneyAccumulation != null && oldMoneyAccumulation == null)
                    {
                        moneyAccumulation.set_targetId(id);
                        long ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_ACCUMULATION, null,
                                PutMoneyAccumulation(moneyAccumulation, EnumTypeAction.Insert));
                        moneyAccumulation.set_id(ii);
                        b = ii > 0;

                        if (b && walletId > 0)
                        {
                            int cnt = sqLiteDatabase.update(DBHelper.TABLE_MONEY_CATEGORY,
                                    PutMoneyCategoryNameStatus(moneyTarget.get_name(), EnumCategoryStatus.Active),
                                    DBHelper.KEY_MONEY_CATEGORY_ID + "= ?",
                                    new String[] { String.valueOf(walletId) });
                            b = cnt == 1;
                        }

                        if (b && walletId == 0)
                        {
                            MoneyCategory wallet = new MoneyCategory(0L, 0L,
                                    walletMaxPosition,
                                    moneyTarget.get_name(),
                                    0L, EnumTypeCategory.Wallet, false, false,
                                    false, true, moneyTarget.get_id(),
                                    EnumCategoryType.Base,
//                                    EnumCategoryStatus.Active,
                                    0L,
                                    false,
                                    0L);
                            walletId = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CATEGORY, null,
                                    PutMoneyCategory(wallet, EnumTypeAction.Insert));
                            wallet.set_id(walletId);
                            b = walletId > 0;
                        }

                        if (b)
                            for (MoneyPNL item: moneyAccumulation.get_pnls())
                            {
                                item.set_accumulationId(moneyAccumulation.get_id());
                                item.set_IncreaseCategoryId(walletId);
                                item.set_incCategoryName(moneyTarget.get_name());
                                ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_PNL, null,
                                        PutMoneyPNL(item, EnumTypeAction.Insert));
                                item.set_id(ii);
                                b = b && ii > 0;

                                if (b && !item.get_isPlan())
                                {
                                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                            item.getIncreaseCategoryId(), item.get_totalSumLong());

                                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                            item.getCostCategoryId(), -item.get_totalSumLong());
                                }
                            }
                    }
                }


                if (b && moneyTarget.get_planValues() != null)
                    for (PlanValue planValue: moneyTarget.get_planValues())
                    {
                        if (planValue.get_id() == 0)
                        {
                            int ii = (int) sqLiteDatabase.insert(DBHelper.TABLE_PLANVALUE, null,
                                PutPlanValue(planValue, EnumTypeAction.Insert));
                            b = b && ii > 0;
                        }
                        else
                        {
                            int ii = sqLiteDatabase.update(DBHelper.TABLE_PLANVALUE,
                                    PutPlanValue(planValue, EnumTypeAction.Update),
                                    DBHelper.KEY_PLANVALUE_ID + "= " +
                                    planValue.get_id(), null);

                            if (ii == 0)
                            {
                                ii = (int) sqLiteDatabase.insert(DBHelper.TABLE_PLANVALUE, null,
                                        PutPlanValue(planValue, EnumTypeAction.Insert));
                                b = b && ii > 0;
                            }
                            else
                                b = b && ii == 1;
                        }
                    }

                if (b)
                    for (Long ii: moneyTarget.get_deletedPlanValues())
                    {
                        int delCount = sqLiteDatabase.delete(DBHelper.TABLE_PLANVALUE,
                                DBHelper.KEY_PLANVALUE_ID + "=" + ii, null);
                        b = b && delCount == 1;
                    }

                if (b)
                    b = sqLiteDatabase.update(TABLE_TIME_PROJECT,
                            PutTasksProjectName(moneyTarget.get_name(), moneyTarget.get_status().toString()),
                            DBHelper.KEY_TIME_PROJECT_TARGET_ID + "= ? AND " +
                                    KEY_TIME_PROJECT_PARENT_ID + ">0",
                            new String[] { String.valueOf(id) }) == 1;

                if (updateCount == 1 && b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return  (updateCount == 1 && b);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyTarget(MoneyTarget moneyTarget) throws Exception
    {
        Long id = Long.valueOf(moneyTarget.get_id());

        if (id == 0)
        {
            return false;
        }

        try
        {
            Long walletId = Long.valueOf(OneMoneyCategoryId4Target(id, EnumTypeCategory.Wallet));
            int countPnls = QueryMoneyPNLCount4Accumulation(moneyTarget.get_id());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_TARGET,
                        DBHelper.KEY_MONEY_TARGET_ID + "=" + id, null);
                b = delCount == 1;

                if (b)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_TARGET, id);
                    b = AddCDelObject(sqLiteDatabase, cDelObject) > 0;
                }

                MoneyAccumulation oldMoneyAccumulation = moneyTarget.get_oldMoneyAccumulation();

                if (b && oldMoneyAccumulation != null)
                {
                    delCount = sqLiteDatabase.delete(TABLE_MONEY_ACCUMULATION,
                            DBHelper.KEY_MONEY_ACCUMULATION_TARGET_ID + "=?",
                            new String[]{String.valueOf(id)});
                    b = delCount == 1;

                    if (b)
                    {
                        CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_ACCUMULATION,
                                Long.valueOf(oldMoneyAccumulation.get_id()));
                        int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                        if (idCDelObject != -1)
                            b = true;
                    }

                    if (b)
                        for (MoneyPNL item: oldMoneyAccumulation.get_pnls())
                        {
                            if (item.get_isPlan())
                            {
                                countPnls--;
                                delCount = sqLiteDatabase.delete(TABLE_MONEY_PNL,
                                        DBHelper.KEY_MONEY_PNL_ID + "=?",
                                        new String[]{String.valueOf(item.get_id())});
                                b = b && delCount == 1;

                                if (b)
                                {
                                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_PNL,
                                            item.get_id());
                                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                                    if (idCDelObject != -1)
                                        b = true;
                                }

                                if (!item.get_oldIsPlan())
                                {
                                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                            item.getOldCostCategoryId(), item.get_oldTotalSumLong());

                                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                            item.getOldIncreaseCategoryId(), -item.get_oldTotalSumLong());
                                }
                            }
                        }

                    if (b)
                    {
                        if (countPnls > 0)
                        {
                            b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_CATEGORY,
                                    PutMoneyCategoryName(moneyTarget.get_name()),
                                    DBHelper.KEY_MONEY_CATEGORY_ID + "= ?",
                                    new String[]{String.valueOf(walletId)}) == 1;
                        }
                        else
                        {
                            delCount = sqLiteDatabase.delete(TABLE_MONEY_CATEGORY,
                                    DBHelper.KEY_MONEY_CATEGORY_ID + "=?",
                                    new String[]{ String.valueOf(walletId) });
                            b = delCount == 1;

                            if (b)
                            {
                                CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_CATEGORY, walletId);
                                int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                                if (idCDelObject != -1)
                                    b = true;
                            }
                        }
                    }
                }

                if (b)
                {
                    b = sqLiteDatabase.delete(TABLE_TIME_PROJECT,
                            DBHelper.KEY_TIME_PROJECT_TARGET_ID + "=? AND " +
                                    KEY_TIME_PROJECT_PARENT_ID + ">0",
                            new String[] { String.valueOf(id) }) == 1;
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public Long QueryMoneyTargetNextId() throws Exception {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_TARGET_ID + ") as _max from " + TABLE_MONEY_TARGET;
            cursor = database.rawQuery(sqlQuery, new String[]{ });
            if (cursor.moveToFirst())
            {
                return cursor.getLong(cursor.getColumnIndex("_max")) + 1;
            }
            else
                return 1L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }
    //endregion

    //region MoneyIncrease
    public int GetCountMoneyIncrease() throws Exception { return GetCount(TABLE_MONEY_INCREASE); }

    public Cursor QueryMoneyIncrease(MoneyIncreaseSettings moneyIncreaseSettings) throws Exception
    {
        try
        {
            String sql =
                    "select " + TABLE_MONEY_INCREASE + ".*, incoming1." +
                            key_MONEY_INCREASE_INCOMING_COUNT +
                            " from " + TABLE_MONEY_INCREASE +

                            " left join (select " + KEY_MONEY_INCOMING_INCREASE_ID +
                            ", count (*) as " + key_MONEY_INCREASE_INCOMING_COUNT +
                            " from " + TABLE_MONEY_INCOMING;

            sql += " group by " + KEY_MONEY_INCOMING_INCREASE_ID + ") incoming1 " +

                    "on incoming1." + KEY_MONEY_INCOMING_INCREASE_ID + "=" + TABLE_MONEY_INCREASE +
                    "."+ KEY_MONEY_INCREASE_ID;

            if (moneyIncreaseSettings != null)
                if (!moneyIncreaseSettings.get_showArchive())
                    sql += " where " + TABLE_MONEY_INCREASE + "." + KEY_MONEY_INCREASE_ARCHIVE + "=0";

            sql += " order by " +  TABLE_MONEY_INCREASE + "."+ KEY_MONEY_INCREASE_YEAR +
                    ", " + TABLE_MONEY_INCREASE + "." + KEY_MONEY_INCREASE_MONTH;

            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncrease4FactY() throws Exception
    {
        try
        {
            String sql =
                    "select distinct " +
                            KEY_MONEY_PNL_YEAR_INT +
                            " from " + TABLE_MONEY_PNL;

            sql += " where " + KEY_MONEY_PNL_ISPLAN + "=0";

            sql += " order by " +
                    KEY_MONEY_PNL_YEAR_INT;

            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncrease4FactMY() throws Exception
    {
        try
        {
            String sql =
                    "select distinct " +
                            "01 as " + KEY_MONEY_PNL_DATE_INT +
                            "," + KEY_MONEY_PNL_MONTH_INT +
                            ", " + KEY_MONEY_PNL_YEAR_INT +
                            " from " + TABLE_MONEY_PNL;

            sql += " where " + KEY_MONEY_PNL_ISPLAN + " = 0";

            sql += " order by " +
                    KEY_MONEY_PNL_YEAR_INT +
                    ", " + KEY_MONEY_PNL_MONTH_INT;

            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncrease4FactDWMY() throws Exception
    {
        try
        {
            String sql =
                    "select distinct " +
                            KEY_MONEY_PNL_DATE_INT +
                            ", " + KEY_MONEY_PNL_MONTH_INT +
                            ", " + KEY_MONEY_PNL_YEAR_INT +
                            " from " + TABLE_MONEY_PNL;

            sql += " where " + KEY_MONEY_PNL_ISPLAN + "=0";

            sql += " order by " +
                    KEY_MONEY_PNL_YEAR_INT +
                    ", " + KEY_MONEY_PNL_MONTH_INT +
                    ", " + KEY_MONEY_PNL_DATE_INT;

            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncreasePrevValue(String month, String year) throws Exception
    {
        if (month == null || year == null)
            return null;

        try
        {
            String sqlQuery = "select *"
                    + " from " + TABLE_MONEY_INCREASE
                    + " where (" + KEY_MONEY_INCREASE_YEAR + " <= ?"
                    + " and " + KEY_MONEY_INCREASE_MONTH + " < ?)"
                    + " or (" + KEY_MONEY_INCREASE_YEAR + " < ?)"
                    + " order by " + KEY_MONEY_INCREASE_YEAR + " desc, "
                    + KEY_MONEY_INCREASE_MONTH + " desc"
                    + " limit 1";
            return getReadableDatabase().rawQuery(sqlQuery, new String[]{year, month, year});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncreaseNextValue(String month, String year) throws Exception
    {
        if (month == null || year == null)
            return null;

        try
        {
            String sqlQuery = "select *"
                + " from " + TABLE_MONEY_INCREASE
                + " where (" + KEY_MONEY_INCREASE_YEAR + " >= ?"
                + " and " + KEY_MONEY_INCREASE_MONTH + " > ?)"
                + " or (" + KEY_MONEY_INCREASE_YEAR + " > ?)"
                + " order by " + KEY_MONEY_INCREASE_YEAR + " asc, "
                + KEY_MONEY_INCREASE_MONTH + " asc"
                + " limit 1";
        return getReadableDatabase().rawQuery(sqlQuery,new String[] {year, month, year});
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    private ContentValues PutMoneyIncrease(MoneyIncrease moneyIncrease, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_INCREASE_DAY, moneyIncrease.get_day());
        contentValues.put(DBHelper.KEY_MONEY_INCREASE_MONTH, moneyIncrease.get_month());
        contentValues.put(DBHelper.KEY_MONEY_INCREASE_YEAR, moneyIncrease.get_year());
        contentValues.put(DBHelper.KEY_MONEY_INCREASE_VALUE, moneyIncrease.get_value());
        contentValues.put(DBHelper.KEY_MONEY_INCREASE_ARCHIVE, moneyIncrease.get_archiveI());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyIncrease(MoneyIncrease moneyIncrease) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_MONEY_INCREASE, null,
                    PutMoneyIncrease(moneyIncrease, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyIncrease(MoneyIncrease moneyIncrease) throws Exception
    {
        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_INCREASE,
                    PutMoneyIncrease(moneyIncrease, EnumTypeAction.Update),
                    DBHelper.KEY_MONEY_INCREASE_ID + "= ?",
                    new String[] {String.valueOf(moneyIncrease.get_id())});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyIncrease(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INCREASE,
                        DBHelper.KEY_MONEY_INCREASE_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_INCREASE, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                        b = true;
                }

                if (b)
                    sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INCOMING,
                            DBHelper.KEY_MONEY_INCOMING_INCREASE_ID + "=" + id, null);

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean CheckDateExist(Long id, String month, String year) throws Exception {
        if (month == null || year == null)
            return false;

        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select *"
                    + " from " + TABLE_MONEY_INCREASE
                    + " where " + KEY_MONEY_INCREASE_YEAR + " = ?"
                    + " and " + KEY_MONEY_INCREASE_MONTH + " = ?"
                    + " and " + KEY_MONEY_INCREASE_ID + " <> ?";
            cursor = database.rawQuery(sqlQuery, new String[]{year, month, String.valueOf(id)});
            return (cursor.getCount() >= 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }
    //endregion

    //region MoneyCategory
    public int GetCountMoneyCategory() throws Exception { return GetCount(TABLE_MONEY_CATEGORY); }

    public Cursor OneMoneyCategory(Long categoryId) throws Exception
    {
        try
        {
            String sql =
                    "select " + TABLE_MONEY_CATEGORY +
                            ".*, category1." + key_MONEY_CATEGORY_COUNT_CHILD +
                            ", incoming1." + key_MONEY_CATEGORY_COUNT_INCOMING +
                            ", pnl1." + key_MONEY_CATEGORY_COUNT_PNL_INC +
                            ", pnl2." + key_MONEY_CATEGORY_COUNT_PNL_COST +
                            ", pnl3." + key_MONEY_CATEGORY_COUNT_PNL_SUB +
                            " from " + TABLE_MONEY_CATEGORY +

                            " left join (select " + KEY_MONEY_CATEGORY_PARENT_ID + ", count (" +
                            KEY_MONEY_CATEGORY_ID + ") as " + key_MONEY_CATEGORY_COUNT_CHILD +
                            " from " + TABLE_MONEY_CATEGORY +
                            " where " + KEY_MONEY_CATEGORY_PARENT_ID + " = ?" +
                            " group by " + KEY_MONEY_CATEGORY_PARENT_ID + ") category1 " +
                            "on category1." + KEY_MONEY_CATEGORY_PARENT_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_INCOMING_CATEGORY_ID + ", count (" +
                            KEY_MONEY_INCOMING_ID + ") as " + key_MONEY_CATEGORY_COUNT_INCOMING +
                            " from " + TABLE_MONEY_INCOMING +
                            " where " + KEY_MONEY_INCOMING_CATEGORY_ID + " = ?" +
                            " group by " + KEY_MONEY_INCOMING_CATEGORY_ID + ") incoming1 " +
                            "on incoming1." + KEY_MONEY_INCOMING_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_INC_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_INC +
                            " from " + TABLE_MONEY_PNL +
                            " where " + KEY_MONEY_PNL_INC_CATEGORY_ID + " = ?" +
                            " group by " + KEY_MONEY_PNL_INC_CATEGORY_ID + ") pnl1 " +
                            "on pnl1." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_COST_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_COST +
                            " from " + TABLE_MONEY_PNL +
                            " where " + KEY_MONEY_PNL_COST_CATEGORY_ID + " = ?" +
                            " group by " + KEY_MONEY_PNL_COST_CATEGORY_ID + ") pnl2 " +
                            "on pnl2." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_SUB_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_SUB +
                            " from " + TABLE_MONEY_PNL +
                            " where " + KEY_MONEY_PNL_SUB_CATEGORY_ID + " = ?" +
                            " group by " + KEY_MONEY_PNL_SUB_CATEGORY_ID + ") pnl3 " +
                            "on pnl3." + KEY_MONEY_PNL_SUB_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " where " + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID + "=? " +
                            "ORDER BY " + KEY_MONEY_CATEGORY_POSITION;

            String[] params = new String[]{String.valueOf(categoryId), String.valueOf(categoryId),
                    String.valueOf(categoryId), String.valueOf(categoryId),
                    String.valueOf(categoryId), String.valueOf(categoryId)};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Long OneMoneyCategoryId4Target(Long targetId, EnumTypeCategory typeCategory) throws Exception
    {
        try
        {
            long categoryId = 0L;
            Cursor cursor = getReadableDatabase().rawQuery("select * from " +
                    TABLE_MONEY_CATEGORY + " where " + KEY_MONEY_CATEGORY_TARGET_ID +
                    "=? AND " + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=?"
                    , new String[]{String.valueOf(targetId), typeCategory.toString()});
            if (cursor.moveToFirst())
            {
                int index = cursor.getColumnIndex(KEY_MONEY_CATEGORY_ID);
                if (index >= 0)
                    categoryId = cursor.getLong(index);
            }
            cursor.close();

            return categoryId;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Long OneMoneyCategoryId(Long creditId, EnumTypeCategory typeCategory) throws Exception
    {
        try
        {
            Cursor cursor = getReadableDatabase().query(TABLE_MONEY_CATEGORY, null,
                    KEY_MONEY_CATEGORY_CREDIT_ID + "=? AND " +
                            KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=?",
                    new String[]{String.valueOf(creditId), typeCategory.toString()},
                    null, null, null);
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_CATEGORY_ID);

            long id = 0L;
            if (cursor.getCount() == 1 && cursor.moveToFirst())
                id = cursor.getLong(idIndex);

            cursor.close();
            return id;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public String Query1MoneyCategoryName(Long categoryId)
    {
        if (categoryId == null)
            return "";

        Cursor cursor = getReadableDatabase().query(TABLE_MONEY_CATEGORY, null,
                KEY_MONEY_CATEGORY_ID + "=" + categoryId,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst())
        {
            int categoryNameIndex = cursor.getColumnIndex(KEY_MONEY_CATEGORY_NAME);
            if (categoryNameIndex != -1)
                return cursor.getString(categoryNameIndex);
            cursor.close();
        }

        return "";
    }

    public Cursor QueryMoneyCategory(Long parentId,
                                     MoneyCategorySettings moneyCategorySettings,
                                     GlobalClass gVar) throws Exception
    {
        ArrayList<EnumCategoryType> categoryList = new ArrayList<>();
        if (moneyCategorySettings != null && !gVar.get_hasUnpaidSubscription())
            categoryList = moneyCategorySettings.get_categoryList();

        try
        {
            String sql =
                    "select " + TABLE_MONEY_CATEGORY +
                            ".*, category1." + key_MONEY_CATEGORY_COUNT_CHILD +
                            ", incoming1." + key_MONEY_CATEGORY_COUNT_INCOMING +
                            ", pnl1." + key_MONEY_CATEGORY_COUNT_PNL_INC +
                            ", pnl2." + key_MONEY_CATEGORY_COUNT_PNL_COST +
                            ", pnl3." + key_MONEY_CATEGORY_COUNT_PNL_SUB +
                            " from " + TABLE_MONEY_CATEGORY;
            sql += " left join (select " + KEY_MONEY_CATEGORY_PARENT_ID + ", count (" +
                            KEY_MONEY_CATEGORY_ID + ") as " + key_MONEY_CATEGORY_COUNT_CHILD +
                            " from " + TABLE_MONEY_CATEGORY;

            if (moneyCategorySettings != null)
            {
                if (moneyCategorySettings.get_typeCategory() != null &&
                        moneyCategorySettings.get_typeCategory() != EnumTypeCategory.All)
                    sql += " where " + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " = '" +
                            moneyCategorySettings.get_typeCategory().name() + "'";
            }

            sql +=
                    " group by " + KEY_MONEY_CATEGORY_PARENT_ID + ") category1 " +
                            "on category1." + KEY_MONEY_CATEGORY_PARENT_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_INCOMING_CATEGORY_ID + ", count (" +
                            KEY_MONEY_INCOMING_ID + ") as " + key_MONEY_CATEGORY_COUNT_INCOMING +
                            " from " + TABLE_MONEY_INCOMING +
                            " group by " + KEY_MONEY_INCOMING_CATEGORY_ID + ") incoming1 " +
                            "on incoming1." + KEY_MONEY_INCOMING_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_INC_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_INC +
                            " from " + TABLE_MONEY_PNL +
                            " group by " + KEY_MONEY_PNL_INC_CATEGORY_ID + ") pnl1 " +
                            "on pnl1." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_COST_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_COST +
                            " from " + TABLE_MONEY_PNL +
                            " group by " + KEY_MONEY_PNL_COST_CATEGORY_ID + ") pnl2 " +
                            "on pnl2." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_SUB_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_SUB +
                            " from " + TABLE_MONEY_PNL +
                            " group by " + KEY_MONEY_PNL_SUB_CATEGORY_ID + ") pnl3 " +
                            "on pnl3." + KEY_MONEY_PNL_SUB_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID;

            boolean and = false;
            if (parentId != null)
            {
                sql += " where " + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_PARENT_ID + "= " + parentId;
                and = true;
            }

            if (moneyCategorySettings != null)
            {
                if (moneyCategorySettings.get_typeCategory() != null &&
                        moneyCategorySettings.get_typeCategory() != EnumTypeCategory.All)
                {
                    if (and)
                        sql += " AND ";
                    else
                        sql += " where ";
                    sql += TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "= '" +
                            moneyCategorySettings.get_typeCategory().name() + "'";
                    and = true;
                }

                if (!gVar.get_hasUnpaidSubscription() &&
                    !moneyCategorySettings.get_showArchive())
                {
                    if (and)
                        sql += " AND ";
                    else
                    {
                        sql += " where ";
                        and = true;
                    }
                    sql += "(" + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_ARCHIVE + "=0" +
                        " OR " + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_ARCHIVE + "='')";
                }

                if (!gVar.get_hasUnpaidSubscription() &&
                        categoryList.size() > 0)
                {
                    if (and)
                        sql += " AND ";
                    else
                    {
                        sql += " where ";
                        and = true;
                    }
                    sql += " (" + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_TYPE + " = '" +
                            categoryList.get(0).toString() + "'";

                    for (int i = 1; i < categoryList.size(); i++)
                        sql += " OR " + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_TYPE + " = '" +
                                categoryList.get(i).toString() + "'";
                    sql += ")";
                }

            }

            sql += " ORDER BY " + KEY_MONEY_CATEGORY_POSITION;

//            String[] params = new String[]{typeCategory.name(),
//                                        String.valueOf(parentId), typeCategory.name()};
//            if (parentId == null && (typeCategory == null || typeCategory.equals(EnumTypeCategory.All)))
//                params = new String[]{};
//            else if (parentId == null)
//                params = new String[]{typeCategory.name(), typeCategory.name()};
//            else if (typeCategory == null || typeCategory.equals(EnumTypeCategory.All))
//            params = new String[]{String.valueOf(parentId)};

//            return getReadableDatabase().rawQuery(sql, params);
            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyCategory4Portfolio(ArrayList<Long> portfolios) throws Exception
    {
        try
        {
            String selection = "";
            if (portfolios != null && portfolios.size() > 0)
            {
                selection = KEY_MONEY_PORTFOLIO_ID + "=" + portfolios.get(0);
                for (int i = 1; i < portfolios.size(); i++)
                    selection = " OR " + KEY_MONEY_PORTFOLIO_ID + "=" + portfolios.get(i);
            }

            return getReadableDatabase().query(TABLE_MONEY_PORTFOLIO,
                    new String[] { KEY_MONEY_PORTFOLIO_WALLET },
                    selection,
                    null,
                    null,
                    null,
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyCategory(ArrayList<Long> parentIds,
                                     IGetCategoryList settings,
                                     GlobalClass gVar) throws Exception
    {
        EnumTypeCategory typeCategory = settings.get_typeCategory();

        if (parentIds == null || parentIds.size() == 0)
            return null;

        ArrayList<EnumCategoryType> categoryList = new ArrayList<>();
        if (settings != null && !gVar.get_hasUnpaidSubscription())
            categoryList = settings.get_categoryList();

        try
        {
            StringBuilder sql =
                    new StringBuilder("select " + TABLE_MONEY_CATEGORY +
                            ".*, category1." + key_MONEY_CATEGORY_COUNT_CHILD +
                            ", incoming1." + key_MONEY_CATEGORY_COUNT_INCOMING +
                            ", pnl1." + key_MONEY_CATEGORY_COUNT_PNL_INC +
                            ", pnl2." + key_MONEY_CATEGORY_COUNT_PNL_COST +
                            ", pnl3." + key_MONEY_CATEGORY_COUNT_PNL_SUB +
                            " from " + TABLE_MONEY_CATEGORY +

                            " left join (select " + KEY_MONEY_CATEGORY_PARENT_ID + ", count (" +
                            KEY_MONEY_CATEGORY_ID + ") as " + key_MONEY_CATEGORY_COUNT_CHILD +
                            " from " + TABLE_MONEY_CATEGORY +
                            " where " + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " = ?" +
                            " group by " + KEY_MONEY_CATEGORY_PARENT_ID + ") category1 " +
                            "on category1." + KEY_MONEY_CATEGORY_PARENT_ID +
                            "=" + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_INCOMING_CATEGORY_ID + ", count (" +
                            KEY_MONEY_INCOMING_ID + ") as " + key_MONEY_CATEGORY_COUNT_INCOMING +
                            " from " + TABLE_MONEY_INCOMING +
                            " group by " + KEY_MONEY_INCOMING_CATEGORY_ID + ") incoming1 " +
                            "on incoming1." + KEY_MONEY_INCOMING_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_INC_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_INC +
                            " from " + TABLE_MONEY_PNL +
                            " group by " + KEY_MONEY_PNL_INC_CATEGORY_ID + ") pnl1 " +
                            "on pnl1." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_COST_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_COST +
                            " from " + TABLE_MONEY_PNL +
                            " group by " + KEY_MONEY_PNL_COST_CATEGORY_ID + ") pnl2 " +
                            "on pnl2." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " left join (select " + KEY_MONEY_PNL_SUB_CATEGORY_ID + ", count (" +
                            KEY_MONEY_PNL_ID + ") as " + key_MONEY_CATEGORY_COUNT_PNL_SUB +
                            " from " + TABLE_MONEY_PNL +
                            " group by " + KEY_MONEY_PNL_SUB_CATEGORY_ID + ") pnl3 " +
                            "on pnl3." + KEY_MONEY_PNL_SUB_CATEGORY_ID +
                            "=" + TABLE_MONEY_CATEGORY + "."+ KEY_MONEY_CATEGORY_ID +

                            " where (" +
                            TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_PARENT_ID + "=" +
                            parentIds.get(0));

            for (int i = 1; i < parentIds.size(); i++)
                sql.append(" OR " + TABLE_MONEY_CATEGORY + "." +
                KEY_MONEY_CATEGORY_PARENT_ID + "=").append(parentIds.get(i));

            sql.append(") AND " + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=? ");

            if (parentIds.size() > 0 && parentIds.get(0) != 0L && categoryList.size() > 0)
            {
                sql.append(" AND (" + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_TYPE + " = '" +
                        categoryList.get(0).toString() + "'");
                for (int i = 1; i < categoryList.size(); i++)
                    sql.append(" OR " + TABLE_MONEY_CATEGORY + "." + KEY_MONEY_CATEGORY_TYPE + " = '" +
                            categoryList.get(i).toString()+ "'");
                sql.append(")");
            }

            sql.append(" ORDER BY " + KEY_MONEY_CATEGORY_POSITION);

            String[] params = new String[]{typeCategory.name(), typeCategory.name()};

            return getReadableDatabase().rawQuery(sql.toString(), params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyCategoryNameAndClearTargetId(String name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_NAME, name);
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_TARGET_ID, 0);

        return PutCustom(contentValues, EnumTypeAction.Update);
    }

    private ContentValues PutMoneyCategoryName(String name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_NAME, name);

        return PutCustom(contentValues, EnumTypeAction.Update);
    }

    private ContentValues PutMoneyCategoryNameStatus(String name, EnumCategoryStatus status)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_NAME, name);
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_STATUS, status.toString());

        return PutCustom(contentValues, EnumTypeAction.Update);
    }

    private ContentValues PutMoneyCategory(MoneyCategory moneyCategory, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_PARENT_ID, moneyCategory.get_parentId());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_NAME, moneyCategory.get_name());
        if (DATABASE_VERSION > 8)
        {
            if (!moneyCategory.getTypeCategory().equals(EnumTypeCategory.Wallet) &&
                    moneyCategory.get_parentId() == 0L)
                contentValues.put(DBHelper.KEY_MONEY_CATEGORY_SUM_L, 0L);
            else
                contentValues.put(DBHelper.KEY_MONEY_CATEGORY_SUM_L, moneyCategory.get_sumLong());
        }
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_POSITION, moneyCategory.get_position());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_TYPE_CATEGORY, moneyCategory.getTypeCategory().toString());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_SUMTOBALANCE, moneyCategory.get_sumToBalanceI());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_CREDIT_ID, moneyCategory.get_creditId());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_ARCHIVE, moneyCategory.get_archiveI());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_TARGET_ID, moneyCategory.get_targetId());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_TYPE, moneyCategory.get_type().toString());
//        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_STATUS, moneyCategory.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_CATEGORY_ICON, moneyCategory.get_icon());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyCategory(MoneyCategory moneyCategory) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(
                    DBHelper.TABLE_MONEY_CATEGORY, null,
                    PutMoneyCategory(moneyCategory, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyCategory(MoneyCategory moneyCategory) throws Exception
    {
        Long id = moneyCategory.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_CATEGORY,
                    PutMoneyCategory(moneyCategory, EnumTypeAction.Update),
                    DBHelper.KEY_MONEY_CATEGORY_ID + "= ?", new String[] {String.valueOf(id)});
            return  (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyCategoryPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MONEY_CATEGORY_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_CATEGORY,
                    contentValues, DBHelper.KEY_MONEY_CATEGORY_ID + "= ?",
                    new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyCategorySumLByDeltaLong(SQLiteDatabase sqLiteDatabase,
                                                 Long categoryId, Long deltaSum) throws Exception
    {
        if (categoryId == null) { return false; }

        try
        {
            sqLiteDatabase.execSQL("update " + TABLE_MONEY_CATEGORY + " set " +
                    KEY_MONEY_CATEGORY_SUM_L + "=" + KEY_MONEY_CATEGORY_SUM_L + "+?, "
                    + KEY_ALL_DATETIME_UPDATE + "=?"
                    + " where " + KEY_MONEY_CATEGORY_ID + "=?",
                    new Object[] {deltaSum, Calendar.getInstance().getTime().toString(), categoryId});

            return true;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyCategory(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_CATEGORY,
                        DBHelper.KEY_MONEY_CATEGORY_ID + "=" + id, null);

                if (delCount == 1) {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_CATEGORY, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1) {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public int QueryMoneyCategoryMaxPosition(Long parentId, EnumTypeCategory typeCategory) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_CATEGORY_POSITION + ") as _max from " +
                    TABLE_MONEY_CATEGORY + " where " + KEY_MONEY_CATEGORY_PARENT_ID + " = ?" +
                    " and " + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " = ?";
            cursor = database.rawQuery(sqlQuery, new String[]{ String.valueOf(parentId), typeCategory.name()});
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }
    //endregion

    //region MoneyIncoming
    public int GetCountMoneyIncoming() throws Exception { return GetCount(TABLE_MONEY_INCOMING); }

    public Cursor QueryMoneyIncoming(ArrayList<Long> increaseIds, ArrayList<Long> categoryIds) throws Exception
    {
        if (increaseIds == null || categoryIds == null || increaseIds.size() == 0 || categoryIds.size() == 0)
            return null;

        StringBuilder sIncrease = new StringBuilder();
        StringBuilder sCategory = new StringBuilder();

        sIncrease.append("(" + KEY_MONEY_INCOMING_INCREASE_ID + "=").append(increaseIds.get(0));
        for (int i = 1; i < increaseIds.size(); i++)
            if (increaseIds.get(i) != -1)
                sIncrease.append(" OR " + KEY_MONEY_INCOMING_INCREASE_ID + "=").append(increaseIds.get(i));
        sIncrease.append(")");

        sCategory.append("(" + KEY_MONEY_INCOMING_CATEGORY_ID + "=").append(categoryIds.get(0));
        for (int i = 1; i < categoryIds.size(); i++)
            if (categoryIds.get(i) != -1)
                sCategory.append(" OR " + KEY_MONEY_INCOMING_CATEGORY_ID + "=").append(categoryIds.get(i));
        sCategory.append(")");

        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_MONEY_INCOMING,
                    null, sIncrease + " AND " +
                            sCategory, null, null, null,
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncomingFactDWMY(ArrayList<Long> increaseIds, ArrayList<Long> categoryIds,
                                           EnumTypeCategory typeCategory) throws Exception
    {
        if (increaseIds == null || categoryIds == null || increaseIds.size() == 0 || categoryIds.size() == 0)
            return null;

        StringBuilder sCategory = new StringBuilder();
        if (typeCategory == EnumTypeCategory.Increase)
        {
            sCategory.append("(" + KEY_MONEY_PNL_COST_CATEGORY_ID + "=").append(categoryIds.get(0));
            for (int i = 1; i < categoryIds.size(); i++)
                if (categoryIds.get(i) != -1)
                    sCategory.append(" OR " + KEY_MONEY_PNL_COST_CATEGORY_ID + "=").append(categoryIds.get(i));
            sCategory.append(")");
        }
        else
        {
            sCategory.append("(" + KEY_MONEY_PNL_INC_CATEGORY_ID + "=").append(categoryIds.get(0));
            for (int i = 1; i < categoryIds.size(); i++)
                if (categoryIds.get(i) != -1)
                    sCategory.append(" OR " + KEY_MONEY_PNL_INC_CATEGORY_ID + "=").append(categoryIds.get(i));
            sCategory.append(")");
        }

        String sql = "select " +
                KEY_MONEY_PNL_DATE_INT  + ", " +
                KEY_MONEY_PNL_MONTH_INT  + ", " +
                KEY_MONEY_PNL_YEAR_INT + ", " +
                KEY_MONEY_PNL_SUB_CATEGORY_ID +
                ", sum(" + KEY_MONEY_PNL_SUM_L + ") as " + key_MONEY_PNL_SUMSUM_L +
                ", sum(" + KEY_MONEY_PNL_PERCENTSUM_L + ") as " + key_MONEY_PNL_PERCENTSUMSUM_L +
                " from " + TABLE_MONEY_PNL +
                " where " + KEY_MONEY_PNL_ISPLAN + "=0" +
                " group by " +
                KEY_MONEY_PNL_YEAR_INT + ", " +
                KEY_MONEY_PNL_MONTH_INT + ", " +
                KEY_MONEY_PNL_DATE_INT + ", " +
                KEY_MONEY_PNL_SUB_CATEGORY_ID +
                " order by " +
                KEY_MONEY_PNL_YEAR_INT + ", " +
                KEY_MONEY_PNL_MONTH_INT + ", " +
                KEY_MONEY_PNL_DATE_INT;

        try
        {
            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncomingFactMY(ArrayList<Long> increaseIds, ArrayList<Long> categoryIds,
                                         EnumTypeCategory typeCategory) throws Exception
    {
        if (increaseIds == null || categoryIds == null || increaseIds.size() == 0 || categoryIds.size() == 0)
            return null;

        StringBuilder sCategory = new StringBuilder();
        if (typeCategory == EnumTypeCategory.Increase)
        {
            sCategory.append("(" + KEY_MONEY_PNL_COST_CATEGORY_ID + "=").append(categoryIds.get(0));
            for (int i = 1; i < categoryIds.size(); i++)
                if (categoryIds.get(i) != -1)
                    sCategory.append(" OR " + KEY_MONEY_PNL_COST_CATEGORY_ID + "=").append(categoryIds.get(i));
            sCategory.append(")");
        }
        else
        {
            sCategory.append("(" + KEY_MONEY_PNL_INC_CATEGORY_ID + "=").append(categoryIds.get(0));
            for (int i = 1; i < categoryIds.size(); i++)
                if (categoryIds.get(i) != -1)
                    sCategory.append(" OR " + KEY_MONEY_PNL_INC_CATEGORY_ID + "=").append(categoryIds.get(i));
            sCategory.append(")");
        }

        String sql = "select " + KEY_MONEY_PNL_MONTH_INT  + ", " + KEY_MONEY_PNL_YEAR_INT + ", " +
            KEY_MONEY_PNL_SUB_CATEGORY_ID +
            ", sum(" + KEY_MONEY_PNL_SUM_L + ") as " + key_MONEY_PNL_SUMSUM_L +
                ", sum(" + KEY_MONEY_PNL_PERCENTSUM_L + ") as " + key_MONEY_PNL_PERCENTSUMSUM_L +
            " from " + TABLE_MONEY_PNL +
                " where " + KEY_MONEY_PNL_ISPLAN + "=0" +
                " group by " + KEY_MONEY_PNL_YEAR_INT + ", " + KEY_MONEY_PNL_MONTH_INT + ", " +
            KEY_MONEY_PNL_SUB_CATEGORY_ID +
            " order by " + KEY_MONEY_PNL_YEAR_INT + ", " + KEY_MONEY_PNL_MONTH_INT;

        try
        {
            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyIncomingFactY(ArrayList<Long> increaseIds, ArrayList<Long> categoryIds,
                                           EnumTypeCategory typeCategory) throws Exception
    {
        if (increaseIds == null || categoryIds == null || increaseIds.size() == 0 || categoryIds.size() == 0)
            return null;

        StringBuilder sCategory = new StringBuilder();
        if (typeCategory == EnumTypeCategory.Increase)
        {
            sCategory.append("(" + KEY_MONEY_PNL_COST_CATEGORY_ID + "=").append(categoryIds.get(0));
            for (int i = 1; i < categoryIds.size(); i++)
                if (categoryIds.get(i) != -1)
                    sCategory.append(" OR " + KEY_MONEY_PNL_COST_CATEGORY_ID + "=").append(categoryIds.get(i));
            sCategory.append(")");
        }
        else
        {
            sCategory.append("(" + KEY_MONEY_PNL_INC_CATEGORY_ID + "=").append(categoryIds.get(0));
            for (int i = 1; i < categoryIds.size(); i++)
                if (categoryIds.get(i) != -1)
                    sCategory.append(" OR " + KEY_MONEY_PNL_INC_CATEGORY_ID + "=").append(categoryIds.get(i));
            sCategory.append(")");
        }

        String sql = "select " + KEY_MONEY_PNL_YEAR_INT + ", " +
                KEY_MONEY_PNL_SUB_CATEGORY_ID +
                ", sum(" + KEY_MONEY_PNL_SUM_L + ") as " + key_MONEY_PNL_SUMSUM_L +
                ", sum(" + KEY_MONEY_PNL_PERCENTSUM_L + ") as " + key_MONEY_PNL_PERCENTSUMSUM_L +
                " from " + TABLE_MONEY_PNL +
                " where " + KEY_MONEY_PNL_ISPLAN + "=0" +
                " group by " + KEY_MONEY_PNL_YEAR_INT + ", " +
                KEY_MONEY_PNL_SUB_CATEGORY_ID +
                " order by " + KEY_MONEY_PNL_YEAR_INT;

        try
        {
            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyIncoming(MoneyIncoming moneyIncoming, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_INCOMING_CATEGORY_ID, moneyIncoming.get_categoryId());
        contentValues.put(DBHelper.KEY_MONEY_INCOMING_INCREASE_ID, moneyIncoming.get_increaseId());
        contentValues.put(DBHelper.KEY_MONEY_INCOMING_SUM_L, moneyIncoming.get_sumLong());
        contentValues.put(DBHelper.KEY_MONEY_INCOMING_PERCENT, moneyIncoming.get_percentOfSum());
        contentValues.put(DBHelper.KEY_MONEY_INCOMING_TYPE, moneyIncoming.get_typeIncoming().toString());
        contentValues.put(DBHelper.KEY_MONEY_INCOMING_EXTERNAL_ID, moneyIncoming.get_externalId());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyIncoming(MoneyIncoming moneyIncoming) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_MONEY_INCOMING,
                    null, PutMoneyIncoming(moneyIncoming, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyIncoming(MoneyIncoming moneyIncoming) throws Exception
    {
        Long id = moneyIncoming.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_INCOMING,
                    PutMoneyIncoming(moneyIncoming, EnumTypeAction.Update),
                    DBHelper.KEY_MONEY_INCOMING_ID + "= ?",
                    new String[] {String.valueOf(id)});
            return  (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyIncoming(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INCOMING,
                        DBHelper.KEY_MONEY_INCOMING_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_INCOMING, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyPlan
    public int GetCountMoneyPlan() throws Exception { return GetCount(TABLE_MONEY_PERIODPLAN); }

    private ContentValues PutMoneyPlanFact(MoneyPlan moneyPlan, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MONEY_PERIODPLAN_CATEGORY_ID, moneyPlan.getMoneyCategoryId());
        contentValues.put(KEY_MONEY_PERIODPLAN_MONTH, moneyPlan.get_month());
        contentValues.put(KEY_MONEY_PERIODPLAN_YEAR, moneyPlan.get_year());
        contentValues.put(KEY_MONEY_PERIODPLAN_SUM_L, moneyPlan.get_sumLong());
        contentValues.put(KEY_MONEY_PERIODPLAN_TYPE, moneyPlan.get_typeIncoming().toString());
        contentValues.put(KEY_MONEY_PERIODPLAN_EXTERNAL_ID, moneyPlan.get_externalId());

        return PutCustom(contentValues, typeAction);
    }

    public Cursor QueryMoneyPlan(MoneyPlanFactSettings moneyPlanFactSettings) throws Exception
    {
        CustomPeriodClass customPeriodClass = moneyPlanFactSettings.get_customPeriodClass();
        Calendar calendarStart = customPeriodClass.get_start_dateC();
        Calendar calendarEnd = customPeriodClass.get_end_dateC();

        StringBuilder sWhere = new StringBuilder();
        sWhere.append(KEY_MONEY_PERIODPLAN_MONTH + " >= ").append(calendarStart.get(Calendar.MONTH));
        sWhere.append(" AND " + KEY_MONEY_PERIODPLAN_MONTH + " <= ").append(calendarEnd.get(Calendar.MONTH));
        sWhere.append(" AND " + KEY_MONEY_PERIODPLAN_YEAR + " >= ").append(calendarStart.get(Calendar.YEAR));
        sWhere.append(" AND " + KEY_MONEY_PERIODPLAN_YEAR + " <= ").append(calendarEnd.get(Calendar.YEAR));

        try
        {
            return getReadableDatabase().query(
                    DBHelper.TABLE_MONEY_PERIODPLAN,
                    null,
                    sWhere.toString(),
                    null,
                    null,
                    null,
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean CopyMoneyPlansFromIncoming(ArrayList<Long> categoryIds,
                                              ArrayList<MoneyIncoming> moneyIncomingArrayList,
                                              int month, int year) throws Exception
    {
        StringBuilder where = new StringBuilder();
        where.append(KEY_MONEY_PERIODPLAN_MONTH + "=").append(month);
        where.append(" AND " + KEY_MONEY_PERIODPLAN_YEAR + "=").append(year);
        where.append(" AND (");
        where.append(KEY_MONEY_PERIODPLAN_CATEGORY_ID + "=" + categoryIds.get(0));
        for (int i = 1; i < categoryIds.size(); i++)
            where.append(" OR " + KEY_MONEY_PERIODPLAN_CATEGORY_ID + "=" + categoryIds.get(i));
        where.append(")");

        Cursor moneyPlanCursor = getReadableDatabase().query(
                DBHelper.TABLE_MONEY_PERIODPLAN,
                null,
                where.toString(),
                null,
                null,
                null,
                null);

        ArrayList<Long> moneyPlansIds = new ArrayList<>();
        if (moneyPlanCursor.moveToFirst())
        {
            int idIndex = moneyPlanCursor.getColumnIndex(DBHelper.KEY_MONEY_PERIODPLAN_ID);

            do
            {
                moneyPlansIds.add(moneyPlanCursor.getLong(idIndex));
            }
            while (moneyPlanCursor.moveToNext());
        }
        moneyPlanCursor.close();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        boolean b = true;

        try
        {
            int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_PERIODPLAN,
                    where.toString(), null);

            if (delCount > 1)
            {
                for (Long id: moneyPlansIds)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_PERIODPLAN, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject == -1)
                        b = false;
                }
            }

            if (b)
            {
                for (MoneyIncoming moneyIncoming: moneyIncomingArrayList)
                {
                    EnumTypeIncoming typeIncoming = moneyIncoming.get_typeIncoming();
                    if (typeIncoming.equals(EnumTypeIncoming.Percent))
                        typeIncoming = EnumTypeIncoming.Sum;

                    MoneyPlan moneyPlan = new MoneyPlan(0L,
                            new MoneyCategory(moneyIncoming.get_categoryId()),
                            month,
                            year,
                            moneyIncoming.get_sumLong(),
                            typeIncoming,
                            moneyIncoming.get_externalId());
                    long id = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_PERIODPLAN,
                            null, PutMoneyPlanFact(moneyPlan, EnumTypeAction.Insert));
                    b = b && id > 0;
                }
            }

            if (b)
                sqLiteDatabase.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("MoneyPlans Copy", e.getMessage());
            return false;
        }
        finally
        {
            sqLiteDatabase.endTransaction();
        }

        return true;
    }

    public Long AddMoneyPlan(MoneyPlan moneyPlan) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_MONEY_PERIODPLAN,
                    null, PutMoneyPlanFact(moneyPlan, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyPlan(MoneyPlan moneyPlan) throws Exception
    {
        Long id = moneyPlan.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_PERIODPLAN,
                    PutMoneyPlanFact(moneyPlan, EnumTypeAction.Update),
                    DBHelper.KEY_MONEY_PERIODPLAN_ID + "= ?",
                    new String[] {String.valueOf(id)});
            return  (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyPlan(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_PERIODPLAN,
                        DBHelper.KEY_MONEY_PERIODPLAN_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_PERIODPLAN, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyPNL
    public int GetCountMoneyPNL() throws Exception { return GetCount(TABLE_MONEY_PNL); }

    public Cursor QueryMoneyPNL(
                                Long accumulationId,
                                Calendar dateStart,
                                Calendar dateEnd,
                                MoneyTransactionSettings moneyTransactionSettings) throws Exception
    {
        String dayStart = "01";
        String monthStart = "01";
        String yearStart = "1900";
        if (dateStart != null)
        {
            dayStart = String.valueOf(dateStart.get(Calendar.DATE));
            monthStart = String.valueOf(dateStart.get(Calendar.MONTH));
            yearStart = String.valueOf(dateStart.get(Calendar.YEAR));
        }

        String dayEnd = "31";
        String monthEnd = "12";
        String yearEnd = "9999";

        if (dateEnd != null)
        {
            dayEnd = String.valueOf(dateEnd.get(Calendar.DATE));
            monthEnd = String.valueOf(dateEnd.get(Calendar.MONTH));
            yearEnd = String.valueOf(dateEnd.get(Calendar.YEAR));
        }

        try
        {
            String sql = "select distinct " + TABLE_MONEY_PNL +
                    ".*, ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_COST_CATEGORY_NAME +
                    ", ctg1." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_COST_CATEGORY_ICON +
                    ", ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY +
                    ", ctg2." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_INC_CATEGORY_NAME +
                    ", ctg2." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_INC_CATEGORY_ICON +
                    ", ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY +
                    ", ctg3." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_SUB_CATEGORY_NAME +
                    ", ctg3." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_SUB_CATEGORY_ICON +
                    " from " + TABLE_MONEY_PNL;

            if (moneyTransactionSettings != null
                    && moneyTransactionSettings.get_enumTransactionType().equals(EnumTransactionType.PNL)
                    && moneyTransactionSettings.get_tagId() != null
                    && moneyTransactionSettings.get_tagId() != 0L)
                sql +=
                        " inner join " + TABLE_TAGGED + " tagged on tagged." + KEY_TAGGED_EXTERNALID +
                        " = " + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_ID +
                        " inner join " + TABLE_TAG + " tag on tag." + KEY_TAG_ID +
                                " = tagged." + KEY_TAGGED_TAGID;

            sql +=
                    " left join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                    " join " + TABLE_MONEY_CATEGORY + " ctg2 on " + "ctg2." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg3 on " + "ctg3." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_SUB_CATEGORY_ID +

                    " where ";

            if (moneyTransactionSettings != null
                    && moneyTransactionSettings.get_enumTransactionType().equals(EnumTransactionType.PNL))
            {
                if (moneyTransactionSettings.get_typeCategory() != null &&
                        moneyTransactionSettings.get_typeCategory() != EnumTypeCategory.All)
                {
                    if (moneyTransactionSettings.get_typeCategory().equals(EnumTypeCategory.Increase) ||
                            moneyTransactionSettings.get_typeCategory().equals(EnumTypeCategory.Cost))
                        sql += " (ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "='" +
                                moneyTransactionSettings.get_typeCategory() + "' OR " +
                                " ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "='" +
                                moneyTransactionSettings.get_typeCategory() + "') AND ";
                    else if (moneyTransactionSettings.get_typeCategory().equals(EnumTypeCategory.Wallet))
                        sql += " (ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "='" +
                                moneyTransactionSettings.get_typeCategory() + "' AND " +
                                " ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "='" +
                                moneyTransactionSettings.get_typeCategory() + "') AND ";
                }

                if (moneyTransactionSettings.get_isPlan() != null)
                {
                    if (moneyTransactionSettings.get_isPlan())
                        sql += " (" + KEY_MONEY_PNL_ISPLAN + "= 1) AND ";
                    else
                        sql += " (" + KEY_MONEY_PNL_ISPLAN + "= 0) AND ";
                }

                if (moneyTransactionSettings.get_categoryId() != null &&
                        moneyTransactionSettings.get_categoryId() != 0L)
                    sql += "(" + KEY_MONEY_PNL_INC_CATEGORY_ID + "=" +
                            moneyTransactionSettings.get_categoryId() + " OR " +
                            KEY_MONEY_PNL_COST_CATEGORY_ID + "=" +
                            moneyTransactionSettings.get_categoryId() + ") AND ";

                if (moneyTransactionSettings.get_subCategoryId() != null &&
                        moneyTransactionSettings.get_subCategoryId() != 0L)
                    sql += "(" + KEY_MONEY_PNL_SUB_CATEGORY_ID + "=" +
                            moneyTransactionSettings.get_subCategoryId() + ") AND ";

                if (moneyTransactionSettings.get_tagId() != null
                        && moneyTransactionSettings.get_tagId() != 0L)
                    sql += "(tag." + KEY_TAG_ID + "=" + moneyTransactionSettings.get_tagId() + ") AND ";
            }

            if (accumulationId != null)
                sql += "(" + KEY_MONEY_PNL_ACCUMULATION_ID + "=?) AND ";

            sql += "(" + KEY_MONEY_PNL_YEAR_INT + ">? OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + ">?) OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "=? AND " +
                    KEY_MONEY_PNL_DATE_INT + ">=?))" +
                    " AND (" + KEY_MONEY_PNL_YEAR_INT + "<? OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "<?) OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "=? AND " +
                    KEY_MONEY_PNL_DATE_INT + "<=?))" +
                    " order by " + KEY_MONEY_PNL_YEAR_INT + " desc," +
                    KEY_MONEY_PNL_MONTH_INT + " desc," +
                    KEY_MONEY_PNL_DATE_INT + " desc";

            String[] params =
                    new String[] {yearStart, yearStart, monthStart, yearStart, monthStart, dayStart,
                        yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyPNL(EnumTypeCategory typeCategory,
                                Calendar dateStart, Calendar dateEnd,
                                boolean accumulation) throws Exception
    {
        String dayStart = "01";
        String monthStart = "01";
        String yearStart = "1900";
        if (dateStart != null)
        {
            dayStart = String.valueOf(dateStart.get(Calendar.DATE));
            monthStart = String.valueOf(dateStart.get(Calendar.MONTH));
            yearStart = String.valueOf(dateStart.get(Calendar.YEAR));
        }

        String dayEnd = "31";
        String monthEnd = "12";
        String yearEnd = "9999";

        if (dateEnd != null)
        {
            dayEnd = String.valueOf(dateEnd.get(Calendar.DATE));
            monthEnd = String.valueOf(dateEnd.get(Calendar.MONTH));
            yearEnd = String.valueOf(dateEnd.get(Calendar.YEAR));
        }

        try
        {
            String sql = "select " + TABLE_MONEY_PNL +
                    ".*, ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_COST_CATEGORY_NAME +
                    ", ctg1." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_COST_CATEGORY_ICON +
                    ", ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY +
                    ", ctg2." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_INC_CATEGORY_NAME +
                    ", ctg2." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_INC_CATEGORY_ICON +
                    ", ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY +
                    ", ctg3." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_SUB_CATEGORY_NAME +
                    ", ctg3." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_SUB_CATEGORY_ICON +
                    " from " + TABLE_MONEY_PNL +

                    " join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                    " join " + TABLE_MONEY_CATEGORY + " ctg2 on " + "ctg2." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg3 on " + "ctg3." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_SUB_CATEGORY_ID +

                    " where (ctg1." +
                    KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=? OR ctg2." +
                    KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=?)" +
                    " AND (" + KEY_MONEY_PNL_YEAR_INT + ">? OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + ">?) OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "=? AND " +
                    KEY_MONEY_PNL_DATE_INT + ">=?))" +
                    " AND (" + KEY_MONEY_PNL_YEAR_INT + "<? OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "<?) OR (" +
                    KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "=? AND " +
                    KEY_MONEY_PNL_DATE_INT + "<=?))";
            if (accumulation)
                sql += " AND (" + KEY_MONEY_PNL_ACCUMULATION_ID + ">0)";
            sql += " order by " + KEY_MONEY_PNL_YEAR_INT + " desc," +
                    KEY_MONEY_PNL_MONTH_INT + " desc," +
                    KEY_MONEY_PNL_DATE_INT + " desc";

            return getReadableDatabase().rawQuery(sql,
                    new String[] {typeCategory.toString(), typeCategory.toString(),
                            yearStart, yearStart, monthStart, yearStart, monthStart, dayStart,
                            yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyPNL4Credit(Long creditId, EnumTypeCategory typeCategory) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_PNL +
                    ".*, ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_COST_CATEGORY_NAME +
                    ", ctg1." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_COST_CATEGORY_ICON +
                    ", ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY +
                    ", ctg2." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_INC_CATEGORY_NAME +
                    ", ctg2." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_INC_CATEGORY_ICON +
                    ", ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY +
                    ", ctg3." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_SUB_CATEGORY_NAME +
                    " from " + TABLE_MONEY_PNL +

                    " join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                    " join " + TABLE_MONEY_CATEGORY + " ctg2 on " + "ctg2." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg3 on " + "ctg3." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_SUB_CATEGORY_ID +

                    " where (ctg1." +
                    KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=? OR ctg2." +
                    KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=?)" +
                    " AND " + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_CREDIT_ID + "=?" +
                    " order by " + KEY_MONEY_PNL_YEAR_INT + " ," +
                    KEY_MONEY_PNL_MONTH_INT + " ," +
                    KEY_MONEY_PNL_DATE_INT;

            return getReadableDatabase().rawQuery(sql,
                    new String[] {typeCategory.toString(), typeCategory.toString(),
                    String.valueOf(creditId)});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private int QueryMoneyPNLCount4Accumulation(Long targetId) throws Exception
    {
        try
        {
            Long id = OneMoneyCategoryId4Target(targetId, EnumTypeCategory.Wallet);
            Cursor cursor = getReadableDatabase().rawQuery(
                    "select * from " + TABLE_MONEY_PNL +
                    " WHERE " + KEY_MONEY_PNL_INC_CATEGORY_ID + "=? OR " +
                    KEY_MONEY_PNL_COST_CATEGORY_ID + "=?",
                    new String[]{ String.valueOf(id), String.valueOf(id) });
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyPNL4Accumulation(int accumulationId, EnumTypeCategory typeCategory) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_PNL +
                    ".*, ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_COST_CATEGORY_NAME +
                    ", ctg1." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_COST_CATEGORY_ICON +
                    ", ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_COST_CATEGORY_TYPE_CATEGORY +
                    ", ctg2." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_INC_CATEGORY_NAME +
                    ", ctg2." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_PNL_INC_CATEGORY_ICON +
                    ", ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + " as " + key_MONEY_PNL_INC_CATEGORY_TYPE_CATEGORY +
                    ", ctg3." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_PNL_SUB_CATEGORY_NAME +
                    " from " + TABLE_MONEY_PNL +

                    " left join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_COST_CATEGORY_ID +
                    " join " + TABLE_MONEY_CATEGORY + " ctg2 on " + "ctg2." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_INC_CATEGORY_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg3 on " + "ctg3." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_SUB_CATEGORY_ID +

                    " where " +
                    "(" +
                    "ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=? OR " +
                    "ctg2." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=?)" + " AND " +
                    TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_ACCUMULATION_ID + "=?" +
                    " order by " + KEY_MONEY_PNL_YEAR_INT + " ," +
                    KEY_MONEY_PNL_MONTH_INT + " ," +
                    KEY_MONEY_PNL_DATE_INT;

            return getReadableDatabase().rawQuery(sql,
                    new String[] {
                            typeCategory.toString(),
                            typeCategory.toString(),
                            String.valueOf(accumulationId)
            });
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public Cursor Query1INcreaseMoneyPNL(Long subCategoryId, Long creditId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_PNL + ".*" +
                    " from " + TABLE_MONEY_PNL +
                    " join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_SUB_CATEGORY_ID +
                    " where (ctg1." + KEY_MONEY_CATEGORY_TYPE_CATEGORY + "=? AND " +
                    TABLE_MONEY_PNL + "." + KEY_MONEY_PNL_CREDIT_ID + "=? AND " +
                    KEY_MONEY_PNL_SUB_CATEGORY_ID + "=?)";

            Cursor cursor = getReadableDatabase().rawQuery(sql,
                    new String[] {EnumTypeCategory.Increase.toString(),
                    String.valueOf(creditId),
                    String.valueOf(subCategoryId)});

            if (cursor.getCount() == 1)
                return cursor;

            return null;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyPNLAccumulationIdAndCalculated(
            int accumulationId, boolean calculated, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_PNL_ACCUMULATION_ID, accumulationId);
        contentValues.put(DBHelper.KEY_MONEY_PNL_CALCULATED, calculated);

        return PutCustom(contentValues, typeAction);
    }

    private ContentValues PutMoneyPNL(MoneyPNL moneyPNL, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_PNL_COST_CATEGORY_ID, moneyPNL.getCostCategoryId());
        contentValues.put(DBHelper.KEY_MONEY_PNL_INC_CATEGORY_ID, moneyPNL.getIncreaseCategoryId());
        contentValues.put(DBHelper.KEY_MONEY_PNL_SUB_CATEGORY_ID, moneyPNL.getSubCategoryId());
//        contentValues.put(DBHelper.KEY_MONEY_PNL_SUM, moneyPNL.getSum());
        if (DATABASE_VERSION > 8)
            contentValues.put(DBHelper.KEY_MONEY_PNL_SUM_L, moneyPNL.get_sumLong());
        contentValues.put(DBHelper.KEY_MONEY_PNL_DATE_INT, moneyPNL.getDay());
        contentValues.put(DBHelper.KEY_MONEY_PNL_MONTH_INT, moneyPNL.getMonth());
        contentValues.put(DBHelper.KEY_MONEY_PNL_YEAR_INT, moneyPNL.getYear());
        contentValues.put(DBHelper.KEY_MONEY_PNL_TASK_ID, moneyPNL.get_taskId());
        contentValues.put(DBHelper.KEY_MONEY_PNL_COMMENT, moneyPNL.get_comment());
        contentValues.put(DBHelper.KEY_MONEY_PNL_ISPLAN, moneyPNL.get_isPlan());
        contentValues.put(DBHelper.KEY_MONEY_PNL_CREDIT_ID, moneyPNL.get_creditId());
        contentValues.put(DBHelper.KEY_MONEY_PNL_ACCUMULATION_ID, moneyPNL.get_accumulationId());
        contentValues.put(DBHelper.KEY_MONEY_PNL_CALCULATED, moneyPNL.get_calculated());
//        contentValues.put(DBHelper.KEY_MONEY_PNL_PERCENTSUM, moneyPNL.get_percentSum());
        if (DATABASE_VERSION > 8)
            contentValues.put(DBHelper.KEY_MONEY_PNL_PERCENTSUM_L, moneyPNL.get_percentSumLong());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyPNL(MoneyPNL moneyPNL) throws Exception
    {
        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Long id;
            boolean b;

            sqLiteDatabase.beginTransaction();
            try
            {
                id = sqLiteDatabase.insert(
                        DBHelper.TABLE_MONEY_PNL, null,
                        PutMoneyPNL(moneyPNL, EnumTypeAction.Insert));
                b = id > 0;

                if (b && !moneyPNL.get_isPlan())
                {
                    if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getIncreaseCategoryId(), moneyPNL.get_totalSumLong());
                    }
                    else if (moneyPNL.get_typeCategory() == EnumTypeCategory.Cost)
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());

                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getSubCategoryId(), -moneyPNL.get_totalSumLong());
                    }
                    else
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getIncreaseCategoryId(), moneyPNL.get_totalSumLong());

                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());
                    }
                }

                if (b && !moneyPNL.get_isPlan() && moneyPNL.get_inc4Cost() != null)
                    for (MoneyInc4Cost moneyInc4Cost: moneyPNL.get_inc4Cost())
                    {
                        moneyInc4Cost.set_pnl_id(id);
                        int ii = (int) sqLiteDatabase.insert(DBHelper.TABLE_MONEY_INC4COST, null,
                                PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Insert));
                        b = b && ii > 0;

                        if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                        {
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_totalSumLong());
                        }
                    }

                if (b && moneyPNL.get_Tagged() != null)
                    for (Tagged tagged: moneyPNL.get_Tagged())
                    {
                        tagged.set_externalId(id);
                        int ii = (int) sqLiteDatabase.insert(DBHelper.TABLE_TAGGED, null,
                                PutTagged(tagged, EnumTypeAction.Insert));
                        b = b && ii > 0;
                    }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            if (b)
                return id;
            else
                return 0L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyPNL(@NonNull MoneyPNL moneyPNL) throws Exception
    {
        Long id = moneyPNL.get_id();

        try
        {
            Long incrCreditsSubCategoryId = OneMoneyCategoryId(moneyPNL.get_creditId(),
                    EnumTypeCategory.Increase);
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int updateCount;
            boolean b;

            sqLiteDatabase.beginTransaction();
            try
            {
                updateCount = sqLiteDatabase.update(DBHelper.TABLE_MONEY_PNL,
                        PutMoneyPNL(moneyPNL, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_PNL_ID + "= ?", new String[] {String.valueOf(id)});
                b = updateCount == 1;

                if (b)
                {
                    if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                    {
                        if (!moneyPNL.get_isPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getIncreaseCategoryId(), moneyPNL.get_totalSumLong());

                        if (!moneyPNL.get_oldIsPlan())
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldIncreaseCategoryId(), -moneyPNL.get_oldTotalSumLong());

                        if (b && moneyPNL.get_creditId() != 0)
                        {
                            if (!moneyPNL.get_oldIsPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getOldSubCategoryId(), moneyPNL.get_oldSumLong());

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getSubCategoryId(), -moneyPNL.get_sumLong());
                        }
                    }
                    else if (moneyPNL.get_typeCategory() == EnumTypeCategory.Cost)
                    {
                        if (!moneyPNL.get_isPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());

                        if (!moneyPNL.get_oldIsPlan())
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());

                        if (!moneyPNL.get_isPlan())
                        {
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getSubCategoryId(), -moneyPNL.get_totalSumLong());

                            if (incrCreditsSubCategoryId != 0)
                                b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        incrCreditsSubCategoryId, moneyPNL.get_sumLong());
                        }

                        if (!moneyPNL.get_oldIsPlan())
                        {
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldSubCategoryId(), moneyPNL.get_oldTotalSumLong());

                            if (incrCreditsSubCategoryId != 0)
                                b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        incrCreditsSubCategoryId, -moneyPNL.get_oldSumLong());
                        }
                    }
                    else
                    {
                        if (!moneyPNL.get_isPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getIncreaseCategoryId(), moneyPNL.get_totalSumLong());

                        if (!moneyPNL.get_oldIsPlan())
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldIncreaseCategoryId(), -moneyPNL.get_oldTotalSumLong());

                        if (!moneyPNL.get_isPlan())
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());

                        if (!moneyPNL.get_oldIsPlan())
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());
                    }
                }

                List<Long> ids = new ArrayList<>();
                if (b && moneyPNL.get_inc4Cost() != null)
                    for (MoneyInc4Cost moneyInc4Cost: moneyPNL.get_inc4Cost())
                    {
                        moneyInc4Cost.set_pnl_id(moneyPNL.get_id());

                        if (moneyInc4Cost.get_id() <= 0)
                        {
                            long ii = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_INC4COST, null,
                                    PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Insert));
                            b = b && ii > 0;
                            ids.add(ii);

                            if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                            {
                                b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_totalSumLong());
                            }
                        }
                        else
                        {
                            int ii = sqLiteDatabase.update(DBHelper.TABLE_MONEY_INC4COST,
                                    PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Update),
                                    DBHelper.KEY_MONEY_INC4COST_ID + "= " +
                                            moneyInc4Cost.get_id(), null);

                            if (ii == 0)
                            {
                                ii = (int) sqLiteDatabase.insert(DBHelper.TABLE_MONEY_INC4COST, null,
                                        PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Insert));
                                b = b && ii > 0;

                                if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                                {
                                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                            moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_totalSumLong());
                                }
                            }
                            else
                            {
                                b = b && ii == 1;

                                if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
//                                    if (!moneyInc4Cost.getSum().equals(moneyInc4Cost.get_oldSum()) ||
//                                            moneyInc4Cost.get_percentSum() != moneyInc4Cost.get_oldPercentSum() ||
//                                            moneyInc4Cost.get_inc_subCategory_id() != moneyInc4Cost.get_old_inc_subCategory_id())
                                    if (!moneyInc4Cost.get_sumLong().equals(moneyInc4Cost.get_oldSumLong()) ||
                                            !Objects.equals(moneyInc4Cost.get_percentSumLong(), moneyInc4Cost.get_oldPercentSumLong()) ||
                                            !Objects.equals(moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_old_inc_subCategory_id()))
                                    {
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_totalSumLong());

                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                moneyInc4Cost.get_old_inc_subCategory_id(), -moneyInc4Cost.get_oldTotalSumLong());
                                    }
                            }
                        }
                    }

                if (b && moneyPNL.get_deletedInc4Cost() != null)
                    for (MoneyInc4Cost moneyInc4Cost: moneyPNL.get_deletedInc4Cost())
                    {
                        int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INC4COST,
                                DBHelper.KEY_MONEY_INC4COST_ID + "=" +
                                moneyInc4Cost.get_id(), null);
                        b = b && delCount == 1;

                        if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                        {
                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyInc4Cost.get_old_inc_subCategory_id(), -moneyInc4Cost.get_oldTotalSumLong());
                        }
                    }

                List<Long> ids2 = new ArrayList<>();
                if (b && moneyPNL.get_Tagged() != null)
                    for (Tagged tagged: moneyPNL.get_Tagged())
                    {
                        tagged.set_externalId(moneyPNL.get_id());

                        if (tagged.get_id() <= 0)
                        {
                            long ii = sqLiteDatabase.insert(DBHelper.TABLE_TAGGED, null,
                                    PutTagged(tagged, EnumTypeAction.Insert));
                            b = b && ii > 0;
                            ids2.add(ii);
                        }
                        else
                        {
                            int ii = sqLiteDatabase.update(DBHelper.TABLE_TAGGED,
                                    PutTagged(tagged, EnumTypeAction.Update),
                                    DBHelper.KEY_TAGGED_ID + "= " + tagged.get_id(), null);

                            if (ii == 0)
                            {
                                ii = (int) sqLiteDatabase.insert(DBHelper.TABLE_TAGGED, null,
                                        PutTagged(tagged, EnumTypeAction.Insert));
                                b = b && ii > 0;
                            }
                            else
                                b = b && ii == 1;
                        }
                    }

                if (b && moneyPNL.get_deletedTagged() != null)
                    for (Tagged tagged: moneyPNL.get_deletedTagged())
                    {
                        int delCount = sqLiteDatabase.delete(DBHelper.TABLE_TAGGED,
                                DBHelper.KEY_TAGGED_ID + "=" + tagged.get_id(), null);
                        b = b && delCount == 1;
                    }

                if (b)
                {
                    if (moneyPNL.get_inc4Cost() != null)
                    {
                        int i = 0;
                        for (MoneyInc4Cost moneyInc4Cost: moneyPNL.get_inc4Cost())
                            if (moneyInc4Cost.get_id() <= 0)
                            {
                                moneyInc4Cost.set_id(ids.get(i));
                                i += 1;
                            }
                    }

                    if (moneyPNL.get_Tagged() != null)
                    {
                        int i = 0;
                        for (Tagged tagged: moneyPNL.get_Tagged())
                            if (tagged.get_id() <= 0)
                            {
                                tagged.set_id(ids2.get(i));
                                i += 1;
                            }
                    }

                    sqLiteDatabase.setTransactionSuccessful();
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return  b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyPNL(@NonNull MoneyPNL moneyPNL) throws Exception
    {
        if (moneyPNL.get_id() == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int delCount;
            boolean b;

            sqLiteDatabase.beginTransaction();
            try
            {
                delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_PNL,
                        DBHelper.KEY_MONEY_PNL_ID + "=" + moneyPNL.get_id(), null);
                b = delCount == 1;

                if (b)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_PNL,
                            moneyPNL.get_id());
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    b = idCDelObject > 0;
                }

                if (b && !moneyPNL.get_oldIsPlan())
                {
                    if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getOldIncreaseCategoryId(), -moneyPNL.get_oldTotalSumLong());
                    }
                    else if (moneyPNL.get_typeCategory() == EnumTypeCategory.Cost)
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());

                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getOldSubCategoryId(), moneyPNL.get_oldTotalSumLong());
                    }
                    else
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getOldIncreaseCategoryId(), -moneyPNL.get_oldTotalSumLong());

                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());
                    }
                }

                if (b && moneyPNL.get_inc4Cost() != null)
                    for (MoneyInc4Cost moneyInc4Cost: moneyPNL.get_inc4Cost())
                    {
                            delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INC4COST,
                                    DBHelper.KEY_MONEY_INC4COST_ID + "=" +
                                            moneyInc4Cost.get_id(), null);
                            b = b && delCount == 1;

                            if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                            {
                                b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyInc4Cost.get_old_inc_subCategory_id(), -moneyInc4Cost.get_oldTotalSumLong());
                            }
                        }

                if (b && moneyPNL.get_deletedInc4Cost() != null)
                    for (MoneyInc4Cost moneyInc4Cost: moneyPNL.get_deletedInc4Cost())
                    {
                            delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INC4COST,
                                    DBHelper.KEY_MONEY_INC4COST_ID + "=" +
                                            moneyInc4Cost.get_id(), null);
                            b = b && delCount == 1;

                            if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
                            {
                                b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyInc4Cost.get_old_inc_subCategory_id(), -moneyInc4Cost.get_oldTotalSumLong());
                            }
                        }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

//    public int DeleteMoneyPNLByDate(Calendar dateStart, Calendar dateEnd, Integer categoryId) throws Exception
//    {
//        int count1 = GetCountMoneyPNL();
//        String dayStart = String.valueOf(dateStart.get(Calendar.DATE));
//        String dayEnd = String.valueOf(dateEnd.get(Calendar.DATE));
//        String monthStart = String.valueOf(dateStart.get(Calendar.MONTH));
//        String monthEnd = String.valueOf(dateEnd.get(Calendar.MONTH));
//        String yearStart = String.valueOf(dateStart.get(Calendar.YEAR));
//        String yearEnd = String.valueOf(dateEnd.get(Calendar.YEAR));
//
//        String sql= "delete from " + TABLE_MONEY_PNL + " WHERE " +
//                "(" + KEY_MONEY_PNL_YEAR_INT + ">? OR (" +
//                KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + ">?) OR (" +
//                KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "=? AND " +
//                KEY_MONEY_PNL_DATE_INT + ">=?))" +
//                " AND (" + KEY_MONEY_PNL_YEAR_INT + "<? OR (" +
//                KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "<?) OR (" +
//                KEY_MONEY_PNL_YEAR_INT + "=? AND " + KEY_MONEY_PNL_MONTH_INT + "=? AND " +
//                KEY_MONEY_PNL_DATE_INT + "<=?))";
//
//        String[] params = new String[] {
//                yearStart, yearStart, monthStart, yearStart, monthStart, dayStart,
//                yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd};
//
//        if (categoryId != null && categoryId != -1)
//        {
//            sql += " AND (" + KEY_MONEY_PNL_INC_CATEGORY_ID + " =? OR " +
//                    KEY_MONEY_PNL_COST_CATEGORY_ID + " =?)";
//            params = new String[] {
//                    yearStart, yearStart, monthStart, yearStart, monthStart, dayStart,
//                    yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd, categoryId.toString()};
//        }
//
//        getWritableDatabase().rawQuery(sql, params);
//
////        delCount = getWritableDatabase().delete()
//
//        return count1 - GetCountMoneyPNL();
//    }
    //endregion

    //region MoneyInc4Cost
    public int GetCountMoneyInc4Cost() throws Exception { return GetCount(TABLE_MONEY_INC4COST); }

    public Cursor QueryMoneyInc4Cost(Long pnlId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_INC4COST +
                    ".*, ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_INC_CATEGORY_NAME +
                    ", ctg1." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_INC_CATEGORY_ICON +
                    ", ctg2." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_INC_SUB_CATEGORY_NAME +
                    ", ctg2." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_INC_SUB_CATEGORY_ICON +
                    ", ctg3." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_COST_CATEGORY_NAME +
                    ", ctg3." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_COST_CATEGORY_ICON +
                    ", ctg4." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_COST_SUB_CATEGORY_NAME +
                    ", ctg4." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_COST_SUB_CATEGORY_ICON +
                    " from " + TABLE_MONEY_INC4COST +

                    " join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_INC_ID +
                    " join " + TABLE_MONEY_CATEGORY + " ctg2 on " + "ctg2." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg3 on " + "ctg3." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_COST_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg4 on " + "ctg4." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID
                    + " where (" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_PNL_ID  + "=?)";

            return getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(pnlId)});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyInc4Cost(Calendar dateStart, Calendar dateEnd) throws Exception
    {
        String dayStart = String.valueOf(dateStart.get(Calendar.DATE));
        String dayEnd = String.valueOf(dateEnd.get(Calendar.DATE));
        String monthStart = String.valueOf(dateStart.get(Calendar.MONTH));
        String monthEnd = String.valueOf(dateEnd.get(Calendar.MONTH));
        String yearStart = String.valueOf(dateStart.get(Calendar.YEAR));
        String yearEnd = String.valueOf(dateEnd.get(Calendar.YEAR));

        try
        {
            String sql = "select " + TABLE_MONEY_INC4COST +
                    ".*, ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_INC_CATEGORY_NAME +
                    ", ctg1." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_INC_CATEGORY_ICON +
                    ", ctg2." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_INC_SUB_CATEGORY_NAME +
                    ", ctg2." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_INC_SUB_CATEGORY_ICON +
                    ", ctg3." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_COST_CATEGORY_NAME +
                    ", ctg3." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_COST_CATEGORY_ICON +
                    ", ctg4." + KEY_MONEY_CATEGORY_NAME + " as " + key_MONEY_INC4COST_COST_SUB_CATEGORY_NAME +
                    ", ctg4." + KEY_MONEY_CATEGORY_ICON + " as " + key_MONEY_INC4COST_COST_SUB_CATEGORY_ICON +
                    " from " + TABLE_MONEY_INC4COST +

                    " join " + TABLE_MONEY_CATEGORY + " ctg1 on " + "ctg1." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_INC_ID +
                    " join " + TABLE_MONEY_CATEGORY + " ctg2 on " + "ctg2." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg3 on " + "ctg3." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_COST_ID +
                    " left join " + TABLE_MONEY_CATEGORY + " ctg4 on " + "ctg4." + KEY_MONEY_CATEGORY_ID +
                    "=" + TABLE_MONEY_INC4COST + "." + KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID +
                    " where " +
                    "(" + KEY_MONEY_INC4COST_INC_YEAR_INT + ">? OR (" +
                    KEY_MONEY_INC4COST_INC_YEAR_INT + "=? AND " + KEY_MONEY_INC4COST_INC_MONTH_INT + ">?) OR (" +
                    KEY_MONEY_INC4COST_INC_YEAR_INT + "=? AND " + KEY_MONEY_INC4COST_INC_MONTH_INT + "=? AND " +
                    KEY_MONEY_INC4COST_INC_DATE_INT + ">=?))" +
                    " AND (" + KEY_MONEY_INC4COST_INC_YEAR_INT + "<? OR (" +
                    KEY_MONEY_INC4COST_INC_YEAR_INT + "=? AND " + KEY_MONEY_INC4COST_INC_MONTH_INT + "<?) OR (" +
                    KEY_MONEY_INC4COST_INC_YEAR_INT + "=? AND " + KEY_MONEY_INC4COST_INC_MONTH_INT + "=? AND " +
                    KEY_MONEY_INC4COST_INC_DATE_INT + "<=?))" +
                    " order by " + KEY_MONEY_INC4COST_INC_YEAR_INT + " desc," +
                    KEY_MONEY_INC4COST_INC_MONTH_INT + " desc," +
                    KEY_MONEY_INC4COST_INC_DATE_INT + " desc";

            String[] params = new String[] {yearStart, yearStart, monthStart, yearStart, monthStart, dayStart,
                    yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyInc4Cost(MoneyInc4Cost moneyInc4Cost, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_PNL_ID, moneyInc4Cost.get_pnl_id());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_COST_ID, moneyInc4Cost.getCostCategoryId());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_COST_SUB_CATEGORY_ID, moneyInc4Cost.get_cost_subCategory_id());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_INC_ID, moneyInc4Cost.getIncreaseCategoryId());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID, moneyInc4Cost.get_inc_subCategory_id());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_INC_DATE_INT, moneyInc4Cost.getDay());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_INC_MONTH_INT, moneyInc4Cost.getMonth());
        contentValues.put(DBHelper.KEY_MONEY_INC4COST_INC_YEAR_INT, moneyInc4Cost.getYear());
//        contentValues.put(DBHelper.KEY_MONEY_INC4COST_SUM, moneyInc4Cost.getSum());
        if (DATABASE_VERSION > 8)
            contentValues.put(DBHelper.KEY_MONEY_INC4COST_SUM_L, moneyInc4Cost.get_sumLong());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyInc4Cost(MoneyInc4Cost moneyInc4Cost) throws Exception
    {
        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            long id;
            boolean b;

            sqLiteDatabase.beginTransaction();
            try
            {
                id = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_INC4COST, null,
                        PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Insert));
                b= id > 0;

                if (b)
                {
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_totalSumLong());

                    if (moneyInc4Cost.get_typeCategory() == EnumTypeCategory.Wallet)
                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyInc4Cost.get_cost_subCategory_id(), -moneyInc4Cost.get_totalSumLong());
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            if (b)
                return id;
            else
                return 0L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyInc4Cost(MoneyInc4Cost moneyInc4Cost) throws Exception
    {
        Long id = moneyInc4Cost.get_id();

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                int updateCount = sqLiteDatabase.update(DBHelper.TABLE_MONEY_INC4COST,
                        PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_INC4COST_ID + "= ?", new String[]{String.valueOf(id)});
                b = updateCount == 1;

                if (b)
                {
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyInc4Cost.get_old_inc_subCategory_id(), -moneyInc4Cost.get_oldTotalSumLong());

                    if (moneyInc4Cost.get_typeCategory() == EnumTypeCategory.Wallet)
                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyInc4Cost.get_old_cost_subCategory_id(), moneyInc4Cost.get_oldTotalSumLong());

                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyInc4Cost.get_inc_subCategory_id(), moneyInc4Cost.get_totalSumLong());

                    if (moneyInc4Cost.get_typeCategory() == EnumTypeCategory.Wallet)
                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyInc4Cost.get_cost_subCategory_id(), -moneyInc4Cost.get_totalSumLong());
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return b;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyInc4Cost(MoneyInc4Cost moneyInc4Cost) throws Exception
    {
        Long id = Long.valueOf(moneyInc4Cost.get_id());
        if (id == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_INC4COST,
                        DBHelper.KEY_MONEY_INC4COST_ID + "=" + id, null);
                b = delCount == 1;

                if (b)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_INC4COST, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);
                    b = idCDelObject > 0;
                }

                if (b)
                {
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyInc4Cost.get_old_inc_subCategory_id(), -moneyInc4Cost.get_oldTotalSumLong());

                    if (moneyInc4Cost.get_typeCategory() == EnumTypeCategory.Wallet)
                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyInc4Cost.get_old_cost_subCategory_id(), moneyInc4Cost.get_oldTotalSumLong());
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyCredit
    public int GetCountMoneyCredit() throws Exception { return GetCount(TABLE_MONEY_CREDIT); }

    public Cursor QueryMoneyCredit() throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_CREDIT + ".*, " +
                    TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_NAME + " as " + key_MONEY_CREDIT_TARGET_NAME +
                    " from " + TABLE_MONEY_CREDIT +
                    " left join " + TABLE_MONEY_TARGET + " on" +
                    " " + TABLE_MONEY_CREDIT + "." + KEY_MONEY_CREDIT_TARGET_ID +
                    "=" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_ID;

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor GetOneMoneyCredit(Long creditId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_CREDIT + ".*, " +
                    TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_NAME + " as " + key_MONEY_CREDIT_TARGET_NAME +
                    " from " + TABLE_MONEY_CREDIT +
                    " left join " + TABLE_MONEY_TARGET + " on" +
                    " " + TABLE_MONEY_CREDIT + "." + KEY_MONEY_CREDIT_TARGET_ID +
                    "=" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_ID +
                    " where " + TABLE_MONEY_CREDIT + "." + KEY_MONEY_CREDIT_ID + "=" + creditId;

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor GetOneMoneyCredit4Target(Long targetId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_CREDIT + ".*, " +
                    TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_NAME + " as " + key_MONEY_CREDIT_TARGET_NAME +
                    " from " + TABLE_MONEY_CREDIT +
                    " left join " + TABLE_MONEY_TARGET + " on" +
                    " " + TABLE_MONEY_CREDIT + "." + KEY_MONEY_CREDIT_TARGET_ID +
                    "=" + TABLE_MONEY_TARGET + "." + KEY_MONEY_TARGET_ID +
                    " where " + TABLE_MONEY_CREDIT + "." + KEY_MONEY_CREDIT_TARGET_ID + "=" + targetId;

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyCredit(MoneyCredit moneyCredit, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_TARGET_ID, moneyCredit.get_target_id());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_NAME, moneyCredit.get_name());
//        contentValues.put(DBHelper.KEY_MONEY_CREDIT_STARTSUMM, moneyCredit.get_startSumm());
        if (DATABASE_VERSION > 8)
            contentValues.put(DBHelper.KEY_MONEY_CREDIT_STARTSUM_L, moneyCredit.get_startSummLong());
//        contentValues.put(DBHelper.KEY_MONEY_CREDIT_SUM, moneyCredit.get_summ());
        if (DATABASE_VERSION > 8)
            contentValues.put(DBHelper.KEY_MONEY_CREDIT_SUM_L, moneyCredit.get_sumLong());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_PERCENT, moneyCredit.get_percent());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_DATESTART_INT, moneyCredit.get_calendarStart_Date());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_MONTHSTART_INT, moneyCredit.get_calendarStart_Month());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_YEARSTART_INT, moneyCredit.get_calendarStart_Year());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_LOANTERM, moneyCredit.get_loanTerm());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_TYPE, moneyCredit.get_type().toString());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_STATUS, moneyCredit.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_WALLET, moneyCredit.get_wallet());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_INC4COST_CATEGORY, moneyCredit.get_inc4CostCategoryId());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_INC4COST_SUBCATEGORY, moneyCredit.get_inc4CostSubCategoryId());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyCredit(MoneyCredit moneyCredit, Context context) throws Exception
    {
        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        Long increaseCreditsCategoryId = globalVariable.get_increaseCreditsCategoryId();
        Long costCreditsCategoryId = globalVariable.get_costCreditsCategoryId();
        int increaseCreditsCategoryPos = 1;
        int costCreditsCategoryPos = 1;
        int increaseCreditsSubCategoryPos = 1;
        int costCreditsSubCategoryPos = 1;
        Long increaseCreditsSubCategoryId = 0L;
        Long costCreditsSubCategoryId = 0L;

        if (increaseCreditsCategoryId == 0)
            increaseCreditsCategoryPos = QueryMoneyCategoryMaxPosition(0L, EnumTypeCategory.Increase) + 1;
        else
            increaseCreditsSubCategoryPos = QueryMoneyCategoryMaxPosition(
                    increaseCreditsCategoryId, EnumTypeCategory.Increase) + 1;

        if (costCreditsCategoryId == 0)
            costCreditsCategoryPos = QueryMoneyCategoryMaxPosition(0L, EnumTypeCategory.Cost) + 1;
        else
            costCreditsSubCategoryPos = QueryMoneyCategoryMaxPosition(
                    costCreditsCategoryId, EnumTypeCategory.Cost) + 1;

        Long typeAsset4CreditId = globalVariable.get_typeAsset4Creditd();
        Cursor cursor = QueryOneMoneyTypeAsset(typeAsset4CreditId);
        if (!cursor.moveToFirst())
            typeAsset4CreditId = 0L;
        cursor.close();

        long assetId;
        int moneyTypeAssetMaxPosition = QueryMoneyTypeAssetMaxPosition(0L);
        int moneyAssetMaxPosition = 1;
        if (typeAsset4CreditId != null && typeAsset4CreditId != 0L)
            moneyAssetMaxPosition = QueryMoneyAssetMaxPosition(typeAsset4CreditId) + 1;
        int moneyLotAssetMaxPosition = QueryMoneyLotAssetMaxPosition(typeAsset4CreditId) + 1;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            long id;
            boolean b;

            try
            {
                id = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CREDIT, null,
                        PutMoneyCredit(moneyCredit, EnumTypeAction.Insert));
                moneyCredit.set_id(id);
                b = id > 0;

                if (b && increaseCreditsCategoryId == 0)
                {
                    MoneyCategory moneyCategory1 = new MoneyCategory(
                            0L, 0L,
                            increaseCreditsCategoryPos,
                            context.getString(R.string.money_credit),
                            0L,
                            EnumTypeCategory.Increase,
                            false, false,
                            false, false,
                            0L,
                            EnumCategoryType.Base,
//                            EnumCategoryStatus.Active,
                            0L,
                            false,
                            0L);
                    increaseCreditsCategoryId = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CATEGORY,
                            null, PutMoneyCategory(moneyCategory1, EnumTypeAction.Insert));
                    b = increaseCreditsCategoryId > 0;
                }

                if (b && costCreditsCategoryId == 0)
                {
                    MoneyCategory moneyCategory2 = new MoneyCategory(
                            0L, 0L,
                            costCreditsCategoryPos,
                            context.getString(R.string.money_credit),
                            0L,
                            EnumTypeCategory.Cost,
                            false, false,
                            false, false,
                            0L,
                            EnumCategoryType.Base,
//                            EnumCategoryStatus.Active,
                            0L,
                            false,
                            0L);
                    costCreditsCategoryId = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CATEGORY,
                            null, PutMoneyCategory(moneyCategory2, EnumTypeAction.Insert));
                    b = costCreditsCategoryId > 0;
                }

                if (b)
                {
                    MoneyCategory moneyCategory11 = new MoneyCategory(
                            0L, increaseCreditsCategoryId,
                            increaseCreditsSubCategoryPos,
                            moneyCredit.get_name(),
                            moneyCredit.get_status().equals(EnumCreditStatus.Active) ? -moneyCredit.get_startSummLong() : 0L,
                            EnumTypeCategory.Increase,
                            false, false,
                            false, false,
                            moneyCredit.get_target_id(),
                            EnumCategoryType.Base,
//                            EnumCategoryStatus.Active,
                            moneyCredit.get_id(),
                            false,
                            0L);
                    increaseCreditsSubCategoryId = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CATEGORY,
                            null, PutMoneyCategory(moneyCategory11, EnumTypeAction.Insert));
                    b = increaseCreditsSubCategoryId > 0;
                }

                if (b)
                {
                    MoneyCategory moneyCategory21 = new MoneyCategory(
                            0L, costCreditsCategoryId,
                            costCreditsSubCategoryPos,
                            moneyCredit.get_name(),
                            0L,
                            EnumTypeCategory.Cost,
                            false, false,
                            false, false,
                            moneyCredit.get_target_id(),
                            EnumCategoryType.Base,
//                            EnumCategoryStatus.Active,
                            moneyCredit.get_id(),
                            false,
                            0L);
                    costCreditsSubCategoryId = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_CATEGORY,
                            null, PutMoneyCategory(moneyCategory21, EnumTypeAction.Insert));
                    b = costCreditsSubCategoryId > 0;
                }

                MoneyPNL moneyPNL_increase = null;
                if (b)
                {
                    moneyPNL_increase = new MoneyPNL(0L, EnumTypeCategory.Increase,
                            moneyCredit.get_wallet(), moneyCredit.get_wallet(),
                            increaseCreditsCategoryId, increaseCreditsCategoryId,
                            increaseCreditsSubCategoryId, increaseCreditsSubCategoryId,
                            "", "", "",
                            moneyCredit.get_calendarStart_Date(),
                            moneyCredit.get_calendarStart_Month(),
                            moneyCredit.get_calendarStart_Year(),
                            moneyCredit.get_startSummLong(),
                            moneyCredit.get_startSummLong(),
                            0, "",
                            0L, 0L, 0L,
                            !moneyCredit.get_status().equals(EnumCreditStatus.Active),
                            !moneyCredit.get_status().equals(EnumCreditStatus.Active),
                            moneyCredit.get_id(), 0L, true,
                            0L, 0L,
                            null, null,
                            null, null);

                    moneyPNL_increase.set_id(sqLiteDatabase.insert(TABLE_MONEY_PNL,
                            null, PutMoneyPNL(moneyPNL_increase, EnumTypeAction.Insert)));
                    b = moneyPNL_increase.get_id() > 0;

                    if (b && moneyCredit.get_status().equals(EnumCreditStatus.Active))
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                moneyPNL_increase.getIncreaseCategoryId(), moneyPNL_increase.get_totalSumLong());
                }

                if (b)
                {
                    MoneyInc4Cost moneyInc4Cost = new MoneyInc4CostIndependent(
                            0L,
                            moneyPNL_increase.get_id(),
                            EnumTypeCategory.Increase,
                            0L,
                            0L,
                            0L,
                            "",
                            0L,
                            "",
                            0L,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostCategoryId() : costCreditsCategoryId,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostSubCategoryId() : costCreditsSubCategoryId,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostSubCategoryId() : costCreditsSubCategoryId,
                            "",
                            0L,
                            "",
                            0L,
                            moneyCredit.get_calendarStart_Date(),
                            moneyCredit.get_calendarStart_Month(),
                            moneyCredit.get_calendarStart_Year(),
                            moneyCredit.get_startSummLong(),
                            moneyCredit.get_startSummLong());

                    moneyInc4Cost.set_id(sqLiteDatabase.insert(TABLE_MONEY_INC4COST,
                            null, PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Insert)));
                    b = moneyInc4Cost.get_id() > 0;
                }

                if (b && moneyCredit.get_status().equals(EnumCreditStatus.Active))
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostSubCategoryId() : costCreditsSubCategoryId,
                            moneyCredit.get_startSummLong());

                if (b && moneyCredit.get_pnls() != null)
                    for (MoneyPNL moneyPNL: moneyCredit.get_pnls())
                    {
                        moneyPNL.set_creditId(moneyCredit.get_id());
                        moneyPNL.set_IncreaseCategoryId(costCreditsCategoryId);
                        moneyPNL.set_subCategoryId(costCreditsSubCategoryId);
                        b = sqLiteDatabase.insert(TABLE_MONEY_PNL,
                                null, PutMoneyPNL(moneyPNL, EnumTypeAction.Insert)) > 0;

                        if (b && !moneyPNL.get_isPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());

                        if (!b)
                            break;

                        if (!moneyPNL.get_isPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    increaseCreditsSubCategoryId, moneyPNL.get_sumLong());

                        if (!b)
                            break;
                    }

                if (b && moneyCredit.get_pivots() != null)
                    for (MoneyCreditPivotPoint moneyCreditPivotPoint: moneyCredit.get_pivots())
                    {
                        moneyCreditPivotPoint.set_creditId(moneyCredit.get_id());
                        b = sqLiteDatabase.insert(TABLE_MONEY_CREDIT_PIVOT_POINT,
                                null, PutMoneyPivotPoint(moneyCreditPivotPoint, EnumTypeAction.Insert)) > 0;

                        if (!b)
                            break;
                    }

                if (b && typeAsset4CreditId == 0L)
                {
                    MoneyTypeAsset moneyTypeAsset = new MoneyTypeAsset(
                            0L,
                            context.getString(R.string.money_credit),
                            "",
                            0L,
                            moneyTypeAssetMaxPosition,
                            EnumStatus.Started,
                            0);
                    typeAsset4CreditId = sqLiteDatabase.insert(TABLE_MONEY_TYPEASSET, null,
                            PutMoneyTypeAsset(moneyTypeAsset, EnumTypeAction.Insert));
                    b = typeAsset4CreditId > 0;
                }

                MoneyAsset moneyAsset = null;
                if (b)
                {
                    moneyAsset = new MoneyAsset(
                            0L,
                            0L,
                            id,
                            moneyCredit.get_name(),
                            "",
                            moneyAssetMaxPosition,
                            EnumStatus.Started,
                            typeAsset4CreditId,
                            context.getString(R.string.money_credit),
                            EnumKindAsset.Conservative,
                            "",
                            EnumCurrency.RUB,
                            false,
                            1);
                    assetId = sqLiteDatabase.insert(TABLE_MONEY_ASSET, null,
                            PutMoneyAsset(moneyAsset, EnumTypeAction.Insert));
                    b = assetId > 0;
                    moneyAsset.set_id(assetId);
                }

                if (b)
                {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.set(1900, 0, 1);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.set(2900, 11, 31);

                    MoneyLotAsset moneyLotAsset = new MoneyLotAsset(
                            0L,
                            globalVariable.get_creditGroupPortfolioId(),
                            "",
                            globalVariable.get_creditPortfolioId(),
                            "",
                            0L,
                            moneyAsset,
                            moneyCredit.get_name(),
                            "",
                            moneyLotAssetMaxPosition,
                            1,
                            calendarStart,
                            calendarEnd,
                            EnumStatus.Started,
                            MoneyLotAsset.EnumTypeMoneyLotAsset.LotCredit,
                            null,
                            null);
                    b = sqLiteDatabase.insert(TABLE_MONEY_LOT_ASSET, null,
                            PutMoneyLotAsset(moneyLotAsset, EnumTypeAction.Insert)) > 0L;
                }

                if (b)
                {
                    globalVariable.set_increaseCreditsCategoryId(increaseCreditsCategoryId);
                    globalVariable.set_costCreditsCategoryId(costCreditsCategoryId);
                    globalVariable.set_typeAsset4CreditId(typeAsset4CreditId);
                    globalVariable.SaveSharedPreferences4CreditParams();
                    sqLiteDatabase.setTransactionSuccessful();
                }

                return id;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.getMessage());
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyCredit(MoneyCredit moneyCredit, Context context) throws Exception
    {
        Long id = moneyCredit.get_id();

        if (id == 0)
            return false;

        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        Long increaseCreditsCategoryId = globalVariable.get_increaseCreditsCategoryId();

        boolean b;
        Long incrCategoryId = OneMoneyCategoryId(id, EnumTypeCategory.Increase);
        Long costCategoryId = OneMoneyCategoryId(id, EnumTypeCategory.Cost);

        Cursor cursor = Query1INcreaseMoneyPNL(incrCategoryId, id);
        cursor.moveToFirst();
        @SuppressLint("Range")
        Long pnlID = cursor.getLong(cursor.getColumnIndex(KEY_MONEY_PNL_ID));
        int index = cursor.getColumnIndex(KEY_MONEY_PNL_SUB_CATEGORY_ID);
        Long oldCostSubId = cursor.getLong(index == -1 ? 0 : index);
        cursor.close();

        Hashtable<Long, Long> sums = new Hashtable<>();
        cursor = QueryMoneyInc4Cost(pnlID);
        if (cursor.moveToFirst())
        {
            int sumIndex = cursor.getColumnIndex(KEY_MONEY_INC4COST_SUM_L);
            int subCategoryIndex = cursor.getColumnIndex(KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID);
            do
            {
                if (sums.get(subCategoryIndex) == null)
                    sums.put(cursor.getLong(subCategoryIndex), cursor.getLong(sumIndex));
                else
                    sums.put(cursor.getLong(subCategoryIndex), sums.get(subCategoryIndex) + cursor.getLong(sumIndex));
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        Long typeAsset4CreditId = globalVariable.get_typeAsset4Creditd();
        cursor = QueryOneMoneyAsset4Credit(moneyCredit.get_id());
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(KEY_MONEY_ASSET_ID);
        long assetId = cursor.getLong(idIndex);
        cursor.close();

        cursor = QueryMoneyLotAsset4Asset(null, assetId);
        cursor.moveToFirst();
        idIndex = cursor.getColumnIndex(KEY_MONEY_LOT_ASSET_ID);
        long lotAssetId = cursor.getLong(idIndex);
        cursor.close();

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            try
            {
                b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_CREDIT,
                        PutMoneyCredit(moneyCredit, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_CREDIT_ID + "= ?", new String[]{String.valueOf(id)}) == 1;

                if (b)
                    b = sqLiteDatabase.update(TABLE_MONEY_CATEGORY,
                            PutMoneyCategoryName(moneyCredit.get_name()),
                            KEY_MONEY_CATEGORY_ID + "=?",
                            new String[]{ String.valueOf(incrCategoryId) }) == 1;

                if (b)
                    b = sqLiteDatabase.update(TABLE_MONEY_CATEGORY,
                            PutMoneyCategoryName(moneyCredit.get_name()),
                            KEY_MONEY_CATEGORY_ID + "=?",
                            new String[]{ String.valueOf(costCategoryId) }) == 1;

                if (b)
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            incrCategoryId,
                            moneyCredit.get_oldStartSumLong() - moneyCredit.get_startSummLong());

                if (b && moneyCredit.get_oldStatus().equals(EnumCreditStatus.Active))
                {
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyCredit.get_oldWallet(), -moneyCredit.get_oldStartSumLong());



                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            oldCostSubId, moneyCredit.get_oldStartSumLong());
                }

                if (b)
                    b = sqLiteDatabase.delete(TABLE_MONEY_PNL,
                            DBHelper.KEY_MONEY_PNL_ID + "=" + pnlID, null) == 1;

                if (b)
                {
                    int count = sqLiteDatabase.delete(TABLE_MONEY_INC4COST,
                            DBHelper.KEY_MONEY_INC4COST_PNL_ID + "=" + pnlID, null);
                    b = count > 0;
                }

                if (b && moneyCredit.get_oldStatus().equals(EnumCreditStatus.Active))
                    for (Long key: sums.keySet())
                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase, key, -sums.get(key));

                MoneyPNL moneyPNL_increase = null;
                if (b)
                {
                    moneyPNL_increase = new MoneyPNL(0L, EnumTypeCategory.Increase,
                            moneyCredit.get_wallet(), moneyCredit.get_wallet(),
                            increaseCreditsCategoryId, increaseCreditsCategoryId,
                            incrCategoryId, incrCategoryId,
                            "", "", "",
                            moneyCredit.get_calendarStart_Date(),
                            moneyCredit.get_calendarStart_Month(),
                            moneyCredit.get_calendarStart_Year(),
                            moneyCredit.get_status().equals(EnumCreditStatus.Active) ? moneyCredit.get_startSummLong() : 0L,
                            moneyCredit.get_status().equals(EnumCreditStatus.Active) ? moneyCredit.get_startSummLong() : 0L,
                            0, "",
                            0L, 0L, 0L,
                            false, false,
                            moneyCredit.get_id(), 0L, true,
                            0L, 0L,
                            null, null,
                            null, null);
                    moneyPNL_increase.set_id(sqLiteDatabase.insert(TABLE_MONEY_PNL,
                            null, PutMoneyPNL(moneyPNL_increase, EnumTypeAction.Insert)));
                    b = moneyPNL_increase.get_id() > 0;
                }

                if (b && moneyCredit.get_status().equals(EnumCreditStatus.Active))
                {
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyCredit.get_wallet(), moneyCredit.get_startSummLong());

                    b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            incrCategoryId, -moneyCredit.get_startSummLong());
                }

                if (b)
                {
                    MoneyInc4Cost moneyInc4Cost = new MoneyInc4CostIndependent(
                            0L,
                            moneyPNL_increase.get_id(),
                            EnumTypeCategory.Increase,
                            0L,
                            0L,
                            0L,
                            "",
                            0L,
                            "",
                            0L,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostCategoryId() : globalVariable.get_costCreditsCategoryId(),
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostSubCategoryId() : costCategoryId,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostSubCategoryId() : costCategoryId,
                            "",
                            0L,
                            "",
                            0L,
                            moneyCredit.get_calendarStart_Date(),
                            moneyCredit.get_calendarStart_Month(),
                            moneyCredit.get_calendarStart_Year(),
                            moneyCredit.get_startSummLong(),
                            moneyCredit.get_startSummLong());

                    moneyInc4Cost.set_id(sqLiteDatabase.insert(TABLE_MONEY_INC4COST,
                            null, PutMoneyInc4Cost(moneyInc4Cost, EnumTypeAction.Insert)));
                    b = moneyInc4Cost.get_id() > 0;
                }

                if (b && moneyCredit.get_status().equals(EnumCreditStatus.Active))
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                            moneyCredit.get_inc4CostSubCategoryId() > 0 ? moneyCredit.get_inc4CostSubCategoryId() : costCategoryId,
                            moneyCredit.get_startSummLong());

                List<Long> ids = new ArrayList<>();
                if (b && moneyCredit.get_pnls() != null)
                    for (MoneyPNL moneyPNL: moneyCredit.get_pnls())
                    {
                        if (moneyPNL.get_id() == 0)
                        {
                            long ii = sqLiteDatabase.insert(TABLE_MONEY_PNL, null,
                                    PutMoneyPNL(moneyPNL, EnumTypeAction.Insert));
                            b = ii > 0;
                            ids.add(ii);

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getSubCategoryId(), -moneyPNL.get_totalSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        incrCategoryId, moneyPNL.get_sumLong());

                            if (!b)
                                break;
                        }
                        else
                        {
                            b = sqLiteDatabase.update(TABLE_MONEY_PNL,
                                    PutMoneyPNL(moneyPNL, EnumTypeAction.Update),
                                    KEY_MONEY_PNL_ID + "=?",
                                    new String[]{String.valueOf(moneyPNL.get_id())}) == 1;

                            if (!b)
                                break;

                            if (!moneyPNL.get_oldIsPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_oldIsPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getOldSubCategoryId(), moneyPNL.get_oldTotalSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_oldIsPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        incrCategoryId, -moneyPNL.get_oldSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getCostCategoryId(), -moneyPNL.get_totalSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        moneyPNL.getSubCategoryId(), -moneyPNL.get_totalSumLong());

                            if (!b)
                                break;

                            if (!moneyPNL.get_isPlan())
                                b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                        incrCategoryId, moneyPNL.get_sumLong());

                            if (!b)
                                break;
                        }
                    }

                if (b && moneyCredit.get_deletedPnls() != null)
                    for (MoneyPNL moneyPNL: moneyCredit.get_deletedPnls())
                    {
                        b = sqLiteDatabase.delete(TABLE_MONEY_PNL,
                                KEY_MONEY_PNL_ID + "=?",
                                new String[]{String.valueOf(moneyPNL.get_id())}) == 1;

                        if (!b)
                            break;

                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());

                        if (!b)
                            break;

                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldSubCategoryId(), moneyPNL.get_oldTotalSumLong());

                        if (!b)
                            break;

                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    incrCategoryId, -moneyPNL.get_oldSumLong());

                        if (!b)
                            break;
                    }

                List<Long> idsPivot = new ArrayList<>();
                if (b && moneyCredit.get_pivots() != null)
                    for (MoneyCreditPivotPoint moneyCreditPivotPoint: moneyCredit.get_pivots())
                    {
                        if (moneyCreditPivotPoint.get_id() == 0)
                        {
                            long ii = sqLiteDatabase.insert(TABLE_MONEY_CREDIT_PIVOT_POINT, null,
                                    PutMoneyPivotPoint(moneyCreditPivotPoint, EnumTypeAction.Insert));
                            b = ii > 0;
                            idsPivot.add(ii);

                            if (!b)
                                break;
                        }
                        else
                        {
                            b = sqLiteDatabase.update(TABLE_MONEY_CREDIT_PIVOT_POINT,
                                    PutMoneyPivotPoint(moneyCreditPivotPoint, EnumTypeAction.Update),
                                    KEY_MONEY_CREDIT_PIVOT_POINT_ID + "=?",
                                    new String[]{String.valueOf(moneyCreditPivotPoint.get_id())}) == 1;

                            if (!b)
                                break;
                        }
                    }

                if (b && moneyCredit.get_deletedPivots() != null)
                    for (MoneyCreditPivotPoint moneyCreditPivotPoint: moneyCredit.get_deletedPivots())
                    {
                        b = sqLiteDatabase.delete(TABLE_MONEY_CREDIT_PIVOT_POINT,
                                KEY_MONEY_CREDIT_PIVOT_POINT_ID + "=?",
                                new String[]{String.valueOf(moneyCreditPivotPoint.get_id())}) == 1;

                        if (!b)
                            break;
                    }

                MoneyAsset moneyAsset = null;
                if (b)
                {
                    moneyAsset = new MoneyAsset(
                            assetId,
                            0L,
                            id,
                            moneyCredit.get_name(),
                            "",
                            0,
                            EnumStatus.Started,
                            typeAsset4CreditId,
                            context.getString(R.string.money_credit),
                            EnumKindAsset.Conservative,
                            "",
                            EnumCurrency.RUB,
                            false,
                            1);
                    b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_ASSET,
                            PutMoneyAssetWOPosition(moneyAsset),
                            DBHelper.KEY_MONEY_ASSET_ID + "= ?", new String[]{String.valueOf(assetId)}) == 1;
                }

                if (b)
                {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.set(1900, 0, 1);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.set(2900, 11, 31);

                    MoneyLotAsset moneyLotAsset = new MoneyLotAsset(
                            lotAssetId,
                            globalVariable.get_creditGroupPortfolioId(),
                            "",
                            globalVariable.get_creditPortfolioId(),
                            "",
                            0L,
                            moneyAsset,
                            moneyCredit.get_name(),
                            "",
                            0,
                            1,
                            calendarStart,
                            calendarEnd,
                            EnumStatus.Started,
                            MoneyLotAsset.EnumTypeMoneyLotAsset.LotCredit,
                            null,
                            null);
                    b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_LOT_ASSET,
                            PutMoneyLotAssetName(moneyLotAsset, EnumTypeAction.Update),
                            DBHelper.KEY_MONEY_LOT_ASSET_ID + "= ?", new String[]{String.valueOf(lotAssetId)}) == 1;
                }

                if (b)
                {
                    if (moneyCredit.get_pnls() != null)
                    {
                        int i = 0;
                        for (MoneyPNL moneyPNL : moneyCredit.get_pnls())
                            if (moneyPNL.get_id() == 0)
                            {
                                moneyPNL.set_id(ids.get(i));
                                i += 1;
                            }

                        i = 0;
                        for (MoneyCreditPivotPoint moneyCreditPivotPoint : moneyCredit.get_pivots())
                            if (moneyCreditPivotPoint.get_id() == 0)
                            {
                                moneyCreditPivotPoint.set_id(idsPivot.get(i));
                                i += 1;
                            }
                    }

                    sqLiteDatabase.setTransactionSuccessful();
                }

                return b;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.getMessage());
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyCredit(MoneyCredit moneyCredit, Context context) throws Exception
    {
        Long id = moneyCredit.get_id();
        if (id == 0)
            return false;

        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        Long increaseCreditsCategoryId = globalVariable.get_increaseCreditsCategoryId();
        Long costCreditsCategoryId = globalVariable.get_costCreditsCategoryId();

        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Increase, true, true,
                true, EnumCategoryType.All, null);
        Cursor cursor = QueryMoneyCategory(increaseCreditsCategoryId, moneyCategorySettings, globalVariable);
        boolean delIncrCreditsCategory = cursor.getCount() == 1;
        cursor.close();
        cursor = QueryMoneyCategory(costCreditsCategoryId, moneyCategorySettings, globalVariable);
        boolean delCostCreditsCategory = cursor.getCount() == 1;
        cursor.close();

        Long incrCreditsSubCategoryId = OneMoneyCategoryId(id, EnumTypeCategory.Increase);
        Long costCreditsSubCategoryId = OneMoneyCategoryId(id, EnumTypeCategory.Cost);

        cursor = Query1INcreaseMoneyPNL(incrCreditsSubCategoryId, id);
        cursor.moveToFirst();
        @SuppressLint("Range")
        Long increasePnlId = cursor.getLong(cursor.getColumnIndex(KEY_MONEY_PNL_ID));
        @SuppressLint("Range")
        Long increaseCategoryId = cursor.getLong(cursor.getColumnIndex(KEY_MONEY_PNL_INC_CATEGORY_ID));
        @SuppressLint("Range")
        long sum = cursor.getLong(cursor.getColumnIndex(KEY_MONEY_PNL_SUM_L));
        cursor.close();

        Hashtable<Long, Long> sums = new Hashtable<>();
        cursor = QueryMoneyInc4Cost(increasePnlId);
        if (cursor.moveToFirst())
        {
            int sumIndex = cursor.getColumnIndex(KEY_MONEY_INC4COST_SUM_L);
            int subCategoryIndex = cursor.getColumnIndex(KEY_MONEY_INC4COST_INC_SUB_CATEGORY_ID);
            do
            {
                if (sums.get(subCategoryIndex) == null)
                    sums.put(cursor.getLong(subCategoryIndex), cursor.getLong(sumIndex));
                else
                    sums.put(cursor.getLong(subCategoryIndex), sums.get(subCategoryIndex) + cursor.getLong(sumIndex));
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        cursor = QueryOneMoneyAsset4Credit(moneyCredit.get_id());
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(KEY_MONEY_ASSET_ID);
        long assetId = cursor.getLong(idIndex);
        cursor.close();

        cursor = QueryMoneyLotAsset4Asset(null, assetId);
        cursor.moveToFirst();
        idIndex = cursor.getColumnIndex(KEY_MONEY_LOT_ASSET_ID);
        long lotAssetId = cursor.getLong(idIndex);
        cursor.close();

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_CREDIT,
                        DBHelper.KEY_MONEY_CREDIT_ID + "=" + id, null);
                b = delCount == 1;

                if (b)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_CREDIT, id);
                    b = AddCDelObject(sqLiteDatabase, cDelObject) > 0;
                }

                if (b)
                    b = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_CATEGORY,
                            DBHelper.KEY_MONEY_CATEGORY_ID + "=" + incrCreditsSubCategoryId,
                            null) == 1;

                if (b)
                    b = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_CATEGORY,
                            DBHelper.KEY_MONEY_CATEGORY_ID + "=" + costCreditsSubCategoryId,
                            null) == 1;

                if (b && delIncrCreditsCategory)
                    b = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_CATEGORY,
                            DBHelper.KEY_MONEY_CATEGORY_ID + "=" + increaseCreditsCategoryId,
                            null) == 1;

                if (b && delCostCreditsCategory)
                    b = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_CATEGORY,
                            DBHelper.KEY_MONEY_CATEGORY_ID + "=" + costCreditsCategoryId,
                            null) == 1;

                if (b)
                {
                    int count = sqLiteDatabase.delete(TABLE_MONEY_CREDIT_PIVOT_POINT,
                            DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID + "=" + id, null);
                    b = count == moneyCredit.get_pivots().size() + moneyCredit.get_deletedPivots().size();
                }

                if (b)
                {
                    int count = sqLiteDatabase.delete(TABLE_MONEY_PNL, DBHelper.KEY_MONEY_PNL_CREDIT_ID + "=" + id, null);
                    b = count == moneyCredit.get_pnls().size() + moneyCredit.get_deletedPnls().size() + 1;
                }

                if (b && moneyCredit.get_oldStatus().equals(EnumCreditStatus.Active))
                    b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase, increaseCategoryId, -sum);

                if (b)
                {
                    int count = sqLiteDatabase.delete(TABLE_MONEY_INC4COST,
                            DBHelper.KEY_MONEY_INC4COST_PNL_ID + "=" + increasePnlId, null);
                    b = count > 0;
                }

                if (b && moneyCredit.get_oldStatus().equals(EnumCreditStatus.Active))
                    for (Long key: sums.keySet())
                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase, key, -sums.get(key));

                if (b && moneyCredit.get_pnls() != null)
                    for (MoneyPNL moneyPNL: moneyCredit.get_pnls())
                    {
                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());

                        if (!b)
                            break;

                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    incrCreditsSubCategoryId, -moneyPNL.get_oldSumLong());

                        if (!b)
                            break;
                    }

                if (b && moneyCredit.get_deletedPnls() != null)
                    for (MoneyPNL moneyPNL: moneyCredit.get_deletedPnls())
                    {
                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    moneyPNL.getOldCostCategoryId(), moneyPNL.get_oldTotalSumLong());

                        if (!b)
                            break;

                        if (!moneyPNL.get_oldIsPlan())
                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                    incrCreditsSubCategoryId, -moneyPNL.get_oldSumLong());

                        if (!b)
                            break;
                    }

                if (b)
                    b = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_LOT_ASSET,
                            DBHelper.KEY_MONEY_LOT_ASSET_ID + "=" + lotAssetId,
                            null) == 1;

                if (b)
                    b = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_ASSET,
                            DBHelper.KEY_MONEY_ASSET_ID + "=" + assetId,
                            null) == 1;

                if (b)
                {
                    if (delIncrCreditsCategory)
                        globalVariable.set_increaseCreditsCategoryId(0L);
                    if (delCostCreditsCategory)
                        globalVariable.set_costCreditsCategoryId(0L);
                    globalVariable.SaveSharedPreferences4CreditParams();
                    sqLiteDatabase.setTransactionSuccessful();
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyCreditPivotPoint
    public Cursor GetMoneyPivotPoints4Credit(Long creditId) throws Exception
    {
        try
        {
            String sql = "select * from " + TABLE_MONEY_CREDIT_PIVOT_POINT +
                    " where " + TABLE_MONEY_CREDIT_PIVOT_POINT + "." +
                    KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID + "=" + creditId +
                    " order by " + KEY_MONEY_CREDIT_PIVOT_POINT_LOANTERM + " desc";

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyPivotPoint(MoneyCreditPivotPoint moneyCreditPivotPoint, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_CREDIT_ID, moneyCreditPivotPoint.get_creditId());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_STARTSUM_L, moneyCreditPivotPoint.get_startSumLong());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_PERCENT, moneyCreditPivotPoint.get_percent());
        contentValues.put(DBHelper.KEY_MONEY_CREDIT_PIVOT_POINT_LOANTERM, moneyCreditPivotPoint.get_loanTerm());

        return PutCustom(contentValues, typeAction);
    }
    //endregion

    //region MoneyAccumulation
    public int GetCountMoneyAccumulation() throws Exception { return GetCount(TABLE_MONEY_ACCUMULATION); }

    public Cursor QueryMoneyAccumulation(Integer targetId) throws Exception
    {
        try
        {
            StringBuilder sql = new StringBuilder();
            sql.append(" select ").append(TABLE_MONEY_ACCUMULATION).append(".*,")
                    .append(TABLE_MONEY_TARGET).append(".").append(KEY_MONEY_TARGET_NAME).append(" as ").
                    append(key_MONEY_ACCUMULATION_NAME).append(" from ").append(TABLE_MONEY_ACCUMULATION).
                    append(" join ").append(TABLE_MONEY_TARGET).append(" on ").
                    append(TABLE_MONEY_ACCUMULATION).append(".").append(KEY_MONEY_ACCUMULATION_TARGET_ID).
                    append("=").append(TABLE_MONEY_TARGET).append(".").append(KEY_MONEY_TARGET_ID);
            if (targetId != null)
                sql.append(" where ").append(KEY_MONEY_ACCUMULATION_TARGET_ID).append("=").
                        append(targetId);
            sql.append(" order by ").append(TABLE_MONEY_TARGET).append(".").append(KEY_MONEY_TARGET_NAME);

            return getReadableDatabase().rawQuery(sql.toString(), null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyAccumulation(MoneyAccumulation moneyAccumulation, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_TARGET_ID, moneyAccumulation.get_targetId());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_TYPE, moneyAccumulation.get_accumulationType().toString());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_PROGRESSION_COEF, moneyAccumulation.get_progressionCoefficent());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_MONTH_START, moneyAccumulation.get_monthStart());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_YEAR_START, moneyAccumulation.get_yearStart());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_MONTH_END, moneyAccumulation.get_monthEnd());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_YEAR_END, moneyAccumulation.get_yearEnd());
        contentValues.put(DBHelper.KEY_MONEY_ACCUMULATION_START_SUM, moneyAccumulation.get_startSum());

        return PutCustom(contentValues, typeAction);
    }
    //endregion

    //region MoneyPortfolio
    public int GetCountMoneyPortfolio() throws Exception { return GetCount(TABLE_MONEY_PORTFOLIO); }

    public Cursor QueryMoneyPortfolio(Long parentId,
                                      Long id,
                                      MoneyPortfolioSettings moneyPortfolioSettings) throws Exception
    {
        try
        {
            String selection = "select " + TABLE_MONEY_PORTFOLIO + ".*, portfolio1." +
                    key_MONEY_PORTFOLIO_LOTCOUNT +
                    " from " + TABLE_MONEY_PORTFOLIO +
                    " left join (select " + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID +
                    ", count (*) as " + key_MONEY_PORTFOLIO_LOTCOUNT +
                    " from " + TABLE_MONEY_LOT_ASSET +
                    " group by " + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + ") portfolio1 " +
                    "on portfolio1." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + "=" + TABLE_MONEY_PORTFOLIO +
                    "."+ KEY_MONEY_PORTFOLIO_ID;

            boolean existSelArgs = false;
            if (parentId != null)
            {
                selection += " WHERE " +  KEY_MONEY_PORTFOLIO_PARENT_ID + "=" + parentId;
                existSelArgs = true;
            }
            if (id != null)
            {
                selection += existSelArgs ? " AND " : " WHERE ";
                selection += KEY_MONEY_PORTFOLIO_ID + "=" + id;
            }

            String[] params = new String[]{};
            return getReadableDatabase().rawQuery(selection, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public int QueryMoneyPortfolioMaxPosition(Long parentId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_PORTFOLIO_POSITION + ") as _max from " +
                    TABLE_MONEY_PORTFOLIO + " where " + KEY_MONEY_PORTFOLIO_PARENT_ID + " = ?";
            cursor = database.rawQuery(sqlQuery, new String[]{ String.valueOf(parentId)});
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    private ContentValues PutMoneyPortfolio(MoneyPortfolio moneyPortfolio, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TIME_PRIORITY_NAME, moneyPortfolio.get_name());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_COMMENT, moneyPortfolio.get_comment());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_PARENT_ID, moneyPortfolio.get_parentId());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_POSITION, moneyPortfolio.get_position());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_STATUS, moneyPortfolio.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_PORTFOLIO_WALLET, moneyPortfolio.get_walletId());
        contentValues.put(DBHelper.KEY_MONEY_PORTFOLIO_SUBCATEGORY, moneyPortfolio.get_subCategoryId());

        return PutCustom(contentValues, typeAction);
    }

    @SuppressLint("Range")
    public Long AddMoneyPortfolio(MoneyPortfolio moneyPortfolio,
                                  Context context) throws Exception
    {
        try
        {
            GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

            Long typeAsset4WalletId = globalVar.get_typeAsset4WalletId();
            Cursor cursor = QueryOneMoneyTypeAsset(typeAsset4WalletId);
            if (!cursor.moveToFirst())
                typeAsset4WalletId = 0L;
            cursor.close();

            long assetId = 0L;
            String walletName = Query1MoneyCategoryName(moneyPortfolio.get_walletId());
            int moneyTypeAssetMaxPosition = QueryMoneyTypeAssetMaxPosition(0L);
            int moneyAssetMaxPosition = 1;
            if (typeAsset4WalletId != null && typeAsset4WalletId != 0L)
                moneyAssetMaxPosition = QueryMoneyAssetMaxPosition(typeAsset4WalletId) + 1;

            if (moneyPortfolio.get_walletId() != null && moneyPortfolio.get_walletId() != 0L)
            {
                    cursor = QueryOneMoneyAsset4Wallet(moneyPortfolio.get_walletId());
                    if (cursor.moveToFirst())
                        assetId = cursor.getLong(cursor.getColumnIndex(KEY_MONEY_PORTFOLIO_ID));
                    cursor.close();
            }
            int moneyLotAssetMaxPosition = QueryMoneyLotAssetMaxPosition(typeAsset4WalletId) + 1;

            long moneyLotAssetId = 0L;
            if (moneyPortfolio.get_walletId() != null && moneyPortfolio.get_walletId() != 0L)
            {
                cursor = QueryMoneyLotAsset4Asset(null, assetId);
                if (cursor.moveToFirst())
                    moneyLotAssetId = cursor.getLong(cursor.getColumnIndex(KEY_MONEY_PORTFOLIO_ID));
                cursor.close();
            }

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            long id;

            try
            {
                id = sqLiteDatabase.insert(TABLE_MONEY_PORTFOLIO, null,
                        PutMoneyPortfolio(moneyPortfolio, EnumTypeAction.Insert));
                boolean b = id > 0;

                if (moneyPortfolio.get_walletId() > 0L)
                {
                    if (b && typeAsset4WalletId == 0L) {
                        MoneyTypeAsset moneyTypeAsset = new MoneyTypeAsset(
                                0L,
                                context.getString(R.string.money_category_wallet),
                                "",
                                0L,
                                moneyTypeAssetMaxPosition,
                                EnumStatus.Started,
                                0);
                        typeAsset4WalletId = sqLiteDatabase.insert(TABLE_MONEY_TYPEASSET, null,
                                PutMoneyTypeAsset(moneyTypeAsset, EnumTypeAction.Insert));
                        b = typeAsset4WalletId > 0;
                    }

                    MoneyAsset moneyAsset = null;
                    if (b && assetId == 0L)
                    {
                        moneyAsset = new MoneyAsset(
                                0L,
                                moneyPortfolio.get_walletId(),
                                0L,
                                walletName,
                                "",
                                moneyAssetMaxPosition,
                                EnumStatus.Started,
                                typeAsset4WalletId,
                                context.getString(R.string.money_category_wallet),
                                EnumKindAsset.Conservative,
                                "",
                                EnumCurrency.RUB,
                                false,
                                1);
                        assetId = sqLiteDatabase.insert(TABLE_MONEY_ASSET, null,
                                PutMoneyAsset(moneyAsset, EnumTypeAction.Insert));
                        b = assetId > 0;
                        moneyAsset.set_id(assetId);
                    }

                    if (b && moneyLotAssetId == 0)
                    {
                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(1900, 0, 1);
                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.set(2900, 11, 31);

                        MoneyLotAsset moneyLotAsset = new MoneyLotAsset(
                                0L,
                                0L,
                                "",
                                0L,
                                "",
                                0L,
                                moneyAsset,
                                walletName,
                                "",
                                moneyLotAssetMaxPosition,
                                1,
                                calendarStart,
                                calendarEnd,
                                EnumStatus.Started,
                                MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet,
                                null,
                                null);
                        moneyLotAssetId = sqLiteDatabase.insert(TABLE_MONEY_LOT_ASSET, null,
                                PutMoneyLotAsset(moneyLotAsset, EnumTypeAction.Insert));
                        b = moneyLotAssetId > 0;
                    }
                }

                if (b)
                {
                    sqLiteDatabase.setTransactionSuccessful();
                    if (moneyPortfolio.get_walletId() > 0L)
                    {
                        globalVar.set_typeAsset4WalletId(typeAsset4WalletId);
                        globalVar.SaveSharedPreferences();
                    }
                    return id;
                }
                else
                    return -1L;
            }
            catch (Exception e)
            {
                    Log.d("MoneyPortfolio Insert", e.getMessage());
                    return -1L;
                }
            finally
            {
                    sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyPortfolio(MoneyPortfolio moneyPortfolio,
                                        Context context) throws Exception
    {
        Long id = moneyPortfolio.get_id();

        try
        {
            GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

            Long typeAsset4WalletId = globalVar.get_typeAsset4WalletId();
            long assetId = 0L;
            String walletName = Query1MoneyCategoryName(moneyPortfolio.get_walletId());
            int moneyTypeAssetMaxPosition = QueryMoneyTypeAssetMaxPosition(0L);
            int moneyAssetMaxPosition = 1;
            if (typeAsset4WalletId != null && typeAsset4WalletId != 0L)
                moneyAssetMaxPosition = QueryMoneyAssetMaxPosition(typeAsset4WalletId) + 1;

            if (moneyPortfolio.get_walletId() != null && moneyPortfolio.get_walletId() != 0L)
            {
                Cursor cursor = QueryOneMoneyAsset4Wallet(moneyPortfolio.get_walletId());
                if (cursor.moveToFirst())
                {
                    int idIndex = cursor.getColumnIndex(KEY_MONEY_PORTFOLIO_ID);
                    if (id != -1)
                        assetId = cursor.getLong(idIndex);
                }
                cursor.close();
            }
            int moneyLotAssetMaxPosition = QueryMoneyLotAssetMaxPosition(typeAsset4WalletId) + 1;

            long lotAssetId = 0L;
            if (moneyPortfolio.get_walletId() > 0)
            {
                Cursor cursor = QueryMoneyLotAsset4Asset(null, assetId);
                if (cursor.moveToFirst())
                {
                    int idIndex = cursor.getColumnIndex(KEY_MONEY_LOT_ASSET_ID);
                    lotAssetId = cursor.getLong(idIndex);
                    cursor.close();
                }
            }

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            boolean b;

            try
            {
                int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_PORTFOLIO,
                        PutMoneyPortfolio(moneyPortfolio, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_PORTFOLIO_ID + "= ?", new String[] {String.valueOf(id)});
                b = updateCount == 1;

                if (moneyPortfolio.get_walletId() > 0L)
                {
                    if (b && typeAsset4WalletId == 0L)
                    {
                        MoneyTypeAsset moneyTypeAsset = new MoneyTypeAsset(
                                0L,
                                context.getString(R.string.money_category_wallet),
                                "",
                                0L,
                                moneyTypeAssetMaxPosition,
                                EnumStatus.Started,
                                0);
                        typeAsset4WalletId = sqLiteDatabase.insert(TABLE_MONEY_TYPEASSET, null,
                                PutMoneyTypeAsset(moneyTypeAsset, EnumTypeAction.Insert));
                        b = typeAsset4WalletId > 0;
                    }

                    MoneyAsset moneyAsset = null;
                    if (b && assetId == 0L)
                    {
                        moneyAsset = new MoneyAsset(
                                0L,
                                moneyPortfolio.get_walletId(),
                                0L,
                                walletName,
                                "",
                                moneyAssetMaxPosition,
                                EnumStatus.Started,
                                typeAsset4WalletId,
                                context.getString(R.string.money_category_wallet),
                                EnumKindAsset.Conservative,
                                "",
                                EnumCurrency.RUB,
                                false,
                                1);
                        assetId = sqLiteDatabase.insert(TABLE_MONEY_ASSET, null,
                                PutMoneyAsset(moneyAsset, EnumTypeAction.Insert));
                        b = assetId > 0;
                        moneyAsset.set_id(assetId);
                    }

                    if (b && lotAssetId > 0)
                    {
                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(1900, 0, 1);
                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.set(2900, 11, 31);

                        MoneyLotAsset moneyLotAsset = new MoneyLotAsset(
                                lotAssetId,
                                0L,
                                "",
                                0L,
                                "",
                                0L,
                                moneyAsset,
                                walletName,
                                "",
                                0,
                                1,
                                calendarStart,
                                calendarEnd,
                                EnumStatus.Started,
                                MoneyLotAsset.EnumTypeMoneyLotAsset.LotCredit,
                                null,
                                null);
                        b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_LOT_ASSET,
                                PutMoneyLotAssetName(moneyLotAsset, EnumTypeAction.Update),
                                DBHelper.KEY_MONEY_LOT_ASSET_ID + "= ?", new String[]{String.valueOf(lotAssetId)}) == 1;
                    }

                    if (b && lotAssetId == 0)
                    {
                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(1900, 0, 1);
                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.set(2900, 11, 31);

                        MoneyLotAsset moneyLotAsset = new MoneyLotAsset(
                                0L,
                                0L,
                                "",
                                0L,
                                "",
                                0L,
                                moneyAsset,
                                walletName,
                                "",
                                moneyLotAssetMaxPosition,
                                1,
                                calendarStart,
                                calendarEnd,
                                EnumStatus.Started,
                                MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet,
                                null,
                                null);
                        long moneyLotAssetId = sqLiteDatabase.insert(TABLE_MONEY_LOT_ASSET, null,
                                PutMoneyLotAsset(moneyLotAsset, EnumTypeAction.Insert));
                        b = moneyLotAssetId > 0;
                    }
                }

                if (b)
                {
                    sqLiteDatabase.setTransactionSuccessful();
                    if (moneyPortfolio.get_walletId() > 0L)
                    {
                        globalVar.set_typeAsset4WalletId(typeAsset4WalletId);
                        globalVar.SaveSharedPreferences();
                    }
                }

                return b;
            }
            catch (Exception e)
            {
                Log.d("MoneyPortfolio Update", e.getMessage());
                return false;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
//        try
//        {
//            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_PORTFOLIO,
//                    PutMoneyPortfolio(moneyPortfolio, EnumTypeAction.Update),
//                    DBHelper.KEY_MONEY_PORTFOLIO_ID + "= ?", new String[] {String.valueOf(id)});
//            return updateCount == 1;
//        }
//        catch (Exception e)
//        {
//            throw new Exception(e.getMessage());
//        }
    }

    public boolean UpdateMoneyPortfolioPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MONEY_PORTFOLIO_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_PORTFOLIO,
                    contentValues, DBHelper.KEY_MONEY_PORTFOLIO_ID + "= ?",
                    new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyPortfolio(Long id) throws Exception
    {
        if (id == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_PORTFOLIO,
                        DBHelper.KEY_MONEY_PORTFOLIO_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_PORTFOLIO, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyTypeAsset
    public int GetCountMoneyTypeAsset() throws Exception { return GetCount(TABLE_MONEY_TYPEASSET); }

    public Cursor QueryMoneyTypeAsset(Long parentId, MoneyTypeAssetSettings moneyTypeAssetSettings) throws Exception
    {
        try
        {
            String sql =
                    "select " + TABLE_MONEY_TYPEASSET + ".*, t1." + key_MONEY_TYPEASSET_COUNT_ASSET +
                            " from " + TABLE_MONEY_TYPEASSET +

                            " left join (select " + KEY_MONEY_ASSET_TYPE + ", count (" +
                            KEY_MONEY_ASSET_ID + ") as " + key_MONEY_TYPEASSET_COUNT_ASSET +
                            " from " + TABLE_MONEY_ASSET +
                            " group by " + KEY_MONEY_ASSET_TYPE + ") t1 " +

                    "on t1." + KEY_MONEY_ASSET_TYPE + "=" + TABLE_MONEY_TYPEASSET + "."+
                    KEY_MONEY_TYPEASSET_ID;

            if (parentId != null)
                sql += " where " + KEY_MONEY_TYPEASSET_PARENT_ID + "=" + parentId;

            sql += " order by " +  TABLE_MONEY_TYPEASSET + "."+ KEY_MONEY_TYPEASSET_POSITION;

            String[] params = new String[]{};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryOneMoneyTypeAsset(Long id) throws Exception
    {
        try
        {
            String sql =
                    "select " + TABLE_MONEY_TYPEASSET + ".*, t1." + key_MONEY_TYPEASSET_COUNT_ASSET +
                            " from " + TABLE_MONEY_TYPEASSET +

                            " left join (select " + KEY_MONEY_ASSET_TYPE + ", count (" +
                            KEY_MONEY_ASSET_ID + ") as " + key_MONEY_TYPEASSET_COUNT_ASSET +
                            " from " + TABLE_MONEY_ASSET +
                            " group by " + KEY_MONEY_ASSET_TYPE + ") t1 " +

                            "on t1." + KEY_MONEY_ASSET_TYPE + "=" + TABLE_MONEY_TYPEASSET + "."+
                            KEY_MONEY_TYPEASSET_ID;

            sql += " where " + KEY_MONEY_TYPEASSET_ID + "=" + id;

            sql += " order by " +  TABLE_MONEY_TYPEASSET + "."+ KEY_MONEY_TYPEASSET_POSITION;

            String[] params = new String[]{};

            return getReadableDatabase().rawQuery(sql, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public int QueryMoneyTypeAssetMaxPosition(Long parentId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_TYPEASSET_POSITION + ") as _max from " +
                    TABLE_MONEY_TYPEASSET + " where " + KEY_MONEY_TYPEASSET_PARENT_ID + " = ?";
            cursor = database.rawQuery(sqlQuery, new String[]{ String.valueOf(parentId)});
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    private ContentValues PutMoneyTypeAsset(MoneyTypeAsset moneyTypeAsset, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_TYPEASSET_NAME, moneyTypeAsset.get_name());
        contentValues.put(DBHelper.KEY_MONEY_TYPEASSET_COMMENT, moneyTypeAsset.get_comment());
        contentValues.put(DBHelper.KEY_MONEY_TYPEASSET_PARENT_ID, moneyTypeAsset.get_parentId());
        contentValues.put(DBHelper.KEY_MONEY_TYPEASSET_POSITION, moneyTypeAsset.get_position());
        contentValues.put(DBHelper.KEY_MONEY_TYPEASSET_STATUS, moneyTypeAsset.get_status().toString());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyTypeAsset(MoneyTypeAsset moneyTypeAsset) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_MONEY_TYPEASSET, null,
                    PutMoneyTypeAsset(moneyTypeAsset, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyTypeAsset(MoneyTypeAsset moneyTypeAsset) throws Exception
    {
        Long id = moneyTypeAsset.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_TYPEASSET,
                    PutMoneyTypeAsset(moneyTypeAsset, EnumTypeAction.Update),
                    DBHelper.KEY_MONEY_TYPEASSET_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyTypeAssetPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MONEY_TYPEASSET_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_TYPEASSET,
                    contentValues, DBHelper.KEY_MONEY_TYPEASSET_ID + "= ?",
                    new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyTypeAsset(Long id) throws Exception
    {
        if (id == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_TYPEASSET,
                        DBHelper.KEY_MONEY_TYPEASSET_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_TYPEASSET, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyAsset
    public int GetCountMoneyAsset() throws Exception { return GetCount(TABLE_MONEY_ASSET); }

    public Cursor QueryMoneyAsset(MoneyAssetSettings moneyAssetSettings) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_ASSET + ".*" +
                    ", t1." + KEY_MONEY_TYPEASSET_NAME + " as " + key_MONEY_ASSET_TYPE_NAME +
                    ", t2." + key_MONEY_ASSET_LOTCOUNT +
                    " from " + TABLE_MONEY_ASSET +
                    " left join " + TABLE_MONEY_TYPEASSET + " t1" +
                    " on " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_TYPE + "=" +
                    "t1." + KEY_MONEY_TYPEASSET_ID +
                    " left join (select " + KEY_MONEY_LOT_ASSET_ASSET_ID +
                    ", count (*) as " + key_MONEY_ASSET_LOTCOUNT +
                    " from " + TABLE_MONEY_LOT_ASSET +
                    " group by " + KEY_MONEY_LOT_ASSET_ASSET_ID + ") t2 " +
                    "on t2." + KEY_MONEY_LOT_ASSET_ASSET_ID + "=" + TABLE_MONEY_ASSET +
                    "."+ KEY_MONEY_ASSET_ID;

            if (moneyAssetSettings.get_moneyTypeAssetId() != null)
                sql += " where " + KEY_MONEY_ASSET_TYPE + "=" + moneyAssetSettings.get_moneyTypeAssetId();

            sql += " order by " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sql, new String[] {});


//            String selection = "select " + TABLE_MONEY_PORTFOLIO + ".*, portfolio1." +
//                    key_MONEY_PORTFOLIO_LOTCOUNT +
//                    " from " + TABLE_MONEY_PORTFOLIO +
//                    " left join (select " + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID +
//                    ", count (*) as " + key_MONEY_PORTFOLIO_LOTCOUNT +
//                    " from " + TABLE_MONEY_LOT_ASSET +
//                    " group by " + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + ") portfolio1 " +
//                    "on portfolio1." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + "=" + TABLE_MONEY_PORTFOLIO +
//                    "."+ KEY_MONEY_PORTFOLIO_ID;
//
//            boolean existSelArgs = false;
//            if (parentId != null)
//            {
//                selection += " WHERE " +  KEY_MONEY_PORTFOLIO_PARENT_ID + "=" + parentId;
//                existSelArgs = true;
//            }
//            if (id != null)
//            {
//                selection += existSelArgs ? " AND " : " WHERE ";
//                selection += KEY_MONEY_PORTFOLIO_ID + "=" + id;
//            }
//
//            String[] params = new String[]{};
//            return getReadableDatabase().rawQuery(selection, params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryOneMoneyAsset(Long id) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_ASSET + ".*" +
                    ", t1." + KEY_MONEY_TYPEASSET_NAME + " as " + key_MONEY_ASSET_TYPE_NAME +
                    ", t2." + key_MONEY_ASSET_LOTCOUNT +
                    " from " + TABLE_MONEY_ASSET +
                    " left join " + TABLE_MONEY_TYPEASSET + " t1" +
                    " on " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_TYPE + "=" +
                    "t1." + KEY_MONEY_TYPEASSET_ID +
                    " left join (select " + KEY_MONEY_LOT_ASSET_ASSET_ID +
                    ", count (*) as " + key_MONEY_ASSET_LOTCOUNT +
                    " from " + TABLE_MONEY_LOT_ASSET +
                    " group by " + KEY_MONEY_LOT_ASSET_ASSET_ID + ") t2 " +
                    "on t2." + KEY_MONEY_LOT_ASSET_ASSET_ID + "=" + TABLE_MONEY_ASSET +
                    "."+ KEY_MONEY_ASSET_ID;

            sql += " where " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_ID + "=" + id;

            sql += " order by " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sql, new String[] {});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryOneMoneyAsset4Wallet(Long walletId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_ASSET + ".*" +
                    ", t1." + KEY_MONEY_TYPEASSET_NAME + " as " + key_MONEY_ASSET_TYPE_NAME +
                    " from " + TABLE_MONEY_ASSET +
                    " left join " + TABLE_MONEY_TYPEASSET + " t1" +
                    " on " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_TYPE + "=" +
                    "t1." + KEY_MONEY_TYPEASSET_ID;

            sql += " where " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_WALLET_ID + "=" + walletId;

            sql += " order by " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sql, new String[] {});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryOneMoneyAsset4Credit(Long creditId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_ASSET + ".*" +
                    ", t1." + KEY_MONEY_TYPEASSET_NAME + " as " + key_MONEY_ASSET_TYPE_NAME +
                    " from " + TABLE_MONEY_ASSET +
                    " left join " + TABLE_MONEY_TYPEASSET + " t1" +
                    " on " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_TYPE + "=" +
                    "t1." + KEY_MONEY_TYPEASSET_ID;

            sql += " where " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_CREDIT_ID + "=" + creditId;

            sql += " order by " + TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sql, new String[] {});
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public int QueryMoneyAssetMaxPosition(Long moneyTypeAssetId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_MONEY_ASSET_POSITION + ") as _max from " +
                    TABLE_MONEY_ASSET + " where " + KEY_MONEY_ASSET_TYPE + " = ?";
            cursor = database.rawQuery(sqlQuery, new String[]{ moneyTypeAssetId.toString()});
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    private ContentValues PutMoneyAsset(MoneyAsset moneyAsset, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_ASSET_NAME, moneyAsset.get_name());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_WALLET_ID, moneyAsset.get_wallet_id());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_CREDIT_ID, moneyAsset.get_credit_id());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_TICKER, moneyAsset.get_ticker());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_COMMENT, moneyAsset.get_comment());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_POSITION, moneyAsset.get_position());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_STATUS, moneyAsset.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_TYPE, moneyAsset.get_moneyTypeAssetId());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_KIND, moneyAsset.get_kindAsset().toString());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_CURRENCY, moneyAsset.get_currency().toString());

        return PutCustom(contentValues, typeAction);
    }

    private ContentValues PutMoneyAssetWOPosition(MoneyAsset moneyAsset)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_ASSET_NAME, moneyAsset.get_name());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_WALLET_ID, moneyAsset.get_wallet_id());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_CREDIT_ID, moneyAsset.get_credit_id());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_TICKER, moneyAsset.get_ticker());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_COMMENT, moneyAsset.get_comment());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_STATUS, moneyAsset.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_TYPE, moneyAsset.get_moneyTypeAssetId());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_KIND, moneyAsset.get_kindAsset().toString());
        contentValues.put(DBHelper.KEY_MONEY_ASSET_CURRENCY, moneyAsset.get_currency().toString());

        return PutCustom(contentValues, EnumTypeAction.Update);
    }

    public Long AddMoneyAsset(MoneyAsset moneyAsset) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_MONEY_ASSET, null,
                    PutMoneyAsset(moneyAsset, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyAsset(MoneyAsset moneyAsset) throws Exception
    {
        Long id = moneyAsset.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_ASSET,
                    PutMoneyAsset(moneyAsset, EnumTypeAction.Update),
                    DBHelper.KEY_MONEY_ASSET_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyAssetPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MONEY_ASSET_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_ASSET,
                    contentValues, DBHelper.KEY_MONEY_ASSET_ID + "= ?",
                    new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyAsset(Long id) throws Exception
    {
        if (id == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_ASSET,
                        DBHelper.KEY_MONEY_ASSET_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_ASSET, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyLotAsset
    public int GetCountMoneyLotAsset() throws Exception { return GetCount(TABLE_MONEY_LOT_ASSET); }

    public Cursor QueryMoneyLotAsset(MoneyLotAssetSettings moneyLotAssetSettings,
                                     Long moneyLotAssetId) throws Exception
    {
        try
        {
            Calendar dateStart = Calendar.getInstance();
            Calendar dateEnd = Calendar.getInstance();
            EnumKindAsset kindAsset = EnumKindAsset.Blank;
            Long typeAssetId = 0L;
            Long groupPortfolioId = 0L;
            Long portfolioId = 0L;

            if (moneyLotAssetSettings != null)
            {
                dateStart = moneyLotAssetSettings.get_customPeriodClass().get_start_dateC();
                dateEnd = moneyLotAssetSettings.get_customPeriodClass().get_end_dateC();
                kindAsset = moneyLotAssetSettings.get_kindAsset();
                typeAssetId = moneyLotAssetSettings.get_typeAsset();
                groupPortfolioId = moneyLotAssetSettings.get_groupPortfolioId();
                portfolioId = moneyLotAssetSettings.get_portfolioId();
            }
            else
            {
                dateStart.set(1900, 1, 1);
                dateEnd.set(2900, 11, 30);
            }
            if (dateStart == null)
            {
                dateStart = Calendar.getInstance();
                dateStart.set(1900, 1, 1);
            }
            if (dateEnd == null)
            {
                dateEnd = Calendar.getInstance();
                dateEnd.set(2900, 11, 30);
            }
            int dayStart = dateStart.get(Calendar.DATE);
            int monthStart = dateStart.get(Calendar.MONTH);
            int yearStart = dateStart.get(Calendar.YEAR);
            int dayEnd = dateEnd.get(Calendar.DATE);
            int monthEnd = dateEnd.get(Calendar.MONTH);
            int yearEnd = dateEnd.get(Calendar.YEAR);

            String sBuilder = "select " + TABLE_MONEY_LOT_ASSET + ".*" +
                    ", t1." + KEY_MONEY_PORTFOLIO_NAME + " as " + key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME +
                    ", t2." + KEY_MONEY_PORTFOLIO_NAME + " as " + key_MONEY_LOT_ASSET_PORTFOLIO_NAME +
                    ", t2." + KEY_MONEY_PORTFOLIO_WALLET + " as " + key_MONEY_LOT_ASSET_PORTFOLIO_WALLET +
                    ", tm." + KEY_MONEY_ASSET_CREDIT_ID +
                    ", tm." + KEY_MONEY_ASSET_KIND +
                    ", tm." + KEY_MONEY_ASSET_TYPE +
                    " from " + TABLE_MONEY_LOT_ASSET +
                    " left join " + TABLE_MONEY_PORTFOLIO + " t1 on " +
                    "t1." + KEY_MONEY_PORTFOLIO_ID + "=" +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID +
                    " left join " + TABLE_MONEY_PORTFOLIO + " t2 on " +
                    "t2." + KEY_MONEY_PORTFOLIO_ID + "=" +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID +
                    " left join " + TABLE_MONEY_ASSET + " tm on " +
                    "tm." + KEY_MONEY_ASSET_ID + "=" +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_ASSET_ID +
                    " where " + "(" + KEY_MONEY_LOT_ASSET_YEAR_START + "<" + yearEnd +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_START + "=" + yearEnd +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_START + "<" + monthEnd + ")" +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_START + "=" + yearEnd +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_START + "=" + monthEnd +
                    " AND " + KEY_MONEY_LOT_ASSET_DATE_START + "<=" + dayEnd + ")" +
                    ")" +
                    " AND " + "(" + KEY_MONEY_LOT_ASSET_YEAR_END + ">" + yearStart +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_END + "=" + yearStart +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_END + ">" + monthStart + ")" +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_END + "=" + yearStart +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_END + "=" + monthStart +
                    " AND " + KEY_MONEY_LOT_ASSET_DATE_END + ">=" + dayStart + ")" +
                    ")";
            if (moneyLotAssetId != null)
                sBuilder += " AND " + TABLE_MONEY_LOT_ASSET + "." +
                        KEY_MONEY_LOT_ASSET_ID + "=" + moneyLotAssetId;
            if (groupPortfolioId != null && groupPortfolioId != 0L)
                sBuilder += " AND " + TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID +
                        "=" + groupPortfolioId;
            if (portfolioId != null && portfolioId != 0L)
                sBuilder += " AND " + TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID +
                        "=" + portfolioId;
            if (typeAssetId != null && typeAssetId != 0L)
                sBuilder += " AND tm." + KEY_MONEY_ASSET_TYPE + "=" + typeAssetId;
            if (kindAsset != null && kindAsset != EnumKindAsset.Blank)
                sBuilder += " AND tm." + KEY_MONEY_ASSET_KIND + "='" + kindAsset + "'";
            sBuilder += " AND tm." + KEY_MONEY_ASSET_WALLET_ID + "=0";
            sBuilder += " order by " + KEY_MONEY_LOT_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sBuilder, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    public Cursor QueryMoneyLotAsset4Wallet(MoneyLotAssetSettings moneyLotAssetSettings,
                                            Long walletId) throws Exception
    {
        try
        {
            Calendar dateStart = Calendar.getInstance();
            Calendar dateEnd = Calendar.getInstance();
            EnumKindAsset kindAsset = EnumKindAsset.Blank;
            Long typeAssetId = 0L;
            Long groupPortfolioId = 0L;
            Long portfolioId = 0L;

            if (moneyLotAssetSettings != null)
            {
                dateStart = moneyLotAssetSettings.get_customPeriodClass().get_start_dateC();
                dateEnd = moneyLotAssetSettings.get_customPeriodClass().get_end_dateC();
                kindAsset = moneyLotAssetSettings.get_kindAsset();
                typeAssetId = moneyLotAssetSettings.get_typeAsset();
                groupPortfolioId = moneyLotAssetSettings.get_groupPortfolioId();
                portfolioId = moneyLotAssetSettings.get_portfolioId();
            }
            else
            {
                dateStart.set(1900, 1, 1);
                dateEnd.set(2900, 11, 30);
            }
            if (dateStart == null)
            {
                dateStart = Calendar.getInstance();
                dateStart.set(1900, 1, 1);
            }
            if (dateEnd == null)
            {
                dateEnd = Calendar.getInstance();
                dateEnd.set(2900, 11, 30);
            }
            int dayStart = dateStart.get(Calendar.DATE);
            int monthStart = dateStart.get(Calendar.MONTH);
            int yearStart = dateStart.get(Calendar.YEAR);
            int dayEnd = dateEnd.get(Calendar.DATE);
            int monthEnd = dateEnd.get(Calendar.MONTH);
            int yearEnd = dateEnd.get(Calendar.YEAR);

            String sBuilder =
                        "select " + TABLE_MONEY_LOT_ASSET + ".*" +
                        ", t1." + KEY_MONEY_PORTFOLIO_NAME + " as " + key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME +
                        ", t2." + KEY_MONEY_PORTFOLIO_NAME + " as " + key_MONEY_LOT_ASSET_PORTFOLIO_NAME +
                        ", t2." + KEY_MONEY_PORTFOLIO_WALLET + " as " + key_MONEY_LOT_ASSET_PORTFOLIO_WALLET +
                        ", tm." + KEY_MONEY_ASSET_WALLET_ID + " as " + key_MONEY_LOT_ASSET_WALLET_ID +
                        " from " + TABLE_MONEY_LOT_ASSET +
                        " left join " + TABLE_MONEY_PORTFOLIO + " t1 on " +
                        "t1." + KEY_MONEY_PORTFOLIO_ID + "=" +
                        TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID +
                        " left join " + TABLE_MONEY_PORTFOLIO + " t2 on " +
                        "t2." + KEY_MONEY_PORTFOLIO_ID + "=" +
                        TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID +
                        " left join " + TABLE_MONEY_ASSET + " tm on " +
                        "tm." + KEY_MONEY_ASSET_ID + "=" +
                        TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_ASSET_ID +
                        " where " + "(" + KEY_MONEY_LOT_ASSET_YEAR_START + "<" + yearEnd +
                        " OR (" + KEY_MONEY_LOT_ASSET_YEAR_START + "=" + yearEnd +
                        " AND " + KEY_MONEY_LOT_ASSET_MONTH_START + "<" + monthEnd + ")" +
                        " OR (" + KEY_MONEY_LOT_ASSET_YEAR_START + "=" + yearEnd +
                        " AND " + KEY_MONEY_LOT_ASSET_MONTH_START + "=" + monthEnd +
                        " AND " + KEY_MONEY_LOT_ASSET_DATE_START + "<=" + dayEnd + ")" +
                        ")" +
                        " AND " + "(" + KEY_MONEY_LOT_ASSET_YEAR_END + ">" + yearStart +
                        " OR (" + KEY_MONEY_LOT_ASSET_YEAR_END + "=" + yearStart +
                        " AND " + KEY_MONEY_LOT_ASSET_MONTH_END + ">" + monthStart + ")" +
                        " OR (" + KEY_MONEY_LOT_ASSET_YEAR_END + "=" + yearStart +
                        " AND " + KEY_MONEY_LOT_ASSET_MONTH_END + "=" + monthStart +
                        " AND " + KEY_MONEY_LOT_ASSET_DATE_END + ">=" + dayStart + ")" +
                        ")";
            if (typeAssetId != null && typeAssetId != 0L)
                sBuilder += " AND tm." + KEY_MONEY_ASSET_TYPE + "=" + typeAssetId;
            if (kindAsset != null && kindAsset != EnumKindAsset.Blank)
                sBuilder += " AND tm." + KEY_MONEY_ASSET_KIND + "='" + kindAsset + "'";
            sBuilder += " AND tm." + KEY_MONEY_ASSET_WALLET_ID + "<>0";

            sBuilder += " AND tm." + KEY_MONEY_ASSET_WALLET_ID + " IN " +
                    "(select " + KEY_MONEY_PORTFOLIO_WALLET + " from " + TABLE_MONEY_PORTFOLIO +
                    " where " + KEY_MONEY_PORTFOLIO_WALLET + "<>0" + " AND " +
                    KEY_MONEY_PORTFOLIO_PARENT_ID + "<>0";
            if (walletId != null && walletId != 0L)
                sBuilder += " AND tm." + KEY_MONEY_ASSET_WALLET_ID + "=" + walletId;
            if (groupPortfolioId != null && groupPortfolioId != 0L)
                sBuilder += " AND " + KEY_MONEY_PORTFOLIO_PARENT_ID + "=" + groupPortfolioId;
            if (portfolioId != null && portfolioId != 0L)
                sBuilder += " AND " + KEY_MONEY_PORTFOLIO_ID + "=" + portfolioId;
            sBuilder += ")";

            sBuilder += " order by " + KEY_MONEY_LOT_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sBuilder, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyLotAsset4Asset(MoneyLotAssetSettings moneyLotAssetSettings,
                                     Long assetId) throws Exception
    {
        try
        {
            Calendar dateStart = Calendar.getInstance();
            Calendar dateEnd = Calendar.getInstance();

            if (moneyLotAssetSettings != null)
            {
                dateStart = moneyLotAssetSettings.get_customPeriodClass().get_start_dateC();
                dateEnd = moneyLotAssetSettings.get_customPeriodClass().get_end_dateC();
            }
            else
            {
                dateStart.set(1900, 1, 1);
                dateEnd.set(2900, 11, 30);
            }
            int dayStart = dateStart.get(Calendar.DATE);
            int monthStart = dateStart.get(Calendar.MONTH);
            int yearStart = dateStart.get(Calendar.YEAR);
            int dayEnd = dateEnd.get(Calendar.DATE);
            int monthEnd = dateEnd.get(Calendar.MONTH);
            int yearEnd = dateEnd.get(Calendar.YEAR);

            String sBuilder = "select " + TABLE_MONEY_LOT_ASSET + ".*" +
                    ", t1." + KEY_MONEY_PORTFOLIO_NAME + " as " + key_MONEY_LOT_ASSET_GROUP_PORTFOLIO_NAME +
                    ", t2." + KEY_MONEY_PORTFOLIO_NAME + " as " + key_MONEY_LOT_ASSET_PORTFOLIO_NAME +
                    " from " + TABLE_MONEY_LOT_ASSET +
                    " left join " + TABLE_MONEY_PORTFOLIO + " t1 on " +
                    "t1." + KEY_MONEY_PORTFOLIO_ID + "=" +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID +
                    " left join " + TABLE_MONEY_PORTFOLIO + " t2 on " +
                    "t2." + KEY_MONEY_PORTFOLIO_ID + "=" +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID +
                    " where " + "(" + KEY_MONEY_LOT_ASSET_YEAR_START + "<" + yearEnd +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_START + "=" + yearEnd +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_START + "<" + monthEnd + ")" +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_START + "=" + yearEnd +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_START + "=" + monthEnd +
                    " AND " + KEY_MONEY_LOT_ASSET_DATE_START + "<=" + dayEnd + ")" +
                    ")" +
                    " AND " + "(" + KEY_MONEY_LOT_ASSET_YEAR_END + ">" + yearStart +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_END + "=" + yearStart +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_END + ">" + monthStart + ")" +
                    " OR (" + KEY_MONEY_LOT_ASSET_YEAR_END + "=" + yearStart +
                    " AND " + KEY_MONEY_LOT_ASSET_MONTH_END + "=" + monthStart +
                    " AND " + KEY_MONEY_LOT_ASSET_DATE_END + ">=" + dayStart + ")" +
                    ")";
            if (assetId != null)
                sBuilder += " AND " + TABLE_MONEY_LOT_ASSET + "." +
                        KEY_MONEY_LOT_ASSET_ASSET_ID + "=" + assetId;
            sBuilder += " order by " + KEY_MONEY_LOT_ASSET_POSITION;

            return getReadableDatabase().rawQuery(sBuilder, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public int QueryMoneyLotAssetMaxPosition(Long moneyTypeAssetId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_POSITION +
                    ") as _max from " +
                    TABLE_MONEY_LOT_ASSET + " join " + TABLE_MONEY_ASSET +
                    " on " + TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_ASSET_ID + "=" +
                    TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_ID +
                    " where " +
                    TABLE_MONEY_ASSET + "." + KEY_MONEY_ASSET_TYPE + " = " + moneyTypeAssetId;
            cursor = database.rawQuery(sqlQuery, new String[]{ });
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    private ContentValues PutMoneyLotAsset(MoneyLotAsset moneyLotAsset, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_GROUP_PORTFOLIO_ID, moneyLotAsset.get_groupPortfolioId());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_PORTFOLIO_ID, moneyLotAsset.get_portfolioId());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_ASSET_ID, moneyLotAsset.get_moneyAsset().get_id());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_POSITION, moneyLotAsset.get_position());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_STATUS, moneyLotAsset.get_status().toString());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_COUNT, moneyLotAsset.get_count());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_NAME, moneyLotAsset.get_name());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_COMMENT, moneyLotAsset.get_comment());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_DATE_START, moneyLotAsset.get_dateStart());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_START, moneyLotAsset.get_monthStart());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_START, moneyLotAsset.get_yearStart());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_DATE_END, moneyLotAsset.get_dateEnd());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_MONTH_END, moneyLotAsset.get_monthEnd());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_YEAR_END, moneyLotAsset.get_yearEnd());

        return PutCustom(contentValues, typeAction);
    }

    private ContentValues PutMoneyLotAssetName(MoneyLotAsset moneyLotAsset, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_NAME, moneyLotAsset.get_name());

        return PutCustom(contentValues, typeAction);
    }

    @SuppressLint("SuspiciousIndentation")
    public boolean UpdateMoneyLotAsset(MoneyLotAsset moneyLotAsset, Context context) throws Exception
    {
        Long id = moneyLotAsset.get_id();

        try
        {
            GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
            Long walletId = 0L;
            Long subCategoryId = 0L;
            Cursor cursor = getReadableDatabase().query(DBHelper.TABLE_MONEY_PORTFOLIO,
                    null,
                    KEY_MONEY_PORTFOLIO_ID + "=" + moneyLotAsset.get_portfolioId(),
                    null,
                    null, null, null);
            if (cursor.moveToFirst())
            {
                int walletIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_WALLET);
                int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_SUBCATEGORY);
                walletId = cursor.getLong(walletIndex);
                subCategoryId = cursor.getLong(subCategoryIndex);
            }
            cursor.close();

            if (moneyLotAsset.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                walletId = moneyLotAsset.get_moneyAsset().get_wallet_id();

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            boolean b;

            try
            {
                int updateCount = sqLiteDatabase.update(DBHelper.TABLE_MONEY_LOT_ASSET,
                        PutMoneyLotAsset(moneyLotAsset, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_LOT_ASSET_ID + "= ?", new String[] {String.valueOf(id)});
                b = updateCount == 1;

                if (b && moneyLotAsset.get_lotAssetOperations() != null)
                {
                    for (MoneyLotAssetOperation item: moneyLotAsset.get_lotAssetOperations())
                        if (!Objects.equals(moneyLotAsset.get_moneyAsset().get_moneyTypeAssetId(), globalVar.get_typeAsset4WalletId()) ||
                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission) ||
                                        item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                            {
                                if (moneyLotAsset.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                                    subCategoryId = item.get_subCategoryId();

                                    if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLPlus) ||
                                        item.get_lotAssetOperation().equals(EnumLotAssetOperation.PNLMinus) ||
                                        item.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditPlus) ||
                                        item.get_lotAssetOperation().equals(EnumLotAssetOperation.CreditMinus))
                                    continue;

                                if (item.get_id() == 0L)
                                {
                                    item.set_id(sqLiteDatabase.insert(
                                            TABLE_MONEY_LOT_ASSET_OPERATION, null,
                                            PutMoneyLotAssetOperation(item, EnumTypeAction.Insert)));
                                    b = b && item.get_id() != 0L;

                                    if (b)
                                    {
                                        if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                                        {
                                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    walletId, -item.get_sum());
                                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    subCategoryId, -item.get_sum());
                                        }
                                        else if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                                        {
                                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    walletId, item.get_sum());
                                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    subCategoryId, item.get_sum());
                                        }
                                    }
                                }
                                else {
                                    updateCount = sqLiteDatabase.update(
                                            TABLE_MONEY_LOT_ASSET_OPERATION,
                                            PutMoneyLotAssetOperation(item, EnumTypeAction.Update),
                                            DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ID + "= ?",
                                            new String[]{String.valueOf(item.get_id())});
                                    b = b && updateCount == 1;

                                    if (b)
                                    {
                                        if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                                        {
                                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    walletId, item.get_sumOld() - item.get_sum());
                                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    subCategoryId, item.get_sumOld() - item.get_sum());
                                        }
                                        else if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                                        {
                                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    walletId, item.get_sum() - item.get_sumOld());
                                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    subCategoryId, item.get_sum() - item.get_sumOld());
                                        }
                                    }
                                }
                            }
                }

                if (b && moneyLotAsset.get_deletedLotAssetOperations() != null)
                {
                    for (MoneyLotAssetOperation item: moneyLotAsset.get_deletedLotAssetOperations())
                        if (item.get_id() != 0L)
                            if (!Objects.equals(moneyLotAsset.get_moneyAsset().get_moneyTypeAssetId(), globalVar.get_typeAsset4WalletId()) ||
                                    item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission) ||
                                    item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                            {
                                if (moneyLotAsset.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                                    subCategoryId = item.get_subCategoryId();

                                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_LOT_ASSET_OPERATION,
                                        DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ID + "=" + item.get_id(), null);
                                b = b && delCount == 1;

                                if (b && item.get_id() != 0L)
                                {
                                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_LOT_ASSET_OPERATION, item.get_id());
                                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);
                                    b = idCDelObject != -1;

                                    if (b)
                                    {
                                        if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                                        {
                                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    walletId, item.get_sumOld());
                                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    subCategoryId, item.get_sumOld());
                                        }
                                        else if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                                item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                                        {
                                            b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    walletId, -item.get_sumOld());
                                            b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                    subCategoryId, -item.get_sumOld());
                                        }
                                    }
                                }
                            }
                }

                if (b)
                {
                    sqLiteDatabase.setTransactionSuccessful();
                    return true;
                }
                else
                    return false;
            }
            catch (Exception e)
            {
                Log.d("MoneyLotAssetOperation Insert", e.getMessage());
                return false;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyLotAssetPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_MONEY_LOT_ASSET,
                    contentValues, DBHelper.KEY_MONEY_LOT_ASSET_ID + "= ?",
                    new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyLotAsset(MoneyLotAsset moneyLotAsset) throws Exception
    {
        long id = moneyLotAsset.get_id();

        if (id == 0L)
            return false;

        try
        {
            Long walletId = 0L;
            Long subCategoryId = 0L;
            Cursor cursor = getReadableDatabase().query(DBHelper.TABLE_MONEY_PORTFOLIO,
                    null,
                    KEY_MONEY_PORTFOLIO_ID + "=" + moneyLotAsset.get_portfolioId(),
                    null,
                    null, null, null);
            if (cursor.moveToFirst())
            {
                int walletIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_WALLET);
                int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_SUBCATEGORY);
                walletId = cursor.getLong(walletIndex);
                subCategoryId = cursor.getLong(subCategoryIndex);
            }
            cursor.close();
            if (moneyLotAsset.get_moneyAsset().get_wallet_id() != 0L)
                walletId = moneyLotAsset.get_moneyAsset().get_wallet_id();

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_LOT_ASSET,
                        DBHelper.KEY_MONEY_LOT_ASSET_ID + "=" + id, null);
                b = delCount == 1;

                if (b)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_LOT_ASSET, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    b = idCDelObject != -1;
                }

                if (b)
                {
                    if (moneyLotAsset.get_lotAssetOperations() != null)
                    {
                        for (MoneyLotAssetOperation item: moneyLotAsset.get_lotAssetOperations())
                            if (item.get_id() != 0L)
                            {
                                delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_LOT_ASSET_OPERATION,
                                        DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ID + "=" + item.get_id(), null);
                                b = b && delCount == 1;

                                if (b)
                                {
                                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_LOT_ASSET_OPERATION, item.get_id());
                                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);
                                    b = idCDelObject != -1;
                                }

                                if (b)
                                {
                                    if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                                    {
                                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                walletId, item.get_sumOld());
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                subCategoryId, item.get_sumOld());
                                    }
                                    else if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                                    {
                                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                walletId, -item.get_sumOld());
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                subCategoryId, -item.get_sumOld());
                                    }
                                }
                            }
                    }

                    if (b && moneyLotAsset.get_deletedLotAssetOperations() != null)
                    {
                        for (MoneyLotAssetOperation item: moneyLotAsset.get_deletedLotAssetOperations())
                            if (item.get_id() != 0L)
                            {
                                delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_LOT_ASSET_OPERATION,
                                        DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ID + "=" + item.get_id(), null);
                                b = b && delCount == 1;

                                if (b)
                                {
                                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_LOT_ASSET_OPERATION, item.get_id());
                                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);
                                    b = idCDelObject != -1;
                                }

                                if (b)
                                {
                                    if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletComission))
                                    {
                                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                walletId, item.get_sumOld());
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                subCategoryId, item.get_sumOld());
                                    }
                                    else if (item.get_lotAssetOperation().equals(EnumLotAssetOperation.Sell) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.Dividend) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.Coupon) ||
                                            item.get_lotAssetOperation().equals(EnumLotAssetOperation.WalletIncome))
                                    {
                                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                walletId, -item.get_sumOld());
                                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                                subCategoryId, -item.get_sumOld());
                                    }
                                }
                            }
                    }
                }

                if (b)
                {
                    sqLiteDatabase.setTransactionSuccessful();
                    return true;
                }
                else
                    return false;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyLotAssetOperation
    public int GetCountMoneyLotAssetOperation() throws Exception
    {
        String where = KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + "='" + EnumLotAssetOperation.Buy + "'";
        return GetCount(TABLE_MONEY_LOT_ASSET_OPERATION, where);
    }

    public Cursor QueryMoneyLotAssetOperation(MoneyLotAssetOperationSettings moneyLotAssetOperationSettings) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_MONEY_LOT_ASSET_OPERATION + ".*" +
                    " from " + TABLE_MONEY_LOT_ASSET_OPERATION;
            if (moneyLotAssetOperationSettings.get_lot_asset_Id() != null)
                    sql += " where " + KEY_MONEY_LOT_ASSET_OPERATION_LOT_ID + "=" +
                            moneyLotAssetOperationSettings.get_lot_asset_Id();
            sql += " order by " +
                    KEY_MONEY_LOT_ASSET_OPERATION_YEAR + " desc, " +
                    KEY_MONEY_LOT_ASSET_OPERATION_MONTH + " desc, " +
                    KEY_MONEY_LOT_ASSET_OPERATION_DATE + " desc";

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryMoneyLotAssetOperation4Wallet(Long walletId) throws Exception
    {
        try
        {
            String sql =
                    "select " + TABLE_MONEY_LOT_ASSET_OPERATION + ".*" +
                    " from " + TABLE_MONEY_LOT_ASSET_OPERATION +
                    " inner join " + TABLE_MONEY_LOT_ASSET + " on " +
                    TABLE_MONEY_LOT_ASSET_OPERATION + "." + KEY_MONEY_LOT_ASSET_OPERATION_LOT_ID + "=" +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_ID +
                    " left join " + TABLE_MONEY_PORTFOLIO + " on " +
                    TABLE_MONEY_LOT_ASSET + "." + KEY_MONEY_LOT_ASSET_PORTFOLIO_ID + "=" +
                    TABLE_MONEY_PORTFOLIO + "." + KEY_MONEY_PORTFOLIO_ID;
            sql +=
                    " where " + TABLE_MONEY_PORTFOLIO + "." + KEY_MONEY_PORTFOLIO_WALLET + "=" + walletId +
                    " AND (" +
                    TABLE_MONEY_LOT_ASSET_OPERATION + "." + KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + "='" +
                    EnumLotAssetOperation.Buy + "'" +
                    " OR " + TABLE_MONEY_LOT_ASSET_OPERATION + "." + KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + "='" +
                    EnumLotAssetOperation.Coupon + "'" +
                    " OR " + TABLE_MONEY_LOT_ASSET_OPERATION + "." + KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + "='" +
                    EnumLotAssetOperation.Dividend + "'" +
                    " OR " + TABLE_MONEY_LOT_ASSET_OPERATION + "." + KEY_MONEY_LOT_ASSET_OPERATION_OPERATION + "='" +
                    EnumLotAssetOperation.Sell + "'" +
                    ")";
            sql +=
                    " order by " +
                    KEY_MONEY_LOT_ASSET_OPERATION_YEAR + " desc, " +
                    KEY_MONEY_LOT_ASSET_OPERATION_MONTH + " desc, " +
                    KEY_MONEY_LOT_ASSET_OPERATION_DATE + " desc";

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }


    private ContentValues PutMoneyLotAssetOperation(MoneyLotAssetOperation moneyLotAssetOperation,
                                                    EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_LOT_ID, moneyLotAssetOperation.get_moneyLotAsset().get_id());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_OPERATION, moneyLotAssetOperation.get_lotAssetOperation().toString());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_CATEGORY, moneyLotAssetOperation.get_categoryId());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_SUBCATEGORY, moneyLotAssetOperation.get_subCategoryId());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_YEAR, moneyLotAssetOperation.get_year());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_MONTH, moneyLotAssetOperation.get_month());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_DATE, moneyLotAssetOperation.get_day());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_COUNT, moneyLotAssetOperation.get_count());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_PRICE, moneyLotAssetOperation.get_price());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_COMISSION, moneyLotAssetOperation.get_commission());
        contentValues.put(DBHelper.KEY_MONEY_LOT_ASSET_OPERATION_ACCUMULATED_PRICE, moneyLotAssetOperation.get_accumulatedPrice());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyLotAssetOperation(MoneyLotAssetOperation moneyLotAssetOperation) throws Exception
    {
        try
        {
            try
            {
                long walletId = 0L;
                long subCategoryId = 0L;
                Cursor cursor = getReadableDatabase().query(DBHelper.TABLE_MONEY_PORTFOLIO,
                        null,
                        KEY_MONEY_PORTFOLIO_ID + "=" + moneyLotAssetOperation.get_moneyLotAsset().get_portfolioId(),
                        null,
                        null, null, null);
                if (cursor.moveToFirst())
                {
                    int walletIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_WALLET);
                    int subCategoryIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_PORTFOLIO_SUBCATEGORY);
                    walletId = cursor.getLong(walletIndex);
                    subCategoryId = cursor.getLong(subCategoryIndex);
                }
                cursor.close();

                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                sqLiteDatabase.beginTransaction();

                long id;
                boolean b = false;

                try
                {
                    if (moneyLotAssetOperation.get_moneyLotAsset().get_id() == 0)
                    {
                        id = sqLiteDatabase.insert(
                                TABLE_MONEY_LOT_ASSET, null,
                                PutMoneyLotAsset(moneyLotAssetOperation.get_moneyLotAsset(), EnumTypeAction.Insert));
                        if (id > 0)
                        {
                            moneyLotAssetOperation.get_moneyLotAsset().set_id(id);
                            b = true;
                        }

//                        if (b)
//                        {
//                            MoneyLotAssetOperation moneyLotAssetOperation1 = moneyLotAssetOperation.Clone();
//                            moneyLotAssetOperation1.set_lotAssetOperation(EnumLotAssetOperation.Create);
//
//                            id = sqLiteDatabase.insert(
//                                    TABLE_MONEY_LOT_ASSET_OPERATION, null,
//                                    PutMoneyLotAssetOperation(moneyLotAssetOperation1, EnumTypeAction.Insert));
//                            if (id > 0)
//                                moneyLotAssetOperation1.set_id(id);
//                            else
//                                b = false;
//                        }
                    }

                    if (b)
                    {
                        id = sqLiteDatabase.insert(
                                TABLE_MONEY_LOT_ASSET_OPERATION, null,
                                PutMoneyLotAssetOperation(moneyLotAssetOperation, EnumTypeAction.Insert));
                        if (id > 0)
                            moneyLotAssetOperation.set_id(id);
                        else
                            b = false;
                    }

                    if (b && moneyLotAssetOperation.get_lotAssetOperation().equals(EnumLotAssetOperation.Buy))
                    {
                        b = UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                walletId, -moneyLotAssetOperation.get_sum());

                        b = b && UpdateMoneyCategorySumLByDeltaLong(sqLiteDatabase,
                                subCategoryId, -moneyLotAssetOperation.get_sum());
                    }

                    if (b)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        return moneyLotAssetOperation.get_id();
                    }
                    else
                        return -1L;
                }
                catch (Exception e)
                {
                    Log.d("MoneyLotAssetOperation Insert", e.getMessage());
                    return -1L;
                }
                finally
                {
                    sqLiteDatabase.endTransaction();
                }

            }
            catch (Exception e)
            {
                throw new Exception(e.getMessage());
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region MoneyBoard
    public int GetCountMoneyBorad() throws Exception { return GetCount(TABLE_MONEY_BOARD); }

    public Cursor QueryMoneyBoard(GlobalClass gVar) throws Exception
    {
        try
        {
            return getReadableDatabase().query(
                    TABLE_MONEY_BOARD,
                    null,
//                    gVar.get_hasUnpaidSubscription() ? null : KEY_MONEY_BOARD_VISIBLE + "=1",
                    null,
                    null,
                    null,
                    null,
                    KEY_MONEY_BOARD_POSITION);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutMoneyBoard(MoneyBoard moneyBoard, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MONEY_BOARD_NAME, moneyBoard.get_enumBoard().toString());
        contentValues.put(DBHelper.KEY_MONEY_BOARD_POSITION, moneyBoard.get_position());
        contentValues.put(DBHelper.KEY_MONEY_BOARD_VISIBLE, moneyBoard.get_visibleI());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddMoneyBoard(MoneyBoard moneyBoard) throws Exception
    {
        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                Long id = sqLiteDatabase.insert(DBHelper.TABLE_MONEY_BOARD, null,
                        PutMoneyBoard(moneyBoard, EnumTypeAction.Insert));
                b = id > 0;

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return id;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateMoneyBoard(MoneyBoard moneyBoard) throws Exception
    {
        Long id = moneyBoard.get_id();

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                b = sqLiteDatabase.update(DBHelper.TABLE_MONEY_BOARD,
                        PutMoneyBoard(moneyBoard, EnumTypeAction.Update),
                        DBHelper.KEY_MONEY_BOARD_ID + "= ?", new String[]{String.valueOf(id)}) == 1;

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return b;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteMoneyBoard(Long id) throws Exception
    {
        if (id == 0)
            return false;

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_MONEY_BOARD,
                        DBHelper.KEY_MONEY_BOARD_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_MONEY_BOARD, id);
                    b = AddCDelObject(sqLiteDatabase, cDelObject) > 0;
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region TasksProject
    public int GetCountTasksProject() throws Exception { return GetCount(TABLE_TIME_PROJECT); }

    public Cursor QueryTasksProject(Long parentId, TaskProjectSettings taskProjectSettings) throws Exception
    {
        try
        {
            ArrayList<EnumStatus> statusList = new ArrayList<>();
            if (taskProjectSettings != null)
                statusList = taskProjectSettings.get_statusList();

            String sql = "select " + TABLE_TIME_PROJECT + ".*" +
                    ", project1." + key_TIME_PROJECT_PROJECT_COUNT +
                    ", task1." + key_TIME_PROJECT_TASK_COUNT_HIGH +
                    ", task2." + key_TIME_PROJECT_TASK_COUNT_MIDDLE +
                    ", task3." + key_TIME_PROJECT_TASK_COUNT_LOW +
                    ", task4." + key_TIME_PROJECT_TASK_COUNT_NONE +
                    " from " + TABLE_TIME_PROJECT;

            sql += " left join (select " + KEY_TIME_PROJECT_PARENT_ID + ", count (*) as "
                    + key_TIME_PROJECT_PROJECT_COUNT + " from " + TABLE_TIME_PROJECT;

            sql +=  " group by " + KEY_TIME_PROJECT_PARENT_ID + ") project1" +
                    " on project1." + KEY_TIME_PROJECT_PARENT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_HIGH + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=1" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task1" +
                    " on task1." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_MIDDLE + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=2" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task2" +
                    " on task2." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_LOW + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=3" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task3" +
                    " on task3." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_NONE + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=4" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task4" +
                    " on task4." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            if (parentId != null)
                sql += " where " + TABLE_TIME_PROJECT + "." +
                        KEY_TIME_PROJECT_PARENT_ID + "=" + parentId;
            else
                sql += " where " + TABLE_TIME_PROJECT + "." +
                        KEY_TIME_PROJECT_PARENT_ID + ">0";

            if (parentId != null && parentId > 0 && statusList.size() > 0)
            {
                sql += " AND (" + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_STATUS + " = '" +
                        statusList.get(0).toString() + "'";
                for (int i = 1; i < statusList.size(); i++)
                    sql += " OR " + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_STATUS + " = '" +
                            statusList.get(i).toString()+ "'";
                sql += ")";
            }
            sql += " order by " + KEY_TIME_PROJECT_POSITION;

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Cursor QueryTaskProjectByTargetId(Long targetId) throws Exception
    {
        try
        {
            String sql = "select " + TABLE_TIME_PROJECT + ".*" +
                    ", project1." + key_TIME_PROJECT_PROJECT_COUNT +
                    ", task1." + key_TIME_PROJECT_TASK_COUNT_HIGH +
                    ", task2." + key_TIME_PROJECT_TASK_COUNT_MIDDLE +
                    ", task3." + key_TIME_PROJECT_TASK_COUNT_LOW +
                    ", task4." + key_TIME_PROJECT_TASK_COUNT_NONE +
                    " from " + TABLE_TIME_PROJECT;

            sql += " left join (select " + KEY_TIME_PROJECT_PARENT_ID + ", count (*) as "
                    + key_TIME_PROJECT_PROJECT_COUNT + " from " + TABLE_TIME_PROJECT +
                    " group by " + KEY_TIME_PROJECT_PARENT_ID + ") project1" +
                    " on project1." + KEY_TIME_PROJECT_PARENT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_HIGH + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=1" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task1" +
                    " on task1." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_MIDDLE + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=2" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task2" +
                    " on task2." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_LOW + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=3" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task3" +
                    " on task3." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " left join (select " + KEY_TIME_TASK_PROJECT_ID + ", count (*) as "
                    + key_TIME_PROJECT_TASK_COUNT_NONE + " from " + TABLE_TIME_TASK +
                    " where " + KEY_TIME_TASK_PRIORITY + "=4" +
                    " AND " + KEY_TIME_TASK_CONFIRMED + "=0" +
                    " group by " + KEY_TIME_TASK_PROJECT_ID + ") task4" +
                    " on task4." + KEY_TIME_TASK_PROJECT_ID + "="
                    + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID;

            sql += " where " + TABLE_TIME_PROJECT + "." +
                        KEY_TIME_PROJECT_TARGET_ID + "=" + targetId +
            " AND " + TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_PARENT_ID + ">0";

            sql += " order by " + KEY_TIME_PROJECT_POSITION;

            return getReadableDatabase().rawQuery(sql, null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public Long QueryTasksProject4Dream(Long dreamId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            cursor = database.query(TABLE_TIME_PROJECT, null,
                    KEY_TIME_PROJECT_TARGET_ID + "=? AND " +
                    KEY_TIME_PROJECT_PARENT_ID + "=0", new String[]{String.valueOf(dreamId)},
                    null, null, null);

            if (cursor.moveToFirst())
            {
                return cursor.getLong(cursor.getColumnIndex(KEY_TIME_PROJECT_ID));
            }
            else
                return 0L;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    @SuppressLint("Range")
    public int QueryTasksProject4Target(Long targetId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            cursor = database.query(TABLE_TIME_PROJECT, null,
                    KEY_TIME_PROJECT_TARGET_ID + "=? AND " +
                            KEY_TIME_PROJECT_PARENT_ID + ">0", new String[]{String.valueOf(targetId)},
                    null, null, null);

            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex(KEY_TIME_PROJECT_ID));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    @SuppressLint("Range")
    public int QueryTasksProjectMaxPosition(Long parentId) throws Exception
    {
        Cursor cursor = null;

        try (SQLiteDatabase database = getReadableDatabase())
        {
            String sqlQuery = "select max(" + KEY_TIME_PROJECT_POSITION + ") as _max from " +
                    TABLE_TIME_PROJECT + " where " + KEY_TIME_PROJECT_PARENT_ID + " = ?";
            cursor = database.rawQuery(sqlQuery, new String[]{ String.valueOf(parentId)});
            if (cursor.moveToFirst())
            {
                return cursor.getInt(cursor.getColumnIndex("_max"));
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    private ContentValues PutTasksProject(TasksProject tasksProject, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TIME_PRIORITY_NAME, tasksProject.get_name());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_COMMENT, tasksProject.get_comment());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_PARENT_ID, tasksProject.get_parentId());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_TARGET_ID, tasksProject.get_target_id());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_POSITION, tasksProject.get_position());
        contentValues.put(DBHelper.KEY_TIME_PROJECT_STATUS, tasksProject.get_status().toString());

        return PutCustom(contentValues, typeAction);
    }

    private ContentValues PutTasksProjectName(String name, String status)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TIME_PROJECT_NAME, name);
        contentValues.put(DBHelper.KEY_TIME_PROJECT_STATUS, status);

        return PutCustom(contentValues, EnumTypeAction.Update);
    }

    public Long AddTasksProject(TasksProject tasksProject) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_TIME_PROJECT, null,
                    PutTasksProject(tasksProject, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateTasksProject(TasksProject tasksProject) throws Exception
    {
        Long id = tasksProject.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_TIME_PROJECT,
                    PutTasksProject(tasksProject, EnumTypeAction.Update),
                    DBHelper.KEY_TIME_PROJECT_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateTasksProjectPosition(Long id, Integer position) throws Exception
    {
        if (id == null) { return false; }

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_TIME_PROJECT_POSITION, position);
            Calendar calendar = Calendar.getInstance();
            contentValues.put(DBHelper.KEY_ALL_DATETIME_UPDATE, calendar.getTime().toString());

            int updateCount = getWritableDatabase().update(DBHelper.TABLE_TIME_PROJECT,
                    contentValues, DBHelper.KEY_TIME_PROJECT_ID + "= ?",
                    new String[] {id.toString()});
            return (updateCount == 1);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteTasksProject(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_TIME_PROJECT,
                        DBHelper.KEY_TIME_PROJECT_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_TIME_PROJECT, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region TasksPriority
    public int GetCountTimePriority() throws Exception { return GetCount(TABLE_TIME_PRIORITY); }

    public Cursor QueryTimePriority() throws Exception
    {
        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_TIME_PRIORITY,
                    null, null,
                    null, null, null,
                    KEY_TIME_PRIORITY_NAME);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutTimePriority(TasksPriority tasksPriority, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TIME_PRIORITY_NAME, tasksPriority.get_name());
        contentValues.put(DBHelper.KEY_TIME_PRIORITY_COMMENT, tasksPriority.get_comment());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddTimePriority(TasksPriority tasksPriority) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_TIME_PRIORITY, null,
                    PutTimePriority(tasksPriority, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateTimePriority(TasksPriority tasksPriority) throws Exception
    {
        Long id = tasksPriority.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_TIME_PRIORITY,
                    PutTimePriority(tasksPriority, EnumTypeAction.Update),
                    DBHelper.KEY_TIME_PRIORITY_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteTimePriority(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_TIME_PRIORITY,
                        DBHelper.KEY_TIME_PRIORITY_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_TIME_PRIORITY, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region TasksTask
    public int GetCountTasksTask() throws Exception { return GetCount(TABLE_TIME_TASK); }

    public Cursor QueryTasksTask(Calendar dateStart, Calendar dateEnd, Long parentId,
                                TasksTaskSettings tasksTaskSettings) throws Exception
    {
        String dayStart = dateStart == null ? "" : String.valueOf(dateStart.get(Calendar.DATE));
        String dayEnd = dateEnd == null ? "" : String.valueOf(dateEnd.get(Calendar.DATE));
        String monthStart = dateStart == null ? "" : String.valueOf(dateStart.get(Calendar.MONTH));
        String monthEnd = dateEnd == null ? "" : String.valueOf(dateEnd.get(Calendar.MONTH));
        String yearStart = dateStart == null ? "" : String.valueOf(dateStart.get(Calendar.YEAR));
        String yearEnd = dateEnd == null ? "" : String.valueOf(dateEnd.get(Calendar.YEAR));
        Long groupOfProjectId = tasksTaskSettings.get_groupOfProjectId();
        Long projectId = tasksTaskSettings.get_projectId();
        Boolean confirmedType = tasksTaskSettings.get_confirmedType();
        Long priority = tasksTaskSettings.get_priority();

        try
        {
            boolean wherePlus = true;
            String[] params = null;

            StringBuilder sql = new StringBuilder("select " + TABLE_TIME_TASK + ".*, " +
                    TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_NAME + " as " + key_TIME_TASK_PROJECT_NAME +
                    " from " + TABLE_TIME_TASK +
                    " left join " + TABLE_TIME_PROJECT + " on " +
                    TABLE_TIME_TASK + "." + KEY_TIME_TASK_PROJECT_ID + "=" +
                    TABLE_TIME_PROJECT + "." + KEY_TIME_PROJECT_ID);

            if (confirmedType != null && confirmedType)
            {
                sql.append(" where ");
                wherePlus = false;
                sql.append(KEY_TIME_TASK_CONFIRMED + "=1 ");
            }
            else if (confirmedType != null)
            {
                sql.append(" where ");
                wherePlus = false;
                sql.append(KEY_TIME_TASK_CONFIRMED + "=0 ");
            }

            if (parentId != null)
            {
                if (wherePlus)
                {
                    sql.append(" where ");
                    wherePlus = false;
                }
                else
                    sql.append(" AND ");
                sql.append(TABLE_TIME_TASK + "." + KEY_TIME_TASK_PARENT_ID + "=").append(parentId);
            }

            if (priority != null && priority != 0)
            {
                if (wherePlus)
                {
                    sql.append(" where ");
                    wherePlus = false;
                }
                else
                    sql.append(" AND ");
                sql.append(KEY_TIME_TASK_PRIORITY + "=").append(priority);
            }

            if (projectId != null)
            {
                if (wherePlus)
                {
                    sql.append(" where ");
                    wherePlus = false;
                }
                else
                    sql.append(" AND ");
                sql.append(KEY_TIME_TASK_PROJECT_ID + "=").append(projectId);
            }
            else if (groupOfProjectId != null)
            {
                Cursor cursor = QueryTasksProject(groupOfProjectId,
                        new TaskProjectSettings(EnumTargetStatus4Settings.All, null));
                if (cursor.getCount() > 0)
                {
                    if (wherePlus)
                    {
                        sql.append(" where ");
                        wherePlus = false;
                    }
                    else
                        sql.append(" AND ");

                    if (cursor.moveToFirst())
                    {
                        int cnt = 0;
                        sql.append("(");
                        int idIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_PROJECT_ID);

                        do
                        {
                            if (cnt == 0)
                            {
                                cnt++;
                                sql.append(KEY_TIME_TASK_PROJECT_ID + "=").append(cursor.getInt(idIndex));
                            }
                            else
                                sql.append(" OR " + KEY_TIME_TASK_PROJECT_ID + "=").append(cursor.getInt(idIndex));
                        }
                        while (cursor.moveToNext());
                    }

                    sql.append(")");
                    cursor.close();
                }
            }

            if (dateStart != null && dateEnd != null)
            {
                if (wherePlus)
                    sql.append(" where ");
                else
                    sql.append(" AND ");

                sql.append("(" + KEY_TIME_TASK_YEAR_INT_PLAN + ">? OR (" + KEY_TIME_TASK_YEAR_INT_PLAN +
                        "=? AND " + KEY_TIME_TASK_MONTH_INT_PLAN + ">?) OR (" + KEY_TIME_TASK_YEAR_INT_PLAN +
                        "=? AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "=? AND " + KEY_TIME_TASK_DATE_INT_PLAN + ">=?))" +
                        " AND (" + KEY_TIME_TASK_YEAR_INT_PLAN + "<? OR (" + KEY_TIME_TASK_YEAR_INT_PLAN +
                        "=? AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "<?) OR (" + KEY_TIME_TASK_YEAR_INT_PLAN +
                        "=? AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "=? AND " + KEY_TIME_TASK_DATE_INT_PLAN + "<=?)) ");

                params = new String[]{yearStart, yearStart, monthStart, yearStart, monthStart, dayStart,
                        yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd};
            }
            else if (dateEnd != null)
            {
                if (wherePlus)
                    sql.append(" where ");
                else
                    sql.append(" AND ");

                sql.append("(" + KEY_TIME_TASK_YEAR_INT_PLAN + ">0 AND "
                        + KEY_TIME_TASK_MONTH_INT_PLAN + ">0 AND "
                        + KEY_TIME_TASK_DATE_INT_PLAN + ">0)" +
                        " AND (" + KEY_TIME_TASK_YEAR_INT_PLAN + "<? OR (" + KEY_TIME_TASK_YEAR_INT_PLAN +
                        "=? AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "<?) OR (" + KEY_TIME_TASK_YEAR_INT_PLAN +
                        "=? AND " + KEY_TIME_TASK_MONTH_INT_PLAN + "=? AND " + KEY_TIME_TASK_DATE_INT_PLAN + "<=?)) ");

                params = new String[]{yearEnd, yearEnd, monthEnd, yearEnd, monthEnd, dayEnd};
            }

            String orderConfirmed = "";
            if (parentId != null && parentId != 0)
                orderConfirmed = KEY_TIME_TASK_CONFIRMED + ", ";

            if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Date_Priority_Name))
                sql.append(" order by ").append(orderConfirmed).append(KEY_TIME_TASK_YEAR_INT_PLAN).
                        append(", ").append(KEY_TIME_TASK_MONTH_INT_PLAN).append(", ").
                        append(KEY_TIME_TASK_DATE_INT_PLAN).append(", ").
                        append(KEY_TIME_TASK_HOUR_INT_PLAN).append(", ").
                        append(KEY_TIME_TASK_MINUTE_INT_PLAN).append(", ").
                        append(KEY_TIME_TASK_PRIORITY).append(", ").append(KEY_TIME_TASK_NAME);
            else if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Priority_Date_Name))
                sql.append(" order by ").append(orderConfirmed).append(KEY_TIME_TASK_PRIORITY).
                        append(", ").append(KEY_TIME_TASK_YEAR_INT_PLAN).
                        append(", ").append(KEY_TIME_TASK_MONTH_INT_PLAN).
                        append(", ").append(KEY_TIME_TASK_DATE_INT_PLAN).
                        append(", ").append(KEY_TIME_TASK_HOUR_INT_PLAN).
                        append(", ").append(KEY_TIME_TASK_MINUTE_INT_PLAN).
                        append(", ").append(KEY_TIME_TASK_NAME);
            else if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Name))
                sql.append(" order by ").append(orderConfirmed).append(KEY_TIME_TASK_NAME);

            return getReadableDatabase().rawQuery(sql.toString(), params);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean QueryTasksTaskNextExist(Long occuredId) throws Exception
    {

        try
        {
            Cursor cursor = getReadableDatabase().query(TABLE_TIME_TASK, null,
                    KEY_TIME_TASK_OCCURED_ID + "=?", new String[]{ String.valueOf(occuredId) },
                    null, null, null);

            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutTasksTask(TasksTask tasksTask, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TIME_TASK_TYPE, tasksTask.get_type().toString());
        contentValues.put(DBHelper.KEY_TIME_TASK_PROJECT_ID, tasksTask.get_project_id());
        contentValues.put(DBHelper.KEY_TIME_TASK_PARENT_ID, tasksTask.get_parentId());
        contentValues.put(DBHelper.KEY_TIME_TASK_OCCURED_ID, tasksTask.get_occured_id());
        contentValues.put(DBHelper.KEY_TIME_TASK_NAME, tasksTask.get_name());
        contentValues.put(DBHelper.KEY_TIME_TASK_COMMENT, tasksTask.get_comment());
        contentValues.put(DBHelper.KEY_TIME_TASK_PRIORITY, tasksTask.get_priority());
        contentValues.put(DBHelper.KEY_TIME_TASK_DATE_INT_PLAN, tasksTask.get_day_plan());
        contentValues.put(DBHelper.KEY_TIME_TASK_MONTH_INT_PLAN, tasksTask.get_month_plan());
        contentValues.put(DBHelper.KEY_TIME_TASK_YEAR_INT_PLAN, tasksTask.get_year_plan());
        contentValues.put(DBHelper.KEY_TIME_TASK_HOUR_INT_PLAN, tasksTask.get_hour_plan());
        contentValues.put(DBHelper.KEY_TIME_TASK_MINUTE_INT_PLAN, tasksTask.get_minute_plan());
        contentValues.put(DBHelper.KEY_TIME_TASK_DATE_INT_FACT, tasksTask.get_day_fact());
        contentValues.put(DBHelper.KEY_TIME_TASK_MONTH_INT_FACT, tasksTask.get_month_fact());
        contentValues.put(DBHelper.KEY_TIME_TASK_YEAR_INT_FACT, tasksTask.get_year_fact());
        contentValues.put(DBHelper.KEY_TIME_TASK_HOUR_INT_FACT, tasksTask.get_hour_fact());
        contentValues.put(DBHelper.KEY_TIME_TASK_MINUTE_INT_FACT, tasksTask.get_minute_fact());
        contentValues.put(DBHelper.KEY_TIME_TASK_REPEAT, tasksTask.get_repeatI());
        contentValues.put(DBHelper.KEY_TIME_TASK_REPEAT_TYPE, tasksTask.get_repeatType().toString());
        contentValues.put(DBHelper.KEY_TIME_TASK_REPEAT_PER, tasksTask.get_repeatPer());
        contentValues.put(DBHelper.KEY_TIME_TASK_REPEAT_SUBTASKS, tasksTask.get_repeatSubTasks().toString());
        contentValues.put(DBHelper.KEY_TIME_TASK_CONFIRMED, tasksTask.get_confirmedI());
        contentValues.put(DBHelper.KEY_TIME_TASK_STATUS,
                tasksTask.get_status() == null ? "" : tasksTask.get_status().toString());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddTasksTask(TasksTaskIndependent tasksTaskIndependent) throws Exception
    {
        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            try
            {
                Long id = sqLiteDatabase.insert(DBHelper.TABLE_TIME_TASK, null,
                        PutTasksTask(tasksTaskIndependent, EnumTypeAction.Insert));
                boolean b = id > 0;

                if (b && tasksTaskIndependent.get_childs() != null)
                    for (TasksTask tasksTaskChild: tasksTaskIndependent.get_childs())
                    {
                        tasksTaskChild.set_parent_id(id);
                        tasksTaskChild.set_id(sqLiteDatabase.insert(DBHelper.TABLE_TIME_TASK, null,
                                PutTasksTask(tasksTaskChild, EnumTypeAction.Insert)));
                        b = b && tasksTaskChild.get_id() > 0;
                    }

                if (b && tasksTaskIndependent.get_nextTask() != null)
                {
                    TasksTask tasksTaskNew = tasksTaskIndependent.get_nextTask();
                    tasksTaskNew.set_occured_id(id);
                    tasksTaskNew.set_id(sqLiteDatabase.insert(TABLE_TIME_TASK, null,
                            PutTasksTask(tasksTaskNew, EnumTypeAction.Insert)));
                    b = tasksTaskNew.get_id() > 0;

                    if (b && tasksTaskIndependent.get_nextTask() != null)
                        for (TasksTask tasksTaskChildNext: tasksTaskIndependent.get_nextTask().get_childs())
                        {
                            tasksTaskChildNext.set_parent_id(tasksTaskNew.get_id());
                            tasksTaskChildNext.set_id(sqLiteDatabase.insert(TABLE_TIME_TASK, null,
                                    PutTasksTask(tasksTaskChildNext, EnumTypeAction.Insert)));
                            b = tasksTaskChildNext.get_id() > 0;
                        }
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return id;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdateTasksTask(TasksTaskIndependent tasksTaskIndependent) throws Exception
    {
        Long id = tasksTaskIndependent.get_id();
        boolean existNextTask = QueryTasksTaskNextExist(id);
        if (existNextTask)
            tasksTaskIndependent.clear_nextTask();

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            try
            {
                boolean b = sqLiteDatabase.update(DBHelper.TABLE_TIME_TASK,
                        PutTasksTask(tasksTaskIndependent, EnumTypeAction.Update),
                        DBHelper.KEY_TIME_TASK_ID + "= ?", new String[]{String.valueOf(id)}) == 1;

                if (b && tasksTaskIndependent.get_childs() != null)
                    for (TasksTask tasksTaskChild: tasksTaskIndependent.get_childs())
                        if (tasksTaskChild.get_id() != 0)
                            b = b && sqLiteDatabase.update(DBHelper.TABLE_TIME_TASK,
                                    PutTasksTask(tasksTaskChild, EnumTypeAction.Update),
                                    DBHelper.KEY_TIME_TASK_ID + "= ?",
                                    new String[]{String.valueOf(tasksTaskChild.get_id())}) == 1;

                if (b && tasksTaskIndependent.get_childs() != null)
                    for (TasksTask tasksTaskChild: tasksTaskIndependent.get_childs())
                        if (tasksTaskChild.get_id() == 0)
                        {
                            tasksTaskChild.set_parent_id(id);
                            tasksTaskChild.set_id(sqLiteDatabase.insert(DBHelper.TABLE_TIME_TASK, null,
                                    PutTasksTask(tasksTaskChild, EnumTypeAction.Insert)));
                            b = b && tasksTaskChild.get_id() > 0;
                        }

                if (b && tasksTaskIndependent.get_deletedChilds() != null)
                    for (TasksTask tasksTaskChild: tasksTaskIndependent.get_deletedChilds())
                        b = b && sqLiteDatabase.delete(DBHelper.TABLE_TIME_TASK,
                                DBHelper.KEY_TIME_TASK_ID + "=" +
                                        tasksTaskChild.get_id(), null) == 1;

                if (b && !existNextTask && tasksTaskIndependent.get_nextTask() != null)
                {
                    TasksTask tasksTaskNew = tasksTaskIndependent.get_nextTask();
                    tasksTaskNew.set_occured_id(id);
                    tasksTaskNew.set_id(sqLiteDatabase.insert(TABLE_TIME_TASK, null,
                            PutTasksTask(tasksTaskNew, EnumTypeAction.Insert)));
                    b = tasksTaskNew.get_id() > 0;

                    if (b && tasksTaskIndependent.get_nextTask() != null)
                        for (TasksTask tasksTaskChildNext: tasksTaskIndependent.get_nextTask().get_childs())
                        {
                            tasksTaskChildNext.set_parent_id(tasksTaskNew.get_id());
                            tasksTaskChildNext.set_id(sqLiteDatabase.insert(TABLE_TIME_TASK, null,
                                    PutTasksTask(tasksTaskChildNext, EnumTypeAction.Insert)));
                            b = tasksTaskChildNext.get_id() > 0;
                        }
                }

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();

                return b;
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeleteTasksTask(TasksTaskIndependent tasksTaskIndependent) throws Exception
    {
        Long id = Long.valueOf(tasksTaskIndependent.get_id());
        if (id == 0)
            return false;


        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b;

            try
            {
                b = sqLiteDatabase.delete(DBHelper.TABLE_TIME_TASK,
                        DBHelper.KEY_TIME_TASK_ID + "=" + id, null) == 1;

                if (b)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_TIME_TASK, id);
                    b = AddCDelObject(sqLiteDatabase, cDelObject) > 0;
                }

                if (b && tasksTaskIndependent.get_childs() != null)
                    for (TasksTask tasksTaskChild: tasksTaskIndependent.get_childs())
                        b = b && sqLiteDatabase.delete(DBHelper.TABLE_TIME_TASK,
                                DBHelper.KEY_TIME_TASK_ID + "=" +
                                        tasksTaskChild.get_id(), null) == 1;

                if (b && tasksTaskIndependent.get_deletedChilds() != null)
                    for (TasksTask tasksTaskChild: tasksTaskIndependent.get_deletedChilds())
                        b = b && sqLiteDatabase.delete(DBHelper.TABLE_TIME_TASK,
                                DBHelper.KEY_TIME_TASK_ID + "=" +
                                        tasksTaskChild.get_id(), null) == 1;

                if (b)
                    sqLiteDatabase.setTransactionSuccessful();
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region PersonAssistant
    public int GetCountPersonAssistant() throws Exception { return GetCount(TABLE_PERSON_ASSISTANT); }

    public Cursor QueryPersonAssistant() throws Exception
    {
        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_PERSON_ASSISTANT,
                    null, null,
                    null, null, null,
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutPersonAssistant(PersonAssistant personAssistant, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_PERSON_ASSISTANT_NAME, personAssistant.get_name());
        contentValues.put(DBHelper.KEY_PERSON_ASSISTANT_SURNAME, personAssistant.get_surname());
        contentValues.put(DBHelper.KEY_PERSON_ASSISTANT_MIDDLENAME, personAssistant.get_middleName());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddPersonAssistant(PersonAssistant personAssistant) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_PERSON_ASSISTANT, null,
                    PutPersonAssistant(personAssistant, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdatePersonAssistant(PersonAssistant personAssistant) throws Exception
    {
        Long id = personAssistant.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_PERSON_ASSISTANT,
                    PutPersonAssistant(personAssistant, EnumTypeAction.Update),
                    DBHelper.KEY_PERSON_ASSISTANT_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeletePersonAssistant(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_PERSON_ASSISTANT,
                        DBHelper.KEY_PERSON_ASSISTANT_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_PERSON_ASSISTANT, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region PersonComm
    public int GetCountPersonComm() throws Exception { return GetCount(TABLE_PERSON_COMM); }

    public Cursor QueryPersonComm() throws Exception
    {
        try
        {
            return getReadableDatabase().query(DBHelper.TABLE_PERSON_COMM,
                    null, null,
                    null, null, null,
                    null);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    private ContentValues PutPersonComm(PersonComm personComm, EnumTypeAction typeAction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_PERSON_COMM_ASSIST_ID, personComm.get_assistId());
        contentValues.put(DBHelper.KEY_PERSON_COMM_NAME, personComm.get_name());
        contentValues.put(DBHelper.KEY_PERSON_COMM_COMM, personComm.get_comm());

        return PutCustom(contentValues, typeAction);
    }

    public Long AddPersonComm(PersonComm personComm) throws Exception
    {
        try
        {
            return getWritableDatabase().insert(DBHelper.TABLE_PERSON_COMM, null,
                    PutPersonComm(personComm, EnumTypeAction.Insert));
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean UpdatePersonComm(PersonComm personComm) throws Exception
    {
        Long id = personComm.get_id();

        try
        {
            int updateCount = getWritableDatabase().update(DBHelper.TABLE_PERSON_COMM,
                    PutPersonComm(personComm, EnumTypeAction.Update),
                    DBHelper.KEY_PERSON_COMM_ID + "= ?", new String[] {String.valueOf(id)});
            return updateCount == 1;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public boolean DeletePersonComm(Long id) throws Exception
    {
        if (id == 0)
        {
            return false;
        }

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            boolean b = false;

            try
            {
                int delCount = sqLiteDatabase.delete(DBHelper.TABLE_PERSON_COMM,
                        DBHelper.KEY_PERSON_COMM_ID + "=" + id, null);

                if (delCount == 1)
                {
                    CDelObject cDelObject = new CDelObject(0L, TABLE_PERSON_COMM, id);
                    int idCDelObject = AddCDelObject(sqLiteDatabase, cDelObject);

                    if (idCDelObject != -1)
                    {
                        sqLiteDatabase.setTransactionSuccessful();
                        b = true;
                    }
                }
            }
            finally
            {
                sqLiteDatabase.endTransaction();
            }

            return b;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
    //endregion
}
