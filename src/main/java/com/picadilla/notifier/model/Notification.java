package com.picadilla.notifier.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t")
@NamedQueries({
        @NamedQuery(name = "Notification.All", query = "SELECT n FROM Notification n") })
public class Notification {

    @EmbeddedId
    private NotificationId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_OF_NEXT")
    private NextNotificationStatus statusOfNext;

    public NextNotificationStatus getStatusOfNext() {
        return statusOfNext;
    }

    public String getEmail() {
        return id.getEmail();
    }

    public Date getDate() {
        return id.getDate();
    }

    public static Notification newNotification(String email, Date date){
        Notification result = new Notification();
        result.statusOfNext = NextNotificationStatus.NONE;
        result.id = new NotificationId(email, date);
        return result;
    }

    @Override
    public String toString(){
        return "Email: " + getEmail() + " date: " + getDate() + " status of nxt: " + statusOfNext;
    }
}
