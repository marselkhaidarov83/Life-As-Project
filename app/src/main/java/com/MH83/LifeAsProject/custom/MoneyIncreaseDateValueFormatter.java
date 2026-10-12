package com.MH83.LifeAsProject.custom;

import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class MoneyIncreaseDateValueFormatter extends ValueFormatter
{
    private ArrayList<MoneyIncrease> _dataSet;
    BarLineChartBase<?> _chart;

    public MoneyIncreaseDateValueFormatter(BarLineChartBase<?> chart)
    {
        _chart = chart;
    }

    public void setDataSet(ArrayList<MoneyIncrease> dataSet)
    {
        _dataSet = dataSet;
    }

    @Override
    public String getFormattedValue(float value)
    {
        try
        {
            return _dataSet.get((int) value).get_month_year();
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
