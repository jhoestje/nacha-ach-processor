package com.khs.payroll.ach.file.record;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchAddendumRecord {
    @NotBlank(message = "Record Type Code is required")
    private String recordTypeCode; // Always "7"
    
    @NotBlank(message = "Addenda Type Code is mandatory")
    @Pattern(regexp = "05", message = "Addenda Type Code must be '05'")
    private String addendaTypeCode; // Always "05"; Defines the type of addendum
    
    @Nullable
    @Size(max = 80, message = "Payment Related Information must not exceed 80 characters")
    private String paymentRelatedInfo; // Not required;  Holds supplementary information (up to 80 characters)
    
    @NotNull(message = "Addenda Sequence Number is mandatory")
    @Min(value = 1, message = "Addenda Sequence Number must be at least 1")
    private Integer addendaSequenceNumber; // Identifies the sequence number of the addenda (starting at 0001)
    
    @NotNull(message = "Entry Detail Sequence Number is mandatory")
    @Positive(message = "Entry Detail Sequence Number must be a positive number")    
    private Integer entryDetailSequenceNumber; // Identifies the Entry Detail Record this addendum is tied to
}
