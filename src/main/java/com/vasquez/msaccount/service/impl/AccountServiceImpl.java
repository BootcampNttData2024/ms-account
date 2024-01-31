package com.vasquez.msaccount.service.impl;

import com.vasquez.msaccount.entity.Account;
import com.vasquez.msaccount.entity.enums.ClientType;
import com.vasquez.msaccount.entity.enums.ProductType;
import com.vasquez.msaccount.entity.enums.ProfileType;
import com.vasquez.msaccount.proxy.CreditProxy;
import com.vasquez.msaccount.proxy.ProductProxy;
import com.vasquez.msaccount.repository.AccountRepository;
import com.vasquez.msaccount.service.AccountService;
import com.vasquez.msaccount.service.exception.AppException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ProductProxy productProxy;
    private final CreditProxy creditProxy;

    public AccountServiceImpl(AccountRepository accountRepository, ProductProxy productProxy, ProductProxy productProxy1, CreditProxy creditProxy) {
        this.accountRepository = accountRepository;
        this.productProxy = productProxy1;
        this.creditProxy = creditProxy;
    }

    @Override
    public Mono<Account> create(Account request) {
        request.setAvailableBalance(request.getMinOpening());
        return productProxy.getProductBusinessRuleById(request.getProductBusinessRuleId())
                .switchIfEmpty(Mono.error(AppException.notFound("The service.getProductBusinessRuleById not found.")))
                .flatMap(productParameter -> productProxy.getProductById(productParameter.getProductId())
                        .switchIfEmpty(Mono.error(AppException.notFound("The service productProxy.getProductById not found.")))
                        .flatMap(productResponse -> {
                            Predicate<String> evaluateEqualsProductName = (value) -> value.equals(productResponse.getName());

                            boolean isPersonalClient = ClientType.PERSONAL.getValue().equals(productParameter.getClientType());
                            boolean isBusinessClient = ClientType.BUSINESS.getValue().equals(productParameter.getClientType());
                            boolean isVipProfile = ProfileType.VIP.getValue().equals(productParameter.getProfileType());
                            boolean isPymProfile = ProfileType.PYME.getValue().equals(productParameter.getProfileType());

                            Mono<Boolean> isFoundCreditCard = creditProxy.countCreditCardByClientId(request.getClientId()).flatMap(creditCard -> creditCard > 0 ? Mono.just(true) : Mono.just(false));
                            Mono<Boolean> isFoundCurrentAccount = this.countByClientIdAndProductBusinessRuleId(request.getClientId(), productParameter.getProductBusinessRuleId()).flatMap(accountCount -> accountCount > 0 ? Mono.just(true) : Mono.just(false));

                            boolean isSavingAccount = evaluateEqualsProductName.test(ProductType.SAVING_ACCOUNT.getValue());
                            boolean isCurrentAccount = evaluateEqualsProductName.test(ProductType.CURRENT_ACCOUNT.getValue());

                            boolean isValidHeadlines = request.getHeadlinesIds().split(",").length == 1;
                            boolean isValidSignatories = request.getSignatoriesIds().split(",").length == 1;

                            return Mono.zip(isFoundCreditCard, isFoundCurrentAccount)
                                    .flatMap(response -> {
                                        boolean isFoundCreditCard1 = response.getT1();
                                        boolean isFoundCurrentAccount1 = response.getT2();
                                        if (isPersonalClient) {
                                            if (isVipProfile) {
                                                boolean isInvalidMinMonthly = productParameter.getMinMonthlyAmount() > request.getMinMonthlyAmount();
                                                if (!isFoundCreditCard1)
                                                    return Mono.error(AppException.notFound("The VIP profile requires you to have a credit card."));
                                                if (isSavingAccount && isInvalidMinMonthly)
                                                    return Mono.error(AppException.badRequest("A minimum monthly amount of 500 is required."));
                                            }
                                            if (!isValidHeadlines && isValidSignatories)
                                                return Mono.error(AppException.badRequest("The PERSONAL client type cannot be more than one owner or signers."));
                                        }
                                        if (isBusinessClient) {
                                            if (!isCurrentAccount)
                                                return Mono.error(AppException.badRequest("For the type BUSINESS client you can only create current accounts."));
                                            if (isPymProfile) {
                                                if (!isFoundCreditCard1)
                                                    return Mono.error(AppException.notFound("The PYME profile requires you to have a CREDIT CARD."));
                                                if (!isFoundCurrentAccount1)
                                                    return Mono.error(AppException.notFound("The PYME profile requires you to have a CURRENT ACCOUNT."));
                                            }
                                        }
                                        return accountRepository.save(request);
                                    });
                        }));
    }

    @Override
    public Mono<Account> update(Account request, String id) {
        return this.findById(id).flatMap(acc -> {
            acc.setAvailableBalance(request.getAvailableBalance());
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

    public Mono<Long> countByClientIdAndProductBusinessRuleId(String clientId, String productBusinessRuleId) {
        return accountRepository.countByClientIdAndProductBusinessRuleId(clientId, productBusinessRuleId);
    }
}
