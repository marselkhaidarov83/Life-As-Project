package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

public interface IFragmentShowTransactionList
{
    View.OnClickListener onClickToShowList();
    boolean ShowButtonShowList(EnumTypeCategory typeCategory, Context context);
}
