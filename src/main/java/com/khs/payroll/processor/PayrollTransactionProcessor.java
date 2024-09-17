package com.khs.payroll.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khs.payroll.ach.file.validator.OriginAcountValidator;
import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.repository.PaymentBatchRepository;
import com.khs.payroll.repository.PaymentBatchStateRepository;

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
    private OriginAcountValidator originAcountValidator;

    public PayrollTransactionProcessor(final PaymentBatchRepository batchRepository, final PaymentBatchStateRepository batchStateRepository,
            final OriginAcountValidator originAcountValidator) {
        this.batchRepository = batchRepository;
        this.batchStateRepository = batchStateRepository;
        this.originAcountValidator = originAcountValidator;
    }

    public void process(final List<PaymentBatch> batches) {
        LOG.info(String.format("Processing %d batches.", batches.size()));
        PaymentBatchState stateProcessing = batchStateRepository.findByState("PROCESSING");
        PaymentBatchState stateComplete = batchStateRepository.findByState("COMPLETE");
        PaymentBatchState stateFailed = batchStateRepository.findByState("FAILED");

        // keep track of failed payments for reporting
        List<String> paymentErrors = new ArrayList<>();
        /////////////////////// need to seed data with user accounts and info
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
        // verify origin account
        try {
            originAcountValidator.validate(batch.getOriginatingDFIIdentification(), batch.getCompanyIdentification());
            batch.getPayments().stream().forEach(p -> transferFunds(p));
        } catch (Exception e) {
            // fail batch
            // fail all payments
        }
    }

    @Transactional
    private void transferFunds(PayrollPayment payment) {
        
        LOG.info(String.format("Processing payment trace number %s.", payment.getTraceNumber()));
        // Logic to transfer funds from the payroll account to the employee’s account
        // Call to internal/external banking APIs
    }
}
