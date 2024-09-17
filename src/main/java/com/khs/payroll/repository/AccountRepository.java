package com.khs.payroll.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khs.payroll.domain.Account;

public interface AccountRepository  extends MongoRepository<Account, String> {  

    Optional<Account> findByAccountNumber(String accountNumber);
}
