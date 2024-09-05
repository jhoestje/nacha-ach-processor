package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum AchFileControlFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    BATCH_COUNT(1, 7),
    BLOCK_COUNT(7, 13),
    ENTRY_ADDENDA_COUNT(13, 21),
    ENTRY_HASH(21, 31),
    TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT(31, 43),
    TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT(43, 55),
    RESERVED(55, 94);

    private int start;
    private int end;

    AchFileControlFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
