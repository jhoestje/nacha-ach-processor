package com.khs.payroll.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.ach.file.record.AchPayment;

@Deprecated
public interface AchPaymentRepository  extends MongoRepository<AchPayment, String> {  

    
}
