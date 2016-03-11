package com.picadilla.notifier.repository;

import com.picadilla.notifier.model.GmailNotificationStrategy;
import com.picadilla.notifier.model.Notification;
import com.picadilla.notifier.model.NotificationEntity;
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
public class NotificationRepository {

    @PersistenceContext
    private EntityManager em;

    public List<? extends Notification> getNotSentBefore(@Nonnull Date maxDate) {
        Assert.notNull(maxDate);
        List<NotificationEntity> notifications = em.createNamedQuery("Notification.All", NotificationEntity.class)
                .setParameter("maxDate", maxDate)
                .setMaxResults(100).getResultList();
        notifications.forEach(notification -> notification.prepare(new GmailNotificationStrategy()));
        return notifications;
    }
}
