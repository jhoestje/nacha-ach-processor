package com.khs.payroll.constant;

import java.util.Optional;

public enum AchReturnCode {

    INSUFFICIENT_FUNDS("R01", "Available balance is not sufficient to cover the debit entry."),
    ACCOUNT_CLOSED("R02", "Previously active account has been closed."),
    NO_ACCOUNT("R03", "Account number does not correspond to an open account."),
    INVALID_ACCOUNT_NUMBER("R04", "Account number structure is invalid or not recognized."),
    UNAUTHORIZED_DEBIT("R05", "Unauthorized debit entry to a consumer account using corporate SEC Code."),
    ODFI_REQUEST_RETURN("R06", "Returned at the request of the originating depository financial institution (ODFI)."),
    AUTHORIZATION_REVOKED("R07", "Customer who previously authorized an entry has revoked authorization."),
    PAYMENT_STOPPED("R08", "Stop payment has been placed on a specific ACH transaction."),
    UNCOLLECTED_FUNDS("R09", "Sufficient balance exists, but funds are not available due to uncollected checks."),
    NOT_AUTHORIZED("R10", "Customer advises that the debit entry was not authorized."),
    TRUNCATION_RETURN("R11", "Truncated check entry return."),
    BRANCH_SOLD("R12", "Bank branch has been sold to another depository financial institution (DFI)."),
    RDFI_NOT_QUALIFIED("R13", "Receiving depository financial institution (RDFI) is not qualified to participate in ACH."),
    REP_PAYEE_DECEASED("R14", "Representative payee is deceased or unable to act in that capacity."),
    ACCOUNT_HOLDER_DECEASED("R15", "The account holder or beneficiary is deceased."),
    ACCOUNT_FROZEN("R16", "The account is frozen and funds cannot be debited or credited."),
    FILE_RECORD_ERROR("R17", "Improper formatting in the ACH file."),
    IMPROPER_ENTRY_DATE("R18", "The effective entry date is invalid."),
    NON_TRANSACTION_ACCOUNT("R20", "Entry refused because the account is a non-transaction account."),
    INVALID_COMPANY_ID("R21", "Invalid company identification number."),
    INVALID_INDIVIDUAL_ID("R22", "Invalid individual identification number."),
    CREDIT_REFUSED("R23", "The receiving account refuses the credit entry."),
    DUPLICATE_ENTRY("R24", "The entry appears to be a duplicate."),
    ADDENDA_ERROR("R25", "Addenda record is incorrect or not properly formatted."),
    FIELD_ERROR("R26", "Improper field content in the ACH record."),
    DUPLICATE_RETURN("R27", "The return is a duplicate of a previously submitted return."),
    RDFI_NOT_PARTICIPANT("R28", "The receiving depository financial institution (RDFI) does not participate in the check truncation program."),
    CORPORATE_NOT_AUTHORIZED("R29", "Corporate customer advises that the entry was not authorized."),
    UNABLE_TO_POST_ENTRY("R30", "RDFI is unable to post the entry."),
    PERMISSIBLE_RETURN("R31", "Permissible return entry for corporate payments (CCD and CTX)."),
    XCK_ENTRY_RETURN("R32", "Return of a destroyed check entry (XCK)."),
    RISK_THRESHOLD_EXCEEDED("R34", "RDFI exceeded risk threshold for the transaction."),
    RDFI_ACCOUNT_HOLDER_DECEASED("R36", "RDFI advises that the account holder is deceased."),
    DOCUMENT_PRESENTED_FOR_PAYMENT("R37", "Source document presented for payment."),
    IMPROPER_SOURCE_DOCUMENT("R38", "Improper source document used in a POP entry."),
    INVALID_SOURCE_DOCUMENT("R39", "Invalid source document used in a POP entry."),
    ENR_ENTRY_RETURN("R40", "Return of an ENR (automated enrollment) entry."),
    INVALID_TRANSACTION_CODE("R41", "Invalid transaction code used in an entry."),
    ROUTING_NUMBER_MISMATCH("R42", "Routing number does not match any valid routing number."),
    IMPROPER_DEBIT_AUTHORIZATION("R51", "Improper authorization for a debit transaction."),
    STATE_LAW("R52", "State law prohibits or limits the transaction."),
    INVALID_TRANSACTION("R53", "Invalid transaction under ACH rules.");

    private final String code;
    private final String description;

    AchReturnCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

    public static Optional<AchReturnCode> getAchReturnCodeByCode(String achCode) {
        for (AchReturnCode code : AchReturnCode.values()) {
            if (code.getCode().equals(achCode)) {
                return Optional.of(code);
            }
        }
        return Optional.empty();
    }
}
