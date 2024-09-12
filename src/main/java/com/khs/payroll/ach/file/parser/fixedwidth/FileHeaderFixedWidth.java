package com.khs.payroll.ach.file.parser.fixedwidth;

import lombok.Getter;

@Getter
public enum FileHeaderFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    PRIORITY_CODE(1, 3),
    IMMEDIATE_DESTINATION(4, 13), // first character is a blank; don't include
    IMMEDIATE_ORIGIN(14, 23), // first character is a blank; don't include
    FILE_CREATION_DATE(23, 29),
    FILE_CREATION_TIME(29, 33),
    FILE_ID_MODIFIER(33, 34),
    RECORD_SIZE(34, 37),
    BLOCKING_FACTOR(37, 39),
    FORMAT_CODE(39, 40),
    IMMEDIATE_DESTINATION_NAME(40, 63),
    IMMEDIATE_ORIGIN_NAME(63, 86),
    REFERENCE_CODE(86, 94);

    private int start;
    private int end;

    FileHeaderFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
