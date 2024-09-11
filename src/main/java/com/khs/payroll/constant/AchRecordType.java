package com.khs.payroll.constant;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * The Nacha file format includes specifications for these record types
 */
public enum AchRecordType {
    FILE_HEADER_RECORD("1"),
    BATCH_HEADER_RECORD("5"),
    ENTRY_DETAIL_RECORD("6"),
    ADDENDUM("7"),
    BATCH_CONTROL_RECORD("8"),
    FILE_CONTROL_RECORD("9");
    //9999.. file padding
    private String recordType;
    
    AchRecordType(final String recordType) {
        this.recordType = recordType;
    }
    
    public String getRecordType() {
        return this.recordType;
    }

    public static Optional<AchRecordType> recordTypeOf(final String recordTypeOf) throws Exception {
        for(AchRecordType t : values()) {
            if(StringUtils.equals(t.getRecordType(), recordTypeOf)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }
    
}
