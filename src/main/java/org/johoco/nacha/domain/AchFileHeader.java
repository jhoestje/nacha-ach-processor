package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchFileHeader {
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
