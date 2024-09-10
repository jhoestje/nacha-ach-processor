package org.johoco.payroll.ach.file.parser.fixedwidth;

import lombok.Getter;

@Getter
public enum BatchHeaderFixedWidth {

    RECORD_TYPE_CODE(0, 1),                // Record Type Code (Position 1)
    SERVICE_CLASS_CODE(1, 4),              // Service Class Code (Position 2-4)
    COMPANY_NAME(4, 20),                   // Company Name (Position 5-20)
    COMPANY_DISCRETIONARY_DATA(20, 40),    // Company Discretionary Data (Position 21-40)
    COMPANY_IDENTIFICATION(40, 50),        // Company Identification (Position 41-50)
    STANDARD_ENTRY_CLASS_CODE(50, 53),     // Standard Entry Class Code (Position 51-53)
    COMPANY_ENTRY_DESCRIPTION(53, 63),     // Company Entry Description (Position 54-63)
    COMPANY_DESCRIPTIVE_DATE(63, 69),      // Company Descriptive Date (Position 64-69)
    EFFECTIVE_ENTRY_DATE(69, 75),          // Effective Entry Date (Position 70-75)
    SETTLEMENT_DATE_JULIAN(75, 78),        // Settlement Date Julian (Position 76-78)
    ORIGINATOR_STATUS_CODE(78, 79),        // Originator Status Code (Position 79)
    ORIGINATING_DFI_IDENTIFICATION(79, 87),// Originating DFI Identification (Position 80-87)
    BATCH_NUMBER(87, 94);                  // Batch Number (Position 88-94)

    private int start;
    private int end;

    BatchHeaderFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
