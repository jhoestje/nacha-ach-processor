package org.johoco.payroll.constant;

import java.util.Optional;

import lombok.Getter;

/**
 * NACHA standard entry class (SEC) codes in an ACH file include
 */
@Getter
public enum StandardEntryClassCode {

    PPD("PPD", "Prearranged Payment and Deposit Entry", "Both", "Consumer"),
    CCD("CCD", "Corporate Credit or Debit", "Both", "Corporate"),
    CTX("CTX", "Corporate Trade Exchange", "Both", "Corporate"),
    WEB("WEB", "Web Initiated Entry", "Debit", "Consumer"),
    TEL("TEL", "Telephone Initiated Entry", "Debit", "Consumer"),
    RCK("RCK", "Re-presented Check Entry", "Debit", "Consumer"),
    POP("POP", "Point of Purchase Entry", "Debit", "Consumer"),
    ARC("ARC", "Accounts Receivable Entry", "Debit", "Consumer"),
    BOC("BOC", "Back Office Conversion Entry", "Debit", "Consumer"),
    IAT("IAT", "International ACH Transaction", "Both", "Both"),
    MTE("MTE", "Machine Transfer Entry", "Both", "Consumer"),
    SHR("SHR", "Shared Network Transaction", "Both", "Consumer"),
    POS("POS", "Point-of-Sale Entry", "Debit", "Consumer"),
    XCK("XCK", "Destroyed Check Entry", "Debit", "Consumer"),
    OTHER("OTHER", "Other or Unspecified", "Both", "Both");

    private final String secCode;
    private final String achApplication;
    private final String debitOrCredit;
    private final String consumerOrCorporate;

    StandardEntryClassCode(String secCode, String achApplication, String debitOrCredit, String consumerOrCorporate) {
        this.secCode = secCode;
        this.achApplication = achApplication;
        this.debitOrCredit = debitOrCredit;
        this.consumerOrCorporate = consumerOrCorporate;
    }

    public String getSecCode() {
        return secCode;
    }

    public String getAchApplication() {
        return achApplication;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public String getConsumerOrCorporate() {
        return consumerOrCorporate;
    }

    public static Optional<StandardEntryClassCode> findByCode(final String code) {
        for (StandardEntryClassCode secCode : StandardEntryClassCode.values()) {
            if (secCode.getSecCode().equals(code)) {
                return Optional.of(secCode);
            }
        }
        return Optional.empty();
    }
}