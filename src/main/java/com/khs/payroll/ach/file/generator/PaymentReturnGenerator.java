package com.khs.payroll.ach.file.generator;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.khs.payroll.ach.file.record.AchAddendumRecord;
import com.khs.payroll.ach.file.record.AchBatchControlRecord;
import com.khs.payroll.ach.file.record.AchBatchHeaderRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.record.AchFileControlRecord;
import com.khs.payroll.ach.file.record.AchFileHeaderRecord;

public class PaymentReturnGenerator {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd");// YYMMDD
    // Time format HHMM 24 Hour

    public AchFileHeaderRecord generateFileHeader(final String line) {
        return null;
    }

    public AchEntryDetailRecord generateEntryDetail(final String line) {
        return null;
    }

    // different types to generate
    public AchAddendumRecord generateAddendum(final String line) {
        return null;
    }

    public AchBatchControlRecord generateBatchControlRecord(final String line) {
        return null;
    }

    public AchBatchHeaderRecord generateBatchHeaderRecord(final String line) {
        return null;
    }

    public AchFileControlRecord generateFileControlRecord(final String line) {
        return null;

    }

    // finish calculations, sums, and hash
    public void finalizeReturnFile() {

    }

    // alphanumeric field must be left-justified and post-padded with spaces
    private String padStringData(final String value, final int length) {
        if (value == null) {
            return String.format("%0" + length + "d", 0);
        }
        return StringUtils.leftPad(String.valueOf(value), length, ' ');
    }

    // A numeric field must be unsigned, right-justified and pre-padded with zeros.
    private String padNumeric(final Long value, final int length) {
        if (value == null) {
            return String.format("%0" + length + "d", 0);
        }
        return StringUtils.leftPad(String.valueOf(value), length, '0');
    }

    // numeric field must be unsigned, right-justified and pre-padded with zeros
    private String padInteger(final Integer value, final int length) {
        NumberUtils.isParsable(null);
        if (value == null) {
            return String.format("%0" + length + "d", 0);
        }
        return StringUtils.leftPad(String.valueOf(value), length, '0');
    }

    // numeric field must be unsigned, right-justified and pre-padded with zeros
    private String padInteger(final BigDecimal value, final int length) {
        if (value == null) {
            return String.format("%0" + length + "d", 0);
        }
        return StringUtils.leftPad(String.valueOf(value), length, '0');
    }
    
    // format to mmYYdd???????
    private String padInteger(final Date value, final int length) {
        if (value == null) {
            return String.format("%0" + length + "d", 0);
        }
        return StringUtils.leftPad(String.valueOf(value), length, '0');
    }
}
