package org.johoco.nacha.file.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.johoco.nacha.constant.AchRecordType;
import org.johoco.nacha.domain.AchAddendum;
import org.johoco.nacha.domain.AchBatchControlRecord;
import org.johoco.nacha.domain.AchBatchHeaderRecord;
import org.johoco.nacha.domain.AchEntryDetail;
import org.johoco.nacha.domain.AchFileControlRecord;
import org.johoco.nacha.domain.AchFileLog;
import org.johoco.nacha.domain.AchFileHeader;
import org.johoco.nacha.parser.AchFileLineParser;
import org.johoco.nacha.repository.AchFileLogRepository;
import org.springframework.stereotype.Component;

@Component
public class AchFileProcessor {

    private AchFileLogRepository logRepository;

    public AchFileProcessor(final AchFileLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void process(final File achFile) {
        AchFileLog request = new AchFileLog();
        request.setFilename(achFile.getName());
        logRepository.save(request);

        try (BufferedReader reader = new BufferedReader(new FileReader(achFile.getAbsolutePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String recordTypeValue = line.substring(0, 1);
                AchRecordType recordType = AchRecordType.recordTypeOf(recordTypeValue);
                switch (recordType) {
                case FILE_HEADER_RECORD:
                    AchFileHeader header = AchFileLineParser.parseFileHeader(line);
                    break;
                case ENTRY_DETAIL_RECORD:
                    AchEntryDetail entryDetail = AchFileLineParser.parseEntryDetail(line);
                    break;
                case ADDENDUM:
                    AchAddendum addendum = AchFileLineParser.parseAddendum(line);
                    break;
                case BATCH_CONTROL_TOTAL:
                    AchBatchControlRecord controlTotal = AchFileLineParser.parseBatchControlRecord(line);
                    break;
                case BATCH_HEADER_RECORD:
                    AchBatchHeaderRecord headerRecord = AchFileLineParser.parseBatchHeaderRecord(line);
                    break;
                case FILE_CONTROL_RECORD:
                    AchFileControlRecord controlRecord = AchFileLineParser.parseFileControlRecord(line);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
