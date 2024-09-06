package org.johoco.nacha.parser;

import org.johoco.nacha.constant.AddendumFixedWidth;
import org.johoco.nacha.constant.BatchControlFixedWidth;
import org.johoco.nacha.constant.BatchHeaderFixedWidth;
import org.johoco.nacha.constant.EntryDetailFixedWidth;
import org.johoco.nacha.constant.FileControlFixedWidth;
import org.johoco.nacha.constant.FileHeaderFixedWidth;
import org.johoco.nacha.domain.AchAddendumRecord;
import org.johoco.nacha.domain.AchBatchControlRecord;
import org.johoco.nacha.domain.AchBatchHeaderRecord;
import org.johoco.nacha.domain.AchEntryDetail;
import org.johoco.nacha.domain.AchFileControlRecord;
import org.johoco.nacha.domain.AchFileHeaderRecord;
import org.springframework.stereotype.Service;

@Service
public class AchFileLineParser {

    public AchFileHeaderRecord parseFileHeader(final String line) {
        AchFileHeaderRecord header = new AchFileHeaderRecord();
        header.setRecordTypeCode(line.substring(FileHeaderFixedWidth.RECORD_TYPE_CODE.getStart(), FileHeaderFixedWidth.RECORD_TYPE_CODE.getEnd()));
        header.setPriorityCode(line.substring(FileHeaderFixedWidth.PRIORITY_CODE.getStart(), FileHeaderFixedWidth.PRIORITY_CODE.getEnd()));
        header.setImmediateDestination(
                line.substring(FileHeaderFixedWidth.IMMEDIATE_DESTINATION.getStart(), FileHeaderFixedWidth.IMMEDIATE_DESTINATION.getEnd()));
        header.setImmediateOrigin(line.substring(FileHeaderFixedWidth.IMMEDIATE_ORIGIN.getStart(), FileHeaderFixedWidth.IMMEDIATE_ORIGIN.getEnd()));
        header.setFileCreationDate(line.substring(FileHeaderFixedWidth.FILE_CREATION_DATE.getStart(), FileHeaderFixedWidth.FILE_CREATION_DATE.getEnd()));
        header.setFileCreationTime(line.substring(FileHeaderFixedWidth.FILE_CREATION_TIME.getStart(), FileHeaderFixedWidth.FILE_CREATION_TIME.getEnd()));
        header.setFileIdModifier(line.substring(FileHeaderFixedWidth.FILE_ID_MODIFIER.getStart(), FileHeaderFixedWidth.FILE_ID_MODIFIER.getEnd()));
        header.setRecordSize(line.substring(FileHeaderFixedWidth.RECORD_SIZE.getStart(), FileHeaderFixedWidth.RECORD_SIZE.getEnd()));
        header.setBlockingFactor(line.substring(FileHeaderFixedWidth.BLOCKING_FACTOR.getStart(), FileHeaderFixedWidth.BLOCKING_FACTOR.getEnd()));
        header.setFormatCode(line.substring(FileHeaderFixedWidth.FORMAT_CODE.getStart(), FileHeaderFixedWidth.FORMAT_CODE.getEnd()));
        header.setImmediateDestinationName(
                line.substring(FileHeaderFixedWidth.IMMEDIATE_DESTINATION_NAME.getStart(), FileHeaderFixedWidth.IMMEDIATE_DESTINATION_NAME.getEnd()));
        header.setImmediateOriginName(
                line.substring(FileHeaderFixedWidth.IMMEDIATE_ORIGIN_NAME.getStart(), FileHeaderFixedWidth.IMMEDIATE_ORIGIN_NAME.getEnd()));
        header.setReferenceCode(line.substring(FileHeaderFixedWidth.REFERENCE_CODE.getStart(), FileHeaderFixedWidth.REFERENCE_CODE.getEnd()));
        return header;
    }

    // need to account for PPD and WEB Entry differences
    public AchEntryDetail parseEntryDetail(final String line) {
        AchEntryDetail entryDetail = new AchEntryDetail();
        entryDetail.setRecordTypeCode(line.substring(EntryDetailFixedWidth.RECORD_TYPE_CODE.getStart(), EntryDetailFixedWidth.RECORD_TYPE_CODE.getEnd()));
        entryDetail.setTransactionCode(line.substring(EntryDetailFixedWidth.TRANSACTION_CODE.getStart(), EntryDetailFixedWidth.TRANSACTION_CODE.getEnd()));
        entryDetail.setReceivingDFIIdentification(
                line.substring(EntryDetailFixedWidth.RECEIVING_DFI_IDENTIFICATION.getStart(), EntryDetailFixedWidth.RECEIVING_DFI_IDENTIFICATION.getEnd()));
        entryDetail.setCheckDigit(line.substring(EntryDetailFixedWidth.CHECK_DIGIT.getStart(), EntryDetailFixedWidth.CHECK_DIGIT.getEnd()));
        entryDetail.setDFIAccountNumber(line.substring(EntryDetailFixedWidth.DFI_ACCOUNT_NUMBER.getStart(), EntryDetailFixedWidth.DFI_ACCOUNT_NUMBER.getEnd()));
        entryDetail.setAmount(line.substring(EntryDetailFixedWidth.AMOUNT.getStart(), EntryDetailFixedWidth.AMOUNT.getEnd()));
        entryDetail.setIdentificationNumber(line.substring(EntryDetailFixedWidth.INDIVIDUAL_IDENTIFICATION_NUMBER.getStart(),
                EntryDetailFixedWidth.INDIVIDUAL_IDENTIFICATION_NUMBER.getEnd()));
        entryDetail.setReceivingName(line.substring(EntryDetailFixedWidth.INDIVIDUAL_NAME.getStart(), EntryDetailFixedWidth.INDIVIDUAL_NAME.getEnd()));
        entryDetail
                .setDiscretionaryData(line.substring(EntryDetailFixedWidth.DISCRETIONARY_DATA.getStart(), EntryDetailFixedWidth.DISCRETIONARY_DATA.getEnd()));
        entryDetail.setAddendaRecordIndicator(
                line.substring(EntryDetailFixedWidth.ADDENDA_RECORD_INDICATOR.getStart(), EntryDetailFixedWidth.ADDENDA_RECORD_INDICATOR.getEnd()));
        entryDetail.setTraceNumber(line.substring(EntryDetailFixedWidth.TRACE_NUMBER.getStart(), EntryDetailFixedWidth.TRACE_NUMBER.getEnd()));
        return entryDetail;
    }

    public AchAddendumRecord parseAddendum(final String line) {
        AchAddendumRecord addendum = new AchAddendumRecord();
        addendum.setRecordTypeCode(line.substring(AddendumFixedWidth.RECORD_TYPE_CODE.getStart(), AddendumFixedWidth.RECORD_TYPE_CODE.getEnd()));
        addendum.setAddendaTypeCode(line.substring(AddendumFixedWidth.ADDENDA_TYPE_CODE.getStart(), AddendumFixedWidth.ADDENDA_TYPE_CODE.getEnd()));
        addendum.setPaymentRelatedInfo(line.substring(AddendumFixedWidth.PAYMENT_INFORMATION.getStart(), AddendumFixedWidth.PAYMENT_INFORMATION.getEnd()));
        addendum.setAddendaSequenceNumber(
                line.substring(AddendumFixedWidth.ADDENDA_SEQUENCE_NUMBER.getStart(), AddendumFixedWidth.ADDENDA_SEQUENCE_NUMBER.getEnd()));
        addendum.setEntryDetailSequenceNumber(
                line.substring(AddendumFixedWidth.ENTRY_DETAIL_SEQUENCE_NUMBER.getStart(), AddendumFixedWidth.ENTRY_DETAIL_SEQUENCE_NUMBER.getEnd()));
        return addendum;
    }

    public AchBatchControlRecord parseBatchControlRecord(final String line) {
        AchBatchControlRecord batchControl = new AchBatchControlRecord();
        batchControl.setRecordTypeCode(line.substring(BatchControlFixedWidth.RECORD_TYPE_CODE.getStart(), BatchControlFixedWidth.RECORD_TYPE_CODE.getEnd()));
        batchControl
                .setServiceClassCode(line.substring(BatchControlFixedWidth.SERVICE_CLASS_CODE.getStart(), BatchControlFixedWidth.SERVICE_CLASS_CODE.getEnd()));
        batchControl.setEntryAddendaCount(
                line.substring(BatchControlFixedWidth.ENTRY_ADDENDA_COUNT.getStart(), BatchControlFixedWidth.ENTRY_ADDENDA_COUNT.getEnd()));
        batchControl.setEntryHash(line.substring(BatchControlFixedWidth.ENTRY_HASH.getStart(), BatchControlFixedWidth.ENTRY_HASH.getEnd()));
        batchControl
                .setTotalDebitAmount(line.substring(BatchControlFixedWidth.TOTAL_DEBIT_AMOUNT.getStart(), BatchControlFixedWidth.TOTAL_DEBIT_AMOUNT.getEnd()));
        batchControl.setTotalCreditAmount(
                line.substring(BatchControlFixedWidth.TOTAL_CREDIT_AMOUNT.getStart(), BatchControlFixedWidth.TOTAL_CREDIT_AMOUNT.getEnd()));
        batchControl.setCompanyIdentification(
                line.substring(BatchControlFixedWidth.COMPANY_IDENTIFICATION.getStart(), BatchControlFixedWidth.COMPANY_IDENTIFICATION.getEnd()));
        batchControl.setMessageAuthenticationCode(
                line.substring(BatchControlFixedWidth.MESSAGE_AUTHENTICATION_CODE.getStart(), BatchControlFixedWidth.MESSAGE_AUTHENTICATION_CODE.getEnd()));
        batchControl.setReserved(line.substring(BatchControlFixedWidth.RESERVED.getStart(), BatchControlFixedWidth.RESERVED.getEnd()));
        batchControl.setOriginatingDFIIdentification(line.substring(BatchControlFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getStart(),
                BatchControlFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getEnd()));
        batchControl.setBatchNumber(line.substring(BatchControlFixedWidth.BATCH_NUMBER.getStart(), BatchControlFixedWidth.BATCH_NUMBER.getEnd()));
        return batchControl;
    }

    public AchBatchHeaderRecord parseBatchHeaderRecord(final String line) {
        AchBatchHeaderRecord batchHeader = new AchBatchHeaderRecord();
        batchHeader.setRecordTypeCode(line.substring(BatchHeaderFixedWidth.RECORD_TYPE_CODE.getStart(), BatchHeaderFixedWidth.RECORD_TYPE_CODE.getEnd()));
        batchHeader.setServiceClassCode(line.substring(BatchHeaderFixedWidth.SERVICE_CLASS_CODE.getStart(), BatchHeaderFixedWidth.SERVICE_CLASS_CODE.getEnd()));
        batchHeader.setCompanyName(line.substring(BatchHeaderFixedWidth.COMPANY_NAME.getStart(), BatchHeaderFixedWidth.COMPANY_NAME.getEnd()));
        batchHeader.setCompanyDiscretionaryData(
                line.substring(BatchHeaderFixedWidth.COMPANY_DISCRETIONARY_DATA.getStart(), BatchHeaderFixedWidth.COMPANY_DISCRETIONARY_DATA.getEnd()));
        batchHeader.setCompanyIdentification(
                line.substring(BatchHeaderFixedWidth.COMPANY_IDENTIFICATION.getStart(), BatchHeaderFixedWidth.COMPANY_IDENTIFICATION.getEnd()));
        batchHeader.setStandardEntryClassCode(
                line.substring(BatchHeaderFixedWidth.STANDARD_ENTRY_CLASS_CODE.getStart(), BatchHeaderFixedWidth.STANDARD_ENTRY_CLASS_CODE.getEnd()));
        batchHeader.setCompanyEntryDescription(
                line.substring(BatchHeaderFixedWidth.COMPANY_ENTRY_DESCRIPTION.getStart(), BatchHeaderFixedWidth.COMPANY_ENTRY_DESCRIPTION.getEnd()));
        batchHeader.setCompanyDescriptiveDate(
                line.substring(BatchHeaderFixedWidth.COMPANY_DESCRIPTIVE_DATE.getStart(), BatchHeaderFixedWidth.COMPANY_DESCRIPTIVE_DATE.getEnd()));
        batchHeader.setEffectiveEntryDate(
                line.substring(BatchHeaderFixedWidth.EFFECTIVE_ENTRY_DATE.getStart(), BatchHeaderFixedWidth.EFFECTIVE_ENTRY_DATE.getEnd()));
        batchHeader.setSettlementDate(
                line.substring(BatchHeaderFixedWidth.SETTLEMENT_DATE_JULIAN.getStart(), BatchHeaderFixedWidth.SETTLEMENT_DATE_JULIAN.getEnd()));
        batchHeader.setOriginatorStatusCode(
                line.substring(BatchHeaderFixedWidth.ORIGINATOR_STATUS_CODE.getStart(), BatchHeaderFixedWidth.ORIGINATOR_STATUS_CODE.getEnd()));
        batchHeader.setOriginatingDFIIdentification(
                line.substring(BatchHeaderFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getStart(), BatchHeaderFixedWidth.ORIGINATING_DFI_IDENTIFICATION.getEnd()));
        batchHeader.setBatchNumber(line.substring(BatchHeaderFixedWidth.BATCH_NUMBER.getStart(), BatchHeaderFixedWidth.BATCH_NUMBER.getEnd()));
        return batchHeader;
    }

    public AchFileControlRecord parseFileControlRecord(final String line) {
        AchFileControlRecord fileControl = new AchFileControlRecord();
        fileControl.setRecordTypeCode(line.substring(FileControlFixedWidth.RECORD_TYPE_CODE.getStart(), FileControlFixedWidth.RECORD_TYPE_CODE.getEnd()));
        fileControl.setBatchCount(Integer.parseInt(line.substring(FileControlFixedWidth.BATCH_COUNT.getStart(), FileControlFixedWidth.BATCH_COUNT.getEnd()).trim()));
        fileControl.setBlockCount(Integer.parseInt(line.substring(FileControlFixedWidth.BLOCK_COUNT.getStart(), FileControlFixedWidth.BLOCK_COUNT.getEnd()).trim()));
        fileControl.setEntryAddendaCount(Integer.parseInt(line.substring(FileControlFixedWidth.ENTRY_ADDENDA_COUNT.getStart(), FileControlFixedWidth.ENTRY_ADDENDA_COUNT.getEnd()).trim()));
        fileControl.setEntryHash(Long.parseLong(line.substring(FileControlFixedWidth.ENTRY_HASH.getStart(), FileControlFixedWidth.ENTRY_HASH.getEnd()).trim()));
        fileControl.setTotalDebitAmount(Double.parseDouble(line.substring(FileControlFixedWidth.TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT.getStart(), FileControlFixedWidth.TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT.getEnd()).trim()) / 100);
        fileControl.setTotalCreditAmount(Double.parseDouble(line.substring(FileControlFixedWidth.TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT.getStart(), FileControlFixedWidth.TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT.getEnd()).trim()) / 100);
     
        return fileControl;
    }
}
