package com.dm.retail.repository.impl;

import com.dm.retail.enums.UserType;
import com.dm.retail.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getUserTypeByUsername(String username) {
        logger.info("Inside UserRepositoryImpl.getUserTypeByUsername with username {}", username);
        String query = "SELECT USERTYPE FROM AUTH.USER_DTLS WHERE USERNAME = ?";
        try{
            logger.info("Query : {} with username : {}", query, username);
            return jdbcTemplate.queryForObject(query,String.class, username);
        }catch(Exception exception){
            logger.error("Exception occurred in getUserTypeByUsername for username {}", username);
        }
        return null;
    }
}
