package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.notification.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationRepo <T extends Notification> {

    List<T> prepareNotSentAfter(Date maxDate);

    void update(List<T> notifications);
}
