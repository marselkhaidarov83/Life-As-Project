package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.TagDataSource;
import com.MH83.LifeAsProject.data.Custom.TaggedDataSource;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;

public class Tagged extends CustomMoneyObject
{
    private Long _id;
    private Long _externalId;
    private Tag _tag;

    @Override
    public String getSValue()
    {
        if (_tag != null)
            return _tag.get_name();
        return "";
    }
    public String get_comment()
    {
        if (_tag != null)
            return _tag.get_comment();

        return "";
    }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    public EnumTagTypeData get_tagTypeData()
    {
        if (_tag != null)
            return _tag.get_tagTypeData();

        return null;
    }

    public Tag get_tag() { return _tag; }

    public Long get_externalId() { return _externalId; }
    public void set_externalId(Long externalId) { _externalId = externalId; }

    @Override
    public boolean canDelete(Context context)
    {
        return true;
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (TaggedDataSource.GetCount(context) >= taggedFreeCount)
            {
                Toast.makeText(context,
                        context.getString(R.string.ErrorTooManyTag), Toast.LENGTH_LONG).show();

                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public Tagged(Long id, Long externalId, Tag tag)
    {
        _id = id;
        _tag = tag;
        _externalId = externalId;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
//        _id = dbHelper.AddTagged(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        _id = dbHelper.AddTag(this);
//        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.UpdateTag(this);
        return true;
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return true;
//        return dbHelper.DeleteTag(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
//        CustomTrack.TrackCountTag(TagDataSource.GetCount(dbHelper));
    }
}
