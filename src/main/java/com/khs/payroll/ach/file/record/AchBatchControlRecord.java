package com.khs.payroll.ach.file.record;

import java.math.BigDecimal;

import com.khs.payroll.constant.ServiceClassCode;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Record Type Code is required")
    private String recordTypeCode; // "8" for Batch Control

    @NotNull(message = "Service Class Code is required")
    private ServiceClassCode serviceClassCode;      // Identifies the type of transaction (e.g., 220 for credits)

    @NotNull(message = "Entry Addenda Count is mandatory")
    @Positive(message = "Entry Addenda Count must be a positive number")
    private Integer entryAddendaCount; // Number of Entry and Addenda records in the batch

    @NotNull(message = "Entry Hash is mandatory")
    @Positive(message = "Entry Hash must be a positive number")
    private Integer entryHash; // Sum of routing numbers from the Entry Detail records (used for verification)

    @NotNull(message = "Total Debit Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total Debit Amount must be zero or positive")
    private BigDecimal totalDebitAmount; // Total debits in the batch (in cents)

    @NotNull(message = "Total Credit Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total Credit Amount must be zero or positive")
    private BigDecimal totalCreditAmount; // Total credits in the batch (in cents)

    @NotBlank(message = "Company Identification is mandatory")
    @Size(max = 10, message = "Company Identification must not exceed 10 characters")
    private String companyIdentification; // Company identification (should match Batch Header; usually their IRS EIN)

    @Nullable
    @Size(max = 8, message = "Message Authentication Code must not exceed 8 characters")
    private String messageAuthenticationCode; // Not Required; Optional field; often blank; The 8-character code from a
                                              // special key
    @Nullable
    @Size(max = 6, message = "Reserved field must not exceed 6 characters")
    private String reserved; //  Not Required; Reserved (Position 74-79): Reserved for future use, typically left blank.

    @NotNull(message = "Originating DFI Identification is mandatory")
    @Digits(integer = 8, fraction = 0, message = "Originating DFI Identification must be an 8-digit number")
    private Integer originatingDFIIdentification; // First 8 digits of the originating DFI routing number

    @NotNull(message = "Batch Number is mandatory")
    @Positive(message = "Batch Number must be a positive number")
    private Integer batchNumber; // A sequential number assigned to the batch, used for tracking
}
