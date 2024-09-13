package com.khs.payroll.ach.file.validator.context;

import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.validator.constant.ValidationStep;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Context to provide details if there are issues during the validation process.
 */
@Getter
@Setter
@ToString
public class AchFileValidationContext {
    private final String fileName;
    private AchBatch currentBatch;
    private AchEntryDetailRecord currentEntryDetail;
    private ValidationStep currentValidationStep;

    public AchFileValidationContext(final String fileName) {
        this.fileName = fileName;
    }

    public void resetCurrentBatch() {
        this.currentBatch = null;
    }

    public void resetCurrentEntryDetail() {
        this.currentEntryDetail = null;
    }
}
