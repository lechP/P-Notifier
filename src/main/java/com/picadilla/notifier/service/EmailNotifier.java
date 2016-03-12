package com.picadilla.notifier.service;

import com.picadilla.notifier.domain.Notification;
import com.picadilla.notifier.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailNotifier implements Notifier {

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public void notifyBunchOfPlayers() {
        //TODO is there possibility to get rid of "? extends"
        List<? extends Notification> notifications = notificationRepo.prepareNotSentAfter(new Date());
        notifications.forEach(Notification::send);
        notificationRepo.update(notifications);
    }
}
