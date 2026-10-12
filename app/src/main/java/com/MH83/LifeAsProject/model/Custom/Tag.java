package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.TagDataSource;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;

public class Tag extends CustomMoneyObject
{
    private Long _id;
    private final String _name;
    private final String _comment;
    private final EnumTagTypeData _tagTypeData;
    private boolean _archive;

    public String get_name() { return _name; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }
    public String get_comment() { return _comment; }

    public boolean get_archive() { return _archive; }
    public int get_archiveI() { return _archive ? 1 : 0; }
    public void inverse_archive() { _archive = !_archive; }

    private Long _countExternal;
    public Long get_countExternal() { return _countExternal; }
    public void set_countExternal(Long countExternal) { _countExternal = countExternal; }
    public EnumTagTypeData get_tagTypeData() { return _tagTypeData; }

    @Override
    public boolean canDelete(Context context)
    {
        if (_countExternal == null)
            return true;
        return _countExternal == 0L;
    }

    public static boolean canAdd(Context context, GlobalClass globalVariable)
    {
        if (globalVariable.get_hasPaymentOrTestSubscription())
            return true;
        else if (globalVariable.get_hasUnpaidSubscription())
        {
            if (TagDataSource.GetCount(context) >= tagFreeCount)
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

    public Tag(Long id, String name, String comment, EnumTagTypeData tagTypeData,
               Long countExternal, boolean archive)
    {
        _id = id;
        _name = name;
        _comment = comment;
        _tagTypeData = tagTypeData;
        _countExternal = countExternal;
        _archive = archive;
    }

    public Tag CloneFull()
    {
        return new Tag(_id, _name, _comment, _tagTypeData, _countExternal, _archive);
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddTag(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddTag(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.UpdateTag(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return dbHelper.DeleteTag(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountTag(TagDataSource.GetCount(dbHelper));
    }
}
