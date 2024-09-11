package com.khs.payroll.ach.file.record;

import java.util.ArrayList;
import java.util.List;

import com.khs.payroll.constant.ServiceClassCode;
import com.khs.payroll.constant.StandardEntryClassCode;
import com.khs.payroll.constant.TransactionCode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchEntryDetail {
    private String recordTypeCode;                  // "6" for Entry Detail
    private TransactionCode transactionCode;        // Identifies the type of transaction (e.g., credit, debit)
    private String receivingDFIIdentification;      // First 8 digits of the receiving bank's routing number
    private int checkDigit;                         // Last digit of the routing number
    private String dFIAccountNumber;                // Account number at the receiving financial institution
    private double amount;                          // Transaction amount in cents
    private String identificationNumber;            // Not required;  Identification number (optional, varies by application)
    private String receivingName;                   // Name of the recipient (person or company)
    private String discretionaryData;               // For PPD and CCD entries;  Reserved for use by the originator (optional)
    //Payment Type Code                             // TEL and WEB entries;  codes, of significance to them, to enable specialized handling of the entry
    private int traceNumber;                        // Unique identifier for the transaction (ODFI's routing number + sequence number)
    private ServiceClassCode serviceClassCode;      // Identifies the type of transaction (e.g., 220 for credits)
    private String companyName;                     // The name of the company (Originator)
    private String companyDiscretionaryData;        // Not required;  Originator/ODFI may include codes of significance only to them to enable specialized handling of all entries within the batch.
    private String companyIdentification;           // Identification of the company (can be EIN or other identifier)
    private StandardEntryClassCode standardEntryClassCode;   // Type of transactions (e.g., "PPD", "CCD", etc.)
    private String companyEntryDescription;         // Describes the purpose (e.g., "PAYROLL", "VENDOR PAY")
    private String companyDescriptiveDate;          // A date description like "JANUARY PAY"
    private String effectiveEntryDate;              // Date when the transactions should be processed
    private String settlementDate;                  // Date assigned by ACH Operator, left blank initially
    private String originatorStatusCode;            // Code identifying originator status (1 for ACH operator)
    private String originatingDFIIdentification;    // Routing number of the originating bank
    private List<AchAddendumRecord> addenda;        // IAT can have more than one
//  private int addendaRecordIndicator;             // Indicates whether there's an addenda record (0 = no, 1 = yes)
    
    
    public AchEntryDetail addAchAddendum(final AchAddendumRecord achAddendum) {
        if(addenda == null) {
            addenda = new ArrayList<>();
        }
        addenda.add(achAddendum);
        return this;
    }
}
