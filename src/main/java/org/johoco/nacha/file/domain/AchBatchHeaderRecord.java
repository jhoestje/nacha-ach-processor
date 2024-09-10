package org.johoco.nacha.file.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchBatchHeaderRecord {
    private String recordTypeCode;                  // "5" for Batch Header
    private int serviceClassCode;                // Identifies the type of transaction (e.g., 220 for credits)
    private String companyName;                     // The name of the company (Originator)
    private String companyDiscretionaryData;        // Not required;  Originator/ODFI may include codes of significance only to them to enable specialized handling of all entries within the batch.
    private String companyIdentification;           // Identification of the company (can be EIN or other identifier)
    private String standardEntryClassCode;          // Type of transactions (e.g., "PPD", "CCD", etc.)
    private String companyEntryDescription;         // Describes the purpose (e.g., "PAYROLL", "VENDOR PAY")
    private String companyDescriptiveDate;          // A date description like "JANUARY PAY"
    private String effectiveEntryDate;              // Date when the transactions should be processed
    private String settlementDate;                  // Date assigned by ACH Operator, left blank initially
    private String originatorStatusCode;            // Code identifying originator status (1 for ACH operator)
    private String originatingDFIIdentification;    // Routing number of the originating bank
    private int batchNumber;                     // Batch Number (7 chars)
}
