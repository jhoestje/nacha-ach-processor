package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * aka AchFile Footer Record
 */
@Getter
@Setter
public class AchFileControlRecord {
    private String recordTypeCode;                // Typically "9" for File Control
    private int batchCount;                       // Total number of batches in the file
    private int blockCount;                       // Total number of blocks (10 records per block)
    private int entryAddendaCount;                // Total number of Entry and Addenda records in the file
    private long entryHash;                       // Sum of the routing numbers of all batches (truncated)
    private long totalDebitAmount;                // Total debits in the file (in cents)
    private long totalCreditAmount;               // Total credits in the file (in cents)

}
