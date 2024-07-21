package com.ms.security.authserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/connection")
    public String getConnection(){
        return new String("INSIDE");
    }
}
