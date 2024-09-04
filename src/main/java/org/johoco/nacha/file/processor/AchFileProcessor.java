package org.johoco.nacha.file.processor;

import java.io.File;

import org.johoco.nacha.domain.AchFileLog;
import org.johoco.nacha.repository.AchFileLogRepository;
import org.springframework.stereotype.Component;

@Component
public class AchFileProcessor {

private AchFileLogRepository requestRepository;
    
    public AchFileProcessor(final AchFileLogRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    
    public void process(final File achFile) {
        AchFileLog request = new AchFileLog();
        request.setFilename(achFile.getName());
        requestRepository.save(request);
    }

}
