package com.khs.payroll.ach.file.record;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import com.khs.payroll.constant.TransactionCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchEntryDetailRecord {
    @NotBlank(message = "Record Type Code is required")
    @Size(min = 1, max = 1)
    private String recordTypeCode;                  // "6" for Entry Detail
    
    @NotNull(message = "Transaction Code is required")
    private TransactionCode transactionCode;        // Identifies the type of transaction (e.g., credit, debit)
    
    @NotBlank(message = "Record Type Code must be 8 characters")
    @Size(min = 8, max = 8)
    private String receivingDFIIdentification;      // First 8 digits of the receiving bank's routing number
    
    @NotNull(message = "Check Digit is required")
    @Positive(message = "Check Digit must be a positive number")
    private Integer checkDigit;                         // Last digit of the routing number
    
    @NotBlank(message = "DFI Account Number must be 8 characters")
    @Size(min = 8, max = 8)
    private String dFIAccountNumber;                // Account number at the receiving financial institution
    
    @NotNull(message = "Amount is required")
    private Double amount;                          // Transaction amount in cents
    @Nullable
    private String identificationNumber;            // Not required;  Identification number (optional, varies by application)
    
    @NotBlank(message = "Receiving Name is mandatory")
    @Size(max = 22, message = "Receiving Name must not exceed 22 characters")
    private String receivingName;                   // Name of the recipient (person or company)
    
    @Size(max = 2, message = "Discretionary Data must not exceed 2 characters")
    private String discretionaryData;               // For PPD and CCD entries;  Reserved for use by the originator (optional)
    //Payment Type Code                             // TEL and WEB entries;  codes, of significance to them, to enable specialized handling of the entry
  
    @NotNull(message = "Addenda Record Indicator is mandatory")
    @Size(min = 0, max = 1, message = "Addenda Record Indicator must be either 0 (no) or 1 (yes)")
    private Integer addendaRecordIndicator;             // Indicates whether there's an addenda record (0 = no, 1 = yes)
    
    @NotNull(message = "Trace Number is mandatory")
    @Positive(message = "Trace Number must be a positive number")
    private Integer traceNumber;                        // Unique identifier for the transaction (ODFI's routing number + sequence number)
    
    @Valid
    private List<AchAddendumRecord> addenda;        // IAT can have more than one
    
    public AchEntryDetailRecord addAchAddendum(final AchAddendumRecord achAddendum) {
        if(addenda == null) {
            addenda = new ArrayList<>();
        }
        addenda.add(achAddendum);
        return this;
    }
}
