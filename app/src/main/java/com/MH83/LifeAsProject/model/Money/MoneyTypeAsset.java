package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumCurrency;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.lang.reflect.GenericArrayType;
import java.util.Calendar;

public class MoneyTypeAsset extends CustomMoneyObject implements IgetId, IIsParent
{
    //region Parameters
    private Long _id;
    private Long _parent_id;
    private final String _name;
    private final String _comment;
    private int _position;
    private boolean _hasChild = false;
    private final EnumStatus _status;
    private final int _countAsset;
    //endregion

    //region Get/Set data
    public String get_name() { return _name; }
    public String get_comment() { return _comment; }
    public boolean get_hasChild() { return _hasChild; }
    public void set_hasChild(boolean hasChild) { _hasChild = hasChild; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public void set_parentId(Long parentId) { _parent_id = parentId; }
    public boolean hasParent() { return _parent_id != 0; }
    public boolean isChild() { return _parent_id != 0; }

    public int get_countAsset() { return _countAsset; }

    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }

    public EnumStatus get_status() { return _status; }

    @Override
    public boolean canDelete(Context context)
    {
        return !_hasChild && _countAsset == 0;
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyTypeAssetDataSource.GetCount(context) >= moneyTypeAssetFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyTypeAsset), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }
    //endregion

    //region IgetId
    public Long get_id() { return _id; }
    public Long get_parentId() { return _parent_id; }
    //endregion

    //region IIsParent
    public boolean isParent() { return _parent_id == 0; }
    //endregion

    //region Constructors
    public MoneyTypeAsset(Long id,
                          String name,
                          String comment,
                          Long parent_id,
                          int position,
                          EnumStatus status,
                          int countAsset)
    {
        _id = id;
        _name = name;
        _comment = comment;
        _parent_id = parent_id;
        _position = position;
        _status = status;
        _countAsset = countAsset;
    }

    public MoneyTypeAsset Clone()
    {
        return new MoneyTypeAsset(_id, _name, _comment, _parent_id, _position, _status, _countAsset);
    }

    public MoneyLotAsset GetMoneyLotAsset()
    {
        return new MoneyLotAsset(_id,
                0L,
                "",
                0L,
                "",
                0L,
                null,
                _name,
                _comment,
                _position,
                1,
                Calendar.getInstance(),
                Calendar.getInstance(),
                EnumStatus.Started,
                MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset,
                null,
                null);
    }
    //endregion

    //region Get MoneyAsset
    public MoneyAsset GetMoneyAsset()
    {
        return new MoneyAsset(_id, 0L, 0L,
                _name, _comment, _position, _status,
                0L,
                "",
                EnumKindAsset.Conservative, "", EnumCurrency.RUB, true,
                0);
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyTypeAsset(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyTypeAsset(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyTypeAsset(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyTypeAsset(_id);
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyTypeAsset(MoneyTypeAssetDataSource.GetCount(dbHelper));
    }
    //endregion
}
