package com.picadilla.notifier.business.strategy;

import com.picadilla.notifier.domain.model.Notification;

//TODO deal with circular dependency between model.Notification and strategy.NotificationStrategy
public interface NotificationStrategy {

    void send(Notification notification);

}
