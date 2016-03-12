package com.picadilla.notifier.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class NotificationEntityTest {

    private NotificationEntity testedObject;

    @Mock
    private NotificationStrategy mockStrategy;

    @Before
    public void setUp(){
        testedObject = NotificationEntity.newNotification("any mail", new Date());
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNull() throws Exception {
        //noinspection ConstantConditions
        testedObject.prepare(null);
    }

    @Test
    public void shouldChangeStatusToInProgress() throws Exception {
        //when
        testedObject.prepare(mockStrategy);
        //then
        assertThat(testedObject.getStatusOfNext()).isEqualTo(NextNotificationStatus.IN_PROGRESS);
    }

    @Test
    public void shouldForwardSendingToStrategy() {
        //given
        testedObject.prepare(mockStrategy);
        //when
        testedObject.send();
        //then
        verify(mockStrategy).send(testedObject);
    }
}