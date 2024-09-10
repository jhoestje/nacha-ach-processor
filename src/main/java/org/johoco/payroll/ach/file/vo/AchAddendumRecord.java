package org.johoco.payroll.ach.file.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchAddendumRecord {
    private String recordTypeCode; // "7"
    private String addendaTypeCode; // Defines the type of addendum
    private String paymentRelatedInfo; // Not required;  Holds supplementary information (up to 80 characters)
    private int addendaSequenceNumber; // Identifies the sequence number of the addenda (starting at 0001)
    private int entryDetailSequenceNumber; // Identifies the Entry Detail Record this addendum is tied to
}
