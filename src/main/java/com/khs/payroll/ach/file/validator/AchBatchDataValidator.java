package com.khs.payroll.ach.file.validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchBatchControlRecord;
import com.khs.payroll.ach.file.record.AchBatchHeaderRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.validator.constant.ValidationStep;
import com.khs.payroll.ach.file.validator.context.AchFileValidationContext;
import com.khs.payroll.constant.StandardEntryClassCode;

import jakarta.validation.ValidationException;

// ACH batches, an informative company entry description should be included
public class AchBatchDataValidator {

    private static final String BATCH_EFFECTIVE_DATE_MESSAGE = "Incorrect batch effective date %s";
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private static final String STANDARD_ENTRY_CLASS_CODE_MESSAGE = "Incorrect Standard Entry Class Code %s";
    private static final String EXPECTED_BATCH_ENTRY_HASH_MESSAGE = "Incorrect expected Batch Entry Hash %s";
    private static final String TOTAL_ENTRY_AND_ADDENDA_COUNT_MESSAGE = "expectedTotalEntryAndAddendaCount %d did not equal actualEntryAndAddendaCount %d";
    private static final String TOTAL_DEBIT_AMOUNT_MESSAGE = "expectedTotalDebitAmount %s did not equal actualTotalDebitAmount %s";
    private static final String TOTAL_CREDIT_AMOUNT_MESSAGE = "expectedTotalCreditAmount %s did not equal actualTotalCreditAmount %s";

    /**
     * Batch control total “sums up all entries' count and dollar amount. It also
     * includes a hash total (i.e., checksum) to ensure validity of the batch.”
     *
     * @param batch
     * @param control
     */
    public void validate(final AchBatch batch, final AchFileValidationContext context) {
        context.setCurrentBatch(batch);
        // validate numbers and sums
        validateStandardEntryClassCode(batch, context);
        validateAmounts(batch, context);
        validateEntryHash(batch, context);
        validateEffectiveDate(batch, context);
        context.resetCurrentBatch();
    }

    /**
     * Validate Debit/Credit totals for the batch.
     * 
     * @param payment
     */
    @SuppressWarnings("incomplete-switch")
    private void validateAmounts(final AchBatch batch, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.BATCH_AMOUNTS);
        AchBatchControlRecord control = batch.getControlRecord();
        // entry+ addenda count
        Integer expectedTotalEntryAndAddendaCount = control.getEntryAddendaCount();
        // totalDebitAmount
        BigDecimal expectedTotalDebitAmount = control.getTotalDebitAmount();
        // totalCreditAmount
        BigDecimal expectedTotalCreditAmount = control.getTotalCreditAmount();

        int actualEntryAndAddendaCount = 0;
        BigDecimal actualTotalDebitAmount = new BigDecimal(0.0);
        BigDecimal actualTotalCreditAmount = new BigDecimal(0.0);

        for (AchEntryDetailRecord ed : batch.getEntryDetails()) {
            context.setCurrentEntryDetail(ed);
            ++actualEntryAndAddendaCount;
            if (!CollectionUtils.isEmpty(ed.getAddenda())) {
                actualEntryAndAddendaCount += ed.getAddenda().size();
            }
            switch (ed.getTransactionCode()) {
            case CONSUMER_CREDIT_DEPOSIT:
            case CORPORATE_CREDIT_DEPOSIT: {
                actualTotalCreditAmount = actualTotalCreditAmount.add(ed.getAmount());
                break;
            }
            case CONSUMER_DEBIT_PAYMENT:
            case CORPORATE_DEBIT_PAYMENT: {
                actualTotalDebitAmount = actualTotalDebitAmount.add(ed.getAmount());
                break;
            }
            }
        }
        context.resetCurrentEntryDetail();
        if (!expectedTotalCreditAmount.equals(actualTotalCreditAmount)) {
            reportError(context,
                    String.format(TOTAL_CREDIT_AMOUNT_MESSAGE, expectedTotalCreditAmount.toPlainString(), actualTotalCreditAmount.toPlainString()));
        }
        if (!expectedTotalDebitAmount.equals(actualTotalDebitAmount)) {
            reportError(context, String.format(TOTAL_DEBIT_AMOUNT_MESSAGE, expectedTotalDebitAmount.toPlainString(), actualTotalDebitAmount.toPlainString()));
        }
        if (!expectedTotalEntryAndAddendaCount.equals(Integer.valueOf(actualEntryAndAddendaCount))) {
            reportError(context, String.format(TOTAL_ENTRY_AND_ADDENDA_COUNT_MESSAGE, expectedTotalEntryAndAddendaCount, actualEntryAndAddendaCount));
        }
    }

    /**
     * Batch control total “sums up all entries' count and dollar amount. It also
     * includes a hash total (i.e., checksum) to ensure validity of the batch.”
     *
     * @param batch
     * @param control
     */
    private void validateEntryHash(final AchBatch batch, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.BATCH_ENTRY_HASH);
        AchBatchControlRecord control = batch.getControlRecord();
        Integer expectedEntryHash = control.getEntryHash();
        int actualEntryHash = 0;
        for (AchEntryDetailRecord ed : batch.getEntryDetails()) {
            context.setCurrentEntryDetail(ed);
            actualEntryHash += Integer.parseInt(ed.getReceivingDFIIdentification());
        }
        context.resetCurrentEntryDetail();
        if (!expectedEntryHash.equals(Integer.valueOf(actualEntryHash))) {
            reportError(context, String.format(EXPECTED_BATCH_ENTRY_HASH_MESSAGE, actualEntryHash));
        }
    }

    /**
     * For Payroll/Direct Deposits should use the 'PPD' standard entry class code
     * (SEC code)
     * 
     * @param batch
     * @param context
     */
    private void validateStandardEntryClassCode(final AchBatch batch, final AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.BATCH_SEC_CODE);
        AchBatchHeaderRecord header = batch.getHeaderRecord();

        if (!StandardEntryClassCode.PPD.equals(header.getStandardEntryClassCode())) {
            reportError(context, String.format(STANDARD_ENTRY_CLASS_CODE_MESSAGE, header.getStandardEntryClassCode().toString()));
        }
    }

    /**
     * Just make sure the effective date is after "today" in this example.
     * 
     * @param batch
     * @param context
     */
    private void validateEffectiveDate(AchBatch batch, AchFileValidationContext context) {
        context.setCurrentValidationStep(ValidationStep.BATCH_EFFECTIVE_DATE);
        if (LocalDate.now().isAfter(batch.getHeaderRecord().getEffectiveEntryDate())) {
            reportError(context, String.format(BATCH_EFFECTIVE_DATE_MESSAGE, batch.getHeaderRecord().getEffectiveEntryDate()));
        }
    }

    private void reportError(final AchFileValidationContext context, String message) {
        context.addErrorMessage(new ValidationException(message));
        LOG.error(message);
        LOG.error(context.toString());
    }
}
