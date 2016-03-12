package com.picadilla.notifier.domain;

import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t")
@NamedQueries({
        @NamedQuery(name = "Notification.All", query = "SELECT n FROM NotificationEntity n " +
                "WHERE n.statusOfNext = 'NONE' AND n.id.date <= :maxDate"),
        @NamedQuery(name = "Notification.top100toNotify", query = "SELECT n FROM NotificationEntity n")})
public class NotificationEntity implements Notification {

    @EmbeddedId
    private NotificationId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_OF_NEXT")
    private NextNotificationStatus statusOfNext;

    @Transient
    private NotificationStrategy strategy;

    public NextNotificationStatus getStatusOfNext() {
        return statusOfNext;
    }

    public String getEmail() {
        return id.getEmail();
    }

    public Date getDate() {
        return new Date(id.getDate().getTime());
    }

    public static NotificationEntity newNotification(String email, Date date) {
        NotificationEntity result = new NotificationEntity();
        result.statusOfNext = NextNotificationStatus.NONE;
        result.id = new NotificationId(email, new Date(date.getTime()));
        return result;
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
        strategy.send(this);
    }
}
