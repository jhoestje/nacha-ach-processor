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
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(achFile.getAbsolutePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String recordTypeValue = line.substring(0, 1);
                AchRecordType recordType = AchRecordType.valueOf(recordTypeValue);
                switch (recordType) {
                    case FILE_HEADER_RECORD:
                        AchHeader header = AchFileLineParser.parseHeader(line);
                        // Process header
                        break;

                    case ENTRY_DETAIL_RECORD:
                        AchEntryDetail entryDetail = AchFileLineParser.parseEntryDetail(line);
                        // Process entry detail
                        break;
                case ADDENDUM:
                    break;
                case BATCH_CONTROL_TOTAL:
                    break;
                case BATCH_HEADER_RECORD:
                    break;
                case FILE_CONTROL_RECORD:
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
