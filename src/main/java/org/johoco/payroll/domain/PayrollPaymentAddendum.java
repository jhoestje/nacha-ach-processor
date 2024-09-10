package org.johoco.payroll.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("payrollPaymentAddenda")
public class PayrollPaymentAddendum {
    @Id
    private String id;
    @CreatedDate
    private Instant createdDate; 
    @LastModifiedDate
    private Instant lastModifiedDate;
    private String paymentRelatedInfo;
}
