package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchHeader {
    private String recordTypeCode;
    private String priorityCode;
    private String immediateDestination;
    private String immediateOrigin;
}
