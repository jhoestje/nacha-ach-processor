package org.johoco.payroll.ach.file.parser.fixedwidth;

import lombok.Getter;

@Getter
public enum EntryDetailFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    TRANSACTION_CODE(1, 3),
    RECEIVING_DFI_IDENTIFICATION(3, 11),
    CHECK_DIGIT(11, 12),
    DFI_ACCOUNT_NUMBER(12, 29),
    AMOUNT(29, 39),
    INDIVIDUAL_IDENTIFICATION_NUMBER(39, 54),
    INDIVIDUAL_NAME(54, 76),
    DISCRETIONARY_DATA(76, 78),
    ADDENDA_RECORD_INDICATOR(78, 79),
    TRACE_NUMBER(79, 94);

    private int start;
    private int end;

    EntryDetailFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
