package com.MH83.LifeAsProject.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Money.MoneyAccountingDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.view.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MoneyWalletOperationsActivity extends AppCompatActivity
{
    //region Extras
    public static String TargetExtra = "Target";
    public static String TargetIdExtra = "TargetId";
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        CustomTrack.TrackMNotifFromNoAccumulationThisMonth();

        String s = getIntent().getStringExtra(TargetIdExtra);
        Log.d("MoneyWalletOperationsActivity", s);
        if (s.contains(TargetExtra))
        {
            CustomPeriodClass customPeriodClass = CustomPeriodClass.GetList(getApplicationContext()).get(2);
            ArrayList<MoneyAccountingSum> dataSetWallets = MoneyAccountingDataSource.LoadDataWallet(
                    getApplicationContext(), customPeriodClass.get_start_dateC(),
                    customPeriodClass.get_end_dateC());

            Long walletId = MoneyCategoryDataSource.LoadOneId4Target(getApplicationContext(),
                    Long.parseLong(s.substring(6)));
//            globalVar.set_pnl_categoryId(walletId);
            globalVar.get_moneyTransactionSettingsHT(EnumTransactionType.Inc4Cost).set_categoryId(walletId);
            globalVar.get_moneyTransactionSettingsHT(EnumTransactionType.Inc4Cost).set_subCategoryId(null);
            globalVar.set_pnl_moneyAccountingSumCost(null);
            for (MoneyAccountingSum mas: dataSetWallets)
                if (Objects.equals(mas.getCategoryId(), walletId))
                {
                    globalVar.set_pnl_moneyAccountingSumIncrease(mas);
                    break;
                }
            globalVar.set_pnl_customPeriodClass(customPeriodClass);
            globalVar.set_pnl_scrollPosition(0);
            globalVar.set_pnl_transactionType(EnumTransactionType.PNL);

            globalVar.set_moneyAccountingSettings_fromItem(R.id.navigation_money_accounting);
            globalVar.set_mainActivity_navigationId(R.id.navigation_money_pnl);
        }

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(resultIntent);
        finish();

        super.onCreate(savedInstanceState);
    }
}
