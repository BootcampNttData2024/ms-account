package com.vasquez.msaccount.entity.enums;

import lombok.Getter;

@Getter
public enum ClientType {

    PERSONAL("PERSONAL"),
    BUSINESS("EMPRESARIAL");
    private String value;

    ClientType(String value) {
        this.value = value;
    }

}
