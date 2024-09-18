package com.khs.payroll.account;

import com.khs.payroll.domain.PayrollPayment;

public interface AccountManager {
    void applyFunds(PayrollPayment payment) throws Exception;
}
