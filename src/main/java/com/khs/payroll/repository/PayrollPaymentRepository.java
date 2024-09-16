package com.khs.payroll.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.PayrollPayment;

public interface PayrollPaymentRepository extends MongoRepository<PayrollPayment, String> {

}
