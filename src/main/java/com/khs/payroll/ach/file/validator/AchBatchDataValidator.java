package com.khs.payroll.ach.file.validator;

import org.springframework.util.CollectionUtils;

import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchBatchControlRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.record.AchPayment;

import jakarta.validation.Validator;

public class AchBatchDataValidator {

    private Validator validator;

    public AchBatchDataValidator(final Validator validator) {
        this.validator = validator;
    }

    /**
     * Validate Debit/Credit totals for the batch.
     * 
     * @param payment
     */
    @SuppressWarnings("incomplete-switch")
    public void validate(final AchBatch batch) {
        // validate numbers and sums

        AchBatchControlRecord control = batch.getControlRecord();
        // entry+ addenda count
        Integer expectedTotalEntryAndAddendaCount = control.getEntryAddendaCount();
        // totalDebitAmount
        Double expectedTotalDebitAmount = control.getTotalDebitAmount();
        // totalCreditAmount
        Double expectedTotalCreditAmount = control.getTotalCreditAmount();
        // serviceClassCode -> credit?

        int actualEntryAndAddendaCount = 0;
        double actualTotalDebitAmount = 0.0;
        double actualTotalCreditAmount = 0.0;

        for (AchEntryDetailRecord ed : batch.getEntryDetails()) {
            ++actualEntryAndAddendaCount;
            if (!CollectionUtils.isEmpty(ed.getAddenda())) {
                actualEntryAndAddendaCount += ed.getAddenda().size();
            }
            switch (ed.getTransactionCode()) {
            case CONSUMER_CREDIT_DEPOSIT:
            case CORPORATE_CREDIT_DEPOSIT: {
                actualTotalCreditAmount += ed.getAmount();
                break;
            }
            case CONSUMER_DEBIT_PAYMENT:
            case CORPORATE_DEBIT_PAYMENT: {
                actualTotalDebitAmount += ed.getAmount();
                break;
            }
            }
        }
        
        if(!expectedTotalCreditAmount.equals(Double.valueOf(actualTotalCreditAmount))) {
            
        }
        if(!expectedTotalDebitAmount.equals(Double.valueOf(actualTotalDebitAmount))) {
            
        }
        if(!expectedTotalEntryAndAddendaCount.equals(Integer.valueOf(actualEntryAndAddendaCount))) {
            
        }
    }
    // For Payroll/Direct Deposits
    // should use the 'PPD' standard entry class code (SEC code) and, as with all
    // ACH batches, an informative company entry description should be included

//    @Override
//    public boolean supports(Class<?> clazz) {
//        return AchBatch.class.isAssignableFrom(clazz);
//    }

//    incorrect account details, or closed accounts. You should handle ACH returns and error notifications from the bank by parsing any return files, logging the issues, and notifying HR or the employees.
//
//            Implement retries or alternative payment methods (e.g., paper checks) for failed transactions.
//            Log detailed error messages for easy reconciliation.

}
