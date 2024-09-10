package org.johoco.nacha.file.parser.fixedwidth;

import lombok.Getter;

@Getter
public enum FileControlFixedWidth {
    RECORD_TYPE_CODE(0, 1),               // '9' indicating File Control Record
    BATCH_COUNT(1, 7),                    // Total number of Batch Header Records
    BLOCK_COUNT(7, 13),                   // Total number of physical blocks in the file
    ENTRY_ADDENDA_COUNT(13, 21),          // Total number of Entry Detail and Addenda records in the file
    ENTRY_HASH(21, 31),                   // Sum of the Receiving DFI Identification (last 8 digits)
    TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT(31, 43),  // Total debit dollar amount in the file
    TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT(43, 55), // Total credit dollar amount in the file
    RESERVED(55, 94);                     // Reserved space (blank) in the file control record

    private int start;
    private int end;

    FileControlFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
