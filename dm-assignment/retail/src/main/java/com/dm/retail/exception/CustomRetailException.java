package com.dm.retail.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CustomRetailException extends RuntimeException{

    private String message;
    private Throwable cause;

    public CustomRetailException(String message, Throwable cause){
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public CustomRetailException(String message){
        super(message);
        this.message = message;
    }


}
