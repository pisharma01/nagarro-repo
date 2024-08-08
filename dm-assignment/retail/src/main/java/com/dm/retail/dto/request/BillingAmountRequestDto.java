package com.dm.retail.dto.request;

import com.dm.retail.dto.ProductDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class BillingAmountRequestDto implements Serializable {
    private String username;
    private List<ProductDto> products;
}
