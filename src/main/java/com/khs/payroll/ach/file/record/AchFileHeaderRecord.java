package com.khs.payroll.ach.file.record;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Types of file: unbalanced vs balanced Same Day ACH: rules It is a Nacha Rules
 * violation to debit a Receiver prior to the authorization date.
 * 
 * Reversals or Enforcement
 */
@Getter
@Setter
@ToString
public class AchFileHeaderRecord {
    @NotBlank(message = "Record Type Code is required")
    private String recordTypeCode; // Always "1"

    @NotBlank(message = "Priority Code is required")
    @Pattern(regexp = "01", message = "Priority Code must be '01'")
    private String priorityCode; // Always "01."

    @NotBlank(message = "Immediate Destination is required")
    @Pattern(regexp = "\\d{9}", message = "Immediate Destination must be a nine-digit number")
    private String immediateDestination; // The nine-digit routing number of the institution receiving the ACH file for
                                         // processing

    @NotBlank(message = "Immediate Origin is required")
    @Pattern(regexp = "\\d{9}", message = "Immediate Origin must be a nine-digit number")
    private String immediateOrigin; // The nine-digit routing transit number of the institution sending
                                    // (originating) the ACH file,

    @NotBlank(message = "File Creation Date is required")
    @Pattern(regexp = "\\d{6}", message = "File Creation Date must be in YYMMDD format")
    private String fileCreationDate; // YYMMDD

    @Nullable
    @Pattern(regexp = "\\d{4}", message = "File Creation Time must be in HHMM format", groups = { Nullable.class })
    private String fileCreationTime; // Not required; HHMM

    @NotBlank(message = "File Id Modifier is required")
    private String fileIdModifier; // For a single processing day, each file submitted by the financial institution
                                   // should have a unique ID to allow for thorough duplicate file identification.

    @NotBlank(message = "Record Size is required")
    @Pattern(regexp = "094", message = "Record Size must be '094'")
    private String recordSize; // Always "094"

    @NotBlank(message = "Blocking Factor is required")
    @Pattern(regexp = "10", message = "Blocking Factor must be '10'")
    private String blockingFactor; // Always "10"

    @NotBlank(message = "Format Code is required")
    @Pattern(regexp = "1", message = "Format Code must be '1'")
    private String formatCode; // Always "1."

    @Nullable
    @Size(max = 23, message = "Immediate Destination Name must not exceed 23 characters")
    private String immediateDestinationName; // Not required; Name of the financial institution receiving the payment file.

    @Nullable
    @Size(max = 23, message = "Immediate Origin Name must not exceed 23 characters")
    private String immediateOriginName; // Not required; Name of the financial institution sending the payment file.

    @Nullable
    @Size(max = 8, message = "Reference Code must not exceed 8 characters")
    private String referenceCode; // Not required; This field is reserved for information pertinent to the
                                  // business
}
