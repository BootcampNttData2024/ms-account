package com.vasquez.msaccount.service;

import com.vasquez.msaccount.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> create(Account request);

    Mono<Account> update(Account request, String accountId);

    Mono<Account> findById(String accountId);

    Flux<Account> findAll();

    Mono<Void> deleteById(String accountId);

    Mono<Account> findAccountByNumber(String accountNumber);

}
