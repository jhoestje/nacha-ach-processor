package com.khs.payroll.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.PayrollPaymentAddendum;

public interface PaymentAddumdaRepository  extends MongoRepository<PayrollPaymentAddendum, String> {  
    
}
