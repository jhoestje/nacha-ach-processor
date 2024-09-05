package org.johoco.nacha.parser;

import org.johoco.nacha.constant.EntryDetailFixedWidth;
import org.johoco.nacha.constant.FileHeaderFixedWidth;
import org.johoco.nacha.domain.AchAddendum;
import org.johoco.nacha.domain.AchBatchControlRecord;
import org.johoco.nacha.domain.AchBatchHeaderRecord;
import org.johoco.nacha.domain.AchEntryDetail;
import org.johoco.nacha.domain.AchFileControlRecord;
import org.johoco.nacha.domain.AchHeader;

import static org.johoco.nacha.constant.FileHeaderFixedWidth.*;

import java.math.BigDecimal;

import static org.johoco.nacha.constant.EntryDetailFixedWidth.*;

public class AchFileLineParser {

    public static AchHeader parseHeader(final String line) {
        AchHeader header = new AchHeader();
        header.setRecordTypeCode(line.substring(FileHeaderFixedWidth.RECORD_TYPE_CODE.getStart(), FileHeaderFixedWidth.RECORD_TYPE_CODE.getEnd()));
        header.setPriorityCode(line.substring(PRIORITY_CODE.getStart(), PRIORITY_CODE.getEnd()));
        header.setImmediateDestination(line.substring(IMMEDIATE_DESTINATION.getStart(), IMMEDIATE_DESTINATION.getEnd()));
        header.setImmediateOrigin(line.substring(IMMEDIATE_ORIGIN.getStart(), IMMEDIATE_ORIGIN.getEnd()));

        return header;
    }

    public static AchEntryDetail parseEntryDetail(final String line) {
        AchEntryDetail entryDetail = new AchEntryDetail();
        entryDetail.setRecordTypeCode(line.substring(EntryDetailFixedWidth.RECORD_TYPE_CODE.getStart(), EntryDetailFixedWidth.RECORD_TYPE_CODE.getEnd()));
        entryDetail.setTransactionCode(line.substring(TRANSACTION_CODE.getStart(), TRANSACTION_CODE.getEnd()));
        entryDetail.setReceivingDFI(line.substring(RECEIVING_DFI.getStart(), RECEIVING_DFI.getEnd()));
        entryDetail.setCheckDigit(line.substring(CHECK_DIGIT.getStart(), CHECK_DIGIT.getEnd()));
        entryDetail.setDFIAccountNumber(line.substring(12, 29).trim());
        entryDetail.setAmount(Long.parseLong(line.substring(29, 39).trim()));
        entryDetail.setIndividualIDNumber(line.substring(39, 54).trim());
        entryDetail.setIndividualName(line.substring(54, 76).trim());
        entryDetail.setDiscretionaryData(line.substring(76, 78).trim());
        entryDetail.setAddendaRecordIndicator(line.substring(78, 79));
        entryDetail.setTraceNumber(line.substring(79, 94));

        return entryDetail;
    }

    public static AchAddendum parseAddendum(final String line) {
        AchAddendum addendum = new AchAddendum();
        addendum.setRecordTypeCode(line.substring(0, 1));
        addendum.setAddendaTypeCode(line.substring(1, 3));
        addendum.setPaymentRelatedInfo(line.substring(3, 83).trim());
        addendum.setAddendaSequenceNumber(line.substring(83, 87));
        addendum.setEntryDetailSequenceNumber(line.substring(87, 94));
        return addendum;
    }

    public static AchBatchControlRecord parseBatchControlTotal(String line) {
        AchBatchControlRecord batchControl = new AchBatchControlRecord();
        batchControl.setRecordTypeCode(line.substring(0, 1));
        batchControl.setServiceClassCode(line.substring(1, 4));
        batchControl.setEntryAddendaCount(Integer.parseInt(line.substring(4, 10).trim()));
        batchControl.setEntryHash(Long.parseLong(line.substring(10, 20).trim()));
        batchControl.setTotalDebitAmount(Long.parseLong(line.substring(20, 32).trim()));
        batchControl.setTotalCreditAmount(Long.parseLong(line.substring(32, 44).trim()));
        batchControl.setCompanyIdentification(line.substring(44, 54));
        batchControl.setOriginatingDFI(line.substring(54, 62));
        batchControl.setBatchNumber(line.substring(62, 70));
        return batchControl;
    }

    public static AchBatchHeaderRecord parseBatchHeaderRecord(String line) {
        AchBatchHeaderRecord batchHeader = new AchBatchHeaderRecord();
        batchHeader.setRecordTypeCode(line.substring(0, 1));
        batchHeader.setServiceClassCode(line.substring(1, 4));
        batchHeader.setCompanyName(line.substring(4, 20).trim());
        batchHeader.setCompanyIdentification(line.substring(20, 30));
        batchHeader.setStandardEntryClassCode(line.substring(30, 33));
        batchHeader.setCompanyEntryDescription(line.substring(33, 43));
        batchHeader.setCompanyDescriptiveDate(line.substring(43, 49));
        batchHeader.setEffectiveEntryDate(line.substring(49, 55));
        batchHeader.setSettlementDate(line.substring(55, 58));
        batchHeader.setOriginatorStatusCode(line.substring(58, 59));
        batchHeader.setOriginatingDFI(line.substring(59, 67));
        batchHeader.setBatchNumber(line.substring(67, 74));
        return batchHeader;
    }

    public static AchFileControlRecord parseFileControlRecord(String line) {
        AchFileControlRecord fileControl = new AchFileControlRecord();
        fileControl.setRecordTypeCode(line.substring(0, 1));
        fileControl.setBatchCount(Integer.parseInt(line.substring(1, 7).trim()));
        fileControl.setBlockCount(Integer.parseInt(line.substring(7, 13).trim()));
        fileControl.setEntryAddendaCount(Integer.parseInt(line.substring(13, 21).trim()));
        fileControl.setEntryHash(Long.parseLong(line.substring(21, 31).trim()));
        fileControl.setTotalDebitAmount(Long.parseLong(line.substring(31, 43).trim()));
        fileControl.setTotalCreditAmount(Long.parseLong(line.substring(43, 55).trim()));

        return fileControl;
    }
}
