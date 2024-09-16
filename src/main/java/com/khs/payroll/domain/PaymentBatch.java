package com.khs.payroll.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document("paymentBatches")
public class PaymentBatch {
    
    @Id
    private String id;
    @CreatedDate
    private Date createdDate; 
    @LastModifiedDate
    private Date lastModifiedDate;
    // When to process
    private LocalDate effectiveBatchDate;
    private String originatingDFIIdentification;
    @DBRef
    private PaymentBatchState batchState;
    @DBRef
    List<PayrollPayment> payments;
    
    public PaymentBatch(final LocalDate effectiveEntryDate, final String originatingDFIIdentification, final PaymentBatchState batchState) {
        this.effectiveBatchDate = effectiveEntryDate;
        this.originatingDFIIdentification = originatingDFIIdentification;
        this.batchState = batchState;
    }

    public PaymentBatch addPayrollPayment(final PayrollPayment payment) {
        if (payments == null) {
            payments = new ArrayList<>();
        }
        payments.add(payment);
        return this;
    }
    
}
