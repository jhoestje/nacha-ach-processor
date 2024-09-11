package com.khs.payroll.ach.file.record;

import java.util.Date;

import org.springframework.lang.Nullable;

import com.khs.payroll.constant.ServiceClassCode;
import com.khs.payroll.constant.StandardEntryClassCode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchBatchHeaderRecord {
    private String recordTypeCode;                  // "5" for Batch Header
    
    @NotNull(message = "Service Class Code is required")
    private ServiceClassCode serviceClassCode;      // Identifies the type of transaction (e.g., 220 for credits)
    
    @NotBlank(message = "Company Name is required")
    @Size(max = 16, message = "Company Name must not exceed 16 characters")
    private String companyName;                     // The name of the company (Originator)
    
    @Size(max = 20, message = "Company Discretionary Data must not exceed 20 characters")
    @Nullable
    private String companyDiscretionaryData;        // Not required;  Originator/ODFI may include codes of significance only to them to enable specialized handling of all entries within the batch.
    
    @NotBlank(message = "Company Identification is required")
    @Size(max = 10, message = "Company Identification must not exceed 10 characters")
    private String companyIdentification;           // Identification of the company (can be EIN or other identifier)
    
    @NotNull(message = "Standard Entry Class Code is required")
    private StandardEntryClassCode standardEntryClassCode;  // Type of transactions (e.g., "PPD", "CCD", etc.)
    
    @NotBlank(message = "Company Entry Description is required")
    @Size(max = 10, message = "Company Entry Description must not exceed 10 characters")
    private String companyEntryDescription;         // Describes the purpose (e.g., "PAYROLL", "VENDOR PAY")
    
    @NotBlank(message = "Company Descriptive Date is required")
    @Size(max = 6, message = "Company Descriptive Date must not exceed 6 characters")
    private String companyDescriptiveDate;          // A date description like "JANUARY PAY"
    
    @NotNull(message = "Effective Entry Date is required")
     private Date effectiveEntryDate;              // Date when the transactions should be processed
    
    @Nullable
    private Date settlementDate;                  // Not required; Date assigned by ACH Operator, left blank initially
    
    @NotBlank(message = "Originator Status Code is required")
    @Pattern(regexp = "1", message = "Originator Status Code must be '1'")
    private String originatorStatusCode;            // Code identifying originator status (1 for ACH operator)
    
    @NotBlank(message = "Originating DFI Identification is required")
    @Pattern(regexp = "\\d{8}", message = "Originating DFI Identification must be 8 digits")
    private String originatingDFIIdentification;    // Routing number of the originating bank
    
    @NotNull(message = "Batch Number is required")
    @Min(value = 1, message = "Batch Number must be at least 1")
    private Integer batchNumber;                     // Batch Number (7 chars)
}
