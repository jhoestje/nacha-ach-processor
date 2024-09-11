package com.khs.payroll.constant;

import lombok.Getter;

@Getter
public enum TransactionCode {

    // Transaction codes for consumer accounts
    CONSUMER_CREDIT_DEPOSIT(22, "Consumer Credit (Deposit)", "Credit", "Consumer"),
    CONSUMER_DEBIT_PAYMENT(27, "Consumer Debit (Payment)", "Debit", "Consumer"),
    // Transaction codes for corporate accounts
    CORPORATE_CREDIT_DEPOSIT(32, "Corporate Credit (Deposit)", "Credit", "Corporate"),
    CORPORATE_DEBIT_PAYMENT(37, "Corporate Debit (Payment)", "Debit", "Corporate"),
    // Prenotifications
    PRENOTIFICATION_CREDIT(23, "Prenotification Credit", "Credit", "Both"),
    PRENOTIFICATION_DEBIT(28, "Prenotification Debit", "Debit", "Both");

    private final int code;
    private final String description;
    private final String debitOrCredit;
    private final String consumerOrCorporate;

    TransactionCode(int code, String description, String debitOrCredit, String consumerOrCorporate) {
        this.code = code;
        this.description = description;
        this.debitOrCredit = debitOrCredit;
        this.consumerOrCorporate = consumerOrCorporate;
    }


    // Static method to find TransactionCode by its numeric code
    public static TransactionCode findByCode(int code) {
        for (TransactionCode transactionCode : TransactionCode.values()) {
            if (transactionCode.getCode() == code) {
                return transactionCode;
            }
        }
        // Optionally throw an exception or return null if not found
        return null;
    }
}

