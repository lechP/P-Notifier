package com.picadilla.notifier.demo;

import com.picadilla.notifier.model.Notification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class SpringJpaDemo {

    private static final Log log = LogFactory.getLog(SpringJpaDemo.class);

    public static void main(String[] args) {
        log.info("The demo begins");

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
        NotificationService service = (NotificationService) context.getBean("notificationService");

        Notification notification = Notification.newNotification("abcd@uvaw.com", new Date());
        service.saveNotification(notification);
        log.info("Succesfully saved: " + notification);

        List<Notification> all = service.getAllNotifications();
        for (Notification notification1 : all) {
            log.warn("Found notification in db: " + notification1);
        }

        log.info("good night");
    }

}
