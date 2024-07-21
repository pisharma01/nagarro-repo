package com.ms.security.authserver.repository;

import com.ms.security.authserver.entity.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("Inside loadUserByEmail in UserRepositoryImpl with username as {}",username);
        try{
            String sqlQuery = "SELECT USR.ID, USR.USERNAME, USR.PASSWORD, USR.FIRSTNAME, USR.LASTNAME, " +
                    "USR.ENABLED,  ATHR.AUTHORITY FROM Auth.USER_DTLS USR " +
                    "INNER JOIN Auth.AUTHORITY_DTLS ATHR " +
                    "ON USR.ID = ATHR.USER_ID " +
                    "WHERE USR.USERNAME = ? ";
            logger.info("Query : {}", sqlQuery);
           return jdbcTemplate.query(sqlQuery, userDetailsRowMapper(), username)
                   .stream()
                   .findFirst()
                   .orElseThrow();
        }catch(Exception exception){
            logger.error("Exception occurred in UserRepositoryImpl");
            throw new UsernameNotFoundException("Username not found");
        }
    }


    private RowMapper<UserDetails> userDetailsRowMapper(){
        return new RowMapper<UserDetails>() {

            public UserDetails mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                UserDetails userDetails = new UserDetails();
                userDetails.setId(String.valueOf(resultSet.getInt("ID")));
                userDetails.setPassword(resultSet.getString("PASSWORD"));
                userDetails.setUsername(resultSet.getString("USERNAME"));
                userDetails.setFirstname(resultSet.getString("FIRSTNAME"));
                userDetails.setLastname(resultSet.getString("LASTNAME"));
                userDetails.setEnabled(resultSet.getString("ENABLED").equalsIgnoreCase("Y") ?
                        Boolean.TRUE : Boolean.FALSE);
                userDetails.setRole(resultSet.getString("AUTHORITY"));
                return userDetails;
            }
        };
    }
}
