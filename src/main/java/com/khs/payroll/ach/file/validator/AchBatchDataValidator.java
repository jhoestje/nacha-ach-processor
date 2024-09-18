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

// ACH batches, an informative company entry description should be included
public class AchBatchDataValidator {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    public AchBatchDataValidator() {
    }

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
            LOG.error(String.format("expectedTotalCreditAmount %s did not equal actualTotalCreditAmount %s", expectedTotalCreditAmount.toPlainString(),
                    actualTotalCreditAmount.toPlainString()));
            LOG.error(context.toString());
        }
        if (!expectedTotalDebitAmount.equals(actualTotalDebitAmount)) {
            LOG.error(String.format("expectedTotalDebitAmount %s did not equal actualTotalDebitAmount %s", expectedTotalDebitAmount.toPlainString(),
                    actualTotalDebitAmount.toPlainString()));
            LOG.error(context.toString());
        }
        if (!expectedTotalEntryAndAddendaCount.equals(Integer.valueOf(actualEntryAndAddendaCount))) {
            LOG.error(String.format("expectedTotalEntryAndAddendaCount %d did not equal actualEntryAndAddendaCount %d", expectedTotalEntryAndAddendaCount,
                    actualEntryAndAddendaCount));
            LOG.error(context.toString());
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
            LOG.error("");
            LOG.error(context.toString());
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
            LOG.error(String.format("Incorrect Standard Entry Class Code %s", header.getStandardEntryClassCode().toString()));
            LOG.error(context.toString());
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
            LOG.error(String.format("Incorrect batch effective date %s", batch.getHeaderRecord().getEffectiveEntryDate()));
            LOG.error(context.toString());
        }
    }
}
