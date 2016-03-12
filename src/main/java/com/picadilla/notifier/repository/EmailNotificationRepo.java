package com.picadilla.notifier.repository;

import com.picadilla.notifier.domain.EmailNotification;
import com.picadilla.notifier.domain.GmailNotificationStrategy;
import com.picadilla.notifier.domain.Notification;
import com.picadilla.notifier.domain.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class EmailNotificationRepo implements NotificationRepo<EmailNotification> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NotificationStrategy strategy;
    @Value("${notifier.bunch.size}")
    private int sizeOfBunch;

    public List<EmailNotification> prepareNotSentAfter(@Nonnull Date maxDate) {
        Assert.notNull(maxDate);
        List<EmailNotification> notifications = em.createNamedQuery("Notification.All", EmailNotification.class)
                .setParameter("maxDate", maxDate)
                .setMaxResults(sizeOfBunch).getResultList();
        notifications.forEach(notification -> notification.prepare(strategy));
        return notifications;
    }

    @Override
    public void update(List<EmailNotification> notifications) {

    }
}
