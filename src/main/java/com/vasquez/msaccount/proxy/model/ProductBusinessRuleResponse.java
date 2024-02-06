package com.vasquez.msaccount.proxy.model;

import lombok.Data;

/**
 * Credit card response.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Data
public class ProductBusinessRuleResponse {

  private String productBusinessRuleId;

  private String productId;

  private String clientType;

  private String profileType;

  private Double maintenanceCommission;

  private Integer minHolders;

  private Integer minSignatories;

  private String maxMovementsPerMonth;

  private String dayMovementsPerMonth;

  private String maxSavingAccounts;

  private String maxCurrentAccounts;

  private String maxFixedTermAccounts;

  private String maxCredits;

  private Double minMonthlyAmount;

  private Boolean requiredCreditCard;

  private Boolean requiredCurrentAccount;

  private String maxTransactionFree;

  private Double commissionPerTransaction;

}
