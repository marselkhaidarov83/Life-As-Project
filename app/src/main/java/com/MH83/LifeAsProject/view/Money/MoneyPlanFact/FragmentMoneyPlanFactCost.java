package com.MH83.LifeAsProject.view.Money.MoneyPlanFact;

import android.os.Bundle;

public class FragmentMoneyPlanFactCost extends FragmentMoneyPlanFact
{
    //region Constructors
    public FragmentMoneyPlanFactCost()
    {
        super();
    }
    //endregion

    //region StartFragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        StopNotif();

//        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
//        globalVariable.set_enumMoneyIncomingAndCostTypeData(EnumMoneyIncAndCostSetting.IncomingPlan);
//        globalVariable.set_moneyCategorySettings_fromItem(R.id.navigation_money_incoming);

        super.onCreate(savedInstanceState);
    }

    private void StopNotif()
    {
//        NotificationManager notificationManager =
//                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(NotifMoneySettings.NotifIdActualMonthPlanIncoming);
    }
    //endregion
}