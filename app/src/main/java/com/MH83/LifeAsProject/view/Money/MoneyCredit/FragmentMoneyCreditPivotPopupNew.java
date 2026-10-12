package com.MH83.LifeAsProject.view.Money.MoneyCredit;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.MH83.LifeAsProject.model.Money.MoneyCreditPivotPoint;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

public class FragmentMoneyCreditPivotPopupNew extends CustomPopupBottomFragment<MoneyCreditPivotPoint>
    implements IFragmentViewCustom
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_credit_pivot_new;
    //endregion

    //region Fragment parameters
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    private EditText _editTextStartSum;
    private EditText _editTextLoanTerm;
    private EditText _editTextCreditPercent;
    //endregion

    //region Constructors
    public FragmentMoneyCreditPivotPopupNew()
    {
        super(_layoutId,
                R.string.fragmentMoneyCreditPivotPopupAddTitle,
                R.string.fragmentMoneyCreditPivotPopupUpdateTitle,
                R.string.errorUpdateMoneyCreditPivot);
    }

    public FragmentMoneyCreditPivotPopupNew(AppCompatActivity activity,
                                            MoneyCreditPivotPoint data,
                                            EnumTypeAction typeAction,
                                            IFragmentViewCustom<MoneyCreditPivotPoint> iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                null,
                typeAction,
                null,
                R.string.fragmentMoneyCreditPivotPopupAddTitle,
                R.string.fragmentMoneyCreditPivotPopupUpdateTitle,
                R.string.errorUpdateMoneyCreditPivot,
                null,
                iFragmentViewCustom,
                null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _editTextCreditPercent = view.findViewById(R.id.editTextPercent);
        _editTextStartSum = view.findViewById(R.id.editTextStartSum);
        _editTextLoanTerm = view.findViewById(R.id.editTextLoanTerm);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextStartSum;
        _focusedViewToUpdate = _editTextStartSum;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
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
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Credit_Pivot_Width),
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
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Credit_Pivot_Width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }
    //endregion

    //region Set Listeners
    public void ClearFocus()
    {
        _editTextStartSum.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editTextStartSum.getWindowToken(), 0);
    }
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
            set_StartSumLong(_data.get_startSumLong());
            set_loanTerm(_data.get_loanTerm());
            set_percent(_data.get_percent());
        }
        else
        {
            set_StartSumLong(0L);
            set_loanTerm(0);
            set_percent(0d);
        }
    }

    private void set_StartSumLong(Long sumLong)
    {
        if (sumLong == 0L)
            _editTextStartSum.setText("");
        else if (Math.floorMod(sumLong, 100L) == 0)
            _editTextStartSum.setText(String.valueOf(Math.floorDiv(sumLong, 100L)));
        else
            _editTextStartSum.setText(String.valueOf(sumLong / 100d));
    }

    private void set_percent(Double percent)
    {
        if (percent == 0d)
            _editTextCreditPercent.setText("");
        else if ((100 * percent.intValue()) == (100 * percent))
        {
            _editTextCreditPercent.setText(String.valueOf(percent.intValue()));
        }
        else
        {
            _editTextCreditPercent.setText(String.valueOf(percent));
        }
    }

    private void set_loanTerm(int loanTerm)
    {
        if (loanTerm == 0)
            _editTextLoanTerm.setText("");
        else
            _editTextLoanTerm.setText(String.valueOf(loanTerm));
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        if (!CheckStartSum())   return false;
        if (!CheckLoanTerm())   return false;
        if (!CheckPercent())    return false;

        return true;
    }

    private boolean CheckStartSum()
    {
        try
        {
            if (getStartSumLong() == 0L)
            {
                _editTextStartSum.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextStartSum, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifySumm), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextStartSum.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextStartSum, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectSumm), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckLoanTerm()
    {
        try
        {
            if (_editTextLoanTerm.getText().toString().equals(""))
            {
                _editTextLoanTerm.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextLoanTerm, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(_iFragmentViewCustom.getActivity(),
                        getContext().getString(R.string.specifyLoanTerm), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextLoanTerm.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextLoanTerm, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectLoanTerm), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean CheckPercent()
    {
        try
        {
//            if (getPercent() == 0d)
//            {
//                _editTextCreditPercent.requestFocus();
//                InputMethodManager imm =
//                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(_editTextCreditPercent, InputMethodManager.SHOW_IMPLICIT);
//
//                Toast.makeText(_iFragmentViewCustom.getActivity(),
//                        getContext().getString(R.string.specifyCreditPercent), Toast.LENGTH_SHORT).show();
//                return false;
//            }
        }
        catch (Exception e)
        {
            _editTextCreditPercent.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextCreditPercent, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(_iFragmentViewCustom.getActivity(),
                    getContext().getString(R.string.specifyCorrectCreditPercent), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected boolean CheckFormValuesForDelete()
    {
        return true;
    }

    //endregion

    //region Get Params
    private Long get_id()
    {
        if (_data != null)
            return _data.get_id();

        return 0L;
    }

    private Long get_creditId()
    {
        if (_data != null)
            return _data.get_creditId();

        return 0L;
    }

    private Long getStartSumLong() throws Exception
    {
        try
        {
            String s = _editTextStartSum.getText().toString();
            int pos = s.indexOf(".");
            int length = s.length();
            s = s.replace(".", "");
            long res = 0L;
            if (!s.equals(""))
                res = Long.parseLong(s);            if (pos < 0)
                return 100 * res;
            else if (pos + 2 == length)
                return 10 * res;
            else
                return res;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private Double getPercent() throws Exception
    {
        try
        {
            return Double.parseDouble(_editTextCreditPercent.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private int get_loanTerm() throws Exception
    {
        try
        {
            return Integer.parseInt(_editTextLoanTerm.getText().toString());
        }
        catch (Exception e) { throw new Exception(e); }
    }
    //endregion

    //region Save Params
    protected MoneyCreditPivotPoint GetDataFromForm()
    {
        try
        {
            return new MoneyCreditPivotPoint(
                    get_id(),
                    get_creditId(),
                    getStartSumLong(),
                    getPercent(),
                    get_loanTerm());
        }
        catch (Exception e)
        {
            Log.d("error", e.getMessage());
            return null;
        }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion
}