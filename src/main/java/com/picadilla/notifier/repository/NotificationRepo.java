package com.picadilla.notifier.repository;

import com.picadilla.notifier.domain.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationRepo<T extends Notification> {

    List<T> prepareNotSentAfter(Date maxDate);

    void update(List<T> notifications);
}
