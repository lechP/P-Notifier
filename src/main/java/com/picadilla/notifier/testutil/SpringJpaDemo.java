package com.picadilla.notifier.testutil;

import com.picadilla.notifier.domain.NotificationEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class SpringJpaDemo {

    private static final Log log = LogFactory.getLog(SpringJpaDemo.class);

    public static void main(String[] args) {
        log.info("The testutil begins");

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-prod-config.xml");
        NotificationService service = (NotificationService) context.getBean("notificationService");

        NotificationEntity notificationEntity = NotificationEntity.newNotification("abcd@uvaw.com", new Date());
        service.saveNotification(notificationEntity);
        log.info("Succesfully saved: " + notificationEntity);

        List<NotificationEntity> all = service.getAllNotifications();
        for (NotificationEntity notificationEntity1 : all) {
            log.warn("Found notificationEntity in db: " + notificationEntity1);
        }

        log.info("good night");
    }

}
