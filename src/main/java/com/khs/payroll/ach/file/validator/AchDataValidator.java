package com.khs.payroll.ach.file.validator;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchPayment;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Component
public class AchDataValidator {
    
    private Validator validator;
    private Logger LOG = LoggerFactory.getLogger(getClass());
    public AchDataValidator(final Validator validator) {
        this.validator = validator;
    }
    
    public void validate(final AchPayment payment) {
        AchBatchDataValidator batchValidator = new AchBatchDataValidator(validator);
        Set<ConstraintViolation<AchPayment>> violations = validator.validate(payment);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AchPayment> violation : violations) {
                sb.append(violation.getPropertyPath())
                  .append(" ")
                  .append(violation.getMessage())
                  .append("\n");
            }
            LOG.info(sb.toString());
          throw new ConstraintViolationException(violations);
        }
        
        for(AchBatch ab : payment.getBatchRecords()) {
            batchValidator.validate(ab);    
        }
        
        
        // annotation constraint validation first
//        Errors errors = validator.validateObject(payment);
//        validator.validate(payment.getFileHeader(), errors);
//        validator.validate(payment.getBatchRecords(), errors);
//        validator.validate(payment.getFileControl(), errors);\
        
//        batchValidator.validate(payment.getBatchRecords(), errors);
//        
//        if (errors.hasErrors()) {
//          errors.getAllErrors();
//        }
        // validate file header and control are present
        
        // validate a batch is present with header and controller
        // validate an entry detail is present
        
        //validate required properties are present... maybe add as annotation to class?
        // validate numbers and sums

    }
    //For Payroll/Direct Deposits
    //should use the 'PPD' standard entry class code (SEC code) and, as with all ACH batches, an informative company entry description should be included

//    @Override
//    public boolean supports(Class<?> clazz) {
//        return AchPayment.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        LOG.debug("here");
//        //validator.validate(target, errors);;
//    }
    
    
//    incorrect account details, or closed accounts. You should handle ACH returns and error notifications from the bank by parsing any return files, logging the issues, and notifying HR or the employees.
//
//            Implement retries or alternative payment methods (e.g., paper checks) for failed transactions.
//            Log detailed error messages for easy reconciliation.

    
    
}
