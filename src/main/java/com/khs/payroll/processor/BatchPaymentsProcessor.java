package com.khs.payroll.processor;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger LOG = LoggerFactory.getLogger(getClass());
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
     * Will want to implement a duplicate payment rule
     * 
     * @param payments
     */
    public void batchPayments(final List<PayrollPayment> payments) {
        LOG.info("Starting to Batch payments");
        PaymentBatchState pendingState = batchStateRepository.findByState("PENDING");

        for (PayrollPayment payment : payments) {

            Optional<PaymentBatch> existingPaymentBatchOpt = batchRepository
                    .findByEffectiveBatchDateAndOriginatingDFIIdentificationAndBatchState(payment.getEffectiveEntryDate(), payment.getOriginatingDFIIdentification(), pendingState);

            PaymentBatch existingPaymentBatch = existingPaymentBatchOpt.orElse(new PaymentBatch(payment.getEffectiveEntryDate(),
                    payment.getOriginatingDFIIdentification(), pendingState, payment.getCompanyIdentification()));
            if (!CollectionUtils.isEmpty(payment.getAddumda())) {
                addumdaRepository.saveAll(payment.getAddumda());
            }
            paymentRepository.save(payment);
            existingPaymentBatch.addPayrollPayment(payment);

            batchRepository.save(existingPaymentBatch);
            LOG.info("Finished Batching payment " + payment.getTraceNumber());
        }
    }
}
