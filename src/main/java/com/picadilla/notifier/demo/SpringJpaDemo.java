package com.picadilla.notifier.demo;

import com.picadilla.notifier.model.Notification;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class SpringJpaDemo {

    public static void main(String[] args) {
        System.out.print("****************** BEGINNING DEMO ****************");

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
        NotificationService service = (NotificationService) context.getBean("notificationService");

        Notification notification = Notification.newNotification("abcd@uvaw.com", new Date());
        service.saveNotification(notification);
        System.out.println(notification);

        List<Notification> all = service.getAllNotifications();
        for (Notification notification1 : all) {
            System.out.println(notification1);
        }

        System.out.println("******************* FINITO *******************");
    }

}
