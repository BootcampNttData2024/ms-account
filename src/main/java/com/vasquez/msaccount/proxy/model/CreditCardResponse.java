package com.vasquez.msaccount.proxy.model;

import lombok.Data;

/**
 * Credit card response.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Data
public class CreditCardResponse {

  private String creditCardId;

  private String productId;

  private String clientId;

  private String cardNumber;

  private Double availableAmount;

  private Double creditLimit;

}
