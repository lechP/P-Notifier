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
    @Value("${notifier.notification.period.days}")
    private int notificationPeriod;

    @Override
    public void schedule() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> notifier.notifyBunchOfPlayers(), 0, notificationPeriod, TimeUnit.MINUTES);
    }
}
