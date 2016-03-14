package com.picadilla.notifier.domain.model;

import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;

public class DeliveryReport {

    private Date deliveryDate;

    public boolean isDelivered(){
        return deliveryDate != null;
    }

    public Date getDeliveryDate(){
        return new Date(deliveryDate.getTime());
    }

    public DeliveryReport(@Nonnull Date deliveryDate){
        Assert.notNull(deliveryDate);
        this.deliveryDate = new Date(deliveryDate.getTime());
    }

}
