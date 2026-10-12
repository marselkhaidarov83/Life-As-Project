package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.model.Interface.IGetCategoryList;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoneyPlanFactSettings extends CustomMoneyObject implements IGetCategoryList
{
    //region Parameters
    private final EnumTypeCategory _typeCategory;

    private CustomPeriodClass _customPeriodClass;
    public CustomPeriodClass get_customPeriodClass() { return _customPeriodClass; }
    public void set_customPeriodClass(CustomPeriodClass customPeriodClass)
    {
        _customPeriodClass = customPeriodClass;
    }

    private boolean _showPercent;
    public boolean get_showPercent() { return _showPercent; }
    public void set_showPercent(boolean b) { _showPercent = b; }

    private boolean _showBlankData;
    public boolean get_showBlankData() { return _showBlankData; }
    public void set_showBlankData(boolean b) { _showBlankData = b; }

    private final Boolean _isPlan;
    public Boolean get_isPlan() { return _isPlan; }

    private ArrayList<Long> _expandedList;
    public ArrayList<Long> get_expandedList() { return _expandedList; }
    public void set_expandedList(ArrayList<Long> list) { _expandedList = list; }

    private EnumCategoryType _categoryType;
    public void set_typeCategory(EnumCategoryType categoryType) { _categoryType = categoryType; }
    public EnumCategoryType get_categoryType() { return _categoryType; }

    private boolean _showExpandButton;
    public boolean get_showExpandButton() { return _showExpandButton; }
    public void set_showExpandButton(boolean showExpandButton) { _showExpandButton = showExpandButton; }

    private Long _tagId;
    public Long get_tagId() { return _tagId; }
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

    //region Constructors
    public MoneyPlanFactSettings(EnumTypeCategory typeCategory,
                                 CustomPeriodClass customPeriodClass,
                                 boolean showPercent,
                                 boolean showBlankData,
                                 Boolean isPlan,
                                 ArrayList<Long> expandedList,
                                 boolean showExpandButton,
                                 EnumCategoryType categoryType,
                                 Long tagId)
    {
        _typeCategory = typeCategory;
        _customPeriodClass = customPeriodClass;
        _showPercent = showPercent;
        _showBlankData = showBlankData;
        _isPlan = isPlan;
        _expandedList = expandedList;
        _showExpandButton = showExpandButton;
        _categoryType = categoryType;
        _tagId = tagId;
    }

    public MoneyPlanFactSettings Clone()
    {
        return new MoneyPlanFactSettings(
                _typeCategory,
                _customPeriodClass,
                _showPercent,
                _showBlankData,
                _isPlan,
                _expandedList,
                _showExpandButton,
                _categoryType,
                _tagId);
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
            Hashtable<EnumTypeCategory, MoneyPlanFactSettings> moneyPlanFactSettingsHT)
    {
        int i = 0;
        if (moneyPlanFactSettingsHT != null)
            for (EnumTypeCategory enumTypeCategory : moneyPlanFactSettingsHT.keySet())
            {
                MoneyPlanFactSettings moneyPlanFactSettings = moneyPlanFactSettingsHT.get(enumTypeCategory);
                editor.putString("MoneyPlanFactSettings." + i + ".EnumTypeCategory",
                        enumTypeCategory.toString());
                editor.putBoolean("MoneyPlanFactSettings." + i + ".ShowPercent",
                        moneyPlanFactSettings.get_showPercent());
                editor.putBoolean("MoneyPlanFactSettings." + i + ".ShowBlankData",
                        moneyPlanFactSettings.get_showBlankData());
                String sPlan;
                if (moneyPlanFactSettings.get_isPlan() == null)
                    sPlan = "null";
                else if (moneyPlanFactSettings.get_isPlan())
                    sPlan = "true";
                else
                    sPlan = "false";
                editor.putString("MoneyPlanFactSettings." + i + ".IsPlan", sPlan);
                editor.putBoolean("MoneyPlanFactSettings." + i + ".ShowExpandData",
                        moneyPlanFactSettings.get_showExpandButton());
                editor.putString("MoneyPlanFactSettings." + i + ".CategoryType",
                        moneyPlanFactSettings.get_categoryType().toString());

                String sTagId;
                if (moneyPlanFactSettings.get_tagId() == null)
                    sTagId = "null";
                else
                    sTagId = moneyPlanFactSettings.get_tagId().toString();
                editor.putString("MoneyPlanFactSettings." + i + ".TagId", sTagId);
                i++;
            }
        editor.putInt("MoneyPlanFactSettings.Count", i);

        return editor;
    }

    public static Hashtable<EnumTypeCategory, MoneyPlanFactSettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<EnumTypeCategory, MoneyPlanFactSettings> ht = new Hashtable<>();

        int count = settings.getInt("MoneyPlanFactSettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            EnumTypeCategory enumTypeCategory =
                    EnumTypeCategory.valueOf(
                    settings.getString("MoneyPlanFactSettings." + i + ".EnumTypeCategory",
                            EnumTypeCategory.Increase.name()));

            EnumCategoryType categoryType;
            try
            {
                categoryType = EnumCategoryType.valueOf(
                        settings.getString("MoneyPlanFactSettings." + i + ".CategoryType", EnumCategoryType.All.toString()));
            }
            catch (Exception e) { categoryType = EnumCategoryType.All; }

            String sPlan;
            Boolean isPlan = false;
            try
            {
                sPlan = settings.getString("MoneyPlanFactSettings." + i + ".IsPlan", "false");
                if (sPlan.equals("null"))
                    isPlan = null;
                else if (sPlan.equals("true"))
                    isPlan = true;
            }
            catch (Exception e) { isPlan = false; }

            String sTagId;
            Long tagId;
            try
            {
                sTagId = settings.getString("MoneyPlanFactSettings." + i + ".TagId", "null");
                if (sTagId.equals("null"))
                    tagId = null;
                else
                    tagId = Long.parseLong(sTagId);
            }
            catch (Exception e) { tagId = null; }

            MoneyPlanFactSettings moneyPlanFactSettings = new MoneyPlanFactSettings(
                    enumTypeCategory,
                    new CustomPeriodClass(CustomPeriodClass.MONTH_ID, ""),
                    settings.getBoolean("MoneyPlanFactSettings." + i + ".ShowPercent", false),
                    settings.getBoolean("MoneyPlanFactSettings." + i + ".ShowBlankData", false),
                    isPlan,
                    null,
                    true,
                    categoryType,
                    tagId);

            ht.put(enumTypeCategory, moneyPlanFactSettings);
        }

        return ht;
    }
    //endregion
}
