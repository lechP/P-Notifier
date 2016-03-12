package com.picadilla.notifier.testutil;

import com.picadilla.notifier.domain.NotificationEntity;
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

    public List<NotificationEntity> getAllNotifications() {
        return em.createNamedQuery("Notification.All", NotificationEntity.class).getResultList();
    }

    public void save(NotificationEntity notificationEntity){
        em.persist(notificationEntity);
    }


}
