package com.MH83.LifeAsProject.view.Custom;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;

public class CustomFragment extends Fragment implements IFragmentGotoSocial, IFragmentGotoCabinet
{
    //region IFragmentGotoSocial
    @Override
    public boolean ShowGotoCabinetButton(Context context)
    {
        GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        return globalVariable.get_showToBuyButtons();
    }
    //endregion

    //region IFragmentGotoCabinet
    @Override
    public boolean ShowGotoSocialButton(Context context)
    {
        GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        return globalVariable.get_showToBuyButtons();
    }
    //endregion
}
