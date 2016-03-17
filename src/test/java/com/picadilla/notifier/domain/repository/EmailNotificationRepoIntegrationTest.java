package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.notification.EmailNotification;
import com.picadilla.notifier.testutil.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailNotificationRepoIntegrationTest extends IntegrationTest {

    private static final String SCRIPTS_PATH = "/db/integration/";
    private static final String SCRIPT_110_NOTIFICATIONS = SCRIPTS_PATH + "testset_exceedingSingleBunchSize.sql";
    private static final String SCRIPT_SMALL = SCRIPTS_PATH + "testset_variousDatesAndStatuses.sql";

    private static final int SINGLE_BUNCH_SIZE = 100;

    @Autowired
    private NotificationRepo<EmailNotification> testedObject;

    @Test
    public void shouldGetNotificationsOnlyForStatusNoneAndOlderThanSpecifiedPeriod() throws Exception {
        //given
        loadData(SCRIPT_SMALL);
        Date maxDate = getDateMinusDays(5);
        //when
        List<EmailNotification> resultList = testedObject.prepareNotSentAfter(maxDate);
        //then
        assertThat(resultList).hasSize(2);
    }

    @Test
    public void shouldGetExactly100NotificationsInFirstCallAnd10InSecond() throws Exception {
        //given
        loadData(SCRIPT_110_NOTIFICATIONS);
        //when
        List<EmailNotification> firstBunch = testedObject.prepareNotSentAfter(new Date());
        List<EmailNotification> secondBunch = testedObject.prepareNotSentAfter(new Date());
        //then
        assertThat(firstBunch).hasSize(SINGLE_BUNCH_SIZE);
        assertThat(secondBunch).hasSize(10);
    }

    private Date getDateMinusDays(int days) {
        ZoneId defaultZone = ZoneId.systemDefault();
        LocalDateTime time = LocalDateTime.ofInstant(new Date().toInstant(), defaultZone).minus(Period.ofDays(days));
        return Date.from(time.atZone(defaultZone).toInstant());
    }


}