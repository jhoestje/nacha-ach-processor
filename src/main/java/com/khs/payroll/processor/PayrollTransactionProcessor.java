package com.khs.payroll.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

//2. validation
//Transaction validation: Verify that each payment detail (e.g., employee bank account number, routing number, amount) is valid.
//Employee information: Match the payroll payment details to the corresponding account information stored within your bank’s system.

//3. Transaction Management
//Leverage Spring’s transactional support (@Transactional) to ensure that the process of transferring funds is atomic. If a failure occurs at any point, the entire transaction should roll back.
//If your system handles payments in batches, make sure each batch is processed completely or not at all, in case of failures.
//Implement retry mechanisms or compensating transactions if something fails during processing.
//4. Fund Transfer Mechanism
//Once you've validated the ACH file and employee account details, initiate the transfer of funds from the business’s payroll account to the employee accounts.
//Integrate with your bank's internal systems for fund transfers:
//Internal bank APIs: This might involve using APIs provided by the bank’s internal systems to move money between accounts.
//Ledger updates: Ensure that the bank's ledger is updated accordingly to reflect the changes.
//Ensure that the logic supports real-time fund settlement, and that transfers are executed efficiently.
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
            LOG.info(String.format("Processing batch %s OriginatingDFI.", batch.getOriginatingDFIIdentification()));
            batch.setBatchState(stateProcessing);
            batchRepository.save(batch);
            try {
                processBatch(batch);
                batch.setBatchState(stateComplete);
            } catch (Exception e) {
                // Handle errors (e.g., log the error, retry, etc.)
                paymentErrors.add("Bad batch");
                batch.setBatchState(stateFailed);
                batchRepository.save(batch);
            }
        }
    }

    private void processBatch(final PaymentBatch batch) {
        try {
            originAcountValidator.validate(batch);

            for (PayrollPayment payment : batch.getPayments()) {
                try {
                    LOG.info(String.format("Processing payment trace number %s.", payment.getTraceNumber()));
                    destinationAcountValidator.validate(payment);
                    accountManager.applyFunds(payment);
                    payment.setState(PaymentState.PROCESSED);
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

        } catch (Exception e) {
            LOG.error("Batch failed, System Error", e);
            // fail batch
            // fail all payments
        }
    }

}
