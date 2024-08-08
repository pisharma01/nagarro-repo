package com.dm.retail.entity;


import com.dm.retail.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String productCode;
    private String productName;
    private ProductType type;
    private String unitsAvailable;
    private String description;
    private BigDecimal price;
}
