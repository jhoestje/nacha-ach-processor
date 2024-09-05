package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchEntryDetail {
    private String recordTypeCode;              // Typically "6" for Entry Detail
    private String transactionCode;             // Identifies the type of transaction (e.g., credit, debit)
    private String receivingDFI;                // First 8 digits of the receiving bank's routing number
    private String checkDigit;                  // Last digit of the routing number
    private String DFIAccountNumber;            // Account number at the receiving financial institution
    private Long amount;                        // Transaction amount in cents
    private String individualIDNumber;          // Identification number (optional, varies by application)
    private String individualName;              // Name of the recipient (person or organization)
    private String discretionaryData;           // Reserved for use by the originator (optional)
    private String addendaRecordIndicator;      // Indicates whether there's an addenda record (0 = no, 1 = yes)
    private String traceNumber;                 // Unique identifier for the transaction (ODFI's routing number + sequence number)

}
