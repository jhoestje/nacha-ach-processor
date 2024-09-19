package com.khs.payroll.exception;

import com.khs.payroll.constant.AchReturnCode;

import lombok.ToString;

@ToString
public class InvalidPaymentException extends Exception {

    private static final long serialVersionUID = 4152134435438991458L;
    
    private AchReturnCode returnCode;
    
    public InvalidPaymentException(final AchReturnCode returnCode, final String message) {
        super(message);
        this.returnCode = returnCode;
    }

    public AchReturnCode getReturnCode() {
        return returnCode;
    }
}
