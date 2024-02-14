package com.vasquez.msaccount.web;


import com.vasquez.msaccount.api.AccountApiDelegate;
import com.vasquez.msaccount.model.AccountRequest;
import com.vasquez.msaccount.model.AccountResponse;
import com.vasquez.msaccount.business.AccountService;
import com.vasquez.msaccount.web.mapper.AccountMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account api delegate.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Service
public class AccountApiDelegateImpl implements AccountApiDelegate {

  private final AccountService accountService;

  public AccountApiDelegateImpl(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> addAccount(Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
    return accountRequest
      .map(AccountMapper::toEntity)
      .flatMap(accountService::create)
      .map(AccountMapper::toResponse)
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteAccountById(String accountId, ServerWebExchange exchange) {
    return accountService.deleteById(accountId)
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> getAccountById(String accountId, ServerWebExchange exchange) {
    return accountService.findById(accountId)
      .map(AccountMapper::toResponse)
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<AccountResponse>>> getAllAccounts(ServerWebExchange exchange) {
    return Mono.just(accountService.findAll()
      .map(AccountMapper::toResponse))
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> updateAccount(String accountId, Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
    return accountRequest
      .map(AccountMapper::toEntity)
      .flatMap(acc -> accountService.update(acc, accountId))
      .map(AccountMapper::toResponse)
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> getByAccountNumber(String accountNumber, ServerWebExchange exchange) {
    return accountService.findAccountByNumber(accountNumber)
      .map(AccountMapper::toResponse)
      .map(ResponseEntity::ok);
  }
}
