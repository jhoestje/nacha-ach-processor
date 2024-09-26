package com.khs.payroll.ach.file.parser.fixedwidth;

import lombok.Getter;

/**
 * Extra processing details for an Addendum Return record.
 */
@Getter
public enum AddendumReturnFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    ADDENDA_TYPE_CODE(1, 3),
    RETURN_REASON_CODE(3, 6),
    ORIGINAL_ENTRY_TRACE_NUMBER(6, 21),
    DATE_OF_DEATH(21, 27), // Used only with Return Codes R14 and R15
    ORIGINAL_RECEIVING_DFI_IDENFICATION(27, 35),
    ADDENDA_INFORMATION(35, 79),
    TRACE_NUMBER(3, 83);

    private int start;
    private int end;

    AddendumReturnFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
