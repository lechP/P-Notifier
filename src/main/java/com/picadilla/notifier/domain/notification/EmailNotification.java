package com.picadilla.notifier.domain.notification;

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
        @NamedQuery(name = "Notification.bunchToNotify", query = "SELECT n FROM EmailNotification n " +
                "WHERE n.statusOfNext = 'NONE' AND n.id.date <= :maxDate")})
public class EmailNotification implements Notification, Serializable {

    private static final long serialVersionUID = 10985353323534L;

    @EmbeddedId
    private NotificationId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_OF_NEXT")
    private NextNotificationStatus statusOfNext;

    @Transient
    private NotificationStrategy strategy;

    /** for JPA provider use only */
    protected EmailNotification(){}

    public EmailNotification(@Nonnull String email, @Nonnull Date date){
        Assert.notNull(email);
        Assert.isTrue(EmailValidator.getInstance().isValid(email));
        Assert.notNull(date);
        this.statusOfNext = NextNotificationStatus.NONE;
        this.id = new NotificationId(email, new Date(date.getTime()));
    }

    public NextNotificationStatus getStatusOfNext() {
        return statusOfNext;
    }

    public String getEmail() {
        return id.getEmail();
    }

    public Date getDate() {
        return new Date(id.getDate().getTime());
    }

    @Override
    public String toString() {
        return "Email: " + getEmail() + " date: " + getDate() + " status of nxt: " + statusOfNext;
    }

    @Override
    public void prepare(@Nonnull NotificationStrategy strategy) {
        Assert.notNull(strategy);
        this.strategy = strategy;
        statusOfNext = NextNotificationStatus.IN_PROGRESS;
    }

    @Override
    public void send() {
        if(strategy==null){
            throw new UnpreparedNotificationException("Notification should have been prepared before it is to be sent");
        }
        strategy.send(getEmail());
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
}
