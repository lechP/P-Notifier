package com.picadilla.notifier.testutil;

import com.picadilla.notifier.model.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional //TODO props??
public class NotificationDemoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Notification> getAllNotifications() {
        return em.createNamedQuery("Notification.All", Notification.class).getResultList();
    }

    public void save(Notification notification){
        em.persist(notification);
    }


}
