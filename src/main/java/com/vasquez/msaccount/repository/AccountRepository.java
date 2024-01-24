package com.vasquez.msaccount.repository;

import com.vasquez.msaccount.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
}
