package com.dm.retail.enums;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    CR501("CR501","Internal Server Error");

    private final String errorCode;
    private final String errorMessage;

    ErrorCodes(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
