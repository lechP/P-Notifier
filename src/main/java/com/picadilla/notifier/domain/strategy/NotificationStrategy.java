package com.picadilla.notifier.domain.strategy;

import com.picadilla.notifier.domain.common.DeliveryReport;

public interface NotificationStrategy {

    DeliveryReport send(String recipient);

}
