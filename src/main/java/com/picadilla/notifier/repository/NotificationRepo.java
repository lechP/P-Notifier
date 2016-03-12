package com.picadilla.notifier.repository;

import com.picadilla.notifier.domain.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationRepo {

    List<? extends Notification> prepareNotSentBefore(Date maxDate);

    void update(List<? extends Notification> notifications);
}
