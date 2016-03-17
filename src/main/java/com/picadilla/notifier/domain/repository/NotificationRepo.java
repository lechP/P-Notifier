package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.notification.Notification;

import java.util.Date;
import java.util.List;

/**
 * Repository for receiving and updating notifications in datasource
 *
 * @param <T> any form of {@link Notification}
 */
public interface NotificationRepo<T extends Notification> {

    /**
     * Receives notifications which were not send after the specified date
     *
     * @param maxDate notifications sent after this date should be ignored
     * @return list of notifications
     */
    List<T> prepareNotSentAfter(Date maxDate);

    /**
     * Updates the state of notifications in the datasource
     *
     * @param notifications notifications to be updated
     */
    void update(List<T> notifications);
}
