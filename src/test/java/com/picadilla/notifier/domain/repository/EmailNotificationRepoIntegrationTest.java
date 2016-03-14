package com.picadilla.notifier.domain.repository;

import com.picadilla.notifier.domain.model.Notification;
import com.picadilla.notifier.testutil.DatabaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailNotificationRepoIntegrationTest extends DatabaseTest {

    private static final String SCRIPTS_PATH = "/db/integration/";
    private static final String SCRIPT_110_NOTIFICATIONS = SCRIPTS_PATH + "testset_exceedingSingleBunchSize.sql";
    private static final String SCRIPT_SMALL = SCRIPTS_PATH + "testset_variousDatesAndStatuses.sql";

    private static final int SINGLE_BUNCH_SIZE = 100;

    @Autowired
    private NotificationRepo testedObject;

    @Test
    public void shouldGetNotificationsOnlyForStatusNoneAndOlderThanSpecifiedPeriod() throws Exception {
        loadData(SCRIPT_SMALL);
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_MONTH, -5);
        Date maxDate = rightNow.getTime();
        List<? extends Notification> resultList = testedObject.prepareNotSentBefore(maxDate);
        assertThat(resultList).hasSize(2);
    }

    @Test
    public void shouldGetExactly100NotificationsInFirstCallAnd10InSecond() throws Exception {
        loadData(SCRIPT_110_NOTIFICATIONS);
        List<? extends Notification> firstBunch = testedObject.prepareNotSentBefore(new Date());
        List<? extends Notification> secondBunch = testedObject.prepareNotSentBefore(new Date());
        assertThat(firstBunch).hasSize(SINGLE_BUNCH_SIZE);
        assertThat(secondBunch).hasSize(10);
    }
}