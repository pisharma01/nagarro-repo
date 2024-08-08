package com.dm.retail.repository.impl;

import com.dm.retail.entity.Product;
import com.dm.retail.enums.ProductType;
import com.dm.retail.exception.CustomRetailException;
import com.dm.retail.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getProductsByProductCode(Set<String> productCodes) {
        logger.info("Inside ProductRepositoryImpl.getProductsByProductCode with username {}", productCodes.toString());
        String query = "SELECT ID, PRODUCT_CODE, PRODUCT_NAME, PRODUCT_TYPE," +
                " UNITS_AVAILABLE, DESCRIPTION, PRICE FROM AUTH.PRODUCT WHERE" +
                " PRODUCT_CODE IN(:productCodes)";
        try{
            logger.info("Query : {}", query);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("productCodes", productCodes);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            return namedParameterJdbcTemplate.query(query,parameters,new RowMapper<Product>() {

                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = new Product();
                    product.setId(rs.getLong("ID"));
                    product.setProductCode(rs.getString("PRODUCT_CODE"));
                    product.setProductName(rs.getString("PRODUCT_NAME"));
                    product.setType(ProductType.valueOf(rs.getString("PRODUCT_TYPE")));
                    product.setUnitsAvailable(rs.getString("UNITS_AVAILABLE"));
                    product.setDescription(rs.getString("DESCRIPTION"));
                    product.setPrice(rs.getBigDecimal("PRICE"));
                    return product;
                }
            });
        }catch(Exception exception){
            logger.error("Exception occurred in ProductRepositoryImpl");
            throw new CustomRetailException(exception.getMessage(), exception);
        }
    }
}
