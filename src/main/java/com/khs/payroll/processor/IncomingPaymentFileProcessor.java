package com.khs.payroll.processor;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.processor.AchFileProcessor;
import com.khs.payroll.ach.file.record.AchPayment;
import com.khs.payroll.ach.file.validator.AchDataValidator;

@Component
public class IncomingPaymentFileProcessor {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private AchFileProcessor fileProcessor;
    private AchDataValidator validator;

    public IncomingPaymentFileProcessor(final AchFileProcessor fileProcessor, final AchDataValidator validator) {
        this.fileProcessor = fileProcessor;
        this.validator = validator;
    }

    public void process(final File achFile) {
        try {
            AchPayment payments = fileProcessor.process(achFile);
            validator.validate(payments);
            LOG.info("finsihed validation");
        } catch (Exception e) {
            LOG.error(String.format("Failed to register incoming payments File  %s", achFile.getName()), e);
        }
    }
}
