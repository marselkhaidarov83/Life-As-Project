package com.MH83.LifeAsProject.adapter.viewPager;

import android.support.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionValue;

public class ViewPagerAdapter extends FragmentStateAdapter
{
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) { super(fragmentActivity); }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch (position)
        {
            case 0:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value0);
            case 1:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value1);
            case 2:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value2);
            case 3:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value3);
            case 4:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value4);
            case 5:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value5);
            case 6:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value6);
            case 7:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value7);
            case 8:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value8);
            case 9:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_1);
            case 10:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_2);
            case 11:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_3);
            case 12:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_4);
            case 13:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_5);
            case 14:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_6);
            case 15:
                return FragmentSubscriptionValue.newInstance(R.string.Popup_Subscription_Value_7);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount()
    {
        return 16;  // adjust this value based on the number of tabs you want
    }
}
