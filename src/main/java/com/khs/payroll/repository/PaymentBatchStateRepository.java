package com.khs.payroll.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.PaymentBatchState;

public interface PaymentBatchStateRepository  extends MongoRepository<PaymentBatchState, String> {  

//    @Query(fields="{'name' : 1, 'quantity' : 1}")
//    List<AchFileRequest> findAll();
    
    PaymentBatchState findByState(String state);

    
}
