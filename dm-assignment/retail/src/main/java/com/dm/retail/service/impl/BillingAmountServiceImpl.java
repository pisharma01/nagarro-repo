package com.dm.retail.service.impl;

import com.dm.retail.dto.ProductDto;
import com.dm.retail.dto.request.BillingAmountRequestDto;
import com.dm.retail.dto.response.BillingAmountResponseDto;
import com.dm.retail.enums.ProductType;
import com.dm.retail.enums.UserType;
import com.dm.retail.exception.CustomRetailException;
import com.dm.retail.repository.ProductRepository;
import com.dm.retail.repository.UserRepository;
import com.dm.retail.service.BillingAmountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class BillingAmountServiceImpl implements BillingAmountService {

    private static final Logger logger = LoggerFactory.getLogger(BillingAmountServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BillingAmountResponseDto computeNetBillingAmount(BillingAmountRequestDto billingAmountRequestDto) {
        logger.info("Inside BillingAmountServiceImpl.computeNetBillingAmount");
        try{
            if(billingAmountRequestDto.getUsername() == null || billingAmountRequestDto.getUsername().trim().isEmpty()){
               throw new CustomRetailException("Username can't be empty");
            }
            UserType userType = UserType.valueOf(userRepository.getUserTypeByUsername(billingAmountRequestDto.getUsername()));
            BigDecimal billingAmountBeforeDiscount = billingAmountRequestDto.getProducts().stream()
                    .filter(product -> !product.getType().equals( ProductType.GROCERY.name()))
                    .map(ProductDto::getTotalPrice)
                    .reduce(new BigDecimal("0"), BigDecimal::add);
            logger.info("billingAmountBeforeDiscount {}" , billingAmountBeforeDiscount);
            BigInteger divisorForHundredMultiples = billingAmountBeforeDiscount.toBigInteger()
                    .divide(BigInteger.valueOf(100));
            BigInteger hundredMultiplesCount =
                    divisorForHundredMultiples.compareTo(BigInteger.valueOf(1)) < 0
                            ? BigInteger.valueOf(0) : divisorForHundredMultiples;
            BigDecimal amountAgainstHundred = new BigDecimal(hundredMultiplesCount.multiply(BigInteger.valueOf(5)),
            new MathContext(2, RoundingMode.UNNECESSARY));
            logger.info("amountAgainstHundred {}", amountAgainstHundred);
            logger.info("userType.getDiscount() {}", userType.getDiscount());
            BigDecimal totalDiscountOffered =
            billingAmountBeforeDiscount.multiply(new BigDecimal(userType.getDiscount())
                    , new MathContext(2, RoundingMode.UNNECESSARY))
                                .divide(new BigDecimal(100),
                            new MathContext(2, RoundingMode.UNNECESSARY))
                    .add(amountAgainstHundred, new MathContext(2, RoundingMode.UNNECESSARY));
            logger.info("TotalDiscountOffered {}", totalDiscountOffered);
            BigDecimal initialAmount = billingAmountRequestDto.getProducts().stream()
                    .map(ProductDto::getTotalPrice)
                    .reduce(new BigDecimal("0"), BigDecimal::add);
            logger.info("InitialAmount {}", initialAmount);
            return new BillingAmountResponseDto(initialAmount.subtract(totalDiscountOffered)
                    .setScale(2, RoundingMode.UNNECESSARY));
        }catch(Exception exception){
            logger.error("Exception occurred inside computeNetBillingAmount for username {}", billingAmountRequestDto.getUsername());
            throw new CustomRetailException(exception.getMessage(), exception);
        }
    }

}
