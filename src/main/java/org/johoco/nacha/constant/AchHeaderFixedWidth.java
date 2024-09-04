package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum AchHeaderFixedWidth {
    RECORD_TYPE_CODE(0,1),
    PRIORITY_CODE(1,3),
    IMMEDIATE_DESTINATION(3,13),
    IMMEDIATE_ORIGIN(13,23)   ;

    private int start;
    private int end;
    
    AchHeaderFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
