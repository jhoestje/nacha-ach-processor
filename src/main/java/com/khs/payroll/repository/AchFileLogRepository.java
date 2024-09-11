package com.khs.payroll.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.AchFileLog;

public interface AchFileLogRepository  extends MongoRepository<AchFileLog, String> {  

//    @Query(fields="{'name' : 1, 'quantity' : 1}")
//    List<AchFileRequest> findAll();
    
}
