package com.vasquez.msaccount.proxy;

import com.vasquez.msaccount.proxy.model.CreditResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Credit card proxy.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Service
public class CreditProxy {

  @Value("${microservices.ms-credit.base-url}")
  private String creditBaseUrl;

  /**
   * Count credit card by client id.
   *
   * @param id client id
   * @return Mono Integer
   */
  public Mono<Integer> countCreditCardByClientId(String id) {
    return WebClient.create()
      .get()
      .uri(creditBaseUrl + "/credit-card/count?clientId=" + id)
      .retrieve()
      .bodyToMono(Integer.class);
  }

  /**
   * Count credit card by product id.
   *
   * @return Flux credit response
   */
  public Mono<Integer> countByClientIdAndPaymentDueDateLessThan(String clientId) {
    return WebClient.create()
      .get()
      .uri(creditBaseUrl + "/credit/count-expired?clientId=" + clientId)
      .retrieve()
      .bodyToMono(Integer.class);
  }

}
