package com.ms.security.authserver.error.dto;

import lombok.Data;

@Data
public class ViolationDto {

    private String fieldName;
    private String message;

}
