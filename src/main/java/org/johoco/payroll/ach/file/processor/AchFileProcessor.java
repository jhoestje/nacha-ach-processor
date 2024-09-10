package org.johoco.payroll.ach.file.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.lang3.StringUtils;
import org.johoco.payroll.ach.file.parser.AchFileLineParser;
import org.johoco.payroll.ach.file.vo.AchAddendumRecord;
import org.johoco.payroll.ach.file.vo.AchBatch;
import org.johoco.payroll.ach.file.vo.AchBatchControlRecord;
import org.johoco.payroll.ach.file.vo.AchBatchHeaderRecord;
import org.johoco.payroll.ach.file.vo.AchEntryDetail;
import org.johoco.payroll.ach.file.vo.AchFileControlRecord;
import org.johoco.payroll.ach.file.vo.AchFileHeaderRecord;
import org.johoco.payroll.ach.file.vo.AchPayment;
import org.johoco.payroll.constant.AchRecordType;
import org.johoco.payroll.domain.AchFileLog;
import org.johoco.payroll.repository.AchFileLogRepository;
import org.johoco.payroll.repository.AchPaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AchFileProcessor {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private AchFileLogRepository logRepository;
    private AchPaymentRepository paymentRepository;
    private AchFileLineParser lineParser;

    private String paddingRecordCharacters = "99999";

    public AchFileProcessor(final AchFileLogRepository logRepository, final AchFileLineParser lineParser, final AchPaymentRepository paymentRepository) {
        this.logRepository = logRepository;
        this.lineParser = lineParser;
        this.paymentRepository = paymentRepository;
    }

    public void process(final File achFile) {
        AchFileLog request = new AchFileLog();
        request.setFilename(achFile.getName());
        logRepository.save(request);

        try (BufferedReader reader = new BufferedReader(new FileReader(achFile.getAbsolutePath()))) {
            AchPayment payments = new AchPayment();
            AchBatch currentBatch = new AchBatch();
            AchEntryDetail currentEntryDetail = new AchEntryDetail();
            String line;
            while ((line = reader.readLine()) != null) {
                String recordTypeValue = line.substring(0, 1);
                AchRecordType recordType = AchRecordType.recordTypeOf(recordTypeValue);
                switch (recordType) {
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
            }
            // validate and save
            paymentRepository.save(payments);
            LOG.info(String.format("Finished Processing ACH file:  %s:", achFile.getName()));
        } catch (Exception e) {
            LOG.error(String.format("Failed to parse ACH File  %s", achFile.getName()), e);
        }
    }

}
