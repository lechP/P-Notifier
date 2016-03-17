package com.picadilla.notifier.domain.strategy;

import com.picadilla.notifier.domain.common.DeliveryReport;

/**
 * Object which holds the strategy of sending of the notification
 */
public interface NotificationStrategy {

    /**
     * @param recipient address on which notification should be sent.
     * @return {@link DeliveryReport} indicating if send was successful or not.
     */
    DeliveryReport send(String recipient);

}
