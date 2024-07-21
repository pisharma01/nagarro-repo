package com.ms.security.authserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;

public class CommonUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private CommonUtil() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Instatntiation not allowed");
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static String maskString(String input){
        try{
            if(input.length() <= 4){
                return input;
            }
            int maskedLength = input.length() - 4 ;
            String maskedCharacters = "*".repeat(maskedLength);
            String unmaskedCharacters = input.substring(input.length() - 4);
            return maskedCharacters + unmaskedCharacters;
        }catch(Exception ex){
            logger.error("Error masking string input");
            return "****";

        }


    }
}
