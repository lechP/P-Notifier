package com.picadilla.notifier.demo;

import com.picadilla.notifier.model.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional //TODO props??
public class NotificationDemoResource {

    @PersistenceContext
    private EntityManager em;

    public List<Notification> getAllNotifications() {
        return em.createNamedQuery("Notification.All", Notification.class).getResultList();
    }


}
