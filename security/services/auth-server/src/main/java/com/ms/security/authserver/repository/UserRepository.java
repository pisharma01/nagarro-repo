package com.ms.security.authserver.repository;


import com.ms.security.authserver.entity.UserDetails;

public interface UserRepository {

    UserDetails loadUserByUsername(String email);

}
