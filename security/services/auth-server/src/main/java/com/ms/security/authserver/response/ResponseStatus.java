package com.ms.security.authserver.response;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ResponseStatus {

    SUCCESS, FAIL, ERROR;

    String toValue(){
        return name().toLowerCase();
    }

    @JsonCreator
    static ResponseStatus from(String value){
        return Arrays.asList(values()).stream()
                .filter(item -> item.toValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

}
