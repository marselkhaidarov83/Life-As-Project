package com.MH83.LifeAsProject.view.Money.MoneyCategory;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.common.IconAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerCategoryTypeAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyCategoryAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.ISetSelIcon;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanFactLegendToAddPopup;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanFactLegendToChangePopup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyCategoryPopupNew extends CustomPopupBottomFragment<MoneyCategory>
    implements ISetSelIcon, IFragmentViewCustom<Icon>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_category_new;
    //endregion

    //region Fragment parameters
    private List<MoneyCategory> _parentCategory;
    private boolean _archive = false;
    //endregion

    //region Fragment settings
    private EnumTypeCategory _typeCategory;
    private Long _id;
    private int _position;
    private Long _baseParentId = 0L;
    private boolean _hasChild = false;
    private boolean _existIncoming = false;
    private boolean _existPNL = false;
    //endregion

    //region Fragment Components
    private RecyclerView recyclerViewIcon;
    private Spinner _spinnerParent;
    private EditText _editTextName;
    TextView _textViewSum, textViewParent;
    private EditText _editTextSum;
    private Spinner _spinnerType;
    private ImageView _imageViewArchive;
    //endregion

    //region Constructors
    public FragmentMoneyCategoryPopupNew()
    {
        super(_layoutId,
                R.string.PopupCategoryAdd,
                R.string.PopupCategoryUpdate,
                R.string.errorUpdateMoneyCategory);
    }

    public FragmentMoneyCategoryPopupNew(AppCompatActivity activity,
                                         MoneyCategory data,
                                         EnumTypeAction typeAction,
                                         IFragmentViewCustom iFragmentViewCustom,
                                         EnumTypeCategory typeCategory)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.PopupCategoryAdd,
                R.string.PopupCategoryUpdate,
                R.string.errorUpdateMoneyCategory,
                iFragmentViewCustom,
                null,
                null);

        _typeCategory = typeCategory;
        MoneyCategorySettings moneyCategorySettings = new MoneyCategorySettings(
                _typeCategory, true, false,
                true, EnumCategoryType.All,null);
        _parentCategory = MoneyCategoryDataSource.LoadDataParent(activity, true, moneyCategorySettings);

        final GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        globalVariable.set_typeCategory(typeCategory);
        globalVariable.set_parentCategory(_parentCategory);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        _typeCategory = globalVariable.get_typeCategory();
        _parentCategory = globalVariable.get_parentCategory();
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        recyclerViewIcon = view.findViewById(R.id.recycleViewIcon);
        _spinnerParent = view.findViewById(R.id.spinnerParent);
        _editTextName = view.findViewById(R.id.editTextName);
        _textViewSum = view.findViewById(R.id.textViewSum);
        _editTextSum = view.findViewById(R.id.editTextSum);
        textViewParent = view.findViewById(R.id.textViewParent);
        _spinnerType = view.findViewById(R.id.spinnerType);
        _imageViewArchive = view.findViewById(R.id.imageViewArchive);

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

        FillVisibility();
        SetEnabled();
    }

    private void SetEnabled()
    {
        GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();

        if (_data != null && globalVariable.get_hasUnpaidSubscription())
            _editTextSum.setEnabled(false);
    }

    private void FillVisibility()
    {
        if (_typeCategory == EnumTypeCategory.Increase ||
                _typeCategory == EnumTypeCategory.Cost)
        {
            //TODO: раскомментировать когда-нибудь
//            _textViewSum.setVisibility(View.INVISIBLE);
//            _editTextSum.setVisibility(View.INVISIBLE);
//
//            _textViewSum.setHeight(0);
//            _editTextSum.setHeight(0);
//
//            _textViewSum.requestLayout();
//            _editTextSum.requestLayout();
//            view.requestLayout();
        }
        else if (_typeCategory == EnumTypeCategory.Wallet)
        {
            textViewParent.setVisibility(View.INVISIBLE);
            _spinnerParent.setVisibility(View.INVISIBLE);

            textViewParent.setHeight(0);
            _spinnerParent.getLayoutParams().height = 0;
        }
    }

    private void FillRViewAdapter()
    {
        ArrayList<Icon> list = IconDataSource.LoadData(_typeCategory);
        IconAdapter adapter = new IconAdapter(getContext(), list, this,
                _data == null ? 0L : _data.get_icon());
        recyclerViewIcon.setAdapter(adapter);
    }

    private void FillSpinnerParent()
    {
        List<MoneyCategory> list = new ArrayList<>(_parentCategory);
        for (int i = 0; i < list.size(); i++)
            if (Objects.equals(_id, list.get(i).get_id()) && _id != 0)
            {
                list.remove(i);
                break;
            }
        SpinnerMoneyCategoryAdapter adapter = new SpinnerMoneyCategoryAdapter(getContext(), list);
        _spinnerParent.setAdapter(adapter);
    }

    private void FillSpinnerType()
    {
        List<EnumCategoryType> list = new ArrayList<>();

        if (_typeCategory.equals(EnumTypeCategory.Wallet))
        {
            list.add(EnumCategoryType.WalletWallet);
            list.add(EnumCategoryType.WalletAccumulation);
        }
        else
        {
            list.add(EnumCategoryType.Base);
            list.add(EnumCategoryType.Advanced);
            list.add(EnumCategoryType.Project);
        }

        SpinnerCategoryTypeAdapter adapter = new SpinnerCategoryTypeAdapter(getContext(), list);
        _spinnerType.setAdapter(adapter);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillRViewAdapter();
        FillSpinnerParent();
        FillSpinnerType();
//        FillSpinnerStatus();
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_data} );
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

        _imageViewArchive.setOnClickListener(view ->
        {
            _archive = !_archive;
            setArchiveImage(_archive);
        });

        _editTextSum.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String str = editable.toString();
                int p = str.indexOf(".");
                if (p != -1)
                {
                    String tmpStr = str.substring(p);
                    if (tmpStr.length() == 4)
                        editable.delete(editable.length()-1, editable.length());
                }
            }
        });

        _imageViewMascotToChange.setOnClickListener(view ->
                new FragmentMoneyCategoryLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
    }
    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        _data = (MoneyCategory) objects[0];

        if (_data != null)
        {
            set_Id(_data.get_id());
            set_Position(_data.get_position());
            set_ParentId(_data.get_parentId());
            set_Name(_data.get_name());
            set_type(_data.get_type());
            set_archive(_data.get_archive());
            set_SumLong(_data.get_sumLong());
            set_hasChild(_data.get_hasChild());
            setExistIncoming(_data.getExistIncoming());
            setExistPNL(_data.getExistPNL());
        }
        else
        {
            set_Id(0L);
            set_Position(0);
            set_ParentId(0L);
            set_Name("");
            set_type(EnumCategoryType.Base);
            set_archive(false);
            set_SumLong(0L);
            set_hasChild(false);
            setExistIncoming(false);
            setExistPNL(false);
        }
    }

    private void set_hasChild(boolean b)
    {
        _hasChild = b;
        if (_hasChild)
            _spinnerParent.setEnabled(false);
    }

    private void setExistIncoming(boolean b)
    {
        _existIncoming = b;
    }

    private void setExistPNL(boolean b)
    {
        _existPNL = b;
    }

    private void set_Id(Long id) { _id = id; }
    private void set_Position(int position) { _position = position; }
    public void set_ParentId(Long parentId)
    {
        _baseParentId = parentId;
        for (int i = 0; i < _parentCategory.size(); i++)
            if (Objects.equals(_parentCategory.get(i).get_id(), parentId))
            {
                _spinnerParent.setSelection(i);
                break;
            }
    }
    public void set_type(EnumCategoryType categoryType)
    {
        for (int i = 0; i < _spinnerType.getAdapter().getCount(); i++)
            if (_spinnerType.getAdapter().getItem(i) == categoryType)
            {
                _spinnerType.setSelection(i);
                break;
            }
    }
    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }

    private void set_SumLong(Long sumLong)
    {
        if (sumLong == 0L)
            _editTextSum.setText("");
        else if (Math.floorMod(sumLong, 100L) == 0)
        {
            _editTextSum.setText(String.valueOf(Math.floorDiv(sumLong, 100L)));
        }
        else
        {
            _editTextSum.setText(String.valueOf(sumLong / 100d));
        }
    }

    private void set_archive(boolean archive)
    {
        _archive = archive;
        setArchiveImage(archive);
    }

    private void setArchiveImage(boolean archive)
    {
        if (archive)
            _imageViewArchive.setImageResource(R.drawable.icon_archive);
        else
            _imageViewArchive.setImageResource(R.drawable.icons_nonarchive);
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
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
        if (_baseParentId != 0)
            return true;
        else
        {
            if (_hasChild)
            {
                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.errorExistChildsMoneyCategory), Toast.LENGTH_SHORT).show();
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
        if (_id != 0)
            return _id;

        return -1L;
    }

    private Integer get_Position()
    {
        if (_position == 0)
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                int position = dbHelper.QueryMoneyCategoryMaxPosition(get_ParentId(), _typeCategory) + 1;
                set_Position(position);
                return position;
            }
            catch (Exception e) { return 0; }
        }
        else
        {
            if (Objects.equals(_baseParentId, get_ParentId()))
                return _position;
            else
            {
                try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
                {
                    int position = dbHelper.QueryMoneyCategoryMaxPosition(get_ParentId(), _typeCategory) + 1;
                    set_Position(position);
                    return position;
                }
                catch (Exception e) { return 0; }
            }
        }
    }

    private Long get_ParentId()
    {
        return  ((MoneyCategory)_spinnerParent.getSelectedItem()).get_id();
    }

    private String getName() throws Exception
    {
        try
        {
            return _editTextName.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Long getSumLong() throws Exception
    {
        try
        {
            String s = _editTextSum.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            long rres;
            long res = 0L;
            if (!s.equals(""))
                res = Long.parseLong(s);
            if (pos < 0)
                rres = 100 * res;
            else if (pos + 2 == length)
                rres = 10 * res;
            else
                rres = res;

            return rres;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private boolean getHasChild() { return _hasChild; }

    private boolean getExistIncoming() { return _existIncoming; }

    private boolean getExistPNL() { return _existPNL; }
    private Long get_targetId()
    {
        return _data == null ? 0L : _data.get_targetId();
    }
    private EnumCategoryType get_categoryType()
    {
        return (EnumCategoryType) _spinnerType.getSelectedItem();
    }
    private Long get_CreditId() { return _data != null ? _data.get_creditId() : 0L; }

    private boolean getArchive()
    {
        return _archive;
    }
    private Long getIcon()
    {
        return ((IconAdapter) recyclerViewIcon.getAdapter()).getSelectedIcon();
    }
    //endregion

    //region Save Params
    protected MoneyCategory GetDataFromForm()
    {
        try
        {
            return new MoneyCategory(get_Id(), get_ParentId(), get_Position(), getName(),
                    getSumLong(),
                    _typeCategory, getHasChild(), getExistIncoming(), getExistPNL(),
                    false, get_targetId(),
                    get_categoryType(),
                    get_CreditId(),
                    getArchive(),
                    getIcon());
        }
        catch (Exception e) { return  null; }
    }
    //endregion

    //region ISetSelIcon
    @Override
    public void SetSelIcon(Long selIconId)
    {

    }

    @Override
    public void ClearFocus()
    {
        _editTextName.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextSum.getWindowToken(), 0);
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Icon icon) {
        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Icon icon) {
        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Icon icon) {
        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Icon icon) {
        return false;
    }
    //endregion
}