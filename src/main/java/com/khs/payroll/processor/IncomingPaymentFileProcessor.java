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
import com.khs.payroll.constant.AchFileState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.exception.FileValidationException;
import com.khs.payroll.payment.converter.PaymentConversion;
import com.khs.payroll.repository.AchPaymentRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

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
    private AchPaymentRepository achRepository;

    public IncomingPaymentFileProcessor(final AchFileParser fileProcessor, final AchDataValidator validator, final PaymentConversion conversion,
            final BatchPaymentsProcessor batch, final AchPaymentRepository achRepository) {
        this.fileProcessor = fileProcessor;
        this.validator = validator;
        this.conversion = conversion;
        this.batch = batch;
        this.achRepository = achRepository;
    }

    public void process(final File achFile) {
        AchPayment achPayments = null;
        AchFileValidationContext context = new AchFileValidationContext(achFile.getName());
        try {
            LOG.info("Processing file " + achFile.getName());
            achPayments = fileProcessor.parse(achFile);
            validator.validate(achPayments, context);
            // convert to domain objects
            List<PayrollPayment> payments = conversion.convertToPayments(achPayments);
            // assign to a batch for scheduled payment and save
            batch.batchPayments(payments);
            // done processing until scheduled payment
            achPayments.setState(AchFileState.PROCESSED);
            achRepository.save(achPayments);
        } catch (ConstraintViolationException cve) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
            }
            processFailed(achPayments, sb, context, cve);
            LOG.error(String.format("Failed to register incoming payments File  %s", achFile.getName()), cve);
        } catch (FileValidationException fve) {
            StringBuilder sb = new StringBuilder();
            for (ValidationException violation : context.getErrorMessages()) {
                sb.append(violation.getMessage()).append("\n");
            }
            processFailed(achPayments, sb, context, fve);
            LOG.error(String.format("Failed to register incoming payments File  %s", achFile.getName()), fve);
        } catch (Exception e) {
            processFailed(achPayments, new StringBuilder(), context, e);
        }
    }

    private void processFailed(final AchPayment achPayments, final StringBuilder message, final AchFileValidationContext context, final Exception e) {
        LOG.error(message.toString());
        LOG.error(context.toString());
        if (achPayments != null) {
            achPayments.setState(AchFileState.FAILED);
            achRepository.save(achPayments);
        }
        LOG.error("Failed to register incoming payments File", e);
    }
}
