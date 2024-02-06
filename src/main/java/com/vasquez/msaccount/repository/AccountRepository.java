package com.vasquez.msaccount.repository;

import com.vasquez.msaccount.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Account repository.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

  Mono<Account> findByAccountNumber(String accountNumber);

  Mono<Long> countByClientIdAndProductId(String clientId, String productId);

}
