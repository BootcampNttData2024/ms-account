package com.vasquez.msaccount.business.impl;

import com.vasquez.msaccount.entity.Account;
import com.vasquez.msaccount.entity.enums.ClientType;
import com.vasquez.msaccount.entity.enums.ProductType;
import com.vasquez.msaccount.entity.enums.ProfileType;
import com.vasquez.msaccount.proxy.ClientProxy;
import com.vasquez.msaccount.proxy.CreditProxy;
import com.vasquez.msaccount.proxy.ProductProxy;
import com.vasquez.msaccount.proxy.model.ClientResponse;
import com.vasquez.msaccount.proxy.model.ProductResponse;
import com.vasquez.msaccount.repository.AccountRepository;
import com.vasquez.msaccount.business.AccountService;
import com.vasquez.msaccount.business.exception.AppException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.function.Predicate;

/**
 * Account service implementation.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final ProductProxy productProxy;
  private final CreditProxy creditProxy;
  private final ClientProxy clientProxy;

  /**
   * Account service constructor.
   *
   * @param accountRepository account
   * @param productProxy      product proxy
   * @param creditProxy       credit proxy
   */
  public AccountServiceImpl(AccountRepository accountRepository, ProductProxy productProxy, CreditProxy creditProxy, ClientProxy clientProxy) {
    this.accountRepository = accountRepository;
    this.productProxy = productProxy;
    this.creditProxy = creditProxy;
    this.clientProxy = clientProxy;
  }

  @Override
  public Mono<Account> create(Account request) {
    log.info("AccountServiceImpl.create request: {}", request);
    Mono<ClientResponse> clientResponseMono = clientProxy.getClientById(request.getClientId());
    Mono<Integer> creditResponse = creditProxy.countByClientIdAndPaymentDueDateLessThan(request.getClientId());

    request.setAvailableBalance(request.getMinOpening());
    return productProxy.getProductBusinessRuleById(request.getProductBusinessRuleId())
      .doOnNext(productParameter -> log.info("AccountServiceImpl.create productParameter: {}", productParameter))
      .switchIfEmpty(Mono.error(AppException.notFound("The service.getProductBusinessRuleById not found.")))
      .flatMap(productParameter -> Mono.zip(productProxy.getProductById(productParameter.getProductId()),
          clientResponseMono, creditResponse)
        .doOnNext(responseTuple -> log.info("AccountServiceImpl.create responseTuple: {}", responseTuple))
        .switchIfEmpty(Mono.error(AppException.notFound("The service productProxy.getProductById not found.")))
        .flatMap(responseTuple2 -> {
          log.info("AccountServiceImpl.create responseTuple2: {}", responseTuple2);
          ProductResponse productResponse = responseTuple2.getT1();
          ClientResponse clientResponse = responseTuple2.getT2();
          Integer quantityExpiredCredits = responseTuple2.getT3();

          //check if exist expired credit
          if (quantityExpiredCredits >= 1)
            return Mono.error(AppException.badRequest("You have an expired credit."));

          if (!clientResponse.getClientType().equals(productParameter.getClientType()))
            return Mono.error(AppException
              .badRequest("Conflict: The clientId is of type " + clientResponse.getClientType() + " while the productBusinessRuleId is of " + productParameter.getClientType() + " type"));

          request.setProductId(productParameter.getProductId());
          Predicate<String> evaluateEqualsProductName = value -> value.equals(productResponse.getName());

          boolean isPersonalClient = ClientType.PERSONAL.getValue().equals(productParameter.getClientType());
          boolean isBusinessClient = ClientType.BUSINESS.getValue().equals(productParameter.getClientType());
          boolean isVipProfile = ProfileType.VIP.getValue().equals(productParameter.getProfileType());
          boolean isPymProfile = ProfileType.PYME.getValue().equals(productParameter.getProfileType());

          Mono<Integer> quantityCreditCard = creditProxy.countCreditCardByClientId(request.getClientId()).flatMap(Mono::just);
          Mono<Integer> quantityAccount = this.countByClientIdAndProductId(request.getClientId(), productParameter.getProductId()).flatMap(Mono::just);

          boolean isSavingAccount = evaluateEqualsProductName.test(ProductType.SAVING_ACCOUNT.getValue());
          boolean isCurrentAccount = evaluateEqualsProductName.test(ProductType.CURRENT_ACCOUNT.getValue());

          boolean isValidHeadlines = request.getHeadlinesIds().split(",").length == 1;
          boolean isValidSignatories = request.getSignatoriesIds().split(",").length == 1;

          return Mono.zip(quantityCreditCard, quantityAccount)
            .switchIfEmpty(Mono.error(AppException.notFound("The service.countCreditCardByClientId not found.")))
            .doOnNext(responseTuple -> log.info("AccountServiceImpl.create responseTuple: {}", responseTuple))
            .flatMap(response -> {
              log.info("AccountServiceImpl.create response: {}", response);
              int foundCreditCard = response.getT1();
              int foundAccount = response.getT2();
              if (isPersonalClient) {
                if (isVipProfile) {
                  boolean isInvalidMinMonthly = productParameter.getMinMonthlyAmount() > request.getMinMonthlyAmount();
                  if (foundCreditCard == 0)
                    return Mono.error(AppException.badRequest("The VIP profile requires you to have a credit card."));
                  if (isSavingAccount && isInvalidMinMonthly)
                    return Mono.error(AppException.badRequest("A minimum monthly amount of 500 is required."));
                }
                if (foundAccount >= 1)
                  return Mono.error(AppException.badRequest("The client already has a " + productResponse.getName().toUpperCase()));
                if (!isValidHeadlines && isValidSignatories)
                  return Mono.error(AppException.badRequest("The PERSONAL client type cannot be more than one owner or signers."));
              }
              if (isBusinessClient) {
                if (!isCurrentAccount)
                  return Mono.error(AppException.badRequest("For the type BUSINESS client you can only create current accounts."));
                if (isPymProfile) {
                  if (foundCreditCard == 0)
                    return Mono.error(AppException.badRequest("The PYME profile requires you to have a CREDIT CARD."));
                  if (foundAccount == 0)
                    return Mono.error(AppException.badRequest("The PYME profile requires you to have a CURRENT ACCOUNT."));
                }
              }
              return accountRepository.save(request);
            });
        }));
  }

  @Override
  public Mono<Account> update(Account request, String id) {
    log.info("AccountServiceImpl.update request: {}", request);
    return this.findById(id).flatMap(acc -> {
      acc.setAvailableBalance(request.getAvailableBalance());
      log.info("AccountServiceImpl.update acc: {}", acc);
      return accountRepository.save(acc);
    });
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

  public Mono<Account> findAccountByNumber(String accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber);
  }

  public Mono<Integer> countByClientIdAndProductId(String clientId, String productId) {
    return accountRepository.countByClientIdAndProductId(clientId, productId)
      .flatMap(res -> Mono.just(Integer.parseInt(res.toString())));
  }

}

