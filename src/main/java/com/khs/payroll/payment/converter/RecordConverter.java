package com.khs.payroll.payment.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.parser.fixedwidth.FileachBatchFixedWidth;
import com.khs.payroll.ach.file.record.AchAddendumRecord;
import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchBatchachBatchRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.record.AchFileHeaderRecord;
import com.khs.payroll.ach.file.record.AchFileachBatchRecord;
import com.khs.payroll.ach.file.record.AchPayment;
import com.khs.payroll.constant.AchRecordType;
import com.khs.payroll.constant.PaymentState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.domain.PayrollPaymentAddendum;

@Component
public class RecordConverter {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    public List<PayrollPayment> convertToRecords(final AchPayment achPayment) {
        List<PayrollPayment> payments = new ArrayList<>();
//        achPayment.getBatchRecords().stream().forEach(ab -> convertAchBatch(ab, payments));
        return payments;
    }

    public StringBuilder convertFileHeaderRecord(final AchFileHeaderRecord header, final List<PayrollPayment> payments) {
        
        
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_HEADER_RECORD.getRecordType());

        header.getRecordTypeCode(cleanStringData(line, FileachBatchFixedWidth.RECORD_TYPE_CODE.getStart(), FileachBatchFixedWidth.RECORD_TYPE_CODE.getEnd()));
        header.getPriorityCode(cleanStringData(line, FileachBatchFixedWidth.PRIORITY_CODE.getStart(), FileachBatchFixedWidth.PRIORITY_CODE.getEnd()));
        header.getImmediateDestination(
                cleanStringData(line, FileachBatchFixedWidth.IMMEDIATE_DESTINATION.getStart(), FileachBatchFixedWidth.IMMEDIATE_DESTINATION.getEnd()));
        header.getImmediateOrigin(
                cleanStringData(line, FileachBatchFixedWidth.IMMEDIATE_ORIGIN.getStart(), FileachBatchFixedWidth.IMMEDIATE_ORIGIN.getEnd()));
        header.getFileCreationDate(
                cleanStringData(line, FileachBatchFixedWidth.FILE_CREATION_DATE.getStart(), FileachBatchFixedWidth.FILE_CREATION_DATE.getEnd()));
        header.getFileCreationTime(
                cleanStringData(line, FileachBatchFixedWidth.FILE_CREATION_TIME.getStart(), FileachBatchFixedWidth.FILE_CREATION_TIME.getEnd()));
        header.getFileIdModifier(cleanStringData(line, FileachBatchFixedWidth.FILE_ID_MODIFIER.getStart(), FileachBatchFixedWidth.FILE_ID_MODIFIER.getEnd()));
        header.getRecordSize(cleanStringData(line, FileachBatchFixedWidth.RECORD_SIZE.getStart(), FileachBatchFixedWidth.RECORD_SIZE.getEnd()));
        header.getBlockingFactor(cleanStringData(line, FileachBatchFixedWidth.BLOCKING_FACTOR.getStart(), FileachBatchFixedWidth.BLOCKING_FACTOR.getEnd()));
        header.getFormatCode(cleanStringData(line, FileachBatchFixedWidth.FORMAT_CODE.getStart(), FileachBatchFixedWidth.FORMAT_CODE.getEnd()));
        header.getImmediateDestinationName(cleanStringData(line, FileachBatchFixedWidth.IMMEDIATE_DESTINATION_NAME.getStart(),
                FileachBatchFixedWidth.IMMEDIATE_DESTINATION_NAME.getEnd()));
        header.getImmediateOriginName(
                cleanStringData(line, FileachBatchFixedWidth.IMMEDIATE_ORIGIN_NAME.getStart(), FileachBatchFixedWidth.IMMEDIATE_ORIGIN_NAME.getEnd()));
        header.getReferenceCode(cleanStringData(line, FileachBatchFixedWidth.REFERENCE_CODE.getStart(), FileachBatchFixedWidth.REFERENCE_CODE.getEnd()));

        return sb;

    }

    private StringBuilder convertFileControlRecord(final AchBatch achBatch, final List<PayrollPayment> payments) {
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_achBatch_RECORD.getRecordType());
        return sb;
    }

    private StringBuilder convertBatchachBatchRecord(final AchBatch achBatch, final List<PayrollPayment> payments) {
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_achBatch_RECORD.getRecordType());
        return sb;
    }

    private StringBuilder convertBatchControlRecord(final AchBatch achBatch, final List<PayrollPayment> payments) {
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_achBatch_RECORD.getRecordType());
        return sb;
    }

    private StringBuilder convertEnryDetailRecord(final AchBatch achBatch, final List<PayrollPayment> payments) {
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_achBatch_RECORD.getRecordType());
        return sb;
    }

    private StringBuilder convertAddendumRecord(final AchBatch achBatch, final List<PayrollPayment> payments) {
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_achBatch_RECORD.getRecordType());
        return sb;
    }

    // convert to factory pattern for converter
    private StringBuilder convertPaymentReturnAddendum(final AchAddendumRecord achAddendum) {
        StringBuilder sb = new StringBuilder(AchRecordType.FILE_achBatch_RECORD.getRecordType());
        return sb;
    }
}
