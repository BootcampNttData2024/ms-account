package com.vasquez.msaccount.entity.enums;

import lombok.Getter;

/**
 * ProductType enum.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Getter
public enum ProfileType {

  VIP("VIP"),
  PYME("PYME");
  private String value;

  ProfileType(String value) {
    this.value = value;
  }

}
