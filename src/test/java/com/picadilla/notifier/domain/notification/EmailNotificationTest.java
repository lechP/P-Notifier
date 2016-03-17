package com.picadilla.notifier.domain.notification;

import com.picadilla.notifier.domain.common.DeliveryReport;
import com.picadilla.notifier.domain.exception.UndeliveriedNotificationException;
import com.picadilla.notifier.domain.exception.UnpreparedNotificationException;
import com.picadilla.notifier.domain.strategy.NotificationStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailNotificationTest {

    private EmailNotification testedObject;

    private static final String TEST_VALID_EMAIL = "any.valid@email.com";

    @Mock
    private NotificationStrategy mockStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNullForEmail() throws Exception {
        //when
        //noinspection ConstantConditions
        testedObject = new EmailNotification(null, new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptInvalidEmail() throws Exception {
        //when
        testedObject = new EmailNotification("do I look like a valid e-mail address?", new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNullForDate() throws Exception {
        //when
        //noinspection ConstantConditions
        testedObject = new EmailNotification("any.valid@email.address", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNullForStrategy() throws Exception {
        //given
        testedObject = getTestEmailNotification();
        //noinspection ConstantConditions
        testedObject.prepare(null);
    }

    @Test
    public void shouldChangeStatusToInProgress() throws Exception {
        //given
        testedObject = getTestEmailNotification();
        //when
        testedObject.prepare(mockStrategy);
        //then
        assertThat(testedObject.getNextNotificationState()).isEqualTo(NextNotificationState.IN_PROGRESS);
    }

    @Test(expected = UnpreparedNotificationException.class)
    public void shouldBeUnableToSendIfNotPreparedPreviously() {
        //given
        testedObject = getTestEmailNotification();
        //when
        testedObject.send();
    }

    @Test
    public void shouldForwardSendingToStrategy() {
        //given
        testedObject = getTestEmailNotification();
        testedObject.prepare(mockStrategy);
        when(mockStrategy.send(anyString())).thenReturn(DeliveryReport.succeed(new Date()));
        //when
        testedObject.send();
        //then
        verify(mockStrategy).send(TEST_VALID_EMAIL);
    }

    @Test
    public void shouldSetStatusToSendIfStrategySucceds() {
        //given
        testedObject = getTestEmailNotification();
        testedObject.prepare(mockStrategy);
        when(mockStrategy.send(anyString())).thenReturn(DeliveryReport.succeed(new Date()));
        //when
        testedObject.send();
        //then
        assertThat(testedObject.getNextNotificationState()).isEqualTo(NextNotificationState.SENT);
    }

    @Test
    public void shouldSetStatusToNoneIfStrategyFails() {
        //given
        testedObject = getTestEmailNotification();
        testedObject.prepare(mockStrategy);
        when(mockStrategy.send(anyString())).thenReturn(DeliveryReport.failed());
        //when
        testedObject.send();
        //then
        assertThat(testedObject.getNextNotificationState()).isEqualTo(NextNotificationState.NONE);
    }

    @Test
    public void shouldReturnNewNotificationIfStrategySucceds() {
        //given
        Date deliveryDate = new Date();
        testedObject = getTestEmailNotification();
        testedObject.prepare(mockStrategy);
        when(mockStrategy.send(anyString())).thenReturn(DeliveryReport.succeed(deliveryDate));
        testedObject.send();
        //when
        EmailNotification next = testedObject.getNextNotification();
        //then
        assertThat(next).isEqualTo(new EmailNotification(TEST_VALID_EMAIL, deliveryDate));
    }

    @Test(expected = UndeliveriedNotificationException.class)
    public void shouldThrowExceptionIfStrategyFails() {
        //given
        testedObject = getTestEmailNotification();
        testedObject.prepare(mockStrategy);
        when(mockStrategy.send(anyString())).thenReturn(DeliveryReport.failed());
        testedObject.send();
        //when
        testedObject.getNextNotification();
    }

    private EmailNotification getTestEmailNotification() {
        return new EmailNotification(TEST_VALID_EMAIL, new Date());
    }
}