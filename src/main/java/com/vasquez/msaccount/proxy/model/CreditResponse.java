package com.vasquez.msaccount.proxy.model;

import lombok.Data;

/**
 * CreditResponse.java
 *
 * @author Vasquez
 * @version 1.0.0
 */
@Data
public class CreditResponse {

  private String creditId;

  private String clientId;

  private String productId;

  private String cardNumber;

  private Double amountRequested;

  private Integer maxMonthsOfPayment;

  private Double monthlyFee;

  private String requestedDate;

  private String paymentDueDate;

}
