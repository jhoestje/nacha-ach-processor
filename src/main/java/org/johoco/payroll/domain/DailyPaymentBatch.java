package org.johoco.payroll.domain;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("dailyPaymentBatches")
public class DailyPaymentBatch {
    
    @Id
    private String id;
    @CreatedDate
    private Instant createdDate; 
    @LastModifiedDate
    private Instant lastModifiedDate;
    // When to process
    private Instant batchDate;
    private String account;
    private PaymentBatchState batchState;
    List<PayrollPayment> payments;
    
}
