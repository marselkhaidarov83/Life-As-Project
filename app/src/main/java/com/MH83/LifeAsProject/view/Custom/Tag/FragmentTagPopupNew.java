package com.MH83.LifeAsProject.view.Custom.Tag;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerTagTypeDataAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamLegendToChangePopup;

import java.util.ArrayList;
import java.util.List;

public class FragmentTagPopupNew extends CustomPopupBottomFragment<Tag>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_tag_new;
    //endregion

    //region Fragment parameters
    final int _dialogWidthPhone = 1400;
    final int _dialogWidthTablet = 1000;
    private boolean _archive = false;
    //endregion

    //region Fragment Components
    private EditText _editTextName;
    private Spinner _spinnerTagTypeData;
    private EditText _editTextComment;
    private ImageView _imageViewArchive;
    //endregion

    //region Constructors
    public FragmentTagPopupNew()
    {
        super(_layoutId,
                R.string.PopupTagAdd,
                R.string.PopupTagUpdate,
                R.string.errorUpdateTag);
    }

    public FragmentTagPopupNew(AppCompatActivity activity,
                               Tag data,
                               EnumTypeAction typeAction,
                               IFragmentViewCustom<Tag> iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                R.string.PopupTagAdd,
                R.string.PopupTagUpdate,
                R.string.errorUpdateTag,
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

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        List<EnumTagTypeData> list = new ArrayList<>();
        list.add(EnumTagTypeData.PNL);

        if (_spinnerTagTypeData != null)
        {
            SpinnerTagTypeDataAdapter adapter = new SpinnerTagTypeDataAdapter(getContext(), list);
            _spinnerTagTypeData.setAdapter(adapter);
        }
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _editTextName = view.findViewById(R.id.editTextName);
        _spinnerTagTypeData = view.findViewById(R.id.spinnerTagTypeData);
        _editTextComment = view.findViewById(R.id.editTextComment);
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
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues();
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
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewArchive.setOnClickListener(view ->
        {
            _archive = !_archive;
            setArchiveImage(_archive);
        });

        _textViewCaption.setOnClickListener(view ->
        {
            _editTextName.clearFocus();
            _editTextComment.clearFocus();

            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        });

        _imageViewMascotToChange.setOnClickListener(view ->
                new FragmentTagLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
    }
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
    protected void SetValues()
    {
        if (_data != null)
        {
            set_Name(_data.get_name());
            set_tagTypeData(_data.get_tagTypeData());
            set_Comment(_data.get_comment());
            set_archive(_data.get_archive());
        }
        else
        {
            set_Name("");
            set_Comment("");
            set_archive(false);
        }
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }

    private void set_tagTypeData(EnumTagTypeData tagTypeData)
    {
        for (int i = 0; i < _spinnerTagTypeData.getAdapter().getCount(); i++)
            if (_spinnerTagTypeData.getAdapter().getItem(i).equals(tagTypeData))
            {
                _spinnerTagTypeData.setSelection(i);
                break;
            }
    }

    private void set_Comment(String comment)
    {
        if (_editTextComment != null)
            _editTextComment.setText(comment);
    }

    private void set_archive(boolean archive)
    {
        _archive = archive;
        setArchiveImage(archive);
    }

    private void setArchiveImage(boolean archive)
    {
        if (_imageViewArchive == null)
            return;

        if (archive)
            _imageViewArchive.setImageResource(R.drawable.icon_archive);
        else
            _imageViewArchive.setImageResource(R.drawable.icons_nonarchive);
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

    private EnumTagTypeData get_tagTypeData()
    {
        return (EnumTagTypeData) _spinnerTagTypeData.getSelectedItem();
    }

    private String get_comment() throws Exception
    {
        try
        {
            return _editTextComment.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Long get_countExternal()
    {
        if (_data == null)
            return 0L;
        return _data.get_countExternal();
    }

    private boolean get_archive()
    {
        return _archive;
    }
    //endregion

    //region Save Params
    protected Tag GetDataFromForm()
    {
        try
        {
            return new Tag(
                    get_id(),
                    getName(),
                    get_comment(),
                    get_tagTypeData(),
                    get_countExternal(),
                    get_archive());
        }
        catch (Exception e) { return null; }
    }
    //endregion
}