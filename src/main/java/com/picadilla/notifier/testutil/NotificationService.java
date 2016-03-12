package com.picadilla.notifier.testutil;

import com.picadilla.notifier.domain.Notification;
import com.picadilla.notifier.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    public List<? extends Notification> getAllNotifications() {
        return notificationRepo.prepareNotSentBefore(new Date());
    }

}
