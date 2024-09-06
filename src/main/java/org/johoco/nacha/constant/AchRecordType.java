package org.johoco.nacha.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * The Nacha file format includes specifications for these record types
 */
public enum AchRecordType {
    FILE_HEADER_RECORD("1"),
    BATCH_HEADER_RECORD("5"),
    ENTRY_DETAIL_RECORD("6"),
    ADDENDUM("7"),
    BATCH_CONTROL_TOTAL("8"),
    FILE_CONTROL_RECORD("9");
//999 file padding
    private String recordType;
    
    AchRecordType(final String recordType) {
        this.recordType = recordType;
    }
    
    public String getRecordType() {
        return this.recordType;
    }

    public static AchRecordType recordTypeOf(final String recordTypeOf) throws Exception {
        for(AchRecordType t : values()) {
            if(StringUtils.equals(t.getRecordType(), recordTypeOf)) {
                return t;
            }
        }
        throw new Exception(String.format("AchRecordType of %s was not found", recordTypeOf));
    }
    
}
