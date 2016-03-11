package com.picadilla.notifier.model;

public interface Notification {

    void prepare(NotificationStrategy strategy);

    void send();

}
