package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

public interface IFragmentShowFullData
{
    boolean ShowFullData(Context context);
    boolean HideFullData(Context context);
    View.OnClickListener onClickToShowFullData();
    View.OnClickListener onClickToHideFullData();
}
