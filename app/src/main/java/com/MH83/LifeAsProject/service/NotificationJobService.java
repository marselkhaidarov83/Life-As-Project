package com.MH83.LifeAsProject.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class NotificationJobService extends JobService
{
    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {
        return false;
    }
}
