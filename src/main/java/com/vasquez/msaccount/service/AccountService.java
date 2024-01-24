package com.vasquez.msaccount.service;

import com.vasquez.msaccount.entity.Account;
import com.vasquez.msaccount.repository.AccountRepository;
import com.vasquez.msaccount.util.CrudUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements CrudUtil<Account, String> {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Account> create(Account request) {
        return accountRepository.save(request);
    }

    @Override
    public Mono<Account> update(Account request, String id) {
        return accountRepository.save(request);
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return accountRepository.deleteById(id);
    }
}
