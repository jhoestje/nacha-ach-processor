package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchEntryDetail {
    private String recordTypeCode;                  // "6" for Entry Detail
    private String transactionCode;                 // Identifies the type of transaction (e.g., credit, debit)
    private String receivingDFIIdentification;      // First 8 digits of the receiving bank's routing number
    private String checkDigit;                      // Last digit of the routing number
    private String dFIAccountNumber;                // Account number at the receiving financial institution
    private String amount;                          // Transaction amount in cents
    private String individualIdentificationNumber;  // Identification number (optional, varies by application)
    private String individualName;                  // Name of the recipient (person or organization)
    private String discretionaryData;               // Reserved for use by the originator (optional)
    private String addendaRecordIndicator;          // Indicates whether there's an addenda record (0 = no, 1 = yes)
    private String traceNumber;                     // Unique identifier for the transaction (ODFI's routing number + sequence number)

}
