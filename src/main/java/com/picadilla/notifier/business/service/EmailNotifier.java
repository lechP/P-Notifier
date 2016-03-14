package com.picadilla.notifier.business.service;

import com.picadilla.notifier.domain.model.Notification;
import com.picadilla.notifier.domain.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmailNotifier implements Notifier {

    @Autowired
    private NotificationRepo notificationRepo;

    @Value("${notifier.delay.period.days}")
    private int daysOfDelay;

    @Override
    public void notifyBunchOfPlayers() {
        //TODO is there possibility to get rid of "? extends"
        List<? extends Notification> notifications = notificationRepo.prepareNotSentBefore(getShiftedDate(-daysOfDelay));
        notifications.forEach(Notification::send);
        notificationRepo.update(notifications);
    }

    private Date getShiftedDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}
