package com.khs.payroll.account;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.khs.payroll.domain.Account;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.repository.AccountRepository;

/**
 * 
 */
@Component
public class WayTooSimpleAccountManager implements AccountManager {

    private AccountRepository accountRepository;

    @Override
    public void transferFunds(PayrollPayment payment) {

        Optional<Account> accountOpt = accountRepository.findByAccountNumber(payment.getReceivingDFIIdentification());
        
        if()

        // Debit the company account for the total payroll amount
        companyAccount.setBalance(companyAccount.getBalance().subtract(totalDebitAmount));
        accountRepository.save(companyAccount);

        // Credit the employee's account
        employeeAccount.setBalance(employeeAccount.getBalance().add(payment.getAmount()));
        accountRepository.save(employeeAccount);
    }

}
