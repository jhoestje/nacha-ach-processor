package com.khs.payroll.ach.file.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.parser.AchFileLineParser;
import com.khs.payroll.ach.file.record.AchAddendumRecord;
import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchBatchControlRecord;
import com.khs.payroll.ach.file.record.AchBatchHeaderRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.record.AchFileControlRecord;
import com.khs.payroll.ach.file.record.AchFileHeaderRecord;
import com.khs.payroll.ach.file.record.AchPayment;
import com.khs.payroll.constant.AchRecordType;
import com.khs.payroll.domain.AchFileLog;
import com.khs.payroll.repository.AchFileLogRepository;

@Component
public class AchFileParser {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private AchFileLogRepository logRepository;
    private AchFileLineParser lineParser = new AchFileLineParser();

    // ACH files need to be a multiple of 10 lines long are lines are padded with
    // all 9s
    private String paddingRecordCharacters = "99999";

    public AchFileParser(final AchFileLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public AchPayment parse(final File achFile) throws Exception {
        AchFileLog request = new AchFileLog();
        request.setFilename(achFile.getName());
        logRepository.save(request);

        try (BufferedReader reader = new BufferedReader(new FileReader(achFile.getAbsolutePath()))) {
            AchPayment payments = new AchPayment();
            AchBatch currentBatch = new AchBatch();
            AchEntryDetailRecord currentEntryDetail = new AchEntryDetailRecord();
            String line;
            while ((line = reader.readLine()) != null) {
                String recordTypeValue = line.substring(0, 1);
                Optional<AchRecordType> recordTypeOpt = AchRecordType.recordTypeOf(recordTypeValue);
                if (recordTypeOpt.isPresent()) {
                    switch (recordTypeOpt.get()) {
                    case FILE_HEADER_RECORD:
                        AchFileHeaderRecord header = lineParser.parseFileHeader(line);
                        payments.setFileHeader(header);
                        break;
                    case ENTRY_DETAIL_RECORD:
                        currentEntryDetail = lineParser.parseEntryDetail(line);
                        currentBatch.addEntryDetail(currentEntryDetail);
                        break;
                    case ADDENDUM:
                        AchAddendumRecord addendum = lineParser.parseAddendum(line);
                        currentEntryDetail.addAchAddendum(addendum);
                        break;
                    case BATCH_CONTROL_RECORD:
                        AchBatchControlRecord batchControl = lineParser.parseBatchControlRecord(line);
                        currentBatch.setControlRecord(batchControl);
                        payments.addAchBatch(currentBatch);
                        currentBatch = new AchBatch();
                        break;
                    case BATCH_HEADER_RECORD:
                        currentBatch = new AchBatch();
                        AchBatchHeaderRecord headerRecord = lineParser.parseBatchHeaderRecord(line);
                        currentBatch.setHeaderRecord(headerRecord);
                        break;
                    case FILE_CONTROL_RECORD:
                        if (!StringUtils.startsWith(line, paddingRecordCharacters)) {
                            AchFileControlRecord controlRecord = lineParser.parseFileControlRecord(line);
                            payments.setFileControl(controlRecord);
                        } else {
                            LOG.info(String.format("Skipping padding record in file %s", achFile.getName()));
                        }
                        break;
                    }
                } else {
                    // fail file with details
                }
            }
            LOG.info(String.format("Finished parsing ACH file:  %s:", achFile.getName()));
            return payments;
        } catch (Exception e) {
            LOG.error(String.format("Failed to parse ACH File  %s", achFile.getName()), e);
            //need to change exception type to bad file
            throw e;
        }
    }

}
