package com.MH83.LifeAsProject.view.Money.MoneyIncoming;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.os.Bundle;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;

import java.util.ArrayList;

public class FragmentMoneyIncoming extends FragmentMoneyIncomingAndCost
{
    //region Constructors
    public FragmentMoneyIncoming()
    {
        super(EnumTypeCategory.Increase, EnumTypeData.Plan);
    }
    //endregion

    //region StartFragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        StopNotif();

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingPlan);
//        globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_incoming);

        super.onCreate(savedInstanceState);
    }

    private void StopNotif()
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifMoneySettings.NotifIdActualMonthPlanIncoming);
    }
    //endregion
}