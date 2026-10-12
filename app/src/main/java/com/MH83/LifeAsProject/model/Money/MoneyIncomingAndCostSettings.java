package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.model.Interface.IGetCategoryList;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoneyIncomingAndCostSettings extends CustomMoneyObject implements IGetCategoryList {
    //region Parameters
    private final EnumTypeCategory _typeCategory;

    private boolean _showIncreasePercent;
    public boolean get_showIncreasePercent() { return _showIncreasePercent; }
    public void set_showIncreasePercent(boolean b) { _showIncreasePercent = b; }

    private boolean _showPercent;
    public boolean get_showPercent() { return _showPercent; }
    public void set_showPercent(boolean b) { _showPercent = b; }

    private boolean _showBlankData;
    public boolean get_showBlankData() { return _showBlankData; }
    public void set_showBlankData(boolean b) { _showBlankData = b; }

    private final EnumTypeData _typeData;
    public EnumTypeData get_typeData() { return _typeData; }

    private EnumPeriodData _periodData;
    public EnumPeriodData get_periodData() { return _periodData; }
    public void set_periodData(EnumPeriodData periodData) { _periodData = periodData; }

    private final int _fragmentId;
    public int get_fragmentId() { return _fragmentId; }

    private Long _selMoneyIncreaseId;
    public Long get_selMoneyIncreaseId() { return _selMoneyIncreaseId; }
    public void set_selMoneyIncreaseId(Long id) { _selMoneyIncreaseId = id; }

    private int _countMoneyIncrease;
    public int get_countMoneyIncrease() { return _countMoneyIncrease; }
    public void set_countMoneyIncrease(int count) { _countMoneyIncrease = count; }

    private ArrayList<Long> _expandedList;
    public ArrayList<Long> get_expandedList() { return _expandedList; }
    public void set_expandedList(ArrayList<Long> list) { _expandedList = list; }

    private EnumCategoryType _categoryType;
    public void set_typeCategory(EnumCategoryType categoryType) { _categoryType = categoryType; }
    public EnumCategoryType get_categoryType() { return _categoryType; }

    private boolean _showExpandButton;
    public boolean get_showExpandButton() { return _showExpandButton; }
    public void set_showExpandButton(boolean showExpandButton) { _showExpandButton = showExpandButton; }
    //endregion

    //region Constructors
    public MoneyIncomingAndCostSettings(EnumTypeCategory typeCategory,
                                        boolean showIncreasePercent,
                                        boolean showPercent,
                                        boolean showBlankData,
                                        EnumTypeData typeData,
                                        EnumPeriodData periodData,
                                        int fragmentId,
                                        Long selMoneyIncreaseId,
                                        int countMoneyIncrease,
                                        ArrayList<Long> expandedList,
                                        boolean showExpandButton,
                                        EnumCategoryType categoryType)
    {
        _typeCategory = typeCategory;
        _showIncreasePercent = showIncreasePercent;
        _showPercent = showPercent;
        _showBlankData = showBlankData;
        _typeData = typeData;
        _periodData = periodData;
        _fragmentId = fragmentId;
        _selMoneyIncreaseId = selMoneyIncreaseId;
        _countMoneyIncrease = countMoneyIncrease;
        _expandedList = expandedList;
        _showExpandButton = showExpandButton;
        _categoryType = categoryType;
    }

    public MoneyIncomingAndCostSettings Clone()
    {
        return new MoneyIncomingAndCostSettings(_typeCategory,
                _showIncreasePercent,
                _showPercent,
                _showBlankData,
                _typeData,
                _periodData,
                _fragmentId,
                _selMoneyIncreaseId,
                _countMoneyIncrease,
                _expandedList,
                _showExpandButton,
                _categoryType);
    }
    //endregion

    //region CustomMoneyObject
    @Override
    public String getSValue() { return ""; }

    @Override
    public void set_id(Long id) {}

    @Override
    public boolean canDelete(Context context) { return false; }
    //endregion

    //region IGetCategoryList
    public EnumTypeCategory get_typeCategory() { return _typeCategory; }
    public ArrayList<EnumCategoryType> get_categoryList()
    {
        return get_statusList(_categoryType);
    }

    public static ArrayList<EnumCategoryType> get_statusList(EnumCategoryType categoryType)
    {
        ArrayList<EnumCategoryType> list = new ArrayList<>();

        switch (categoryType)
        {
            case Base:
                list.add(EnumCategoryType.Base);
                break;
            case Advanced:
                list.add(EnumCategoryType.Advanced);
                break;
            case AdvancedPlus:
                list.add(EnumCategoryType.Base);
                list.add(EnumCategoryType.Advanced);
                break;
            case Project:
                list.add(EnumCategoryType.Project);
                break;
            default:
        }

        return list;
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }
    //endregion

    //region SaveToSharedPreferences
    public static SharedPreferences.Editor SaveToSharedPreferences(
            SharedPreferences.Editor editor,
            Hashtable<EnumMoneyIncAndCostSetting, MoneyIncomingAndCostSettings> moneyIncomingSettingsHT)
    {
        int i = 0;
        if (moneyIncomingSettingsHT != null)
            for (EnumMoneyIncAndCostSetting enumMoneyIncAndCostSetting : moneyIncomingSettingsHT.keySet())
            {
                MoneyIncomingAndCostSettings moneyIncomingAndCostSettings = moneyIncomingSettingsHT.get(enumMoneyIncAndCostSetting);
                editor.putString("MoneyIncomingAndCostSettings." + i + ".EnumMoneyIncAndCostSetting",
                        enumMoneyIncAndCostSetting.toString());
                editor.putString("MoneyIncomingAndCostSettings." + i + ".EnumTypeCategory",
                        moneyIncomingAndCostSettings.get_typeCategory().toString());
                editor.putBoolean("MoneyIncomingAndCostSettings." + i + ".ShowIncreasePercent",
                        moneyIncomingAndCostSettings.get_showIncreasePercent());
                editor.putBoolean("MoneyIncomingAndCostSettings." + i + ".ShowPercent",
                        moneyIncomingAndCostSettings.get_showPercent());
                editor.putBoolean("MoneyIncomingAndCostSettings." + i + ".ShowBlankData",
                        moneyIncomingAndCostSettings.get_showBlankData());
                editor.putString("MoneyIncomingAndCostSettings." + i + ".EnumTypeData",
                        moneyIncomingAndCostSettings.get_typeData().toString());
                editor.putString("MoneyIncomingAndCostSettings." + i + ".EnumPeriodData",
                        moneyIncomingAndCostSettings.get_periodData().toString());
//                editor.putInt("MoneyIncomingAndCostSettings." + i + ".FragmentId",
//                        moneyIncomingAndCostSettings.get_fragmentId());
                editor.putLong("MoneyIncomingAndCostSettings." + i + ".SelMoneyIncreaseId",
                        moneyIncomingAndCostSettings.get_selMoneyIncreaseId());
                editor.putInt("MoneyIncomingAndCostSettings." + i + ".CountMoneyIncrease",
                        moneyIncomingAndCostSettings.get_countMoneyIncrease());
                editor.putString("MoneyIncomingAndCostSettings." + i + ".CategoryType",
                        moneyIncomingAndCostSettings.get_categoryType().toString());

                i++;
            }
        editor.putInt("MoneyIncomingAndCostSettings.Count", i);

        return editor;
    }

    public static Hashtable<EnumMoneyIncAndCostSetting, MoneyIncomingAndCostSettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<EnumMoneyIncAndCostSetting, MoneyIncomingAndCostSettings> ht = new Hashtable<>();

        int count = settings.getInt("MoneyIncomingAndCostSettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            EnumMoneyIncAndCostSetting enumMoneyIncAndCostSetting =
                EnumMoneyIncAndCostSetting.valueOf(
                    settings.getString("MoneyIncomingAndCostSettings." + i + ".EnumMoneyIncAndCostSetting",
                            EnumMoneyIncAndCostSetting.IncomingPlan.name()));
            int fragmentId = R.id.navigation_money_incoming;
            if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.IncomingFact))
                fragmentId = R.id.navigation_money_incoming_fact;
            else if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.ExpensePlan))
                fragmentId = R.id.navigation_money_costs;
            else if (enumMoneyIncAndCostSetting.equals(EnumMoneyIncAndCostSetting.ExpenseFact))
                fragmentId = R.id.navigation_money_costs_fact;
            long _selMoneyIncreaseId;
            try { _selMoneyIncreaseId = settings.getLong("MoneyIncomingAndCostSettings." + i + ".SelMoneyIncreaseId", 0); }
            catch (Exception e) { _selMoneyIncreaseId = 0L; }
            EnumCategoryType categoryType = EnumCategoryType.All;
            try
            {
                categoryType = EnumCategoryType.valueOf(
                        settings.getString("MoneyIncomingAndCostSettings." + i + ".CategoryType", EnumCategoryType.All.toString()));
            }
            catch (Exception e) { categoryType = EnumCategoryType.All; }
            MoneyIncomingAndCostSettings moneyIncomingAndCostSettings = new MoneyIncomingAndCostSettings(
                    EnumTypeCategory.valueOf(settings.getString(
                            "MoneyIncomingAndCostSettings." + i + ".EnumTypeCategory",
                            EnumTypeCategory.Increase.name())),
                    settings.getBoolean("MoneyIncomingAndCostSettings." + i + ".ShowIncreasePercent", false),
                    settings.getBoolean("MoneyIncomingAndCostSettings." + i + ".ShowPercent", false),
                    settings.getBoolean("MoneyIncomingAndCostSettings." + i + ".ShowBlankData", false),
                    EnumTypeData.valueOf(settings.getString(
                            "MoneyIncomingAndCostSettings." + i + ".EnumTypeData",
                            EnumTypeData.Plan.name())),
                    EnumPeriodData.valueOf(settings.getString(
                            "MoneyIncomingAndCostSettings." + i + ".EnumPeriodData",
                            EnumPeriodData.Month.name())),
                    fragmentId,
                    _selMoneyIncreaseId,
                    settings.getInt("MoneyIncomingAndCostSettings." + i + ".CountMoneyIncrease", 0),
                    null,
                    true,
                    categoryType);

            ht.put(enumMoneyIncAndCostSetting, moneyIncomingAndCostSettings);
        }

        return ht;
    }
    //endregion
}
