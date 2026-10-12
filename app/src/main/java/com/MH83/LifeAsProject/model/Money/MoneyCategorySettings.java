package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.model.Interface.IGetCategoryList;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MoneyCategorySettings extends CustomMoneyObject implements IGetCategoryList
{
    //region Parameters
    private EnumTypeCategory _typeCategory;
    public void set_typeCategory(EnumTypeCategory typeCategory) { _typeCategory = typeCategory; }

    private boolean _showBlankData;
    public boolean get_showBlankData() { return _showBlankData; }
    public void set_showBlankData(boolean b) { _showBlankData = b; }

    private List<Long> _expandedList;
    public List<Long> get_expandedList() { return _expandedList; }
    public void set_expandedList(List<Long> list) { _expandedList = list; }

    private final boolean _showArchive;
    public boolean get_showArchive() { return _showArchive; }

    private EnumCategoryType _categoryType;
    public void set_typeCategory(EnumCategoryType categoryType) { _categoryType = categoryType; }
    public EnumCategoryType get_categoryType() { return _categoryType; }

    private boolean _showExpandButton;
    public boolean get_showExpandButton() { return _showExpandButton; }
    public void set_showExpandButton(boolean showExpandButton) { _showExpandButton = showExpandButton; }
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
    public MoneyCategorySettings(EnumTypeCategory typeCategory,
                                 boolean showBlankData,
                                 boolean showArchive,
                                 boolean showExpandButton,
                                 EnumCategoryType categoryType,
                                 List<Long> expandedList)
    {
        _typeCategory = typeCategory;
        _showBlankData = showBlankData;
        _expandedList = expandedList;
        _categoryType = categoryType;
        _showArchive = showArchive;
        _showExpandButton = showExpandButton;
    }

    public MoneyCategorySettings Clone()
    {
        return new MoneyCategorySettings(_typeCategory, _showBlankData, _showArchive,
                _showExpandButton, _categoryType, _expandedList);
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
            Hashtable<EnumTypeCategory, MoneyCategorySettings> moneyCategorySettingsHT)
    {
        int i = 0;
        if (moneyCategorySettingsHT != null)
            for (EnumTypeCategory typeCategory : moneyCategorySettingsHT.keySet())
            {
                MoneyCategorySettings moneyCategorySettings = moneyCategorySettingsHT.get(typeCategory);
                editor.putString("MoneyCategorySettings." + i + ".TypeCategory",
                        typeCategory.toString());
                editor.putBoolean("MoneyCategorySettings." + i + ".ShowBlankData",
                        moneyCategorySettings.get_showBlankData());
                editor.putBoolean("MoneyCategorySettings." + i + ".ShowArchive",
                        moneyCategorySettings.get_showArchive());
                editor.putBoolean("MoneyCategorySettings." + i + ".ShowExpandData",
                        moneyCategorySettings.get_showExpandButton());
                editor.putString("MoneyCategorySettings." + i + ".CategoryType",
                        moneyCategorySettings.get_categoryType().toString());

                i++;
            }
        editor.putInt("MoneyCategorySettings.Count", i);

        return editor;
    }

    public static Hashtable<EnumTypeCategory, MoneyCategorySettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<EnumTypeCategory, MoneyCategorySettings> ht = new Hashtable<>();

        int count = settings.getInt("MoneyCategorySettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            EnumCategoryType categoryType;
            try
            {
                categoryType = EnumCategoryType.valueOf(
                        settings.getString("MoneyCategorySettings." + i + ".CategoryType", EnumCategoryType.All.toString()));
            }
            catch (Exception e) { categoryType = EnumCategoryType.All; }

            MoneyCategorySettings moneyIncomingAndCostSettings = new MoneyCategorySettings(
                    EnumTypeCategory.valueOf(
                            settings.getString("MoneyCategorySettings." + i + ".TypeCategory",
                                    EnumTypeCategory.Increase.name())),
                    settings.getBoolean("MoneyCategorySettings." + i + ".ShowBlankData", false),
                    settings.getBoolean("MoneyCategorySettings." + i + ".ShowArchive", true),
                    true,
                    categoryType,
                    null);

            ht.put(EnumTypeCategory.valueOf(
                            settings.getString("MoneyCategorySettings." + i + ".TypeCategory",
                                    EnumTypeCategory.Increase.name())),
                    moneyIncomingAndCostSettings);
        }

        return ht;
    }
    //endregion
}
