package com.vasquez.msaccount.entity.enums;

import lombok.Getter;

@Getter
public enum ProductType {
    SAVING_ACCOUNT("CUENTA DE AHORROS"),
    CURRENT_ACCOUNT("CUENTA CORRIENTE"),
    FIXED_TERM_ACCOUNT("CUENTA A PLAZO FIJO"),
    CREDIT("CREDITO");
    private String value;

    ProductType(String value) {
        this.value = value;
    }

}
