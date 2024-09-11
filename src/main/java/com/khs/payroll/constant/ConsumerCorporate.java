package com.khs.payroll.constant;

import lombok.Getter;

@Getter
public enum ConsumerCorporate {
    CONSUMER("Consumer"),
    CORPORATE("Corporate"),
    BOTH("Both");
    
    private final String type;
    
    ConsumerCorporate(final String type) {
        this.type = type;
    }
    
}
