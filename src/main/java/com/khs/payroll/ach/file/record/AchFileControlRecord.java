package com.khs.payroll.ach.file.record;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * aka AchFile Footer Record
 */
@Getter
@Setter
@ToString
public class AchFileControlRecord {
    @NotBlank(message = "Record Type Code is required")
    private String recordTypeCode; // "9" for File Control
    
    @NotNull(message = "Batch Count is required")
    @Positive(message = "Batch Count must be a positive number")
    private Integer batchCount; // Total number of batches in the file
    
    @NotNull(message = "Block Count is required")
    @Positive(message = "Block Count must be a positive number")
    private Integer blockCount; // Total number of blocks (10 records per block)
    
    @NotNull(message = "Entry Addenda Count is required")
    @Positive(message = "Entry Addenda Count must be a positive number")
    private Integer entryAddendaCount; // Total number of Entry and Addenda records in the file
    
    @NotNull(message = "Entry Hash is required")
    @Positive(message = "Entry Hash must be a positive number")
    private Long entryHash; // Sum of the routing numbers of all batches (truncated)
    
    @NotNull(message = "Total Debit Amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total Debit Amount must be zero or positive")
    private Double totalDebitAmount; // Total debits in the file (in cents)
    
    @NotNull(message = "Total Credit Amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total Credit Amount must be zero or positive")
    private Double totalCreditAmount; // Total credits in the file (in cents)
    // Reserved 56-94
}
