package com.khs.payroll.exception;

public class InvalidAccountException extends Exception {

    private static final long serialVersionUID = 4152134435438991458L;
    
    public InvalidAccountException( String message) {
        super(message);
    }

}
