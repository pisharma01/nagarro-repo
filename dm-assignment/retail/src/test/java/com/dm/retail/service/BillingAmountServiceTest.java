package com.dm.retail.service;


import com.dm.retail.dto.ProductDto;
import com.dm.retail.dto.request.BillingAmountRequestDto;
import com.dm.retail.dto.response.BillingAmountResponseDto;
import com.dm.retail.enums.UserType;
import com.dm.retail.exception.CustomRetailException;
import com.dm.retail.repository.ProductRepository;
import com.dm.retail.repository.UserRepository;
import com.dm.retail.service.impl.BillingAmountServiceImpl;
import org.apache.logging.log4j.util.Strings;
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
        BillingAmountResponseDto response = billingAmountService.computeNetBillingAmount(getBillingAmountRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(new BigDecimal("164").setScale(2, RoundingMode.UNNECESSARY), response.getAmount());
    }

    @Test
    public void computeBillingAmountWithCustomerAsNullUnsuccessfully(){
        BillingAmountRequestDto request = getBillingAmountRequest();
        request.setUsername(Strings.EMPTY);
        Assertions.assertThrows(CustomRetailException.class, () -> billingAmountService.computeNetBillingAmount(request));

    }

    private BillingAmountRequestDto getBillingAmountRequest(){
        BillingAmountRequestDto request = new BillingAmountRequestDto();
        ProductDto grocery = new ProductDto("GRC001", "COCA-COLA",
                "GROCERY", "DRINK", new BigDecimal("2"), 10L,
                new BigDecimal("20"));
        ProductDto clothing = new ProductDto("CL001", "POLO_SHIRT",
                "CLOTHING", "CLOTHING", new BigDecimal("2"), 10L,
                new BigDecimal("20"));
        ProductDto securitySupply = new ProductDto("SEC001", "CAMERA",
                "SECURITY_SUPPLIES", "SECURITY_SUPPLIES", new BigDecimal("25"), 8L,
                new BigDecimal("200"));
        request.setProducts(List.of(grocery, clothing, securitySupply));
        request.setUsername("pisharma");
        return request;
    }

}
