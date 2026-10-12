package com.MH83.LifeAsProject.data.Money;

import android.content.Context;
import android.database.Cursor;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.model.Money.MoneyDreamSettings;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MoneyDreamDataSource
{
    public static List<MoneyDream> LoadDataCustom(Context context, Long dreamId,
                                                  MoneyDreamSettings moneyDreamSettings)
    {
        class _local
        {
            public final EnumStatus status;
            public final int count;

            public _local(EnumStatus status, int count)
            {
                this.status = status;
                this.count = count;
            }
        }

        ArrayList<MoneyDream> data = new ArrayList<>();

        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            GlobalClass gVar = (GlobalClass) context.getApplicationContext();

            Cursor cursor = dbHelper.QueryMoneyDream(dreamId,
                                                     moneyDreamSettings,
                                                     gVar.get_hasUnpaidSubscription());

            if (cursor.moveToFirst())
            {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_DREAM_ID);
                int positionIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_DREAM_POSITION);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_DREAM_NAME);
                int imagePathIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_DREAM_IMAGE_PATH);
                int imageUrlIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_DREAM_IMAGE_URL);
                int countTargetIndex = cursor.getColumnIndex(DBHelper.key_MONEY_DREAM_TARGET_COUNT);
                int archiveIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_DREAM_ARCHIVE);

                do
                {
                    data.add(new MoneyDream(cursor.getLong(idIndex), cursor.getInt(positionIndex),
                            cursor.getString(nameIndex), cursor.getString(imagePathIndex),
                            cursor.getString(imageUrlIndex), cursor.getInt(countTargetIndex) > 0,
                            0, null,
                            cursor.getInt(archiveIndex) == 1));
                }
                while (cursor.moveToNext());
            }

            Hashtable<Long, List<_local>> hTable = new Hashtable<>();
            cursor = dbHelper.QueryMoneyTarget4Status(null);

            if (cursor.moveToFirst())
            {
                int dreamIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_DREAM_ID);
                int statusIndex = cursor.getColumnIndex(DBHelper.KEY_MONEY_TARGET_STATUS);
                int countIndex = cursor.getColumnIndex(DBHelper.key_MONEY_TARGET_COUNT);

                do
                {
                    List<_local> local = hTable.get(cursor.getLong(dreamIndex));
                    if (local == null)
                    {
                        local = new ArrayList<>();
                        hTable.put(cursor.getLong(dreamIndex), local);
                    }

                    local.add(new _local(EnumStatus.valueOf(cursor.getString(statusIndex)),
                            cursor.getInt(countIndex)));
                }
                while (cursor.moveToNext());
            }

            for (MoneyDream moneyDream: data)
            {
                List<_local> list = hTable.get(moneyDream.get_id());
                if (list != null)
                {
                    for (_local local: list)
                    {
                        moneyDream.set_countTarget(moneyDream.get_countTarget() + local.count);
                        if (local.status == EnumStatus.Blank)
                        {
                            if (moneyDream.getStatus() != EnumStatus.Started)
                                moneyDream.setStatus(EnumStatus.Blank);
                        }
                        else if (local.status == EnumStatus.Started)
                            moneyDream.setStatus(EnumStatus.Started);
                        else if (local.status == EnumStatus.Finished)
                        {
                            if (moneyDream.getStatus() == null)
                                moneyDream.setStatus(EnumStatus.Finished);
                        }
                    }
                }
            }

            for (MoneyDream moneyDream: data)
                if (moneyDream.getStatus() == null)
                    moneyDream.setStatus(EnumStatus.Blank);

            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<MoneyDream> LoadData(Context context,
                                            MoneyDreamSettings moneyDreamSettings)
    {
        return LoadDataCustom(context, null, moneyDreamSettings);
    }

    public static MoneyDream GetOneMoneyDream(Context context, Long dreamId)
    {
        List<MoneyDream> list = LoadDataCustom(context, dreamId, null);
        if (list.size() == 1)
            return list.get(0);
        else
            return null;
    }

    public static int GetCount(Context context)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(context))
        {
            return dbHelper.GetCountMoneyDream();
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static int GetCount(DBHelper dbHelper)
    {
        try
        {
            return dbHelper.GetCountMoneyDream();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
