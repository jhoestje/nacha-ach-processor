package com.khs.payroll.ach.file.parser;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.khs.payroll.ach.file.parser.fixedwidth.AddendumFixedWidth;
import com.khs.payroll.ach.file.parser.fixedwidth.BatchControlFixedWidth;
import com.khs.payroll.ach.file.parser.fixedwidth.BatchHeaderFixedWidth;
import com.khs.payroll.ach.file.parser.fixedwidth.EntryDetailFixedWidth;
import com.khs.payroll.ach.file.parser.fixedwidth.FileControlFixedWidth;
import com.khs.payroll.ach.file.parser.fixedwidth.FileHeaderFixedWidth;
import com.khs.payroll.ach.file.record.AchAddendumRecord;
import com.khs.payroll.ach.file.record.AchBatchControlRecord;
import com.khs.payroll.ach.file.record.AchBatchHeaderRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.record.AchFileControlRecord;
import com.khs.payroll.ach.file.record.AchFileHeaderRecord;
import com.khs.payroll.constant.ServiceClassCode;
import com.khs.payroll.constant.StandardEntryClassCode;
import com.khs.payroll.constant.TransactionCode;

public class AchFileLineParser {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd");//YYMMDD
    // Time format HHMM 24 Hour

    public AchFileHeaderRecord parseFileHeader(final String line) {
        AchFileHeaderRecord header = new AchFileHeaderRecord();
        header.setRecordTypeCode(cleanStringData(line, FileHeaderFixedWidth.RECORD_TYPE_CODE.getStart(), FileHeaderFixedWidth.RECORD_TYPE_CODE.getEnd()));
        header.setPriorityCode(cleanStringData(line, FileHeaderFixedWidth.PRIORITY_CODE.getStart(), FileHeaderFixedWidth.PRIORITY_CODE.getEnd()));
        header.setImmediateDestination(
                cleanStringData(line, FileHeaderFixedWidth.IMMEDIATE_DESTINATION.getStart(), FileHeaderFixedWidth.IMMEDIATE_DESTINATION.getEnd()));
        header.setImmediateOrigin(cleanStringData(line, FileHeaderFixedWidth.IMMEDIATE_ORIGIN.getStart(), FileHeaderFixedWidth.IMMEDIATE_ORIGIN.getEnd()));
        header.setFileCreationDate(cleanStringData(line, FileHeaderFixedWidth.FILE_CREATION_DATE.getStart(), FileHeaderFixedWidth.FILE_CREATION_DATE.getEnd()));
        header.setFileCreationTime(cleanStringData(line, FileHeaderFixedWidth.FILE_CREATION_TIME.getStart(), FileHeaderFixedWidth.FILE_CREATION_TIME.getEnd()));
        header.setFileIdModifier(cleanStringData(line, FileHeaderFixedWidth.FILE_ID_MODIFIER.getStart(), FileHeaderFixedWidth.FILE_ID_MODIFIER.getEnd()));
        header.setRecordSize(cleanStringData(line, FileHeaderFixedWidth.RECORD_SIZE.getStart(), FileHeaderFixedWidth.RECORD_SIZE.getEnd()));
        header.setBlockingFactor(cleanStringData(line, FileHeaderFixedWidth.BLOCKING_FACTOR.getStart(), FileHeaderFixedWidth.BLOCKING_FACTOR.getEnd()));
        header.setFormatCode(cleanStringData(line, FileHeaderFixedWidth.FORMAT_CODE.getStart(), FileHeaderFixedWidth.FORMAT_CODE.getEnd()));
        header.setImmediateDestinationName(
                cleanStringData(line, FileHeaderFixedWidth.IMMEDIATE_DESTINATION_NAME.getStart(), FileHeaderFixedWidth.IMMEDIATE_DESTINATION_NAME.getEnd()));
        header.setImmediateOriginName(
                cleanStringData(line, FileHeaderFixedWidth.IMMEDIATE_ORIGIN_NAME.getStart(), FileHeaderFixedWidth.IMMEDIATE_ORIGIN_NAME.getEnd()));
        header.setReferenceCode(cleanStringData(line, FileHeaderFixedWidth.REFERENCE_CODE.getStart(), FileHeaderFixedWidth.REFERENCE_CODE.getEnd()));
        return header;
    }

    // need to account for PPD and WEB Entry differences
    public AchEntryDetailRecord parseEntryDetail(final String line) throws ParseException {
        AchEntryDetailRecord entryDetail = new AchEntryDetailRecord();
        entryDetail
                .setRecordTypeCode(cleanStringData(line, EntryDetailFixedWidth.RECORD_TYPE_CODE.getStart(), EntryDetailFixedWidth.RECORD_TYPE_CODE.getEnd()));

        String transactionCodeString = cleanStringData(line, EntryDetailFixedWidth.TRANSACTION_CODE.getStart(),
                EntryDetailFixedWidth.TRANSACTION_CODE.getEnd());
        Optional<TransactionCode> transactionCodeOpt = TransactionCode.findByCode(transactionCodeString);
        if (transactionCodeOpt.isEmpty()) {
            throw new ParseException(String.format("Unknown Transaction code %s", transactionCodeString), BatchHeaderFixedWidth.SERVICE_CLASS_CODE.getStart());
        }
        entryDetail.setTransactionCode(transactionCodeOpt.get());
        entryDetail.setReceivingDFIIdentification(cleanStringData(line, EntryDetailFixedWidth.RECEIVING_DFI_IDENTIFICATION.getStart(),
                EntryDetailFixedWidth.RECEIVING_DFI_IDENTIFICATION.getEnd()));
        entryDetail.setCheckDigit(cleanInteger(line, EntryDetailFixedWidth.CHECK_DIGIT.getStart(), EntryDetailFixedWidth.CHECK_DIGIT.getEnd()));
        entryDetail.setDfiAccountNumber(
                cleanStringData(line, EntryDetailFixedWidth.DFI_ACCOUNT_NUMBER.getStart(), EntryDetailFixedWidth.DFI_ACCOUNT_NUMBER.getEnd()));
        entryDetail.setAmount(cleanBigDecimal(line, EntryDetailFixedWidth.AMOUNT.getStart(), EntryDetailFixedWidth.AMOUNT.getEnd()));
        entryDetail.setIdentificationNumber(cleanStringData(line, EntryDetailFixedWidth.INDIVIDUAL_IDENTIFICATION_NUMBER.getStart(),
                EntryDetailFixedWidth.INDIVIDUAL_IDENTIFICATION_NUMBER.getEnd()));
        entryDetail.setReceivingName(cleanStringData(line, EntryDetailFixedWidth.INDIVIDUAL_NAME.getStart(), EntryDetailFixedWidth.INDIVIDUAL_NAME.getEnd()));
        entryDetail.setDiscretionaryData(
                cleanStringData(line, EntryDetailFixedWidth.DISCRETIONARY_DATA.getStart(), EntryDetailFixedWidth.DISCRETIONARY_DATA.getEnd()));
        entryDetail.setAddendaRecordIndicator(
                cleanInteger(line, EntryDetailFixedWidth.ADDENDA_RECORD_INDICATOR.getStart(), EntryDetailFixedWidth.ADDENDA_RECORD_INDICATOR.getEnd()));
        entryDetail.setTraceNumber(cleanLong(line, EntryDetailFixedWidth.TRACE_NUMBER.getStart(), EntryDetailFixedWidth.TRACE_NUMBER.getEnd()));
        return entryDetail;
    }

    public AchAddendumRecord parseAddendum(final String line) {
        AchAddendumRecord addendum = new AchAddendumRecord();
        addendum.setRecordTypeCode(cleanStringData(line, AddendumFixedWidth.RECORD_TYPE_CODE.getStart(), AddendumFixedWidth.RECORD_TYPE_CODE.getEnd()));
        addendum.setAddendaTypeCode(cleanStringData(line, AddendumFixedWidth.ADDENDA_TYPE_CODE.getStart(), AddendumFixedWidth.ADDENDA_TYPE_CODE.getEnd()));
        addendum.setPaymentRelatedInfo(
                cleanStringData(line, AddendumFixedWidth.PAYMENT_INFORMATION.getStart(), AddendumFixedWidth.PAYMENT_INFORMATION.getEnd()));
        addendum.setAddendaSequenceNumber(
                cleanInteger(line, AddendumFixedWidth.ADDENDA_SEQUENCE_NUMBER.getStart(), AddendumFixedWidth.ADDENDA_SEQUENCE_NUMBER.getEnd()));
        addendum.setEntryDetailSequenceNumber(
                cleanInteger(line, AddendumFixedWidth.ENTRY_DETAIL_SEQUENCE_NUMBER.getStart(), AddendumFixedWidth.ENTRY_DETAIL_SEQUENCE_NUMBER.getEnd()));
        return addendum;
    }

    public AchBatchControlRecord parseBatchControlRecord(final String line) throws ParseException {
        AchBatchControlRecord batchControl = new AchBatchControlRecord();
        batchControl
                .setRecordTypeCode(cleanStringData(line, BatchControlFixedWidth.RECORD_TYPE_CODE.getStart(), BatchControlFixedWidth.RECORD_TYPE_CODE.getEnd()));
        String serviceClassCodeString = cleanStringData(line, BatchControlFixedWidth.SERVICE_CLASS_CODE.getStart(),
                BatchControlFixedWidth.SERVICE_CLASS_CODE.getEnd());
        Optional<ServiceClassCode> serviceClassCodeOpt = ServiceClassCode.findByCode(serviceClassCodeString);
        if (serviceClassCodeOpt.isEmpty()) {
            throw new ParseException(String.format("Unknown Service Class code %s", serviceClassCodeString),
                    BatchControlFixedWidth.SERVICE_CLASS_CODE.getStart());
        }
        batchControl.setServiceClassCode(serviceClassCodeOpt.get());
        batchControl.setEntryAddendaCount(
                cleanInteger(line, BatchControlFixedWidth.ENTRY_ADDENDA_COUNT.getStart(), BatchControlFixedWidth.ENTRY_ADDENDA_COUNT.getEnd()));
        batchControl.setEntryHash(cleanInteger(line, BatchControlFixedWidth.ENTRY_HASH.getStart(), BatchControlFixedWidth.ENTRY_HASH.getEnd()));
        batchControl.setTotalDebitAmount(
                cleanBigDecimal(line, BatchControlFixedWidth.TOTAL_DEBIT_AMOUNT.getStart(), BatchControlFixedWidth.TOTAL_DEBIT_AMOUNT.getEnd()));
        batchControl.setTotalCreditAmount(
                cleanBigDecimal(line, BatchControlFixedWidth.TOTAL_CREDIT_AMOUNT.getStart(), BatchControlFixedWidth.TOTAL_CREDIT_AMOUNT.getEnd()));
        batchControl.setCompanyIdentification(
                cleanStringData(line, BatchControlFixedWidth.COMPANY_IDENTIFICATION.getStart(), BatchControlFixedWidth.COMPANY_IDENTIFICATION.getEnd()));
        batchControl.setMessageAuthenticationCode(cleanStringData(line, BatchControlFixedWidth.MESSAGE_AUTHENTICATION_CODE.getStart(),
                BatchControlFixedWidth.MESSAGE_AUTHENTICATION_CODE.getEnd()));
        batchControl.setReserved(cleanStringData(line, BatchControlFixedWidth.RESERVED.getStart(), BatchControlFixedWidth.RESERVED.getEnd()));
        batchControl.setOriginatingDFIIdentification(cleanInteger(line, BatchControlFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getStart(),
                BatchControlFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getEnd()));
        batchControl.setBatchNumber(cleanInteger(line, BatchControlFixedWidth.BATCH_NUMBER.getStart(), BatchControlFixedWidth.BATCH_NUMBER.getEnd()));
        return batchControl;
    }

    public AchBatchHeaderRecord parseBatchHeaderRecord(final String line) throws ParseException {
        AchBatchHeaderRecord batchHeader = new AchBatchHeaderRecord();
        batchHeader
                .setRecordTypeCode(cleanStringData(line, BatchHeaderFixedWidth.RECORD_TYPE_CODE.getStart(), BatchHeaderFixedWidth.RECORD_TYPE_CODE.getEnd()));
        String serviceClassCodeString = cleanStringData(line, BatchHeaderFixedWidth.SERVICE_CLASS_CODE.getStart(),
                BatchHeaderFixedWidth.SERVICE_CLASS_CODE.getEnd());
        Optional<ServiceClassCode> serviceClassCodeOpt = ServiceClassCode.findByCode(serviceClassCodeString);
        if (serviceClassCodeOpt.isEmpty()) {
            throw new ParseException(String.format("Unknown Service Class code %s", serviceClassCodeString),
                    BatchHeaderFixedWidth.SERVICE_CLASS_CODE.getStart());
        }
        batchHeader.setServiceClassCode(serviceClassCodeOpt.get());
        batchHeader.setCompanyName(cleanStringData(line, BatchHeaderFixedWidth.COMPANY_NAME.getStart(), BatchHeaderFixedWidth.COMPANY_NAME.getEnd()));
        batchHeader.setCompanyDiscretionaryData(
                cleanStringData(line, BatchHeaderFixedWidth.COMPANY_DISCRETIONARY_DATA.getStart(), BatchHeaderFixedWidth.COMPANY_DISCRETIONARY_DATA.getEnd()));
        batchHeader.setCompanyIdentification(
                cleanStringData(line, BatchHeaderFixedWidth.COMPANY_IDENTIFICATION.getStart(), BatchHeaderFixedWidth.COMPANY_IDENTIFICATION.getEnd()));

        String secCodeString = cleanStringData(line, BatchHeaderFixedWidth.STANDARD_ENTRY_CLASS_CODE.getStart(),
                BatchHeaderFixedWidth.STANDARD_ENTRY_CLASS_CODE.getEnd());
        Optional<StandardEntryClassCode> secCodeOpt = StandardEntryClassCode.findByCode(secCodeString);
        if (secCodeOpt.isEmpty()) {
            throw new ParseException(String.format("Unknown Stand Entry Class code %s", secCodeString),
                    BatchHeaderFixedWidth.STANDARD_ENTRY_CLASS_CODE.getStart());
        }
        batchHeader.setStandardEntryClassCode(secCodeOpt.get());

        batchHeader.setCompanyEntryDescription(
                cleanStringData(line, BatchHeaderFixedWidth.COMPANY_ENTRY_DESCRIPTION.getStart(), BatchHeaderFixedWidth.COMPANY_ENTRY_DESCRIPTION.getEnd()));
        batchHeader.setCompanyDescriptiveDate(
                cleanStringData(line, BatchHeaderFixedWidth.COMPANY_DESCRIPTIVE_DATE.getStart(), BatchHeaderFixedWidth.COMPANY_DESCRIPTIVE_DATE.getEnd()));
        // check date format
        batchHeader.setEffectiveEntryDate(LocalDate.parse(
                cleanStringData(line, BatchHeaderFixedWidth.EFFECTIVE_ENTRY_DATE.getStart(), BatchHeaderFixedWidth.EFFECTIVE_ENTRY_DATE.getEnd()), dateFormat));

        String settlementDateString = cleanStringData(line, BatchHeaderFixedWidth.SETTLEMENT_DATE_JULIAN.getStart(),
                BatchHeaderFixedWidth.SETTLEMENT_DATE_JULIAN.getEnd());
        if (StringUtils.isNotBlank(settlementDateString)) {
            batchHeader.setSettlementDate(LocalDate.parse(settlementDateString, dateFormat));
        }
        batchHeader.setOriginatorStatusCode(
                cleanStringData(line, BatchHeaderFixedWidth.ORIGINATOR_STATUS_CODE.getStart(), BatchHeaderFixedWidth.ORIGINATOR_STATUS_CODE.getEnd()));
        batchHeader.setOriginatingDFIIdentification(cleanStringData(line, BatchHeaderFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getStart(),
                BatchHeaderFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getEnd()));
        batchHeader.setBatchNumber(cleanInteger(line, BatchHeaderFixedWidth.BATCH_NUMBER.getStart(), BatchHeaderFixedWidth.BATCH_NUMBER.getEnd()));
        return batchHeader;
    }

    public AchFileControlRecord parseFileControlRecord(final String line) {
        AchFileControlRecord fileControl = new AchFileControlRecord();
        fileControl
                .setRecordTypeCode(cleanStringData(line, FileControlFixedWidth.RECORD_TYPE_CODE.getStart(), FileControlFixedWidth.RECORD_TYPE_CODE.getEnd()));
        fileControl.setBatchCount(cleanInteger(line, FileControlFixedWidth.BATCH_COUNT.getStart(), FileControlFixedWidth.BATCH_COUNT.getEnd()));
        fileControl.setBlockCount(cleanInteger(line, FileControlFixedWidth.BLOCK_COUNT.getStart(), FileControlFixedWidth.BLOCK_COUNT.getEnd()));
        fileControl.setEntryAddendaCount(
                cleanInteger(line, FileControlFixedWidth.ENTRY_ADDENDA_COUNT.getStart(), FileControlFixedWidth.ENTRY_ADDENDA_COUNT.getEnd()));
        fileControl.setEntryHash(cleanLong(line, FileControlFixedWidth.ENTRY_HASH.getStart(), FileControlFixedWidth.ENTRY_HASH.getEnd()));
        fileControl.setTotalDebitAmount(cleanBigDecimal(line, FileControlFixedWidth.TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT.getStart(),
                FileControlFixedWidth.TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT.getEnd()));
        fileControl.setTotalCreditAmount(cleanBigDecimal(line, FileControlFixedWidth.TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT.getStart(),
                FileControlFixedWidth.TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT.getEnd()));

        return fileControl;
    }

    // alphanumeric field must be left-justified and post-padded with spaces
    private String cleanStringData(final String line, final int start, final int end) {
        return line.substring(start, end).stripTrailing();
    }

    // numeric field must be unsigned, right-justified and pre-padded with zeros
    private BigDecimal cleanBigDecimal(final String line, final int start, final int end) {
        return new BigDecimal(line.substring(start, end).trim()).divide(new BigDecimal(100));
    }

    // numeric field must be unsigned, right-justified and pre-padded with zeros
    private long cleanLong(final String line, final int start, final int end) {
        return Long.parseLong(line.substring(start, end).trim());
    }

    // numeric field must be unsigned, right-justified and pre-padded with zeros
    private int cleanInteger(final String line, final int start, final int end) {
        return Integer.parseInt(line.substring(start, end).trim());
    }
}
