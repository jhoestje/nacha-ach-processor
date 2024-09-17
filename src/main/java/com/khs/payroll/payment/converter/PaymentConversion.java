package com.khs.payroll.payment.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.khs.payroll.ach.file.record.AchAddendumRecord;
import com.khs.payroll.ach.file.record.AchBatch;
import com.khs.payroll.ach.file.record.AchBatchHeaderRecord;
import com.khs.payroll.ach.file.record.AchEntryDetailRecord;
import com.khs.payroll.ach.file.record.AchPayment;
import com.khs.payroll.constant.PaymentState;
import com.khs.payroll.domain.PayrollPayment;
import com.khs.payroll.domain.PayrollPaymentAddendum;

@Component
public class PaymentConversion {

    public List<PayrollPayment> convertToPayments(final AchPayment achPayment) {
        List<PayrollPayment> payments = new ArrayList<>();
        achPayment.getBatchRecords().stream().forEach(ab -> convertAchBatch(ab, payments));
        return payments;
    }

    private void convertAchBatch(final AchBatch achBatch, final List<PayrollPayment> payments) {
        AchBatchHeaderRecord batchHeader = achBatch.getHeaderRecord();

        for (AchEntryDetailRecord ed : achBatch.getEntryDetails()) {
            PayrollPayment payment = new PayrollPayment();
            payment.setState(PaymentState.PENDING);
            payment.setCompanyName(batchHeader.getCompanyName());
            payment.setCompanyDiscretionaryData(batchHeader.getCompanyDiscretionaryData());
            payment.setCompanyIdentification(batchHeader.getCompanyIdentification());
            payment.setStandardEntryClassCode(batchHeader.getStandardEntryClassCode());
            payment.setCompanyEntryDescription(batchHeader.getCompanyEntryDescription());
            payment.setEffectiveEntryDate(batchHeader.getEffectiveEntryDate());
            payment.setOriginatorStatusCode(batchHeader.getOriginatorStatusCode());
            payment.setOriginatingDFIIdentification(batchHeader.getOriginatingDFIIdentification());
            payment.setTransactionCode(ed.getTransactionCode());
            payment.setReceivingDFIIdentification(ed.getReceivingDFIIdentification());
            payment.setCheckDigit(ed.getCheckDigit());
            payment.setDfiAccountNumber(ed.getDfiAccountNumber());
            payment.setAmount(ed.getAmount());
            payment.setIdentificationNumber(ed.getIdentificationNumber());
            payment.setReceivingName(ed.getReceivingName());
            payment.setDiscretionaryData(ed.getDiscretionaryData());
            payment.setTraceNumber(ed.getTraceNumber());
            if (ed.getAddenda() != null) {
             // @formatter:off
                List<PayrollPaymentAddendum> paymentAddendas = ed.getAddenda().stream()
                        .map(addendum -> convertAddendum(addendum))
                        .collect(Collectors.toList());
                // @formatter:on
                payment.setAddumda(paymentAddendas);
            }

            payments.add(payment);
        }
    }

    private PayrollPaymentAddendum convertAddendum(final AchAddendumRecord achAddendum) {
        PayrollPaymentAddendum addendum = new PayrollPaymentAddendum();
        addendum.setPaymentRelatedInfo(achAddendum.getPaymentRelatedInfo());
        addendum.setAddendaSequenceNumber(achAddendum.getAddendaSequenceNumber());
        addendum.setEntryDetailSequenceNumber(achAddendum.getEntryDetailSequenceNumber());
        return addendum;
    }
}
