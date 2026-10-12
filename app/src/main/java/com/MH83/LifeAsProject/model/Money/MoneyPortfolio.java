package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyPortfolioDataSource;
import com.MH83.LifeAsProject.interfac.IIsParent;
import com.MH83.LifeAsProject.interfac.IgetId;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

import java.util.Objects;

public class MoneyPortfolio extends CustomMoneyObject implements IgetId, IIsParent
{
    //region Parameters
    private Long _id;
    private Long _parent_id;
    private final String _name;
    private final String _comment;
    private int _position;
    private boolean _hasChild = false;
    private final EnumStatus _status;
    private final Long _walletId;
    private final Long _subCategoryId;
    private final int _countLot;
    //endregion

    //region Get/Set Data
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
    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }
    public EnumStatus get_status() { return _status; }
    public Long get_walletId() { return _walletId; }
    public Long get_subCategoryId() { return _subCategoryId; }
    public int get_countLot() { return _countLot; }

    @Override
    public boolean canDelete(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        return !_hasChild && _countLot == 0 &&
                !Objects.equals(globalVar.get_creditPortfolioId(), _id);
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyPortfolioDataSource.GetCount(context) >= moneyPortfolioFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyPortfolio), Toast.LENGTH_LONG).show();

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
    public MoneyPortfolio(Long id,
                          String name,
                          String comment,
                          Long parent_id,
                          int position,
                          EnumStatus status,
                          Long walletId,
                          Long subCategoryId,
                          int countLot)
    {
        _id = id;
        _name = name;
        _comment = comment;
        _parent_id = parent_id;
        _position = position;
        _status = status;
        _walletId = walletId;
        _subCategoryId = subCategoryId;
        _countLot = countLot;
    }

    public MoneyPortfolio Clone()
    {
        return new MoneyPortfolio(_id, _name, _comment, _parent_id, _position, _status,
                _walletId, _subCategoryId, _countLot);
    }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
//        _id = dbHelper.AddMoneyPortfolio(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyPortfolio(this, context);
        TrackCount(dbHelper);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyPortfolio(this, context);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyPortfolio(_id);
    }
    //endregion

    //region Track
    private void TrackCount(DBHelper dbHelper)
    {
        CustomTrack.TrackCountMoneyPortfolio(MoneyPortfolioDataSource.GetCount(dbHelper));
    }
    //endregion
}
