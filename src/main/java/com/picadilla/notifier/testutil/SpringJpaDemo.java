package com.picadilla.notifier.testutil;

import com.picadilla.notifier.domain.EmailNotification;
import com.picadilla.notifier.domain.Notification;
import com.picadilla.notifier.spring.CommonConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class SpringJpaDemo {

    private static final Log log = LogFactory.getLog(SpringJpaDemo.class);

    public static void main(String[] args) {
        log.info("The testutil begins");

        ApplicationContext context = new AnnotationConfigApplicationContext(CommonConfig.class);
        NotificationService service = context.getBean(NotificationService.class);

        List<? extends Notification> all = service.getAllNotifications();
        all.forEach(n -> log.warn("Found emailNotification in db: " + n));

        log.info("good night");
    }

}
