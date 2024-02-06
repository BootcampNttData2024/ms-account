package com.vasquez.msaccount.proxy;

import com.vasquez.msaccount.proxy.model.ProductBusinessRuleResponse;
import com.vasquez.msaccount.proxy.model.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Product proxy.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Service
public class ProductProxy {

  @Value("${microservices.ms-product.base-url}")
  private String productBaseUrl;

  /**
   * Count product by business rule id.
   *
   * @param id business rule id
   * @return Mono Integer
   */
  public Mono<ProductBusinessRuleResponse> getProductBusinessRuleById(String id) {
    return WebClient.create()
      .get()
      .uri(productBaseUrl + "/product-business-rule/" + id)
      .retrieve()
      .bodyToMono(ProductBusinessRuleResponse.class);
  }

  /**
   * Count product by client id.
   *
   * @param id client id
   * @return Mono Integer
   */
  public Mono<ProductResponse> getProductById(String id) {
    return WebClient.create()
      .get()
      .uri(productBaseUrl + "/product/" + id)
      .retrieve()
      .bodyToMono(ProductResponse.class);
  }

}
