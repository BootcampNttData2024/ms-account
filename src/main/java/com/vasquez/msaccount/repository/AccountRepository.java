package com.vasquez.msaccount.repository;

import com.vasquez.msaccount.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findByAccountNumber(String accountNumber);

    Mono<Long> countByClientIdAndProductBusinessRuleId(String clientId, String productBusinessRuleId);

}
