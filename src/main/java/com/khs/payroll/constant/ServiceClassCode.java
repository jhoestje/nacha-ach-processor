package com.khs.payroll.constant;

import java.util.Optional;

import lombok.Getter;

@Getter
public enum ServiceClassCode {

    // Common Service Class Codes
    MIXED_DEBITS_AND_CREDITS(200, "Mixed Debits and Credits", "Both"),
    CREDITS_ONLY(220, "Credits Only", "Credit"),
    DEBITS_ONLY(225, "Debits Only", "Debit");

    private final int code;
    private final String description;
    private final String debitOrCredit;

    ServiceClassCode(final int code, final String description, final String debitOrCredit) {
        this.code = code;
        this.description = description;
        this.debitOrCredit = debitOrCredit;
    }

    public static Optional<ServiceClassCode> findByCode(final int code) {
        for (ServiceClassCode serviceClassCode : ServiceClassCode.values()) {
            if (serviceClassCode.getCode() == code) {
                return Optional.of(serviceClassCode);
            }
        }
        return Optional.empty();
    }

    public static Optional<ServiceClassCode> findByCode(final String code) {
        return findByCode(Integer.parseInt(code));
    }
}
