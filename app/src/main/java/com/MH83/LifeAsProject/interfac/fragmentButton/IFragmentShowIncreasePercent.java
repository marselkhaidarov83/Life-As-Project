package com.MH83.LifeAsProject.interfac.fragmentButton;

import android.content.Context;
import android.view.View;

public interface IFragmentShowIncreasePercent
{
    boolean ShowIncreasePercent(Context context);
    boolean HideIncreasePercent(Context context);
    View.OnClickListener onClickToShowIncreasePercent();
    View.OnClickListener onClickToHideIncreasePercent();
}
