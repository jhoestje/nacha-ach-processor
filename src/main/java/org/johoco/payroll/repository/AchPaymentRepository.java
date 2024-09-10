package org.johoco.payroll.repository;

import org.johoco.payroll.ach.file.vo.AchPayment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AchPaymentRepository  extends MongoRepository<AchPayment, String> {  

    
}
