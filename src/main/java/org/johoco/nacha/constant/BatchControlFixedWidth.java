package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum BatchControlFixedWidth {
    RECORD_TYPE_CODE(0, 1),                // Record Type Code (Position 1)
    SERVICE_CLASS_CODE(1, 4),              // Service Class Code (Position 2-4)
    ENTRY_ADDENDA_COUNT(4, 10),            // Entry/Addenda Count (Position 5-10)
    ENTRY_HASH(10, 20),                    // Entry Hash (Position 11-20)
    TOTAL_DEBIT_AMOUNT(20, 32),            // Total Debit Amount (Position 21-32)
    TOTAL_CREDIT_AMOUNT(32, 44),           // Total Credit Amount (Position 33-44)
    COMPANY_IDENTIFICATION(44, 54),        // Company Identification (Position 45-54)
    MESSAGE_AUTHENTICATION_CODE(54, 73),   // Message Authentication Code (Position 55-73)
    RESERVED(73, 79),                      // Reserved (Position 74-79)
    ORIGINATING_DFI_IDENTIFICATION(79, 87),// Originating DFI Identification (Position 80-87)
    BATCH_NUMBER(87, 94);                  // Batch Number (Position 88-94)

    private int start;
    private int end;

    BatchControlFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
