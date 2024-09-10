package org.johoco.payroll.constant;

import lombok.Getter;

@Getter
public enum ConsumerCorporate {
    CONSUMER("Consumer"),
    CORPORATE("Corporate"),
    BOTH("Both");
    
    private String type;
    
    ConsumerCorporate(String type) {
        this.type = type;
    }
    
}
