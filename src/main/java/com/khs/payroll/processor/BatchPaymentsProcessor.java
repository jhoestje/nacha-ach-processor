package com.khs.payroll.processor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.khs.payroll.domain.PayrollPayment;

/**
 * Batch incoming payments for transactional processing later.
 */
@Component
public class BatchPaymentsProcessor {
    
    //TODO:  add database batch lookup for add payments if it exists already
    public void batchPayments(final List<PayrollPayment> payments) {
        // Group by originatingDFIIdentification + payment date
        Map<String, Map<LocalDate, List<PayrollPayment>>> paymentBatches = new HashMap<>();
        for (PayrollPayment payment : payments) {
            String dfiId = payment.getOriginatingDFIIdentification();
            LocalDate paymentDate = payment.getEffectiveEntryDate();

            paymentBatches
                .computeIfAbsent(dfiId, k -> new HashMap<>())
                .computeIfAbsent(paymentDate, k -> new ArrayList<>())
                .add(payment);
        }
    }
}
