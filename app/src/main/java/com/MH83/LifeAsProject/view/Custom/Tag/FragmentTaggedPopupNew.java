package com.MH83.LifeAsProject.view.Custom.Tag;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerTagAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Custom.TagDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.List;

public class FragmentTaggedPopupNew extends CustomPopupBottomFragment<Tagged>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_tagged_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment Components
    private Spinner _spinnerTag;
    ImageView _imageViewMascotToChange2;
    //endregion

    //region Constructors
    public FragmentTaggedPopupNew()
    {
        super(_layoutId,
                R.string.PopupTaggedAdd,
                R.string.PopupTaggedUpdate,
                R.string.errorUpdateTagged);
    }

    public FragmentTaggedPopupNew(AppCompatActivity activity,
                                  Tagged data,
                                  EnumTypeAction typeAction,
                                  IFragmentViewCustom<Tagged> iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                null,
                typeAction,
                null,
                R.string.PopupTaggedAdd,
                R.string.PopupTaggedUpdate,
                R.string.errorUpdateTagged,
                null,
                iFragmentViewCustom,
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

        TagSettings tagSettings = new TagSettings(EnumTagTypeData.PNL, false);
        List<Tag> list = TagDataSource.LoadData(getContext(), tagSettings, false);

        SpinnerTagAdapter adapter = new SpinnerTagAdapter(getContext(), list);
        _spinnerTag.setAdapter(adapter);
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _spinnerTag = view.findViewById(R.id.spinnerTag);
        _imageViewMascotToChange2 = view.findViewById(R.id.imageViewMascotToChange2);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _spinnerTag;
        _focusedViewToUpdate = _spinnerTag;
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

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ||
                globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
        {
             getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Tagged_Width) /*our width*/,
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

        _imageViewMascotToChange2.setOnClickListener(view ->
                new FragmentTaggedLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
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

    //region Set Params
    protected void SetValues()
    {
        if (_data != null)
        {
            set_Tag(_data.get_tag().get_id());
        }
    }

    private void set_Tag(Long id)
    {
        for (int i = 0; i < _spinnerTag.getAdapter().getCount(); i++)
            if (((Tag) _spinnerTag.getAdapter().getItem(i)).get_id().equals(id))
            {
                _spinnerTag.setSelection(i);
                break;
            }
    }
    //endregion

    //region Get Params
    private Long get_id()
    {
        return _data == null ? 0 : _data.get_id();
    }

    private Tag get_Tag()
    {
        return (Tag) _spinnerTag.getSelectedItem();
    }
    //endregion

    //region Save Params
    protected Tagged GetDataFromForm()
    {
        try
        {
            return new Tagged(get_id(), null, get_Tag());
        }
        catch (Exception e) { return null; }
    }
    //endregion
}