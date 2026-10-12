package com.MH83.LifeAsProject.view.Money.MoneyAsset;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerEnumCurrencyAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerEnumKindAssetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerEnumStatusAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerTypeAssetAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumCurrency;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAssetSettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentMoneyAssetPopupNew extends CustomPopupBottomFragment<MoneyAsset>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_asset_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    private Spinner _spinnerStatus;
    private EditText _editTextName;
    private EditText _editTextComment;
    private EditText _editTextTicker;
    private Spinner _spinnerKindAsset;
    private Spinner _spinnerTypeAsset;
    private Spinner _spinnerCurrencyAsset;
    //endregion

    //region Constructors
    public FragmentMoneyAssetPopupNew()
    {
        super(_layoutId,
                R.string.PopupAssetAdd,
                R.string.PopupAssetUpdate,
                R.string.errorUpdateMoneyAsset);
    }

    public FragmentMoneyAssetPopupNew(AppCompatActivity activity,
                                      MoneyAsset data,
                                      EnumTypeAction typeAction,
                                      IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.PopupAssetAdd,
                R.string.PopupAssetUpdate,
                R.string.errorUpdateMoneyAsset,
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

        _spinnerStatus = view.findViewById(R.id.spinnerStatus);
        _spinnerKindAsset = view.findViewById(R.id.spinnerKindAsset);
        _spinnerTypeAsset = view.findViewById(R.id.spinnerTypeAsset);
        _spinnerCurrencyAsset = view.findViewById(R.id.spinnerCurrencyAsset);
        _editTextName = view.findViewById(R.id.editTextName);
        _editTextComment = view.findViewById(R.id.editTextComment);
        _editTextTicker = view.findViewById(R.id.editTextTicker);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextName;
        _focusedViewToUpdate = _editTextName;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Asset_Width),
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
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Asset_Width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }
    //endregion

    //region SetAdapters
    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillSpinnerTypeAsset();
        FillSpinnerKindAsset();
        FillSpinnerCurrencyAsset();
        FillSpinnerStatus();
    }

    private void FillSpinnerTypeAsset()
    {
        ArrayList<MoneyTypeAsset> list = MoneyTypeAssetDataSource.LoadDataParent(
                getContext(), false, new MoneyTypeAssetSettings(null));

        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
        ArrayList<MoneyTypeAsset> list2 = new ArrayList<>();
        for (MoneyTypeAsset item: list)
            if (!Objects.equals(item.get_id(), globalVar.get_typeAsset4WalletId()) &&
                    !Objects.equals(item.get_id(), globalVar.get_typeAsset4Creditd()))
                list2.add(item);

        SpinnerTypeAssetAdapter adapter = new SpinnerTypeAssetAdapter(getContext(), list2);
        _spinnerTypeAsset.setAdapter(adapter);
    }

    private void FillSpinnerKindAsset()
    {
        ArrayList<EnumKindAsset> list = new ArrayList<>();
        list.add(EnumKindAsset.Conservative);
        list.add(EnumKindAsset.Moderate);
        list.add(EnumKindAsset.Aggressive);

        SpinnerEnumKindAssetAdapter adapter = new SpinnerEnumKindAssetAdapter(getContext(), list);
        _spinnerKindAsset.setAdapter(adapter);
    }

    private void FillSpinnerCurrencyAsset()
    {
        ArrayList<EnumCurrency> list = new ArrayList<>();
        list.add(EnumCurrency.RUB);
        list.add(EnumCurrency.USD);
        list.add(EnumCurrency.CNY);

        SpinnerEnumCurrencyAdapter adapter = new SpinnerEnumCurrencyAdapter(getContext(), list);
        _spinnerCurrencyAsset.setAdapter(adapter);
    }

    private void FillSpinnerStatus()
    {
        ArrayList<EnumStatus> list = new ArrayList<>();
        list.add(EnumStatus.Started);
        list.add(EnumStatus.Irrelevant);

        SpinnerEnumStatusAdapter adapter = new SpinnerEnumStatusAdapter(getContext(), list);
        _spinnerStatus.setAdapter(adapter);
    }
    //endregion

    //region Set Listeners
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(null);
    }

    protected void SetValues(Object[] objects)
    {
        if (_data != null)
        {
            set_Name(_data.get_name());
            set_Ticker(_data.get_ticker());
            set_TypeAsset(_data.get_moneyTypeAssetId());
            set_KindAsset(_data.get_kindAsset());
            set_CurrencyAsset(_data.get_currency());
            set_Status(_data.get_status());
            set_Comment(_data.get_comment());
        }
        else
        {
            set_Name("");
            set_Ticker("");
            set_TypeAsset(0L);
            set_KindAsset(EnumKindAsset.Conservative);
            set_CurrencyAsset(EnumCurrency.RUB);
            set_Status(EnumStatus.Blank);
            set_Comment("");
        }
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }
    private void set_Ticker(String ticker)
    {
        _editTextTicker.setText(ticker);
    }
    private void set_TypeAsset(Long typeAssetId)
    {
        for (int i = 0; i < _spinnerTypeAsset.getAdapter().getCount(); i++)
            if (Objects.equals(((MoneyTypeAsset) _spinnerTypeAsset.getAdapter().getItem(i)).get_id(), typeAssetId))
            {
                _spinnerTypeAsset.setSelection(i);
                break;
            }
    }
    private void set_KindAsset(EnumKindAsset kindAsset)
    {
        for (int i = 0; i < _spinnerKindAsset.getAdapter().getCount(); i++)
            if (_spinnerKindAsset.getAdapter().getItem(i).equals(kindAsset))
            {
                _spinnerKindAsset.setSelection(i);
                break;
            }
    }
    private void set_CurrencyAsset(EnumCurrency currency)
    {
        for (int i = 0; i < _spinnerCurrencyAsset.getAdapter().getCount(); i++)
            if (_spinnerCurrencyAsset.getAdapter().getItem(i).equals(currency))
            {
                _spinnerCurrencyAsset.setSelection(i);
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
    private void set_Comment(String comment)
    {
        _editTextComment.setText(comment);
    }
    //endregion

    //region Check Params
    protected boolean CheckFormValues()
    {
        return CheckName();
    }

    protected boolean CheckFormValuesForDelete() { return true; }

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
    //endregion

    //region Get Params
    private Long get_Id()
    {
        return _data != null ? _data.get_id() : 0L;
    }
    private Long get_walletId()
    {
        return _data == null ? 0L : _data.get_wallet_id();
    }
    private Long get_creditId()
    {
        return _data == null ? 0L : _data.get_credit_id();
    }
    private Integer get_Position()
    {
        if (_data == null || !Objects.equals(_data.get_moneyTypeAssetId(), get_typeAssetId()))
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                return dbHelper.QueryMoneyAssetMaxPosition(get_typeAssetId()) + 1;
            }
            catch (Exception e) { return 0; }
        }
        else
            return _data.get_position();
    }

    private String getName() throws Exception
    {
        try
        {
            return _editTextName.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private String getTicker() throws Exception
    {
        try
        {
            return _editTextTicker.getText().toString();
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

    private Long get_typeAssetId()
    {
        return ((MoneyTypeAsset) _spinnerTypeAsset.getSelectedItem()).get_id();
    }
    private String get_typeAssetName()
    {
        return ((MoneyTypeAsset) _spinnerTypeAsset.getSelectedItem()).get_name();
    }
    private EnumKindAsset get_kindAsset()
    {
        return (EnumKindAsset) _spinnerKindAsset.getSelectedItem();
    }
    private EnumCurrency get_currencyAsset()
    {
        return (EnumCurrency) _spinnerCurrencyAsset.getSelectedItem();
    }
    private EnumStatus get_status()
    {
        return (EnumStatus) _spinnerStatus.getSelectedItem();
    }
    private int get_countLot()
    {
        if (_data == null)
            return 0;
        return _data.get_lotCount();
    }
    //endregion

    //region Save Params
    protected MoneyAsset GetDataFromForm()
    {
        try
        {
            return new MoneyAsset(
                    get_Id(),
                    get_walletId(),
                    get_creditId(),
                    getName(),
                    getComment(),
                    get_Position(),
                    get_status(),
                    get_typeAssetId(),
                    get_typeAssetName(),
                    get_kindAsset(),
                    getTicker(),
                    get_currencyAsset(),
                    false,
                    get_countLot());
        }
        catch (Exception e)
        {
            return  null;
        }
    }
    //endregion
}