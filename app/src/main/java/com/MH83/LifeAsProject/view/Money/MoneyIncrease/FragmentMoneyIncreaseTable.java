package com.MH83.LifeAsProject.view.Money.MoneyIncrease;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyIncreaseAdapter;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;

import java.util.ArrayList;

public class FragmentMoneyIncreaseTable extends Fragment
        implements IFragmentViewCustom<MoneyIncrease>
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_increase_table2;
    //endregion

    //region Fragment parameters
    private ArrayList<MoneyIncrease> _dataSet;
    //endregion

    //region Fragment Components
    private RecyclerView rView;
    //endregion

    //region Fragment settings
    private int _scrollPosition;

    public Long getIncreaseId()
    {
        MoneyIncreaseAdapter moneyIncreaseAdapter = ((MoneyIncreaseAdapter) rView.getAdapter());
        if (moneyIncreaseAdapter != null)
        {
            MoneyIncrease moneyIncrease = moneyIncreaseAdapter.getCurrentItem();
            if (moneyIncrease != null)
                return moneyIncrease.get_id();
            else
                return 0L;
        }
        else
            return 0L;
    }
    //endregion

    //region Constructors
    public FragmentMoneyIncreaseTable() { }

    public FragmentMoneyIncreaseTable(ArrayList<MoneyIncrease> dataSet, int scrollPosition)
    {
        _dataSet = dataSet;
        _scrollPosition = scrollPosition;
    }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        rView = view.findViewById(R.id.listViewR);
        MoneyIncreaseAdapter adapter = new MoneyIncreaseAdapter(getContext(), _dataSet, this);
        rView.setAdapter(adapter);

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyIncreaseLineBasedBorderLineDp);

        MoneyIncreaseSimpleCallback misc = new MoneyIncreaseSimpleCallback(rView, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(rView);

        rView.scrollToPosition(_scrollPosition);

        return view;
    }
    //endregion

    //region Click to button
    @Override
    public boolean refreshViewOnAdd(MoneyIncrease moneyIncrease)
    {
        MoneyIncreaseAdapter moneyIncreaseAdapter = ((MoneyIncreaseAdapter) rView.getAdapter());
        if (moneyIncreaseAdapter != null)
        {
            int pos = moneyIncreaseAdapter.AddItem(moneyIncrease);
            rView.smoothScrollToPosition(pos);
        }

        return true;
    }

    @Override
    public boolean refreshViewOnUpdate(MoneyIncrease moneyIncrease)
    {
        MoneyIncreaseAdapter moneyIncreaseAdapter = ((MoneyIncreaseAdapter) rView.getAdapter());
        if (moneyIncreaseAdapter != null)
        {
            int pos = moneyIncreaseAdapter.UpdateItem(moneyIncrease);
            if (pos != 0)
                rView.smoothScrollToPosition(pos);
        }

        return true;
    }

    @Override
    public boolean refreshViewOnDelete(MoneyIncrease moneyIncrease)
    {
        MoneyIncreaseAdapter moneyIncreaseAdapter = ((MoneyIncreaseAdapter) rView.getAdapter());
        if (moneyIncreaseAdapter != null)
            moneyIncreaseAdapter.DeleteFromDataset(moneyIncrease);

        return true;
    }

    @Override
    public boolean refreshViewOnSelect(MoneyIncrease moneyIncrease) { return false; }

    public int getScrollPosition()
    {
        if (rView.getLayoutManager() == null)
            return 0;
        else
            return  ((LinearLayoutManager)rView.getLayoutManager()).findFirstVisibleItemPosition();
    }

    public void GoToFragmentMoneyIncoming()
    {
        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_incoming);
    }
    //endregion
}