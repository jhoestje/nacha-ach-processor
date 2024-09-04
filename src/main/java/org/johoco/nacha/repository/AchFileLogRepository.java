package org.johoco.nacha.repository;

import org.johoco.nacha.domain.AchFileLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AchFileLogRepository  extends MongoRepository<AchFileLog, String> {  

//    @Query(fields="{'name' : 1, 'quantity' : 1}")
//    List<AchFileRequest> findAll();
    
}
