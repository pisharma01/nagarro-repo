package com.dm.retail.dto;

import com.dm.retail.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productCode;
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private Long quantity;
    private BigDecimal totalPrice;
}
