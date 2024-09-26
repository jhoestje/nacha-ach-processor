package com.khs.payroll.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.domain.PaymentBatchState;

public interface PaymentBatchRepository extends MongoRepository<PaymentBatch, String> {

    Optional<PaymentBatch> findByEffectiveBatchDateAndOriginatingDFIIdentificationAndBatchState(LocalDate effectiveBatchDate, String originatingDFIIdentification, PaymentBatchState batchState);

    List<PaymentBatch> findByEffectiveBatchDateAndBatchState(LocalDate effectiveBatchDate, PaymentBatchState batchState);

}
