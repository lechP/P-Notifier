package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.model.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationRepo {

    List<? extends Notification> prepareNotSentBefore(Date maxDate);

    void update(List<? extends Notification> notifications);
}
