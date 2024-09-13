package com.khs.payroll.scheduled;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.repository.PaymentBatchRepository;

public class PaymentTransactionJob {
    
    private PaymentBatchRepository batchRepository;
    
    public PaymentTransactionJob(final PaymentBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }
    
    @Scheduled(cron = "0 0 2 * * ?")  // Runs every day at 2 AM
    public void processScheduledPayments() {
//        Date today = Date.to;
//        // get by date, status, and 
//        List<PaymentBatch> batchProcess = batchRepository.findByPaymentDateAndStatus(today, "PENDING");
//
//        for (ScheduledPayment payment : paymentsToProcess) {
//            try {
//                // Process the payment
//                transferFunds(payment);
//                payment.setStatus("PROCESSED");
//                paymentRepository.save(payment);
//            } catch (Exception e) {
//                // Handle errors (e.g., log the error, retry, etc.)
//                payment.setStatus("FAILED");
//                paymentRepository.save(payment);
//            }
//        }
    }
    
    @Transactional
    public void transferFunds(PaymentBatch payment) {
        // Logic to transfer funds from the payroll account to the employee’s account
        // Call to internal/external banking APIs
    }
}
