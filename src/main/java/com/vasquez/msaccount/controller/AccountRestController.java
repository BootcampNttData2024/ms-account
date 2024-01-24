package com.vasquez.msaccount.controller;

import com.vasquez.msaccount.api.AccountApi;
import com.vasquez.msaccount.model.AccountRequest;
import com.vasquez.msaccount.model.AccountResponse;
import com.vasquez.msaccount.service.AccountService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountRestController implements AccountApi {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Mono<AccountResponse> addAccount(Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<Void> deleteAccountById(String accountId, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<AccountResponse> getAccountById(String accountId, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<Flux<AccountResponse>> getAllAccounts(ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<AccountResponse> updateAccount(String accountId, Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
        return null;
    }
}
