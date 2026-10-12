package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.view.View;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

public interface IFragmentManageMoneyCategory
{
    View.OnClickListener onClickToManageMoneyCategory();
    EnumTypeCategory getTypeCategory();
    int getFromItem();
}
