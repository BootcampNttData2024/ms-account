package com.vasquez.msaccount.proxy;

import com.vasquez.msaccount.proxy.model.ClientResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Client proxy.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Service
public class ClientProxy {

  @Value("${microservices.ms-client.base-url}")
  private String clientBaseUrl;

  /**
   * Get client by id.
   *
   * @param id client id
   * @return Mono client response
   */
  public Mono<ClientResponse> getClientById(String id) {
    return WebClient.create()
      .get()
      .uri(clientBaseUrl + "/client/" + id)
      .retrieve()
      .bodyToMono(ClientResponse.class);
  }

}
