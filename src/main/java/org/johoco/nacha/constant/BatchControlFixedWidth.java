package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum BatchControlFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    SERVICE_CLASS_CODE(1, 4),
    ENTRY_ADDENDA_COUNT(4, 10),
    ENTRY_HASH(10, 20),
    TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT(20, 32),
    TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT(32, 44),
    COMPANY_IDENTIFICATION(44, 54),
    MESSAGE_AUTHENTICATION_CODE(54, 73),
    RESERVED(73, 79),
    ORIGINATING_DFI_ID(79, 87),
    BATCH_NUMBER(87, 94);

    private int start;
    private int end;

    BatchControlFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
