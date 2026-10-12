package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.IMASCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyCategory extends CustomMoneyObject
        implements IgetId, IIsParent, IMASCategory
{
    private Long _id;
    private Long _parentId;
    private int _position;
    private final String _name;
    private Long _sumLong;
    private final EnumTypeCategory _typeCategory;
    private boolean _hasChild;
    private final boolean _existIncoming;
    private final boolean _existPNL;
    private final boolean _sumToBalance;
    private final Long _creditId;
    private final Long _targetId;
    private final EnumCategoryType _type;
//    private final EnumCategoryStatus _status;
    private final Long _icon;
    private boolean _archive;

    public Long get_id() { return _id; }
    @Override
    public Long getCategoryId() { return get_id();}
    public void set_id(Long id) { _id = id; }
    public Long get_parentId() { return _parentId; }
    public void set_parentId(Long parentId) { _parentId = parentId; }
    public boolean hasParent() { return _parentId != 0; }
    public boolean isChild() { return _parentId != 0; }
    public boolean isParent() { return _parentId == 0; }
    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }
    public void set_hasChild(boolean hasChild) { _hasChild = hasChild; }
    public boolean get_hasChild() { return _hasChild; }
    public boolean getExistIncoming() { return _existIncoming; }
    public boolean getExistPNL() { return _existPNL; }
    public String get_name() { return _name; }
    @Override
    public String getCategoryName() { return get_name();}
    public Long get_sumLong() { return _sumLong; }
    public void changeSumLong(Long delta) { _sumLong += delta; }
    public EnumTypeCategory getTypeCategory() { return _typeCategory; }
    public int get_sumToBalanceI() { return _sumToBalance? 1 : 0; }
    public Long get_creditId() { return _creditId; }
    public Long get_targetId() { return _targetId; }
    public EnumCategoryType get_type() { return _type; }
//    public EnumCategoryStatus get_status() { return _status; }
    public Long get_icon() { return _icon; }

    @Override
    public String getSValue() { return _name; }
    public String getImage() { return ""; }
    public boolean canDelete(Context context)
    {
        return !_hasChild && !_existIncoming && !_existPNL;
    }

    public boolean get_archive() { return _archive; }
    public int get_archiveI() { return _archive ? 1 : 0; }
    public void inverse_archive() { _archive = !_archive; }


    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyCategoryDataSource.GetCount(context) >= moneyCategoryFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyCategory), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public MoneyCategory(Long id)
    {
        _id = id;
        _parentId = null;
        _position = 0;
        _name = "";
        _sumLong = null;
        _typeCategory = null;
        _hasChild = false;
        _existIncoming = false;
        _existPNL = false;
        _sumToBalance = false;
        _targetId = null;
        _type = null;
//        _status = null;
        _creditId = null;
        _icon = null;
        _archive = false;
    }

    public MoneyCategory(Long id, Long parentId, int position, String name,
                         Long sumLong,
                         EnumTypeCategory typeCategory, boolean hasChild, boolean existIncoming,
                         boolean existPNL, boolean isSumToBalance, Long targetId,
                         EnumCategoryType type,
                         Long creditId,
                         boolean archive,
                         Long icon)
    {
        _id = id;
        _parentId = parentId;
        _position = position;
        _name = name;
        _sumLong = sumLong;
        _typeCategory = typeCategory;
        _hasChild = hasChild;
        _existIncoming = existIncoming;
        _existPNL = existPNL;
        _sumToBalance = isSumToBalance;
        _targetId = targetId;
        _type = type;
        _creditId = creditId;
        _archive = archive;
        _icon = icon;
    }

    public MoneyCategory Clone()
    {
        return new MoneyCategory(-1L, _parentId, 0, _name, 0L, _typeCategory,
                false, false, false, _sumToBalance, _targetId,
                _type, _creditId, _archive, _icon);
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyCategory(this);
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyCategory(this);
        TrackCount(dbHelper, context);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyCategory(this);
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyCategory(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyCategory(MoneyCategoryDataSource.GetCount(dbHelper));
    }
}
