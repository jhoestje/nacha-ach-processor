package com.khs.payroll.ach.file.validator;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.khs.payroll.constant.AchReturnCode;
import com.khs.payroll.constant.TransactionCode;
import com.khs.payroll.domain.Account;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.exception.InvalidPaymentException;
import com.khs.payroll.repository.AccountRepository;

/**
 * Validate the details on the destination financial institution and account
 * involved in the transaction.
 * 
 * Verify the destination information from internal trusted sources.
 */
@Component
public class DestinationAcountValidator {

    private AccountRepository accountRepository;

    public DestinationAcountValidator(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Add validation rules to limit mistakes and fraud. ex: add an validation rule
     * to compare for possible duplicate payments.
     * 
     * @param originatingDFIIdentification
     * @param companyIdentification
     * @throws InvalidPaymentException
     */
    public void validate(final PayrollPayment payment) throws InvalidPaymentException {

        Optional<Account> accountOpt = accountRepository.findByAccountNumber(payment.getDfiAccountNumber());

        if (accountOpt.isEmpty()) {
            throw new InvalidPaymentException(AchReturnCode.NO_ACCOUNT ,"ACH DFI account number did not match an internal account" + payment.getTraceNumber());
        }
        Account account = accountOpt.get();
        if (!StringUtils.equals(account.getAccountName(), payment.getReceivingName())) {
            throw new InvalidPaymentException(AchReturnCode.FIELD_ERROR ,"ACH account name did not match an internal account name" + payment.getTraceNumber());
        }
        // Prenotifications Payment will have 0.00 for amount
        // does the account have enough money for the debit?
        if (TransactionCode.CONSUMER_DEBIT_PAYMENT.equals(payment.getTransactionCode())
                || TransactionCode.CORPORATE_DEBIT_PAYMENT.equals(payment.getTransactionCode())) {
            if (0 > account.getAmount().compareTo(payment.getAmount())) {
                throw new InvalidPaymentException(AchReturnCode.INSUFFICIENT_FUNDS ,"Lack of funds for debit" + payment.getTraceNumber());
            }
        }
    }

}
