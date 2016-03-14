package com.picadilla.notifier.domain.common;

import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;

public class DeliveryReport {

    private Date deliveryDate;

    public boolean isDelivered() {
        return deliveryDate != null;
    }

    public Date getDeliveryDate() {
        return new Date(deliveryDate.getTime());
    }

    private DeliveryReport() {
    }

    public static DeliveryReport succeed(@Nonnull Date deliveryDate) {
        Assert.notNull(deliveryDate);
        DeliveryReport report = new DeliveryReport();
        report.deliveryDate = new Date(deliveryDate.getTime());
        return report;
    }

    public static DeliveryReport failed() {
        return new DeliveryReport();
    }

}
