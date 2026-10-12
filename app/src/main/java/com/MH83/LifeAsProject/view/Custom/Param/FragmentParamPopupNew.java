package com.MH83.LifeAsProject.view.Custom.Param;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.Param;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

public class FragmentParamPopupNew extends CustomPopupBottomFragment<Param>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_param_new;
    //endregion

    //region Fragment parameters
    private int _dialogWidthPhone = 1400;
    private int _dialogWidthTablet = 1000;
    //endregion

    //region Fragment Components
    private EditText _editTextName;
    private EditText _editTextMeasure;
    private EditText _editTextComment;
    //endregion

    //region Constructors
    public FragmentParamPopupNew()
    {
        super(_layoutId,
                R.string.PopupParamAdd,
                R.string.PopupParamUpdate,
                R.string.errorUpdateParam);
    }

    public FragmentParamPopupNew(AppCompatActivity activity,
                                 Param data,
                                 EnumTypeAction typeAction,
                                 IFragmentViewCustom<Param> iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                R.string.PopupParamAdd,
                R.string.PopupParamUpdate,
                R.string.errorUpdateParam,
                iFragmentViewCustom,
                null,
                null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_popup(this);
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _editTextName = view.findViewById(R.id.editTextName);
        _editTextMeasure = view.findViewById(R.id.editTextMeasure);
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
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_data} );
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//
//        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            int height = displaymetrics.heightPixels;
//            height -= _layoutCaption.getLayoutParams().height;
//            height -= 3 * getContext().getResources().getDimension(R.dimen.BasePadding);
//            height -= _buttonSave.getLayoutParams().height;
//            height -= _editTextName.getLayoutParams().height;
//            height -= 3 * getContext().getResources().getDimension(R.dimen.BasePadding);
//
//            ViewGroup.LayoutParams lp = _subsamplingScaleImageView.getLayoutParams();
//            lp.height = height;
//            _subsamplingScaleImageView.setLayoutParams(lp);
//
//            _dialogWidth = (int) (4 * height / 3f);
//        }
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
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
                getDialog().getWindow().setLayout(_dialogWidthPhone /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            else
                getDialog().getWindow().setLayout(_dialogWidthTablet /*our width*/,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners() { super.SetListeners(); }
    //endregion

    //region Check Params
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

                Toast.makeText(getActivity(),
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

            Toast.makeText(getActivity(),
                    getContext().getString(R.string.specifyCorrectName), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
        return true;
    }
    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        _data = (Param) objects[0];

        if (_data != null)
        {
            set_Name(_data.get_name());
            set_Measure(_data.get_measure());
            set_Comment(_data.get_comment());
        }
        else
        {
            set_Name("");
            set_Measure("");
            set_Comment("");
        }
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }

    private void set_Measure(String measure)
    {
        _editTextMeasure.setText(measure);
    }

    private void set_Comment(String comment)
    {
        _editTextComment.setText(comment);
    }
    //endregion

    //region Get Params
    private Long get_id()
    {
        return _data == null ? 0 : _data.get_id();
    }

    private String getName() throws Exception
    {
        try
        {
            return _editTextName.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private String get_measure() throws Exception
    {
        try
        {
            return _editTextMeasure.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private String get_comment() throws Exception
    {
        try
        {
            return _editTextComment.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }
    //endregion

    //region Save Params
    protected Param GetDataFromForm()
    {
        try
        {
            return new Param(get_id(), getName(), get_comment(), get_measure());
        }
        catch (Exception e) { return null; }
    }
    //endregion
}