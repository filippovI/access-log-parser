package ru.courses.main.exceptions;

public class LogEntryException extends RuntimeException {

    public LogEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
