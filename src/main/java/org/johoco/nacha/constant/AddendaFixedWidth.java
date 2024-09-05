package org.johoco.nacha.constant;

import lombok.Getter;

@Getter
public enum AddendaFixedWidth {
    RECORD_TYPE_CODE(0, 1),
    ADDENDA_TYPE_CODE(1, 3),
    PAYTMENT_RELATED_INFORMATION(3, 83),
    ADDENDA_SEQUENCE_NUMBER(83, 87),
    ENTRY_DETAIL_SEQUENCE_NUMBER(87, 94);

    private int start;
    private int end;

    AddendaFixedWidth(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
