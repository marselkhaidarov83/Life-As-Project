package com.MH83.LifeAsProject.view.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.view.MainActivity;

public class MoneyCostActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        CustomTrack.TrackMNotifFromActualMonthPlanCost();

        globalVar.set_mainActivity_navigationId(R.id.navigation_money_costs);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(resultIntent);
        finish();

        super.onCreate(savedInstanceState);
    }
}
