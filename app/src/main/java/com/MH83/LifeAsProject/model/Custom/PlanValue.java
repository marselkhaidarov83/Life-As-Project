package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Custom.PlanValueDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumPlanValueType;

public class PlanValue extends CustomMoneyObject
{
    private Long _id;
    private Long _target_id;
    private final Long _param_id;
    private final EnumPlanValueType _type;
    private final double _coeff;
    private final double _value;
    private final Long _src_planValue_id1;
    private final Long _src_planValue_id2;
    private final String _name;
    private final String _measure;

    public String get_name() { return _name; }
    public Long get_target_id() { return _target_id; }
    public void set_target_id(Long target_id) { _target_id = target_id; }
    public Long get_param_id() { return _param_id; }
    public Long get_src_planValue_id1() { return _src_planValue_id1; }
    public Long get_src_planValue_id2() { return _src_planValue_id2; }
    public EnumPlanValueType get_type() { return _type; }
    public String get_typeName(Context context)
    {
        return get_typeNameC(context, _type);
    }
    public static String get_typeNameC(Context context, EnumPlanValueType type)
    {
        switch (type)
        {
            case Koef:
                return context.getResources().getString(R.string.Koef);
            case InverseKoef:
                return context.getResources().getString(R.string.InverseKoef);
            case Value:
                return context.getResources().getString(R.string.Value);
            case Sum:
                return context.getResources().getString(R.string.Sum);
            case Diff:
                return context.getResources().getString(R.string.Diff);
            case Multiplication:
                return context.getResources().getString(R.string.Multiplication);
            case Dividing:
                return context.getResources().getString(R.string.Dividing);
            default:
                return "????????????????";
        }
    }
    public double get_coeff() { return _coeff; }
    public double get_value() { return _value; }
    public String get_measure() { return _measure; }

    @Override
    public String getSValue() { return _name; }

    @Override
    public void set_id(Long id) { _id = id; }
    public Long get_id() { return _id; }

    @Override
    public boolean canDelete(Context context) { return true; }

    public static boolean canAdd(Context context, boolean hasSubscription)
    {
        if (hasSubscription)
            return true;

        if (PlanValueDataSource.GetCount(context) >= planValueFreeCount)
        {
            Toast.makeText(context,
                    context.getString(R.string.ErrorTooManyPlanValue), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public PlanValue(Long id, Long target_id, Long param_id, EnumPlanValueType type,
                     double coeff, double value, Long src_planValue_id1,
                     Long src_planValue_id2, String name, String measure)
    {
        _id = id;
        _target_id = target_id;
        _param_id = param_id;
        _type = type;
        _coeff = coeff;
        _value = value;
        _src_planValue_id1 = src_planValue_id1;
        _src_planValue_id2 = src_planValue_id2;
        _name = name;
        _measure = measure;
    }

    public void AddToDatabaseWOTrack(DBHelper dbHelper) throws Exception
    {
        _id = dbHelper.AddPlanValue(this);
    }

    @Override
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        _id = dbHelper.AddPlanValue(this);
//        TrackCount(dbHelper, context);
    }

    public void AddToDatabaseFromTarget(DBHelper dbHelper, Context context) throws Exception
    {
        _id = dbHelper.AddPlanValue(this);
        TrackCount(dbHelper, context);
    }

    @Override
    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.UpdatePlanValue(this);
        return true;
    }

    public boolean UpdateInDatabaseFromTarget(DBHelper dbHelper) throws Exception
    {
        return dbHelper.UpdatePlanValue(this);
    }

    @Override
    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
//        return dbHelper.DeletePlanValue(_id);
        return true;
    }

    public boolean DeleteFromDatabaseFromTarget(DBHelper dbHelper) throws Exception
    {
        return dbHelper.DeletePlanValue(_id);
    }

    private void TrackCount(DBHelper dbHelper, Context context)
    {
        CustomTrack.TrackCountPlanValue(PlanValueDataSource.GetCount(dbHelper));
    }
}
