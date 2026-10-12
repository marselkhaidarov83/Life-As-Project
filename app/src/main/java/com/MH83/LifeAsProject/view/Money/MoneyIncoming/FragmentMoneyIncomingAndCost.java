package com.MH83.LifeAsProject.view.Money.MoneyIncoming;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyIncomingAndCostAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyIncomingDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyIncreaseDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumPeriodData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentDohodRashod;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentIncrease;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPlanFact;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPlanFactMoney;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowBlankData;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostAdvanced;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostAdvancedPlus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostBase;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostProject;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncomingAndCostProjectPlus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowIncreasePercent;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowPercent;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomTextView;
import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentManageMoneyCategory;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyIncrease.FragmentMoneyIncreasePopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyIncomingAndCost extends CustomFragment
        implements IFragmentViewCustom,
        IFragmentManageMoneyCategory, IFragmentExpandAll, IFragmentCollapseAll,
        IFragmentLegend, IFragmentSettings, IFragmentPlanFact,
        IFragmentShowIncreasePercent, IFragmentShowPercent,
        IFragmentShowBlankData,
        IFragmentIncrease,
        IFragmentShowIncomingAndCostBase, IFragmentShowIncomingAndCostProjectPlus,
        IFragmentShowIncomingAndCostAdvanced, IFragmentShowIncomingAndCostAdvancedPlus,
        IFragmentShowIncomingAndCostProject, IFragmentShowIncomingAndCostAll,
        IFragmentDohodRashod, IFragmentPlanFactMoney
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_incoming;
    //endregion

    //region Fragment parameters
    private List<MoneyIncrease> _moneyIncreases;
    private EnumTypeCategory _typeCategory;
    private EnumTypeData _typeData;
    //endregion

    //region Fragment Components
    TreeView treeView;
    private TextView editedTextView;
    private SeekBar _seekBarCountMIncrease;
    private SeekBar _seekBarMIncrease;
    TextView textView0Increase;
    private List<TextView> textViewIncrease;
    private List<TextView> textViewResult;
    private List<ConstraintLayout> constraintLayouts;
    private List<ConstraintLayout> constraintResultLayouts;
    private MaterialCardView materialCardViewHeader;
    //endregion

    //region Fragment settings
    private int editedPos;
    private char delimiter = ' ';
    private final int _maxCountMoneyIncrease = 5;
    private int _countMoneyIncrease = 3;
    int shift = 0;
    int activeCol = 0;
    //endregion

    //region IFragmentManageMoneyCategory
    public EnumTypeCategory getTypeCategory()
    {
        return _typeCategory;
    }
    public int getFromItem()
    {
        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        return globalVar.get_moneyIncomingAndCostSettingsHT().get_fragmentId();
    }
    //endregion

    //region IFragmentSettings
    @Override
    public boolean ShowFormSettingButton() { return true; }

    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyIncomingSettingsPopup(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyIncomingAndCostSettingsHT().Clone(),
                    this).PopupShow();
        };
    }
    //endregion

    //region Constructors
    public FragmentMoneyIncomingAndCost() { }

    public FragmentMoneyIncomingAndCost(EnumTypeCategory typeCategory,
                                        EnumTypeData typeData)
    {
        _typeCategory = typeCategory;
        _typeData = typeData;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _typeCategory = globalVariable.get_moneyIncomingAndCostSettingsHT().get_typeCategory();
        _typeData = globalVariable.get_moneyIncomingAndCostSettingsHT().get_typeData();
        _countMoneyIncrease = globalVariable.get_moneyIncomingAndCostSettingsHT().get_countMoneyIncrease();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        GlobalClass global = (GlobalClass) container.getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            MoneyIncomingAndCostSettings moneyIncomingAndCostSettings =
                    global.get_moneyIncomingAndCostSettingsHT();
            moneyIncomingAndCostSettings.set_showBlankData(true);
            moneyIncomingAndCostSettings.set_showPercent(false);
            moneyIncomingAndCostSettings.set_showIncreasePercent(false);
            moneyIncomingAndCostSettings.set_periodData(EnumPeriodData.Month);
            moneyIncomingAndCostSettings.set_typeCategory(EnumCategoryType.All);
            global.set_moneyIncomingAndCostSettings(moneyIncomingAndCostSettings);
        }

        View view = inflater.inflate(_layoutId, container, false);

        FindComponents(view);
        SetListeners();

        FillData();

        ShowLegend();

        return view;
    }

    private void FillData()
    {
        Reinitialize();
        SetText2Form();
        LoadData();
        FillSeekBarCount();
        FillSeekBarMIncrease();
    }

    private void Reinitialize()
    {
        shift = 0;
        _seekBarCountMIncrease.setMax(5);
        _seekBarCountMIncrease.setOnSeekBarChangeListener(null);
        _seekBarMIncrease.setOnSeekBarChangeListener(null);

        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        _typeCategory = globalVar.get_moneyIncomingAndCostSettingsHT().get_typeCategory();
        _typeData = globalVar.get_moneyIncomingAndCostSettingsHT().get_typeData();
        _countMoneyIncrease = globalVar.get_moneyIncomingAndCostSettingsHT().get_countMoneyIncrease();
    }

    private void LoadData()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        _moneyIncreases = MoneyIncreaseDataSource.LoadData(getContext(),
                globalVariable.get_moneyIncomingAndCostSettingsHT().Clone());
    }

    private void FindComponents(View view)
    {
        treeView = view.findViewById(R.id.tree_view);
        _seekBarCountMIncrease = view.findViewById(R.id.seekBarCountMIncrease);
        _seekBarMIncrease = view.findViewById(R.id.seekBarMIncrease);
        materialCardViewHeader = view.findViewById(R.id.materialCardViewHeader);
        textView0Increase = view.findViewById(R.id.textView0Increase);

        textViewIncrease = new ArrayList<>();
        textViewIncrease.add(0, view.findViewById(R.id.textView1Increase));
        textViewIncrease.add(1, view.findViewById(R.id.textView2Increase));
        textViewIncrease.add(2, view.findViewById(R.id.textView3Increase));
        textViewIncrease.add(3, view.findViewById(R.id.textView4Increase));
        textViewIncrease.add(4, view.findViewById(R.id.textView5Increase));

        textViewResult = new ArrayList<>();
        textViewResult.add(0, view.findViewById(R.id.textView1Result));
        textViewResult.add(1, view.findViewById(R.id.textView2Result));
        textViewResult.add(2, view.findViewById(R.id.textView3Result));
        textViewResult.add(3, view.findViewById(R.id.textView4Result));
        textViewResult.add(4, view.findViewById(R.id.textView5Result));

        constraintLayouts = new ArrayList<>();
        constraintLayouts.add(0, view.findViewById(R.id.constraintLayout1));
        constraintLayouts.add(1, view.findViewById(R.id.constraintLayout2));
        constraintLayouts.add(2, view.findViewById(R.id.constraintLayout3));
        constraintLayouts.add(3, view.findViewById(R.id.constraintLayout4));
        constraintLayouts.add(4, view.findViewById(R.id.constraintLayout5));

        constraintResultLayouts = new ArrayList<>();
        constraintResultLayouts.add(0, view.findViewById(R.id.constraintResultLayout1));
        constraintResultLayouts.add(1, view.findViewById(R.id.constraintResultLayout2));
        constraintResultLayouts.add(2, view.findViewById(R.id.constraintResultLayout3));
        constraintResultLayouts.add(3, view.findViewById(R.id.constraintResultLayout4));
        constraintResultLayouts.add(4, view.findViewById(R.id.constraintResultLayout5));
    }

    private void SetText2Form()
    {
        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        switch (globalVar.get_enumMoneyIncAndCostSetting())
        {
            case IncomingPlan:
                textView0Increase.setText(R.string.money_incoming_plan);
                break;
            case IncomingFact:
                textView0Increase.setText(R.string.money_incoming_fact);
                break;
            case ExpensePlan:
                textView0Increase.setText(R.string.money_costs_plan);
                break;
            case ExpenseFact:
                textView0Increase.setText(R.string.money_costs_fact);
                break;
            default:
        }
    }

    private void SetListeners()
    {
        ((CustomTextView) textViewIncrease.get(4)).setOnLayoutListener(view1 -> ChangeTextViewHeight());

        for (int i = 0; i < _maxCountMoneyIncrease; i++)
            textViewResult.get(i).setOnClickListener(view1 ->
                    _seekBarMIncrease.setProgress(
                            _seekBarMIncrease.getProgress() + textViewResult.indexOf(view1) - shift));

        for (int i = 0; i < _maxCountMoneyIncrease; i++)
            textViewIncrease.get(i).setOnClickListener(view1 ->
                    _seekBarMIncrease.setProgress(
                            _seekBarMIncrease.getProgress() + textViewIncrease.indexOf(view1) - shift));

        for (int i = 0; i < _maxCountMoneyIncrease; i++)
            textViewIncrease.get(i).setOnLongClickListener(view1 ->
            {
                if (_typeData.equals(EnumTypeData.Fact))
                    return false;

                editedTextView = (TextView) view1;
                editedPos = _seekBarMIncrease.getProgress() + textViewIncrease.indexOf(view1) - shift;
                MoneyIncrease editedMoneyIncrease = _moneyIncreases.get(editedPos);

                FragmentMoneyIncreasePopupNew fragmentPopupNew =
                        new FragmentMoneyIncreasePopupNew((AppCompatActivity) getContext(),
                                editedMoneyIncrease, EnumTypeAction.Update, this, true,
                                editedMoneyIncrease.get_id(),
                                editedMoneyIncrease.get_month(),
                                editedMoneyIncrease.get_year(),
                                editedMoneyIncrease.get_value()
                        );
                fragmentPopupNew.PopupShow();
                return false;
            });
    }

    private void ShowLegend()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyIncomingDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyIncoming"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyIncoming");
        }
    }

    private void ChangeVisibleCountViews()
    {
        for (int i = 0; i < _countMoneyIncrease; i++)
        {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) constraintLayouts.get(i).getLayoutParams();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.weight = 1;
            constraintLayouts.get(i).setLayoutParams(params);

            params =
                    (LinearLayout.LayoutParams) constraintResultLayouts.get(i).getLayoutParams();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.weight = 1;
            constraintResultLayouts.get(i).setLayoutParams(params);
        }
        for (int i = _countMoneyIncrease; i < _maxCountMoneyIncrease; i++)
        {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) constraintLayouts.get(i).getLayoutParams();
            params.width = 0;
            params.weight = 0;
            constraintLayouts.get(i).setLayoutParams(params);

            params =
                    (LinearLayout.LayoutParams) constraintResultLayouts.get(i).getLayoutParams();
            params.width = 0;
            params.weight = 0;
            constraintResultLayouts.get(i).setLayoutParams(params);
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
        {
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                _seekBarCountMIncrease.getLayoutParams().width = displaymetrics.widthPixels / 2;
            }
            else
            {
                _seekBarCountMIncrease.getLayoutParams().width = displaymetrics.widthPixels / 4;
            }
        }
    }

    private void FillSeekBarCount()
    {
        if (_moneyIncreases.size() < _maxCountMoneyIncrease)
        {
            _seekBarCountMIncrease.setMax(_moneyIncreases.size());
            if (_countMoneyIncrease > _moneyIncreases.size())
            {
                _seekBarCountMIncrease.setProgress(_moneyIncreases.size());
                _countMoneyIncrease = _moneyIncreases.size();
                GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
                globalVar.get_moneyIncomingAndCostSettingsHT().set_countMoneyIncrease(_countMoneyIncrease);
            }
        }

        _seekBarCountMIncrease.setProgress(_countMoneyIncrease);
        ChangeVisibleCountViews();

        _seekBarCountMIncrease.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                _countMoneyIncrease = _seekBarCountMIncrease.getProgress();
                GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
                globalVar.get_moneyIncomingAndCostSettingsHT().set_countMoneyIncrease(_countMoneyIncrease);
                ChangeVisibleCountViews();

                CalcShift();
                FillHeader();
                FillAdapterMI();
                FillResult();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void FillSeekBarMIncrease()
    {
        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        Long selMoneyIncreaseId = globalVar.get_moneyIncomingAndCostSettingsHT().get_selMoneyIncreaseId();

        if (_typeData == EnumTypeData.Fact &&
                (selMoneyIncreaseId == null || selMoneyIncreaseId == 0))
        {
            selMoneyIncreaseId = _moneyIncreases.get(_moneyIncreases.size() - 1).get_id();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(selMoneyIncreaseId);
        }

        _seekBarMIncrease.setMax(_moneyIncreases.size() - 1);

        for (int i = 0; i < _moneyIncreases.size(); i++)
            if (Objects.equals(_moneyIncreases.get(i).get_id(), selMoneyIncreaseId))
            {
                _seekBarMIncrease.setProgress(i);
                break;
            }

        CalcShift();
        FillHeader();
        FillAdapterMI();
        FillResult();

        _seekBarMIncrease.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
                globalVar.get_moneyIncomingAndCostSettingsHT().set_selMoneyIncreaseId(_moneyIncreases.get(i).get_id());

                CalcShift();
                FillHeader();
                FillAdapterMI();
                FillResult();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void CalcShift()
    {
        int index = _seekBarMIncrease.getProgress();

        shift = (_countMoneyIncrease - 1) / 2;
        activeCol = (_countMoneyIncrease - 1) / 2;

        for (int i = 0; i < (_countMoneyIncrease - 1) / 2; i++)
            if (index - i <= 0)
            {
                activeCol--;
                shift--;
            }

        for (int i = 0; i < _countMoneyIncrease; i++)
            if (index + i - shift >= _moneyIncreases.size())
            {
                shift++;
                activeCol++;
            }
    }

    private void FillResult()
    {
        for (int i = 0; i < _countMoneyIncrease; i++)
            FillResultOnce(i, false);
        for (int i = _countMoneyIncrease; i < _maxCountMoneyIncrease; i++)
            FillResultOnce(i, true);
    }

    public void FillResultOnce(int i, boolean clear)
    {
        if (_moneyIncreases.size() <= i)
            return;

        if (_seekBarMIncrease.getProgress() + i - shift < _moneyIncreases.size() && !clear)
        {
            if (treeView.getAdapter() != null)
            {
                String sSum;

                if (_typeData == EnumTypeData.Plan)
                    sSum = CustomStaticClass.getIntRubleSum(
                            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).getSum(i).intValue(), true);
                else
                    sSum = CustomStaticClass.getRubleSum(
                                    ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).getSum(i), true);

                GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
                if (globalVariable.get_moneyIncomingAndCostSettingsHT().get_showPercent())
                {
                    sSum += " (";
                    DecimalFormat twoDForm = new DecimalFormat("#.##");
                    int val1 = ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).getSum(i).intValue();
                    int val2 = _moneyIncreases.get(_seekBarMIncrease.getProgress() + i - shift).get_value();
                    sSum += twoDForm.format(100f * val1 / val2);
                    sSum += "%)";
                }

                textViewResult.get(i).setText(sSum);
            }
            else
                textViewResult.get(i).setText("");
        }
        else
            textViewIncrease.get(i).setText("");
    }

    private void FillHeader()
    {
        delimiter = ' ';

        for (int i = 0; i < _countMoneyIncrease; i++)
            FileHeaderOnce(i, false);
        for (int i = _countMoneyIncrease; i < _maxCountMoneyIncrease; i++)
            FileHeaderOnce(i, true);
    }

    private void ChangeTextViewHeight()
    {
        int maxLines = 1;
        for (int i = 0; i < _countMoneyIncrease; i++)
            maxLines = Math.max(maxLines, textViewIncrease.get(i).getLineCount());
        for (int i = 0; i < _countMoneyIncrease; i++)
            textViewIncrease.get(i).setMaxLines(maxLines);

        if (maxLines == 2)
        {
            delimiter = '\n';
            for (int i = 0; i < _countMoneyIncrease; i++)
                FileHeaderOnce(i, false, delimiter);
        }

        materialCardViewHeader.getLayoutParams().height = (int) (maxLines == 1 ?
                getContext().getResources().getDimensionPixelSize(R.dimen.MoneyIncomingTopTextView1RowHeight) :
                getContext().getResources().getDimensionPixelSize(R.dimen.MoneyIncomingTopTextView2RowHeight));
    }

    private void FileHeaderOnce(int i, boolean clear) { FileHeaderOnce(i, clear, ' '); }
    private void FileHeaderOnce(int i, boolean clear, Character delimiter)
    {
        if (_moneyIncreases.size() <= i)
            return;

        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();

        if (_seekBarMIncrease.getProgress() + i - shift < _moneyIncreases.size() && !clear)
            textViewIncrease.get(i).setText(
                    _moneyIncreases.get(_seekBarMIncrease.getProgress() + i - shift).
                            getSValue(delimiter, _typeData, globalVar.get_moneyIncomingAndCostSettingsHT().get_periodData()));
        else
            textViewIncrease.get(i).setText("");
    }

    private void FillAdapterMI()
    {
        ArrayList<Long> array = new ArrayList<>() ;
        for (int i = 0; i < _maxCountMoneyIncrease; i++)
            array.add(i, -1L);

        int index = _seekBarMIncrease.getProgress();

        for (int i = 0; i < _countMoneyIncrease; i++)
            array.set(i, _moneyIncreases.get(i + index - shift).get_id());

        Long prevIncreaseId = -1L;
        if (index - 1 - shift >= 0)
            prevIncreaseId = _moneyIncreases.get(index - 1 - shift).get_id();
        array.add(prevIncreaseId);

        Hashtable<Long, MoneyIncrease> htMoneyIncrease = new Hashtable<>();
        for (MoneyIncrease mIncrease: _moneyIncreases)
            htMoneyIncrease.put(mIncrease.get_id(), mIncrease);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        MoneyIncomingAndCostSettings moneyIncomingSettings =
                globalVariable.get_moneyIncomingAndCostSettingsHT().Clone();

        MoneyIncomingAndCostAdapter adapter = new MoneyIncomingAndCostAdapter(getContext(),
                this, array,
                _moneyIncreases.size() > 0 ? _moneyIncreases.get(0).get_id() : 0,
                _maxCountMoneyIncrease,
                activeCol,
                htMoneyIncrease,
                moneyIncomingSettings);

        treeView.setAdapter(adapter);
    }
    //endregion

    //region IFragmentPlanFact
    public boolean needPlanButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettings().get_typeData().equals(EnumTypeData.Fact);
    }

    @Override
    public View.OnClickListener onClickToSetPlan()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            switch (globalVariable.get_enumMoneyIncAndCostSetting())
            {
                case IncomingFact:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingPlan);
//                    globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_incoming);
                    break;
                case ExpenseFact:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpensePlan);
//                    globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_costs);
                    break;
            }

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    public boolean needFactButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettings().get_typeData().equals(EnumTypeData.Plan);
    }

    @Override
    public View.OnClickListener onClickToSetFact()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            switch (globalVariable.get_enumMoneyIncAndCostSetting())
            {
                case IncomingPlan:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingFact);
//                    globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_incoming_fact);
                    break;
                case ExpensePlan:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpenseFact);
//                    globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_costs_fact);
                    break;
            }

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

//    @Override
//    public EnumTypeData GetTypeData() { return _typeData; }
    //endregion

    //region IFragmentManageMoneyCategory
    @Override
    public View.OnClickListener onClickToManageMoneyCategory()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            if (getActivity() != null)
                ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_category,
                        globalVar.get_moneyIncomingAndCostSettingsHT().get_fragmentId());
        };
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyIncoming)
        {
            MoneyIncomingAndCostAdapter adapter = (MoneyIncomingAndCostAdapter) treeView.getAdapter();
            if (adapter != null)
                adapter.AddItem((MoneyIncoming) object);

            return true;
        }

        if (object instanceof MoneyIncomingAndCostSettings)
        {
            MoneyIncomingAndCostSettings moneyIncomingSettings = (MoneyIncomingAndCostSettings) object;
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (moneyIncomingSettings.get_typeCategory().equals(EnumTypeCategory.Increase))
            {
                if (moneyIncomingSettings.get_typeData().equals(EnumTypeData.Plan))
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingPlan);
                else
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingFact);
            }
            else if (moneyIncomingSettings.get_typeCategory().equals(EnumTypeCategory.Cost))
            {
                if (moneyIncomingSettings.get_typeData().equals(EnumTypeData.Plan))
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpensePlan);
                else
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpenseFact);
            }
            globalVariable.set_moneyIncomingAndCostSettings(moneyIncomingSettings);

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyIncoming)
        {
            MoneyIncomingAndCostAdapter adapter = (MoneyIncomingAndCostAdapter) treeView.getAdapter();
            if (adapter != null)
                adapter.UpdateItem((MoneyIncoming) object);

            return true;
        }
        else if (object instanceof  MoneyIncrease)
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();

            _moneyIncreases.set(editedPos, (MoneyIncrease) object);
            editedTextView.setText(_moneyIncreases.get(editedPos).getSValue(delimiter, _typeData,
                    globalVar.get_moneyIncomingAndCostSettingsHT().get_periodData()));

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyIncoming)
        {
            MoneyIncomingAndCostAdapter adapter = (MoneyIncomingAndCostAdapter) treeView.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset((MoneyIncoming) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion

    //region IFragmentExpandAll
    public boolean needExpandButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
//        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
//                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(globalVar.get_enumMoneyIncAndCostSetting()).get_showExpandButton();
    }

    @Override
    public View.OnClickListener onClickExpandAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT(globalVar.get_enumMoneyIncAndCostSetting()).set_showExpandButton(false);

            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            IExpandAllAdapter adapter = ((IExpandAllAdapter) treeView.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(false);
        };
    }
    //endregion

    //region IFragmentCollapseAll
    public boolean needCollapseButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
//        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
//                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return !globalVar.get_moneyIncomingAndCostSettingsHT(globalVar.get_enumMoneyIncAndCostSetting()).get_showExpandButton();
    }

    @Override
    public View.OnClickListener onClickCollapseAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT(globalVar.get_enumMoneyIncAndCostSetting()).set_showExpandButton(true);

            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) treeView.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
        };
    }
    //endregion

    //region IFragmentShowIncreasePercent
    private EnumMoneyIncAndCostSetting get_enumMoneyIncAndCostSetting()
    {
        if (_typeCategory.equals(EnumTypeCategory.Increase))
        {
            if (_typeData.equals(EnumTypeData.Plan))
                return EnumMoneyIncAndCostSetting.IncomingPlan;
            else
                return EnumMoneyIncAndCostSetting.IncomingFact;
        }
        else
        {
            if (_typeData.equals(EnumTypeData.Plan))
                return EnumMoneyIncAndCostSetting.ExpensePlan;
            else
                return EnumMoneyIncAndCostSetting.ExpenseFact;
        }
    }
    public boolean ShowIncreasePercent(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (global.get_hasUnpaidSubscription())
            return false;

        return !global.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).get_showIncreasePercent() &&
                !global.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).get_showPercent();
    }
    public boolean HideIncreasePercent(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (global.get_hasUnpaidSubscription())
            return false;

        return false;
    }
    @Override
    public View.OnClickListener onClickToShowIncreasePercent()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showIncreasePercent(true);
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showPercent(false);
            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyIncomingAndCostSettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    public View.OnClickListener onClickToHideIncreasePercent()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showIncreasePercent(false);
            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyIncomingAndCostSettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowPercent
    @Override
    public boolean ShowPercent(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (global.get_hasUnpaidSubscription())
            return false;

        return global.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).get_showIncreasePercent();
    }

    @Override
    public boolean HidePercent(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();

        if (global.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (global.get_hasUnpaidSubscription())
            return false;

        return global.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).get_showPercent();
    }

    @Override
    public View.OnClickListener onClickToShowPercent()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showPercent(true);
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showIncreasePercent(false);
            FillResult();
            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyIncomingAndCostSettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    @Override
    public View.OnClickListener onClickToHidePercent()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showPercent(false);
            FillResult();
            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyIncomingAndCostSettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowBlankData
    @Override
    public boolean ShowBlankData(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return !globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).get_showBlankData();
    }

    @Override
    public boolean HideBlankData(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).get_showBlankData();
    }

    @Override
    public View.OnClickListener onClickToShowBlankData()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showBlankData(true);
            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyIncomingAndCostSettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    @Override
    public View.OnClickListener onClickToHideBlankData()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_showBlankData(false);
            ((MoneyIncomingAndCostAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyIncomingAndCostSettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentLegend
    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentMoneyIncomingLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }

    //endregion

    //region IFragmentIncrease
    @Override
    public View.OnClickListener onClickToIncrease()
    {
        return view ->
        {
            if (getActivity() != null)
                ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_increase, 0);
        };
    }
    //endregion

    //region Touch
//    public View.OnTouchListener onTouchLayout()
//    {
//        return (view, motionEvent) ->
//        {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
//                startMoveXLayout = motionEvent.getX();
//            else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
//                startMoveXLayout = -1;
//            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
//            {
//                if (startMoveXLayout != -1)
//                {
//                    float delta = motionEvent.getX() - startMoveXLayout;
//
//                    if (delta > baseDelta)
//                    {
//                        Log.d("delta1", String.valueOf(delta));
//                        _seekBarMIncrease.setProgress(_seekBarMIncrease.getProgress() - 1);
//                        startMoveXLayout = motionEvent.getX();
//                    }
//                    else if (delta < -baseDelta)
//                    {
//                        Log.d("delta2", String.valueOf(delta));
//                        _seekBarMIncrease.setProgress(_seekBarMIncrease.getProgress() + 1);
//                        startMoveXLayout = motionEvent.getX();
//                    }
//                }
//            }
//
//            return false;
//        };
//    }
//
//    public View.OnTouchListener onTouchItem()
//    {
//        return (view, motionEvent) ->
//        {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
//                startMoveXItem = motionEvent.getX();
//            else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
//                startMoveXItem = -1;
////                if (motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN)
////                {
////                    coords00 = new MotionEvent.PointerCoords();
////                    coords10 = new MotionEvent.PointerCoords();
////                    motionEvent.getPointerCoords(0, coords00);
////                    motionEvent.getPointerCoords(1, coords10);
////                }
////                else if (motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP)
////                {
////                    coords00 = null;
////                    coords10 = null;
////                }
//            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
//            {
////                    if (coords00 != null && coords10 != null)
////                    {
////                        MotionEvent.PointerCoords coords01 = new MotionEvent.PointerCoords();
////                        MotionEvent.PointerCoords coords11 = new MotionEvent.PointerCoords();
////                        motionEvent.getPointerCoords(0, coords01);
////                        motionEvent.getPointerCoords(1, coords11);
////                        float delta1 = coords01.x - coords00.x;
////                        float delta2 = coords11.x - coords10.x;
////
////                        float delta;
////                        if (coords00.x < coords10.x)
////                            delta = delta1 - delta2;
////                        else
////                            delta = delta2 - delta1;
////
////                        if (delta > baseDelta)
////                            _seekBarCountMIncrease.setProgress(_seekBarCountMIncrease.getProgress() - 1);
////                        else if (delta < -baseDelta)
////                            _seekBarCountMIncrease.setProgress(_seekBarCountMIncrease.getProgress() + 1);
////                    }
//                if (startMoveXItem != -1)
//                {
//                    float delta = motionEvent.getX() - startMoveXItem;
//
//                    if (delta > baseDelta)
//                    {
//                        Log.d("delta1", String.valueOf(delta));
//                        _seekBarMIncrease.setProgress(_seekBarMIncrease.getProgress() - 1);
//                        startMoveXItem = motionEvent.getX();
//                    }
//                    else if (delta < -baseDelta)
//                    {
//                        Log.d("delta2", String.valueOf(delta));
//                        _seekBarMIncrease.setProgress(_seekBarMIncrease.getProgress() + 1);
//                        startMoveXItem = motionEvent.getX();
//                    }
//                }
//            }
//
//            return false;
//        };
//    }
    //endregion

    //region IFragmentShowIncomingAndCostBase
    @Override
    public boolean ShowIncomingAndCostBase(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).
                get_categoryType().equals(EnumCategoryType.All);
    }

    @Override
    public View.OnClickListener onClickToShowIncomingAndCostBase()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_typeCategory(EnumCategoryType.Base);
            FillHeader();
            FillAdapterMI();
            FillResult();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowIncomingAndCostAdvanced
    @Override
    public boolean ShowIncomingAndCostAdvanced(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).
                get_categoryType().equals(EnumCategoryType.Base);
    }

    @Override
    public View.OnClickListener onClickToShowIncomingAndCostAdvanced()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_typeCategory(EnumCategoryType.Advanced);
            FillHeader();
            FillAdapterMI();
            FillResult();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowIncomingAndCostAdvancedPlus
    @Override
    public boolean ShowIncomingAndCostAdvancedPlus(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).
                get_categoryType().equals(EnumCategoryType.Advanced);
    }

    @Override
    public View.OnClickListener onClickToShowIncomingAndCostAdvancedPlus()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_typeCategory(EnumCategoryType.AdvancedPlus);
            FillHeader();
            FillAdapterMI();
            FillResult();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowIncomingAndCostProject
    @Override
    public boolean ShowIncomingAndCostProject(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).
                get_categoryType().equals(EnumCategoryType.AdvancedPlus);
    }

    @Override
    public View.OnClickListener onClickToShowIncomingAndCostProject()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_typeCategory(EnumCategoryType.Project);
            FillHeader();
            FillAdapterMI();
            FillResult();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowIncomingAndCostProjectPlus
    @Override
    public boolean ShowIncomingAndCostProjectPlus(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).
                get_categoryType().equals(EnumCategoryType.Project);
    }

    @Override
    public View.OnClickListener onClickToShowIncomingAndCostProjectPlus()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_typeCategory(EnumCategoryType.ProjectPlus);
            FillHeader();
            FillAdapterMI();
            FillResult();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    // region IFragmentShowIncomingAndCostAll
    @Override
    public boolean ShowIncomingAndCostAll(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return false;

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyIncomingAndCostSettingsHT(get_enumMoneyIncAndCostSetting()).
                get_categoryType().equals(EnumCategoryType.ProjectPlus);
    }

    @Override
    public View.OnClickListener onClickToShowIncomingAndCostAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyIncomingAndCostSettingsHT().set_typeCategory(EnumCategoryType.All);
            FillHeader();
            FillAdapterMI();
            FillResult();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentDohodRashod
    @Override
    public View.OnClickListener onClickToSetDohod()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            switch (globalVariable.get_enumMoneyIncAndCostSetting())
            {
                case ExpensePlan:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingPlan);
                    break;
                case ExpenseFact:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingFact);
                    break;
            }

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
            ((MainActivity) getActivity()).SetTitle(R.id.navigation_money_incoming);
        };
    }

    @Override
    public View.OnClickListener onClickToSetRashod()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            switch (globalVariable.get_enumMoneyIncAndCostSetting())
            {
                case IncomingPlan:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpensePlan);
                    break;
                case IncomingFact:
                    globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.ExpenseFact);
                    break;
            }

            FillData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
            ((MainActivity) getActivity()).SetTitle(R.id.navigation_money_costs);
        };
    }

    @Override
    public EnumTypeCategory GetTypeCategory(Context context) {
        return _typeCategory;
    }
    //endregion

    //region IFragmentPlanFactMoney
    @Override
    public View.OnClickListener onClickToPlanFactMoney()
    {
        return view ->
        {
            if (getActivity() != null)
            {
                GlobalClass globVar = (GlobalClass) getContext().getApplicationContext();
                globVar.set_enumMoneyPlanFactTypeCategory(_typeCategory);
                globVar.get_moneyPlanFactSettings().set_customPeriodClass(
                        new CustomPeriodClass(CustomPeriodClass.MONTH_ID, ""));
                switch (_typeCategory)
                {
                    case Increase:
                        ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_planfact_income,
                                R.id.navigation_money_incoming);
                        break;
                    case Cost:
                        ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_planfact_cost,
                                R.id.navigation_money_incoming);
                        break;
                }
            }
        };
    }
    //endregion
}