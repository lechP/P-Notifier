package com.picadilla.notifier.testutil;

import com.picadilla.notifier.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationDemoRepository notificationRepository;

    public List<Notification> getAllNotifications(){
        return notificationRepository.getAllNotifications();
    }

    public void saveNotification(Notification notification){
        notificationRepository.save(notification);
    }

}
