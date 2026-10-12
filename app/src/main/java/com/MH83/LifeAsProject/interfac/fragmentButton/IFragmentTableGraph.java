package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.view.View;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeView;

public interface IFragmentTableGraph
{
    View.OnClickListener onClickToSetTable();
    View.OnClickListener onClickToSetGraph();
    EnumTypeView GetTypeView();
}
