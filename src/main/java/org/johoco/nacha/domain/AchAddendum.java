package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchAddendum {
    private String recordTypeCode; // "7"
    private String addendaTypeCode; // Defines the type of addendum
    private String paymentRelatedInfo; // Holds supplementary information (up to 80 characters)
    private String addendaSequenceNumber; // Identifies the sequence number of the addenda (starting at 0001)
    private String entryDetailSequenceNumber; // Identifies the Entry Detail Record this addendum is tied to
}
