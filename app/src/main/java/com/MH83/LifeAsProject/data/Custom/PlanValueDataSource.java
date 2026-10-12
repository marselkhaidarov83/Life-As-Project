package com.MH83.LifeAsProject.data.Custom;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumPlanValueType;
import com.MH83.LifeAsProject.model.Custom.PlanValue;

import java.util.ArrayList;
import java.util.List;

public class PlanValueDataSource
{
    public static List<PlanValue> LoadData(Context context, int targetId)
    {
        ArrayList<PlanValue> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            Cursor cursor = dbHelper.QueryPlanValue(targetId);

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_ID);
                int targetIdIndex = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_TARGET_ID);
                int paramIdIndex = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_PARAM_ID);
                int typeIndex = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_TYPE);
                int coeffIndex = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_COEFFICIENT);
                int valueIndex = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_VALUE);
                int srcPV1Index = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_PV_SRC1);
                int srcPV2Index = cursor.getColumnIndex(DBHelper.KEY_PLANVALUE_PV_SRC2);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_PARAM_NAME);
                int measureIndex = cursor.getColumnIndex(DBHelper.KEY_PARAM_MEASURE);

                do
                {
                    data.add(new PlanValue(cursor.getLong(idIndex),
                            cursor.getLong(targetIdIndex),
                            cursor.getLong(paramIdIndex),
                            EnumPlanValueType.valueOf(cursor.getString(typeIndex)),
                            cursor.getDouble(coeffIndex),
                            cursor.getDouble(valueIndex),
                            cursor.getLong(srcPV1Index),
                            cursor.getLong(srcPV2Index),
                            cursor.getString(nameIndex),
                            cursor.getString(measureIndex)));
                }
                while (cursor.moveToNext());
            }

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountPlanValue();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountPlanValue();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
