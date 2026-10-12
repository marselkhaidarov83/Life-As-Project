package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumDataOrSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyAccountingSum extends CustomMoneyObject implements IMASCategory, Parcelable
{
    private final Long _categoryId;
    private final String _categoryName;
    private Long _sumLong;
    private Long _budgetLong;
    private final Long _icon;
    private final EnumTypeCategory _typeCategory;
    private final EnumDataOrSetting _dataOrSetting;

    public Long getCategoryId() { return _categoryId; }
    public String getCategoryName() { return _categoryName; }
    public Long get_sumLong() { return _sumLong; }
    public void set_sumLong(Long sumLong) { _sumLong = sumLong; }
    public Long get_budgetLong() { return _budgetLong; }
    public void set_budgetLong(Long budgetLong) { _budgetLong = budgetLong; }
    public Long get_icon() { return _icon; }
    public EnumTypeCategory getTypeCategory() { return _typeCategory; }
    public EnumDataOrSetting get_dataOrSetting() { return _dataOrSetting; }
    public boolean canDelete(Context context)
    {
        return true;
    }

    public MoneyAccountingSum(Long categoryId, String categoryName, EnumTypeCategory typeCategory,
                              Long sumLong,
                              Long budgetLong,
                              Long icon, EnumDataOrSetting dataOrSetting)
    {
        _categoryId = categoryId;
        _categoryName = categoryName;
        _typeCategory = typeCategory;
//        _sum = sum;
        _sumLong = sumLong;
//        _budget = budget;
        _budgetLong = budgetLong;
        _icon = icon;
        _dataOrSetting = dataOrSetting;
    }

    @Override
    public String getSValue() {
        return null;
    }

    @Override
    public void set_id(Long id) { }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception { return false; }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i)
    {
        parcel.writeLong(_categoryId);
        parcel.writeString(_categoryName);
        if (_sumLong == null)
        {
            parcel.writeByte((byte) 0);
        }
        else
        {
            parcel.writeByte((byte) 1);
            parcel.writeLong(_sumLong);
        }
        parcel.writeLong(_icon);
        parcel.writeString(_typeCategory.name());
        parcel.writeString(_dataOrSetting.name());
    }

    protected MoneyAccountingSum(Parcel in)
    {
        _categoryId = in.readLong();
        _categoryName = in.readString();
        if (in.readByte() == 0)
            _sumLong = null;
        else
            _sumLong = in.readLong();
        _icon = in.readLong();
        _typeCategory = EnumTypeCategory.valueOf(in.readString());
        _dataOrSetting = EnumDataOrSetting.valueOf(in.readString());
    }

    public static final Creator<MoneyAccountingSum> CREATOR = new Creator<MoneyAccountingSum>()
    {
        @Override
        public MoneyAccountingSum createFromParcel(Parcel in) {
            return new MoneyAccountingSum(in);
        }

        @Override
        public MoneyAccountingSum[] newArray(int size) {
            return new MoneyAccountingSum[size];
        }
    };
    //endregion
}
