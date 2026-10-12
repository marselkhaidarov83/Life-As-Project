package com.MH83.LifeAsProject.view.Custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;

public abstract class CustomFragmentPopup<TDataSet extends CustomMoneyObject>
{
    protected Context _context;
    protected View _popupView;
    protected IFragmentViewCustom<TDataSet> iFragmentViewCustom;
    protected EnumTypeAction _typeAction;
    protected TextView textViewTitle;
    protected String buttonTxt;
    protected AlertDialog alertDialog;

    public CustomFragmentPopup(Context context, IFragmentViewCustom<TDataSet> fragment,
                               EnumTypeAction typeAction)
    {
        _context = context;
        iFragmentViewCustom = fragment;
        _typeAction = typeAction;
    }

    protected void setDateView(String month, String year, TextView textView)
    {
        CustomStaticClass.setDateView(_context, month, year, textView);
    }

    //region MakeDialog
    @SuppressLint("ResourceAsColor")
    protected void SetBasicParams(int addTitle, int updateTitle)
    {
        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();

        buttonTxt = _context.getString(R.string.buttonAddText);
        int popupTile = addTitle;
        if (_typeAction == EnumTypeAction.Update)
        {
            buttonTxt = _context.getString(R.string.buttonUpdateText);
            popupTile = updateTitle;
        }

        textViewTitle = new TextView(_context);
        textViewTitle.setText(popupTile);
        textViewTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewTitle.setBackgroundColor(R.color.blue);
        if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            textViewTitle.setTextSize(20);
        else
            textViewTitle.setTextSize(18);
        textViewTitle.setPadding(10, 10, 10, 10);
    }

    protected boolean CheckPopupShow()
    {
//        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
//        if (!globalVariable.get_hasSubscription())
//        {
//            Toast.makeText(iFragmentViewCustom.getActivity(),
//                    _context.getString(R.string.ErrorSubscription), Toast.LENGTH_LONG).show();
//
//            return false;
//        }

        return true;
    }

    protected void MakeAlertDialog(int addTitle, int updateTitle,
                                   int error)
    {
        MakeAlertDialog(addTitle, updateTitle, error, null, false,
                null, false);
    }
    protected void MakeAlertDialog(int addTitle, int updateTitle, int error,
                                   View focusedViewToAdd, boolean showInputToAdd,
                                   View focusedViewToUpdate, boolean showInputToUpdate)
    {
        SetBasicParams(addTitle, updateTitle);

        AlertDialog.Builder mDialogBuilder1 = new AlertDialog.Builder(_context);
        mDialogBuilder1.setView(_popupView);
        mDialogBuilder1
                .setCancelable(false)
                .setCustomTitle(textViewTitle)
                .setNegativeButton(_context.getString(R.string.buttonCancelText),
                        (dialog1, id) -> { });

        if (_typeAction == EnumTypeAction.Insert || _typeAction == EnumTypeAction.Update)
            mDialogBuilder1.setPositiveButton(buttonTxt, (dialog1, id) -> { });

        if (_typeAction == EnumTypeAction.Update && ShowDeleteButton())
            mDialogBuilder1.setNeutralButton(_context.getString(R.string.buttonDeleteText),
                    (dialog1, id) -> { });

        alertDialog = mDialogBuilder1.create();
        alertDialog.show();

        if (_typeAction == EnumTypeAction.Insert && focusedViewToAdd != null)
        {
            focusedViewToAdd.requestFocus();
            if (showInputToAdd)
                alertDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        if (_typeAction == EnumTypeAction.Update && focusedViewToUpdate != null)
        {
            focusedViewToUpdate.requestFocus();
            if (showInputToUpdate)
                alertDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        int widthDialog = GetWidthDialogPx();
        int heightDialog = GetHeightDialogPx();

        if (alertDialog.getWindow() != null && (widthDialog != 0 || heightDialog != 0))
        {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            if (widthDialog != 0)
                lp.width = widthDialog;
            if (heightDialog != 0)
                lp.height = heightDialog;
            alertDialog.getWindow().setAttributes(lp);
        }

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v ->
        {
            if (CheckFormValues())
            {
                TDataSet dataSet = SaveFormValues(error);
                if (dataSet != null && iFragmentViewCustom != null)
                {
                    try
                    {
                        if (_typeAction == EnumTypeAction.Insert)
                            iFragmentViewCustom.refreshViewOnAdd(dataSet);
                        else if (_typeAction == EnumTypeAction.Update)
                            iFragmentViewCustom.refreshViewOnUpdate(dataSet);
                    }
                    catch (Exception e) { }

                    alertDialog.dismiss();
                }
            }
        });

        if (_typeAction == EnumTypeAction.Update && ShowDeleteButton())
            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(view1 ->
            {
                try
                {
                    if (CheckFormValuesForDelete())
                    {
                        TDataSet dataSet = GetDataFromForm();
                        if (DeleteItem(dataSet))
                        {
                            iFragmentViewCustom.refreshViewOnDelete(dataSet);
                            alertDialog.cancel();
                        }
                    }
                }
                catch (Exception e) { }
            });
    }
    protected int GetWidthDialogPx() { return 0; }
    protected int GetHeightDialogPx() { return 0; }
    //endregion

    //region Abstract Methods
    protected abstract void SetValues(Object[] objects);
    protected abstract boolean ShowDeleteButton();
    protected abstract boolean CheckFormValues();
    protected abstract boolean CheckFormValuesForDelete();
    protected abstract TDataSet GetDataFromForm();
    //endregion

    //region Save Params
    protected TDataSet SaveFormValues(int error)
    {
        TDataSet dataSet = null;
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (_context != null)
            {
                dataSet = GetDataFromForm();
                if (_typeAction == EnumTypeAction.Insert)
                    dataSet.AddToDatabase(dbHelper, _context);
                else if (_typeAction == EnumTypeAction.Update)
                {
                    if (!dataSet.UpdateInDatabase(dbHelper, _context))
                    {
                        Toast.makeText(iFragmentViewCustom.getActivity(),
                                _context.getString(error), Toast.LENGTH_SHORT).show();
                        dataSet = null;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(_context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return dataSet;
    }
    //endregion

    //region Select
    protected void SelectItem()
    {
        iFragmentViewCustom.refreshViewOnSelect(GetDataFromForm());
        alertDialog.cancel();
    }
    //endregion

    //region Delete Item
    protected boolean DeleteItem(TDataSet dataSet)
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            return dataSet.DeleteFromDatabase(dbHelper, _context);
        }
        catch (Exception e)
        {
            Toast.makeText(_context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //endregion
}
