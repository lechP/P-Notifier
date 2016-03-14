package com.picadilla.notifier.main;


import com.picadilla.notifier.business.service.Scheduler;
import com.picadilla.notifier.spring.CommonConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CommonConfig.class);
        context.getBean(Scheduler.class).schedule();
    }
}
