package com.ms.security.authserver.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDetails {

    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private boolean enabled;
    private String email;
}
