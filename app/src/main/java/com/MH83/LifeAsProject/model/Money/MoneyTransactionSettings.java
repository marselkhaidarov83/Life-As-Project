package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import java.util.Hashtable;

public class MoneyTransactionSettings extends CustomMoneyObject
{
    //region Parameters
    private final EnumTransactionType _enumTransactionType;
    public EnumTransactionType get_enumTransactionType() { return _enumTransactionType; }

    private EnumTypeCategory _typeCategory;
    public EnumTypeCategory get_typeCategory() { return _typeCategory; }
    public void set_typeCategory(EnumTypeCategory typeCategory) { _typeCategory = typeCategory; }

    private Boolean _isPlan;
    public Boolean get_isPlan() { return _isPlan; }
    public void set_isPlan(Boolean isPlan) { _isPlan = isPlan; }

    private Long _categoryId;
    public Long get_categoryId() { return _categoryId; }
    public void set_categoryId(Long categoryId) { this._categoryId = categoryId; }

    private Long _subCategoryId;
    public Long get_subCategoryId() { return _subCategoryId; }
    public void set_subCategoryId(Long subCategoryId) { _subCategoryId = subCategoryId; }

    private Long _tagId;
    public Long get_tagId() { return _tagId; }
    public void set_tagId(Long tagId) { _tagId = tagId; }
    //endregion

    //region Constructors
    public MoneyTransactionSettings(EnumTransactionType enumTransactionType,
                                    EnumTypeCategory typeCategory,
                                    Boolean isPlan,
                                    Long categoryId,
                                    Long subCategoryId,
                                    Long tagId)
    {
        _enumTransactionType = enumTransactionType;
        _typeCategory = typeCategory;
        _isPlan = isPlan;
        _categoryId = categoryId;
        _subCategoryId = subCategoryId;
        _tagId = tagId;
    }

    public MoneyTransactionSettings Clone()
    {
        return new MoneyTransactionSettings(
                _enumTransactionType,
                _typeCategory,
                _isPlan,
                _categoryId,
                _subCategoryId,
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
            Hashtable<EnumTransactionType, MoneyTransactionSettings> moneyTransactionSettingsHT)
    {
        int i = 0;
        if (moneyTransactionSettingsHT != null)
            for (EnumTransactionType enumTransactionType : moneyTransactionSettingsHT.keySet())
            {
                MoneyTransactionSettings moneyTransactionSettings =
                        moneyTransactionSettingsHT.get(enumTransactionType);
                editor.putString("MoneyTransactionSettings." + i + ".EnumTransactionType",
                        enumTransactionType.toString());

                String sTypeCategory;
                if (moneyTransactionSettings.get_typeCategory() == null)
                    sTypeCategory = "null";
                else
                    sTypeCategory = moneyTransactionSettings.get_typeCategory().toString();
                editor.putString("MoneyTransactionSettings." + i + ".TypeCategory", sTypeCategory);

                String sPlan;
                if (moneyTransactionSettings.get_isPlan() == null)
                    sPlan = "null";
                else if (moneyTransactionSettings.get_isPlan())
                    sPlan = "true";
                else
                    sPlan = "false";
                editor.putString("MoneyTransactionSettings." + i + ".IsPlan", sPlan);

                String sCategoryId;
                if (moneyTransactionSettings.get_categoryId() == null)
                    sCategoryId = "null";
                else
                    sCategoryId = moneyTransactionSettings.get_categoryId().toString();
                editor.putString("MoneyTransactionSettings." + i + ".CategoryId", sCategoryId);

                String sSubCategoryId;
                if (moneyTransactionSettings.get_subCategoryId() == null)
                    sSubCategoryId = "null";
                else
                    sSubCategoryId = moneyTransactionSettings.get_subCategoryId().toString();
                editor.putString("MoneyTransactionSettings." + i + ".SubCategoryId", sSubCategoryId);

                String sTagId;
                if (moneyTransactionSettings.get_tagId() == null)
                    sTagId = "null";
                else
                    sTagId = moneyTransactionSettings.get_tagId().toString();
                editor.putString("MoneyTransactionSettings." + i + ".TagId", sTagId);

                i++;
            }
        editor.putInt("MoneyTransactionSettings.Count", i);

        return editor;
    }

    public static Hashtable<EnumTransactionType, MoneyTransactionSettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<EnumTransactionType, MoneyTransactionSettings> ht = new Hashtable<>();

        int count = settings.getInt("MoneyTransactionSettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            EnumTransactionType enumTransactionType =
                    EnumTransactionType.valueOf(
                    settings.getString("MoneyTransactionSettings." + i + ".EnumTransactionType",
                            EnumTransactionType.PNL.name()));

            EnumTypeCategory typeCategory;
            try
            {
                typeCategory = EnumTypeCategory.valueOf(
                        settings.getString("MoneyTransactionSettings." + i + ".EnumTypeCategory", EnumTypeCategory.Increase.toString()));
            }
            catch (Exception e) { typeCategory = EnumTypeCategory.Increase; }

            String sPlan;
            Boolean isPlan = false;
            try
            {
                sPlan = settings.getString("MoneyTransactionSettings." + i + ".IsPlan", "false");
                if (sPlan.equals("null"))
                    isPlan = null;
                else if (sPlan.equals("true"))
                    isPlan = true;
            }
            catch (Exception e) { isPlan = false; }

            String sCategoryId;
            Long categoryId;
            try
            {
                sCategoryId = settings.getString("MoneyTransactionSettings." + i + ".CategoryId", "null");
                if (sCategoryId.equals("null"))
                    categoryId = null;
                else
                    categoryId = Long.parseLong(sCategoryId);
            }
            catch (Exception e) { categoryId = null; }

            String sSubCategory;
            Long subCategoryId;
            try
            {
                sSubCategory = settings.getString("MoneyTransactionSettings." + i + ".SubCategoryId", "null");
                if (sSubCategory.equals("null"))
                    subCategoryId = null;
                else
                    subCategoryId = Long.parseLong(sSubCategory);
            }
            catch (Exception e) { subCategoryId = null; }

            String sTag;
            Long tagId;
            try
            {
                sTag = settings.getString("MoneyTransactionSettings." + i + ".TagId", "null");
                if (sTag.equals("null"))
                    tagId = null;
                else
                    tagId = Long.parseLong(sTag);
            }
            catch (Exception e) { tagId = null; }

            MoneyTransactionSettings moneyTransactionSettings = new MoneyTransactionSettings(
                    enumTransactionType,
                    typeCategory,
                    isPlan,
                    categoryId,
                    subCategoryId,
                    tagId);

            ht.put(enumTransactionType, moneyTransactionSettings);
        }

        return ht;
    }
    //endregion
}
