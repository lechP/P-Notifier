package com.picadilla.notifier.domain;

public interface NotificationStrategy {

    void send(Notification notification);

}
