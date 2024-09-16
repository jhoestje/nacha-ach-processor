package com.khs.payroll.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.PaymentBatch;

public interface PaymentBatchRepository extends MongoRepository<PaymentBatch, String> {

    Optional<PaymentBatch> findByEffectiveBatchDateAndOriginatingDFIIdentification(LocalDate effectiveBatchDate, String originatingDFIIdentification);

}
