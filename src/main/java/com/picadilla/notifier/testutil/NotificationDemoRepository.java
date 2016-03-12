package com.picadilla.notifier.testutil;

import com.picadilla.notifier.domain.EmailNotification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@Transactional //TODO props??
public class NotificationDemoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<EmailNotification> getAllNotifications() {
        return em.createNamedQuery("Notification.All", EmailNotification.class).setParameter("maxDate", new Date()).getResultList();
    }

    public void save(EmailNotification emailNotification){
        em.persist(emailNotification);
    }


}
