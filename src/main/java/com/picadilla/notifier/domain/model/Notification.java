package com.picadilla.notifier.domain.model;

import com.picadilla.notifier.business.strategy.NotificationStrategy;

public interface Notification {

    void prepare(NotificationStrategy strategy);

    void send();

}
