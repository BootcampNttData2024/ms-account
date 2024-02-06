package com.vasquez.msaccount.proxy.model;

import lombok.Data;

/**
 * Client response.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Data
public class ClientResponse {

  private String clientId;

  private String profileId;

  private String clientType;

  private String documentType;

  private Integer documentNumber;

  private String firstName;

  private String lastName;

  private String email;

  private Integer phoneNumber;

  private String address;
}
