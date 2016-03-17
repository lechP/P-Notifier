package com.picadilla.notifier.domain.notification;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Embedded id of the {@link EmailNotification}
 */
@Embeddable
public class NotificationId implements Serializable {

    private static final long serialVersionUID = 9073543221315L;

    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "NOTIFICATION_DATE", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    /**
     * for JPA provider use only
     */
    protected NotificationId() {
    }

    /**
     * should not be created outside the package
     */
    NotificationId(String email, Date date) {
        this.email = email;
        this.date = new Date(date.getTime());
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationId that = (NotificationId) o;

        return email.equals(that.email) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NotificationId{" +
                "email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
