package com.MH83.LifeAsProject.view.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.view.MainActivity;

import java.util.Calendar;

public class MoneyTargetActivity extends AppCompatActivity
{
    //region Extras
    public static String TargetExtra = "MoneyTarget";
    public static String DreamIdExtra = "DreamId";
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        CustomTrack.TrackPNotifFromNeedTargetInDream();

        String s = getIntent().getStringExtra(DreamIdExtra);
        if (s.contains(TargetExtra))
        {
            globalVar.set_moneyTargetDreamId(Long.parseLong(s.substring(11)));
            globalVar.set_moneyTargetTargetId(0L);
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
