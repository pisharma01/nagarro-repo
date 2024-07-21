package com.ms.security.authserver.config;

import com.ms.security.authserver.config.security.AuthEntryPoint;
import com.ms.security.authserver.service.impl.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthServerSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new CustomAuthenticationProvider());
    }

    @Bean
    public AuthEntryPoint authEntryPoint() {
        return new AuthEntryPoint();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(authorizeManagerRequestMatchersRegistry -> {
            authorizeManagerRequestMatchersRegistry.anyRequest().authenticated();
        })//.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authEntryPoint()))
        .formLogin(Customizer.withDefaults());

        return httpSecurity.build();

    }
}
