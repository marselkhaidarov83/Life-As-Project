package com.MH83.LifeAsProject.view.Money.MoneyPortfolio;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerEnumStatusAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyCategoryAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyPortfolioAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPortfolioDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyPortfolio;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyPortfolioPopupNew extends CustomPopupBottomFragment<MoneyPortfolio>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_portfolio_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    private Spinner _spinnerParent;
    private Spinner _spinnerWallet;
    private Spinner _spinnerCategory;
    private Spinner _spinnerSubCategory;
    private Spinner _spinnerStatus;
    private EditText _editTextName;
    private EditText _editTextComment;
    private TableLayout _tableLayout;
    private TableRow _tableRowWallet;
    private TableRow _tableRowCategory;
    private TableRow _tableRowSubCategory;
    private TextView _textViewWallet;
    private TextView _textViewCategory;
    private TextView _textViewSubCategory;
    //endregion

    //region Constructors
    public FragmentMoneyPortfolioPopupNew()
    {
        super(_layoutId,
                R.string.PopupPortfolioAdd,
                R.string.PopupPortfolioUpdate,
                R.string.errorUpdateMoneyPortfolio);
    }

    public FragmentMoneyPortfolioPopupNew(AppCompatActivity activity,
                                          MoneyPortfolio data,
                                          EnumTypeAction typeAction,
                                          IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.PopupPortfolioAdd,
                R.string.PopupPortfolioUpdate,
                R.string.errorUpdateMoneyPortfolio,
                iFragmentViewCustom,
                null,
                null);
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerParent = view.findViewById(R.id.spinnerParent);
        _spinnerWallet = view.findViewById(R.id.spinnerWallet);
        _spinnerCategory = view.findViewById(R.id.spinnerCategory);
        _spinnerSubCategory = view.findViewById(R.id.spinnerSubCategory);
        _spinnerStatus = view.findViewById(R.id.spinnerStatus);
        _editTextName = view.findViewById(R.id.editTextName);
        _editTextComment = view.findViewById(R.id.editTextComment);
        _tableLayout = view.findViewById(R.id.tableLayout);
        _tableRowWallet = view.findViewById(R.id.tableRowWallet);
        _tableRowCategory = view.findViewById(R.id.tableRowCategory);
        _tableRowSubCategory = view.findViewById(R.id.tableRowSubCategory);
        _textViewWallet = view.findViewById(R.id.textViewWallet);
        _textViewCategory = view.findViewById(R.id.textViewCategory);
        _textViewSubCategory = view.findViewById(R.id.textViewSubCategory);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextName;
        _focusedViewToUpdate = _editTextName;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        SetEnabled();
        SetVisible();
    }

    private void SetEnabled()
    {
        if (_data != null && _data.get_hasChild())
            _spinnerParent.setEnabled(false);
    }

    private void SetVisible()
    {
        if (get_ParentId() == 0)
        {
            _tableRowWallet.setVisibility(View.INVISIBLE);
            _tableRowCategory.setVisibility(View.INVISIBLE);
            _tableRowSubCategory.setVisibility(View.INVISIBLE);
            _textViewWallet.getLayoutParams().height = 0;
            _textViewCategory.getLayoutParams().height = 0;
            _textViewSubCategory.getLayoutParams().height = 0;
            _spinnerWallet.getLayoutParams().height = 0;
            _spinnerCategory.getLayoutParams().height = 0;
            _spinnerSubCategory.getLayoutParams().height = 0;
            _tableRowWallet.setPadding(0, 0, 0, 0);
            _tableRowCategory.setPadding(0, 0, 0, 0);
            _tableRowSubCategory.setPadding(0, 0, 0, 0);

            _tableLayout.getLayoutParams().height =
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Portfolio_Height1);
        }
        else
        {
            _tableRowWallet.setVisibility(View.VISIBLE);
            _tableRowCategory.setVisibility(View.VISIBLE);
            _tableRowSubCategory.setVisibility(View.VISIBLE);
            _textViewWallet.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _textViewCategory.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _textViewSubCategory.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _spinnerWallet.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _spinnerCategory.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _spinnerSubCategory.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            _tableRowWallet.setPadding(0, 0, 0,
                    (int) getContext().getResources().getDimension(R.dimen.BasePadding));
            _tableRowCategory.setPadding(0, 0, 0,
                    (int) getContext().getResources().getDimension(R.dimen.BasePadding));
            _tableRowSubCategory.setPadding(0, 0, 0,
                    (int) getContext().getResources().getDimension(R.dimen.BasePadding));

            _tableLayout.getLayoutParams().height =
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Portfolio_Height2);
        }
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillSpinnerWallet();
        FillSpinnerCategory();
        FillSpinnerParent();
        FillSpinnerStatus();
    }

    private void FillSpinnerWallet()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Wallet, true, false,
                true, EnumCategoryType.All,null);
        ArrayList<MoneyCategory> list = MoneyCategoryDataSource.LoadDataParent(getContext(),
                true, moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerWallet.setAdapter(adapter);
    }

    private void FillSpinnerCategory()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Cost, true, false,
                true, EnumCategoryType.All,null);
        ArrayList<MoneyCategory> list = MoneyCategoryDataSource.LoadDataParent(getContext(),
                false, moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerCategory.setAdapter(adapter);
    }

    private void FillSpinnerSubCategory()
    {
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                EnumTypeCategory.Cost, true, false,
                true, EnumCategoryType.All,null);
        ArrayList<MoneyCategory> list = MoneyCategoryDataSource.LoadDataChildren(getContext(),
                get_CategoryId(), moneyCategorySettings);

        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerSubCategory.setAdapter(adapter);
    }

    private void FillSpinnerParent()
    {
        GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        List<MoneyPortfolio> list =
                MoneyPortfolioDataSource.LoadDataParent(_activity.getApplicationContext(),
                        true, globalVariable.get_moneyPortfolioSettings());
        if (_data != null && _data.get_id() != 0)
            for (MoneyPortfolio item : list)
                if (Objects.equals(item.get_id(), _data.get_id()))
                {
                    list.remove(item);
                    break;
                }

        SpinnerMoneyPortfolioAdapter adapter = new SpinnerMoneyPortfolioAdapter(getContext(), list);
        _spinnerParent.setAdapter(adapter);
    }

    private void FillSpinnerStatus()
    {
        ArrayList<EnumStatus> list = new ArrayList<>();
//        list.add(EnumStatus.Blank);
        list.add(EnumStatus.Started);
//        list.add(EnumStatus.Finished);
        list.add(EnumStatus.Irrelevant);

        SpinnerEnumStatusAdapter adapter = new SpinnerEnumStatusAdapter(getContext(), list);
        _spinnerStatus.setAdapter(adapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width),
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Category_Width),
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

        _spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                FillSpinnerSubCategory();
                if (_data != null && _data.get_subCategoryId() != 0L)
                    set_subCategory(_data.get_subCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        _spinnerParent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                SetVisible();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {} );
    }

    protected void SetValues(Object[] objects)
    {
        if (_data != null)
        {
            set_Parent(_data.get_parentId());
            set_Status(_data.get_status());
            set_Name(_data.get_name());
            set_Comment(_data.get_comment());
            set_Wallet(_data.get_walletId());
            MoneyCategory moneyCategory = MoneyCategoryDataSource.LoadOne(getContext(), _data.get_subCategoryId());
            if (moneyCategory != null)
            {
                set_Category(moneyCategory.get_parentId());
                FillSpinnerSubCategory();
            }
            set_subCategory(_data.get_subCategoryId());
        }
        else
        {
            set_Parent(0L);
            set_Status(EnumStatus.Blank);
            set_Name("");
            set_Comment("");
            set_Wallet(0L);
            set_Category(0L);
            FillSpinnerSubCategory();
            set_subCategory(0L);
        }
    }

    private void set_Parent(Long parentId)
    {
        for (int i = 0; i < _spinnerParent.getAdapter().getCount(); i++)
            if (((MoneyPortfolio) _spinnerParent.getAdapter().getItem(i)).get_id().equals(parentId))
            {
                _spinnerParent.setSelection(i);
                break;
            }
    }
    private void set_Status(EnumStatus status)
    {
        for (int i = 0; i < _spinnerStatus.getAdapter().getCount(); i++)
            if (_spinnerStatus.getAdapter().getItem(i).equals(status))
            {
                _spinnerStatus.setSelection(i);
                break;
            }
    }
    private void set_Wallet(Long walletId)
    {
        for (int i = 0; i < _spinnerWallet.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerWallet.getAdapter().getItem(i)).get_id(), walletId))
            {
                _spinnerWallet.setSelection(i);
                break;
            }
    }
    private void set_Category(Long categoryId)
    {
        for (int i = 0; i < _spinnerCategory.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyCategory) _spinnerCategory.getAdapter().getItem(i)).get_id(), categoryId))
            {
                _spinnerCategory.setSelection(i);
                break;
            }
    }
    private void set_subCategory(Long subCategoryId)
    {
        if (_spinnerSubCategory.getAdapter() != null)
            for (int i = 0; i < _spinnerSubCategory.getAdapter().getCount(); i++)
                if (Objects.equals(((MoneyCategory) _spinnerSubCategory.getAdapter().getItem(i)).get_id(), subCategoryId))
                {
                    _spinnerSubCategory.setSelection(i);
                    break;
                }
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }
    private void set_Comment(String comment)
    {
        _editTextComment.setText(comment);
    }
    //endregion

    //region Check Params
    @Override
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        return CheckName();
    }

    private boolean CheckName()
    {
        try
        {
            String name = getName();
            if (name.equals(""))
            {
                _editTextName.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyName), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextName.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectName), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
        if (_data != null && _data.get_parentId() != 0)
            return true;
        else
        {
            if (_data != null && _data.get_hasChild())
            {
                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.errorExistChildsMoneyPortfolio), Toast.LENGTH_SHORT).show();
                return false;
            }
            else
                return true;
        }
    }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        return _data != null ? _data.get_id() : 0;
    }

    private Integer get_Position()
    {
        if (_data == null || !Objects.equals(_data.get_parentId(), get_ParentId()))
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                return dbHelper.QueryMoneyPortfolioMaxPosition(get_ParentId()) + 1;
            }
            catch (Exception e) { return 0; }
        }
        else
            return _data.get_position();
    }

    private Long get_ParentId()
    {
        return  ((MoneyPortfolio) _spinnerParent.getSelectedItem()).get_id();
    }

    private String getName() throws Exception
    {
        try
        {
            return _editTextName.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private String getComment() throws Exception
    {
        try
        {
            return _editTextComment.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private EnumStatus get_status()
    {
        return (EnumStatus) _spinnerStatus.getSelectedItem();
    }
    private Long get_walletId()
    {
        if (get_ParentId() == 0L)
            return 0L;
        return ((MoneyCategory) _spinnerWallet.getSelectedItem()).get_id();
    }
    private Long get_CategoryId()
    {
        if (get_ParentId() == 0L)
            return 0L;
        return ((MoneyCategory) _spinnerCategory.getSelectedItem()).get_id();
    }
    private Long get_subCategoryId()
    {
        if (get_ParentId() == 0L)
            return 0L;
        return ((MoneyCategory) _spinnerSubCategory.getSelectedItem()).get_id();
    }
    private int get_countLot()
    {
        if (_data == null)
            return 0;

        return _data.get_countLot();
    }
    //endregion

    //region Save Params
    protected MoneyPortfolio GetDataFromForm()
    {
        try
        {
            return new MoneyPortfolio(get_Id(),
                    getName(),
                    getComment(),
                    get_ParentId(),
                    get_Position(),
                    get_status(),
                    get_walletId(),
                    get_subCategoryId(),
                    get_countLot());
        }
        catch (Exception e)
        {
            return  null;
        }
    }
    //endregion
}