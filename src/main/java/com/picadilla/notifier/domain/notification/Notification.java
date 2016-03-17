package com.picadilla.notifier.domain.notification;

import com.picadilla.notifier.domain.strategy.NotificationStrategy;

/**
 * Interface representing behaviour of any kind of notification
 */
public interface Notification {

    /**
     * Prepares notification to be sent
     *
     * @param strategy strategy which should be used during sending
     */
    void prepare(NotificationStrategy strategy);

    /**
     * sends the notification and updates its state accordingly
     */
    void send();

}
