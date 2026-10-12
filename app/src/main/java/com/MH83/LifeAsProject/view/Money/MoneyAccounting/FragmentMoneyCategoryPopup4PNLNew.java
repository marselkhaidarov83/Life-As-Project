package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyCategoryPopupAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;

public class FragmentMoneyCategoryPopup4PNLNew extends CustomPopupBottomFragment<MoneyCategory>
        implements IFragmentViewCustom<MoneyCategory>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_category4pnl_new;
    //endregion

    //region Fragment parameters
    private Long _categoryId;
    private EnumTypeCategory _typeCategory;
    RecyclerView rView;
    ImageView _imageViewMascotToChange2;
    ArrayList<MoneyCategory> _dataSet;
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    //endregion

    //region Constructors
    public FragmentMoneyCategoryPopup4PNLNew()
    {
        super(_layoutId,
                R.string.fragmentMoneyCategoryPopupSelectTitle,
                R.string.fragmentMoneyCategoryPopupSelectTitle,
                0);
    }

    public FragmentMoneyCategoryPopup4PNLNew(AppCompatActivity activity,
                                             MoneyCategory data,
                                             EnumTypeAction typeAction,
                                             EnumTypeAction typeAction2,
                                             EnumTypeAction typeAction3,
                                             IFragmentViewCustom iFragmentViewCustom,
                                             IFragmentViewCustom iFragmentViewCustom2,
                                             IFragmentViewCustom iFragmentViewCustom3,
                                             EnumTypeCategory typeCategory,
                                             Long categoryId)
    {
        super(activity, _layoutId, data,
                typeAction,
                typeAction2,
                typeAction3,
                R.string.fragmentMoneyCategoryPopupSelectTitle,
                R.string.fragmentMoneyCategoryPopupSelectTitle,
                0,
                iFragmentViewCustom,
                iFragmentViewCustom2,
                iFragmentViewCustom3);

        _typeCategory = typeCategory;
        _categoryId = categoryId;

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_categoryId(categoryId);
        globalVariable.set_typeCategory4PNL(typeCategory);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _categoryId = globalVariable.get_categoryId();
        _typeCategory = globalVariable.get_typeCategory4PNL();

        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                _typeCategory, true, false,
                true, EnumCategoryType.All,null);
        _dataSet = MoneyCategoryDataSource.LoadDataParent(
                getContext(), false, moneyCategorySettings);
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        rView = view.findViewById(R.id.listViewR);
        _imageViewMascotToChange2 = view.findViewById(R.id.imageViewMascotToChange2);

        _focusedViewToAdd = null;
        _focusedViewToUpdate = null;
        _showInputToAdd = false;
        _showInputToUpdate = false;
    }


    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        MoneyCategoryPopupAdapter adapter = new MoneyCategoryPopupAdapter(
                getContext(), _typeCategory, _dataSet, this);
        rView.setAdapter(adapter);
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] { _categoryId } );
    }

    @Override
    public void PopupShow()
    {
        super.PopupShow();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width) /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewMascotToChange2.setOnClickListener(view ->
                new FragmentMoneyCategory4PNLLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
    }
    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        SetCategoryId((Long) objects[0]);
    }

    public void SetCategoryId(Long categoryId)
    {
        _categoryId = categoryId;
    }
    public void SelectCategoryId(Long categoryId)
    {
        _categoryId = categoryId;
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_categoryId(categoryId);

        SelectItem();
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return false;
    }

    protected boolean CheckFormValues()
    {
        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
        return true;
    }
    //endregion

    //region Get Params
    private Long getCategoryId() { return _categoryId; }
    //endregion

    //region Save Params
    protected MoneyCategory GetDataFromForm()
    {
        try
        {
            return MoneyCategoryDataSource.LoadOne(getContext(), getCategoryId());
        }
        catch (Exception e) { return null; }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(MoneyCategory moneyCategory) { return false; }

    @Override
    public boolean refreshViewOnUpdate(MoneyCategory moneyCategory) { return false; }

    @Override
    public boolean refreshViewOnDelete(MoneyCategory moneyCategory) { return false; }

    @Override
    public boolean refreshViewOnSelect(MoneyCategory moneyCategory)
    {
        _categoryId = moneyCategory.get_id();
        return true;
    }
    //endregion
}