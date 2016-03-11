package com.picadilla.notifier.repository;

import com.picadilla.notifier.model.Notification;
import com.picadilla.notifier.testutil.DatabaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationRepositoryTest extends DatabaseTest{

    @Autowired
    private NotificationRepository sut;

    @Test
    public void shouldGetExactly100Notifications() throws Exception {
        loadData("/db/testset_moreThan100.sql");
        List<Notification> resultList = sut.getBunchOfNotifications();
        assertThat(resultList).hasSize(100);
    }
}