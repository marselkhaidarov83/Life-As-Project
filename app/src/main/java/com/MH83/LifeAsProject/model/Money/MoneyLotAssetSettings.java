package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.ETypeCapitalData;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyLotAssetSettings extends CustomMoneyObject
{
    //region Parameters
    private Long _groupPortfolioId;
    public Long get_groupPortfolioId() { return _groupPortfolioId; }
    public void set_groupPortfolioId(Long groupPortfolioId) { _groupPortfolioId = groupPortfolioId; }

    private Long _portfolioId;
    public Long get_portfolioId() { return _portfolioId; }
    public void set_portfolioId(Long portfolioId) { _portfolioId = portfolioId; }

    private EnumKindAsset _kindAsset;
    public EnumKindAsset get_kindAsset() { return _kindAsset; }
    public void set_kindAsset(EnumKindAsset kindAsset) { _kindAsset = kindAsset; }

    private Long _typeAsset;
    public Long get_typeAsset() { return _typeAsset; }
    public void set_typeAsset(Long typeAsset) { _typeAsset = typeAsset; }

    private CustomPeriodClass _customPeriodClass;
    public CustomPeriodClass get_customPeriodClass() { return _customPeriodClass; }
    public void set_customPeriodClass(CustomPeriodClass customPeriodClass)
    {
        _customPeriodClass = customPeriodClass;
    }

    private ETypeCapitalData _typeCapitalData;
    public ETypeCapitalData get_typeCapitalData() { return _typeCapitalData; }
    public void set_typeCapitalData(ETypeCapitalData typeCapitalData) { _typeCapitalData = typeCapitalData; }
    //endregion

    //region Constructors
    public MoneyLotAssetSettings(
            Long groupPortfolioId,
            Long portfolioId,
            EnumKindAsset kindAsset,
            Long typeAsset,
            CustomPeriodClass customPeriodClass,
            ETypeCapitalData typeCapitalData)
    {
        _groupPortfolioId = groupPortfolioId;
        _portfolioId = portfolioId;
        _kindAsset = kindAsset;
        _typeAsset = typeAsset;
        _customPeriodClass = customPeriodClass;
        _typeCapitalData = typeCapitalData;
    }

    public MoneyLotAssetSettings Clone()
    {
        return new MoneyLotAssetSettings(
                _groupPortfolioId,
                _portfolioId,
                _kindAsset,
                _typeAsset,
                _customPeriodClass,
                _typeCapitalData);
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
            MoneyLotAssetSettings moneyAssetSettings)
    {
        editor.putLong("MoneyLotAssetSettings.GroupPortfolioId", moneyAssetSettings.get_groupPortfolioId());
        editor.putLong("MoneyLotAssetSettings.PortfolioId", moneyAssetSettings.get_portfolioId());
        editor.putString("MoneyLotAssetSettings.KindAsset", moneyAssetSettings.get_kindAsset().toString());
        editor.putLong("MoneyLotAssetSettings.TypeAsset", moneyAssetSettings.get_typeAsset());
        editor.putString("MoneyLotAssetSettings.TypeCapitalData", moneyAssetSettings.get_typeCapitalData().toString());

        return editor;
    }

    public static MoneyLotAssetSettings GetFromSharedPreferences(SharedPreferences settings)
    {
        return new MoneyLotAssetSettings(
                settings.getLong("MoneyLotAssetSettings.GroupPortfolioId", 0L),
                settings.getLong("MoneyLotAssetSettings.PortfolioId", 0L),
                EnumKindAsset.valueOf(settings.getString("MoneyLotAssetSettings.KindAsset",
                        EnumKindAsset.Blank.toString())),
                settings.getLong("MoneyLotAssetSettings.TypeAsset", 0L),
                new CustomPeriodClass(3, ""),
                ETypeCapitalData.valueOf(settings.getString("MoneyLotAssetSettings.TypeCapitalData",
                        ETypeCapitalData.Sum.toString())));
    }
    //endregion
}
