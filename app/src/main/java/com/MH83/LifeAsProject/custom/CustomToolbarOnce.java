package com.MH83.LifeAsProject.custom;

import android.annotation.SuppressLint;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;

public class CustomToolbarOnce<T>
{
    @SuppressLint("RestrictedApi")
    public ActionMenuItemView RefreshToolbarTopRightOnce(Fragment fragmentContainerMain,
                                            androidx.appcompat.widget.Toolbar toolbar,
                                            int menuId, int buttonId)
    {
        ActionMenuItemView actionMenuItemView = toolbar.findViewById(buttonId);
        T fragment = (T) fragmentContainerMain;
        if (fragment != null)
        {
            if (actionMenuItemView == null)
            {
                toolbar.inflateMenu(menuId);
                actionMenuItemView = toolbar.findViewById(buttonId);
            }
            //actionMenuItemView.setOnClickListener(
              //      ((IFragmentManageMoneyCategory)fragmentContainerMain).onClickToManageMoneyCategory());
            actionMenuItemView.getItemData().setVisible(true);
        }
        else if (actionMenuItemView != null)
        {
            actionMenuItemView.getItemData().setVisible(false);
        }

        return actionMenuItemView;
    }
}
