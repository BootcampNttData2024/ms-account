package com.vasquez.msaccount.entity.enums;

import lombok.Getter;

/**
 * ClientType enum.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Getter
public enum ClientType {

  PERSONAL("PERSONAL"),
  BUSINESS("EMPRESARIAL");
  private String value;

  ClientType(String value) {
    this.value = value;
  }

}
