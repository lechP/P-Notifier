package com.picadilla.notifier.repository;

import com.picadilla.notifier.model.Notification;
import com.picadilla.notifier.testutil.DatabaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationRepositoryTest extends DatabaseTest {

    private static final String SCRIPTS_PATH = "/db/";
    private static final String SCRIPT_110_NOTIFICATIONS = SCRIPTS_PATH + "testset_moreThan100.sql";
    private static final String SCRIPT_SMALL = SCRIPTS_PATH + "testset_variousDatesAndStatuses.sql";

    @Autowired
    private NotificationRepository sut;

    @Test
    public void shouldGetNotificationsOnlyForStatusNoneAndOlderThanSpecifiedPeriod() throws Exception {
        loadData(SCRIPT_SMALL);
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_MONTH, -5);
        Date maxDate = rightNow.getTime();
        List<? extends Notification> resultList = sut.getNotSentBefore(maxDate);
        assertThat(resultList).hasSize(2);
    }

    @Test
    public void shouldGetExactly100NotificationsInFirstCallAnd10InSecond() throws Exception {
        loadData(SCRIPT_110_NOTIFICATIONS);
        List<? extends Notification> firstBunch = sut.getNotSentBefore(new Date());
        List<? extends Notification> secondBunch = sut.getNotSentBefore(new Date());
        assertThat(firstBunch).hasSize(100);
        assertThat(secondBunch).hasSize(10);
    }
}