package org.johoco.nacha.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchEntryDetail {
    private String transactionCode;
    private String receivingDFI;
    private String checkDigit;
    private String accountNumber;
}
