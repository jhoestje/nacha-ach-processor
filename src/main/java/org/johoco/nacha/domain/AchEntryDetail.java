package org.johoco.nacha.domain;

import java.util.ArrayList;
import java.util.List;

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
    private String identificationNumber;            // Identification number (optional, varies by application)
    private String receivingName;                   // Name of the recipient (person or company)
    private String discretionaryData;               // For PPD and CCD entries;  Reserved for use by the originator (optional)
    //Payment Type Code                             // TEL and WEB entries;  codes, of significance to them, to enable specialized handling of the entry
    private String addendaRecordIndicator;          // Indicates whether there's an addenda record (0 = no, 1 = yes)
    private String traceNumber;                     // Unique identifier for the transaction (ODFI's routing number + sequence number)
    private List<AchAddendumRecord> addenda;              // IAT can have more than one
    
    public AchEntryDetail addAchAddendum(final AchAddendumRecord achAddendum) {
        if(addenda == null) {
            addenda = new ArrayList<>();
        }
        addenda.add(achAddendum);
        return this;
    }
}
