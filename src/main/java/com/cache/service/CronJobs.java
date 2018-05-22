package com.cache.service;

import com.cache.service.cacheMapApi.impl.CacheMapApiImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class CronJobs implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String mapName = jobExecutionContext.getJobDetail().getKey().getName();
        try {
            new CacheMapApiImpl().loadMapByName(mapName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
