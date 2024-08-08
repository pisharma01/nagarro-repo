package com.dm.retail.repository;

import com.dm.retail.entity.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository {

   List<Product> getProductsByProductCode(Set<String> productCodes);
}
