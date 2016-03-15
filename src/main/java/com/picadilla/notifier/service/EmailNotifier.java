package com.picadilla.notifier.service;

import com.picadilla.notifier.domain.notification.EmailNotification;
import com.picadilla.notifier.domain.repository.NotificationRepo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class EmailNotifier implements Notifier {

    private static final Log log = LogFactory.getLog(EmailNotifier.class);

    @Autowired
    private NotificationRepo<EmailNotification> notificationRepo;

    //TODO move down?
    @Value("${notifier.delay.period.days}")
    private int daysOfDelay;

    @Override
    public void notifyBatchOfPlayers() {
        log.debug("Notifying next batch of players.");
        List<EmailNotification> notifications = notificationRepo.prepareNotSentAfter(getShiftedDate(daysOfDelay));
        notifications.forEach(EmailNotification::send);
        notificationRepo.update(notifications);
    }

    private Date getShiftedDate(int days) {
        LocalDateTime time = LocalDateTime.now().minus(Period.ofDays(days));
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
}
