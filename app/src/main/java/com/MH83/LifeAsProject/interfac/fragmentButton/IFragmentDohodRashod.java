package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;

public interface IFragmentDohodRashod
{
    View.OnClickListener onClickToSetDohod();
    View.OnClickListener onClickToSetRashod();
    EnumTypeCategory GetTypeCategory(Context context);
}
