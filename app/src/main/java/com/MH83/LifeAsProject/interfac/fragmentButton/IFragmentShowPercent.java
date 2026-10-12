package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

public interface IFragmentShowPercent
{
    boolean ShowPercent(Context context);
    boolean HidePercent(Context context);
    View.OnClickListener onClickToShowPercent();
    View.OnClickListener onClickToHidePercent();
}
