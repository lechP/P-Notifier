package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.notification.EmailNotification;
import com.picadilla.notifier.domain.notification.NextNotificationState;
import com.picadilla.notifier.domain.strategy.NotificationStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log log = LogFactory.getLog(EmailNotificationRepo.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NotificationStrategy strategy;
    @Value("${notifier.batch.size}")
    private int batchSize;

    public List<EmailNotification> prepareNotSentAfter(@Nonnull Date maxDate) {
        Assert.notNull(maxDate);
        log.debug("Searching for notifications not sent after " + maxDate);

        List<EmailNotification> notifications = em.createNamedQuery("Notification.batchToNotify", EmailNotification.class)
                .setParameter("maxDate", maxDate)
                .setMaxResults(batchSize).getResultList();
        notifications.forEach(notification -> notification.prepare(strategy));

        log.debug(notifications.size() + " notifications found ");
        return notifications;
    }

    @Override
    public void update(@Nonnull List<EmailNotification> notifications) {
        Assert.notNull(notifications);
        notifications.forEach(notification -> {
            em.merge(notification);
            if (notification.getNextNotificationState() == NextNotificationState.SENT) {
                em.persist(notification.getNextNotification());
            }
        });
        log.debug("Updated " + notifications.size() + " notifications and theirs successors in db.");
    }
}
