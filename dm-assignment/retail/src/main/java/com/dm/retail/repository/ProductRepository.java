package com.dm.retail.repository;

import com.dm.retail.entity.Product;

import java.util.List;

public interface ProductRepository {

   List<Product> getProductsByProductCode(List<String> productCodes);
}
