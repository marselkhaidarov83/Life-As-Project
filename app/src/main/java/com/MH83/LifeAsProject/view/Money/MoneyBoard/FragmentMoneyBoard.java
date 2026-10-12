package com.MH83.LifeAsProject.view.Money.MoneyBoard;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyBoardAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyBoardDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.model.Money.MoneyBoard;
import com.MH83.LifeAsProject.model.Money.MoneyBoardSettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;

import java.util.ArrayList;

public class FragmentMoneyBoard extends CustomFragment implements IFragmentViewCustom<Object>,
        IFragmentLegend, IFragmentSettings
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_board;
    //endregion

    //region Fragment parameters
    private ArrayList<MoneyBoard> _dataSet;
    //endregion

    //region Fragment Components
    private RecyclerView rView;
    //endregion

    //region Constructors
    public FragmentMoneyBoard() { }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        globalVariable.set_IFragmentViewCustom(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);
        FindComponents(view);

        LoadData();

        return view;
    }

    private void LoadData()
    {
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
        _dataSet = MoneyBoardDataSource.LoadData(getContext(), true);

        ArrayList<MoneyBoard> data = new ArrayList<>();
        for (MoneyBoard item: _dataSet)
            if (gVar.get_hasUnpaidSubscription() || item.is_visible())
                data.add(item);
        MoneyBoardAdapter adapter = new MoneyBoardAdapter(getContext(), data,
                this, GetHeightDream());
        rView.setAdapter(adapter);

//        if (MoneyDreamDataSource.GetCount(getContext()) == 0 &&
//                !globalVariable.get_legendShowed().equals("FragmentMoneyDream"))
//        {
//            ClickToLegend();
//            globalVariable.set_legendShowed("FragmentMoneyDream");
//        }
    }

    private void FindComponents(View view)
    {
        rView = view.findViewById(R.id.recycleViewBoard);
    }

    private int GetHeightDream()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int heightDream = displaymetrics.heightPixels;
        heightDream += getResources().getDimension(R.dimen.UnknownToolbarHeight);
        heightDream -= getResources().getDimension(R.dimen.Fragment_MainActivity_Toolbar_Height) +
                getResources().getDimension(R.dimen.MoneyDreamBottomToolbarHeight);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            heightDream -= getResources().getDimension(R.dimen.UnknownPortraitHeight);
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
                heightDream /= 5;
            else
                heightDream /= 3;
        }
        else
        {
            heightDream -= getResources().getDimension(R.dimen.UnknownLandscapeHeight);
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
                heightDream /= 3;
            else
                heightDream += getResources().getDimension(R.dimen.HalfPadding);
        }

        return heightDream;
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyBoardSettings)
        {
            LoadData();
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion

    //region IFragmentLegend
    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentMoneyBoardLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            MoneyBoardSettings moneyBoardSettings = new MoneyBoardSettings(new ArrayList<>(_dataSet));
            new FragmentMoneyBoardSettings(
                    (AppCompatActivity) getContext(),
                    moneyBoardSettings,
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton()
    {
        return true;
    }
    //endregion
}