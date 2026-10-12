package com.MH83.LifeAsProject.custom;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.data.Custom.UUIDDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.ESubscribe;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumDatabase;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeResource;
import com.MH83.LifeAsProject.model.Custom.CCabinet;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSettings;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyDreamSettings;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyIncreaseSettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperationSettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Custom.PlanValue;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolioSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTargetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAssetSettings;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;
import com.MH83.LifeAsProject.model.Settings.NotifProjectSettings;
import com.MH83.LifeAsProject.model.Settings.NotifSettings;
import com.MH83.LifeAsProject.model.Settings.NotifTasksSettings;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.UUID;

public class GlobalClass extends Application
{
    //region TestParams
    //для теста покупок и неоплаченного использования true
    public static boolean TestPaid() { return false; }
    //для меня true
    public static boolean FreeProgram() { return false; }
    //для режима разработки true
    public static boolean TestProgram() { return false; }
    //для режима разработки уведомлений true
    public static boolean TestNotification() { return false; }
    //для меня true
    public static boolean SendTestTrack() { return false; }
    //endregion

    //region Subscription
    private static final ESubscribe _subscribe = ESubscribe.Rustore;
    public static boolean SubscrGoogle() { return _subscribe == ESubscribe.Google; }
    public static boolean SubscrRustore() { return _subscribe == ESubscribe.Rustore; }
    public static boolean SubscrHuawei() { return _subscribe == ESubscribe.Huawei; }

    final private UUID _unpaid2WeekKey = UUID.fromString("a0b1450c-7e60-424b-8581-d064a8dd38d4");
    //действует для выкладки
    private UUID _unpaidKey = UUID.fromString("a0b1450c-7e60-424b-8581-d064a8dd38d4");
//    действует для теста
//    private UUID _unpaidKey = UUID.fromString("1e88cf58-06e7-4226-a7db-fa0f446d2e08");
    //помним о считывании из Settings
    public void set_unpaidKey(UUID key) { _unpaidKey = key; }

    private String _purchaseId;
    private String _purchaseIdSaved;
    public void set_purchaseId(String purchaseId) { _purchaseId = purchaseId; }

    private String _productIdSaved;
    private String _productId;
    public void set_productId(String productId) { _productId = productId; }

    private Calendar _dateUpdateSubscription;

    private Calendar _startDate;
    private Calendar _startDateSaved;
    public void set_startDate(Calendar startDate) { this._startDate = startDate; }
    public Calendar get_startDate()
    {
        if (_startDate != null)
            return _startDate;

        return _startDateSaved;
    }

    private Calendar _endDate;
    private Calendar _endDateSaved;
    public void set_endDate(Calendar endDate) { this._endDate = endDate; }
    public Calendar get_endDate()
    {
        if (_endDate != null)
            return _endDate;

        return _endDateSaved;
    }

    public void ClearSubscription4Test()
    {
        if (FreeProgram())
            return;
        
        _productId = null;
        _purchaseId = null;
        _startDate = null;
        _endDate = null;
        _dateUpdateSubscription = Calendar.getInstance();
        _dateUpdateSubscription.add(Calendar.MONTH, -1);
        SaveSharedPreferencesSubscription4Test();
    }

    public boolean noneSubscription()
    {
        return !get_hasPaymentOrTestSubscription() &&
                !get_hasUnpaidSubscription();
    }

    public boolean get_hasPaymentOrTestSubscription()
    {
        return get_hasTestSubscription() || get_hasPaymentSubscription();
    }

    public boolean get_hasTestSubscription()
    {
        if (get_hasPaymentSubscription())
            return false;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -14);

        return _firstOpenDate.getTime().getTime() > calendar.getTime().getTime() &&
                _unpaidKey.equals(_unpaid2WeekKey);
    }

    public boolean get_hasUnpaidSubscription()
    {
        if (get_hasPaymentSubscription())
            return false;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        UUID uuid = UUIDDataSource.GetUUIDs().get(calendar.getTime().getTime());
        return _unpaidKey.equals(uuid);
    }

    public boolean get_showToBuyButtons()
    {
        return !get_hasPaymentSubscription();
    }

    public boolean get_hasPaymentSubscription()
    {
        if (FreeProgram())
            return true;

        boolean b = _productId != null && !_productId.equals("") &&
                _purchaseId != null & !_purchaseId.equals("") &&
                _startDate != null && _endDate != null &&
                _endDate.getTime().getTime() > Calendar.getInstance().getTime().getTime();

        if (b) return true;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        return _productIdSaved != null && !_productIdSaved.equals("") &&
                _purchaseIdSaved != null & !_purchaseIdSaved.equals("") &&
                _startDateSaved != null && _endDateSaved != null &&
                _endDateSaved.getTime().getTime() > Calendar.getInstance().getTime().getTime() &&
                _dateUpdateSubscription != null &&
                _dateUpdateSubscription.getTime().getTime() > calendar.getTime().getTime();
    }
    //endregion

    //region Tracking params
    final String _sdk_key_tracker = "80349160569241031296";
    public String get_sdk_key_tracker() { return _sdk_key_tracker; }
    //endregion

    private CCabinet _cCabinet;
    public void set_cCabinet(CCabinet cCabinet) { _cCabinet = cCabinet; }
    public CCabinet get_cCabinet() { return _cCabinet; }

    private final Stack<Integer> _navigationIds = new Stack<>();
    public void AddToNavigationIds(Integer id)
    {
        if (_navigationIds.size() == 0 || !Objects.equals(_navigationIds.peek(), id))
            _navigationIds.push(id);
    }
    public Integer GetFromNavigationIds()
    {
        if (_navigationIds.size() > 0)
            return _navigationIds.pop();
        return 0;
    }

    private EnumTypeDevice _typeDevice;
    public void set_typeDevice(EnumTypeDevice typeDevice) { _typeDevice = typeDevice; }
    public EnumTypeDevice get_typeDevice() { return _typeDevice; }

    private String _deviceId;
    public String get_deviceId() { return _deviceId; }

    private Calendar _firstOpenDate;
    public Calendar get_firstOpenDate() { return _firstOpenDate; }
    public Calendar get_firstOpenDateTo()
    {
        Calendar calendar = (Calendar) _firstOpenDate.clone();
        calendar.add(Calendar.DATE, 14);
        return calendar;
    }

    private boolean _firstOpen;
    public boolean getFirstOpen() { return _firstOpen; }
    public void setFirstOpen(boolean firstOpen) { _firstOpen = firstOpen; }

    private EnumTypeAction _typeAction4Popup;
    private EnumTypeAction _typeAction4Popup2;
    private EnumTypeAction _typeAction4Popup3;
    public EnumTypeAction get_typeAction4Popup() { return _typeAction4Popup; }
    public void set_typeAction4Popup(EnumTypeAction typeAction) { _typeAction4Popup = typeAction; }
    public EnumTypeAction get_typeAction4Popup2() { return _typeAction4Popup2; }
    public void set_typeAction4Popup2(EnumTypeAction typeAction) { _typeAction4Popup2 = typeAction; }
    public EnumTypeAction get_typeAction4Popup3() { return _typeAction4Popup3; }
    public void set_typeAction4Popup3(EnumTypeAction typeAction) { _typeAction4Popup3 = typeAction; }
    public void clear_typeActions()
    {
        _typeAction4Popup = null;
        _typeAction4Popup2 = null;
        _typeAction4Popup3 = null;
    }
    private Object _data;
    public Object get_data() { return _data; }
    private IFragmentViewCustom _iFragmentViewCustom;
    public IFragmentViewCustom get_IFragmentViewCustom() { return _iFragmentViewCustom; }
    public void set_IFragmentViewCustom(IFragmentViewCustom iFragmentViewCustom)
    {
        _iFragmentViewCustom = iFragmentViewCustom;
    }

    private IFragmentViewCustom _iFragmentViewCustom2;
    public IFragmentViewCustom get_IFragmentViewCustom2() { return _iFragmentViewCustom2; }
    public void set_IFragmentViewCustom2(IFragmentViewCustom iFragmentViewCustom2)
    {
        _iFragmentViewCustom2 = iFragmentViewCustom2;
    }

    private IFragmentViewCustom _iFragmentViewCustom3;
    public IFragmentViewCustom get_IFragmentViewCustom3() { return _iFragmentViewCustom3; }

    public void set_4Popup(EnumTypeAction typeAction,
                           EnumTypeAction typeAction2,
                           EnumTypeAction typeAction3,
                           Object data,
                           IFragmentViewCustom iFragmentViewCustom,
                           IFragmentViewCustom iFragmentViewCustom2,
                           IFragmentViewCustom iFragmentViewCustom3)
    {
        _typeAction4Popup = typeAction;
        _typeAction4Popup2 = typeAction2;
        _typeAction4Popup3 = typeAction3;
        _data = data;
        _iFragmentViewCustom = iFragmentViewCustom;
        _iFragmentViewCustom2 = iFragmentViewCustom2;
        _iFragmentViewCustom3 = iFragmentViewCustom3;
    }

    private CustomPopupBottomFragment _popup;
    public void set_popup(CustomPopupBottomFragment popup) { _popup = popup; }
    public CustomPopupBottomFragment get_popup() { return _popup; }

    private Long _moneyDreamId;
    public Long get_moneyDreamId() { return _moneyDreamId; }
    public void set_moneyDreamId(Long moneyDreamId) { _moneyDreamId = moneyDreamId; }

    private String _dateMonth;
    public void set_dateMonth(String dateMonth) { _dateMonth = dateMonth; }
    public String get_dateMonth() { return _dateMonth; }
    private String _dateYear;
    public void set_dateYear(String dateYear) { _dateYear = dateYear; }
    public String get_dateYear() { return _dateYear; }

    private String _imagePath;
    public void set_imagePath(String imagePath) { _imagePath = imagePath; }
    public String get_imagePath(boolean clear)
    {
        String s = _imagePath;
        if (clear)
            _imagePath = null;
        return s;
    }

    private EnumTypeCategory _typeCategory;
    public void set_typeCategory(EnumTypeCategory typeCategory) { _typeCategory = typeCategory; }
    public EnumTypeCategory get_typeCategory() { return _typeCategory; }

    private Long _target_id;
    public void set_target_id(Long target_id) { _target_id = target_id; }
    public Long get_target_id() { return _target_id; }

    private List<PlanValue> _planValues;
    public void set_planValues(List<PlanValue> list) { _planValues = list; }
    public List<PlanValue> get_planValues() { return _planValues; }

    private List<MoneyCategory> _parentCategory;
    public void set_parentCategory(List<MoneyCategory> _parentCategory)
    {
        this._parentCategory = _parentCategory;
    }
    public List<MoneyCategory> get_parentCategory() { return _parentCategory; }

    //region LegendShowed
    private String _legendShowed;
    public String get_legendShowed() { return _legendShowed == null ? "" : _legendShowed; }
    public void set_legendShowed(String legendShowed) { _legendShowed = legendShowed; }

    private Hashtable<String, Boolean> _legendShowedHT = new Hashtable<>();
    public Boolean get_legendShowedHT(String form)
    {
        if (_legendShowedHT != null &&
            _legendShowedHT.keySet().contains(form))
            return _legendShowedHT.get(form);

        return false;
    }
    public void set_legendShowedHT(String form)
    {
        _legendShowedHT.put(form, true);
        SaveSharedPreferences();
    }
    //endregion

    //region MoneyPNLParams
    //Нет необходимости сохранять
//    private Long _pnl_categoryId;
//    public void set_pnl_categoryId(Long pnl_categoryId) { _pnl_categoryId = pnl_categoryId; }
//    public Long get_pnl_categoryId() { return _pnl_categoryId; }

    //Нет необходимости сохранять
    private MoneyAccountingSum _pnl_moneyAccountingSumCost;
    public void set_pnl_moneyAccountingSumCost(MoneyAccountingSum mas)
    {
        _pnl_moneyAccountingSumCost = mas;
    }
    public MoneyAccountingSum get_pnl_moneyAccountingSumCost()
    {
        return _pnl_moneyAccountingSumCost;
    }

    //Нет необходимости сохранять
    private MoneyAccountingSum _pnl_moneyAccountingSumIncrease;
    public void set_pnl_moneyAccountingSumIncrease(MoneyAccountingSum mas)
    {
        _pnl_moneyAccountingSumIncrease = mas;
    }
    public MoneyAccountingSum get_pnl_moneyAccountingSumIncrease()
    {
        return _pnl_moneyAccountingSumIncrease;
    }

    //Нет необходимости сохранять
    private CustomPeriodClass _pnl_customPeriodClass;
    public void set_pnl_customPeriodClass(CustomPeriodClass customPeriodClass)
    {
        _pnl_customPeriodClass = customPeriodClass;
    }
    public CustomPeriodClass get_pnl_customPeriodClass() { return _pnl_customPeriodClass; }

    //Нет необходимости сохранять
    private int _pnl_scrollPosition;
    public void set_pnl_scrollPosition(int position) { _pnl_scrollPosition = position; }
    public int get_pnl_scrollPosition() { return _pnl_scrollPosition; }
    //endregion

    //region MoneyCapitalParams
    //Нет необходимости сохранять
    private CustomPeriodClass _capital_customPeriodClass = new CustomPeriodClass(3, "");
    public void set_capital_customPeriodClass(CustomPeriodClass customPeriodClass)
    {
        _capital_customPeriodClass = customPeriodClass;
    }
    public CustomPeriodClass get_capital_customPeriodClass() { return _capital_customPeriodClass; }
    //endregion

    //region MoneyTypeAsset Params
    private Long _typeAsset4WalletId;

    public void set_typeAsset4WalletId(Long typeAssetWalletId) { _typeAsset4WalletId = typeAssetWalletId; }

    public Long get_typeAsset4WalletId() { return _typeAsset4WalletId; }
    //endregion

    //region MoneyIncomingAndCostParams
//    private Hashtable<EnumTypeCategory, EnumTypeData> _moneyIncomingAndCostTypeData;
//    public void set_moneyIncomingAndCostTypeData(
//            EnumTypeCategory typeCategory, EnumTypeData typeData)
//    {
//        if (_moneyIncomingAndCostTypeData == null)
//            _moneyIncomingAndCostTypeData = new Hashtable<>();
//
//        _moneyIncomingAndCostTypeData.put(typeCategory, typeData);
//    }
//    public EnumTypeData get_moneyIncomingAndCostTypeData(EnumTypeCategory typeCategory)
//    {
//        if (_moneyIncomingAndCostTypeData == null)
//            _moneyIncomingAndCostTypeData = new Hashtable<>();
//
//        EnumTypeData typeData = _moneyIncomingAndCostTypeData.get(typeCategory);
//
//        if (typeData == null)
//        {
//            typeData = EnumTypeData.Plan;
//            set_moneyIncomingAndCostTypeData(typeCategory, typeData);
//        }
//
//        return typeData;
//    }
//
//    private EnumTypeCategory _moneyIncomingAndCostTypeCategory;
//    public void set_moneyIncomingAndCostTypeCategory(EnumTypeCategory typeCategory)
//    {
//        _moneyIncomingAndCostTypeCategory = typeCategory;
//    }
//    public EnumTypeCategory get_moneyIncomingAndCostTypeCategory()
//    {
//        return _moneyIncomingAndCostTypeCategory;
//    }
    //endregion

    //region MoneyIncomingAndCostPopupNew params
    private MoneyCategory _incomingAndCostPopupNew_moneyCategory;
    public void set_incomingAndCostPopupNew_moneyCategory(MoneyCategory moneyCategory)
    {
        _incomingAndCostPopupNew_moneyCategory = moneyCategory;
    }
    public MoneyCategory get_incomingAndCostPopupNew_moneyCategory() { return _incomingAndCostPopupNew_moneyCategory; }

    private MoneyIncrease _incomingAndCostPopupNew_increase;
    public void set_incomingAndCostPopupNew_increase(MoneyIncrease moneyIncrease)
    {
        _incomingAndCostPopupNew_increase = moneyIncrease;
    }
    public MoneyIncrease get_incomingAndCostPopupNew_increase() { return _incomingAndCostPopupNew_increase; }
    //endregion

    //region MoneyPlanPopupNew params
    private MoneyCategory _moneyPlanPopupNew_moneyCategory;
    public void set_moneyPlanPopupNew_moneyCategory(MoneyCategory moneyCategory)
    {
        _moneyPlanPopupNew_moneyCategory = moneyCategory;
    }
    public MoneyCategory get_moneyPlanPopupNew_moneyCategory() { return _moneyPlanPopupNew_moneyCategory; }

    private int _moneyPlanPopupNew_month;
    public void set_moneyPlanPopupNew_month(int month)
    {
        _moneyPlanPopupNew_month = month;
    }
    public int get_moneyPlanPopupNew_month() { return _moneyPlanPopupNew_month; }

    private int _moneyPlanPopupNew_year;
    public void set_moneyPlanPopupNew_year(int year)
    {
        _moneyPlanPopupNew_year = year;
    }
    public int get_moneyPlanPopupNew_year() { return _moneyPlanPopupNew_year; }
    //endregion

    //region MoneyAccountingPopupNew params
    private IMASCategory _moneyAccountingSumCost;
    public void set_moneyAccountingSumCost(IMASCategory moneyAccountingSumCost)
    {
        _moneyAccountingSumCost = moneyAccountingSumCost;
    }
    public IMASCategory get_moneyAccountingSumCost() { return _moneyAccountingSumCost;}

    private IMASCategory _moneyAccountingSumIncrease;
    public void set_moneyAccountingSumIncrease(IMASCategory moneyAccountingSumIncrease)
    {
        _moneyAccountingSumIncrease = moneyAccountingSumIncrease;
    }
    public IMASCategory get_moneyAccountingSumIncrease() { return _moneyAccountingSumIncrease; }

    private Calendar _MAccPstartDate;
    public void set_MAccPstartDate(Calendar MAccPstartDate) { _MAccPstartDate = MAccPstartDate; }
    public Calendar get_MAccPstartDate() { return _MAccPstartDate; }

    private Calendar _MAccPendDate;
    public void set_MAccPendDate(Calendar MAccPendDate) { _MAccPendDate = MAccPendDate; }
    public Calendar get_MAccPendDate() { return _MAccPendDate; }

    private Long _selSubCategoryId;
    public void set_selSubCategoryId(Long selSubCategoryId) { _selSubCategoryId = selSubCategoryId; }
    public Long get_selSubCategoryId() { return _selSubCategoryId; }

    private Long _selSubCategoryIcon;
    public void set_selSubCategoryIcon(Long icon) { _selSubCategoryIcon = icon; }
    public Long get_selSubCategoryIcon() { return _selSubCategoryIcon; }

    private String _selSubCategoryName;
    public void set_selSubCategoryName(String selSubCategoryName) { _selSubCategoryName = selSubCategoryName; }
    public String get_selSubCategoryName() { return _selSubCategoryName; }

    private MoneyPNL _MAccPmoneyPNL;
    public void set_MAccmoneyPNL(MoneyPNL moneyPNL)
    {
        _MAccPmoneyPNL = moneyPNL;
    }
    public MoneyPNL get_MAccPmoneyPNL() { return _MAccPmoneyPNL; }

    private boolean _fragmentPopupEditable;
    public void set_fragmentPopupEditable(boolean editable)
    {
        _fragmentPopupEditable = editable;
    }
    public boolean get_fragmentPopupEditable()
    {
        return _fragmentPopupEditable;
    }

    private boolean _fragmentPopupEditIsPlan;
    public void set_fragmentPopupEditIsPlan(boolean editIsPlan)
    {
        _fragmentPopupEditIsPlan = editIsPlan;
    }
    public boolean get_fragmentPopupEditIsPlan()
    {
        return _fragmentPopupEditIsPlan;
    }

    private MoneyInc4Cost _MAccMoneyInc4Cost;
    public void set_MAccMoneyInc4Cost(MoneyInc4Cost moneyInc4Cost)
    {
        _MAccMoneyInc4Cost = moneyInc4Cost;
    }
    public MoneyInc4Cost get_MAccMoneyInc4Cost() { return _MAccMoneyInc4Cost; }

    private Long _MAccIncreaseCategoryId;
    public void set_MAccIncreaseCategoryId(Long MAccIncreaseCategoryId)
    {
        _MAccIncreaseCategoryId = MAccIncreaseCategoryId;
    }
    public Long get_MAccIncreaseCategoryId() { return _MAccIncreaseCategoryId; }

    private Long _MAccIncreaseCategoryIcon;
    public void set_MAccIncreaseCategoryIcon(Long icon)
    {
        _MAccIncreaseCategoryIcon = icon;
    }
    public Long get_MAccIncreaseCategoryIcon() { return _MAccIncreaseCategoryIcon; }

    private Long _MAccOldIncreaseCategoryId;
    public void set_MAccOldIncreaseCategoryId(Long MAccOldIncreaseCategoryId)
    {
        _MAccOldIncreaseCategoryId = MAccOldIncreaseCategoryId;
    }
    public Long get_MAccOldIncreaseCategoryId() { return _MAccOldIncreaseCategoryId; }

    private Long _MAccOldIncSubCategoryId;
    public void set_MAccOldIncSubCategoryId(Long MAccOldIncSubCategoryId)
    {
        _MAccOldIncSubCategoryId = MAccOldIncSubCategoryId;
    }
    public Long get_MAccOldIncSubCategoryId() { return _MAccOldIncSubCategoryId; }

    private Long _MAccCostCategoryId;
    public void set_MAccCostCategoryId(Long MAccCostCategoryId)
    {
        _MAccCostCategoryId = MAccCostCategoryId;
    }
    public Long get_MAccCostCategoryId() { return _MAccCostCategoryId; }

    private Long _MAccCostCategoryIcon;
    public void set_MAccCostCategoryIcon(Long icon)
    {
        _MAccCostCategoryIcon = icon;
    }
    public Long get_MAccCostCategoryIcon() { return _MAccCostCategoryIcon; }

    private Long _MAccOldCostCategoryId;
    public void set_MAccOldCostCategoryId(Long MAccOldCostCategoryId)
    {
        _MAccOldCostCategoryId = MAccOldCostCategoryId;
    }
    public Long get_MAccOldCostCategoryId() { return _MAccOldCostCategoryId; }

    private Long _MAccOldSubCategoryId;
    public void set_MAccOldSubCategoryId(Long MAccOldSubCategoryId)
    {
        _MAccOldSubCategoryId = MAccOldSubCategoryId;
    }
    public Long get_MAccOldSubCategoryId() { return _MAccOldSubCategoryId; }

//    private Double _MAccOldSum;
//    public void set_MAccOldSum(Double MAccOldSum) { _MAccOldSum = MAccOldSum; }
//    public Double get_MAccOldSum() { return _MAccOldSum; }
    private Long _MAccOldSumLong;
    public void set_MAccOldSumLong(Long MAccOldSumLong) { _MAccOldSumLong = MAccOldSumLong; }
    public Long get_MAccOldSumLong() { return _MAccOldSumLong; }

    private Calendar _MAccDate;
    public void set_MAccDate(Calendar MAccDate) { _MAccDate = MAccDate; }
    public Calendar get_MAccDate() { return _MAccDate; }

    private EnumTypeCategory _MAccTypeCategory;
    public void set_MAccTypeCategory(EnumTypeCategory typeCategory)
    {
        _MAccTypeCategory = typeCategory;
    }
    public EnumTypeCategory get_MAccTypeCategory() { return _MAccTypeCategory; }

    private EnumTypeCategory _MAccTypeCategoryChanged;
    public void set_MAccTypeCategoryChanged(EnumTypeCategory typeCategory)
    {
        _MAccTypeCategoryChanged = typeCategory;
    }
    public EnumTypeCategory get_MAccTypeCategoryChanged() { return _MAccTypeCategoryChanged; }
    //endregion

    //region MoneyCategoryPopup4PNLNew
    private Long _categoryId;
    public void set_categoryId(Long categoryId) { _categoryId = categoryId; }
    public Long get_categoryId() { return _categoryId; }

    private EnumTypeCategory _typeCategory4PNL;
    public void set_typeCategory4PNL(EnumTypeCategory typeCategory4PNL) { _typeCategory4PNL = typeCategory4PNL; }
    public EnumTypeCategory get_typeCategory4PNL() { return  _typeCategory4PNL; }
    //endregion

    //region MoneyCreditParams
    private Long _increaseCreditsCategoryId;
    public void set_increaseCreditsCategoryId(Long creditsCategoryId) { _increaseCreditsCategoryId = creditsCategoryId;}
    public Long get_increaseCreditsCategoryId() { return _increaseCreditsCategoryId;}

    private Long _costCreditsCategoryId;
    public void set_costCreditsCategoryId(Long creditsCategoryId) { _costCreditsCategoryId = creditsCategoryId;}
    public Long get_costCreditsCategoryId() { return _costCreditsCategoryId;}

    private Long _typeAsset4CreditId;
    public void set_typeAsset4CreditId(Long typeAsset4CreditId) { _typeAsset4CreditId = typeAsset4CreditId; }
    public Long get_typeAsset4Creditd() { return _typeAsset4CreditId; }

    private Long _creditGroupPortfolioId;
    public void set_creditGroupPortfolioId(Long groupPortfolioId) { _creditGroupPortfolioId = groupPortfolioId; }
    public Long get_creditGroupPortfolioId() { return _creditGroupPortfolioId; }

    private Long _creditPortfolioId;
    public void set_creditPortfolioId(Long creditPortfolioId) { _creditPortfolioId = creditPortfolioId; }
    public Long get_creditPortfolioId() { return _creditPortfolioId; }
    //endregion

    //region MoneyTargetParams
    private int _costTargetsCategoryId;
    public void set_costTargetsCategoryId(int costTargetsCategoryId) { _costTargetsCategoryId = costTargetsCategoryId;}
    public int get_costTargetsCategoryId() { return _costTargetsCategoryId;}
    //endregion

    //region MoneyIncomingAndCostSetting
//    private Hashtable<EnumTypeCategory, MoneyIncomingAndCostSettings> _moneyIncomingAndCostSettings =
//            new Hashtable<>();
//    public void set_moneyIncomingAndCostSettings(MoneyIncomingAndCostSettings data)
//    {
//        _moneyIncomingAndCostSettings.put(data.get_typeCategory(), data);
//    }
//    public MoneyIncomingAndCostSettings get_moneyIncomingAndCostSettings(EnumTypeCategory typeCategory)
//    {
//        MoneyIncomingAndCostSettings data = _moneyIncomingAndCostSettings.get(typeCategory);
//
//        if (data == null)
//        {
//            data = new MoneyIncomingAndCostSettings(typeCategory,
//                    false,
//                    false,
//                    false,
//                    EnumTypeData.Plan,
//                    EnumPeriodData.Month,
//                    false);
//            set_moneyIncomingAndCostSettings(data);
//        }
//
//        return data;
//    }
    //endregion

    //region MoneyDreamSetting
    private MoneyDreamSettings _moneyDreamSettings;
    public MoneyDreamSettings get_moneyDreamSettings() { return _moneyDreamSettings; }
    public void set_moneyDreamSettings(MoneyDreamSettings moneyDreamSettings)
    {
        _moneyDreamSettings = moneyDreamSettings;
        SaveSharedPreferences();
    }
    //endregion

    //region TagSettings
    private EnumTagTypeData _enumTagTypeData;
    public EnumTagTypeData get_enumTagTypeData() { return _enumTagTypeData; }
    public void set_enumTagTypeData(EnumTagTypeData enumTagTypeData)
    {
        _enumTagTypeData = enumTagTypeData;
    }

    private Hashtable<EnumTagTypeData, TagSettings> _tagSettingsHT;
    public TagSettings get_TagSettings()
    {
        if (_tagSettingsHT == null)
            _tagSettingsHT = new Hashtable<>();
        if (_enumTagTypeData == null)
            return null;

        TagSettings tagSettings = _tagSettingsHT.get(_enumTagTypeData);
        if (tagSettings != null)
            return tagSettings;
        else
        {
            set_TagSettings(new TagSettings(_enumTagTypeData, true));
            return _tagSettingsHT.get(_enumTagTypeData);
        }
    }

    public void set_TagSettings(TagSettings tagSettings)
    {
        if (_tagSettingsHT == null)
            _tagSettingsHT = new Hashtable<>();

        _tagSettingsHT.put(_enumTagTypeData, tagSettings);
        SaveSharedPreferences();
    }
    //endregion

    //region MoneyIncreaseSetting
    private MoneyIncreaseSettings _moneyIncreaseSettings;
    public MoneyIncreaseSettings get_moneyIncreaseSettings() { return _moneyIncreaseSettings; }
    public void set_moneyIncreaseSettings(MoneyIncreaseSettings moneyIncreaseSettings)
    {
        _moneyIncreaseSettings = moneyIncreaseSettings;
        SaveSharedPreferences();
    }
    //endregion

    //region MoneyTargetSetting
    private Long _moneyTargetDreamId;
    public Long get_moneyTargetDreamId() { return _moneyTargetDreamId; }
    public void set_moneyTargetDreamId(Long dreamId) { _moneyTargetDreamId = dreamId; }

    private Long _moneyTargetTargetId;
    public Long get_moneyTargetTargetId() { return _moneyTargetTargetId; }
    public void set_moneyTargetTargetId(Long targetId) { _moneyTargetTargetId = targetId; }

    private Integer _moneyTargetYear;
    public Integer get_moneyTargetYear() { return _moneyTargetYear; }
    public void set_moneyTargetYear(Integer moneyTargetYear) { _moneyTargetYear = moneyTargetYear; }
    public void plus1_moneyTargetYear() { _moneyTargetYear++; }
    public void minus1_moneyTargetYear() { _moneyTargetYear--; }

    private EnumTargetStatus4Settings _moneyTargetEnumTargetStatus4Settings;
    public EnumTargetStatus4Settings get_moneyTargetEnumTargetStatus4Settings() { return _moneyTargetEnumTargetStatus4Settings; }
    public void set_moneyTargetEnumTargetStatus4Settings(EnumTargetStatus4Settings enumTargetStatus4Settings)
    {
        _moneyTargetEnumTargetStatus4Settings = enumTargetStatus4Settings;
    }

    private Hashtable<Long, MoneyTargetSettings> _moneyTargetSettingsHT;
    public MoneyTargetSettings get_moneyTargetSettings()
    {
        if (_moneyTargetSettingsHT == null)
            _moneyTargetSettingsHT = new Hashtable<>();
        if (_moneyTargetDreamId == null)
            return null;

        MoneyTargetSettings moneyTargetSettings = _moneyTargetSettingsHT.get(_moneyTargetDreamId);
        if (moneyTargetSettings != null)
            return moneyTargetSettings;
        else
        {
            set_moneyTargetSettingsHT(new MoneyTargetSettings(EnumTargetStatus4Settings.All));
            return _moneyTargetSettingsHT.get(_moneyTargetDreamId);
        }
    }

    public void set_moneyTargetSettingsHT(MoneyTargetSettings moneyTargetSettings)
    {
        if (_moneyTargetSettingsHT == null)
            _moneyTargetSettingsHT = new Hashtable<>();

        _moneyTargetSettingsHT.put(_moneyTargetDreamId, moneyTargetSettings);
        SaveSharedPreferences();
    }
    //endregion

    //region MoneyCategorySetting
    private EnumTypeCategory _moneyCategorySettingEnumTypeCategory;
    public void set_moneyCategorySettingEnumTypeCategory(EnumTypeCategory typeCategory)
    {
        _moneyCategorySettingEnumTypeCategory = typeCategory;
    }
    public EnumTypeCategory get_moneyCategorySettingEnumTypeCategory()
    {
        return _moneyCategorySettingEnumTypeCategory;
    }

    private Hashtable<EnumTypeCategory, MoneyCategorySettings> _moneyCategorySettingsHT;
    public MoneyCategorySettings get_moneyCategorySettingsHT()
    {
        return get_moneyCategorySettingsHT(_moneyCategorySettingEnumTypeCategory);
    }

    public MoneyCategorySettings get_moneyCategorySettingsHT(EnumTypeCategory typeCategory)
    {
        if (_moneyCategorySettingsHT == null)
            _moneyCategorySettingsHT = new Hashtable<>();

        MoneyCategorySettings moneyCategorySettings = _moneyCategorySettingsHT.get(typeCategory);
        if (moneyCategorySettings != null)
            return moneyCategorySettings;
        else
        {
            set_moneyCategorySettingsHT(new MoneyCategorySettings(
                    typeCategory,
                    true,
                    true,
                    true,
                    EnumCategoryType.All,
                    null));
            return _moneyCategorySettingsHT.get(typeCategory);
        }
    }

    public void set_moneyCategorySettingsHT(MoneyCategorySettings moneyCategorySettings)
    {
        if (_moneyCategorySettingsHT == null)
            _moneyCategorySettingsHT = new Hashtable<>();

        _moneyCategorySettingsHT.put(_moneyCategorySettingEnumTypeCategory, moneyCategorySettings);
        SaveSharedPreferences();
    }

    private int _moneyCategorySettings_fromItem;
    public void set_moneyCategorySettings_fromItem(int fromItem) { _moneyCategorySettings_fromItem = fromItem; }
    public int get_moneyCategorySettings_fromItem() { return _moneyCategorySettings_fromItem; }
    //endregion

    //region MoneyIncomingAndCostSetting
    private EnumMoneyIncAndCostSetting _enumMoneyIncAndCostSetting;
    public void set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting enumMoneyIncAndCostSetting)
    {
        _enumMoneyIncAndCostSetting = enumMoneyIncAndCostSetting;
    }
    public EnumMoneyIncAndCostSetting get_enumMoneyIncAndCostSetting() { return _enumMoneyIncAndCostSetting; }

    private Hashtable<EnumMoneyIncAndCostSetting, MoneyIncomingAndCostSettings> _moneyIncAndCostSettingHT;
    public MoneyIncomingAndCostSettings get_moneyIncomingAndCostSettingsHT()
    {
        return get_moneyIncomingAndCostSettingsHT(_enumMoneyIncAndCostSetting);
    }
    public MoneyIncomingAndCostSettings get_moneyIncomingAndCostSettingsHT(EnumMoneyIncAndCostSetting enumMoneyIncAndCostSetting)
    {
        if (_moneyIncAndCostSettingHT == null)
            _moneyIncAndCostSettingHT = new Hashtable<>();

        MoneyIncomingAndCostSettings moneyIncomingAndCostSettings = _moneyIncAndCostSettingHT.get(enumMoneyIncAndCostSetting);
        if (moneyIncomingAndCostSettings != null)
            return moneyIncomingAndCostSettings;
        else
        {
            if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.IncomingPlan))
                set_moneyIncomingAndCostSettingsHT(enumMoneyIncAndCostSetting,
                        new MoneyIncomingAndCostSettings(
                        EnumTypeCategory.Increase, false, false, true,
                        EnumTypeData.Plan, EnumPeriodData.Month, R.id.navigation_money_incoming,
                        0L, 3, null, true, EnumCategoryType.All));
            else if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.IncomingFact))
                set_moneyIncomingAndCostSettingsHT(enumMoneyIncAndCostSetting,
                        new MoneyIncomingAndCostSettings(
                        EnumTypeCategory.Increase, false, false, false,
                        EnumTypeData.Fact, EnumPeriodData.Month, R.id.navigation_money_incoming_fact,
                        0L, 3, null, true, EnumCategoryType.All));
            else if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.ExpensePlan))
                set_moneyIncomingAndCostSettingsHT(enumMoneyIncAndCostSetting,
                        new MoneyIncomingAndCostSettings(
                        EnumTypeCategory.Cost, false, false, true,
                        EnumTypeData.Plan, EnumPeriodData.Month, R.id.navigation_money_costs,
                        0L, 3, null, true, EnumCategoryType.All));
            else if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.ExpenseFact))
                set_moneyIncomingAndCostSettingsHT(enumMoneyIncAndCostSetting,
                        new MoneyIncomingAndCostSettings(
                        EnumTypeCategory.Cost, false, false, false,
                        EnumTypeData.Fact, EnumPeriodData.Month, R.id.navigation_money_costs_fact,
                        0L, 3, null,true, EnumCategoryType.All));

            return _moneyIncAndCostSettingHT.get(enumMoneyIncAndCostSetting);
        }
    }

    public MoneyIncomingAndCostSettings get_moneyIncomingAndCostSettings()
    {
        return get_moneyIncomingAndCostSettingsHT(_enumMoneyIncAndCostSetting);
    }

    public void set_moneyIncomingAndCostSettingsHT(
            EnumMoneyIncAndCostSetting enumMoneyIncAndCostSetting,
            MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        if (_moneyIncAndCostSettingHT == null)
            _moneyIncAndCostSettingHT = new Hashtable<>();

        _moneyIncAndCostSettingHT.put(enumMoneyIncAndCostSetting, moneyIncomingAndCostSettings);
        SaveSharedPreferences();
    }
    public void set_moneyIncomingAndCostSettings(MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
    {
        set_moneyIncomingAndCostSettingsHT(_enumMoneyIncAndCostSetting, moneyIncomingAndCostSettings);
    }

    public int get_moneyIncomingAndCostSettingsCountMoneyIncrease(EnumTypeCategory typeCategory,
                                                                  EnumTypeData typeData)
    {
        MoneyIncomingAndCostSettings moneyIncomingAndCostSettings;
        if (typeCategory.equals(EnumTypeCategory.Increase))
        {
            if (typeData.equals(EnumTypeData.Plan))
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.IncomingPlan);
            else
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.IncomingFact);
        }
        else
        {
            if (typeData.equals(EnumTypeData.Plan))
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.ExpensePlan);
            else
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.ExpenseFact);
        }
        return moneyIncomingAndCostSettings.get_countMoneyIncrease();
    }

    public ArrayList<Long> get_expandedList(EnumTypeCategory typeCategory, EnumTypeData typeData)
    {
        MoneyIncomingAndCostSettings moneyIncomingAndCostSettings;
        if (typeCategory.equals(EnumTypeCategory.Increase))
        {
            if (typeData.equals(EnumTypeData.Plan))
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.IncomingPlan);
            else
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.IncomingFact);
        }
        else
        {
            if (typeData.equals(EnumTypeData.Plan))
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.ExpensePlan);
            else
                moneyIncomingAndCostSettings =
                        _moneyIncAndCostSettingHT.get(EnumMoneyIncAndCostSetting.ExpenseFact);
        }
        return moneyIncomingAndCostSettings.get_expandedList();
    }
    //endregion

    //region MoneyPlanFactSettings
    private EnumTypeCategory _enumMoneyPlanFactTypeCategory;
    public void set_enumMoneyPlanFactTypeCategory(EnumTypeCategory enumMoneyPlanFactTypeCategory)
    {
        _enumMoneyPlanFactTypeCategory = enumMoneyPlanFactTypeCategory;
    }
    public EnumTypeCategory get_enumMoneyPlanFactTypeCategory() { return _enumMoneyPlanFactTypeCategory; }

    private Hashtable<EnumTypeCategory, MoneyPlanFactSettings> _moneyPlanFactSettingHT;
    public MoneyPlanFactSettings get_moneyPlanFactSettings()
    {
        return get_moneyPlanFactSettingsHT(_enumMoneyPlanFactTypeCategory);
    }
    public MoneyPlanFactSettings get_moneyPlanFactSettingsHT(EnumTypeCategory enumMoneyPlanFactTypeCategory)
    {
        if (_moneyIncAndCostSettingHT == null)
            _moneyIncAndCostSettingHT = new Hashtable<>();

        MoneyPlanFactSettings moneyPlanFactSettings = _moneyPlanFactSettingHT.get(enumMoneyPlanFactTypeCategory);
        if (moneyPlanFactSettings != null)
            return moneyPlanFactSettings;
        else
        {
            if (enumMoneyPlanFactTypeCategory.equals(EnumTypeCategory.Increase))
                set_moneyPlanFactSettingsHT(enumMoneyPlanFactTypeCategory,
                        new MoneyPlanFactSettings(
                                EnumTypeCategory.Increase,
                                new CustomPeriodClass(3, ""),
                                false,
                                true,
                                false,
                                null,
                                true,
                                EnumCategoryType.All,
                                null));
            else if (enumMoneyPlanFactTypeCategory.equals(EnumTypeCategory.Cost))
                set_moneyPlanFactSettingsHT(enumMoneyPlanFactTypeCategory,
                        new MoneyPlanFactSettings(
                                EnumTypeCategory.Cost,
                                new CustomPeriodClass(3, ""),
                                false,
                                true,
                                false,
                                null,
                                true,
                                EnumCategoryType.All,
                                null));

            return _moneyPlanFactSettingHT.get(enumMoneyPlanFactTypeCategory);
        }
    }

    public void set_moneyPlanFactSettingsHT(
            EnumTypeCategory enumMoneyPlanFactTypeCategory,
            MoneyPlanFactSettings moneyPlanFactSettings)
    {
        if (_moneyPlanFactSettingHT == null)
            _moneyPlanFactSettingHT = new Hashtable<>();

        _moneyPlanFactSettingHT.put(enumMoneyPlanFactTypeCategory, moneyPlanFactSettings);
        SaveSharedPreferences();
    }
    public void set_moneyPlanFactSettings(MoneyPlanFactSettings moneyPlanFactSettings)
    {
        _enumMoneyPlanFactTypeCategory = moneyPlanFactSettings.get_typeCategory();
        set_moneyPlanFactSettingsHT(_enumMoneyPlanFactTypeCategory, moneyPlanFactSettings);
    }

    public ArrayList<Long> get_expandedList(EnumTypeCategory typeCategory)
    {
        MoneyPlanFactSettings moneyPlanFactSettings = _moneyPlanFactSettingHT.get(typeCategory);
        return moneyPlanFactSettings.get_expandedList();
    }
    //endregion

    //region MoneyTransactionSettings
    //Нет необходимости сохранять
    private EnumTransactionType _transactionType;
    public void set_pnl_transactionType(EnumTransactionType type) { _transactionType = type; }
    public EnumTransactionType get_pnl_transactionType() { return _transactionType; }

    private Hashtable<EnumTransactionType, MoneyTransactionSettings> _moneyTransactionSettingsHT;
    public MoneyTransactionSettings get_moneyTransactionSettings()
    {
        return get_moneyTransactionSettingsHT(_transactionType);
    }
    public MoneyTransactionSettings get_moneyTransactionSettingsHT(EnumTransactionType transactionType)
    {
        if (_moneyTransactionSettingsHT == null)
            _moneyTransactionSettingsHT = new Hashtable<>();

        MoneyTransactionSettings moneyTransactionSettings =
                _moneyTransactionSettingsHT.get(transactionType);
        if (moneyTransactionSettings != null)
            return moneyTransactionSettings;
        else
        {
            if (transactionType.equals(EnumTransactionType.PNL))
                set_moneyTransactionSettingsHT(transactionType,
                        new MoneyTransactionSettings(
                                transactionType,
                                EnumTypeCategory.All,
                                null,
                                null,
                                null,
                                null));
            else if (transactionType.equals(EnumTransactionType.Inc4Cost))
                set_moneyTransactionSettingsHT(transactionType,
                        new MoneyTransactionSettings(
                                transactionType,
                                EnumTypeCategory.All,
                                null,
                                null,
                                null,
                                null));

            return _moneyTransactionSettingsHT.get(transactionType);
        }
    }

    public void set_moneyTransactionSettingsHT(
            EnumTransactionType transactionType,
            MoneyTransactionSettings moneyTransactionSettings)
    {
        if (_moneyTransactionSettingsHT == null)
            _moneyTransactionSettingsHT = new Hashtable<>();

        _moneyTransactionSettingsHT.put(transactionType, moneyTransactionSettings);
        SaveSharedPreferences();
    }
    public void set_moneyTransactionSettings(MoneyTransactionSettings moneyTransactionSettings)
    {
        _transactionType = moneyTransactionSettings.get_enumTransactionType();
        set_moneyTransactionSettingsHT(_transactionType, moneyTransactionSettings);
        SaveSharedPreferences();
    }
    //endregion

    //region TaskProjectSetting
    private TaskProjectSettings _taskProjectSettings;
    public void set_taskProjectSettings(TaskProjectSettings taskProjectSettings)
    {
        _taskProjectSettings = taskProjectSettings;
    }
    public TaskProjectSettings get_taskProjectSettings()
    {
        if (_taskProjectSettings == null)
            _taskProjectSettings = new TaskProjectSettings(EnumTargetStatus4Settings.All,  null);
        return _taskProjectSettings;
    }
    //endregion

    //region MoneyAccountingSetting
    private MoneyAccountingSettings _moneyAccountingSettings;
    public void set_moneyAccountingSettings(MoneyAccountingSettings moneyAccountingSettings)
    {
        _moneyAccountingSettings = moneyAccountingSettings;
        SaveSharedPreferences();
    }
    public MoneyAccountingSettings get_moneyAccountingSettings()
    {
        if (_moneyAccountingSettings == null)
            _moneyAccountingSettings = new MoneyAccountingSettings(false);

        return _moneyAccountingSettings;
    }

    private int _moneyAccountingSettings_fromItem;
    public void set_moneyAccountingSettings_fromItem(int fromItem) { _moneyAccountingSettings_fromItem = fromItem; }
    public int get_moneyAccountingSettings_fromItem() { return _moneyAccountingSettings_fromItem; }
    //endregion

    //region MoneyPortfolioSetting
    private MoneyPortfolioSettings _moneyPortfolioSettings;
    public void set_moneyPortfolioSettings(MoneyPortfolioSettings moneyPortfolioSettings)
    {
        _moneyPortfolioSettings = moneyPortfolioSettings;
    }
    public MoneyPortfolioSettings get_moneyPortfolioSettings() { return _moneyPortfolioSettings; }
    //endregion

    //region MoneyTypeAssetSettings
    private MoneyTypeAssetSettings _moneyTypeAssetSettings;
    public void set_moneyPortfolioSettings(MoneyTypeAssetSettings moneyTypeAssetSettings)
    {
        _moneyTypeAssetSettings = moneyTypeAssetSettings;
    }
    public MoneyTypeAssetSettings get_moneyTypeAssetSettings() { return _moneyTypeAssetSettings; }
    //endregion

    //region MoneyAssetSettings
    private MoneyAssetSettings _moneyAssetSettings;
    public void set_moneyPortfolioSettings(MoneyAssetSettings moneyAssetSettings)
    {
        _moneyAssetSettings = moneyAssetSettings;
    }
    public MoneyAssetSettings get_moneyAssetSettings() { return _moneyAssetSettings; }
    //endregion

    //region MoneyLotAssetSettings
    private MoneyLotAssetSettings _moneyLotAssetSettings;
    public void set_moneyLotAssetSettings(MoneyLotAssetSettings moneyLotAssetSettings)
    {
        _moneyLotAssetSettings= moneyLotAssetSettings;
    }
    public MoneyLotAssetSettings get_moneyLotAssetSettings() { return _moneyLotAssetSettings; }
    //endregion

    //region MoneyLotAssetOperationSettings
    private MoneyLotAssetOperationSettings _moneyLotAssetOperationSettings;
    public void set_moneyLotAssetOperationSettings(MoneyLotAssetOperationSettings moneyLotAssetOperationSettings)
    {
        _moneyLotAssetOperationSettings = moneyLotAssetOperationSettings;
    }
    public MoneyLotAssetSettings get_moneyLotAssetOperationSettings() { return _moneyLotAssetSettings; }
    //endregion

    //region TasksTaskSetting
    //Не надо сохранять
    private EnumTaskSettings _enumTaskSettings;
    public void set_enumTaskSettings(EnumTaskSettings ets) { _enumTaskSettings = ets; }
    public EnumTaskSettings get_enumTaskSettings() { return _enumTaskSettings; }

    private Hashtable<EnumTaskSettings, TasksTaskSettings> _tasksTaskSettingsHT;
    public TasksTaskSettings get_tasksTaskSettingsHT()
    {
        if (_tasksTaskSettingsHT == null)
            _tasksTaskSettingsHT = new Hashtable<>();

        TasksTaskSettings tasksTaskSettings = _tasksTaskSettingsHT.get(_enumTaskSettings);
        if (tasksTaskSettings != null)
            return tasksTaskSettings;
        else
        {
            if (_enumTaskSettings.equals(EnumTaskSettings.Today))
                set_tasksTaskSettingsHT(new TasksTaskSettings(new CustomPeriodClass(0, ""),
                        false, null, null, 0L,
                        EnumTaskSorting.Priority_Date_Name, false));
            else if (_enumTaskSettings.equals(EnumTaskSettings.Inbox))
                set_tasksTaskSettingsHT(new TasksTaskSettings(new CustomPeriodClass(6, ""),
                        false, 0L, 0L, 0L,
                        EnumTaskSorting.Date_Priority_Name, false));
            else
                set_tasksTaskSettingsHT(new TasksTaskSettings(new CustomPeriodClass(3, ""),
                        false, null, null, 0L,
                        EnumTaskSorting.Date_Priority_Name, true));

            return _tasksTaskSettingsHT.get(_enumTaskSettings);
        }
    }
    public void set_tasksTaskSettingsHT(TasksTaskSettings tasksTaskSettings)
    {
        if (_tasksTaskSettingsHT == null)
            _tasksTaskSettingsHT = new Hashtable<>();

        _tasksTaskSettingsHT.put(_enumTaskSettings, tasksTaskSettings);
        SaveSharedPreferences();
    }
    //endregion

    //region MainActivity params
    private int _mainActivity_navigationId = R.id.navigation_money_dream;
    public int get_mainActivity_navigationId()
    {
        return _mainActivity_navigationId;
    }
    public void set_mainActivity_navigationId(int id) { _mainActivity_navigationId = id; }

    private EnumTypeResource _mainActivity_typeResource = EnumTypeResource.Money;
    public EnumTypeResource get_mainActivity_typeResource() { return _mainActivity_typeResource; }
    public void set_mainActivity_typeResource(EnumTypeResource typeResource) { _mainActivity_typeResource = typeResource; }
    //endregion

    //region MainSettings
    private EnumDatabase _enumDatabase = EnumDatabase.Work;
    public EnumDatabase get_enumDatabase() { return _enumDatabase; }
    public void set_enumDatabase(EnumDatabase enumDatabase) { _enumDatabase = enumDatabase; }

    boolean _isNight;
    public boolean get_isNight() { return _isNight; }
    public void set_isNight(boolean b)
    {
        _isNight = b;
        SaveSharedPreferencesIsNight();
    }

    NotifSettings _notifSettings = new NotifSettings();
    public NotifSettings get_notifSettings() { return _notifSettings; }

    NotifProjectSettings _notifProjectSettings = new NotifProjectSettings();
    public NotifProjectSettings get_notifProjectSettings() { return _notifProjectSettings; }

    NotifMoneySettings _notifMoneySettings = new NotifMoneySettings();
    public NotifMoneySettings getNotifMoneySettings() { return _notifMoneySettings; }

    NotifTasksSettings _notifTasksSettings = new NotifTasksSettings();
    public NotifTasksSettings get_notifTasksSettings() { return _notifTasksSettings; }
    //endregion

    //region Mascot Settings
    private boolean _showMascot = true;
    public boolean get_showMascot() { return _showMascot; }
    public void set_showMascot(boolean showMascot) { _showMascot = showMascot; }
    //endregion

    //region SharedPreferences - сохранение между запусками программы
    public void SaveSharedPreferences()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        editor = SaveSharedPreferencesSubscription4Test(editor);
        editor.putString("unpaidKey", _unpaidKey.toString());
        editor.putLong("firstOpenDate", _firstOpenDate.getTimeInMillis());
        editor.putBoolean("firstOpen", _firstOpen);

        editor.putLong("typeAsset4WalletId", _typeAsset4WalletId);

        SaveSharedPreferences4CreditParams(editor);

        editor.putInt("costTargetsCategoryId", _costTargetsCategoryId);

        //region LegendShowed
        editor.putInt("LegendShowedHTCount", _legendShowedHT.size());
        int i = 0;
        for (String key: _legendShowedHT.keySet())
        {
            editor.putString("LegendShowedHT" + i + "Form", key);
            editor.putBoolean("LegendShowedHT" + i + "Showed", _legendShowedHT.get(key));
            i++;
        }
        //endregion

        //region MainSettings
        editor.putString("MainSettings.EnumDatabase", _enumDatabase.toString());
        //endregion

        //region Mascot Settings
        editor.putBoolean("MainSettings.ShowMascot", _showMascot);
        //endregion

        //region NotifSettings
        editor.putBoolean("isNight", _isNight);
        editor = _notifSettings.SaveToSharedPreferences(editor);
        editor = _notifProjectSettings.SaveToSharedPreferences(editor);
        editor = _notifMoneySettings.SaveToSharedPreferences(editor);
        editor = _notifTasksSettings.SaveToSharedPreferences(editor);
        //endregion

        //region MoneyDreamSettings
        editor = MoneyDreamSettings.SaveToSharedPreferences(editor, _moneyDreamSettings);
        //endregion

        //region MoneyTagSettings
        editor = TagSettings.SaveToSharedPreferences(editor, _tagSettingsHT);
        //endregion

        //region MoneyIncreaseSettings
        editor = MoneyIncreaseSettings.SaveToSharedPreferences(editor, _moneyIncreaseSettings);
        //endregion

        //region MoneyTargetSettings
        editor = MoneyTargetSettings.SaveToSharedPreferences(editor, _moneyTargetSettingsHT);
        //endregion

        //region MoneyCategorySettings
        editor = MoneyCategorySettings.SaveToSharedPreferences(editor, _moneyCategorySettingsHT);
        //endregion

        //region MoneyIncomingAndCostSettings
        editor = MoneyIncomingAndCostSettings.SaveToSharedPreferences(editor, _moneyIncAndCostSettingHT);
        //endregion

        //region MoneyPlanFactSettings
        editor = MoneyPlanFactSettings.SaveToSharedPreferences(editor, _moneyPlanFactSettingHT);
        //endregion

        //region MoneyAccountingSetting
        editor = MoneyAccountingSettings.SaveToSharedPreferences(editor, _moneyAccountingSettings);
        //endregion

        //region MoneyPortfolioSetting
        editor = MoneyPortfolioSettings.SaveToSharedPreferences(editor, _moneyPortfolioSettings);
        //endregion

        //region MoneyTypeAsseSetting
        editor = MoneyTypeAssetSettings.SaveToSharedPreferences(editor, _moneyTypeAssetSettings);
        //endregion

        //region MoneyAsseSetting
        editor = MoneyAssetSettings.SaveToSharedPreferences(editor, _moneyAssetSettings);
        //endregion

        //region MoneyLotAssetSetting
        editor = MoneyLotAssetSettings.SaveToSharedPreferences(editor, _moneyLotAssetSettings);
        //endregion

        //region MoneyLotAssetOperationSetting
        editor = MoneyLotAssetOperationSettings.SaveToSharedPreferences(editor, _moneyLotAssetOperationSettings);
        //endregion

        //region TaskProjectSettings
        editor = TaskProjectSettings.SaveToSharedPreferences(editor, _taskProjectSettings);
        //endregion

        //region TaskTasksSettings
        editor = TasksTaskSettings.SaveToSharedPreferences(editor, _tasksTaskSettingsHT);
        //endregion

        editor.apply();
    }

    public void SaveSharedPreferences4CreditParams()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        SaveSharedPreferences4CreditParams(editor);
        editor.apply();
    }

    public void SaveSharedPreferences4CreditParams(SharedPreferences.Editor editor)
    {
        editor.putLong("increaseCreditsCategoryId", _increaseCreditsCategoryId);
        editor.putLong("costCreditsCategoryId", _costCreditsCategoryId);
        editor.putLong("creditGroupPortfolioId", _creditGroupPortfolioId);
        editor.putLong("creditPortfolioId", _creditPortfolioId);
        editor.putLong("typeAsset4CreditId", _typeAsset4CreditId);
    }

    public void SaveSharedPreferencesSubscription4Test()
    {
        _productId = null;
        _purchaseId = null;
        _startDate = null;
        _endDate = null;
        _dateUpdateSubscription = Calendar.getInstance();
        _dateUpdateSubscription.add(Calendar.MONTH, -1);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SaveSharedPreferencesSubscription4Test(settings.edit()).apply();
    }
    public SharedPreferences.Editor SaveSharedPreferencesSubscription4Test(SharedPreferences.Editor editor)
    {
        editor.putString("productId", _productId);
        editor.putString("purchaseId", _purchaseId);
        editor.putLong("dateUpdateSubscription", Calendar.getInstance().getTime().getTime());
        if (_startDate != null)
            editor.putLong("startDate", _startDate.getTime().getTime());
        if (_endDate != null)
            editor.putLong("endDate", _endDate.getTime().getTime());

        return editor;
    }

    public void SaveSharedPreferencesIsNight()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isNight", _isNight);

        editor.apply();
    }

    public void SaveSharedPreferencesNotifSettingsNotifEnabled()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifSettings.SaveToSharedPreferencesNotifEnabled(editor).apply();
    }

    public void SaveSharedPreferencesNotifSettingsNotifStart()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifSettings.SaveToSharedPreferencesNotifStart(editor).apply();
    }

    public void SaveSharedPreferencesNotifSettingsNotifEnd()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifSettings.SaveToSharedPreferencesNotifEnd(editor).apply();
    }

    public void SaveSharedPreferencesNotifProjectSettingsNeedDream()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifProjectSettings.SaveToSharedPreferencesNeedDream(editor).apply();
    }

    public void SaveSharedPreferencesNotifProjectSettingsNeedTarget()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifProjectSettings.SaveToSharedPreferencesNeedTarget(editor).apply();
    }

    public void SaveSharedPreferencesNotifProjectSettingsNeedTaskInProject()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifProjectSettings.SaveToSharedPreferencesNeedTaskInProject(editor).apply();
    }

    public void SaveSharedPreferencesNotifMoneySettingsActualMonthPlanIncoming()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifMoneySettings.SaveToSharedPreferencesActualMonthPlanIncoming(editor).apply();
    }

    public void SaveSharedPreferencesNotifMoneySettingsActualMonthPlanCost()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifMoneySettings.SaveToSharedPreferencesActualMonthPlanCost(editor).apply();
    }

    public void SaveSharedPreferencesNotifMoneySettingsNoEnteredCostPerDay()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifMoneySettings.SaveToSharedPreferencesNoEnteredCostPerDay(editor).apply();
    }

    public void SaveSharedPreferencesNotifMoneySettingsMoneyReportPrevMonth()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifMoneySettings.SaveToSharedPreferencesMoneyReportPrevMonth(editor).apply();
    }

    public void SaveSharedPreferencesNotifTasksSettingsOverdueTasksInMorning()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifTasksSettings.SaveToSharedPreferencesOverdueTasksInMorning(editor).apply();
    }

    public void SaveSharedPreferencesNotifTasksSettingsCountTasksForDay()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifTasksSettings.SaveToSharedPreferencesCountTasksForDay(editor).apply();
    }

    public void SaveSharedPreferencesNotifTasksSettingsNoTasksForDay()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifTasksSettings.SaveToSharedPreferencesNoTasksForDay(editor).apply();
    }

    public void SaveSharedPreferencesNotifTasksSettingsCountUnfinishedTasksInEvening()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifTasksSettings.SaveToSharedPreferencesCountUnfinishedTasksInEvening(editor).apply();
    }

    public void SaveSharedPreferencesNotifTasksSettingsCountTasksInbox()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        _notifTasksSettings.SaveToSharedPreferencesCountTasksInbox(editor).apply();
    }

    public void SaveSharedPreferencesCapitalSettings()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = settings.edit();
        MoneyLotAssetSettings.SaveToSharedPreferences(editor, _moneyLotAssetSettings);
        editor.apply();
    }

    @SuppressLint("HardwareIds")
    public void LoadSharedPreferences()
    {
        _deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        _productIdSaved = settings.getString("productId", "");
        _purchaseIdSaved = settings.getString("purchaseId", "");

        _dateUpdateSubscription = Calendar.getInstance();
        _dateUpdateSubscription.setTimeInMillis(settings.getLong("dateUpdateSubscription", 0));

        _startDateSaved = Calendar.getInstance();
        _startDateSaved.setTimeInMillis(settings.getLong("startDate", 0));

        _endDateSaved = Calendar.getInstance();
        _endDateSaved.setTimeInMillis(settings.getLong("endDate", 0));

        //TODO: снять комментарии перед выкладкой
        _unpaidKey = UUID.fromString(
                settings.getString("unpaidKey", _unpaid2WeekKey.toString()));

        _firstOpenDate = Calendar.getInstance();
        long timeInMillis = settings.getLong("firstOpenDate", 0);
        if (timeInMillis > 0)
            _firstOpenDate.setTimeInMillis(timeInMillis);
        _firstOpen = settings.getBoolean("firstOpen", true);

        try { _typeAsset4WalletId = settings.getLong("typeAsset4WalletId", 0); }
        catch (Exception e) { _typeAsset4WalletId = 0L; }

        try { _increaseCreditsCategoryId = settings.getLong("increaseCreditsCategoryId", 0L); }
        catch (Exception e) { _increaseCreditsCategoryId = 0L; }
        try { _costCreditsCategoryId = settings.getLong("costCreditsCategoryId", 0L); }
        catch (Exception e) { _costCreditsCategoryId = 0L; }
        try { _creditGroupPortfolioId = settings.getLong("creditGroupPortfolioId", 0L); }
        catch (Exception e) { _creditGroupPortfolioId = 0L; }
        try { _creditPortfolioId = settings.getLong("creditPortfolioId", 0L); }
        catch (Exception e) { _creditPortfolioId = 0L; }
        try { _typeAsset4CreditId = settings.getLong("typeAsset4CreditId", 0L); }
        catch (Exception e) { _typeAsset4CreditId = 0L; }

        _costTargetsCategoryId = settings.getInt("costTargetsCategoryId", 0);

        _isNight = settings.getBoolean("isNight", false);

        //region LegendShowed
        _legendShowedHT = new Hashtable<>();
        int count = settings.getInt("LegendShowedHTCount", 0);
        for (int i = 0; i < count; i++)
        {
            _legendShowedHT.put(
                    settings.getString("LegendShowedHT" + i + "Form", "Form"),
                    settings.getBoolean("LegendShowedHT" + i + "Showed", false));
        }
        //endregion

        //region MainSettings
        _enumDatabase = EnumDatabase.valueOf(
                settings.getString("MainSettings.EnumDatabase", EnumDatabase.Work.toString()));
        //endregion

        //region Mascot Settings
        _showMascot = settings.getBoolean("MainSettings.ShowMascot", true);
        //endregion

        //region NotifSettings
        _notifSettings = NotifSettings.GetFromSharedPreferences(settings);
        _notifProjectSettings = NotifProjectSettings.GetFromSharedPreferences(settings);
        _notifMoneySettings = NotifMoneySettings.GetFromSharedPreferences(settings);
        _notifTasksSettings = NotifTasksSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyTargetSettings
        _moneyDreamSettings = MoneyDreamSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyTargetSettings
        _tagSettingsHT = TagSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyIncreaseSettings
        _moneyIncreaseSettings = MoneyIncreaseSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyTargetSettings
        _moneyTargetSettingsHT = MoneyTargetSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyCategorySettings
        _moneyCategorySettingsHT = MoneyCategorySettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyIncomingAndCostSettings
        _moneyIncAndCostSettingHT = MoneyIncomingAndCostSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyPlanFactSettings
        _moneyPlanFactSettingHT = MoneyPlanFactSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyAccountingSetting
        _moneyAccountingSettings = MoneyAccountingSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyPortfolioSettings
        _moneyPortfolioSettings = MoneyPortfolioSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyTypeAssetSettings
        _moneyTypeAssetSettings = MoneyTypeAssetSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyAssetSettings
        _moneyAssetSettings = MoneyAssetSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyLotAssetSettings
        _moneyLotAssetSettings = MoneyLotAssetSettings.GetFromSharedPreferences(settings);
        //endregion

        //region MoneyLotAssetOperationSettings
        _moneyLotAssetOperationSettings = MoneyLotAssetOperationSettings.GetFromSharedPreferences(settings);
        //endregion

        //region TaskProjectSettings
        _taskProjectSettings = TaskProjectSettings.GetFromSharedPreferences(settings);
        //endregion

        //region TaskTasksSettings
        _tasksTaskSettingsHT = TasksTaskSettings.GetFromSharedPreferences(settings);
        //endregion
    }

    @SuppressLint("HardwareIds")
    public void LoadSharedPreferences4Notif()
    {
        _deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        _isNight = settings.getBoolean("isNight", false);

        _notifSettings = NotifSettings.GetFromSharedPreferences(settings);
        _notifProjectSettings = NotifProjectSettings.GetFromSharedPreferences(settings);
        _notifMoneySettings = NotifMoneySettings.GetFromSharedPreferences(settings);
        _notifTasksSettings = NotifTasksSettings.GetFromSharedPreferences(settings);
    }
    //endregion
}
