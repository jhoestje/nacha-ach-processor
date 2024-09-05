package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * aka AchBatch Footer Record
 */
@Getter
@Setter
public class AchBatchControlRecord {
    private String recordTypeCode;               // Typically "8" for Batch Control
    private String serviceClassCode;             // Identifies the type of transaction (e.g., 220 for credits)
    private int entryAddendaCount;               // Number of Entry and Addenda records in the batch
    private long entryHash;                      // Sum of routing numbers (used for verification)
    private long totalDebitAmount;               // Total debits in the batch (in cents)
    private long totalCreditAmount;              // Total credits in the batch (in cents)
    private String companyIdentification;        // Company identification (should match Batch Header)
    private String originatingDFI;               // First 8 digits of the originating DFI routing number
    private String batchNumber;                  // Batch Number (7 chars)
}
