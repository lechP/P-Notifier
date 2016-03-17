package com.picadilla.notifier.domain.strategy;

import com.picadilla.notifier.domain.common.DeliveryReport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class GmailNotificationStrategyTest {

    @InjectMocks
    private GmailNotificationStrategy testedObject;

    @Mock
    private SimpleMailMessage templateMessage;
    @Mock
    private MailSender mailSender;

    private static final String TEST_ADDRESS = "some.mail@address.com";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(testedObject, "messageBody", "any body");
        ReflectionTestUtils.setField(testedObject, "messageTitle", "any title");
    }

    @Test
    public void shouldReturnPositiveDeliveryReport() throws Exception {
        //given
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        //when
        DeliveryReport result = testedObject.send(TEST_ADDRESS);
        //then
        assertThat(result.isDelivered()).isTrue();
    }

    @Test
    public void shouldReturnNegativeDeliveryReport() throws Exception {
        //given
        doThrow(MailSendException.class).when(mailSender).send(any(SimpleMailMessage.class));
        //when
        DeliveryReport result = testedObject.send(TEST_ADDRESS);
        //then
        assertThat(result.isDelivered()).isFalse();
    }
}