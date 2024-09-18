package com.khs.payroll.processor;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.processor.AchFileParser;

@Deprecated
public class PaymentProcessor {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private AchFileParser fileProcessor;

    public PaymentProcessor(final AchFileParser fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
    
    public void process(final File achFile) {
        try {
            fileProcessor.parse(achFile);
        } catch (Exception e) {
            LOG.error(String.format("Failed to register incoming payments File  %s", achFile.getName()), e);
        }
    }
}
