package com.picadilla.notifier.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

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
}
