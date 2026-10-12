package com.MH83.LifeAsProject.view.Money.MoneyIncrease;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.MoneyIncreaseDateValueFormatter;
import com.MH83.LifeAsProject.custom.MyValueFormatter;
import com.MH83.LifeAsProject.custom.XYMarkerView;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumUpOrDown;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyIncreaseGraph extends Fragment
        implements IFragmentViewCustom<MoneyIncrease>
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_increase_graph;
    //endregion

    //region Fragment parameters
    private ArrayList<MoneyIncrease> _dataSet;
    FragmentMoneyIncreaseGraph _this;
//endregion

    //region Fragment Components
    private BarChart _chart;
    private SeekBar _seekBarCount, _seekBarShift;
    private TextView textViewCount, textViewShift;
    //endregion

    //region Fragment settings
    private final int _maxCountBars = 60;
    final int _minCountBars = 3;
    protected Typeface tfLight;
    int startColor1, startColor2, endColor1, endColor2;
    private GradientColor gradientColor1, gradientColor2;
    private boolean breakSetDataFromDB = false;
    private int _countMax, _shiftMax, _countProgress, _shiftProgress;
    public int getCountMax() { return _seekBarCount.getMax(); }
    public int getShiftMax() { return _seekBarShift.getMax(); }
    public int getCountProgress() { return _seekBarCount.getProgress(); }
    public int getShiftProgress() { return _seekBarShift.getProgress(); }
    //endregion

    //region Constructors
    public FragmentMoneyIncreaseGraph()
    {
        _this = this;
    }

    public FragmentMoneyIncreaseGraph(ArrayList<MoneyIncrease> dataSet, int countMax, int shiftMax,
                                      int countProgress, int shiftProgress)
    {
        _this = this;
        _dataSet = dataSet;
        _countMax = countMax;
        _countProgress = countProgress;
        _shiftMax = shiftMax;
        _shiftProgress = shiftProgress;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null)
        {
            AssetManager assetManager = getActivity().getAssets();
            if (assetManager != null)
                tfLight = Typeface.createFromAsset(assetManager, "OpenSans-Light.ttf");
        }

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        globalVariable.set_IFragmentViewCustom(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        _chart = view.findViewById(R.id.chart1);

        textViewCount = view.findViewById(R.id.textViewCount);
        textViewShift = view.findViewById(R.id.textViewShift);

        _seekBarCount = view.findViewById(R.id.seekBarX);
        _seekBarShift = view.findViewById(R.id.seekBarY);

        _chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                MoneyIncrease aff = (MoneyIncrease) e.getData();

                FragmentMoneyIncreasePopupNew fragmentPopupNew =
                        new FragmentMoneyIncreasePopupNew((AppCompatActivity) getContext(),
                                aff, EnumTypeAction.Update, _this, false,
                                aff.get_id(), aff.get_month(), aff.get_year(), aff.get_value()
//                                , aff.getExistIncoming()
                        );
                fragmentPopupNew.PopupShow();

//                FragmentMoneyIncreasePopup fragmentMoneyIncreasePopup = new FragmentMoneyIncreasePopup(getContext(), _this,
//                        EnumTypeAction.Update);
//                fragmentMoneyIncreasePopup.PopupShow(LayoutInflater.from(getContext()),
//                        aff.get_id(), aff.get_month(), aff.get_year(), aff.get_value(),
//                        aff.getExistIncoming(), false);
            }

            @Override
            public void onNothingSelected() { }
        });

        _seekBarCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (!breakSetDataFromDB)
                    setDataFromDB();
                breakSetDataFromDB = false;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        _seekBarShift.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (!breakSetDataFromDB)
                    setDataFromDB();
                breakSetDataFromDB = false;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        setChartSetting();
        setXAxisSettings();
        setYAxisSettings();
        setLegendSetting();
        setSeekBarCountSetting(_countMax, _countProgress);
        setSeekBarShiftSetting(_shiftMax, _shiftProgress);
        setDataFromDB();

        return view;
    }

    private void setChartSetting()
    {
        _chart.setDrawBarShadow(false);
        _chart.setDrawValueAboveBar(true);

        _chart.getDescription().setEnabled(false);

        // if more than _maxCountBars entries are displayed in the chart, no values will be drawn
        _chart.setMaxVisibleValueCount(_maxCountBars);

        // scaling can now only be done on x- and y-axis separately
        _chart.setPinchZoom(false);

        _chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);
        _chart.setDrawMarkers(false);
        _chart.setDrawBarShadow(false);
    }

    private void setXAxisSettings()
    {
        ValueFormatter xAxisFormatter = new MoneyIncreaseDateValueFormatter(_chart);

        XAxis xAxis = _chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        if (tfLight != null)
            xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(xAxisFormatter);

        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(_chart); // For bounds control
        _chart.setMarker(mv); // Set the marker to the chart
    }

    private void setYAxisSettings()
    {
        ValueFormatter custom = new MyValueFormatter(" ₽");

        YAxis leftAxis = _chart.getAxisLeft();
        if (tfLight != null)
            leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = _chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        if (tfLight != null)
            rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
    }

    private  void setLegendSetting()
    {
        Legend l = _chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    private void setSeekBarCountSetting(int maxValue, int progress)
    {
        int _maxValue = 10;
        if (maxValue != 0)      _maxValue = maxValue;
        _seekBarCount.setMax(_maxValue);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            _seekBarCount.setMin(Math.min(_maxValue, _minCountBars));

        if (progress != 0)
            _seekBarCount.setProgress(progress);
        else if (_seekBarCount.getProgress() > _maxValue)
            _seekBarCount.setProgress(_maxValue);

        _chart.getXAxis().setLabelCount(_seekBarCount.getProgress());
        textViewCount.setText(String.valueOf(_seekBarCount.getProgress()));
    }

    private void setSeekBarShiftSetting(int maxValue, int progress)
    {
        _seekBarShift.setProgress(progress);
        setSeekBarShiftSetting(maxValue);
    }

    private void setSeekBarShiftSetting(int maxValue)
    {
        _seekBarShift.setMax(maxValue);

        textViewShift.setText(String.valueOf(_seekBarShift.getProgress()));
    }

    private void initColors()
    {
        if (startColor1 == 0)
        {
            FragmentActivity fragmentActivity = getActivity();
            if (fragmentActivity != null)
            {
                startColor1 = ContextCompat.getColor(fragmentActivity, android.R.color.holo_green_light);
                startColor2 = ContextCompat.getColor(fragmentActivity, android.R.color.holo_red_light);
                endColor1 = ContextCompat.getColor(fragmentActivity, android.R.color.holo_red_light);
                endColor2 = ContextCompat.getColor(fragmentActivity, android.R.color.holo_green_light);
                gradientColor1 = new GradientColor(startColor1, endColor1);
                gradientColor2 = new GradientColor(startColor2, endColor2);
            }
        }
    }

    private void setDataFromDB()
    {
        if (_dataSet == null)
            return;

        int count = _seekBarCount.getProgress();
        int shift = _seekBarShift.getProgress();
        initColors();

        int size = _dataSet.size();
        MoneyIncreaseDateValueFormatter valueFormatter =
                (MoneyIncreaseDateValueFormatter) _chart.getXAxis().getValueFormatter();
        valueFormatter.setDataSet(_dataSet);

        int maxCountSeekBar = _maxCountBars;
        if (size < maxCountSeekBar)     maxCountSeekBar = size;
        setSeekBarCountSetting(maxCountSeekBar, 0);

        setSeekBarShiftSetting(_seekBarCount.getMax() - _seekBarCount.getProgress());

        ArrayList<BarEntry> values = new ArrayList<>();

        BarDataSet set1;
        List<GradientColor> gradientColors = new ArrayList<>();

        if (size < count + shift)
            count = size - shift;

        for (int i = shift; i < count + shift; i++)
        {
            MoneyIncrease mi = _dataSet.get(i);
            BarEntry barEntry = new BarEntry(i, _dataSet.get(i).get_value());
            barEntry.setData(mi);
            values.add(barEntry);
            if (_dataSet.get(i).get_upOrDown() == EnumUpOrDown.Up)
                gradientColors.add(gradientColor2);
            else
                gradientColors.add(gradientColor1);
        }

        set1 = new BarDataSet(values, "");
        set1.setDrawIcons(false);
        set1.setGradientColors(gradientColors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        if (tfLight != null)
            data.setValueTypeface(tfLight);
        data.setBarWidth(0.9f);

        _chart.setData(data);
        _chart.invalidate();
    }
    //endregion

    //region Click to button
    @Override
    public boolean refreshViewOnAdd(MoneyIncrease moneyIncrease)
    {
        AddItemToView(moneyIncrease);
        setYAxisSettings();

        return true;
    }

    @Override
    public boolean refreshViewOnUpdate(MoneyIncrease moneyIncrease)
    {
        long id = moneyIncrease.get_id();
        Integer month = moneyIncrease.getI_month();
        Integer year = moneyIncrease.getI_year();

        int positionUpdate = _dataSet.size();
        for (int i = 0; i < _dataSet.size(); i++)
            if (id == _dataSet.get(i).get_id())
            {
                positionUpdate = i;
                break;
            }

        int newPositionUpdate = positionUpdate;
        for (int i = 0; i < _dataSet.size(); i++)
            if ((month <= _dataSet.get(i).getI_month() && year.equals(_dataSet.get(i).getI_year()))
                    || (year < _dataSet.get(i).getI_year()))
            {
                newPositionUpdate = i;
                break;
            }

        if (newPositionUpdate == positionUpdate)
        {
            UpdateItemInView(positionUpdate, moneyIncrease);
        }
        else
        {
            RemoveItemFromView(positionUpdate);
            AddItemToView(moneyIncrease);
        }

        setYAxisSettings();
        _chart.invalidate();

        return true;
    }

    @Override
    public boolean refreshViewOnDelete(MoneyIncrease moneyIncrease)
    {
        Long id = moneyIncrease.get_id();

        int positionDelete = _dataSet.size();
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                positionDelete = i;
                break;
            }

        RemoveItemFromView(positionDelete);

        setYAxisSettings();
        _chart.invalidate();

        return true;
    }

    @Override
    public boolean refreshViewOnSelect(MoneyIncrease moneyIncrease) { return false; }

    private void AddItemToView(MoneyIncrease moneyIncrease)
    {
        Integer month = moneyIncrease.getI_month();
        Integer year = moneyIncrease.getI_year();

        int positionInsert = _dataSet.size();
        for (int i = 0; i < _dataSet.size(); i++)
            if ((month < _dataSet.get(i).getI_month() && year.equals(_dataSet.get(i).getI_year()))
                    || (year < _dataSet.get(i).getI_year()))
            {
                positionInsert = i;
                break;
            }

        if (positionInsert > 0)
            moneyIncrease.set_upOrDown(_dataSet.get(positionInsert - 1).get_value());
        _dataSet.add(positionInsert, moneyIncrease);
        if (positionInsert < _dataSet.size() - 1)
            _dataSet.get(positionInsert + 1).set_upOrDown(_dataSet.get(positionInsert).get_value());

        breakSetDataFromDB = true;
        _seekBarCount.setMax(_seekBarCount.getMax() + 1);
        _seekBarShift.setMax(_seekBarShift.getMax() + 1);

        BarData barData = _chart.getData();
        ArrayList<IBarDataSet> dataSets = (ArrayList<IBarDataSet>) barData.getDataSets();
        BarDataSet set1 = (BarDataSet) dataSets.get(0);
        ArrayList<BarEntry> values = (ArrayList<BarEntry>) set1.getValues();
        int count = values.size();

        if (!(((month > ((MoneyIncrease) values.get(0).getData()).getI_month() &&
                (year.equals(((MoneyIncrease) values.get(0).getData()).getI_year())))
                || (year > ((MoneyIncrease) values.get(0).getData()).getI_year())) &&
                ((month < ((MoneyIncrease) values.get(count - 1).getData()).getI_month() &&
                        (year.equals(((MoneyIncrease) values.get(count - 1).getData()).getI_year())))
                        || (year < ((MoneyIncrease) values.get(count - 1).getData()).getI_year()))))
        {
            _seekBarShift.setProgress(positionInsert, true);
        }
    }

    private void RemoveItemFromView(int positionRemove)
    {
        if (positionRemove == 0)
            _dataSet.get(positionRemove + 1).set_upOrDown(0);
        else if (positionRemove < _dataSet.size() - 1)
            _dataSet.get(positionRemove + 1).set_upOrDown(_dataSet.get(positionRemove - 1).get_value());
        _dataSet.remove(positionRemove);

        breakSetDataFromDB = true;
        _seekBarCount.setMax(_seekBarCount.getMax() - 1);
        _seekBarShift.setMax(_seekBarShift.getMax() - 1);
    }

    private void UpdateItemInView(int positionUpdate, MoneyIncrease moneyIncrease)
    {
        if (positionUpdate > 0)
            moneyIncrease.set_upOrDown(_dataSet.get(positionUpdate - 1).get_value());
        _dataSet.set(positionUpdate, moneyIncrease);
        if (positionUpdate < _dataSet.size() - 1)
            _dataSet.get(positionUpdate + 1).set_upOrDown(_dataSet.get(positionUpdate).get_value());

        setDataFromDB();
    }
    //endregion
}