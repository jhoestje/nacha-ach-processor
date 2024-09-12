package com.khs.payroll.processor;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.processor.AchFileProcessor;

@Component
public class PaymentProcessor {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private AchFileProcessor fileProcessor;

    public PaymentProcessor(final AchFileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
    
    public void process(final File achFile) {
        try {
            fileProcessor.process(achFile);
        } catch (Exception e) {
            LOG.error(String.format("Failed to register incoming payments File  %s", achFile.getName()), e);
        }
    }
}
