package org.johoco.nacha.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchPayment {
    private AchFileHeaderRecord fileHeader;
    private List<AchBatch> batchRecords;
    private AchFileControlRecord fileControl;
        
    public AchPayment addAchBatch(final AchBatch achBatch) {
        if(batchRecords == null) {
            batchRecords = new ArrayList<>();
        }
        batchRecords.add(achBatch);
        return this;
    }
}
