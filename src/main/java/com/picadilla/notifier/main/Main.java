package com.picadilla.notifier.main;


import com.picadilla.notifier.service.Scheduler;
import com.picadilla.notifier.spring.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Entry point to the whole application. Loads the Spring context and launches {@link Scheduler}
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean(Scheduler.class).schedule();
    }
}
