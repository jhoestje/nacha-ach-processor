package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum AchEntryDetailFixedWidth {
    TRANSACTION_CODE(0, 2),
    RECEIVING_DFI(2, 11),
    CHECK_DIGIT(11, 12),
    ACCOUNT_NUMBER(12, 28);

    private int start;
    private int end;

    AchEntryDetailFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
