package com.MH83.LifeAsProject.view.Custom;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class CustomPopupBottomFragment<TDataSet extends CustomMoneyObject> extends CustomBottomFragment
{
    //region Fragment constants
    protected TDataSet _data;
    protected EnumTypeAction _typeAction;
    protected EnumTypeAction _typeAction2;
    protected EnumTypeAction _typeAction3;
    private final int _addTitle;
    private final int _updateTitle;
    private final int _error;
    protected IFragmentViewCustom<TDataSet> _iFragmentViewCustom;
    protected IFragmentViewCustom<TDataSet> _iFragmentViewCustom2;
    protected IFragmentViewCustom<TDataSet> _iFragmentViewCustom3;

    protected View _focusedViewToAdd;
    protected boolean _showInputToAdd = false;
    protected View _focusedViewToUpdate;
    protected boolean _showInputToUpdate = false;

    protected ImageView _imageViewMascotToChange;
    //endregion

    //region Fragment components
    protected ImageView _imageViewDelete;
    protected View _layoutCaption;
    protected TextView _textViewCaption;
    protected Button _buttonSave;
    protected Button _buttonSave2;
    protected Button _buttonSave3;
    //endregion

    //region Custom Method
    protected void setDateView(String month, String year, TextView textView)
    {
        CustomStaticClass.setDateView(getContext(), month, year, textView);
    }
    //endregion

    //region Constructor
    public CustomPopupBottomFragment(int layoutId, int addTitle, int updateTitle,
                                     int error)
    {
        super(layoutId);

        _addTitle = addTitle;
        _updateTitle = updateTitle;
        _error = error;
    }
    public CustomPopupBottomFragment(AppCompatActivity activity, int layoutId, TDataSet data,
                                     EnumTypeAction typeAction,
                                     EnumTypeAction typeAction2,
                                     EnumTypeAction typeAction3,
                                     int addTitle, int updateTitle,
                                     int error,
                                     IFragmentViewCustom<TDataSet> iFragmentViewCustom,
                                     IFragmentViewCustom<TDataSet> iFragmentViewCustom2,
                                     IFragmentViewCustom<TDataSet> iFragmentViewCustom3)
    {
        super(activity, layoutId);

        _data = data;

        _typeAction = typeAction;
        _typeAction2 = typeAction2;
        _typeAction3 = typeAction3;

        _addTitle = addTitle;
        _updateTitle = updateTitle;
        _error = error;

        _iFragmentViewCustom = iFragmentViewCustom;
        _iFragmentViewCustom2 = iFragmentViewCustom2;
        _iFragmentViewCustom3 = iFragmentViewCustom3;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if ((_typeAction != null || _typeAction2 != null || _typeAction3 != null) &&
                (_iFragmentViewCustom != null || _iFragmentViewCustom2 != null || _iFragmentViewCustom3 != null))
            globalVariable.set_4Popup(_typeAction, _typeAction2, _typeAction3,
                    _data, _iFragmentViewCustom, _iFragmentViewCustom2, _iFragmentViewCustom3);
        else
        {
            _typeAction = globalVariable.get_typeAction4Popup();
            _typeAction2 = globalVariable.get_typeAction4Popup2();
            _typeAction3 = globalVariable.get_typeAction4Popup3();
            _data = (TDataSet) globalVariable.get_data();
            _iFragmentViewCustom = globalVariable.get_IFragmentViewCustom();
            _iFragmentViewCustom2 = globalVariable.get_IFragmentViewCustom2();
            _iFragmentViewCustom3 = globalVariable.get_IFragmentViewCustom3();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        SetTitle();
        SetAdapters();
        SetComponentsSetting(view);
        SetDataToView();
        SetComponentsSettingPost(view);
        SetListeners();
        SetHeightComponents();
        SetWidthComponents();
        SetFocusToView();
        SetTimers();

        return view;
    }

    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _layoutCaption = view.findViewById(R.id.layoutCaption);
        _textViewCaption = view.findViewById(R.id.textViewCaption);
        _buttonSave = view.findViewById(R.id.buttonSave);
        _buttonSave2 = view.findViewById(R.id.buttonSave2);
        _buttonSave3 = view.findViewById(R.id.buttonSave3);
        _imageViewDelete = view.findViewById(R.id.imageViewDelete);
        _imageViewMascotToChange = view.findViewById(R.id.imageViewMascotToChange);

        if (_imageViewDelete != null && ShowDeleteButton())
            _imageViewDelete.setVisibility(View.VISIBLE);
    }

    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        SetListeners4SaveButton();
        SetListeners4DeleteButton();
    }

    protected void ClearFocus() { }

    private void SetListeners4SaveButton()
    {
        if (_buttonSave != null)
            _buttonSave.setOnClickListener(view -> SaveButtonListener());

        if (_buttonSave2 != null)
            _buttonSave2.setOnClickListener(view -> SaveButtonListener());

        if (_buttonSave3 != null)
            _buttonSave3.setOnClickListener(view -> SaveButtonListener());
    }
    protected void SaveButtonListener()
    {
        ClearFocus();

        if (CheckFormValues())
        {
            TDataSet dataSet = SaveFormValues(_error);
            if (dataSet != null &&
                    (_iFragmentViewCustom != null || _iFragmentViewCustom2 != null ||
                            _iFragmentViewCustom3 != null))
            {
                try
                {
                    boolean b = false;

                    if (_typeAction3 != null)
                    {
                        if (_typeAction3 == EnumTypeAction.Insert)
                        {
                            try
                            {
                                b = _iFragmentViewCustom3.refreshViewOnAdd(dataSet);
                            }
                            catch (Exception e3) { }
                        }
                        else if (_typeAction3 == EnumTypeAction.Update)
                        {
                            try
                            {
                                b = _iFragmentViewCustom3.refreshViewOnUpdate(dataSet);
                            }
                            catch (Exception e3) { }
                        }
                    }

                    if (!b && _typeAction2 != null)
                    {
                        if (_typeAction2 == EnumTypeAction.Insert)
                        {
                            try
                            {
                                b = _iFragmentViewCustom2.refreshViewOnAdd(dataSet);
                            }
                            catch (Exception e2) {}
                        }
                        else if (_typeAction2 == EnumTypeAction.Update)
                        {
                            try
                            {
                                b = _iFragmentViewCustom2.refreshViewOnUpdate(dataSet);
                            }
                            catch (Exception e2) {}
                        }
                    }

                    if (!b && _typeAction != null)
                    {
                        if (_typeAction == EnumTypeAction.Insert)
                        {
                            try
                            {
                                _iFragmentViewCustom.refreshViewOnAdd(dataSet);
                            }
                            catch (Exception e1)
                            {
                                Log.d("exception", e1.getMessage());
                            }
                        }
                        else if (_typeAction == EnumTypeAction.Update)
                        {
                            try
                            {
                                _iFragmentViewCustom.refreshViewOnUpdate(dataSet);
                            }
                            catch (Exception e1)
                            {
                                Log.d("exception", e1.getMessage());
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.d("Error", e.getMessage());
                }

                dismiss();
            }
        }
    }

    private void SetListeners4DeleteButton()
    {
        if (_imageViewDelete != null)
            _imageViewDelete.setOnClickListener(view ->
            {
                try
                {
                    if (CheckFormValuesForDelete())
                    {
                        TDataSet dataSet = GetDataFromForm();
                        if (DeleteItem(dataSet))
                        {
                            boolean b = false;

                            if (_typeAction3 != null && _typeAction3 == EnumTypeAction.Update)
                            {
                                try
                                {
                                    b = _iFragmentViewCustom3.refreshViewOnDelete(dataSet);
                                }
                                catch (Exception e3) {}
                            }

                            if (!b)
                            {
                                if (_typeAction2 != null && _typeAction2 == EnumTypeAction.Update)
                                {
                                    try
                                    {
                                        b = _iFragmentViewCustom2.refreshViewOnDelete(dataSet);
                                    }
                                    catch (Exception e2) {}
                                }
                            }

                            if (!b)
                            {
                                if (_typeAction != null && _typeAction == EnumTypeAction.Update)
                                {
                                    try
                                    {
                                        _iFragmentViewCustom.refreshViewOnDelete(dataSet);
                                    }
                                    catch (Exception e1)
                                    {
                                        Log.d("Error update object", e1.getMessage());
                                    }
                                }
                            }

                            dismiss();
                        }
                    }
                }
                catch (Exception e) { }

            });
    }

    protected void SetTitle()
    {
        int popupCaption = _addTitle;

        if (_typeAction3 == EnumTypeAction.Update)
            popupCaption = _updateTitle;
        else if (_typeAction3 == null)
        {
            if (_typeAction2 == EnumTypeAction.Update)
                popupCaption = _updateTitle;
            else if (_typeAction2 == null)
            {
                if (_typeAction == EnumTypeAction.Update)
                    popupCaption = _updateTitle;
            }
        }

        if (_textViewCaption != null)
            _textViewCaption.setText(popupCaption);
    }

    protected void SetComponentsSetting(View view)
    {
        GlobalClass gV = (GlobalClass) view.getContext().getApplicationContext();
        if (_imageViewMascotToChange != null)
            _imageViewMascotToChange.setVisibility(gV.get_showMascot() ? View.VISIBLE : View.INVISIBLE);
    }

    protected void SetComponentsSettingPost(View view) { }

    protected void SetDataToView() { }

    protected void SetAdapters() { }

    protected void SetTimers() { }

    protected void SetHeightComponents() { }

    protected void SetWidthComponents() { }

    private void SetFocusToView()
    {
        boolean focusInsert = false;
        if (_typeAction3 == EnumTypeAction.Insert)
            focusInsert = true;
        else if (_typeAction3 == null)
        {
            if (_typeAction2 == EnumTypeAction.Insert)
                focusInsert = true;
            else if (_typeAction2 == null)
                if (_typeAction == EnumTypeAction.Insert)
                    focusInsert = true;
        }

        if (_showInputToAdd && focusInsert && _focusedViewToAdd != null)
        {
            _focusedViewToAdd.requestFocus();
            if (_showInputToAdd)
                getDialog().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        boolean focusUpdate = false;
        if (_typeAction3 == EnumTypeAction.Update)
            focusUpdate = true;
        else if (_typeAction3 == null)
        {
            if (_typeAction2 == EnumTypeAction.Update)
                focusUpdate = true;
            else if (_typeAction2 == null)
                if (_typeAction == EnumTypeAction.Update)
                    focusUpdate = true;
        }

        if (_showInputToUpdate && focusUpdate && _focusedViewToUpdate != null)
        {
            _focusedViewToUpdate.requestFocus();
            if (_showInputToAdd)
                getDialog().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void PopupShow()
    {
        if (!CheckPopupShow())  return;

        super.PopupShow();
    }
    //endregion

    //region Check Params
    protected boolean CheckPopupShow()
    {
        return true;
    }
    //endregion

    //region Base Methods
    protected void SetValues(Object[] objects) { }
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }
    protected boolean CheckFormValues() { return false; }
    protected boolean CheckFormValuesForDelete() { return false; }
    protected TDataSet GetDataFromForm() { return null; }
    //endregion

    //region Save Params
    protected TDataSet SaveFormValues(int error)
    {
        TDataSet dataSet = null;
        try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
        {
            if (getContext() != null)
            {
                dataSet = GetDataFromForm();

                boolean bInsert = false;
                if (_typeAction3 == EnumTypeAction.Insert)
                    bInsert = true;
                else if (_typeAction3 == null)
                {
                    if (_typeAction2 == EnumTypeAction.Insert)
                        bInsert = true;
                    else if (_typeAction2 == null)
                        if (_typeAction == EnumTypeAction.Insert)
                            bInsert = true;
                }

                boolean bUpdate = false;
                if (_typeAction3 == EnumTypeAction.Update)
                    bUpdate = true;
                else if (_typeAction3 == null)
                {
                    if (_typeAction2 == EnumTypeAction.Update)
                        bUpdate = true;
                    else if (_typeAction2 == null)
                        if (_typeAction == EnumTypeAction.Update)
                            bUpdate = true;
                }

                if (bInsert)
                    dataSet.AddToDatabase(dbHelper, getContext().getApplicationContext());
                else if (bUpdate)
                {
                    if (!dataSet.UpdateInDatabase(dbHelper, getContext()))
                    {
                        Toast.makeText(getActivity(),
                                getContext().getString(error), Toast.LENGTH_SHORT).show();
                        dataSet = null;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return dataSet;
    }
    //endregion

    //region Select
    protected void SelectItem()
    {
        TDataSet dataSet = GetDataFromForm();

        boolean b = false;

        if (_typeAction3 != null && _typeAction3 == EnumTypeAction.Select)
        {
            try
            {
                b = _iFragmentViewCustom3.refreshViewOnSelect(dataSet);
            }
            catch (Exception e3) {}
        }

        if (!b)
        {
            if (_typeAction2 != null && _typeAction2 == EnumTypeAction.Select)
            {
                try
                {
                    b = _iFragmentViewCustom2.refreshViewOnSelect(dataSet);
                }
                catch (Exception e2) {}
            }
        }

        if (!b)
        {
            if (_typeAction != null && _typeAction == EnumTypeAction.Select)
            {
                try
                {
                    _iFragmentViewCustom.refreshViewOnSelect(dataSet);
                }
                catch (Exception e1) {}
            }
        }

        dismiss();
    }
    //endregion

    //region Delete Item
    protected boolean DeleteItem(TDataSet dataSet)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
        {
            return dataSet.DeleteFromDatabase(dbHelper, getContext());
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //endregion
}
