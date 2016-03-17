package com.picadilla.notifier.service;

import com.picadilla.notifier.domain.notification.EmailNotification;
import com.picadilla.notifier.domain.repository.NotificationRepo;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EmailNotifierTest {

    @InjectMocks
    private EmailNotifier testedObject;
    @Mock
    private NotificationRepo<EmailNotification> notificationRepo;
    @Mock
    private EmailNotification notification;

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
        List<EmailNotification> preparedNotifications = Lists.newArrayList(notification, notification);
        doReturn(preparedNotifications).when(notificationRepo).prepareNotSentAfter(any(Date.class));
        //when
        Date beforeCall = currentTimeShiftedByMillis(-1);
        testedObject.notifyBatchOfPlayers();
        Date afterCall = currentTimeShiftedByMillis(+1);
        //then
        verify(notificationRepo).prepareNotSentAfter(argument.capture());
        assertThat(argument.getValue()).isBetween(subtractDays(beforeCall, daysOfDelay), subtractDays(afterCall, daysOfDelay));
        verify(notification, times(2)).send();
        verify(notificationRepo).update(preparedNotifications);
    }

    private Date subtractDays(Date date, int days) {
        ZoneId defaultZone = ZoneId.systemDefault();
        LocalDateTime time = LocalDateTime.ofInstant(date.toInstant(), defaultZone).minus(Period.ofDays(days));
        return Date.from(time.atZone(defaultZone).toInstant());
    }

    private Date currentTimeShiftedByMillis(int millis) {
        return new Date(new Date().getTime() + millis);
    }
}