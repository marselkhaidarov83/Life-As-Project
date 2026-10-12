package com.MH83.LifeAsProject.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.view.MainActivity;

public class MoneyDreamActivity extends AppCompatActivity
{
    //region Extras
    public static String NavigationIdExtra = "NavigationId";
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        GlobalClass globalVar = (GlobalClass) getApplicationContext();
        CustomTrack.TrackPNotifFromNeedDream();

        String s = getIntent().getStringExtra(NavigationIdExtra);

        if (s != null && s.equals("MoneyDream"))
            globalVar.set_mainActivity_navigationId(R.id.navigation_money_dream);

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(resultIntent);
        finish();

        super.onCreate(savedInstanceState);
    }
}
