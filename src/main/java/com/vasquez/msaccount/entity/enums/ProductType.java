package com.vasquez.msaccount.entity.enums;

import lombok.Getter;

/**
 * Client type enum.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Getter
public enum ProductType {

  SAVING_ACCOUNT("CUENTA DE AHORROS"),
  CURRENT_ACCOUNT("CUENTA CORRIENTE"),
  FIXED_TERM_ACCOUNT("CUENTA A PLAZO FIJO"),
  CREDIT_CARD("TARJETA DE CREDITO"),
  DEBIT_CARD("TARJETA DE DEBITO");
  private String value;

  ProductType(String value) {
    this.value = value;
  }

}
