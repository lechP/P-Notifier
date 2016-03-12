package com.picadilla.notifier.testutil;

import com.picadilla.notifier.domain.EmailNotification;
import com.picadilla.notifier.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo<EmailNotification> notificationRepository;

    public List<EmailNotification> getAllNotifications(){
        return notificationRepository.prepareNotSentAfter(new Date());
    }

}
