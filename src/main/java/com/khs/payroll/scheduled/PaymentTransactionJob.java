package com.khs.payroll.scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

//    @Scheduled(cron = "0 0 2 * * ?")  // Runs every day at 2 AM
    // To trigger the scheduler to run every two seconds
    @Scheduled(fixedRate = 2000)
    public void processScheduledPayments() {
        PaymentBatchState statePending = batchStateRepository.findByState("PENDING");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        LOG.info("Cron job Scheduler: Job running at - " + strDate);
        LocalDate today = LocalDate.now();
        List<PaymentBatch> batchesToday = batchRepository.findByEffectiveBatchDateAndBatchState(today, statePending);
        LOG.info("Found batch count:  " + batchesToday.size());

        transactionProcessor.process(batchesToday);
    }

}
