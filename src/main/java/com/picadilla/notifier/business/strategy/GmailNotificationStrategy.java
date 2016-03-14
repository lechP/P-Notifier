package com.picadilla.notifier.business.strategy;

import com.picadilla.notifier.domain.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class GmailNotificationStrategy implements NotificationStrategy {

    @Override
    public void send(Notification notification) {

    }
}
