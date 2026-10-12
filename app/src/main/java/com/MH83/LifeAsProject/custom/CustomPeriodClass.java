package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class CustomPeriodClass implements Parcelable
{
    public static int MONTH_ID = 3;
    public static int QUARTER_ID = 4;
    public static int YEAR_ID = 5;

    int _id;
    String _name;
    private int _shift = 0;

    public int get_id() { return _id; }
    public String get_name() { return _name;}
    public int GetShift() { return _shift; }
    public void SetShift(int shift) { _shift = shift; }
    public void ResetShift() { SetShift(0); }
    public void IncShift() { _shift++; }
    public void DecShift() { _shift--; }
    public void get_name4View(Context context, TextView textView)
    {
        switch (_id)
        {
            case 2:
                CustomStaticClass.setDateRangeView(context, get_start_dateC() , get_end_dateC(),
                        false, textView);
                break;
            case 4:
                CustomStaticClass.setDateRangeView(context, get_start_dateC() , get_end_dateC(),
                        false, textView);
                break;
            case 3:
                CustomStaticClass.setDateView(context, String.valueOf(get_start_dateC().get(Calendar.MONTH) + 1),
                        String.valueOf(get_start_dateC().get(Calendar.YEAR)), textView);
                break;
            case 5:
                CustomStaticClass.setDateYearView(get_start_dateC().get(Calendar.YEAR), textView);
                break;
            default:
                CustomStaticClass.setDateView(context, get_start_dateC(), true, textView);
        }
    }

    public Calendar get_start_dateC()
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        switch (_id)
        {
            case 0:
                c.set(Calendar.DATE, 1);
                c.set(Calendar.MONTH, 1);
                c.set(Calendar.YEAR, 2000);
                return c;
            case 2:
                c.add(Calendar.DATE, 7 * _shift);
                int dayOfWeek = Math.floorMod((c.get(Calendar.DAY_OF_WEEK) + 6), 7);
                if (dayOfWeek == 0) dayOfWeek = 7;
                c.add(Calendar.DATE, -(dayOfWeek - 1));
                return c;
            case 3:
                c.add(Calendar.MONTH, _shift);
                c.set(Calendar.DATE, 1);
                return c;
            case 4:
                c.add(Calendar.MONTH, 3 * _shift);
                c.set(Calendar.DATE, 1);
//                int shiftM = c.get(Calendar.MONTH) / 3;
                int shiftM = Math.floorMod(c.get(Calendar.MONTH), 3);
                c.add(Calendar.MONTH, -shiftM);
                return c;
            case 5:
                c.add(Calendar.YEAR, _shift);
                c.set(Calendar.DATE, 1);
                c.set(Calendar.MONTH, 0);
                return c;
            case 6:
                return null;
            default:
                c.add(Calendar.DATE, _shift);
                return c;
        }
    }

    public Calendar get_end_dateC()
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        switch (_id)
        {
            case 2:
                c.add(Calendar.DATE, 7 * _shift);
                int dayOfWeek = Math.floorMod((c.get(Calendar.DAY_OF_WEEK) + 6), 7);
                if (dayOfWeek == 0) dayOfWeek = 7;
                c.add(Calendar.DATE, 7- (dayOfWeek));
                return c;
            case 3:
                c.add(Calendar.MONTH, _shift);
                c.set(Calendar.DATE, 1);
                c.add(Calendar.MONTH, 1);
                c.add(Calendar.DATE, -1);
                return c;
            case 4:
                c.add(Calendar.MONTH, 3 * _shift);
                c.set(Calendar.DATE, 1);
//                int shiftM = c.get(Calendar.MONTH) / 3;
                int shiftM = Math.floorMod(c.get(Calendar.MONTH), 3);
                c.add(Calendar.MONTH, 3 - shiftM);
                c.add(Calendar.DATE, -1);
                return c;
            case 5:
                c.add(Calendar.YEAR, _shift);
                c.set(Calendar.DATE, 1);
                c.set(Calendar.MONTH, 0);
                c.add(Calendar.YEAR, 1);
                c.add(Calendar.DATE, -1);
                return c;
            case 6:
                return null;
            default:
                c.add(Calendar.DATE, _shift);
                return c;
        }
    }

    public CustomPeriodClass(int id, String name)
    {
        _id = id;
        _name = name;
    }

    public CustomPeriodClass(int id, String name, int shift)
    {
        _id = id;
        _name = name;
        _shift = shift;
    }

    public static ArrayList<CustomPeriodClass> GetListPlus0(Context context)
    {
        ArrayList<CustomPeriodClass> list = new ArrayList<>();
        list.add(new CustomPeriodClass(0, context.getString(R.string.Day)));
        list.add(new CustomPeriodClass(1, context.getString(R.string.Day)));
        list.add(new CustomPeriodClass(2, context.getString(R.string.Week)));
        list.add(new CustomPeriodClass(MONTH_ID, context.getString(R.string.Month)));
        list.add(new CustomPeriodClass(4, context.getString(R.string.Quarter)));
        list.add(new CustomPeriodClass(5, context.getString(R.string.Year)));
        list.add(new CustomPeriodClass(6, context.getString(R.string.AllTime)));

        return list;
    }

    public static ArrayList<CustomPeriodClass> GetList(Context context)
    {
        ArrayList<CustomPeriodClass> list = new ArrayList<>();
        list.add(new CustomPeriodClass(1, context.getString(R.string.Day)));
        list.add(new CustomPeriodClass(2, context.getString(R.string.Week)));
        list.add(new CustomPeriodClass(MONTH_ID, context.getString(R.string.Month)));
        list.add(new CustomPeriodClass(4, context.getString(R.string.Quarter)));
        list.add(new CustomPeriodClass(5, context.getString(R.string.Year)));
        list.add(new CustomPeriodClass(6, context.getString(R.string.AllTime)));

        return list;
    }

    public static ArrayList<CustomPeriodClass> GetList345(Context context)
    {
        ArrayList<CustomPeriodClass> list = new ArrayList<>();
        list.add(new CustomPeriodClass(3, context.getString(R.string.Month)));
        list.add(new CustomPeriodClass(4, context.getString(R.string.Quarter)));
        list.add(new CustomPeriodClass(5, context.getString(R.string.Year)));

        return list;
    }

    //region Parcelable
    @Override
    public int describeContents() { return CONTENTS_FILE_DESCRIPTOR; }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i)
    {
        parcel.writeInt(_id);
        parcel.writeString(_name);
        parcel.writeInt(_shift);
    }

    protected CustomPeriodClass(Parcel in)
    {
        _id = in.readInt();
        _name = in.readString();
        _shift = in.readInt();
    }

    public static final Creator<CustomPeriodClass> CREATOR = new Creator<CustomPeriodClass>()
    {
        @Override
        public CustomPeriodClass createFromParcel(Parcel in)
        {
            return new CustomPeriodClass(in);
        }

        @Override
        public CustomPeriodClass[] newArray(int size)
        {
            return new CustomPeriodClass[size];
        }
    };
    //endregion

    //region
    public SharedPreferences.Editor SaveToSharedPreferences(SharedPreferences.Editor editor,
                                                            String prefix)
    {
        SharedPreferences.Editor editor1 = editor;

        editor1.putInt(prefix + "CustomPeriodClass.Id", _id);
        editor1.putString(prefix + "CustomPeriodClass.Name", _name);
        editor1.putInt(prefix + "CustomPeriodClass.Shift", 0);

        return editor1;
    }

    public static CustomPeriodClass GetFromSharedPreferences(SharedPreferences settings,
                                                             String prefix)
    {
        return new CustomPeriodClass(
                settings.getInt(prefix + "CustomPeriodClass.Id", 0),
                settings.getString(prefix + "CustomPeriodClass.Name", ""),
                settings.getInt(prefix + "CustomPeriodClass.Shift", 0));

    }
    //endregion
}
