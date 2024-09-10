package org.johoco.payroll.ach.file.vo;

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
    private String recordTypeCode;              // Always "1"
    private String priorityCode;                // Always "01."
    private String immediateDestination;        // The nine-digit routing number of the institution receiving the ACH file for processing
    private String immediateOrigin;             // The nine-digit routing transit number of the institution sending (originating) the ACH file,
    private String fileCreationDate;            // YYMMDD
    private String fileCreationTime;            // Not required; HHMM
    private String fileIdModifier;              // For a single processing day, each file submitted by the financial institution should have a unique ID to allow for thorough duplicate file identification.
    private String recordSize;                  // Always "094"
    private String blockingFactor;              // Always "10" 
    private String formatCode;                  // Always "1."
    private String immediateDestinationName;    // Not required; Name of the financial institution receiving the payment file.
    private String immediateOriginName;         // Not required; Name of the financial institution sending the payment file.
    private String referenceCode;               // Not required; This field is reserved for information pertinent to the business
}
