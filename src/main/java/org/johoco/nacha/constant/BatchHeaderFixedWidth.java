package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum BatchHeaderFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    SERVICE_CLASS_CODE(1, 4),
    COMPANY_NAME(4, 20),
    COMPANY_DISCRETIONARY_DATA(20, 40),
    COMPANY_IDENTIFICATION(40, 50),
    STANDARD_ENTRY_CLASS_CODE(50, 53),
    COMPANY_ENTRY_DESCRIPTION(53, 63),
    COMPANY_DESCRIPTION_DATE(63, 69),
    EFFECTIVE_ENTRY_DATE(69, 75),
    RESERVED(75, 78),
    ORIGINATOR_STATUS_CODE(78, 79),
    ORIGINATING_DFI_ID(79, 87),
    BATCH_NUMBER(87, 94);

    private int start;
    private int end;

    BatchHeaderFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
