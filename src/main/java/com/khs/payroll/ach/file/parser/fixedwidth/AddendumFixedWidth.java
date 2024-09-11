package com.khs.payroll.ach.file.parser.fixedwidth;

import lombok.Getter;

/**
 * Extra processing details for an Entry Detail record.
 */
@Getter
public enum AddendumFixedWidth {
    RECORD_TYPE_CODE(0, 1),                 // Record Type Code (Position 1)
    ADDENDA_TYPE_CODE(1, 3),                // Addenda Type Code (Position 2-3)
    PAYMENT_INFORMATION(3, 83),             // Payment Related Information (Position 4-83)
    ADDENDA_SEQUENCE_NUMBER(83, 87),        // Addenda Sequence Number (Position 84-87)
    ENTRY_DETAIL_SEQUENCE_NUMBER(87, 94);   // Entry Detail Sequence Number (Position 88-94)

    private int start;
    private int end;

    AddendumFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
