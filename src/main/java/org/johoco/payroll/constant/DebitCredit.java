package org.johoco.payroll.constant;

import lombok.Getter;

@Getter
public enum DebitCredit {
    DEBIT("Debit"),
    CREDIT("Credit"),
    BOTH("Both");
    
    private String type;
    
    DebitCredit(String type) {
        this.type = type;
    }
    
}
