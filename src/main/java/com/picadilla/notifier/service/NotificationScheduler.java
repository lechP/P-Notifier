package com.picadilla.notifier.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationScheduler implements Scheduler {

    private static final Log log = LogFactory.getLog(NotificationScheduler.class);

    @Autowired
    private Notifier notifier;
    @Value("${notifier.notification.period.value}")
    private int periodValue;
    @Value("${notifier.notification.period.unit:HOURS}")
    private String periodUnit;

    @Override
    public void schedule() {
        log.debug("Scheduling notification of players. It will occur once per " + periodValue + " " + periodUnit);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> notifier.notifyBatchOfPlayers(), 0, periodValue, getTimeUnit());
    }

    private TimeUnit getTimeUnit() {
        try {
            return TimeUnit.valueOf(periodUnit);
        } catch (IllegalArgumentException e) {
            return TimeUnit.HOURS;
        }
    }


}
