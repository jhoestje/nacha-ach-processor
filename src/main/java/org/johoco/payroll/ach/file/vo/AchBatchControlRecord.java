package org.johoco.payroll.ach.file.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * aka AchBatch Footer or Trailer Record
 */
@Getter
@Setter
@ToString
public class AchBatchControlRecord {
    private String recordTypeCode;                  // "8" for Batch Control
    private int serviceClassCode;                // Identifies the type of transaction (e.g., 220 for credits)
    private int entryAddendaCount;               // Number of Entry and Addenda records in the batch
    private int entryHash;                       // Sum of routing numbers from the Entry Detail records (used for verification)
    private double totalDebitAmount;                // Total debits in the batch (in cents)
    private double totalCreditAmount;               // Total credits in the batch (in cents)
    private String companyIdentification;           // Company identification (should match Batch Header; usually their IRS EIN)
    private String messageAuthenticationCode;       // Not Required;  Optional field; often blank; The 8-character code from a special key
    private String reserved;                        // Reserved (Position 74-79): Reserved for future use, typically left blank.
    private int originatingDFIIdentification;    // First 8 digits of the originating DFI routing number
    private int batchNumber;                     // A sequential number assigned to the batch, used for tracking
}
