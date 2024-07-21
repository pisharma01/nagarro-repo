package com.ms.security.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityClientSecurityConfig extends BasicConfig{

    private static final String[] WHITE_LIST_URLS = {
            "/hello",
            "resendVerifyToken"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(athorizationManagerRequestMatchersRegistry ->{

                    athorizationManagerRequestMatchersRegistry
                            .requestMatchers(WHITE_LIST_URLS).permitAll()
                            .requestMatchers("/api/**").authenticated();
                })
                .oauth2Login(oauth2LoginCustomizer -> {
                    oauth2LoginCustomizer.loginPage("/oauth2/authorization/api-client-oidc");
                })
                .oauth2Client(Customizer.withDefaults());

        return httpSecurity.build();

    }
}
