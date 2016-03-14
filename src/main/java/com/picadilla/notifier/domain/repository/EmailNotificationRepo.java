package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.model.EmailNotification;
import com.picadilla.notifier.domain.model.Notification;
import com.picadilla.notifier.business.strategy.NotificationStrategy;
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
public class EmailNotificationRepo implements NotificationRepo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NotificationStrategy strategy;
    @Value("${notifier.batch.size}")
    private int sizeOfBunch;

    public List<EmailNotification> prepareNotSentBefore(@Nonnull Date maxDate) {
        Assert.notNull(maxDate);
        List<EmailNotification> notifications = em.createNamedQuery("Notification.bunchToNotify", EmailNotification.class)
                .setParameter("maxDate", maxDate)
                .setMaxResults(sizeOfBunch).getResultList();
        notifications.forEach(notification -> notification.prepare(strategy));
        return notifications;
    }

    @Override
    public void update(List<? extends Notification> notifications) {

    }
}
