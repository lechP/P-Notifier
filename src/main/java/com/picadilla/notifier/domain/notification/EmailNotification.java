package com.picadilla.notifier.domain.notification;

import com.picadilla.notifier.domain.common.DeliveryReport;
import com.picadilla.notifier.domain.exception.UndeliveriedNotificationException;
import com.picadilla.notifier.domain.exception.UnpreparedNotificationException;
import com.picadilla.notifier.domain.strategy.NotificationStrategy;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t")
@NamedQueries({
        @NamedQuery(name = "Notification.batchToNotify", query = "SELECT n FROM EmailNotification n " +
                "WHERE n.nextNotificationState = 'NONE' AND n.id.date <= :maxDate")})
public class EmailNotification implements Notification, Serializable {

    private static final long serialVersionUID = 10985353323534L;

    @EmbeddedId
    private NotificationId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_OF_NEXT")
    private NextNotificationState nextNotificationState;

    @Transient
    private NotificationStrategy strategy;
    @Transient
    private DeliveryReport deliveryReport;

    /** for JPA provider use only */
    protected EmailNotification(){}

    public EmailNotification(@Nonnull String email, @Nonnull Date date){
        Assert.notNull(email);
        Assert.isTrue(EmailValidator.getInstance().isValid(email));
        Assert.notNull(date);
        this.nextNotificationState = NextNotificationState.NONE;
        this.id = new NotificationId(email, new Date(date.getTime()));
    }

    public NextNotificationState getNextNotificationState() {
        return nextNotificationState;
    }

    public String getEmail() {
        return id.getEmail();
    }

    public Date getDate() {
        return new Date(id.getDate().getTime());
    }

    @Override
    public void prepare(@Nonnull NotificationStrategy strategy) {
        Assert.notNull(strategy);
        this.strategy = strategy;
        nextNotificationState = NextNotificationState.IN_PROGRESS;
    }

    @Override
    public void send() {
        if(strategy==null){
            throw new UnpreparedNotificationException("Notification should have been prepared before it is to be sent");
        }
        deliveryReport = strategy.send(getEmail());
        updateStatus();
    }

    private void updateStatus() {
        if(deliveryReport.isDelivered()){
            nextNotificationState = NextNotificationState.SENT;
        }else{
            nextNotificationState = NextNotificationState.NONE;
        }
    }

    public EmailNotification getNextNotification(){
        if(deliveryReport==null || !deliveryReport.isDelivered()){
            throw new UndeliveriedNotificationException("Next notification wasn't successfully delivered");
        }
        return new EmailNotification(this.getEmail(), deliveryReport.getDeliveryDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailNotification that = (EmailNotification) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "EmailNotification{" +
                "id=" + id +
                ", nextNotificationState=" + nextNotificationState +
                ", strategy=" + strategy +
                ", deliveryReport=" + deliveryReport +
                '}';
    }
}
