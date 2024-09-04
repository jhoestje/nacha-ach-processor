package org.johoco.nacha.file.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.johoco.nacha.constant.AchRecordType;
import org.johoco.nacha.domain.AchEntryDetail;
import org.johoco.nacha.domain.AchFileLog;
import org.johoco.nacha.domain.AchHeader;
import org.johoco.nacha.parser.AchFileLineParser;
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
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader("path/to/ach/file.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String recordType = line.substring(0, 1);
                AchRecordType t = AchRecordType.valueOf(recordType);
                switch (t) {
                    case FILE_HEADER_RECORD:
                        AchHeader header = AchFileLineParser.parseHeader(line);
                        // Process header
                        break;

                    case ENTRY_DETAIL_RECORD:
                        AchEntryDetail entryDetail = AchFileLineParser.parseEntryDetail(line);
                        // Process entry detail
                        break;

                    // Handle other record types
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
