package com.khs.payroll.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khs.payroll.account.AccountManager;
import com.khs.payroll.ach.file.validator.DestinationAcountValidator;
import com.khs.payroll.ach.file.validator.OriginAcountValidator;
import com.khs.payroll.constant.PaymentState;
import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.exception.InvalidAccountException;
import com.khs.payroll.repository.PaymentBatchRepository;
import com.khs.payroll.repository.PaymentBatchStateRepository;
import com.khs.payroll.repository.PayrollPaymentRepository;

import jakarta.validation.ValidationException;

@Component
public class PayrollTransactionProcessor {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    private PaymentBatchRepository batchRepository;
    private PaymentBatchStateRepository batchStateRepository;
    private PayrollPaymentRepository paymentRepository;
    private OriginAcountValidator originAcountValidator;
    private DestinationAcountValidator destinationAcountValidator;
    private AccountManager accountManager;

    public PayrollTransactionProcessor(final PaymentBatchRepository batchRepository, final PaymentBatchStateRepository batchStateRepository,
            final PayrollPaymentRepository paymentRepository, final OriginAcountValidator originAcountValidator,
            final DestinationAcountValidator destinationAcountValidator, final AccountManager accountManager) {
        this.batchRepository = batchRepository;
        this.batchStateRepository = batchStateRepository;
        this.paymentRepository = paymentRepository;
        this.originAcountValidator = originAcountValidator;
        this.destinationAcountValidator = destinationAcountValidator;
        this.accountManager = accountManager;
    }

    public void process(final List<PaymentBatch> batches) {
        LOG.info(String.format("Processing %d batches.", batches.size()));
        PaymentBatchState stateProcessing = batchStateRepository.findByState("PROCESSING");
        PaymentBatchState stateComplete = batchStateRepository.findByState("COMPLETE");
        PaymentBatchState stateFailed = batchStateRepository.findByState("FAILED");

        // keep track of failed payments for reporting
        List<String> paymentErrors = new ArrayList<>();
        for (PaymentBatch batch : batches) {
            LOG.info(String.format("Processing batch for company %s.", batch.getCompanyName()));
            batch.setBatchState(stateProcessing);
            batchRepository.save(batch);
            try {
                processBatch(batch);
                batch.setBatchState(stateComplete);
            } catch (ValidationException ve) {
                LOG.error("Origin account validation failed, System Error", ve);
                batch.setBatchState(stateFailed);
            } catch (Exception e) {
                paymentErrors.add("System error processing batch for payments");
                batch.setBatchState(stateFailed);
            } finally {
                batchRepository.save(batch);
            }
        }
    }

    private void processBatch(final PaymentBatch batch) {
        originAcountValidator.validate(batch);

        for (PayrollPayment payment : batch.getPayments()) {
            try {
                LOG.info(String.format("Processing payment trace number %s.", payment.getTraceNumber()));
                destinationAcountValidator.validate(payment);
                processPayment(payment);
                LOG.info(String.format("Processed payment trace number %s.", payment.getTraceNumber()));
            } catch (InvalidAccountException iae) {
                LOG.error("Payment failed, Account Validation Error", iae);
                payment.setState(PaymentState.FAILED);
                payment.setStateReason("Account Validation Error: " + iae.getMessage());
            } catch (Exception e) {
                LOG.error("Payment failed, System Error", e);
                payment.setState(PaymentState.FAILED);
                payment.setStateReason("System Error: " + e.getMessage());
            } finally {
                paymentRepository.save(payment);
            }
        }

    }

    @Transactional
    private void processPayment(PayrollPayment payment) throws Exception {
        accountManager.applyFunds(payment);
        payment.setState(PaymentState.PROCESSED);
    }

}
