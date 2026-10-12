package com.MH83.LifeAsProject.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

import java.util.Calendar;
import java.util.Locale;

public class CustomStaticClass
{
    public static Character RubleChar = '₽';
    public static Character PercentChar = '%';

    //region set Day of Week
    public static void setDayOfWeek(Context context, Calendar calendar, TextView textView)
    {
        textView.setText(getDayOfWeek(context, calendar));
    }

    public static String getDayOfWeek(Context context, Calendar calendar)
    {
        String dayOfWeek = "";
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 2:
                dayOfWeek = context.getString(R.string.Monday);
                break;
            case 3:
                dayOfWeek = context.getString(R.string.Tuesday);
                break;
            case 4:
                dayOfWeek = context.getString(R.string.Wednesday);
                break;
            case 5:
                dayOfWeek = context.getString(R.string.Thursday);
                break;
            case 6:
                dayOfWeek = context.getString(R.string.Friday);
                break;
            case 7:
                dayOfWeek = context.getString(R.string.Saturday);
                break;
            case 1:
                dayOfWeek = context.getString(R.string.Sunday);
                break;
        }

        return dayOfWeek;
    }
    //endregion

    //region set Date View
    public static void setDateView(Context context, String month, String year, TextView textView)
    {
        String date = "";
        switch (month)
        {

            case "01": date = context.getString(R.string.January);     break;
            case "1": date = context.getString(R.string.January);      break;
            case "02": date = context.getString(R.string.February);    break;
            case "2": date = context.getString(R.string.February);     break;
            case "03": date = context.getString(R.string.March);       break;
            case "3": date = context.getString(R.string.March);        break;
            case "04": date = context.getString(R.string.April);     break;
            case "4": date = context.getString(R.string.April);      break;
            case "05": date = context.getString(R.string.May);        break;
            case "5": date = context.getString(R.string.May);         break;
            case "06": date = context.getString(R.string.June);       break;
            case "6": date = context.getString(R.string.June);        break;
            case "07": date = context.getString(R.string.July);       break;
            case "7": date = context.getString(R.string.July);        break;
            case "08": date = context.getString(R.string.August);     break;
            case "8": date = context.getString(R.string.August);      break;
            case "09": date = context.getString(R.string.September);   break;
            case "9": date = context.getString(R.string.September);    break;
            case "10": date = context.getString(R.string.October);    break;
            case "11": date = context.getString(R.string.November);     break;
            case "12": date = context.getString(R.string.December);    break;
        }
        String value = date + " " + year;
        textView.setText(value);
    }

    public static void setDateView(Context context, Calendar calendar,
                                   boolean withYear, TextView textView)
    {
        setDateView(context, calendar, withYear, textView, false);
    }

    public static void setDateView(Context context, Calendar calendar,
                                   boolean withYear, TextView textView,
                                   boolean withDayOfWeek)
    {
        if (calendar == null)
            textView.setText(context.getResources().getString(R.string.WithoutTime));
        else
            setDateView(context, calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), withYear, textView, withDayOfWeek, false);
    }

    public static void setDateView(Context context, Calendar calendar,
                                   boolean withYear, TextView textView,
                                   boolean withDayOfWeek, boolean withDayOfToday)
    {
        if (calendar == null)
            textView.setText(context.getResources().getString(R.string.WithoutTime));
        else
            setDateView(context, calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR), withYear, textView, withDayOfWeek, withDayOfToday);
    }

    public static void setDateView(Context context, Integer day, Integer month,
                                   Integer year, TextView textView)
    {
        setDateView(context, day, month, year, true, textView, false, false);
    }

    public static void setDateView(Context context, Integer day, Integer month,
                                       Integer year, boolean withYear, TextView textView,
                                        boolean withDayOfWeek,
                                        boolean withDayOfToday)
    {
        textView.setText(getDate4View(context, day, month, year, withYear, withDayOfWeek,
                withDayOfToday));
    }

    public static void setDateView(Context context, Integer day, Integer month,
                                   Integer year,
                                   Integer hour, Integer minute,
                                   TextView textView)
    {
        if (day == 0 && month == 0 && year == 0 && hour == 0 && minute == 0)
            textView.setText("");
        else
            setDateView(context, day, month, year, true, hour, minute, textView);
    }


    public static void setDateView(Context context, Integer day, Integer month,
                                   Integer year, boolean withYear,
                                   Integer hour, Integer minute,
                                   TextView textView)
    {
        textView.setText(getDate4View(context, day, month, year, withYear, hour, minute));
    }

    public static String getDate4View(Context context, Calendar calendar, boolean withYear)
    {
        return getDate4View(context, calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), withYear, false, false);
    }

    private static String getDate4View(Context context, Integer day, Integer month,
                                       Integer year, boolean withYear,
                                       Integer hour, Integer minute)

    {
        String s = getDate4View(context, day, month, year, withYear, false, false);

        s += " ";
        if (hour < 10)
            s += "0";
        s += hour + ":";

        if (minute < 10)
            s += "0";
        s += minute;

        return s;
    }

    public static String getDate4View(Context context, Integer day, Integer month,
                                      Integer year, boolean withYear,
                                      boolean withDayOfWeek,
                                      boolean withDayOfToday)
    {
        String date = "";
        if (day < 10)
            date = "0";
        date += day + " ";

        switch (month)
        {
            case 0: date += context.getString(R.string.JanuaryR);      break;
            case 1: date += context.getString(R.string.FebruaryR);     break;
            case 2: date += context.getString(R.string.MarchR);        break;
            case 3: date += context.getString(R.string.AprilR);        break;
            case 4: date += context.getString(R.string.MayR);          break;
            case 5: date += context.getString(R.string.JuneR);         break;
            case 6: date += context.getString(R.string.JulyR);         break;
            case 7: date += context.getString(R.string.AugustR);       break;
            case 8: date += context.getString(R.string.SeptemberR);    break;
            case 9: date += context.getString(R.string.OctoberR);     break;
            case 10: date += context.getString(R.string.NovemberR);    break;
            case 11: date += context.getString(R.string.DecemberR);    break;
        }
        if (withYear)
            date += " " + year;

        if (withDayOfWeek)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            date += " - " + getDayOfWeek(context, calendar);
        }

        if (withDayOfToday)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            date += " - " + getDayOfToday(context, calendar);
        }

        return date;
    }

    private static String getDayOfToday(Context context, Calendar calendar)
    {
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(Calendar.HOUR, 0);
        calendarToday.set(Calendar.HOUR_OF_DAY, 0);
        calendarToday.set(Calendar.MINUTE, 0);
        calendarToday.set(Calendar.SECOND, 0);
        calendarToday.set(Calendar.MILLISECOND, 0);

        Calendar startWeek = Calendar.getInstance();
        startWeek.set(Calendar.HOUR, 0);
        startWeek.set(Calendar.HOUR_OF_DAY, 0);
        startWeek.set(Calendar.MINUTE, 0);
        startWeek.set(Calendar.SECOND, 0);
        startWeek.set(Calendar.MILLISECOND, 0);
        while (startWeek.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
            startWeek.add(Calendar.DATE, -1);

        Calendar endWeek = Calendar.getInstance();
        endWeek.set(Calendar.HOUR, 0);
        endWeek.set(Calendar.HOUR_OF_DAY, 0);
        endWeek.set(Calendar.MINUTE, 0);
        endWeek.set(Calendar.SECOND, 0);
        endWeek.set(Calendar.MILLISECOND, 0);
        while (endWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            endWeek.add(Calendar.DATE, 1);

        Calendar startMonth = Calendar.getInstance();
        startMonth.set(Calendar.HOUR, 0);
        startMonth.set(Calendar.HOUR_OF_DAY, 0);
        startMonth.set(Calendar.MINUTE, 0);
        startMonth.set(Calendar.SECOND, 0);
        startMonth.set(Calendar.MILLISECOND, 0);
        startMonth.set(Calendar.DATE, 1);

        Calendar endMonth = Calendar.getInstance();
        endMonth.set(Calendar.HOUR, 0);
        endMonth.set(Calendar.HOUR_OF_DAY, 0);
        endMonth.set(Calendar.MINUTE, 0);
        endMonth.set(Calendar.SECOND, 0);
        endMonth.set(Calendar.MILLISECOND, 0);
        endMonth.set(Calendar.DATE, 1);
        endMonth.add(Calendar.MONTH, 1);
        endMonth.add(Calendar.DATE, -1);

        Calendar startYear = Calendar.getInstance();
        startYear.set(Calendar.HOUR, 0);
        startYear.set(Calendar.HOUR_OF_DAY, 0);
        startYear.set(Calendar.MINUTE, 0);
        startYear.set(Calendar.SECOND, 0);
        startYear.set(Calendar.MILLISECOND, 0);
        startYear.set(Calendar.DATE, 1);
        startYear.set(Calendar.MONTH, 0);

        Calendar endYear = Calendar.getInstance();
        endYear.set(Calendar.HOUR, 0);
        endYear.set(Calendar.HOUR_OF_DAY, 0);
        endYear.set(Calendar.MINUTE, 0);
        endYear.set(Calendar.SECOND, 0);
        endYear.set(Calendar.MILLISECOND, 0);
        endYear.set(Calendar.DATE, 1);
        endYear.set(Calendar.MONTH, 0);
        endYear.add(Calendar.YEAR, 1);
        endYear.add(Calendar.DATE, -1);

        if (calendar.getTime().getTime() == calendarToday.getTime().getTime())
            return context.getString(R.string.Today);
        else if  (calendar.getTime().getTime() < calendarToday.getTime().getTime())
        {
            calendarToday.add(Calendar.DATE, -1);
            if (calendarToday.getTime().getTime() == calendar.getTime().getTime())
                return context.getString(R.string.Yesterday);

            calendarToday.add(Calendar.DATE, -1);
            if (calendarToday.getTime().getTime() == calendar.getTime().getTime())
                return context.getString(R.string.DayBeforeYesterday);

            if (calendar.getTime().getTime() >= startWeek.getTime().getTime())
                return context.getString(R.string.ThisWeek);

            startWeek.add(Calendar.DATE, -7);
            if (calendar.getTime().getTime() >= startWeek.getTime().getTime())
                return context.getString(R.string.PrevWeek);

            if (calendar.getTime().getTime() >= startMonth.getTime().getTime())
                return context.getString(R.string.ThisMonth);

            startMonth.add(Calendar.MONTH, -1);
            if (calendar.getTime().getTime() >= startMonth.getTime().getTime())
                return context.getString(R.string.PrevMonth);

            if (calendar.getTime().getTime() >= startYear.getTime().getTime())
                return context.getString(R.string.ThisYear);

            startYear.add(Calendar.MONTH, -1);
            if (calendar.getTime().getTime() >= startYear.getTime().getTime())
                return context.getString(R.string.PrevYear);

            return context.getString(R.string.LongTimeAgo);
        }
        else
        {
            calendarToday.add(Calendar.DATE, 1);
            if (calendarToday.getTime().getTime() == calendar.getTime().getTime())
                return context.getString(R.string.Tomorrow);

            calendarToday.add(Calendar.DATE, 1);
            if (calendarToday.getTime().getTime() == calendar.getTime().getTime())
                return context.getString(R.string.DayAfterTomorrow);

            if (calendar.getTime().getTime() <= endWeek.getTime().getTime())
                return context.getString(R.string.ThisWeek);

            endWeek.add(Calendar.DATE, 7);
            if (calendar.getTime().getTime() <= endWeek.getTime().getTime())
                return context.getString(R.string.NextWeek);

            if (calendar.getTime().getTime() <= endMonth.getTime().getTime())
                return context.getString(R.string.ThisMonth);

            endMonth.add(Calendar.DATE, 7);
            if (calendar.getTime().getTime() <= endMonth.getTime().getTime())
                return context.getString(R.string.NextMonth);

            if (calendar.getTime().getTime() <= endYear.getTime().getTime())
                return context.getString(R.string.ThisYear);

            endYear.add(Calendar.DATE, 7);
            if (calendar.getTime().getTime() <= endYear.getTime().getTime())
                return context.getString(R.string.NextYear);

            return context.getString(R.string.NearFuture);
        }
    }

    public static String getDateShort4View(Integer day, Integer month, Integer year)
    {
        String date = "";
        if (day < 10)
            date = "0";
        date += day + ".";

        if (month < 9)
            date += "0";
        date += (month + 1) + "." + year;

        return date;
    }

    public static void setDateRangeView(Context context, Calendar calendar1, Calendar calendar2,
                                        boolean withYear, TextView textView)
    {
        setDateRangeView(context, calendar1.get(Calendar.DATE), calendar1.get(Calendar.MONTH),
                    calendar1.get(Calendar.YEAR), calendar2.get(Calendar.DATE),
                    calendar2.get(Calendar.MONTH), calendar1.get(Calendar.YEAR),
                    withYear, textView);
    }

    @SuppressLint("SetTextI18n")
    public static void setDateRangeView(Context context, Integer day1, Integer month1,
                                        Integer year1, Integer day2, Integer month2,
                                        Integer year2, boolean withYear, TextView textView)
    {
        textView.setText(getDate4View(context, day1, month1, year1, withYear, false, false) + " - " +
                            getDate4View(context, day2, month2, year2, withYear, false, false));
    }

    @SuppressLint("SetTextI18n")
    public static void setDateYearView(Integer year, TextView textView)
    {
        textView.setText(year + " год");
    }
    //endregion

    public static String getTypeCategory4View(Context context, EnumTypeCategory typeCategory)
    {
        if (typeCategory.equals(EnumTypeCategory.Wallet))
            return context.getString(R.string.Wallet);
        if (typeCategory.equals(EnumTypeCategory.Increase))
            return context.getString(R.string.Increase);
        else
            return context.getString(R.string.Cost);
    }

    public static String getRubleSum(Double sum)
    {
        return getRubleSum(sum, true);
    }

    public static String getRubleSum(Float sum)
    {
        return getRubleSum(sum, true);
    }

    public static String getFormatSum(Float sum)
    {
        return getFormatSum(sum, true);
    }

    public static String getIntRubleSum(Integer sum)
    {
        return getIntRubleSum(sum, true);
    }

    public static String getIntRubleSum(Integer sum, boolean show0)
    {
        if (sum.equals(0) && !show0)
            return "";

        return getIntFormatSum(sum, show0) + "\u00A0" + RubleChar;
    }

    public static String getIntFormatSum(Integer sum, boolean show0)
    {
        if (sum.equals(0) && !show0)
            return "";

        return String.format(Locale.FRANCE, "%,1d", sum).
                replace(" ", "\u00A0");
    }

    public static String getRubleSum(Double sum, boolean show0)
    {
        if (sum.equals(0f) && !show0)
            return "";

        return getFormatSum(sum, show0) + "\u00A0" + RubleChar;
    }

    public static String getRubleSum(Float sum, boolean show0)
    {
        if (sum.equals(0f) && !show0)
            return "";

        return getFormatSum(sum, show0) + "\u00A0" + RubleChar;
    }

    public static String getFormatSum(Double sum, boolean show0)
    {
        if (sum.equals(0f) && !show0)
            return "";

        return String.format(Locale.FRANCE, "%,.2f", sum).
                replace(" ", "\u00A0");
    }

    public static String getFormatSum(Float sum, boolean show0)
    {
        if (sum.equals(0f) && !show0)
            return "";

        return String.format(Locale.FRANCE, "%,.2f", sum).
                replace(" ", "\u00A0");
    }

    public static String getAbsFloatPercent(Float percent, boolean show0)
    {
        if (percent.equals(0f) && !show0)
            return "";

        return String.format(Locale.FRANCE, "%,.2f", Math.abs(percent)).
                replace(" ", "\u00A0") + "\u00A0" + PercentChar;
    }
}
