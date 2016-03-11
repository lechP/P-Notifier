package com.picadilla.notifier.repository;

import com.picadilla.notifier.model.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class NotificationRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Notification> getBunchOfNotifications(){
        return em.createNamedQuery("Notification.All", Notification.class).setMaxResults(100).getResultList();
    }
}
