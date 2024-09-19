package com.khs.payroll.ach.file.validator;

import org.springframework.stereotype.Component;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.exception.InvalidPaymentException;

/**
 * Validate the details on the origin financial institution involved in the
 * transaction.
 * 
 * Helps ensure the ACH File was not edited.
 * 
 * This will be replace by a trusted source to validate information.
 */
@Component
public class OriginAcountValidator {

    /**
     * Add validation rules to limit mistakes and fraud. Ex: add an amount
     * validation rule to compare possible authorized payroll amount.
     * 
     * @param originatingDFIIdentification
     * @param companyIdentification
     */
    public void validate(final PaymentBatch batch) throws InvalidPaymentException {
        // batch.getOriginatingDFIIdentification()
        // validate the company identification with the NACHA network
        // batch.getCompanyIdentification()
        
        // for this example, just return for happy path
        return;
    }

}
