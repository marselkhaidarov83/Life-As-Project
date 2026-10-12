package com.MH83.LifeAsProject.view.Money.MoneyBoard;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyBoardSettingsAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyBoardSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

public class FragmentMoneyBoardSettings extends CustomPopupBottomFragment<MoneyBoardSettings>
    implements IFragmentViewCustom
{
    //region Constants
    private static final int _layoutId = R.layout.popup_settings_money_board;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private RecyclerView _recyclerView;
    private ImageView _imageViewMascotToSettings;
    //endregion

    //region Start Fragment
    public FragmentMoneyBoardSettings()
    {
        super(_layoutId,
                R.string.SettingsMoneyBoard,
                R.string.SettingsMoneyBoard,
                0);
    }

    public FragmentMoneyBoardSettings(AppCompatActivity activity,
                                      MoneyBoardSettings moneyBoardSettings,
                                      IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity,
                _layoutId,
                moneyBoardSettings,
                EnumTypeAction.Insert,
                null,
                null,
                R.string.SettingsMoneyBoard,
                R.string.SettingsMoneyBoard,
                0,
                iFragmentViewCustom,
                null,
                null);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopupSettings_Capital_Width) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    }

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewMascotToSettings.setOnClickListener(view ->
                new FragmentMoneyBoardLegendToSettingsPopup((AppCompatActivity) getContext()).PopupShow());
    }

    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _recyclerView = view.findViewById(R.id.recycleViewBoard);
        _imageViewMascotToSettings = view.findViewById(R.id.imageViewMascotToSettings);
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
            _recyclerView.setEnabled(false);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        height += getResources().getDimension(R.dimen.UnknownToolbarHeight);
        height -= getResources().getDimension(R.dimen.Fragment_MainActivity_Toolbar_Height) +
                getResources().getDimension(R.dimen.MoneyDreamBottomToolbarHeight);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            height -= getResources().getDimension(R.dimen.UnknownPortraitHeight);
        else
            height -= getResources().getDimension(R.dimen.UnknownLandscapeHeight);

        if (_recyclerView != null)
        {
            ViewGroup.LayoutParams lp = _recyclerView.getLayoutParams();
            lp.height = (int) Math.min(height,
                    getResources().getDimension(R.dimen.Board_Popup_RecyclerView_Linear_Height));
            _recyclerView.setLayoutParams(lp);
        }

        if (!global.get_showMascot())
            _imageViewMascotToSettings.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();
    }

    //endregion

    //region Set Params

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        SetBoardAdapter();
    }

    private void SetBoardAdapter()
    {
        MoneyBoardSettingsAdapter adapter = new MoneyBoardSettingsAdapter(
                getContext(),
                _data.get_moneyBoards(),
                this);
        _recyclerView.setAdapter(adapter);

//        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
//        if (!gVar.get_hasUnpaidSubscription())
//        {
//            int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyPNLLineBasedBorderLineDp);
//            MoneyTransactionSimpleCallback misc = new MoneyTransactionSimpleCallback(_recyclerView, basedBorderLineLeftDp);
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
//            itemTouchHelper.attachToRecyclerView(_recyclerView);
//        }
    }
    //endregion

    //region Check Params
    @Override
    protected boolean CheckFormValues()
    {
        return true;
    }
    //endregion

    //region Get Params

    //endregion

    //region Save Params
    @Override
    protected MoneyBoardSettings GetDataFromForm()
    {
        return new MoneyBoardSettings(((MoneyBoardSettingsAdapter) _recyclerView.getAdapter()).get_dataSet());
    }
    //region

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object) {
        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object) {
        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object) {
        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) {
        return false;
    }
    //endregion
}
