package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAssetDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumCurrency;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Objects;

public class MoneyAsset extends CustomMoneyObject implements IgetId, IIsParent
{
    //region Parameters
    private Long _id;
    private final Long _wallet_id;
    private final Long _credit_id;
    private final String _name;
    private final String _comment;
    private int _position;
    private final EnumStatus _status;
    private final String _ticker;
    private Long _moneyTypeAssetId;
    private final String _moneyTypeAssetName;
    private final EnumKindAsset _kindAsset;
    private final EnumCurrency _currency;
    private boolean _hasChild;
    private final boolean _isParent;
    private final int _lotCount;
    //endregion

    //region Get/Set data
    public String get_name() { return _name; }
    public String get_comment() { return _comment; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    public Long get_wallet_id() { return _wallet_id; }
    public Long get_credit_id() { return _credit_id; }

    @Override
    public Long get_parentId() { return _moneyTypeAssetId; }

    public String get_moneyTypeAssetName() { return _moneyTypeAssetName; }

    @Override
    public boolean isParent() { return _isParent; }

    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }

    public void set_hasChild(boolean hasChild) { _hasChild = hasChild; }
    public EnumStatus get_status() { return _status; }

    public Long get_moneyTypeAssetId() { return  _moneyTypeAssetId; }
    public void set_moneyTypeAssetId(Long moneyTypeAssetId) { _moneyTypeAssetId = moneyTypeAssetId; }
    public EnumKindAsset get_kindAsset() { return _kindAsset; }
    public String get_ticker() { return _ticker; }
    public EnumCurrency get_currency() { return _currency; }
    public int get_lotCount() { return _lotCount; }

    @Override
    public boolean canDelete(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        return !Objects.equals(_moneyTypeAssetId, globalVar.get_typeAsset4WalletId()) &&
                !Objects.equals(_moneyTypeAssetId, globalVar.get_typeAsset4Creditd()) &&
                _lotCount == 0;
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyAssetDataSource.GetCount(context) >= moneyAssetFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyAsset), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }
    //endregion

    //region Constructors
    public MoneyAsset(Long id,
                      Long wallet_id,
                      Long credit_id,
                      String name,
                      String comment,
                      int position,
                      EnumStatus status,
                      Long moneyTypeAssetId,
                      String moneyTypeAssetName,
                      EnumKindAsset kindAsset,
                      String ticker,
                      EnumCurrency currency,
                      boolean isParent,
                      int lotCount)
    {
        _id = id;
        _wallet_id = wallet_id;
        _credit_id = credit_id;
        _name = name;
        _comment = comment;
        _position = position;
        _status = status;
        _moneyTypeAssetId = moneyTypeAssetId;
        _moneyTypeAssetName = moneyTypeAssetName;
        _kindAsset = kindAsset;
        _ticker = ticker;
        _currency = currency;
        _isParent = isParent;
        _lotCount = lotCount;
    }

    public MoneyAsset Clone()
    {
        return new MoneyAsset(_id, _wallet_id, _credit_id,
                _name, _comment, _position, _status,
                _moneyTypeAssetId,
                _moneyTypeAssetName,
                _kindAsset, _ticker, _currency, _isParent, _lotCount);
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyAsset(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyAsset(this);
        TrackCount(dbHelper);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyAsset(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyAsset(_id);
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper)
    {
        CustomTrack.TrackCountMoneyAsset(MoneyAssetDataSource.GetCount(dbHelper));
    }

    //endregion
}
