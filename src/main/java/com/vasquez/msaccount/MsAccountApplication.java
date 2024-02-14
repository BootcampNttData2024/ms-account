package com.vasquez.msaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Initializes the Spring Boot application.
 *
 * @author Vasquez
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MsAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsAccountApplication.class, args);
  }

}
