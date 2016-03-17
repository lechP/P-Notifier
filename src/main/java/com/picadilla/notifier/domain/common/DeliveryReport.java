package com.picadilla.notifier.domain.common;

import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Object describing result of the notification delivery
 */
public class DeliveryReport {

    private Date deliveryDate;

    public boolean isDelivered() {
        return deliveryDate != null;
    }

    public Date getDeliveryDate() {
        return new Date(deliveryDate.getTime());
    }

    /**
     * should be created only by factory methods
     */
    private DeliveryReport() {
    }

    /**
     * Creates report for succesfull delivery
     *
     * @param deliveryDate date of the delivery
     */
    public static DeliveryReport succeed(@Nonnull Date deliveryDate) {
        Assert.notNull(deliveryDate);
        DeliveryReport report = new DeliveryReport();
        report.deliveryDate = new Date(deliveryDate.getTime());
        return report;
    }

    /**
     * Creates report for failed delivery
     */
    public static DeliveryReport failed() {
        return new DeliveryReport();
    }

}
