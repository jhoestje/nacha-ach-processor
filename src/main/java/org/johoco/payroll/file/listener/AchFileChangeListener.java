package org.johoco.payroll.file.listener;

import java.util.Set;

import org.johoco.payroll.ach.file.processor.AchFileProcessor;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

@Component
public class AchFileChangeListener implements FileChangeListener {

    private AchFileProcessor fileProcessor;

    public AchFileChangeListener(final AchFileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for (ChangedFiles files : changeSet) {
            for (ChangedFile cf : files.getFiles()) {
                System.out.println(cf.getType() + ":" + cf.getFile().getName());
                if (Type.ADD.equals(cf.getType()))
                    fileProcessor.process(cf.getFile());
            }
        }
    }
}
