package com.vasquez.msaccount.proxy;

import com.vasquez.msaccount.proxy.model.CreditCardResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CreditProxy {

    @Value("${microservices.ms-credit.base-url}")
    private String creditBaseUrl;

    public Mono<Integer> countCreditCardByClientId(String id) {
        return WebClient.create()
                .get()
                .uri(creditBaseUrl + "/credit-card/count?clientId=" + id)
                .retrieve()
                .bodyToMono(Integer.class);
    }

}
