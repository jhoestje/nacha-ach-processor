package com.khs.payroll.ach.file.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.khs.payroll.ach.file.record.AchPayment;

@Component
public class AchDataValidator implements Validator {
    
    private Validator validator;
    
    public AchDataValidator(final Validator validator) {
        this.validator = validator;
    }
    
    public void validate(final AchPayment payment) {
        // annotation constraint validation first
        Errors errors = validator.validateObject(payment);
        if (errors.hasErrors()) {
          errors.getAllErrors();
        }
        // validate file header and control are present
        
        // validate a batch is present with header and controller
        // validate an entry detail is present
        
        //validate required properties are present... maybe add as annotation to class?
        // validate numbers and sums

    }
    //For Payroll/Direct Deposits
    //should use the 'PPD' standard entry class code (SEC code) and, as with all ACH batches, an informative company entry description should be included

    @Override
    public boolean supports(Class<?> clazz) {
        return AchPayment.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validator.validate(target, errors);;
    }
    
    
//    incorrect account details, or closed accounts. You should handle ACH returns and error notifications from the bank by parsing any return files, logging the issues, and notifying HR or the employees.
//
//            Implement retries or alternative payment methods (e.g., paper checks) for failed transactions.
//            Log detailed error messages for easy reconciliation.

    
    
}
