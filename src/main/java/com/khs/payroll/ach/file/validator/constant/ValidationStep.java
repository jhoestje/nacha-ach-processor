package com.khs.payroll.ach.file.validator.constant;

public enum ValidationStep {
    FILE_AMOUNTS,
    BATCH_AMOUNTS,
    BATCH_ENTRY_HASH,
    BATCH_SEC_CODE,
    ENTRY_DETAIL_ADDENDUM_INDICATOR,
    ANNOTATIONS,
    FILE_ENTRY_HASH,
    BATCH_EFFECTIVE_DATE;
}
