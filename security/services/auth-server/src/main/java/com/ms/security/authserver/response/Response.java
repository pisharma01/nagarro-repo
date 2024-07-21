package com.ms.security.authserver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.security.authserver.error.dto.ValidationErrorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private String errorCode;
    private String message;
    private ResponseStatus status;
    private T data;
    private String transactionId;
    private ValidationErrorDto validationErrorDto;
    private String errorId;

    Boolean success(){
        return Objects.nonNull(status) && status == ResponseStatus.SUCCESS;
    }

    Boolean hasData(){
        return Objects.nonNull(data);
    }

}
