package com.picadilla.notifier.domain.exception;

public class UndeliveriedNotificationException extends RuntimeException {

    public UndeliveriedNotificationException(String message) {
        super(message);
    }
}
