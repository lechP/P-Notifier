package com.picadilla.notifier.domain;

import org.springframework.util.Assert;
import org.apache.commons.validator.routines.EmailValidator;
import javax.annotation.Nonnull;
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

    /** for JPA provider use only */
    protected NotificationId() {}

    public NotificationId(@Nonnull String email, @Nonnull Date date){
        Assert.notNull(email);
        Assert.isTrue(EmailValidator.getInstance().isValid(email));
        Assert.notNull(date);
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
