package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

public interface IFragmentShowBlankData
{
    boolean ShowBlankData(Context context);
    boolean HideBlankData(Context context);
    View.OnClickListener onClickToShowBlankData();
    View.OnClickListener onClickToHideBlankData();
}
