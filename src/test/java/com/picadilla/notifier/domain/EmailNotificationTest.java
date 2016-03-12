package com.picadilla.notifier.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class EmailNotificationTest {

    private EmailNotification testedObject;

    @Mock
    private NotificationStrategy mockStrategy;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNullForEmail() throws Exception {
        //when
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
        assertThat(testedObject.getStatusOfNext()).isEqualTo(NextNotificationStatus.IN_PROGRESS);
    }

    @Test
    public void shouldForwardSendingToStrategy() {
        //given
        testedObject = getTestEmailNotification();
        testedObject.prepare(mockStrategy);
        //when
        testedObject.send();
        //then
        verify(mockStrategy).send(testedObject);
    }

    private EmailNotification getTestEmailNotification() {
        return new EmailNotification("any.valid@email.com", new Date());
    }
}