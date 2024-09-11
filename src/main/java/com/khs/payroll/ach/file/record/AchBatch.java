package com.khs.payroll.ach.file.record;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchBatch {
    @NotNull(message = "ACH Batch Header Record is required")
    @Valid
    private AchBatchHeaderRecord headerRecord;

    @NotNull(message = "ACH Entry Detail Records list is required")
    @Size(min = 1, message = "At least one Entry Detail Record is required")
    @Valid
    private List<AchEntryDetailRecord> entryDetails; // Transactions

    @NotNull(message = "ACH Batch Control Record is required")
    @Valid
    private AchBatchControlRecord controlRecord;

    public AchBatch addEntryDetail(final AchEntryDetailRecord entryDetail) {
        if (entryDetails == null) {
            entryDetails = new ArrayList<>();
        }
        entryDetails.add(entryDetail);
        return this;
    }
}
