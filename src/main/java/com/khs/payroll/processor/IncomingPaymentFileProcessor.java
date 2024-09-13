package com.khs.payroll.processor;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.processor.AchFileParser;
import com.khs.payroll.ach.file.record.AchPayment;
import com.khs.payroll.ach.file.validator.AchDataValidator;
import com.khs.payroll.ach.file.validator.context.AchFileValidationContext;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.payment.converter.PaymentConversion;

/**
 * Main processor class that contains all the steps to get the payments ready
 * for transactions.
 */
@Component
public class IncomingPaymentFileProcessor {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private AchFileParser fileProcessor;
    private AchDataValidator validator;
    private PaymentConversion conversion;
    private BatchPaymentsProcessor batch;

    public IncomingPaymentFileProcessor(final AchFileParser fileProcessor, final AchDataValidator validator, final PaymentConversion conversion,
            final BatchPaymentsProcessor batch) {
        this.fileProcessor = fileProcessor;
        this.validator = validator;
        this.conversion = conversion;
        this.batch = batch;
    }

    public void process(final File achFile) {
        try {
            AchPayment achPayments = fileProcessor.parse(achFile);
            LOG.info("finsihed parsing");
            AchFileValidationContext context = new AchFileValidationContext(achFile.getName());
            validator.validate(achPayments, context);
            // convert to domain objects
            List<PayrollPayment> payments = conversion.convertToPayments(achPayments);
            // assign to a batch for processing and save
            batch.batchPayments(payments);
            // done until processing
        } catch (Exception e) {
            LOG.error(String.format("Failed to register incoming payments File  %s", achFile.getName()), e);
        }
    }
}
