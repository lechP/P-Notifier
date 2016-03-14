package com.picadilla.notifier.service;

import com.picadilla.notifier.business.service.EmailNotifier;
import com.picadilla.notifier.domain.notification.Notification;
import com.picadilla.notifier.domain.repository.NotificationRepo;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EmailNotifierTest {

    @InjectMocks
    private EmailNotifier testedObject;
    @Mock
    private NotificationRepo notificationRepo;
    @Mock
    private Notification notification;

    private ArgumentCaptor<Date> argument;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        argument = ArgumentCaptor.forClass(Date.class);
    }

    @Test
    public void shouldGetSendAndUpdateNotifications() throws Exception {
        //given
        final int daysOfDelay = 5;
        ReflectionTestUtils.setField(testedObject, "daysOfDelay", daysOfDelay);
        List<Notification> preparedNotifications = Lists.newArrayList(notification, notification);
        doReturn(preparedNotifications).when(notificationRepo).prepareNotSentBefore(any(Date.class));
        //when
        Date beforeCall = new Date();
        testedObject.notifyBunchOfPlayers();
        Date afterCall = new Date();
        //then
        verify(notificationRepo).prepareNotSentBefore(argument.capture());
        assertThat(argument.getValue()).isBetween(moveDateByDays(beforeCall, -daysOfDelay), moveDateByDays(afterCall, -daysOfDelay));
        verify(notification, times(2)).send();
        verify(notificationRepo).update(preparedNotifications);
    }

    private Date moveDateByDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}