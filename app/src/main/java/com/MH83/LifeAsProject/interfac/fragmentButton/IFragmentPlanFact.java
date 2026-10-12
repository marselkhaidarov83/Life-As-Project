package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeView;

public interface IFragmentPlanFact
{
    boolean needPlanButton(Context context);
    View.OnClickListener onClickToSetPlan();
    boolean needFactButton(Context context);
    View.OnClickListener onClickToSetFact();
//    EnumTypeData GetTypeData();
}
