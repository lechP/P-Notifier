package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.notification.EmailNotification;
import com.picadilla.notifier.domain.notification.NextNotificationState;
import com.picadilla.notifier.domain.strategy.NotificationStrategy;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EmailNotificationRepoTest {

    @InjectMocks
    private EmailNotificationRepo testedObject;
    @Mock
    private EntityManager em;
    @Mock
    private NotificationStrategy strategy;

    @Mock
    private EmailNotification persisted, undelivered, delivered, nextNotification;
    @Mock
    private TypedQuery<EmailNotification> query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPrepareEveryNotificationFromDb() throws Exception {
        //given
        Date date = new Date();
        List<EmailNotification> notifications = Lists.newArrayList(persisted, persisted);
        namedQueryShouldReturn(notifications);
        //when
        testedObject.prepareNotSentAfter(date);
        //then
        verify(persisted, times(2)).prepare(strategy);
    }

    private void namedQueryShouldReturn(List<EmailNotification> notifications) {
        when(em.createNamedQuery(anyString(), eq(EmailNotification.class))).thenReturn(query);
        when(query.setParameter(anyString(), any(Date.class))).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(notifications);
    }

    @Test
    public void shouldMergeBothExistingNotificationsAndPersistOneNewNotification() throws Exception {
        //given
        when(delivered.getNextNotificationState()).thenReturn(NextNotificationState.SENT);
        when(delivered.getNextNotification()).thenReturn(nextNotification);
        when(undelivered.getNextNotificationState()).thenReturn(NextNotificationState.NONE);
        //when
        testedObject.update(Lists.newArrayList(delivered, undelivered));
        //then
        verify(em).merge(delivered);
        verify(em).merge(undelivered);
        verify(em).persist(nextNotification);
    }
}