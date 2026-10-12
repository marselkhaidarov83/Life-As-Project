package com.MH83.LifeAsProject.view.Money.MoneyIncoming;

import android.os.Bundle;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumMoneyIncAndCostSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;

public class FragmentMoneyIncomingFact extends FragmentMoneyIncomingAndCost
{
    //region Constructors
    public FragmentMoneyIncomingFact()
    {
        super(EnumTypeCategory.Increase, EnumTypeData.Fact);
    }
    //endregion

    //region StartFragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
//        StopNotif();

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingFact);
//        globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_incoming_fact);

        super.onCreate(savedInstanceState);
    }

//    private void StopNotif()
//    {
//        NotificationManager notificationManager =
//                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(NotifMoneySettings.NotifIdActualMonthPlanIncoming);
//    }
    //endregion
}