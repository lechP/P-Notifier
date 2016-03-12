package com.picadilla.notifier.domain;

public interface Notification {

    void prepare(NotificationStrategy strategy);

    void send();

}
