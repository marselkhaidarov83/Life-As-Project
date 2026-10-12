package com.MH83.LifeAsProject.view.Money.MoneyTypeAsset;

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
import com.MH83.LifeAsProject.adapter.spinner.SpinnerEnumStatusAdapter;
import com.MH83.LifeAsProject.adapter.spinner.money.SpinnerMoneyTypeAssetAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMoneyTypeAssetPopupNew extends CustomPopupBottomFragment<MoneyTypeAsset>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_typeasset_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
//    private Spinner _spinnerParent;
    private Spinner _spinnerStatus;
    private EditText _editTextName;
    private EditText _editTextComment;
    //endregion

    //region Constructors
    public FragmentMoneyTypeAssetPopupNew()
    {
        super(_layoutId,
                R.string.PopupTypeAssetAdd,
                R.string.PopupTypeAssetUpdate,
                R.string.errorUpdateMoneyTypeAsset);
    }

    public FragmentMoneyTypeAssetPopupNew(AppCompatActivity activity,
                                          MoneyTypeAsset data,
                                          EnumTypeAction typeAction,
                                          IFragmentViewCustom iFragmentViewCustom)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.PopupTypeAssetAdd,
                R.string.PopupTypeAssetUpdate,
                R.string.errorUpdateMoneyTypeAsset,
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

//        _spinnerParent = view.findViewById(R.id.spinnerParent);
        _spinnerStatus = view.findViewById(R.id.spinnerStatus);
        _editTextName = view.findViewById(R.id.editTextName);
        _editTextComment = view.findViewById(R.id.editTextComment);

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
    }

    private void SetEnabled()
    {
//        if (_data != null && _data.get_hasChild())
//            _spinnerParent.setEnabled(false);
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillSpinnerParent();
        FillSpinnerStatus();
    }

    private void FillSpinnerParent()
    {
        GlobalClass globalVariable = (GlobalClass) _activity.getApplicationContext();
        List<MoneyTypeAsset> list =
                MoneyTypeAssetDataSource.LoadDataParent(_activity.getApplicationContext(),
                        true, globalVariable.get_moneyTypeAssetSettings());
        if (_data != null && _data.get_id() != 0)
            for (MoneyTypeAsset item : list)
                if (item.get_id() == _data.get_id())
                {
                    list.remove(item);
                    break;
                }

        SpinnerMoneyTypeAssetAdapter adapter = new SpinnerMoneyTypeAssetAdapter(getContext(), list);
//        _spinnerParent.setAdapter(adapter);
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
//            set_hasChild(_data.get_hasChild());
        }
        else
        {
            set_Parent(0L);
            set_Status(EnumStatus.Blank);
            set_Name("");
            set_Comment("");
//            set_hasChild(false);
        }
    }

    private void set_Parent(Long parentId)
    {
//        for (int i = 0; i < _spinnerParent.getAdapter().getCount(); i++)
//            if (((MoneyTypeAsset) _spinnerParent.getAdapter().getItem(i)).get_id().equals(parentId))
//            {
//                _spinnerParent.setSelection(i);
//                break;
//            }
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
        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
        return
                _data != null &&
                        _data.canDelete(getContext()) &&
                        !Objects.equals(_data.get_id(), globalVar.get_typeAsset4WalletId()) &&
                        !Objects.equals(_data.get_id(), globalVar.get_typeAsset4Creditd());
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
                        getContext().getString(R.string.errorExistChildsMoneyTypeAsset), Toast.LENGTH_SHORT).show();
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
        if (_data == null || _data.get_parentId() != get_ParentId())
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                return dbHelper.QueryMoneyTypeAssetMaxPosition(get_ParentId()) + 1;
            }
            catch (Exception e) { return 0; }
        }
        else
            return _data.get_position();
    }

    private Long get_ParentId()
    {
//        return  ((MoneyTypeAsset)_spinnerParent.getSelectedItem()).get_id();
        return 0L;
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

    private int get_countAsset()
    {
        return _data == null ? 0 :_data.get_countAsset();
    }
    //endregion

    //region Save Params
    protected MoneyTypeAsset GetDataFromForm()
    {
        try
        {
            return new MoneyTypeAsset(get_Id(),
                    getName(),
                    getComment(),
                    get_ParentId(),
                    get_Position(),
                    get_status(),
                    get_countAsset());
        }
        catch (Exception e)
        {
            return  null;
        }
    }
    //endregion
}