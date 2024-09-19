package com.khs.payroll.exception;

import com.khs.payroll.constant.AchReturnCode;

import jakarta.validation.ValidationException;

public class AchFieldValidationException extends ValidationException {

    private static final long serialVersionUID = 9014439454779217377L;
    private final AchReturnCode returnCode;

    public AchFieldValidationException(AchReturnCode returnCode, String message) {
        super(message);
        this.returnCode = returnCode;
    }

    public AchReturnCode getReturnCode() {
        return returnCode;
    }

}
