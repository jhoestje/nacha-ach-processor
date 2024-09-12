package com.khs.payroll.ach.file.parser.fixedwidth;

import lombok.Getter;

/**
 * Payment transaction
 */
@Getter
public enum EntryDetailFixedWidth {
    RECORD_TYPE_CODE(0, 1),                     // always "6"
    TRANSACTION_CODE(1, 3),                     // Transaction code is used to identify the type of account for the debit or credit. 
    RECEIVING_DFI_IDENTIFICATION(3, 11),        //  first eight digits of the recipient’s routing number.
    CHECK_DIGIT(11, 12),                        // last number in the recipient’s 9-digit routing number.
    DFI_ACCOUNT_NUMBER(12, 29),                 // This is the account number of the recipient.
    AMOUNT(29, 39),
    INDIVIDUAL_IDENTIFICATION_NUMBER(39, 54),   // identify the transaction to the Receiver
    INDIVIDUAL_NAME(54, 76),
    DISCRETIONARY_DATA(76, 78),
    ADDENDA_RECORD_INDICATOR(78, 79),           // Indicator if an addenda is present
    TRACE_NUMBER(79, 94);                       // Enter the first 8 digits of originating DFI identification such as the Chase routing number

    private int start;
    private int end;

    EntryDetailFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
