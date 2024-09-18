package com.khs.payroll.account;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khs.payroll.constant.TransactionCode;
import com.khs.payroll.domain.Account;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.repository.AccountRepository;

/**
 * This account manager does not reflect production worthy code.  Just a very simple credit/debit manager.
 */
@Component
public class WayTooSimpleAccountManager implements AccountManager {

    private AccountRepository accountRepository;
    
    public WayTooSimpleAccountManager(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Transactional
    @Override
    public void applyFunds(PayrollPayment payment) throws Exception {

        Account account = accountRepository.findByAccountNumber(payment.getDfiAccountNumber()).get();
        if (TransactionCode.CONSUMER_DEBIT_PAYMENT.equals(payment.getTransactionCode())
                || TransactionCode.CORPORATE_DEBIT_PAYMENT.equals(payment.getTransactionCode())) {
            // Debit the company account for the total payroll amount
            account.setAmount(account.getAmount().subtract(payment.getAmount()));
            
        } else if (TransactionCode.CONSUMER_CREDIT_DEPOSIT.equals(payment.getTransactionCode())
                || TransactionCode.CORPORATE_CREDIT_DEPOSIT.equals(payment.getTransactionCode())) {
            // Credit the employee's account
            account.setAmount(account.getAmount().add(payment.getAmount()));
        } else {
            throw new Exception("Not supported transaction");
        }
        accountRepository.save(account);
    }

}
