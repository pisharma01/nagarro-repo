package com.dm.retail.enums;

import lombok.Getter;

@Getter
public enum UserType {
    EMPLOYEE(30), AFFILIATE(20), CUSTOMER(10);

    private final Long discount;

    UserType(long discount) {
        this.discount = discount;
    }

}
