package com.MH83.LifeAsProject.custom;

import java.util.Calendar;

public class CustomDateCheck
{
    public static boolean CheckPrevDate(GlobalClass gVar, CustomPeriodClass customPeriodClass)
    {
        if (gVar.get_hasUnpaidSubscription())
        {
            Calendar calendarPrevMonthBegin = Calendar.getInstance();
            calendarPrevMonthBegin.add(Calendar.DATE, -3);
            calendarPrevMonthBegin.set(Calendar.DATE, 1);
            calendarPrevMonthBegin.set(Calendar.HOUR, 0);
            calendarPrevMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarPrevMonthBegin.set(Calendar.MINUTE, 0);
            calendarPrevMonthBegin.set(Calendar.SECOND, 0);
            calendarPrevMonthBegin.set(Calendar.MILLISECOND, 0);

            Calendar calendarThisMonthBegin = Calendar.getInstance();
            calendarThisMonthBegin.set(Calendar.DATE, 1);
            calendarThisMonthBegin.set(Calendar.HOUR, 0);
            calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisMonthBegin.set(Calendar.MINUTE, 0);
            calendarThisMonthBegin.set(Calendar.SECOND, 0);
            calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);

            customPeriodClass.DecShift();
            Calendar calendar = customPeriodClass.get_start_dateC();
            customPeriodClass.IncShift();

            return calendar.getTime().getTime() == calendarPrevMonthBegin.getTime().getTime() ||
                    calendar.getTime().getTime() == calendarThisMonthBegin.getTime().getTime();
        }

        return true;
    }

    public static boolean CheckNextDate(GlobalClass gVar, CustomPeriodClass customPeriodClass)
    {
        if (gVar.get_hasUnpaidSubscription())
        {
            Calendar calendarPrevMonthBegin = Calendar.getInstance();
            calendarPrevMonthBegin.add(Calendar.DATE, -3);
            calendarPrevMonthBegin.set(Calendar.DATE, 1);
            calendarPrevMonthBegin.set(Calendar.HOUR, 0);
            calendarPrevMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarPrevMonthBegin.set(Calendar.MINUTE, 0);
            calendarPrevMonthBegin.set(Calendar.SECOND, 0);
            calendarPrevMonthBegin.set(Calendar.MILLISECOND, 0);

            Calendar calendarThisMonthBegin = Calendar.getInstance();
            calendarThisMonthBegin.set(Calendar.DATE, 1);
            calendarThisMonthBegin.set(Calendar.HOUR, 0);
            calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarThisMonthBegin.set(Calendar.MINUTE, 0);
            calendarThisMonthBegin.set(Calendar.SECOND, 0);
            calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);

            customPeriodClass.IncShift();
            Calendar calendar = customPeriodClass.get_start_dateC();
            customPeriodClass.DecShift();

            return calendar.getTime().getTime() == calendarPrevMonthBegin.getTime().getTime() ||
                    calendar.getTime().getTime() == calendarThisMonthBegin.getTime().getTime();
        }

        return true;
    }
}
