package com.khs.payroll.constant;

import java.util.Optional;

import lombok.Getter;

@Getter
public enum AddendaTypeCode {
    POINT_OF_SALE_ENTRY("02"),
    PAYMENT_RELATED("05"), // Payment Related Information
    // ... IAT codes
    CORRECTED_DATA("98"), // Notification of Change (NOC)
    RETURN("99"); // Return Entry / Dishonored Return / Contested Dishonored Return

    private String code;

    AddendaTypeCode(final String code) {
        this.code = code;
    }

    public static Optional<AddendaTypeCode> getAddendaTypeCodeByCode(String achCode) {
        for (AddendaTypeCode code : AddendaTypeCode.values()) {
            if (code.getCode().equals(achCode)) {
                return Optional.of(code);
            }
        }
        return Optional.empty();
    }
}
