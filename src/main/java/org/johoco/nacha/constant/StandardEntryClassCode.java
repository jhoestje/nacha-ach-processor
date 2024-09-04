package org.johoco.nacha.constant;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * Nacha standard entry class (SEC) codes in a Nacha file (ACH file) include
 */
@Getter
public enum StandardEntryClassCode {
    ACCOUNTS_RECEIVABLE_CONVERSION("ARC", "Accounts Receivable Conversion"),
    BACK_OFFICE_CONVERSION("BOC", "Back Office Conversion"),
    CASH_CONCENTRATION_OR_DISBURSEMENT("CCD", "Cash Concentration or Disbursement"),
    CUSTOMER_INITIATED_ENTRY("CIE", "Customer Initiated Entry"),
    CORPORTATE_TRADE_EXCHANGE("CTX", "Corporate Trade Exchange"),
    INTERNATIONAL("IAT", "International"),
    POINT_OF_PURCHASE("POP", "Point of Purchase Entry"),
    PREARRANGED_PAYMENT_AND_DEPOSIT_ENTRY("PPD", "Prearranged Payment and Deposit Entry"),
    RE_PRESENTED_CHECK_ENTRY("RCK", "Re-Presented Check Entry"),
    TELEPHONE_INITIATED_ENTRY("TEL", "Telephone-Initiated Entry"),
    INTERNET_INITIATED_ENTRY("WEB", "Internet Initiated Entry");

    private String code;
    private String label;

    StandardEntryClassCode(final String code, final String label) {
        this.code = code;
        this.label = label;
    }
    
    public static StandardEntryClassCode codeOf(final String codeOf) throws Exception {
        for(StandardEntryClassCode e : values()) {
            if(StringUtils.equalsIgnoreCase(e.getCode(), codeOf)); {
                return e;
            }
        }
        throw new Exception(String.format("StandardEntryClassCode of %s was not found", codeOf));
    }
}
