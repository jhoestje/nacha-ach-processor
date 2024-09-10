package org.johoco.nacha.repository;

import org.johoco.nacha.file.domain.AchPayment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AchPaymentRepository  extends MongoRepository<AchPayment, String> {  

    
}
