package org.johoco.nacha.domain;

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
    private String recordTypeCode;
    private String priorityCode;
    private String immediateDestination;
    private String immediateOrigin;
    private String fileCreationDate;
    private String fileCreationTime;
    private String fileIdModifier;
    private String recordSize;
    private String blockingFactor;
    private String formatCode;
    private String immediateDestinationName;
    private String immediateOriginName;
    private String referenceCode;
}
