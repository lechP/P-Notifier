package com.picadilla.notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationScheduler implements Scheduler {

    @Autowired
    private Notifier notifier;
    @Value("${notifier.notification.period.value}")
    private int periodValue;
    @Value("${notifier.notification.period.unit:HOURS}")
    private String periodUnit;

    @Override
    public void schedule() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> notifier.notifyBunchOfPlayers(), 0, periodValue, getTimeUnit());
    }

    private TimeUnit getTimeUnit(){
        try{
            return TimeUnit.valueOf(periodUnit);
        }catch(IllegalArgumentException e){
            return TimeUnit.HOURS;
        }
    }


}
