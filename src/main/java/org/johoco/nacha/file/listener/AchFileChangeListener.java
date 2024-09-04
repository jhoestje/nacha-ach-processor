package org.johoco.nacha.file.listener;

import java.util.Set;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

@Component
public class AchFileChangeListener implements FileChangeListener {

    public AchFileChangeListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for (ChangedFiles cfiles : changeSet) {
            for (ChangedFile cfile : cfiles.getFiles()) {
                System.out.println(cfile.getType() + ":" + cfile.getFile().getName());
            }
        }
    }

}
