package com.vasquez.msaccount.web;


import com.vasquez.msaccount.api.AccountApiDelegate;
import com.vasquez.msaccount.model.AccountRequest;
import com.vasquez.msaccount.model.AccountResponse;
import com.vasquez.msaccount.service.AccountService;
import com.vasquez.msaccount.web.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private final AccountService accountService;

    public AccountApiDelegateImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Mono<AccountResponse> addAccount(Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
        return accountRequest.map(AccountMapper::toEntity)
                .flatMap(accountService::create)
                .map(AccountMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteAccountById(String accountId, ServerWebExchange exchange) {
        return accountService.deleteById(accountId);
    }

    @Override
    public Mono<AccountResponse> getAccountById(String accountId, ServerWebExchange exchange) {
        return accountService.findById(accountId).map(AccountMapper::toResponse);
    }

    @Override
    public Mono<Flux<AccountResponse>> getAllAccounts(ServerWebExchange exchange) {
        return Mono.just(accountService.findAll().map(AccountMapper::toResponse));
    }

    @Override
    public Mono<AccountResponse> updateAccount(String accountId, Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
        return accountRequest.map(AccountMapper::toEntity)
                .flatMap(acc -> accountService.update(acc, accountId))
                .map(AccountMapper::toResponse);
    }

    @Override
    public Mono<AccountResponse> getByAccountNumber(String accountNumber, ServerWebExchange exchange) {
        return accountService.findAccountByNumber(accountNumber).map(AccountMapper::toResponse);
    }
}
