package com.dm.retail.exception.handler;

import com.dm.retail.dto.response.ErrorResponse;
import com.dm.retail.enums.ErrorCodes;
import com.dm.retail.exception.CustomRetailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomRetailException.class)
    public ResponseEntity<ErrorResponse> handleMyCustomException(CustomRetailException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.CR501.name(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
