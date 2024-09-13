package com.khs.payroll.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("paymentBatches")
public class PaymentBatch {
    
    @Id
    private String id;
    @CreatedDate
    private LocalDate createdDate; 
    @LastModifiedDate
    private LocalDate lastModifiedDate;
    // When to process
    private LocalDate batchDate;
    private String account;
    private PaymentBatchState batchState;
    List<PayrollPayment> payments;
    
}
