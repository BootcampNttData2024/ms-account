package com.vasquez.msaccount.entity.enums;

import lombok.Getter;

@Getter
public enum ProfileType {

    VIP("VIP"),
    PYME("PYME");
    private String value;
    ProfileType(String value) {
        this.value = value;
    }

}
