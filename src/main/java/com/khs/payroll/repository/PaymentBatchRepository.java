package com.khs.payroll.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;

public interface PaymentBatchRepository extends MongoRepository<PaymentBatch, String> {

    List<PaymentBatch> findByPaymentDateAndStatus(LocalDate today, PaymentBatchState state);  
}
