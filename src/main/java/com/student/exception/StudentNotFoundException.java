package com.student.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(final String message) {
        super(message);
    }
}
