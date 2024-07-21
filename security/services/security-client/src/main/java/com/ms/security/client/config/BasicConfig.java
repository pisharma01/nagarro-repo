package com.ms.security.client.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

public abstract class BasicConfig {

    @Bean
    public DataSource datasource(){
        DataSourceBuilder<?> datasourceBuilder = DataSourceBuilder.create();
        return datasourceBuilder.url("jdbc:oracle:thin:@192.168.1.38:1521:orcl")
                .username("system")
                .password("Admin.123")
                .driverClassName("oracle.jdbc.driver.OracleDriver")
                .build();

    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return  new JdbcTemplate(datasource());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

}
