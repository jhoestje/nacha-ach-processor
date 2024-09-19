package com.khs.payroll.ach.file.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.validator.constant.ValidationStep;
import com.khs.payroll.ach.file.validator.context.AchFileValidationContext;
import com.khs.payroll.constant.AchReturnCode;
import com.khs.payroll.exception.AchFieldValidationException;

public class AchEntryDetailDataValidator {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private static final Integer HAS_ADDENDUM_INDICATOR = Integer.valueOf(1);
    private static final String ERROR_MESSAGE = "Either incorrect Addunda Indicator or missing Addunda";

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
                context.addErrorMessage(new AchFieldValidationException(AchReturnCode.ADDENDA_ERROR, ERROR_MESSAGE));
                LOG.error(ERROR_MESSAGE);
                LOG.error(context.toString());
            }
        }
        context.resetCurrentEntryDetail();
    }

}
