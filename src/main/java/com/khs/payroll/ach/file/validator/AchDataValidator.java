package com.khs.payroll.ach.file.validator;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchBatchControlRecord;
import com.khs.payroll.ach.file.record.AchFileControlRecord;
import com.khs.payroll.ach.file.record.AchPayment;
import com.khs.payroll.ach.file.validator.constant.ValidationStep;
import com.khs.payroll.ach.file.validator.context.AchFileValidationContext;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

// For Payroll/Direct Deposits
// should use the 'PPD' standard entry class code (SEC code) and, as with all
// ACH batches, an informative company entry description should be included

//incorrect account details, or closed accounts. You should handle ACH returns and error notifications from the bank by parsing any return files, logging the issues, and notifying HR or the employees.
//
//        Implement retries or alternative payment methods (e.g., paper checks) for failed transactions.
//        Log detailed error messages for easy reconciliation.

@Component
public class AchDataValidator {

    private Validator validator;
    private Logger LOG = LoggerFactory.getLogger(getClass());

    public AchDataValidator(final Validator validator) {
        this.validator = validator;
    }

    /**
     * File control total “sums up all entries' count and dollar amount. It also
     * includes a hash total (i.e., checksum) to ensure validity of the batches.”
     *
     * @param payment
     */
    public void validate(final AchPayment payment, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.ANNOTATIONS);
        AchBatchDataValidator batchValidator = new AchBatchDataValidator();
        Set<ConstraintViolation<AchPayment>> violations = validator.validate(payment);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AchPayment> violation : violations) {
                sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
            }
            LOG.info(sb.toString());
            throw new ConstraintViolationException(violations);
        }

        validateAmountTotals(payment, context);

        Integer expectedBatchRecordCount = payment.getFileControl().getBatchCount();
        int actualBatchRecordCount = 0;
        for (AchBatch ab : payment.getBatchRecords()) {
            ++actualBatchRecordCount;
            batchValidator.validate(ab, context);
        }

        if (!expectedBatchRecordCount.equals(Integer.valueOf(actualBatchRecordCount))) {
            LOG.error("");
            LOG.error(context.toString());
        }

        validateEntryHash(payment, context);

    }

    /**
     * Ensure the validity of the amounts.
     * 
     * @param payment
     */
    private void validateAmountTotals(final AchPayment payment, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.FILE_AMOUNTS);
        AchFileControlRecord control = payment.getFileControl();
        // entry+ addenda count
        Integer expectedTotalEntryAndAddendaCount = control.getEntryAddendaCount();
        // totalDebitAmount
        Double expectedTotalDebitAmount = control.getTotalDebitAmount();
        // totalCreditAmount
        Double expectedTotalCreditAmount = control.getTotalCreditAmount();

        int actualEntryAndAddendaCount = 0;
        double actualTotalDebitAmount = 0.0;
        double actualTotalCreditAmount = 0.0;

        for (AchBatch ab : payment.getBatchRecords()) {
            context.setCurrentBatch(ab);
            AchBatchControlRecord batchControl = ab.getControlRecord();

            actualEntryAndAddendaCount += batchControl.getEntryAddendaCount();
            actualTotalCreditAmount = Double.sum(actualTotalCreditAmount, batchControl.getTotalCreditAmount());
            actualTotalDebitAmount += Double.sum(actualTotalDebitAmount, batchControl.getTotalDebitAmount());
        }
        context.resetCurrentBatch();
        
        if (!expectedTotalCreditAmount.equals(Double.valueOf(actualTotalCreditAmount))) {
            LOG.error("");
            LOG.error(context.toString());

        }
        if (!expectedTotalDebitAmount.equals(Double.valueOf(actualTotalDebitAmount))) {
            LOG.error("");
            LOG.error(context.toString());
        }
        if (!expectedTotalEntryAndAddendaCount.equals(Integer.valueOf(actualEntryAndAddendaCount))) {
            LOG.error("");
            LOG.error(context.toString());
        }
    }

    /**
     * Ensure validity of the batches.
     *
     * @param batch
     * @param control
     */
    private void validateEntryHash(final AchPayment payment, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.FILE_ENTRY_HASH);
        AchFileControlRecord fileControl = payment.getFileControl();
        Long expectedEntryHash = fileControl.getEntryHash();
        long actualEntryHash = 0;
        for (AchBatch ed : payment.getBatchRecords()) {
            context.setCurrentBatch(ed);
            actualEntryHash = Long.sum(actualEntryHash, ed.getControlRecord().getEntryHash());
        }
        context.resetCurrentBatch();
        if (!expectedEntryHash.equals(Long.valueOf(actualEntryHash))) {
            LOG.error("");
            LOG.error(context.toString());
        }
    }
}
