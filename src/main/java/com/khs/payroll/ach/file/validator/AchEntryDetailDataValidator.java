package com.khs.payroll.ach.file.validator;

import java.util.Collections;

import org.springframework.util.CollectionUtils;

import com.khs.payroll.ach.file.record.AchEntryDetailRecord;

public class AchEntryDetailDataValidator {

    private static final Integer HAS_ADDENDUM_INDICATOR = Integer.valueOf(1);

    /**
     * Validate addenda indicator along with the presence of the addendum.
     * 
     * @param payment
     */
    public void validate(final AchEntryDetailRecord entryDetail) {

        if (HAS_ADDENDUM_INDICATOR.equals(entryDetail.getAddendaRecordIndicator())) {
            if (CollectionUtils.isEmpty(entryDetail.getAddenda())) {
                
            }
        }

        // validate file header and control are present

        // validate a batch is present with header and controller
        // validate an entry detail is present

        // validate required properties are present... maybe add as annotation to class?
        // validate numbers and sums

    }

}
