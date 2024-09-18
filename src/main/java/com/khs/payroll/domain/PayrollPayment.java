package com.khs.payroll.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.khs.payroll.constant.PaymentState;
import com.khs.payroll.constant.ServiceClassCode;
import com.khs.payroll.constant.StandardEntryClassCode;
import com.khs.payroll.constant.TransactionCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("payrollPayments")
public class PayrollPayment {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate; 
    @LastModifiedDate
    private Date lastModifiedDate;
    private TransactionCode transactionCode;        // Identifies the type of transaction (e.g., credit, debit)
    private String receivingDFIIdentification;      // First 8 digits of the receiving bank's routing number
    private Integer checkDigit;                     // Last digit of the routing number
    private String dfiAccountNumber;                // Account number at the receiving financial institution
    private BigDecimal amount;                          // Transaction amount in cents
    private String identificationNumber;            // Not required;  Identification number (optional, varies by application)
    private String receivingName;                   // Name of the recipient (person or company)
    private String discretionaryData;               // For PPD and CCD entries;  Reserved for use by the originator (optional)
    //Payment Type Code                             // TEL and WEB entries;  codes, of significance to them, to enable specialized handling of the entry
    private Long traceNumber;
    private ServiceClassCode serviceClassCode;      // Identifies the type of transaction (e.g., 220 for credits)
    private String companyName;                     // The name of the company (Originator)
    private String companyDiscretionaryData;        // Not required;  Originator/ODFI may include codes of significance only to them to enable specialized handling of all entries within the batch.
    private String companyIdentification;           // Identification of the company (can be EIN or other identifier)
    private StandardEntryClassCode standardEntryClassCode;    // Type of transactions (e.g., "PPD", "CCD", etc.)
    private String companyEntryDescription;         // Describes the purpose (e.g., "PAYROLL", "VENDOR PAY")
    private String companyDescriptiveDate;          // A date description like "JANUARY PAY"
    private LocalDate effectiveEntryDate;           // Date when the transactions should be processed
    private String originatorStatusCode;            // Code identifying originator status (1 for ACH operator)
    private String originatingDFIIdentification;    // Routing number of the originating bank
    @DBRef
    private List<PayrollPaymentAddendum> addumda;
    private PaymentState state;
    private String stateReason;                     // Additional information for the state;  Usually for the FAILED state
}
