package com.khs.payroll.ach.file.validator;

import java.math.BigDecimal;
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
import com.khs.payroll.constant.AchReturnCode;
import com.khs.payroll.exception.AchFieldValidationException;
import com.khs.payroll.exception.FileValidationException;

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

    private static final String BATCH_RECORD_COUNT_MESSAGE = "Incorrect Batch Record Count %d";
    private Validator validator;
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private static final String TOTAL_ENTRY_AND_ADDENDA_COUNT_MESSAGE = "expectedTotalEntryAndAddendaCount %d did not equal actualEntryAndAddendaCount %d";
    private static final String TOTAL_DEBIT_AMOUNT_MESSAGE = "expectedTotalDebitAmount %s did not equal actualTotalDebitAmount %s";
    private static final String TOTAL_CREDIT_AMOUNT_MESSAGE = "expectedTotalCreditAmount %s did not equal actualTotalCreditAmount %s";
    private static final String EXPECTED_BATCH_ENTRY_HASH_MESSAGE = "Incorrect expected File Entry Hash %s";

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
        LOG.info("Starting to validate " + context.getFileName());
        context.setCurrentValidationStep(ValidationStep.ANNOTATIONS);
        AchBatchDataValidator batchValidator = new AchBatchDataValidator();
        Set<ConstraintViolation<AchPayment>> violations = validator.validate(payment);
        if (!violations.isEmpty()) {
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
            reportError(context, AchReturnCode.FIELD_ERROR, String.format(BATCH_RECORD_COUNT_MESSAGE, actualBatchRecordCount));
        }

        validateEntryHash(payment, context);
        LOG.info(String.format("Finished validating %s with %d", context.getFileName(), context.getErrorMessages().size()));
        if (!context.getErrorMessages().isEmpty()) {
            throw new FileValidationException(context.getErrorMessages());
        }
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
        BigDecimal expectedTotalDebitAmount = control.getTotalDebitAmount();
        // totalCreditAmount
        BigDecimal expectedTotalCreditAmount = control.getTotalCreditAmount();

        int actualEntryAndAddendaCount = 0;
        BigDecimal actualTotalDebitAmount = new BigDecimal(0.0);
        BigDecimal actualTotalCreditAmount = new BigDecimal(0.0);

        for (AchBatch ab : payment.getBatchRecords()) {
            context.setCurrentBatch(ab);
            AchBatchControlRecord batchControl = ab.getControlRecord();

            actualEntryAndAddendaCount += batchControl.getEntryAddendaCount();
            actualTotalCreditAmount = actualTotalCreditAmount.add(batchControl.getTotalCreditAmount());
            actualTotalDebitAmount = actualTotalDebitAmount.add(batchControl.getTotalDebitAmount());
        }
        context.resetCurrentBatch();

        if (!expectedTotalCreditAmount.equals(actualTotalCreditAmount)) {
            reportError(context, AchReturnCode.FIELD_ERROR,
                    String.format(TOTAL_CREDIT_AMOUNT_MESSAGE, expectedTotalCreditAmount.toPlainString(), actualTotalCreditAmount.toPlainString()));
        }
        if (!expectedTotalDebitAmount.equals(actualTotalDebitAmount)) {
            reportError(context, AchReturnCode.FIELD_ERROR, String.format(TOTAL_DEBIT_AMOUNT_MESSAGE, expectedTotalDebitAmount.toPlainString(), actualTotalDebitAmount.toPlainString()));
        }
        if (!expectedTotalEntryAndAddendaCount.equals(Integer.valueOf(actualEntryAndAddendaCount))) {
            reportError(context, AchReturnCode.ADDENDA_ERROR, String.format(TOTAL_ENTRY_AND_ADDENDA_COUNT_MESSAGE, expectedTotalEntryAndAddendaCount, actualEntryAndAddendaCount));
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
            reportError(context, AchReturnCode.FILE_RECORD_ERROR, String.format(EXPECTED_BATCH_ENTRY_HASH_MESSAGE, actualEntryHash));
        }
    }

    private void reportError(final AchFileValidationContext context, final AchReturnCode returnCode, final String message) {
        do something?
        context.addErrorMessage(new AchFieldValidationException(returnCode, message));
        LOG.error(message);
        LOG.error(context.toString());
    }
}
