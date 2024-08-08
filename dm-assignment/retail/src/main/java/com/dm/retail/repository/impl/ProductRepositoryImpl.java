package com.dm.retail.repository.impl;

import com.dm.retail.entity.Product;
import com.dm.retail.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> getProductsByProductCode(List<String> productCodes) {

        return List.of();
    }
}
