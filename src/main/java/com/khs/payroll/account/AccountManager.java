package com.khs.payroll.account;

import com.khs.payroll.domain.PayrollPayment;

public interface AccountManager {
    void transferFunds(PayrollPayment payment);
}
