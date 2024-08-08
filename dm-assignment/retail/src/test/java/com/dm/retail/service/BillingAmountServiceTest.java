package com.dm.retail.service;


import com.dm.retail.dto.ProductDto;
import com.dm.retail.dto.request.BillingAmountRequestDto;
import com.dm.retail.dto.response.BillingAmountResponseDto;
import com.dm.retail.entity.Product;
import com.dm.retail.enums.ProductType;
import com.dm.retail.enums.UserType;
import com.dm.retail.exception.CustomRetailException;
import com.dm.retail.repository.ProductRepository;
import com.dm.retail.repository.UserRepository;
import com.dm.retail.service.impl.BillingAmountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BillingAmountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private BillingAmountServiceImpl billingAmountService;

    @Test
    public void computeBillingAmountWithCustomerAsEmployeeSuccessfully(){
        Mockito.when(userRepository.getUserTypeByUsername(any())).thenReturn(UserType.EMPLOYEE.name());
        Mockito.when(productRepository.getProductsByProductCode(any())).thenReturn(List.of(
                new Product(1L, "GRC001",  "GROCERY", ProductType.GROCERY, "100", "GROCERY", new BigDecimal("4")),
                new Product(2L, "CL001", "CLOTHING", ProductType.CLOTHING, "100", "CLOTHING", new BigDecimal("5")),
                new Product(4L, "SEC001", "CAMERA", ProductType.SECURITY_SUPPLIES, "100", "SECURITY_SUPPLIES",
                        new BigDecimal("25"))
        ));
        BillingAmountResponseDto response = billingAmountService.computeNetBillingAmount(getBillingAmountRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(new BigDecimal("145").setScale(2, RoundingMode.UNNECESSARY), response.getAmount());
    }

    @Test
    public void computeBillingAmountWithCustomerAsNullUnsuccessfully(){
        BillingAmountRequestDto request = getBillingAmountRequest();
        request.setUserId(null);
        Assertions.assertThrows(CustomRetailException.class, () -> billingAmountService.computeNetBillingAmount(request));

    }

    @Test
    public void computeBillingAmountWithInvalidProductIdsUnsuccessfully(){
        Mockito.when(userRepository.getUserTypeByUsername(any())).thenReturn(UserType.EMPLOYEE.name());
        BillingAmountRequestDto request = getInvalidProductsBillingAmountRequest();
        Mockito.when(productRepository.getProductsByProductCode(any())).thenReturn(null);
        Assertions.assertThrows(CustomRetailException.class, () -> billingAmountService.computeNetBillingAmount(request));

    }

    private BillingAmountRequestDto getBillingAmountRequest(){
        BillingAmountRequestDto request = new BillingAmountRequestDto();
        ProductDto grocery = new ProductDto("GRC001",
                 2L
                );
        ProductDto clothing = new ProductDto("CL001", 2L);
        ProductDto securitySupply = new ProductDto("SEC001", 8L);
        request.setProducts(List.of(grocery, clothing, securitySupply));
        request.setUserId(1L);
        return request;
    }

    private BillingAmountRequestDto getInvalidProductsBillingAmountRequest(){
        BillingAmountRequestDto request = new BillingAmountRequestDto();
        ProductDto grocery = new ProductDto("GRC000",
                2L
        );
        ProductDto clothing = new ProductDto("CL011", 2L);
        ProductDto securitySupply = new ProductDto("SEC032", 8L);
        request.setProducts(List.of(grocery, clothing, securitySupply));
        request.setUserId(1L);
        return request;
    }

}
