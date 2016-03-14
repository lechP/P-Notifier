package com.picadilla.notifier.domain.strategy;

import com.picadilla.notifier.domain.common.DeliveryReport;

//TODO consider place out of domain??
public interface NotificationStrategy {

    DeliveryReport send(String recipient);

}
