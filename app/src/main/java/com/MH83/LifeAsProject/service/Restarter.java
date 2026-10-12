package com.MH83.LifeAsProject.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class Restarter extends BroadcastReceiver
{
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("Broadcast Listened", "Restarter");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(
                    NotificationWorker.class).build();
            WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);

//            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
//                    NotificationWorker.class, 15, TimeUnit.MINUTES)
//                    .build();
//            WorkManager.getInstance(this).enqueue(periodicWorkRequest);
        }

//        Log.i("Broadcast Listened", "Service tried to stop");
//        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            context.startForegroundService(new Intent(context, NotificationService.class));
//        else
//            context.startService(new Intent(context, NotificationService.class));
    }
}
