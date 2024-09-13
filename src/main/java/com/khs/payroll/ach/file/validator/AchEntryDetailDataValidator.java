package com.khs.payroll.ach.file.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.validator.constant.ValidationStep;
import com.khs.payroll.ach.file.validator.context.AchFileValidationContext;

public class AchEntryDetailDataValidator {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private static final Integer HAS_ADDENDUM_INDICATOR = Integer.valueOf(1);

    /**
     * Validate addenda indicator along with the presence of the addendum.
     * 
     * @param payment
     */
    public void validate(final AchEntryDetailRecord entryDetail, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.ENTRY_DETAIL_ADDENDUM_INDICATOR);
        context.setCurrentEntryDetail(entryDetail);
        if (HAS_ADDENDUM_INDICATOR.equals(entryDetail.getAddendaRecordIndicator())) {
            if (CollectionUtils.isEmpty(entryDetail.getAddenda())) {
                LOG.error("");
                LOG.error(context.toString());
            }
        }
        context.resetCurrentEntryDetail();
    }

}
