package com.khs.payroll.domain;

import java.time.LocalDate;

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
    private LocalDate createdDate; 
    @LastModifiedDate
    private LocalDate lastModifiedDate;
    private String paymentRelatedInfo;
    private Integer addendaSequenceNumber;
    private Integer entryDetailSequenceNumber;
}
