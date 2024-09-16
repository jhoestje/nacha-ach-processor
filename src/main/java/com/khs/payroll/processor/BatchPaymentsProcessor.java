package com.khs.payroll.processor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.repository.PaymentBatchRepository;
import com.khs.payroll.repository.PayrollPaymentRepository;

/**
 * Batch incoming payments for transactional processing later.
 */
@Component
public class BatchPaymentsProcessor {

    private PaymentBatchRepository batchRepository;
    private PayrollPaymentRepository paymentRepository;

    public BatchPaymentsProcessor(final PaymentBatchRepository batchRepository, final PayrollPaymentRepository paymentRepository) {
        this.batchRepository = batchRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Add the payment to batches for processing on the payment effective date.
     * 
     * @param payments
     */
    public void batchPayments(final List<PayrollPayment> payments) {
        // Group by originatingDFIIdentification + payment date
//        Map<String, Map<LocalDate, List<PayrollPayment>>> paymentBatches = new HashMap<>();
        for (PayrollPayment payment : payments) {
//            String dfiId = payment.getOriginatingDFIIdentification();
//            LocalDate paymentDate = payment.getEffectiveEntryDate();

//            paymentBatches.computeIfAbsent(dfiId, k -> new HashMap<>()).computeIfAbsent(paymentDate, k -> new ArrayList<>()).add(payment);

            Optional<PaymentBatch> existingPaymentBatchOpt = batchRepository.findByEffectiveBatchDateAndOriginatingDFIIdentification(payment.getEffectiveEntryDate(),
                    payment.getOriginatingDFIIdentification());

            PaymentBatch existingPaymentBatch = existingPaymentBatchOpt.orElse(new PaymentBatch(payment.getEffectiveEntryDate(),
                    payment.getOriginatingDFIIdentification(), new PaymentBatchState()));
            // need duplicate payment checks
            paymentRepository.save(payment);
            existingPaymentBatch.addPayrollPayment(payment);
            
            batchRepository.save(existingPaymentBatch);
        }

    }
}
