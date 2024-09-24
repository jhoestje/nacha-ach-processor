package com.khs.payroll.scheduled;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;
import com.khs.payroll.processor.PayrollTransactionProcessor;
import com.khs.payroll.repository.PaymentBatchRepository;
import com.khs.payroll.repository.PaymentBatchStateRepository;

@Component
public class PaymentTransactionJob {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private PaymentBatchRepository batchRepository;
    private PaymentBatchStateRepository batchStateRepository;
    private PayrollTransactionProcessor transactionProcessor;

    public PaymentTransactionJob(final PaymentBatchRepository batchRepository, final PaymentBatchStateRepository batchStateRepository,
            final PayrollTransactionProcessor transactionProcessor) {
        this.batchRepository = batchRepository;
        this.batchStateRepository = batchStateRepository;
        this.transactionProcessor = transactionProcessor;
    }

    // To trigger the scheduler to run every 10 seconds
//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 19 * * ?")  // Runs every day at 7 PM
    public void processScheduledPayments() {
        PaymentBatchState statePending = batchStateRepository.findByState("PENDING");
        
        LocalDate today = LocalDate.now();
        List<PaymentBatch> batchesToday = batchRepository.findByEffectiveBatchDateAndBatchState(today, statePending);
        LOG.info("Found batch count:  " + batchesToday.size());

        transactionProcessor.process(batchesToday);
    }

}
