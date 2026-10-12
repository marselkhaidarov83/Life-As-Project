package com.MH83.LifeAsProject.view.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.view.MainActivity;

public class MoneyTargetAccumulationDoneActivity extends AppCompatActivity
{
    //region Extras
    public static String DreamExtra = "MoneyDream";
    public static String DreamIdExtra = "DreamId";
    public static String TargetExtra = "MoneyTarget";
    public static String TargetIdExtra = "TargetId";
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        CustomTrack.TrackMNotifAccumulationDone();

        String s = getIntent().getStringExtra(DreamIdExtra);
        String s2 = getIntent().getStringExtra(TargetIdExtra);
        if (s.contains(DreamExtra) && s2.contains(TargetExtra))
        {
            globalVar.set_moneyTargetDreamId(Long.parseLong(s.substring(10)));
            globalVar.set_moneyTargetTargetId(Long.parseLong(s2.substring(11)));
            globalVar.set_moneyTargetYear(null);
            globalVar.set_moneyTargetEnumTargetStatus4Settings(null);
            globalVar.set_mainActivity_navigationId(R.id.navigation_money_target);
        }

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(resultIntent);
        finish();

        super.onCreate(savedInstanceState);
    }
}
