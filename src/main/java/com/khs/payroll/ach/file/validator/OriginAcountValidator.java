package com.khs.payroll.ach.file.validator;

import org.springframework.stereotype.Component;

/**
 * Validate the details on the origin financial institution involved in the transaction.
 * 
 * Helps ensure the ACH File was not edited.
 * 
 * This will be replace by a trusted source to validate information.
 */
@Component
public class OriginAcountValidator {

    public void validate(final String originatingDFIIdentification, final String companyIdentification) {
        // validate the company identification with the NACHA network
        return;
    }

}
