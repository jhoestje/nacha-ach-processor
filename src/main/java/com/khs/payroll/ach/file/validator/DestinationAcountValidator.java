package com.khs.payroll.ach.file.validator;

import org.springframework.stereotype.Component;

import com.khs.payroll.domain.PayrollPayment;

/**
 * Validate the details on the destination financial institution and account involved in the transaction.
 * 
 * Verify the destination information from internal trusted sources.
 */
@Component
public class DestinationAcountValidator {

    public void validate(final PayrollPayment payment) {
       // verify dfiAccountNumber
        return;
    }

}
