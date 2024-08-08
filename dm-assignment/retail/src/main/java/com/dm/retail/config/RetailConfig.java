package com.dm.retail.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.List;

@Configuration
public class RetailConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60)).build();
    }

    @Bean
    public DataSource datasource(){
        DataSourceBuilder<?> datasourceBuilder = DataSourceBuilder.create();
        return datasourceBuilder.url("jdbc:oracle:thin:@localhost:1521/ORCLPDB")
                .username("AUTH")
                .password("password")
                .driverClassName("oracle.jdbc.driver.OracleDriver")
                .build();

    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return  new JdbcTemplate(datasource());
    }

}
