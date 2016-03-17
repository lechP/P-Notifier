package com.picadilla.notifier.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.picadilla.notifier.domain.notification.EmailNotification;
import com.picadilla.notifier.domain.notification.NextNotificationState;
import com.picadilla.notifier.testutil.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailNotifierIntegrationTest extends IntegrationTest {

    @Autowired
    private EmailNotifier testedObject;

    private GreenMail greenMail;
    @Value("${mail.port}")
    private int port;

    private static final String SCRIPTS_PATH = "/db/integration/";
    private static final String SCRIPT_SINGLENONE = SCRIPTS_PATH + "testset_singleNone.sql";
    private static final String SCRIPT_TWO_NOT_TO_BE_SENT = SCRIPTS_PATH + "testset_twoNotToBeSent.sql";

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        String protocol = "smtp";
        greenMail = new GreenMail(new ServerSetup(port, null, protocol));
        greenMail.start();
    }

    @After
    public void tearDown() throws Exception {
        greenMail.stop();
    }

    @Test
    public void shouldSendExactOneNotification() throws Exception {
        //given
        loadData(SCRIPT_SINGLENONE);
        setNotificationDelayDays(1); //expected: only first call will find notification to send
        //when
        testedObject.notifyBatchOfPlayers();
        testedObject.notifyBatchOfPlayers();
        //then
        assertThat(greenMail.getReceivedMessages().length).isEqualTo(1);
        assertThatDbIsUpdatedProperly();
    }

    private void assertThatDbIsUpdatedProperly() {
        List<EmailNotification> notifications = em.createQuery("SELECT n FROM EmailNotification n", EmailNotification.class).getResultList();
        assertThat(notifications).hasSize(2);
        Collections.sort(notifications, (n1, n2) -> n1.getDate().compareTo(n2.getDate()));
        assertThat(notifications.get(0).getNextNotificationState()).isEqualTo(NextNotificationState.SENT);
        assertThat(notifications.get(1).getNextNotificationState()).isEqualTo(NextNotificationState.NONE);
    }

    @Test
    public void shouldSendTwoNotifications() throws Exception {
        //given
        loadData(SCRIPT_SINGLENONE);
        setNotificationDelayDays(-1); //expected: both calls will find notification to send
        //when
        testedObject.notifyBatchOfPlayers();
        testedObject.notifyBatchOfPlayers();
        //then
        assertThat(greenMail.getReceivedMessages().length).isEqualTo(2);
    }

    @Test
    public void shouldNotSendAnyNotification() throws Exception {
        //given
        loadData(SCRIPT_TWO_NOT_TO_BE_SENT); //first has status 'SENT' and second is too young
        setNotificationDelayDays(2);
        //when
        testedObject.notifyBatchOfPlayers();
        //then
        assertThat(greenMail.getReceivedMessages().length).isEqualTo(0);
    }

    private void setNotificationDelayDays(int daysOfDelay) {
        ReflectionTestUtils.setField(testedObject, "daysOfDelay", daysOfDelay);
    }
}
