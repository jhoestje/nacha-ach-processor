package com.khs.payroll.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.ValidationException;

public class FileValidationException extends ValidationException {

    private static final long serialVersionUID = 7753436329050361738L;
    private final List<AchFieldValidationException> exceptions;

    public FileValidationException(String message, List<AchFieldValidationException> constraintViolations) {
        super(message);

        if (constraintViolations == null) {
            this.exceptions = null;
        } else {
            this.exceptions = new ArrayList<>(constraintViolations);
        }
    }

    public FileValidationException(List<AchFieldValidationException> constraintViolations) {
        this(constraintViolations != null ? toString(constraintViolations) : null, constraintViolations);
    }

    public List<AchFieldValidationException> getValidationException() {
        return exceptions;
    }

    private static String toString(List<AchFieldValidationException> exceptions) {
        return exceptions.stream().map(cv -> cv == null ? "null" : cv.getMessage()).collect(Collectors.joining(", "));
    }
}
