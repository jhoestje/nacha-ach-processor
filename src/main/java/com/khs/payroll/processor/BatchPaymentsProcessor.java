package com.khs.payroll.processor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.repository.PaymentAddumdaRepository;
import com.khs.payroll.repository.PaymentBatchRepository;
import com.khs.payroll.repository.PaymentBatchStateRepository;
import com.khs.payroll.repository.PayrollPaymentRepository;

/**
 * Batch incoming payments for transactional processing later.
 */
@Component
public class BatchPaymentsProcessor {

    private PaymentBatchRepository batchRepository;
    private PayrollPaymentRepository paymentRepository;
    private PaymentBatchStateRepository batchStateRepository;
    private PaymentAddumdaRepository addumdaRepository;

    public BatchPaymentsProcessor(final PaymentBatchRepository batchRepository, final PayrollPaymentRepository paymentRepository,
            final PaymentBatchStateRepository batchStateRepository, final PaymentAddumdaRepository addumdaRepository) {
        this.batchRepository = batchRepository;
        this.paymentRepository = paymentRepository;
        this.batchStateRepository = batchStateRepository;
        this.addumdaRepository = addumdaRepository;
    }

    /**
     * Add the payment to batches for processing on the payment effective date.
     * 
     * Group by originatingDFIIdentification + payment effective date
     * 
     * @param payments
     */
    public void batchPayments(final List<PayrollPayment> payments) {

        PaymentBatchState pendingState = batchStateRepository.findByState("PENDING");

        for (PayrollPayment payment : payments) {

            Optional<PaymentBatch> existingPaymentBatchOpt = batchRepository
                    .findByEffectiveBatchDateAndOriginatingDFIIdentification(payment.getEffectiveEntryDate(), payment.getOriginatingDFIIdentification());

            PaymentBatch existingPaymentBatch = existingPaymentBatchOpt.orElse(new PaymentBatch(payment.getEffectiveEntryDate(),
                    payment.getOriginatingDFIIdentification(), pendingState, payment.getCompanyIdentification()));
            // TODO: implement a duplicate payment check
            if (!CollectionUtils.isEmpty(payment.getAddumda())) {
                addumdaRepository.saveAll(payment.getAddumda());
            }
            paymentRepository.save(payment);
            existingPaymentBatch.addPayrollPayment(payment);

            batchRepository.save(existingPaymentBatch);
        }
    }
}
