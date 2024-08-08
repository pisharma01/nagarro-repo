package com.dm.retail.entity;


import com.dm.retail.enums.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Product {
    private Long id;
    private String productCode;
    private String name;
    private ProductType type;
    private String unitsAvailable;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
