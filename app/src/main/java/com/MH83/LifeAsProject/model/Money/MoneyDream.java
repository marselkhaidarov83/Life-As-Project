package com.MH83.LifeAsProject.model.Money;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyDreamDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class MoneyDream extends CustomMoneyObject
{
    private Long _id;
    private int _position;
    private final String _name;
    private String _imagePath;
    private final String _imageUrl;
    private final boolean _existAnyTarget;
    private int _countTarget;
    private EnumStatus _status;
    private boolean _archive;
    private int _descPosition;

    public int get_descPosition() { return _descPosition; }
    public void set_descPosition(int descPosition) { _descPosition = descPosition; }

    public Long get_id() { return _id; }

    @Override
    public String getSValue() { return _name;}

    public boolean is_archive() { return _archive; }
    public int is_archiveI() { return _archive ? 1 : 0; }

    public void set_id(Long id) { _id = id; }
    public int get_position() { return _position; }
    public void set_position(int position) { _position = position; }
    public String get_name() { return _name; }
    public String get_imagePath() { return _imagePath; }
    public void set_imagePath(String imagePath) { _imagePath = imagePath; }
    public String get_imageUrl() { return _imageUrl; }
    public boolean getExistAnyTarget() { return _existAnyTarget; }
    public boolean canDelete(Context context)
    {
        return !_existAnyTarget;
    }
    public int get_countTarget() { return _countTarget; }
    public void set_countTarget(int countTarget) { _countTarget = countTarget; }
    public String getTextStatus()
    {
        if (_countTarget == 0)
            return "";

        return String.valueOf(_countTarget);
    }
    public EnumStatus getStatus() { return _status; }
    public int getStatusColor()
    {
        switch (_status)
        {
            case Blank:
                return R.color.white;
            case Started:
                return R.color.orange;
            case Finished:
                return R.color.green;
            default:
                return R.color.lightGrey;
        }
    }
    public void setStatus(EnumStatus status) { _status = status; }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (MoneyDreamDataSource.GetCount(context) >= moneyDreamFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyDream), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public MoneyDream(Long id, int position, String name, String imagePath, String imageUrl,
                      boolean existAnyTarget, int countTarget, EnumStatus status,
                      boolean archive)
    {
        _id = id;
        _position = position;
        _descPosition = position;
        _name = name;
        _imagePath = imagePath;
        _imageUrl = imageUrl;
        _existAnyTarget = existAnyTarget;
        _countTarget = countTarget;
        _status = status;
        _archive = archive;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddMoneyDream(this);
    }

    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddMoneyDream(this);
        TrackCount(dbHelper, context);
    }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateMoneyDream(this);
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteMoneyDream(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountMoneyDream(MoneyDreamDataSource.GetCount(dbHelper));
    }
}
