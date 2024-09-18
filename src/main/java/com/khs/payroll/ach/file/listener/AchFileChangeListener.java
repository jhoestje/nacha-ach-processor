package com.khs.payroll.ach.file.listener;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import com.khs.payroll.processor.IncomingPaymentFileProcessor;

@Component
public class AchFileChangeListener implements FileChangeListener {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    private IncomingPaymentFileProcessor processor;

    public AchFileChangeListener(final IncomingPaymentFileProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for (ChangedFiles files : changeSet) {
            for (ChangedFile cf : files.getFiles()) {
                LOG.debug(cf.getType() + ":" + cf.getFile().getName());
                if (Type.ADD.equals(cf.getType()))
                    try {
                        processor.process(cf.getFile());
                    } catch (Exception e) {
                        LOG.error("Processing incoming file failed", e);
                    }
            }
        }
    }
}
