package org.johoco.payroll.ach.file.record;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchBatch {
    private AchBatchHeaderRecord headerRecord;
    private List<AchEntryDetail> entryDetails;      // Transactions    
    private AchBatchControlRecord controlRecord;
    
    public AchBatch addEntryDetail(final AchEntryDetail entryDetail) {
        if(entryDetails == null) {
            entryDetails = new ArrayList<>();
        }
        entryDetails.add(entryDetail);
        return this;
    }
}
