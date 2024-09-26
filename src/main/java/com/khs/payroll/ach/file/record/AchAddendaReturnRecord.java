package com.khs.payroll.ach.file.record;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import com.khs.payroll.constant.AddendaTypeCode;

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
public class AchAddendaReturnRecord {

    @NotBlank(message = "Record Type Code is required")
    private String recordTypeCode; // Always "7"

    @NotNull(message = "Addenda Type Code is mandatory")
    private AddendaTypeCode addendaTypeCode; // Always "99"; Defines the type of addendum

    @Pattern(regexp = "R\\d{2}", message = "Invalid Return Reason Code. Expected format 'R' followed by two digits.")
    private String returnReasonCode; // // Return Reason Code: e.g., R01, R69

    @NotNull(message = "Original Trace Number is mandatory")
    @Positive(message = "Original Trace Number must be a positive number")
    private Long originalEntryTraceNumber;

    @Nullable
    private LocalDate dateOfDeath; // Used only with Return Codes R14 and R15; MMDDYY

    @NotBlank(message = "Record Type Code must be 8 characters")
    @Size(min = 8, max = 8)
    private String originalReceivingDfiIdentification;

    @Nullable
    @Size(max = 44, message = "Payment Related Information must not exceed 80 characters")
    private String addendaInformation; // Not required; Holds supplementary information (up to 80 characters)

    @NotNull(message = "Trace Number is mandatory")
    @Positive(message = "Trace Number must be a positive number")
    private Long traceNumber; // Unique identifier for the transaction (ODFI's routing number + sequence
                              // number)

}
