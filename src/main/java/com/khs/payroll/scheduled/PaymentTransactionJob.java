package com.khs.payroll.scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.repository.PaymentBatchRepository;
import com.khs.payroll.repository.PaymentBatchStateRepository;

@Component
public class PaymentTransactionJob {
    private Logger LOG = LoggerFactory.getLogger(getClass());
    private PaymentBatchRepository batchRepository;
    private PaymentBatchStateRepository batchState;

    public PaymentTransactionJob(final PaymentBatchRepository batchRepository, final PaymentBatchStateRepository batchState) {
        this.batchRepository = batchRepository;
        this.batchState = batchState;
    }

//    @Scheduled(cron = "0 0 2 * * ?")  // Runs every day at 2 AM
    // To trigger the scheduler to run every two seconds
    @Scheduled(fixedRate = 2000)
    public void processScheduledPayments() {
        PaymentBatchState statePending = batchState.findByState("PENDING");
        PaymentBatchState stateProcessing = batchState.findByState("PROCESSING");
        PaymentBatchState stateComplete = batchState.findByState("COMPLETE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        LOG.info("Cron job Scheduler: Job running at - " + strDate);
        LocalDate today = LocalDate.now();
        List<PaymentBatch> batchesToday = batchRepository.findByEffectiveBatchDateAndBatchState(today, statePending);
        LOG.info("Found batch count:  " + batchesToday.size());

        // keep track of failed payments for reporting
        List<String> paymentErrors = new ArrayList<>();
        
        for (PaymentBatch batch : batchesToday) {
            batch.setBatchState(stateProcessing);
            batchRepository.save(batch);
            try {
                processBatch(batch);
                batch.setBatchState(stateComplete);
            } catch (Exception e) {
                // Handle errors (e.g., log the error, retry, etc.)
                PaymentBatchState stateFailed = batchState.findByState("PENDING");
                batch.setBatchState(stateFailed);
                batchRepository.save(batch);
            }
        }
    }
    
    public void processBatch(final PaymentBatch payment) {
        // Logic to transfer funds from the payroll account to the employee’s account
        // Call to internal/external banking APIs
    }

    @Transactional
    public void transferFunds(PaymentBatch payment) {
        // Logic to transfer funds from the payroll account to the employee’s account
        // Call to internal/external banking APIs
    }
}
