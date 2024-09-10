package org.johoco.payroll.constant;

import java.util.Optional;

public enum AchReturnCode {
    
    // Administrative Return Codes (R01–R33)
    R01("Insufficient Funds", "Available balance is not sufficient to cover the dollar value of the debit entry."),
    R02("Account Closed", "Previously active account has been closed by the receiver or customer."),
    R03("No Account/Unable to Locate Account", "The account number does not correspond to the individual identified in the entry or the account number is incorrect."),
    R04("Invalid Account Number", "The account number structure is not valid."),
    R05("Unauthorized Debit to Consumer Account Using Corporate SEC Code", "Improper debit entry was transmitted to a consumer account."),
    R06("Returned per ODFI's Request", "The ODFI has requested that the RDFI return the ACH entry."),
    R07("Authorization Revoked by Customer", "Consumer who previously authorized an entry has revoked authorization."),
    R08("Payment Stopped", "Receiver has stopped payment on the entry."),
    R09("Uncollected Funds", "Sufficient funds are not yet available in the account."),
    R10("Customer Advises Unauthorized", "Receiver claims that debit entry is not authorized."),
    R11("Check Truncation Entry Return", "A return initiated by the RDFI for a check truncation entry."),
    R12("Branch Sold to Another DFI", "RDFI is no longer responsible for the account, due to a branch closure or sale."),
    R13("RDFI Not Qualified to Participate", "Receiving financial institution is not qualified to participate in ACH."),
    R14("Representative Payee Deceased or Unable to Continue in that Capacity", "The representative payee is no longer able to act in that capacity."),
    R15("Beneficiary or Account Holder Deceased", "The account holder or beneficiary of the funds has passed away."),
    R16("Account Frozen", "The account has been frozen."),
    R17("File Record Edit Criteria", "Used by the RDFI to indicate that the file failed to pass certain edit criteria."),
    R18("Improper Effective Entry Date", "The effective entry date is incorrect or not properly formatted."),
    R19("Amount Field Error", "Incorrect amount field in the ACH entry."),
    R20("Non-Transaction Account", "Entry destined for a non-transaction account."),
    R21("Invalid Company Identification", "Invalid company identification number."),
    R22("Invalid Individual ID Number", "Invalid individual ID number."),
    R23("Credit Entry Refused by Receiver", "The receiver has refused the credit entry."),
    R24("Duplicate Entry", "Duplicate of a previously initiated entry."),
    R25("Addenda Error", "Improper addenda information was supplied."),
    R26("Mandatory Field Error", "Required field missing or incorrect."),
    R27("Trace Number Error", "Incorrect or duplicate trace number."),
    R28("Routing Number Check Digit Error", "Incorrect routing number check digit."),
    R29("Corporate Customer Advises Not Authorized", "Corporate customer advises that debit is not authorized."),
    R30("RDFI Not Participant in Check Truncation Program", "RDFI is not participating in the check truncation program."),
    R31("Permissible Return Entry (CCD and CTX Only)", "Return is allowed for corporate credit or debit entries."),
    R32("RDFI Non-Settlement", "The RDFI could not settle the entry."),
    R33("Return of XCK Entry", "Return of an XCK entry."),
    
    // Return Codes (R37–R85)
    R37("Source Document Presented for Payment", "Source document has already been presented for payment."),
    R38("Stop Payment on Source Document", "Stop payment issued on the source document."),
    R39("Improper Source Document", "Improper source document."),
    R40("Improper Entry to Consumer Account", "Improper entry to a consumer account."),
    R41("Invalid Transaction Code", "Transaction code is invalid."),
    R42("Routing Number Not Qualified", "Routing number is not qualified to participate in the ACH network."),
    R43("Invalid DFI Account Number", "DFI account number is invalid."),
    R44("Invalid Individual Name", "Individual name is invalid."),
    R45("Invalid Individual Identification Number", "Invalid individual identification number."),
    R46("Invalid Representative Payee", "Invalid representative payee."),
    R47("Duplicate Return", "Duplicate return."),
    R50("State Law Affecting RCK Acceptance", "State law prohibits RCK acceptance."),
    R51("Item Related to RCK Entry is Ineligible", "RCK entry is ineligible."),
    R52("Stop Payment on Item Related to RCK Entry", "Stop payment issued on the item related to RCK entry."),
    R53("Item and RCK Entry Presented for Payment", "Both item and RCK entry presented."),
    R80("IAT Entry Coding Error", "International ACH transaction entry coding error."),
    R81("Non-Participant in IAT Program", "The RDFI is not a participant in the IAT program."),
    R82("Invalid Foreign Receiving DFI Identification", "Invalid foreign receiving DFI identification."),
    R83("Foreign Receiving DFI Unable to Settle", "Foreign receiving DFI is unable to settle."),
    R84("Entry Not Processed by Gateway", "Entry not processed by gateway."),
    R85("Incorrectly Coded Outbound International Payment", "Outbound international payment is incorrectly coded.");

    private final String reason;
    private final String description;

    AchReturnCode(String reason, String description) {
        this.reason = reason;
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }
    
    public static Optional<AchReturnCode> findByCode(String code) {
        for (AchReturnCode returnCode : AchReturnCode.values()) {
            if (returnCode.name().equals(code)) {
                return Optional.of(returnCode);
            }
        }
        return Optional.empty();
    }
}
