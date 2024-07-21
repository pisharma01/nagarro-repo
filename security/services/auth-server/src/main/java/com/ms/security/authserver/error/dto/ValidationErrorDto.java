package com.ms.security.authserver.error.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorDto {

    private List<ViolationDto> violations = new ArrayList<>();

}
