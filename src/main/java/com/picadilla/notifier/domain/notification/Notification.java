package com.picadilla.notifier.domain.notification;

import com.picadilla.notifier.domain.strategy.NotificationStrategy;

public interface Notification {

    void prepare(NotificationStrategy strategy);

    void send();

}
