package com.vasquez.msaccount.business;

import com.vasquez.msaccount.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account service.
 *
 * @author Vasquez
 * @version 1.0.
 */
public interface AccountService {

  Mono<Account> create(Account request);

  Mono<Account> update(Account request, String accountId);

  Mono<Account> findById(String accountId);

  Flux<Account> findAll();

  Mono<Void> deleteById(String accountId);

  Mono<Account> findAccountByNumber(String accountNumber);

}
