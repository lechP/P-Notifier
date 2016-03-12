package com.picadilla.notifier.service;

import com.picadilla.notifier.domain.EmailNotification;
import com.picadilla.notifier.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailNotifier implements Notifier {

    @Autowired
    private NotificationRepo<EmailNotification> notificationRepo;

    @Override
    public void notifyBunchOfPlayers() {
        //TODO here should be used interface Notification...
        List<EmailNotification> notifications = notificationRepo.prepareNotSentAfter(new Date());
        notifications.forEach(EmailNotification::send);
        notificationRepo.update(notifications);
    }
}
