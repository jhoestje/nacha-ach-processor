package org.johoco.nacha.parser;

import org.johoco.nacha.domain.AchEntryDetail;
import org.johoco.nacha.domain.AchHeader;

import static org.johoco.nacha.constant.AchHeaderFixedWidth.*;
import static org.johoco.nacha.constant.AchEntryDetailFixedWidth.*;

public class AchFileLineParser {
    
    public static AchHeader parseHeader(final String line) {
        AchHeader header = new AchHeader();
        header.setRecordTypeCode(line.substring(RECORD_TYPE_CODE.getStart(), RECORD_TYPE_CODE.getEnd()));
        header.setPriorityCode(line.substring(PRIORITY_CODE.getStart(), PRIORITY_CODE.getEnd()));
        header.setImmediateDestination(line.substring(IMMEDIATE_DESTINATION.getStart(), IMMEDIATE_DESTINATION.getEnd()));
        header.setImmediateOrigin(line.substring(IMMEDIATE_ORIGIN.getStart(), IMMEDIATE_ORIGIN.getEnd()));

        return header;
    }

    public static AchEntryDetail parseEntryDetail(final String line) {
        AchEntryDetail entryDetail = new AchEntryDetail();
        entryDetail.setTransactionCode(line.substring(TRANSACTION_CODE.getStart(), TRANSACTION_CODE.getEnd()));
        entryDetail.setReceivingDFI(line.substring(RECEIVING_DFI.getStart(), RECEIVING_DFI.getEnd()));
        entryDetail.setCheckDigit(line.substring(CHECK_DIGIT.getStart(), CHECK_DIGIT.getEnd()));
        entryDetail.setAccountNumber(line.substring(ACCOUNT_NUMBER.getStart(), ACCOUNT_NUMBER.getEnd()));

        return entryDetail;
    }
}
